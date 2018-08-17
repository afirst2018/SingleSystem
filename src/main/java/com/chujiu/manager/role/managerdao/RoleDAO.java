package com.chujiu.manager.role.managerdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.RoleDto;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[角色管理]
 * Description: [描述该类功能介绍]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public interface RoleDAO {

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询角色信息]
	 * @return List<RoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:51:30
	 */
	List<RoleDto> getRoleList();

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询角色信息]
	 * @param page
	 * @param rolename
	 * @return List<RoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:51:48
	 */
	List<RoleDto> getRoleListByPage(@Param("page") PageParameter page,@Param("name") String rolename);

	/**
	 * Created on   2016年5月16日
	 * Discription: [通过id查询角色信息]
	 * @param id
	 * @return RoleDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:02
	 */
	RoleDto getRoleById(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [通过name查询角色信息]
	 * @param name
	 * @return RoleDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:18
	 */
	RoleDto getRoleByName(String name);

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增角色]
	 * @param role
	 * @return int
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:56
	 */
	int addRole(RoleDto role);

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改角色]
	 * @param role
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:53:17
	 */
	int updateRole(RoleDto role);

	/**
	 * Created on   2016年5月16日
	 * Discription: [删除角色]
	 * @param id
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:53:42
	 */
	void deleteRole(long id);
}
