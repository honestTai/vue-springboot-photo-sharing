package com.app.server.mapper;

import com.app.server.entity.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 评论接口
 */
@Mapper
public interface CommentsMapper {

    /**
     * 评论列表查询接口
     * @param id id
     * @return
     */
    List<Comments> selectList(Integer id);

    /**
     * 增加
     * @param comments
     */
    void add(Comments comments);

    void delete(Integer id);

    Comments selectOneById(Integer id);

    @Update("update comments set reply = #{reply} where id = #{id}")
    void replyComment(Comments comments);
}
