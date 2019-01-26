package cn.kanyun.cpa.listenner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TomcatListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("当前工作目录路径为：" + sce.getServletContext().getRealPath("/"));
        System.out.println("tomcat启动了..........");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("tomcat关闭了..............");
    }
}
