package cn.kanyun.cpa.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanyun on 2018/2/23 0023.
 */
public class CookieUtils {

    /**
     * Cookie的名字
     */
    public static final String COOKIE_HISTORYID = "cookies_history";
    /**
     * Cookie的存活时间
     */
    public static final int COOKIE_LIFE_TIME = 14 * 24 * 60 * 60;
    /**
     * 最大的浏览历史记录条数
     */
    public static final int HISTORY_COUNT = 5;

    /**
     * 获得指定cookie中的值
     *
     * @param request
     * @param cookieName 要查找的cookie的名字
     * @return 返回指定Cookie中的字符串值
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {

        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 找到指定的cookie
                if (cookie != null && COOKIE_HISTORYID.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 设置指定cookie中的值（1.得到原先cookie中的值；2.重新设置cookie的值；3.保存重置后的cookie）
     *
     * @param request
     * @param response
     * @param cookieName 要设置的cookie的名字
     * @param goodsid    要添加到浏览历史中的 goodsID号
     * @param count      总共可以显示的历史记录条数
     */
    public static void setCookieValue(HttpServletRequest request,
                                 HttpServletResponse response, String cookieName, String goodsid,
                                 int count) {
        // 得到指定的cookie
        String ids = getCookie(request, cookieName);
        // 设置cookie中格的浏览记录
        ids = setValue(ids, goodsid, count);
        // 保存cookie
        saveCookie(request, response, cookieName, ids);
    }

    // 测试方法
    public static void main(String[] args) {
        System.out.println(setValue(null, "1", 3));
        System.out.println(setValue("1,2,3", "1", 3));
        System.out.println(setValue("2,1,3", "1", 3));
        System.out.println(setValue("2,1", "1", 3));
        System.out.println(setValue("2,3,4", "1", 3));
        System.out.println(setValue("2,4", "1", 3));
        System.out.println(setValue("4,3,1", "1", 3));
    }

    /**
     * 设置浏览历史字符串
     *
     * @param ids
     * @param sbookid 最新浏览的id号
     * @return 修改后的字符串
     */
    private static String setValue(String ids, String bookid, int count) {
        // 1、 没有任何记录--》直接添加 id
        // 2、 1,2,3 4--》 4,1,2
        // 3、 1,2,3 2 --》 2,1,3
        // 4、1,2 3--》 3,1,2
        // 如果不存在Cookie或者Cookie中没有值
        StringBuffer sb = new StringBuffer();
        if (ids == null) {
            sb.append(bookid);
        } else {
            List<String> list = Arrays.asList(ids.split("\\,"));
            LinkedList<String> idsList = new LinkedList<String>(list);
            // 未浏览过
            if (!idsList.contains(bookid)) {
                if (idsList.size() < count) {
                    idsList.addFirst(bookid);
                } else {
                    idsList.removeLast();
                    idsList.addFirst(bookid);
                }
            } else {
                // 如果包含已浏览的
                idsList.remove(bookid);
                idsList.addFirst(bookid);
            }
            for (String id : idsList) {
                sb.append(id).append(",");
            }
            if (sb != null && sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    /**
     * 保存cookie的值
     *
     * @param request
     * @param response
     * @param cookieName 要保存的cookie的名字
     * @param value      保存到cookie中的值
     */
    public static void saveCookie(HttpServletRequest request,
                                  HttpServletResponse response, String cookieName, String value) {
        saveCookie(request, response, cookieName, value, COOKIE_LIFE_TIME);
    }

    /**
     * 删除指定的cookie
     *
     * @param request
     * @param response
     * @param cookieName 要删除的cookie
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response, String cookieName) {
        saveCookie(request, response, cookieName, "", 0);
    }

    /**
     * 保存cookie 并设置cookie存活的时间
     *
     * @param request
     * @param response
     * @param cookieName 要保存的cookie的名字
     * @param value      cookie中要存放的值
     * @param time       cookie存活时间
     */
    public static void saveCookie(HttpServletRequest request,
                                  HttpServletResponse response, String cookieName, String value,
                                  int time) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(time);// 设置Cookie的存活时间
        cookie.setPath(request.getServletContext().getContextPath());
        response.addCookie(cookie);// 保存cookie
    }

    /**================================= 工具二 =================================*/

    /**
     * 得到Cookie的值, 不编码
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值,
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 得到Cookie的值,
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,但不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 设置Cookie的值 不设置生效时间,但编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * 删除Cookie带cookie域名
     */
    public static void deleteCookieAndDomain(HttpServletRequest request, HttpServletResponse response,
                                             String cookieName) {
        doSetCookie(request, response, cookieName, "", -1, false);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     *
     * @param cookieMaxage cookie生效的最大秒数
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                          String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0)
                cookie.setMaxAge(cookieMaxage);
            if (null != request) {// 设置域名的cookie
                String domainName = getDomainName(request);
                System.out.println(domainName);
                if (!"localhost".equals(domainName)) {
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     *
     * @param cookieMaxage cookie生效的最大秒数
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                          String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0)
                cookie.setMaxAge(cookieMaxage);
            if (null != request) {// 设置域名的cookie
                String domainName = getDomainName(request);
                System.out.println(domainName);
                if (!"localhost".equals(domainName)) {
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到cookie的域名
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName = null;

        String serverName = request.getRequestURL().toString();
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
}
