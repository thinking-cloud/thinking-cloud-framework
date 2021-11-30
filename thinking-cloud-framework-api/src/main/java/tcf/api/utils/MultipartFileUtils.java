package tcf.api.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * MultipartFile的工具类
 * 
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public class MultipartFileUtils {


    /**
     *  file对象转 MultipartFile对象
     * @param file file对象
     * @param sizeThreshold 缓存大小, 如果超过这个大下则需要系列化到文件
     * @param multipartFilePath 序列化文件的目录
     * @return 转换后的MultipartFile对象
     */
    public static MultipartFile fileToMultipartFile(File file, int sizeThreshold, String multipartFilePath) {
        FileItemFactory factory = new DiskFileItemFactory(sizeThreshold, new File(multipartFilePath));
        FileItem item = factory.createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE,false, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try (
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
        ) {
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
           throw new RuntimeException(e);
        }

        MultipartFile multipartFile = new CommonsMultipartFile(item);
        return multipartFile;
    }

    /**
     * 字节数组转MultipartFile对象
     * @param content 字节数组
     * @param fileName 文件名
     * @return  ultipartFile对象
     */
    public static MultipartFile byteToMultipartFile(byte[] content, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(content.length,null);
        FileItem item = factory.createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE, false, fileName);
        try ( OutputStream os = item.getOutputStream(); ) {
            os.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MultipartFile multipartFile = new CommonsMultipartFile(item);
        return multipartFile;
    }
}
