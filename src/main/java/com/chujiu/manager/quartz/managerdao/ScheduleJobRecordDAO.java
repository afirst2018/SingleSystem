package com.chujiu.manager.quartz.managerdao;

import com.chujiu.dto.ScheduleJobRecordDto;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2016/10/27.
 */
public interface ScheduleJobRecordDAO {

    int addRecord(@Param("entity") ScheduleJobRecordDto scheduleJobDto);

    int updateRecord(@Param("id") Object recordId);
}
