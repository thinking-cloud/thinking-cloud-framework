package tcf.httpclient.interceptors;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>
 * 打印RestTempate的请求响应拦截器
 * </P>
 * 
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Slf4j
public class RestTempateLoggingInterceptor implements ClientHttpRequestInterceptor {

	/**
	 * 打印请求信息
	 * 
	 * @param request 请求对象
	 * @param body    请求体
	 * @throws IOException
	 */
	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		
		log.debug("===========================request begin================================================");
		log.debug("{} {}", request.getMethod(), request.getURI());
		for (Entry<String, List<String>> header : request.getHeaders().entrySet()) {
			log.debug("{}:{}", header.getKey(), header.getValue());
		}
		log.debug("\n");
		log.debug("{}", new String(body, "UTF-8"));
		log.debug("==========================request end================================================");
	}

	/**
	 * 打印响应信息
	 * 
	 * @param resp 响应对象
	 * @return 处理后的响应对象
	 * @throws IOException
	 */
	private ClientHttpResponse traceResponse(ClientHttpResponse resp) throws IOException {
		ClientHttpResponse response = new BufferingClientHttpResponseWrapper(resp);
		log.debug("============================response begin==========================================");
		log.debug("{}", response.getStatusCode());
		for (Entry<String, List<String>> header : response.getHeaders().entrySet()) {
			log.debug("{}:{}", header.getKey(), header.getValue());
		}

		StringBuilder body = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				body.append(line);
				// inputStringBuilder.append('\n');
				line = bufferedReader.readLine();
			}
		}
		log.debug("{}", body.toString());
		log.debug("\n");
		log.debug("=======================response end=================================================");
		return response;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		ClientHttpResponse traceResponse = traceResponse(response);
		return traceResponse;
	}

	/**
	 * ClientHttpResponse的包装类
	 * <p>
	 * 日志打印完毕后,再将响应信息放回, 防止业务中无法获取响应体信息
	 * </p>
	 * 
	 * @author think
	 */
	private class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

		private final ClientHttpResponse response;

		private byte[] body;

		BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
			this.response = response;
		}

		@Override
		public HttpStatus getStatusCode() throws IOException {
			return this.response.getStatusCode();
		}

		@Override
		public int getRawStatusCode() throws IOException {
			return this.response.getRawStatusCode();
		}

		@Override
		public String getStatusText() throws IOException {
			return this.response.getStatusText();
		}

		@Override
		public HttpHeaders getHeaders() {
			return this.response.getHeaders();
		}

		@Override
		public InputStream getBody() throws IOException {
			if (this.body == null) {
				this.body = StreamUtils.copyToByteArray(this.response.getBody());
			}
			return new ByteArrayInputStream(this.body);
		}

		@Override
		public void close() {
			this.response.close();
		}

	}
}
