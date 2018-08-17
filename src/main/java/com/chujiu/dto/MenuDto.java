package com.chujiu.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on   2016年5月16日
 * Description: [菜单表表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class MenuDto implements Serializable {

	private static final long serialVersionUID = -3559883047940181969L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 顺序号
	 */
	private String orderNum;

	/**
	 * 父节点ID
	 */
	private String parentId;

	/**
	 * 菜单路径
	 */
	private String menuUrl;

	/**
	 * 图片地址
	 */
	private String image;

	/**
	 * 菜单类型
	 */
	private String menuType;

	/**
	 * 是否叶子节点
	 */
	private String isleaf;

	private Set<MenuDto> resources = new HashSet<MenuDto>();

	public Set<MenuDto> getResources() {
		return resources;
	}

	public void setResources(Set<MenuDto> resources) {
		this.resources = resources;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	@Override
	public String toString() {
		return "MenuEntity [id=" + id + ", menuName=" + menuName + ", orderNum=" + orderNum
				+ ", parentId=" + parentId + ", menuUrl=" + menuUrl + ", image=" + image
				+ ", menuType=" + menuType + ", isleaf=" + isleaf + "]";
	}
}