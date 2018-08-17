package com.chujiu.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统
 * Description: [Spring Security工具类]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class SecurityUtil {

	private static Logger log = LoggerFactory.getLogger(SecurityUtil.class);

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取当前登录用户用户名]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午1:41:50
	 */
	public static String getCurrentUserName(){
		String currentUserName = null;
		Map<String, Object> map = getCurrentUserMap();
		if(null != getCurrentUserMap()){
			currentUserName = String.valueOf(map.get("username"));
			if(null == currentUserName || "".equals(currentUserName)){
				currentUserName = "";
			}
		}else{
			currentUserName = "";
		}
		return currentUserName;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取当前登录用户信息]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午1:41:50
	 */
	public static Map<String, Object> getCurrentUserMap() {
		Map<String, Object> returnMap = null;
		try {
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!"java.lang.String".equals(obj.getClass().getName())) {
				returnMap = transBean2Map(obj);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return returnMap;
	}
	/**
	 * Created on   2016年8月19日
	 * Discription: [获取当前登录用户信息-用户ID]
	 * @return long
	 * @author:     yuanbz
	 * @update:     2016年8月19日
	 *
	 * */
	public static Long getCurrentUserId(){
		Long currentUserId = null;
		Map<String, Object> map = getCurrentUserMap();
		if(null != getCurrentUserMap()){
			if(!StringUtils.isEmpty(String.valueOf(map.get("id")))){
				currentUserId = new Long(String.valueOf(map.get("id")));
			}
		}
		return currentUserId;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取当前登录用户具有的所有角色名称]
	 * @return List<String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:00:22
	 */
	public static List<String> getCurrentUserRoleNames() {
		List<String> roleList = new ArrayList<String>();
		UserDetails userDetails = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != auth) {
			/*Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!"java.lang.String".equals(obj.getClass().getName())) {
				userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
				for (GrantedAuthority authority : authList) {
					roleList.add(authority.getAuthority());
				}
			}*/
			if (auth.getPrincipal() instanceof UserDetails) {
				userDetails = (UserDetails) auth.getPrincipal();
			} else if (auth.getDetails() instanceof UserDetails) {
				userDetails = (UserDetails) auth.getDetails();
			} else {
				throw new AccessDeniedException("User not properly authenticated.");
			}
			Collection<? extends GrantedAuthority> authList = userDetails.getAuthorities();
			int i=0;
			for (GrantedAuthority authority : authList) {
				if (!"ROLE_DEFAULT".equals(authority.getAuthority())) {
					roleList.add(authority.getAuthority());
				}
				i++;
			}
		}
		return roleList;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [Object转map]
	 * @param obj
	 * @return Map<String,Object>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:45:38
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性  
				if (!"class".equals(key)) {
					// 得到property对应的getter方法  
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return map;
	}
}
