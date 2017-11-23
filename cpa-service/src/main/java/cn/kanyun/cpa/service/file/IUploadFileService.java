package cn.kanyun.cpa.service.file;

import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public interface IUploadFileService {
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
     *   
     * @author Kanyun 
     * @Description: 数据验证
     * @date 2017/11/21 22:19
     * @param   file 上传的数据
     * @param   type 数据类型（文件：file,图片：image）
     * @return   
     */  
    CpaResult validateFile(MultipartFile[] files, String[] type);
}