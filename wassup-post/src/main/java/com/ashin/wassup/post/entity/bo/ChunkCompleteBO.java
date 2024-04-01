package com.ashin.wassup.post.entity.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkCompleteBO {
    private String uploadId;
    private String md5;
}
