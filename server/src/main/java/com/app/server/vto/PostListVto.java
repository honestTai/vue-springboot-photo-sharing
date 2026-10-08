package com.app.server.vto;

import com.app.server.base.Page;
import lombok.Data;

/**
 * 最新参数查询
 */
@Data
public class PostListVto extends Page {

    /**
     * 分类id
     */
    private Integer tagId;

    /**
     * 标题
     */
    private String title;

    /**
     * 排序{name: '时间倒叙', value: 0}, {name: '时间正序', value: 1}, {
     *         name: '收藏倒叙',
     *         value: 2
     *       }, {name: '收藏正叙', value: 3}, {name: '点赞倒叙', value: 4}, {name: '点赞倒叙', value: 5}
     */
    private Integer sort;
}
