package com.chujiu.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created on   2016年5月16日
 * Description: [用户表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @see org.springframework.security.core.userdetails.UserDetails
 * @author:     suliang
 * @version:    1.0
*/
public class UserDto implements UserDetails, Serializable {

	private static final long serialVersionUID = 3931034697236735563L;

	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 用户名（登录账号）
	 */
	private String username;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 账号是否可用
	 */
	protected boolean enabled = true;
	
	/**
	 * 账号是否可用
	 */
	private String enabledStr;

	/**
	 * 非过期账号
	 */
	private boolean accountNonExpired = true;

	/**
	 * 非锁定账号
	 */
	private boolean accountNonLocked = true;

	/**
	 * 非过期登录认证
	 */
	private boolean credentialsNonExpired = true;

	/**
	 * 禁用原因
	 */
	private String enableDesc;

	/**
	 * 创建人
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	private Date createOn;
	
	/**
	 * 创建时间(字符串)
	 */
	private String createOnStr;

	/**
	 * 修改人
	 */
	private Long modifiedBy;

	/**
	 * 修改时间
	 */
	private Date modifiedOn;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 电子邮箱
	 */
	private String email;
	
	/**
	 * QQ号码
	 */
	private String qq;

	/**
	 * 用户具有的角色集合（数据库中没有与之对应的字段，用户登录后系统自动注入）
	 */
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object != null && object instanceof UserDto
				&& ((UserDto) object).getUsername() != null
				&& ((UserDto) object).getUsername().equals(this.getUsername())) {
			result = true;
		}
		return result;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEnableDesc() {
		return enableDesc;
	}

	public void setEnableDesc(String enableDesc) {
		this.enableDesc = enableDesc;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getEnabledStr() {
		return enabledStr;
	}

	public void setEnabledStr(String enabledStr) {
		this.enabledStr = enabledStr;
	}

	public String getCreateOnStr() {
		return createOnStr;
	}

	public void setCreateOnStr(String createOnStr) {
		this.createOnStr = createOnStr;
	}
}