package tcf.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * 文件相关的工具类
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class FileUtils {

	/**
	 * 创建目录
	 * 
	 * @param file
	 */
	public static void mkdirs(File file) {
		if (!file.exists()) {
			boolean status = file.mkdirs();
			if (!status) {
				throw new RuntimeException("Error creating file the " + file.getAbsolutePath());
			}
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 */

	public static void createFile(File file) {
		try {
			File parentFile = file.getParentFile();
			mkdirs(parentFile);
			boolean createNewFile = file.createNewFile();
			if (!createNewFile) {
				throw new RuntimeException("Error creating file the " + file.getAbsolutePath());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path文件路径
	 * @return *
	 * @throws Exception
	 */
	public static String encodeBase64File(File file) {
		try (FileInputStream in = new FileInputStream(file)) {
			return encodeBase64File(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将IO流中的数据转为base64 字符串
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64File(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int len = -1;
		while ((len = in.read(bytes)) != -1) {
			baos.write(bytes, 0, len);
		}
		byte[] byteArray = baos.toByteArray();
		return Base64.getEncoder().encodeToString(byteArray);
	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void decoderBase64File(String base64Str, File file) {
		try (FileOutputStream out = new FileOutputStream(file);) {
			byte[] buffer = Base64.getDecoder().decode(base64Str);
			out.write(buffer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
