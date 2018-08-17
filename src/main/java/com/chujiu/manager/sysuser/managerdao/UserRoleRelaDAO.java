package com.chujiu.manager.sysuser.managerdao;

import java.util.List;
import java.util.Map;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.UserRoleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[权限管理]
 * Description: [用户授权角色DAO]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public interface UserRoleRelaDAO {
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [按角色id删除]
	 * @param roleid
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:58:47
	 */
	void deleteRelaByRoleId(long roleid);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [查询数据]
	 * @return List<Map<String,Object>>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:02
	 */
	List<Map<String,Object>> getUserRoleRelaList();
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [按id查询数据]
	 * @param id
	 * @return List<UserRoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:12
	 */
	List<UserRoleDto> getUserRole(long id);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [按id删除数据]
	 * @param id
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:25
	 */
	void deleteUserRoles(long id);
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [保存数据]
	 * @param list
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:40
	 */
	@CacheEvict(value = "menuCache", allEntries=true)
	void saveUserRole(List<Map<String,Long>> list);

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询数据]
	 * @return List<Map<String,Object>>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:02
	 */
	List<Map<String,Object>> getUserRoleRelaListByRoleNamePage(@Param("page") PageParameter page, @Param("roleName") String roleName, @Param("account")String account, @Param("name") String name);
}
