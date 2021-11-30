package tcf.web.handler;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import tcf.api.exception.ValidFailedException;
import tcf.api.message.Message;

/**
 * 过滤异常响应，转为标准json
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
@ControllerAdvice
@Slf4j
public class MvcExceptionHandler {
	/**
	 * 异常处理
	 * @return 响应对象
	 */
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Message error(Throwable throwable){
		log.error("thinking-core拦截异常：",throwable);
		return Message.error(throwable);
	}

	/**
	 * 验证失败异常处理
	 * @return 响应对象
	 */
	@ExceptionHandler(ValidFailedException.class)
	@ResponseBody
	public Message failed(ValidFailedException exception){
		log.error("验证失败："+exception.getMessage());
		return Message.failure(exception.getMessage());
	}

	/**
	 * validate 验证失败处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	@ResponseBody
	public Message failed(Exception e){
		StringBuilder errorInfo = new StringBuilder();
		BindingResult bindingResult=null;
		if(e instanceof MethodArgumentNotValidException){
			bindingResult= ((MethodArgumentNotValidException)e).getBindingResult();
		}
		if(e instanceof BindException){
			bindingResult= ((BindException)e).getBindingResult();
		}
		for(int i = 0; i < bindingResult.getFieldErrors().size(); i++){
			if(i > 0){
				errorInfo.append(",");
			}
			FieldError fieldError = bindingResult.getFieldErrors().get(i);
			errorInfo.append(fieldError.getDefaultMessage());
		}
		log.error("验证失败："+errorInfo.toString());
		return Message.failure(errorInfo.toString());
	}

	/**
	 * 解析json异常处理
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public Message failed(HttpMessageNotReadableException exception){
		if(exception.getMessage().indexOf("JSON parse error: Unexpected character")>=0) {
			log.error("thinking-core拦截异常：请求json格式有误");
			return Message.failure("请求json格式有误");
		}else{
			log.error("thinking-core拦截异常：{}",exception);
			return Message.failure(exception.getMessage());
		}
	}


}