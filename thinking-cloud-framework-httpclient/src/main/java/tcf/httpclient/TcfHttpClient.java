package tcf.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import tcf.httpclient.entity.DownloadFile;
import tcf.httpclient.entity.RespData;
import tcf.httpclient.entity.UploadFile;
import tcf.httpclient.exception.TcfHttpClientException;
import tcf.httpclient.utils.HttpUrlPramsUtils;
import tcf.httpclient.utils.RestTemplateUtils;
import tcf.utils.data.GsonUtils;

/**
 * <P>
 * 	发送rest请求的工具类
 * </P>
 * 
 * @author think
 * @date 2020年11月18日
 */
@Component
@Data
public class TcfHttpClient {
	@Autowired
	private RestTemplateUtils restTemplateUtils;
	
	/**
	 * 返回加载的restTemplate
	 * @return
	 */
	public RestTemplate restTemplate() {
		return restTemplateUtils.restTemplate();
	}

	/**
	 * 发送get请求
	 * 
	 * @param <P>       请求参数对象
	 * @param <R>       响应数据对象
	 * @param url       请求url
	 * @param headers   请求头
	 * @param params    请求参数
	 * @param bodyClass 响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名	 
	 * @return
	 */
	public <P, R> RespData<R> get(String url, HttpHeaders headers, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		url = HttpUrlPramsUtils.replaceVariablePath(url,params);
		url = HttpUrlPramsUtils.obj2UrlParams(url, params, true,exculdeFieldNames);
		return restTemplateUtils.rest(url, HttpMethod.GET, new HttpEntity<String>(headers), bodyClass);

	}

