
package tcf.utils.date;

import tcf.utils.exception.DateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * 日期相关工具类
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class DateFormatUtils {

	private final static String DEFALUT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期类型 转为 指定格式的字符串
	 * @param date 日期
	 * @param pattern 目标字符串格式
	 * @return 字符串类型的日期格式
	 */
	public static String  dateFormatString(Date date,String pattern) {
		if(pattern ==null || pattern.trim().equals("")){
			pattern = DEFALUT_PATTERN;
		}
		if(date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 日期类型 转为 默认格式的字符串
	 * @param date 日期
	 * @return 字符串类型的日期格式
	 */
	public static String toString(Date date) {
		System.out.println("flag");
		return dateFormatString(date,DEFALUT_PATTERN);
	}
	
	/**
	 * 日期类型 转为 默认格式的字符串
	 * @param time 时间戳
	 * @return 字符串类型的日期格式
	 */
	public static String toString(long time) {
		return dateFormatString(new Date(time),DEFALUT_PATTERN);
	}
	
	/**
	 * 根据指定的字符串格式 转为 日期对象
	 * @param format 日期格式字符串
	 * @param pattern 格式字符串
	 * @return 日期对象
	 */
	public static Date toDate(String format,String pattern) {
		try {
			if(format == null) return null;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(format);
		}catch (ParseException e) {
			throw new DateFormatException("解析日期出现异常:"+e.getMessage()+" 正确格式:"+pattern);
		}
	}

	/**
	 * 根据指定的字符串格式 转为 日期对象
	 * @param format 日期格式字符串
	 * @return 日期对象
	 */
	public static Date toDate(String format) {
		return toDate(format, DEFALUT_PATTERN);
	}
}
