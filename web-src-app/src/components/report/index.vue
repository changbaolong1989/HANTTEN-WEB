<template>
  <div class="report">
    <div class="scroll" v-if="NoData">
      <mt-navbar v-model="selected">
        <mt-tab-item id="1" v-if="!!userPowerInfo.reportZJForm">造价报表</mt-tab-item>
        <mt-tab-item id="2" v-if="!!userPowerInfo.reportZBForm">招标报表</mt-tab-item>
        <mt-tab-item id="3" v-if="!!userPowerInfo.reportJLForm">监理报表</mt-tab-item>
        <mt-tab-item id="4" v-if="!!userPowerInfo.reportJXForm">绩效报表</mt-tab-item>
        <mt-tab-item id="5" v-if="!!userPowerInfo.reportZHCWForm">综合财务报表</mt-tab-item>
        <mt-tab-item id="6" v-if="!!userPowerInfo.reportZHHTForm">综合合同报表</mt-tab-item>
      </mt-navbar>
    </div>
    <mt-tab-container v-if="NoData"><component :is="template" :selected="selected"></component></mt-tab-container>
    <div v-if="!NoData" class="NoData">
      <div class="noDataCont">
        <img src="../../assets/img/noData.png" width="100%" alt="" />
        <p>暂无数据</p>
      </div>
    </div>
  </div>
</template>

<script>
import fiveOne from './common/fiveOne'; /* 五合一 */
import performance from './performance/index'; /*绩效报表*/
export default {
  data() {
    return {
      userPowerInfo: this.SStorage.getItem('userPowerInfo'),
      selected: '1',
      template: 'fiveOne',
      NoData: true
    };
  },
  created() {
    var userPowerInfo = this.SStorage.getItem('userPowerInfo');
    if (!!userPowerInfo.reportZJForm) {
      this.selected = '1';
    } else if (!!userPowerInfo.reportZBForm) {
      this.selected = '2';
    } else if (!!userPowerInfo.reportJLForm) {
      this.selected = '3';
    } else if (!!userPowerInfo.reportJXForm) {
      this.selected = '4';
    } else if (!!userPowerInfo.reportZHCWForm) {
      this.selected = '5';
    } else if (!!userPowerInfo.reportZHHTForm) {
      this.selected = '6';
    } else {
      this.NoData = false;
    }
  },
  components: {
    fiveOne: fiveOne,
    performance: performance
  },
  watch: {
    selected: function(newval, oldval) {
      if (newval != 4) {
        this.template = 'fiveOne';
      } else {
        this.template = 'performance';
      }
    }
  }
};
</script>

<style scoped lang="scss">
.report {
  width: 100%;
  padding-bottom: 110px;
  .scroll {
    width: 100%;
    overflow: scroll;
    .mint-navbar {
      max-width: 160%;
      min-width: 100%;
      position: relative;
    }
  }
  .mint-tab-container {
    background-color: #f1f5f8;
  }
}
</style>
