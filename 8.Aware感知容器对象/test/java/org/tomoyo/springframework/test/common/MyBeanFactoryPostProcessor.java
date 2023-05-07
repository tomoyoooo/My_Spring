package org.tomoyo.springframework.test.common;

import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.PropertyValue;
import org.tomoyo.springframework.beans.PropertyValues;
import org.tomoyo.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.tomoyo.springframework.beans.factory.config.BeanDefinition;
import org.tomoyo.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "修改为字节跳动"));
    }
}
