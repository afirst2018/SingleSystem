package com.chujiu.manager.role.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SecurityUtil;
import com.chujiu.core.util.SqlTool;
import com.chujiu.dto.PrivilegeDto;
import com.chujiu.dto.RoleDto;
import com.chujiu.dto.RolePrivilegeDto;
import com.chujiu.manager.privilege.managerservice.PrivilegeService;
import com.chujiu.manager.role.managerservice.RoleService;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[角色管理]
 * Description: [角色管理]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PrivilegeService privilegeService;

	/**
	 * Created on   2016年5月16日
	 * Discription: [首页显示]
	 * @param modelMap
	 * @return String
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:30:34
	 */
	@RequestMapping("method_index")
	public String index(ModelMap modelMap) {
		List<PrivilegeDto> plist = privilegeService.getPrivilegeList();
		modelMap.put("authList", plist);
		return "role/role";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取角色列表]
	 * @param pageNo
	 * @param rolename
	 * @return Map<String,Object>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:31:05
	 */
	@RequestMapping("method_getrolelist")
	public @ResponseBody Map<String, Object> getRoleList(
			@RequestParam(value = "page", required = false) Integer pageNo,
			@RequestParam(value = "u", required = false) String rolename) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageParameter page = new PageParameter();
		if (pageNo != null) {
			page.setPageNo(pageNo);
		} else {
			page.setPageNo(1);
		}
		List<RoleDto> list = roleService.getRoleListByPage(page, rolename);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取角色信息]
	 * @param id
	 * @return RoleDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:31:18
	 */
	@RequestMapping("method_getRoleInfo")
	public @ResponseBody RoleDto getRoleInfo(@RequestParam(value = "id", required = true) long id) {
		RoleDto role = roleService.getRoleById(id);
		return role;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [增加角色]
	 * @param role
	 * @return Map<String,Object>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:31:39
	 */
	@RequestMapping("method_addrole")
	public @ResponseBody Map<String, Object> addRole(RoleDto role) {
		Map<String, Object> result = new HashMap<String, Object>();
		String name = role.getRoleName();
		RoleDto roleCheck = roleService.getRoleByName(name);
		if (roleCheck != null) {
			result.put("result", false);
			result.put("msg", "该名称角色已存在！");
		} else if (name.equals("ROLE_ANONYMOUS") || name.equals("ROLE_LOGIN") || name.equals("ROLE_DEFAULT")) {
			result.put("result", false);
			result.put("msg", name + " 为系统预留的角色名称，不可使用");
		} else {
			// int newid = roleService.addRole(role);
			role.setUserId(SecurityUtil.getCurrentUserId());
			roleService.addRole(role);
			result.put("result", true);
			result.put("newid", role.getId());
		}
		return result;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验角色名是否存在]
	 * @param rolename
	 * @return Map<String,Boolean>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:31:58
	 */
	@RequestMapping("method_checkrolename")
	public @ResponseBody Map<String, Boolean> checkRoleName(
			@RequestParam(value = "roleName", required = true) String rolename) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		RoleDto roleCheck = roleService.getRoleByName(rolename);
		if (roleCheck != null) {
			result.put("valid", false);
		} else {
			result.put("valid", true);
		}
		return result;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改角色]
	 * @param role
	 * @return boolean
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:32:19
	 */
	@RequestMapping("method_updaterole")
	public @ResponseBody boolean updateRole(RoleDto role) {
		boolean flag = false;
		role.setUserId(SecurityUtil.getCurrentUserId());
		int cnt = roleService.updateRole(role);
		if(cnt > 0){
			flag = true;
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [删除角色]
	 * @param id
	 * @return boolean
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:32:29
	 */
	@RequestMapping("method_deleterole")
	public @ResponseBody boolean deleteRole(@RequestParam(value = "id", required = true) long id) {
		boolean flag = false;
		try{
			flag = roleService.deleteRole(id);
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取角色权限]
	 * @param id
	 * @return List<RolePrivilegeDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:32:41
	 */
	@RequestMapping("method_getroleauth")
	public @ResponseBody List<RolePrivilegeDto> getRoleAuth(
			@RequestParam(value = "id", required = true) long id) {
		return privilegeService.getRoleAuth(id);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [保存角色权限]
	 * @param roleid
	 * @param auth
	 * @return boolean
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:32:53
	 */
	@RequestMapping("method_saveroleauth")
	public @ResponseBody boolean saveRoleAuth(
			@RequestParam(value = "rid", required = true) long roleid,
			@RequestParam(value = "auth[]", required = false) long[] auth) {
		List<Map<String, Long>> parm = new ArrayList<Map<String, Long>>();
		if (auth != null) {
			for (long auth_id : auth) {
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("roleId", roleid);
				map.put("privilegeId", auth_id);
				parm.add(map);
			}
		}
		privilegeService.saveRoleAuth(roleid, parm);
		return true;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询权限信息]
	 * @param page
	 * @param roleName
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:20:35
	 */
	@ResponseBody
	@RequestMapping("method_queryRole")
	public Map<String, Object> queryRole(PageParameter page, @RequestParam String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 记录查询条件,为翻页查询使用。
		// 说明：翻页时使用的查询条件是点击“查询按钮”记录的查询条件，而不使用用户临时输入的查询条件。
		map.put("roleNameConditionTxtHdn", roleName);
		List<RoleDto> list = roleService.getRoleListByPage(page,SqlTool.transfer(roleName));
		map.put("page", page);
		map.put("resultList", list);
		return map;
	}
}
