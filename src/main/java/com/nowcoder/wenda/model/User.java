package com.nowcoder.wenda.model;

/**
 * 描述：User表对应的model
 *
 * @author huanxiong
 * @create 2018-08-13 21:14
 *
 * <p>
 * DROP TABLE IF EXISTS `user`;
 * CREATE TABLE `user` (
 * `id` int unsigned not null auto_increment,
 * `name` varchar(64) not null default '',
 * `password` varchar(128) not null default '',
 * `salt` varchar(32) not null default '',
 * `head_url` varchar(256) null default '',
 * primary key (`id`),
 * unique key `name` (`name`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;

    public User() {

    }

    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return "Hello, my name is " + name;
    }
}
