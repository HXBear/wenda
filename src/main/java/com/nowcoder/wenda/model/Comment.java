package com.nowcoder.wenda.model;

import java.util.Date;

/**
 * 描述：comment表对应的model
 *
 * @author huanxiong
 * @create 2018-08-23 20:53
 * <p>
 * DROP TABLE IF EXISTS `comment`;
 * CREATE TABLE `comment` (
 * `id` INT NOT NULL AUTO_INCREMENT,
 * `content` TEXT NOT NULL comment '评论的内容',
 * `user_id` INT NOT NULL comment '评论用户的id',
 * `entity_id` INT NOT NULL comment 'question问题对应的id',
 * `entity_type` INT NOT NULL comment '实体的类型，这里只能是question类型，即数字1',
 * `created_date` DATETIME NOT NULL comment '建表日期',
 * `status` INT NOT NULL DEFAULT 0 comment '失效的状态，0表示没有失效',
 * PRIMARY KEY (`id`),
 * INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class Comment {
    private int id;
    private int userId;
    private int entityId;
    private int entityType;
    private String content;
    private Date createdDate;
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

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
