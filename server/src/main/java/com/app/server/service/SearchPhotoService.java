package com.app.server.service;

import com.app.server.base.BaseService;
import com.app.server.base.Result;
import com.app.server.vto.LikePhoto;

/**
 * 查询相同图片接口服务类
 */
public interface SearchPhotoService extends BaseService {

    /**
     * 查询相同的图片
     * @param likePhoto 参数
     * @return
     */
    Result like(LikePhoto likePhoto);
}
