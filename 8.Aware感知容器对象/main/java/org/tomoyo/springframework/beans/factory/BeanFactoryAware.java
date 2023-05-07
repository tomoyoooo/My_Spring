package org.tomoyo.springframework.beans.factory;

import org.tomoyo.springframework.beans.BeansException;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
