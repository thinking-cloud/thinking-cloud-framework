package tcf.web.configure.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * thinking cloud framework 的swagger配置
 *
 * @author think
 * @Date 2021年12月14日
 * @version 1.0.0
 */
@Component
@ConfigurationProperties("tcf.swagger")
@Data
public class TCFSwaggerProperties {
	/** swagger docket 的类型 ： 通过注解生成 sawagger文档 */
	public static final String SWAGGER_DOCKET_TYPE_API = "api";
	/** swagger docket 的类型 ： 通过包名生成 sawagger文档 */
	public static final String SWAGGER_DOCKET_TYPE_PACKAGE = "package";
	
	// 是否开启swagger
	private boolean enable;
	// swagger扫描接口的类型：api、package
	private String scanType ;
	// 当类型为package时，扫描的包名
	private String scanPackages;
	
	// 接口文档的标题
	private String apiTitle;
	// 接口文档的版本
	private String apiVersion;
	// 接口文档的描述
	private String apiDescription;
	
	// 接口文档联系人
	private String contactName;
	// 联系人相关的描述url
	private String contactUrl;
	// 接口文档联系人的email
	private String contactEmail;
	
	// 两者要一一对应 
	// 请求头名  逗号分隔
	private String[] headerNames ; 
	//请求头解释  逗号分隔
	private String[] headerDescriptions;
}
