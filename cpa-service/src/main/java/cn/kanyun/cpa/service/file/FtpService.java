package cn.kanyun.cpa.service.file;

public interface FtpService {
    public static final String SERVICE_NAME = "cn.kanyun.cpa.Service.file.impl.FtpServiceImpl";
    
    /**    
     *   
     * @author Kanyun 
     * @Description: 上传文件到FTP
     * @date 2017/11/22 23:01
     * @param   
     * @return   
     */
    Enum uploadFTP(String localDirectory,String remoteDirectory) throws Exception;

}
