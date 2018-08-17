package com.chujiu.dto;

import java.util.List;

/**
 * 资源角色dto
 * 
 * @author Administrator
 */
public class PrivilegeInfoStructDto {	
	
	//全部角色列表
	private List<RoleDto> allRoles;
	
	//菜单和角色列表
	private List<UrlAndRolesDto> allResourceAndRoles;

	public List<RoleDto> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<RoleDto> allRoles) {
		this.allRoles = allRoles;
	}

	public List<UrlAndRolesDto> getAllResourceAndRoles() {
		return allResourceAndRoles;
	}

	public void setAllResourceAndRoles(List<UrlAndRolesDto> allResourceAndRoles) {
		this.allResourceAndRoles = allResourceAndRoles;
	}
	
}



