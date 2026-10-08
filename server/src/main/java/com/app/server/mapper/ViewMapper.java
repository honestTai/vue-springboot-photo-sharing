package com.app.server.mapper;

import com.app.server.entity.View;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 浏览次数接口
 */
@Mapper
public interface ViewMapper {

    /**
     * 增加数据
     * @param view 浏览记录
     */
    @Insert("insert into view (comment_id) values(#{commentId})")
    void addView(View view);
}
