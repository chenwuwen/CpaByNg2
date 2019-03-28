package cn.kanyun.cpa.chat;

import cn.kanyun.cpa.chat.core.ChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Spring 启动监听器
 * 当spring启动时启动Netty
 */
@Slf4j
@Component
public class StartChatServer implements InitializingBean {

    @Autowired
    private ChatServer chatServer;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("==== Spring 启动了========");
        log.info("==== 启动 ChatServer========");
        chatServer.start();
    }
}