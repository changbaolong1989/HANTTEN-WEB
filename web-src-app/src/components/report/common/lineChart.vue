<template>
  <div id="lineChart"></div>
</template>

<script>
// import echarts from 'echarts';
export default {
  props: ['chartData'],
  data() {
    return {
      xData: this.chartData.xData,
      series: this.chartData.series,
      myChart: '' // eChart对象
    };
  },
  mounted: function() {
    let vm = this;
    let lineChart = document.getElementById('lineChart');
    // 基于准备好的dom，初始化echarts实例
    vm.myChart = echarts.init(lineChart, 'light');
    window.onresize = function() {
      vm.myChart.resize();
    };
    vm.updataChart();
  },
  watch: {
    chartData: {
      handler() {
        let vm = this;
        vm.xData = vm.chartData.xData;
        vm.series = vm.chartData.series;
        vm.updataChart();
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    updataChart() {
      let vm = this;
      if (!vm.myChart) return;
      // 组织eChart识别的数据格式
      let _series = [];
      for (let i = 0, len = vm.series.length; i < len; i++) {
        _series.push({
          data: vm.series[i],
          type: 'line'
        });
      }
      // 绘制图表
      vm.myChart.setOption({
        xAxis: {
          type: 'category',
          data: vm.xData,
          axisLine: {
            lineStyle: {
              color: "#999999"
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: "#999999"
            }
          }
        },
        grid: {
          top: '10%',
          left: '15%',
          right: '5%'
        },
        dataZoom: [
          {
             type: 'slider',
             realtime: true,
          }
        ],
        series: _series
      });
    }
  }
};
</script>

<style lang="scss" scoped>
#lineChart {
  height: 460px;
  width: 94%;
  margin: 20px auto;
  background-color: white;
  border-radius: 10px;
}
</style>
