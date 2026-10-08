import Vue from 'vue';
import Router from 'vue-router';

//路由
Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/home',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/home/Home.vue'),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/addUser',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/user/AddUser.vue'),
                    meta: { title: '添加/修改用户' }
                },
                {
                    path: '/photoList',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/photo/PhotoList.vue'),
                    meta: { title: '图片列表' }
                },
                {
                    path: '/addPhoto',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/photo/AddPhoto.vue'),
                    meta: { title: '添加/修改图片' }
                },
                {
                    path: '/listPhotoCircle',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/photoCircle/listPhotoCircle.vue'),
                    meta: { title: '论坛列表' }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/login/Login.vue'),
            meta: { title: '登录' }
        }
    ]
});
