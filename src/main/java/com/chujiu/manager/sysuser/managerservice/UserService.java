package com.chujiu.manager.sysuser.managerservice;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SecurityUtil;
import com.chujiu.dto.UserDto;
import com.chujiu.dto.UserRoleDto;
import com.chujiu.manager.sysuser.managerdao.UserDAO;
import com.chujiu.manager.sysuser.managerdao.UserRoleRelaDAO;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[用户管理 ]
 * Description: [描述该类功能介绍]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     sul
 * @version:    1.0
*/
@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleRelaDAO userRoleRelaDao;

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取所有非超级管理员用户列表]
	 * @param
	 * @return Map<String,Map<String,String>>
	 * @author:     wtc
	 * @update:     2016年5月16日 下午3:02:39
	 */
	@Cacheable(value = "userListCache")
	public Map<String,Map<String,String>> getSysUserMap() {
		String[] names = {"id", "username", "realName"};
		List<UserDto> userDtoList = userDAO.getSysUserList();
		Map<String, String> map = new HashMap<>(userDtoList.size());
		for (UserDto userDto : userDtoList) {
			try {
				map.put(BeanUtils.getProperty(userDto,names[0]),
						BeanUtils.getProperty(userDto,names[2])==null?BeanUtils.getProperty(userDto,names[1]):BeanUtils.getProperty(userDto,names[2]));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

		}
		Map<String, Map<String, String>> map1 = new HashMap<>();
		map1.put("userList", map);
		return map1;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改密码]
	 * @param dto
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午3:02:39
	 */
	public int updatePwd(UserDto dto) {
		return userDAO.updatePwd(dto);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按mame查询用信息]
	 * @param username
	 * @return UserDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:01:08
	 */
	public UserDto getSysUserByName(String username) {
		return userDAO.getSysUserByName(username);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按id查询用户信息]
	 * @param id
	 * @return UserDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:01:50
	 */
	public UserDto getSysUserById(long id) {
		return userDAO.getSysUserById(id);
	}

	// new start

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询系统管理员列表]
	 * @param page
	 * @param dto
	 * @param loginUserId
	 * @return List<UserDetailDto>
	 * @author:     sul
	 * @update:     2016年5月16日 下午3:06:38
	 */
	public List<UserDto> selectSysUserListPage(PageParameter page, UserDto dto, Long loginUserId) {
		return userDAO.selectSysUserListPage(page,dto,loginUserId);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按ID查询系统管理员信息]
	 * @param id
	 * @return UserDetailDto
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	public UserDto selectSysUserById(String id) {
		return userDAO.selectSysUserById(id);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [停用账号]
	 * @param id
	 * @param reason
	 * @return boolean
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	public boolean updateDisabledAccount(String id, String reason) {
		boolean flag = false;
		Object idObj = SecurityUtil.getCurrentUserMap().get("id");
		if(StringUtils.isEmpty(idObj)){
			return false;
		}else {
			Long userId = new Long(String.valueOf(idObj));
			int cnt = userDAO.updateDisabledAccount(id,reason,userId);
			if(cnt > 0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [启用账号]
	 * @param id
	 * @return boolean
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	public boolean updateEnabledAccount(String id) {
		boolean flag = false;
		Object idObj = SecurityUtil.getCurrentUserMap().get("id");
		if(StringUtils.isEmpty(idObj)){
			return false;
		}else {
			Long userId = new Long(String.valueOf(idObj));
			int cnt = userDAO.updateEnabledAccount(id,userId);
			if(cnt > 0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改协会人员信息（账号详细信息）]
	 * @param dto
	 * @return boolean
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	public boolean updateUser(UserDto dto) {
		boolean flag = false;
		Object idObj = SecurityUtil.getCurrentUserMap().get("id");
		if(StringUtils.isEmpty(idObj)){
			return false;
		}else {
			Long userId = new Long(String.valueOf(idObj));
			int cnt = userDAO.updateUser(dto,userId);
			if(cnt > 0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验管理员账号（username）是否存在]
	 * @param username
	 * @return int
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	public int checkSysUsername(String username) {
		return userDAO.checkSysUsername(username);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增管理员账号及详细信息]
	 * @param dto
	 * @return boolean
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:57:34
	 */
	public boolean addUser(UserDto dto){
		boolean flag = false;
		Date now = new Date();
		Object idObj = SecurityUtil.getCurrentUserMap().get("id");
		if(StringUtils.isEmpty(idObj)){
			return false;
		}else {
			Long userId = new Long(String.valueOf(idObj));
			dto.setCreateBy(userId);
			dto.setCreateOn(now);
			dto.setModifiedBy(userId);
			dto.setModifiedOn(now);
			int cnt1 = userDAO.insertUser(dto);
			if(cnt1 > 0){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [按id获取角色用户关系列表]
	 * @param id
	 * @return List<UserRoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:20:24
	 */
	public List<UserRoleDto> getUserRole(long id) {
		return userRoleRelaDao.getUserRole(id);
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [保存用户角色关系信息]
	 * @param id
	 * @param list void
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:20:37
	 */
	public void saveUserRole(long id, List<Map<String, Long>> list) {
		userRoleRelaDao.deleteUserRoles(id);
		if (list.size() > 0){
			userRoleRelaDao.saveUserRole(list);
		}
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [删除用户，用户角色信息]
	 * @param id void
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:18:51
	 */
	public boolean deleteUserById(long id) {
		userDAO.deleteUserRole(id);
		userDAO.deleteUser(id);
		userDAO.deleteUserDetail(id);
		return true;
	}
}
