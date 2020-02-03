const listHeaders = [
  {
    id: 'professionalId',
    name: '专业'
  },
  {
    id: 'alterName',
    name: '变更名称'
  },
  {
    id: 'contractNum',
    name: '合同编号'
  },
  {
    id: 'contractName',
    name: '合同名称'
  },
  {
    id: 'contentSummary',
    name: '内容摘要'
  },
  {
    id: 'talkDate',
    name: '洽商日期'
  },
  {
    id: 'receiveDate',
    name: '接收日期'
  },
  {
    id: 'sts',
    name: '状态'
  },
  {
    id: 'declareAmount',
    name: '申报金额（元）'
  },
  {
    id: 'auditAmount',
    name: '监理审核金额（元）'
  },
  {
    id: 'hanttenAmount',
    name: '汉腾审核金额（元）'
  },
  {
    id: 'ownerAmount',
    name: '业主审批金额（元）'
  },
  {
    id: 'addSubAmount',
    name: '核增/减金额（元）'
  },
  {
    id: 'symbol',
    name: '批准文号'
  },
  {
    id: 'alterAnalyse',
    name: '变更洽商费用分析'
  },
  {
    id: 'remark',
    name: '备注'
  }
];

let changeDetailForm = {
  alterId: '',
  projectId: '',
  contractId: '',
  contractName: '',
  alterName: '',
  professionalId: '',
  professionalName: '',
  contentSummary: '',
  talkDate: '',
  receiveDate: '',
  sts: '',
  stsName: '',
  declareAmount: '',
  auditAmount: '',
  hanttenAmount: '',
  ownerAmount: '',
  symbol: '',
  alterAnalyse: '',
  alterAnalyseName: '',
  remark: ''
};

const formRules = {
  contractName: [{
    required: true,
    message: '请选择合同名称',
    trigger: 'change'
  }],
  alterName: [{
    required: true,
    message: '请输入变更名称',
    trigger: 'blur'
  }],
  professionalName: [{
    required: true,
    message: '请选择专业名称',
    trigger: 'change'
  }],
  hanttenAmount: [{
    required: true,
    message: '请输入审核金额',
    trigger: 'change'
  }]
};

function getContractInfo(data, contractId) {
  for (let i = 0, len = data.length; i < len; i++) {
    if (data[i]['contractId'] === contractId) {
      return data[i];
    }
  }
}

function getText(val, data) {
  for (let i = 0; i < data.length; i++) {
    if (data[i].value === val) {
      return data[i].text;
    }
  }
}

const treatyType = {
  '1': '工程类合约',
  '2': '采购类合约',
  '3': '服务类合约',
  '4': '土地类合约',
  '5': '其他类合约'
};

const undertakeType = {
  '1': '施工总承包',
  '2': '发包人发包专业合同',
  '3': '发包人自行发包专业'
};

const professionalSelectData = [
  {'text': '图纸会审', 'value': '1'},
  {'text': '设计变更', 'value': '2'},
  {'text': '变更洽商', 'value': '3'},
  {'text': '现场签证', 'value': '4'}
];

const staSelectData = [
  {'text': '业主审批金额', 'value': 'A'},
  {'text': '汉腾与承包商已核对且无争议的变更洽商金额', 'value': 'B'},
  {'text': '汉腾与承包商已核对且有争议的变更洽商金额', 'value': 'C'},
  {'text': '汉腾已审核完毕尚未与申报单位核对金额', 'value': 'D'},
  {'text': '汉腾正在审核的变更金额', 'value': 'E'},
  {'text': '承包商申报资料不全或尚未申报费用', 'value': 'F'},
  {'text': '预计会发生，但无变更资料', 'value': 'G'}
];

const alterAnalyseSelectData = [
  {'text': '调差', 'value': '1'},
  {'text': '业主要求', 'value': '2'},
  {'text': '设计要求', 'value': '3'},
  {'text': '法规要求', 'value': '4'},
  {'text': '设计/合同错误或不足', 'value': '5'},
  {'text': '现场不可预见得情况', 'value': '6'},
  {'text': '深化设计/协调', 'value': '7'},
  {'text': '一般修改', 'value': '8'},
  {'text': '其他', 'value': '9'}
];

export default {
  listHeaders,
  changeDetailForm,
  formRules,
  treatyType,
  undertakeType,
  professionalSelectData,
  staSelectData,
  alterAnalyseSelectData,
  getContractInfo,
  getText
}
