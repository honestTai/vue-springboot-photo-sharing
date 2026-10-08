package com.app.server.entity;

import com.app.server.EncryptColumn;
import lombok.Data;

/**
 * 用户信息实体
 */
@Data
public class UserInfo {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户登录账号
     */
    @EncryptColumn
    private String num;

    /**
     * 用户登录密码
     */
    @EncryptColumn
    private String pwd;

    /**
     * 用户头像
     */
    private String userImageUrl;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 收藏总数
     */
    private Integer collectTotal;

    /**
     * 点赞总数
     */
    private Integer likeTotal;

    /**
     * 评论
     */
    private Integer commentTotal;

    /**
     * 发帖
     */
    private Integer poTotal;


}
