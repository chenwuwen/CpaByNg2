package cn.kanyun.cpa.chat.handler.in;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
/**
 * 在使用Handler的过程中，需要注意：
 * 1、ChannelInboundHandler之间的传递，通过调用 ctx.fireChannelRead(msg) 实现；调用ctx.write(msg) 将传递到ChannelOutboundHandler。
 * 2、ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。
 * 3、ChannelOutboundHandler 在注册的时候需要放在最后一个ChannelInboundHandler之前，否则将无法传递到ChannelOutboundHandler。
 * 4、Handler的消费处理放在最后一个处理。
 */

/**
 * 当客户端连接到服务端时，会先执行channelActive方法，当有数据传递过来时，再执行channelRead
 */
@Slf4j
public class StateHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 需要编解码的才会去用messageReceived，一般都是使用ChannelRead来读取的
     * 泛型不匹配，不会调用messageReceived的
     * 没有做过任何的编码解码的泛型是 ByteBuf
     * 如果一个Handler中同时存在messageReceived()方法和channelRead()方法,则只会调用channelRead()方法
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info(">>>>>>> StateHandler messageReceived() 接受了消息：{}<<<<<<", o);
//        对于Netty报错:io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1 的解决方法：在合适的地方,补上 ByteBuf.retain(); 这个意思是 让netty的引用计数+1..报错的地方是因为想减1,但是没得减,也可以使用ReferenceCountUtil.retain(o)解决;
//        ByteBuf byteBuf = (ByteBuf) o;
//        byteBuf.retain();
//        调用ChannelHandlerContext的fireChannelRead方法,通知后续的ChannelHandler继续进行处理
//        在messageReceived()方法中,如果不显示的调用该方法,则Handler无法向下继续调用(会直接调用本Handler的channelReadComplete()方法)
        channelHandlerContext.fireChannelRead(o);
    }


    /**
     * 新客户端接入
     * 通道激活时触发，当客户端connect成功后，服务端就会接收到这个事件，从而可以把客户端的Channel记录下来，供后面复用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(">>>>>>>>>> {}：有新客户端接入：{} <<<<<<<<<<<", LocalDateTime.now(ZoneId.systemDefault()), ctx.channel().remoteAddress());
//        ctx.channel().writeAndFlush(">>>>>>>您已连接上服务器<<<<<<");
//        若把这一句注释掉将无法将event传递给下一个ClientHandler,该方法将调用 ctx.fireChannelRead方法将请求传递下去
        super.channelActive(ctx);
    }


    /**
     * 从通道中读取数据，也就是服务端接收客户端发来的数据，参数msg就是发来的信息，可以是基础类型，也可以是序列化的复杂对象
     *  但是这个数据在不进行解码时它是ByteBuf类型
     *  如果一个Handler中同时存在messageReceived()方法和channelRead()方法,则只会调用channelRead()方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info(">>>>>>>> StateHandler channelRead <<<<<<<<<<");
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] msgByte = new byte[buf.readableBytes()];
//        buf.readBytes(msgByte);
//        log.info(new String(msgByte,"UTF-8"));
//        super.channelRead(ctx, msg);
//    }


    /**
     * channelRead执行后触发
     * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info(">>>>>>>> StateHandler channelReadComplete <<<<<<<<<<");
        super.channelReadComplete(ctx);
    }


    /**
     * 客户端断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(">>>>>>>>>>{}：客户端断开连接。。。<<<<<<<<<<<", LocalDateTime.now());
        super.channelInactive(ctx);
    }

    /**
     * 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(">>>>>>>> 发生异常 。。。<<<<<<<<");
        log.error(cause.getMessage());
        ctx.close();
    }
}