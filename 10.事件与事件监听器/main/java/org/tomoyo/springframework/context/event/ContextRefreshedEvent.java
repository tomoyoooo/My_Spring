package org.tomoyo.springframework.context.event;

public class ContextRefreshedEvent extends ApplicationContextEvent{

    /**
     * 创建一个prototypical Event
     * @param source
     */
    public ContextRefreshedEvent(Object source){
        super(source);
    }
}
