<template>

  <div class="task">
    <div class="form-block" v-if="currentTransmittedData.showProjectNumFlg">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">项目编号:{{currentTransmittedData.projectNumber}}</p>
        </el-col>
        <el-col :span="12" style="padding-left: 15px;">
          <p style="font-size: 12px;">合同编号:{{currentTransmittedData.contractNumber}}</p>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">任务名称:{{currentTransmittedData.taskName}}</p>
        </el-col>
        <el-col :span="12" style="padding-left: 15px;">
          <p style="font-size: 12px;">创建时间:{{currentTransmittedData.createDate}}</p>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">合同类型:-</p>
        </el-col>
        <el-col :span="12" style="padding-left: 15px;">
          <p style="font-size: 12px;">咨询类别:{{currentTransmittedData.counselTypeName}}</p>
        </el-col>
      </el-row>
    </div>

    <div class="img-failure" v-if="showImg">
      <img v-bind:src="imgSrc" :alt="imgAlt" />
      <p style="text-align: center;">{{ imgAlt }}</p>
    </div>

    <div class="title form-block" v-if="transmittedData.taskContent">
      <el-row :gutter="gutter">
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">任务说明:</p>
        </el-col>
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">{{transmittedData.taskDesc}}</p>
        </el-col>
      </el-row>
    </div>

    <div class="title form-block" v-if='transmittedData.showPeriodDescFlg && !(showOnlyOfficeCompFlag && (currentTransmittedData.periodType === "2" || currentTransmittedData.periodType === "3"))'>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">工作步骤说明:</p>
        </el-col>
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">{{transmittedData.periodDesc}}</p>
        </el-col>
      </el-row>
    </div>

    <!-- A3组件 -->
    <div class="form-block form-block__row--auto-width" v-if="A3Flg && !correctTaskMaterialFlag">
      <a3 :projectName='projectName' :contractNum='contractNum' :outcomeDocNo="outcomeDocNo" :tableListData='tableListData'></a3>
    </div>

    <!-- onlyOffice -->
    <div class="form-block form-block__row--auto-width" v-if='showOnlyOfficeCompFlag && (currentTransmittedData.periodType === "2" || currentTransmittedData.periodType === "3")' style="height: 700px;">
      <office :file="taskMaterialInfo"></office>
    </div>

    <!-- 人员调整 -->
    <personnel-adjustment :transmittedData="currentTransmittedData" v-if="currentTransmittedData.periodType == 4"></personnel-adjustment>

    <!-- A4组件 -->
    <div class="form-block form-block__row--auto-width" v-if="currentTransmittedData.periodType == 1 && currentTransmittedData.pageContent == 'A4'">
      <a4 :transmittedData="currentTransmittedData"></a4>
    </div>

    <!-- A7(招标工作进度计划)组件 -->
    <div class="form-block form-block__row--auto-width" v-if="currentTransmittedData.pageContent == 'A7'">
      <a7 :transmittedData="currentTransmittedData"></a7>
    </div>

    <!-- 成果文件组件 -->
    <div class="form-block form-block__row--auto-width" style="background-color: white;" v-if="currentTransmittedData.periodType == '3' && !correctTaskMaterialFlag">
      <prepare ref="prepare" :transmittedData="currentTransmittedData" :disabled="disabled"></prepare>
    </div>

    <!-- A5组件 -->
    <div class="form-block form-block__row--auto-width" v-if="this.currentTransmittedData.pageContent == 'A5'">
      <a5 :projectName='currentTransmittedData.projectNumber' :contractNum='currentTransmittedData.contractNumber' :transmittedData='currentTransmittedData'></a5>
    </div>

    <!-- A8组件(招标代理) -->
    <div class="form-block" v-if="this.currentTransmittedData.pageContent == 'A8'&& this.currentTransmittedData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'">
      <a8_zbdl :transmittedData='currentTransmittedData'></a8_zbdl>
    </div>

    <!-- A8组件(造价部) -->
    <div class="form-block" v-if="this.currentTransmittedData.pageContent == 'A8'&& this.currentTransmittedData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'">
      <a8_zj :transmittedData='currentTransmittedData'></a8_zj>
    </div>

    <!-- A6组件(造价部)  -->
    <div class="form-block" v-if="this.currentTransmittedData.pageContent == 'A6'">
      <a6 :transmittedData='currentTransmittedData' ref="a6"></a6>
    </div>

    <!-- 项目监理部列表 -->
    <div class="form-block" style="padding-bottom:56px;" v-if='currentTransmittedData.periodType === "5"'>
      <div>
          <el-table :data="tableData" border style="width: 100%;">
            <el-table-column align="center" prop="orderNumber" label="序号"></el-table-column>
            <el-table-column align="center" prop="fileNumber" label="档案编号"></el-table-column>
            <el-table-column align="center" prop="approveTime" label="归档时间"></el-table-column>
            <el-table-column align="center" prop="approveUserName" label="归档人"></el-table-column>
          </el-table>
      </div>
      <!-- 分页 -->
      <div class="block item-fen" v-show="total > 0">
        <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
         :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
         :total="total"></el-pagination>
        <el-button class="linear" type="primary" size="small">确定</el-button>
      </div>
    </div>

    <!-- 专家意见表 -->
    <rxpert-opinion-list
    ref="rxpertOpinionList"
    v-if="currentTransmittedData.periodType == 6"
    :data="rxpertOpinionListForm" @submit="rxpertOpinionListSubmit" :disabled="disabled"></rxpert-opinion-list>

    <!-- 磋商文件确认文件意见 -->
    <consultation-document-confirmation-document-opinion
    ref="consultationDocumentConfirmationDocumentOpinion"
    v-if="currentTransmittedData.periodType == 7"
    :data="consultationDocumentConfirmationDocumentOpinionForm" @submit="consultationDocumentConfirmationDocumentOpinionSubmit" :disabled="disabled"></consultation-document-confirmation-document-opinion>

    <!-- 未成交通知书 -->
    <unfilled-notice
    ref="unfilledNotice"
    v-if="currentTransmittedData.periodType == 8"
    :data="unfilledNoticeForm" @submit="unfilledNoticeSubmit" :disabled="disabled"></unfilled-notice>

  </div>
