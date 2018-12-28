package com.yuan.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangy on 2018/11/1.
 */
public class HelloScheduler {

    /**
     * 第一个Quartz简单的例子
     */
    public static void firstQuartz() throws Exception {
        //创建一个JobDetail实例，将该实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();
        System.out.println("JobDetail name:" + jobDetail.getKey().getName());
        System.out.println("JobDetail group:" + jobDetail.getKey().getGroup());
        System.out.println("JobDetail jobClass:" + jobDetail.getJobClass().getName());
        //创建一个Trigger实例，定义该Job立即执行，并且每隔两秒钟重复执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(now));
        //将3个实例联系到一起
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 使用usingJobData()方法给HelloJob execute()方法中的JobExecutionContext参数传值
     */
    public static void jobExecutionContextAndJobDataMap() throws Exception {
        //创建一个JobDetail实例，将该实例与HelloJob绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1")
                .usingJobData("msg", "hello myJob1").usingJobData("FloatJobVal", 3.14F).build();
        //创建一个Trigger实例，定义该Job立即执行，并且每隔两秒钟重复执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .usingJobData("msg", "hello myTrigger1").usingJobData("DoubleTriggerVal", 2.0D)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(now));
        //将3个实例联系到一起
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void testTrigger() throws Exception {
        //打印当前时间
        Date start = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(start));
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
        //获取距离当前时间3秒后的时间，作为开始执行的时间
        start.setTime(start.getTime() + 3000);
        //获取距离当前时间6秒后的时间，作为执行结束时间
        Date end = new Date();
        end.setTime(end.getTime() + 6000);
        //创建一个Trigger实例，定义该Job的开始时间和结束时间，并且每隔两秒钟重复执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .startAt(start).endAt(end)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //将3个实例联系到一起
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void testSimpleTrigger() throws Exception {
        //打印当前时间
        Date start = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(start));
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
        //获取当前时间4秒后的时间
        start.setTime(start.getTime() + 4000L);
        //创建一个Trigger实例，定义该Job的在当前时间4秒后执行，且仅执行1次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .startAt(start)
                .build();
        //注释掉的代码表示：定义该Job的在当前时间4秒后执行，之后每隔2秒执行一次，共执行3次（除第一次外）
//        Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
//                .startAt(start)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).withRepeatCount(3))
//                .build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //将3个实例联系到一起
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 从打印看出，在设置了endAt(end)时间后，会将withRepeatCount(3)的效果覆盖掉，
     * 也就是当结束时间到，无论有没有重复到规定的3次，都将停止任务
     */
    public static void testSimpleTrigger2() throws Exception {
        //打印当前时间
        Date start = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(start));
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
        //获取当前时间4秒后的时间，作为执行任务开始时间
        start.setTime(start.getTime() + 4000L);
        //获取当前时间6秒后的时间，作为执行任务结束时间
        Date end = new Date();
        end.setTime(start.getTime() + 6000L);
        //创建一个Trigger实例，定义该Job的在当前时间4秒后执行，之后每隔2秒执行一次，共执行3次（除第一次外）
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .startAt(start).endAt(start)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).withRepeatCount(3))
                .build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //将3个实例联系到一起
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * CronTrigger：基于日历的作业调度器，而不是像SimpleTrigger那样精确指定间隔时间，比SimpleTrigger更常用。
     * 配合Cron表达式一起使用，Cron表达式【秒 分 时 日期 月 周 年】
     * 何时执行关键就看.withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ? *"))里的Cron表达式如何定义
     * Cron表达式示例：
     * 每秒都触发：* * * * * ? *
     * 每天10点15分触发：0 15 10 ? * *
     * 每天下午的2点到2点59分（整点开始，每隔5分钟触发）：0 0/5 14 * * ?
     * 每天14点至14点59分55秒，以及18点至18点59分55秒，每隔5秒执行一次：0/5 * 14,18 * * ?
     * 从周一到周五每天上午10点15分触发：0 15 10 ? * MON-FRI
     * 每月的第三周的星期五10点15分开始触发：0 15 10 ? * 6#3
     * 从2016-2017年每月最后一周的星期五的10点15分触发：0 15 10 ? * 6L 2016-2017
     * 注：百度 在线Cron表达式生成器
     */
    public static void testCronTrigger() throws Exception {
        //打印当前时间
        Date start = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(start));
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
        //创建一个CronTrigger实例，定义该Job执行时间
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * 14,18 * * ?"))
                .build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //将3个实例联系到一起
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void testScheduler() throws Exception {
        //打印当前时间
        Date start = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler当前时间是：" + sdf.format(start));
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
        //创建一个CronTrigger实例，定义该Job执行时间
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();
        //创建Scheduler实例
        SchedulerFactory sfact = new StdSchedulerFactory();
        Scheduler scheduler = sfact.getScheduler();
        scheduler.start();
        //将3个实例联系到一起，打印任务的执行时间
        System.out.println("该任务执行时间：" + sdf.format(scheduler.scheduleJob(jobDetail, trigger)));
        //任务执行2秒后挂起
        Thread.sleep(2000L);
        scheduler.standby();//只是暂时被挂起，是可以通过调用方法被重新启动的
//        scheduler.shutdown();//停止调度器，无法被重启
        //挂起3秒后重新启动
        Thread.sleep(3000L);
        scheduler.start();
    }

    public static void main(String[] args) throws Exception {
//        firstQuartz();
//        jobExecutionContextAndJobDataMap();
//        testTrigger();
//        testSimpleTrigger();
//        testSimpleTrigger2();
//        testCronTrigger();
        testScheduler();
    }

}
