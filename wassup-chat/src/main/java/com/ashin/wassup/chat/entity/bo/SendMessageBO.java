package com.ashin.wassup.chat.entity.bo;

import lombok.Data;

/**
 * 发送消息bo
 *
 * @author ashinnotfound
 * @date 2023/11/03
 */
@Data
public class SendMessageBO {
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
}
