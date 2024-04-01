package com.ashin.wassup.post.service;

import com.ashin.wassup.post.entity.bo.ChunkCompleteBO;
import com.ashin.wassup.post.entity.bo.ChunkUploadBO;
import com.ashin.wassup.post.entity.bo.UploadBO;

import java.util.HashMap;

public interface FileService {
    void upload(UploadBO uploadBO);

    HashMap<String, Object> chunkUpload(ChunkUploadBO uploadBO);

    void chunkCompose(ChunkCompleteBO chunkCompleteBO);
}
