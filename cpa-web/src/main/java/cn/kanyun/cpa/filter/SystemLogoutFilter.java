package cn.kanyun.cpa.filter;

import cn.kanyun.cpa.util.WebUtil;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 此过滤是对Shiro的logout过滤器的补充,shiro自带的logout过滤器只能清除shiro管理的session,
 * 通过继承shiro的logout过滤器，可以实现自定义的操作，如清除shiro的session和容器的session
 * 定义过滤器后，要在shiro的配置文件中，更改Logout过滤器的类
 * 需要通过@Service注解，使用spring容器来管理，在spring-shiro.xml中配置shiro过滤器直接使用
 */
@Service
public class SystemLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据(shiro的数据)
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);

        try {

            subject.logout();

        } catch (SessionException ise) {
            ise.printStackTrace();
        } finally {
            //这是自定义的操作,清除手动保存在容器里的session
            WebUtil.removeSessionUser((HttpServletRequest) request);
        }

//        注销后重定向到哪个页面,redirectUrl在shiro的配置文件中定义
        this.issueRedirect(request, response, redirectUrl);
        //返回false表示不执行后续的过滤器，直接返回跳转到登录页面
        return false;
    }
}
