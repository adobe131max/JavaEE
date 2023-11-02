# Spring Security

## 使用

``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## 功能

### Authentication （身份验证）

Spring Security 提供了多种身份验证机制，包括基于表单、基于HTTP基本认证、基于令牌、LDAP等。可以根据你的应用程序需求选择适合的认证方式。
可以使用用户名和密码、令牌、证书等方式验证用户身份。

### Authorization （授权）

Spring Security 允许你定义访问控制规则，以确定哪些用户可以访问应用程序的特定资源或功能。这些规则可以通过注解、XML配置、Java配置等方式定义。
支持基于角色（Role-based）和基于权限（Permission-based）的授权。

### Security Filter Chain （安全过滤链）

### 用户会话管理

Spring Security 支持用户会话管理，包括处理会话超时、会话固定攻击、会话管理策略等。

### Cross-Site Request Forgery （CSRF 防护）

Spring Security 提供内置的 CSRF 防护机制，用于防止跨站请求伪造攻击。

## JWT

JWT，全称为 JSON Web Token，是一种用于安全地在不同系统之间传递信息的开放标准（RFC 7519），通常用于身份验证和授权。JWT 以紧凑的方式表示和传输信息，通常以字符串形式表示，并由三个部分组成，这三个部分之间用点号（.）分隔开来：

Header（头部）：包含了指定 JWT 使用的签名算法和令牌类型等信息，通常是一个 JSON 对象。例如：

``` json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

这个部分通常指定了签名算法，常见的算法包括 HS256（HMAC SHA-256）、RS256（RSA SHA-256）等。

Payload（负载）：包含了要传输的信息，也是一个 JSON 对象，可以包含用户的身份信息、权限、过期时间等自定义的声明。例如：

``` json
{
  "sub": "1234567890",
  "name": "John Doe",
  "exp": 1609459200
}
```

这个部分是 JWT 的主要内容，用于传递各种声明信息。

Signature（签名）：签名部分用于验证 JWT 的真实性和完整性。签名是通过将头部和负载部分进行签名，并使用密钥生成的。签名的目的是确保 JWT 在传输过程中没有被篡改。这部分使用指定的签名算法和密钥生成。例如：

``` scss
HMACSHA256(
  base64UrlEncode(header) + "." + 
  base64UrlEncode(payload),
  secret
)
```

JWT 的主要优势包括：

- 轻量级和紧凑：JWT 是一个紧凑的字符串，适合在网络上快速传输，不占用太多带宽。
- 无状态：JWT 本身包含了所有必要的信息，不需要在服务器端存储会话信息，因此可以支持分布式系统。
- 自包含：JWT 包含了所需的信息，因此可以减少服务器查询数据库的次数。
- 安全性：通过签名和密钥，可以确保 JWT 没有被篡改。

JWT 主要用于身份验证（Authentication）和授权（Authorization）。用户登录后，服务器可以生成一个 JWT 并返回给客户端，客户端将 JWT 存储，并在后续请求中将其包含在请求头中。服务器可以验证 JWT 的签名来验证用户的身份，并根据 JWT 中的声明信息来授权用户访问资源。

``` java
package whu.edu.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析和验证JWT令牌的工具类
 */
@Component
public class JwtTokenUtil {

    // token的过期时间
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    // token的密钥
    @Value("${jwt.secret}")
    private String secret;

