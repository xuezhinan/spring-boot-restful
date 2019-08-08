package com.asiainfo.springbootrestful.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Api(description = "登录接口")
@Controller
public class LoginController {


    @RequestMapping("/")
    public String index(HttpServletRequest request){
        return "index";
    }
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(HttpServletRequest request){
        return "hello word";
    }

    @RequestMapping("/success")
    public String success(ModelMap modelMap, String param, HttpServletRequest httpServletRequest){
        System.out.println(modelMap.get("orderId"));
        System.out.println(param);
        System.out.println(httpServletRequest.getParameter("orderId"));
        modelMap.addAttribute("name","张三");
        modelMap.addAttribute("words","大家好！");
        modelMap.addAttribute("id","num01");
        modelMap.addAttribute("users", Arrays.asList("zhangsan","lisi","wangwu"));
        ApplicationContext ioc = new AnnotationConfigApplicationContext();
        System.out.println(ioc.containsBean("localeResolver"));
        return "test/success";
    }

    @ApiOperation(value = "处理登录",notes = "denglu")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")})
    @PostMapping("/dologin")
    public String doLogin(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          ModelMap modelMap, HttpSession httpSession){
        System.out.println(userName+"----------"+password);
        if ("admin".equals(userName) && "123456".equals(password)){
            //登录成功，登录信息放到session中
            httpSession.setAttribute("userInfo",userName);
            modelMap.addAttribute("userName",userName);
            //防止表单重复提交，可以重定向,重定向之后的页面无法取到map中的值
            return "redirect:/main.html";
        }else {
            //防止表单重复提交，可以重定向
            modelMap.addAttribute("msg","用户名密码错误！");
            return "index.html";
        }
    }
}

