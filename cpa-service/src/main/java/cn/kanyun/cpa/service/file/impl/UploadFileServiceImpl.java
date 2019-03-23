package cn.kanyun.cpa.service.file.impl;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.service.file.FtpService;
import cn.kanyun.cpa.service.file.UploadFileService;
import cn.kanyun.cpa.util.DateUtils;
import cn.kanyun.cpa.util.FileHelper;
import cn.kanyun.cpa.util.FileUtil;
import cn.kanyun.cpa.util.FolderUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kanyun
 */
@Service(UploadFileService.SERVICE_NAME)
public class UploadFileServiceImpl implements UploadFileService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    /**
     * @describe: 这个方法的作用是初始化图片大小列表, 初始化List（双括号初始化法）
     * @params:
     * @Author: Kanyun
     * @Date: 2018/7/23 9:55
     */
    public List<String> imgSizes = new ArrayList<String>() {{
        add("200-220");
        /*圆形头像*/
        add("50-50");
    }};

    @Resource(name = FtpService.SERVICE_NAME)
    private FtpService ftpService;

    @Override
    public CpaResult upLoadImg(MultipartFile[] files) throws Exception {
        CpaResult result = new CpaResult();
        for (MultipartFile file : files) {
            String newFileName = FileUtil.generateFileName(file.getOriginalFilename(), "", true);
            String rootPath = UploadFileServiceImpl.class.getResource("/").getPath() + "images/";
            String filePath = rootPath + DateUtils.formatDate(Instant.now(),DateUtils.pattern_date_oblique_line) + "/" + newFileName;

            try {
                //先创建文件夹
                FolderUtils.mkdirs(filePath);
                // 转存文件
                file.transferTo(new File(filePath));
//                文件转换为BufferedImage使用gm处理图片需要
//                BufferedImage image = ImageIO.read(new FileInputStream(filePath));
                for (String size : imgSizes) {
//                   压缩图片保存路径
                    String descPath = rootPath + DateUtils.formatDate(Instant.now(),DateUtils.pattern_date_oblique_line) + "/" + FileUtil.generateFileName(newFileName, size, false);
//                创建文件
                    File handlerFile = new File(descPath);
                    if (!handlerFile.exists()) {
                        handlerFile.createNewFile();
                    }
                    //压缩图片并导出图片
                    Thumbnails.of(filePath).size(Integer.parseInt(size.split("-")[0]), Integer.parseInt(size.split("-")[1])).toFile(descPath);
                }
                //上传文件到FTP
                ftpService.uploadFTP(filePath, "/image/" + DateUtils.formatDate(Instant.now(),DateUtils.pattern_date_oblique_line) + "/" + newFileName);
                result.setState(CpaConstants.OPERATION_SUCCESS);
                result.setData(filePath.replace(rootPath, ""));
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("UploadFileServiceImpl 上传图片或压缩图片出错: " + e);
                result.setState(CpaConstants.OPERATION_ERROR);
            }
        }
        return result;
    }

    @Override
    public String upLoadQRPic(String filePath) throws Exception {
        ftpService.uploadFTP(filePath, "/share/");
        String url = "share/" + FileHelper.getFileName(filePath);
        return url;
    }


}
