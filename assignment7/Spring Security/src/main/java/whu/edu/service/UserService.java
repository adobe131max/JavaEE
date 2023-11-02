package whu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import whu.edu.entity.User;
import whu.edu.entity.UserDto;

public interface UserService extends IService<User> {

    UserDto getUser(String name);

    void deleteUser(String userName);

    void updateUser(User user);

    void addUser(User user);
}
