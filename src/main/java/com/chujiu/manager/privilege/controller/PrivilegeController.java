package com.chujiu.manager.privilege.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chujiu.core.util.SpringContextUtils;
import com.chujiu.security.access.InvocationSecurityMetadataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SqlTool;
import com.chujiu.dto.MenuDto;
import com.chujiu.dto.PrivilegeDto;
import com.chujiu.dto.PrivilegeMenuDto;
import com.chujiu.manager.menumanage.managerservice.MenuManageService;
import com.chujiu.manager.privilege.managerservice.PrivilegeService;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[权限管理]
 * Description: [权限管理]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Controller
@RequestMapping("privilege")
public class PrivilegeController {

	@Autowired
	private PrivilegeService privilegeService;
	
	@Autowired
	private MenuManageService menuManageService;

	/**
	 * Created on   2016年5月16日
	 * Discription: [跳转到权限列表页面]
	 * @param modelMap
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:25:49
	 */
	@RequestMapping("method_index")
	public String index(ModelMap modelMap) {
		PageParameter page = new PageParameter();
		List<PrivilegeDto> list = privilegeService.queryPrivilegeListByPage(page, null);
		modelMap.put("page", page);
		modelMap.put("resultList", list);
		return "privilege/privilegeListManage";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询权限列表]
	 * @param page
	 * @param privilegeEntity
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:26:10
	 */
	@ResponseBody
	@RequestMapping("method_queryPrivilegeList")
	public Map<String, Object> queryPrivilegeList(PageParameter page,
			PrivilegeDto privilegeEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 记录查询条件,为翻页查询使用。
		// 说明：翻页时使用的查询条件是点击“查询按钮”记录的查询条件，而不使用用户临时输入的查询条件。
		map.put("priNameTxtHdn", privilegeEntity.getPrivilegeName());
		map.put("priDescTxtHdn", privilegeEntity.getPrivilegeDesc());
		privilegeEntity.setPrivilegeName(SqlTool.transfer(privilegeEntity.getPrivilegeName()));
		privilegeEntity.setPrivilegeDesc(SqlTool.transfer(privilegeEntity.getPrivilegeDesc()));
		List<PrivilegeDto> list = privilegeService.queryPrivilegeListByPage(page, privilegeEntity);
		map.put("page", page);
		map.put("resultList", list);
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验待插入的数据是否已存在]
	 * @param privilegeEntity
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:27:13
	 */
	@ResponseBody
	@RequestMapping("method_checkExistOne")
	public Map<String, Object> checkExistOne(PrivilegeDto privilegeEntity) {
		PrivilegeDto entity = privilegeService.queryPrivilegeListForCheck(privilegeEntity);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == entity) {
			map.put("flag", "0");
		} else {
			map.put("flag", "1");
		}
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [向权限表新增数据]
	 * @param privilegeEntity
	 * @return Map<String,String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:27:33
	 */
	@ResponseBody
	@RequestMapping("method_addPrivilegeList")
	public Map<String, String> addPrivilegeList(PrivilegeDto privilegeEntity) {
		Map<String, String> resultMap = new HashMap<String, String>();
		int count = privilegeService.addPrivilegeList(privilegeEntity);
		String message = "";
		if (count > 0) {
			message = "增加成功";
		} else {
			message = "增加失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改权限表数据]
	 * @param privilegeEntity
	 * @return Map<String,String>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:27:53
	 */
	@ResponseBody
	@RequestMapping("method_updPrivilegeList")
	public Map<String, String> updPrivilegeList(PrivilegeDto privilegeEntity) {
		Map<String, String> resultMap = new HashMap<String, String>();
		int count = privilegeService.updatePrivilegeListById(privilegeEntity);
		String message = "";
		if (count > 0) {
			message = "修改成功";
		} else {
			message = "修改失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id查询权限]
	 * @param id
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:28:13
	 */
	@ResponseBody
	@RequestMapping("method_queryPrivilegeListById")
	public Map<String, Object> queryPrivilegeListById(
			@RequestParam(value = "id", required = true) long id) {
		PrivilegeDto entity = privilegeService.queryPrivilegeListById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == entity) {
			map.put("flag", "false");
		} else {
			map.put("flag", "true");
			map.put("entity", entity);
		}
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id删除权限]
	 * @param id
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:28:32
	 */
	@ResponseBody
	@RequestMapping("method_delPrivilegeListById")
	public Map<String, Object> delPrivilegeListById(
			@RequestParam(value = "id", required = true) long id) {
		boolean flag = privilegeService.deletePrivilegeListById(id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "";
		if (flag) {
			message = "删除成功";
		} else {
			message = "删除失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据权限id获取对应的所有menuId]
	 * @param id
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:28:44
	 */
	@ResponseBody
	@RequestMapping("method_getMenuIds")
	public Map<String, Object> showPrivilegeListPage(
			@RequestParam(value = "id", required = true) long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PrivilegeMenuDto> list = privilegeService.queryMenuIdByPid(id);
		map.put("menuIds", list);
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [保存权限，菜单关系]
	 * @param addIds
	 * @param delIds
	 * @param privilegeId
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:29:07
	 */
	@ResponseBody
	@RequestMapping("method_savePrivilegeMenuRela")
	public Map<String, Object> savePrivilegeMenuRela(
			@RequestParam(value = "addIds", required = false) String addIds,
			@RequestParam(value = "delIds", required = false) String delIds,
			@RequestParam(value = "privilegeId", required = true) long privilegeId) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<PrivilegeMenuDto> addList = new ArrayList<PrivilegeMenuDto>();
		List<PrivilegeMenuDto> delList = new ArrayList<PrivilegeMenuDto>();
		PrivilegeMenuDto entity = null;

		String addIdsArr[] = null;
		if (null != addIds && !"".equals(addIds)) {
			addIdsArr = addIds.split(",");
			for (String addId : addIdsArr) {
				if (null != addId && !"".equals(addId)) {
					entity = new PrivilegeMenuDto();
					entity.setMenuId(Long.parseLong(addId));
					entity.setPrivilegeId(privilegeId);
					addList.add(entity);
				}
			}
		}
		String delIdsArr[] = null;
		if (null != delIds && !"".equals(delIds)) {
			delIdsArr = delIds.split(",");
			for (String delId : delIdsArr) {
				if (null != delId && !"".equals(delId)) {
					entity = new PrivilegeMenuDto();
					entity.setMenuId(Long.parseLong(delId));
					entity.setPrivilegeId(privilegeId);
					delList.add(entity);
				}
			}
		}
		boolean flag = privilegeService.savePrivilegeMenuRela(addList, delList);
		String message = "";
		if (flag) {
			message = "操作成功";
		} else {
			message = "操作失败";
		}
		map.put("message", message);
		return map;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [开发用权限设定页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:29:07
	 */
	@RequestMapping("method_indexDev")
	public String indexDev(ModelMap modelMap) {
		PageParameter page = new PageParameter();
		List<PrivilegeDto> list = privilegeService.queryPrivilegeListByPage(page, null);
		modelMap.put("page", page);
		modelMap.put("resultList", list);
		return "privilege/privilegeListManageDev";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [开发用，刷新权限信息配置，当手动在数据库中配置权限时，无须重启tomcat服务]
	 * @author:     suliang
	 * @update:     2016年9月2日 下午2:25:49
	 */
	@RequestMapping("method_refreshPrivelege")
	public void refreshPrivelege() {
		InvocationSecurityMetadataSource metadataSrc = (InvocationSecurityMetadataSource) SpringContextUtils.getBeanById("invocationSecurityMetadataSource");
		metadataSrc.initResources();
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询权限对应的菜单信息]
	 * @author:     suliang
	 * @update:     2016年9月2日 下午2:25:49
	 */
	@RequestMapping(value = "method_queryMenuForPrivilege")
	@ResponseBody
	public List<MenuDto> queryMenuForPrivilege() {
		return menuManageService.queryMenuForPrivilege();
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询菜单]
	 * @return List<MenuDto>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:43:45
	 */
	@RequestMapping(value = "method_queryMenu")
	@ResponseBody
	public List<MenuDto> queryMenu() {
		return menuManageService.queryMenu();
	}
}
