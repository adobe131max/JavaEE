package edu.whu;

public class MyClass {

    public void no() {
        System.out.println("no @InitMethod");
    }

    @InitMethod
    public void init() {
        System.out.println("@InitMethod");
    }
}
