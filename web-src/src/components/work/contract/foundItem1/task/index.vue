<template>
  <!-- 任务主组件-->
  <div class="foundItem-task">

    <!-- 中间视图 -->
    <div class="center--work-contract">

      <!-- A3 -->
      <a3PhasesIndex v-if="currentTransmitData.periodType == 2"
      :currentTransmitData="currentTransmitData"
      :office="office"
      :correctTaskMaterialFlag="correctTaskMaterialFlag"></a3PhasesIndex>

      <!-- 成果文件 -->
      <PreparationIndex v-if="currentTransmitData.periodType == 3"
      :currentTransmitData="currentTransmitData"
      :office="office"
      :correctTaskMaterialFlag="correctTaskMaterialFlag"
      :disabled="disabled"></PreparationIndex>

      <!-- 项目监理 -->
      <projectSupervisionIndex v-if="currentTransmitData.periodType == 5"
      :currentTransmitData="currentTransmitData"></projectSupervisionIndex>

	    <!-- 其他阶段 -->
	    <otherPhasesIndex v-if="currentTransmitData.periodType != 2 && currentTransmitData.periodType != 3
      && currentTransmitData.periodType != 5 && currentTransmitData.treeNodeLevel == 6"
	    :currentTransmitData="currentTransmitData"
      :disabled="disabled"></otherPhasesIndex>

      <!-- 点击任务 -->
      <TaskDetails v-if="currentTransmitData.treeNodeLevel == 5" :currentTransmitData="currentTransmitData"></TaskDetails>

    </div>
    <!-- 右侧视图 -->
    <div width="200px" class="right--work-contract">

      <!-- A3 -->
      <a3PhasesRight v-if="currentTransmitData.periodType == 2"
      :currentTransmitData="currentTransmitData"
      :postil="postil"
      :jurisdiction="jurisdiction"
      :typeFlag="typeFlag"
      :errorTyleList="errorTyleList"
      :auditFileList="auditFileList"></a3PhasesRight>

      <!-- 成果文件 -->
      <PreparationRight v-if="currentTransmitData.periodType == 3 && shwoChild"
      :currentTransmitData="currentTransmitData"
      :postil="postil"
      :jurisdiction="jurisdiction"
      :typeFlag="typeFlag"
      :errorTyleList="errorTyleList"
      :auditFileList="auditFileList"></PreparationRight>

      <!-- 项目监理 -->
	    <projectSupervisionRight v-if="currentTransmitData.periodType == 5"
      :currentTransmitData="currentTransmitData" :typeFlag="typeFlag"></projectSupervisionRight>

	    <!-- 其他阶段 -->
	    <otherPhasesRight v-if="currentTransmitData.periodType != 2 && currentTransmitData.periodType != 3
      && currentTransmitData.periodType != 5 && currentTransmitData.treeNodeLevel == 6"
	    :currentTransmitData="currentTransmitData" :typeFlag="typeFlag"></otherPhasesRight>

    </div>

  </div>
</template>

