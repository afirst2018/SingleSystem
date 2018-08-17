package com.chujiu.manager.quartz.job;

import com.chujiu.dto.ScheduleJobDto;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class QuartzJobFactoryImpl implements Job {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJobFactoryImpl.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        logger.debug("outInf{} " , "任务正在运行");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.debug("outInf{} " , "延时任务成功运行");
        ScheduleJobDto scheduleJobDto = (ScheduleJobDto)context.getMergedJobDataMap().get("scheduleJobDto");
        logger.debug("outInf{} " , "任务名称 = [" + scheduleJobDto.getJobName() + "]");
    }

}
