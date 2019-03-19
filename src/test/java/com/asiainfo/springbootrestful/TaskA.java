package com.asiainfo.springbootrestful;


import java.util.concurrent.TimeUnit;

//实现runnable接口创建任务
public class TaskA implements Runnable{

    protected int countDown = 10;

    private static int taskCount = 0;

    private final  int id = taskCount++;

    public String status(){
        String n = countDown > 0 ? Integer.toString(countDown) : "Die" ;

        return "#"+id+"("+n+")";
    }
    @Override
    public void run() {
        while (countDown-- >0){
            System.out.println(status());
            //Thread.yield();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
