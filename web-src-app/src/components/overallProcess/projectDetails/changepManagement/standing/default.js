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
    id: 'contractClass',
    name: '合同类型'
  },
  {
    id: 'withTaxMoney',
    name: '合同签订金额（元）'
  },
  {
    id: 'sumDeclareAmount',
    name: '申报金额（元）'
  },
  {
    id: 'sumHanttenAmount',
    name: '汉腾审核金额（元）'
  },
  {
    id: 'perHanttenAmount',
    name: '汉腾审核金额（%）'
  },
  {
    id: 'confirmed',
    name: '已确认（元）'
  },
  {
    id: 'estimatedAmount',
    name: '预估金额（元）'
  }
];

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

const contractClassData = [
  {'text': '单价合同', 'value': '1'},
  {'text': '总价合同', 'value': '2'},
  {'text': '其他', 'value': '3'}
];

export default {
  listHeaders,
  treatyType,
  undertakeType,
  contractClassData,
  getContractInfo,
  getText
}
