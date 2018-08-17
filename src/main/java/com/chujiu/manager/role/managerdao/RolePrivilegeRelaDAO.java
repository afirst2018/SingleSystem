package com.chujiu.manager.role.managerdao;

import java.util.List;
import java.util.Map;

import com.chujiu.dto.RolePrivilegeDto;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[角色权限关系管理]
 * Description: [角色权限关系管理]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public interface RolePrivilegeRelaDAO {

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询角色权限关系表数据]
	 * @param id
	 * @return List<RolePrivilegeDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:54:44
	 */
	List<RolePrivilegeDto> getRoleAuth(long id);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [删除角色权限关系表数据]
	 * @param id
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:55:01
	 */
	void deleteRoleAuth(long id);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [新增数据]
	 * @param list
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:55:20
	 */
	void saveRoleAuth(List<Map<String,Long>> list);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [按权限id删除角色权限关系表数据]
	 * @param id
	 * @return int
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:55:38
	 */
	int deleteRoleAuthByAuthId(long id);
}
