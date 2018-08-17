package com.chujiu.security.access;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 *  登录切入点处理类
 * 
 * @author suliang@win-stock.com.cn
 * @version 1.0
 * @see com.chujiu.security.interceptor.CustomFilterSecurityInterceptor
 */
@SuppressWarnings("deprecation")
public class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	protected static Logger logger=LoggerFactory.getLogger(AuthenticationEntryPoint.class);
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String redirectUrl = null;
		if (this.isUseForward()) {
			if (this.isForceHttps() && "http".equals(request.getScheme())) {
				// First redirect the current request to HTTPS.
				// When that request is received, the forward to the login page will be used.
				redirectUrl = buildHttpsRedirectUrlForRequest(httpRequest);
			}
			if (redirectUrl == null) {
				String loginForm = determineUrlToUseForThisRequest(httpRequest, httpResponse, authException);
				if (logger.isDebugEnabled()) {
					logger.debug("Server side forward to: " + loginForm);
				}
				RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginForm);
				dispatcher.forward(request, response);
				return;
			}
		} else {
			// redirect to login page. Use https if forceHttps true
			redirectUrl = buildRedirectUrlToLoginPage(httpRequest, httpResponse, authException);
		}
		redirectStrategy.sendRedirect(httpRequest, httpResponse, redirectUrl);
	}
}
