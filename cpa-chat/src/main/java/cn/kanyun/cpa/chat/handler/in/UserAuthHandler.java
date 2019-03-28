package cn.kanyun.cpa.chat.handler.in;

import cn.kanyun.cpa.chat.ChatUserManager;
import cn.kanyun.cpa.chat.Constants;
import cn.kanyun.cpa.chat.definition.ChatCodeEnum;
import cn.kanyun.cpa.chat.util.NettyUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import static cn.kanyun.cpa.chat.definition.ChatCodeEnum.SYS_USER_COUNT;
import static cn.kanyun.cpa.chat.definition.ChatCodeEnum.getChatCodeEnum;

@Slf4j
public class UserAuthHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("UserAuthHandler channelActive()");
        super.channelActive(ctx);
    }

    /**
     * 这里没有调用channelHandlerContext.fireChannelRead()方法是因为
     * 在handleWebSocket()方法中调用了
     * 该方法主要判断是HTTP请求还是WebSocket请求
     * 是HTTP请求时，就handHttpRequest()来进行处理，该方法判断是否有握手的倾向，
     * 如果不是WebSocket握手请求消息,那么直接返回HTTP 400 BAD REQUEST 响应给客户端，应答消息，并关闭链接。
     * 如果是握手请求，那么就进行握手,将WebSocket相关的编码和解码类动态添加到ChannelPipeline中
     * 是WebSocket则群发，服务端向每个连接上来的客户端群发消息
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        log.info(">>>>>>>>UserAuthHandler messageReceived()方法：判断消息类型：{}<<<<<<<", msg.getClass());
        if (msg instanceof FullHttpRequest) {  //传统的HTTP接入：第一次握手请求消息由HTTP协议承载，所以它是一个HTTP消息，执行handleHttpRequest方法来处理WebSocket握手请求。
            log.info("UserAuthHandler messageReceived()方法接收到的参数类型是【传统的HTTP接入】");
//            FullHttpRequest主要包含对HttpRequest和httpContent的组合
            handleHttpRequest(channelHandlerContext, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {  //WebSocket接入：客户端通过文本框提交请求消息给服务端，WebSocketServerHandler接收到的是已经解码后的WebSocketFrame消息。
            log.info("UserAuthHandler messageReceived()方法接收到的参数类型是【WebSocket接入】");
//            WebSocket定义了六种帧:对于聊天应用而言，其包含如下帧：CloseWebSocketFrame、PingWebSocketFrame、PongWebSocketFrame、TextWebSocketFrame。
            handleWebSocket(channelHandlerContext, (WebSocketFrame) msg);
        }
//        这个方法也是解决[netty refCnt: 0, decrement: 1]的报错的
//        这是因为Netty有引用计数器的原因，自从Netty 4开始，对象的生命周期由它们的引用计数（reference counts）管理，而不是由垃圾收集器（garbage collector）管理了。ByteBuf是最值得注意的，它使用了引用计数来改进分配内存和释放内存的性能。
//        在我们创建ByteBuf对象后，它的引用计数是1，当你释放（release）引用计数对象时，它的引用计数减1，如果引用计数为0，这个引用计数对象会被释放（deallocate）,并返回对象池。
//        当尝试访问引用计数为0的引用计数对象会抛出IllegalReferenceCountException异常
        ReferenceCountUtil.retain(msg);
        log.info("UserAuthHandler messageReceived()方法未找到合适的消息类型,Handler将终止向后传递");

    }

    /**
     * 该方法,是IdleStateHandler实现心跳的关键,当在 server的pipline中添加了
     * new IdleStateHandler() 那么将会定期执行该方法
     * 根据不同的 IO idle 类型来产生不同的 IdleStateEvent 事件,
     * 在 userEventTriggered 中, 根据 IdleStateEvent 的 state() 的不同, 而进行不同的处理.
     * 例如如果是读取数据 idle, 则 e.state() == READER_IDLE, 因此就调用 handleReaderIdle 来处理它
     *
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
                ChatUserManager.broadCastInfo(SYS_USER_COUNT, ChatUserManager.getAuthUserCount());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    /**
     * 处理客户端向服务端发起http握手请求的业务
     * 当用户打开网页客户端时,触发此方法
     * @param ctx
     * @param request
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
//         如果HTTP解码失败，返回HHTP异常
//        如果不是WebSocket握手请求消息，那么就返回 HTTP 400 BAD REQUEST 响应给客户端
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            log.warn("WebSocket请求异常");
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

//        握手成功之后，发送消息时会返回第一段代码，因此判断context的Attribut就可以分发路由了，给不同的handlerWebSocketFrame处理机制
//        HttpMethod method = request.method();
//        String uri = request.uri();
//        if (method== HttpMethod.GET &&"/webssss".equals(uri)) {
////            重点在这里，对于URL的不同，给ChannelHandlerContext设置一个Attribut
//            ctx.attr(AttributeKey.valueOf("type")).set("android");
//        } else if (method==HttpMethod.GET&&"/websocket".equals(uri)) {
//            ctx.attr(AttributeKey.valueOf("type")).set("live");
//        }

//        构造握手响应返回(注意，这条地址别被误导了，其实这里填写什么都无所谓，WS协议消息的接收不受这里控制)
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                Constants.WEBSOCKET_URL, null, true);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 动态加入WebSocket的编解码处理
            // 通过它构造握手响应消息返回给客户端，
            // 同时将WebSocket相关的编码和解码类动态添加到ChannelPipeline中，用于WebSocket消息的编解码，
            // 添加WebSocketEncoder和WebSocketDecoder之后，服务端就可以自动对WebSocket消息进行编解码了
            handshaker.handshake(ctx.channel(), request);
            // 存储已经连接的Channel
            ChatUserManager.addChannel(ctx.channel());
        }
    }

    /**
     * 处理客户端与服务端之前的websocket业务
     * <p>
     * (1) 第一次握手由HTTP协议承载，所以是一个HTTP消息，根据消息头中是否包含"Upgrade"字段来判断是否是websocket。
     * (2) 通过校验后，构造WebSocketServerHandshaker，通过它构造握手响应信息返回给客户端，同时将WebSocket相关的编码和解码类动态添加到ChannelPipeline中。
     * 下面分析链路建立之后的操作：
     * (1) 客户端通过文本框提交请求给服务端，Handler收到之后已经解码之后的WebSocketFrame消息。
     * (2) 如果是关闭按链路的指令就关闭链路
     * (3) 如果是维持链路的ping消息就返回Pong消息。
     * (4) 否则就返回应答消息
     *
     * @param ctx
     * @param frame
     */
    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路命令[如果已经关闭,直接返回]
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            ChatUserManager.removeChannel(ctx.channel());
            return;
        }
        // 判断是否Ping消息[如果是直接返回]
        if (frame instanceof PingWebSocketFrame) {
            log.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否Pong消息[如果是直接返回]
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
        ChatCodeEnum chatCode = getChatCodeEnum(code);
        Channel channel = ctx.channel();
//        switch case语句case后的枚举常量不带枚举类型,所以不能写成 ChatCodeEnum.PING_CODE
        switch (chatCode) {
//            当收到客户端的Ping消息或者Pong消息时,更新时间,以免通道被删
//            在ChatServer类中定义了一个定时Ping任务,当客户端收到Ping请求时,客户端会相应一个Pong请求,这个时候服务端,将客户端的响应,再响应回去,类似于三次握手
            case PING_CODE:
            case PONG_CODE:
                ChatUserManager.updateUserTime(channel);
                ChatUserManager.sendPong(ctx.channel());
                log.info("receive pong message, address: {}", NettyUtil.parseChannelRemoteAddr(channel));
                return;
            case AUTH_CODE:
//              认证：保存聊天用户(第二个参数是 聊天用户昵称)
                boolean isSuccess = ChatUserManager.saveUser(channel, json.getString("nick"));
//                返回认证结果信息
                ChatUserManager.sendInfo(channel, ChatCodeEnum.SYS_AUTH_STATE, isSuccess);
                if (isSuccess) {
//                    如果认证成功：广播消息(谁加入了聊天,可以再前端补全,服务端只发送名字)
                    ChatUserManager.broadCastInfo(SYS_USER_COUNT,  ChatUserManager.getUserInfo(channel).getNickName() );
                }
                return;
            case MESS_CODE: //普通的消息留给MessageHandler处理
                log.info(">>>>>>>>>正常聊天发送消息。。。。<<<<<<<<<");
                break;
            default:
                log.warn("The code [{}] can't be auth!!!", code);
                return;
        }
        //后续消息交给MessageHandler处理
        ctx.fireChannelRead(frame.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("UserAuthHandler exceptionCaught() 方法 抛出异常：{}", cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 服务端向客户端响应消息
     *
     * @param ctx
     * @param req
     * @param res
     */
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
                                  DefaultFullHttpResponse res) {

        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

}