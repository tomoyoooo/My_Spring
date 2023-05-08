package org.tomoyo.springframework.context;

import org.tomoyo.springframework.beans.factory.HierarchicalBeanFactory;
import org.tomoyo.springframework.beans.factory.ListableBeanFactory;
import org.tomoyo.springframework.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
