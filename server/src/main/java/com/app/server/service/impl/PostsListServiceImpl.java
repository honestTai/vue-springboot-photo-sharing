package com.app.server.service.impl;

import com.app.server.base.BaseServiceImpl;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.dto.PhotoListDto;
import com.app.server.entity.PhotoInfo;
import com.app.server.mapper.PhotoInfoMapper;
import com.app.server.mapper.UserCollectionMapper;
import com.app.server.service.PostsListService;
import com.app.server.util.FilePhotoUtil;
import com.app.server.vto.PostListVto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 最新图片接口实现
 */

@Service
public class PostsListServiceImpl extends BaseServiceImpl implements PostsListService {

    @Autowired
    private PhotoInfoMapper photoInfoMapper;

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    /**
     * 重载实现，最新图片接口实现
     */
    @Override
    public Result POSTS_LIST_PAGE_INFO(PostListVto postListVto) {
        //初始化pageHelper插件
        PageHelper.startPage(postListVto.getPageCurrent(), postListVto.getPageSize());
        return success(new PageInfo<>(photoInfoMapper.POSTS_LISTS(postListVto)));
    }

    @Override
    public Result selectByTapAndLikeTitle(Page page) {
        PageHelper.startPage(page.getPageCurrent(), page.getPageSize());
        return success(new PageInfo<PhotoListDto>(photoInfoMapper.selectByTapAndLikeTitle(page.getTitle(), page.getTagId())));
    }

    @Override
    public Result deletePhotoByIdList(List<Integer> idList) {
        idList.forEach(x -> {
            PhotoInfo photoInfo = photoInfoMapper.selectOneById(x);
            //首先删除关于这张图片的收藏数
            userCollectionMapper.deleteByPhotoId(x);
            //在删除实体地址
            FilePhotoUtil.deleteFileUrl(photoInfo.getFilePath());
            //数据库删除
            photoInfoMapper.deleteById(x);
        });
        return success();
    }

    @Override
    public Result updatePhotoInfoById(PhotoInfo photoUploadByTag) {
        PhotoInfo photoInfo = photoInfoMapper.selectPhotoByPath(photoUploadByTag.getFilePath());
        photoUploadByTag.setPhotoUrl(photoInfo.getPhotoUrl());
        photoUploadByTag.setFilePath(photoInfo.getFilePath());
        photoInfoMapper.updatePhotoInfoById(photoUploadByTag);
        photoInfoMapper.deleteByPhotoPath(photoUploadByTag.getFilePath());
        return success();
    }

}
