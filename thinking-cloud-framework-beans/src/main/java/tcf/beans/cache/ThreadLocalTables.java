package tcf.beans.cache;

import java.util.Date;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
/**
 * 所有 threadlocal集合
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Slf4j
public final class ThreadLocalTables {
	// bo base
	public static ThreadLocal<Object> IDS = new ThreadLocal<>();
	public static ThreadLocal<Object> DATA_AUTHOR = new ThreadLocal<>();
	
	// 查询
	public static ThreadLocal<Date> CREATE_TIME_BEFORE = new ThreadLocal<>();
	public static ThreadLocal<Date> CREATE_TIME_AFTER = new ThreadLocal<>();
	public static ThreadLocal<Date> LAST_UPDATE_TIME_BEFORE = new ThreadLocal<>();
	public static ThreadLocal<Date> LAST_UPDATE_TIME_AFTER = new ThreadLocal<>();
	
	// limit
	public static ThreadLocal<Integer> THREADLOCAL_PAGENO =new ThreadLocal<>();
	public static ThreadLocal<Integer> THREADLOCAL_PAGESIZE =new ThreadLocal<>();
	public static ThreadLocal<Long> THREADLOCAL_TOTALRECORD = new ThreadLocal<>();
	
	public static void clear() {
		// bo
		IDS.remove();
		DATA_AUTHOR.remove();
		CREATE_TIME_BEFORE.remove();
		CREATE_TIME_AFTER.remove();
		LAST_UPDATE_TIME_AFTER.remove();
		LAST_UPDATE_TIME_BEFORE.remove();
		
		// limit
		THREADLOCAL_PAGENO.remove();
		THREADLOCAL_PAGESIZE.remove();
		THREADLOCAL_TOTALRECORD.remove();
	}
}