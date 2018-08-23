package com.nowcoder.wenda.model;

import org.springframework.stereotype.Component;

/**
 * 描述：存放取出来的用户
 *
 * @author huanxiong
 * @create 2018-08-18 17:52
 */
@Component
public class HostHolder {
    // 为每一个线程都分配一个user对象
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser (User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
