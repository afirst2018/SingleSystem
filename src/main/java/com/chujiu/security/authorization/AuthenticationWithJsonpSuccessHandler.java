package com.chujiu.security.authorization;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * 扩展自org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
 * 为解决其他应用跨域登录本应用的场景添加的处理
 *
 * @author Luke Taylor
 * @author suliang
 * @since 3.0
 */
public class AuthenticationWithJsonpSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		// 处理跨域登录jquery jsonp 请求 start
		String accessType = request.getParameter("access_type");
		String callback = request.getParameter("jsoncallback");
		if("jsonp".equals(accessType)){
			// TODO  如需跨域登录，把下面的http://192.168.4.57:8080/xxxx/ 配置到pom.xml,config.properties中
			request.getSession().setAttribute("jsonp_application", "http://192.168.4.57:8080/xxxx/");
			String jsonObject = callback+"({ \"login_status\":\"1\"})";
			PrintWriter out = response.getWriter();
			out.print(jsonObject);
			out.flush();
			out.close();
			return;
		}
		// 处理跨域登录jquery jsonp 请求 end
		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}

		clearAuthenticationAttributes(request);

		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
}

