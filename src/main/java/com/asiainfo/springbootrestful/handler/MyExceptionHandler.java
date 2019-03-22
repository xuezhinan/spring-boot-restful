package com.asiainfo.springbootrestful.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//自定义异常处理器
@ControllerAdvice
public class MyExceptionHandler {
    //可以指定处理指定异常。或者处理所有异常
    @ResponseBody//同样需要该注解 返回数据而不是视图
    @ExceptionHandler(IOException.class)
    public Map handlerException(Exception e){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code","异常编码");
        map.put("errmsg","--------");
        return map;
    }
}
