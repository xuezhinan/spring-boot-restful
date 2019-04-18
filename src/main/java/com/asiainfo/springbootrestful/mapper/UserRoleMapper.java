package com.asiainfo.springbootrestful.mapper;

import com.asiainfo.springbootrestful.entities.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
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

    //mybatis实现批量插入，这样只会连接一次数据库，有一定的程度的性能提升
    @InsertProvider(type = Provider.class ,method = "batchAdd")
    public int batchInsert(List<UserRole> list);

    class Provider {
        public String batchAdd(Map map){
            List<UserRole> list = (List<UserRole>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("insert into smbms_role(roleCode,roleName,createdBy,creationDate) values");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].roleCode},#'{'list[{0}].roleName},#'{'list[{0}].createdBy},#'{'list[0].creationDate})"
            );
            for (int i = 0; i < list.size(); i++) {
                sb.append(mf.format(new Object[]{i}));
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
    }

}
