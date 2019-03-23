package cn.kanyun.cpa.chat.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty服务抽象类
 */
@Slf4j
public abstract class BaseServer implements Server {

    /**
     * 服务端口
     */
    protected int port;

    private Properties props;

    /**
     * EventLoop定义了Netty的核心抽象，用于处理连接的生命周期中所发生的事件。
     * 一个EventLoopGroup包含一个或者多个EventLoop；
     * 一个EventLoop在它的生命周期内只和一个Thread绑定；
     * 所有由EventLoop处理得I/O事件都将在它专有的Thread上处理
     * 一个Channel在它的生命周期内只注册一个EventLoop；
     * 一个EventLoop可能会被分配给一个或多个Channel。
     */
    protected DefaultEventLoopGroup defLoopGroup;

    /**
     * 创建两个EventLoop的组，EventLoop 这个相当于一个处理线程，
     * 是Netty接收请求和处理IO请求的线程。不理解的话可以百度NIO图解
     * NioEventLoopGroup是一个处理I/O操作的多线程事件循环
     */

    /**
     * 第一个，通常称为“boss”，接受传入的连接
     */
    protected NioEventLoopGroup bossGroup;
    /**
     * 第二个，通常称为“worker”，当boss接受连接并注册被接受的连接到worker时，处理被接受连接的流量。
     * 使用了多少线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置
     */
    protected NioEventLoopGroup workGroup;
    protected NioServerSocketChannel ssch;
    /**
     * 异步通知
     * Netty所有的I/O操作都是异步的。因为一个操作可能不会立即返回，
     * 所以我们需要一种用于在之后得某个时间点确定其结果的方法。
     * 为此，Netty提供了ChannelFuture接口，其addListener()方法注册了一个ChannelFutureListener，
     * 以便在某个操作完成时（无论是否成功）得到通知。
     */
    protected ChannelFuture cf;

    /**
     * ServerBootstrap负责初始化netty服务器(客户端使用Bootstrap初始化)，并且开始监听端口的socket请求
     * ServerBootstrap用一个ServerSocketChannelFactory 来实例化。
     * ServerSocketChannelFactory 有两种选择，一种是NioServerSocketChannelFactory，一种是OioServerSocketChannelFactory。
     * 前者使用NIO，后则使用普通的阻塞式IO。它们都需要两个线程池实例作为参数来初始化，一个是boss线程池，一个是worker线程池。
     * ServerBootstrap.bind(int)负责绑定端口，当这个方法执行后，ServerBootstrap就可以接受指定端口上的socket连接了。一个ServerBootstrap可以绑定多个端口
     * https://blog.csdn.net/sou_time/article/details/72817916
     */
    protected ServerBootstrap b;

    /**
     * 构造方法,创建该类实例(或者其子类)时[该类是抽象类,不能使用new创建对象。因为调用抽象方法没意义],即运行init()方法
     */
    public BaseServer() {
        log.info(">>>>server port：{}<<<<<<<", port);
        init();
    }

    public void init() {
        try {
//            加载配置文件中的端口号
            props = org.springframework.core.io.support.PropertiesLoaderUtils.loadAllProperties("netty-config.properties");
            port = Integer.parseInt(props.get("netty.server-port").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        defLoopGroup = new DefaultEventLoopGroup(8, Executors.newSingleThreadExecutor(new ThreadFactory() {

            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "DEFAULT_EVENT_LOOP_GROUP_" + index.incrementAndGet());
            }
        }));
//        初始化线程组,构建线程组的时候,如果不传入参数,则默认线程数量为CPU数量
        bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), Executors.newSingleThreadExecutor(new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "BOSS_" + index.incrementAndGet());
            }
        }));
//          初始化线程组,构建线程组的时候,如果不传入参数,则默认线程数量为CPU数量
        workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 10, Executors.newSingleThreadExecutor(new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        }));

        b = new ServerBootstrap();
    }

    @Override
    public void shutdown() {
        if (defLoopGroup != null) {
            defLoopGroup.shutdownGracefully();
        }
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}