package com.asiainfo.springbootrestful.component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器要实现HandlerInterceptor接口

public class LoginInterceptors implements HandlerInterceptor {

    Logger log = LoggerFactory.getLogger(getClass());
    //在业务处理器处理请求之前调用，多用于登录校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("userInfo");
        if (user == null){
            //没有登录跳转到登录页面,重定向到登录页面
            request.setAttribute("errmsg","没有登录，请登录！");
            //重定向到登录页面
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else {
            //登录成功
            return true;
        }
    }
    //在业务处理器处理请求之后生成视图之前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.error("****************分割线*******************");
        log.info("进入postHandle方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("进入afterCompletion方法");
    }
}
