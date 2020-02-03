export default {
  indexContract:{// 合同的index页
          // 模板合同还是普通合同
          contractType: "0",
          // 模板合同
          formworkType: "",
          noticeName: "",
          releaseDate: "",
          userName: "",
          signPathFile: "",
          materialName: "",
          // 表单
          input: '',
          textarea: '',
          checkbox: [],
          checkboxC: [],
          select: '',
          date: ''
  },
  baseContract: { // 基础合同数据
    // 项目名称
    projectName: "",
    // 项目咨询类型
    counselBusinessType: "",
    // 项目咨询类型阶段
    counselTypePeriod: "",
    // 合同盖章类型
    contractSealType: "",
    // 项目类别
    projectTypeList: [],
    // 住宅
    residential: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 檐高
      eavesHeight: "",
      // 建筑控制高度
      buildingControlHeight: "",
      // 容积率
      volumeRatio: "",
      // 住宅类别
      homeType: ""
    },
    // 商业建筑
    commercialBuildings: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 檐高
      eavesHeight: "",
      // 建筑控制高度
      buildingControlHeight: ""
    },
    // 酒店及休闲娱乐
    hotelAndLeisureEntertainment: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 檐高
      eavesHeight: "",
      // 建筑控制高度
      buildingControlHeight: "",
      // 房间数量
      roomNum: ""
    },
    // 工业建筑
    industrialArchitecture: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 檐高
      eavesHeight: "",
      // 层数
      level: "",
      // 跨度
      span: "",
      // 生产状况
      produceCondition: ""
    },
    // 教育
    education: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 绿化率
      makeGreenRatio: "",
      // 绿色等级
      greenLevel: ""
    },
    // 文化及体育设施
    culturalAndSportsFacilities: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 绿化率
      makeGreenRatio: "",
      // 绿色等级
      greenLevel: ""
    },
    // 电力
    power: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 装机容量
      installationCapacity: "",
      // 发电机变压器参数数量
      transformersNum: "",
      // 线缆敷设长度
      cableLayingLength: "",
      // 线缆敷设方式
      cableLayingMethod: "",
      // 输电量
      transmissionCapacity: ""
    },
    // 铁路、道路及桥梁建设
    railwayRoadBridgeConstruction: {
      // 等级
      grade: "",
      // 全线（桥）长度
      bridgeLength: "",
      // 设计速度
      designSpeed: "",
      // 轨道类型
      orbitalType: "",
      // 桥梁类型
      bridgeType: ""
    },
    // 水务
    water: {
      // 正常运用水位
      normalOperatingWaterLevel: "",
      // 总库容
      totalStorageCapacity: "",
      // 最大坝高
      maximumDamHeight: "",
      // 电站装机容量
      installedCapacityOfPowerStation: ""
    },
    // 公用事业及城市基础设施
    publicUtilitiesUrbanInfrastructure: {
      // 占地面积
      landArea: "",
      // 建筑面积
      floorArea: "",
      // 其他（长度/规模/范围等）
      others: ""
    },
    // 水运、交通
    waterTransportationAndTransportation: {
      // 港口（码头）吞吐量
      portThroughput: "",
      // 航道（闸机）通过量
      channelThroughput: "",
      // 主要货物的流量流向
      flowDirection: "",
      // 分航线运量
      branchLineVolume: "",
      // 船舶流量
      shipFlow: "",
      // 集疏方式
      gatheringAndDistributingWays: ""
    },
    // 其他
    others: {
      // 其他
      others: ""
    },
    // 项目地址
    projectAddress: "",
    // 投资金额
    investedAmount: "",
    // 是否暂定
    isProvisional: "",
    // 项目性质
    projectNatureList: [],
    // 委托人
    trusterName: "",
    // 委托人性质
    trusterTypeList: [],
    // 委托方负责人
    trusterDutyUser: "",
    // 委托方负责人电话
    trusterDutyPhone: "",
    // 咨询人
    consultUser: "",
    // 第三方（合作单位）
    thirdParty: "",
    // 拟定合同负责人
    dutyPeople: "",
    // 拟定人
    planner: "",
    // 合同性质
    contractNatureList: [],
    // 确认方式
    determinateMethodList: [],
    // 拟定合同金额
    contractAmount: "",
    // 拟定合同金额是否暂定（0：否，1：是）
    isValueProvisional: "",
    // 咨询合同签订日期
    contractDate: "",
    // 其他情况
    otherThings: "",
    // 合作公司
    cooperateCompany: "",
    // 请款时间
    characteristic: [
      {
        startDate: '',
        receivedAmount: '',
        isProvisional:''
      }
    ],
    // 表单
    input: '',
    textarea: '',
    checkbox: [],
    select: '',
    date: '',
    radioC: '' ,// 模板合同还是普通合同
    // 模板合同还是普通合同
    contractType: "0",
    // 模板合同
    formworkType: "",
  },
  formCostConsulting: { // 造价合同
    province: '',
    region: "",
    generalize: "",
    fund: "",
    period: "",
    scope: "",
    deadline: "",
    copies: "",
    entrust: "",
    consult: "",
    representative: "",
    personnel: "",
    pay: "",
    dispute: "",
    team: "",
    recipient: "",
    site: "",
    reward: ""
  },
  formConstructionProjectBiddingAgency: { // 建设工程招标代理合同
    provinceName: '', // 省份
    districtName: '', // 地区
    scale: '', // 规模
    biddingScale: '', // 招标规模
    agentWorkContent: '', // 代理工作内容
    signAddress: '', // 合同订立地点
    agentRange: '', // 代理范围
    agentContent: '', // 代理内容
    paymentMethod: '', // 代理报酬的支付方式
  },
  formProjectManagementConsultingService: { // 工程管理咨询服务合同
    provinceName: '', // 省份
    districtName: '', // 地区
    serviceContent: '', // 服务内容
    consultAddress: '', // 咨询工程师的地址
    telNumber: '', // 电话号码
    faxNumber: '', // 传真号码
    floorage: '', // 项目建筑面积
    useNature: '', // 项目使用性质
    designPay: '', // 设计阶段服务报酬
    constructionPay: '', // 施工阶段服务报酬
    settlementPay: '', // 结算阶段服务报酬
    advancePayment: '', // 预付款
    progressPayment: '', // 进度款
    balance: '', // 余款
    isDesignPayProvisional: '', // 设计阶段服务报酬暂定
    isConstructionPayProvisional: '', // 施工阶段服务报酬暂定
    isSettlementPayProvisional: '', // 结算阶段服务报酬暂定
    isAdvancePaymentProvisional: '', // 预付款暂定
    isProgressPaymentProvisional: '', // 进度款暂定
    isBalanceProvisional: '', // 余款暂定
    addClauses: '', // 附加条款
  },
  formSupervision: { // 监理合同
    supervisor: '', //监理人
    projectScale: '', //工程规模
    instalCost: '', //工程概算投资额或建筑安装工程费
    isInstalCostProvisional: '', //工程概算投资额或建筑安装工程费暂定
    engineerName: '', //姓名：3（汉字）
    IDNumber: '', //身份证号：18（数字）
    registrationNumber: '', //注册号
    signAmount: '', //请输入签约酬金与补偿费用
    isSignAmountProvisional: '', //请输入签约酬金与补偿费用 暂定
    superviseDate: '', //监理期限
    startYear: '',
    startMonth: '',
    startDay: '',
    endYear: '',
    endMonth: '',
    endDay: '',
    signDate: '', //订立时间
    signYear: '',
    signMonth: '',
    signDay: '',
    signAddress: '', //订立地点
    truBusinessLicenseNumber: '', //营业执照号
    truAddress: '', //住所
    truPostalCode: '', //邮政编码
    truOpenBank: '', //开户银行
    truAcountNumber: '', //账号
    truTelNumber: '', //电话
    truFaxNumber: '', //传真
    truEMail: '', //电子邮箱
    supBusinessLicenseNumber: '', //营业执照号
    supCertificateNumber: '', //资质证书编号
    supAddress: '', //住所
    supPostalCode: '', //邮政编码
    supOpenBank: '', //开户银行
    supAcountNumber: '', //账号
    supTelNumber: '', //电话
    supFaxNumber: '', //传真
    supEMail: '', //电子邮箱
    specialClauses: '', //专用条款
    trusterAgent: '', //委托人代表为
    paymentMethod: '', //监理酬金具体支付方式
    disputeResolution: '', //争议解决
  }
}
  let vm = this;
      // 项目名称
      // 咨询业务类型
      form.counselBusinessType = form.consultancy;
      delete form.consultancy;
      // 咨询类型阶段
      form.counselTypePeriod = form.consultancyStage;
      delete form.consultancyStage;
      // 项目地点
      form.projectAddress = form.location;
      delete form.location;
      // 项目类别
      form.projectTypeList = form.classification;
      delete form.classification;
      // 项目类别:0住宅
      // 原住宅类型
      form.residential2 = form.residential;
      form.residential = {};
      // 占地面积
      form.residential.landArea = form.landArea;
      delete form.landArea;
      // 建筑面积
      form.residential.floorArea = form.floorArea;
      delete form.floorArea;
      // 檐高
      form.residential.eavesHeight = form.eavesHeight;
      delete form.eavesHeight;
      // 建筑控制高度
      form.residential.buildingControlHeight = form.buildingControlHeight;
      delete form.buildingControlHeight;
      // 容积率
      form.residential.volumeRatio = form.volumeRatio;
      delete form.volumeRatio;
      // 住宅类别
      form.residential.homeType = form.residential2;
      delete form.residential2;
      // 项目类别:1商业建筑
      form.commercialBuildings = {};
      // 占地面积
      form.commercialBuildings.landArea = form.businesslandArea;
      delete form.businesslandArea;
      // 建筑面积
      form.commercialBuildings.floorArea = form.businessfloorArea;
      delete form.businessfloorArea;
      // 檐高
      form.commercialBuildings.eavesHeight = form.businesseavesHeight;
      delete form.businesseavesHeight;
      // 建筑控制高度
      form.commercialBuildings.buildingControlHeight = form.businessbuildingControlHeight;
      delete form.businessbuildingControlHeight;
      // 项目类别:2酒店及休闲娱乐
      form.hotelAndLeisureEntertainment = {};
      // 占地面积
      form.hotelAndLeisureEntertainment.landArea = form.hotellandArea;
      delete form.hotellandArea;
      // 建筑面积
      form.hotelAndLeisureEntertainment.floorArea = form.hotelfloorArea;
      delete form.hotelfloorArea;
      // 檐高
      form.hotelAndLeisureEntertainment.eavesHeight = form.hoteleavesHeight;
      delete form.hoteleavesHeight;
      // 建筑控制高度
      form.hotelAndLeisureEntertainment.buildingControlHeight = form.hotelbuildingControlHeight;
      delete form.hotelbuildingControlHeight;
      // 房间数量
      form.hotelAndLeisureEntertainment.roomNum = form.hotelroomNum;
      delete form.hotelroomNum;
      // 项目类别:3工业建筑
      form.industrialArchitecture = {};
      // 占地面积
      form.industrialArchitecture.landArea = form.engineeringlandArea;
      delete form.engineeringlandArea;
      // 建筑面积
      form.industrialArchitecture.floorArea = form.engineeringfloorArea;
      delete form.engineeringfloorArea;
      // 檐高
      form.industrialArchitecture.eavesHeight = form.engineeringeavesHeight;
      delete form.engineeringeavesHeight;
      // 层数
      form.industrialArchitecture.level = form.engineeringlevel;
      delete form.engineeringlevel;
      // 跨度
      form.industrialArchitecture.span = form.engineeringspan;
      delete form.engineeringspan;
      // 生产状况
      form.industrialArchitecture.produceCondition = form.engineeringproduceCondition;
      delete form.engineeringproduceCondition;
      // 项目类别:4教育
      form.education = {};
      // 占地面积
      form.education.landArea = form.educationlandArea;
      delete form.educationlandArea;
      // 建筑面积
      form.education.floorArea = form.educationeducationfloorArea;
      delete form.educationeducationfloorArea;
      // 绿化率
      form.education.makeGreenRatio = form.educationmakeGreenRatio;
      delete form.educationmakeGreenRatio;
      // 绿色等级
      form.education.greenLevel = form.educationgreenLevel;
      delete form.educationgreenLevel;
      // 项目类别:5文化及体育设施
      form.culturalAndSportsFacilities = {};
      // 占地面积
      form.culturalAndSportsFacilities.landArea = form.culturelandArea;
      delete form.culturelandArea;
      // 建筑面积
      form.culturalAndSportsFacilities.floorArea = form.culturefloorArea;
      delete form.culturefloorArea;
      // 人容量
      form.culturalAndSportsFacilities.persionCapacity = form.culturemakeGreenRatio;
      delete form.culturemakeGreenRatio;
      // 项目类别:6电力
      form.power = {};
      // 占地面积
      form.power.landArea = form.electrilandArea;
      delete form.electrilandArea;
      // 建筑面积
      form.power.floorArea = form.electrifloorArea;
      delete form.electrifloorArea;
      // 装机容量
      form.power.installationCapacity = form.electriinstallationCapacity;
      delete form.electriinstallationCapacity;
      // 发电机变压器参数数量
      form.power.transformersNum = form.electritransformersNum;
      delete form.electritransformersNum;
      // 线缆敷设长度
      form.power.cableLayingLength = form.electricableLayingLength;
      delete form.electricableLayingLength;
      // 线缆敷设方式
      form.power.cableLayingMethod = form.electricableLayingMethod;
      delete form.electricableLayingMethod;
      // 输电量
      form.power.transmissionCapacity = form.electritransmissionCapacity;
      delete form.electritransmissionCapacity;
      // 项目类别:7铁路、道路及桥梁建设
      form.railwayRoadBridgeConstruction = {};
      // 等级
      form.railwayRoadBridgeConstruction.grade = form.railwaygrade;
      delete form.railwaygrade;
      // 全线（桥）长度
      form.railwayRoadBridgeConstruction.bridgeLength = form.railwaybridgeLength;
      delete form.railwaybridgeLength;
      // 设计速度
      form.railwayRoadBridgeConstruction.designSpeed = form.railwaydesignSpeed;
      delete form.railwaydesignSpeed;
      // 轨道类型
      form.railwayRoadBridgeConstruction.orbitalType = form.railwayOrbitalType;
      delete form.railwayOrbitalType;
      // 桥梁类型
      form.railwayRoadBridgeConstruction.bridgeType = form.railwaybridgeType;
      delete form.railwaybridgeType;
      // 项目类别:8水务
      form.water = {};
      // 正常运用水位
      form.water.normalOperatingWaterLevel = form.waternormalOperatingWaterLevel;
      delete form.waternormalOperatingWaterLevel;
      // 总库容
      form.water.totalStorageCapacity = form.watertotalStorageCapacity;
      delete form.watertotalStorageCapacity;
      // 最大坝高
      form.water.maximumDamHeight = form.watermaximumDamHeight;
      delete form.watermaximumDamHeight;
      // 电站装机容量
      form.water.installedCapacityOfPowerStation = form.waterinstalledCapacityOfPowerStation;
      delete form.waterinstalledCapacityOfPowerStation;
      // 项目类别:9公用事业及城市基础设施
      form.publicUtilitiesUrbanInfrastructure = {};
      // 占地面积
      form.publicUtilitiesUrbanInfrastructure.landArea = form.publiclandArea;
      delete form.publiclandArea;
      // 建筑面积
      form.publicUtilitiesUrbanInfrastructure.floorArea = form.publicfloorArea;
      delete form.publicfloorArea;
      // 其他（长度/规模/范围等）
      form.publicUtilitiesUrbanInfrastructure.others = form.publicothers;
      delete form.publicothers;
      // 项目类别:10水运、交通
      form.waterTransportationAndTransportation = {};
      // 港口（码头）吞吐量
      form.waterTransportationAndTransportation.portThroughput = form.marineportThroughput;
      delete form.marineportThroughput;
      // 航道（闸机）通过量
      form.waterTransportationAndTransportation.channelThroughput = form.marinechannelThroughput;
      delete form.marinechannelThroughput;
      // 主要货物的流量流向
      form.waterTransportationAndTransportation.flowDirection = form.marineflowDirection;
      delete form.marineflowDirection;
      // 分航线运量
      form.waterTransportationAndTransportation.branchLineVolume = form.marinebranchLineVolume;
      delete form.marinebranchLineVolume;
      // 船舶流量
      form.waterTransportationAndTransportation.shipFlow = form.marinemarineshipFlow;
      delete form.marinemarineshipFlow;
      // 集疏方式
      form.waterTransportationAndTransportation.gatheringAndDistributingWays = form.marinegatheringAndDistributingWays;
      delete form.marinegatheringAndDistributingWays;
      // 项目类别:11其他
      form.others2 = form.others;
      form.others = {};
      // 港口（码头）吞吐量
      form.others.others = form.others2;
      delete form.others2;
      //--------------------------------------------------
      // 投资金额
      form.investedAmount = form.investment;
      delete form.investment;
      // 投资金额暂定
      form.isProvisional = form.investmentTentative?'1':'';
      delete form.investmentTentative;
      // 项目性质
      form.projectNatureList = form.nature;
      delete form.nature;
      // 委托人
      form.trusterName = form.consignor;
      delete form.consignor;
      // 委托人性质
      form.trusterTypeList = form.clientNature;
      delete form.clientNature;
      // 委托方负责人
      form.trusterDutyUser = form.encrusting;
      delete form.encrusting;
      // 委托方负责人电话
      form.trusterDutyPhone = form.principalTel;
      delete form.principalTel;
      // 咨询人
      form.consultUser = form.consultant;
      delete form.consultant;
      // 第三方（合作单位）
      form.thirdParty = form.third;
      delete form.third;
      // 扉页咨询人
      // 拟定合同负责人
      form.dutyPeople = form.principal;
      delete form.principal;
      // 拟定人
      form.planner = form.formulate;
      delete form.formulate;
      // 合同性质
      form.contractNatureList = form.character;
      delete form.character;
      // 确定方式
      form.determinateMethodList = form.confirmation;
      delete form.confirmation;
      // 合同拟定金额
      // 合同拟定金额 暂定
      form.isValueProvisional = form.contractAmountding?'1':'';
      delete form.contractAmountding;
      // 咨询合同签订日期
      form.contractDate = form.Pickertext;
      delete form.Pickertext;
      // 预计收款时间
      form.characteristic = form.list;
      delete form.list;
      for (let i = 0; i < form.characteristic.length; i++) {
        // 收款时间
        form.characteristic[i].startDate = new Date(form.characteristic[i].completionText);
        delete form.characteristic[i].completionText;
        // 收款金额
        form.characteristic[i].receivedAmount = form.characteristic[i].receivableAmount;
        delete form.characteristic[i].receivableAmount;
        // 收款金额暂定
        form.characteristic[i].isProvisional = form.characteristic[i].receivableAmounting?'1':'';
        delete form.characteristic[i].receivableAmounting;
      }
      // 其他情况
      form.otherThings = form.conditions;
      delete form.conditions;
      // 合同类型
      // 合同文件
      // 模板合同类型
