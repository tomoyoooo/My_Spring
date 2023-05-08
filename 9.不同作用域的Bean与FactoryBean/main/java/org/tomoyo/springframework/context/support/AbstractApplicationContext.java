package org.tomoyo.springframework.context.support;

import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.tomoyo.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.tomoyo.springframework.beans.factory.config.BeanPostProcessor;
import org.tomoyo.springframework.context.ApplicationEvent;
import org.tomoyo.springframework.context.ApplicationListener;
import org.tomoyo.springframework.context.ConfigurableApplicationContext;
import org.tomoyo.springframework.context.event.ApplicationEventMulticaster;
import org.tomoyo.springframework.context.event.ContextClosedEvent;
import org.tomoyo.springframework.context.event.ContextRefreshedEvent;
import org.tomoyo.springframework.context.event.SimpleApplicationEventMulticaster;
import org.tomoyo.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        //1. 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();

        //2. 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3. 添加ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //4. 在Bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //5. BeanPostProcessor需要提前于其他Bean对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //6. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }


    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {

        getBeanFactory().destroySingletons();
    }
}
