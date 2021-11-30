package tcf.web.configure;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.SerializationFeature;

import tcf.web.converts.HttpMessageNullValueConvert;

/**
 * 配置HttpMessageConvert的配置类
 * 1. 添加空值处理
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Configuration
public class HttpMessageConvertConfiguration implements WebMvcConfigurer {
	
	public HttpMessageConverter<?> NullValConverter(){
		MappingJackson2HttpMessageConverter fastConverter = new MappingJackson2HttpMessageConverter();
		fastConverter.getObjectMapper().getSerializerProvider().setNullValueSerializer(new HttpMessageNullValueConvert());
		fastConverter.getObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		return fastConverter;
	} 

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.clear();
		List<HttpMessageConverter<?>> convertersList = new LinkedList<>();
		for (HttpMessageConverter<?> httpMessageConverter : converters) {
			if(httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
				convertersList.add(NullValConverter());
				continue;
			}
			convertersList.add(httpMessageConverter);
		}
		converters.clear();
		converters.addAll(convertersList);
	}
}
