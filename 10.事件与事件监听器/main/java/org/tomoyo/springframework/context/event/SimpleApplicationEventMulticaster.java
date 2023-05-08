package org.tomoyo.springframework.context.event;

import org.tomoyo.springframework.beans.factory.BeanFactory;
import org.tomoyo.springframework.context.ApplicationEvent;
import org.tomoyo.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory){
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for(final ApplicationListener listener : getApplicationListeners(event)){
            listener.onApplicationEvent(event);
        }
    }
}
