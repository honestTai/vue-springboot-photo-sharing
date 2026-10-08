package com.app.server.base;

import lombok.Data;

import java.util.List;

/**
 * 分页参数
 */
@Data
public class Page {

    /**
     * 页数
     */
    private Integer pageCurrent;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * userName
     */
    private String userName;

    private List<Integer> idList;

    private String title;

    private Integer tagId;

    private String comment;

    private Integer type;

    private Integer id;
}
