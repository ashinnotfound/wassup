package com.ashin.wassup.account.service.impl;

import com.ashin.wassup.account.entity.dto.UserInfoDTO;
import com.ashin.wassup.account.entity.po.UserInfo;
import com.ashin.wassup.account.mapper.UserInfoMapper;
import com.ashin.wassup.account.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Value("${default-avatar-md5}")
    private String defaultAvatarMd5;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfoDTO getUserInfo(int id) {

        UserInfo userInfo = userInfoMapper.selectById(id);
        Assert.notNull(userInfo, "该用户的信息不存在");
        return UserInfoDTO.builder().id(userInfo.getId()).userName(userInfo.getUserName()).nickName(userInfo.getNickName()).avatar(userInfo.getAvatar()).build();
    }

    @Override
    public void addUserInfo(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoDTO.getId());
        userInfo.setUserName(userInfoDTO.getUserName());
        if(null == userInfoDTO.getNickName() || userInfoDTO.getNickName().isEmpty()) {
            userInfo.setNickName(userInfoDTO.getUserName());
        }
        if(null == userInfoDTO.getAvatar() || userInfoDTO.getAvatar().isEmpty()) {
            userInfo.setAvatar(defaultAvatarMd5);
        }
        Assert.isTrue(1 == userInfoMapper.insert(userInfo), "添加新用户数据失败");
    }
}
