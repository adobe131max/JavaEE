# Spring

## IOC 控制反转

Beans

bean    标签：告诉 Spring 要创建的对象
id:     对象的唯一标识，就像每个人的身份证一样，不可重复
class:  bean 的完全限定名，即从 package name 到 class name
property：  给属性赋值，name 的名称取决于 set 方法对应的名称 ref 依赖的 bean id

### 生命周期

scope="singleton" 默认  单例模式
scope="prototype"

init-method
destroy-method
