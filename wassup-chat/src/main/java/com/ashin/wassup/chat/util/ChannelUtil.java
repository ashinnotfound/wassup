package com.ashin.wassup.chat.util;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChannelUtil {

    private final Map<Integer, ChannelGroup> channelGroupMap = new HashMap<>();

    public ChannelGroup getChannelGroup(int belongingId) {
        if (channelGroupMap.containsKey(belongingId)){
            return channelGroupMap.get(belongingId);
        }
        ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        channelGroupMap.put(belongingId, channelGroup);
        return channelGroup;
    }
}
