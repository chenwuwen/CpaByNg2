package cn.kanyun.cpa.chat.core;

import cn.kanyun.cpa.chat.ChatUserManager;
import cn.kanyun.cpa.chat.handler.ChatServerChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Netty中包含下面几个主要的组件：
 * Bootstrap：netty的组件容器，用于把其他各个部分连接起来；如果是TCP的Server端，则为ServerBootstrap.
 * Channel：代表一个Socket的连接
 * EventLoopGroup：一个Group包含多个EventLoop，可以理解为线程池
 * EventLoop：处理具体的Channel，一个EventLoop可以处理多个Channel
 * ChannelPipeline：每个Channel绑定一个pipeline，在上面注册处理逻辑handler
 * Handler：具体的对消息或连接的处理，有两种类型，Inbound和Outbound。分别代表消息接收的处理和消息发送的处理。
 * ChannelFuture：注解回调方法
 */
@Slf4j
@Component
@Scope("singleton")
public class ChatServer extends BaseServer {

    /**
     * ScheduledExecutorService,是基于线程池设计的定时任务类。同时也是Timer类的替代品
     * 主要原因例如以下：
     * 1.Timer不支持多线程。全部挂在Timer下的任务都是单线程的，任务仅仅能串行运行。假设当中一个任务运行时间过长。会影响到其它任务的运行，然后就可能会有各种接踵而来的问题。
     * 2.Timer的线程不捕获异常。TimerTask假设抛出异常，那么Timer唯一的进程就会挂掉，这样挂在Timer下的全部任务都会无法继续运行。
     * ScheduledExecutorService。详细实现类是：ScheduledThreadPoolExecutor。ScheduledThreadPoolExecutor支持多线程。同一时候在线程中对异常进行了捕获。
     * ScheduledExecutorService,每个调度任务都会分配到线程池中的一个线程去执行,也就是说,任务是并发执行,互不影响。
     * 需要注意,只有当调度任务来的时候,ScheduledExecutorService才会真正启动一个线程,其余时间ScheduledExecutorService都是处于轮询任务的状态。
     * ScheduledExecutorService 中两种最常用的调度方法 ScheduleAtFixedRate 和 ScheduleWithFixedDelay
     * ScheduleAtFixedRate 每次执行时间为上一次任务开始起向后推一个时间间隔，即每次执行时间为 :initialDelay, initialDelay+period, initialDelay+2*period, …；
     * ScheduleWithFixedDelay 每次执行时间为上一次任务结束起向后推一个时间间隔，即每次执行时间为：initialDelay, initialDelay+executeTime+delay, initialDelay+2*executeTime+2*delay。
     * 由此可见，ScheduleAtFixedRate 是基于固定时间间隔进行任务调度，ScheduleWithFixedDelay 取决于每次任务执行的时间长短，是基于不固定时间间隔进行任务调度。
     */
    private ScheduledExecutorService executorService;

    public ChatServer() {
//      创建两个线程的线程池
        executorService = Executors.newScheduledThreadPool(2);
    }

    @Override
    public void start() {
//        绑定线程组[设置为:Reactor主从多线程模型.还有另外两种Reactor模型,分别是:多线程模型,单线程模型]
        b.group(bossGroup, workGroup)
                // 设置Netty通信模式为NIO,同步非阻塞模式,也可以设置成同步阻塞模式[OioServerSocketChannel.class]
                .channel(NioServerSocketChannel.class)
//                这几个option项,netty依赖要选5版本的,选其他版本会报警告(Unknown channel option 'SO_SNDBUF' for channel )，并且服务端接受不到客户端数据，因为4版本的不能重写channelRead和exceptionCaught方法
//                option参数说明：https://blog.csdn.net/u011186019/article/details/78733382
//                开启心跳监测(保证连接有效)
                .option(ChannelOption.SO_KEEPALIVE, true)
//                自动读取,默认值为True,为True时，每次读操作完毕后会自动调用channel.read()，从而有数据到达便能读取；否则，需要用户手动调用channel.read()[在channelActive方法中]
                .option(ChannelOption.AUTO_READ, true)
//              禁用Nagle算法:如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false。
                .option(ChannelOption.TCP_NODELAY, true)
//                设定缓冲区大小,缓存区的单位是字节
                .option(ChannelOption.SO_BACKLOG, 1024)
//                接收缓冲区
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
//                 发送缓冲区
                .option(ChannelOption.SO_SNDBUF, 16 * 1024)
//                 返回channel绑定的本地地址，remoteAddress()返回与Channel连接的远程地址
                .localAddress(new InetSocketAddress(port))
//               进行Netty数据处理的过滤器配置（责任链设计模式）添加handler，用来监听已经连接的客户端的Channel的动作和状态,handler在初始化时就会执行，而childHandler会在客户端成功connect后才执行，这是两者的区别。
//               Netty中的所有handler都实现自ChannelHandler接口。按照输出输出来分，分为ChannelInboundHandler、ChannelOutboundHandler两大类。ChannelInboundHandler对从客户端发往服务器的报文进行处理，一般用来执行解码、读取客户端数据、进行业务处理等；ChannelOutboundHandler对从服务器发往客户端的报文进行处理，一般用来进行编码、发送报文到客户端。
//               Netty中，可以注册多个handler。ChannelInboundHandler按照注册的先后顺序(即书写的顺序呢)执行；ChannelOutboundHandler按照注册的先后顺序逆序执行
                .childHandler(new ChatServerChannelInitializer(defLoopGroup));

        try {
//            bind方法会创建一个serverChannel,并且会将当前的channel注册到bossGroup上面,会为其绑定本地端口，并对其进行初始化，sync() 异步线程处理
//            bind() 如果参数为空(且上面的localAddress也没有指定端口),会随机指定端口（异步绑定服务器，调用sync()方法阻塞等待直到绑定完成）
            cf = b.bind().sync();

            InetSocketAddress address = (InetSocketAddress) cf.channel().localAddress();
            log.info("WebSocketServer start success, port is:{}", address.getPort());

            // 定时扫描所有的Channel，关闭失效的Channel [从现在开始3秒钟之后，每隔60秒钟执行一次run方法(这里指关闭Channel)]
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    log.info(">>>>>>> 定时关闭不活跃的Channel <<<<<<<<<<");
                    ChatUserManager.scanNotActiveChannel();
                }
            }, 3, 60, TimeUnit.SECONDS);

            // 定时向所有客户端发送Ping消息,初始化延时3秒,前一次执行完成后的第50秒 再执行
            executorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    log.info(">>>>>>> 定时ping客户端 <<<<<<<<<<");
                    ChatUserManager.broadCastPing();
                }
            }, 3, 50, TimeUnit.SECONDS);

//          等待服务端监听端口关闭 [阻塞]
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("WebSocketServer start fail: {}", e);
            // 关闭boss线程池
            bossGroup.shutdownGracefully() ;
            // 关闭work线程池
            workGroup.shutdownGracefully() ;
        }
    }

    @Override
    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
        }
        super.shutdown();
    }
}