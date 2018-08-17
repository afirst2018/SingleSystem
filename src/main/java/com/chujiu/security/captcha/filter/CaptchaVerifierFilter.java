package com.chujiu.security.captcha.filter;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

public class CaptchaVerifierFilter extends OncePerRequestFilter {
	protected Logger logger = Logger.getLogger(CaptchaVerifierFilter.class);
	private String failureUrl;
	private CaptchaCaptureFilter captchaCaptureFilter;
	private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	public void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		this.logger.debug("Captcha verifier filter");
		this.logger.debug("userResponse: "+ this.captchaCaptureFilter.getUserCaptchaResponse());

		if (this.captchaCaptureFilter.getUserCaptchaResponse() != null) {
			boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(this.captchaCaptureFilter.getRequest(),this.captchaCaptureFilter.getUserCaptchaResponse());
			if (!captchaPassed) {
				this.logger.debug("Captcha is invalid!");

				this.failureHandler.setDefaultFailureUrl(this.failureUrl);
				this.logger.debug("test return failureUrl:" + this.failureUrl);
				this.failureHandler.onAuthenticationFailure(req,res,new BadCredentialsException("Captcha invalid! "+ this.captchaCaptureFilter.getRequest()+ " "+ this.captchaCaptureFilter.getUserCaptchaResponse()));
			} else {
				this.logger.debug("Captcha is valid!");
			}

			this.logger.debug("return failureUrl:" + this.failureUrl);
			resetCaptchaFields();
		}
		chain.doFilter(req, res);
	}

	public void resetCaptchaFields() {
		this.captchaCaptureFilter.setUserCaptchaResponse(null);
	}

	public String getFailureUrl() {
		return this.failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public CaptchaCaptureFilter getCaptchaCaptureFilter() {
		return this.captchaCaptureFilter;
	}

	public void setCaptchaCaptureFilter(
			CaptchaCaptureFilter captchaCaptureFilter) {
		this.captchaCaptureFilter = captchaCaptureFilter;
	}
}