package com.chujiu.manager.quartz.job;

import com.chujiu.dto.ScheduleJobDto;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class QuartzJobGeneral3 implements Job {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobGeneral3.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        logger.debug("outInf{} " , "独立任务3正在运行start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("outInf{} " , "独立任务3成功运行end");
        ScheduleJobDto scheduleJobDto = (ScheduleJobDto)context.getMergedJobDataMap().get("scheduleJobDto");
        logger.debug("outInf{} " , "任务名称 = [" + scheduleJobDto.getJobName() + "]");
    }

}
