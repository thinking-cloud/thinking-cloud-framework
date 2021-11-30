package tcf.api.feign.interceptor;

import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tcf.beans.cache.ThreadLocalTables;

/**
 * 清理ThreadLocal 
 * 
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class ClearThreadLocalTables implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		ThreadLocalTables.clear();
		log.info("feign RequestInterceptor clear threadLocalTables success");
	}
}
