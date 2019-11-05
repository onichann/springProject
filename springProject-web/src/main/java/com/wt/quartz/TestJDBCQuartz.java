package com.wt.quartz;

import com.wt.quartz.job.TestJDBCJob2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class TestJDBCQuartz {

    private static void startScheduler() throws SchedulerException {

        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        JobDetail jobDetail= JobBuilder.newJob(TestJDBCJob2.class).withIdentity("myjob","myjobGroup").build();
        Trigger trigger=TriggerBuilder.newTrigger()
                .withIdentity("testTrigger","testTrigger")
                //.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?").withMisfireHandlingInstructionFireAndProceed())
               .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(1).withMisfireHandlingInstructionNowWithExistingCount())
                .startNow().build();
        jobDetail.getJobDataMap().put("count",1);
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }

    public static void main(String[] args) throws SchedulerException {
        startScheduler();
//        System.out.println(new Date());
//        System.out.println(LocalDateTime.now().plusSeconds(10));
//        Date startTime = nextGivenSecondDate(null, 10);
//        System.out.println(startTime);
    }
}
