package org.tomoyo.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static Map<String, String> mp = new HashMap<>();

    public void initDataMethod(){
        System.out.println("执行：init-method");
        mp.put("10001", "小傅哥");
        mp.put("10002", "八杯水");
        mp.put("10003", "阿毛");
    }

    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        mp.clear();
    }



    public String queryUserName(String uId){
        return mp.get(uId);
    }
}
