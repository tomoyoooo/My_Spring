package org.tomoyo.springframework.context.event;

import org.tomoyo.springframework.context.ApplicationContext;
import org.tomoyo.springframework.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {


    /**
     * 创建一个prototypical Event
     * @param source
     */
    public ApplicationContextEvent(Object source){
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }

}
