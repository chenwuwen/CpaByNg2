package cn.kanyun.cpa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 构建可运行jar包：3中方式：
 * 1.使用Servlet容器运行（Tomcat、Jetty等）----不可取 缺点：增加复杂性（端口、管理）tomcat/jetty等占用端口,dubbo服务也需要端口
 * 浪费资源（内存）:单独启动tomcat,jetty占用内存大
 * 2.自建Main方法类来运行（spring容器） ----不建议（本地调试可用）缺点： Dobbo本身提供的高级特性没用上自已编写启动类可能会有缺陷【本例是该方式】
 * 3.使用Dubbo框架提供的Main方法类来运行（Spring容器）----建议使用 优点：框架本身提供（com.alibaba.dubbo.container.Main）可实现优雅关机
 *
 * 提供者以jar包运行是dubbo官方推荐的:
 * 1.服务容器是一个standalone的启动程序，因为后台服务不需要Tomcat或JBoss等Web容器的功能，如果硬要用Web容器去加载服务提供方，增加复杂性，也浪费资源。
 * 2.服务容器只是一个简单的Main方法，并加载一个简单的Spring容器，用于暴露服务。
 * 3.服务容器的加载内容可以扩展，内置了spring，jetty，logj等加载，可通过Container扩展点进行扩展
 * <p>
 * 提供者以jar包方式运行,在本地调试可以用main方法运行,部署的时候就只能是打成jar包了.
 * 使用maven来配置,使用默认的Spring Container (自动加载META-INF/spring目录下的所有Spring配置。)
 *
 * @author Administrator
 * @date 2018/8/2
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
//            加载配置文件,是加载该Module内的配置文件,所以打包部署需要将配置文件复制到本地Module中
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:*.xml");
            context.start();
            log.info("=========cpa-service已启动=========");
        } catch (BeansException e) {
            log.info("=========cpa-service启动失败========= \n {}", e);
        }
    }
}
