<template>
  <!-- A2-->
  <div class="A2">
    <p class="Atitle">工程咨询业务备案表</p>
    <ul class="contentlist" >
      <li>
        <span>合同编号</span>
        <span>{{ tableData.contractNum }}</span>
      </li>
      <li>
        <span>合同签订时间</span>
        <span>{{ tableData.contractDate }}</span>
      </li>
      <li>
        <span>项目名称</span>
        <span>{{ tableData.projectName }}</span>
      </li>
      <li>
        <span>项目类别</span>
        <span>{{ tableData.counselBusinessType }}</span>
      </li>
      <li>
        <span>成果文件编号</span>
        <span>{{ tableData.reportNum }}</span>
      </li>
      <li>
        <span>项目委托单位</span>
        <span>{{ tableData.trusterName }}</span>
      </li>
      <li>
        <span>项目概况</span>
        <span>{{ tableData.projectCondition }}</span>
      </li>
      <li>
        <span>发包人</span>
        <span>{{ tableData.employer }}</span>
      </li>
      <li>
        <span>项目中标人</span>
        <span>{{ tableData.bidPerson }}</span>
      </li>
      <li>
        <span>计划完成时间</span>
        <span>{{ tableData.planStartDate}}-{{tableData.planEndDate}}</span>
      </li>
      <li>
        <span>实际完成时间</span>
        <span>{{tableData.actualStartDate}} - {{tableData.actualEndDate}}</span>
      </li>
      <li>
        <span>延迟原因</span>
        <span>{{ tableData.delayReason }}</span>
      </li>
      <li>
        <span>咨询主要结论</span>
        <span>{{ tableData.mainResult }}</span>
      </li>
      <li>
        <span>咨询人签名</span>
        <span></span>
      </li>
      <li>
        <span>咨询人签名时间</span>
        <span></span>
      </li>
      <li>
        <span>项目负责人签名</span>
        <span>{{ tableData.dutyPeople }}</span>
      </li>
      <li>
        <span>项目负责人签名时间</span>
        <span>{{ tableData.approveTime }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
     tableData: {},
    };
  },
  props: ['transmittedData'],
  mounted() {
    this.getCompileResultsFile();
  },
  methods: {
    //查询编制成果文件-A2信息
    getCompileResultsFile() {
      const vm = this;
      console.log(vm.transmittedData.relationId);
      this.requestPost('/task/query/searchTaskCompileResultsFileA2', {
        relationId: vm.transmittedData.relationId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            vm.tableData = res.data.dataVO;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    }
  }
};
</script>

<style lang="scss">
.A2 {
  width: 100%;
  .contentlist {
    background-color: #fff !important;
  }
}
</style>
