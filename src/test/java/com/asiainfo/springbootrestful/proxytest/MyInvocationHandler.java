package com.asiainfo.springbootrestful.proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/****
 *
 * 创建代理对象的过程
 * ①创建被代理的接口和类；
 * ②创建InvocationHandler接口的实现类，在invoke方法中实现代理逻辑；
 * ③通过Proxy的静态方法newProxyInstance( ClassLoaderloader, Class[] interfaces, InvocationHandler h)创建一个代理对象
 * ④使用代理对象。
 *
 */
public class MyInvocationHandler implements InvocationHandler {


    private  Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("准备插入数据");

        Object res = method.invoke(target,args);

        System.out.println("插入数据成功");

        return res;

    }
}
