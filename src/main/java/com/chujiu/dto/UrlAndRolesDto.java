package com.chujiu.dto;

import java.util.List;

/**
 * 菜单对应权限列表dto
 * @author Administrator
 */
public class UrlAndRolesDto {

	//菜单url
	private String menuUrl;
	
	//用户角色列表
	private List<String> roleNameList;

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}

	
}
