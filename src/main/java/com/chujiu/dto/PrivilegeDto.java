package com.chujiu.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on   2016年5月16日
 * Description: [权限表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public class PrivilegeDto implements Serializable {

	private static final long serialVersionUID = 924407902339566217L;

	/**
	 * id
	 */
	private long id;

	/**
	 * 权限名称
	 */
	private String privilegeName;

	/**
	 * 权限描述
	 */
	private String privilegeDesc;

	/**
	 * 状态
	 */
	private String status;

	private Set<MenuDto> menus = new HashSet<MenuDto>();

	public Set<MenuDto> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuDto> menus) {
		this.menus = menus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeDesc() {
		return privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PrivilegeDto other = (PrivilegeDto) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
}
