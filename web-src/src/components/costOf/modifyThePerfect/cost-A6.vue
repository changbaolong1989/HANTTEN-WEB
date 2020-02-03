<template>
  <div class="cost-A4 matterForm">
    <el-row>
      <el-col :span="18">
        <div>
          <p>{{title}}</p>
        </div>
      </el-col>
      <el-col :span="6" class="item-tab-chuang" style="text-align: right;margin: 0;" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
        <div @click="showDialogBiddingPlan" style="display: inline-block;">
          <span class="linear el-icon-edit"></span>
          编辑
        </div>
      </el-col>
    </el-row>
    <!-- 招标计划表格 -->
    <table-bidding-plan v-if="showT1" ref="tableBiddingPlan" :data="tableData"></table-bidding-plan>
    <!-- 咨询工作记录表格 -->
    <table-consultation-record v-else ref="tableConsultationRecord" :data="tableData"></table-consultation-record>
    <!-- 招标计划弹窗 -->
    <dialog-bidding-plan ref="dialogBiddingPlan" @submit="submitBiddingPlan"></dialog-bidding-plan>
    <!-- 咨询工作记录弹窗 -->
    <dialog-consultation-record ref="dialogConsultationRecord" @submit="submitConsultationRecord"></dialog-consultation-record>
  </div>
</template>

<script>
  import tableBiddingPlan from './tableBiddingPlan' /* 招标计划表格 */
  import dialogBiddingPlan from './dialogBiddingPlan' /* 招标计划弹窗 */

  import tableConsultationRecord from './tableConsultationRecord' /* 咨询工作记录表格 */
  import dialogConsultationRecord from './dialogConsultationRecord' /* 咨询工作记录弹窗 */

  export default {
    components: {
      "table-bidding-plan": tableBiddingPlan,
      "dialog-bidding-plan": dialogBiddingPlan,
      'table-consultation-record': tableConsultationRecord,
      "dialog-consultation-record": dialogConsultationRecord
    },
    mounted(){
      let vm = this;
      // 查询数据
      vm.getData();
    },
    props: ['transmittedData'],
    data() {
      return {
        showT1: true, // 显示第一个表格
        title: "", // 表格名称
        tableData: []
      }
    },

    methods: {
      searchList() {
        this.apiAddress("/taskExecute/query/searchA6Info", {
          "relationId": this.transmittedData.relationId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            let vm = this;
//            let ref = vm.showT1 ? 'dialogBiddingPlan'/* 招标计划 */: 'dialogConsultationRecord'/* 咨询工作记录 */;
//            vm.$refs[ref].form = res.data.dataVO;
            vm.tableData = res.data.dataVO;
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      submitBiddingPlan(data) {// 招标计划提交表单
        let vm = this;
        // 复制咨询工作记录dialog内容
        let dialogForm = Object.assign({},vm.$refs['dialogBiddingPlan'].form);
        // 复制数组
        dialogForm.unitProjectList = [...dialogForm.unitProjectList];
        // 关系id
        dialogForm.relationId = vm.transmittedData.relationId;

        // 保存拟定招标规划记录信息
        vm.apiAddress('/taskExecute/add/insertA6ZbdlInfo',dialogForm).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 关闭dialog
            vm.$refs['dialogBiddingPlan'].show = false;
            vm.$message({
              message: res.data.message,
              type: 'success',
              center: true
            });
            this.searchList();
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });


      },
      submitConsultationRecord(data){// 咨询工作记录提交表单
        let vm = this;
        // 复制咨询工作记录dialog内容
        let dialogForm = Object.assign({},vm.$refs['dialogConsultationRecord'].form);
        // 复制数组
        dialogForm.unitProjectList = [...dialogForm.unitProjectList];
        // 关系id
        dialogForm.relationId = vm.transmittedData.relationId;

        // 保存咨询工作记录信息
        vm.apiAddress('/taskExecute/add/insertA6Info',dialogForm).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 关闭dialog
            vm.$refs['dialogConsultationRecord'].show = false;
            vm.$message({
              message: res.data.message,
              type: 'success',
              center: true
            });
            this.searchList();
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      },
      showDialogBiddingPlan() {
        let vm = this;
        let ref = vm.showT1 ? 'dialogBiddingPlan'/* 招标计划 */: 'dialogConsultationRecord'/* 咨询工作记录 */;
        vm.$refs[ref].show = true;
        vm.$refs[ref].form =  vm.tool.deepCopy(this.tableData,vm.tool.deepCopy);
      },
      getData(){// 获取表格数据
        this.searchList();
      }
    }
  }
</script>

<style lang="scss"></style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');
</style>
