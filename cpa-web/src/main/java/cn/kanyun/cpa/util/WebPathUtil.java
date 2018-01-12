package cn.kanyun.cpa.util;

import java.io.File;
/**
 * classpath实际上就是编译后的 以 classes 文件夹为起点的路径
 * Main.class.getResource(“”); -> 得到的是当前class所在的路径
 * Main.class.getResourceAsStream(“”); -> 是从当前路径查找资源资源
 * Main.class.getClassLoader.getResource(“”);  -> 得到的是当前类classloader加载类的起始位置
 * Main.class.getClassLoader.getResourceAsStream(“”);  -> 从classpath的起始位置查找资源
 * 但是  Main.class.getResource(“/”);  -> 表示从classpath目录下找  也就是说 Main.class.getResource(“/”); 等价于 Main.class.getClassLoader.getResource(“”);
 * 但是 Main.class.getClassLoader.getResourceAsStream(“/”); 返回的是null
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
 *  web项目打包以后classes文件在磁盘中的绝对路径
 */
public class WebPathUtil {
    public static String getWebPath(Class clazz) {
        String webPath = "";
        File classesFile = new File(clazz.getClassLoader().getResource("").getPath());
        webPath = classesFile.getParent() + File.separator + "classes" + File.separator;
        return webPath;
    }

    public static void main(String[] args) {
        WebPathUtil webPathUtil = new WebPathUtil();
        System.out.println(webPathUtil.test());
    }

    private String test(){
      String kkk=  WebPathUtil.getWebPath(this.getClass());
      return kkk;
    }
}
