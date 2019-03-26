package com.asiainfo.springbootrestful;

import com.alibaba.fastjson.JSON;
import com.asiainfo.springbootrestful.controller.QryController;
import com.asiainfo.springbootrestful.entities.UserRole;
import com.asiainfo.springbootrestful.proxytest.MyInvocationHandler;
import com.asiainfo.springbootrestful.proxytest.UserService;
import com.asiainfo.springbootrestful.proxytest.UserServiceImpl;
import com.asiainfo.springbootrestful.service.QryService;
import com.asiainfo.springbootrestful.utils.ClassUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRestfulApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    QryService qryService;

    @Autowired
    RedisTemplate myRedisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void proxytrest(){
        UserService target = new UserServiceImpl();
        QryController qryController = new QryController();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(target);
        //第一个参数是指定代理类的类加载器（我们传入当前测试类的类加载器） 
        //第二个参数是代理类需要实现的接口（我们传入被代理类实现的接口，这样生成的代理类和被代理类就实现了相同的接口） 
        //第三个参数是invocation handler，用来处理方法的调用。这里传入我们自己实现的handler

        UserService proxyObject = (UserService) Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(),
                target.getClass().getInterfaces(),invocationHandler);

        proxyObject.add("张三");

    }

    @Test
    public void redisTest(){
        UserRole role = qryService.getRoleById(2);
        //stringRedisTemplate.opsForValue().append("name","zhangsan");
        //myRedisTemplate.opsForValue().set("obj-3", role);
        UserRole userRole = new UserRole();
        userRole.setId(6);
        userRole.setRoleCode("bianma");
        userRole.setRoleName("zhangdsagn");
        userRole.setCreatedBy("1");
        /*UserRole obj = (UserRole) myRedisTemplate.opsForValue().get("obj-4");
        System.out.println(obj.getId());*/
        myRedisTemplate.opsForValue().set("obj-4",userRole);
    }

    @Test
    public void classTest(){
        String packName = "com.asiainfo.springbootrestful.controller";
        Set<Class<?>> set = ClassUtil.getClasses(packName);
        System.err.println(set);
    }
}
