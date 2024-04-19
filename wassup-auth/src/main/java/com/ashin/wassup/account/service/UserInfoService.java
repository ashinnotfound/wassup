package com.ashin.wassup.account.service;

import com.ashin.wassup.account.entity.dto.UserInfoDTO;

public interface UserInfoService {
    UserInfoDTO getUserInfo(int id);

    void addUserInfo(UserInfoDTO userInfoDTO);
}
