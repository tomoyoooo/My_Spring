package org.tomoyo.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static Map<String, String> mp = new HashMap<>();

    static {
        mp.put("10001", "tomoyo");
        mp.put("10002", "doge");
        mp.put("10003", "kk");
    }

    public String queryUserName(String uId){
        return mp.get(uId);
    }
}
