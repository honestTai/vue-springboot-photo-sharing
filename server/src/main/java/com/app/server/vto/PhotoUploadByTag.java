package com.app.server.vto;

import lombok.Data;

/**
 * 上传文件参数接受类
 */
@Data
public class PhotoUploadByTag {
    /**
     * 静态地址
     */
    private String url;

    /**
     * 指纹
     */
    private String fingerprint;

    /**
     * 实体地址
     */
    private String filePath;

    /**
     * 分类id
     */
    private Integer tagId;

    /**
     * 用户id
     */
    private Integer userId;
}
