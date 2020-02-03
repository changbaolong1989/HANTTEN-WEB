<template>
  <el-table :data="currentTableData" :default-sort = "{prop: 'groupMonths', order: 'descending'}" border style="width: 100%;margin-top: 20px;">
    <el-table-column align="center" prop="groupMonths" label="时间" v-if="currentShowCols.groupMonths"></el-table-column>
    <el-table-column align="center" prop="addContractCount" label="新增合同数量" v-if="currentShowCols.addContractCount"></el-table-column>
    <el-table-column align="center" prop="sumAddContractCount" label="年度累计新增合同数量" v-if="currentShowCols.sumAddContractCount"></el-table-column>
    <el-table-column align="center" prop="doneContractCount" label="完成合同数量" v-if="currentShowCols.doneContractCount"></el-table-column>
    <el-table-column align="center" prop="sumDoneContractCount" label="年度累计完成合同数量" v-if="currentShowCols.sumDoneContractCount"></el-table-column>
    <el-table-column align="center" prop="requestTotalAmount" label="请款金额" v-if="currentShowCols.requestTotalAmount"></el-table-column>
    <el-table-column align="center" prop="sumRequestTotalAmount" label="年度累计请款金额" v-if="currentShowCols.sumRequestTotalAmount"></el-table-column>
    <el-table-column align="center" prop="paymentTotalAmount" label="到款金额" v-if="currentShowCols.paymentTotalAmount"></el-table-column>
    <el-table-column align="center" prop="sumPaymentTotalAmount" label="年度累计到款金额" v-if="currentShowCols.sumPaymentTotalAmount"></el-table-column>
    <el-table-column align="center" prop="differenceValue" label="未到金额" v-if="currentShowCols.differenceValue"></el-table-column>
    <el-table-column align="center" prop="sumDifferenceValue" label="年度累计未到金额" v-if="currentShowCols.sumDifferenceValue"></el-table-column>
  </el-table>
</template>

<script>
  export default {
    props: ['tableData','showCols'],
    data() {
      return {
        currentTableData: this.tableData || [],
        currentShowCols:this.showCols
      };
    },
    watch: {
      tableData: {
        handler() {
          let vm = this;
          vm.currentTableData = vm.tableData;
          console.log("vm.currentTableData",vm.currentTableData)
        },
        immediate: true
      },
      showCols: {
        handler() {
          let vm = this;
          vm.currentShowCols = vm.showCols;
        },
        immediate: true
      }
    }
  }
</script>

<style>
</style>
