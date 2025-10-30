package com.enterprisex.wallsofwonder.oms;

import com.enterprisex.wallsofwonder.oms.ServiceImpl.UserDetail;

public class UserContext {

    private static final ThreadLocal<UserDetail> userHolder = new ThreadLocal<>();

    public static void setUser(UserDetail user) {
        userHolder.set(user);
    }

    public static UserDetail getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}