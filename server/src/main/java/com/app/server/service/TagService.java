package com.app.server.service;

import com.app.server.base.BaseService;
import com.app.server.base.Result;
import com.app.server.entity.Tag;

/**
 * 分类接口
 */
public interface TagService extends BaseService {

    /**
     * 分类列表接口
     * @return
     */
    Result tagList();

    /**
     * 查询该分类下的图片
     * @param tag tag实体类
     * @return
     */
    Result photoList(Tag tag);
}
