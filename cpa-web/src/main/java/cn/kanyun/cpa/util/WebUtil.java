package cn.kanyun.cpa.util;


import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.redis.service.RedisService;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kanyun on 2016/5/16 0016.
 */
public class WebUtil {

    @Resource(name = RedisService.SERVICE_NAME)
    private static RedisService redisService;

    /**
     * 在session中获得user
     *
     * @param request
     * @return
     */
    public synchronized static CpaUser getSessionUser(HttpServletRequest request) {
        CpaUser user = null;
        try {
            if (CpaConstants.SESSION_USE_REDIS) {
                user = (CpaUser) redisService.getCacheObject(request.getHeader("Authorization"));
            } else {
                user = (CpaUser) request.getSession().getAttribute(CpaConstants.USER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 将user保存到session中
     *
     * @param request
     * @return
     */
    public synchronized static void setSessionUser(HttpServletRequest request, CpaUser us) {
        try {
            if (CpaConstants.SESSION_USE_REDIS) {
                String token = JwtUtil.createJWT(JwtUtil.generalSubject(us), us.getUserName(), CpaConstants.JWT_ISSUSER, CpaConstants.JWT_SECRET);
                redisService.setCacheObject(token, us);
            } else {
                request.getSession().setAttribute(CpaConstants.USER, us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将user从session中移除
     *
     * @param request
     * @return
     */
    public synchronized static void removeSessionUser(HttpServletRequest request) {
        request.getSession().removeAttribute(CpaConstants.USER);
    }

    /**
     * 验证当前的手机浏览器是否是app内置的浏览器
     *
     * @param request
     * @return
     */
    public static Boolean isBuiltInBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        String regExpWeChat = "micromessenger";
        String regExpWeBo = "weibo/";
        String regExpQq = "qq/";
        String regExUc = "uc";

        if (isMatchedStr(regExpQq, userAgent)) {//是QQ内置的浏览器
            return true;
        } else if (isMatchedStr(regExpWeChat, userAgent)) {//微信内置浏览器
            return true;
        } else if (isMatchedStr(regExpWeBo, userAgent)) {//微博内置浏览器
            return true;
        } else if (isMatchedStr(regExUc, userAgent)) {
            return true;
        }
        return false;
    }

    private static boolean isMatchedStr(String regex, String str) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        String pattern = p.pattern();
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断是否是手机端访问
     *
     * @param request
     * @return
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        String requestHeader = request.getHeader("user-agent");
        String[] deviceArray = new String[]{"android", "mac os", "windows phone"};
        if (requestHeader == null) {
            return false;
        }
        requestHeader = requestHeader.toLowerCase();
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Author: kanyun
     * @Description: 获取客户端IP地址
     * 获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。
     * 但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了，如果通过了多级反向代理的话，
     * X-Forwarded-For的值并不止一个，而是一串IP值， 究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 例如：X-Forwarded-For：192.168.1.110, 192.168.1.120,
     * 192.168.1.130, 192.168.1.100 用户真实IP为： 192.168.1.110
     * @Date: 2017/7/19 8:41
     * @params:
     */

    public static String getClientIp(HttpServletRequest request) {
        String client_ip = request.getHeader("x-forwarded-for");
//        每一个if条件都一样,如果第一个if获取不到就接着第二个获取，如果获取到了下面就不进行，如此类推
        if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("Proxy-Client-IP");
        }
        if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip)) {
            client_ip = request.getRemoteAddr();
            if (client_ip.equals("127.0.0.1") || client_ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                client_ip = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (client_ip != null && client_ip.length() > 15) {  //"***.***.***.***".length() = 15
            if (client_ip.indexOf(",") > 0) {
                client_ip = client_ip.substring(0, client_ip.indexOf(","));
            }
        }

//        String server_ip = InetAddress.getLocalHost().getHostAddress();
        return client_ip;
    }

    /**
     * @Author: kanyun
     * @Description: 使用淘宝的IP地址库, 获取用户ip地址所在地, 需联网
     * @Date: 2017/7/19 8:51
     * @params:
     */
    public static String getClientAddr(HttpServletRequest request) {
        //淘宝IP地址库：http://ip.taobao.com/instructions.php
        String clientAddr = null;
        String ip = getClientIp(request);
        try {
            //URL U = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=114.111.166.72");
            URL U = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            URLConnection connection = U.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            JSONObject jsonObject = JSONObject.parseObject(result);
            Map<String, Object> map = (Map) jsonObject;
            String code = String.valueOf(map.get("code"));//0：成功，1：失败。
            if ("1".equals(code)) {//失败
                String data = String.valueOf(map.get("data"));//错误信息
            } else if ("0".equals(code)) {//成功
                Map<String, Object> data = (Map<String, Object>) map.get("data");

                String country = String.valueOf(data.get("country"));//国家
                String area = String.valueOf(data.get("area"));//
                String city = String.valueOf(data.get("city"));//省（自治区或直辖市）
                String region = String.valueOf(data.get("region"));//市（县）
                clientAddr = country + "-" + city + "-" + region;
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientAddr;
    }


}