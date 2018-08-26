package com.nowcoder.wenda.util;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 描述：点赞和点踩功能的实现
 * 我们选择Redis里面的set集合： 适用于无序的集合，点赞，点踩，抽奖，已读，共同好友等功能的实现
 *
 * @author huanxiong
 * @create 2018-08-26 9:44
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    // 添加操作
    public long sadd(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return 0;
    }

    // 删除操作
    public long srem(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("删除失败" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    // 获得集合中元素的个数
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    // 某个值在集合中是否存在
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }
}
