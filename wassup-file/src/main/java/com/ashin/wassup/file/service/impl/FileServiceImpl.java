package com.ashin.wassup.file.service.impl;

import com.ashin.wassup.file.client.MyMinioClient;
import com.ashin.wassup.file.entity.bo.ChunkCompleteBO;
import com.ashin.wassup.file.entity.bo.ChunkUploadBO;
import com.ashin.wassup.file.entity.bo.UploadBO;
import com.ashin.wassup.file.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private MyMinioClient myMinioClient;

    @Override
    public void upload(UploadBO uploadBO) {
        if(myMinioClient.exist(uploadBO.getMd5())) {
            return;
        }
        MultipartFile file = uploadBO.getFile();
        try {
            Assert.isTrue(myMinioClient.upload(file.getInputStream(), file.getSize(), uploadBO.getMd5()), "上传文件失败");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<String, Object> chunkUpload(ChunkUploadBO chunkUploadBO) {
        if(myMinioClient.exist(chunkUploadBO.getMd5())) {
            return null;
        }
        String uploadId = myMinioClient.createMultipartUpload(chunkUploadBO.getMd5());

        List<String> presignedObjectUrl = myMinioClient.getPresignedObjectUrl(uploadId, chunkUploadBO.getMd5(), chunkUploadBO.getChunkSize());

        Assert.notNull(presignedObjectUrl, "请求分片上传文件失败");

        HashMap<String, Object> result = new HashMap<>();
        result.put("uploadId", uploadId);
        result.put("url", presignedObjectUrl);
        return result;
    }

    @Override
    public void chunkCompose(ChunkCompleteBO chunkCompleteBO) {
        Assert.isTrue(myMinioClient.completeMultipartUpload(chunkCompleteBO.getMd5(), chunkCompleteBO.getUploadId()), "合并分片失败");
    }

    @Override
    public String getUrl(String objectName) {
        Assert.notNull(objectName, "获取链接失败，objectName不能为空");
        return myMinioClient.getUrl(objectName);
    }
}
