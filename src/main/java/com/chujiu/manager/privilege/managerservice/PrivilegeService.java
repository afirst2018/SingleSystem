package com.chujiu.manager.privilege.managerservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chujiu.core.exception.ApplicationException;
import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.PrivilegeDto;
import com.chujiu.dto.PrivilegeMenuDto;
import com.chujiu.dto.RolePrivilegeDto;
import com.chujiu.manager.privilege.managerdao.PrivilegeDAO;
import com.chujiu.manager.role.managerdao.RolePrivilegeRelaDAO;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[权限管理]
 * Description: [权限相关信息管理 ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Service
public class PrivilegeService {

	@Autowired
	private PrivilegeDAO privilegeDAO;

	@Autowired
	private RolePrivilegeRelaDAO rolePrivilegeRelaDAO;

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取权限信息]
	 * @return List<PrivilegeDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:13:37
	 */
	public List<PrivilegeDto> getPrivilegeList() {
		return privilegeDAO.getPrivilegeList();
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [获取角色-权限信息]
	 * @param id
	 * @return List<RolePrivilegeDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:13:57
	 */
	public List<RolePrivilegeDto> getRoleAuth(long id) {
		return rolePrivilegeRelaDAO.getRoleAuth(id);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [向角色-权限关系表增加数据]
	 * @param id
	 * @param list void
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午3:14:12
	 */
	public void saveRoleAuth(long id, List<Map<String, Long>> list) {
		rolePrivilegeRelaDAO.deleteRoleAuth(id);
		if (list.size() > 0) {
			rolePrivilegeRelaDAO.saveRoleAuth(list);
		}
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询权限列表]
	 * @param page
	 * @param privilegeEntity
	 * @return List<PrivilegeDto>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:14:37
	 */
	public List<PrivilegeDto> queryPrivilegeListByPage(PageParameter page,
			PrivilegeDto privilegeEntity) {
		return privilegeDAO.selectPrivilegePage(page, privilegeEntity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验待插入表中的数据是否已存在]
	 * @param privilegeEntity
	 * @return PrivilegeDto
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:14:57
	 */
	public PrivilegeDto queryPrivilegeListForCheck(PrivilegeDto privilegeEntity) {
		return privilegeDAO.selectPrivilegeForCheck(privilegeEntity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [向权限表里增加一条记录]
	 * @param privilegeEntity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:15:16
	 */
	public int addPrivilegeList(PrivilegeDto privilegeEntity) {
		return privilegeDAO.addPrivilegeList(privilegeEntity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改权限表数据]
	 * @param privilegeEntity
	 * @return int
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:15:42
	 */
	public int updatePrivilegeListById(PrivilegeDto privilegeEntity) {
		return privilegeDAO.updatePrivilegeListById(privilegeEntity);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id查询权限]
	 * @param id
	 * @return PrivilegeDto
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:15:55
	 */
	public PrivilegeDto queryPrivilegeListById(long id) {
		return privilegeDAO.selectPrivilegeListById(id);
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据id删除权限]
	 * @param id
	 * @return boolean
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:16:10
	 */
	public boolean deletePrivilegeListById(long id) {
		boolean flag = false;
		try {
			rolePrivilegeRelaDAO.deleteRoleAuthByAuthId(id);
			privilegeDAO.delPrivilegeMenuByPriId(id);
			privilegeDAO.deletePrivilegeListById(id);
			flag = true;
		} catch (Exception e) {
			throw new ApplicationException(null, e);
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [根据权限id查询对应的所有menu]
	 * @param id
	 * @return List<PrivilegeMenuDto>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午3:16:42
	 */
	public List<PrivilegeMenuDto> queryMenuIdByPid(long id) {
		return privilegeDAO.selectMenuIdByPid(id);
	}

	public boolean savePrivilegeMenuRela(List<PrivilegeMenuDto> addList,
			List<PrivilegeMenuDto> delList) {
		boolean flag = false;
		try {
			if (null != addList && addList.size() > 0) {
				privilegeDAO.addPrivilegeMenuRelaBatch(addList);
			}
			if (null != delList && delList.size() > 0) {
				privilegeDAO.delPrivilegeMenuRelaBatch(delList);
			}
			flag = true;
		} catch (Exception e) {
			throw new ApplicationException(null, e);
		}
		return flag;
	}
}
