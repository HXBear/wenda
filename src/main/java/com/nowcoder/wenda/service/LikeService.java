package com.nowcoder.wenda.service;

import com.nowcoder.wenda.util.JedisAdapter;
import com.nowcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：所有点赞和点踩的service
 *
 * @author huanxiong
 * @create 2018-08-26 20:16
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    // 获得点赞数目
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    // 获得用户喜欢的状态
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        // 如果想点赞，就将踩里面的userId删掉
        String disLike = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLike, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entitytype, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entitytype, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String like = RedisKeyUtil.getLikeKey(entitytype, entityId);
        jedisAdapter.srem(like, String.valueOf(userId));

        return jedisAdapter.scard(disLikeKey);
    }
}
