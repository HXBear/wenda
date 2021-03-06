package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;

/**
 * 描述：数据库的增删改查操作
 *
 * @author huanxiong
 * @create 2018-08-14 16:07
 */
@Mapper
public interface UserDao {
    // 定义user的基本变量
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    // 插入操作
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") " +
            "values(#{name}, #{password}, #{salt}, #{headUrl})"})
    int addUser(User user);

    // 查找
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name = #{name}"})
    User selectByName(String name);

    // 更新操作
    @Update({"update ", TABLE_NAME, " set password = #{password} where id = #{id}"})
    void updatePassword(User user);

    // 删除操作
    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
