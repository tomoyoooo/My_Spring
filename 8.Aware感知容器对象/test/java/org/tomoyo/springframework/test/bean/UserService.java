package org.tomoyo.springframework.test.bean;

import org.tomoyo.springframework.beans.factory.*;
import org.tomoyo.springframework.context.ApplicationContext;
import org.tomoyo.springframework.context.ApplicationContextAware;

public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private UserDao userDao;
    private String uId;
    private String company;
    private String location;


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader is: " + classLoader);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanName is: " + name);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String queryUserInfo(){
        return userDao.queryUserName(uId) + ", 公司：" + company + "， 地点：" + location;
    }

//    @Override
//    public void destroy() throws Exception {
//        System.out.println("UserService销毁");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("UserService初始化");
//    }
}
