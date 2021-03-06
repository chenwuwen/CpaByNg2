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
    public static final String IDENTIFYING_CODE = "validateCode";

    /**
     * 用户名
     */
    public static final String USER = "user";

    /**
     * 参数为空
     */
    public static final String PARAM_IS_NULL = "参数为空";

    /**
     * 对象为空
     */
    public static final String OBJECT_IS_NULL = "对象为空";

    /**
     * 参数对象为空
     */
    public static final String PARAM_OBJECT_IS_NULL = "参数对象为空";

    /**
     * 是
     */
    public static final String YES = "是";

    /**
     * 否
     */
    public static final String NO = "否";

    /**
     * 用户未登录
     */
    public static final Integer USER_NOT_LOGIN = 0;

    /**
     * 用户已登录
     */
    public static final Integer USER_HAS_LOGIN = 1;

    /**
     * 数据记录未找到
     */
    public static final Integer NOT_FOUND_DATA = 0;

    /**
     * 成功获取数据记录,或操作成功
     */
    public static final Integer OPERATION_SUCCESS = 1;

    /**
     * 获取记录失败,或操作失败
     */
    public static final Integer OPERATION_ERROR = 2;

    /**
     * 用户名或密码错误
     */
    public static final String USERNAME_OR_PASSWORD_ERROE = "用户名或密码错误";

    /**
     * JWT生成Token的key自定义字符串
     */
    public static final String JWT_SECRET = "kanyun1993chenwuwen02cpa10com";

    /**
     * JWT生成Token设置过期时间
     */
    public static final long JWT_REFRESH_INTERVAL = 60 * 60 * 1000;

    /**
     * Redis 保存用户SESSION 有效时间 [因此当jwt快过期时,redis的缓存也快过期了,所以更换新的jwt的同时也要同时续期redis]
     */
    public static final int REDIS_SESSION_USE_EXPIRE = 60 * 60 * 1000;

    /**
     * JWT生成Token设置ISSUSER
     */
    public static final String JWT_ISSUSER = "cpa.kanyun.com";

    /**
     * 最大上传文件，超过此值上传文件将产生临时文件
     */
    public static final Integer MIN_FILE_SIZE = 2048;

    /**
     * 当前活跃(在线)用户数
     */
    public static final String ONLINE_USER_COUNT = "online_user_count";

    /**
     * 当前活跃(在线)用户
     */
    public static final String ONLINE_USER = "online_user";

    /**
     * 是否使用Redis代替SESSION
     */
    public static final Boolean SESSION_USE_REDIS = true;

    /**
     * 默认租户
     */
    public static final String DEFAULT_TENANT = "DEFAULT";

}
