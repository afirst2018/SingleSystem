package com.chujiu.dto;

import java.io.Serializable;

/**
 * Created on   2016年5月16日
 * Description: [用户角色关系表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
public class UserRoleDto implements Serializable {

	private static final long serialVersionUID = -5060709297468207381L;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 角色id
	 */
	private long roleId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
