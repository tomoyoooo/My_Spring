package org.tomoyo.springframework.beans.factory;

import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.tomoyo.springframework.beans.factory.config.BeanDefinition;
import org.tomoyo.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;
}
