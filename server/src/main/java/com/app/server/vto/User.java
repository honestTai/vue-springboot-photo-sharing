package com.app.server.vto;

import com.app.server.base.Page;
import lombok.Data;

/**
 * 用户相关的参数,集成分页实体
 */
@Data
public class User extends Page {

    /**
     * 查询type(0上传的图片1收藏的图片)
     */
    private Integer viewType;

    /**
     * 图片id
     */
    private Integer photoId;

    /**
     * 图片path
     */
    private String filePath;
}
