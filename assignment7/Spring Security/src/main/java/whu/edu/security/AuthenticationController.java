package whu.edu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import whu.edu.entity.User;

@RestController
@CrossOrigin
@RequestMapping("authenticate")
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId());
        if (passwordEncoder.matches(user.getPassword(),userDetails.getPassword())) {
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("用户认证未通过");
        }
    }
}
