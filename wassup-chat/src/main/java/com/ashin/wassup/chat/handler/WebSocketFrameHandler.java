package com.ashin.wassup.chat.handler;

import cn.hutool.json.JSONUtil;
import com.ashin.wassup.chat.entity.bo.SendMessageBO;
import com.ashin.wassup.chat.service.ChatService;
import com.ashin.wassup.chat.util.ChannelUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<Object> {

    @Resource
    private ChatService chatService;

    @Resource
    private ChannelUtil channelUtil;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object frame) {
        if (frame instanceof TextWebSocketFrame) {
            SendMessageBO sendMessageBO = JSONUtil.toBean(((TextWebSocketFrame) frame).text(), SendMessageBO.class);
            channelUtil.getChannelGroup(sendMessageBO.getBelongingId()).add(ctx.channel());
            chatService.sendMessage(sendMessageBO);
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
