package tcf.web.configure.swagger;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tcf.core.exception.UnexpectedExpressionValueException;
import tcf.web.constants.WebConstant;

/**
 * 默认的swagger配置
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Configuration
@EnableSwagger2
public class DefaultSwaggerConfig {
	@Value("${swagger.docket.type:api}")
	private String swaggerDocketType ;
	@Value("${swagger.base.package:thinking.cloud}")
	private String swaggerBasePackage;
	
	@Value("${swagger.api.title:thinking cloud}")
	private String swaggerApiTitle;
	@Value("${swagger.api.version:v1.0.0}")
	private String swaggerApiVersion;
	@Value("${swagger.api.description:default}")
	private String swaggerApiDescription;
	
	@Value("${swagger.contact.name:thinking-cloud}")
	private String swaggerContactName;
	@Value("${swagger.contact.url:''}")
	private String swaggerContactUrl;
	@Value("${swagger.contact.email:''}")
	private String swaggerContactEmail;
	
	//两者要一一对应 
	@Value("${swagger.headers.name:''}") //请求头名  逗号分隔
	private String headers ; 
	@Value("${swagger.headers.description:''}")	//请求头解释  逗号分隔
	private String descriptions;
	
	@Bean
	public Docket createDocket() {
		ApiSelectorBuilder select = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select();
		switch (swaggerDocketType) {
			case WebConstant.SWAGGER_DOCKET_TYPE_API: 	//设置加了ApiOperation注解的类，才生成接口文档
				select.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
				break;
			case WebConstant.SWAGGER_DOCKET_TYPE_PACKAGE: //设置包下的类，才生成接口文档
				select.apis(RequestHandlerSelectors.basePackage(swaggerBasePackage));
				break;
			default:
				throw new UnexpectedExpressionValueException(swaggerDocketType);
		}
		
		List<Parameter> headerList = new LinkedList<Parameter>();
		if(headers != null) {
			String[] descriptions = new String[0];
			
			if(this.descriptions != null) {
				descriptions = this.descriptions.split(",");
			}
			
			int index = 0;
			ParameterBuilder parameterBuild = null;
			for (String headerName : headers.split(",")) {
				parameterBuild = new ParameterBuilder();
				parameterBuild.name(headerName);
				if(index < descriptions.length) {
					parameterBuild.description(descriptions[index]);
				}else {
					parameterBuild.description(headerName);
				}
				parameterBuild.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
				headerList.add(parameterBuild.build());
				index++;
			}
		}
		
		return select.paths(PathSelectors.any()).build().globalOperationParameters(headerList);
	}
	
	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title(swaggerApiTitle).version(swaggerApiVersion).description(swaggerApiDescription)
					  .contact(new Contact(swaggerContactName, swaggerContactUrl, swaggerContactEmail));
		
		return apiInfoBuilder.build();
	}
	

}
