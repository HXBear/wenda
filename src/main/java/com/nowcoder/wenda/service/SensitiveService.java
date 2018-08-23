package com.nowcoder.wenda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 描述：敏感词过滤的服务
 *
 * @author huanxiong
 * @create 2018-08-22 23:54
 */
@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    // 默认敏感词替换符
    private static final String DEFAULT_REPLACEMENT = "敏感词";



    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
