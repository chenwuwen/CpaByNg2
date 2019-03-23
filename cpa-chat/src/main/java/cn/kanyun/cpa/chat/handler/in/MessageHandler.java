package cn.kanyun.cpa.chat.handler.in;

import cn.kanyun.cpa.chat.ChatCode;
import cn.kanyun.cpa.chat.ChatUserManager;
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
        log.info("====服务端接收到消息：{}=========",textWebSocketFrame.text());
        ChatUser userInfo = ChatUserManager.getUserInfo(channelHandlerContext.channel());
        if (userInfo != null && userInfo.isAuth()) {
            log.info("=======发送消息的用户通过了认证,服务端可以广播了=====");
            JSONObject json = JSONObject.parseObject(textWebSocketFrame.text());
            // 广播返回用户发送的消息文本
            ChatUserManager.broadcastMess(userInfo.getUserId(), userInfo.getNickName(), json.getString("mess"));
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ChatUserManager.removeChannel(ctx.channel());
        ChatUserManager.broadCastInfo(ChatCode.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(">>>>>>>> 连接错误关闭 channel :{}", cause);
        ChatUserManager.removeChannel(ctx.channel());
        ChatUserManager.broadCastInfo(ChatCode.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("MessageHandler channelActive()");
        super.channelActive(ctx);
    }
}