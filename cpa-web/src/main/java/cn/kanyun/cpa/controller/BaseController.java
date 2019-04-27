package cn.kanyun.cpa.controller;

/**
 * 谈谈springmvc的优化
 * 1.controller如果能保持单例,尽量使用单例,这样可以减少创建对象和回收对象的开销.
 * 也就是说,如果controller的类变量和实例变量可以以方法形参声明的尽量以方法的形参声明,
 * 不要以类变量和实例变量声明,这样可以避免线程安全问题.
 * 2.处理request的方法中的形参务必加上@RequestParam注解,
 * 这样可以避免springmvc使用asm框架读取class文件获取方法参数名的过程.
 * 即便springmvc对读取出的方法参数名进行了缓存,如果不要读取class文件当然是更加好.
 * 3.阅读源码的过程中,发现springmvc并没有对处理url的方法进行缓存,
 * 也就是说每次都要根据请求url去匹配controller中的方法url,如果把url和method的关系缓存起来,
 * 会不会带来性能上的提升呢?有点恶心的是,
 * 负责解析url和method对应关系的ServletHandlerMethodResolver是一个private的内部类,
 * 不能直接继承该类增强代码,必须要该代码后重新编译.当然,如果缓存起来,必须要考虑缓存的线程安全问题.
 */
public class BaseController {
}