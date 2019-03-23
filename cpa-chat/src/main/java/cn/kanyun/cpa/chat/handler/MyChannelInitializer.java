package cn.kanyun.cpa.chat.handler;

import cn.kanyun.cpa.chat.MarshallingCodeCFactory;
import cn.kanyun.cpa.chat.handler.in.MessageHandler;
import cn.kanyun.cpa.chat.handler.in.StateHandler;
import cn.kanyun.cpa.chat.handler.in.UserAuthHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private EventLoopGroup loopGroup;

    public MyChannelInitializer() {
    }

    public MyChannelInitializer(EventLoopGroup loopGroup) {
        this.loopGroup = loopGroup;
    }
    /**
     * 当新连接accept的时候，这个方法会调用
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        socketChannel.pipeline().addLast(loopGroup);

//        请求解码器
        socketChannel.pipeline().addLast(new HttpServerCodec());
//        将多个消息转换成单一的消息对象
        socketChannel.pipeline().addLast(new HttpObjectAggregator(65536));
//        支持异步发送大的码流，一般用于发送文件流
        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
//        编解码器配置(调用了JBoss的marshalling库) 必须放在pipline的最前头，否则会让信息发不出去
        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
//        检测链路是否读空闲，也是Netty的心跳机制
        socketChannel.pipeline().addLast(new IdleStateHandler(60, 0, 0));

        socketChannel.pipeline().addLast(new TestNetty5Handler());
//        ChannelInboundHandler 进方向的Handler,按注册先后顺序执行
        socketChannel.pipeline().addLast(new StateHandler());
//        处理握手和认证
        socketChannel.pipeline().addLast(new UserAuthHandler());
//        处理消息的发送
        socketChannel.pipeline().addLast(new MessageHandler());
//        丢弃消息
        socketChannel.pipeline().addLast(new DiscardServerHandler());
    }
}