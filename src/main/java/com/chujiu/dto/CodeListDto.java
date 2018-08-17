package com.chujiu.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on   2016年5月16日
 * Description: [code_list表Dto]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
public class CodeListDto implements Serializable {

	private static final long serialVersionUID = 2822705282054324064L;
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 类型名称
	 */
	private String kindName;
	
	/**
	 * 类型值
	 */
	private String kindValue;
	
	/**
	 * 数据字典名称
	 */
	private String codeName;
	
	/**
	 * 数据字典值
	 */
	private String codeValue;
	
	/**
	 * 顺序号
	 */
	private String orderNum;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人
	 */
	private Long createBy;

	/**
	 * 创建时间
	 */
	private Date createOn;

	/**
	 * 修改人
	 */
	private Long modifiedBy;

	/**
	 * 修改时间
	 */
	private Date modifiedOn;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getKindValue() {
		return kindValue;
	}
	public void setKindValue(String kindValue) {
		this.kindValue = kindValue;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
}
