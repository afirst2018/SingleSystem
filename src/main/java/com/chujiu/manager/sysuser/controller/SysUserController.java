package com.chujiu.manager.sysuser.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chujiu.core.page.PageParameter;
import com.chujiu.core.util.SecurityUtil;
import com.chujiu.core.util.SqlTool;
import com.chujiu.dto.RoleDto;
import com.chujiu.dto.UserDto;
import com.chujiu.dto.UserRoleDto;
import com.chujiu.manager.role.managerservice.RoleService;
import com.chujiu.manager.sysuser.managerservice.UserService;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[用户管理]
 * Description: [后台用户管理]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
@Controller
@RequestMapping("sysuser")
public class SysUserController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * Created on   2016年5月16日
	 * Discription: [首页显示]
	 * @return String
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:50:17
	 */
	@RequestMapping("method_index")
	public String subsys(ModelMap modelMap) {
		List<RoleDto> roleList = roleService.getRoleList();
		modelMap.put("roleList", roleList);
		return "sysuser/sysuser";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验原密码是否正确]
	 * @param npwd
	 * @return Map<String,Boolean>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午2:31:58
	 */
	@ResponseBody
	@RequestMapping("method_changePwdBySelf")
	public  Map<String, Boolean> changePwdBySelf(@RequestParam(value = "npwd", required = true) String npwd) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] encodeByte = Base64.encodeBase64(md5.digest(npwd.getBytes()));
			String md5Password = (new String(encodeByte, "utf-8"));

			long id = Long.valueOf(SecurityUtil.getCurrentUserMap().get("id").toString());
			UserDto dto = userService.getSysUserById(id);
			dto.setPassword(md5Password);
			dto.setModifiedBy(id);

			int rtn = userService.updatePwd(dto);
			if (rtn > 0) {
				result.put("valid", true);
			} else {
				result.put("valid", false);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			result.put("valid", false);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.put("valid", false);
		}
		return result;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验原密码是否正确]
	 * @param opwd
	 * @return Map<String,Boolean>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午2:31:58
	 */
	@ResponseBody
	@RequestMapping("method_checkPwd")
	public  Map<String, Boolean> checkRoleName(@RequestParam(value = "opwd", required = true) String opwd) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		try {
			long id = Long.valueOf(SecurityUtil.getCurrentUserMap().get("id").toString());
			UserDto dto = userService.getSysUserById(id);

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] encodeByte = Base64.encodeBase64(md5.digest(opwd.getBytes()));
			String md5Password = (new String(encodeByte, "utf-8"));

			if (dto != null && md5Password.equals(dto.getPassword())) {
				result.put("valid", true);
			} else {
				result.put("valid", false);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [分页查询用户列表]
	 * @param page
	 * @param dto
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:20:35
	 */
	@ResponseBody
	@RequestMapping("method_querySysUserList")
	public Map<String, Object> querySysUserDetailList(PageParameter page, UserDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserDto> list = new ArrayList<UserDto>();
		// 记录查询条件,为翻页查询使用。
		map.put("accountTxtHdn", dto.getUsername());
		map.put("nameTxtHdn", dto.getRealName());
		map.put("mobileTxtHdn", dto.getMobile());
		map.put("enabledTxtHdn", dto.getEnabledStr());
		//处理特殊字符
		dto.setUsername(SqlTool.transfer(dto.getUsername()));
		dto.setRealName(SqlTool.transfer(dto.getRealName()));
		dto.setMobile(SqlTool.transfer(dto.getMobile()));
		Object idObj = SecurityUtil.getCurrentUserMap().get("id");
		if(!StringUtils.isEmpty(idObj)){
			Long userId = new Long(String.valueOf(idObj));
			list = userService.selectSysUserListPage(page,dto,userId);
		}
		map.put("page", page);
		map.put("resultList", list);
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [按id获取用户]
	 * @param id
	 * @return UserDto
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:17
	 */
	@RequestMapping(value = "method_getSysUserById")
	public @ResponseBody UserDto getSysUserById(@RequestParam(value = "id", required = true) String id) {
		UserDto dto = userService.selectSysUserById(id);
		return dto;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [停用账号]
	 * @param id
	 * @param reason
	 * @return String
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:17
	 */
	@RequestMapping(value = "method_updateDisabledAccount")
	public @ResponseBody String updateDisabledAccount(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "reason", required = true) String reason) {
		boolean flag = userService.updateDisabledAccount(id,reason);
		return flag?"1":"0";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [启用账号]
	 * @param id
	 * @return String
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:17
	 */
	@RequestMapping(value = "method_updateEnabledAccount")
	public @ResponseBody String updateEnabledAccount(@RequestParam(value = "id", required = true) String id) {
		boolean flag = userService.updateEnabledAccount(id);
		return flag?"1":"0";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [修改用户细信息]
	 * @param dto
	 * @return String
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:52:17
	 */
	@RequestMapping(value = "method_updateUser")
	public @ResponseBody String updateUser(UserDto dto) {
		boolean flag = userService.updateUser(dto);
		return flag?"1":"0";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [管理员修改密码]
	 * @param npwd
	 * @return Map<String,Boolean>
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午2:31:58
	 */
	@ResponseBody
	@RequestMapping("method_changePwdByManager")
	public  String changePwdByManager(@RequestParam String npwd,@RequestParam String userId) {
		String flag = "0";
		try {
			Object idObj = SecurityUtil.getCurrentUserMap().get("id");
			if (StringUtils.isEmpty(idObj)) {
				return "0";
			} else {
				Long loginUserId = new Long(String.valueOf(idObj));
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] encodeByte = Base64.encodeBase64(md5.digest(npwd.getBytes()));
				String md5Password = (new String(encodeByte, "utf-8"));
				UserDto dto = new UserDto();
				dto.setId(userId);
				dto.setModifiedBy(loginUserId);
				dto.setPassword(md5Password);
				int rtn = userService.updatePwd(dto);
				if (rtn > 0) {
					flag = "1";
				}
			}
		}catch (Exception e){
			flag = "0";
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [校验用户账号（username）是否存在]
	 * @param account
	 * @return Map<String,Object>
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:20:35
	 */
	@ResponseBody
	@RequestMapping("method_checkUsername")
	public Map<String, Boolean> checkUsername(@RequestParam String account) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		int cnt = userService.checkSysUsername(account);
		if(cnt > 0){
			map.put("valid", true);
		}else{
			map.put("valid", false);
		}
		return map;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [新增用户信息]
	 * @param dto
	 * @return String
	 * @author:     sul
	 * @update:     2016年5月16日 下午2:52:17
	 */
	@RequestMapping(value = "method_addUser")
	public @ResponseBody String addUserAndDetail(UserDto dto) {
		boolean flag = false;
		try {
			String opwd = dto.getPassword();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] encodeByte = Base64.encodeBase64(md5.digest(opwd.getBytes()));
			String md5Password = (new String(encodeByte, "utf-8"));
			dto.setPassword(md5Password);
			flag = userService.addUser(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag?"1":"0";
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [获取用户角色]
	 * @param id
	 * @return List<UserRoleDto>
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:35:54
	 */
	@RequestMapping("method_getuserrole")
	public @ResponseBody List<UserRoleDto> getUserRole(@RequestParam(value = "id",required=true) long id) {
		List<UserRoleDto> list = userService.getUserRole(id);
		return list;
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [保存用户角色]
	 * @param uid
	 * @param role
	 * @return boolean
	 * @author:     lin.ch
	 * @update:     2016年5月16日 下午2:36:14
	 */
	@RequestMapping("method_saveuserrole")
	public @ResponseBody boolean saveUserRole(@RequestParam(value = "uid",required=true) long uid,@RequestParam(value = "role[]",required=false) long[] role) {
		List<Map<String, Long>> parm = new ArrayList<Map<String, Long>>();
		if (role != null) {
			for (long role_id : role) {
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("userId", uid);
				map.put("roleId", role_id);
				parm.add(map);
			}
		}
		userService.saveUserRole(uid, parm);
		return true;
	}
	@RequestMapping("method_deluserrolebyid")
	public @ResponseBody Map<String, Object> method_delUserRoleById(
			@RequestParam(value = "id", required = true) long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = userService.deleteUserById(id);
		String message = "";
		if (flag) {
			message = "删除成功";
		} else {
			message = "删除失败";
		}
		resultMap.put("message", message);
		return resultMap;
	}
	
}