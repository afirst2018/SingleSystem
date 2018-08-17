package com.chujiu.dto;

import java.io.Serializable;

/**
 * Created on   2016年5月16日
 * Description: [权限菜单关系表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class PrivilegeMenuDto implements Serializable {
	private static final long serialVersionUID = 4082830785237264607L;

	/**
	 * 权限id
	 */
	private long privilegeId;

	/**
	 * 菜单id
	 */
	private long menuId;

	public long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
}
