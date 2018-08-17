package com.chujiu.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.chujiu.dto.PrivilegeInfoStructDto;
import com.chujiu.dto.RoleDto;
import com.chujiu.dto.UrlAndRolesDto;
import com.chujiu.security.service.ICustomSecurityService;

/**
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 * @author suliang@win-stock.com.cn
 * @version 1.0
 * @see com.chujiu.security.interceptor.CustomFilterSecurityInterceptor
 */
public class InvocationSecurityMetadataSource implements InitializingBean,FilterInvocationSecurityMetadataSource {

	private ICustomSecurityService cllCustomSecurityService;
	private HashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new HashMap<RequestMatcher, Collection<ConfigAttribute>>();
	private Collection<ConfigAttribute> allAttribute = new HashSet<ConfigAttribute>();

	/**
	 * 初始化所有资源和资源对应的角色
	 */
	public void initResources() {
		if(null == cllCustomSecurityService){
			throw new RuntimeException(ICustomSecurityService.class.getName() + "接口的实例为空，无法初始化资源");
		}else{
			PrivilegeInfoStructDto privilegeInfoStructDto = cllCustomSecurityService.getAllResourceAndRoles();
			// 读取所有角色
			List<RoleDto> allRoleNameList = privilegeInfoStructDto.getAllRoles();
			SecurityConfig attrConfig = null;
			if(null != allRoleNameList && allRoleNameList.size() > 0){
				for(RoleDto role : allRoleNameList){
					if(null != role && !"".equals(role)){
						attrConfig = new SecurityConfig(role.getRoleName());
						allAttribute.add(attrConfig);
					}
				}
			}
			// 查询每个资源所对应的所有角色
			List<UrlAndRolesDto> urlAndRolesList = privilegeInfoStructDto.getAllResourceAndRoles();
			Collection<ConfigAttribute> array = null;
			RequestMatcher matcher = null;
			SecurityConfig securityConfig = null;
			String url = "";
			if(null != urlAndRolesList && urlAndRolesList.size() > 0){
				for(UrlAndRolesDto ur : urlAndRolesList){					
					array = new ArrayList<ConfigAttribute>();
					url = ur.getMenuUrl();
					if(null != url && !"".equals(url)){
						matcher = new AntPathRequestMatcher(url);
						List<String> roleNameList = ur.getRoleNameList();
						for(String roleName : roleNameList){
							if(roleName != null && !"".equals(roleName)){
								securityConfig = new SecurityConfig(roleName);
								array.add(securityConfig);
							}
						}
						requestMap.put(matcher, array);						
					}
				}
			}
		}
	}

	/**
	 * 根据资源获取需要的权限名称
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// 把对象转化为请求
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		// 循环整个Map 看看有没有可以匹配的,如果有匹配的就立刻返回
		Collection<ConfigAttribute> attrHashSet = new HashSet<ConfigAttribute>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				attrHashSet.addAll(entry.getValue());
			}
		}
		if (attrHashSet.size() > 0) {
			return new ArrayList<ConfigAttribute>(attrHashSet);
		}
		return Collections.emptyList();
	}

	/**
	 * 获取所有权限
	 * 
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return this.allAttribute;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	public ICustomSecurityService getCllCustomSecurityService() {
		return cllCustomSecurityService;
	}

	public void setCllCustomSecurityService(
			ICustomSecurityService cllCustomSecurityService) {
		this.cllCustomSecurityService = cllCustomSecurityService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initResources();
	}
}
