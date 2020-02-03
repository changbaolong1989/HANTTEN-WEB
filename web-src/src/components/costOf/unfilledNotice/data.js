export default {
  form: {
    projectName: "", // 项目名称
    projectNum: "", // 项目编号
    tenderUnitName: "", // 投标单位名称
    bidderList: [{ // 投标人列表
      tenderUserName: "", // 投标人名称
      sort: "" ,// 排名次序
      bidScore: "" // 评标得分
    }],
    agentOrg: "", // 采购代理机构
    editDate: "", // 日期
  },
  rules: {
    tenderUnitName: [{
      required: true,
      message: "请输入投标单位名称",
      trigger: "blur"
    }],
    agentOrg: [{
      required: true,
      message: "请输入采购代理机构",
      trigger: "blur"
    }],
    editDate: [{
      required: true,
      message: "请输入日期",
      trigger: "blur"
    }]
  }
}
