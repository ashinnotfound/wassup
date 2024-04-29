package com.ashin.wassup.post.service.impl;

import com.ashin.wassup.api.client.AuthFeign;
import com.ashin.wassup.api.client.FileFeign;
import com.ashin.wassup.api.entity.dto.UserInfoDTO;
import com.ashin.wassup.common.result.CommonResult;
import com.ashin.wassup.post.entity.vo.PostVO;
import com.ashin.wassup.post.mapper.MediaMapper;
import com.ashin.wassup.post.mapper.PostMapper;
import com.ashin.wassup.post.entity.bo.NewPostBO;
import com.ashin.wassup.post.entity.po.Media;
import com.ashin.wassup.post.entity.po.Post;
import com.ashin.wassup.post.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;
    @Resource
    private MediaMapper mediaMapper;

    @Resource
    private FileFeign fileFeign;
    @Resource
    private AuthFeign authFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newPost(NewPostBO newPostBO) {
        Assert.isTrue((!newPostBO.getHasMedia() && !"".equals(newPostBO.getContent())) || (newPostBO.getHasMedia() && !newPostBO.getMediaMd5s().isEmpty()), "发送贴文失败, 贴文不能为空或媒体文件缺失");

        Post post = Post.builder().content(newPostBO.getContent()).postTime(newPostBO.getPostTime()).hasMedia(newPostBO.getHasMedia()).userId(newPostBO.getUserId()).build();
        Assert.isTrue(1 == postMapper.insert(post), "发送贴文失败, 数据库异常");

        if(newPostBO.getHasMedia()) {
            for(String md5 : newPostBO.getMediaMd5s()) {
                Media media = Media.builder().md5(md5).postId(post.getId()).build();
                Assert.isTrue(1 == mediaMapper.insert(media), "发送贴文失败, 数据库异常");
            }
        }
    }

    @Override
    public PostVO getPost(int id) {
        Post post = postMapper.selectById(id);
        Assert.notNull(post, "获取贴文失败, 该帖文不存在");

        return getPostVO(post);
    }

    @Override
    public List<PostVO> getAllPost() {
        List<Post> postList = postMapper.selectList(null);

        return getPostVOS(postList);
    }

    @Override
    public List<PostVO> getMyPost(int userId) {
        List<Post> postList = postMapper.selectList(new QueryWrapper<Post>().eq("user_id", userId));

        return getPostVOS(postList);
    }

    private List<PostVO> getPostVOS(List<Post> postList) {
        ArrayList<PostVO> postVOArrayList = new ArrayList<>();
        for (Post post : postList) {
            postVOArrayList.add(getPostVO(post));
        }

        return postVOArrayList;
    }

    private PostVO getPostVO(Post post) {

        CommonResult<UserInfoDTO> result = authFeign.getUserInfo(post.getUserId());
        Assert.isTrue(result.isSuccess(), result.getMessage());
        UserInfoDTO userInfoDTO = result.getData();

        PostVO postVO = PostVO.builder()
                .userId(post.getUserId())
                .userName(userInfoDTO.getNickName())
                .userAvatar(fileFeign.getUrl(userInfoDTO.getAvatar()).getData())
                .postTime(post.getPostTime())
                .content(post.getContent())
                .build();
        if(post.getHasMedia()) {
            postVO.setHasMedia(true);
            List<Media> mediaArrayList = mediaMapper.selectList(new QueryWrapper<Media>().eq("post_id", post.getId()));
            ArrayList<String> urls = new ArrayList<>();
            for(Media media : mediaArrayList) {
                urls.add(fileFeign.getUrl(media.getMd5()).getData());
            }
            postVO.setMediaUrls(urls);
        }else {
            postVO.setHasMedia(false);
        }

        return postVO;
    }
}
