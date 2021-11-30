package tcf.httpclient.entity;

import java.util.UUID;

import org.springframework.core.io.Resource;

import lombok.Data;

/**
 * 下载文件对象
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public interface DownloadFile  {
	
	/**
	 * 下载文件，临时文件所在目录
	 * @return
	 */
	default String getPath() {
		return "/tmp/";
	}
	
	/**
	 * 下载文件，临时文件名
	 * @return
	 */
	default String getFileName() {
		return UUID.randomUUID()+".tmp";
	}
}
