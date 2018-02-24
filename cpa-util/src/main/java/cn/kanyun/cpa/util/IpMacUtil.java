package cn.kanyun.cpa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.*;
import java.util.Enumeration;

/**
 *   
 *
 * @author Jack 
 * @version V1.0  
 * @Title: IpMacUtil.java
 * @Package com.jarvis.base.util
 * @Description:Ip工具类。
 * @date 2017年9月2日 下午3:05:00
 */
public class IpMacUtil {

    /**
     * 隐藏IP的最后一段
     *
     * @param ip 需要进行处理的IP
     * @return 隐藏后的IP
     */
    public static String hideIp(String ip) {
        if (StringHelper.isEmpty(ip)) {
            return "";
        }

        int pos = ip.lastIndexOf(".");
        if (pos == -1) {
            return ip;
        }

        ip = ip.substring(0, pos + 1);
        ip = ip + "*";
        return ip;
    }

    /**
     * 获取IP地址.
     *
     * @param request  HTTP请求.
     * @param response HTTP响应.
     * @param url      需转发到的URL.
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断该字串是否为IP
     *
     * @param ipStr IP字串
     * @return
     */
    public static boolean isIP(String ipStr) {
        String ip = "(25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";
        String ipDot = ip + "\\.";
        return ipStr.matches(ipDot + ipDot + ipDot + ip);
    }

    /**
     * 获取客户端Mac
     *
     * @param ip
     * @return
     */
    public static String getMACAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            return "";
        }
        return macAddress;
    }

    /*===============获取服务器本机ip===============*/

    /**
     * 获得内网IP
     * 需要注意的是，如果本机存在多块网卡(包括虚拟网卡),返回的结果可能存在问题，所以尽量禁用不用的网卡
     * @return 内网IP
     */
    public static String getIntranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取公网IP,通过请求ip.chinaz.com来实现，相对来说是比较准确的，前提是本地服务器可以接入公网
     * 通过请求 "http://ip.chinaz.com/getip.aspx" 返回回来的是一个json字符串 格式如下 ： {ip:'221.221.239.44',address:'北京市海淀区 联通'}
     * 没有接入公网的话会报“java.net.UnknownHostException”异常 ，个人感觉这个方法局限性比较大，因为大部分应用服务器都接的是局域网
     * 用以避免外网直接访问应用服务器，其接收请求大都通过外网访问web服务器(如Nginx),nginx通过反向代理将请求转发到应用服务器上，所以应用
     * 服务器是无法连接公网的，所以局限性很大
     *
     * @return 返回数组类型 ，0 为ip地址 ，1 为区域地址
     * @throws IOException
     */
    public static String[] getPublicNetWorkIp() throws IOException {
        String chinaz = "http://ip.chinaz.com/getip.aspx";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        String read = "";
        url = new URL(chinaz);
        urlConnection = (HttpURLConnection) url.openConnection();
        in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        while ((read = in.readLine()) != null) {
            sb.append(read);
        }
//        sb.toString();
        JSONObject jb = JSON.parseObject(sb.toString());
//        jb.get("ip");
//        jb.get("address");
        String[] ip = {String.valueOf(jb.get("ip")), String.valueOf(jb.get("address"))};
        return ip;
    }

    /**
     * 获得外网IP
     * @return 外网IP
     */
    public static String getInternetIp(){
        try{
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements())
            {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements())
                {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(getIntranetIp()))
                    {
                        return ip.getHostAddress();
                    }
                }
            }

            // 如果没有外网IP，就返回内网IP
            return getIntranetIp();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
