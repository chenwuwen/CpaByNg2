package cn.kanyun.cpa.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

@Slf4j
public class ClientOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        log.info("ClientOutHandler connect() running");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        log.info("ClientOutHandler disconnect() running");
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        log.info("ClientOutHandler close() running");
        super.close(ctx, promise);
    }

    /**
     * 发送消息时,该方法执行,该方法在flush()方法前执行,可以对消息进行加工
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("ClientOutHandler write() running");
        log.info("发送的消息类型：{}",msg.getClass());
        super.write(ctx, msg, promise);
    }

    /**
     * 发送消息时,该方法执行,该方法在write()方法后执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("ClientOutHandler flush() running");
        super.flush(ctx);
    }

    /**
     * ChannelOutboundHandler上的read方法
     * 是为了拦截ChannelHandlerContext.read()操作。也就是说，ChannelOutboundHandler可以通过read()方法
     * 在必要的时候阻止向inbound读取更多数据的操作。这个设计在处理协议的握手时非常有用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }
}