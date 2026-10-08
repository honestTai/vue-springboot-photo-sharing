import axios from 'axios';
import { Notification, MessageBox } from 'element-ui';
import router from '@/router';
import { Loading } from 'element-ui';



// 添加请求拦截器
axios.interceptors.request.use(function(config) {
    // axios.defaults.timeout=6000
    let loadingInstance1 = Loading.service({ fullscreen: true });
    const token = localStorage.getItem('token');
    if (token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
        config.headers.token = token;  //请求头加上token
    }
    return config;
}, function(error) {
    Notification.error({
        title: '请求失败'
    });
    // 对请求错误做些什么
    return Promise.reject(error);
});


// 添加响应拦截器
// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // axios.defaults.timeout=6000
    // Indicator.close();
    // 对响应数据做点什么
    let loadingInstance2 = Loading.service({ fullscreen: true });
    loadingInstance2.close();
    // Indicator.close();
    // 对响应数据做点什么
    if (response.data.code == 403) {
        Notification.error({
            title: '无权访问'
        });
    }
    if (response.data.code == 500) {
        Notification.error({
            title: response.data.msg
        });
    }
    if (response.data.code == 402) {
        Notification.error({
            title: response.data.msg
        });
    }
    if (response.data.code == 200) {
        return response;
    }
    if(response.data.code==401){
        Notification.success({
            title: response.data.msg+"请重新登录"
        });
        router.push('/login');
    }
}, function (error) {
    let loadingInstance2 = Loading.service({ fullscreen: true });
    loadingInstance2.close();
    Notification.error({
        title: '请求超时'
    });
    console.log(error)
    // 对响应错误做点什么
    return Promise.reject(error);
});


export const post = (name = '', data = {}) => {
    const url = `${name}`;

    return axios({
        method: 'POST',
        url,
        data,
        timeout: 300000
    });
};

export const get = (url) => {
    return axios({
        method: 'get',
        url: `${url}`,
        timeout: 300000
    });
};


// export default post
