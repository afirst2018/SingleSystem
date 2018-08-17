package com.chujiu.security.authorization;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

/**
 * 自定义登出处理，（如果是其他应用跨域登录本应用，退出登录后返回其他应用）
 *
 * @author suliang
 */
public class CustomLogoutHandler implements LogoutHandler {

	public CustomLogoutHandler() {
	}

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		try {
			String webapp_url = (String)request.getSession().getAttribute("jsonp_application");
			if(!StringUtils.isEmpty(webapp_url)){
				response.sendRedirect((String)request.getSession().getAttribute("jsonp_application"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
