package org.tomoyo.springframework.test.common;

import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.factory.config.BeanPostProcessor;
import org.tomoyo.springframework.test.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            UserService userService = (UserService) bean;
            userService.setLocation("修改为北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "来了初始化后的处理方法");
        if("userService".equals(beanName)){
            UserService userService = (UserService) bean;
            userService.setLocation("修改为上海");
        }
        return bean;
    }
}
