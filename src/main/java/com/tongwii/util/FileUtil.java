package com.tongwii.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;


/**
 * 文件处理工具类
 */
public class FileUtil {

    private static final String UPLOAD_FOLDER_URL = "/uploadAvatar/";
    /**
     * 得到要存储的目标文件
     *
     * @param fileInfoName 文件名称
     * @return File 要存储的目标文件
     */
    private static File getDestFile(String fileInfoName) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();

        String dest = request.getServletContext().getRealPath("/") + UPLOAD_FOLDER_URL + getRelativeFilePath(fileInfoName);

        return new File(dest);
    }

    /**
     * 得到按日期划分文件夹的相对地址
     *
     * @param fileInfoName 文件名称
     * @return String 相对地址
     */
    private static String getRelativeFilePath(String fileInfoName) {
        Calendar calendar = Calendar.getInstance();
        String path = calendar.get(1) + "/" + (calendar.get(2) + 1) + "/" + fileInfoName;

        return path;
    }

    /**
     * 上传文件，获得文件的相对地址
     * @param source 要上传的文件
     * @param fileInfoName 文件名称
     * @return String 文件上传的相对地址
     * @throws IOException
     */
    public static String uploadFile(MultipartFile source, String fileInfoName) throws IOException {
        File destFile = FileUtil.getDestFile(fileInfoName);
        FileUtils.copyInputStreamToFile(source.getInputStream(), destFile);
        return UPLOAD_FOLDER_URL+getRelativeFilePath(fileInfoName);
    }

    /**
     * 返回文件类型
     *
     * @param fileName 文件名称
     * @return String 文件类型
     */
    public static String rtnFileType(String fileName) {
        if ((fileName == null) || (fileName.length() == 0)) {
            return null;
        }
        fileName = fileName.toLowerCase();
        if ((fileName.endsWith(".jpg")) || (fileName.endsWith(".gif")) || (fileName.endsWith(".jpeg")) || (fileName.endsWith(".bmp")) || (fileName.endsWith(".tga")) || (fileName.endsWith(".png"))) {
            return "image";
        }
        if ((fileName.endsWith(".mp3")) || (fileName.endsWith(".midi")) || (fileName.endsWith(".wav")) || (fileName.endsWith(".wma"))) {
            return "audio";
        }
        return "application";
    }

    /**
     * 得到文件后缀
     *
     * @param fileName 文件名称
     * @return String 文件后缀
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
    }

    /**
     * 判断文件是否为图片
     *
     * @param fileName 文件名称
     * @return Boolean
     */
    public static boolean isImage(String fileName) {
        fileName = fileName.toLowerCase();
        return (fileName.endsWith(".png")) || (fileName.endsWith(".jpg")) || (fileName.endsWith(".gif")) || (fileName.endsWith(".jpeg")) || (fileName.endsWith(".bmp"));
    }
}

