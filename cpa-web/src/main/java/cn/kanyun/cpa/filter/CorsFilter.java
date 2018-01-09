package cn.kanyun.cpa.filter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Title: 跨域访问处理(跨域资源共享)
 * @description 解决前后端分离架构中的跨域问题
 */

/** CorsFilter 将从 web.xml 中读取相关 Filter 初始化参数，并将在处理 HTTP 请求时将这些参数写入对应的 CORS 响应头中*/

/**
 * 需要注意的是，CORS 规范中定义 Access-Control-Allow-Origin 只允许两种取值，要么为 *，要么为具体的域名，
 * 也就是说，不支持同时配置多个域名。为了解决跨多个域的问题，需要在代码中做一些处理，这里将 Filter 初始化参数作为一个域名的集合（用逗号分隔），
 * 只需从当前请求中获取 Origin 请求头，就知道是从哪个域中发出的请求，若该请求在以上允许的域名集合中，
 * 则将其放入 Access-Control-Allow-Origin 响应头，这样跨多个域的问题就轻松解决了
 */

public class CorsFilter implements Filter {

    /**日志处理(@author: rico) */
    private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);

    /**Access-Control-Allow-Origin：允许访问的客户端域名，例如：http://web.xxx.com，若为 *，则表示从任意域都能访问，即不做任何限制；*/
    private String allowOrigin;
    /**Access-Control-Allow-Methods：允许访问的方法名，多个方法名用逗号分割，例如：GET,POST,PUT,DELETE,OPTIONS；*/
    private String allowMethods;
    /**Access-Control-Allow-Credentials：是否允许请求带有验证信息，若要获取客户端域下的 cookie 时，需要将其设置为 true；*/
    private String allowCredentials;
    /**Access-Control-Allow-Headers：允许服务端访问的客户端请求头，多个请求头用逗号分割，例如：Content-Type；*/
    private String allowHeaders;
    /**Access-Control-Expose-Headers：允许客户端访问的服务端响应头，多个响应头用逗号分割。*/
    private String exposeHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    /**
     * @description 通过CORS技术实现AJAX跨域访问,只要将CORS响应头写入response对象中即可
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String currentOrigin = request.getHeader("Origin");
        log.debug("currentOrigin : " + currentOrigin);
        if (StringUtils.isNotEmpty(allowOrigin)) {
            List<String> allowOriginList = Arrays
                    .asList(allowOrigin.split(","));
            log.debug("allowOriginList : " + allowOrigin);
            if (CollectionUtils.isNotEmpty(allowOriginList)) {
                if (allowOriginList.contains(currentOrigin)) {
                    response.setHeader("Access-Control-Allow-Origin",
                            currentOrigin);
                }
            }
        }
        if (StringUtils.isNotEmpty(allowMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (StringUtils.isNotEmpty(allowCredentials)) {
            response.setHeader("Access-Control-Allow-Credentials",
                    allowCredentials);
        }
        if (StringUtils.isNotEmpty(allowHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (StringUtils.isNotEmpty(exposeHeaders)) {
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}