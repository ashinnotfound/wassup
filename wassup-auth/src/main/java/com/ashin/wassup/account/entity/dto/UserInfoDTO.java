package com.ashin.wassup.account.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDTO {
    private Integer id;
    private String userName;
    private String nickName;
    private String avatar;
}
