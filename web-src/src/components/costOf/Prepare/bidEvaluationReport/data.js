export default {
  form: {
    projectName: "", // 项目名称
    purchaser: "", // 采购人
    purchasingAgency: "", // 采购代理机构
    biddingDescription: "", // 招标描述
    firstItemContent: "", // 内容(第一项)
    secondSectionContent: "", // 内容(第二项)
    establishingABidEvaluationCommittee: "", // 组建评标委员会
    familiarWithBiddingDocuments: "", // 熟悉招标文件
    review: "", // 评审
    bidEvaluationResult: "", // 评标结果
    bidderList: [{ // 投标人列表
      bidderName: "", // 投标人名称
      rankingOrder: "", // 排名次序
      bidEvaluation: "" // 评标得分
    }],
    recommendedCompanyName: "", // 推荐公司名称
    companyName: "", // 公司名称
    date: "" // 日期
  },
  rules: {
    purchaser: [{
      required: true,
      message: "请输入采购人",
      trigger: "blur"
    }],
    purchasingAgency: [{
      required: true,
      message: "请输入采购代理机构",
      trigger: "blur"
    }],
    biddingDescription: [{
      required: true,
      message: "请输入招标描述",
      trigger: "blur"
    }],
    firstItemContent: [{
      required: true,
      message: "请输入内容",
      trigger: "blur"
    }],
    secondSectionContent: [{
      required: true,
      message: "请输入内容",
      trigger: "blur"
    }],
    establishingABidEvaluationCommittee: [{
      required: true,
      message: "请输入组建评标委员会",
      trigger: "blur"
    }],
    familiarWithBiddingDocuments: [{
      required: true,
      message: "请输入熟悉招标文件",
      trigger: "blur"
    }],
    review: [{
      required: true,
      message: "请输入评审",
      trigger: "blur"
    }],
    bidEvaluationResult: [{
      required: true,
      message: "请输入评标结果",
      trigger: "blur"
    }],
    recommendedCompanyName: [{
      required: true,
      message: "请输入推荐公司名称",
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
