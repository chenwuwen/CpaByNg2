package cn.kanyun.cpa.model.constants;

/**
 * 常量类
 *
 * @author Kanyun
 */
public class CpaConstants {

    /**
     * 验证码
     */
    public static String IDENTIFYING_CODE = "validateCode";

    /**
     * 用户名
     */
    public static String USER = "user";

    /**
     * 参数为空
     */
    public static String PARAM_IS_NULL = "参数为空";

    /**
     * 对象为空
     */
    public static String OBJECT_IS_NULL = "对象为空";

    /**
     * 参数对象为空
     */
    public static String PARAM_OBJECT_IS_NULL = "参数对象为空";

    /**
     * 是
     */
    public static String YES = "是";

    /**
     * 否
     */
    public static String NO = "否";

    /**
     * 用户未登录
     */
    public static Integer USER_NOT_LOGIN = 0;

    /**
     * 用户已登录
     */
    public static Integer USER_HAS_LOGIN = 1;

    /**
     * 数据记录未找到
     */
    public static Integer NOT_FOUND_DATA = 0;

    /**
     * 成功获取数据记录,或操作成功
     */
    public static Integer OPERATION_SUCCESS = 1;

    /**
     * 获取记录失败,或操作失败
     */
    public static Integer OPERATION_ERROR = 2;

    /**
     * 用户名或密码错误
     */
    public static String USERNAME_OR_PASSWORD_ERROE = "用户名或密码错误";

    /**
     * JWT生成Token的key自定义字符串
     */
    public static String JWT_SECRET = "kanyun1993chenwuwen02cpa10com";

    /**
     * JWT生成Token设置过期时间
     */
    public static long JWT_REFRESH_INTERVAL = 60 * 60 * 1000;

    /**
     * JWT生成Token设置ISSUSER
     */
    public static String JWT_ISSUSER = "kanyun.cpa.com";

    /**
     * 最大上传文件，超过此值上传文件将产生临时文件
     */
    public static Integer MIN_FILE_SIZE = 2048;

    /**
     * 当前活跃(在线)用户数
     */
    public static String ONLINE_USER_COUNT = "online_user_count";

    /**
     * 当前活跃(在线)用户
     */
    public static String ONLINE_USER = "online_user";
}
