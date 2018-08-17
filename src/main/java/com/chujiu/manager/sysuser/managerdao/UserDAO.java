package com.chujiu.manager.sysuser.managerdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.UserDto;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[用户管理]
 * Description: [用户管理 ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
public interface UserDAO {

	/**
	 * Created on   2016年5月16日
	 * Discription: [查询用户列表]
	 * @return List<UserDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:57:08
	 */
	List<UserDto> getSysUserList();

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改密码]
	 * @param userEntity
	 * @return int
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:38
	 */
	int updatePwd(UserDto userEntity);

	/**
	 * Created on   2016年5月16日
	 * Discription: [删除用户角色关系]
	 * @param id void
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:59:20
	 */
	@CacheEvict(value = "userListCache", allEntries=true)
	void deleteUserRole(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [按id查询用户]
	 * @param id
	 * @return UserDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:58:25
	 */
	UserDto getSysUserById(long id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [按名称查询用户]
	 * @param username
	 * @return UserDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:57:51
	 */
	UserDto getSysUserByName(String username);

	// new start

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询系统管理员列表]
	 * @param page
	 * @param entity
	 * @param loginUserId
	 * @return List<UserDetailDto>
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	List<UserDto> selectSysUserListPage(@Param("page") PageParameter page,@Param("entity") UserDto entity,@Param("loginUserId") Long loginUserId);

	/**
	 * Created on   2016年5月16日
	 * Discription: [按ID查询系统管理员信息]
	 * @param id
	 * @return UserDetailDto
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	UserDto selectSysUserById(@Param("id") String id);

	/**
	 * Created on   2016年5月16日
	 * Discription: [停用账号]
	 * @param id
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	@CacheEvict(value = "userListCache", allEntries=true)
	int updateDisabledAccount(@Param("id") String id,@Param("reason") String reason,@Param("loginUserId") Long loginUserId);

	/**
	 * Created on   2016年5月16日
	 * Discription: [启用账号]
	 * @param id
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	@CacheEvict(value = "userListCache", allEntries=true)
	int updateEnabledAccount(@Param("id") String id,@Param("loginUserId") Long loginUserId);

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改协会人员信息（账号详细信息）]
	 * @param entity
	 * @param loginUserId
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	int updateUser(@Param("entity") UserDto entity,@Param("loginUserId") Long loginUserId);

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验管理员账号（username）是否存在]
	 * @param username
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	int checkSysUsername(@Param("username") String username);

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增管理员账号]
	 * @param dto
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	@CacheEvict(value = "userListCache", allEntries=true)
	int insertUser(UserDto dto);

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增管理员账号详细信息]
	 * @param dto
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	int insertUserDetail(UserDto dto);


	@CacheEvict(value = "userListCache", allEntries=true)
	void deleteUser(long id);
	
	@CacheEvict(value = "userListCache", allEntries=true)
	void deleteUserDetail(long id);
}