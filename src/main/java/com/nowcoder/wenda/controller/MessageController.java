package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.Message;
import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.model.ViewObject;
import com.nowcoder.wenda.service.MessageService;
import com.nowcoder.wenda.service.UserService;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：私信相关的Controller请求
 *
 * @author huanxiong
 * @create 2018-08-24 14:30
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    // 发送站内信
    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName, @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "请您登录后再操作！");
            }
            User user = userService.selectByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString(1, "该用户不存在");
            }

            Message msg = new Message();
            msg.setContent(content);
            msg.setFromId(hostHolder.getUser().getId());
            msg.setToId(user.getId());
            msg.setCreatedDate(new Date());
            messageService.addMessage(msg);

            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error(" 增加站内信失败 " + e.getMessage());
            return WendaUtil.getJSONString(1, "插入站内信失败");
        }
    }

    // 个人私信列表
    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversation(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            List<Message> conversationList = messageService.getConversatoinList(localUserId, 0, 10);

            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user", user);
                vo.set("unread", messageService.getConversationUnreadCount(localUserId, msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);

        } catch (Exception e) {
            logger.error("获取站内信失败" + e.getMessage());
        }
        return "letter";
    }

    // 私信详情
    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(
            Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);

            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
                messageService.hasRead(msg.getId(), 1);
            }

            model.addAttribute("messages", messages);

        } catch (Exception e) {
            logger.error("获取详情消息失败 " + e.getMessage());
        }
        return "letterDetail";
    }
}
