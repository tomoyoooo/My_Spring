package org.tomoyo.springframework.test.bean;

import org.tomoyo.springframework.beans.factory.DisposableBean;
import org.tomoyo.springframework.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {
    private UserDao userDao;

    private String uId;

    private String company;

    private String location;

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

    @Override
    public void destroy() throws Exception {
        System.out.println("UserService销毁");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserService的afterPropertySet");
    }
}
