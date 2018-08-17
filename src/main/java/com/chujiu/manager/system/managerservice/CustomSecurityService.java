package com.chujiu.manager.system.managerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chujiu.dto.PrivilegeInfoStructDto;
import com.chujiu.dto.UrlAndRolesDto;
import com.chujiu.manager.externalInterface.ExternalInterface;
import com.chujiu.security.service.ICustomSecurityService;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[公共]
 * Description: [Security相关 ，加载资源（菜单），角色信息实现类。系统初始化时会调用该类中的方法。]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class CustomSecurityService implements ICustomSecurityService{

	@Autowired
	private ExternalInterface externalInterface;

	/**
	 * Created on 2016年5月16日
	 * Discription:[查询每个资源对应的所有角色,返回的list中每个记录中有两个字段，顺序固定，第一个是资源（菜单）的url，第二个是可访问该资源（菜单）的角色名称]
	 * @return LinkedList<String[]>
	 * @author: suliang
	 * @update: 2016年5月16日 下午3:11:44
	 */
	@Override
	public PrivilegeInfoStructDto getAllResourceAndRoles() {
		PrivilegeInfoStructDto privilegeInfoStructDto = JSON.parseObject(externalInterface.getAllResourceAndRoles(),PrivilegeInfoStructDto.class);
		Map<String,List<String>> menuRoleNameMap = new HashMap<String, List<String>>();
		List<String> roleNameList = new ArrayList<String>();
		roleNameList.add("ROLE_LOGIN");
		menuRoleNameMap.put("/**",roleNameList);
		// 系统欢迎页面
		roleNameList = new ArrayList<String>();
		roleNameList.add("ROLE_DEFAULT");
		menuRoleNameMap.put("/index.html**",roleNameList);
		//校验当前登录人原密码
		roleNameList = new ArrayList<String>();
		roleNameList.add("ROLE_DEFAULT");
		menuRoleNameMap.put("/sysuser/checkPwd.html",roleNameList);
		// 修改当前登录人密码	
		roleNameList = new ArrayList<String>();
		roleNameList.add("ROLE_DEFAULT");
		menuRoleNameMap.put("/sysuser/changePwdBySelf.html",roleNameList);
		setDefuatUrlAndRoles(privilegeInfoStructDto.getAllResourceAndRoles(),menuRoleNameMap);
		return privilegeInfoStructDto;
	}

	public void setDefuatUrlAndRoles(List<UrlAndRolesDto> urlAndRolesList,Map<String,List<String>> menuRoleNameMap){
		UrlAndRolesDto urlAndRolesDto = new UrlAndRolesDto();
		for (String key : menuRoleNameMap.keySet()) {
			urlAndRolesDto.setMenuUrl(key);
			urlAndRolesDto.setRoleNameList(menuRoleNameMap.get(key));
			urlAndRolesList.add(urlAndRolesDto); 
		}
	}
	
}
