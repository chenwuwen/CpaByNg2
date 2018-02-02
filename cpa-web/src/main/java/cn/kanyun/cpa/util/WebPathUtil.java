package cn.kanyun.cpa.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * classpath实际上就是编译后的 以 classes 文件夹为起点的路径
 * Main.class.getResource(“”); -> 得到的是当前class所在的路径
 * Main.class.getResourceAsStream(“”); -> 是从当前路径查找资源资源
 * Main.class.getClassLoader.getResource(“”);  -> 得到的是当前类classloader加载类的起始位置
 * Main.class.getClassLoader.getResourceAsStream(“”);  -> 从classpath的起始位置查找资源
 * 但是  Main.class.getResource(“/”);  -> 表示从classpath目录下找  也就是说 Main.class.getResource(“/”); 等价于 Main.class.getClassLoader.getResource(“”);
 * 但是 Main.class.getClassLoader.getResourceAsStream(“/”); 返回的是null
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 * <p>
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 * <p>
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 */

/**
 * 关于Servlet 资源路径
 * ServletContext.getRealPath(“/”) 返回的是 war 包展开后的从系统根目录到war展开地址的根路径，比如windows 就是 file:///d/path/to/war/
 * 也就是上面做了两个动作， 先从 war 根目录找到资源， 然后返回资源完整路径
 * 同样的 ServletContext.getResource(“/”) 返回的的是从war 根目录查找到的资源，只不过返回的是 URL ServletContext.getResourceAsStream(“/”)
 * 返回的是和上面一样的 InputStream
 * 但是 ServletContext.getResource(“”) 返回的是相对于URL的路径，相当于从当前URL根路径查找资源 ServletContext.getResourceAsStream(“”) 和上面一样，
 * 只不过返回InputStream
 */

/**
 * (1)、request.getRealPath("/");//不推荐使用获取工程的根路径
 * (2)、request.getRealPath(request.getRequestURI());//获取jsp的路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (3)、request.getSession().getServletContext().getRealPath("/");//获取工程的根路径，这个方法比较好用，可以直接在servlet和jsp中使用
 * (4)、this.getClass().getClassLoader().getResource("").getPath();//获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
 */

/**
 *  web项目打包以后classes文件在磁盘中的绝对路径
 */
public class WebPathUtil {
    public static String getWebPath(Class clazz) {
        String webPath = "";
        File classesFile = new File(clazz.getClassLoader().getResource("").getPath());
        webPath = classesFile.getParent() + File.separator + "classes" + File.separator;
        return webPath;
    }

    /**
     * @describe: 获取请求路径(非客户端IP)，需要注意的是返回值一般都是域名,如果不返回域名,返回web服务器IP,也是通过先获取域名再解析域名获取IP,注意web服务器IP与应用服务器IP的区别
     * @params: projectName：为true 返回带项目的路径，domainName：为ture返回域名，false:返回服务器IP,(Nginx服务器IP)
     * @Author: Kanyun
     * @Date: 2018/2/2 0002 10:04
     */
    public static String getRequestPath(HttpServletRequest request, Boolean projectName, Boolean domainName) throws UnknownHostException {
        StringBuffer requestUrl = request.getRequestURL();
        String tmpUrl = "";
        String url = "";
        if (projectName) {
            tmpUrl = requestUrl.delete(requestUrl.length() - request.getRequestURI().length(), requestUrl.length()).append(request.getServletContext().getContextPath()).append("/").toString();
        } else {
            tmpUrl = requestUrl.delete(requestUrl.length() - request.getRequestURI().length(), requestUrl.length()).append("/").toString();
        }
        if (domainName) {
            InetAddress[] ips;
            if (projectName) {
                //如果前面显示了项目名,这边需要将前面的项目名去掉(使用replace方法)，然后解析域名获取ip，最后加上项目名
                tmpUrl = StringUtils.replaceOnce(tmpUrl, request.getServletContext().getContextPath(), "");
                ips = InetAddress.getAllByName(tmpUrl);
                url = ips[0].toString() + request.getServletContext().getContextPath();
            } else {
                ips = InetAddress.getAllByName(tmpUrl);
                url = ips[0].toString();
            }
        } else {
            url = tmpUrl.toString();
        }
        return url;
    }

    public static void main(String[] args) {
        WebPathUtil webPathUtil = new WebPathUtil();
        System.out.println(webPathUtil.test());
    }

    private String test() {
        String kkk = WebPathUtil.getWebPath(this.getClass());
        return kkk;
    }
}
