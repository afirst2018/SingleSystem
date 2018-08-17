package com.chujiu.manager.privilege.managerdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.PrivilegeDto;
import com.chujiu.dto.PrivilegeMenuDto;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[权限管理]
 * Description: [描述该类功能介绍]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public interface PrivilegeDAO {

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询权限列表]
	 * @return List<PrivilegeDto>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午2:47:18
	 */
	List<PrivilegeDto> getPrivilegeList();

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询权限列表]
	 * @param page
	 * @param entity
	 * @return List<PrivilegeDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:47:41
	 */
	List<PrivilegeDto> selectPrivilegePage(@Param("page") PageParameter page,
			@Param("entity") PrivilegeDto entity);

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验指定权限名称的权限是否存在]
	 * @param entity
	 * @return PrivilegeDto
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:48:01
	 */
	PrivilegeDto selectPrivilegeForCheck(@Param("entity") PrivilegeDto entity);

	/**
	 * Created on   2016年5月16日
	 * Discription: [增加权限信息]
	 * @param entity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:48:36
	 */
	int addPrivilegeList(PrivilegeDto entity);

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改权限信息]
	 * @param entity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:48:52
	 */
	int updatePrivilegeListById(@Param("entity") PrivilegeDto entity);

	/**
	 * Created on   2016年5月16日
	 * Discription: [按id查询权限信息]
	 * @param id
	 * @return PrivilegeDto
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:49:08
	 */
	PrivilegeDto selectPrivilegeListById(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [通过id删除权限信息]
	 * @param id
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:49:32
	 */
	int deletePrivilegeListById(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [通过权限id在权限-菜单关系表中查询指定权限对应的所有菜单信息]
	 * @param id
	 * @return List<PrivilegeMenuDto>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:49:53
	 */
	List<PrivilegeMenuDto> selectMenuIdByPid(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [批量在权限-菜单关系表中增加数据]
	 * @param list
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:50:08
	 */
	// 做权限设定操作后，需要刷新菜单缓存
	@CacheEvict(value = "menuCache", allEntries=true)
	int addPrivilegeMenuRelaBatch(List<PrivilegeMenuDto> list);

	/**
	 * Created on   2016年5月16日
	 * Discription: [批量删除权限-菜单关系表中的数据]
	 * @param list
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:50:22
	 */
	// 做权限设定操作后，需要刷新菜单缓存
	@CacheEvict(value = "menuCache", allEntries=true)
	int delPrivilegeMenuRelaBatch(List<PrivilegeMenuDto> list);

	/**
	 * Created on   2016年5月16日
	 * Discription: [按权限id批量删除权限-菜单关系表中的数据]
	 * @param id
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:50:37
	 */
	int delPrivilegeMenuByPriId(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据菜单id查询关联表，用于校验]
	 * @param id
	 * @return int
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午2:50:52
	 */
	int checkMenuDelRef(long id);
}
