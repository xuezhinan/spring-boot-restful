package com.asiainfo.springbootrestful.aop;

import com.asiainfo.springbootrestful.dataSourcePeiZhi.RoutingDataSource;
import com.asiainfo.springbootrestful.dataSourcePeiZhi.handler.DataSourceHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ControllerLoggingAOP {

    Logger logger =LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.asiainfo.springbootrestful.controller.*.*(..))")
    public void PointCut(){}


    //
    @Before("@annotation(com.asiainfo.springbootrestful.dataSourcePeiZhi.RoutingDataSource)")
    public void before(JoinPoint joinPoint){

        //获取当前访问的类
        Class<?> className = joinPoint.getTarget().getClass();
        //获取访问的方法名
        String methodName = joinPoint.getSignature().getName();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        //得到方法的参数类型
        Class<?>[] argClass = methodSignature.getMethod().getParameterTypes();
        String dataSource = DataSourceHandler.DEFAUL_DATASOURCE;

        try {
            //得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            //判断是否存在注解@RoutingDataSource
            if (method.isAnnotationPresent(RoutingDataSource.class)){
                RoutingDataSource annotation = method.getAnnotation(RoutingDataSource.class);
                //取出数据源注解中的数据源
                dataSource = annotation.value();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //切换数据源
        DataSourceHandler.setDB(dataSource);
    }

    @After("@annotation(com.asiainfo.springbootrestful.dataSourcePeiZhi.RoutingDataSource)")
    public void after(JoinPoint joinPoint){
        DataSourceHandler.clearDB();
    }

    /*@AfterReturning(value = "PointCut()", returning = "result")
    public void after(JoinPoint joinPoint,Object result){
        logger.info("调用方法："+joinPoint.getSignature().getName());
        logger.info("方法参数是："+joinPoint.getArgs()[0]);
        logger.info("方法返回结果是："+result);
    }*/
}
