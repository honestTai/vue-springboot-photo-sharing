import { post } from './ajax';
import { get } from './ajax';

//后台接口地址
const baseUrl = 'http://127.0.0.1:7777/api/photo/app/';


//登录
export const login = data => post(`${baseUrl}login`, data);



/**用户接口开始**/
//获取用户信息
export const pageUser = data => post(`${baseUrl}page`, data);

//删除用户信息
export const deleteUser = data => post(`${baseUrl}delete`, data);

//增加用户信息
export const addUser = data => post(`${baseUrl}addUser`, data);

//获取单个用户信息
export const updateUser = data => post(`${baseUrl}updateUser`, data);

/**用户接口结束**/

/**图片信息管理***/
export const pagePhoto = data => post(`${baseUrl}photoPage`, data);

export const listTag = data => get(`${baseUrl}tagList`);

export const add = data => post(`${baseUrl}upload`, data);

export const updatePhoto = data => post(`${baseUrl}updatePhoto`, data);

export const deletePhoto = data => post(`${baseUrl}deletePhoto`, data);
/**图片信息管理***/

/**论坛管理**/

export const pagePhotoCircle = data => post(`${baseUrl}pagePhotoCircle`, data);

export const deletePhotoCircle = data => post(`${baseUrl}deletePhotoCricle`, data);

export const viewComment = data => get(`${baseUrl}detailInfo/`+data);

export const delComment = data => get(`${baseUrl}delMineData/`+data+`/4`);

/**论坛管理**/











