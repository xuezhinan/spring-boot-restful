package com.asiainfo.springbootrestful.proxytest;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
//MethodInterceptor借口是spring包下的

public class CglibProxy implements MethodInterceptor  {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("方法执行前-------------");
        methodProxy.invoke(o,objects);
        return null;
    }

}
