package cn.kanyun.cpa.interceptor;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author Administrator
 * @Description: 刷新jwt 的token
 * @date 2018/7/27
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    /**
     * Token临界时间,有效剩余时间小于该时间,将刷新Token
     */
    private static Integer timeLeft = 20 * 60 * 1000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("=======进入刷新Token拦截器========");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//        获取token
        String token = request.getHeader("Authorization");
        if (null != token) {
            Claims claims = JwtUtil.parseJWT(token, CpaConstants.JWT_SECRET);
            logger.info("{} ,请求地址：{} ,当前Token：", LocalDateTime.now(), request.getRequestURI(), token);
//        获得过期时间
            Date expDate = claims.getExpiration();
//        如果过期时间距离当前时间小于20分钟,则重新生成token
            if (System.currentTimeMillis() - expDate.getTime() < timeLeft) {
//            获得个人信息
                String sub = claims.getSubject();
//            获得接收对象
                String audience = claims.getAudience();
//            生成新Token
                String newToken = JwtUtil.createJWT(sub, audience, CpaConstants.JWT_ISSUSER, CpaConstants.JWT_SECRET);

                logger.info("当前Token有效期不足20分钟,刷新Token,新Token为：", newToken);
//            将新token写到响应头中
                response.setHeader("Authorization", newToken);
            }
        }
    }
}
