package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.Question;
import com.nowcoder.wenda.model.ViewObject;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author huanxiong
 * @create 2018-08-16 18:17
 */
@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    // 提取处理公共部分
    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestion(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();

        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    // 问答网首页的搭建
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(
            Model model, @RequestParam(value = "pop", defaultValue = "0") int pop
    ) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

    // 问答网用户的搭建
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(
            Model model, @PathVariable("userId") int userId
    ) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "index";
    }
}
