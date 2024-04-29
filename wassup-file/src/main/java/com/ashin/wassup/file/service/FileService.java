package com.ashin.wassup.file.service;

import com.ashin.wassup.file.entity.bo.ChunkCompleteBO;
import com.ashin.wassup.file.entity.bo.ChunkUploadBO;
import com.ashin.wassup.file.entity.bo.UploadBO;

import java.util.HashMap;

public interface FileService {
    void upload(UploadBO uploadBO);

    HashMap<String, Object> chunkUpload(ChunkUploadBO uploadBO);

    void chunkCompose(ChunkCompleteBO chunkCompleteBO);

    String getUrl(String objectName);
}
