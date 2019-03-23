package cn.kanyun.cpa.chat;

import cn.kanyun.cpa.chat.entity.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ChatClient {

    public EventLoopGroup work;

    public Bootstrap bootstrap;

    public Properties props;

    public int port;

    public ChatClient() {
        init();
    }

    public void init() {
        bootstrap = new Bootstrap();
        work = new NioEventLoopGroup();
        try {
//            加载配置文件中的端口号
            props = org.springframework.core.io.support.PropertiesLoaderUtils.loadAllProperties("netty-config.properties");
            port = Integer.parseInt(props.get("netty.server-port").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动
     */
    public void start() {
        try {
            bootstrap.group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
                            //这里一定要加入这两个类，是用来给object编解码的，如果没有就无法传送对象
                            //并且，实体类要实现Serializable接口，否则也无法传输
                            channel.pipeline().addLast(new ObjectEncoder());
                            channel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.weakCachingConcurrentResolver(null)));
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new StringEncoder());
//                            channel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
//                            channel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            channel.pipeline().addLast(new HeartBeatClientHandler());
                            channel.pipeline().addLast(new ClientInHandler());
                            channel.pipeline().addLast(new ClientOutHandler());

                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();

//            ChannelHandlerContext的writeAndFlush是从当前handler直接发出这个消息，而channel的writeAndFlush是从整个pipline最后一个outhandler发出
//            future.channel().writeAndFlush("XXXXXXXXXX");
//            future.channel().closeFuture().sync();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                log.info("请输入聊天内容：");
                String msg = reader.readLine();
                Message message = Message.builder().nickName("看云").sendTime(LocalDateTime.now()).content(msg).build();
                future.channel().writeAndFlush(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            work.shutdownGracefully();
        }
    }
}