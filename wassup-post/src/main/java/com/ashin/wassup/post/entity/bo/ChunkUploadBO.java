package com.ashin.wassup.post.entity.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkUploadBO {
    private String md5;
    private int chunkSize;
}
