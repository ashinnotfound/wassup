package com.ashin.wassup.post.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
public class PostVO {
    /**
     * 帖子内容
     */
    private String content;

    /**
     * 是否有媒体文件
     */
    private Boolean hasMedia;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 发帖时间
     */
    private LocalDateTime postTime;

    /**
     * 媒体文件 url
     */
    private ArrayList<String> mediaUrls;
}
