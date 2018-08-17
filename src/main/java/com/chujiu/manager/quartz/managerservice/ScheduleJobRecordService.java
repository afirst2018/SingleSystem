package com.chujiu.manager.quartz.managerservice;

import com.chujiu.dto.ScheduleJobRecordDto;
import com.chujiu.manager.quartz.managerdao.ScheduleJobRecordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianci on 2016/10/27.
 */
@Service
public class ScheduleJobRecordService{

    @Autowired
    private ScheduleJobRecordDAO scheduleJobRecordDAO;


    public int addRecord(ScheduleJobRecordDto scheduleJobDto) {
        return scheduleJobRecordDAO.addRecord(scheduleJobDto);
    }

    public int updateRecord(Object recordId) {
        return scheduleJobRecordDAO.updateRecord(recordId);
    }
}
