export default {
  baseContract: { // 基础合同数据
    // 项目名称
    projectName: "",
    // 项目咨询类型
    counselBusinessType: "",
    //合同盖章类型
    contractSealType:"1",
    // 项目咨询类型阶段
    counselTypePeriod: "",
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
    controlObj:{},// 控制对象(以后删除临时使用)
    contractType:'0'// 合同类型
  }
}
