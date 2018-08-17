package com.chujiu.dto;

import java.util.Date;

public class UserInfoDto {
	/**
	 * 主键id
	 */
	private String id;
	
	private String menuCd;

	/**
	 * 用户名（登录账号）
	 */
	private String userName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户类型（1：协会管理人员，2：会员）
	 */
	private String userType;

	/**
	 * 账号是否可用
	 */
	protected boolean enabled = true;

	/**
	 * 非过期账号
	 */
	private boolean accountNonExpired = true;

	/**
	 * 非锁定账号
	 */
	private boolean accountNonLocked = true;

	/**
	 * 非过期认证
	 */
	private boolean credentialsNonExpired = true;

	/**
	 * 禁用原因
	 */
	private String enableDesc;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private Date createOn;

	/**
	 * 修改人
	 */
	private String modifiedBy;

	/**
	 * 修改时间
	 */
	private Date modifiedOn;

	 /**
     * 性别
     */
    private String sexType;

    /**
     * 职务
     */
    private String position;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 电邮
     */
    private String email;

    /**
     * 腾讯QQ号
     */
    private String qq;

    /**
     * 腾讯微信号
     */
    private String wechat;

    /**
     * 出生日期
     */
    private Date birth;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}


	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}


	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}


	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}


	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}


	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}


	public String getEnableDesc() {
		return enableDesc;
	}


	public void setEnableDesc(String enableDesc) {
		this.enableDesc = enableDesc;
	}

	public Date getCreateOn() {
		return createOn;
	}


	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public String getSexType() {
		return sexType;
	}


	public void setSexType(String sexType) {
		this.sexType = sexType;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getWechat() {
		return wechat;
	}


	public void setWechat(String wechat) {
		this.wechat = wechat;
	}


	public Date getBirth() {
		return birth;
	}


	public void setBirth(Date birth) {
		this.birth = birth;
	}


	public String getMenuCd() {
		return menuCd;
	}


	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}

}
