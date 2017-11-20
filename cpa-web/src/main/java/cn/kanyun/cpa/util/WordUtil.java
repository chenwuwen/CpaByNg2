package cn.kanyun.cpa.util;

import cn.kanyun.cpa.controller.itempool.CpaRepertoryController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.management.resources.agent;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/**
 * 实现Word模板读取替换内容输出
 *
 * @param filePath 模板路径
 * @param haderMap 页眉数据
 * @param bodyMap  内容数据
 * @param footMap  页脚数据
 * <p>
 * response.setContentType()的String参数及对应类型
 * <p>
 * <option   value="image/bmp">BMP</option>
 * <option   value="image/gif">GIF</option>
 * <option   value="image/jpeg">JPEG</option>
 * <option   value="image/tiff">TIFF</option>
 * <option   value="image/x-dcx">DCX</option>
 * <option   value="image/x-pcx">PCX</option>
 * <option   value="text/html">HTML</option>
 * <option   value="text/plain">TXT</option>
 * <option   value="text/xml">XML</option>
 * <option   value="application/afp">AFP</option>
 * <option   value="application/pdf">PDF</option>
 * <option   value="application/rtf">RTF</option>
 * <option   value="application/msword">MSWORD</option>
 * <option   value="application/vnd.ms-excel">MSEXCEL</option>
 * <option   value="application/vnd.ms-powerpoint">MSPOWERPOINT</option>
 * <option   value="application/wordperfect5.1">WORDPERFECT</option>
 * <option   value="application/vnd.lotus-wordpro">WORDPRO</option>
 * <option   value="application/vnd.visio">VISIO</option>
 * <option   value="application/vnd.framemaker">FRAMEMAKER</option>
 * <option   value="application/vnd.lotus-1-2-3">LOTUS123</option>
 * <p>
 * response.setContentType()的String参数及对应类型
 * <p>
 * <option   value="image/bmp">BMP</option>
 * <option   value="image/gif">GIF</option>
 * <option   value="image/jpeg">JPEG</option>
 * <option   value="image/tiff">TIFF</option>
 * <option   value="image/x-dcx">DCX</option>
 * <option   value="image/x-pcx">PCX</option>
 * <option   value="text/html">HTML</option>
 * <option   value="text/plain">TXT</option>
 * <option   value="text/xml">XML</option>
 * <option   value="application/afp">AFP</option>
 * <option   value="application/pdf">PDF</option>
 * <option   value="application/rtf">RTF</option>
 * <option   value="application/msword">MSWORD</option>
 * <option   value="application/vnd.ms-excel">MSEXCEL</option>
 * <option   value="application/vnd.ms-powerpoint">MSPOWERPOINT</option>
 * <option   value="application/wordperfect5.1">WORDPERFECT</option>
 * <option   value="application/vnd.lotus-wordpro">WORDPRO</option>
 * <option   value="application/vnd.visio">VISIO</option>
 * <option   value="application/vnd.framemaker">FRAMEMAKER</option>
 * <option   value="application/vnd.lotus-1-2-3">LOTUS123</option>
 */

/**
 *response.setContentType()的String参数及对应类型
 *
 *<option   value="image/bmp">BMP</option>
 *<option   value="image/gif">GIF</option>
 *<option   value="image/jpeg">JPEG</option>
 *<option   value="image/tiff">TIFF</option>
 *<option   value="image/x-dcx">DCX</option>
 *<option   value="image/x-pcx">PCX</option>
 *<option   value="text/html">HTML</option>
 *<option   value="text/plain">TXT</option>
 *<option   value="text/xml">XML</option>
 *<option   value="application/afp">AFP</option>
 *<option   value="application/pdf">PDF</option>
 *<option   value="application/rtf">RTF</option>
 *<option   value="application/msword">MSWORD</option>
 *<option   value="application/vnd.ms-excel">MSEXCEL</option>
 *<option   value="application/vnd.ms-powerpoint">MSPOWERPOINT</option>
 *<option   value="application/wordperfect5.1">WORDPERFECT</option>
 *<option   value="application/vnd.lotus-wordpro">WORDPRO</option>
 *<option   value="application/vnd.visio">VISIO</option>
 *<option   value="application/vnd.framemaker">FRAMEMAKER</option>
 *<option   value="application/vnd.lotus-1-2-3">LOTUS123</option>
 */

/**
 *MIME映射策略就是在网页中使用哪个应用程序（即插件），打开哪种文件。另外还有使用权
 *限问题。比如对PDF文档，用“application/pdf“策略。这在动态网页中很常见。出现这种
 *现象，有两种情形：一是使用一个应用程序去打开它不能打开的文档，比如用在标签中定义
 *“DWG”文档用“application/pdf”，就会出现无法打开的问题。二是文件扩展名符合要求
 *，但文件内容（格式）不符合要求。你可以检查你浏览的网页源代码，获得出错信息。检查
 *方法是：查看—源文件。寻找类似于“application/pdf
 *“的字符串，就可以看到，要打开的文件是否与应用程序匹配。 追问
 *如果不相匹配如何解决回答这通常是由网页编写人来更改。比如：你在源文件里面找到你要
 *打开的文件的HTML标签，在里面加上应用程序即可。比如，你要在网页上打开一个PDF文档?
 *业絇DF文档那一行，在HTML标签里加上 type=“application/pdf “
 *就可以了。比如以下HTML文件：<!----------测试MIME-----------
 *><html><head><title>测试MIME</title></head><body><a
 *type="application/pdf"href="test.pdf">测试MIME</a></body>
 *</html将上面的代码保存为test.html，再在相同的位置存储一个pdf文档，双击它就会在网
 *页中打开该文
 */

public class WordUtil {

    private static final Logger logger = LoggerFactory.getLogger(WordUtil.class);

