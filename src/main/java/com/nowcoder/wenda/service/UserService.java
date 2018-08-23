package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述：
 *
 * @author huanxiong
 * @create 2018-08-16 20:19
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    // 查询用户
    public User getUser(int id) {
        return userDao.selectById(id);
    }

    // 注册服务
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);

        if (null != user) {
            map.put("msg", "用户名已存在");
            return map;
        }

        // 密码强度检测
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5)); // 截取随机的五位为salt
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);

        // 注册完毕自动就登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    // 登录服务
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
        }

        User user = userDao.selectByName(username);

        if (null == user) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "您输入的密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket); // 传过去，因为要下发给浏览器

        return map;
    }

    // 增加一个Ticket
    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);

        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24); // 1000毫秒 = 1秒 即一天之后过期
        ticket.setExpired(date); // 设置过期时间
        ticket.setStatus(0); // 0表示激活
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", "")); // 生成随机的ticket

        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    // 登出服务
    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1); // 修改数据库状态码 status
    }


}
