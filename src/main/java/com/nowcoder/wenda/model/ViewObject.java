package com.nowcoder.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：多属性显示
 *
 * @author huanxiong
 * @create 2018-08-17 20:29
 */
public class ViewObject {
    private Map<String, Object> objects = new HashMap<String, Object>();

    public void set(String key, Object value) {
        objects.put(key, value);
    }

    public Object get(String key) {
        return objects.get(key);
    }
}
