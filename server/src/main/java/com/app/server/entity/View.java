package com.app.server.entity;

import lombok.Data;

@Data
public class View {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 论坛id
     */
    private Integer commentId;

    public View(Integer commentId) {
        this.commentId = commentId;
    }
}
