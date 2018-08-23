package com.nowcoder.wenda.interceptor;

import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 描述：拦截器 -- 检测提交过来的用户是谁
 *
 * @author huanxiong
 * @create 2018-08-18 17:05
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (null != httpServletRequest.getCookies()) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) { // 如果找到了 “key” 为 “ticket” 的cookie
                    ticket = cookie.getValue(); // 将 cookie 的 “value” 赋值给 ticket
                    break;
                }
            }
        }

        if (null != ticket) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket); // 查询数据库并返回 LoginTicket 对象
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = userDao.selectById(loginTicket.getUserId());

            hostHolder.setUser(user);

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // 在所有的velocity模板中都可以访问user
        if (null != modelAndView) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 结束的时候清空拦截器
        hostHolder.clear();
    }
}
