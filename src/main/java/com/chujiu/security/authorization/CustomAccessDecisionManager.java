package com.chujiu.security.authorization;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * security 决策处理器 
 * 
 * @author suliang@win-stock.com.cn
 * @version 1.0
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {
	@Override
	public void decide(Authentication authentication, Object object,Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (null == configAttributes) {
			return;
		}
		Iterator<ConfigAttribute> cons = configAttributes.iterator();
		while (cons.hasNext()) {
			ConfigAttribute ca = cons.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			// gra 为用户所被赋予的权限，needRole为访问相应的资源应具有的权限
			for (GrantedAuthority gra : authentication.getAuthorities()) {
				if (needRole.trim().equals(gra.getAuthority().trim())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("无权限，拒绝访问。");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}

