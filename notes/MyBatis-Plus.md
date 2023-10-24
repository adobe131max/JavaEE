# MyBatis-Plus

## Maven坐标

``` xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.29</version>
</dependency>
```

## yaml配置

``` yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test_db
    username: root
    password: 123456
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    global-config:
      db-config:
        # 主键生成策略
        id-type: auto
  # 配置类型别名对应的包
  type-aliases-package: whu.edu.springbootdemo.entity
```

## Service Mapper

``` java
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
}

public interface CommodityService extends IService<Commodity> {
}

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {
}
```

## 功能

### Page 分页查询

### 条件构造器

QueryWrapper LambdaQueryWrapper

### 字段自动填充

`@TableField`

### 逻辑删除

`@TableLogic`

### 乐观锁

`@Version`

### 代码生成器
