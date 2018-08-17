package com.chujiu.manager.quartz.job;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.chujiu.dto.ScheduleJobDto;
import com.chujiu.manager.quartz.shell.RmtShellExecutor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/10/14.
 */
@DisallowConcurrentExecution
public class QuartzJobShell implements Job {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobShell.class);

    private Connection conn;
    private String charset = Charset.defaultCharset().toString();

    private final String usr = "root";
    private final String password = "cptbtptp";
    private final String serverIP = "192.168.1.202";
    private final String shPath = "/home/jesse/文档/hello.sh";

    private final int TIME_OUT = 1000 * 5 * 60;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        RmtShellExecutor exe = new RmtShellExecutor(serverIP, usr, password);

        String outInf;

        try {
            outInf = exe.exec("sh " + shPath + " xn");
            logger.debug("outInf{} " , outInf);
            logger.debug("outInf{} " , "调用shell任务成功运行");
            ScheduleJobDto scheduleJobDto = (ScheduleJobDto)jobExecutionContext.getMergedJobDataMap().get("scheduleJobDto");
            logger.debug("outInf{} " , "任务名称 = [" + scheduleJobDto.getJobName() + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean login() throws IOException {
        conn = new Connection(serverIP);
        conn.connect();
        return conn.authenticateWithPassword(usr, password);
    }

    public String exec(String cmds) throws IOException {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        int ret = -1;

        try {
            if (login()) {
                Session session = conn.openSession();
                session.execCommand(cmds);
                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);
                logger.info("caijl:[INFO] outStr=" + outStr);
                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);
                logger.info("caijl:[INFO] outErr=" + outErr);
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                ret = session.getExitStatus();

            } else {
                logger.error("caijl:[INFO] ssh2 login failure:" + serverIP);
                throw new IOException("SSH2_ERR");
            }

        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stdOut != null)
                stdOut.close();
            if (stdErr != null)
                stdErr.close();
        }

        return outStr;
    }

    private String processStream(InputStream in, String charset) throws IOException {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }
}
