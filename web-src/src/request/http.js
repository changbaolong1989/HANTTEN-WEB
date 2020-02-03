/**axios封装
 * 请求拦截、相应拦截、错误统一处理
 */
import Vue from 'vue'
import axios from 'axios';
import store from '../store/store'
import context from '@/main.js'
import router from '../router/index'
import {
  showLoading,
  hideLoading
} from './loading';
// post请求头
axios.defaults.headers.post['Content-Type'] = 'application/json';
// // 请求拦截器
axios.interceptors.request.use(
/*  config => {
    if (store.state.token) {
      config.headers.Authorization = `${store.state.token}`
    }
    return config
  },*/
  req => {
    var flag = req.data;
    if(req.url!="/message/query/searchMessageDetailInfo"){
      // showLoading();
    }
    return req;
  },
  error => {
    return Promise.error(error);
  })

// 响应拦截器
axios.interceptors.response.use(
  response => {
    if (response.status === 200) {
      // 数据请求成功
      hideLoading();
      return Promise.resolve(response);
    } else {
      /*数据请求失败 */
      hideLoading();
      return Promise.reject(response);
    }
  },
  // 服务器状态码不是200的情况
  error => {
    console.log("error:",error)
    if (error.response.status) {
      switch (error.response.status) {
        // 401: 未登录   
        // 未登录则跳转登录页面，并携带当前页面的路径   
        // 在登录成功后返回当前页面，这一步需要在登录页操作。   
        case 401:
          hideLoading();
          //store.commit('doLogout');
          router.currentRoute.path !== '/' &&
          router.replace({
            path: '/'
          });
          break;
          // 404请求不存在   
        case 404:
          context.$message({
            center: true,
            message: '网络请求不存在',
            type: 'error'
          });
          break;
          // 其他错误，直接抛出错误提示   
        default:
          context.$message({
            center: true,
            message: error.response.data.message,
            type: 'error'
          });
      }
      return Promise.reject(error.response);
    }
  }
);

/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get(url, params) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
        params: params
      })
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        if (!err.status == 401) {
          reject(err)
        }
      })
  });
}
/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function post(url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params)
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        if (!err.status == 401) {
          reject(err);
        }
      })
  });
}
