package com.app.server.util;

import com.app.server.entity.PhotoInfo;
import com.app.server.mapper.UserCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TotalCollect {

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    /**
     * 收藏数装入
     *
     * @param selectPhotoList 图片实体集合
     * @return
     */
    public List<PhotoInfo> TotalCollect(List<PhotoInfo> selectPhotoList) {
        //循环集合
        for (PhotoInfo photoInfo : selectPhotoList) {
            //执行查询SQL并装入集合
            photoInfo.setTotalCollect(userCollectionMapper.selectCountByPhotoId(photoInfo.getId()));
        }
        //返回原集合
        return selectPhotoList;
    }
}
