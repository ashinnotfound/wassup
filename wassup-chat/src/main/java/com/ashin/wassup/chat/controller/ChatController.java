package com.ashin.wassup.chat.controller;

import com.ashin.wassup.chat.entity.bo.GetChatHistoryBO;
import com.ashin.wassup.chat.entity.po.ChatMessage;
import com.ashin.wassup.chat.service.ChatService;
import com.ashin.wassup.common.result.CommonResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    @GetMapping
    public CommonResult<List<ChatMessage>> getChatHistory(GetChatHistoryBO getChatHistoryBO) {
        return CommonResult.operateSuccess("获取聊天记录成功", chatService.getChatHistory(getChatHistoryBO));
    }
}
