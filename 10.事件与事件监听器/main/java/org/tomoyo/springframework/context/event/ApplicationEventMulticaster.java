package org.tomoyo.springframework.context.event;

import org.tomoyo.springframework.context.ApplicationListener;
import org.tomoyo.springframework.context.ApplicationEvent;

public interface ApplicationEventMulticaster {

    /**
     * 添加一个监听器通知所有事件
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除一个监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播应用事件给对应的监听器
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
