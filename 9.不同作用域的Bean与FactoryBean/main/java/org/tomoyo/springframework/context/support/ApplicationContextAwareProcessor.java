package org.tomoyo.springframework.context.support;

import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.context.ApplicationContextAware;
import org.tomoyo.springframework.beans.factory.config.BeanPostProcessor;
import org.tomoyo.springframework.context.ApplicationContext;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
