<template>
  <!-- 招标部A7 -->
  <div class="tenderingA7">
    <p class="Atitle">招标工作进度计划（勘察、设计）</p>
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ form.projectName}}</span>
      </li>
      <li>
        <span>资料最后上传时间</span>
        <span>{{ (form.startDate || '') + ' - ' + (form.endDate || '')}}</span>
      </li>
    </ul>
    <ul v-for="(i, j) in tableData" class="contentlist" :key="'list' + j">
      <li>
        <span>招标类型</span>
        <span>{{ i.tenderTypeContent }}</span>
      </li>
      <li>
        <span>序号</span>
        <span>{{ i.sortNum }}</span>
      </li>
      <li>
        <span>工作流程</span>
        <span>{{ i.workflowName }}</span>
      </li>
      <li>
        <span>节点</span>
        <span>{{ i.startDate}} -- {{i.endDate}}</span>
      </li>
      <li>
        <span>时间</span>
        <span>{{ i.days }}</span>
      </li>
      <li>
        <span>涉及部门</span>
        <span>{{ i.involvedDepartment }}</span>
      </li>
      <li>
        <span>备注</span>
        <span>{{ i.remark }}</span>
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data() {
    return {
      form: {},
      tableData: [],
    };
  },
  props:['transmittedData'],
  mounted() {
    this.searchList();
  },
  methods: {
    searchList() {
      let vm = this;
      vm.requestPost('/taskExecute/query/searchA7ZbdlInfo', {
        relationId: this.transmittedData.relationId,
        taskId: this.transmittedData.taskId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            console.log("res",res)
            vm.form.projectName = res.data.dataVO.projectName;
            vm.form.endDate = res.data.dataVO.endDate;
            vm.form.startDate = res.data.dataVO.startDate;
            vm.tableData = res.data.dataVO.showList;
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    }
  }
};
</script>