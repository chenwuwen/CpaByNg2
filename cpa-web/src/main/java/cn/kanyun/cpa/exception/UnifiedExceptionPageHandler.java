package cn.kanyun.cpa.exception;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/7/17.
 * 统一异常处理,返回ModelAndView [即返回页面]
 * 需要注意的是，添加注解,同时此类可以被spring扫描到
 * 此项目不需要使用
 */
@Component
public class UnifiedExceptionPageHandler implements HandlerExceptionResolver {

    /**
     *
     * 使用ModelAndView类用来存储处理完后的结果数据，以及显示该数据的视图。从名字上看ModelAndView中的Model代表模型，View代表视图，
     * 这个名字就很好地解释了该类的作用。业务处理器调用模型层处理完用户请求后，把结果数据存储在该类的model属性中，
     * 把要返回的视图信息存储在该类的view属性中，然后让该ModelAndView返回该Spring MVC框架。框架通过调用配置文件中定义的视图解析器，
     * 对该对象进行解析，最后把结果数据显示在指定的页面上。
     * 具体作用：
     * 1、返回指定页面
     * ModelAndView构造方法可以指定返回的页面名称，也可以通过setViewName()方法跳转到指定的页面
     * 2、返回所需数值
     * 使用addObject()设置需要返回的值，addObject()有几个不同参数的方法，可以默认和指定返回对象的名字。
     * ModelAndView的构造方法有7个。但是它们都是相通的
     * ModelAndView更多资料 https://blog.csdn.net/qq30211478/article/details/78016155
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return ModelAndView
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @Nullable Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return null;
    }
}
