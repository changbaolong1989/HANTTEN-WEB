<template>
  <!-- 磋商文件确认文件意见 -->
  <div class="budgetReviewComments">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" v-model="templateform.projectName" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>项目编号</label>
          <input type="text" v-model="templateform.projectNum" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>招标控制价</label>
          <input type="text" v-model="templateform.tenderSumLimit" :disabled="disabled" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>采购人</label>
          <input type="text" v-model="templateform.purchaser" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>招标代理机构</label>
          <input type="text" v-model="templateform.agentOrg" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>审核意见</label>
          <input type="text" v-model="templateform.isAgree=='0'?'同意':templateform.isAgree=='1'?'不同意':''" :disabled="disabled" />
        </div>
        <div class="form-item" v-if="templateform.isAgree=='1'">
          <label>修改意见</label>
          <input type="text" v-model="templateform.approveOpinion" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>日期</label>
          <input type="text" v-model="templateform.editDate" :disabled="disabled" />
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      templateform: {
        agentOrg: "",
        approveOpinion: "",
        editDate: "",
        isAgree:'',
        projectName: "",
        projectNum: "",
        purchaser: "",
        taskId: "",
        tenderSumLimit: "",
      },
      disabled: true
    };
  },
  props: { data: Object },
  watch: {
    data: {
      handler: function(val, oldVal) {
        this.templateform = Object.assign({},this.templateform,val.form);
      },
      immediate: true,
      deep: true
    }
  },
  methods: {}
};
</script>
