<template>
  <!-- 五合一-->
  <div class="fiveOne" v-if="selected!=4">
    <search @condition="changeCondition" :key="Charted"></search>
    <mt-tabbar v-model="Charted">
      <mt-tab-item id="Chart1" v-if="selected != 5">新增合同数量</mt-tab-item>
      <mt-tab-item id="Chart2" v-if="selected != 6">请款金额</mt-tab-item>
      <mt-tab-item id="Chart3" v-if="selected != 6">到款金额</mt-tab-item>
    </mt-tabbar>
    <mt-tab-container>
      <lineChart :chartData="lineChart" v-if="Charted == 'Chart1'"></lineChart>
      <lineChart :chartData="lineChart" v-if="Charted == 'Chart2'"></lineChart>
      <lineChart :chartData="lineChart" v-if="Charted == 'Chart3'"></lineChart>
    </mt-tab-container>
    <tableSyntheticalReportForm :contentList="contentList"></tableSyntheticalReportForm>
  </div>
</template>

<script>
import search from './search'; /* 搜索功能 */
import lineChart from './lineChart'; /* 搜索功能 */
import tableSyntheticalReportForm from './tableSyntheticalReportForm'; /* 列表数据 */
export default {
  data() {
    return {
      Charted: 'Chart1',
      lineChart: {
        xData: [],
        series: []
      },
      date: new Date().getFullYear(),
      addContractCount: [] /* 新增合同数量 */,
      requestTotalAmount: [] /* 请款金额 */,
      paymentTotalAmount: [] /* 到款金额 */,
      contentList: [],
    };
  },
  props: ['selected'],
  components: {
    search: search,
    lineChart: lineChart,
    tableSyntheticalReportForm: tableSyntheticalReportForm
  },
  watch: {
    Charted: function(newval, oldval) {
      this.changedate(newval);
    },
    selected: function(newval, oldval) {
      this.date = new Date().getFullYear();
      if (newval == 5) {
        this.Charted = 'Chart2';
      } else {
        this.Charted = 'Chart1';
      }
      this.changedate(newval);
      this.init();
    }
  },
  created() {
    if(this.selected==5){
      this.Charted = 'Chart2';
    }
    this.init();
  },
  methods: {
    init() {
      this.lineChart.xData = [];
      this.addContractCount = [];
      this.requestTotalAmount = [];
      this.paymentTotalAmount = [];
      this.patientInfo = {
        departmentId: this.selected == 1 ? this.manufactureDeptId : this.selected == 2 ? this.tenderDeptId : this.selected == 3 ? this.superviseDeptId : '',
        searchDate: this.date
      };
      /* 页面初始化请求数据 */
      this.requestPost('/reportForm/query/searchSyntheticalReportFormList', this.patientInfo)
        .then(res => {
          if (res.data.status == 'success') {
            this.contentList = res.data.dataList;
            const vm = this;
            res.data.dataList.forEach(function(i) {
              /* 横坐标 */
              vm.lineChart.xData.push(i.groupMonths);
              vm.addContractCount.push(i.addContractCount);
              vm.requestTotalAmount.push(i.requestTotalAmount);
              vm.paymentTotalAmount.push(i.paymentTotalAmount);
            });
            this.changedate(this.Charted);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    changeCondition(val) {
      this.date = val;
      this.init();
    },
    changedate(newval) {
      if (newval == 'Chart1') {
        this.lineChart.series = [this.addContractCount];
      } else if (newval == 'Chart2') {
        this.lineChart.series = [this.requestTotalAmount];
      } else if (newval == 'Chart3') {
        this.lineChart.series = [this.paymentTotalAmount];
      }
    }
  }
};
</script>

<style lang="scss">
.fiveOne {
  width: 100%;
  .mint-tabbar {
    width: 100%;
    height: 100px;
    top: 70px;
    background-color: #fff;
    box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, 0.1);

    .mint-tab-item {
      width: 25%;
      color: #999999;
      .mint-tab-item-label {
        line-height: 100px;
        font-size: 28px;
      }
    }

    .is-selected {
      color: #06a1ee;
      background-color: #fff;
    }
  }
  .mint-tab-container {
    margin-top: 120px;
  }
}
</style>
