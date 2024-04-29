package com.ashin.wassup.file.controller;

import com.ashin.wassup.common.result.CommonResult;
import com.ashin.wassup.file.entity.bo.ChunkCompleteBO;
import com.ashin.wassup.file.entity.bo.ChunkUploadBO;
import com.ashin.wassup.file.entity.bo.UploadBO;
import com.ashin.wassup.file.service.FileService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {
    @Resource
    private FileService fileService;
    @PostMapping("/{md5}")
    public CommonResult<Void> upload(@RequestParam("file") @NotNull MultipartFile file, @PathVariable("md5") @NotNull String md5) {
        fileService.upload(UploadBO.builder().file(file).md5(md5).build());
        return CommonResult.operateSuccess("上传文件成功");
    }

    @PostMapping("/chunk/{md5}")
    public CommonResult<HashMap<String, Object>> chunkUpload(@PathVariable("md5") @NotNull String md5, @RequestBody ChunkUploadBO chunkUploadBO) {
        chunkUploadBO.setMd5(md5);
        return CommonResult.operateSuccess("请求分片上传文件成功", fileService.chunkUpload(chunkUploadBO));
    }

    @PostMapping("/chunk/{md5}/{uploadId}")
    public CommonResult<Void> chunkCompose(@PathVariable("md5") @NotNull String md5, @PathVariable("uploadId") @NotNull String uploadId) {
        fileService.chunkCompose(ChunkCompleteBO.builder().md5(md5).uploadId(uploadId).build());
        return CommonResult.operateSuccess("合并文件成功");
    }

    @GetMapping("/{objectName}")
    public CommonResult<String> getUrl(@PathVariable("objectName") @NotNull String objectName) {
        return CommonResult.operateSuccess("获取url成功", fileService.getUrl(objectName));
    }
}
