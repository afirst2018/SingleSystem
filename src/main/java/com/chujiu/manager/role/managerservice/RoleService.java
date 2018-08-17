package com.chujiu.manager.role.managerservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.RoleDto;
import com.chujiu.manager.role.managerdao.RoleDAO;
import com.chujiu.manager.role.managerdao.RolePrivilegeRelaDAO;
import com.chujiu.manager.sysuser.managerdao.UserRoleRelaDAO;

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
@Service
public class RoleService {

	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private UserRoleRelaDAO userRoleRelaDao;

	@Autowired
	RolePrivilegeRelaDAO rolePrivilegeRelaDAO;

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取所有角色列表]
	 * @return List<RoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:17:19
	 */
	public List<RoleDto> getRoleList() {
		return roleDao.getRoleList();
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页获取所有角色列表]
	 * @param page
	 * @param rolename
	 * @return List<RoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:17:35
	 */
	public List<RoleDto> getRoleListByPage(PageParameter page, String rolename) {
		return roleDao.getRoleListByPage(page, rolename);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按id获取角色信息]
	 * @param id
	 * @return RoleDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:17:48
	 */
	public RoleDto getRoleById(long id) {
		return roleDao.getRoleById(id);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按name获取角色信息]
	 * @param name
	 * @return RoleDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:18:06
	 */
	public RoleDto getRoleByName(String name) {
		return roleDao.getRoleByName(name);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增角色信息]
	 * @param role
	 * @return int
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:18:22
	 */
	public int addRole(RoleDto role) {
		return roleDao.addRole(role);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改角色信息]
	 * @param role void
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:18:39
	 */
	public int updateRole(RoleDto role) {
		return roleDao.updateRole(role);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [删除角色信息]
	 * @param id void
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:18:51
	 */
	public boolean deleteRole(long id) {
		userRoleRelaDao.deleteRelaByRoleId(id);
		rolePrivilegeRelaDAO.deleteRoleAuth(id);
		roleDao.deleteRole(id);
		return true;
	}
}
