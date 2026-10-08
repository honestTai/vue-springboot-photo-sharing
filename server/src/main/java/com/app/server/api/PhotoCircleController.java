package com.app.server.api;

import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.entity.Comments;
import com.app.server.entity.PhotoCircle;
import com.app.server.filter.login.LoginFilter;
import com.app.server.service.PhotoCircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论，列表等操作接口
 */
@CrossOrigin
@RestController
public class PhotoCircleController {

    @Autowired
    private PhotoCircleService photoCircleService;

    /**
     * 增加信息
     *
     * @param photoCircle 实体信息
     * @return
     */
    @PostMapping("addPhotoCircle")
    @LoginFilter
    public Result addPhotoCircle(@RequestBody PhotoCircle photoCircle) {
        return photoCircleService.addPhotoCircle(photoCircle);
    }

    /**
     * 列表获取接口
     *
     * @param photoCircle
     * @return
     */
    @PostMapping("photoCircleList")
    public Result photoCircleList(@RequestBody PhotoCircle photoCircle) {
        return photoCircleService.list(photoCircle);
    }

    /**
     * 查看某条评论
     * @param comments
     * @return
     */
    @PostMapping("replyComment")
    public Result replyComment(@RequestBody Comments comments){
        return photoCircleService.replyComment(comments);
    }

    @GetMapping("detailInfo/{id}")
    public Result detailInfo(@PathVariable("id") Integer id){
        return photoCircleService.detailInfo(id);
    }

    /**
     * 评论
     * @param comments
     * @return
     */
    @PostMapping("addComment")
    @LoginFilter
    public Result addComment(@RequestBody Comments comments){
        return photoCircleService.addComment(comments);
    }

    /**
     * 后台接口分页查看
     * 的所有信息
     */
    @PostMapping("pagePhotoCircle")
    @LoginFilter
    public Result pagePhotoCircle(@RequestBody Page page){
        return photoCircleService.pagePhotoCircle(page);
    }

    /**
     * 删除
     * 判断删除还是评论删除
     */
    @PostMapping("deletePhotoCricle")
    public Result deletePhotoCricle(@RequestBody Page page){
        return photoCircleService.deletePhotoCricle(page);
    }
}
