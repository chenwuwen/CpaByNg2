package cn.kanyun.cpa.service.file;

import cn.kanyun.cpa.model.entity.CpaResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    public static final String SERVICE_NAME="cn.kanyun.cpa.Service.file.impl.UploadFileServiceImpl";

    /**    
     *   
     * @author Kanyun 
     * @Description:  上传图片服务
     * @date 2017/11/21 22:17
     * @param   
     * @return   
     */  
    CpaResult upLoadImg(MultipartFile[] files) throws Exception;

    /**
     * @describe: 上传二维码图片
     * @params:
     * @Author: Kanyun
     * @Date: 2018/2/2 0002 13:23
     */
    String upLoadQRPic(String filePath) throws Exception;

}
