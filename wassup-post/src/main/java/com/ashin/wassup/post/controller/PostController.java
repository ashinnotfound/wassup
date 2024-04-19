package com.ashin.wassup.post.controller;

import com.ashin.wassup.common.result.CommonResult;
import com.ashin.wassup.post.entity.bo.NewPostBO;
import com.ashin.wassup.post.entity.vo.PostVO;
import com.ashin.wassup.post.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping
    public CommonResult<Void> newPost(@RequestHeader("userId") Integer userId, @RequestBody @Validated NewPostBO newPostBO) {
        newPostBO.setUserId(userId);
        newPostBO.setPostTime(LocalDateTime.now());
        postService.newPost(newPostBO);
        return CommonResult.operateSuccess("发送贴文成功");
    }

    @GetMapping("/{id}")
    public CommonResult<PostVO> getPost(@PathVariable("id") Integer id) {
        return CommonResult.operateSuccess("获取贴文成功", postService.getPost(id));
    }

    @GetMapping
    public CommonResult<List<PostVO>> getAllPost() {
        return CommonResult.operateSuccess("获取贴文成功", postService.getAllPost());
    }

    @GetMapping("/me")
    public CommonResult<List<PostVO>> getMyPost(@RequestHeader("userId") Integer userId) {
        return CommonResult.operateSuccess("获取贴文成功", postService.getMyPost(userId));
    }
}
