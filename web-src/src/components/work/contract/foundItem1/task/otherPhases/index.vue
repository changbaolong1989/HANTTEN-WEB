<template>
  <div class="task" style="height: 100%;">
    <div class="title form-block">
      <el-row :gutter="gutter">
        <el-col :span="24"><p style="font-size: 12px;padding-left: 10px;">工作步骤说明:</p></el-col>
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">{{ currentTransmitData.periodDesc }}</p>
        </el-col>
      </el-row>
    </div>

    <!-- 人员调整 -->
    <personnel-adjustment :transmittedData="currentTransmitData" v-if="currentTransmitData.periodType == 4"></personnel-adjustment>

    <!-- A4组件 -->
    <div class="form-block form-block__row--auto-width" v-if="currentTransmitData.periodType == 1 && currentTransmitData.pageContent == 'A4'">
      <a4 :transmittedData="currentTransmitData"></a4>
    </div>

    <!-- A7(招标工作进度计划)组件 -->
    <div class="form-block form-block__row--auto-width" style="height: calc(100% - 91px);" v-if="currentTransmitData.pageContent == 'A7'">
      <a7 :transmittedData="currentTransmitData"></a7>
    </div>

    <!-- A5组件 -->
    <div class="form-block form-block__row--auto-width" v-if="currentTransmitData.pageContent == 'A5'">
      <a5 :projectName="currentTransmitData.projectNumber" :contractNum="currentTransmitData.contractNumber" :transmittedData="currentTransmitData"></a5>
    </div>

    <!-- A8组件(招标代理) -->
    <div class="form-block" v-if="currentTransmitData.pageContent == 'A8' && currentTransmitData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'">
      <a8_zbdl :transmittedData="currentTransmitData"></a8_zbdl>
    </div>

    <!-- A8组件(造价部) -->
    <div class="form-block" v-if="currentTransmitData.pageContent == 'A8' && currentTransmitData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'">
      <a8_zj :transmittedData="currentTransmitData"></a8_zj>
    </div>

    <!-- A6组件(造价部)  -->
    <div class="form-block" v-if="currentTransmitData.pageContent == 'A6'"><a6 :transmittedData="currentTransmitData" ref="a6"></a6></div>
    <!-- 开标、评标-->
    <openBid
      ref="openBid"
      v-if="currentTransmitData.periodType == 9"
      :data="openBidForm"
      @submit="openBidSubmit"
      :disabled="switchDisabled"
    ></openBid>
    <!-- 专家意见表 -->
    <rxpert-opinion-list
      ref="rxpertOpinionList"
      v-if="currentTransmitData.periodType == 6"
      :data="rxpertOpinionListForm"
      @submit="rxpertOpinionListSubmit"
      :disabled="switchDisabled"
    ></rxpert-opinion-list>

    <!-- 磋商文件确认文件意见 -->
    <consultation-document-confirmation-document-opinion
      ref="consultationDocumentConfirmationDocumentOpinion"
      v-if="currentTransmitData.periodType == 7"
      :data="consultationDocumentConfirmationDocumentOpinionForm"
      @submit="consultationDocumentConfirmationDocumentOpinionSubmit"
      :disabled="switchDisabled"
    ></consultation-document-confirmation-document-opinion>

    <!-- 未成交通知书 -->
    <unfilled-notice
      ref="unfilledNotice"
      v-if="currentTransmitData.periodType == 8"
      :data="unfilledNoticeForm"
      @submit="unfilledNoticeSubmit"
      :disabled="switchDisabled"
    ></unfilled-notice>
  </div>
</template>

<script>
import personnelAdjustment from './../personnelAdjustment/index';/* 人员调整 */
import a4 from '../../../../../costOf/collectingData/cost-A4';  /* a4组件 */
import a7 from '../../../../../costOf/tender/cost-A7';  /* A7(招标工作进度计划)组件 */
import a5 from '../../../../../costOf/drawUpAWorkPlan/cost-A5';  /* a5组件 */
import a6 from '../../../../../costOf/modifyThePerfect/cost-A6';  /* a6组件 */
import a8_zbdl from '../../../../../costOf/bids/cost-A8';  /* A8组件(招标代理) */
import a8_zj from '../../../../../costOf/siteVisit/cost-A8Cost';  /* A8组件(造价部) */
import rxpertOpinionList from '../../../../../costOf/rxpertOpinionList/index';  /* 专家意见表 */
import consultationDocumentConfirmationDocumentOpinion from '../../../../../costOf/consultationDocumentConfirmationDocumentOpinion/index';  /* 磋商文件确认文件意见 */
import unfilledNotice from '../../../../../costOf/unfilledNotice/index';  /* 未成交通知书 */
import openBid from '../../../../../costOf/openBid/index';  /* 开标评标 */

