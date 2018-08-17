package com.chujiu.manager.quartz.managerdao;

import com.chujiu.core.page.PageParameter;
import com.chujiu.dto.ScheduleJobDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleJobDAO {

	List<ScheduleJobDto> getScheduleJobAll();

    List<ScheduleJobDto> getScheduleJobByPage(@Param("entity") ScheduleJobDto entity,@Param("page") PageParameter page);

    int updateScheduleJob(@Param("entity") ScheduleJobDto entity);

    ScheduleJobDto getJobById(@Param("id") Long id);
}
