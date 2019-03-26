package com.asiainfo.springbootrestful.mapper;

import com.asiainfo.springbootrestful.entities.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//使用注解版的mapper
@Repository
@Mapper
public interface UserRoleMapper {

    @Select("select * from smbms_role")
    public List<Map<String,Object>> getUserRole();

    @Select("select * from smbms_role where id = #{id}")
    public UserRole getRoleById(Integer id);

    @Insert("insert into smbms_role(roleCode,roleName,createdBy,creationDate) values(#{roleCode},#{roleName},#{createdBy},#{creationDate})")
    public int insert(UserRole userRole);


    @Select("SELECT r.* ,u.* FROM smbms_user u , smbms_role r WHERE u.userRole = r.id")
    public List<Map<String,Object>> joinSelect();

}
