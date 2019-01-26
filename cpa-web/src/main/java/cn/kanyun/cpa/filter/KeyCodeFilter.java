package cn.kanyun.cpa.filter;

import cn.kanyun.cpa.util.HttpCharacterResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * 敏感词过滤器
 */
public class KeyCodeFilter implements Filter {

    private Properties pp = new Properties();

    /**
     * 非法词、敏感词、特殊字符、配置在初始化参数中
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //配置文件位置
        String file = filterConfig.getInitParameter("file");
        //文件实际位置
        String realPath = filterConfig.getServletContext().getRealPath(file);
        try {
            //加载非法词
            pp.load(new FileInputStream(realPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        //过滤编码
        if (rq.getMethod().equalsIgnoreCase("post")) {
            rq.setCharacterEncoding("utf-8");
        } else {
            Iterator its = rq.getParameterMap().values().iterator();
            while (its.hasNext()) {
                String[] params = (String[]) its.next();
                int len = params.length;
                for (int i = 0; i < len; i++) {
                    params[i] = new String(params[i].getBytes("utf-8"), "utf-8");
                }
            }
        }
        //过滤客户端提交表单中特殊字符
        Iterator its = rq.getParameterMap().values().iterator();
        while (its.hasNext()) {
            String[] params = (String[]) its.next();
            for (int i = 0; i < params.length; i++) {
                for (Object oj : pp.keySet()) {
                    String key = (String) oj;
                    params[i] = params[i].replace(key, pp.getProperty(key));
                }
            }
        }

        //过滤服务器端的特殊字符（服务器端response输出到客户端的特殊汉字（色情、情色、赌博等））
        servletResponse.setCharacterEncoding("utf-8");
        HttpCharacterResponseWrapper rs = new HttpCharacterResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(rq, rs);
        //得到response输出内容
        String output = rs.getCw().toString();
        //遍历所有敏感词
        for (Object oj : pp.keySet()) {
            String key = (String) oj;
            //替换敏感词
            output = output.replace(key, pp.getProperty(key));
        }
        //通过原来的response输出内容
        servletResponse.getWriter().print(output);
    }


    @Override
    public void destroy() {

    }
}
