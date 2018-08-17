package com.chujiu.manager.quartz.operate;

import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.manager.quartz.job.MainJob;
import com.chujiu.manager.quartz.listener.MySchedulerListener;
import com.chujiu.manager.quartz.listener.MyTriggerListener;
import com.chujiu.manager.quartz.listener.MyTriggerQueueListener;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.impl.matchers.EverythingMatcher.allTriggers;

/**
 * Created by tianci on 2016/10/14.
 */
public class JobHelper {

    private static final Logger logger = LoggerFactory.getLogger(JobHelper.class);

    private Scheduler scheduler;

    private MySchedulerListener mySchedulerListener;

    private MyTriggerListener myTriggerListener;

    private MyTriggerQueueListener myTriggerQueueListener;

    private MainJob mainJob;

    /**
     * 添加无序任务到任务队列
     *
     * @param job
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public ScheduleJobDto addJob(ScheduleJobDto job) throws SchedulerException {
        if (job == null || !ScheduleJobDto.STATUS_RUNNING.equals(job.getJobStatus())) {
            return null;
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        //不存在，创建一个
        if (null == trigger) {
            try {
                Class clazz = Class.forName(job.getBeanClass());

                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
                if (jobDetail != null) {
                    jobDetail.getJobDataMap().put("scheduleJobDto", job);
                }

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                trigger = TriggerBuilder.newTrigger().usingJobData("orderNo", job.getOrderNo())
                        .withDescription(job.getDescription())
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                //添加监听
                //MySchedulerListener schedulerListener = new MySchedulerListener();
                scheduler.getListenerManager().addSchedulerListener(mySchedulerListener);

                //添加TriggerListener监听器
                //MyTriggerListener myTriggerListener=new MyTriggerListener();
                //  监听器所有的job
                scheduler.getListenerManager().addTriggerListener(myTriggerListener, allTriggers());
                //  监听部分的job
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, keyEquals(new TriggerKey("trigger1_1","tGroup1")));
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, keyEquals(triggerKey));
                //监听特定组的job
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, triggerGroupEquals(job.getJobGroup()));

                scheduler.scheduleJob(jobDetail, trigger);
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }

        } else {
            //Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }

        //得到任务下一次的计划执行时间
        Date nextProcessTime = trigger.getNextFireTime();
        job.setNextProcessTime(nextProcessTime);
        return job;
    }

    /**
     * 添加有序任务到任务队列（顺序执行）
     *
     * @param job
     * @param jobList
     * @param groupJob
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public ScheduleJobDto addJob(ScheduleJobDto job, Integer orderNO, String jobList, ScheduleJobDto groupJob) throws SchedulerException {
        if (job == null || !ScheduleJobDto.STATUS_RUNNING.equals(job.getJobStatus())) {
            return null;
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            try {
                Class clazz = Class.forName(job.getBeanClass());

                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
                if (jobDetail != null) {
                    jobDetail.getJobDataMap().put("scheduleJobDto", job);
                    jobDetail.getJobDataMap().put("scheduleGroupJob", groupJob);
                    jobDetail.getJobDataMap().put("scheduleJobOrderNo", orderNO);
                    jobDetail.getJobDataMap().put("scheduleJobOrderBy", job);
                    jobDetail.getJobDataMap().put("scheduleJobList", jobList);
                }

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                trigger = TriggerBuilder.newTrigger().usingJobData("orderNo", job.getOrderNo())
                        .withDescription(job.getDescription())
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                //添加监听
                //MySchedulerListener schedulerListener = new MySchedulerListener();
                scheduler.getListenerManager().addSchedulerListener(mySchedulerListener);

                //添加TriggerListener监听器
                //MyTriggerListener myTriggerListener=new MyTriggerListener();
                //按顺序执行的任务队列监听器，监听器所有的job
                scheduler.getListenerManager().addTriggerListener(myTriggerQueueListener, allTriggers());
                //监听部分的job
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, keyEquals(new TriggerKey("trigger1_1","tGroup1")));
                //监听特定组的job
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, triggerGroupEquals(job.getJobGroup()));

                scheduler.scheduleJob(jobDetail, trigger);
                //立即执行
                JobUtil.runAJobNow(scheduler, job);
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }

        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);

            //立即执行
            JobUtil.runAJobNow(scheduler, job);
        }

        // 得到任务下一次的计划执行时间
        Date nextProcessTime = trigger.getNextFireTime();
        job.setNextProcessTime(nextProcessTime);
        return job;
    }

    /**
     * 添加按顺序执行的主调度任务
     *
     * @param job
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public ScheduleJobDto addMainJob(ScheduleJobDto job, String jobListStr) throws SchedulerException{
        if (job == null || !ScheduleJobDto.STATUS_RUNNING.equals(job.getJobStatus())) {
            return null;
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            try{
                JobDetail jobDetail = JobBuilder.newJob(mainJob.getClass()).withIdentity(job.getJobName(), job.getJobGroup()).build();

                jobDetail.getJobDataMap().put("scheduleJobDto", job);
                jobDetail.getJobDataMap().put("scheduleJobList", jobListStr);
                jobDetail.getJobDataMap().put("scheduleJobOrderNo", 0);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                trigger = TriggerBuilder.newTrigger().withDescription(job.getDescription()).withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                //添加监听
                scheduler.getListenerManager().addSchedulerListener(mySchedulerListener);
                //  监听器所有的job
                scheduler.getListenerManager().addTriggerListener(myTriggerListener, allTriggers());
                //  监听部分的job
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, keyEquals(new TriggerKey("trigger1_1","tGroup1")));
                //  监听特定组的job
                //scheduler.getListenerManager().addTriggerListener(myTriggerListener, triggerGroupEquals(job.getJobGroup()));

                scheduler.scheduleJob(jobDetail, trigger);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }

        // 得到任务下一次的计划执行时间
        Date nextProcessTime = trigger.getNextFireTime();
        job.setNextProcessTime(nextProcessTime);
        return job;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public MySchedulerListener getMySchedulerListener() {
        return mySchedulerListener;
    }

    public void setMySchedulerListener(MySchedulerListener mySchedulerListener) {
        this.mySchedulerListener = mySchedulerListener;
    }

    public MyTriggerListener getMyTriggerListener() {
        return myTriggerListener;
    }

    public void setMyTriggerListener(MyTriggerListener myTriggerListener) {
        this.myTriggerListener = myTriggerListener;
    }

    public MyTriggerQueueListener getMyTriggerQueueListener() {
        return myTriggerQueueListener;
    }

    public void setMyTriggerQueueListener(MyTriggerQueueListener myTriggerQueueListener) {
        this.myTriggerQueueListener = myTriggerQueueListener;
    }

    public MainJob getMainJob() {
        return mainJob;
    }

    public void setMainJob(MainJob mainJob) {
        this.mainJob = mainJob;
    }
}
