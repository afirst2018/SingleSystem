package com.chujiu.manager.system.managerservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alibaba.fastjson.JSON;
import com.chujiu.dto.RoleDto;
import com.chujiu.dto.UserDto;
import com.chujiu.dto.UserInfoDto;
import com.chujiu.dto.UserRelatedInfoDto;
import com.chujiu.manager.externalInterface.ExternalInterface;
import com.chujiu.manager.menumanage.controller.MenuCache;


/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[公共]
 * Description: [按用户名查询当前用户所具有的全部角色信息]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private ExternalInterface externalInterface;
	
	@Autowired
	private MenuCache menuCache;

	/**
	 * Created on 2016年5月16日
	 * Discription:[通过cas登录验证后，根据用户名加载用户信息]
	 * @return UserDetails
	 * @author: suliang
	 * @update: 2016年5月16日 下午3:15:32
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		// 按用户名查询用户信息，用作校验
		UserRelatedInfoDto userRelatedInfoDto = JSON.parseObject(externalInterface.getUsersAndRoles(),UserRelatedInfoDto.class);
		UserInfoDto userInfo = userRelatedInfoDto.getUserInfo();
		UserDto user = new UserDto();
		BeanUtils.copyProperties(userInfo, user);
		user.setUsername(userInfo.getUserName());
		//TO del
		user.setPassword("xMpCOKC5I4INzFCab3WEmw==");
		if (null != user) {
			List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
			List<RoleDto> rolesList = userRelatedInfoDto.getRolesOfUser();
			if (null != rolesList && rolesList.size() > 0) {
				for (RoleDto roleDto : rolesList) {
					if (null != roleDto && !"".equals(roleDto)) {
						authorityList.add(new SimpleGrantedAuthority(roleDto.getRoleName()));
					}
				}
			}
			//authorityList.add(new SimpleGrantedAuthority("ROLE_LOGIN")); //为开发方便，临时添加的处理，正式发布时需删除
			authorityList.add(new SimpleGrantedAuthority("ROLE_DEFAULT"));
			user.setAuthorities(authorityList);
			menuCache.setMenuList(userRelatedInfoDto.getMenuList());
			menuCache.setRolesOfUser(userRelatedInfoDto.getRolesOfUser());
			menuCache.setSubSystemList(userRelatedInfoDto.getSubSystemList());
		} else {
			throw new UsernameNotFoundException("“" + username + "”  用户不存在");
		}
		return user;
	}
}
