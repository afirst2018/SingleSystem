package com.chujiu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.chujiu.core.util.SpringContextUtils;
import com.chujiu.security.access.InvocationSecurityMetadataSource;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[公共]
 * Description: [刷新缓存AOP]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     lin.ch
 * @version:    1.0
*/
@Aspect
public class RefreshPrivilegeAspect {
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [指定service中的方法，当指定的方法（即操作角色权限关系表，权限菜单关系表时，或对菜单表新增删除操作时，触发刷新权限配置的方法]
	 * @author:     chujiu
	 * @update:     2016年5月8日 下午1:34:47
	 */
	@Pointcut("execution(public * com.chujiu.manager.privilege.managerservice.PrivilegeService.savePrivilegeMenuRela*(..)) || execution(public * com.chujiu.manager.privilege.managerservice.PrivilegeService.saveRoleAuth*(..)) || execution(public * com.chujiu.manager.role.managerservice.RoleService.deleteRole*(..)) || execution(public * com.chujiu.manager.menumanage.managerservice.MenuManageService.insertMenu*(..)) || execution(public * com.chujiu.manager.menumanage.managerservice.MenuManageService.deleteMenuById*(..)) )" )
	public void aspectCall() { }
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [刷新权限缓存]
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午1:34:47
	 */
	@AfterReturning(value="aspectCall()", argNames="rtv", returning="rtv")  
	public void updateServiceCalls(JoinPoint joinPoint, Object rtv) throws Throwable{
		InvocationSecurityMetadataSource metadataSrc = (InvocationSecurityMetadataSource)SpringContextUtils.getBeanById("invocationSecurityMetadataSource");
		metadataSrc.initResources();
	}
}