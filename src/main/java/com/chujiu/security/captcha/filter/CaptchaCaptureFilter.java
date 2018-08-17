package com.chujiu.security.captcha.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

public class CaptchaCaptureFilter extends OncePerRequestFilter {
	protected Logger logger = Logger.getLogger(CaptchaCaptureFilter.class);
	private String userCaptchaResponse;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public void doFilterInternal(HttpServletRequest req,HttpServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		this.logger.debug("Captcha capture filter");
		if (req.getParameter("jcaptcha") != null) {
			this.request = req;
			this.userCaptchaResponse = req.getParameter("jcaptcha");
		}
		this.logger.debug("userResponse: " + this.userCaptchaResponse);
		chain.doFilter(req, res);
	}

	public String getUserCaptchaResponse() {
		return this.userCaptchaResponse;
	}

	public void setUserCaptchaResponse(String userCaptchaResponse) {
		this.userCaptchaResponse = userCaptchaResponse;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}