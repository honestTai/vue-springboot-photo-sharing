<template>
    <div>

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 图片管理</el-breadcrumb-item>
                <el-breadcrumb-item>添加/修改图片</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-form :model="photoInfo" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="图片分类" prop="tagId">
                    <el-select v-model="photoInfo.tagId" placeholder="请选择地图分类">
                        <el-option v-for="item in tagList" v-bind:value="item.id"
                                   v-bind:label="item.name" v-bind:key="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="图片标题" prop="title">
                    <el-input v-model="photoInfo.title"></el-input>
                </el-form-item>
                <el-form-item label="图片指纹算法" prop="fingerprint">
                    <el-input v-model="photoInfo.fingerprint" disabled></el-input>
                </el-form-item>
                <el-form-item label="实体Path" prop="file_path">
                    <el-input v-model="photoInfo.filePath" disabled></el-input>
                </el-form-item>
                <el-form-item label="图片">
                    <el-upload
                        action="http://127.0.0.1:7777/api/photo/app/uploadImgAddUser"
                        method="post"
                        enctype="multipart/form-data"
                        list-type="picture-card"
                        :on-preview="handlePictureCardPreview"
                        :on-success="photoSuccess"
                        :file-list="photoList"
                        :limit="1"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { add, listTag, updatePhoto } from '../../../utils';
import { Notification } from 'element-ui';

export default {
    data() {
        return {
            photoInfo: {
                photoUrl: '',
                tagId: '',
                filePath: '',
                fingerprint: '',
                title: '',
                id: '',
                userId: ''
            },
            rules: {
                // tagId: [
                //     { required: true, message: '请选择地图分类', trigger: 'change' }
                // ],
                // name: [
                //     { required: true, message: '请输入汽车名称', trigger: 'blur' }
                // ],
                // latitude: [
                //     { required: true, message: '请输入维度', trigger: 'blur' }
                // ],
                // longitude: [
                //     { required: true, message: '请输入经度', trigger: 'change' }
                // ]
            },
            //所有末级指标分类
            listMap: [],
            dialogVisible: false,
            dialogImageUrl: '',
            photoList: [],
            tagList: []
        };
    },
    created() {
        listTag().then(res => {
            this.tagList = res.data.data;
        });
        var photoInfo = localStorage.getItem('photo');
        console.log(photoInfo);
        if (photoInfo != null) {
            this.photoInfo = JSON.parse(photoInfo);
            this.photoInfo.filePath = this.photoInfo.file_path;
            this.photoInfo.photoUrl = this.photoInfo.photo_url;
            let photo = {};
            photo.name = this.photoInfo.photo_url;
            photo.url = this.photoInfo.photo_url;
            this.photoList.push(photo);
        }else {
            this.photoInfo = {
                photoUrl: '',
                tagId: '',
                filePath: '',
                fingerprint: '',
                title: '',
                id: '',
                userId: ''
            }
            this.photoList=[]
        }
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (localStorage.getItem('photo') != null) {
                        updatePhoto(this.photoInfo).then(res => {
                            let data = res.data;
                            this.$message.success(data.msg);
                        });
                    } else {
                        add(this.photoInfo).then(res => {
                            let data = res.data;
                            this.$message.success(data.msg);
                        });
                    }
                    // this.$message.success(data.message);
                    this.$router.push('/photoList');
                    return true;
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
            localStorage.removeItem('photo')
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        photoSuccess(response) {
            console.log(response);
            if (response.code == 500) {
                Notification.error({
                    title: '上传失败，系统错误'
                });
            }
            this.photoInfo.photoUrl = response.data.url;
            this.photoInfo.fingerprint = response.data.fingerprint;
            this.photoInfo.filePath = response.data.filePath;
        },
        handlePictureCardPreview(file) {
            this.dialogVisible = true;
            if (this.photoInfo.photo_url != '') {
                this.dialogImageUrl = this.photoInfo.photo_url;
            } else {
                this.dialogImageUrl = file.url;
            }
        }
    }
};
</script>
