<template>
  <!-- 任务详情 -->
  <div class="details">
    <ul class="contentlist">
      <li>
        <span>项目编号：</span>
        <span>{{currentTransmitData.projectNum}}</span>
      </li>
      <li>
        <span>合同编号：</span>
        <span>{{currentTransmitData.contractNumber}}</span>
      </li>
      <li>
        <span>任务名称：</span>
        <span>{{currentTransmitData.label}}</span>
      </li>
      <li>
        <span>创建时间：</span>
        <span>{{currentTransmitData.createDate}}</span>
      </li>
    </ul>
    <ul class="contentlist">
      <li>
        <span>任务说明：</span>
        <span>{{currentTransmitData.taskDesc}}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy) //传输数据
    };
  },
  props: {
    transmitData: Object
  },
  watch: {
    transmitData: {
      handler() {
        let vm = this;
        vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
        console.log('数据', this.currentTransmitData);
      },
      immediate: true
    }
  }
};
</script>

<style scoped="scoped" lang="scss">
.details {
  .contentlist {
    background-color: #fff !important;
    p {
      border-bottom: 2px solid #e5e5e5;
      padding: 30px 30px;
      font-size: 28px;
      color: #666;
    }
  }
}
</style>
