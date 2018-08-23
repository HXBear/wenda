package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 描述：用户登录和注册的Controller
 *
 * @author huanxiong
 * @create 2018-08-18 9:36
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    // 注册页面的处理
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("next") String next,
            @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
            HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

    // 登录注册的页面
    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String regloginPage(
            Model model,
            @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }

    // 登录页面的处理
    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String login(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "next", required = false) String next,
            @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
            HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("smg"));
                return "login";
            }

        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return "login";
        }
    }

    // 登录退出功能
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

}
