package cn.kanyun.cpa.service.file.impl;


import cn.kanyun.cpa.service.file.IFtpService;
import cn.kanyun.cpa.util.FTPUtil;
import cn.kanyun.cpa.util.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(IFtpService.SERVICE_NAME)
public class FtpServiceImpl implements IFtpService {
    @Resource
    private FTPUtil ftpUtil;

    @Override
    public Enum uploadFTP(String localDirectory, String remoteDirectory) throws Exception {

        ftpUtil.connect();
        Enum e = null;
        //下面这两句代码注意不能颠倒
        String prefix = localDirectory.substring(localDirectory.lastIndexOf("/") + 1);
        prefix = prefix.substring(0, prefix.lastIndexOf("."));
        localDirectory = localDirectory.substring(0, localDirectory.lastIndexOf("/"));
        List<String> filePaths = FileUtil.searchFile(localDirectory, prefix, null);
        for (String localpath : filePaths) {
            remoteDirectory = remoteDirectory.substring(0, remoteDirectory.lastIndexOf("/")) + localpath.substring(localpath.lastIndexOf("/"));
            e = ftpUtil.upload(localpath, remoteDirectory);
        }
//        e = ftpUtil.upload(localDirectory, remoteDirectory);
        ftpUtil.disconnect();
//        if (e.toString().indexOf("SUCCESS") != -1) {
//
//        }
        return e;
    }


}
