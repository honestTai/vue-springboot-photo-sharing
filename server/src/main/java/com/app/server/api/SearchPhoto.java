package com.app.server.api;

import com.app.server.base.Result;
import com.app.server.service.SearchPhotoService;
import com.app.server.vto.LikePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相似图片查询接口
 */
@RestController
@CrossOrigin
public class SearchPhoto {

    @Autowired
    private SearchPhotoService searchPhotoService;

    /**
     * 以图找图调用的是自己写的图片识别算法
     * 相似图片查询接口，通过指纹hash算法算出
     * @param likePhoto
     * @return
     */
    @PostMapping("like")
    public Result likePhotoList(@RequestBody LikePhoto likePhoto){
        return searchPhotoService.like(likePhoto);
    }
}
