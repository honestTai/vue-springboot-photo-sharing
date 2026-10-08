package com.app.server.service.impl;

import com.app.server.base.BaseServiceImpl;
import com.app.server.base.Result;
import com.app.server.dto.PhotoUpload;
import com.app.server.entity.PhotoInfo;
import com.app.server.mapper.PhotoInfoMapper;
import com.app.server.service.PhotoService;
import com.app.server.util.FilePhotoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 图片实现接口
 */
@Service
public class PhotoServiceImpl extends BaseServiceImpl implements PhotoService {

    @Autowired
    PhotoInfoMapper photoInfoMapper;

    /**
     * 映射地址
     */
    @Value("${appPhoto.file.static.url}")
    private  String staticUrl;

    /**
     * 实体path
     */
    @Value("${appPhoto.file.path}")
    private  String filePath;

    /**
     * 上传图片实现
     */
    @Override
    public Result upload(MultipartFile uploadFile) throws Exception {
        PhotoUpload photoUpload = FilePhotoUtil.userHeadImageUrl(uploadFile,filePath,staticUrl);
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setFilePath(photoUpload.getFilePath());
        photoInfo.setPhotoUrl(photoUpload.getUrl());
        photoInfo.setTagId(-1);
        photoInfo.setDateTime(new Date());
//        photoInfoMapper.add(photoInfo);
        return success(photoUpload);
    }

    /**
     * 通过地址查询图片信息
     *
     * @param paths 文件地址
     * @return
     */
    @Override
    public PhotoInfo selectPhotoByPath(String paths) {
        return photoInfoMapper.selectPhotoByPath(paths);
    }
}
