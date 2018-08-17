package com.chujiu.manager.externalInterface;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chujiu.dto.MenuDto;
import com.chujiu.dto.RoleDto;
import com.chujiu.dto.UserDto;
import com.chujiu.dto.UserInfoDto;
import com.chujiu.manager.menumanage.managerservice.MenuManageService;
import com.chujiu.manager.role.managerservice.RoleService;
import com.chujiu.manager.system.managerdao.SecurityDAO;
import com.chujiu.manager.sysuser.managerservice.UserService;

/**
 * Created by Administrator on 2016/10/13.
 */
@Component
public class ExternalInterface {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SecurityDAO securityDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuManageService menuManageService;
	
	@Value("${subId}")
	private String subId;
	
    public String getAllResourceAndRoles(){
    	System.out.println("=============================================="+subId);
        //全部角色
        List<RoleDto> allRoles =  roleService.getRoleList();      
        
        //资源角色
        List<Map<String,Object>> allResourceAndRoles = new ArrayList<Map<String,Object>>();
        LinkedList<Map<String, String>> securityList = securityDAO.selectAllResourceAndRoles();
        List<String> tempUrl = new ArrayList<String>();
        List<String> roles = null;
        for (Map<String, String> map : securityList) {
        	String menuUrl = map.get("MENU_URL");
        	String rname = map.get("RNAME");
        	if(tempUrl.contains(menuUrl)){
        		for(int i=0; i<allResourceAndRoles.size(); i++){
        			Map<String,Object> a = allResourceAndRoles.get(i);
        			if(menuUrl.equals(a.get("menuUrl"))){
        				roles = (List<String>) a.get("roleNameList");
        				roles.add(rname);
        				a.put("roleNameList", roles);
        				allResourceAndRoles.remove(i);
        				allResourceAndRoles.add(a);
        			}
        		}
        	}else{
        		roles = new ArrayList<String>();
        		roles.add(rname);
        		tempUrl.add(menuUrl);
        		Map<String,Object> a = new HashMap<String, Object>();
        		a.put("menuUrl", menuUrl);
        		a.put("roleNameList", roles);
        		allResourceAndRoles.add(a);
        	} 
		} 
        //全部角色+资源角色
        JSONObject jsonObj =new JSONObject();
        jsonObj.put("allRoles",allRoles);
        jsonObj.put("allResourceAndRoles",allResourceAndRoles);
        return jsonObj.toJSONString();
    }

    public String getUsersAndRoles(){
        //全部角色
        List<RoleDto> allRoles =  roleService.getRoleList();     
        //用户信息
        UserDto user = userService.getSysUserByName("linzhiling");
        UserInfoDto userInfo = new UserInfoDto();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setUserName(user.getUsername());
        
        //菜单
        List<MenuDto> menuList = menuManageService.queryMenu();
        //子系统
        List<Object> subSystemList = new ArrayList<Object>();
        JSONObject subS = new JSONObject();
        subS.put("subId", "subId1");
        subS.put("subName", "subName1");
        subS.put("subUrl", "http://192.168.4.150:8080/parentSystem");
        subSystemList.add(subS);
        subS = new JSONObject();
        subS.put("subId", "subId2");
        subS.put("subName", "subName2");
        subS.put("subUrl", "http://192.168.4.150:8080/parentSystem2");
        subSystemList.add(subS);      
        //用户角色
        JSONObject jsonObj =new JSONObject();
        jsonObj.put("userInfo",userInfo);
        jsonObj.put("rolesOfUser",allRoles);
        jsonObj.put("menuList",menuList);
        jsonObj.put("subSystemList",subSystemList);
        return jsonObj.toJSONString();
        
    }

    public String getMenuList(){
        List<Object> menuInfo = new ArrayList<Object>();
        JSONObject menu = new JSONObject();
        menu.put("menuCd","1002");
        menu.put("menuName","系统管理");
        menu.put("orderNum","1");
        menu.put("parentId","1001");
        menu.put("menuUrl","/system/codeList/index.html");
        menu.put("image","fa-file-text-o");
        menu.put("menuType","1");
        menu.put("isleaf","0");
        menuInfo.add(menu);
        menu = new JSONObject();
        menu.put("menuCd","1003");
        menu.put("menuName","系统管理1");
        menu.put("orderNum","2");
        menu.put("parentId","1002");
        menu.put("menuUrl","/system/codeList/index.html");
        menu.put("image","fa-file-text-o");
        menu.put("menuType","1");
        menu.put("isleaf","0");
        menuInfo.add(menu);
        JSONArray jsonArray = new JSONArray(menuInfo);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("menuInfo",jsonArray);
        return jsonObj.toJSONString();
    }
    
    @RequestMapping("externalInterface_allResourceAndRoles")
    public @ResponseBody void getAllResourceAndRoles1(){
    	String sysId="";
    	String endpoint = "http://10.28.79.28:8080/WebServiceH2/services/BusiCheckWs?wsdl";
    	try{
    		Properties prop = new Properties();
    		InputStream in = new FileInputStream("checkservice.properties");
    		prop.load(in);
    		endpoint = prop.getProperty("endpoint").trim();
    	}catch(Exception e){
    		System.out.println("[读取接口配置出错] 错误信息： "+e.getMessage());
    		e.printStackTrace();
    		return;
    	}
    	
    	Service service = new Service();
    	Call call;
    	try{
    		call = (Call) service.createCall();
    		call.setTargetEndpointAddress(endpoint);
    		//调用方法名
    		call.setSOAPActionURI("getAllResourceAndRoles");
    		call.setOperationName(new QName("http://intf.busicheck.boco.com", "invoke"));
    		//设置参数
    		call.addParameter("sysId", XMLType.XSD_STRING, ParameterMode.IN);
    		//设置返回类型
    		call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String
    		String result = (String) call.invoke(new Object[] { sysId });
    	}catch (Exception e){
    		System.out.println("出错！！！");
    	}
    	
    }
}
