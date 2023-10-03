package edu.whu;

import edu.whu.pojo.BeanDefinition;
import edu.whu.pojo.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiniApplicationContext {

    private Map<String, Object> beans = new HashMap<>();

    // 初始化 bean
    public MiniApplicationContext(String xmlPath) {
        // 使用Dom4j解析XML
        SAXReader reader = new SAXReader();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(xmlPath);
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            System.out.println("读取xml失败");
            e.printStackTrace();
        }
        assert document != null;
        Element root = document.getRootElement();

        // 解析XML，获取Bean定义信息
        List<Element> beanElements = root.elements("bean");
        for (Element beanElement : beanElements) {
            String id = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);

            List<Element> propertyElements = beanElement.elements("property");
            for (Element propertyElement : propertyElements) {
                Property property = new Property();
                property.setName(propertyElement.attributeValue("name"));
                property.setValue(propertyElement.attributeValue("value"));
                property.setRef(propertyElement.attributeValue("ref"));
                beanDefinition.getProperties().add(property);
            }

            // 使用反射创建Bean并注入属性
            Object bean = null;
            try {
                bean = createBean(beanDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            beans.put(id, bean);
        }
    }

    // 根据 beanDefinition 创建 bean
    public Object createBean(BeanDefinition beanDefinition) throws Exception {
        String className = beanDefinition.getClassName();
        Class<?> clazz = Class.forName(className);
        Object bean = clazz.newInstance();

        for (Property property : beanDefinition.getProperties()) {
            Field field = clazz.getDeclaredField(property.getName());
            field.setAccessible(true);
            if (property.getValue() != null) {
                field.set(bean, property.getValue());
            } else if (property.getRef() != null) {
                Object refBean = beans.get(property.getRef());
                field.set(bean, refBean);
            }
        }

        return bean;
    }

    public Object getBean(String id) {
        return beans.get(id);
    }
}
