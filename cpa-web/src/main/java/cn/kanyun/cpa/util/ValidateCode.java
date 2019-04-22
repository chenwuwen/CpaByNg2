package cn.kanyun.cpa.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/10.
 * 验证码生成器类，可生成数字、大写、小写字母及三者混合类型的验证码。 支持自定义验证码字符数量； 支持自定义验证码图片的大小； 支持自定义需排除的特殊字符；
 * 支持自定义干扰线的数量； 支持自定义验证码图文颜色
 */

public class ValidateCode {

    /**
     * 验证码类型为仅数字 0~9
     */
    public static final int TYPE_NUM_ONLY = 0;

    /**
     * 验证码类型为仅字母，即大写、小写字母混合
     */
    public static final int TYPE_LETTER_ONLY = 1;

    /**
     * 验证码类型为数字、大写字母、小写字母混合
     */
    public static final int TYPE_ALL_MIXED = 2;

    /**
     * 验证码类型为数字、大写字母混合
     */
    public static final int TYPE_NUM_UPPER = 3;

    /**
     * 验证码类型为数字、小写字母混合
     */
    public static final int TYPE_NUM_LOWER = 4;

    /**
     * 验证码类型为仅大写字母
     */
    public static final int TYPE_UPPER_ONLY = 5;

    /**
     * 验证码类型为仅小写字母
     */
    public static final int TYPE_LOWER_ONLY = 6;

    /**
     * 验证码类型数学表达式
     */
    public static final int TYPE_CLAC_ONLY = 7;

    /**
     * 运算符数组
     */
    public static final char[] OPERATION_SYMBOL = {'+', '-', '*'};

    private ValidateCode() {

    }

    /**
     * 生成验证码字符串
     *
     * @param type    验证码类型，参见本类的静态属性
     * @param length  验证码长度，大于0的整数
     * @param exChars 需排除的特殊字符（仅对数字、字母混合型验证码有效，无需排除则为null）
     * @return 验证码字符串
     */
    public static String generateTextCode(int type, int length, String exChars) {

        if (length <= 0) {
            return "";
        }

        StringBuffer code = new StringBuffer();
        int i = 0;
        Random r = new Random();

        switch (type) {

            // 仅数字
            case TYPE_NUM_ONLY:
                while (i < length) {
                    int t = r.nextInt(10);
                    if (exChars == null || exChars.indexOf(t + "") < 0) {// 排除特殊字符
                        code.append(t);
                        i++;
                    }
                }
                break;

            // 仅字母（即大写字母、小写字母混合）
            case TYPE_LETTER_ONLY:
                while (i < length) {
                    int t = r.nextInt(123);
                    if ((t >= 97 || (t >= 65 && t <= 90)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                        code.append((char) t);
                        i++;
                    }
                }
                break;

            // 数字、大写字母、小写字母混合
            case TYPE_ALL_MIXED:
                while (i < length) {
                    int t = r.nextInt(123);
                    if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57))
                            && (exChars == null || exChars.indexOf((char) t) < 0)) {
                        code.append((char) t);
                        i++;
                    }
                }
                break;

            // 数字、大写字母混合
            case TYPE_NUM_UPPER:
                while (i < length) {
                    int t = r.nextInt(91);
                    if ((t >= 65 || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                        code.append((char) t);
                        i++;
                    }
                }
                break;

            // 数字、小写字母混合
            case TYPE_NUM_LOWER:
                while (i < length) {
                    int t = r.nextInt(123);
                    if ((t >= 97 || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                        code.append((char) t);
                        i++;
                    }
                }
                break;

            // 仅大写字母
            case TYPE_UPPER_ONLY:
                while (i < length) {
                    int t = r.nextInt(91);
                    if ((t >= 65) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                        code.append((char) t);
                        i++;
                    }
                }
                break;

            // 仅小写字母
            case TYPE_LOWER_ONLY:
                while (i < length) {
                    int t = r.nextInt(123);
                    if ((t >= 97) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                        code.append((char) t);
                        i++;
                    }
                }
                break;
            // 含有运算符的 计算机中 0是偶数
            default:
                //如果是偶数，注意以位运算来判断奇偶,避免负值的情况
                if ((length & 1) != 1) {
                    System.out.println("输入的长度为偶数：" + length);
                    //需要注意的是,如果是偶数的话则没有办法生成运算表达式,因为运算符总是比数字少一个
                    length -= 1;
                    System.out.println("修改后的长度为：" + length);
                }
                while (i < length) {
                    i++;
                    if ((i & 1) == 1) { //如果奇数,放数字
                        int t = r.nextInt(10);
                        code.append(t);
                    } else {
                        int num = r.nextInt(2);
                        char symbol = OPERATION_SYMBOL[num];
                        code.append(symbol);
                    }
                }
        }

        return code.toString();
    }

