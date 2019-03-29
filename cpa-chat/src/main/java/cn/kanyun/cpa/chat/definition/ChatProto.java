package cn.kanyun.cpa.chat.definition;

import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 聊天室的协议
 */
public class ChatProto {
    public static final int PING_PROTO = 1 << 8 | 220; //ping消息
    public static final int PONG_PROTO = 2 << 8 | 220; //pong消息
    public static final int SYST_PROTO = 3 << 8 | 220; //系统消息
    public static final int EROR_PROTO = 4 << 8 | 220; //错误消息
    public static final int AUTH_PROTO = 5 << 8 | 220; //认证消息
    public static final int MESS_PROTO = 6 << 8 | 220; //普通消息

    private int version = 1;
    private int head;
    private String body;
    private Map<String, Object> extend = new HashMap<>();

    public ChatProto(int head, String body) {
        this.head = head;
        this.body = body;
    }

    /**
     * 组装Ping消息
     *
     * @return
     */
    public static String buildPingProto() {
        return buildProto(PING_PROTO, null, ChatCodeEnum.PING_CODE);
    }

    /**
     * 组装Pong消息
     *
     * @return
     */
    public static String buildPongProto() {
        return buildProto(PONG_PROTO, null, ChatCodeEnum.PONG_CODE);
    }

    /**
     * 组合返回信息(系统信息)
     *
     * @param code
     * @param mess
     * @return
     */
    public static String buildSysProto(ChatCodeEnum code, Object mess) {
        ChatProto chatProto = new ChatProto(SYST_PROTO, null);
        chatProto.extend.put("code", code.getCode());
        chatProto.extend.put("reply", mess);
        return JSONObject.toJSONString(chatProto);
    }


    /**
     * 组装服务器返回的聊天信息
     *
     * @param uid
     * @param nick
     * @param mess
     * @return
     */
    public static String buildMessProto(int uid, String nick, String message) {
        ChatProto chatProto = new ChatProto(MESS_PROTO, message);
        chatProto.extend.put("uid", uid);
        chatProto.extend.put("nick", nick);
        chatProto.extend.put("time", LocalDateTime.now());
        return JSONObject.toJSONString(chatProto);
    }

    public static String buildProto(int head, String body, ChatCodeEnum code) {
        ChatProto chatProto = new ChatProto(head, body);
        chatProto.extend.put("code", code.getCode());
        return JSONObject.toJSONString(chatProto);
    }


    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}