package org.tomoyo.springframework.context;

import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.factory.Aware;
import org.tomoyo.springframework.context.ApplicationContext;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
