<template>
    <div>

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 用户管理</el-breadcrumb-item>
                <el-breadcrumb-item>添加用户/修改用户</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="用户昵称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="登录账号">
                    <el-input v-model="form.num"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.pwd" type="password"></el-input>
                </el-form-item>
                <el-form-item label="用户头像">
                    <el-upload
                        action="http://127.0.0.1:7777/api/photo/app/uploadImgAddUser"
                        method="post"
                        enctype="multipart/form-data"
                        list-type="picture-card"
                        :on-preview="handlePictureCardPreview"
                        :on-success="photoSuccess"
                        :on-remove="removePhoto"
                        :file-list="photoList"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">立即创建</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { addUser, updateUser } from '@/utils';
import { Notification } from 'element-ui';

export default {
    data() {
        return {
            form: {
                num: '',
                name: '',
                pwd: '',
                userImageUrl: ''
            },
            photoList: [],
            dialogVisible: false,
            dialogImageUrl:""
        };
    },
    created() {
        var userId = localStorage.getItem('user');
        if (userId != null) {
            this.form = JSON.parse(userId);
            console.log(this.form);
            let photo = {};
            photo.name = this.form.userImageUrl;
            photo.url = this.form.userImageUrl;
            this.photoList.push(photo);
            console.log(this.photoList);
        }else {
            this.form = {
                num: '',
                name: '',
                pwd: '',
                userImageUrl: ''
            }
        }
    },
    methods: {
        photoSuccess(response) {
            if (response.code == 500) {
                Notification.error({
                    title: '上传失败，系统错误'
                });
            }
            this.form.userImageUrl = response.data.url;
        },
        handlePictureCardPreview(file) {
            this.dialogVisible = true;
            if (this.form.userImageUrl != '') {
                this.dialogImageUrl = this.form.userImageUrl;
            } else {
                this.dialogImageUrl = file.url;
            }
        },
        removePhoto() {
            this.form.userImageUrl = '';
        },
        save() {
            var userId = localStorage.getItem('userId');
            if (userId != null) {
                updateUser(this.form).then(res => {
                    console.log(res);
                    this.$message.success(res.data.msg);
                });
            } else {
                //直接保存用户
                addUser(this.form).then(res => {
                    console.log(res);
                    this.$message.success(res.data.msg);
                });
            }
            localStorage.removeItem('user')
            this.$router.push('/');
        }
    }
};
</script>
