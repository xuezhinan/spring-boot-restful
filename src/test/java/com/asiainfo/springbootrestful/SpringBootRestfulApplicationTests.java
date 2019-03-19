package com.asiainfo.springbootrestful;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRestfulApplicationTests {

    @Test
    public void contextLoads() {
    }

    //线程测试
    @Test
    public void ThreadTest(){
        for (int i = 0 ; i < 5 ; i++){
            new Thread(new TaskA()).start();
        }
        System.out.println("等待结束");
    }

    //线程测试
    @Test
    public void ThreadTest2(){
        //SingleThreadExecutor创建的线程会按被提交的顺序单个顺序执行
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0 ; i < 5 ; i++){
            service.execute(new TaskA());
        }
        service.shutdown();
        System.out.println("等待结束");
    }

    //线程测试
    @Test
    public void ThreadTest3(){

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0 ; i < 5 ; i++){
            service.execute(new TaskA());
        }
        service.shutdown();
        System.out.println("等待结束");
    }
}
