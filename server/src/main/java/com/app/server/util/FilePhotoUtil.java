package com.app.server.util;

import com.app.server.dto.PhotoUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件工具类
 */
public class FilePhotoUtil {


    /**
     * 创建文件夹
     *
     * @param filePath 实体地址
     */
    public static void fileClipCreat(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    /**
     * 删除文件
     *
     * @param fileUrl 文件实体地址
     */
    public static void deleteFileUrl(String fileUrl) {
        //删除文件
        try {
            File file = new File(fileUrl);
            if (file.delete()) {
                System.out.println(file.getName() + " 文件已被删除！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用上传文件工具类
     *
     * @param file      文件
     * @param filePath  实体地址
     * @param staticUrl 虚拟地址
     * @return
     * @throws Exception 错误
     */
    public static PhotoUpload userHeadImageUrl(MultipartFile file, String filePath, String staticUrl) throws Exception {
        String fileName = file.getOriginalFilename();

        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;

        //加个时间戳，尽量避免文件名称重复
        String path = filePath + "/" + fileName;

        //判断文件父目录是否存在
        fileClipCreat(filePath);

        //创建文件路径
        File dest = new File(path);

        try {
            //保存文件
            file.transferTo(dest);
            //静态地址
            String url = staticUrl + fileName;
            //指纹算法code
            String fingerprint = PhotoAlgorithm.produceFingerPrint(path);
            //构造实体返回
            return new PhotoUpload(url, fingerprint, path);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }


}
