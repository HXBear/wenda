package com.nowcoder.wenda.model;

import java.util.Date;

/**
 * 描述：用户登录状态保存的表login_ticket
 *
 * @author huanxiong
 * @create 2018-08-18 15:20
 * <p>
 * DROP TABLE IF EXISTS `login_ticket`;
 * CREATE TABLE `login_ticket` (
 * `id` INT NOT NULL AUTO_INCREMENT,
 * `user_id` INT NOT NULL,
 * `ticket` VARCHAR(45) NOT NULL,
 * `expired` DATETIME NOT NULL,
 * `status` INT NULL DEFAULT 0,
 * PRIMARY KEY (`id`),
 * UNIQUE INDEX `ticket_UNIQUE` (`ticket` ASC)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private Date expired;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
