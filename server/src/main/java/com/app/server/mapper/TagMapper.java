package com.app.server.mapper;

import com.app.server.entity.PhotoInfo;
import com.app.server.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *分类映射SQL接口
 */

@Mapper
public interface TagMapper {

    /**
     * 查询所有分类
     * @return
     */
    List<Tag> selectALL();

    /**
     * 查询分类下的图片
     * @param id
     * @return
     */
    List<PhotoInfo> selectPhotoByTagId(Integer id);
}
