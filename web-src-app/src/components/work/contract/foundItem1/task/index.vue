<template>
  <!-- 任务主组件-->
  <div>
    <div v-if="currentTransmitData.treeNodeLevel == 5"><TaskDetails :currentTransmitData="currentTransmitData"></TaskDetails></div>
    <div v-if="currentTransmitData.treeNodeLevel == 6">
      <div v-if="currentTransmitData.periodType == 2">
        <!-- A3 -->
        <a3PhasesIndex :currentTransmitData="currentTransmitData" :office="office" :correctTaskMaterialFlag="correctTaskMaterialFlag"></a3PhasesIndex>
        <a3PhasesRight
          :currentTransmitData="currentTransmitData"
          :postil="postil"
          :jurisdiction="jurisdiction"
          :typeFlag="typeFlag"
          :errorTyleList="errorTyleList"
          :auditFileList="auditFileList"
        ></a3PhasesRight>
      </div>
      <div v-if="currentTransmitData.periodType == 3">
        <!-- 成果文件 -->
        <PreparationIndex :currentTransmitData="currentTransmitData" :office="office" :correctTaskMaterialFlag="correctTaskMaterialFlag" :disabled="disabled"></PreparationIndex>
        <PreparationRight
          :currentTransmitData="currentTransmitData"
          :postil="postil"
          :jurisdiction="jurisdiction"
          :typeFlag="typeFlag"
          :errorTyleList="errorTyleList"
          :auditFileList="auditFileList"
        ></PreparationRight>
      </div>
      <div v-if="currentTransmitData.periodType == 5">
        <!-- 项目监理 -->
        <projectSupervisionIndex :currentTransmitData="currentTransmitData"></projectSupervisionIndex>
        <projectSupervisionRight :currentTransmitData="currentTransmitData" :typeFlag="typeFlag"></projectSupervisionRight>
      </div>
      <div v-if="currentTransmitData.periodType != 2 && currentTransmitData.periodType != 3 && currentTransmitData.periodType != 5">
        <!-- 其他阶段 -->
        <otherPhasesIndex :currentTransmitData="currentTransmitData"></otherPhasesIndex>
        <otherPhasesRight :currentTransmitData="currentTransmitData" :typeFlag="typeFlag" :jurisdiction="jurisdiction"></otherPhasesRight>
      </div>
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
  data() {
    return {
      list: this.SStorage.getItem(this.$route.path),
      currentTransmitData: this.tool.deepCopy(Object.assign({}, this.transmitData), this.tool.deepCopy) /* 被点击的左边栏的信息 */,
      jurisdiction: {} /* 完整权限信息 */,
      typeFlag: '' /* 当前人权限 0详情 1主提审人 15其他组员 20提审完成状态  2 一级审核 3 二级审核 4三级审核 5签发 */,
      postil: {} /* 批注信息 */,
      correctTaskMaterialFlag: false /* A3表或者编制成果文件是否显示（批注） */,
      office: {
        officeHeight: '700px', // onlyOffice文件的高度
        showOnlyOfficeCompFlag: false, // 是否显示onlyOffice
        taskMaterialInfo: null // office文件信息
      },
      errorTyleList: [], // 错误类型列表
      auditFileList: [] /* 审核意见文件列表 */,
      disabled: false /* 编制成果人表单禁用 */
    };
  },
  props: ['transmitData'] /* 被点击的左边栏的信息 */,
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
  watch: {
    transmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        let vm = this;
        vm.currentTransmitData = newval;
        this.init();
      },
      immeditate: true
    },
    correctTaskMaterialFlag: {
      handler() {
        if (this.correctTaskMaterialFlag) {
          // A3阶段
          if (this.currentTransmitData.periodType === '2') {
            // 查询A3阶段文件信息
            this.searchMaterialByTaskPeriodId();
            // 成果文件阶段
          } else if (this.currentTransmitData.periodType === '3') {
            // 查询成果文件阶段文件信息
            this.searchOutcomeDocumentMaterial();
          }
        } else {
          this.office.showOnlyOfficeCompFlag = this.correctTaskMaterialFlag;
        }
      },
      deep: true,
      immediate: true
    }
  },
  created() {
    let vm = this;
    console.log("transmitData",this.transmitData)
    this.init();
  },
  methods: {
    init() {
      let vm = this;
      /* 请求控制按钮显示的数据typeFlag*/
      this.requestPost('/task/query/showBtnType', {
        periodType: this.currentTransmitData.periodType,
        relationId: this.currentTransmitData.relationId,
        taskId: this.currentTransmitData.taskId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.jurisdiction = res.data.dataVO;
            this.typeFlag = res.data.typeFlag;
            if (this.typeFlag == '1') {
              this.disabled = false;
            } else {
              this.disabled = true;
            }
            this.correctTaskMaterialFlag = this.typeFlag === '2' || this.typeFlag === '3' || this.typeFlag === '4' || this.typeFlag === '5';
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
      this.searchQuestionList(this.currentTransmitData.departmentId);
      // A3阶段
      if (this.currentTransmitData.periodType === '2') {
        this.searchReviewFileRelationList(this.currentTransmitData.relationId);
        // 成果文件阶段
      } else if (this.currentTransmitData.periodType === '3') {
        // 查询成果文件阶段文件信息
        this.searchReviewFileRelationList(this.currentTransmitData.taskId);
      }
      if (this.currentTransmitData.treeNodeLevel == '5') {
        // 查询成果文件阶段文件信息
        this.getTaskDetails(this.currentTransmitData);
      }
    },
    /* 查询任务信息*/
    getTaskDetails(val) {
      const vm = this;
      // 根据任务id查询合同内容
      this.requestPost('/task/query/searchTaskContent', {
        // 任务id
        taskId: val.id
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            vm.currentTransmitData = Object.assign({}, vm.currentTransmitData, {
              contractNumber: res.data.dataVO.contractNum, // 合同编号
              createDate: res.data.dataVO.createDate, // 创建时间
              taskName: res.data.dataVO.taskName, // 任务名称
              showPeriodDescFlg: false, //隐藏工作步骤
              projectNum: vm.list.projectNum,
              counselTypeName:res.data.dataVO.counselTypeName,
              contractTypeName:res.data.dataVO.contractTypeName,
            });
            // 如果有数据，则显示任务描述
            if (res.data.dataVO.taskContent != '') {
              // 显示任务说明
              vm.currentTransmitData.taskContent = true;
              // 设置任务说明
              vm.currentTransmitData.taskDesc = res.data.dataVO.taskContent;
            } else {
              // 隐藏任务说明
              vm.currentTransmitData.taskContent = false;
            }
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },

    // ============================================================================== A3 查询office文件信息 end==============================================================================
    // 查询office文件信息
    searchMaterialByTaskPeriodId() {
      this.requestPost('/task/query/searchMaterialByTaskPeriodId', {
        taskPeriodId: this.currentTransmitData.relationId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            this.office.taskMaterialInfo = {
              fileName: taskMaterialInfo.reviewFileName,
              key: taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl: taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: taskMaterialInfo.reviewFilePath
            };
            this.postil.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            this.postil.reviewFilePath = taskMaterialInfo.reviewFilePath;

            this.postil.reviewFileName = taskMaterialInfo.reviewFileName;

            // 显示office组件
            this.office.showOnlyOfficeCompFlag = true;
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ============================================================================== A3 查询office文件信息 end=========
    // ============================================================================== 成果文件 查询office文件信息 end==============================================================================
    // 查询office文件信息
    searchOutcomeDocumentMaterial() {
      this.requestPost('/task/query/searchOutcomeDocumentMaterial', {
        taskId: this.currentTransmitData.taskId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // office信息变量
            let taskMaterialInfo = res.data.dataVO;

            // office组件需要传入的参数
            this.office.taskMaterialInfo = {
              fileName: taskMaterialInfo.reviewFileName,
              key: taskMaterialInfo.onlyOfficeEditKey,
              downloadUrl: taskMaterialInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: taskMaterialInfo.reviewFilePath
            };

            this.postil.onlyOfficeEditKey = taskMaterialInfo.onlyOfficeEditKey;

            this.postil.reviewFilePath = taskMaterialInfo.reviewFilePath;

            this.postil.reviewFileName = taskMaterialInfo.reviewFileName;

            // 没提交重新编辑申请的时候可以显示批注组件，如果提交了就不能显示批注组件，而显示A3组件
            if (taskMaterialInfo.isSubmitReedit !== '1' && taskMaterialInfo.reviewFileName !== '') {
              // 显示office组件
              this.office.showOnlyOfficeCompFlag = true;
            } else {
              // 可以显示A3组件
              this.correctTaskMaterialFlag = false;
            }
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ============================================================================== 成果文件 查询office文件信息 end==========
    searchQuestionList(departmentId) {
      /* 获取错误类型数据*/
      this.requestPost('/task/query/searchQuestionList', {
        departmentId: departmentId
      })
        .then(res => {
          if (res.data.status == 'success') {
            // 返回的问题列表
            let questionList = res.data.dataList;
            // 循环遍历问题列表
            for (var i = questionList.length - 1; i >= 0; i--) {
              questionList[i].questionNum = 0;
            }
            this.errorTyleList = questionList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 查询审核意见文件列表
    searchReviewFileRelationList(businessId) {
      this.requestPost('/commonModule/query/searchReviewFileRelationList', {
        businessId: businessId
      })
        .then(res => {
          if (res.data.status == 'success') {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileId/g, 'materialId'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFileName/g, 'materialName'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFilePath/g, 'materialPath'));
            }
            this.auditFileList = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
<style>
@import url('../../../../more/common/css/fileList.css');
@import url('../../../../more/common/css/foldedPanel.css');
</style>
