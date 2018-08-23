package com.nowcoder.wenda.model;

import java.util.Date;

/**
 * 描述：question表对应的model
 *
 * @author huanxiong
 * @create 2018-08-14 16:38
 * <p>
 * <p>
 * DROP TABLE IF EXISTS `question`;
 * CREATE TABLE `question` (
 * `id` int(11) NOT NULL AUTO_INCREMENT,
 * `title` varchar(255) NOT NULL,
 * `content` text NULL,
 * `user_id` int(11) NOT NULL,
 * `created_date` datetime NOT NULL,
 * `comment_count` int(11) NOT NULL,
 * PRIMARY KEY (`id`),
 * INDEX `date_index` (`created_date` ASC)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
public class Question {
    private int id;
    private String title;
    private String content;
    private Date createdDate;
    private int userId;
    private int commentCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