    // 从Token中获得Claims
    public Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
    }

    // 生成Token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();//可以自由加入各种身份信息，如角色
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 对Token进行验证
    public Boolean validateToken(String token, UserDetails userDetails) {
        return validateClaim(getClaimFromToken(token), userDetails);
    }

    // 对Claims进行验证
    public boolean validateClaim(Claims claim, UserDetails userDetails) {
        //可以验证其他信息，如角色
        return userDetails != null &&
                claim.getSubject().equals(userDetails.getUsername())
                && !claim.getExpiration().before(new Date());
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean isTokenExpired(String token) {
        Claims claim = getClaimFromToken(token);
        return claim.getExpiration().before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }
}
```

JwtTokenUtil 类提供了一组方法，用于创建、验证和管理 JWT Token。它是一个通用的工具类，通常与 Spring Security 配合使用，用于实现身份验证和授权机制。使用 JWT 可以使应用程序实现无状态的身份验证，同时提供了一定的安全性。

1. @Component 注解：这个注解表明 JwtTokenUtil 类是一个 Spring 组件，会被 Spring IoC 容器管理。
2. JWT_TOKEN_VALIDITY 常量：定义了 JWT Token 的有效期，这里设置为 5 小时，以毫秒为单位。
3. secret 属性：通过 @Value("${jwt.secret}") 注解注入了一个密钥，这个密钥用于签名和验证 JWT Token。jwt.secret 是一个在配置文件中定义的属性。
4. getClaimFromToken 方法：这个方法用于从给定的 JWT Token 中解析出 Claims（声明）。Claims 包含了有关 Token 的信息，例如 Token 的主题、过期时间等。这个方法使用密钥来解析 Token，并返回包含声明信息的 Claims 对象。
5. generateToken 方法：这个方法用于生成 JWT Token。它接受一个 UserDetails 对象，通常包含了用户的详细信息（如用户名）。在生成 Token 时，它设置了一些声明，包括主题（通常是用户名）、Token 的颁发时间和过期时间，并使用密钥对 Token 进行签名。
6. validateToken 方法：用于验证给定的 JWT Token 是否有效。它接受一个 Token 和一个 UserDetails 对象作为参数。它首先从 Token 中解析出 Claims，然后与传入的 UserDetails 进行比较，确保主题一致且 Token 未过期。
7. validateClaim 方法：用于验证 Token 中的 Claims。这个方法可以根据需要自定义验证逻辑，目前示例中只验证了主题和 Token 是否过期。
8. canTokenBeRefreshed 方法：检查是否可以刷新 Token。通常，Token 在过期前一段时间内是可刷新的。这个方法检查 Token 是否已过期或是否在忽略过期的 Token 列表中。
9. isTokenExpired 方法：检查 Token 是否已过期，根据 Token 中的过期时间进行比较。
10. ignoreTokenExpiration 方法：这个方法用于指定哪些 Token 可以忽略过期。在示例中，它始终返回 false，即不忽略过期。

``` java
package whu.edu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whu.edu.entity.Role;
import whu.edu.entity.UserDto;
import whu.edu.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个UserDetailsService的Bean，从数据库中读取用户和权限信息
 */
@Service
public class DbUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
    }

    private static GrantedAuthority[] getAuthorities(UserDto user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            String[] authorityStrs = role.getAuthorities().split(",");
            for (String auth : authorityStrs) {
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }
        return authorities.toArray(new GrantedAuthority[authorities.size()]);
    }
}
```

在 Spring Security 中，UserDetailsService 是一个关键接口，它用于从应用程序的数据库或其他数据源中加载用户详细信息。通过自定义实现 UserDetailsService 接口的类（如 DbUserDetailService），可以将用户的身份验证和授权与应用程序的数据存储集成在一起，以实现安全访问控制。在上述示例中，DbUserDetailService 类负责将数据库中的用户信息转换为 Spring Security 可以理解的格式。

1. @Service 注解：这个注解表示 DbUserDetailService 类是一个 Spring 服务类，它会被 Spring IoC 容器管理。
2. UserDetailsService 接口：DbUserDetailService 类实现了 UserDetailsService 接口，该接口是 Spring Security 提供的用于加载用户详细信息的接口。
3. loadUserByUsername 方法：这个方法是 UserDetailsService 接口的实现方法，用于根据用户名从数据库中加载用户详细信息。具体步骤如下：
4. 通过 userService 调用 getUser(username) 方法来获取用户信息（假设 getUser(username) 方法返回一个 UserDto 对象）。
如果找不到具有给定用户名的用户，抛出 UsernameNotFoundException 异常。
如果找到用户，将用户信息包装成一个 Spring Security 的 User 对象，并返回。User 对象包括用户名、密码、授权信息（角色和权限等）。
getAuthorities 方法：这个私有方法用于从用户的角色中构建授权信息。它遍历用户拥有的角色，并将每个角色的权限字符串（通常是角色的名称）转换为 SimpleGrantedAuthority 对象，然后返回一个 GrantedAuthority 数组。

``` java
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
```

passwordEncoder.matches 方法接受两个参数：明文密码和已加密的密码（通常是数据库中存储的密码的哈希值或加密形式）。它的工作原理是将明文密码使用相同的加密算法和加密参数进行加密，然后将加密后的结果与已加密的密码进行比较。

``` java
package whu.edu.security;

