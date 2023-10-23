package com.ashin.wassup.account.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
@ResponseBody
public class AccountController {

    @PostMapping("/test")
    public String test(){
        return "666";
    }
}
