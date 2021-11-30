package tcf.httpclient.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tcf.httpclient.entity.RespData;
import tcf.httpclient.exception.AuthorizationIsNullException;

/**
 * <P>
 * 	通过RestTemplate发送请求的工具类
 * </P>
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Component
@Slf4j
public class RestTemplateUtils {
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	/**
	 * 返回加载的 restTemplate
	 * @return
	 */
	public RestTemplate restTemplate() {
		return restTemplate;
	}
	
	/**
	 * 打印异常日志信息
	 * @param status 请求类型
	 * @param url 请求url 
	 * @param respData 响应信息
	 */
	public void loggerRequestInfo(HttpMethod method, String url) {
		log.info("请求: {} {}",method.name(), url);
	}
	
	/**
	 * 打印异常日志信息
	 * @param status 请求类型
	 * @param url 请求url 
	 * @param respData 响应信息
	 */
	public void loggerResponseInfo(RespData<?> respData) {
		String body = respData.getData() ==null?respData.getError():respData.getData().toString();
		log.info("响应: {} {}",respData.getStatus(),body);
	}

	
	/**
	 * 成功响应转为响应对象
	 * @param <R> 响应数据对象
	 * @param responseEntity 响应对象
	 * @param bodyClass 响应数据对象字节码
	 * @return 响应对象
	 */
	public <R> RespData<R> successResp2RespData(ResponseEntity<R> responseEntity,Class<R> bodyClass) {
		RespData<R> respData = new RespData<>();
		HttpStatus statusCode = responseEntity.getStatusCode();
		R body = responseEntity.getBody();
		respData.setStatus(statusCode);
		respData.setData(body);
		return respData;
	}
	
	/**
	 * 请求失败转为响应对象
	 * @param e 异常信息
	 * @return 响应对象
	 */
	public <R> RespData<R> errorResp2RespData(HttpStatusCodeException e){
		RespData<R> respData = new RespData<>();
		HttpStatus statusCode = e.getStatusCode();
		String body = e.getResponseBodyAsString();
		respData.setStatus(statusCode);
		respData.setError(body);
		return respData;
	}
	
	/**
	 * 对rest进行简单封装
	 * @param <R> 响应数据对象
	 * @param url 请求url
	 * @param method 请求方式
	 * @param requestEntity 请求体
	 * @param responseType 响应数据对象字节码
	 * @return
	 */
	public <R> RespData<R> rest(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<R> responseType){
		RespData<R> respData = null;
		try {			
			loggerRequestInfo(method,url);
			ResponseEntity<R> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
			respData = successResp2RespData(responseEntity, responseType);
		}catch (HttpClientErrorException e) {
			respData = errorResp2RespData(e);
		} catch (HttpServerErrorException e) {
			respData = errorResp2RespData(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		loggerResponseInfo(respData);
		return respData;
	}
	
	/**
	 * 获取响应头
	 * @param url 请求url
	 * @param method 请求方式
	 * @param paramObj 请求参数对象
	 * @return
	 */
	public RespData<HttpHeaders> responseHeaders(String url, HttpMethod method,Object paramObj){
		RespData<HttpHeaders> respData = new RespData<>();
		try {
			loggerRequestInfo(method,url);
			MultiValueMap<String, Object> params = HttpUrlPramsUtils.obj2MultiValueMap(paramObj, false, null);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<?> requestEntity = new HttpEntity<>(params,headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(url,method,requestEntity,String.class);
			respData.setData(responseEntity.getHeaders());
		}catch (HttpClientErrorException e) {
			respData = errorResp2RespData(e);
		} catch (HttpServerErrorException e) {
			respData = errorResp2RespData(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		loggerResponseInfo(respData);
		return respData;
	}
	
	/**
	 * 生成默认的请求头
	 * @param authorization 认证信息
	 * @return
	 */
	public HttpHeaders generaHeader(String authorization) {
		if(StringUtils.isEmpty(authorization)) {
			throw new AuthorizationIsNullException();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		return headers;
	}
}
