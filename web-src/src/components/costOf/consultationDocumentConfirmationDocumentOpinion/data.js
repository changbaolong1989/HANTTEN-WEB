export default {
  form: {
    projectName: "", // 项目名称
    projectNum: "", // 项目编号
    tenderSumLimit: "", // 招标控制价
    purchaser: "", // 采购人
    agentOrg: "", // 招标代理机构
    isAgree: "0", // 审核意见(0同意,1不同意)
    approveOpinion: "", // 修改意见
    editDate: "" // 日期
  },
  rules: {
    tenderSumLimit: [{
      validator: this.moneyCheck,
      trigger: "blur"
    }],
    purchaser: [{
      required: true,
      message: "请输入采购人",
      trigger: "blur"
    }],
    agentOrg: [{
      required: true,
      message: "请输入招标代理机构",
      trigger: "blur"
    }],
    isAgree: [{
      required: true,
      message: "请选择审核意见",
      trigger: "blur"
    }],
    approveOpinion: [{
      required: true,
      message: "请输入修改意见",
      trigger: "blur"
    }],
    editDate: [{
      required: true,
      message: "请输入日期",
      trigger: "blur"
    }]
  }
}
