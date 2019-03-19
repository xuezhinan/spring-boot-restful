package com.asiainfo.springbootrestful.controller;

import com.alibaba.fastjson.JSON;
import com.asiainfo.springbootrestful.dao.DepartmentDao;
import com.asiainfo.springbootrestful.dao.EmployeeDao;
import com.asiainfo.springbootrestful.entities.Department;
import com.asiainfo.springbootrestful.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmpsController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/emps")
    public String list(ModelMap modelMap){
        Collection<Employee> list = employeeDao.getAll();

        modelMap.addAttribute("empList",list);
        return "emp/list";
    }

    @GetMapping("/emp")
    public String add(ModelMap modelMap){
        Collection<Department> departments = departmentDao.getDepartments();
        modelMap.addAttribute("departments",departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    //此处使用Employee类型作为参数，form表单提交的数据，springMVC会自动的映射为对应的javaBean
    public String addEmp(Employee employee){
       logger.info("参数是："+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
    @ResponseBody
    @PostMapping("/test")
    //这里可以使用Map类型和ModelMap ，不能使用Model类型，ajax请求的json数据可以直接映射为map类型，不能有两个@RequestBody注解
    public String test(@RequestBody ModelMap model, HttpServletRequest httpServletRequest){
        logger.info("参数是："+model);
        String name = (String) (model.get("userName"));
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("pet","dog");
        map.put("age","23");
        //如果返回类型不是标准的json类型就不会执行成功的回调函数
        return JSON.toJSONString(map);
    }
}
