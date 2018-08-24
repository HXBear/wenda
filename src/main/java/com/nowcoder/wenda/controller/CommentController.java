package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.Comment;
import com.nowcoder.wenda.model.EntityType;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.service.CommentService;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.service.SensitiveService;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * 描述：comment的实现
 *
 * @author huanxiong
 * @create 2018-08-23 21:37
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    // 添加评论的方法
    @RequestMapping(value = "/addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId, @RequestParam("content") String content) {

        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);

            // 过滤content
            Comment comment = new Comment();

            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }

            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);

            // 更新题目里面的数量

            // 怎么异步优化

        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/question/" + String.valueOf(questionId);
    }
}
