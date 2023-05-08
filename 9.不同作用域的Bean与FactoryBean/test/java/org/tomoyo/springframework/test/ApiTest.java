package org.tomoyo.springframework.test;

import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;
import org.tomoyo.springframework.beans.PropertyValue;
import org.tomoyo.springframework.beans.PropertyValues;
import org.tomoyo.springframework.beans.factory.config.BeanDefinition;
import org.tomoyo.springframework.beans.factory.config.BeanReference;
import org.tomoyo.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.tomoyo.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.tomoyo.springframework.context.support.ClassPathXmlApplicationContext;
import org.tomoyo.springframework.core.io.DefaultResourceLoader;
import org.tomoyo.springframework.core.io.Resource;
import org.tomoyo.springframework.test.bean.UserDao;
import org.tomoyo.springframework.test.bean.UserService;
import org.tomoyo.springframework.test.event.CustomEvent;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class ApiTest {

    private DefaultResourceLoader resourceLoader;

    @Before
    public void init(){
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws IOException{
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException{
        Resource resource = resourceLoader.getResource("src/test/java/org/tomoyo/springframework/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException{
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/blob/main/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_event(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }


    @Test
    public void test_xml(){
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//
//        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//        reader.loadBeanDefinitions("classpath:spring.xml");
//
//        UserService userService = beanFactory.getBean("userService", UserService.class);
//        String result = userService.queryUserInfo();
//        System.out.println("测试结果：" + result);
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService1 = applicationContext.getBean("userService", UserService.class);
        UserService userService2 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService1);
        System.out.println(userService2);

        String result = userService1.queryUserInfo();
        System.out.println(result);
        System.out.println("ApplicationContextAware："+userService1.getApplicationContext());
        System.out.println("BeanFactoryAware："+userService1.getBeanFactory());
    }

    @Test
    public void test_hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("close！")));
    }
}
