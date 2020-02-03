import Vue from 'vue'
import App from './App'
import axios from 'axios'
import './assets/css/reset.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import 'babel-polyfill'
import tool from './tool.js'
import store from './store/store'
/* 图片预览插件 */
import preview from 'vue-photo-preview';
import 'vue-photo-preview/dist/skin.css';
/* icon图标库 */
import './assets/font/iconfont.css'
import './assets/font/iconfont.js'
/* 引用vuex */
import SStorage from '@/store/session' /* sessionStorage的方法js */
import {
  apiAddress,
  apiAddressGet,
  stateDialog,
} from '@/request/api';
import {
  integer,
  capital,
  fixation,
  tel,
  moneyCheck,
  percentage,
  checkNumAlphaSymbol,
  checkPercentage,
  positiveNumber
} from '@/request/verify';
import{
  downloadFile,
  groupBy ,
  getNextStepNode,
  getStepName,
  getStepWorker,
  getStepNum,
  formatting,
  seniorManagerDeptId,
  marketDeptId,
  manufactureDeptId,
  tenderDeptId,
  superviseDeptId,
} from '@/request/common';
import './assets/css/common.css'
Vue.use(ElementUI,preview, {
  fullscreenEl: false
});
axios.defaults.withCredentials = true /*跨域sessionID不变*/
axios.defaults.baseURL = 'http://localhost:18866' /* 跨域 */
// axios.defaults.baseURL = '' /* 跨域 */
Vue.config.productionTip = false
Vue.prototype.$axios = axios //全局注册，使用方法为:this.$axios
Vue.prototype.SStorage = SStorage
Vue.prototype.apiAddress = apiAddress
Vue.prototype.apiAddressGet = apiAddressGet
Vue.prototype.stateDialog = stateDialog
Vue.prototype.integer = integer
Vue.prototype.capital = capital
Vue.prototype.fixation = fixation
Vue.prototype.tel = tel
Vue.prototype.moneyCheck = moneyCheck
Vue.prototype.percentage = percentage
Vue.prototype.positiveNumber = positiveNumber
Vue.prototype.checkNumAlphaSymbol = checkNumAlphaSymbol
Vue.prototype.checkPercentage = checkPercentage
//文件下载公用方法
Vue.prototype.downloadFile = downloadFile
//列表分组方法
Vue.prototype.groupBy = groupBy
//得到下级步骤
Vue.prototype.getNextStepNode = getNextStepNode
//得到步骤名称
Vue.prototype.getStepName = getStepName
//得到下级步骤工作人
Vue.prototype.getStepWorker = getStepWorker
//得到下级步骤
Vue.prototype.getStepNum = getStepNum
//日期格式化
Vue.prototype.formatting = formatting
//得到高管部门ID
Vue.prototype.seniorManagerDeptId = seniorManagerDeptId
//得到造价部ID
Vue.prototype.manufactureDeptId = manufactureDeptId
//得到招标部ID
Vue.prototype.tenderDeptId = tenderDeptId
//得到监理部ID
Vue.prototype.superviseDeptId = superviseDeptId
//得到市场部门ID
Vue.prototype.marketDeptId = marketDeptId
//添加自定义的工具助手
Vue.prototype.tool = tool;


/* eslint-disable no-new */
const vue = new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  template: '<App/>',
  beforeCreate() {
    Vue.prototype.bus = new Vue(); // 中央事件总线(eventBus)
  }
})
export default vue;
