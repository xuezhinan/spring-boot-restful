package com.asiainfo.springbootrestful.controller;


import com.asiainfo.springbootrestful.service.TimingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TaskContraller {

    @Autowired
    TimingTask timingTask;

    @RequestMapping("/task")
    @ResponseBody
    public String task(HttpServletRequest request) throws Exception {
        timingTask.dealSomething();
        return "success";
    }
}