</template>

<script>
  import personnelAdjustment from './personnelAdjustment/index';/* 人员调整 */

  import a3 from '../../../common/components/a3';  /* a3组件 */

  import a4 from '../../../../costOf/collectingData/cost-A4';  /* a4组件 */

  import a7 from '../../../../costOf/tender/cost-A7';  /* A7(招标工作进度计划)组件 */

  import prepare from '../../../../costOf/Prepare/cost-index';  /* 编制成果文件组件 */

  import a5 from '../../../../costOf/drawUpAWorkPlan/cost-A5';  /* a5组件 */

  import a6 from '../../../../costOf/modifyThePerfect/cost-A6';  /* a6组件 */

  import a8_zbdl from '../../../../costOf/bids/cost-A8';  /* A8组件(招标代理) */

  import a8_zj from '../../../../costOf/siteVisit/cost-A8Cost';  /* A8组件(造价部) */

  import office from '../../../common/components/office';/* onlyOffice组件 */

  import rxpertOpinionList from '../../../../costOf/rxpertOpinionList/index';  /* 专家意见表 */

  import consultationDocumentConfirmationDocumentOpinion from '../../../../costOf/consultationDocumentConfirmationDocumentOpinion/index';  /* 磋商文件确认文件意见 */

  import unfilledNotice from '../../../../costOf/unfilledNotice/index';  /* 未成交通知书 */

  export default {
    components: {
      // 人员调整
      'personnel-adjustment':personnelAdjustment,
      // A3
      'a3': a3,
      // A3
      'a4': a4,
      // A7
      'a7': a7,
      // 成果文件
      'prepare': prepare,
      // A8组件(招标代理)
      'a8_zbdl': a8_zbdl,
      // A8组件(招标代理)
      'a8_zj': a8_zj,
      //A5
      'a5': a5,
      //A6
      'a6': a6,
      //onlyOffice
      'office':office,
      // 专家意见表
      'rxpert-opinion-list':rxpertOpinionList,
      // 磋商文件确认文件意见
      'consultation-document-confirmation-document-opinion':consultationDocumentConfirmationDocumentOpinion,
      // 未成交通知书
      'unfilled-notice':unfilledNotice
    },
    props: ['transmittedData'],
    data() {
      return {
        disabled : false,
        showImg: false,
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
        // 是否显示onlyOffice
        showOnlyOfficeCompFlag:false,
        // office文件信息
        taskMaterialInfo:null,
        correctTaskMaterialFlag:false,
        // 传输数据的当前vue的备份
        currentTransmittedData:this.tool.deepCopy(this.transmittedData,this.tool.deepCopy),
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
    watch: {
      correctTaskMaterialFlag: {
        handler() {
          if (this.correctTaskMaterialFlag) {
            // A3阶段
            if(this.currentTransmittedData.periodType === "2"){
              // 查询A3阶段文件信息
              this.searchMaterialByTaskPeriodId();

              // 成果文件阶段
            } else if (this.currentTransmittedData.periodType === "3"){
              // 查询成果文件阶段文件信息
              this.searchOutcomeDocumentMaterial();
            }
          }else{
            this.showOnlyOfficeCompFlag = this.correctTaskMaterialFlag;
          }
        },
        deep: true,
        immediate: true
      },
      transmittedData:{
        handler(){
          let vm = this;
          vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData,vm.tool.deepCopy);
          vm.init();
        },
        immediate: true
      }
    },
    mounted() {
      let vm = this;
      // 监听禁用表单事件
      vm.bus.$on('disabled', val => {
        vm.disabled = val;
      });
    },
    methods: {
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
      },
      // 初始化信息
      init(){
        let vm = this;
        vm.correctTaskMaterialFlag = false;
        // 如果是A3阶段，则查询审核历史记录列表，并显示A3组件
        if(vm.currentTransmittedData.pageContent === "A3"){
          // 隐藏A3组件
          vm.A3Flg = false;
          // 根据任务阶段关联id查询审核意见列表
          vm.searchHTTaskHisTaskInst(vm.currentTransmittedData.relationId);
          // 根据任务阶段关联id查询项目编号合同编号成果文件编号
          vm.searchProjectNumContractNumFileNumberByTaskPeriodId(vm.currentTransmittedData.relationId);

        } else if(vm.currentTransmittedData.pageContent == "A6"){
          vm.$nextTick(function () {
            if(vm.currentTransmittedData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'){
              // 造价部
              vm.$refs['a6'].showT1 = false;
              // 表格名称
              vm.$refs['a6'].title = "咨询工作记录";
            }else if(vm.currentTransmittedData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'){
              // 招标代理部
              vm.$refs['a6'].showT1 = true;
              vm.$refs['a6'].title = "招标规划";
            }
          })
        } else {
          // 隐藏A3组件
          vm.A3Flg = false;
        }

        vm.taskId = vm.currentTransmittedData.taskId;

        // 查询当前人类别(用以展示按钮)
        vm.showTypeFlag(vm.currentTransmittedData.relationId,vm.currentTransmittedData.periodType,vm.currentTransmittedData.taskId);

        // 如果是项目监理归档阶段
        if(vm.currentTransmittedData.periodType === "5"){
          // 项目监理归档信息列表
          vm.searchArchiveListInfo(vm.taskId, 1, 10);
        }else if(vm.currentTransmittedData.periodType === "6"){
          // 查询专家论证意见表详情
          vm.searchExpertOpinionRelationDetail();
        }else if(vm.currentTransmittedData.periodType === "7"){
          // 查询招标文件确认意见表和任务的关联表详情
          vm.searchFileConfirmOpinionRelationDetail();
        }else if(vm.currentTransmittedData.periodType === "8"){
          // 查询落标通知书和任务关联表详情
          vm.searchFailTenderNoticeRelationInfoDetail();
        }else if(vm.currentTransmittedData.periodType === "9"){
          // 查询开标、评标信息详情
          vm.searchOpenBidInfoDetail();
        }

        // 如果是主提审人则可以编辑
        if(vm.currentTransmittedData.isMainHeadFlag === "1"){
          vm.disabled = false;
        } else {
          vm.disabled = true;
        }

      },
      // ============================================================================== 查询当前人类别 start==============================================================================
      //查询当前人类别(用以展示按钮)
      showTypeFlag(relationId, periodType,taskId) {
        this.apiAddress('/task/query/showBtnType', {
          // 任务阶段id
          "relationId": relationId,
          // 阶段类型
          "periodType": periodType,
          // 任务id
          "taskId": taskId
        }).then(res => {
          if (res.data.status == 'success') {
            // 用户类型,根据这个值去判断显示那些组件和按钮
            this.typeFlag = res.data.typeFlag;

            this.correctTaskMaterialFlag = this.typeFlag === "2" || this.typeFlag === "3" || this.typeFlag === "4" || this.typeFlag === "5";

          } else {
            this.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          this.stateDialog('error', err);
        })
      },
      // ============================================================================== 查询当前人类别 end==============================================================================
      // ============================================================================== 项目监理归档信息列表 start==============================================================================
      // 项目监理归档信息列表
      searchArchiveListInfo(taskId,pageNumber, pageSize) {
        this.apiAddress('/task/query/searchArchiveListInfo', {
          // 任务id
          "taskId": taskId,
          "pageNumber": pageNumber,
          "pageSize": pageSize
        }).then(res => {
          if (res.data.status == 'success') {
            // 列表数据
            this.tableData = res.data.pagedData.dataList;
            // 列表总条数
            this.total = res.data.pagedData.dataCount;
          } else {
            this.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          this.stateDialog('error', err);
        })
      },
      handleSizeChange(val) {
        // 每页条数切换
        this.pageSize = val;
        this.searchArchiveListInfo(this.taskId, this.currentPage, this.pageSize)
      },
      handleCurrentChange(val) {
        //页码切换
        this.currentPage = val;
        this.searchArchiveListInfo(this.taskId, this.currentPage, this.pageSize)
      },
      // ============================================================================== 项目监理归档信息列表 end==============================================================================
      // ============================================================================== 成果文件 查询office文件信息 end==============================================================================
      // 查询office文件信息
      searchOutcomeDocumentMaterial() {
        this.apiAddress('/task/query/searchOutcomeDocumentMaterial', {
          "taskId": this.currentTransmittedData.taskId
        }).then(res => {
          if (res.data.status == 'failure') {
            this.stateDialog('error', res.data.message)
          } else {

            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            this.taskMaterialInfo = {
              fileName:taskMaterialInfo.reviewFileName,
              key:taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl:taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl:taskMaterialInfo.reviewFilePath,
            };

            this.$parent.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            this.$parent.reviewFilePath = taskMaterialInfo.reviewFilePath;

            this.$parent.reviewFileName = taskMaterialInfo.reviewFileName;

            // 没提交重新编辑申请的时候可以显示批注组件，如果提交了就不能显示批注组件，而显示A3组件
            if(taskMaterialInfo.isSubmitReedit !== "1" && taskMaterialInfo.reviewFileName !== ""){
              // 显示office组件
              this.showOnlyOfficeCompFlag = true;
            } else {
              // 可以显示A3组件
              this.correctTaskMaterialFlag = false;
            }

          }
        }).catch(err => {
          this.stateDialog('error', err)
        })
      },
      // ============================================================================== 成果文件 查询office文件信息 end==============================================================================
      // ============================================================================== A3 查询office文件信息 end==============================================================================
      // 查询office文件信息
      searchMaterialByTaskPeriodId() {
        this.apiAddress('/task/query/searchMaterialByTaskPeriodId', {
          "taskPeriodId": this.currentTransmittedData.relationId
        }).then(res => {
          if (res.data.status == 'failure') {
            this.stateDialog('error', res.data.message)
          } else {

            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            this.taskMaterialInfo = {
              fileName:taskMaterialInfo.reviewFileName,
              key:taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl:taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl:taskMaterialInfo.reviewFilePath,
            };

            this.$parent.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            this.$parent.reviewFilePath = taskMaterialInfo.reviewFilePath;

            this.$parent.reviewFileName = taskMaterialInfo.reviewFileName;

            // 显示office组件
            this.showOnlyOfficeCompFlag = true;

          }
        }).catch(err => {
          this.stateDialog('error', err)
        })
      },
      // ============================================================================== A3 查询office文件信息 end==============================================================================
      // 根据任务阶段关联id查询项目编号合同编号成果文件编号
      searchProjectNumContractNumFileNumberByTaskPeriodId(relationId){
        this.apiAddress('/task/query/searchProjectNumContractNumFileNumberByTaskPeriodId', {
          relationId: relationId,
        }).then(
          res => {
            if (res.data.status == 'failure') {
              this.stateDialog('error', res.data.message)
            } else {
              // 项目名称
              this.projectName = res.data.dataVO.projectName;
              // 合同编号
              this.contractNum = res.data.dataVO.contractNum;
              // 成果文件编号
              this.outcomeDocNo = res.data.dataVO.reportNum;
            }
          }).catch(err => {
            this.stateDialog('error', err)
        });
      },
      //查询审核意见列表
      searchHTTaskHisTaskInst(relationId){

        let vm = this;
        //查询审核意见列表
        vm.apiAddress('/task/query/searchHTTaskHisTaskInst', {
          relationId: relationId,
        }).then(
          res => {
            if (res.data.status == 'failure') {
              this.stateDialog('error', res.data.message)
            } else {

              /*------------------------------格式转换START---------------------------------*/

              let dataList = res.data.dataList;

              //for循环遍历更改属性名
              for (var i = 0 ; i < dataList.length ; i++) {

                dataList[i].title = vm.getStepName(dataList[i].taskKey)

                //将属性名htTaskPeriodVOList转换成属性名children
                dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htTaskPeriodVOList/g,"children"));

                // 获取转换格式后的children
                let children = dataList[i].children;

                // 循环遍历历史列表
                for (var j = 0 ; j < children.length ; j++) {
                  //将属性名approveTime转换成属性名time
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g,"time"));
                  //将属性名approveUserName转换成属性名name
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g,"name"));
                  //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,"persions"));
                  //将属性名rejectReason转换成属性名opinion
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g,"opinion"));

                  let persions = vm.groupBy(children[j].persions,function(item){
                    return[item.userId]
                  })

                  for (var k = 0 ; k < persions.length ; k++) {
                    persions[k].name = persions[k].dataList[0].userName
                    //将属性名dataList转换成属性名errorList
                    persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g,"errorList"));
                    let errorList = persions[k].errorList;
                    for (var l = errorList.length - 1; l >= 0; l--) {
                    //将属性名questionName转换成属性名type
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g,"type"));
                    //将属性名questionNum转换成属性名num
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g,"num"));
                    }
                    persions[k].errorList = errorList;
                  }
                  children[j].persions = persions;
                }
              }

              /*---------------------------------格式转换END-------------------------------------*/

              // 把转换后的dataList赋值给opinionList
              vm.tableListData = dataList;

              // 如果是A3阶段，则显示A3组件
              if(this.currentTransmittedData.pageContent == "A3"){
                this.A3Flg = true;
              }

            }
          }).catch(err => {
            this.stateDialog('error', err)
        });
      },

    }
  }
</script>
<style>
  @import url('../../../common/css/other.css');

  /* 文件列表*/
  .task {}

</style>
