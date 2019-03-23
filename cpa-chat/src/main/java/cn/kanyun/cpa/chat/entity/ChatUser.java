package cn.kanyun.cpa.chat.entity;

import lombok.Data;

@Data
public class ChatUser {

    /**
     * 是否认证
     */
    private boolean isAuth = false;

    private String nickName;

    /**
     * 地址
     */
    private String addr;
    /**
     * 登录时间
     */
    private Long time;

    /**
     * UserId
     */
    private int userId;


}