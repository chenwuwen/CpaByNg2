package cn.kanyun.cpa.chat.handler.in;

import cn.kanyun.cpa.chat.ChatCode;
import cn.kanyun.cpa.chat.ChatUserManager;
import cn.kanyun.cpa.chat.Constants;
import cn.kanyun.cpa.chat.entity.ChatUser;
import cn.kanyun.cpa.chat.util.NettyUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("UserAuthHandler channelActive()");
        super.channelActive(ctx);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        log.info(">>>>>>>>UserAuthHandler messageReceived()方法：判断消息类型：{}<<<<<<<", msg.getClass());
        if (msg instanceof FullHttpRequest) {
//            FullHttpRequest主要包含对HttpRequest和httpContent的组合
            handleHttpRequest(channelHandlerContext, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
//            WebSocket定义了六种帧:对于聊天应用而言，其包含如下帧：CloseWebSocketFrame、PingWebSocketFrame、PongWebSocketFrame、TextWebSocketFrame。
            handleWebSocket(channelHandlerContext, (WebSocketFrame) msg);
        }
    }

    /**
     * 该方法,是IdleStateHandler实现心跳的关键,当在 server的pipline中添加了
     * new IdleStateHandler() 那么将会定期执行该方法
     * 根据不同的 IO idle 类型来产生不同的 IdleStateEvent 事件,
     * 在 userEventTriggered 中, 根据 IdleStateEvent 的 state() 的不同, 而进行不同的处理.
     * 例如如果是读取数据 idle, 则 e.state() == READER_IDLE, 因此就调用 handleReaderIdle 来处理它
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (event.state().equals(IdleState.READER_IDLE)) {
                final String remoteAddress = NettyUtil.parseChannelRemoteAddr(ctx.channel());
                log.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                ChatUserManager.removeChannel(ctx.channel());
                ChatUserManager.broadCastInfo(ChatCode.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            log.warn("protobuf don't support websocket");
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                Constants.WEBSOCKET_URL, null, true);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 动态加入websocket的编解码处理
            handshaker.handshake(ctx.channel(), request);
            ChatUser userInfo = new ChatUser();
            userInfo.setAddr(NettyUtil.parseChannelRemoteAddr(ctx.channel()));
            // 存储已经连接的Channel
            ChatUserManager.addChannel(ctx.channel());
        }
    }

    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路命令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            ChatUserManager.removeChannel(ctx.channel());
            return;
        }
        // 判断是否Ping消息
        if (frame instanceof PingWebSocketFrame) {
            log.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否Pong消息
        if (frame instanceof PongWebSocketFrame) {
            log.info("pong message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        // 本程序目前只支持文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame type not supported");
        }
        String message = ((TextWebSocketFrame) frame).text();
        JSONObject json = JSONObject.parseObject(message);
        int code = json.getInteger("code");
        Channel channel = ctx.channel();
        switch (code) {
            case ChatCode.PING_CODE:
            case ChatCode.PONG_CODE:
                ChatUserManager.updateUserTime(channel);
//                ChatUserManager.sendPong(ctx.channel());
                log.info("receive pong message, address: {}", NettyUtil.parseChannelRemoteAddr(channel));
                return;
            case ChatCode.AUTH_CODE:
                boolean isSuccess = ChatUserManager.saveUser(channel, json.getString("nick"));
                ChatUserManager.sendInfo(channel, ChatCode.SYS_AUTH_STATE, isSuccess);
                if (isSuccess) {
                    ChatUserManager.broadCastInfo(ChatCode.SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
                }
                return;
            case ChatCode.MESS_CODE: //普通的消息留给MessageHandler处理
                break;
            default:
                log.warn("The code [{}] can't be auth!!!", code);
                return;
        }
        //后续消息交给MessageHandler处理
        ctx.fireChannelRead(frame.retain());
    }


}