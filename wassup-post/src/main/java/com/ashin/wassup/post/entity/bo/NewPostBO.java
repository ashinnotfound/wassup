package com.ashin.wassup.post.entity.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class NewPostBO {
    /**
     * 帖子内容
     */
    @Size(max = 500, message = "帖子不能超过500个字")
    private String content;

    /**
     * 是否有媒体文件
     */
    @NotNull(message = "贴文媒体文件状态不能为空")
    private Boolean hasMedia;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 发帖时间
     */
    private LocalDateTime postTime;

    /**
     * 媒体 md5
     */
    private ArrayList<String> mediaMd5s;
}
