package com.ashin.wassup.api.entity;

import lombok.Data;

@Data
public class UserInfoDTO {
    private Integer id;
    private String userName;
    private String nickName;
    private String avatar;
}
