package com.ashin.wassup.post.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帖子
 *
 * @author ashinnotfound
 * @date 2024/03/25
 */
@Data
@Builder
public class Post {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

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
     * 发帖时间
     */
    private LocalDateTime postTime;
}
