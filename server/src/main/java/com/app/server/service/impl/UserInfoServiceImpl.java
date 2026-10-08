package com.app.server.service.impl;


import com.app.server.base.BaseServiceImpl;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.dto.LoginUser;
import com.app.server.entity.*;
import com.app.server.mapper.LikeMapper;
import com.app.server.mapper.PhotoInfoMapper;
import com.app.server.mapper.UserCollectionMapper;
import com.app.server.mapper.UserInfoMapper;
import com.app.server.service.UserInfoService;
import com.app.server.util.FilePhotoUtil;
import com.app.server.util.JwtUtil;
import com.app.server.util.TotalCollect;
import com.app.server.util.UserThreadLocal;
import com.app.server.vto.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户操作接口实现
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService {

    /**
     * 映射地址
     */
    @Value("${appPhoto.file.static.url}")
    private String staticUrl;

    /**
     * 实体path
     */
    @Value("${appPhoto.file.path}")
    private String filePath;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Autowired
    private PhotoInfoMapper photoInfoMapper;

    @Resource
    private TotalCollect totalCollect;

    @Autowired
    private LikeMapper likeMapper;

    /**
     * 接口实现
     *
     * @param userInfo 用户基本信息
     * @return
     */
    @Override
    public Result addUser(UserInfo userInfo) {
        //注册前查看账号是否重复
        if (userInfoMapper.selectUserByNum(userInfo.getNum()) > 0) return wrong("账号重复");
        if (Objects.equals(userInfo.getNum(), "") || Objects.equals(userInfo.getPwd(), "") || Objects.equals(userInfo.getName(), ""))
            return wrong("信息为空");
        userInfoMapper.addUser(userInfo);
        return success();
    }

    /**
     * 修改用户
     *
     * @param userInfo
     * @return
     */
    @Override
    public Result updateUser(UserInfo userInfo) throws Exception {
        //获取现在的UserInfo
        UserInfo user = UserThreadLocal.getUser();
        if (!user.getNum().equals(userInfo.getNum())) {
            if (userInfoMapper.selectUserByNum(userInfo.getNum()) > 0) {
                return wrong("账号重复");
            }
        }
        if (!user.getUserImageUrl().equals(userInfo.getUserImageUrl()) && user.getUserImageUrl() != null) {
            String path = filePath + "/" + user.getUserImageUrl().replace(staticUrl, "");
            //删除之前的头像实体文件
            FilePhotoUtil.deleteFileUrl(path);
        }
        userInfoMapper.updateUser(userInfo);
        return loginReload();
    }

    /**
     * 查询自己上传或者收藏的图片
     *
     * @param user
     * @return
     */
    @Override
    public Result myPhotoList(User user) {
        //开始分页
        PageHelper.startPage(user.getPageCurrent(), user.getPageSize());
        if (user.getViewType() == 0) {
            return success(new PageInfo<>(totalCollect.TotalCollect(userInfoMapper.selectPhotoList(UserThreadLocal.getUser().getId()))));
        } else {
            return success(new PageInfo<>(totalCollect.TotalCollect(userInfoMapper.selectMyCollectPhotoList(UserThreadLocal.getUser().getId()))));
        }
    }


    /**
     * 删除收藏或者删除照片
     *
     * @param user
     * @return
     */
    @Override
    public Result del(User user) {
        //逻辑判断,0是删除自己上传的
        if (user.getViewType() == 0) {
            //首先删除关于这张图片的收藏数
            userCollectionMapper.deleteByPhotoId(user.getPhotoId());
            //在删除实体地址
            FilePhotoUtil.deleteFileUrl(user.getFilePath());
            //数据库删除
            photoInfoMapper.deleteById(user.getPhotoId());
        } else {
            //删除自己收藏的
            userCollectionMapper.deleteByUserIdAndPhoneId(user.getPhotoId(), UserThreadLocal.getUser().getId());
        }
        return success();
    }

    /**
     * 登录
     *
     * @param userInfo
     * @return
     */
    @Override
    public Result login(UserInfo userInfo) {
        //执行语句查看是否存在相同数据
        UserInfo user = userInfoMapper.selectUserByNumAndPwd(userInfo);
        if (user == null) {
            return loginFail();
        } else {
            //生成Jwt
            String token = JwtUtil.sign(userInfo.getNum(), userInfo.getPwd());
            //登录信息存入本地线程中
            UserThreadLocal.setUser(user);
            System.out.println(UserThreadLocal.getUser());
            return success(new LoginUser(user, token));
        }
    }

    /**
     * 收藏图片
     *
     * @param photoId
     * @return
     */
    @Override
    public Result collect(Integer photoId) {
        UserInfo userInfo = UserThreadLocal.getUser();
        //查看是否重复收藏
        if (userCollectionMapper.selectCountByPhotoIdAndUserId(photoId, userInfo.getId()) == 1 || userCollectionMapper.selectCountByPhotoIdAndUserId(photoId, userInfo.getId()) > 1) {
            return success("请勿重复收藏");
        } else {
            userCollectionMapper.addCollect(new UserCollection(photoId, userInfo.getId(), new Date()));
            return success();
        }

    }

    /**
     * 用户上传图片
     *
     * @param photoInfo
     * @return
     */
    @Override
    public Result upload(PhotoInfo photoInfo) throws Exception {
        //获取用户id
        photoInfo.setUserId(UserThreadLocal.getUser().getId());
        //存入当前时间
        photoInfo.setDateTime(new Date());
        photoInfoMapper.updatePhotoInfoByFilePath(photoInfo);
        //存入图片表
//        photoInfoMapper.add(photoInfo);
        return success();
    }

    @Override
    public Result page(Page page) {
        PageHelper.startPage(page.getPageCurrent(), page.getPageSize());
        List<UserInfo> userInfos = userInfoMapper.selectUserByLikeUserName(page.getUserName());
        userInfos.forEach(x->{
            x.setCollectTotal(userInfoMapper.totalCollect(x.getId()));
            x.setLikeTotal(userInfoMapper.totalLike(x.getId()));
            x.setCommentTotal(userInfoMapper.totalComment(x.getId()));
            x.setPoTotal(userInfoMapper.totalPo(x.getId()));
        });
        return success(new PageInfo<UserInfo>(userInfos));
    }

    @Override
    public Result delete(List<Integer> idList) {
        idList.forEach(x -> {
            userInfoMapper.delete(x);
        });
        return success();
    }

    /**
     * 点赞图片
     *
     * @return
     */
    @Override
    public Result like(PhotoInfo photoInfo) {
        UserInfo userInfo = UserThreadLocal.getUser();
        if (!photoInfo.getType()) {
            //查看是否重复点赞
            if (likeMapper.selectCountByPhotoIdAndUserId(photoInfo.getId(), userInfo.getId()) == 1 || likeMapper.selectCountByPhotoIdAndUserId(photoInfo.getId(), userInfo.getId()) > 1) {
                return success("请勿重复点赞");
            } else {
                likeMapper.addLike(new LikeTable(userInfo.getId(), photoInfo.getId()));
                return success();
            }
        } else {
            //查看是否重复点赞
            if (likeMapper.selectCountByCommentIdAndUserId(photoInfo.getId(), userInfo.getId()) == 1 || likeMapper.selectCountByCommentIdAndUserId(photoInfo.getId(), userInfo.getId()) > 1) {
                return success("请勿重复点赞");
            } else {
                LikeTable likeTable = new LikeTable();
                likeTable.setCommentId(photoInfo.getId());
                likeTable.setUserId(userInfo.getId());
                likeMapper.addLikeT(likeTable);
                return success();
            }
        }

    }

    /**
     * 统计信息获取
     *
     * @return
     */
    @Override
    public Result staticCount() {
        //收藏数，点赞数，评论数,上传的图片数
        Map<String, Integer> map = new HashMap<>();
        Integer userId = UserThreadLocal.getUser().getId();
        map.put("totalCollect", userInfoMapper.totalCollect(userId));
        map.put("totalLike", userInfoMapper.totalLike(userId));
        map.put("totalComment", userInfoMapper.totalComment(userId));
        map.put("totalPic", userInfoMapper.totalPic(userId));
        map.put("totalPo", userInfoMapper.totalPo(userId));
        return success(map);
    }

    /**
     * 个人中心数据获取
     *
     * @return
     */
    @Override
    public Result mineData(Integer type) {
        if (type == 0) {
            return success(userInfoMapper.selectUserCollect(UserThreadLocal.getUser().getId()));
        } else if (type == 1) {
            List<PhotoInfo> photoInfos = userInfoMapper.selectUserLike(UserThreadLocal.getUser().getId());
            photoInfos.forEach(x->{
                x.setFiles(Collections.singletonList(x.getPhotoUrl()));
            });
            List<PhotoInfo> photoInfosList = userInfoMapper.selectUserLikePhoto(UserThreadLocal.getUser().getId());
            photoInfosList.forEach(x->{
                x.setFiles(Arrays.asList(x.getFile().split(",")));
            });
            photoInfosList.addAll(photoInfos);
            return success(photoInfosList);
        } else if (type == 2) {
            return success(userInfoMapper.selectUserPhoto(UserThreadLocal.getUser().getId()));
        } else if (type == 3) {
            List<PhotoCircle> photoCircles = userInfoMapper.selectUserPhotoCire(UserThreadLocal.getUser().getId());
            photoCircles.forEach(x->{
                x.setFiles(Arrays.asList(x.getFile().split(",")));
            });
            return success(photoCircles);
        } else {
            List<PhotoCircle> photoCircles = userInfoMapper.selectUserComment(UserThreadLocal.getUser().getId());
            photoCircles.forEach(x->{
                x.setFiles(Arrays.asList(x.getFile().split(",")));
            });
            return success(photoCircles);
        }
    }

    /**
     * 个人中心数据删除
     *
     * @param id
     * @param type
     * @return
     */
    @Override
    public Result delMineData(Integer id, Integer type) {
        if (type == 0) {
            userInfoMapper.delUserCollect(id);
        } else if (type == 1) {
            userInfoMapper.delUserLike(id);
        } else if (type == 2) {
            userInfoMapper.delUserPhoto(id);
        } else if (type == 3) {
            userInfoMapper.delUserPhotoCire(id);
        } else {
            userInfoMapper.delUserComment(id);
        }
        return success();
    }

    /**
     * @param photoUploadByTag
     * @return
     */
    @Override
    public Result uploadUser(PhotoInfo photoUploadByTag) {
        //获取用户id
        photoUploadByTag.setUserId(UserThreadLocal.getUser().getId());
        //存入当前时间
        photoUploadByTag.setDateTime(new Date());
//        photoInfoMapper.updatePhotoInfoByFilePath(photoUploadByTag);
        //存入图片表
        photoInfoMapper.add(photoUploadByTag);
        return success();
    }
}
