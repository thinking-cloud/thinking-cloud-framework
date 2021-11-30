package tcf.httpclient.entity;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

/**
 * 响应信息对象
 *
 * @param <T> 成功响应对象泛型
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@Data
public class RespData<T> {
	//响应状态码
	private HttpStatus status;
	// 成功响应数据
	private T data;
	// 异常响应信息
	private String error;

}
