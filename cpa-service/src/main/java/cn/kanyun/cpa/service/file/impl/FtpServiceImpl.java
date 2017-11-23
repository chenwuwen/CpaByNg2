package cn.kanyun.cpa.service.file.impl;


import cn.kanyun.cpa.service.file.IFtpService;
import cn.kanyun.cpa.util.FTPUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(IFtpService.SERVICE_NAME)
public class FtpServiceImpl implements IFtpService {
    @Resource
    private FTPUtil ftpUtil;

    @Override
    public Enum uploadFTP(String localDirectory, String remoteDirectory) throws Exception {

        ftpUtil.connect();
        Enum e = ftpUtil.upload(localDirectory, remoteDirectory);
        ftpUtil.disconnect();
        return e;
    }


}
