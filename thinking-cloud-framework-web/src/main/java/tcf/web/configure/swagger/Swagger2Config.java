//package tcf.web.configure.swagger;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.models.auth.In;
//import lombok.AllArgsConstructor;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
//import springfox.documentation.spring.web.plugins.Docket;
//import tcf.core.exception.UnexpectedExpressionValueException;
//import tcf.web.configure.swagger.exception.TCFSwaggerConfigException;
//import tcf.web.constants.WebConstant;
//
///**
// * swagger2配置
// *
// * @author think
// * @Date 2021-11-30
// * @version 1.0.0
// */
////@Configuration
////@EnableSwagger2
//@AllArgsConstructor
//public class Swagger2Config {
//	private final TCFSwaggerProperties swaggerProp;
//	@Bean
//	public Docket createDocket() {
//		ApiSelectorBuilder select = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select();
//		switch (swaggerProp.getScanType()) {
//			case TCFSwaggerProperties.SWAGGER_DOCKET_TYPE_API: 	//设置加了ApiOperation注解的类，才生成接口文档
//				select.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
//				break;
//			case TCFSwaggerProperties.SWAGGER_DOCKET_TYPE_PACKAGE: //设置包下的类，才生成接口文档
//				select.apis(RequestHandlerSelectors.basePackage(swaggerProp.getScanPackages()));
//				break;
//			default:
//				throw new UnexpectedExpressionValueException(swaggerProp.getScanType());
//		}
//		
//    	String headers = swaggerProp.getHeaderNames();
//    	String descriptions = swaggerProp.getHeaderDescriptions();
//		List<Parameter> headerList = new LinkedList<Parameter>();
//		if(headers != null && descriptions != null && !headers.equals("-") &&  !descriptions.equals("-")) {
//	    	// 获取配置值
//	    	String[] headerArrays = headers.split(",");
//	    	String[] descriptionArrays = descriptions.split(",");
//			if(headerArrays.length != descriptionArrays.length) {
//				throw new TCFSwaggerConfigException("swagger.header.names 与 header.descriptions 配置长度不一致 ");
//			}
//			
//			// 添加请求头
//	    	for (int i = 0; i < headerArrays.length; i++) {
//	    		ParameterBuilder parameterBuild = new ParameterBuilder();
//	    		parameterBuild.name(headerArrays[i]);
//	    		parameterBuild.description(descriptionArrays[i]);
//	    		parameterBuild.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//				headerList.add(parameterBuild.build());
//	    	}
//		}
//		return select.paths(PathSelectors.any()).build().globalOperationParameters(headerList);
//	}
//	
//	private ApiInfo apiInfo() {
//		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
//		apiInfoBuilder.title(swaggerProp.getApiTitle()).version(swaggerProp.getApiVersion()).description(swaggerProp.getApiDescription())
//					  .contact(new Contact(swaggerProp.getContactName(), swaggerProp.getContactUrl(), swaggerProp.getContactEmail()));
//		return apiInfoBuilder.build();
//	}
//}
