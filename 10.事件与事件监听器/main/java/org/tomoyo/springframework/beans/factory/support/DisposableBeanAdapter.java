package org.tomoyo.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.tomoyo.springframework.beans.BeansException;
import org.tomoyo.springframework.beans.factory.DisposableBean;
import org.tomoyo.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition){
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        //实现DisposableBean接口
        if(bean instanceof DisposableBean){
            System.out.println("实现DisposableBean接口");
            ((DisposableBean) bean).destroy();
        }

        //配置destroy-method信息并执行
        if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            System.out.println("未实现DisposableBean接口");
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if(null == destroyMethod){
                throw new BeansException("Could not find a destroy method named '" + destroyMethodName + "' on bean with name '"+ beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
