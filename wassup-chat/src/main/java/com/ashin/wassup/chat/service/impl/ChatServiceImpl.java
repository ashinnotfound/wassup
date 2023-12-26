package com.ashin.wassup.chat.service.impl;

import com.ashin.wassup.chat.entity.bo.GetChatHistoryBO;
import com.ashin.wassup.chat.entity.bo.SendMessageBO;
import com.ashin.wassup.chat.entity.po.ChatMessage;
import com.ashin.wassup.chat.mapper.ChatMessageMapper;
import com.ashin.wassup.chat.service.ChatService;
import com.ashin.wassup.chat.util.ChannelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Resource
    private ChannelUtil channelUtil;

    @Override
    public List<ChatMessage> getChatHistory(GetChatHistoryBO getChatHistoryBO) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<ChatMessage>().eq("belonging_id", getChatHistoryBO.getBelongingId());
        if (getChatHistoryBO.getBeforeTime() != null) {
            queryWrapper.lt("time", getChatHistoryBO.getBeforeTime());
        }
        if (getChatHistoryBO.getAfterTime() != null) {
            queryWrapper.gt("time", getChatHistoryBO.getAfterTime());
        }
        return chatMessageMapper.selectList(queryWrapper);
    }

    @Override
    public void sendMessage(SendMessageBO sendMessageBO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage
                .setUserId(sendMessageBO.getUserId())
                .setUserName(sendMessageBO.getUserName())
                .setContent(sendMessageBO.getContent())
                .setContentType(sendMessageBO.isContentType())
                .setBelongingId(sendMessageBO.getBelongingId())
                .setTime(new Timestamp(new Date().getTime()));
        chatMessageMapper.insert(chatMessage);

        channelUtil.getChannelGroup(sendMessageBO.getBelongingId()).writeAndFlush(sendMessageBO.getContent());
    }
}
