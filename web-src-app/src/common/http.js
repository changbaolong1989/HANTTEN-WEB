/**axios封装
 * 请求拦截、相应拦截、错误统一处理
 */
import axios from 'axios';
import qs from 'qs';
// import Vue from 'vue'
import {
  Indicator
} from 'mint-ui'

// post请求头
axios.defaults.headers.post['Content-Type'] = 'application/json';
// // 请求拦截器
axios.interceptors.request.use(
  req => {
    Indicator.open({
      text: '加载中...',
      spinnerType: 'snake'
    });
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
      Indicator.close();
      return Promise.resolve(response);
    } else {
      /*数据请求失败 */
      return Promise.reject(response);
      Indicator.open({
        text: '加载中...',
        spinnerType: 'snake'
      });
    }
  },
  // 服务器状态码不是200的情况
  error => {
    if (error.response.status) {
      switch (error.response.status) {
        // 401: 未登录   
        // 未登录则跳转登录页面，并携带当前页面的路径   
        // 在登录成功后返回当前页面，这一步需要在登录页操作。   
        case 401:
          router.replace({
            path: '/',
            query: {
              redirect: router.currentRoute.fullPath
            }
          });
          break;
          // 404请求不存在   
        case 404:

          break;
          // 其他错误，直接抛出错误提示   
        default:

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
export function requestGet(url, params) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
        params: params
      })
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        reject(err)
      })
  });
}
/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function requestPost(url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params)
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        reject(err)
      })
  });
}
