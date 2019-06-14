package cn.kanyun.cpa.exception.handle;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.terracotta.modules.ehcache.async.exceptions.ProcessingException;

/**
 * Created by Administrator on 2018/7/17.
 * Spring 统一异常处理有 3 种方式，分别为：
 * 1.使用 @ExceptionHandler 注解
 * 2.实现 HandlerExceptionResolver 接口
 * 3.使用 @ControllerAdvice 注解
 * A
 * 使用 @ ExceptionHandler 注解，有一个不好的地方就是：进行异常处理的方法必须与出错的方法在同一个Controller里面使用如下：
 * 可以看到，这种方式最大的缺陷就是不能全局控制异常。每个类都要写一遍。
 * <p>
 * 使用 @ControllerAdvice+ @ ExceptionHandler 注解
 * 上文说到 @ ExceptionHandler 需要进行异常处理的方法必须与出错的方法在同一个Controller里面。
 * 那么当代码加入了 @ControllerAdvice，则不需要必须在同一个 controller 中了。这也是 Spring 3.2 带来的新特性。
 * 从名字上可以看出大体意思是控制器增强。 也就是说，@controlleradvice + @ ExceptionHandler 也可以实现全局的异常捕捉。
 *
 * @ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度
 * @ControllerAdvice：异常集中处理，更好的使业务逻辑与异常处理剥离开
 * @ResponseStatus：可以将某种异常映射为HTTP状态码
 * @ExceptionHandler 该注解作用对象为方法，并且在运行时有效，value()可以指定异常类。由该注解注释的方法可以具有灵活的输入参数
 * 异常参数：包括一般的异常或特定的异常（即自定义异常），如果注解没有指定异常类，会默认进行映射
 * 请求或响应对象 (Servlet API or Portlet API)： 你可以选择不同的类型，如ServletRequest/HttpServletRequest或PortleRequest/ActionRequest/RenderRequest。
 * Session对象(Servlet API or Portlet API)： HttpSession或PortletSession
 * WebRequest或NativeWebRequest
 * Locale
 * InputStream/Reader
 * OutputStream/Writer
 * Model
 * 方法返回值可以为：
 * ModelAndView对象
 * Model对象
 * Map对象
 * View对象
 * String对象
 * 还有@ResponseBody、HttpEntity<?>或ResponseEntity<?>，以及void
 */

/**A
 * 使用 @ ExceptionHandler 注解，有一个不好的地方就是：进行异常处理的方法必须与出错的方法在同一个Controller里面使用如下：
 * 可以看到，这种方式最大的缺陷就是不能全局控制异常。每个类都要写一遍。
 */

//@Controller
//public class GlobalController {
//
//    *//**
//     * 用于处理异常的
//     * @return
//     *//*
//    @ExceptionHandler({MyException.class})
//    public String exception(MyException e) {
//        System.out.println(e.getMessage());
//        e.printStackTrace();
//        return "exception";
//    }
//
//    @RequestMapping("test")
//    public void test() {
//        throw new MyException("出错了！");
//    }
//}


/**
 * 使用 @ControllerAdvice+ @ ExceptionHandler 注解
 * 上文说到 @ ExceptionHandler 需要进行异常处理的方法必须与出错的方法在同一个Controller里面。
 * 那么当代码加入了 @ControllerAdvice，则不需要必须在同一个 controller 中了。这也是 Spring 3.2 带来的新特性。
 * 从名字上可以看出大体意思是控制器增强。 也就是说，@controlleradvice + @ ExceptionHandler 也可以实现全局的异常捕捉。
 */

/**
 * @ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度
 * @ControllerAdvice：异常集中处理，更好的使业务逻辑与异常处理剥离开
 * @ResponseStatus：可以将某种异常映射为HTTP状态码
 */

/**
 * @ExceptionHandler 该注解作用对象为方法，并且在运行时有效，value()可以指定异常类。由该注解注释的方法可以具有灵活的输入参数
 * 异常参数：包括一般的异常或特定的异常（即自定义异常），如果注解没有指定异常类，会默认进行映射
 * 请求或响应对象 (Servlet API or Portlet API)： 你可以选择不同的类型，如ServletRequest/HttpServletRequest或PortleRequest/ActionRequest/RenderRequest。
 * Session对象(Servlet API or Portlet API)： HttpSession或PortletSession
 * WebRequest或NativeWebRequest
 * Locale
 * InputStream/Reader
 * OutputStream/Writer
 * Model
 * 方法返回值可以为：
 * ModelAndView对象
 * Model对象
 * Map对象
 * View对象
 * String对象
 * 还有@ResponseBody、HttpEntity<?>或ResponseEntity<?>，以及void
 */

/**
 * @ControllerAdvice 该注解作用对象为TYPE，包括类、接口和枚举等，在运行时有效，并且可以通过Spring扫描为bean组件。
 * 其可以包含由@ExceptionHandler、@InitBinder 和@ModelAttribute标注的方法，
 * 可以处理多个Controller类，这样所有控制器的异常可以在一个地方进行处理。
 */

@ControllerAdvice
public class UnifiedExceptionJsonHandler {

    private static final Logger logger = LoggerFactory.getLogger(UnifiedExceptionJsonHandler.class);

    /**
     * Http状态码详解：http://tool.oschina.net/commons?type=5
     */

    /**
     * 400 - Bad Request
     * 1、语义有误，当前请求无法被服务器理解。除非进行修改，否则客户端不应该重复提交这个请求。
     * 2、请求参数有误。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public CpaResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        CpaResult result = new CpaResult();
        result.setState(CpaConstants.OPERATION_ERROR);
        result.setMsg("参数解析失败");
        return result;
    }

    /**
     * 405 - Method Not Allowed
     * 请求行中指定的请求方法不能被用于请求相应的资源。该响应必须返回一个Allow 头信息用以表示出当前资源能够接受的请求方法的列表。
     * 鉴于 PUT，DELETE 方法会对服务器上的资源进行写操作，因而绝大部分的网页服务器都不支持或者在默认配置下不允许上述请求方法，
     * 对于此类请求均会返回405错误。
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public CpaResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        CpaResult result = new CpaResult();
        result.setState(CpaConstants.OPERATION_ERROR);
        result.setMsg("不支持当前请求方法");
        return result;
    }

    /**
     * 415 - Unsupported Media Type
     * 对于当前请求的方法和所请求的资源，请求中提交的实体并不是服务器中所支持的格式，因此请求被拒绝
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public CpaResult handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        CpaResult result = new CpaResult();
        result.setState(CpaConstants.OPERATION_ERROR);
        result.setMsg("不支持当前媒体类型");
        return result;
    }

    /**
     * 500 - Internal Server Error
     * 服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。一般来说，这个问题都会在服务器的程序码出错时出现。
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProcessingException.class)
    public CpaResult handleException(Exception e) {
        CpaResult result = new CpaResult();
        if (e instanceof ProcessingException) {
            result.setState(CpaConstants.OPERATION_ERROR);
            result.setMsg(e.getMessage());
            return result;
        }
        return result;
    }

}
