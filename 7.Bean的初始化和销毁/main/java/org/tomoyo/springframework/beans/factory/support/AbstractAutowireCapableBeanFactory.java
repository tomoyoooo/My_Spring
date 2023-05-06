package org.tomoyo.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.tomoyo.springframework.beans.factory.DisposableBean;
import org.tomoyo.springframework.beans.factory.InitializingBean;
import org.tomoyo.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.tomoyo.springframework.beans.factory.config.BeanPostProcessor;
import org.tomoyo.springframework.beans.factory.config.BeanReference;
import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.PropertyValue;
import org.tomoyo.springframework.beans.PropertyValues;
import org.tomoyo.springframework.beans.factory.config.BeanDefinition;
import cn.hutool.core.bean.BeanUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.PriorityQueue;

//实现了bean的实例化操作
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try{
            //bean = beanDefinition.getBeanClass().newInstance();
            bean = createBeanInstance(beanDefinition, beanName, args);
            applyPropertyValues(beanName, bean, beanDefinition);
            bean = initializeBean(beanName, bean, beanDefinition);

        } catch (Exception e){
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        addSingleton(beanName, bean);
        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition){
        if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args){
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for(Constructor ctor : declaredConstructors){
            if(null != args && ctor.getParameterTypes().length == args.length){
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * Bean属性填充
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        try{
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for(PropertyValue pv : propertyValues.getPropertyValues()){
                String name = pv.getName();
                Object value = pv.getValue();
                if(value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
                //Field field = bean.getClass().getDeclaredField(name);
                //field.setAccessible(true);
                //field.set(name, value);
            }
        } catch (Exception e){
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    public InstantiationStrategy getInstantiationStrategy(){
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy){
        this.instantiationStrategy = instantiationStrategy;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition){
        //1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //2. 执行初始化
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e){
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        //3. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception{
        //实现InitializingBean接口
        if(bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //配置init-method信息并执行
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if(null == initMethod){
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '"+ beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor : getBeanPostProcessors()){
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if(null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor : getBeanPostProcessors()){
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if(null == current) return result;
            result = current;
        }
        return result;
    }
}
