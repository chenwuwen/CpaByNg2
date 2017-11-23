package cn.kanyun.cpa.util;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.UUID;

public class FileUtil {
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
            if (f.isFile())
                return f.delete();
            else if (f.isDirectory()) {
                File[] files = f.listFiles();

                for (int i = 0; i < files.length; i++) {

                    if (files[i].lastModified() < deletetime.getTimeInMillis()) {

                        if (!deleteFile(files[i]))
                            return false;
                    }
                }
                return true;
            } else
                return false;
        } else
            return false;
    }

    // 迭代删除目录下的文件
    public static boolean deleteFile(File f) {
        if (f.exists()) {
            if (f.isFile())
                return f.delete();
            else if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (!deleteFile(files[i]))
                        return false;
                }

                // return f.delete(); 连带目录一起删除
                return true;
            } else
                return false;
        } else
            return false;
    }

    // 判断文件是否存在
    public static boolean getlistfilename(String path, String time) {

        java.io.File f = new java.io.File(path);

        if (f.exists()) {
            if (f.isFile())
                return f.delete();
            else if (f.isDirectory()) {
                File[] files = f.listFiles();

                for (int i = 0; i < files.length; i++) {
                    // System.out.println((files[i].getName()).substring(10,18));
                    // System.out.println(time);
                    if (((files[i].getName()).substring(10, 18)).equals(time))
                        return true;
                }
                return false;
            } else
                return false;
        } else
            return false;
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
     * @Description:文件重命名(windows文件夹名不能包含*号)
     * 在Windows系统中，文件名不允许使用的字符有
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
            originalName = originalName.substring(0,originalName.lastIndexOf("."));
            name.append(originalName).append("_").append(size).append(".").append(suffix);
        }

        return name.toString();

    }

    /**    
     *   
     * @author Kanyun 
     * @Description: 缩短uuid的位数为16位，因为在windows下，文件路径最长为256个字符,，所以文件名名很长可能无法创建文件夹，所以缩短
     * @date 2017/11/22 21:17  
     * @param   
     * @return   
     */  
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
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
        System.out.println(generateFileName(name,"200*200",false));
    }

}
