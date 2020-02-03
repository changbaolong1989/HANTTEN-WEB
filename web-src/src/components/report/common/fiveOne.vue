<template>
  <!-- 监理 -->
  <div class="report-supervision">
    <search ref="search" searchType="year" @click="searchData()" :tabName="tabName"></search>
    <el-row style="margin-top: 20px;">
      <el-col :span="24"> {{'comprehensiveFinancial'==tabName?'年度总到款金额（元）':'年度合同数'}}：{{countNum}}</el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-tabs v-model="activeTab" type="card" @tab-click="tabClick">
          <el-tab-pane label="新增合同数量" name="newContractQuantity" v-if="'comprehensiveFinancial'!=tabName">
          </el-tab-pane>
          <el-tab-pane label="请款金额" name="pleaseIncreaseTheAmountOfMoney" v-if="'consolidatedContractStatement'!=tabName">
          </el-tab-pane>
          <el-tab-pane label="到款金额" name="toTheAmount" v-if="'consolidatedContractStatement'!=tabName">
          </el-tab-pane>
        </el-tabs>
        <div class="item-tab-bor linear"></div>
        <line-chart :chartData="lineChart"></line-chart>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <table-synthetical-report-form :tableData="tableData" :showCols="showCols" v-if="tableShow"></table-synthetical-report-form>
      </el-col>
    </el-row>
  </div>

</template>

<script>
  /* 搜索 */
  import search from '../common/search';
  /* d3-直线图 */
  import lineChart from '../common/echarts/lineChart';
  /* 综合报表 */
  import tableSyntheticalReportForm from '../common/tableSyntheticalReportForm';
  export default {
    props: ['tabName','departmentId'],
    components: {
      search,
      'line-chart': lineChart,
      'table-synthetical-report-form': tableSyntheticalReportForm
    },
    beforemount(){
      console.log(1)
      this.searchData()
    },
    mounted() { // 生命周期-> el 被新创建的 vm.$el 替换，并挂载到实例上去之后调用该钩子
      let vm = this;
      console.log(2)
      vm.$nextTick(function () {
        vm.tabClick({name:"newContractQuantity"});
      })
    },
    data() {

      return {
        showCols: { // 显示的表格列
          groupMonths: true, // 时间
          addContractCount: true, // 新增合同数量
          sumAddContractCount: true, // 年度累计新增合同数量
          doneContractCount: true, // 完成合同数量
          sumDoneContractCount: true, // 年度累计完成合同数量
          requestTotalAmount: true, // 请款金额
          sumRequestTotalAmount: true, // 年度累计请款金额
          paymentTotalAmount: true, // 到款金额
          sumPaymentTotalAmount: true, // 年度累计到款金额
          differenceValue: true, // 未到金额
          sumDifferenceValue: true, // 年度累计未到金额
        },
        lineChart: {
          xData: [],
          series: []
        },
        activeTab: 'newContractQuantity', // 默认选中的tab
        tableData: [],
        addContractCountList:[],
        requestTotalAmountList:[],
        paymentTotalAmountList:[],
        countNum:'',
        tableShow:true,// 显示表格数据
      }
    },
    watch: {
      tabName: {
        handler() {
          let vm = this;
          // 改变表格显示列以及图表的默认选中
          vm.changeShowCols();

          vm.searchData();
          // 查询表格数据以及图表数据
          // todo
        },
        immediate: true
      },
      departmentId: {
        handler() {
          let vm = this;
          // 改变表格显示列以及图表的默认选中
          // 查询表格数据以及图表数据
          // todo
        },
        immediate: true
      },
    },
    methods: {
      searchData() {
        let vm = this;
        console.log("vm.lineChart",vm.lineChart);
      this.$nextTick(function () {
        // 查询事件
        let formData = {
          searchDate: vm.$refs['search'].form.date,
          departmentId:vm.departmentId
        };
        vm.tableShow = false;
        vm.apiAddress('/reportForm/query/searchSyntheticalReportFormList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let dataList = res.data.dataList;
            vm.tableShow = true;
            vm.tableData = dataList
            vm.countNum = dataList[dataList.length-1].sumAddContractCount
            if(this.tabName == 'comprehensiveFinancial'){
              vm.countNum = dataList[dataList.length-1].sumPaymentTotalAmount
            }
            let xAxis  = []
            let addContractCountList = []
            let requestTotalAmountList = []
            let paymentTotalAmountList = []
            for (var i = 0; i < dataList.length; i++) {
              xAxis.push(dataList[i].groupMonths);
              addContractCountList.push(dataList[i].addContractCount);
              requestTotalAmountList.push(dataList[i].requestTotalAmount);
              paymentTotalAmountList.push(dataList[i].paymentTotalAmount);
            }
            vm.addContractCountList =addContractCountList;
            vm.requestTotalAmountList =requestTotalAmountList;
            vm.paymentTotalAmountList =paymentTotalAmountList;
            vm.lineChart.xData = xAxis;
            vm.tabClick({name:vm.activeTab});
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      });

      },
      tabClick(target) {
        // 更新折线图数据
        let vm = this;
        switch (target.name) {
          case 'newContractQuantity': // 新增合同数量
            vm.lineChart.series = [
              vm.addContractCountList
            ];
            break;
          case 'pleaseIncreaseTheAmountOfMoney': // 请款金额
            vm.lineChart.series = [
              vm.requestTotalAmountList
            ];
            break;
          case 'toTheAmount': // 到款金额
            vm.lineChart.series = [
              vm.paymentTotalAmountList
            ];
            break;
        }
      },
      changeShowCols() {
        let vm = this;
        let def = {};
         vm.activeTab = 'newContractQuantity';
        switch (vm.tabName) {
          case 'cost':
          case 'tender':
          case 'supervision':
            vm.showCols = {
              groupMonths: true, // 时间
              addContractCount: true, // 新增合同数量
              sumAddContractCount: true, // 年度累计新增合同数量
              doneContractCount: true, // 完成合同数量
              sumDoneContractCount: true, // 年度累计完成合同数量
              requestTotalAmount: true, // 请款金额
              sumRequestTotalAmount: true, // 年度累计请款金额
              paymentTotalAmount: true, // 到款金额
              sumPaymentTotalAmount: true, // 年度累计到款金额
              differenceValue: true, // 未到金额
              sumDifferenceValue: true, // 年度累计未到金额
            }
            break;
          case 'comprehensiveFinancial':
          vm.activeTab = 'pleaseIncreaseTheAmountOfMoney';
            vm.showCols = {
              groupMonths: true, // 时间
              requestTotalAmount: true, // 请款金额
              sumRequestTotalAmount: true, // 年度累计请款金额
              paymentTotalAmount: true, // 到款金额
              sumPaymentTotalAmount: true, // 年度累计到款金额
              differenceValue: true, // 未到金额
              sumDifferenceValue: true, // 年度累计未到金额
            }
            break;
          case 'consolidatedContractStatement':
            vm.showCols = {
              groupMonths: true, // 时间
              addContractCount: true, // 新增合同数量
              sumAddContractCount: true, // 年度累计新增合同数量
              doneContractCount: true, // 完成合同数量
              sumDoneContractCount: true, // 年度累计完成合同数量
            }
            break;
        }


      }
    }
  }
</script>

<style lang="scss">
</style>
