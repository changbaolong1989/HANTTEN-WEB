export default {
  form: {
    projectName: "",// 项目名称
    projectNum: "",// 项目编号
    purchaser: "",// 采购人
    agentOrg: "",// 采购代理机构
    purchaserType: "",// 采购类别
    expertOpinion: "",// 专家论证意见
    expertSign: "",// 论证专家签字
    proveTime: "",// 论证时间
    remark: "",// 备注
  },
  rules: {
    purchaser:[{
      required: true,
      message: "请输入采购人",
      trigger: "blur"
    }],
    agentOrg:[{
      required: true,
      message: "请输入采购代理机构",
      trigger: "blur"
    }],
    purchaserType:[{
      required: true,
      message: "请输入采购类别",
      trigger: "blur"
    }],
    expertOpinion:[{
      required: true,
      message: "请输入专家论证意见",
      trigger: "blur"
    }],
    expertSign:[{
      required: true,
      message: "请输入论证专家签字",
      trigger: "blur"
    }],
    proveTime:[{
      required: true,
      message: "请输入论证时间",
      trigger: "blur"
    }],
    remark:[{
      required: true,
      message: "请输入备注",
      trigger: "blur"
    }]
  }
}
