package com.ashin.wassup.post.client;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyMinioClient extends MinioClient {
    private final String domain;
    private final String bucket;
    private final String exposeDomain;


    public MyMinioClient(MinioClient client, String domain, String bucket, String exposeDomain) {
        super(client);
        this.domain = domain;
        this.bucket = bucket;
        this.exposeDomain = exposeDomain;
    }

    public boolean upload(InputStream inputStream, long fileSize, String objectName) {
        try {
            super.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(inputStream, fileSize, -1)
                            .build()
            );
            return true;
        }catch (Exception e) {
            log.error("{} minio上传文件 {} 失败:{}", LocalDateTime.now(), objectName, e.getMessage());
            return false;
        }
    }

    public String getUrl(String objectName) {
        return exposeDomain + "/" + bucket + "/" + objectName;
    }

    public boolean exist(String objectName) {
        try {
            super.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public String createMultipartUpload(String objectName) {
        try {
            return super.createMultipartUpload(bucket, null, objectName, null, null).result().uploadId();
        }catch (Exception e) {
            log.error("{} minio创建 {} 分片任务失败:{}", LocalDateTime.now(), objectName, e.getMessage());
            return null;
        }
    }

    public List<String> getPresignedObjectUrl(String uploadId, String objectName, int chunkSize) {
        List<String> result = new ArrayList<>();

        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("uploadId", uploadId);

        try {
            for (int i = 1; i <= chunkSize; i++) {
                reqParams.put("partNumber", String.valueOf(i));
                result.add(super.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.PUT)
                                .object(objectName)
                                .bucket(bucket)
                                .extraQueryParams(reqParams)
                                .build()
                ));
            }

            return result;
        }catch (Exception e) {
            log.error("{} minio获取 {} 分片任务url失败, uploadId为{}:{}", LocalDateTime.now(), objectName, uploadId, e.getMessage());
            return null;
        }
    }

    public boolean completeMultipartUpload(String objectName, String uploadId) {
        try {
            super.completeMultipartUpload(
                    bucket,
                    null,
                    objectName,
                    uploadId,
                    super.listParts(bucket, null, objectName, null,null, uploadId, null, null).result().partList().toArray(new Part[]{}),
                    null,
                    null
            );
            return true;
        }catch (Exception e) {
            log.error("minio合并文件 {} 失败, uploadId为{}:{}", objectName, uploadId, e.getMessage());
            return false;
        }
    }
}
