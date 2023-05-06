package org.tomoyo.springframework.beans.factory.config;

public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void destroySingletons();
}
