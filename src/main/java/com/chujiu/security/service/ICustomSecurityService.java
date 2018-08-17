package com.chujiu.security.service;

import com.chujiu.dto.PrivilegeInfoStructDto;

/**
 * ICllCustomSecurityService
 * 初始化权限，资源，角色信息接口，需要相应的web应用实现该接口
 * 系统初始化时会调用该类中的方法。
 * 
 * @author suliang@win-stock.com.cn
 * @version 1.0
 * @see com.chujiu.security.access.InvocationSecurityMetadataSource
 */
public interface ICustomSecurityService {
	
	/**
	 * 查询每个资源（URL）所对应的所有角色
	 * 
	 * @return
	 */
	public PrivilegeInfoStructDto getAllResourceAndRoles();
	
}
