package com.app.server.entity;

import lombok.Data;

/**
 * 点赞表
 */
@Data
public class LikeTable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户表主键
     */
    private Integer userId;

    /**
     * 图片表主键
     */
    private Integer photoId;

    /**
     * 论坛表主键
     */
    private Integer commentId;


    public LikeTable(Integer userId, Integer photoId) {
        this.userId = userId;
        this.photoId = photoId;
    }

    public LikeTable() {
    }
}
