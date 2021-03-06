package com.caoyuqian.lol.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qian
 * @version V1.0
 * @Title: PrintTimeJob
 * @Package: com.caoyuqian.lol.task
 * @Description: TOTO
 * @date 2019-08-28 18:51
 **/
public class PrintTimeJob extends QuartzJobBean {
    private final static Logger log = LoggerFactory.getLogger(PrintTimeJob.class);
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //获取JobDetail中关联的数据
        String msg = (String) context.getJobDetail().getJobDataMap().get("msg");
        log.info("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
    }
}
