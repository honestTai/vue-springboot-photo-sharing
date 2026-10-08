package com.app.server.api;

import com.app.server.base.Result;
import com.app.server.filter.login.LoginFilter;
import com.app.server.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传接口
 */
@RestController
@CrossOrigin
public class PhotoUpload {

    @Autowired
    private PhotoService photoService;

    /**
     * 通用文件上传接口,开启登录拦截
     *
     * @param uploadFile 上传的文件二进制
     * @return
     * @throws Exception 抛出的异常
     */
    @PostMapping("uploadImg")
    @ResponseBody
    @LoginFilter
    public Result uploadImg(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return photoService.upload(uploadFile);
    }


    /**
     * 通用文件上传接口,注册接口,不开启登录拦截
     *
     * @param uploadFile 上传的文件二进制
     * @return
     * @throws Exception 抛出的异常
     */
    @PostMapping("uploadImgAddUser")
    @ResponseBody
    public Result uploadImgAddUser(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return photoService.upload(uploadFile);
    }

    @Value("${appPhoto.file.path}")
    private String filePath;
}
