package edu.whu.pojo;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

    private String id;

    private String className;

    private List<Property> properties = new ArrayList<>();

    public BeanDefinition() {
    }

    public BeanDefinition(String id, String className, List<Property> properties) {
        this.id = id;
        this.className = className;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
