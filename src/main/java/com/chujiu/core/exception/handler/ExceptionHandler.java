package com.chujiu.core.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.chujiu.core.exception.ApplicationException;
import com.chujiu.core.exception.cache.ExceptionCodeCache;


/**
 * 
 * @ClassName: ExceptionHandler 
 * @Description: 公共异常处理类 
 * @author chujiu
 * @date 2016年5月16日 下午2:02:53 
 *
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	private final static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		String errorMessage = null;
		if (exception instanceof ApplicationException) {
			errorMessage = MessageFormatter
					.arrayFormat(
							ExceptionCodeCache.getExceptionCache(
									((ApplicationException) exception)
											.getErrorCode()).getMessage(),
							((ApplicationException) exception)
									.getErrorInfoParameters()).getMessage();
			log.error(errorMessage, exception);
		} else {
			errorMessage = "系统内部发生错误，稍后请重试...";
			log.error(errorMessage, exception);
		}

		boolean isAjax = isAjaxRequest(request);
		if(isAjax){
			String jsonObject = "{\"errorStatus\":\"exception\",\"errorMsg\":\""+errorMessage+"\"}";
			response.setContentType("application/json");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObject);
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error("ajax输出流操作失败", e);
			}
		}else{
		    request.getSession().setAttribute("EXCEPTION_ERRORMSG", errorMessage);
		    request.getSession().setAttribute("EXCEPTION_ERRORDETAIL", exception);
		    return new ModelAndView(new RedirectView(getBasePath(request)+"/error.html"));
		}
		return null;
	}
	/**
	 * 判断是否为ajax请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("x-requested-with");
		if (header != null && "XMLHttpRequest".equalsIgnoreCase(header)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取工程跟路径，带到前台，以便后续处理
	 *
	 * @param request
	 * @return
	 */
	private String getBasePath(HttpServletRequest request){
		String webRootPath = request.getContextPath();
		String scheme = request.getScheme();
		int port = request.getServerPort();
		String portStr = "";
		// Append the port number if it's not standard for the scheme
		if (port != (scheme.equals("http") ? 80 : 443)) { // 即判断 port != 80 || port != 443
			portStr = ":" + Integer.toString(port);
		}
		return request.getScheme() + "://" + request.getServerName() + portStr + webRootPath;
	}

}
