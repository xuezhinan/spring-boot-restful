package com.asiainfo.springbootrestful.controller;


import com.alibaba.fastjson.JSON;
import com.asiainfo.springbootrestful.service.QryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class QryController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    QryService qryService;

   /* @ResponseBody
    @RequestMapping("/get")
    public String getUser(HttpServletRequest request){
        List<Map<String, Object>> list = qryService.getList();
        logger.info(JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }*/

    @ResponseBody
    @RequestMapping("/getRole")
    public String getRoleById(HttpServletRequest request){
        List<Map<String, Object>> list = qryService.getRole();
        logger.info(JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

    @ResponseBody
    @RequestMapping("/join")
    public String joinget(HttpServletRequest request){
        List<Map<String, Object>> list = qryService.joinget();
        logger.info(JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

}
