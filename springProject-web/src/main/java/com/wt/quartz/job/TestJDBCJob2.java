package com.wt.quartz.job;

import org.quartz.*;

import java.util.Date;

/**
 * @DisallowConcurrentExecution 保证多个任务间不会同时执行，将在上次执行完成之后执行
 * @PersistJobDataAfterExecution 保存在JobDataMap传递的参数
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJDBCJob2 implements Job {
    public boolean isRunning = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(!isRunning){
            JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
           int count= jobDataMap.getInt("count");
            System.out.println("count:"+count);
            jobDataMap.put("count", count+1);

            System.out.println(new Date()+" mission start");
            for (int i = 0; i < 100; i++) {
                System.out.println("mission process "+(i+1)+"%");
                if(-1==i){
                    JobExecutionException e2 = new JobExecutionException(new Exception());
                    //发生异常重复执行
//                    e2.setRefireImmediately(true);
//                    发生异常后停止所有的trigger
                    e2.setUnscheduleAllTriggers(true);
                    throw e2;
                }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

            System.out.println(new Date()+" minssion complete");
        }
        System.out.println(new Date()+" mission out");

    }
}
