package com.chujiu.dto;

import java.io.Serializable;

/**
 * Created on   2016年5月16日
 * Description: [角色表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public class RoleDto implements Serializable {

	private static final long serialVersionUID = -2095808114483384821L;

	/**
	 * 主键ID
	 */
	private long id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleDesc;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 当前登录人id
	 */
	private Long userId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}