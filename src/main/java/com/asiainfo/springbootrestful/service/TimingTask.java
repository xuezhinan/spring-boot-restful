package com.asiainfo.springbootrestful.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimingTask {

    @Async // 这是一个异步处理的方法，多线程执行异步任务
    /**fixedRate:上一次开始执行时间点之后5秒再执行*/
    //@Scheduled(fixedRate = 10000)  //定时任务的注解,单位是毫秒
    /**fixedDelay:上一次执行完毕时间点之后5秒再执行*/
    //@Scheduled(fixedDelay = 5000)
    //@Scheduled(cron = "* * * * * * *")
    /**第一次延迟2秒后执行，之后按fixedDelay的规则每5秒执行一次*/
    //@Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void dealSomething() throws Exception{
        System.out.println("执行之前的时间是："+System.currentTimeMillis());
        //模拟调用接口耗时
        Thread.sleep(3000);
        System.out.println("执行之后的时间是："+System.currentTimeMillis());
        System.out.println("定时任务执行！");
    }
}
