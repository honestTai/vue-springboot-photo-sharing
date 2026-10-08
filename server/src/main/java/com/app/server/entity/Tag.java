package com.app.server.entity;

import com.app.server.base.Page;
import lombok.Data;

/**
 * 图片分类实体
 */
@Data
public class Tag extends Page {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * icon路由
     */
    private String cuIcon;

    /**
     * 各个分类下的图片数量
     */
    private Integer badge;

    /**
     * 颜色
     */
    private String color;
}
