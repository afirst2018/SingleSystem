package com.chujiu.manager.useronline.controller;

import com.chujiu.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianci on 2016/10/9.
 */
@Controller
@RequestMapping("UserOnlineController")
public class UserOnlineController {
    @Autowired
    private SessionRegistry sessionRegistry;//利用spring注入后就可以取所有登录用户，不能加static属性，否则会取不到

    @RequestMapping("method_index")
    public String index(){
        return "useronline/useronline";
    }

    @RequestMapping("method_getUserOnline")
    @ResponseBody
    public List<UserDto> getUserOnline(@RequestParam(value = "username",required = false) String username){//获取查询在线用户
        List<Object> objects = sessionRegistry.getAllPrincipals();//获取在线用户list
        List<UserDto> userDtoList = new ArrayList<>();
        for (Object o : objects) {
            UserDto user = (UserDto) o;//强转为User
            if(user.isCredentialsNonExpired()){//取没过期的用户
                if(username!=null&&!"".equals(username)&&user.getUsername().indexOf(username)>-1){
                    userDtoList.add(user);
                }
                if(username==null||"".equals(username)){
                    userDtoList.add(user);
                }
            }
        }
        return userDtoList;
    }

    @RequestMapping(value = "method_kickOff")
    @ResponseBody
    public String kickoutUser(@RequestParam(value = "username") String username) {//踢出在线用户
        System.out.println("kick out : " + username);
        List<Object> objects = sessionRegistry.getAllPrincipals();
        for (Object o : objects) {
            UserDto user = (UserDto) o;
            if (user.getUsername().equals(username)) {
                List<SessionInformation> sis = sessionRegistry.getAllSessions(o, false);
                if (sis != null) {
                    for (SessionInformation si : sis) {
                        si.expireNow();//踢出
                        user.setCredentialsNonExpired(false);//设定为过期
                        System.out.println(si.isExpired() ? "yes,  session be expired" : "no yet,session still active");
                        // this.sessionRegistry.removeSessionInformation(si.getSessionId());<span style="white-space:pre">  </span>
                        System.out.println("---" + username + "---have be kick out!");
                    }
                }

                return "success";
            }
        }
        System.out.println("no one call ---" + username + "---login");
        return "error";
    }
}
