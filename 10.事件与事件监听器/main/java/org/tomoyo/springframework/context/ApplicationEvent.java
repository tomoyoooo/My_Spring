package org.tomoyo.springframework.context;

import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {

    /**
     * 创建一个prototypical Event
     * @param source
     */
    public ApplicationEvent(Object source){
        super(source);
    }
}
