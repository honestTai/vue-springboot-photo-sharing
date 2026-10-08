package com.app.server.mapper;

import com.app.server.entity.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户基本信息映射SQL接口
 */

@Mapper
public interface UserInfoMapper {

    /**
     * 增加用户
     *
     * @param userInfo
     */
    void addUser(UserInfo userInfo);

    /**
     * 修改个人信息
     *
     * @param userInfo
     */
    void updateUser(UserInfo userInfo);

    /**
     * 查询自己上传的图片
     *
     * @param userId
     * @return
     */
    List<PhotoInfo> selectPhotoList(Integer userId);

    /**
     * 查询自己收藏的图片
     *
     * @param userId 用户id
     * @return
     */
    List<PhotoInfo> selectMyCollectPhotoList(Integer userId);

    /**
     * 登录
     *
     * @param userInfo 用户实体
     * @return
     */
    UserInfo selectUserByNumAndPwd(UserInfo userInfo);

    /**
     * 是否有重复账号
     *
     * @param num 登录账号
     * @return
     */
    Integer selectUserByNum(String num);

    /**
     * 通过用户账号获取用户基本信息
     *
     * @param num 用户账号
     */
    UserInfo selectUserInfoByNum(String num);

    /**
     * 根据用户id 查用户名字
     *
     * @param userId
     * @return
     */
    String selectUserById(Integer userId);

    List<UserInfo> selectUserByLikeUserName(String userName);

    void delete(Integer x);

    @Select("select count(*) from usercollection where user_id = #{userId}")
    Integer totalCollect(Integer userId);

    @Select("select count(*) from liketable where user_id = #{userId}")
    Integer totalLike(Integer userId);

    @Select("select count(*) from comments where userId = #{userId}")
    Integer totalComment(Integer userId);

    @Select("select count(*) from photoinfo where user_id = #{userId}")
    Integer totalPic(Integer userId);

    @Select("select count(*) from photocircle where userId = #{userId}")
    Integer totalPo(Integer userId);

    @Select("select t1.id,\n" +
            "        t2.photo_url AS photoUrl,\n" +
            "        t2.tag_id AS tagId,\n" +
            "        t2.dateTime,\n" +
            "        t2.user_id AS userId,\n" +
            "        t2.file_path AS filePath,\n" +
            "        t2.fingerprint,\n" +
            "        t2.title from usercollection t1 left join photoinfo t2 on t1.photo_id = t2.id where t1.user_id = #{id}")
    List<PhotoInfo> selectUserCollect(Integer id);

    @Select("select t1.id as photoCircleId,t1.file,t1.title,t2.id from liketable t2 join photocircle t1 on t1.id = t2.comment_id where t2.user_id = #{id}")
    List<PhotoInfo> selectUserLikePhoto(Integer id);

    @Select("select t1.id,\n" +
            "        t2.photo_url AS photoUrl,\n" +
            "        t2.tag_id AS tagId,\n" +
            "        t2.dateTime,\n" +
            "        t2.user_id AS userId,\n" +
            "        t2.file_path AS filePath,\n" +
            "        t2.fingerprint,\n" +
            "        t2.title from liketable t1 join photoinfo t2 on t1.photo_id = t2.id where t1.user_id = #{id}")
    List<PhotoInfo> selectUserLike(Integer id);

    @Select("select t1.id,\n" +
            "        t1.photo_url AS photoUrl,\n" +
            "        t1.tag_id AS tagId,\n" +
            "        t1.dateTime,\n" +
            "        t1.user_id AS userId,\n" +
            "        t1.file_path AS filePath,\n" +
            "        t1.fingerprint,\n" +
            "        t1.title from photoinfo t1 where t1.user_id = #{id}")
    List<PhotoInfo> selectUserPhoto(Integer id);

    @Select("select id as photoCircleId ,id,file,title from photocircle where userId = #{id}")
    List<PhotoCircle> selectUserPhotoCire(Integer id);

    @Select("select t1.id as photoCircleId,t1.file,t1.title,t2.id from comments t2 left join photocircle t1 on t1.id = t2.photoCircleId where t2.userId = #{id}")
    List<PhotoCircle> selectUserComment(Integer id);

    @Delete("delete from usercollection where id = #{id} ")
    void delUserCollect(Integer id);

    @Delete("delete from liketable where id = #{id} ")
    void delUserLike(Integer id);

    @Delete("delete from photoinfo where id = #{id} ")
    void delUserPhoto(Integer id);

    @Delete("delete from photocircle where id = #{id} ")
    void delUserPhotoCire(Integer id);

    @Delete("delete from comments where id = #{id} ")
    void delUserComment(Integer id);
}
