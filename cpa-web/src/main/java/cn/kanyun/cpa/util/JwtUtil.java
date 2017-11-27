package cn.kanyun.cpa.util;

import cn.kanyun.cpa.model.entity.user.CpaUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import net.sf.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Created by KANYUN on 2017/11/27.
 * Java Web Token 工具类
 * https://github.com/jwtk/jjwt
 * http://www.jianshu.com/p/fcc1a6482143?from=timeline
 * JWTs包含三部分，每一个都附加.分隔符。这三部分是：
 * Header:我们注册token算法和类型的地方
 * Payload:JWT的本质。保持所有我们所有需要数据的JSON对象。所需数据包含注册在JWTs配置和我们想要的任意数据。
 * Signature：Signature是签名动作发生的地方，为了得到签名，我们使用Base64URL编码头部，接着使用Base64URL编码payoad，然后把这段字符串和密钥一起使用哈希算法加密。token在服务端解码，需要以上信息。这就意味着如果谁想改变token中的信息，那将是很难做到的。
 */

/**
 * JWT有什么好处
 * 1、支持跨域访问: Cookie是不允许垮域访问的，这一点对Token机制是不存在的，前提是传输的用户认证信息通过HTTP头传输.
 * 2、无状态(也称：服务端可扩展行):Token机制在服务端不需要存储session信息，因为Token 自身包含了所有登录用户的信息，只需要在客户端的cookie或本地介质存储状态信息.
 * 4、更适用CDN: 可以通过内容分发网络请求你服务端的所有资料（如：javascript，HTML,图片等），而你的服务端只要提供API即可.
 * 5、去耦: 不需要绑定到一个特定的身份验证方案。Token可以在任何地方生成，只要在你的API被调用的时候，你可以进行Token生成调用即可.
 *6、更适用于移动应用: 当你的客户端是一个原生平台（iOS, Android，Windows 8等）时，Cookie是不被支持的（你需要通过Cookie容器进行处理），这时采用Token认证机制就会简单得多。
 * 7、CSRF:因为不再依赖于Cookie，所以你就不需要考虑对CSRF（跨站请求伪造）的防范。
 * 8、性能: 一次网络往返时间（通过数据库查询session信息）总比做一次HMACSHA256计算 的Token验证和解析要费时得多.
 * 9、不需要为登录页面做特殊处理: 如果你使用Protractor 做功能测试的时候，不再需要为登录页面做特殊处理.
 * 10、基于标准化:你的API可以采用标准化的 JSON Web Token (JWT). 这个标准已经存在多个后端库（.NET, Ruby, Java,Python, PHP）和多家公司的支持（如：Firebase,Google, Microsoft）.
 */

/**
 * 流程
 * 1、用户认证。认证方式可能很多，自己认证或者sso。
 * 2、认证后，服务器构造JWT。
 *3、把JWT返回客户端，客户端存储。
 * 4、客户端访问服务器，带上JWT。
 * 5、服务器端判断JWT是否正确并且没有超时，正常，向下流转；否则，转到授权。
 */

/**
 * 客户端
 * jwt存储方式自己灵活掌握。
 * web：cookie／localStorage／sessionStorage／；
 * app：内存
 */
public class JwtUtil {

//    Key key = MacProvider.generateKey();//这里是加密解密的key。github Demo上Key的获取方式

    /**
     * @param jsonWebToken:为jwt字符串
     * @return
     * @author Kanyun
     * @date 2017/11/27 19:42
     * @class_name: JwtUtil
     * @Description: 解密Token字符串
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody(); //得到body后我们可以从body中获取我们需要的信息
            /**
             * //比如 获取主题,当然，这是我们在生成jwt字符串的时候就已经存进来的
             * String subject = body.getSubject();
             */
            return claims;
        } catch (Exception ex) {
            /**
             * ExpiredJwtException
             * jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，
             * 如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
             */
            return null;
        }

    }


    /**
     * @param base64Security:生成加解密key的原材料（自定义的字符串）TTLMillis：Token过期时间 Issuser:代表这个JWT的签发主体 Audience：代表这个JWT的接收对象
     * @return
     * @author Kanyun
     * @date 2017/11/27 19:43
     * @class_name: JwtUtil
     * @Description: 生成Token字符串, 用以返回给前台
     */
    public static String createJWT(String subject,
                                   String audience, String issuer, long TTLMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //设置算法（必须）
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥 就是一个base64加密后的字符串？这里是加密解密的key。
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuedAt(now) //创建时间
                .setSubject(subject) //主题，也差不多是个人的一些信息
                .setIssuer(issuer) //JWT的签发主体
                .setAudience(audience) //JWT的接收对象
                .signWith(signatureAlgorithm, signingKey); //估计是第三段密钥
        //添加Token过期时间
        if (TTLMillis >= 0) {
            //过期时间
            long expMillis = nowMillis + TTLMillis;
            //现在是什么时间
            Date exp = new Date(expMillis);
            //系统时间之前的token都是不可以被承认的
            builder.setExpiration(exp).setNotBefore(now);
        }
        return builder.compact(); //这个是全部设置完成后拼成jwt串的方法


    }

    /**
     * 生成subject信息
     * @param user 为自己用户token的一些信息比如id，权限，名称等。不要将隐私信息放入(因为payload能够被类似jwt.io的调试工具轻松解码)
     * @return
     */
    public static String generalSubject(CpaUser user){
        JSONObject jo = new JSONObject();
        jo.put("userId", user.getId());
        jo.put("userMame", user.getUserName());
        return jo.toString();
    }


}
