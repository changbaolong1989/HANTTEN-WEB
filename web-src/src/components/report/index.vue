<template>
  <div class="m-report">
    <el-tabs v-model="activeTab" type="card" @tab-click="tabClick" v-if="!nothing">
      <el-tab-pane label="造价报表" name="cost" v-if="!!userPowerInfo.reportZJForm" >
      </el-tab-pane>
      <el-tab-pane label="招标报表" name="tender" v-if="!!userPowerInfo.reportZBForm"  >
      </el-tab-pane>
      <el-tab-pane label="监理报表" name="supervision" v-if="!!userPowerInfo.reportJLForm"  >
      </el-tab-pane>
      <el-tab-pane label="绩效报表" name="performance" v-if="!!userPowerInfo.reportJXForm" >
      </el-tab-pane>
      <el-tab-pane label="综合财务报表" name="comprehensiveFinancial" v-if="!!userPowerInfo.reportZHCWForm" >
      </el-tab-pane>
      <el-tab-pane label="综合合同报表" name="consolidatedContractStatement" v-if="!!userPowerInfo.reportZHHTForm" >
      </el-tab-pane>
    </el-tabs>
    <div class="item-tab-bor linear" v-if="!nothing"></div>
    <component v-bind:is="componentName" :tabName="activeTab" :departmentId="departmentId" v-if="!nothing"></component>
    <div
    v-if="nothing" style="margin: auto;text-align: center;margin-top: 20%;">
    <img src="../../assets/image/no-item.png" alt="">
    <p>暂无数据</p>
    </div>
  </div>
</template>

<script>
  /* 绩效 */
  import performance from './performance';
/* 综合合同 */
  import fiveOne from './common/fiveOne';

  export default {
    components: {
      performance,/* 绩效 */
      fiveOne
    },
    data() {
      return {
        nothing:false,
        componentName:'fiveOne',// 默认是五合一页面
        activeTab: 'cost',
        departmentId:this.manufactureDeptId,
        userPowerInfo:this.SStorage.getItem("userPowerInfo"),
      }
    },
    watch:{
      userPowerInfo:{
        handler(){
          let vm = this;
          if(vm.userPowerInfo.reportZJForm){
            // 造价报表
            vm.activeTab = 'cost';
            vm.departmentId = vm.manufactureDeptId;
          }else if(vm.userPowerInfo.reportZBForm){
            // 招标报表
            vm.activeTab = 'tender';
            vm.departmentId = vm.tenderDeptId;
          }else if(vm.userPowerInfo.reportJLForm){
            // 监理报表
            vm.activeTab = 'supervision';
            vm.departmentId = vm.superviseDeptId;
          }else if(vm.userPowerInfo.reportJXForm){
            // 绩效报表
            vm.activeTab = 'performance';
            vm.departmentId = null;
          }else if(vm.userPowerInfo.reportZHCWForm){
            // 综合财务报表
            vm.activeTab = 'comprehensiveFinancial';
            vm.departmentId = null;
          }else if(vm.userPowerInfo.reportZHHTForm){
            // 综合合同报表
            vm.activeTab = 'consolidatedContractStatement';
            vm.departmentId = null;
          }
          if(!vm.userPowerInfo.reportZJForm&&!vm.userPowerInfo.reportZBForm&&!vm.userPowerInfo.reportJLForm&&!vm.userPowerInfo.reportJXForm&&!vm.userPowerInfo.reportZHCWForm&&!vm.userPowerInfo.reportZHHTForm){
            vm.nothing = true;
          }else{
            vm.nothing = false;
          }
          vm.tabClick({name:vm.activeTab});
        },
        immediate: true
      }
    },
    methods: {
      chengeDeptId(deptId){
        console.log("deptId",deptId)
      },
      searchReportForm(val){
        console.log("val",val);
      },
      tabClick(target) { // tab切换
        let vm = this;
        if('performance'==target.name){
          vm.componentName = 'performance'
        }else if ('cost'==target.name) {
          vm.departmentId = vm.manufactureDeptId;
          vm.componentName = 'fiveOne';
        }else if ('tender'==target.name) {
          vm.departmentId = vm.tenderDeptId;
          vm.componentName = 'fiveOne';
        }else if ('supervision'==target.name) {
          vm.departmentId = vm.superviseDeptId;
          vm.componentName = 'fiveOne';
        }else{
          vm.departmentId = null;
          vm.componentName = 'fiveOne';
        };
      }
    }
  }
</script>

<style lang="scss">
  .m-report {
    padding: 0px 25px 25px!important;
    overflow: auto;

    /* 标签页 */
    .el-tabs--card {
      &>.el-tabs__header {
        border-bottom: none;
        margin: 0;

        .el-tabs__nav-scroll {
          padding: 20px 20px 0;
          // box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.4) inset;

          .el-tabs__nav {
            border: 1px;

            .el-tabs__item {
              border-radius: 10px 10px 0 0;
              border: none;
              box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
              background-color: white;

              &.is-active {
                background-color: #0aa3ef;
                color: white;
              }
            }
          }
        }
      }
    }
  }
</style>
