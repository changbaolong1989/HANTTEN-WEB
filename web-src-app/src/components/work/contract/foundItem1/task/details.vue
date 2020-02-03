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
      <li>
        <span>合同类型：</span>
        <span>{{currentTransmitData.contractTypeName}}</span>
      </li>
      <li>
        <span>咨询类别：</span>
        <span>{{currentTransmitData.counselTypeName}}</span>
      </li>
    </ul>
    <ul class="contentlist" v-if="currentTransmitData.taskDesc">
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
    };
  },
  props: {
    currentTransmitData: Object
  },
  watch: {
    currentTransmitData: {
      handler(newval) {
        let vm = this;
        vm.currentTransmitData = newval;
      },
      immediate: true
    }
  },
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
