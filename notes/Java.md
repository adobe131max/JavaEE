# Java

Java参考: <https://www.bookstack.cn/read/liaoxuefeng-java/3576e343e8542103.md>

## OOP

### 类和对象

### 多态

在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被称为覆写（Override）

Java的实例方法调用是基于运行时的实际类型的动态调用，而非变量的声明类型。这个非常重要的特性在面向对象编程中称之为多态

### final

`final`修饰的类不能被继承，`final`修饰的方法不能被`Override`，`final`修饰的字段在初始化后不能被修改

### 抽象类

如果一个class定义了方法，但没有具体执行代码，这个方法就是抽象方法，抽象方法用`abstract`修饰。

因为无法执行抽象方法，因此这个类也必须申明为抽象类（abstract class）。

使用`abstract`修饰的类就是抽象类。无法实例化一个抽象类

抽象类本身被设计成只能用于被继承，因此，抽象类可以强迫子类实现其定义的抽象方法，否则编译会报错。因此，抽象方法实际上相当于定义了“规范”

### 接口

如果一个抽象类没有字段，所有方法全部都是抽象方法，就可以把该抽象类改写为接口：`interface`

``` java
interface Person {
    void run();
    String getName();
}
```

接口定义的所有方法默认都是`public abstract`的，所以这两个修饰符不需要写出来（写不写效果都一样）

当一个具体的class去实现一个`interface`时，需要使用`implements`关键字

在接口中，可以定义`default`方法，实现类可以不必覆写`default`方法。`default`方法的目的是，当我们需要给接口新增一个方法时，会涉及到修改全部子类。如果新增的是`default`方法，那么子类就不必全部修改，只需要在需要覆写的地方去覆写新增方法

### 静态字段和静态方法

在class中定义的字段，称为实例字段。实例字段的特点是每个实例都有独立的字段，各个实例的同名字段互不影响。还有一种字段，是用`static`修饰的字段，称为静态字段

用`static`修饰的方法称为静态方法

静态字段和静态方法通过类名调用

#### 枚举

enum是一个class，每个枚举的值都是class实例

## 异常

try包裹的内容在发生异常时，在发生异常的语句前面执行的内容不会恢复

## 反射

类名为Class的class，每加载一种class，JVM就会为其创建一个Class类型的实例，每个class的Class实例都是唯一的

Java反射机制的核心是在程序运行时动态加载类并获取类的详细信息，从而操作类或对象的属性和方法。本质是JVM得到class对象之后，再通过class对象进行反编译，从而获取对象的各种信息。

Java属于先编译再运行的语言，程序中对象的类型在编译期就确定下来了，而当程序在运行时可能需要动态加载某些类，这些类因为之前用不到，所以没有被加载到JVM。通过反射，可以在运行时动态地创建对象并调用其属性，不需要提前在编译期知道运行的对象是谁。

我们编译时知道类或对象的具体信息，此时直接对类和对象进行操作即可，无需使用反射（reflection）

如果编译不知道类或对象的具体信息，此时应该如何做呢？这时就要用到 反射 来实现。比如类的名称放在XML文件中，属性和属性值放在XML文件中，需要在运行时读取XML文件，动态获取类的信息

访问class的Class的实例：

1. 通过class的静态变量class获取：`Class cls = String.class`
2. 通过实例的`getClass()`方法
3. 通过静态方法全类名访问：`Class.forName("java.lang.String")`

## 注解

## 集合

## IO

### Stream

try后面的括号中可以写多行语句，会自动关闭括号中的资源（前提是这个资源实现了AutoCloseable接口的类）

``` java
try (InputStream inputStream = new FileInputStream(file)) {
    // todos ...
} catch {
    // ...
}
```

#### instanceof

instanceof是Java中的二元运算符，左边是对象，右边是类；当对象是右边类或子类所创建对象时，返回true；否则，返回false

左边应该可以子类，null为false

## 单元测试

## ELSE

### classpath

### 序列化

序列化是指把一个Java对象变成二进制内容，本质上就是一个byte[]数组

一个Java对象要能序列化，必须实现java.io.Serializable接口，Serializable是一个空接口，本身没有实际意义，只需要implements
