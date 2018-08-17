package com.chujiu.manager.quartz.operate;

import com.alibaba.fastjson.JSON;
import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.manager.quartz.managerservice.ScheduleJobService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JobStarter {

    private ScheduleJobService scheduleJobService;

    private JobHelper jobHelper;

    @Value("${quartz.start}")
    private String quartzStart;

    /**
     * 服务器启动，加载job_status=1的任务到任务队列中
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        if ("on" .equals(quartzStart)) {
            // 这里获取任务信息数据
            List<ScheduleJobDto> jobList = scheduleJobService.getScheduleJobAll();
            //按顺序执行的组list
            List<List<ScheduleJobDto>> queueLists = new ArrayList<>();
            //按顺序执行队列list
            List<ScheduleJobDto> queueList = new LinkedList<>();
            //独立执行list
            List<ScheduleJobDto> generalJobList = new ArrayList<>();
            //拆分list为按顺序执行的任务list，独立执行的任务list
            int i = 0;
            for (ScheduleJobDto sch : jobList) {
                if ("1" .equals(sch.getIsOrderBy())) {
                    if (queueList.isEmpty()) {
                        queueList.add(sch);
                    } else if (sch.getJobGroup().equals(queueList.get(queueList.size() - 1).getJobGroup())) {
                        queueList.add(sch);
                    }
                    if (jobList.size()==1||
                            (i < (jobList.size() - 1) && !queueList.get(queueList.size() - 1).getJobGroup().equals(jobList.get(i + 1).getJobGroup()))) {
                        queueLists.add(queueList);
                        queueList = new LinkedList<>();
                    }
                } else {
                    generalJobList.add(sch);
                }
                i++;
            }

            //添加按顺序执行的任务到线程队列
            for (List<ScheduleJobDto> list : queueLists) {
                String jobListStr = JSON.toJSONString(list);
                ScheduleJobDto job = new ScheduleJobDto();
                job.setId(10000l);//获取第一个作为group_job执行
                job.setJobName(list.get(0).getJobName() + "_Group_Job");
                job.setJobGroup(list.get(0).getJobGroup());
                job.setGroupJob(true);//group_job标识
                job.setJobStatus("1");
                job.setCronExpression(list.get(0).getCronExpression());
                job.setDescription("组任务执行");

                jobHelper.addMainJob(job, jobListStr);
            }

            //添加独立执行的任务到线程队列
            for (ScheduleJobDto job : generalJobList) {
                jobHelper.addJob(job);
            }
        }

    }

    public ScheduleJobService getScheduleJobService() {
        return scheduleJobService;
    }

    public void setScheduleJobService(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    public JobHelper getJobHelper() {
        return jobHelper;
    }

    public void setJobHelper(JobHelper jobHelper) {
        this.jobHelper = jobHelper;
    }
}
