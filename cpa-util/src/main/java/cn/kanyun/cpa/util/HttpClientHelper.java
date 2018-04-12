package cn.kanyun.cpa.util;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 使用HttpClient保持会话(主要是JSESSIONID,当然可以换成TOKEN)有两种方式：
 * 1.登录方法，与请求方法与登录方法之间存在调用关系,即:远程登录完毕之后立即调用请求方法,最后结束
 * 这种方式保持会话的关键是：BasicHttpContext BasicHttpContext里有个Map<Object,Object>的对象用来记录一次请求响应的信息，当响应信息返回时，
 * 就会被set到context里，当然响应的cookie信息也就被存储在context里,包括传回的sessionId，当第二次请求的时候传入相同的context，那么请求的过程中会将context里的sessionId提取出来传给服务器，
 * sessionId一样，自然而然的就是同一个session对象，总结来说代表一个逻辑相关的会话的多个请求序列应该具有相同的HttpContext实例以确保请求之间的信息状态的自动传播
 * 那么对于这种方式，我们需要做的就是定义一个全局的Context
 * private static HttpContext localContext = new BasicHttpContext();
 * private static HttpClientContext context = HttpClientContext.adapt(localContext);
 * 定义好之后，只要顺序执行(即先登录,后请求) excute(httpClient,context)就可以了
 * 详见地址：https://blog.csdn.net/z69183787/article/details/70809497
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientHelper {

    // utf-8字符编码
    private static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。
    private static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型。相当于form表单的形式，提交数据
    private static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // HTTP内容类型。相当于form表单的形式，提交数据
    private static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";

    //保持会话的关键：全局的context,但是需要在一个线程里面执行,也就是一个方法完成登录及其他请求(需要保证是同一个HttpClient对象),但是如果登录之后获取JESSIOINID,之后在请求的方法里带上JESSIONID保持会话是不能实现的
    private static HttpContext localContext = new BasicHttpContext();
    private static HttpClientContext context = HttpClientContext.adapt(localContext);

    

    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {
        
        try {
            //System.out.println("初始化HttpClientTest~~~开始");
			SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();

            //System.out.println("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        

        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000).build();
    }

    private static CloseableHttpClient getHttpClient() {
        
        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
        
        return httpClient;
    }

    /**
     * 发送Post请求
     * 
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求
     * 
     * @param httpGet
     * @return
     */
    private static String sendHttpGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
    
    
    
    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     */
    public static String sendHttpPost(String httpUrl) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 get请求
     * 
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }
    
    /**
     * 发送 get请求
     * 
     * @param maps
     *            参数
     */
    public static String sendHttpGet(String httpUrl, Map<String, String> maps) {
        String param = convertStringParamter(maps);
//        System.out.println(param);
        return sendHttpGet(httpUrl+"?"+param);
    }

    

    /**
     * 发送 post请求（带文件）
     * 
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param fileLists
     *            附件
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (maps != null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
            }
        }
        if (fileLists != null) {
            for (File file : fileLists) {
                FileBody fileBody = new FileBody(file);
                meBuilder.addPart("files", fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     * 
     */
    public static String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (params != null && params.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(params, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * 
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        String parem = convertStringParamter(maps);
        return sendHttpPost(httpUrl, parem);
    }

    
    
    
    /**
     * 发送 post请求 发送json数据
     * 
     * @param httpUrl
     *            地址
     * @param paramsJson
     *            参数(格式 json)
     * 
     */
    public static String sendHttpPostJson(String httpUrl, String paramsJson) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_JSON_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }
    
    /**
     * 发送 post请求 发送xml数据
     * 
     * @param httpUrl   地址
     * @param paramsXml  参数(格式 Xml)
     * 
     */
    public static String sendHttpPostXml(String httpUrl, String paramsXml) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (paramsXml != null && paramsXml.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsXml, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_TEXT_HTML);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }
    

    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     * 
     * @param parameterMap
     *            需要转化的键值对集合
     * @return 字符串
     */
    @SuppressWarnings("rawtypes")
	private static String convertStringParamter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

//    public static void main(String[] args) throws Exception {
//        
////        System.out.println(sendHttpGet("http://www.baidu.com"));
//    	Map<String,String> param = new HashMap<String, String>();
//    	param.put("name", "1213131");
//    	param.put("age", "18");
//        System.out.println(sendHttpGet("http://127.0.0.1:8081/member/getMemberInfoByUserId",param));
//    
//    }
}