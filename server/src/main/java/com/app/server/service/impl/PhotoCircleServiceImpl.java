package com.app.server.service.impl;

import com.app.server.base.BaseServiceImpl;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.entity.Comments;
import com.app.server.entity.PhotoCircle;
import com.app.server.entity.View;
import com.app.server.mapper.CommentsMapper;
import com.app.server.mapper.PhotoCircleMapper;
import com.app.server.mapper.UserInfoMapper;
import com.app.server.mapper.ViewMapper;
import com.app.server.service.PhotoCircleService;
import com.app.server.util.FilePhotoUtil;
import com.app.server.util.UserThreadLocal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 接口实现
 */
@Service
public class PhotoCircleServiceImpl extends BaseServiceImpl implements PhotoCircleService {

    @Autowired
    private PhotoCircleMapper photoCircleMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    ViewMapper viewMapper;

    /**
     * 增加接口实现
     *
     * @param photoCircle 参数
     * @return
     */
    @Override
    public Result addPhotoCircle(PhotoCircle photoCircle) {
        photoCircle.setUserId(UserThreadLocal.getUser().getId());
        photoCircle.setDateTime(new Date());
        photoCircleMapper.addPhotoCircle(photoCircle);
        return success();
    }

    /**
     * 列表获取接口实现
     *
     * @param photoCircle
     * @return
     */
    @Override
    public Result list(PhotoCircle photoCircle) {
        PageHelper.startPage(photoCircle.getPageCurrent(), photoCircle.getPageSize());
        List<PhotoCircle> photoCircleList = photoCircleMapper.selectList(photoCircle);
        photoCircleList.forEach(x -> {
            x.setFiles(Arrays.asList(x.getFile().split(",")));
        });
        return success(new PageInfo<>(photoCircleList));
    }

    /**
     * 接口实现
     *
     * @param photoCircle
     * @return
     */
    @Override
    public Result viewComment(PhotoCircle photoCircle) {
//        PageHelper.startPage(photoCircle.getPageCurrent(), photoCircle.getPageSize());
        List<Comments> commentsList = commentsMapper.selectList(photoCircle.getId());
        commentsList.forEach(x -> {
            x.setUserName(userInfoMapper.selectUserById(x.getUserId()));
        });
        return success(commentsList);
    }

    /**
     * 评论接口
     *
     * @param comments 评论实体内容
     * @return
     */
    @Override
    public Result addComment(Comments comments) {
        comments.setUserId(UserThreadLocal.getUser().getId());
        comments.setDateTime(new Date());
        commentsMapper.add(comments);
        //修改表
        photoCircleMapper.updateTotal(comments.getPhotoCircleId());
        return success();
    }

    @Override
    public Result pagePhotoCircle(Page page) {

        //这里使用MyBatis的树分页查询，查询前定义好分页
        PageHelper.startPage(page.getPageCurrent(), page.getPageSize());

        List<PhotoCircle> photoCircleList = photoCircleMapper.selectTreeAndLikeUserNameAndComments(page);
        photoCircleList.forEach(x->{
            x.setFiles(Arrays.asList(x.getFile().split(",")));
        });

        return success(new PageInfo<PhotoCircle>(photoCircleList));

    }

    @Override
    public Result deletePhotoCricle(Page page) {
        /**
         * 前端判断是什么删除如果是删除，删除所有信息
         * 如果是评论删除只删除评论信息
         */
        if (page.getType() == 1) {
            List<Comments> commentsList = commentsMapper.selectList(page.getId());
            commentsList.forEach(x -> {
                commentsMapper.delete(x.getId());
            });
            //获取图片信息，并删除图片信息
            PhotoCircle photoCircle = photoCircleMapper.selectList(new PhotoCircle()).stream().filter(x -> x.getId() == page.getId()).collect(Collectors.toList()).get(0);
            //在删除实体地址
            FilePhotoUtil.deleteFileUrl("D:/serverFile/server/uploadPath/" + photoCircle.getFile().replace("http://127.0.0.1:7777/api/photo/app/image/", ""));
            photoCircleMapper.delete(page.getId());
        } else {
            photoCircleMapper.updateTotalMinusOne(commentsMapper.selectOneById(page.getId()).getPhotoCircleId());
            commentsMapper.delete(page.getId());
        }
        return success();
    }

    /**
     * 详情接口
     *
     * @param id
     * @return
     */
    @Override
    public Result detailInfo(Integer id) {
        viewMapper.addView(new View(id));
        PhotoCircle photoCircle = photoCircleMapper.selectList(new PhotoCircle()).stream().filter(x -> Objects.equals(x.getId(), id)).collect(Collectors.toList()).get(0);
        List<Comments> comments = commentsMapper.selectList(id);

        photoCircle.setChildren(comments);
        photoCircle.setFiles(Arrays.asList(photoCircle.getFile().split(",")));
        return success(photoCircle);
    }

    /**
     * 评论回复
     *
     * @param comments 回复的内容
     * @return
     */
    @Override
    public Result replyComment(Comments comments) {
        comments.setReplyTime(new Date());
        commentsMapper.replyComment(comments);
        return success();
    }

}
