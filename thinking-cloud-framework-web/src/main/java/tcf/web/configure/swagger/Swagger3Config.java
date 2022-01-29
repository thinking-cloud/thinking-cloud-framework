package tcf.web.configure.swagger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import lombok.AllArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import tcf.web.configure.swagger.exception.TCFSwaggerConfigException;

@EnableOpenApi
@Configuration
@AllArgsConstructor
public class Swagger3Config {
	
	private final TCFSwaggerProperties swaggerProp;

	
	@Bean
	public Docket createDocket() {
		ApiSelectorBuilder select = new Docket(DocumentationType.OAS_30).pathMapping("/").enable(swaggerProp.isEnable()).apiInfo(apiInfo()).select();
		switch (swaggerProp.getScanType()) {
			case TCFSwaggerProperties.SWAGGER_DOCKET_TYPE_API: 	//设置加了ApiOperation注解的类，才生成接口文档
				//select.apis(RequestHandlerSelectors.withMethodAnnotation(Api.class));
				break;
			case TCFSwaggerProperties.SWAGGER_DOCKET_TYPE_PACKAGE: //设置包下的类，才生成接口文档
				select.apis(RequestHandlerSelectors.basePackage(swaggerProp.getScanPackages()));
				break;
			default:
				throw new TCFSwaggerConfigException("swagger.scan.type 异常值："+swaggerProp.getScanType());
		}
		
		Docket docket = select.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
		docket.protocols(new LinkedHashSet<>(Arrays.asList(new String[]{"https", "http"})));
		docket.globalRequestParameters(headers());
		return docket;
	}

    /**
     * 设置请求头
     * @param securitySchemes 其他scheme
     * @return 已添加的scheme
     */
    private List<RequestParameter> headers() {
    	List<RequestParameter> headerList = new LinkedList<>();
    	String[] headers = swaggerProp.getHeaderNames();
    	String[] descriptions = swaggerProp.getHeaderDescriptions();
    	if(headers != null && descriptions != null && headers.length > 0 && descriptions.length > 0) {	
	    	// 获取配置值
			if(headers.length != descriptions.length) {
				throw new TCFSwaggerConfigException("swagger.header.names 与 header.descriptions 配置长度不一致 ");
			}
			// 添加请求头
			RequestParameterBuilder rpb = new RequestParameterBuilder();
			for (int i = 0; i < headers.length; i++) {
				RequestParameter header = rpb.name(headers[i]).description(descriptions[i]).in(ParameterType.HEADER).build();
	    		headerList.add(header);
			}
			
    	}
    	return headerList;
    	
    }
    
	
	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title(swaggerProp.getApiTitle()).version(swaggerProp.getApiVersion()).description(swaggerProp.getApiDescription())
					  .contact(new Contact(swaggerProp.getContactName(), swaggerProp.getContactUrl(), swaggerProp.getContactEmail()));
		
		return apiInfoBuilder.build();
	}
	
	
	/**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration
                            .excludePathPatterns("/swagger**/**")
                            .excludePathPatterns("/webjars/**")
                            .excludePathPatterns("/v3/**")
                            .excludePathPatterns("/doc.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