    /**
     * 已有验证码，生成验证码图片
     *
     * @param textCode       文本验证码
     * @param width          图片宽度
     * @param height         图片高度
     * @param interLine      图片中干扰线的条数
     * @param randomLocation 每个字符的高低位置是否随机
     * @param backColor      图片颜色，若为null，则采用随机颜色
     * @param foreColor      字体颜色，若为null，则采用随机颜色
     * @param lineColor      干扰线颜色，若为null，则采用随机颜色
     * @return 图片缓存对象
     */
    public static BufferedImage generateImageCode(String textCode, int width, int height, int interLine,
                                                  boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {

        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bim.getGraphics();

        // 画背景图
        g.setColor(backColor == null ? getRandomColor() : backColor);
        g.fillRect(0, 0, width, height);

        // 画干扰线
        Random r = new Random();
        if (interLine > 0) {

            int x = 0, y = 0, x1 = width, y1 = 0;
            for (int i = 0; i < interLine; i++) {
                g.setColor(lineColor == null ? getRandomColor() : lineColor);
                y = r.nextInt(height);
                y1 = r.nextInt(height);

                g.drawLine(x, y, x1, y1);
            }
        }

        // 写验证码

        // g.setColor(getRandomColor());
        // g.setColor(isSimpleColor?Color.BLACK:Color.WHITE);

        // 字体大小为图片高度的80%
        int fsize = (int) (height * 0.8);
        int fx = height - fsize;
        int fy = fsize;

        g.setFont(new Font("Default", Font.PLAIN, fsize));

        // 写验证码字符
        for (int i = 0; i < textCode.length(); i++) {
            fy = randomLocation ? (int) ((Math.random() * 0.3 + 0.6) * height) : fy;// 每个字符高低是否随机
            g.setColor(foreColor == null ? getRandomColor() : foreColor);
            g.drawString(textCode.charAt(i) + "", fx, fy);
            fx += fsize * 0.9;
        }

        g.dispose();

        return bim;
    }

    /**
     * 生成图片验证码
     *
     * @param type           验证码类型，参见本类的静态属性
     * @param length         验证码字符长度，大于0的整数
     * @param exChars        需排除的特殊字符
     * @param width          图片宽度
     * @param height         图片高度
     * @param interLine      图片中干扰线的条数
     * @param randomLocation 每个字符的高低位置是否随机
     * @param backColor      图片颜色，若为null，则采用随机颜色
     * @param foreColor      字体颜色，若为null，则采用随机颜色
     * @param lineColor      干扰线颜色，若为null，则采用随机颜色
     * @return 图片缓存对象
     */
    public static BufferedImage generateImageCode(int type, int length, String exChars, int width, int height,
                                                  int interLine, boolean randomLocation, Color backColor, Color foreColor, Color lineColor) {

        String textCode = generateTextCode(type, length, exChars);
        BufferedImage bim = generateImageCode(textCode, width, height, interLine, randomLocation, backColor, foreColor,
                lineColor);

        return bim;
    }

    /**
     * 产生随机颜色
     *
     * @return
     */
    private static Color getRandomColor() {
        Random r = new Random();
        Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        return c;
    }

    /**
     * 传入数学表达式字符串,返回结果
     * Java和Js之间的调用时基于ScriptEngineManager类，这个类是jdk8新增的
     * 所以利用js的eval()函数来计算表达式
     * @param exp
     * @return
     * @throws ScriptException
     */
    private static int calc(String exp) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        return (Integer) scriptEngine.eval(exp);
    }

}

