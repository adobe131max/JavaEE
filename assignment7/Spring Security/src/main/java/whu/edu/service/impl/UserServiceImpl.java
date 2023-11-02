package whu.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import whu.edu.entity.User;
import whu.edu.entity.UserDto;
import whu.edu.mapper.UserMapper;
import whu.edu.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    // 将查询结果添加到缓存user中，以name作为key
    @Override
    @Cacheable(cacheNames = "user", key = "#name", condition = "#name!=null")
    public UserDto getUser(String name) {
        return userMapper.getUser(name);
    }

    // 移除缓存user中key为userName的对象
    @Override
    @CacheEvict(cacheNames = "user", key = "#userName")
    public void deleteUser(String userName) {
        userMapper.deleteById(userName);
    }

    // 移除缓存user中key为name的对象
    @Override
    @CacheEvict(cacheNames = "user", key = "#user.name")
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userMapper.insert(user);
    }
}
