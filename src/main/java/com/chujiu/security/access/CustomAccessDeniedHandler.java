package com.chujiu.security.access;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 增加ajax方式的无权限访问处理
 * 在AccessDeniedHandler的 默认实现（ AccessDeniedHandlerImpl） 基础上 稍作修改 
 * 
 * @see org.springframework.security.web.access.AccessDeniedHandlerImpl
 * @author suliang
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	protected static final Log logger = LogFactory.getLog(CustomAccessDeniedHandler.class);

	private String errorPage;

	public CustomAccessDeniedHandler() {
	}

	public CustomAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		boolean isAjax = isAjaxRequest(request);
		if(isAjax){
			String jsonObject = "{\"accessDeniedStatus\":\"no_permission\",\"basePath\":\""+getBasePath(request)+"\"}";
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(jsonObject);
			out.flush();
			out.close();
		}else{
			if (!response.isCommitted()) {
				if (errorPage != null) {
					// Put exception into request scope (perhaps of use to a view)
					request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
					// Set the 403 status code.
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					// forward to error page.
					RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
					dispatcher.forward(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
				}
			}
		}
	}

	/**
	 * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
	 *
	 * @param errorPage the dispatcher path to display
	 *
	 * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
	 */
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("CustomAccessDeniedHandler :: errorPage must begin with '/'");
		}
		this.errorPage = errorPage;
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
