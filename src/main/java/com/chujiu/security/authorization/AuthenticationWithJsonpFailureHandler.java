package com.chujiu.security.authorization;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

/**
 * 扩展自org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
 * 为解决其他应用跨域登录本应用的场景添加的处理
 *
 * @author Luke Taylor
 * @author suliang
 * @since 3.0
 */
public class AuthenticationWithJsonpFailureHandler implements AuthenticationFailureHandler {
	protected final Log logger = LogFactory.getLog(getClass());

	private String defaultFailureUrl;
	private boolean forwardToDestination = false;
	private boolean allowSessionCreation = true;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public AuthenticationWithJsonpFailureHandler() {
	}

	public AuthenticationWithJsonpFailureHandler(String defaultFailureUrl) {
		setDefaultFailureUrl(defaultFailureUrl);
	}

	/**
	 * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise returns a 401 error code.
	 * <p>
	 * If redirecting or forwarding, {@code saveException} will be called to cache the exception for use in
	 * the target view.
	 */
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		// 处理跨域登录jquery jsonp 请求 start
		String accessType = request.getParameter("access_type");
		String callback = request.getParameter("jsoncallback");
		String errorMsg = "";
		if(null != exception){
			if(exception instanceof UsernameNotFoundException){
				errorMsg = exception.getMessage();//用户不存在
			}else if(exception instanceof BadCredentialsException){
				errorMsg="密码错误";
			}else if(exception instanceof IllegalValidateCodeException){
				errorMsg="验证码错误";
			}else if(exception instanceof LockedException){//指定用户是否被锁定或者解锁,锁定的用户无法通过身份验证
				errorMsg="账户已锁定";
			}else if(exception instanceof DisabledException){//是否被禁用,禁用的用户不能通过验证
				errorMsg="系统已停止此账号的服务，无法访问并使用本系统。";
			}else if(exception instanceof AccountExpiredException){//账户是否过期,过期无法通过验证
				errorMsg="账户已过期";
			}else if(exception instanceof CredentialsExpiredException){//指示是否已过期的用户的凭据(密码),过期的凭据无法通过认证
				errorMsg="认证信息已过期";//如：规定密码必须在一个月后修改，但是没有修改
			}else{
				errorMsg = "登录验证失败";
			}
		}else{
			errorMsg = "登录验证失败";
		}
		if("jsonp".equals(accessType)){
			//String jsonObject = "{\"login_status\":\"1\"}";  { \"login_status\":\"0\",\"errorMsg\":\"aaa\"}
			// String jsonObject = callback+"({ \"login_status\":\"0\"})";
			String jsonObject = callback+"({ \"login_status\":\"0\",\"errorMsg\":\""+errorMsg+"\"})";
			PrintWriter out = response.getWriter();
			out.print(jsonObject);
			out.flush();
			out.close();
			return;
		}
		// 处理跨域登录jquery jsonp 请求 end
		if (defaultFailureUrl == null) {
			logger.debug("No failure URL set, sending 401 Unauthorized error");

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
		} else {
			saveException(request, exception);

			if (forwardToDestination) {
				logger.debug("Forwarding to " + defaultFailureUrl);

				request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
			} else {
				logger.debug("Redirecting to " + defaultFailureUrl);
				redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
			}
		}
	}

	/**
	 * Caches the {@code AuthenticationException} for use in view rendering.
	 * <p>
	 * If {@code forwardToDestination} is set to true, request scope will be used, otherwise it will attempt to store
	 * the exception in the session. If there is no session and {@code allowSessionCreation} is {@code true} a session
	 * will be created. Otherwise the exception will not be stored.
	 */
	protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
		if (forwardToDestination) {
			request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		} else {
			HttpSession session = request.getSession(false);

			if (session != null || allowSessionCreation) {
				request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
			}
		}
	}

	/**
	 * The URL which will be used as the failure destination.
	 *
	 * @param defaultFailureUrl the failure URL, for example "/loginFailed.jsp".
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
				"'" + defaultFailureUrl + "' is not a valid redirect URL");
		this.defaultFailureUrl = defaultFailureUrl;
	}

	protected boolean isUseForward() {
		return forwardToDestination;
	}

	/**
	 * If set to <tt>true</tt>, performs a forward to the failure destination URL instead of a redirect. Defaults to
	 * <tt>false</tt>.
	 */
	public void setUseForward(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	/**
	 * Allows overriding of the behaviour when redirecting to a target URL.
	 */
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	protected boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}
}
