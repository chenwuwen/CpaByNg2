package cn.kanyun.cpa.listenner;

import cn.kanyun.cpa.model.constants.CpaConstants;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

/**
 * 使用HttpSessionListener接口可监听session的创建和失效
 * session是在用户第一次访问页面时创建
 * 在session超时或调用request.getSession().invalidate()时失效
 * 因此利用HttpSessionListener接口可方便的做到几个功能
 * 1、统计在线用户
 * 2、限定账号的同时登录个数
 * 3、记录用户退出时间
 * 注意事项:
 * 1、一个浏览器只能创建一个session对象，也就是说多用户会覆盖session。
 * 2、同一用户不用浏览器登录，会产生多个session，这时候需要判断用户是否已登录，将新session替换就session。
 */

/**
 * Servlet3.0中的监听器跟之前2.5的差别不大，唯一的区别就是增加了对注解的支持。在3.0以前我们的监听器配置都是需要配置在web.xml文
 * 件中的。在3.0中我们有了更多的选择，之前在web.xml文件中配置的方式还是可以的，同时我们还可以使用注解进行配置。对于使用注解的
 * 监听器就是在监听器类上使用@WebListener进行标注，这样Web容器就会把它当做一个监听器进行注册和使用了。但需要主要的是使用注解的
 * 方式需要在web.xml的头部加上  " metadata-complete="false"
 */
@WebListener
public class OnLineUserListenner implements HttpSessionListener {

    /**
     * Session创建
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext context = session.getServletContext();
        //用set集合来存储session对象
        HashSet<HttpSession> sessionSet = (HashSet<HttpSession>) context.getAttribute(CpaConstants.ONLINE_USER);
        if (sessionSet == null) {
            sessionSet = new HashSet<HttpSession>();
            context.setAttribute(CpaConstants.ONLINE_USER, sessionSet);
        }
        //这里主要是为了检验用户是否登录，登录的话强制移除该session，加入新session
        for (HttpSession s : sessionSet) {
            if (session.getAttribute(CpaConstants.USER) == s.getAttribute(CpaConstants.ONLINE_USER)) {
                sessionSet.remove(s);
            }
        }
        sessionSet.add(session);
        //存储在线人数，利用了set集合不重复的特性，避免了重复登录
        context.setAttribute(CpaConstants.ONLINE_USER_COUNT, sessionSet.size());
    }

    /**
     * Session关闭(通过注销功能实现的Session关闭，而不是tomcat关闭)
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        ServletContext context = se.getSession().getServletContext();
        if (context.getAttribute(CpaConstants.ONLINE_USER_COUNT) == null) {
            context.setAttribute(CpaConstants.ONLINE_USER_COUNT, 0);
        } else {
            int lineCount = (Integer) context.getAttribute(CpaConstants.ONLINE_USER_COUNT);
            if (lineCount < 1) {
                lineCount = 1;
            }
            context.setAttribute(CpaConstants.ONLINE_USER_COUNT, lineCount - 1);
        }
        HttpSession session = se.getSession();
        HashSet<HttpSession> sessionSet = (HashSet<HttpSession>) context.getAttribute(CpaConstants.ONLINE_USER);
        if (sessionSet != null) {
            sessionSet.remove(session);
        }

    }
}
