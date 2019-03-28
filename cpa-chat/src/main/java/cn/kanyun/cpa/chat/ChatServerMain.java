package cn.kanyun.cpa.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 聊天服务启动类(主类)
 */
@Slf4j
public class ChatServerMain {
    public static void main(String[] args) {
        try {
//          加载配置文件,是加载该Module内的配置文件,所以打包部署需要将配置文件复制到本地Module中
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-netty.xml");
//            StartChatServer类实现了Spring的InitializingBean监听器,可以在Spring启动时自动启动ChatServer,之所以使用监听器这种方式,是因为这种方式可以在集成Web环境下运行也可以单独模块化运行,而下面的start()方法,可以不执行,当执行上面的ClassPathXmlApplicationContext构造方法时Spring就已经启动了
            context.start();
            log.info("====== Spring start success =====");
//            ChatServer chatServer = new ChatServer();
//            ChatServer chatServer = (ChatServer) context.getBean("chatServer");
//            chatServer.start();
//            log.info("====== chat server start success =====");
//             注册进程钩子，在JVM进程关闭前释放资源
//            Runtime.getRuntime().addShutdownHook(new Thread() {
//                @Override
//                public void run() {
//                    chatServer.shutdown();
//                    log.warn(">>>>>>>>>> jvm shutdown");
//                    System.exit(0);
//                }
//            });
        } catch (Exception e) {
//            log.info("====== chat server start fail =====");
            log.info("====== Spring start fail =====");
            e.printStackTrace();
        }
    }
}