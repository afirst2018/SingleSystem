package com.chujiu.manager.quartz.listener;

import com.chujiu.core.util.MailUtils;
import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.dto.ScheduleJobRecordDto;
import com.chujiu.manager.quartz.managerservice.ScheduleJobRecordService;
import com.chujiu.manager.quartz.operate.JobHelper;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTriggerListener extends TriggerListernerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(MyTriggerListener.class);

    private ScheduleJobRecordService scheduleJobRecordService;

    private JobHelper jobHelper;

    private MailUtils mailUtils;

    @Override
    public String getName() {
        return "MyTriggerListener";
    }

    /**
     * (1)
     * Trigger被激发 它关联的job即将被运行
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed.
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        ScheduleJobDto scheduleJobDto = (ScheduleJobDto)context.getMergedJobDataMap().get("scheduleJobDto");
        /**任务开始执行 插入执行记录*/
        ScheduleJobRecordDto jobRecord = new ScheduleJobRecordDto();
        jobRecord.setJobName(scheduleJobDto.getJobName());
        jobRecord.setJobGroup(scheduleJobDto.getJobGroup());
        jobRecord.setGroupJOb(scheduleJobDto.isGroupJob());
        jobRecord.setBeanClass(scheduleJobDto.getBeanClass());
        jobRecord.setDescription(scheduleJobDto.getDescription());

        scheduleJobRecordService.addRecord(jobRecord);
        trigger.getJobDataMap().put("recordId", jobRecord.getId());
        if (scheduleJobDto != null) {
            logger.debug("outInf{} " , "Trigger监听器：MyTriggerListener.triggerFired() job is : "+ scheduleJobDto.getJobName());
            mailUtils.send("初九定时任务执行【"+scheduleJobDto.getJobGroup()+"."+scheduleJobDto.getJobName()+"】", ""+scheduleJobDto.getJobGroup()+"："+scheduleJobDto.getJobName()+"("+trigger.getDescription()==null?"":trigger.getDescription()+")任务开始执行", "", "");
        }
    }


    /**
     * (4) 任务完成时触发
     * Called by the Scheduler when a Trigger has fired, it's associated JobDetail has been executed
     * and it's triggered(xx) method has been called.
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                CompletedExecutionInstruction triggerInstructionCode) {
        /**任务组/当前任务*/
        ScheduleJobDto scheduleJobDto = (ScheduleJobDto)context.getMergedJobDataMap().get("scheduleJobDto");

        /**任务执行完毕更新endTime*/
        scheduleJobRecordService.updateRecord(trigger.getJobDataMap().get("recordId"));

        mailUtils.send("初九定时任务执行【"+scheduleJobDto.getJobGroup()+"."+scheduleJobDto.getJobName()+"】", ""+scheduleJobDto.getJobGroup()+"("+trigger.getDescription()==null?"":trigger.getDescription()+")任务执行完毕", "", "");

    }

    public ScheduleJobRecordService getScheduleJobRecordService() {
        return scheduleJobRecordService;
    }

    public void setScheduleJobRecordService(ScheduleJobRecordService scheduleJobRecordService) {
        this.scheduleJobRecordService = scheduleJobRecordService;
    }

    public JobHelper getJobHelper() {
        return jobHelper;
    }

    public void setJobHelper(JobHelper jobHelper) {
        this.jobHelper = jobHelper;
    }

    public MailUtils getMailUtils() {
        return mailUtils;
    }

    public void setMailUtils(MailUtils mailUtils) {
        this.mailUtils = mailUtils;
    }
}
