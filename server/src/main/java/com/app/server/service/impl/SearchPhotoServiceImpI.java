package com.app.server.service.impl;

import com.app.server.base.BaseServiceImpl;
import com.app.server.base.Result;
import com.app.server.dto.PostsList;
import com.app.server.entity.PhotoInfo;
import com.app.server.mapper.PhotoInfoMapper;
import com.app.server.service.SearchPhotoService;
import com.app.server.util.FilePhotoUtil;
import com.app.server.util.PhotoAlgorithm;
import com.app.server.vto.LikePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询相同图片接口服务实现类
 */

@Service
public class SearchPhotoServiceImpI extends BaseServiceImpl implements SearchPhotoService {


    @Autowired
    private PhotoInfoMapper photoInfoMapper;

    /**
     * 重载实现
     *
     * @param likePhoto 参数
     * @return
     */
    @Override
    public Result like(LikePhoto likePhoto) {
        //查询出系统中所有的图片
        List<PhotoInfo> photoInfoList = photoInfoMapper.selectAll();
        //构造一个新的List
        List<PostsList> photoInfos = new ArrayList<>();
        //循环所有图片，进行汉明算法调用
        for (PhotoInfo photoInfo : photoInfoList) {
            if (PhotoAlgorithm.hammingDistance(likePhoto.getFingerprint(), photoInfo.getFingerprint()) < 5) {
                photoInfos.add(photoInfoMapper.selectOneById(photoInfo.getId()));
            }
        }
        //搜索完后删除图片，保留磁盘空间
        FilePhotoUtil.deleteFileUrl(likePhoto.getFilePath());
        //装载符合条件的图片,并返回
        return success(photoInfos);
    }
}
