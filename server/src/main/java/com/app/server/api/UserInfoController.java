package com.app.server.api;

import com.app.server.EncrptInfo;
import com.app.server.base.Page;
import com.app.server.base.Result;
import com.app.server.entity.PhotoInfo;
import com.app.server.entity.UserInfo;
import com.app.server.filter.login.LoginFilter;
import com.app.server.service.UserInfoService;
import com.app.server.vto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息接口
 */
@RestController
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户注册
     *
     * @param userInfo 用户基本信息实体
     * @return
     */
    @PostMapping("addUser")
    @EncrptInfo(isEnOrDe = true, paramName = "userInfo")
    public Result addUser(@RequestBody UserInfo userInfo) throws Exception {
        return userInfoService.addUser(userInfo);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户基本信息实体
     * @return
     */
    @LoginFilter
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody UserInfo userInfo) throws Exception {
        return userInfoService.updateUser(userInfo);
    }

    /**
     * 查看自己的照片(0自己上传的1收藏)
     *
     * @param user 用户参数vto
     * @return
     */
    @LoginFilter
    @PostMapping("viewMyPhoto")
    public Result myPhotoList(@RequestBody User user) {
        return userInfoService.myPhotoList(user);
    }

    /**
     * 删除图片(0自己上传的1收藏的)
     * @param user 用户参数vto
     * @return
     */
    @LoginFilter
    @PostMapping("del")
    public Result del(@RequestBody User user) {
        return userInfoService.del(user);
    }

    /**
     * 用户收藏图片
     *
     * @param photoInfo 图片参数
     * @return
     */
    @LoginFilter
    @PostMapping("collect")
    public Result collect(@RequestBody PhotoInfo photoInfo) {
        return userInfoService.collect(photoInfo.getId());
    }

    /**
     * 用户点赞图片
     *
     * @param photoInfo 图片参数
     * @return
     */
    @LoginFilter
    @PostMapping("likeTable")
    public Result likeTable(@RequestBody PhotoInfo photoInfo) {
        return userInfoService.like(photoInfo);
    }

    /**
     * 上传图片
     * @param photoUploadByTag 图片上传参数类
     * @return
     */
    @LoginFilter
    @PostMapping("upload")
    public Result upload(@RequestBody PhotoInfo photoUploadByTag) throws Exception {
        return userInfoService.uploadUser(photoUploadByTag);
    }

    /**
     * 用户列表分页
     */
    @LoginFilter
    @PostMapping("page")
    public Result page(@RequestBody Page page){
        return userInfoService.page(page);
    }

    /**
     * 用户删除
     */
    @LoginFilter
    @PostMapping("delete")
    public Result delete(@RequestBody Page page){
        return userInfoService.delete(page.getIdList());
    }

    /**
     * 获取属于自己的信息
     */
    @LoginFilter
    @GetMapping("staticCount")
    public Result staticCount(){
        return userInfoService.staticCount();
    }


    /**
     * 获取属于自己的信息
     */
    @LoginFilter
    @GetMapping("mineData/{type}")
    public Result mineData(@PathVariable("type") Integer type){
        return userInfoService.mineData(type);
    }

    /**
     * 删除
     */
    @LoginFilter
    @GetMapping("delMineData/{id}/{type}")
    public Result delMineData(@PathVariable("id") Integer id,@PathVariable("type") Integer type){
        return userInfoService.delMineData(id,type);
    }


}
