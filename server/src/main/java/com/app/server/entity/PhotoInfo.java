package com.app.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 图片详细信息
 */
@Data
public class PhotoInfo {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 静态地址
     */
    private String photoUrl;

    /**
     * 分类id
     */
    private Integer tagId;

    /**
     * 上传时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss",timezone = "GMT+8")
    private Date dateTime;

    /**
     * 上传用户
     */
    private Integer userId;

    /**
     * 实体路径
     */
    private String filePath;

    /**
     * 图片指纹
     */
    private String fingerprint;

    /**
     * 图片标题
     */
    private String title;

    /**
     * 收藏数系统算
     */
    private Integer totalCollect;

    private Boolean type;

    private String file;

    private Integer photoCircleId;

    private List<String> files;
}
