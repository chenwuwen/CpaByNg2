package cn.kanyun.cpa.chat.entity;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public class Message implements Serializable {

    /**
     * 发送人ID
     */
    private Long userId;

    /**
     * 发送人姓名
     */
    private String nickName;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 消息内容
     */
    private String content;
}