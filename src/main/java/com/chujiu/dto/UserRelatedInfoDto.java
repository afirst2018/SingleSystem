package com.chujiu.dto;

import java.util.List;

/**
 * 用户以及相关信息dto
 * @author Administrator
 *
 */
public class UserRelatedInfoDto {
		
	//用户信息
	private UserInfoDto userInfo;
	
	//用户角色列表
	private List<RoleDto> rolesOfUser;
	
	//子系统信息列表
	private List<SubSystemDto> subSystemList;
	
	//当前用户权限菜单列表
	private List<MenuDto> menuList;

	public List<RoleDto> getRolesOfUser() {
		return rolesOfUser;
	}

	public void setRolesOfUser(List<RoleDto> rolesOfUser) {
		this.rolesOfUser = rolesOfUser;
	}

	public UserInfoDto getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoDto userInfo) {
		this.userInfo = userInfo;
	}

	public List<SubSystemDto> getSubSystemList() {
		return subSystemList;
	}

	public void setSubSystemList(List<SubSystemDto> subSystemList) {
		this.subSystemList = subSystemList;
	}

	public List<MenuDto> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuDto> menuList) {
		this.menuList = menuList;
	}
		
}
