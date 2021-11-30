package tcf.httpclient.factory;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import tcf.httpclient.interceptors.RestTempateLoggingInterceptor;

/**
 * <P>
 *  OkHttp与RestTemplate整合的工厂类
 * </P>
 * 
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Configuration
public class OkHttpRestTemplateFactory {

    @Value("${ok.http.connect-timeout:60}")
    private Integer connectTimeout;

    @Value("${ok.http.read-timeout:60}")
    private Integer readTimeout;

    @Value("${ok.http.write-timeout:60}")
    private Integer writeTimeout;

    @Value("${ok.http.max-idle-connections:5}")
    private Integer maxIdleConnections;

    @Value("${ok.http.keep-alive-duration:300}")
    private Long keepAliveDuration;


    /**
     * 声明 RestTemplate
     */
    @Bean
    public RestTemplate httpRestTemplate() {
        ClientHttpRequestFactory factory = httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        //RestTemplate restTemplate = new RestTemplate();
        // 可以添加消息转换
        //restTemplate.setMessageConverters(null);
        // 可以增加拦截器
        //restTemplate.setInterceptors(...);
        restTemplate.getInterceptors().add(new RestTempateLoggingInterceptor());
        return restTemplate;
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(okHttpConfigClient());
    }

    public OkHttpClient okHttpConfigClient(){
         return new OkHttpClient().newBuilder()
                 .connectionPool(pool())
                 .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                 .readTimeout(readTimeout, TimeUnit.SECONDS)
                 .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                 .hostnameVerifier((hostname, session) -> true)
                 // 设置代理
//              .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
                 // 拦截器
//                .addInterceptor()
                 .build();
    }

    public ConnectionPool pool() {
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
    }
}
