package com.app.server.api;

import com.app.server.base.Result;
import com.app.server.entity.Tag;
import com.app.server.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类接口
 */
@RestController
@CrossOrigin
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 分类接口，默认返回所有分类
     * 登录拦截
     * @return
     */
    @GetMapping("tagList")
    public Result tagList() {
        return tagService.tagList();
    }

    /**
     * 查询该分类下的图片
     * @return
     */
    @PostMapping("photoListByTag")
    public Result PhotoListByTag(@RequestBody Tag tag){
        return tagService.photoList(tag);
    }
}
