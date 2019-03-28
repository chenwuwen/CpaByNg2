package cn.kanyun.cpa.chat.handler;

import cn.kanyun.cpa.chat.handler.in.MessageHandler;
import cn.kanyun.cpa.chat.handler.in.PlaceHolderHandler;
import cn.kanyun.cpa.chat.handler.in.StateHandler;
import cn.kanyun.cpa.chat.handler.in.UserAuthHandler;
import cn.kanyun.cpa.chat.handler.out.MarkMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;

/**
 * 在使用Handler的过程中，需要注意：
 *
 * 1、ChannelInboundHandler之间的传递，通过调用 ctx.fireChannelRead(msg) 实现；调用ctx.write(msg) 将传递到ChannelOutboundHandler。
 *
 * 2、ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。
 *
 * 3、ChannelOutboundHandler 在注册的时候需要放在最后一个ChannelInboundHandler之前，否则将无法传递到ChannelOutboundHandler。
 */

/**
 * ChannelInitializer的主要目的是为程序员提供了一个简单的工具，用于在某个Channel注册到EventLoop后，
 * 对这个Channel执行一些初始化操作。ChannelInitializer虽然会在一开始会被注册到Channel相关的pipeline里，
 * 但是在初始化完成之后，ChannelInitializer会将自己从pipeline中移除，不会影响后续的操作。
 * 对于排在第一位进方向的处理器(也就是Handler)来说,他接收的是字节流,需要先进行解码
 *
 * 使用场景：
 * a. 在ServerBootstrap初始化时，为监听端口accept事件的Channel添加ServerBootstrapAcceptor
 * b. 在有新链接进入时，为监听客户端read/write事件的Channel添加用户自定义的ChannelHandler
 */

/**
 * 封装ChannelInitializer,以免ChatServer类的start()方法过长
 */
public class ChatServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private EventLoopGroup loopGroup;

//    private SslContext context;


    public ChatServerChannelInitializer(EventLoopGroup loopGroup) {
        this.loopGroup = loopGroup;
    }
    /**
     * 当新连接accept的时候，这个方法会调用
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

//        SSLEngine engine = context.newEngine(socketChannel.alloc());

        socketChannel.pipeline().addLast(loopGroup);
//        在大多数情况下，SslHandler都是ChannelPipeline中的第一个ChannelHandler，只有当所有其他ChannelHandler将其逻辑应用于数据之后，才能进行加密操作
//        socketChannel.pipeline().addLast(new SslHandler(engine));
//        请求解码器
        socketChannel.pipeline().addLast(new HttpServerCodec());
//        将多个消息转换成单一的消息对象
        socketChannel.pipeline().addLast(new HttpObjectAggregator(65536));
//        支持异步发送大的码流，一般用于发送文件流
        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
        socketChannel.pipeline().addLast(new StringDecoder(Charset.defaultCharset()));
        socketChannel.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
//        编解码器配置(调用了JBoss的marshalling库) 必须放在pipline的最前头，否则会让信息发不出去
//        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
//        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
//        检测链路是否读空闲，也是Netty的心跳机制
        socketChannel.pipeline().addLast(new IdleStateHandler(60, 0, 0));

//        socketChannel.pipeline().addLast(new TestNetty5Handler());

//        ChannelInboundHandler 进方向的Handler,按注册先后顺序执行
        socketChannel.pipeline().addLast(new StateHandler());
//        处理握手和认证
        socketChannel.pipeline().addLast(new UserAuthHandler());
//        处理消息的发送
        socketChannel.pipeline().addLast(new MessageHandler());
//        丢弃消息
        socketChannel.pipeline().addLast(new DiscardServerHandler());

//        ChannelOutboundHandler 出方向的Handler,按注册先后顺序逆序执行,需要注意的是ChannelOutboundHandler 在注册的时候需要放在最后一个ChannelInboundHandler之前，否则将无法传递到ChannelOutboundHandler
        socketChannel.pipeline().addLast(new MarkMessageHandler());

//        占位Handler
        socketChannel.pipeline().addLast(new PlaceHolderHandler());
    }
}