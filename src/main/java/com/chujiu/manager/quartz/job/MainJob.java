package com.chujiu.manager.quartz.job;

import com.alibaba.fastjson.JSON;
import com.chujiu.core.util.SpringContextUtils;
import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.manager.quartz.operate.JobHelper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created on   2016年11月1日
 * Title:       初九数据科技后台管理系统_[主线任务]
 * Description: [按顺序调度任务队列的主线任务]
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     tianci
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MainJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MainJob.class);

    private JobHelper jobHelper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobHelper = (JobHelper) SpringContextUtils.getBeanById("jobHelper");
        logger.debug("outInf{}" , "按顺序执行任务正在运行");

        Integer orderNo = (Integer) context.getMergedJobDataMap().get("scheduleJobOrderNo");
        ScheduleJobDto scheduleJobDto = (ScheduleJobDto) context.getMergedJobDataMap().get("scheduleJobDto");
        List<ScheduleJobDto> jobList = JSON.parseArray((String) context.getMergedJobDataMap().get("scheduleJobList"), ScheduleJobDto.class);
        if (orderNo < jobList.size()) {
            context.getJobDetail().getJobDataMap().put("scheduleJobOrderBy", jobList.get(orderNo));
            if (scheduleJobDto != null) {
                try {
                    jobHelper.addJob(jobList.get(orderNo), orderNo, (String) context.getMergedJobDataMap().get("scheduleJobList"), scheduleJobDto);
                } catch (SchedulerException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            context.getJobDetail().getJobDataMap().put("scheduleJobOrderNo", ++orderNo);
        } else {
            context.getJobDetail().getJobDataMap().put("scheduleJobOrderNo", 0);
        }
        logger.debug("任务名称 = [" + scheduleJobDto.getJobName() + "]");
    }

    public JobHelper getJobHelper() {
        return jobHelper;
    }

    public void setJobHelper(JobHelper jobHelper) {
        this.jobHelper = jobHelper;
    }

}
