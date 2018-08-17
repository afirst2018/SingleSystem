package com.chujiu.manager.menumanage.managerdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;

import com.chujiu.dto.MenuDto;
import com.chujiu.dto.MenuTreeNode;



/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[菜单管理]
 * Description: [菜单信息管理DAO]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
public interface MenuManageDAO {

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询菜单信息]
	 * @return List<MenuDto>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:46:21
	 */
	List<MenuDto> queryMenu();
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询菜单展示权限]
	 * @return List<MenuDto>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:46:21
	 */
	List<MenuDto> queryMenuForPrivilege();
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [新增菜单信息]
	 * @param entity
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:46:39
	 */
	@CacheEvict(value = "menuCache", allEntries=true)
	int insertMenu(MenuDto entity);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [修改菜单信息]
	 * @param entity
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:46:57
	 */
	@CacheEvict(value = "menuCache", allEntries=true)
	int updateMenu(MenuDto entity);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [删除菜单信息]
	 * @param id
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:47:14
	 */
	@CacheEvict(value = "menuCache", allEntries=true)
	int deleteMenuById(@Param("id") long id);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [校验]
	 * @param id
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:47:32
	 */
	int checkRef(@Param("id") long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [按id查询菜单信息]
	 * @param id
	 * @return MenuDto
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:47:32
	 */
	MenuDto queryMenuById(@Param("id") String id);
	
	// 初始化页面菜单信息使用 start
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询所有非叶子节点]
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午4:19:10
	 * @return      List<MenuTreeNode> .
	 */
	List<MenuTreeNode> selectParentNodes();

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询所有权限对应的菜单]
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午4:18:37
	 * @return      List<MenuTreeNode> .
	 */
	List<MenuTreeNode> selectAllLeafNodesWithRole();
	// 初始化页面菜单信息使用 end
}
