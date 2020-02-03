<template>
  <div class="tenderingA8">
    <p class="Atitle">咨询工作记录</p>
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ form.projectName }}</span>
      </li>
      <li>
        <span>达成意见一致时间</span>
        <span>{{ form.consensusTime }}</span>
      </li>
    </ul>
    <ul v-for="(i, j) in tableData" class="contentlist" :key="'list' + j">
      <li>
        <span>序号</span>
        <span>{{ i.sortNum }}</span>
      </li>
      <li>
        <span>咨询意见</span>
        <span>{{ i.counselAdvise }}</span>
      </li>
      <li>
        <span>委托方回复意见</span>
        <span>{{ i.answerAdvise }}</span>
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [],
      form: {}
    };
  },
  props:['transmittedData'],
  mounted() {
    this.searchList();
  },
  methods: {
    searchList() {
      let vm = this;
      this.requestPost('/taskExecute/query/searchA8Info', {
        relationId: this.transmittedData.relationId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            this.form = vm.tool.deepCopy(res.data.dataVO, vm.tool.deepCopy);
            this.tableData = vm.tool.deepCopy(res.data.dataVO.opinionList, vm.tool.deepCopy);
          }
        })
        .catch(e => {
           this.Toast(e);
        });
    }
  }
};
</script>