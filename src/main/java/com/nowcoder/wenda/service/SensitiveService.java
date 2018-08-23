package com.nowcoder.wenda.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
    private static final String DEFAULT_REPLACEMENT = "***";

    // 创建敏感词节点
    private class TrieNode {
        // 关键词的终结： false代表继续
        private boolean end = false;

        // key 下一个字符，value是对应的节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        // 向指定位置添加节点树
        void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        // 获取下一个节点
        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeywordEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }

    // 根节点
    private TrieNode rootNode = new TrieNode();

    // 判读是否是一个符合
    private boolean isSymbol(char c) {
        // 0x2E80-0x9FFF 东亚文字范围
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    // 添加敏感词关键词
    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;

        // 循环每一个字节
        for (int i = 0; i < lineTxt.length(); i++) {
            Character c = lineTxt.charAt(i);
            // 过滤空格
            if (isSymbol(c)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(c);

            if (node == null) { // 判断是否初始化
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }

            tempNode = node;

            if (i == lineTxt.length() - 1) {
                // 关键词结束，设置结束标志
                tempNode.setKeywordEnd(true);
            }
        }
    }

    // 过滤敏感词
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }

        String replacement = DEFAULT_REPLACEMENT;
        StringBuilder result = new StringBuilder();

        TrieNode tempNode = rootNode;
        int begin = 0; // 回滚数
        int position = 0; // 当前比较的位置

        while (position < text.length()) {
            char c = text.charAt(position);

            // 是空格直接跳过
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            // 当前位置匹配结束
            if (tempNode == null) {
                // 以begin开头的字符串不存在敏感词
                result.append(text.charAt(begin));
                // 跳到下一个字符开始测试
                position = begin + 1;
                begin = position;
                // 回到树初始节点上
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现敏感词，从begin到position的位置用replacement替换掉
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            }else {
                ++ position;
            }
        }
        result.append(text.substring(begin));

        return result.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode = new TrieNode();

        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("读取敏感词文件失败 " + e.getMessage());
        }
    }
}
