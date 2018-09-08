package cn.kanyun.cpa.util;

import cn.kanyun.cpa.model.constants.CpaConstants;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    static int countFiles = 0;  // 声明统计文件个数的变量
    static int countFolders = 0;  // 声明统计文件夹的变量

    public static boolean fileDelete(String filepath) {
        boolean flag1 = false;
        java.io.File file = new java.io.File(filepath);

        flag1 = file.delete();
        return flag1;
    }

    // 挑选符合条件的文件迭代删除
    public static boolean getlistfilename(String path, GregorianCalendar deletetime) {

        java.io.File f = new java.io.File(path);

        if (f.exists()) {
            if (f.isFile()) {
                return f.delete();
            } else if (f.isDirectory()) {
                File[] files = f.listFiles();

                for (int i = 0; i < files.length; i++) {

                    if (files[i].lastModified() < deletetime.getTimeInMillis()) {

                        if (!deleteFile(files[i])) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param path:目录路径（不包括文件名）,prefix:文件名前缀,suffix:文件名后缀
     * @return
     * @author Kanyun
     * @Description: 查找目录下的所有文件(包含条件, 或查找全部[prefix is null])，不递归目录查找
     * @date 2017/11/24 11:54
     */
    public static List<String> searchFile(String path, String prefix, String suffix) {
        List<String> filePaths = new ArrayList<>();
        File files = new File(path);
        String[] names = files.list();
        if (null == prefix) {
            for (String s : names) {
                filePaths.add(files.getAbsolutePath() + "/" + s);
            }
            return filePaths;
        }
        for (String s : names) {
            //文件名前缀与参数一致,是返回true,否则返回false
            boolean a = s.startsWith(prefix);
            //判断本次循环的字符串所指向的内容是否是文件,是则返回true.否则返回false
            boolean b = (new File(files.getAbsolutePath() + "/" + s)).isFile();
            //判断后缀是否一致，暂时先不做判断，因为有些文件没有后缀
//            boolean c = false;
//            if (null != FilenameUtils.getExtension(s) && null != suffix) {
//                c = FilenameUtils.getExtension(s).equals(suffix);
//            }
            if (a && b) {
                filePaths.add(files.getAbsolutePath() + "/" + s);
            }
        }
        return filePaths;
    }

    public static File[] searchFile(File folder, final String keyWord) {// 递归查找包含关键字的文件

        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isFile()) { // 如果是文件
                    countFiles++;
                } else { // 如果是目录
                    countFolders++;
                }
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().toLowerCase().contains(keyWord.toLowerCase()))) { // 目录或文件包含关键字
                    return true;
                }
                return false;
            }
        });

        List<File> result = new ArrayList<File>();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
            if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
                result.add(subFolders[i]);
            } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                File[] foldResult = searchFile(subFolders[i], keyWord);
                for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
                    result.add(foldResult[j]);// 文件保存到集合中
                }
            }
        }

        File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
        result.toArray(files);// 集合数组化
        return files;
    }


    // 迭代删除目录下的文件
    public static boolean deleteFile(File f) {
        if (f.exists()) {
            if (f.isFile()) {
                return f.delete();
            } else if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (!deleteFile(files[i])) {
                        return false;
                    }
                }

                // return f.delete(); 连带目录一起删除
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 判断文件是否存在
    public static boolean getlistfilename(String path, String time) {

        java.io.File f = new java.io.File(path);

        if (f.exists()) {
            if (f.isFile()) {
                return f.delete();
            } else if (f.isDirectory()) {
                File[] files = f.listFiles();

                for (int i = 0; i < files.length; i++) {
                    // System.out.println((files[i].getName()).substring(10,18));
                    // System.out.println(time);
                    if (((files[i].getName()).substring(10, 18)).equals(time)) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isExists_file(String filepath) {
        boolean flag1 = false;
        java.io.File file = new java.io.File(filepath);
        if (file.exists()) {
            flag1 = true;
        }
        return flag1;
    }

    /**
     * @param : originalName  原始文件名(带后缀名)
     * @param : size  图片尺寸(形如 "400-400")
     * @param : flag 是否使用uuid作为文件名前缀(true :使用(即原图使用UUID进行重命名) false: 不使用(压缩图片不适用UUID进行重命名,只需要加后缀))
     * @ClassName:
     * @Author:
     * @Description:文件重命名(windows文件夹名不能包含*号) 在Windows系统中，文件名不允许使用的字符有
     * < > / \ | : " * ?
     * @Date:
     */
    public static String generateFileName(String originalName, String size, Boolean flag) {

        StringBuffer name = new StringBuffer();
        String suffix = FilenameUtils.getExtension(originalName);
        if (flag) {
            /*不直接使用uuid，使用处理后的uuid，减少位数，防止路径过长无法创建文件夹*/
            String handlerUUID = getOrderIdByUUId();

            name.append(handlerUUID).append(size).append(".").append(suffix);
        } else {
            originalName = originalName.substring(0, originalName.lastIndexOf("."));
            name.append(originalName).append("_").append(size).append(".").append(suffix);
        }

        return name.toString();

    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multfile) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile) multfile;
        //这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        //手动创建临时文件
        if (file.length() < CpaConstants.MIN_FILE_SIZE) {
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                    file.getName());
            multfile.transferTo(tmpFile);
            return tmpFile;
        }
        return file;
    }

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description: 缩短uuid的位数为16位，因为在windows下，文件路径最长为256个字符,，所以文件名名很长可能无法创建文件夹，所以缩短
     * @date 2017/11/22 21:17
     */
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    public static void main(String[] args) {
        String name = "asiuhdie.asdasubd.jpg";
        System.out.println(name.substring(1));
        System.out.println(name.length());
        System.out.println(name.lastIndexOf("."));
        System.out.println(generateFileName(name, "200*200", false));
    }

}