    private static String fileName = DateUtils.toYmd(new Date())+"_"+System.currentTimeMillis()+ "_cpa.doc";

    private static Configuration configuration = null;

    public WordUtil() {

        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");

    }

//    static {
//        configuration = new Configuration();
//        configuration.setDefaultEncoding("utf-8");
//    }

    /**
     * 利用匿名类在静态方法中获取当前类，弥补静态方法中不能用this.getClass()的不足
     */
    private static final Class getClassForStatic() {
        return new Object() {
            public Class getClassForStatic() {
                return this.getClass();
            }
        }.getClassForStatic();
    }

    /**
     * 利用匿名类静态方法中获取当前类名
     */
    private static final String getClassNameForStatic() {
        return new Object() {
            public String getClassName() {
                String className = this.getClass().getName();
                return className.substring(0, className.lastIndexOf('$'));
            }
        }.getClassName();
    }

    /**
     *
     * @author Kanyun
     * @Description:
     * @date 2017/11/16 10:55
     * @param
     * @return
     */
    public static void exportWord1(List list, HttpServletResponse response) {
        /*新建空白文档*/
        XWPFDocument document = new XWPFDocument();
        try {
            //返回字节流
            OutputStream os = response.getOutputStream();
//            FileOutputStream out = new FileOutputStream(file);
            //添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            //设置标题
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("Java PoI");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);

            //基本信息表格
            XWPFTable infoTable = document.createTable();
            //去表格边框
            infoTable.getCTTbl().getTblPr().unsetTblBorders();


            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));

            //段落
            if (!list.isEmpty()) {

                XWPFParagraph firstParagraph = document.createParagraph();
                XWPFRun run = firstParagraph.createRun();
                run.setText("Java POI 生成word文件。");
                run.setColor("696969");
                run.setFontSize(16);

                //设置段落背景颜色
                CTShd cTShd = run.getCTR().addNewRPr().addNewShd();
                cTShd.setVal(STShd.CLEAR);
                cTShd.setFill("97FFFF");


                //换行
                XWPFParagraph paragraph1 = document.createParagraph();
                XWPFRun paragraphRun1 = paragraph1.createRun();
                paragraphRun1.setText("\r");
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

            //添加页眉
            CTP ctpHeader = CTP.Factory.newInstance();
            CTR ctrHeader = ctpHeader.addNewR();
            CTText ctHeader = ctrHeader.addNewT();
            String headerText = "Java POI create MS word file.";
            ctHeader.setStringValue(headerText);
            XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
            //设置为右对齐
            headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFParagraph[] parsHeader = new XWPFParagraph[1];
            parsHeader[0] = headerParagraph;
            policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);


            //添加页脚
            CTP ctpFooter = CTP.Factory.newInstance();
            CTR ctrFooter = ctpFooter.addNewR();
            CTText ctFooter = ctrFooter.addNewT();
            String footerText = "http://blog.csdn.net/zhouseawater";
            ctFooter.setStringValue(footerText);
            XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFParagraph[] parsFooter = new XWPFParagraph[1];
            parsFooter[0] = footerParagraph;
            policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

            /*一般在Servlet中，习惯性的会首先设置请求以及响应的内容类型以及编码方式*/
//            response.setCharacterEncoding("utf-8");
            /*response.setContentType(MIME)的作用是使客户端浏览器，区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据(MIME映射策略就是在网页中使用哪个应用程序（即插件），打开哪种文件).例如web浏览器就是通过MIME类型来判断文件是GIF图片。通过MIME类型来处理json字符串。*/
            response.setContentType("application/msword,charset=utf-8");

            //告诉浏览器用下载的方式打开,将文件名进行URL编码
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
//            String agent = request.getHeader("USER-AGENT").toLowerCase();
            //火狐浏览器特殊处理
//            if (agent.contains("firefox")) {
//                response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xlsx");
//            }
            FileOutputStream fos = new FileOutputStream("f:/" + fileName);
            document.write(fos);
//            document.write(os);

            /*清空内存，将内存中的数据立刻写出*/
            os.flush();
            os.close();

        } catch (Exception e) {
            logger.error("WordUtil.exportWord() 异常: " + e);
        }
    }

    /**
     *
     * @author Kanyun
     * @Description: 创建doc文件(使用freemarker模板创建word)
     * @date 2017/11/19 21:36
     * @param
     * @return
     */
    public static File createDoc(Map<String, Object> data) {
        //dataMap 要填入模本的数据文件
        //设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        //这里我们的模板是放在classpath下面
        WordUtil wordUtil = new WordUtil();
//        configuration.setClassForTemplateLoading(this.getClass(), "/");
        configuration.setClassForTemplateLoading(wordUtil.getClass(), "/");
        Template t = null;
        try {
            //test.ftl为要装载的模板
            t = configuration.getTemplate("templateForDoc.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出文档路径及名称
        File outFile = new File(fileName);
        Writer out = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFile);
            //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。
            OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");
            out = new BufferedWriter(oWriter);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            t.process(data, out);
            out.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    /**
     *
     * @author Kanyun
     * @Description: 导出word使用freemarker创建word
     * @date 2017/11/19 21:48
     * @param
     * @return
     */
    public static void exportWord(Map<String, Object> data, HttpServletResponse response) {
        File file = null;
        file = createDoc(data);
        InputStream is = null;
        ServletOutputStream out = null;
        try {
            is = new FileInputStream(file);
            response.setContentType("application/msword,charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            out = response.getOutputStream();
            byte[] buffer = new byte[512];  // 缓冲区
            int bytesToRead = -1;
            while ((bytesToRead = is.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
            out.flush();
            out.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if(file != null) file.delete(); // 删除临时文件
        }

    }
}
