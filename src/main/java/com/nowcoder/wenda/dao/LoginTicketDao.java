package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * 描述：登陆状态的dao
 *
 * @author huanxiong
 * @create 2018-08-18 15:26
 */
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, " ) values (#{userId}, #{ticket}, #{expired}, #{status})"})
    int addTicket(LoginTicket ticket);

    // 用户登录的时候查询登陆状态
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket selectByTicket(String ticket);

    // 用户登出的时候更新状态
    @Update({"update ", TABLE_NAME, " set status = #{status} where ticket = #{ticket}"})
    void updateStatus(
            @Param("ticket") String ticket, @Param("status") int status
    );

}
