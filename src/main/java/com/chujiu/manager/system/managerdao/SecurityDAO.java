package com.chujiu.manager.system.managerdao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.chujiu.dto.UserDto;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[权限管理]
 * Description: [Security 权限相关查询 ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public interface SecurityDAO {
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询所有资源（菜单）-角色对应关系]
	 * @return LinkedList<Map<String,String>>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:56:40
	 */
	LinkedList<Map<String,String>> selectAllResourceAndRoles();
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询系统内所有角色]
	 * @return LinkedList<String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:56:54
	 */
	LinkedList<String> selectAllRoles();
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询指定用户所具有的所有角色]
	 * @param username
	 * @return List<String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:57:09
	 */
	List<String> selectRolesByUsername(String username);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [通过用户名查询指定用户信息]
	 * @param username
	 * @return UserDto
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:57:29
	 */
	UserDto selectUserByName(String username);
}