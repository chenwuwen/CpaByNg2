package cn.kanyun.cpa.chat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 该类只重写一个方法,用来做心跳,具体周期与Client端定义的new IdleStateHandler() 有关
 */
@Slf4j
public class HeartBeatClientHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("===客户端的心跳机制===");
    }
}