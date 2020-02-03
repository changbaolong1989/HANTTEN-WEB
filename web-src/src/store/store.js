import Vuex from 'vuex'
import Vue from 'vue'
Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    user: {},
    token: localStorage.getItem('token') || '',
    status: '',
    processConfigObject: {
      data: [],
      total: 0,
      queryCondition: {
        uuid: '',
        processDictId: '',
        processName: '',
        nodeId: '',
        nodeName: '',
        departmentId: '',
        departmentName: '',
        jobId: '',
        jobName: ''
      }
    },
    wholeProcess: {
      contractObject: {
        data: [],
        total: 0,
        tenderInfo: []
      },
      changeStandingBookObject: {
        data: [],
        total: 0,
        contractInfo: []
      },
      changeStandingBook: {
        data: [],
        total: 0
      },
      statementObject: {
        data: [],
        total: 0,
        contractInfo: []
      },
      costObject: {
        data: [],
        total: 0
      }
    }
  },
  mutations: {
    doLogin: (state, data) => {
      console.log(data);
      localStorage.token = data.token;
      state.token = data.token;
    },
    doLogout: (state) => {
      localStorage.removeItem('token');
      state.token = null
    }
    /*    setProcessConfigObject(state, data) {
          this.processConfigData = data;
        }*/
    /*    changeProcessConfigData(state, data) {
          this.processConfigData = data;
        },
        changeProcessConfigDataTotal(state, data) {
          this.processConfigDataTotal = data;
        }*/
  }
})
