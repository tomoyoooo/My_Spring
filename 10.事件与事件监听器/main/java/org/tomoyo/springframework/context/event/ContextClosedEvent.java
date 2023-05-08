package org.tomoyo.springframework.context.event;

public class ContextClosedEvent extends ApplicationContextEvent{

    /**
     * 创建一个prototypical Event
     * @param source
     */
    public ContextClosedEvent(Object source){
        super(source);
    }
}
