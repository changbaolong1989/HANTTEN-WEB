// 咨询业务类型
const consultancy = [{
    label: '造价咨询',
    value: '0'
  },
  {
    label: '招标代理',
    value: '1'
  },
  {
    label: '项目管理',
    value: '2'
  }
];


const consultancyStage = {
  0: [{
      label: '编制',
      value: '0'
    },
    {
      label: '审核',
      value: '1'
    },
    {
      label: '全过程跟踪审计',
      value: '2'
    },
    {
      label: '全过程工程造价咨询',
      value: '3'
    }
  ],
  1: [{
      label: '施工',
      value: '0'
    },
    {
      label: '监理',
      value: '1'
    },
    {
      label: '设计',
      value: '2'
    },
    {
      label: '勘察',
      value: '3'
    },
    {
      label: '服务类',
      value: '4'
    }, {
      label: '采购',
      value: '5'
    },
    {
      label: '其他',
      value: '6'
    }
  ],
  2: []
};


const residential = [{
    label: '商品房',
    value: '0'
  },
  {
    label: '公租房',
    value: '1'
  },
  {
    label: '共有产权',
    value: '2'
  }
];
const contractType = [{
    label: '自定义合同',
    value: '0'
  },
  {
    label: '模板合同',
    value: '1'
  }
];

const payment = [{
    label: '一次性支付',
    value: '0'
  },
  {
    label: '分期支付',
    value: '1'
  }
];
const agentContent = [{
    label: '是',
    value: '1'
  },
  {
    label: '否',
    value: '0'
  }
];
const agencyRemuneration = [{
    label: '委托人',
    value: '0'
  },
  {
    label: '中标人',
    value: '1'
  }
];
const servicesrange = [{
    label: '决策阶段',
    value: '0,决策阶段'
  },
  {
    label: '设计阶段',
    value: '1,设计阶段'
  },
  {
    label: '发承包阶段',
    value: '2,发承包阶段'
  },
  {
    label: '实施阶段',
    value: '3,实施阶段'
  },
  {
    label: '竣工阶段',
    value: '4,竣工阶段'
  },
  {
    label: '其他阶段',
    value: '5,其他阶段'
  }
];
const nature = [{
    label: '新建',
    value: '0'
  },
  {
    label: '扩建',
    value: '1'
  },
  {
    label: '改建',
    value: '2'
  }
];
const clientNature = [{
    label: '政府部门',
    value: '0'
  },
  {
    label: '国企',
    value: '1'
  },
  {
    label: '私企',
    value: '2'
  },
  {
    label: '上市公司',
    value: '3'
  }
];
const classification = [{
    label: '住宅',
    value: '0'
  },
  {
    label: '商业建筑',
    value: '1'
  },
  {
    label: '酒店及休闲娱乐',
    value: '2'
  },
  {
    label: '工业建筑',
    value: '3'
  },
  {
    label: '教育',
    value: '4'
  },
  {
    label: '文化及体育设施',
    value: '5'
  },
  {
    label: '电力',
    value: '6'
  },
  {
    label: '铁路、道路及桥梁建设',
    value: '7'
  },
  {
    label: '水务',
    value: '8'
  },
  {
    label: '公用事业及城市基础设施',
    value: '9'
  },
  {
    label: '水运、交通',
    value: '10'
  },
  {
    label: '其他',
    value: '11'
  }
];
const character = [{
    label: '双方合同',
    value: '0'
  },
  {
    label: '三方合同',
    value: '1'
  },
  {
    label: '补充合同',
    value: '2'
  },
  {
    label: '单独核算',
    value: '3'
  },
  {
    label: '非单独核算',
    value: '4'
  }
];
const confirmation = [{
    label: '招标',
    value: '0'
  },
  {
    label: '财政委托',
    value: '1'
  },
  {
    label: '审计委托',
    value: '2'
  },
  {
    label: '商务洽谈',
    value: '3'
  },
  {
    label: '战略合作',
    value: '4'
  },
  {
    label: '法院委托',
    value: '5'
  },
  {
    label: '其他',
    value: '6'
  }
];
const jobDuties = [{
    label: '编制',
    value: '1'
  },
  {
    label: '审核',
    value: '2'
  },
  {
    label: '调整',
    value: '3'
  }
];

