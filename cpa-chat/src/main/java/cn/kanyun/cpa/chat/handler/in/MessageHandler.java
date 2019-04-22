package cn.kanyun.cpa.chat.handler.in;

import cn.kanyun.cpa.chat.ChatUserManager;
import cn.kanyun.cpa.chat.definition.ChatCodeEnum;
import cn.kanyun.cpa.chat.entity.ChatUser;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 一般用netty来发送和接收数据都会继承SimpleChannelInboundHandler和ChannelInboundHandlerAdapter这两个抽象类
 * 其实用这两个抽象类是有讲究的
 * 在客户端的业务Handler继承的是SimpleChannelInboundHandler
 * 而在服务器端继承的是ChannelInboundHandlerAdapter
 * 最主要的区别就是SimpleChannelInboundHandler在接收到数据后会自动release掉数据占用的Bytebuffer资源(自动调用Bytebuffer.release())。
 * 而为何服务器端不能用呢，因为我们想让服务器把客户端请求的数据发送回去，而服务器端有可能在channelRead方法返回前还没有写完数据，因此不能让它自动release。
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        log.info("====服务端接收到消息：{}=========", textWebSocketFrame.text());
        ChatUser userInfo = ChatUserManager.getUserInfo(channelHandlerContext.channel());
        if (userInfo != null) {
            if (userInfo.isAuth()) {
                log.info("=======发送消息的用户通过了认证,服务端可以广播了=====");
                JSONObject json = JSONObject.parseObject(textWebSocketFrame.text());
                // 广播返回用户发送的消息文本
                ChatUserManager.broadcastMess(userInfo.getUserId(), userInfo.getNickName(), json.getString("message"));
            } else {
//                如果该用户未认证.则返回信息需要验证
                ChatUserManager.sendSysMess(channelHandlerContext.channel(), ChatCodeEnum.SYS_AUTH_STATE, false);
            }
        }
    }



    /**
     * 当有Channel注册时广播当前聊天人数,由于Channel是先注册上的
     * 用户认证是后来认证的，在用户认证前是否可以查看当前聊天人数？
     * 但是聊天人数一定是认证后的人数！
     * 该方法先于channelActive()方法执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("MessageHandler channelRegistered() 方法");
//        并不执行发送消息(可能是连接未建立完全,可在messageReceived()方法中发送消息)
//        ChatUserManager.sendSysMess(ctx.channel(), ChatCodeEnum.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    /**
     * 管道非注册时触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ChatUserManager.removeChannel(ctx.channel());
        ChatUserManager.broadCastInfo(ChatCodeEnum.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(">>>>>>>> 连接错误关闭 channel :{}", cause);
        ChatUserManager.removeChannel(ctx.channel());
        ChatUserManager.broadCastInfo(ChatCodeEnum.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("MessageHandler channelActive()方法");
        super.channelActive(ctx);
    }
}