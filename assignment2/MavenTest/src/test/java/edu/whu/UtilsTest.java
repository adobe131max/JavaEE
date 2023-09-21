package edu.whu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTest {

    // 创建对象失败
    @Test
    public void testCrateObject() {
        assertThrows(NullPointerException.class, () -> {
            Utils.createObject(null);
        });
    }

    // 类不存在
    @Test
    public void testGetClassClass() {
        assertThrows(ClassNotFoundException.class, () -> {
            Utils.getClassClass("edu.hust.MyClass");
        });
    }

    // 属性文件属性不存在
    @Test
    public void testReadClassPath() {
        assertThrows(NullPointerException.class, () -> {
            Utils.readClassPath("/bootstrap.properties", "applicationProperty");
        });
    }

    @Test
    @InitMethod
    public void testInitMethod() {
        System.out.println("Run InitMethod");
    }
}