export default {
  components: {
    'personnel-adjustment':personnelAdjustment,// 人员调整
    'a4': a4,// A4
    'a7': a7,// A7
    'a8_zbdl': a8_zbdl, // A8组件(招标代理)
    'a8_zj': a8_zj,// A8组件(招标代理)
    'a5': a5,//A5
    'a6': a6,//A6
    'rxpert-opinion-list':rxpertOpinionList,// 专家意见表
    'consultation-document-confirmation-document-opinion':consultationDocumentConfirmationDocumentOpinion,// 磋商文件确认文件意见
    'unfilled-notice':unfilledNotice,// 未成交通知书
    'openBid':openBid// 未成交通知书
  },
  data() {
    return {
      // 表单项间距
      gutter: 40,
      // 审核历史记录
      tableListData: [],
      // 默认不显示A3组件
      A3Flg: false,
      // 项目名称
      projectName: "",
      // 合同编号
      contractNum: "",
      // 成果文件编号
      outcomeDocNo: "",
      // 按钮显示标识
      typeFlag:"",
      //当前页数据
      tableData: [],
      //分页总数据
      total: 0,
      //当前页码
      currentPage: 1,
      //每页显示多少条数据
      pageSize: 10,
      //条目列表
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
      // 任务id
      taskId:"",
      // 专家论证意见表详情
      rxpertOpinionListForm:null,
      // 招标文件确认意见表和任务的关联表详情
      consultationDocumentConfirmationDocumentOpinionForm:null,
      // 落标通知书和任务关联表详情
      unfilledNoticeForm:null,
      // 开标、评标详情
      openBidForm:null
    }
  },
  props: ['currentTransmitData','disabled'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        let vm = this;
        vm.currentTransmitData = newval;
        vm.init();
      },
      immeditate: true,
      deep: true
    },
    disabled: {
      handler(newval) {
        let vm = this;
        vm.switchDisabled = newval;
      },
      immediate: true,
      deep: true
    }
  },
  created() {
    let vm = this;
    vm.init();
  },
  methods: {
    // ============================================ 初始化信息 start============================================
    // 初始化信息
    init(){
      let vm = this;

      vm.$nextTick(function () {
        if(vm.currentTransmitData.pageContent == "A6" && vm.currentTransmitData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'){
          vm.$refs['a6'].showT1 = false;// 造价部
          vm.$refs['a6'].title = "咨询工作记录";// 表格名称
        }else if(vm.currentTransmitData.pageContent == "A6" && vm.currentTransmitData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'){
          vm.$refs['a6'].showT1 = true;// 招标代理部
          vm.$refs['a6'].title = "招标规划";// 表格名称
        }
      });

      vm.taskId = vm.currentTransmitData.taskId;

      if(vm.currentTransmitData.periodType === "6"){
        // 查询专家论证意见表详情
        vm.searchExpertOpinionRelationDetail();
      }else if(vm.currentTransmitData.periodType === "7"){
        // 查询招标文件确认意见表和任务的关联表详情
        vm.searchFileConfirmOpinionRelationDetail();
      }else if(vm.currentTransmitData.periodType === "8"){
        // 查询落标通知书和任务关联表详情
        vm.searchFailTenderNoticeRelationInfoDetail();
      }else if(vm.currentTransmitData.periodType === "9"){
        // 查询开标、评标详情
        vm.searchOpenBidInfoDetail();
      }

      // 如果是主提审人则可以编辑
      if(vm.currentTransmitData.isMainHeadFlag === "1"){
        vm.switchDisabled = false;
      } else {
        vm.switchDisabled = true;
      }

    },
    // ============================================ 初始化信息 end============================================
    /**
     * @method 专家意见表提交表单
     */
    rxpertOpinionListSubmit(promise){
      let vm = this;
      // 获取表单数据
      promise.then(function(data){
        // 设置任务id
        Object.assign(data,{"taskId":vm.taskId});
        vm.apiAddress('/task/add/insertExpertOpinionRelationInfo', data).then(res => {
          if (res.data.status == 'success') {
            vm.stateDialog('success', res.data.message);
            // 查询专家论证意见表详情
            vm.searchExpertOpinionRelationDetail();
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },function(err){
        console.log('没有通过验证',err);
      });
    },
    /**
     * @method 磋商文件确认文件意见提交表单
     */
    consultationDocumentConfirmationDocumentOpinionSubmit(promise){
      let vm = this;
      // 获取表单数据
      promise.then(function(data){
        // 设置任务id
        Object.assign(data,{"taskId":vm.taskId});
        vm.apiAddress('/task/add/insertFileConfirmOpinionRelationInfo', data).then(res => {
          if (res.data.status == 'success') {
            vm.stateDialog('success', res.data.message);
            // 查询招标文件确认意见表和任务的关联表详情
            vm.searchFileConfirmOpinionRelationDetail();
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },function(err){
        console.log('没有通过验证',err);
      });
    },
    /**
     * @method 未成交通知书提交表单
     */
    unfilledNoticeSubmit(promise){
      let vm = this;
      // 获取表单数据
      promise.then(function(data){
        // 设置任务id
        Object.assign(data,{"taskId":vm.taskId});
        vm.apiAddress('/task/add/insertFailTenderNoticeRelationInfo', data).then(res => {
          if (res.data.status == 'success') {
            vm.stateDialog('success', res.data.message);
            // 查询落标通知书和任务关联表详情
            vm.searchFailTenderNoticeRelationInfoDetail();
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },function(err){
        console.log('没有通过验证',err);
      });
    },
    /**
     * @method 开标、评标提交表单
     */
    openBidSubmit(promise){
      let vm = this;
      // 获取表单数据
      promise.then(function(data){
        // 设置任务id
        Object.assign(data,{"taskId":vm.taskId});
        vm.apiAddress('/task/add/insertOpenBidInfo', data).then(res => {
          if (res.data.status == 'success') {
            vm.stateDialog('success', res.data.message);
            // 查询开标、评标详情
            vm.searchOpenBidInfoDetail();
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },function(err){
        console.log('没有通过验证',err);
      });
    },
    /**
     * @method 查询专家论证意见表详情
     */
    searchExpertOpinionRelationDetail(){
      let vm = this;
      vm.apiAddress('/task/query/searchExpertOpinionRelationDetail', {"taskId":vm.taskId}).then(res => {
        if (res.data.status == 'success') {
          // 赋值
          vm.rxpertOpinionListForm = {
            form:res.data.dataVO
          };
        } else {
          vm.stateDialog('error', res.data.message);
        }
      }).catch(err => {
        vm.stateDialog('error', err);
      });
    },
    /**
     * @method 查询招标文件确认意见表和任务的关联表详情
     */
    searchFileConfirmOpinionRelationDetail(){
      let vm = this;
      vm.apiAddress('/task/query/searchFileConfirmOpinionRelationDetail', {"taskId":vm.taskId}).then(res => {
        if (res.data.status == 'success') {
          // 赋值
          vm.consultationDocumentConfirmationDocumentOpinionForm = {
            form:res.data.dataVO
          };
        } else {
          vm.stateDialog('error', res.data.message);
        }
      }).catch(err => {
        vm.stateDialog('error', err);
      });
    },
    /**
     * @method 查询落标通知书和任务关联表详情
     */
    searchFailTenderNoticeRelationInfoDetail(){
      let vm = this;
      vm.apiAddress('/task/query/searchFailTenderNoticeRelationInfoDetail', {"taskId":vm.taskId}).then(res => {
        if (res.data.status == 'success') {
          // 赋值
          vm.unfilledNoticeForm = {
            form:res.data.dataVO
          };
        } else {
          vm.stateDialog('error', res.data.message);
        }
      }).catch(err => {
        vm.stateDialog('error', err);
      });
    },
    /**
     * @method 查询开标、评标详情
     */
    searchOpenBidInfoDetail(){
      let vm = this;
      vm.apiAddress('/task/query/searchOpenBidInfoDetail', {"taskId":vm.taskId}).then(res => {
        if (res.data.status == 'success') {
          // 赋值
          vm.openBidForm = {
            form:res.data.dataVO
          };
        } else {
          vm.stateDialog('error', res.data.message);
        }
      }).catch(err => {
        vm.stateDialog('error', err);
      });
    }
  }
}
</script>
<style>
@import url('../../../../common/css/other.css');
</style>
