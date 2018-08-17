package com.chujiu.dto;

import java.util.Date;

/**
 * 计划任务信息
 * User: tianci
 */
public class ScheduleJobDto {

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";

    /** 任务id */
    private Long id;
    /** 任务执行顺序*/
    private Long orderNo;
    /** 任务是否按顺序执行*/
    private String isOrderBy;
    /**
     * 任务创建时间
     */
    private Date createTime;
    /**
     * 任务更新时间
     */
    private Date updateTime;
    /**
     * 上一次执行时间
     */
    private Date lastProcessTime;
    /**
     * 下次计划执行时间
     */
    private Date nextProcessTime;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 是否组调度总任务
     */
    private boolean isGroupJob;
    /**
     * 任务状态 0禁用 1启用 2删除
     */
    private String jobStatus;
    /**
     * cron任务运行时间表达式
     */
    private String cronExpression;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务执行时调用的任务类 包名+类名
     */
    private String beanClass;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastProcessTime() {
        return lastProcessTime;
    }

    public void setLastProcessTime(Date lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }

    public Date getNextProcessTime() {
        return nextProcessTime;
    }

    public void setNextProcessTime(Date nextProcessTime) {
        this.nextProcessTime = nextProcessTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsOrderBy() {
        return isOrderBy;
    }

    public void setIsOrderBy(String isOrderBy) {
        this.isOrderBy = isOrderBy;
    }

    public boolean isGroupJob() {
        return isGroupJob;
    }

    public void setGroupJob(boolean groupJob) {
        isGroupJob = groupJob;
    }
}