package com.asiainfo.springbootrestful.service;

import com.asiainfo.springbootrestful.entities.UserRole;
import com.asiainfo.springbootrestful.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QryService {

   /* @Autowired
    JdbcTemplate jdbcTemplate;*/

    @Autowired
    UserRoleMapper userRoleMapper;
    /*public List<Map<String, Object>> getList(){

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from ums_admin");

        return list;
    }*/

    public List<Map<String, Object>> getRole(){
        List<Map<String, Object>> roleList = userRoleMapper.getUserRole();

        System.out.println(roleList);
        return roleList;
    }

    public List<Map<String, Object>> getRoleById(Integer id){
        List<Map<String, Object>> roleList = userRoleMapper.getRoleById(id);

        System.out.println(roleList);
        return roleList;
    }


    public int insert(UserRole userRole){
        int num = userRoleMapper.insert(userRole);
        return num;
    }

    public List<Map<String,Object>> joinget(){
        List<Map<String, Object>> mapList = userRoleMapper.joinSelect();
        return mapList;
    }
}
