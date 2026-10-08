package com.app.server.entity;

import com.app.server.base.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 评论实体
 */
@Data
public class Comments extends Page {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 图有圈实体id
     */
    private Integer photoCircleId;

    /**
     * 内容
     */
    private String comment;

    /**
     * 用户id
     */
    private Integer userId;

    private Integer type=2;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private Date dateTime;

    private String userName;

    private String file;

    private Integer total;

    private String userimageurl;

    /**
     * 被回复的id
     */
    private Integer parentId;

    /**
     * 回复的内容
     */
    private String reply;
    
    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private Date replyTime;


}
