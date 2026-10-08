package com.app.server.mapper;

import com.app.server.base.Page;
import com.app.server.entity.PhotoCircle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 映射SQL接口
 */
@Mapper
public interface PhotoCircleMapper {


    /**
     * 增加接口
     * @param photoCircle
     */
    void addPhotoCircle(PhotoCircle photoCircle);

    /**
     * 列表获取时间倒叙
     * @return
     */
    List<PhotoCircle> selectList(PhotoCircle photoCircle);

    /**
     * 评论数加1
     * @param photoCircleId
     */
    void updateTotal(Integer photoCircleId);

    List<PhotoCircle> selectTreeAndLikeUserNameAndComments(Page page);

    void delete(Integer id);

    void updateTotalMinusOne(Integer photoCircleId);
}
