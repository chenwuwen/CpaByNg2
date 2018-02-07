package cn.kanyun.cpa.filter;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.util.WebUtil;
import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 此过滤器是用来判断用户登录状态的,如果请求URI中包含"userLoginState" ,则检测用户是否是登录状态
 * 然后进行返回JSON（此处是判断Session是否存在，也可以换成Token的方式），需要注意的是Controller
 * 方法内是没有这个"userLoginState" URI的，所以其他URI会进入到后续程序中，通过AOP判断登录状态
 * 需要注意的是返回的CpaResult对象需要重写toString方法,然而写了toString()方法之后返回到前台发现
 * Json的key没有双引号,导致js解析异常(原因是在自定义的toString()方法里没有对key做处理)，所以先
 * 将对象转换为Json对象，再把Json对象转换为字符串,在进行输出(详见54-56行代码)。
 */

/**
 * (1)getOutputStream和getWriter方法分别用于得到输出二进制数据、输出文本数据的ServletOuputStream、Printwriter对象。
 * (2)getOutputStream和getWriter这两个方法互相排斥，调用了其中的任何一个方法后，就不能再调用另一方法。
 * (3)Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎从response里面获取，Servlet引擎将这些数据当作响应消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。
 * (4)Serlvet的service方法结束后，Servlet引擎将检查getWriter或getOutputStream方法返回的输出流对象是否已经调用过close方法，如果没有，Servlet引擎将调用close方法关闭该输出流对象
 */
public class UserLoginStatusFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        if (path.indexOf("userLoginState") > 1) {
            CpaUser user = WebUtil.getSessionUser(req);
            CpaResult result = new CpaResult();
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json; charset=utf-8");
            if (user != null) {
                result.setStatus(CpaConstants.USER_HAS_LOGIN);
                result.setMsg("用户已登录");
                JSONObject json = JSONObject.fromObject(result);
                res.getOutputStream().write(json.toString().getBytes());
            } else {
                result.setStatus(CpaConstants.USER_NOT_LOGIN);
                result.setMsg("用户未登录");
                JSONObject json = JSONObject.fromObject(result);
                System.out.println(json.toString());
                System.out.println("==============");
                System.out.println(result.toString());
                res.getOutputStream().write(json.toString().getBytes());
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
