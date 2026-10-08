<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户列表
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
<!--                <el-button-->
<!--                    type="primary"-->
<!--                    icon="el-icon-lx-add"-->
<!--                    class="handle-del mr10"-->
<!--                    @click="addUserFormShow"-->
<!--                >新增用户-->
<!--                </el-button>-->
            </div>
            <div class="handle-box">

                <el-input v-model="query.name" placeholder="请输入真实姓名" class="handle-input mr10"
                          @change="handleSearchRealName"></el-input>
            </div>
            <el-table
                :data="userList"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="160" align="center"></el-table-column>
<!--                <el-table-column prop="num" label="登录账号" width="160" align="center"></el-table-column>-->
                <el-table-column prop="name" label="用户姓名" align="center" width="160"></el-table-column>
                <el-table-column prop="collectTotal" label="收藏数" align="center" width="160"></el-table-column>
                <el-table-column prop="likeTotal" label="点赞数" align="center" width="160"></el-table-column>
                <el-table-column prop="commentTotal" label="评论数" align="center" width="160"></el-table-column>
                <el-table-column prop="poTotal" label="发帖数" align="center" width="160"></el-table-column>
                <el-table-column  label="头像"  align="center">
                    <template slot-scope="scope">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item >
                                <div class="demo-image__preview">
                                    <el-image
                                        style="width: 100px; height: 100px"
                                        :src="scope.row.userImageUrl"
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
                            @click="viewUserDetailInfo(scope.row)"
                        >编辑
                        </el-button>
                    </template>
<!--                    <template slot-scope="scope">-->
<!--                        <el-button-->
<!--                            class="green"-->
<!--                            @click="viewUserDetailInfo(scope.row)"-->
<!--                        >统计-->
<!--                        </el-button>-->
<!--                    </template>-->
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
import { pageUser, deleteUser} from '../../../utils';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                pageCurrent: 1,
                pageSize: 10,
                userName: '',
                idList:[]
            },
            //上次登录时间与地址
            userList:[],
            pageTotal: 0
        };
    },
    created() {
        this.getUserList();
    },
    methods: {
        //获取用户列表
        getUserList() {
            pageUser(this.query).then(res => {
                let data = res.data;
                this.pageTotal = data.data.total;
                this.userList = data.data.list;
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
        //删除操作
        delAllSelection() {
            if (this.query.idList.length === 0) {
                this.$message.warning('请勾选数据');
            } else {
                deleteUser(this.query).then(res => {
                    this.$message.success(res.data.msg);
                    this.getUserList();
                });
            }
        },
        //分页跳转
        handlePageChange(val) {
            this.$set(this.query, 'pageCurrent', val);
            this.getUserList();
        },
        //每页大小改变
        handlePageSizeChange(val) {
            this.$set(this.query, 'pageSize', val);
            this.getUserList();
        },
        //真实姓名
        handleSearchRealName(val) {
            this.$set(this.query, 'userName', val);
            this.getUserList();
        },
        //修改用户信息
        viewUserDetailInfo(id) {
            localStorage.setItem('user', JSON.stringify(id));
            this.$router.push('/addUser')
        },
        addUserFormShow(){
            this.$router.push('/addUser');
        },

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
