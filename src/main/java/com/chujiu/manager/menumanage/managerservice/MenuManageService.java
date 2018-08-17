package com.chujiu.manager.menumanage.managerservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.chujiu.dto.MenuDto;
import com.chujiu.dto.MenuTreeNode;
import com.chujiu.manager.menumanage.managerdao.MenuManageDAO;
import com.chujiu.manager.privilege.managerdao.PrivilegeDAO;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[菜单管理]
 * Description: [菜单管理]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
@Service
public class MenuManageService {
	public static final String PARENT_KEY = "PARENT_KEY";
	@Autowired
	private MenuManageDAO menuManageDao;

	@Autowired
	private PrivilegeDAO privilegeDAO;
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询菜单]
	 * @return List<MenuDto>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:48:25
	 */
	public List<MenuDto> queryMenu() {
		return menuManageDao.queryMenu();
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询菜单展示权限]
	 * @return List<MenuDto>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:48:25
	 */
	public List<MenuDto> queryMenuForPrivilege() {
		return menuManageDao.queryMenuForPrivilege();
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增菜单]
	 * @param entity
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:48:43
	 */
	public int insertMenu(MenuDto entity) {
		DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		entity.setIsleaf("1");
		entity.setMenuType("1");
		entity.setMenuName("新增菜单" + df.format(new Date()));
		entity.setOrderNum("1");
		menuManageDao.insertMenu(entity);
		return Integer.parseInt(entity.getId());
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改菜单]
	 * @param entity
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:49:20
	 */
	public int updateMenuById(MenuDto entity) {
		return menuManageDao.updateMenu(entity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [删除菜单]
	 * @param id
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:57:44
	 */
	public int deleteMenuById(long id) {
		//result 0删除失败，1删除成功，2菜单被引用不能删除
		int result = 0;
		//此处需要增加校验，未被引用的菜单才可以删除
		int count = menuManageDao.checkRef(id) + privilegeDAO.checkMenuDelRef(id);
		if (count > 0) {
			result = 2;
		} else {
			result = menuManageDao.deleteMenuById(id);
		}
		return result;
	}
	
    /**
     * Created on   2016年5月16日
     * Discription: [获取角色对应的菜单列表]
     * @author:     chujiu
     * @update:     2016年5月16日 下午4:50:35
     * @return      Map<String,ArrayList<MenuTreeNode>>
     */
    @Cacheable(value = "menuCache")
    public Map<String, ArrayList<MenuTreeNode>> getRoleMenu() {
        Map<String, ArrayList<MenuTreeNode>> menus = new HashMap<String, ArrayList<MenuTreeNode>>();
        //获取所有非叶子节点
        List<MenuTreeNode> parents = menuManageDao.selectParentNodes();
        ArrayList<MenuTreeNode> parentsArr = new ArrayList<MenuTreeNode>();
        parentsArr.addAll(parents);
        menus.put(PARENT_KEY, parentsArr);
        //获取所有叶子节点
        List<MenuTreeNode> nodesWithRole = menuManageDao.selectAllLeafNodesWithRole();
        for (MenuTreeNode node : nodesWithRole) {
            if (!menus.containsKey(node.getRoleName())) {
                menus.put(node.getRoleName(), new ArrayList<MenuTreeNode>());
            }
            menus.get(node.getRoleName()).add(node);
        }
        return menus;
    }
}
