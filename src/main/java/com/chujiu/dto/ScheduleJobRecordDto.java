package com.chujiu.dto;

import java.util.Date;

/**
 * 计划任务记录信息
 * User: tianci
 */
public class ScheduleJobRecordDto {

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";

    /** 任务id */
    private Long id;
    /**
     * 任务创建时间
     */
    private Date startTime;
    /**
     * 任务更新时间
     */
    private Date endTime;
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
    private boolean isGroupJOb;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务执行时调用的任务类 包名+类名
     */
    private String beanClass;
    /**
     * 详细描述
     */
    private String note;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isGroupJOb() {
        return isGroupJOb;
    }

    public void setGroupJOb(boolean groupJOb) {
        isGroupJOb = groupJOb;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}