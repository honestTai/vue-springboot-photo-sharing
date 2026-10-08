package com.app.server.service;

import com.app.server.base.BaseService;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.entity.Comments;
import com.app.server.entity.PhotoCircle;

/**
 * 接口
 */
public interface PhotoCircleService extends BaseService {

    /**
     * 增加接口
     *
     * @param photoCircle
     * @return
     */
    Result addPhotoCircle(PhotoCircle photoCircle);

    /**
     * 列表获取接口
     * @param photoCircle
     * @return
     */
    Result list(PhotoCircle photoCircle);

    /**
     * 某条评论查看
     * @param photoCircle
     * @return
     */
    Result viewComment(PhotoCircle photoCircle);

    /**
     * 评论接口
     * @param comments
     * @return
     */
    Result addComment(Comments comments);

    Result pagePhotoCircle(Page page);

    Result deletePhotoCricle(Page page);

    /**
     * 详情接口
     * @param id
     * @return
     */
    Result detailInfo(Integer id);

    /**
     * 评论回复
     * @param comments 回复的内容
     * @return
     */
    Result replyComment(Comments comments);
}
