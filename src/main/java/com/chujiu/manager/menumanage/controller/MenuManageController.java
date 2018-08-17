package com.chujiu.manager.menumanage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chujiu.dto.MenuDto;
import com.chujiu.manager.menumanage.managerservice.MenuManageService;



/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[菜单管理]
 * Description: [描述该类功能介绍]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
@Controller
@RequestMapping("/menuManager")
public class MenuManageController {

	@Autowired
	private MenuManageService menuManageService;

	/**
	 * Created on   2016年5月16日
	 * Discription: [显示菜单管理主页面]
	 * @return String
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:43:28
	 */
	@RequestMapping(value = "method_index")
	public String showIndex() {
		return "menumanage/sysMenu";
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
	
	@RequestMapping(value = "method_queryMenuForPrivilege")
	@ResponseBody
	public List<MenuDto> queryMenuForPrivilege() {
		return menuManageService.queryMenuForPrivilege();
	}


	/**
	 * Created on   2016年5月16日
	 * Discription: [新增菜单]
	 * @param entity
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:44:06
	 */
	@RequestMapping(value = "method_insertMenu")
	@ResponseBody
	public int insertMenu(MenuDto entity) {
		return menuManageService.insertMenu(entity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [更新菜单]
	 * @param entity
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:44:23
	 */
	@RequestMapping(value = "method_updateMenuById")
	@ResponseBody
	public int updateMenuById(MenuDto entity) {
		return menuManageService.updateMenuById(entity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [删除菜单]
	 * @param id
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:44:39
	 */
	@RequestMapping(value = "method_deleteMenu")
	@ResponseBody
	public int deleteMenu(long id) {
		return menuManageService.deleteMenuById(id);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [开发人员使用的菜单管理页面]
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:44:39
	 */
	@RequestMapping(value = "method_indexDev")
	public String showIndexDev() {
		return "menumanage/indexDev";
	}
}
