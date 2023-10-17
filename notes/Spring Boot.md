# SpringBoot

Spring Boot是Spring的子项目，旨在简化Spring应用程序的构建和部署。
它提供了自动配置、嵌入式Web服务器等功能，使得快速开发和微服务架构更容易实现。

## Spring Boot 项目结构

src | main | resource |

优先级：application.properties 大于 application.yml

## SpringBoot 原理

Spring Boot 底层框架是 Spring Framework

### 起步依赖

依赖传递：pring-boot-starter-web 集成

### 自动配置

本模块：

外部依赖通过`@Bean` `@Component` 声明 Bean 对象交给 spring 管理，自动存放 IOC 容器，无需注册就可以 @Autowired 注入

第三方依赖：

1. `@ComponentSacn` 组件扫描
2. `@Import` 导入的类会被 spring 加载到IOC容器中

## 代码解释

`@Autowired`

Spring的@Autowired注解是一种用于自动装配（dependency injection）的注解，它允许你将一个或多个依赖注入到一个类中，而无需显式地创建这些依赖对象。@Autowired可以用在构造函数、方法、字段或者setter方法上，以告诉Spring容器在创建Bean的时候如何满足这些依赖。

下面是@Autowired注解的一些常见用法和解释：

1. 构造函数注入：

``` java
    @Autowired
    public MyClass(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
```

这种方式告诉Spring容器在创建MyClass实例时，自动注入一个类型为MyDependency的Bean。  
2. 方法注入：

``` java
    @Autowired
    public void setMyDependency(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
```

这种方式通过Setter方法实现依赖注入。  
3. 字段注入：

``` java
    @Autowired
    private MyDependency myDependency;
```

这种方式通过直接注入字段来实现依赖注入。需要注意的是，字段注入通常不是推荐的做法，因为它破坏了封装性，但在某些情况下可以使用。  
4. 方法参数注入：

``` java
    @Autowired
    public void myMethod(MyDependency myDependency) {
        // 使用myDependency
    }
```

这种方式在方法参数上使用@Autowired注解，Spring容器会在调用方法时自动传入相应的依赖。

---

在Spring中，虽然可以使用字段注入（Field Injection），也就是使用@Autowired注解直接在类字段上注入依赖，但是很多开发者和Spring社区并不推荐这种方式，而更倾向于使用构造函数注入或方法注入。以下是一些原因：

1. 封装性问题： 字段注入会破坏类的封装性，因为它使字段变成了公共的，并且不需要通过构造函数或者Setter方法来设置依赖。这可能会导致类的内部状态被外部直接修改，增加了代码的复杂性和维护难度。

2. 难以进行单元测试： 使用字段注入的类通常难以进行单元测试，因为无法通过构造函数传递模拟或假的依赖对象，也不能轻松地模拟或替换字段上的依赖。相比之下，使用构造函数注入或方法注入的方式更容易进行单元测试，因为你可以在测试中传入模拟的依赖对象。

3. 循环依赖问题： 字段注入可能会引发循环依赖问题，尤其是在复杂的依赖关系中。如果A类依赖于B类，而B类又依赖于A类，这种情况下，字段注入可能导致Spring无法解决循环依赖，从而导致应用程序启动失败。

4. 可读性差： 字段注入使得类的依赖关系不够明显。当你阅读代码时，无法立即看出哪些字段是依赖注入的，哪些是内部状态。而构造函数注入或方法注入会显式地列出所有依赖，使代码更加清晰可读。

`@TableField(exist = false)`标注POJO类对应的字段不存在数据库中

---

`Controller`与`RestController`

Controller 和 RestController 是在Spring Framework中用于处理HTTP请求的两种关键注解，它们用于定义Web应用程序中的控制器类。它们的主要区别在于返回结果的方式和用途：

Controller 注解用于定义一个普通的Spring MVC控制器类。
控制器类的方法通常返回视图名称，这些视图名称将由视图解析器解析为实际的视图页面。
适用于传统的Web应用程序，其中页面渲染由服务器端处理，控制器方法返回的是一个视图模型，其中包含了要呈现给用户的数据以及要使用的视图名称。

RestController 注解用于定义一个控制器类，其中的方法返回的是数据对象，而不是视图名称。
控制器方法的返回值会被自动序列化为JSON或XML等数据格式，并发送给客户端，通常用于构建RESTful API。
适用于构建基于REST风格的Web服务，客户端通过HTTP请求来获取和操作数据，而不是HTML页面。

## Problem

### 为什么Controller层注入的是Service接口，而不是ServiceImpl实现类

需要注意的是，Spring Boot是在ServiceImpl上添加的`@Service`注解，将实现类注入到容器中，只是Controller层使用接口接收，注入的是实现类对象，接收的接口；理解为多态；（Controller–Service–ServiceImpt–Mapper）

1. 松耦合：通过将Service接口注入到Controller层，Controller不需要关心Service的具体实现细节。这意味着可以轻松地更改Service的实现类，而无需修改Controller层的代码。这降低了代码之间的耦合度，使系统更加灵活和容易维护。

2. 依赖倒置：依赖倒置原则要求高层模块不应该依赖于低层模块，两者都应该依赖于抽象。通过注入Service接口而不是实现类，Controller层依赖于Service接口的抽象而不是具体的实现。这使得系统更加可扩展，可以轻松添加新的Service实现，而无需更改Controller层。

3. 可测试性：将Service接口注入到Controller层使得单元测试更容易。您可以使用测试框架轻松地创建模拟或伪造的Service接口实现，以便测试Controller的行为，而无需启动整个Spring容器或与数据库进行互动。

4. 接口分离原则：遵循接口分离原则，一个类不应该强制实现它不使用的方法。如果Controller层直接依赖于Service实现类，那么它可能会受到Service实现类中不相关方法的影响。通过依赖于接口，Controller只关心与业务逻辑相关的方法。
