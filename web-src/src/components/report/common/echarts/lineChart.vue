<template>
  <div id="my_dataviz" style="height:500px;background-color: white;border-radius: 10px 10px 0 0;"></div>
</template>

<script>
  import echarts from 'echarts';
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
      let my_dataviz = document.getElementById('my_dataviz');
      my_dataviz.style.width = my_dataviz.offsetWidth;
      // 基于准备好的dom，初始化echarts实例
      vm.myChart = echarts.init(my_dataviz);
      window.onresize = function() {

        vm.myChart.resize();
      }
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
            boundaryGap: ['20%', '20%']
          },
          yAxis: {
            type: 'value'
          },
          series: _series
        });
      }
    }
  }
</script>

<style>
</style>
