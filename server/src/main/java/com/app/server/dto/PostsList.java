package com.app.server.dto;

import com.app.server.entity.PhotoInfo;
import lombok.Data;

/**
 * 最新图片返回,集成图片基本信息类
 */

@Data
public class PostsList extends PhotoInfo {

    /**
     * 分类名称
     */
    private String tagTitle;

    /**
     * 抬头照片
     */
    private String firstImageUrl;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 收藏总数
     */
    private Integer collectTotal;

    /**
     * 点赞总数
     */
    private Integer likeTotal;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userimageurl;

}
