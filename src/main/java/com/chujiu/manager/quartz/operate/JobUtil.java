package com.chujiu.manager.quartz.operate;

import com.chujiu.dto.ScheduleJobDto;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by tianci
 */
public class JobUtil {
    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public static List<ScheduleJobDto> getAllJob(Scheduler scheduler) throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJobDto> jobList = new ArrayList<ScheduleJobDto>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJobDto job = new ScheduleJobDto();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public static List<ScheduleJobDto> getRunningJob(Scheduler scheduler) throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJobDto> jobList = new ArrayList<ScheduleJobDto>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJobDto job = new ScheduleJobDto();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJobDto
     * @throws SchedulerException
     */
    public static void pauseJob(Scheduler scheduler, ScheduleJobDto scheduleJobDto) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJobDto.getJobName(), scheduleJobDto.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJobDto
     * @throws SchedulerException
     */
    public static void resumeJob(Scheduler scheduler, ScheduleJobDto scheduleJobDto) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJobDto.getJobName(), scheduleJobDto.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJobDto
     * @throws SchedulerException
     */
    public static void deleteJob(Scheduler scheduler, ScheduleJobDto scheduleJobDto) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJobDto.getJobName(), scheduleJobDto.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job
     *
     * @param scheduleJobDto
     * @throws SchedulerException
     */
    public static void runAJobNow(Scheduler scheduler, ScheduleJobDto scheduleJobDto) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJobDto.getJobName(), scheduleJobDto.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJobDto
     * @throws SchedulerException
     */
    public static ScheduleJobDto updateJobCron(Scheduler scheduler, ScheduleJobDto scheduleJobDto) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJobDto.getJobName(), scheduleJobDto.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobDto.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
        // 得到任务下一次的计划执行时间
        Date nextProcessTime = trigger.getNextFireTime();
        scheduleJobDto.setNextProcessTime(nextProcessTime);

        return scheduleJobDto;
    }
}
