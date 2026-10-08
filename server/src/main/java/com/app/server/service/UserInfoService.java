package com.app.server.service;

import com.app.server.base.BaseService;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.entity.PhotoInfo;
import com.app.server.entity.UserInfo;
import com.app.server.vto.User;

import java.util.List;

/**
 * 用户操作接口
 */
public interface UserInfoService extends BaseService {

    /**
     * 增加用户
     * @param userInfo 用户实体
     * @return
     */
    Result addUser(UserInfo userInfo);

    /**
     * 修改用户
     * @param userInfo
     * @return
     * @throws Exception 抛出错误被拦截
     */
    Result updateUser(UserInfo userInfo) throws Exception;

    /**
     * 我的照片/收藏
     * @param user
     * @return
     */
    Result myPhotoList(User user);

    /**
     * 删除照片
     * @param user
     * @return
     */
    Result del(User user);

    /**
     * 登录
     * @param userInfo
     * @return
     */
    Result login(UserInfo userInfo);

    /**
     * 收藏
     * @param photoId 图片id
     * @return
     */
    Result collect(Integer photoId);

    /**
     * 用户上传图片
     * @param photoInfo
     * @return
     */
    Result upload(PhotoInfo photoInfo) throws Exception;

    Result page(Page page);

    Result delete(List<Integer> idList);

    /**
     * 点赞图片
     * @return
     */
    Result like(PhotoInfo photoInfo);

    /**
     * 统计信息获取
     * @return
     */
    Result staticCount();

    /**
     * 个人中心数据获取
     * @return
     */
    Result mineData(Integer type);

    /**
     * 个人中心数据删除
     * @param id
     * @param type
     * @return
     */
    Result delMineData(Integer id, Integer type);

    Result uploadUser(PhotoInfo photoUploadByTag);
}
