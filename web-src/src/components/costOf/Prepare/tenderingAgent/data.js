export default {
  form: {
    projectName: "", // 项目名称
    contractNumber: "", // 合同编号
    reportNo: "", // 报告编号
    biddingProjectName: "", // 招标项目名称
    tendereeName: "", // 招标人名称
    tenderAgencyName: "", // 招标代理机构名称
    tenderAgencyAddress: "", // 招标代理机构地址
    tenderScope: "", // 招标范围
    tenderOrganizationContent: "", // 内容(招标项目概况)
    tenderMethodContent: "", // 内容(招标方式)
    tenderNotice: "", // 招标公告
    biddingRegistration: "", // 投标报名
    biddingDocumentsForSale: "", // 招标文件发售
    siteSurvey: "", // 现场踏勘
    establishmentOfTheBidEvaluationCommittee: "", // 评标委员会组建
    bidOpeningDocument: "", // 开标组织文件
    bidEvaluationProcess: "", // 评标过程
    bidderList: [{ // 投标人列表
      bidderName: "", // 投标人名称
      rankingOrder: "" ,// 排名次序
      bidEvaluation: "" // 评标得分
    }],
    successfulBidding: "", // 中标公示
    winningResult: "", // 中标结果
    signAContract: "", // 合同签订
    companyName: "", // 公司名称
    date: "", // 日期
  },
  rules: {
    reportNo: [{
      required: true,
      message: "请输入报告编号",
      trigger: "blur"
    }],
    biddingProjectName: [{
      required: true,
      message: "请输入招标项目名称",
      trigger: "blur"
    }],
    tendereeName: [{
      required: true,
      message: "请输入招标人名称",
      trigger: "blur"
    }],
    tenderAgencyName: [{
      required: true,
      message: "请输入招标代理机构名称",
      trigger: "blur"
    }],
    tenderAgencyAddress: [{
      required: true,
      message: "请输入招标代理机构地址",
      trigger: "blur"
    }],
    tenderScope: [{
      required: true,
      message: "请输入招标范围",
      trigger: "blur"
    }],
    tenderOrganizationContent: [{
      required: true,
      message: "请输入内容",
      trigger: "blur"
    }],
    tenderMethodContent: [{
      required: true,
      message: "请输入内容",
      trigger: "blur"
    }],
    tenderNotice: [{
      required: true,
      message: "请输入招标公告",
      trigger: "blur"
    }],
    biddingRegistration: [{
      required: true,
      message: "请输入投标报名",
      trigger: "blur"
    }],
    biddingDocumentsForSale: [{
      required: true,
      message: "请输入招标文件发售",
      trigger: "blur"
    }],
    siteSurvey: [{
      required: true,
      message: "请输入现场踏勘",
      trigger: "blur"
    }],
    establishmentOfTheBidEvaluationCommittee: [{
      required: true,
      message: "请输入评标委员会组建",
      trigger: "blur"
    }],
    bidOpeningDocument: [{
      required: true,
      message: "请输入开标组织文件",
      trigger: "blur"
    }],
    bidEvaluationProcess: [{
      required: true,
      message: "请输入评标过程",
      trigger: "blur"
    }],
    successfulBidding: [{
      required: true,
      message: "请输入中标公示",
      trigger: "blur"
    }],
    winningResult: [{
      required: true,
      message: "请输入中标结果",
      trigger: "blur"
    }],
    signAContract: [{
      required: true,
      message: "请输入合同签订",
      trigger: "blur"
    }],
    companyName: [{
      required: true,
      message: "请输入公司名称",
      trigger: "blur"
    }],
    date: [{
      required: true,
      message: "请输入日期",
      trigger: "blur"
    }]
  }
}
