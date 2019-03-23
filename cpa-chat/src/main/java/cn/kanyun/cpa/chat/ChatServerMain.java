package cn.kanyun.cpa.chat;

import cn.kanyun.cpa.chat.core.ChatServer;
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
            context.start();
//            ChatServer chatServer = new ChatServer();
            ChatServer chatServer = (ChatServer) context.getBean("chatServer");
            chatServer.start();
            log.info("====== chat server start success =====");
        } catch (Exception e) {
            log.info("====== chat server start fail =====");
            e.printStackTrace();
        }
    }
}