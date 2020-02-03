const listHeaders = [
  {
    id: 'serialNumber',
    name: '序号'
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
    id: 'contractNum',
    name: '合同编号'
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
    id: 'targetCost',
    name: '目标成本（元）'
  },
  {
    id: 'withTaxMoney',
    name: '已签订合同总价（元）'
  },
  {
    id: 'uncalibratedPrice',
    name: '未定标金额总计（元）'
  },
  {
    id: 'withTaxMoney',
    name: '合同金额总计（元）'
  },
  {
    id: 'temporaryAmount',
    name: '暂定量/项调整'
  },
  {
    id: 'alterValue',
    name: '变更估值'
  },
  {
    id: 'drawingAdjust',
    name: '图差数量调整'
  },
  {
    id: 'signelAdjust',
    name: '暂估单价调整'
  },
  {
    id: 'materialAdjust',
    name: '材料市场调整'
  },
  {
    id: 'peopleAdjust',
    name: '人工市场调整'
  },
  {
    id: 'otherThing',
    name: '其他'
  },
  {
    id: 'taxAdjust',
    name: '税金调整'
  },
  {
    id: 'predictCloseValue',
    name: '预计结算估值'
  },
  {
    id: 'remark',
    name: '备注'
  }
];

let dynamicForm = {
  contractNum: '',
  uncalibratedPrice: '',
  temporaryAmount: '',
  drawingAdjust: '',
  signelAdjust: '',
  materialAdjust: '',
  peopleAdjust: '',
  otherThing: '',
  taxAdjust: '',
  remark: ''
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

const contractClassSelectData = [
  {'text': '单价合同', 'value': '1'},
  {'text': '总价合同', 'value': '2'},
  {'text': '其他', 'value': '3'}
];


export default {
  listHeaders,
  dynamicForm,
  getContractInfo,
  getText,
  treatyType,
  undertakeType,
  contractClassSelectData
  /*professionalSelectData,
  staSelectData,
  alterAnalyseSelectData,

  */
}
