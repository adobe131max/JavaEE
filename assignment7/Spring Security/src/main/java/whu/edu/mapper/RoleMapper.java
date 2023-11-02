package whu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import whu.edu.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select role.* from role, user_role " +
            "where role.id = user_role.role_id " +
            "and user_role.user_id = #{userId}")
    List<Role> findRolesByUser(@Param("userId")String userId);
}
