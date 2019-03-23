package cn.kanyun.cpa.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty5把ChannelInboundHandlerAdapter 和 ChannelOutboundHandlerAdapter 废弃了
 * 并整合为ChannelHandlerAdapter,ChannelHandlerAdapter现在包含输入和输出的处理方法
 * 其他变化：
 * 如果使用了SimpleChannelInboundHandler，你需要把channelRead0()重命名为messageReceived()。
 * 使用Executor代替ThreadFactory
 * 每个Channel现在有了全局唯一的ID  可通过Channel.id()方法获取Channel的ID。
 * 增加了新的ChannelHandlerInvoker接口，用于使用户可以选择使用哪个线程调用事件处理方法。替代之前的在向ChannelPipeline添加 ChannelHandler时指定一个EventExecutor的方式，使用该特性需要指定一个用户自定义的 ChannelHandlerInvoker实现。
 * PooledByteBufAllocator成为默认的allocator
 * Channel.deregister()已被移除。不再生效和被使用。取而代之的，我们将允许Channel被充注册到不同的事件循环。ChannelHandlerContext.attr(..) == Channel.attr(..)
 */
@Slf4j
public class TestNetty5Handler extends ChannelHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("TestNetty5Handler channelActive()");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("TestNetty5Handler channelRead() 接收到消息,消息类型是：{}", msg.getClass());
        log.info("TestNetty5Handler channelRead() 接收到消息：{}", msg.toString());
//        ByteBuf转字符串
        ByteBuf buf = (ByteBuf) msg;
        byte[] byteArray  = new byte[buf.readableBytes()];
        buf.readBytes(byteArray);
        String body = new String(byteArray, "UTF-8");
        log.info("收到消息：{}", body);
        ctx.channel().writeAndFlush("我收到了");
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("TestNetty5Handler exceptionCaught() 异常：{}", cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.writeAndFlush("TestNetty5Handler write()我收到了");
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}