<script>
  import a3PhasesIndex from './a3Phases/index.vue'; /* A3主页面 */
  import a3PhasesRight from './a3Phases/right.vue'; /* A3右边*/
  import otherPhasesIndex from './otherPhases/index.vue'; /* 其他阶段主页面 */
  import otherPhasesRight from './otherPhases/right.vue'; /* 其他阶段右边 */
  import PreparationIndex from './Preparation/index.vue'; /* 成果文件主页面 */
  import PreparationRight from './Preparation/right.vue'; /* 成果文件右边 */
  import projectSupervisionIndex from './projectSupervision/index.vue'; /* 项目监理主页面 */
  import projectSupervisionRight from './projectSupervision/right.vue'; /* 项目监理右边 */
  import TaskDetails from './details.vue'; /* 任务页面 */

  export default {
    components: {
      a3PhasesIndex /* A3主页面 */,
      a3PhasesRight /* A3右边*/,
      otherPhasesIndex /* 其他阶段主页面 */,
      otherPhasesRight /* 其他阶段右边 */,
      PreparationIndex /* 成果文件主页面 */,
      PreparationRight /* 成果文件右边 */,
      projectSupervisionIndex /* 项目监理主页面 */,
      projectSupervisionRight /* 项目监理右边 */,
      TaskDetails
    },
    props: ['transmittedData'],
    data() {
      return {
        currentTransmitData: this.tool.deepCopy(Object.assign({}, this.transmittedData), this.tool.deepCopy) /* 被点击的左边栏的信息 */,
        office: {
          officeHeight: '700px', // onlyOffice文件的高度
          showOnlyOfficeCompFlag: false, // 是否显示onlyOffice
          taskMaterialInfo: null // office文件信息
        },
        jurisdiction:{},/* 完整权限信息 */
        typeFlag:'',/* 当前人权限 0详情 1主提审人 15其他组员 20提审完成状态  2 一级审核 3 二级审核 4三级审核 5签发 */
        postil: {} /* 批注信息 */,
        correctTaskMaterialFlag: false /* A3表或者编制成果文件是否显示（批注） */,
        errorTyleList: [], // 错误类型列表
        auditFileList: [], /* 审核意见文件列表 */
        disabled: false /* 编制成果人表单禁用 */,
        showImg: false,
        shwoChild:false,// 显示子页面
      }
    },
    watch: {
      transmittedData:{
        handler(newval){
          let vm = this;
          vm.currentTransmitData = newval;
          vm.init();
        },
        immediate: true,
        deep: true,
      },
      correctTaskMaterialFlag: {
        handler() {
          if (this.correctTaskMaterialFlag) {
            if (this.currentTransmitData.periodType === '2') {// A3阶段
              this.searchMaterialByTaskPeriodId();// A3 查询office文件信息
            } else if (this.currentTransmitData.periodType === '3') {// 成果文件阶段
              this.searchOutcomeDocumentMaterial();// 成果文件 查询office文件信息
            }
          } else {
            this.office.showOnlyOfficeCompFlag = this.correctTaskMaterialFlag;
          }
        },
        deep: true,
        immediate: true
      }
    },
    methods: {
      // ================================================ 初始化信息 start================================================
      init() {
        let vm = this;
        // 请求控制按钮显示的数据typeFlag
        vm.apiAddress('/task/query/showBtnType', {
          periodType: vm.currentTransmitData.periodType,
          relationId: vm.currentTransmitData.relationId,
          taskId: vm.currentTransmitData.taskId
        }).then(res => {
          if (res.data.status == 'success') {

            // 任务详情
            vm.jurisdiction = res.data.dataVO;

            // 请求控制按钮显示的数据
            vm.typeFlag = res.data.typeFlag;

            // 当是主提审人的时候，可以输入页面；不是主提审人的时候，禁止页面输入
            if (vm.typeFlag == '1') {
              vm.disabled = false;
            } else {
              vm.disabled = true;
            }
            vm.correctTaskMaterialFlag = vm.typeFlag === '2' || vm.typeFlag === '3' || vm.typeFlag === '4' || vm.typeFlag === '5';

            // 显示子页面（当typeFlag的值查询出来之后再显示子页面，避免子页面提前加载显示）
            vm.shwoChild = true;
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });

        if (vm.currentTransmitData.periodType === '2') {// A3阶段
          vm.searchQuestionList(vm.currentTransmitData.departmentId);// 查询问题类型列表信息
          vm.searchReviewFileRelationList(vm.currentTransmitData.relationId);// 查询A3阶段文件信息
        } else if (vm.currentTransmitData.periodType === '3') {// 成果文件阶段
          vm.searchQuestionList(vm.currentTransmitData.departmentId);// 查询问题类型列表信息
          vm.searchReviewFileRelationList(vm.currentTransmitData.taskId);// 查询成果文件阶段文件信息
        }
      },
      // ================================================ 初始化信息 end================================================
      // ================================================ A3 查询office文件信息 end================================================
      // 查询office文件信息
      searchMaterialByTaskPeriodId() {
        let vm = this;
        vm.apiAddress('/task/query/searchMaterialByTaskPeriodId', {
          taskPeriodId: vm.currentTransmitData.relationId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message);
          } else {
            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            vm.office.taskMaterialInfo = {
              fileName: taskMaterialInfo.reviewFileName,
              key: taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl: taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: taskMaterialInfo.reviewFilePath
            };
            vm.postil.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            vm.postil.reviewFilePath = taskMaterialInfo.reviewFilePath;

            vm.postil.reviewFileName = taskMaterialInfo.reviewFileName;

            // 显示office组件
            vm.office.showOnlyOfficeCompFlag = true;
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },
      // ================================================ A3 查询office文件信息 end================================================
      // ================================================ 成果文件 查询office文件信息 end================================================
      // 查询office文件信息
      searchOutcomeDocumentMaterial() {
        let vm = this;
        vm.apiAddress('/task/query/searchOutcomeDocumentMaterial', {
          taskId: vm.currentTransmitData.taskId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message);
          } else {
            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            vm.office.taskMaterialInfo = {
              fileName: taskMaterialInfo.reviewFileName,
              key: taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl: taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: taskMaterialInfo.reviewFilePath
            };

            vm.postil.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            vm.postil.reviewFilePath = taskMaterialInfo.reviewFilePath;

            vm.postil.reviewFileName = taskMaterialInfo.reviewFileName;

            // 没提交重新编辑申请的时候可以显示批注组件，如果提交了就不能显示批注组件，而显示A3组件
            if (taskMaterialInfo.isSubmitReedit !== '1' && taskMaterialInfo.reviewFileName !== '') {
              // 显示office组件
              vm.office.showOnlyOfficeCompFlag = true;
            } else {
              // 可以显示A3组件
              vm.correctTaskMaterialFlag = false;
            }
          }
        })
        .catch(err => {
          vm.stateDialog('error', err);
        });
      },
      // ================================================ 成果文件 查询office文件信息 end================================================
      // ================================================ 查询问题列表 end================================================
      searchQuestionList(departmentId) {
        /* 获取错误类型数据*/
        let vm = this;
        vm.apiAddress('/task/query/searchQuestionList', {
          departmentId: departmentId
        }).then(res => {
          if (res.data.status == 'success') {
            // 返回的问题列表
            let questionList = res.data.dataList;
            // 循环遍历问题列表
            for (var i = questionList.length - 1; i >= 0; i--) {
              questionList[i].questionNum = 0;
            }
            vm.errorTyleList = questionList;
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },
      // ================================================ 查询问题列表 end================================================
      // ================================================ 查询审核意见文件列表 end================================================
      // 查询审核意见文件列表
      searchReviewFileRelationList(businessId) {
        let vm = this;
        vm.apiAddress('/commonModule/query/searchReviewFileRelationList', {
          businessId: businessId
        }).then(res => {
          if (res.data.status == 'success') {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileId/g, 'materialId'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFileName/g, 'materialName'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFilePath/g, 'materialPath'));
            }
            vm.auditFileList = dataList;
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },
      // ================================================ 查询审核意见文件列表 end================================================
    }
  }
</script>
<style lang="scss">
.foundItem-task {
  .el-form-item__error--inline {
    position: absolute!important;
    top: 100%;
    left: 0 !important;
  }
  .item-serial-num {
    float: right;
    color: #b3b3b3;
  }
  .card-right {
    float: right;
  }

  .card-right .el-select {
    width: 120px;
    height: 24px;
  }
  .box-card {
    margin: 10px 0;
    padding: 15px 20px;
    background-color: #f1f5f8;
    border: none;
    width: 100%;
    display: inline-block;
    vertical-align: middle;
    box-shadow: none !important;
    overflow: visible;
  }

  .box-card .el-card__header {
    border-bottom: 1px solid #e3e3e3;
    padding: 10px 0;
  }

  .box-card .el-card__header .clearfix span:first-child {
    float: left;
  }

  .box-card .el-card__body {
    padding: 10px 0;
  }

  .box-card .el-card__body .el-form-item,
  .item-serial .el-form-item {
    display: inline-block;
    margin-bottom: 0px;
  }

  .box-card .el-card__body .el-form-item label {
    width: 58px !important;
    font-size: 12px;
    color: #424242;
    padding: 0;
  }
  .item-serial .el-form-item label {
    width: 50px !important;
    font-size: 12px;
    color: #424242;
    padding: 0;
  }

  .box-card .el-card__body .el-form-item .el-form-item__content,
  .item-serial .el-form-item .el-form-item__content {
    margin-left: 10px !important;
    float: left;
  }

  .box-card .el-card__body .el-form-item .el-form-item__content input,
  .item-serial .el-form-item .el-form-item__content input {
    width: 120px;
    height: 24px;
    line-height: 24px;
  }

  .box-card{
    .el-card__body{
      .el-form-item{
        /* 跨部门合作 */
        &.cooperateContract{

          label{
            width: auto!important;
          }
        }
      }
       .el-form-item__error--inline{
          top: 100%!important;
       }
    }
  }
}
</style>
<style lang="scss" scoped>
.foundItem-task {
  display: flex;
  height: 100%;
  width: 100%;
  background-color: #f7fafc;
  border-radius: 5px;

  /* 动态添加指定人员 */
  .dynamically-add{
    position:relative;

    /* 删除图标 */
    .el-icon-close{
      position: absolute;
      top: 15px;
      left: 195px;
    }
  }
}
</style>
