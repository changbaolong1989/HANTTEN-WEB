<template>
  <!-- 未成交通知书 -->
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
          <label>投标单位名称</label>
          <input type="text" v-model="templateform.tenderUnitName" :disabled="disabled" />
        </div>
      </div>
      <div class="block" v-for="(item,index) in templateform.bidderList">
        <div class="form-item">
          <label>投标人名称</label>
          <input type="text" v-model="item.tenderUserName" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>排名次序</label>
          <input type="text" v-model="item.sort" :disabled="disabled" />
        </div>
        <div class="form-item">
          <label>评标得分</label>
          <input type="text" v-model="item.bidScore" :disabled="disabled" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>采购代理机构</label>
          <input type="text" v-model="templateform.agentOrg" :disabled="disabled" />
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
        bidderList: [],
        editDate: "",
        projectName: "",
        projectNum: "",
        taskId: "",
        tenderUnitName: "",
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
