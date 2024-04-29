package com.ashin.wassup.api.client;

import java.util.HashMap;

import com.ashin.wassup.api.entity.bo.ChunkUploadBO;
import com.ashin.wassup.common.result.CommonResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "wassup-file", path = "/file")
public interface FileFeign {
	@PostMapping("/{md5}")
	CommonResult<Void> upload(@RequestParam("file") MultipartFile file, @PathVariable("md5") String md5);

	@PostMapping("/chunk/{md5}")
	CommonResult<HashMap<String, Object>> chunkUpload(@PathVariable("md5") String md5, @RequestBody ChunkUploadBO chunkUploadBO);

	@PostMapping("/chunk/{md5}/{uploadId}")
	CommonResult<Void> chunkCompose(@PathVariable("md5") String md5, @PathVariable("uploadId") String uploadId);

	@GetMapping("/{objectName}")
	CommonResult<String> getUrl(@PathVariable("objectName") String objectName);
}
