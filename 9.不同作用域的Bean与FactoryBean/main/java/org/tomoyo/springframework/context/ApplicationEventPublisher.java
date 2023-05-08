package org.tomoyo.springframework.context;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
