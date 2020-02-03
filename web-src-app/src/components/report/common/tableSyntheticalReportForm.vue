<template>
  <div class="tableSyntheticalReportForm">
    <p class="contractNum">年度合同数:{{totle}}</p>
    <ul v-for="(item, index) in tabList" class="contentlist">
      <li>
        <span>时间</span>
        <span>{{ item.groupMonths }}</span>
      </li>
      <li>
        <span>新增合同数量</span>
        <span>{{ item.addContractCount }}</span>
      </li>
      <li>
        <span>年度累计新增合同数量</span>
        <span>{{ item.sumAddContractCount }}</span>
      </li>
      <li>
        <span>完成合同数量</span>
        <span>{{ item.doneContractCount }}</span>
      </li>
      <li>
        <span>年度累计完成合同数量</span>
        <span>{{ item.sumDoneContractCount }}</span>
      </li>
      <li>
        <span>请款金额</span>
        <span>{{ item.requestTotalAmount }}</span>
      </li>
      <li>
        <span>年度累计请款金额</span>
        <span>{{ item.sumRequestTotalAmount }}</span>
      </li>
      <li>
        <span>到款金额</span>
        <span>{{ item.paymentTotalAmount }}</span>
      </li>
      <li>
        <span>年度累计到款金额</span>
        <span>{{ item.sumPaymentTotalAmount }}</span>
      </li>
      <li>
        <span>未到金额</span>
        <span>{{ item.differenceValue }}</span>
      </li>
      <li>
        <span>年度累计未到金额</span>
        <span>{{ item.sumDifferenceValue }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tabList: [],
      totle:"",
    };
  },
  props: ['contentList'],
  watch: {
    contentList: function(newval, oldval) {
      this.tabList = newval;
      this.totle = newval[newval.length-1].sumAddContractCount;
    }
  }
};
</script>

<style scoped lang="scss">
.tableSyntheticalReportForm {
  .contentlist {
    background-color: #fff;
  }
  .contractNum {
    line-height: 30px;
    font-size: 28px;
    margin: 30px 20px;
    color: #666;
  }
}
</style>
