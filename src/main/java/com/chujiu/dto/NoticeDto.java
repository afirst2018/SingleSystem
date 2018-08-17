package com.chujiu.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on   2015年8月10日
 * Title:       初九科技（上海）有限公司_[公告管理]
 * Description: [公告表实体类]
 * Copyright:   Copyright (c) 2015
 * Company:     初九科技（上海）有限公司
 * Department:  研发部
 * @author:     chujiu
 * @version:    1.0
*/
public class NoticeDto implements Serializable {
    /**
     * Discription:[字段功能描述]
     */
    private static final long serialVersionUID = -1348493448773809265L;

    private String id;

    private String noticeText;

    private String orderNum;

    private String needShow;

    private Date buildDate;

    private Date modifyDate;

    private String builder;

    private String modifier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getNeedShow() {
        return needShow;
    }

    public void setNeedShow(String needShow) {
        this.needShow = needShow;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getBuilder() {
        return builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

}
