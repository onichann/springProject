package com.wt.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


public class TestSpringRAMJob extends QuartzJobBean {

    private Logger logger=Logger.getLogger(TestSpringRAMJob.class);
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        logger.info("welcome to springRAMQuartz "+ LocalDateTime.now().format(df));
        logger.info("count="+jobExecutionContext.getJobDetail().getJobDataMap().getString("count"));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("complete");
        JobExecutionException jobExecutionException=new JobExecutionException(new Exception());
//        jobExecutionException.setUnscheduleAllTriggers(true);
//        throw jobExecutionException;
    }
}
