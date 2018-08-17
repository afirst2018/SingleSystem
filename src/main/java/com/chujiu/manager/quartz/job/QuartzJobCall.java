package com.chujiu.manager.quartz.job;

import com.chujiu.dto.ScheduleJobDto;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by Administrator on 2016/10/14.
 */
@DisallowConcurrentExecution
public class QuartzJobCall implements Job {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJobCall.class);
    public final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public final String URL = "jdbc:mysql://127.0.0.1:3306/test";
    public final String USERNAME = "root";
    public final String PASSWORD = "root";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Class.forName(DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(URL, USERNAME,
                    PASSWORD);
            String sql = "{CALL add_pro(?,?,?)}"; //调用存储过程
            CallableStatement cstm = connection.prepareCall(sql); //实例化对象cstm
            cstm.setInt(1, 122);
            cstm.setInt(2, 2); //
            cstm.registerOutParameter(3, Types.INTEGER); // 设置返回值类型
            cstm.execute(); // 执行存储过程
            logger.debug("outInf{} " , String.valueOf(cstm.getInt(3)));
            cstm.close();
            connection.close();
            logger.debug("outInf{} " , "存储过程任务成功运行");
            ScheduleJobDto scheduleJobDto = (ScheduleJobDto) jobExecutionContext.getMergedJobDataMap().get("scheduleJobDto");
            logger.debug("outInf{} " , "任务名称 = [" + scheduleJobDto.getJobName() + "]");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
