// import Vue from 'vue'
import MintUI from 'mint-ui'
import {
  Toast
} from 'mint-ui';
import {
  MessageBox
} from 'mint-ui';
import tool from './tool.js'
import 'mint-ui/lib/style.css'
import './assets/css/reset.css'
import './assets/css/common.css'
import './assets/css/form.css'
import './assets/css/nav.css'
import './assets/css/list.css'
/* icon图标库 */
import './assets/font/iconfont.css'
import './assets/font/iconfont.js'
import App from './App'
import router from './router'
import axios from 'axios';
import SStorage from '@/store/session' /* sessionStorage的方法js */
Vue.prototype.SStorage = SStorage
/* 注册全局组件 */
/* element-ui__折叠面板 */
import collapse from './common/vue/element-ui/packages/collapse/src/collapse.vue';
import collapseItem from './common/vue/element-ui/packages/collapse/src/collapse-item.vue';
import mSelect from './common/vue/mSelect.vue';
/* 自定义下拉组件 */
Vue.component('el-collapse', collapse);
Vue.component('el-collapse-item', collapseItem);
Vue.component('m-select', mSelect);
/* 表单验证 */
import VeeValidate,{
  Validator
} from './common/validate.js'
Vue.use(VeeValidate)
/* 公共请求 */
import {
  requestGet,
  requestPost
} from './common/http.js';
/* 日期格式化 */
import {
  formatting,formattingStr
} from './common/dateFormatting.js';
import {
  downloadFile,
  groupBy ,
  getNextStepNode,
  getStepName,
  getStepWorker,
  getStepNum,
  seniorManagerDeptId,
  marketDeptId,
  manufactureDeptId,
  tenderDeptId,
  superviseDeptId,
} from './common/bank.js';
/* px自动转化rem */
window.onresize = setHtmlFontSize;

function setHtmlFontSize() {
  const htmlWidth = document.documentElement.clientWidth || document.body.clientWidth;
  const htmlDom = document.getElementsByTagName('html')[0];
  htmlDom.style.fontSize = htmlWidth / 10 + 'px';
};
setHtmlFontSize();
Vue.config.productionTip = false
// Vue.use(MintUI)
Vue.prototype.requestPost = requestPost
Vue.prototype.formatting = formatting
Vue.prototype.formattingStr = formattingStr
Vue.prototype.Toast = Toast
Vue.prototype.MessageBox = MessageBox
//文件下载公用方法
Vue.prototype.downloadFile = downloadFile
//列表分组方法
Vue.prototype.groupBy = groupBy
//得到下级步骤
Vue.prototype.getNextStepNode = getNextStepNode
//得到步骤名称
Vue.prototype.getStepName = getStepName
//得到下级步骤
Vue.prototype.getStepWorker = getStepWorker
//得到下级步骤
Vue.prototype.getStepNum = getStepNum
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
Vue.prototype.Toast = Toast;
axios.defaults.withCredentials = true /*跨域sessionID不变*/
axios.defaults.baseURL = 'http://localhost:18866' /* 跨域 */
// axios.defaults.baseURL = '' /* 跨域 */
new Vue({
  el: '#app',
  router,
  components: {
    App
  },
  template: '<App/>',
  beforeCreate() {
    Vue.prototype.bus = new Vue(); // 中央事件总线(eventBus)
  }
})
