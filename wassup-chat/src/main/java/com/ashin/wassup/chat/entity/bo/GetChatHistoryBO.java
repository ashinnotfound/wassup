package com.ashin.wassup.chat.entity.bo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GetChatHistoryBO {
    /**
     * 所属 ID
     */
    private int belongingId;

    /**
     * 在某个时间前
     */
    private Timestamp beforeTime;

    /**
     * 在某个时间后
     */
    private Timestamp afterTime;
}
