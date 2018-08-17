package com.chujiu.manager.quartz.listener;

import com.chujiu.core.util.MailUtils;
import com.chujiu.manager.quartz.managerservice.ScheduleJobRecordService;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySchedulerListener extends ScheduleListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MySchedulerListener.class);

    private ScheduleJobRecordService scheduleJobRecordService;

    private MailUtils mailUtils;


    @Override
    public void jobPaused(JobKey jobKey) {
        logger.debug("outInf{} " , jobKey + "被暂停时被执行");

    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        logger.debug("outInf{} " , "出现异常" + msg + "时被执行");
        logger.debug("outInf{} " , cause.getMessage());
        mailUtils.send("初九定时任务错误邮件【"+msg+"】", "出现异常" + msg + ":"+cause.getCause().getMessage()+"", "", "");
    }

    public ScheduleJobRecordService getScheduleJobRecordService() {
        return scheduleJobRecordService;
    }

    public void setScheduleJobRecordService(ScheduleJobRecordService scheduleJobRecordService) {
        this.scheduleJobRecordService = scheduleJobRecordService;
    }

    public MailUtils getMailUtils() {
        return mailUtils;
    }

    public void setMailUtils(MailUtils mailUtils) {
        this.mailUtils = mailUtils;
    }
}
