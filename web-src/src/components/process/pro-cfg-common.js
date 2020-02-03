const formRules = {
  processName: [{
    required: true,
    message: '请选择流程',
    trigger: 'change'
  }],
  nodeName: [{
    required: true,
    message: '请选择节点',
    trigger: 'change'
  }],
  departmentName: [{
    required: true,
    message: '请选择业务线',
    trigger: 'change'
  }],
  jobName: [{
    required: true,
    message: '请选择职位',
    trigger: 'change'
  }]
};

const listHeaders = [
  {
    id: 'processName',
    name: '流程名称'
  },
  {
    id: 'nodeId',
    name: '节点ID'
  },
  {
    id: 'nodeName',
    name: '节点名称'
  },
  {
    id: 'departmentName',
    name: '所属业务线'
  },
  {
    id: 'jobName',
    name: '节点职位'
  }
];

let processConfigForm = {
  uuid: '',
  processDictId: '',
  processName: '',
  nodeId: '',
  nodeName: '',
  departmentId: '',
  departmentName: '',
  jobId: '',
  jobName: ''
};


function loadSelect(vueObj, url, param, dataPropName) {
  vueObj.$axios.post(url, param, vueObj.config).then(function (response) {
    vueObj[dataPropName] = response.data.dataList;
  }).catch(function (error) {
  });
}


export default {formRules, processConfigForm, listHeaders, loadSelect}
