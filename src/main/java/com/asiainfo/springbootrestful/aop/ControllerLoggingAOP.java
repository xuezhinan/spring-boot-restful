package com.asiainfo.springbootrestful.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ControllerLoggingAOP {

    Logger logger =LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.asiainfo.springbootrestful.controller.*.*(..))")
    public void PointCut(){}


    @Before("PointCut()")
    public void before(JoinPoint joinPoint){

    }

    @AfterReturning(value = "PointCut()", returning = "result")
    public void after(JoinPoint joinPoint,Object result){
        logger.info("调用方法："+joinPoint.getSignature().getName());
        logger.info("方法参数是："+joinPoint.getArgs()[0]);
        logger.info("方法返回结果是："+result);
    }
}
