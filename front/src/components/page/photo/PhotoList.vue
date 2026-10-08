<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 图片信息列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除
                </el-button>
                <el-button
                    type="primary"
                    icon="el-icon-lx-add"
                    class="handle-del mr10"
                    @click="addPhoto"
                >新增图片
                </el-button>
            </div>
            <div class="handle-box">
                <el-select  v-model="query.tagId" placeholder="请选择图片分类" @change="searchTag">
                    <el-option key="" label="全部类型" value=""></el-option>
                    <el-option v-for="item in tagList" v-bind:value="item.id"
                               v-bind:label="item.name" v-bind:key="item.id" ></el-option>
                </el-select>
            </div>
            <div class="handle-box">

                <el-input v-model="query.title" placeholder="请输入图片标题" class="handle-input mr10"
                          @change="search"></el-input>
            </div>
            <el-table
                :data="infoList"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="160" align="center"></el-table-column>
                <el-table-column prop="title" label="图片标题" width="160" align="center"></el-table-column>
                <el-table-column prop="dateTime" label="上传时间" align="center" width="160"></el-table-column>
                <el-table-column  label="图片"  align="center">
                    <template slot-scope="scope">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item >
                                <div class="demo-image__preview">
                                    <el-image
                                        style="width: 100px; height: 100px"
                                        :src="scope.row.photo_url"
                                    >
                                    </el-image>
                                </div>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="tagName" label="分类名称"  align="center"></el-table-column>
                <el-table-column label="操作" width="180" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button
                            class="green"
                            @click="roleInfo(scope.row)"
                        >编辑
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total,sizes, prev, pager, next, jumper"
                    @size-change="handlePageSizeChange"
                    @current-change="handlePageChange"
                    :current-page="query.pageCurrent"
                    :page-sizes="[10, 50, 100, 200,500,1000]"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                >
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import { listTag,pagePhoto,deletePhoto} from '../../../utils';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                pageCurrent: 1,
                pageSize: 10,
                idList:[],
                tagId:"",
                title:""
            },
            //上次登录时间与地址
            infoList:[],
            pageTotal: 0,
            tagList:[]
        };
    },
    created() {
        this.getInfoList();
        this.getTagList();
    },
    methods: {
        getTagList(){
            listTag().then(res=>{
                this.tagList=res.data.data
            })
        },

        getInfoList() {
            pagePhoto(this.query).then(res => {
                let data = res.data;
                this.pageTotal = data.data.total;
                this.infoList = data.data.list;
            });
        },
        // 多选操作
        handleSelectionChange(val) {
            let idList = [];
            val.forEach(function(item) {
                idList.push(item.id);
            });
            this.query.idList = idList;
        },
        searchTag(val){
            console.log(val)
            this.$set(this.query, 'tagId', val);
            this.getInfoList();
        },
        //删除操作
        delAllSelection() {
            if (this.query.idList.length === 0) {
                this.$message.warning('请勾选数据');
            } else {
                deletePhoto(this.query).then(res => {
                    this.$message.success(res.data.msg);
                    this.getInfoList();
                });
            }
        },
        //分页跳转
        handlePageChange(val) {
            this.$set(this.query, 'pageCurrent', val);
            this.getInfoList();
        },
        //每页大小改变
        handlePageSizeChange(val) {
            this.$set(this.query, 'pageSize', val);
            this.getInfoList();
        },
        search(val){
            this.$set(this.query, 'title', val);
            this.getInfoList();
        },
        //修改用户信息
        roleInfo(id) {
            localStorage.setItem('photo', JSON.stringify(id));
            this.$router.push('/addPhoto')
        },
        addPhoto(){
            this.$router.push('/addPhoto');
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.mr10 {
    margin-right: 10px;
}

</style>
