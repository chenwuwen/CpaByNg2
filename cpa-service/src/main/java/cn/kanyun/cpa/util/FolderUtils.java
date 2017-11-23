package cn.kanyun.cpa.util;

import org.apache.commons.io.FilenameUtils;
import sun.applet.Main;

import java.io.File;

public class FolderUtils {
    /**
     * 创建完整路径
     *
     * @param path a {@link java.lang.String} object.
     */
    public static final void mkdirs(final String... path) {
        for (String foo : path) {
            final String realPath = FilenameUtils.normalizeNoEndSeparator(foo);
            final File folder = new File(realPath);
            if (!folder.exists() || folder.isFile()) {
                folder.mkdirs();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(FolderUtils.class.getResource("/"));
    }

}
