package com.ashin.wassup.account.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 帐户
 *
 * @author ashinnotfound
 * @date 2023/10/23
 */
@Data
public class Account {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
}
