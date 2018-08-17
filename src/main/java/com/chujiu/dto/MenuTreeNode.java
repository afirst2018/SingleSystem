package com.chujiu.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created on   2016年5月16日
 * Description: [菜单节点类]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class MenuTreeNode implements Serializable, Cloneable, Comparable<MenuTreeNode> {
	private static final long serialVersionUID = 3832019088093126757L;

	/**
	 * id
	 */
	private int id;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 菜单路径
	 */
	private String menuUrl;

	/**
	 * 菜单图标
	 */
	private String image;

	/**
	 * 父节点id
	 */
	private int parentId;

	/**
	 * 顺序号
	 */
	private int orderNum;
	
	/**
	 * 子节点
	 */
	private List<MenuTreeNode> nodes;
	
	/**
	 * 角色名字
	 */
	private String roleName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<MenuTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<MenuTreeNode> nodes) {
		this.nodes = nodes;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof MenuTreeNode) {
			result = this.id == ((MenuTreeNode) obj).getId();
		}
		return result;
	}

	@Override
	public int compareTo(MenuTreeNode o) {
		return this.orderNum - o.orderNum;
	}
}
