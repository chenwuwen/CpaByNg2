package cn.kanyun.cpa.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author Kanyun
 * @date 2018/2/28 0028
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    @Lazy
    private SimpMessagingTemplate template;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // 此处与客户端的 URL 相对应
        webSocketHandlerRegistry.addHandler(logWebSocketHandler(), "/log");
    }

    @Bean
    public WebSocketHandler logWebSocketHandler() {
        return new LogWebSocketHandler(template);
    }
}
