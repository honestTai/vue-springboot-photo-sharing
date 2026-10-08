package com.app.server.mapper;

import com.app.server.entity.UserCollection;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏映射SQL接口
 */
@Mapper
public interface UserCollectionMapper {

    /**
     * 新增收藏
     * @param userCollection 收藏实体
     */
    void addCollect(UserCollection userCollection);

    /**
     * 通过图片id删除所有收藏记录
     * @param photoId 图片id
     */
    void deleteByPhotoId(Integer photoId);

    /**
     * 通过图片id与用户id删除该记录
     * @param photoId 图片id
     * @param id 用户id
     */
    void deleteByUserIdAndPhoneId(Integer photoId, Integer id);

    /**
     * 通过图片id查询该图片的收藏总数
     * @param id 图片id
     * @return
     */
    Integer selectCountByPhotoId(Integer id);

    /**
     * 是否重复收藏SQL
     * @param photoId 图片id
     * @param id 用户id
     * @return
     */
    Integer selectCountByPhotoIdAndUserId(Integer photoId, Integer id);
}
