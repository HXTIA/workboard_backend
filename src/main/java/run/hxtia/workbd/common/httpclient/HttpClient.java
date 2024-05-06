package run.hxtia.workbd.common.httpclient;

import lombok.extern.log4j.Log4j2;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import run.hxtia.workbd.common.util.Constants;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Log4j2
public class HttpClient {

    private static PoolingHttpClientConnectionManager clientConnectionManager = null;
    private static CloseableHttpClient httpClient = null;
    private static RequestConfig config = null;
    private static HttpRequestRetryHandler httpRequestRetryHandler = null;

    /***
     * 获得GET请求的参数
     * @param paramMap
     * @return
     */
    public static String getRequest(Map<String, String> paramMap) {
        StringBuffer stringBuffer = new StringBuffer();

        for (String key : paramMap.keySet()) {
                stringBuffer.append(key + "=" + paramMap.get(key) + "&");
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(), "");
        }
        return stringBuffer.toString();
    }


    /***
     * HTTP  get请求
     * @param url 请求URL
     * @return
     * @throws IOException
     */
    public static String httpGetRequest(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClient.getHttpClient();
        HttpGet get = new HttpGet(url);
        log.info("http开始请求=========");
        HttpResponse response = httpClient.execute(get);
        HttpEntity httpEntity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        if (code !=  Constants.HTTPClient.HTTP_SUCCESS) {
            log.error("请求异常 请求链接{},返回code{}", url, code);
        }
        log.info("http返回结果=========");
        return EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
    }

    /***
     * 执行POST请求
     * @param url
     * @param content
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, String content) throws IOException {
        CloseableHttpClient httpClient = HttpClient.getHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(content, Constants.HTTPClient.APPLICATION_JSON);
        post.setEntity(stringEntity);
        post.setEntity(new StringEntity(content, Charset.forName("UTF-8")));
        post.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        if (code != Constants.HTTPClient.HTTP_SUCCESS) {
            log.error("请求EVE异常 eve请求链接{},返回code{}", url, code);
        }
        return EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
    }

    /***
     * 执行POST请求
     * @param url
     * @param content
     * @return
     * @throws IOException
     */
    public static String httpPostWithHeader(String url, String content, Map<String, String> header) throws IOException {
        CloseableHttpClient httpClient = HttpClient.getHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(content, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        post.setEntity(new StringEntity(content, Charset.forName("UTF-8")));
        post.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        for (Map.Entry<String, String> entry : header.entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
        }
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        if (code != Constants.HTTPClient.HTTP_SUCCESS) {
            log.error("请求EVE异常 eve请求链接{},返回code{}", url, code);
        }
        return EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
    }

    /***
     * POST  key value
     * @param url
     * @param mapVal
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<String,String> mapVal) throws IOException {
        CloseableHttpClient httpClient = HttpClient.getHttpClient();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> pairList=new ArrayList<>(mapVal.size());
        for(Map.Entry<String,String> entry:mapVal.entrySet()){
            NameValuePair pair=new BasicNameValuePair(entry.getKey(),entry.getValue());
            pairList.add(pair);
        }
        post.setEntity(new UrlEncodedFormEntity(pairList,Charset.forName("UTF-8")));
//        post.setHeader(HTTP. Constants.HTTPClient.CONTENT_TYPE, ContentType. Constants.HTTPClient.APPLICATION_JSON.getMimeType());
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        if (code !=  Constants.HTTPClient.HTTP_SUCCESS) {
            log.error("请求EVE异常 eve请求链接{},返回code{}", url, code);
        }
        return EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
    }


    public static String httPostRequest(String url, StringEntity stringEntity) throws IOException {
        CloseableHttpClient httpClient = HttpClient.getHttpClient();
        HttpPost request = new HttpPost(url);
        request.setEntity(stringEntity);
        request.setHeader( Constants.HTTPClient.CONTENT_TYPE,  Constants.HTTPClient.APPLICATION_JSON);
        CloseableHttpResponse response = httpClient.execute(request);

        int code = response.getStatusLine().getStatusCode();
        if (code !=  Constants.HTTPClient.HTTP_SUCCESS) {
            log.error("请求EVE异常 eve请求链接{},返回code{}", url, code);
        }
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, Charset.forName( Constants.HTTPClient.CHARSET_UFS8));
    }


    public static String httPostRequestRetry(String url, StringEntity stringEntity) throws Exception {
        CloseableHttpClient httpClient = HttpClient.getHttpClient();
        HttpPost request = new HttpPost(url);
        request.setEntity(stringEntity);
        request.setHeader( Constants.HTTPClient.CONTENT_TYPE,  Constants.HTTPClient.APPLICATION_JSON);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (Exception e) {
            // 重试
            try {
                response = httpClient.execute(request);
            } catch (Exception e2) {
                throw e2;
            }
        }

        int code = response.getStatusLine().getStatusCode();
        if (code !=  Constants.HTTPClient.HTTP_SUCCESS) {
            log.error("请求异常 请求链接{},返回code{}", url, code);
        }
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, Charset.forName( Constants.HTTPClient.CHARSET_UFS8));
    }

    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClient.class) {
                if (httpClient == null) {
                    init();
                    httpClient = HttpClients.custom()
                            //连接管理器
                            .setConnectionManager(clientConnectionManager)
                            //默认请求配置
                            .setDefaultRequestConfig(config)
                            //重试策略
                            .setRetryHandler(httpRequestRetryHandler)
                            .build();
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建httpclient连接池并初始化
     */
    private static void init() {
        try {
            //这里设置信任所有证书
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .build();
            //配置连接池
            clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            //最大连接
            clientConnectionManager.setMaxTotal(50);
            //默认的每个路由的最大连接数
            clientConnectionManager.setDefaultMaxPerRoute(25);
            // socket配置（默认配置 和 某个host的配置）
            SocketConfig socketConfig = SocketConfig.custom()
                    //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                    .setTcpNoDelay(true)
                    //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                    .setSoReuseAddress(true)
                    //接收数据的等待超时时间，单位ms
                    .setSoTimeout(5000)
                    //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
                    .setSoLinger(6)
                    //开启监视TCP连接是否有效
                    .setSoKeepAlive(true)
                    .build();
            clientConnectionManager.setDefaultSocketConfig(socketConfig);
            clientConnectionManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);
            //消息约束
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            //Http connection相关配置
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            // 配置请求的超时设置
            config = RequestConfig.custom()
                    //连接超时时间
                    .setConnectTimeout(5000)
                    //读超时时间（等待数据超时时间）
                    .setSocketTimeout(5000)
                    //从池中获取连接超时时间
                    .setConnectionRequestTimeout(5000)
                    .build();
            //重试处理默认是重试1次;禁用重试(参数：retryCount、requestSentRetryEnabled)
            HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);
            //自定义重试策略
            httpRequestRetryHandler = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception,
                                            int executionCount, HttpContext context) {
                    // 如果已经重试了1次，就放弃
                    if (executionCount >= 1) {
                        return false;
                    }
                    // 如果服务器丢掉了连接，那么就重试
                    if (exception instanceof NoHttpResponseException) {
                        return true;
                    }
                    // 不要重试SSL握手异常
                    if (exception instanceof SSLHandshakeException) {
                        return false;
                    }
                    // 超时
                    if (exception instanceof InterruptedIOException) {
                        return false;
                    }
                    // 目标服务器不可达
                    if (exception instanceof UnknownHostException) {
                        return false;
                    }
                    // 连接被拒绝
                    if (exception instanceof ConnectTimeoutException) {
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext
                            .adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    //如果请求类型不是HttpEntityEnclosingRequest，被认为是幂等的，那么就重试
                    //HttpEntityEnclosingRequest指的是有请求体的request，比HttpRequest多一个Entity属性
                    //而常用的GET请求是没有请求体的，POST、PUT都是有请求体的
                    //Rest一般用GET请求获取数据，故幂等，POST用于新增数据，故不幂等
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
