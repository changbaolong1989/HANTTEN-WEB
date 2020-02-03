const listHeaders = [
  {
    id: 'contractNum',
    name: '合同编号'
  },
  {
    id: 'treatyType',
    name: '合同分类'
  },
  {
    id: 'undertakeType',
    name: '承包方式'
  },
  {
    id: 'contractName',
    name: '合同名称'
  },
  {
    id: 'contractUnit',
    name: '合作单位'
  },
  {
    id: 'planDate',
    name: '计划工期'
  },
  {
    id: 'contractClass',
    name: '合同类型'
  },
  {
    id: 'targetCost',
    name: '目标成本（元）'
  },
  {
    id: 'exciseMoney',
    name: '除税金额（元）'
  },
  {
    id: 'taxRatio',
    name: '税率（%）'
  },
  {
    id: 'taxMoney',
    name: '税金（元）'
  },
  {
    id: 'withTaxMoney',
    name: '含税金额（元）'
  },
  {
    id: 'statementAmount',
    name: '结算金额（元）'
  },
  {
    id: 'drawingScheme',
    name: '图纸/方案（元）'
  },
  {
    id: 'alterDiscuss',
    name: '变更洽商（元）'
  },
  {
    id: 'visa',
    name: '签证（元）'
  },
  {
    id: 'adjustPrice',
    name: '材料设备调差（元）'
  },
  {
    id: 'surgePrice',
    name: '物价波动调差（元）'
  },
  {
    id: 'serviceFee',
    name: '总包服务费（元）'
  },
  {
    id: 'taxPrice',
    name: '税金调差（元）'
  },
  {
    id: 'otherThing',
    name: '其他（元）'
  },
  {
    id: 'remark',
    name: '备注'
  }
];

let statementForm = {
  statementId: '',
  projectId: '',
  contractId: '',
  contractName: '',
  statementAmount: '',
  drawingScheme: '',
  alterDiscuss: '',
  visa: '',
  adjustPrice: '',
  surgePrice: '',
  serviceFee: '',
  taxPrice: '',
  otherThing: '',
  remark: ''
};

function getFormRules(customValidate) {
  return {
    contractName: [{required: true, message: '请选择合同名称', trigger: 'change'}],
    statementAmount: [{required: true, message: '请输入结算金额', trigger: 'blur'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    drawingScheme: [{required: true, message: '请输入图纸方案', trigger: 'blur'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    alterDiscuss: [{required: true, message: '请输入变更洽商', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    visa: [{required: true, message: '请输入签证', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    adjustPrice: [{required: true, message: '请输入材料设配调差', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    surgePrice: [{required: true, message: '请输入物价波动调差', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    serviceFee: [{required: true, message: '请输入总包服务费', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    taxPrice: [{required: true, message: '请输入税金调差', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    otherThing: [{required: true, message: '请输入其他', trigger: 'change'},{validator: customValidate.moneyCheck, trigger: 'blur'}],
    remark: [{max: 100, message: '最大长度不能超过100', trigger: 'blur'}]
  };
}

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

const contractClassSelectData = [
  {'text': '单价合同', 'value': '1'},
  {'text': '总价合同', 'value': '2'},
  {'text': '其他', 'value': '3'}
];


export default {
  listHeaders,
  statementForm,
  getContractInfo,
  getText,
  getFormRules,
  treatyType,
  undertakeType,
  contractClassSelectData
  /*professionalSelectData,
  staSelectData,
  alterAnalyseSelectData,

  */
}
