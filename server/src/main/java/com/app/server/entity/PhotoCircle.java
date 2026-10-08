package com.app.server.entity;

import com.app.server.base.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 实体信息
 */
@Data
public class PhotoCircle extends Page {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户表主键
     */
    private Integer userId;

    /**
     * 内容
     */
    private String comment;

    /**
     * 发表时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private Date dateTime;

    /**
     * 评论数
     */
    private Integer total;

    /**
     * 附件
     */
    private String file;

    /**
     * 标题
     */
    private String title;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 评论
     */
    private List<Comments> children;

    private Integer type=1;

    /**
     * 点赞数
     */
    private Integer likeTotal;

    /**
     * 评论数据
     */
    private Integer commentCount;

    /**
     * 查看数
     */
    private Integer viewCount;

    /**
     * 用户头像
     */
    private String userimageurl;

    private List<String> files;

    private Integer photoCircleId;

}
