package com.app.server.service.impl;

import com.app.server.base.BaseServiceImpl;
import com.app.server.base.Result;
import com.app.server.entity.Tag;
import com.app.server.mapper.TagMapper;
import com.app.server.service.TagService;
import com.app.server.util.TotalCollect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 分类实现接口
 */
@Service
public class TagServiceImpl extends BaseServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Resource
    private TotalCollect totalCollect;

    /**
     * 分类列表接口实现
     * @return
     */
    @Override
    public Result tagList(){
        return success(tagMapper.selectALL());
    }

    /**
     * 查询该分类下的图片
     * @param tag tag实体类
     * @return
     */
    @Override
    public Result photoList(Tag tag){
        //初始化分页
        PageHelper.startPage(tag.getPageCurrent(),tag.getPageSize());
        return success(new PageInfo<>(totalCollect.TotalCollect(tagMapper.selectPhotoByTagId(tag.getId()))));
    }
}
