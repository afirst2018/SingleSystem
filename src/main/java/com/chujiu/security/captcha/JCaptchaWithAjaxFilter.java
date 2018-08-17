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
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

public class JCaptchaWithAjaxFilter implements Filter {
	// web.xml中的参数定义
	public static final String PARAM_CAPTCHA_PARAMTER_NAME = "captchaParamterName";
	public static final String PARAM_CAPTCHA_SERVICE_ID = "captchaServiceId";
	public static final String PARAM_FILTER_PROCESSES_URL = "filterProcessesUrl";
	public static final String PARAM_FAILURE_URL = "failureUrl";
	public static final String PARAM_AUTO_PASS_VALUE = "autoPassValue";

	// 預設定義值
	public static final String DEFAULT_FILTER_PROCESSES_URL = "/ajaxLoginProcess";

	public static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
	public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

	protected static Logger logger = Logger.getLogger("service");

	private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
	private String captchaServiceId = DEFAULT_CAPTCHA_SERVICE_ID;
	private String captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;
	private String autoPassValue;

	private CaptchaService captchaService;

	/**
	 * @see init(FilterConfig) 初始化Filter
	 * @param FilterConfig
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

		if (logger.isDebugEnabled()) {
			logger.debug(" JCaptcha process url:" + request.getRequestURI());
		}

		// 符合filterProcessesUrl验证处理请求，其他为产生验证图片的请求
		if (StringUtils.startsWith(servletPath, filterProcessesUrl)) {
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
	 * 初始化web.xml中定义的filter init-param.
	 */
	protected void initParameters(final FilterConfig fConfig) {  
		
		if (StringUtils.isBlank(fConfig.getInitParameter(PARAM_FAILURE_URL))) {  
			throw new IllegalArgumentException("CaptchaFilter缺少failureUrl參數");  
		}  
		
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
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * @see validateCaptchaChallenge(HttpServletRequest) 检查验证码
	 * @param HttpServletRequest
	 * @return boolean
	 */
	protected boolean validateCaptchaChallenge(final HttpServletRequest request) {
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(captchaParamterName);
			// 自动通过值不为空，检查输入值是否等于自动通过值
			if (StringUtils.isNotEmpty(autoPassValue)&& autoPassValue.equals(challengeResponse)) {
				return true;
			}
			return captchaService.validateResponseForID(captchaID,challengeResponse);
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * @see redirectFailureUrl(HttpServletRequest，HttpServletResponse) 重定向到失败的页面
	 * @param HttpServletRequest
	 *            ，HttpServletResponse
	 * @return void
	 * @throws IOException
	 */
	protected void redirectFailureUrl(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(),
						JsonEncoding.UTF8);
		try {
			// 登录验证失败返回 1
			objectMapper.writeValue(jsonGenerator, "1");
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: "
					+ ex.getMessage(), ex);
		}
		// response.sendRedirect(request.getContextPath() + failureUrl);
	}

	/**
	 * @see setDisableCacheHeader(HttpServletResponse) 设置浏览器禁止使用Cache
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}
}
