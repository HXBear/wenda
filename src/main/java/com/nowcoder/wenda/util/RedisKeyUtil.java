package com.nowcoder.wenda.util;

/**
 * 描述：获得存储的key
 *
 * @author huanxiong
 * @create 2018-08-26 20:38
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }
}
