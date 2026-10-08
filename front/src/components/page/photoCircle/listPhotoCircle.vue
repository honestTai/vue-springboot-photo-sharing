<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 论坛列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">

                <el-input v-model="query.comment" placeholder="请输入发表内容" class="handle-input mr10"
                          @change="handleSearchComment"></el-input>
            </div>
            <el-table
                :data="userList"
                row-key="id"
                border
                default-expand-all
            >
                <el-table-column prop="userName" label="发布用户" width="160" align="center"></el-table-column>
                <el-table-column prop="dateTime" label="时间" align="center" width="160"></el-table-column>
                <el-table-column prop="comment" label="内容" align="center"></el-table-column>
                <el-table-column prop="title" label="标题" align="center"></el-table-column>
                <el-table-column prop="likeTotal" label="点赞数" align="center" width="160"></el-table-column>
                <el-table-column prop="commentCount" label="评论数" align="center" width="160"></el-table-column>
                <el-table-column prop="viewCount" label="浏览数" align="center" width="160"></el-table-column>
                <el-table-column label="图片" align="center">
                    <template slot-scope="scope">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item >
                                <div class="demo-image__preview">
                                    <el-image
                                        style="width: 100px; height: 100px"
                                        :src="scope.row.files[0]"
                                    >
                                    </el-image>
                                </div>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button
                            class="green"
                            @click="deleteCommentsOrPhotoCircle(scope.row.id,scope.row.type)"
                        >删除
                        </el-button>
                        <el-button
                            class="green"
                            @click="viewComment(scope.row.id)"
                        >查看评论
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
        <el-dialog title="评论查看" :visible.sync="commentListView" width="80%">
            <div class="block">
                <el-timeline>
                    <el-timeline-item :timestamp="item.dateTime" placement="top" v-for="(item,index) in commentList"
                                      :key="index">
                        <el-button type="primary" plain @click="delComment(item.id)">删除评论</el-button>
                        <el-divider content-position="center"></el-divider>
                        <el-card>
                            <h4>内容: {{ item.comment }}</h4>
                            <p>{{ item.userName }} 评论于 {{ item.dateTime }}</p>
                            <div v-if="item.reply !== null">
                                <el-divider content-position="center">回复内容</el-divider>
                                <h4>内容: {{ item.reply }}</h4>
                                <p>回复于 {{ item.replyTime }}</p>
                            </div>
                        </el-card>

                    </el-timeline-item>
                </el-timeline>
            </div>
            <span slot="footer" class="dialog-footer">
        <el-button @click="commentListView = false">关闭</el-button>
      </span>
        </el-dialog>
    </div>

</template>

<script>
import { pagePhotoCircle, deletePhotoCircle, viewComment, delComment } from '../../../utils';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                pageCurrent: 1,
                pageSize: 10,
                name: '',
                idList: [],
                userName:"",
                comment:""
            },
            //上次登录时间与地址
            userList: [],
            pageTotal: 0,
            dialogVisible: false,
            opinion:{
                reply:"",
                id:""
            },
            id:"",
            commentListView:false,
            commentList:[],
            poId:null
        };
    },
    created() {
        this.getPhotoCricleList();
    },
    methods: {

        getPhotoCricleList() {
            pagePhotoCircle(this.query).then(res => {
                let data = res.data;
                this.pageTotal = data.data.total;
                this.userList = data.data.list;
            });
        },
        //分页跳转
        handlePageChange(val) {
            this.$set(this.query, 'pageCurrent', val);
            this.getPhotoCricleList();
        },
        deleteCommentsOrPhotoCircle(e,a){
            deletePhotoCircle({
                id:e,
                type:a
            }).then(res=>{
                this.$message.success('删除成功');
                this.getPhotoCricleList();
            })
        },
        viewComment(e){
            this.poId = e
            this.commentListView = true
            viewComment(e).then(res=>{
              this.commentList = res.data.data.children
            })
        },
        //每页大小改变
        handlePageSizeChange(val) {
            this.$set(this.query, 'pageSize', val);
            this.getPhotoCricleList();
        },
        //发表用户姓名
        handleSearchRealName(val) {
            this.$set(this.query, 'userName', val);
            this.getPhotoCricleList();
        },
        //发表用户姓名
        handleSearchComment(val) {
            this.$set(this.query, 'comment', val);
            this.getPhotoCricleList();
        },
        delComment(e){
            delComment(e).then(res=>{
                this.viewComment(this.poId)
            })
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
