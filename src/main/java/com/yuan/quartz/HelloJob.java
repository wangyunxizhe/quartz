package com.yuan.quartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangy on 2018/11/1.
 * <p>
 * Job为实现业务逻辑的任务接口，实现execute()方法，把要做的事情写里面即可
 */
public class HelloJob implements Job {

    public void firstQuartz() {
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob当前时间是：" + sdf.format(now));
        //编写具体业务逻辑，要干什么
        System.out.println("Hello Job!");
    }

    /**
     * 第一种从JobExecutionContext获取自定义参数的用法：
     * 通过正常的jobExecutionContext.getJobDetail()以及jobExecutionContext.getTrigger()来获取想要的参数
     */
    public void jobExecutionContextAndJobDataMap(JobExecutionContext jobExecutionContext) {
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob当前时间是：" + sdf.format(now));
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        System.out.println("JobDetail中设置的名字和组：" + key.getName() + ":" + key.getGroup());
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println("JobDetail中设置的JobData的两个Value：第一个是：" + dataMap.getString("msg") +
                "====第二个是:" + dataMap.getFloat("FloatJobVal"));
        TriggerKey trKey = jobExecutionContext.getTrigger().getKey();
        System.out.println("Trigger中设置的名字和组：" + trKey.getName() + ":" + trKey.getGroup());
        JobDataMap tdataMap = jobExecutionContext.getTrigger().getJobDataMap();
        System.out.println("Trigger中设置的JobData的两个Value：第一个是：" + tdataMap.getString("msg") +
                "====第二个是:" + tdataMap.getDouble("DoubleTriggerVal"));
    }

    private String msg;
    private Float FloatJobVal;
    private Double DoubleTriggerVal;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Float getFloatJobVal() {
        return FloatJobVal;
    }

    public void setFloatJobVal(Float floatJobVal) {
        FloatJobVal = floatJobVal;
    }

    public Double getDoubleTriggerVal() {
        return DoubleTriggerVal;
    }

    public void setDoubleTriggerVal(Double doubleTriggerVal) {
        DoubleTriggerVal = doubleTriggerVal;
    }

    /**
     * 第二种从JobExecutionContext获取自定义参数的用法：
     * 通过Job类中的属性定义直接获取，
     * 注意！这里定义的属性名，必须和HelloScheduler类中jobExecutionContextAndJobDataMap()方法里
     * .usingJobData()里定义的key保持完全一致！在底层源码中会通过set方法将自定义参数注入
     */
    public void jobExecutionContextAndJobDataMap2(JobExecutionContext jobExecutionContext) {
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob当前时间是：" + sdf.format(now));
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        System.out.println("JobDetail中设置的名字和组：" + key.getName() + ":" + key.getGroup());
        TriggerKey trKey = jobExecutionContext.getTrigger().getKey();
        System.out.println("Trigger中设置的名字和组：" + trKey.getName() + ":" + trKey.getGroup());
        System.out.println("msg======" + msg);
        System.out.println("FloatJobVal======" + FloatJobVal);
        System.out.println("DoubleTriggerVal======" + DoubleTriggerVal);
    }

    public void testTrigger(JobExecutionContext jobExecutionContext) {
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob当前时间是：" + sdf.format(now));
        Trigger thisTrigger = jobExecutionContext.getTrigger();
        System.out.println("此Trigger的开始时间：" + sdf.format(thisTrigger.getStartTime()));
        System.out.println("此Trigger的结束时间：" + sdf.format(thisTrigger.getEndTime()));
        JobKey jobKey = thisTrigger.getJobKey();
        System.out.println("jobKey info====" + "name：" + jobKey.getName() + "||group：" + jobKey.getGroup());
    }

    public void testSimpleTrigger(JobExecutionContext jobExecutionContext) {
        //打印当前时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob当前时间是：" + sdf.format(now));
        System.out.println("~~~~~testSimpleTrigger~~~~~");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        firstQuartz();
//        jobExecutionContextAndJobDataMap(jobExecutionContext);
//        jobExecutionContextAndJobDataMap2(jobExecutionContext);
//        testTrigger(jobExecutionContext);
        testSimpleTrigger(jobExecutionContext);
    }

}
