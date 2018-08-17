package com.chujiu.manager.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chujiu.core.util.SecurityUtil;
import com.chujiu.dto.MenuDto;
import com.chujiu.dto.MenuTreeNode;
import com.chujiu.dto.RoleDto;
import com.chujiu.manager.menumanage.controller.MenuCache;
import com.chujiu.security.authorization.IllegalValidateCodeException;
	
/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[公共]
 * Description: [项目跟路径访问处理 ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Controller
@RequestMapping("/")
public class IndexController {
	public static final String PARENT_KEY = "PARENT_KEY";
	public static final String MENU_LIST = "MENU_LIST";
	
	@Autowired
	private MenuCache menuCache;
	
	@Value("${application.url1}")
	private String applicationUrl1;
	
	@Value("${application.name1}")
	private String applicationName1;
	
	@Value("${application.url2}")
	private String applicationUrl2;
	
	@Value("${application.name2}")
	private String applicationName2;
	
	@Value("${application.url3}")
	private String applicationUrl3;
	
	@Value("${application.name3}")
	private String applicationName3;

	/**
	 * Created on   2016年5月16日
	 * Discription: [欢迎页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:23:26
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		// 从其他应用访问此方法的，记录应用来源，以便退出登录时，返回该应用
		String jsonp_application = request.getParameter("jsonp_application");
		if(!StringUtils.isEmpty(jsonp_application)){
			request.getSession().setAttribute("jsonp_application", jsonp_application);
		}
		return "system/welcome";
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [403 无权访问 错误信息页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:23:39
	 */
	@RequestMapping("accessDenied")
	public String accessDenied(){
		return "system/accessDenied";
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [session超时页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:23:48
	 */
	@RequestMapping("sessionTimeout")
	public String sessionTimeout(HttpServletRequest request,HttpServletResponse response){
		boolean ajaxflag = request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
		// ajax请求时，session超时处理
		if (ajaxflag) {
			String jsonObject = "{\"sessionstatus\":\"timeout\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(jsonObject);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null != out){
					out.flush();
					out.close();
				}
			}
		}
		return "system/sessionTimeout";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [失效页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:24:12
	 */
	@RequestMapping("expired")
	public String expired(HttpServletRequest request,HttpServletResponse response){
		boolean ajaxflag = request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
		// 同一账号，在非同一浏览器或非同一电脑登录互踢
		if (ajaxflag) {
			String jsonObject = "{\"expiredtatus\":\"expired\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(jsonObject);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null != out){
					out.flush();
					out.close();
				}
			}
		}
		return "system/expired";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [404错误处理]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午5:16:49
	 */
	@RequestMapping("pageNotFound")
	public String pageNotFound() {
		return "system/pageNotFound";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [模板菜单初始化]
	 * @param modelMap
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:24:35
	 */
	@RequestMapping("template")
	public String template(ModelMap modelMap) {	
		List<RoleDto> roleList = menuCache.getRolesOfUser();
		MenuTreeNode menuNodes = mergeRoleMenu();
		modelMap.put("menuNodes", menuNodes);
		modelMap.put("username", SecurityUtil.getCurrentUserName());
		// 拼当前登录用户的所有角色名称，逗号分隔
		StringBuilder sbr = new StringBuilder();
		String tempStr = "";
		for(int i = 0; i < roleList.size() ; i++){
			RoleDto roleEntity = roleList.get(i);
			if( roleEntity != null && roleEntity.getRoleDesc() != null && !"".equals(roleEntity.getRoleDesc())){
				tempStr = roleEntity.getRoleDesc();
			}
			if( i == roleList.size() - 1){
				sbr.append(tempStr);
			}else{
				sbr.append(tempStr + ",");
			}
		}
		modelMap.put("roleNames", sbr);
		modelMap.put("subSystemList", menuCache.getSubSystemList());
		return "common/template";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [mergeRoleMenu]
	 * @param roles
	 * @return MenuTreeNode
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午6:38:49
	 */
	private MenuTreeNode mergeRoleMenu() {
		Map<String, ArrayList<MenuTreeNode>> roleMenu = this.getRoleMenu();
		ArrayList<MenuTreeNode> parents = roleMenu.get(PARENT_KEY);
		//获取角色对应的所有菜单列表
		List<MenuTreeNode> list = roleMenu.get(MENU_LIST);
		
		//构造非叶子节点的索引
		Map<Integer, MenuTreeNode> parentsMap = new HashMap<Integer, MenuTreeNode>(parents.size());
		try {
			for (MenuTreeNode parent : parents) {
				parentsMap.put(parent.getId(), (MenuTreeNode) parent.clone());
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//增加根节点
		MenuTreeNode result = parentsMap.get(1);
		//构造菜单树
		for (MenuTreeNode node : list) {
			node.setNodes(new ArrayList<MenuTreeNode>());
			if (result.getNodes() == null) {
				result.setNodes(new ArrayList<MenuTreeNode>());
			}
			MenuTreeNode mtn = buildElement(parentsMap, node);
			if (!result.getNodes().contains(mtn)) {
				result.getNodes().add(mtn);
			}
		}
		sortMenu(result);
		return result;
	}
	
	private  Map<String, ArrayList<MenuTreeNode>> getRoleMenu(){
		Map<String, ArrayList<MenuTreeNode>> menus = new HashMap<String, ArrayList<MenuTreeNode>>();
        //获取所有非叶子节点
		List<MenuTreeNode> parents = new ArrayList<MenuTreeNode>();
		List<MenuDto> menuList = menuCache.getMenuList();
		MenuTreeNode menuTreeNode = null;
		//获取所有叶子节点
        List<MenuTreeNode> nodesWithRole = new ArrayList<MenuTreeNode>();
		for(MenuDto m : menuList){
			if(m!=null){
				menuTreeNode = new MenuTreeNode();
				BeanUtils.copyProperties(m, menuTreeNode);
				//TO modify
				//menuTreeNode.setId(Integer.parseInt(m.getMenuCd()));
				menuTreeNode.setId(Integer.parseInt(m.getId()));
				menuTreeNode.setParentId(Integer.parseInt(m.getParentId()));
				menuTreeNode.setOrderNum(Integer.parseInt(m.getOrderNum()));
				if("0".equals(m.getIsleaf())){
					parents.add(menuTreeNode);
				}else if("1".equals(m.getIsleaf())){
					nodesWithRole.add(menuTreeNode);
				}
			}
		}
        ArrayList<MenuTreeNode> parentsArr = new ArrayList<MenuTreeNode>();
        parentsArr.addAll(parents);
        ArrayList<MenuTreeNode> leafsArr = new ArrayList<MenuTreeNode>();
        leafsArr.addAll(nodesWithRole);
        menus.put(PARENT_KEY, parentsArr);
        menus.put(MENU_LIST, leafsArr);
        return menus;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [buildElement]
	 * @param parentsMap
	 * @param child
	 * @return MenuTreeNode
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午6:39:03
	 */
	private MenuTreeNode buildElement(Map<Integer, MenuTreeNode> parentsMap, MenuTreeNode child) {
		MenuTreeNode parent = parentsMap.get(child.getParentId());
		if (parent.getNodes() == null) {
			parent.setNodes(new ArrayList<MenuTreeNode>());
		}
		if (!parent.getNodes().contains(child)) {
			parent.getNodes().add(child);
		}
		if (parent.getParentId() != 1 && parent.getId() != 1) {
			return buildElement(parentsMap, parent);
		}
		return parent;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [sortMenu]
	 * @param root void
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午6:39:53
	 */
	private void sortMenu(MenuTreeNode root) {
		if (root.getNodes() != null) {
			Collections.sort(root.getNodes());
			for (MenuTreeNode node : root.getNodes()) {
				sortMenu(node);
			}
		}
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [登录]
	 * @param request
	 * @param modelMap
	 * @author:     chujiu
	 * @update:     2016年5月16日 下午6:39:53
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request,ModelMap modelMap) {
		// 如果是第三方跨域登录本应用时，注销登出后，跳转到第三方应用
		String jsonp_application = (String)request.getSession().getAttribute("jsonp_application");
		if(!StringUtils.isEmpty(jsonp_application)){
			return "redirect:" + jsonp_application;
		}
		String msg = "";
		AuthenticationException ep = (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if(null != ep){
			if(ep instanceof UsernameNotFoundException){
				msg = ep.getMessage();//用户不存在
			}else if(ep instanceof BadCredentialsException ){
				msg="密码错误";
			}else if(ep instanceof IllegalValidateCodeException){
				msg="验证码错误";
			}else if(ep instanceof LockedException){//指定用户是否被锁定或者解锁,锁定的用户无法通过身份验证
				msg="账户已锁定";
			}else if(ep instanceof DisabledException){//是否被禁用,禁用的用户不能通过验证
				msg="系统已停止此账号的服务，无法访问并使用本系统。";
			}else if(ep instanceof AccountExpiredException){//账户是否过期,过期无法通过验证
				msg="账户已过期";
			}else if(ep instanceof CredentialsExpiredException){//指示是否已过期的用户的凭据(密码),过期的凭据无法通过认证
				msg="认证信息已过期";//如：规定密码必须在一个月后修改，但是没有修改
			}
			modelMap.put("errorInfo", msg);
		}
		return "login";
	}
	
	/**
	 * 应用公共页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("appcommon")
	public String appcommon(ModelMap modelMap) {
		modelMap.put("applicationUrl1", applicationUrl1);
		modelMap.put("applicationName1", applicationName1);
		modelMap.put("applicationUrl2", applicationUrl2);
		modelMap.put("applicationName2", applicationName2);
		modelMap.put("applicationUrl3", applicationUrl3);
		modelMap.put("applicationName3", applicationName3);
		return "appcommon";
	}
	
	/**
	 * 默认异常处理
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("error")
	public String error(ModelMap modelMap,HttpServletRequest request){
		HttpSession session = request.getSession();
		modelMap.put("errorMsg", session.getAttribute("EXCEPTION_ERRORMSG"));
		modelMap.put("errorDetail", session.getAttribute("EXCEPTION_ERRORDETAIL"));
		session.removeAttribute("EXCEPTION_ERRORMSG");
		session.removeAttribute("EXCEPTION_ERRORDETAIL");
		return "system/error";
	}

}