// const services = [{
//     label: '决策阶段',
//     value: '0',
//     children: [{
//         label: '投资估算',
//         value: '0'
//       },
//       {
//         label: '经济评价',
//         value: '1'
//       },
//       {
//         label: '其他',
//         value: '2'
//       }
//     ]
//   },
//   {
//     label: '设计阶段',
//     value: '1',
//     children: [{
//         label: '设计概算',
//         value: '4'
//       },
//       {
//         label: '施工图概算',
//         value: '5'
//       },
//       {
//         label: '其他',
//         value: '6'
//       }
//     ]
//   },
//   {
//     label: '发承包阶段',
//     value: '2',
//     children: [{
//         label: '工程量清单',
//         value: '7'
//       },
//       {
//         label: '最高投标现价',
//         value: '8'
//       },
//       {
//         label: '投标报价分析',
//         value: '9'
//       },
//       {
//         label: '清标报告',
//         value: '10'
//       },
//       {
//         label: '其他',
//         value: '11'
//       }
//     ]
//   },
//   {
//     label: '实施阶段',
//     value: '3',
//     children: [{
//         label: '资金使用计划',
//         value: '12'
//       },
//       {
//         label: '工程计量与工程款审核',
//         value: '13'
//       },
//       {
//         label: '合同价款调整',
//         value: '14'
//       },
//       {
//         label: '工程变更、索赔、签证',
//         value: '15'
//       },
//       {
//         label: '工程实施阶段造价控制',
//         value: '16'
//       },
//       {
//         label: '其他',
//         value: '17'
//       }
//     ]
//   },
//   {
//     label: '竣工阶段',
//     value: '4',
//     children: [{
//         label: '竣工结算',
//         value: '18'
//       },
//       {
//         label: '竣工决算',
//         value: '19'
//       },
//       {
//         label: '其他',
//         value: '20'
//       }
//     ]
//   },
//   {
//     label: '其他服务',
//     value: '5',
//     children: [{
//       label: '工程造价鉴定',
//       value: '21'
//     }]
//   }
// ];

const services = [{
    label: '决策阶段-投资估算',
    value: '0,决策阶段,投资估算'
  },
  {
    label: '决策阶段-经济评价',
    value: '0,决策阶段,经济评价'
  },
  {
    label: '决策阶段-其他',
    value: '0,决策阶段,其他'
  },
  {
    label: '设计阶段-设计概算',
    value: '1,设计阶段,设计概算'
  },
  {
    label: '设计阶段-施工图预算',
    value: '1,设计阶段,施工图预算'
  },
  {
    label: '设计阶段-其他',
    value: '1,设计阶段,其他'
  },
  {
    label: '发承包阶段-工程量清单',
    value: '2,发承包阶段,工程量清单'
  },
  {
    label: '发承包阶段-最高投标限价',
    value: '2,发承包阶段,最高投标限价'
  },
  {
    label: '发承包阶段-投标报价分析',
    value: '2,发承包阶段,投标报价分析'
  },
  {
    label: '发承包阶段-清标报告',
    value: '2,发承包阶段,清标报告'
  },
  {
    label: '发承包阶段-其他',
    value: '2,发承包阶段,其他'
  },
  {
    label: '实施阶段-资金使用计划',
    value: '3,实施阶段,资金使用计划'
  },
  {
    label: '实施阶段-工程计量与工程款审核',
    value: '3,实施阶段,工程计量与工程款审核'
  },
  {
    label: '实施阶段-合同价款调整',
    value: '3,实施阶段,合同价款调整'
  },
  {
    label: '实施阶段-工程变更、索赔、签证',
    value: '3,实施阶段,工程变更、索赔、签证'
  },
  {
    label: '实施阶段-工程实施阶段造价控制',
    value: '3,实施阶段,工程实施阶段造价控制'
  },
  {
    label: '实施阶段-其他',
    value: '3,实施阶段,其他'
  },
  {
    label: '竣工阶段-竣工结算',
    value: '4,竣工阶段,竣工结算'
  },
  {
    label: '竣工阶段-竣工决算',
    value: '4,竣工阶段,竣工决算'
  },
  {
    label: '竣工阶段-其他',
    value: '4,竣工阶段,其他'
  },
  {
    label: '其他服务-工程造价鉴定',
    value: '5,其他服务,工程造价鉴定'
  }
];

