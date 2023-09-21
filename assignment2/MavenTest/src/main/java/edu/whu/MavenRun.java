package edu.whu;

import java.io.IOException;

public class MavenRun {

    public static void main(String[] args) {
        String property = "bootstrapClass";
        String path = "/myapp.properties";

        String className = null;
        try {
            className = Utils.readClassPath(path, property);
        } catch (NullPointerException | IOException e) {
            System.out.println("Load failure");
        }
        if (className == null) return;

        Class aClass = null;
        try {
            aClass = Utils.getClassClass(className);
        } catch (ClassNotFoundException e) {
            System.out.println("GetGlass failure");
        }
        if (aClass == null) return;

        Object object = null;
        try {
            object = Utils.createObject(aClass);
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("CreateObject failure");
        }
        if (object == null) return;

        Utils.runInitMethod(object, aClass);

        System.out.println("Finish");
    }
}
