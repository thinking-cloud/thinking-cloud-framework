package tcf.web.configure.swagger;

/**
 * 覆盖{@link ModelAttributeParameterExpander}
 * 添加功能，在swagger文档中忽略@IgnoreSwaggerParameter 修饰的GET请求参数。
 * 
 * @see ModelAttributeParameterExpander
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */

//public class CustomizeModelAttributeParameterExpander extends ModelAttributeParameterExpander {
//	  private static final Logger LOG = LoggerFactory.getLogger(CustomizeModelAttributeParameterExpander.class);
//
//
//	  @Autowired
//	  public CustomizeModelAttributeParameterExpander( FieldProvider fields,  AccessorsProvider accessors, EnumTypeDeterminer enumTypeDeterminer) {
//		  super(fields,accessors,enumTypeDeterminer);
//	  }
//}
