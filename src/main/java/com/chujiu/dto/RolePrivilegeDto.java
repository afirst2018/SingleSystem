package com.chujiu.dto;

import java.io.Serializable;

/**
 * Created on   2016年5月16日
 * Description: [角色权限表Dto ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public class RolePrivilegeDto implements Serializable {
	private static final long serialVersionUID = 4082830785237264607L;

	/**
	 * 角色id
	 */
	private long roleId;

	/**
	 * 权限组id
	 */
	private long privilegeId;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}

}
