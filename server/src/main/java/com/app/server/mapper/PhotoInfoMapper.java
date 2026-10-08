package com.app.server.mapper;

import com.app.server.dto.PhotoListDto;
import com.app.server.dto.PostsList;
import com.app.server.entity.PhotoInfo;
import com.app.server.vto.PostListVto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图片基本信息SQL映射接口
 */
@Mapper
public interface PhotoInfoMapper {
    /**
     * 查询最新照片
     * @return
     */
    List<PostsList> POSTS_LISTS(PostListVto postListVto);

    /**
     * 增加图片
     * @param photoInfo 图片实体
     */
    void add(PhotoInfo photoInfo);

    /**
     * 删除图片
     * @param photoId 图片id
     */
    void deleteById(Integer photoId);

    /**
     * 查询所有图片
     * @return
     */
    List<PhotoInfo> selectAll();


    List<PhotoListDto> selectByTapAndLikeTitle(String title, Integer tagId);

    PostsList selectOneById(Integer x);

    void updatePhotoInfoById(PhotoInfo photoUploadByTag);

    PhotoInfo selectPhotoByPath(String paths);

    void deleteByPhotoPath(String filePath);

    void updatePhotoInfoByFilePath(PhotoInfo photoInfo);

    void updatePhotoInfoByFilePathAndId(PhotoInfo photoUploadByTag);
}
