package cn.kanyun.cpa.service.file;

/**
 * @author Kanyun
 */
public interface FtpService {

    String SERVICE_NAME = "cn.kanyun.cpa.Service.file.impl.FtpServiceImpl";

    /**
     * @param
     * @return
     * @author Kanyun
     * @Description: 上传文件到FTP
     * @date 2017/11/22 23:01
     */
    Enum uploadFTP(String localDirectory, String remoteDirectory) throws Exception;

}
