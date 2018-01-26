package cn.kanyun.cpa.model.constants;

/**
 * @ClassName: GraphicImageConstants.java
 * @Author: liukailong
 * @Description: 图片裁切常量
 * @Date: 2015年4月7日
 */

public class GraphicImageConstants {
    /**
     * 全局工具路径 linux环境下不需要此路径
     */
    public static final String GLOBLE_PATH = "D:\\GraphicsMagick-1.3.21-Q8";

    /**
     * 个人工具配置路径 linux环境下不需要此路径
     */
    public static final String INDIVIDUAL_PATH = "D:\\GraphicsMagick-1.3.21-Q8";

    /**
     * 水印内容interface
     */
    public static final String WATERMARK = "www.zhiguw.com";

    /**
     * 水印格式，位置起点
     */
    public static final String MARK_PARAM = "text 0,0 ";

    /**
     * 文件,图片后缀名
     */
    public static final String FILE_NAME_SUFFIX = "zhiguw";

    /**
     * 是否使用gm处理图片，false/null:使用im处理图片
     * 使用im4java调用若遇到 java.io.FileNotFoundException: gm 错误
     * 需要安装Imagemagick并设置IMHomePath,IMHomePath是电脑上Imagemagick安装的目录,比如我的是：G://ImPro//ImageMagick-6.6.9-Q16
     * 指定Imagmagick的目录在cmd.run之前加：  cmd.setSearchPath("G://ImPro//ImageMagick-6.6.9-Q16s");
     */
    public static final Boolean GM = true;

    /**
     * 水印字体
     */
    public static final String FONT = "Arial";
    /**
     * 水印颜色
     */
    public static final String COLOR = "yellow";

    /**
     * 透明度
     */
    public static final Integer DISSOLVE = 50;

    /**
     * 水印重心
     */
    public static final String GRAVITY = "southeast";

    /**
     * 字体粗度
     */
    public static final Integer POINTSIZE = 50;


}
