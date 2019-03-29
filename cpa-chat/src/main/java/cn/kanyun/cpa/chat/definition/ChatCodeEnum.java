package cn.kanyun.cpa.chat.definition;

/**
 * 枚举:
 * 要注意的是：
 * 1. 通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
 * 2. 赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
 */
public enum ChatCodeEnum {



    /**
     * 认证编码【服务端接收的编码】
     */
    AUTH_CODE(10000),

    /**
     * 聊天消息编码【服务端接收的编码】
     */
    MESS_CODE(10086),


    /**
     * 系统消息类型
     */
    /*###########################################*/
    /**
     * 谁加入了聊天【服务端发送的编码】
     */
    SYS_USER_WHO(20001),

    /**
     * 在线人数
     */
    SYS_USER_COUNT(20004),

    /**
     * 认证结果【服务端发送的编码】
     */
    SYS_AUTH_STATE(20002),

    /**
     * 【服务端发送的编码】
     */
    PING_CODE(10015),

    /**
     * 【服务端接收的编码】
     */
    PONG_CODE(10016),

    /**
     * 系统消息
     */
    SYS_OTHER_INFO(20003);



    private final int code;

    /**
     * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
     *
     * @param code
     */
    ChatCodeEnum(int code) {
        this.code = code;
    }

    public static ChatCodeEnum getChatCodeEnum(int code) {
        for (ChatCodeEnum chatCode : ChatCodeEnum.values()) {
            if (chatCode.getCode() == code) {
                return chatCode;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }


}