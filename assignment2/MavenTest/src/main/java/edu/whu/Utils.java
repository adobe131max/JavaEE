package edu.whu;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Utils {

    // 反射调用有 @InitMethod 注解的方法
    public static void runInitMethod(Object object, Class aClass) {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                try {
                    method.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Run failure");
                }
            }
        }
    }

    // 反射创建实例对象
    public static Object createObject(Class aClass) throws InstantiationException, IllegalAccessException {
        return aClass.newInstance();
    }

    // 获取 Class
    public static Class getClassClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    // 读取属性文件
    public static String readClassPath(String path, String property) throws NullPointerException, IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = MavenRun.class.getResourceAsStream(path)) {
            properties.load(inputStream);
            return properties.getProperty(property);
        }
    }
}
