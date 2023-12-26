package com.ashin.wassup.chat.service;

import com.ashin.wassup.chat.entity.bo.GetChatHistoryBO;
import com.ashin.wassup.chat.entity.bo.SendMessageBO;
import com.ashin.wassup.chat.entity.po.ChatMessage;

import java.util.List;

public interface ChatService {

    List<ChatMessage> getChatHistory(GetChatHistoryBO getChatHistoryBO);

    void sendMessage(SendMessageBO sendMessageBO);
}
