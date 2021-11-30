package tcf.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import tcf.beans.cache.ThreadLocalTables;
import tcf.core.filter.ThinkingCloudBaseFilter;

/**
 * 清理ThreadLocalTable的过滤器
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Configuration
@ServletComponentScan("thinking.cloud")
@WebFilter(urlPatterns = "/*")
@Slf4j
public class Filter200_ClearThreadLocalTablesFilter extends ThinkingCloudBaseFilter {

	@Override
	protected void invork(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain chain) throws IOException, ServletException {
		String url = httpServletRequest.getRequestURL().toString();
		// 获取设置 xrequestid
		String xRequestId = httpServletRequest.getHeader("x-request-id");
		// 执行
		chain.doFilter(httpServletRequest, httpServletResponse);
		// 清理
		ThreadLocalTables.clear();
		log.info("request-url:{}  x-request-id:{} clear thread local", url, xRequestId);
	}

	@Override
	protected String filterName() {
		return "清理ThreadLocal的afilter";
	}

}
