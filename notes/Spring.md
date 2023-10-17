# Spring

## IoC（控制反转）容器

Spring的核心概念之一，它管理和控制应用程序中的组件。
通过IoC容器，你可以将对象的创建和依赖注入交给Spring来管理，而不是在代码中直接实例化对象。

## 依赖注入（DI）

DI是IoC的一部分，它是指将组件所需的依赖关系通过配置或注解的方式注入到组件中。
Spring支持构造函数注入、属性注入和方法注入等多种方式来实现依赖注入。

## Spring配置

Spring可以使用XML配置文件、Java配置类或注解来配置应用程序的组件和依赖关系。
了解如何编写和使用这些配置方式是非常重要的。

## Bean

在Spring中，组件通常被称为"Bean"。
Spring管理这些Bean的生命周期和依赖注入，确保它们按照需要被创建和销毁。

bean    标签：告诉 Spring 要创建的对象
id:     对象的唯一标识，就像每个人的身份证一样，不可重复
class:  bean 的完全限定名，即从 package name 到 class name
property：  给属性赋值，name 的名称取决于 set 方法对应的名称 ref 依赖的 bean id

### 生命周期

scope="singleton" 默认  单例模式
scope="prototype"

init-method
destroy-method

## AOP（面向切面编程）

AOP是Spring的一个重要模块，用于处理跨多个组件的横切关注点，如日志记录、事务管理等。
Spring AOP基于代理模式来实现横切关注点的处理。

## Spring MVC

Spring MVC是Spring框架中用于构建Web应用程序的模块，它提供了一种基于MVC（模型-视图-控制器）的方式来组织和处理Web请求。
