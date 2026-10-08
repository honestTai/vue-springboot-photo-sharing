package com.app.server.dto;

import com.app.server.entity.PhotoInfo;
import lombok.Data;

@Data
public class PhotoListDto extends PhotoInfo {

    /**
     * 主键
     */
    private Integer tagId;

    /**
     * 分类名称
     */
    private String tagName;

    private String photo_url;

    private String file_path;
}
