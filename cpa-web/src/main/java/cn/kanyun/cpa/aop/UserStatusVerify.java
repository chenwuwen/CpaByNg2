package cn.kanyun.cpa.aop;

import cn.kanyun.cpa.model.entity.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class UserStatusVerify {
    private static final Logger logger = LoggerFactory.getLogger(UserStatusVerify.class);

 /* Spring AOP支持的AspectJ切入点指示符
    切入点指示符用来指示切入点表达式目的，，在spring AOP中目前只有执行方法这一个连接点，Spring AOP支持的AspectJ切入点指示符如下：
    execution：用于匹配方法执行的连接点；
    within：用于匹配指定类型内的方法执行；
    this：用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
    target：用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
    args：用于匹配当前执行的方法传入的参数为指定类型的执行方法；
    @within：用于匹配所以持有指定注解类型内的方法；
    @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
    @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
    @annotation：用于匹配当前执行方法持有指定注解的方法；
    bean：Spring AOP扩展的，AspectJ没有对于指示符，用于匹配特定名称的Bean对象的执行方法；
    reference pointcut：表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持。
    AspectJ切入点支持的切入点指示符还有： call、get、set、preinitialization、staticinitialization、
    initialization、handler、adviceexecution、withincode、cflow、cflowbelow、if、@this、@withincode；
    但Spring AOP目前不支持这些指示符，使用这些指示符将抛出IllegalArgumentException异常。这些指示符Spring
    AOP可能会在以后进行扩展。
    其中 execution 是用的最多的,其格式为:
    execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
    returning type pattern,name pattern, and parameters pattern是必须的.
    ret-type-pattern:可以为*表示任何返回值,全路径的类名等.
    name-pattern:指定方法名,*代表所以,set*,代表以set开头的所有方法.
    parameters pattern:指定方法参数(声明的类型),(..)代表所有参数,(*)代表一个参数,(*,String)代表第一个参数为任何值,第二个为String类型.*/


    /**
     * Pointcut
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     * <p>
     * execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
     * 除了返回类型模式、方法名模式和参数模式外，其它项都是可选的
     * 在多个表达式之间使用 ||,or表示 或，使用 &&,and表示 与，！表示 非
     */
    @Pointcut("execution(* cn.kanyun.cpa.controller..*.*(..)) && !execution(* cn.kanyun.cpa.controller.itempool.CpaRepertoryController.getUnitExam(..)) && !execution(* cn.kanyun.cpa.controller.itempool.CpaRepertoryController.exportWord(..)) && !execution(* cn.kanyun.cpa.controller.common..*.*(..)) && !execution(* cn.kanyun.cpa.controller.user.UserController.*(..)) && !execution(* cn.kanyun.cpa.controller.LoginController.*(..))")
    private void aspectjMethod() {
    }

    /**
     * Before
     * 在核心业务执行前执行，不能阻止核心业务的调用。
     * 配置前置通知,使用在方法aspectjMethod()上注册的切入点,同时接受JoinPoint切入点对象,可以没有该参数
     *
     * @param joinPoint
     */
    @Before("aspectjMethod()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("-----beforeAdvice().invoke-----");
        logger.info(" 此处意在执行核心业务逻辑前，做一些安全性的判断等等");
        logger.info(" 可通过joinPoint来获取所需要的内容");
        logger.info("-----End of beforeAdvice()------");
    }

    /**
     * After
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
     * 配置后置通知,使用在方法aspect()上注册的切入点
     *
     * @param joinPoint
     */
    @After(value = "aspectjMethod()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("-----afterAdvice().invoke-----");
        logger.info(" 此处意在执行核心业务逻辑之后，做一些日志记录操作等等");
        logger.info(" 可通过joinPoint来获取所需要的内容");
        logger.info("-----End of afterAdvice()------");
    }

    /**
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * Around
     * <p>
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice
     * 执行完AfterAdvice，再转到ThrowingAdvice
     * 配置环绕通知,使用在方法aspect()上注册的切入点
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("-----aroundAdvice().invoke-----");
        logger.info(" 此处可以做类似于Before Advice的事情");
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpSession session = request.getSession();
        // 从session中获取用户信息
        CpaUser user = WebUtil.getSessionUser(request);
        if (null == user) {
            CpaResult result = new CpaResult();
            result.setStatus(CpaConstants.USER_NOT_LOGIN);
            return result;
        }

        //调用核心逻辑
        Object retVal = pjp.proceed();
        logger.info(" 此处可以做类似于After Advice的事情");
        logger.info("-----End of aroundAdvice()------");
        return retVal;
    }

    /**
     * AfterReturning
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     *
     * @param joinPoint
     */
    @AfterReturning(value = "aspectjMethod()", returning = "retVal")
    public void afterReturningAdvice(JoinPoint joinPoint, String retVal) {
        logger.info("-----afterReturningAdvice().invoke-----");
        logger.info("Return Value: " + retVal);
        logger.info(" 此处可以对返回值做进一步处理");
        logger.info(" 可通过joinPoint来获取所需要的内容");
        logger.info("-----End of afterReturningAdvice()------");
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     * <p>
     * 注意：执行顺序在Around Advice之后
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "aspectjMethod()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        logger.info("-----afterThrowingAdvice().invoke-----");
        logger.info(" 错误信息：" + ex.getMessage());
        logger.info(" 此处意在执行核心业务逻辑出错时，捕获异常，并可做一些日志记录操作等等");
        logger.info(" 可通过joinPoint来获取所需要的内容");
        logger.info("-----End of afterThrowingAdvice()------");
    }
}
