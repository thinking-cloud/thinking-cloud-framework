package tcf.api.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcf.api.constant.MESSAGE;

/**
 * 响应给调用者的消息格式
 * 
 * @param <T> 响应数据泛型
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Message", description = "响应对象")
public class Message<T> {

	@Schema(description = "时间戳")
	private Long timestamp=System.currentTimeMillis();
	@Schema(description = "响应码")
	private String code;
	@Schema(description = "响应信息")
	private String msg;
	@Schema(description = "响应数据")
	private T data;
	
	/**
	 * 构建success的响应结果
	 * @param data 响应数据
	 * @return
	 */
	public static <T> Message success(T data){
		return Message
				.builder()
				.code(MESSAGE.SUCCESS.code())
				.msg(MESSAGE.SUCCESS.name())
				.data(data)
				.build();
	}

	/**
	 * 构建success的响应结果
	 * @return
	 */
	public static Message success(){
		return Message
				.builder()
				.code(MESSAGE.SUCCESS.code())
				.msg("操作成功")
				.build();
	}

	/**
	 * 构建后台程序异常的响应结果
	 * @return
	 */
	public static <T> Message error(){
		return Message
				.builder()
				.code(MESSAGE.ERROR.code())
				.msg("后台出现异常")
				.build();
	}

	/**
	 * 构建后台程序异常的响应结果
	 * @return
	 */
	public static  Message error(Throwable e){
		return Message
				.builder()
				.code(MESSAGE.ERROR.code())
				.msg("后台出现异常")
				.data(e.getMessage())
				.build();
	}

	/**
	 * 构建验证失败的消息
	 * @param message 验证消息
	 * @return
	 */
	public static Message failure(String message){
		return Message
				.builder()
				.code(MESSAGE.FAILURE.code())
				.msg(message)
				.build();
	}

	/**
	 * 构建自定义的message响应消息
	 * @param code 响应码
	 * @param message 响应消息
	 * @param data 响应数据
	 * @param <T> 响应数据类型
	 * @return
	 */
	public static <T> Message message(String code,String message, T data){
		return Message
				.builder()
				.code(code)
				.msg(message)
				.data(data)
				.build();
	}
}
