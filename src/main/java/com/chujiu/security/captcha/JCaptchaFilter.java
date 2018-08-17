package com.chujiu.security.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

public class JCaptchaFilter implements Filter {
	// web.xml中的参数定义
	public static final String PARAM_CAPTCHA_PARAMTER_NAME = "captchaParamterName";
	public static final String PARAM_CAPTCHA_SERVICE_ID = "captchaServiceId";
	public static final String PARAM_FILTER_PROCESSES_URL = "filterProcessesUrl";
	public static final String PARAM_FAILURE_URL = "failureUrl";
	public static final String PARAM_AUTO_PASS_VALUE = "autoPassValue";

	public static final String DEFAULT_FILTER_PROCESSES_URL = "/loginProcess";

	public static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
	public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

	protected static Logger logger = Logger.getLogger("service");

	private String failureUrl;
	private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
	private String captchaServiceId = DEFAULT_CAPTCHA_SERVICE_ID;
	private String captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;
	private String autoPassValue;

	private CaptchaService captchaService;

	/**
	 * @see init(FilterConfig) 初始化Filter
	 * @param filterConfig
	 * @return void
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		initParameters(filterConfig);
		initCaptchaService(filterConfig);
	}

	public void doFilter(ServletRequest theRequest,ServletResponse theResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) theRequest;
		HttpServletResponse response = (HttpServletResponse) theResponse;
		String servletPath = request.getServletPath();

		String url = request.getRequestURI();

		if (logger.isDebugEnabled()) {
			logger.debug(" JCaptcha process url:" + url);
		}

		// 符合filterProcessesUrl验证处理请求，其他为产生验证图片的请求
		if (StringUtils.startsWith(servletPath, filterProcessesUrl)) {
			// 由于验证码的校验处理已在spring security 中的
			// com.chujiu.security.authorization.DaoAuthenticationProvider
			// 处理，该分支永远无法执行，可为非spring security应用使用该分支
			boolean validated = validateCaptchaChallenge(request);
			if (validated) {
				chain.doFilter(request, response);
			} else {
				redirectFailureUrl(request, response);
			}
		} else {
			genernateCaptchaImage(request, response);
		}
	}

	/**
	 * @see destory() 销毁Filter
	 * @return void
	 */
	public void destroy() {
	}

	/**
	 * 初始化web.xml中定义的filter init-param
	 */
	protected void initParameters(final FilterConfig fConfig) {
		if (StringUtils.isBlank(fConfig.getInitParameter(PARAM_FAILURE_URL))) {
			// 由于现在在spring security 流程中校验，所以不用指定 failureUrl
			// throw new
			// IllegalArgumentException("CaptchaFilter缺少failureUrl參數");
		}
		failureUrl = fConfig.getInitParameter(PARAM_FAILURE_URL);

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_FILTER_PROCESSES_URL))) {
			filterProcessesUrl = fConfig.getInitParameter(PARAM_FILTER_PROCESSES_URL);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_CAPTCHA_SERVICE_ID))) {
			captchaServiceId = fConfig.getInitParameter(PARAM_CAPTCHA_SERVICE_ID);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_CAPTCHA_PARAMTER_NAME))) {
			captchaParamterName = fConfig.getInitParameter(PARAM_CAPTCHA_PARAMTER_NAME);
		}

		if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_AUTO_PASS_VALUE))) {
			autoPassValue = fConfig.getInitParameter(PARAM_AUTO_PASS_VALUE);
		}
	}

	/**
	 * @see initCaptchaService(FilterConfig) 使用ApplicatonContext取得CaptchaService
	 * @param FilterConfig
	 * @return void
	 */
	protected void initCaptchaService(final FilterConfig fConfig) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		captchaService = (CaptchaService) context.getBean(captchaServiceId);
	}

	/**
	 * @see generateCaptchaImage(HttpServletRequest,HttpServletResponse) 产生验证码图片
	 * @param HttpServletRequest
	 *            ,HttpServletResponse
	 * @return void
	 */
	protected void genernateCaptchaImage(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		setDisableCacheHeader(response);
		ServletOutputStream out = response.getOutputStream();
		String captchaId = request.getSession(true).getId();
		BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId);
		ImageIO.write(challenge, "jpg", out);
		try {
			out.flush();
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
		} finally {
			out.close();
		}
	}

	/**
	 * @see validateCaptchaChallenge(HttpServletRequest) 检查验证码
	 * @param HttpServletRequest
	 * @return boolean
	 */
	protected boolean validateCaptchaChallenge(final HttpServletRequest request) {
		boolean flag = false;
		try {
			String captchaID = request.getSession(true).getId();
			String challengeResponse = request.getParameter(captchaParamterName);
			// 自动通过值不为空，检查输入值是否等于自动通过值
			if (StringUtils.isNotEmpty(autoPassValue) && autoPassValue.equals(challengeResponse)) {
				return true;
			}
			flag =  captchaService.validateResponseForID(captchaID,challengeResponse);
		} catch (CaptchaServiceException e) {
			// 登录页面放太长时间，session超时，导致发生此异常，
			// logger.error(e.getMessage(), e);
			flag =  false;
		}
		return flag;
	}

  /** 
   * @see redirectFailureUrl(HttpServletRequest，HttpServletResponse) 重定向到失败页面
   * @param request
   * @param response
   * @return void
   * @throws IOException
   */  
	protected void redirectFailureUrl(final HttpServletRequest request,final HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + failureUrl);  
	}

	/**
	 * @see setDisableCacheHeader(HttpServletResponse) 设置浏览器禁用Cache
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0L);
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
	}
}
