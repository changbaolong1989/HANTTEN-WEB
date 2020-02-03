<template>
  <!-- 专家意见表 -->
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
          <label>采购代理机构</label>
          <input type="text" v-model="templateform.agentOrg" :disabled="disabled" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>采购人</label>
          <input type="text" v-model="templateform.purchaser" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>采购类别</label>
          <input type="text" v-model="templateform.purchaserType" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>专家论证意见</label>
          <textarea v-model="templateform.expertOpinion" :disabled="disabled"></textarea>
        </div>
        <div class="form-item">
          <label>论证专家签字</label>
          <input type="text" v-model="templateform.expertSign" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>论证时间</label>
          <input type="text" v-model="templateform.proveTime" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>备注</label>
          <textarea v-model="templateform.remark" :disabled="disabled"></textarea>
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
        agentOrg: '',
        expertOpinion: '',
        expertSign: '',
        projectName: '',
        projectNum: '',
        proveTime: '',
        purchaser: '',
        purchaserType: '',
        remark: '',
        taskId: ''
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
