package tcf.web.configure.swagger;



import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.Data;
import tcf.web.configure.swagger.exception.TCFSwaggerConfigException;

//@OpenAPIDefinition(
//		info = @Info(
//				title = "thinking-cloud-docs",
//                version = "1.0",
//                description = "thinking-cloud文档",
//                contact = @Contact(
//                		name = "think",
//                		email = "thinking-cloud-2020@outlook.com"
//                )
//        ),
//        security = @SecurityRequirement(name = "JWT"),
//        externalDocs = @ExternalDocumentation(description = "参考文档",
//                url = "https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations"
//        )
//)
//@SecurityScheme(type = SecuritySchemeType.HTTP, name = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
@Data
@Configuration
public class Swagger3Config {
	
	private final TCFSwaggerProperties swaggerProp;

	@Bean
	public OpenAPI openAPI() {
		// 接口文档联络人
		Contact contact = new Contact();
		contact.name(swaggerProp.getContactName());
		contact.email(swaggerProp.getContactEmail());
		
		// 接口文档标题
		Info info = new Info();
		info.title(swaggerProp.getApiTitle());
		info.description(swaggerProp.getApiDescription());
		info.version(swaggerProp.getApiVersion());
		info.contact(contact);
		
		OpenAPI apiDoc = new OpenAPI();
		apiDoc.info(info);
		
		return apiDoc;
	}
	
	@Bean
	public OperationCustomizer operationCustomizer () {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			return headers(operation);
		};
	}
	
	/**
	 * 请求头处理
	 */
	private Operation headers(Operation operation) {
    	String[] headers = swaggerProp.getHeaderNames();
    	String[] descriptions = swaggerProp.getHeaderDescriptions();
    	if(headers != null && descriptions != null && headers.length > 0 && descriptions.length > 0) {	
	    	// 获取配置值
			if(headers.length != descriptions.length) {
				throw new TCFSwaggerConfigException("swagger.header.names 与 header.descriptions 配置长度不一致 ");
			}
			// 添加请求头
			for (int i = 0; i < headers.length; i++) {
				Parameter paramItem = new Parameter()
				.in(ParameterIn.HEADER.toString())
				.name(headers[i])
				.description(descriptions[i])
				.schema(new StringSchema())
				.required(true);
				operation.addParametersItem(paramItem);
			}
    	}
    	return operation;
	}
//	
//	@Bean
//	public Docket createDocket() {
//		ApiSelectorBuilder select = new Docket(DocumentationType.OAS_30).pathMapping("/").enable(swaggerProp.isEnable()).apiInfo(apiInfo()).select();
//		switch (swaggerProp.getScanType()) {
//			case TCFSwaggerProperties.SWAGGER_DOCKET_TYPE_API: 	//设置加了ApiOperation注解的类，才生成接口文档
//				//select.apis(RequestHandlerSelectors.withMethodAnnotation(Api.class));
//				break;
//			case TCFSwaggerProperties.SWAGGER_DOCKET_TYPE_PACKAGE: //设置包下的类，才生成接口文档
//				select.apis(RequestHandlerSelectors.basePackage(swaggerProp.getScanPackages()));
//				break;
//			default:
//				throw new TCFSwaggerConfigException("swagger.scan.type 异常值："+swaggerProp.getScanType());
//		}
//		
//		Docket docket = select.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
//		docket.protocols(new LinkedHashSet<>(Arrays.asList(new String[]{"https", "http"})));
//		docket.globalRequestParameters(headers());
//		return docket;
//	}
//
//    /**
//     * 设置请求头
//     * @param securitySchemes 其他scheme
//     * @return 已添加的scheme
//     */
//    private List<RequestParameter> headers() {
//    	List<RequestParameter> headerList = new LinkedList<>();
//    	String[] headers = swaggerProp.getHeaderNames();
//    	String[] descriptions = swaggerProp.getHeaderDescriptions();
//    	if(headers != null && descriptions != null && headers.length > 0 && descriptions.length > 0) {	
//	    	// 获取配置值
//			if(headers.length != descriptions.length) {
//				throw new TCFSwaggerConfigException("swagger.header.names 与 header.descriptions 配置长度不一致 ");
//			}
//			// 添加请求头
//			RequestParameterBuilder rpb = new RequestParameterBuilder();
//			for (int i = 0; i < headers.length; i++) {
//				RequestParameter header = rpb.name(headers[i]).description(descriptions[i]).in(ParameterType.HEADER).build();
//	    		headerList.add(header);
//			}
//			
//    	}
//    	return headerList;
//    	
//    }
//    
//	
//	private ApiInfo apiInfo() {
//		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
//		apiInfoBuilder.title(swaggerProp.getApiTitle()).version(swaggerProp.getApiVersion()).description(swaggerProp.getApiDescription())
//					  .contact(new Contact(swaggerProp.getContactName(), swaggerProp.getContactUrl(), swaggerProp.getContactEmail()));
//		
//		return apiInfoBuilder.build();
//	}
	
	
	/**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    public void addInterceptors(InterceptorRegistry registry) {
//        try {
//            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
//            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
//            if (registrations != null) {
//                for (InterceptorRegistration interceptorRegistration : registrations) {
//                    interceptorRegistration
//                            .excludePathPatterns("/swagger**/**")
//                            .excludePathPatterns("/webjars/**")
//                            .excludePathPatterns("/v3/**")
//                            .excludePathPatterns("/doc.html");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
