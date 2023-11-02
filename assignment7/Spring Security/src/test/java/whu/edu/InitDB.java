package whu.edu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whu.edu.entity.User;
import whu.edu.service.UserService;

@SpringBootTest
public class InitDB {

    @Autowired
    UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId("user0");
        user.setPassword("123456");
        userService.addUser(user);
    }
}
