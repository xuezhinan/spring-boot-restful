package com.asiainfo.springbootrestful.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TimingTask {

    @Async // 这是一个异步处理的方法
    @Scheduled(fixedDelay = 10000)  //定时任务的注解,单位是毫秒
    public void dealSomething() throws Exception{
        System.out.println("执行之前的时间是："+System.currentTimeMillis());
        //模拟调用接口耗时
        Thread.sleep(3000);
        System.out.println("执行之后的时间是："+System.currentTimeMillis());
        System.out.println("定时任务执行！");
    }
}