	/**
	 * 发送get请求
	 * 
	 * @param <P>           请求参数对象
	 * @param <R>           响应数据对象
	 * @param url           请求url
	 * @param authorization 认证请求头
	 * @param params        请求参数
	 * @param bodyClass     响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> get(String url, String authorization, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		return get(url, restTemplateUtils.generaHeader(authorization), params, bodyClass,exculdeFieldNames);
	}

	/**
	 * 发送post请求
	 * 
	 * @param <P>       请求参数对象
	 * @param <R>       响应数据对象
	 * @param url       请求url
	 * @param headers   请求头
	 * @param params    请求参数
	 * @param bodyClass 响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> post(String url, HttpHeaders headers, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		url = HttpUrlPramsUtils.replaceVariablePath(url,params);
		String paramsJson = GsonUtils.toJson(params);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return restTemplateUtils.rest(url, HttpMethod.POST, new HttpEntity<String>(paramsJson, headers), bodyClass);
	}

	/**
	 * 发送post请求
	 * 
	 * @param <P>           请求参数对象
	 * @param <R>           响应数据对象
	 * @param url           请求url
	 * @param authorization 认证信息
	 * @param params        请求参数
	 * @param bodyClass     响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> post(String url, String authorization, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		return post(url, restTemplateUtils.generaHeader(authorization), params, bodyClass,exculdeFieldNames);
	}

	/**
	 * 发送put请求
	 * 
	 * @param <P>       请求参数对象
	 * @param <R>       响应数据对象
	 * @param url       请求url
	 * @param headers   请求头
	 * @param params    请求参数
	 * @param bodyClass 响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> put(String url, HttpHeaders headers, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		url = HttpUrlPramsUtils.replaceVariablePath(url,params);
		String paramsJson = GsonUtils.toJson(params);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return restTemplateUtils.rest(url, HttpMethod.PUT, new HttpEntity<String>(paramsJson, headers), bodyClass);
	}

	/**
	 * 发送put请求
	 * 
	 * @param <P>           请求参数对象
	 * @param <R>           响应数据对象
	 * @param url           请求url
	 * @param authorization 认证信息
	 * @param params        请求参数
	 * @param bodyClass     响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> put(String url, String authorization, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		return put(url, restTemplateUtils.generaHeader(authorization), params, bodyClass,exculdeFieldNames);
	}

	/**
	 * 发送delete请求
	 * 
	 * @param <P>       请求参数对象
	 * @param <R>       响应数据对象
	 * @param url       请求url
	 * @param headers   请求头
	 * @param params    请求参数
	 * @param bodyClass 响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> delete(String url, HttpHeaders headers, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		url = HttpUrlPramsUtils.replaceVariablePath(url,params);
		String paramsJson = GsonUtils.toJson(params);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return restTemplateUtils.rest(url, HttpMethod.DELETE, new HttpEntity<String>(paramsJson, headers), bodyClass);
	}

	/**
	 * 发送delete请求
	 * 
	 * @param <P>           请求参数对象
	 * @param <R>           响应数据对象
	 * @param url           请求url
	 * @param authorization 认证信息
	 * @param params        请求参数
	 * @param bodyClass     响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P, R> RespData<R> delete(String url, String authorization, P params, Class<R> bodyClass,String... exculdeFieldNames) {
		return delete(url, restTemplateUtils.generaHeader(authorization), params, bodyClass,exculdeFieldNames);
	}

	/**
	 * 上传文件
	 * 
	 * @param <P>	请求参数对象
	 * @param <R>	响应数据对象
	 * @param url 	请求url
	 * @param method    请求方式
	 * @param headers 请求头
	 * @param params 请求参数对象
	 * @param bodyClass 响应对象字节码
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <R> RespData<R> upload(String url, HttpMethod method,HttpHeaders headers, UploadFile file, Class<R> bodyClass,String... exculdeFieldNames) {
		url = HttpUrlPramsUtils.replaceVariablePath(url,file);
		if(headers ==null) {
			headers = new HttpHeaders();
		}
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> paramsMap = HttpUrlPramsUtils.obj2MultiValueMap(file, true);
		return restTemplateUtils.rest(url, method, new HttpEntity<MultiValueMap<String, Object>>(paramsMap, headers),
				bodyClass);
	}

	/**
	 * 上传文件
	 * 
	 * @param <P>       请求参数对象
	 * @param <R>       响应数据对象
	 * @param url       请求url
	 * @param method    请求方式
	 * @param headers   请求头
	 * @param params    请求参数
	 * @param bodyClass 响应数据对象
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public <P,R> RespData<R> upload(String url, HttpMethod method,String authorization, UploadFile file, Class<R> bodyClass,String... exculdeFieldNames) {
		HttpHeaders headers = restTemplateUtils.generaHeader(authorization);
		return upload(url, method,headers, file, bodyClass,exculdeFieldNames);
	}

	/**
	 * 下载小文件
	 * <p>
	 * 	文件数据存储在内存中
	 * </p>
	 * @param url       请求url
	 * @param headers   请求头
	 * @param restFile  请求数据
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public RespData<Resource> download(String url, HttpHeaders headers, DownloadFile file,String... exculdeFieldNames) {
		url = HttpUrlPramsUtils.replaceVariablePath(url,file);
		url = HttpUrlPramsUtils.obj2UrlParams(url, file, true, exculdeFieldNames);
		return restTemplateUtils.rest(url, HttpMethod.GET, new HttpEntity<String>(headers), Resource.class);
	}
	
	/**
	 * 下载小文件
	 * <p>
	 * 	文件数据存储在内存中
	 * </p>
	 * @param url 请求url
	 * @param authorization 认证信息 
	 * @param restFile 请求数据
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public RespData<Resource> download(String url, String authorization, DownloadFile file,String... exculdeFieldNames) {
		HttpHeaders headers = restTemplateUtils.generaHeader(authorization);
		return download(url, headers, file,exculdeFieldNames);
	}

	/**
	 * 下载大文件
	 * <p>
	 * 	文件数据存储到硬盘
	 * </p>
	 * @param url 下载地址
	 * @param headers 请求头
	 * @param restFile 请求信息 
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public RespData<File> downloadBigFile(String url, HttpHeaders headers, DownloadFile downloadFile,String... exculdeFieldNames) {
		RespData<File> respData = new RespData<>();
		url = HttpUrlPramsUtils.replaceVariablePath(url,respData);
		url = HttpUrlPramsUtils.obj2UrlParams(url, downloadFile, true);
		try {
			restTemplateUtils.loggerRequestInfo(HttpMethod.GET, url);
			File file = restTemplate().execute(url, HttpMethod.GET,request-> {
				for (Entry<String, List<String>> header : headers.entrySet()) {
					request.getHeaders().addAll(header.getKey(), header.getValue());
				}			
			}, clientHttpResponse -> {
				File ret = File.createTempFile(downloadFile.getPath(), downloadFile.getFileName());
				StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
				return ret;
			});
			HttpStatus statusCode = HttpStatus.OK;
			respData.setStatus(statusCode);
			respData.setData(file);
			restTemplateUtils.loggerResponseInfo(respData);
			return respData;
		}catch (HttpClientErrorException e) {
			return restTemplateUtils.errorResp2RespData(e);
		}catch (Exception e) {
			throw new TcfHttpClientException(e);
		}
	}
	
	/**
	 * 下载大文件
	 * <p>
	 * 	文件数据存储到硬盘
	 * </p>
	 * @param url 下载地址 
	 * @param authorization 认证信息
	 * @param restFile 请求信息
	 * @param exculdeFieldNames 过滤掉的属性名
	 * @return
	 */
	public RespData<Resource> downloadBigFile(String url, String authorization, DownloadFile restFile,String... exculdeFieldNames) {
		HttpHeaders generaHeader = restTemplateUtils.generaHeader(authorization);
		return download(url, generaHeader, restFile,exculdeFieldNames);
	}
	
	/**
	 * 获取响应头
	 * @param url 请求url
	 * @param method 请求方法
	 * @param paramObj 请求参数 
	 * @return 响应头
	 */
	public RespData<HttpHeaders> responseHeaders(String url, HttpMethod method,Object paramObj) {
		RespData<HttpHeaders> responseHeaders = restTemplateUtils.responseHeaders(url, method, paramObj);
		return responseHeaders;
	}
}
