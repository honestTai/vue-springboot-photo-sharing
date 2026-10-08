package com.app.server.service;

import com.app.server.base.BaseService;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.entity.PhotoInfo;
import com.app.server.vto.PostListVto;

import java.util.List;

/**
 * 最新图片接口
 */
public interface PostsListService  extends BaseService {

    /**
     * 分页查询，时间倒叙
     * @param postListVto
     * @return
     */
    Result POSTS_LIST_PAGE_INFO(PostListVto postListVto);

    Result selectByTapAndLikeTitle(Page page);

    Result deletePhotoByIdList(List<Integer> idList);

    Result updatePhotoInfoById(PhotoInfo photoUploadByTag);
}
