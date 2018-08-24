package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：QuestionDao的dao层
 *
 * @author huanxiong
 * @create 2018-08-16 21:07
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({
            "insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title}, #{content}, #{createdDate}, #{userId}, #{commentCount})"
    })
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(
            @Param("userId") int id,
            @Param("offset") int offset,
            @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    Question getById(int id);

    @Update({"update ", TABLE_NAME, " set comment_count = #{count} where id = #{entityId}"})
    int updateComment(@Param("entityId") int entityId, @Param("count") int count);
}
