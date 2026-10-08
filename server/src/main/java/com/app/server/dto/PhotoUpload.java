package com.app.server.dto;

import lombok.Data;

/**
 * 图片返回参数实体
 */
@Data
public class PhotoUpload {

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

    public PhotoUpload(String url,String fingerprint,String filePath){
        this.url=url;
        this.fingerprint=fingerprint;
        this.filePath=filePath;
    }
}
