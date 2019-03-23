package cn.kanyun.cpa.chat.core;

/**
 * 监听 接口
 * https://github.com/beyondfengyu/HappyChat
 */
public interface Server {

    /**
     * 启动监听
     */
    void start();

    /**
     * 关闭监听
     */
    void shutdown();

}