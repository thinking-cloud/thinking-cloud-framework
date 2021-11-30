package tcf.api.feign.interceptor;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import feign.Request;
import feign.Request.Body;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * feign请求时 get json转为get 请求参数
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class FeignGetJson2GetParams implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		
		if (HttpMethod.GET.toString() == template.method()) {
			// 获取请求体中的json
			String jsonStr = null;
			try {
				template.getClass().getMethod("requestBody");
				Body requestBody = template.requestBody();
				if(requestBody!=null && requestBody.asBytes()!=null &&requestBody.asBytes().length>0) {
					jsonStr = template.requestBody().asString();
					template.body(Request.Body.empty());
				}
				return ;
			} catch (NoSuchMethodException e) {
				log.warn("当前feign版本过低,请更换至10.0.4以上版本");
				// 低版本处理
				byte[] bytes = template.body();
				if(bytes!=null) {
					jsonStr = new String(bytes);
					template.body(null,Util.UTF_8);
				}else {
					return ;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}finally{
				System.out.println("111");
			}
		
			// json转url param
			Gson gson = new Gson();
			JsonObject fromJson = gson.fromJson(jsonStr, JsonObject.class);
			for (Entry<String, JsonElement> jsonElement : fromJson.entrySet()) {
				String name = jsonElement.getKey();
				if(jsonElement.getValue() instanceof JsonNull) {
					continue;
				}
				String val = jsonElement.getValue().getAsString();
				if(val.startsWith("[") && val.endsWith("]")) {
					val = val.substring(1);
					val = val.substring(0,val.length()-1);
					template.query(name, val.split(","));
				}else {
					template.query(name, val);
				}	
			}
			
			// 处理请求头
			Map<String, Collection<String>> headerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
			for (Entry<String, Collection<String>> header : template.headers().entrySet()) {
				String headerName = header.getKey();
				Collection<String> headerValues = header.getValue();
				if(headerName.toLowerCase().startsWith("content")) {
					log.info("remove header:"+headerName+" values:"+headerValues);
					continue;
				}
				headerMap.put(headerName, headerValues);
			}
			template.headers(headerMap);
		}
	}
}