import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response); //调用后续的过滤器
            return;
        }
        try {
            final String token = header.substring(7);
            //解析token，如果token不合法会抛出异常
            Claims claims = jwtTokenUtil.getClaimFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            if (userDetails != null && jwtTokenUtil.validateClaim(claims, userDetails)) {
                //创建一个身份令牌放入security context中，后面的过滤器可以使用
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            //解析token时产生的异常记入日志，不用直接抛出。因为后续的过滤器没有身份信息，会返回认证错误
            logger.warn(e);
        }
        chain.doFilter(request, response); //调用后续的过滤器
    }
}
```

JwtRequestFilter 是一个用于处理 JWT Token 验证和身份授权的 Spring Security 过滤器。它从请求头中提取 Token，解析 Token 并验证其合法性，然后将用户详细信息设置到 Spring Security 上下文中，以便后续的操作可以使用。如果 Token 无效或解析失败，它将继续执行后续的过滤器链，而不会中断请求处理。这有助于在请求中添加了无效 Token 时，能够记录错误并继续处理请求，而不会导致应用程序崩溃。

1. OncePerRequestFilter：JwtRequestFilter 类继承了 Spring Security 的 OncePerRequestFilter 类，这是一个过滤器基类，确保每个请求只会被过滤一次。
2. doFilterInternal 方法：这个方法是 OncePerRequestFilter 类的抽象方法，用于实际的过滤逻辑。它在每个传入的 HTTP 请求上执行。
   - 首先，它从请求的头部获取 Authorization 头部字段的值，该字段通常用于携带 JWT Token。如果没有 Authorization 头部或者头部不以 "Bearer " 开头，就继续执行后续的过滤器链。
   - 如果存在有效的 Authorization 头部，它会尝试提取 JWT Token，并将其传递给 jwtTokenUtil.getClaimFromToken(token) 方法来解析 Token。如果 Token 不合法，将会抛出异常，这时会捕获异常并记录到日志中，然后继续执行后续的过滤器链。
   - 如果 Token 合法，它会调用 userDetailsService.loadUserByUsername(claims.getSubject()) 方法来加载与 Token 主题（通常是用户名）对应的用户详细信息。
   - 如果用户详细信息存在并且 Token 合法，它会创建一个身份认证令牌 UsernamePasswordAuthenticationToken，并将用户详细信息、空的凭证和用户权限设置为令牌的一部分。然后，它将认证令牌设置到 Spring Security 上下文中，以供后续的安全操作使用。
   - 最后，无论成功还是失败，都会继续调用后续的过滤器链，以便继续处理请求。

``` java
package whu.edu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtRequestFilter jwtRequestFilter; //注入JWT过滤器Bean

    // 使用HttpSecurity来配置认证和授权
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();   //关闭csrf过滤器
        http.authorizeRequests()
                .antMatchers("/authenticate/login").permitAll() //login接口直接放行
                .anyRequest().authenticated()  //其他接口需要认证
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //添加jwtRequestFilter过滤器到UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // 密码加密的Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //BCrypt加密算法
    }
}
```

SecurityConfig 类配置了应用程序的安全性规则，包括允许匿名访问的接口、关闭 CSRF 保护、配置会话管理策略以及添加 JWT 过滤器来处理身份验证和授权。它还定义了一个密码加密的 Bean，用于对密码进行加密和验证。这些配置是 Spring Security 在应用程序中实现身份验证和授权所必需的。

- @EnableWebSecurity 注解：这个注解启用了 Spring Security，并配置了一些默认行为，如启用了基于表单的登录和HTTP Basic认证。
- @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) 注解：这个注解启用了方法级别的安全性，允许在方法上使用 @Secured 和 @PreAuthorize 等注解来进行权限控制。
- JwtRequestFilter Bean：这个 Bean 是一个自定义的 JWT 过滤器，用于处理 JWT Token 的验证和授权。在后面的代码中，它会被添加到 Spring Security 过滤器链中。
- securityFilterChain 方法：这个方法是用于配置 Spring Security 的 SecurityFilterChain 的 Bean。SecurityFilterChain 用于定义安全过滤器链的配置。
  - http.cors()：启用跨域资源共享 (CORS) 支持，允许跨域请求。
  - http.csrf().disable()：禁用 CSRF（跨站请求伪造）保护。在某些情况下，可能需要禁用 CSRF 保护，例如在前后端分离的应用程序中。
  - http.authorizeRequests()：配置请求的授权规则。
    - .antMatchers("/authenticate/login").permitAll()：允许所有用户访问 /authenticate/login 接口，即登录接口，无需身份验证。
    - .anyRequest().authenticated()：对于其他请求，要求用户进行身份验证，即需要登录才能访问。
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)：设置会话管理策略为 STATELESS，表示不使用会话来跟踪用户状态。这是一种常用于 RESTful API 的策略，因为 JWT Token 本身就包含了用户的身份信息，无需在服务端保存会话状态。
  - http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)：将自定义的 jwtRequestFilter 过滤器添加到 Spring Security 过滤器链中，并放置在 UsernamePasswordAuthenticationFilter 之前。这个过滤器用于处理 JWT Token 的验证和授权，以确保请求的合法性。
- passwordEncoder 方法：这个方法定义了一个 PasswordEncoder Bean，用于密码的加密和验证。在示例中，它使用了 BCryptPasswordEncoder，这是一种常用的密码加密算法，用于安全地存储和验证密码。
