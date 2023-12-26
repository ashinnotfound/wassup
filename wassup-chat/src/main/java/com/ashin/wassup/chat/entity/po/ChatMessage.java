package com.ashin.wassup.chat.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 聊天消息
 *
 * @author ashinnotfound
 * @date 2023/11/03
 */
@Data
@Accessors(chain = true)
public class ChatMessage {
    /**
     * 编号
     */
    private int id;
    /**
     * 用户 ID
     */
    private int userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 内容
     */
    private String content;
    /**
     * 内容类型 true文字 false图片
     */
    private boolean contentType;
    /**
     * 所属 ID
     */
    private int belongingId;
    /**
     * 时间
     */
    private Timestamp time;
}