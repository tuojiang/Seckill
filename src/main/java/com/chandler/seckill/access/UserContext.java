package com.chandler.seckill.access;

import com.chandler.seckill.domain.SeckillUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date: 18-12-6
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */

public class UserContext {
    private static ThreadLocal<SeckillUser> userHolder = new ThreadLocal<>();

    public static SeckillUser getUser() {
        return userHolder.get();
    }

    public static void setUser(SeckillUser user) {
        userHolder.set(user);
    }
}
