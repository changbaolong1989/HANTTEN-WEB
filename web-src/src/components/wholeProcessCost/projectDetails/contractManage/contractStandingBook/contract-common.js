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
    id: 'measureSts',
    name: '重计量状态'
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
    id: 'remark',
    name: '备注'
  }
];

let contractForm = {
  contractId: '',
  contractName: '',
  projectId: '',
  tenderId: '',
  treatyType: '',
  treatyTypeName: '',
  undertakeType: '',
  undertakeTypeName: '',
  contractNum: '',
  contractUnit: '',
  planDate: '',
  contractClass: '',
  contractClassName: '',
  measureSts: '',
  measureStsName: '',
  targetCost: '',
  exciseMoney: '',
  taxRatio: '',
  taxMoney: '',
  withTaxMoney: '',
  remark: ''
};

function getFormRules(customValidate) {
  return {
    contractName: [{required: true, message: '请选择合同名称', trigger: 'change'}],
    contractNum: [{validator: customValidate.checkNumAlphaSymbol, trigger: 'blur'},{required: true, message: '请输入合同编号', trigger: 'blur'}],
    contractUnit: [{max: 20, message: '最大长度不能超过20', trigger: 'blur'}],
    planDate: [{max: 20, message: '最大长度不能超过20', trigger: 'blur'}],
    exciseMoney: [{validator: customValidate.moneyCheck, trigger: 'blur'}],
    targetCost: [{validator: customValidate.moneyCheck, trigger: 'blur'}],
    taxMoney: [{validator: customValidate.moneyCheck, trigger: 'blur'}],
    withTaxMoney: [{validator: customValidate.moneyCheck, trigger: 'blur'}],
    taxRatio: [{validator: customValidate.checkPercentage, trigger: 'blur'}],
    remark: [{max: 100, message: '最大长度不能超过100', trigger: 'blur'}]
  };
}

const contractClassSelectData = [
  {'text': '单价合同', 'value': '1'},
  {'text': '总价合同', 'value': '2'},
  {'text': '其他', 'value': '3'}
];
const measureStsSelectData = [
  {'text': '无需重计量', 'value': '1'},
  {'text': '重计量未开始', 'value': '2'},
  {'text': '重计量未完成', 'value': '3'},
  {'text': '重计量已完成', 'value': '4'}
];

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

function getTenderInfo(data, tenderId) {
  for (let i = 0, len = data.length; i < len; i++) {
    if (data[i]['tenderId'] === tenderId) {
      return data[i];
    }
  }
}

function getContractClassText(val) {
  for (let i = 0, contractType = contractClassSelectData; i < contractType.length; i++) {
    if (contractType[i].value === val) {
      return contractType[i].text;
    }
  }
}

function getMeasureStsText(val) {
  for (let i = 0, measureSts = measureStsSelectData; i < measureSts.length; i++) {
    if (measureSts[i].value === val) {
      return measureSts[i].text;
    }
  }
}


export default {
  listHeaders,
  contractForm,
  contractClassSelectData,
  measureStsSelectData,
  treatyType,
  undertakeType,
  getContractClassText,
  getMeasureStsText,
  getFormRules,
  getTenderInfo
}
