export default {
  form: {
    bidTime: "",// 开标时间
    evaluationTime: "",// 评标时间
  },
  rules: {
    bidTime:[{
      // required: true,
      message: "请选择开标时间",
      trigger: "change"
    }],
    evaluationTime:[{
      // required: true,
      message: "请选择评标时间",
      trigger: "change"
    }],
  }
}
