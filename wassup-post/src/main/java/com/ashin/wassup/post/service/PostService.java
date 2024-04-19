package com.ashin.wassup.post.service;

import com.ashin.wassup.post.entity.bo.NewPostBO;
import com.ashin.wassup.post.entity.vo.PostVO;

import java.util.List;

public interface PostService {
    void newPost(NewPostBO newPostBO);

    PostVO getPost(int id);

    List<PostVO> getAllPost();

    List<PostVO> getMyPost(int userId);
}