const jobContent1 = [{
    label: "编制",
    value: 1
  },
  {
    label: "审核",
    value: 2
  },
  {
    label: "调整",
    value: 3
  }
];
const jobContent2 = [{
  label: "编制",
  value: 1
}];



const templatelist = [{
    label: '造价咨询合同',
    value: '0'
  },
  {
    label: '建设工程招标代理合同',
    value: '1'
  },
  {
    label: '工程管理咨询服务合同',
    value: '2'
  },
  {
    label: '监理合同',
    value: '3'
  }
];

// 基本合同信息
const form = {
  projectName: '',
  consultancy: '' /* 咨询类型阶段 */ ,
  consultancyStage: '' /* 咨询类型阶段 */ ,
  location: '' /* 项目地点 */ ,
  classification: [] /* 项目类别 */ ,
  // 0：住宅
  landArea: '' /* 占地面积 */ ,
  floorArea: '' /* 建筑面积 */ ,
  eavesHeight: '' /* 檐高 */ ,
  buildingControlHeight: '' /* 建筑控制高度 */ ,
  volumeRatio: '' /* 容积率 */ ,
  residential: '请选择' /* 住宅类别 */ ,
  // 1：商业建筑businesslandArea
  businesslandArea: '' /* 占地面积 */ ,
  businessfloorArea: '' /* 建筑面积 */ ,
  businesseavesHeight: '' /* 檐高 */ ,
  businessbuildingControlHeight: '' /* 建筑控制高度 */ ,
  // 2：酒店及休闲娱乐
  hotellandArea: '' /* 占地面积 */ ,
  hotelfloorArea: '' /* 建筑面积 */ ,
  hoteleavesHeight: '' /* 檐高 */ ,
  hotelbuildingControlHeight: '' /* 建筑控制高度 */ ,
  hotelroomNum: '' /* 房间数量 */ ,
  // 3：工业建筑
  engineeringlandArea: '' /* 占地面积 */ ,
  engineeringfloorArea: '' /* 建筑面积 */ ,
  engineeringeavesHeight: '' /* 檐高 */ ,
  engineeringlevel: '' /* 层数 */ ,
  engineeringspan: '' /* 跨度 */ ,
  engineeringproduceCondition: '' /* 生产状况 */ ,
  // "4：教育
  educationlandArea: '' /* 占地面积 */ ,
  educationeducationfloorArea: '' /* 建筑面积 */ ,
  educationmakeGreenRatio: '' /* 绿化率 */ ,
  educationgreenLevel: '' /* 绿色等级 */ ,
  // 5：文化及体育设施"
  culturelandArea: '' /* 占地面积 */ ,
  culturefloorArea: '' /* 建筑面积 */ ,
  culturemakeGreenRatio: '' /* 绿化率 */ ,
  culturegreenLevel: '' /* 绿色等级 */ ,
  // 6：电力
  electrilandArea: '' /* 占地面积 */ ,
  electrifloorArea: '' /* 建筑面积 */ ,
  electriinstallationCapacity: '' /* 装机容量 */ ,
  electritransformersNum: '' /* 发电机变压器参数数量 */ ,
  electricableLayingLength: '' /* 线缆敷设长度 */ ,
  electricableLayingMethod: '' /* 线缆敷设方式 */ ,
  electritransmissionCapacity: '' /* 输电量 */ ,
  // 7：铁路、道路及桥梁建设
  railwaygrade: '' /* 等级 */ ,
  railwaybridgeLength: '' /* 全线（桥）长度 */ ,
  railwaydesignSpeed: '' /* 设计速度 */ ,
  railwayOrbitalType: '' /* 轨道类型 */ ,
  railwaybridgeType: '' /* 桥梁类型 */ ,
  // 8：水务
  waternormalOperatingWaterLevel: '' /* 正常运用水位 */ ,
  watertotalStorageCapacity: '' /* 总库容 */ ,
  watermaximumDamHeight: '' /* 最大坝高 */ ,
  waterinstalledCapacityOfPowerStation: '' /* 电站装机容量 */ ,
  // 9：公用事业及城市基础设施
  publiclandArea: '' /* 占地面积 */ ,
  publicfloorArea: '' /* 建筑面积 */ ,
  publicothers: '' /* 其他（长度/规模/范围等） */ ,
  // 10：水运、交通
  marineportThroughput: '' /* 港口（码头）吞吐量 */ ,
  marinechannelThroughput: '' /* 航道（闸机）通过量 */ ,
  marineflowDirection: '' /* 主要货物的流量流向 */ ,
  marinebranchLineVolume: '' /* 分航线运量 */ ,
  marinemarineshipFlow: '' /* 船舶流量 */ ,
  marinegatheringAndDistributingWays: '' /* 集疏方式 */ ,
  // 11：其他
  others: '' /* 其他 */ ,
  investment: '' /* 投资金额 */ ,
  investmentTentative: false /*投资金额暂定*/ ,
  nature: [] /* 项目性质 */ ,
  consignor: '' /* 委托人 */ ,
  clientNature: [] /* 委托人性质 */ ,
  encrusting: '' /* 委托方负责人 */ ,
  principalTel: '' /* 委托方负责人电话 */ ,
  consultant: '' /* 咨询人 */ ,
  third: '' /* 第三方（合作单位） */ ,
  titlePageConsultant: '' /* 扉页咨询人 */ ,
  principal: '' /* 拟定合同负责人 */ ,
  formulate: '' /* 拟定人 */ ,
  character: [] /* 合同性质 */ ,
  confirmation: [] /*确定方式 */ ,
  contractAmount: '' /* 合同拟定金额 */ ,
  contractAmountding: false /* 合同拟定金额 暂定*/ ,
  Pickertext: '请选择' /* 咨询合同签订日期 */ ,
  list: [] /*预计收款时间  */ ,
  conditions: '' /* 其他情况 */ ,
  contractType: '请选择' /*,, 合同类型 */ ,
  contractFile: '', // 合同文件
  templateContract: '0' /* 模板合同类型 */ ,
};

