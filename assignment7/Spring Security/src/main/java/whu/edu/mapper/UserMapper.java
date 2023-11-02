package whu.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import whu.edu.entity.User;
import whu.edu.entity.UserDto;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 查询用户及其角色
    @Select("select * from user where id = #{id}")
    @Results({@Result(id = true, property = "id", column = "id"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "whu.edu.mapper.RoleMapper.findRolesByUser"))})
    UserDto getUser(String id);
}
