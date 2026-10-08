package com.app.server.mapper;

import com.app.server.entity.LikeTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikeMapper {


    /**
     * 插入数据
     * @param likeTable 点赞数据
     */
    @Insert("insert into liketable (photo_id, user_id) values (#{photoId}, #{userId})")
    void addLike(LikeTable likeTable);

    @Insert("insert into liketable (comment_id, user_id) values (#{commentId}, #{userId})")
    void addLikeT(LikeTable likeTable);


    /**
     * 查询是否重复喜欢
     * @param id 图片id
     * @param id1 用户id
     * @return
     */
    @Select("select count(*) from liketable where photo_id = #{id} and user_id =#{id1}")
    int selectCountByPhotoIdAndUserId(Integer id, Integer id1);
    @Select("select count(*) from liketable where comment_id = #{id} and user_id =#{id1}")
    int selectCountByCommentIdAndUserId(Integer id, Integer id1);
}
