package cn.kanyun.cpa.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * java连接linux,并执行命令
 * @author Kanyun
 * @date 2019/6/17
 */
public class SSHUtil {

    private static final int timeout = 1000 * 60;
    /**
     * 执行命令,多个命令 直接用分号 ; 分割
     * @param host
     * @param port
     * @param user
     * @param password
     * @param command
     * @return
     * @throws JSchException
     * @throws IOException
     */
    public static String exeCommand(String host, int port, String user, String password, String command) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
//        跳过主机检查
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect(timeout);

        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        String out = IOUtils.toString(in, "UTF-8");

        channelExec.disconnect();
        session.disconnect();

        return out;
    }

}
