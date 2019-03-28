package cn.kanyun.cpa.chat;

import cn.kanyun.cpa.chat.definition.ChatCodeEnum;
import cn.kanyun.cpa.chat.definition.ChatProto;
import cn.kanyun.cpa.chat.entity.ChatUser;
import cn.kanyun.cpa.chat.util.NettyUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatUserManager {
    private static final Logger logger = LoggerFactory.getLogger(ChatUserManager.class);

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static ConcurrentMap<Channel, ChatUser> userInfos = new ConcurrentHashMap<>();
    private static AtomicInteger userCount = new AtomicInteger(0);

    /**
     * 保存channel到缓存,key是channel value是用户(此时信息是不完整的)
     *
     * @param channel
     */
    public static void addChannel(Channel channel) {
        String remoteAddr = NettyUtil.parseChannelRemoteAddr(channel);
        if (!channel.isActive()) {
            logger.error("channel is not active, address: {}", remoteAddr);
        }
        ChatUser userInfo = new ChatUser();
        userInfo.setAddr(remoteAddr);
        userInfo.setTime(System.currentTimeMillis());
        userInfos.put(channel, userInfo);
    }

    /**
     * 完善聊天用户信息
     * 从Map中根据Channel取到ChatUser完善ChatUser 这时候Map中就保存的是完整的ChatUser了
     *
     * @param channel
     * @param nick
     * @return
     */
    public static boolean saveUser(Channel channel, String nick) {
        ChatUser userInfo = userInfos.get(channel);
        if (userInfo == null) {
            return false;
        }
        if (!channel.isActive()) {
            logger.error("channel is not active, address: {}, nick: {}", userInfo.getAddr(), nick);
            return false;
        }
        // 增加一个认证用户
        userCount.incrementAndGet();
        userInfo.setNickName(nick);
        userInfo.setAuth(true);
//        userInfo.setUserId();
        userInfo.setTime(System.currentTimeMillis());
        return true;
    }

    /**
     * 从缓存中移除Channel，并且关闭Channel
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        try {
            logger.warn("channel will be remove, address is :{}", NettyUtil.parseChannelRemoteAddr(channel));
            rwLock.writeLock().lock();
            channel.close();
            ChatUser userInfo = userInfos.get(channel);
            if (userInfo != null) {
                ChatUser tmp = userInfos.remove(channel);
                if (tmp != null && tmp.isAuth()) {
                    // 减去一个认证用户
                    userCount.decrementAndGet();
                }
            }
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    /**
     * 广播普通消息(即用户发送的聊天消息)
     *
     * @param message
     */
    public static void broadcastMess(int uid, String nick, String message) {
        if (!StringUtils.isBlank(message)) {
            try {
                rwLock.readLock().lock();
                Set<Channel> keySet = userInfos.keySet();
                for (Channel ch : keySet) {
                    ChatUser userInfo = userInfos.get(ch);
                    if (userInfo == null || !userInfo.isAuth()) {
                        continue;
                    }
                    ch.writeAndFlush(new TextWebSocketFrame(ChatProto.buildMessProto(uid, nick, message)));
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    /**
     * 广播系统消息(谁加入了聊天的系统消息)
     */
    public static void broadCastInfo(ChatCodeEnum code, Object mess) {
        try {
            rwLock.readLock().lock();
            Set<Channel> keySet = userInfos.keySet();
            for (Channel ch : keySet) {
                ChatUser userInfo = userInfos.get(ch);
                if (userInfo == null || !userInfo.isAuth()) {
                    continue;
                }
                ch.writeAndFlush(new TextWebSocketFrame(ChatProto.buildSysProto(code, mess)));
            }
        } finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * 广播Ping消息,只有认证过的才会发送Ping消息
     */
    public static void broadCastPing() {
        try {
            rwLock.readLock().lock();
            logger.info("broadCastPing userCount: {}", userCount.intValue());
            Set<Channel> keySet = userInfos.keySet();
            for (Channel ch : keySet) {
                ChatUser userInfo = userInfos.get(ch);
                if (userInfo == null || !userInfo.isAuth()) {
                    continue;
                }
                ch.writeAndFlush(new TextWebSocketFrame(ChatProto.buildPingProto()));
            }
        } finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * 发送认证结果的系统消息
     *
     * @param code
     * @param mess
     */
    public static void sendInfo(Channel channel, ChatCodeEnum code, Object mess) {
        channel.writeAndFlush(new TextWebSocketFrame(ChatProto.buildSysProto(code, mess)));
    }

    public static void sendPong(Channel channel) {
        channel.writeAndFlush(new TextWebSocketFrame(ChatProto.buildPongProto()));
    }

    /**
     * 扫描并关闭失效的Channel
     * 其中包括 通道关闭的,通道不活跃的,未认证的,与服务端Ping Pong超过1分钟未交互的客户端
     */
    public static void scanNotActiveChannel() {
        Set<Channel> keySet = userInfos.keySet();
        for (Channel ch : keySet) {
            ChatUser userInfo = userInfos.get(ch);
            if (userInfo == null) {
                continue;
            }
            if (!ch.isOpen() || !ch.isActive() || (!userInfo.isAuth() &&
                    (System.currentTimeMillis() - userInfo.getTime()) > 60 * 1000)) {
                removeChannel(ch);
            }
        }
    }


    public static ChatUser getUserInfo(Channel channel) {
        return userInfos.get(channel);
    }

    public static ConcurrentMap<Channel, ChatUser> getUserInfos() {
        return userInfos;
    }

    public static int getAuthUserCount() {
        return userCount.get();
    }

    public static void updateUserTime(Channel channel) {
        ChatUser userInfo = getUserInfo(channel);
        if (userInfo != null) {
            userInfo.setTime(System.currentTimeMillis());
        }
    }
}