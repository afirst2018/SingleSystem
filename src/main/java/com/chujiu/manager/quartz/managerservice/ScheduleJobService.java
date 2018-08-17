package com.chujiu.manager.quartz.managerservice;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.manager.quartz.managerdao.ScheduleJobDAO;
import com.chujiu.manager.quartz.operate.JobUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created on   2015年7月8日
 * Title:       麟龙大平台_[投顾CMS]_[菜单管理]
 * Description: [菜单管理]
 * Copyright:   Copyright (c) 2015
 * Company:     初九科技（上海）有限公司
 * Department:  研发部
 * @author:     tianci
 * @version:    1.0
*/
@Service
public class ScheduleJobService {

	@Autowired
	private ScheduleJobDAO scheduleJobDAO;

	public List<ScheduleJobDto> getScheduleJobAll() {
		return scheduleJobDAO.getScheduleJobAll();
	}

    public List<ScheduleJobDto> getScheduleJobByPage(ScheduleJobDto entity, PageParameter page) {
		return scheduleJobDAO.getScheduleJobByPage(entity,page);
	}

    public int updateScheduleJob(ScheduleJobDto entity) {
        return scheduleJobDAO.updateScheduleJob(entity);
    }

    public int runJobNow(Long id, Scheduler scheduler) {
        ScheduleJobDto job = scheduleJobDAO.getJobById(id);
        try {
            JobUtil.runAJobNow(scheduler,job);
            return 1;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