// 造价咨询合同
const formManufacturingCost = {
  costProvince: '' /* 省份 */ ,
  costRegion: '' /* 地区 */ ,
  projectscale: '' /* 工程规模 */ ,
  capitalsource: '' /* 资金来源 */ ,
  Constructionperiod: '' /* 建设工期或周期 */ ,
  ServiceParties: '' /* 双方约定的服务范围及工作内容 */ ,
  viabilityStart: '请选择' /* 服务期限-开始日期 */ ,
  viabilityEnd: '请选择' /* 服务期限-结束日期 */ ,
  Totalcontractcopies: '' /* 总合同份数*/ ,
  Numbercopiescontract: '' /* 委托人合同份数 */ ,
  Consultantcontractcopies: '' /* 咨询人合同份数 */ ,
  Principalrepresentative: '' /* 委托人代表 */ ,
  teammate: '' /* 团队人员 */ ,
  payment: '请选择' /*,, 支付方式 */ ,
  Consultation: '' /* 咨询成果报告出具几日内支付 */ ,
  StagingInformation: '' /* 分期信息 */ ,
  disputeResolution: '' /* 争议解决 */ ,
  ServiceReceiver: '' /* 送达接收人 */ ,
  site: '' /* 送达地点 */ ,
  remunerationting: '' /* 酬金及计取方式为 */ ,
  services: '', // 服务阶段
  jobContent: [], // 服务阶段工作内容
  servicesrange: '', // 服务范围
  remuneration: [] /* 工作内容，酬金 */ ,
  submitResults: [] /* 咨询人提交成果 */ ,
  provided: [] /*, 委托人提供资料 */ ,
};
// 建设工程招标代理合同
const formConstruction = {
  projectProvince: '' /* 省份 */ ,
  projectRegion: '' /* 地区 */ ,
  projectScale: '' /* 规模*/ ,
  biddingScale: '' /* 招标规模 */ ,
  WorkContentAgency: '' /* 代理工作内容 */ ,
  PlaceContract: '' /* 合同订立地点 */ ,
  ScopeAgent: '' /* 代理范围 */ ,
  agentContent: '请选择' /* 代理内容 */ ,
  agencyRemuneration: '请选择' /* 代理报酬的支付方式 */
};
// 工程管理咨询服务合同
const formEngineering = {
  manageProvince: '' /* 省份 */ ,
  manageRegion: '' /* 地区 */ ,
  manageServiceContent: '' /* 服务内容 */ ,
  AddressConsultingEngineer: '' /* 咨询工程师地址 */ ,
  phoneNumber: '' /* 电话号码 */ ,
  faxNO: '' /* 传真号码 */ ,
  ProjectFoorArea: '' /* 项目建筑面积 */ ,
  NatureProjectUse: '' /* 项目使用性质 */ ,
  DesignServiceCompensation: '' /* 设计阶段服务报酬 */ ,
  DesignServiceCompensationIng: false /* 设计阶段服务报酬暂定 */ ,
  PaymentConstructionStage: '' /* 施工阶段服务报酬 */ ,
  PaymentConstructionStageIng: false /* 施工阶段服务报酬暂定 */ ,
  SettlementServiceFee: '' /* 结算阶段服务报酬 */ ,
  SettlementServiceFeeIng: false /* 结算阶段服务报酬暂定 */ ,
  advance: '' /* 预付款 */ ,
  advanceIng: false /* 预付款暂定 */ ,
  interimPayment: '' /* 进度款 */ ,
  interimPaymentIng: false /* 进度款暂定 */ ,
  spareMoney: '' /* 余款 */ ,
  spareMoneyIng: false /* 余款暂定 */ ,
  additional: '' /* 附加款项 */
};
// 监理合同
const formSupervision = {
  supervisor: '' /* 监理人 */ ,
  projectScale: '' /* 工程规模 */ ,
  InstallationCost: '' /* 工程概算投资额或建筑安装工程费 */ ,
  InstallationCostIng: false /* 工程概算投资额或建筑安装工程费 暂定*/ ,
  chiefSupervisionEngineer: '' /* 总监理工程师姓名 */ ,
  IDcardNo: '' /* 身份证号码 */ ,
  registrationNumber: '' /* 注册号 */ ,
  reimbursementExpenses: '' /* 签约酬金与补偿费用 */ ,
  reimbursementExpensesIng: false /* 签约酬金与补偿费用暂定 */ ,
  SupervisionStart: '请选择' /* 监理期限开始时间 */ ,
  SupervisionEnd: '请选择' /* 监理期限结束时间 */ ,
  Conclusion: '请选择' /*, 订立时间 */ ,
  enterPlace: '' /* 订立地点 */ ,
  entrustBusinessLicenseNumber: '' /* 营业执照号 */ ,
  entrustdomicile: '' /* 住所 */ ,
  entrustpostalCode: '' /* 邮政编码 */ ,
  deposit: '' /* 开户银行 */ ,
  accountNumber: '' /* 账号 */ ,
  phone: '' /* 电话 */ ,
  fax: '' /* 传真 */ ,
  emailAddress: '' /* 电子邮箱 */ ,
  businessLicenseNumber: '' /* 营业执照号 */ ,
  QualificationCertificate: '' /* 资质证书编号 */ ,
  domicile: '' /* 住所 */ ,
  postalCode: '' /* 邮政编码 */ ,
  depositBank: '' /* 开户银行 */ ,
  legalaccountNumber: '' /* 账号 */ ,
  legaltel: '' /* 电话 */ ,
  legalfax: '' /* 传真 */ ,
  legalemail: '' /* 电子邮箱 */ ,
  legalexclusiveTerm: '' /* 专用条款 */ ,
  legalPrincipalrepresentative: '' /* 委托人代表 */ ,
  legalsupervisionFee: '' /* 监理酬金具体支付方式 */ ,
  legaldispute: '' /* 争议解决 */
};
export default {
  consultancy,
  consultancyStage,
  residential,
  contractType,
  payment,
  agentContent,
  agencyRemuneration,
  servicesrange,
  nature,
  clientNature,
  classification,
  character,
  confirmation,
  services,
  jobContent1,
  jobContent2,
  jobDuties,
  form, // 基础表单
  formManufacturingCost, // 造价咨询合同
  formConstruction, // 建设工程招标代理合同
  formEngineering, // 工程管理咨询服务合同
  formSupervision, // 监理合同
  templatelist // 模板列表
};
