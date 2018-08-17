package com.chujiu.manager.menumanage.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chujiu.dto.MenuDto;
import com.chujiu.dto.RoleDto;
import com.chujiu.dto.SubSystemDto;

@Component
public class MenuCache {
	private static MenuCache mc;

	private static List<MenuDto> menuList;
	private static List<RoleDto> rolesOfUser;
	private static List<SubSystemDto> subSystemList;
	private MenuCache() {
	}

	public synchronized static MenuCache getMc() {
		if (mc == null) {
			mc = new MenuCache();
		}
		return mc;
	}

	public List<MenuDto> getMenuList() {
		return menuList;
	}

	public static void setMenuList(List<MenuDto> menuList) {
		MenuCache.menuList = menuList;
	}

	public List<RoleDto> getRolesOfUser() {
		return rolesOfUser;
	}

	public static void setRolesOfUser(List<RoleDto> rolesOfUser) {
		MenuCache.rolesOfUser = rolesOfUser;
	}

	public List<SubSystemDto> getSubSystemList() {
		return subSystemList;
	}

	public static void setSubSystemList(List<SubSystemDto> subSystemList) {
		MenuCache.subSystemList = subSystemList;
	}

}
