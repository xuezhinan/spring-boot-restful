package com.asiainfo.springbootrestful.controller;


import com.alibaba.fastjson.JSON;
import com.asiainfo.springbootrestful.entities.UserRole;
import com.asiainfo.springbootrestful.service.QryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Api(description = "查询接口",value = "不知道")
@Controller
public class QryController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RedisCacheManager cacheManager;

    @Autowired
    QryService qryService;

    @ApiOperation(value = "获取用户",notes = "获取")
    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Callable<String> getUser(HttpServletRequest request){
        //使用异步处理，这是tomcat主线程，可以把任务交给子线程去处理
        logger.info("tomcat主线程开始："+Thread.currentThread()+"---------"+System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("子主线程开始："+Thread.currentThread()+"---------"+System.currentTimeMillis());
                UserRole role = qryService.getRoleById(2);
                logger.info(JSON.toJSONString(role));
                logger.info("子主线程开始："+Thread.currentThread()+"---------"+System.currentTimeMillis());
                return JSON.toJSONString(role);
            }
        };
        logger.info("tomcat主线程结束："+Thread.currentThread()+"---------"+System.currentTimeMillis());
        return callable;

    }

    @ApiOperation(value = "查询角色表",notes = "查询")
    @ResponseBody
    @RequestMapping(value = "/getRole",method = RequestMethod.GET)
    public String getRoleById(HttpServletRequest request){
        List<Map<String, Object>> list = qryService.getRole();
        logger.info(JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }
    @ApiOperation(value = "增加角色" ,  notes="增加角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userRole", value = "角色", required = true, paramType = "query", dataType = "")})
    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public UserRole insert(UserRole userRole){
        userRole.setRoleCode("ceshi");
        userRole.setRoleName("读写分离");
        userRole.setCreatedBy("2");
        int i = qryService.insert(userRole);
        return userRole;
    }

    @ResponseBody
    @RequestMapping("/join")
    public String joinget(HttpServletRequest request){
        List<Map<String, Object>> list = qryService.joinget();
        logger.info(JSON.toJSONString(list));
        //获取某个缓存,从缓存中直接获取数据
        Cache cache = cacheManager.getCache("role");
        cache.put("role-1","角色1");
        Cache.ValueWrapper valueWrapper = cache.get("role-1");
        logger.warn("------------->"+JSON.toJSONString(valueWrapper.get()));
        return JSON.toJSONString(list);
    }

    @ResponseBody
    @RequestMapping("/testAsync")
    public String testAsync(HttpServletRequest request) throws ExecutionException, InterruptedException, TimeoutException {

        String async = qryService.testAsync();


        return async;
    }

}
