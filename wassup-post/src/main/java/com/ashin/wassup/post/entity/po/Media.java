package com.ashin.wassup.post.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * 媒体文件
 *
 * @author ashinnotfound
 * @date 2024/03/25
 */
@Data
@Builder
public class Media {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子 ID
     */
    private Integer postId;

    /**
     * 媒体文件md5
     */
    private String md5;
}
