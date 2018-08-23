package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.QuestionDao;
import com.nowcoder.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 描述：
 *
 * @author huanxiong
 * @create 2018-08-17 20:23
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;

    // public Question getById(int id) {
    //     return questionDao.getById(id);
    // }

    // 新增问题
    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    // // 修改问题
    // public int updateCommentCount(int id, int count) {
    //     return questionDao.updateComment(id, count);
    // }
}
