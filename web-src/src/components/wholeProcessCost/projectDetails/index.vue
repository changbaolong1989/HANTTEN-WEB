<template>
  <div class="wholeProcessCost__projectDetails">
      <div class="content-left">
          <div class="container--tree">
            <el-tree :data="treeData" accordion  @node-click="clickNode"></el-tree>
          </div>
      </div>
    <div class="content-right">
      <div :is="current"></div>
    </div>
  </div>
</template>

<script>
  import wholeProcessAssess from '@/components/wholeProcessCost/projectDetails/assess/index' /* 估算  */
  import wholeProcessBudgetaryEstimate from '@/components/wholeProcessCost/projectDetails/budgetaryEstimate/index' /* 概算  */
  import wholeProcessTreaty from '@/components/wholeProcessCost/projectDetails/treaty/index' /* 合约规划  */
  import wholeProcessTender from '@/components/wholeProcessCost/projectDetails/tender/index' /* 招标管理  */
  import wholeProcessCleanTender from '@/components/wholeProcessCost/projectDetails/cleanTender/index' /* 清标台账  */
  import wholeProcessStandingBook from '@/components/wholeProcessCost/projectDetails/contractManage/contractStandingBook/index' /* 合同台账  */
  import wholeProcessLetter from '@/components/wholeProcessCost/projectDetails/letter/index' /* 发出信函登记表 > */
  import wholeProcessMeeting from '@/components/wholeProcessCost/projectDetails/meeting/index' /* 会议纪要 > */
  import wholeProcessChangeStandingBook from '@/components/wholeProcessCost/projectDetails/changeManage/changeStandingBook/index' /* 变更管理，变更台账 */
  import wholeProcessChangeDetail from '@/components/wholeProcessCost/projectDetails/changeManage/changeDetail/index' /* 变更管理，变更台账 */
  import wholeProcessClaim from '@/components/wholeProcessCost/projectDetails/claim/index' /* 索赔台账 */
  import wholeProcessPay from '@/components/wholeProcessCost/projectDetails/pay/index' /* 付款台账 */
  import wholeProcessCloseAccount from '@/components/wholeProcessCost/projectDetails/closeAccount/index' /* 结算台账 */
  import dynamicCostManage from '@/components/wholeProcessCost/projectDetails/dynamicCost/index' /* 动态成本管理 */

  /*import reviewTest from '@/components/wholeProcessCost/projectDetails/review/index' /!* 合同台账  *!/*/

  export default {
    data() {
      return {
          treeData:[],
          current:'wholeProcessAssess',
          projectNum:this.SStorage.getItem(this.$route.path).rowInfo.projectNum,
          projectId:this.SStorage.getItem(this.$route.path).rowInfo.projectId,
      }
    },
     components: {
       wholeProcessAssess,//估算阶段
       wholeProcessBudgetaryEstimate,//概算阶段
       wholeProcessStandingBook,
       wholeProcessChangeStandingBook,
       wholeProcessChangeDetail,
       wholeProcessTreaty,//合约规划
       wholeProcessTender,//招标管理
       wholeProcessCleanTender,//清标台账
       wholeProcessLetter,// 发出信函登记表阶段
       wholeProcessMeeting,// 会议纪要
       wholeProcessClaim,//索赔台账
       wholeProcessPay,//付款台账
       wholeProcessCloseAccount,//结算台账
       dynamicCostManage,//动态成本管理

       /*reviewTest,*/

     },
    mounted(){
      this.getContractTreeData()
    },
    methods: {
      clickNode(data){
        if (data.periodType=='1') {
          this.current = data.showTool;
        }
      },
      selectGetName(key,list){
        for (var i = 0; i < list.length; i++) {
          if (list[i].value==key) {
            return list[i].label
          }
        }
      },
      // 查询树形数据
      getContractTreeData() {
        const vm = this;
        let formData = {
          projectNum:vm.projectNum
        };
        let url = "/wholeProcess/project/query/searchProjectPeriodList"
        vm.apiAddress(url,formData).then(res=>{
          if (res.data.status == 'failure') {
            vm.$message({
            message: res.data.message,
            type: 'error',
            center: true
            });
          } else {
              const table = res.data.dataList;
              vm.treeData = table;
          }
        }).catch(err=>{
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
    }
  }
</script>

<style lang="scss">
  .wholeProcessCost__projectDetails {
    margin-left: 0px;
    min-height: calc(100vh - 114px);
    padding-bottom: 20px;
    display: flex;
    align-items: stretch;

    /* 左侧边栏 */
  .content-left {
    min-width: 200px;
    width: 200px;
    overflow: visible;
    position: relative;
    background-color: white;
    border-radius: 15px 0 0 15px;
    padding: 0 10px 10px 10px;
    box-shadow: 6px 0 8px rgba(176, 192, 202, 0.3);
    z-index: 99;

    .container--tree{
      height: calc(100vh - 155px);
    }

    .container--tree,.el-tree{
      background-color: #f0f7fe;
      border-radius: 5px;
      margin-top: 10px;
      overflow: auto;
    }
  }

    /* 右侧内容部门 */
    .content-right{
      flex-grow: 1;
      background-color: white;
      border-radius: 0 15px 15px 0;
    }
  }

/* 树形控件 */
.el-dropdown-menu{
  &.tool-item{

  }
  padding:0!important;
  button.edit-tool{
    width:100%;
    background-color:white;

    &:hover{
      background-color: #ecf5ff;
      color: #66b1ff;
    }
  }
}

</style>
