<template>
  <div class="right more-sublevel">
    <!-- 上传下载删除-->
    <el-collapse style="border-radius:5px;">
      <!-- 普通阶段文件上传下载删除-->
      <collapseUpAndDownload
        :title="UpAndDownloadTitle"
        v-if="treeList.treeNodeLevel == 6 && periodType != 5"
        :showUploadBtn="showUploadBtnFlg"
        :delBtnFlag="!delBtnFlag"
        @uploadFile="uploadFile"
        @del="deleteFileInfo"
        :multiple="true"
        :files="files"
        :type="type"
        :id="treeList.taskId"
      ></collapseUpAndDownload>
      <!--归档文件-->
      <collapseUpAndDownload
        title="归档文件"
        v-if="
          (((typeFlag === '0' || ((typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit === '1')) && Archivefiles.length > 0) ||
            typeFlag === '6' ||
            ((typeFlag === '7' || typeFlag === '8') && Archivefiles.length > 0)) &&
            periodType === '3'
        "
        :showUploadBtn="typeFlag === '6' && periodType === '3'"
        :delBtnFlag="typeFlag === '6' && periodType === '3'"
        @uploadFile="uploadArchiveFile"
        @del="deleteArchiveFileInfo"
        :files="Archivefiles"
      ></collapseUpAndDownload>
      <input type="file" id="Archivefile" @change="ArchivefileChange($event)" style="display: none;" />
      <collapseUpAndDownload title="相关资料" :multiple="true" :files="reviewFileList" v-if="reviewFileList.length > 0 && periodType === '3'"></collapseUpAndDownload>
      <collapseUpAndDownload
        title="审核意见文件"
        :multiple="true"
        :files="auditFileList"
        v-if="isMainHeadFlag == '1' && auditFileList.length > 0 && (typeFlag == 1 || typeFlag == 15 || typeFlag == 0) && (periodType === '2' || periodType === '3')"
      ></collapseUpAndDownload>
      <!-- 审核意见读写-->
      <!-- A3和成果文件的审核-->
      <div class="primary" v-if="(periodType === '2' || periodType === '3') && typeFlag !== '20'">
        <!-- 只读-->
        <template v-for="(item, i) in opinionList">
          <collapse-review-read :object="item"></collapse-review-read>
        </template>
      </div>
      <div
        class="primary"
        v-if="(typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && (periodType === '2' || (periodType === '3' && isSubmitReedit !== '1'))"
      >
        <!-- 可写-->
        <collapseReviewWrite ref="write" :object="object" @selecError="selecError"></collapseReviewWrite>
      </div>

      <!-- 重新编辑审核历史记录 -->
      <div
        class="primary reedit"
        v-for="(obj, j) in reeditApplicationList"
        v-if="
          reeditApplicationList.length &&
            (((typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit === '1') ||
              (typeFlag === '6' && isSubmitReedit !== '1') ||
              (typeFlag === '0' || typeFlag === '15' || typeFlag === '1' || typeFlag === '7' || typeFlag === '8')) &&
            periodType === '3'
        "
      >
        <p>重新编辑审核意见</p>
        <!-- 只读-->
        <template v-for="(item, i) in obj.htTaskPeriodVOList">
          <collapseReviewReadAudit :object="item"></collapseReviewReadAudit>
        </template>
        <!-- 可写-->
        <collapseReviewWriteAudit
          ref="write"
          :object="object"
          v-if="(typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && periodType === '3' && isSubmitReedit === '1' && obj.isShowWrite"
          @selecError="selecError"
        ></collapseReviewWriteAudit>
      </div>

      <div v-if="periodType == 2 || periodType == 3">
        <div class="primary">
          <mt-switch
            v-if="
              (((approveTime == null && isSubmitReedit !== '1') || approveTime === '') && typeFlag === '1') || ((typeFlag === '2' || typeFlag === '3') && isSubmitReedit !== '1')
            "
            v-model="above"
            @change="skipGrade"
          >
            是否跳级审核
          </mt-switch>
          <button class="linear" v-if="typeFlag == 1 && submitState" @click.prevent="submitAudit">提交审核</button>
          <button class="linear" v-if="typeFlag == 1 && submitState" @click.prevent="submitAudit('BrowseFile')">预览</button>
          <button class="linear" v-if="typeFlag == 20" @click="recallTask">撤回</button>
          <div
            v-if="
              (typeFlag == 2 || typeFlag == 3 || typeFlag == 4 || typeFlag == 5 || typeFlag == 7) &&
                !(ReeditPassBtnFlag && isSubmitReedit === '1') &&
                !(ReeditIssueBtnFlag && isSubmitReedit === '1')
            "
          >
            <button class="linear" @click="auditingHTTask('1')">{{ typeFlag == '5' ? '签发' : '通过' }}</button>
            <button class="linear" @click="auditingHTTask('0')">驳回</button>
          </div>
          <!-- 成果文件归档阶段 -->
          <button class="linear" v-if="ArchiveSubmitArchive && isSubmitReedit !== '1'" @click="submitArchive">提交归档</button>
          <input type="text" class="fileNumber" placeholder="请输入档案编号" v-model="fileNumber" v-if="typeFlag === '8' && periodType === '3'" />
          <button class="linear" v-if="ArchiveConfirmArchive && isSubmitReedit !== '1'" @click="confirmArchive">确认归档</button>
          <!-- 重新编辑申请 -->
          <button class="linear" v-if="ArchiveSubmitArchive && isSubmitReedit !== '1'" @click="reeditApplication">重新编辑申请</button>
          <button class="linear" v-if="ReeditPassBtnFlag && isSubmitReedit === '1'" @click="auditingOutcomeDocumentPeriodReeditApplication('1')">通过</button>
          <button class="linear" v-if="ReeditIssueBtnFlag && isSubmitReedit === '1'" @click="reeditApplicationIssue('1')">签发</button>
          <button class="linear" v-if="ReeditRejectBtnFlag && isSubmitReedit === '1'" @click="auditingOutcomeDocumentPeriodReeditApplication('0')">驳回</button>
        </div>
      </div>
      <div class="primary" v-if="periodType == '5'">
        <!-- 归档阶段 项目监理部 -->
        <button class="linear" v-if="typeFlag == '1' && periodType == '5'" @click="initiateArchive">发起归档</button>
        <input type="text" placeholder="请输入档案编号" class="fileNumber" v-model="fileNumber" v-if="typeFlag === '2' && periodType === '5'" />
        <button class="linear" v-if="typeFlag == '2' && periodType == '5'" @click="confirmArchiveXM">确认归档</button>
      </div>
      <div class="primary" v-if="isMainHeadFlag === '1' && periodType !== '2' && periodType !== '3' && periodType !== '5' && '请款单提交审核' != currentTransmitData.name">
        <button class="linear" @click="isIgnoreTaskPeriod">{{ treeList.isIgnore == '0' ? '忽略' : '展示' }}</button>
        <button class="linear" @click="finish">{{ isFinish === '1' ? '未完成' : '完成' }}</button>
      </div>
    </el-collapse>
    <!-- 上传文件 -->
    <dialogUpload
      ref="dialogUpload"
      :options="options"
      unique="fileTypeId"
      display="fileTypeName"
      :uploadOptions="uploadOptions"
      :files="files"
      @submit="uploadSubmit"
      :periodTypeBoolean="periodType != '1'"
      :periodType="periodType"
    ></dialogUpload>
    <!-- 可写组件 - 对话框 -->
    <collapse-review-write-dialog ref="collapseReviewWrite_dialog" :errorTyleList="errorTyleList" :obj="selectErrorObj" @submit="selectErrorSubmit"></collapse-review-write-dialog>
    <!-- 提交审核-对话框 -->
    <submit-audit ref="submitAudit" @confirmSubmit="confirmSubmit"></submit-audit>
    <!-- 请款单提交审核右侧组件 -->
    <request-payout-r v-if="'请款单提交审核' == currentTransmitData.name" :transmitData="currentTransmitData"></request-payout-r>
    <!-- 合同提交审核右侧组件 -->
    <contract-r v-if="'合同提交审核' == currentTransmitData.name" :transmitData="currentTransmitData"></contract-r>
    <!-- PDF版本预览-->
    <!-- <dialogBrowseFile :file="file" ref="dialogBrowseFile" type="task" :formData="formData"></dialogBrowseFile> -->
    <!-- 提审之前预览-->
    <previewFile ref="dialogBrowseFile" :file="file" :type="type" :formData="formData"></previewFile>
  </div>
</template>

<script>
import collapseUpAndDownload from '../../../more/common/components/collapseUpAndDownload.vue'; /* 文件上传下载 */
// import dialogBrowseFile from '../../../more/common/components/dialogBrowseFile.vue';
import previewFile from '../../../more/common/components/previewFile.vue'; /* 预览 */
import dialogUpload from '../../../more/common/components/dialogUpload.vue'; /* 文件选择弹窗 */
import collapseReviewWrite from '../../../more/common/components/collapseReviewWrite.vue'; /* 审核意见可写*/
import collapseReviewRead from '../../../more/common/components/collapseReviewRead.vue'; /* 审核意见只读 */
import collapseReviewWriteAudit from '../../../more/common/components/collapseReviewWriteAudit.vue'; /* 审核意见可写*/
import collapseReviewReadAudit from '../../../more/common/components/collapseReviewReadAudit.vue'; /* 审核意见只读 */
import collapseReviewWrite_dialog from '../../../more/common/components/collapseReviewWrite_dialog.vue'; /* 可写组件 - 对话框*/
import submitAudit from '../../../more/common/components/submitAudit.vue'; /* 提交审核-对话框 */
import requestPayoutR from './requestPayout/right'; /* 提交审核-对话框 */
import contractR from './contract/right'; /* 提交审核-对话框 */
export default {
  data() {
    const formData = new FormData();
    return {
      // 任务标识
      type: 'task',
      UpAndDownloadTitle: '',
      formData: formData,
      files: [], // 上传文件列表
      options: [] /* 文件上传单选数据 */,
      // 上传按钮是否显示标识
      showUploadBtnFlg: false,
      // 是否显示删除按钮
      delBtnFlag: false,
      uploadOptions: {
        isInvoice: false, // 是否是发票
        limit: 1,
        radioFlag:this.periodType=='1'?false:true
      },
      submitState: true,
      // 是否跳级审核标识
      jumpFlag: false,
      // 审核人
      reviewer: {
        // 当前
        current: '',
        // 下一个
        next: ''
      },
      /* 是否跳级*/
      above: false,
      opinionList: [] /* 文件列表数据 */,
      object: {
        title: '', // 一级面板的标题
        opinion: '', // 一级级面板的意见
        children: [
          {
            id: '', // 二级面板的被审批人标识
            name: '', // 二级面板的被审批人名字
            errorList: [
              // 二级面板的被审批人的错误数组
              {
                num: '', // 错误数量
                type: '' // 错误类型
              }
            ]
          }
        ]
      },
      errorTyleList: [], // 错误类型列表
      selectErrorObj: {}, // 选择错误的对象
      auditFileList: [] /*, 审核意见文件列表 */,
      isSubmitReedit: '', // 是否提交重新编辑申请
      verifyState: null, // 审核状态
      approveTime: null, // 审批时间
      nextTaskKey: '', // 流程下一节点
      periodType: '', // 阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
      // 相关资料文件列表
      reviewFileList: [],
      ArchiveVal: '' /* 上传文件file值 */,
      fileNumber: '', // 档案编号
      archiveList: [], // 上传归档文件数组
      Archivefiles: [], // 归档文件列表
      ArchiveSubmitArchive: false, // 成果文件（归档）提交归档
      ArchiveConfirmArchive: false, // 成果文件（归档）确认归档
      ReeditSubmitAuditBtnFlag: false, // 成果文件（重新编辑申请）提交审核按钮
      ReeditPassBtnFlag: false, // 成果文件（重新编辑申请）通过按钮
      ReeditIssueBtnFlag: false, // 成果文件（重新编辑申请）签发按钮
      ReeditRejectBtnFlag: false, // 成果文件（重新编辑申请）驳回按钮
      // 重新申请列表
      reeditApplicationList: [],
      currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy), //传输数据
      isMainHeadFlag: false,
      file: '' // 待浏览文件
    };
  },
  components: {
    collapseUpAndDownload: collapseUpAndDownload,
    previewFile: previewFile /* 预览 */,
    dialogUpload: dialogUpload,
    collapseReviewWrite: collapseReviewWrite,
    'collapse-review-write-dialog': collapseReviewWrite_dialog,
    'collapse-review-read': collapseReviewRead,
    'submit-audit': submitAudit,
    collapseReviewReadAudit: collapseReviewReadAudit,
    collapseReviewWriteAudit: collapseReviewWriteAudit,
    'request-payout-r': requestPayoutR, //请款单提交审核右侧组件
    'contract-r': contractR //合同提交审核右侧组件
  },
  props: {
    treeList: Object,
    typeFlag: String,
    transmitData: Object,
    postil: Object,
    rightJurisdiction:Object,
  },
  watch: {
    treeList(newval) {
      /* 左边栏数据改变 */
      this.treeList = newval;
      // 是否是主提审人
      this.isMainHeadFlag = this.treeList.isMainHeadFlag;
      // 是否忽略
      this.isIgnore = this.treeList.isIgnore;
      // 是否完成
      this.isFinish = this.treeList.isFinish;
      this.periodType = this.treeList.periodType;
      this.UpAndDownloadTitle = newval.label;
      this.initInfo(this.treeList);
    },
    periodType:{
      handler(){
        this.uploadOptions.radioFlag = this.periodType=='1'?false:true;
      },
      immediate:true
    },
    typeFlag(newval) {
      /* 用户权限改变 */
      this.typeFlag = newval;
      console.log('当前用户权限', this.typeFlag);
      this.initInfo(this.treeList);
    },
    transmitData: {
      handler() {
        let vm = this;
        vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
      },
      immediate: true
    },
    postil: {
      handler(newval) {
        let vm = this;
        vm.postil = newval;
      },
      immediate: true,
      deep: true
    },
    rightJurisdiction: {
      handler(newval) {
        let vm = this;
        vm.rightJurisdiction = newval;
      },
      immediate: true,
      deep: true
    }
  },
  created() {
    const vm = this;
    vm.initInfo();
    vm.bus.$on('changeAuthorized', function(val) {
      if (val != 0) {
        vm.submitState = false;
      } else {
        vm.submitState = true;
      }
    });
    vm.bus.$on('echofile', function(val) {
      vm.submitFile(val);
    });
  },
  methods: {
    /**
     * @method 重置组件显示状态
     */
    resetComponentState() {
      let vm = this;
    },
    initInfo(val) {
      // 重新编辑申请
      let submitReedit = this.rightJurisdiction.isSubmitReedit;
      // 当前状态
      let currentState = this.rightJurisdiction.currentState;
      if (this.periodType === '2') {
        /* A3阶段 */
        this.init(); /* A3查询素材列表*/
        this.initA3(); /* A3任务阶段信息*/
        this.searchHTTaskHisTaskInst(this.treeList.relationId); //查询审核意见列表
        this.searchReviewFileRelationList(this.treeList.relationId); // 查询审核意见文件列表
        this.searchQuestionList(this.treeList.departmentId); // 根据部门id查询问题类型列表信息
      } else if (this.periodType === '3') {
        // 成果文件（归档）提交归档
        this.ArchiveSubmitArchive = this.typeFlag === '6' && this.periodType === '3';
        // 成果文件（归档）确认归档
        this.ArchiveConfirmArchive = this.typeFlag === '8' && this.periodType === '3';
        // ==============================成果文件（重新编辑申请） 按钮 start=================================

        // 成果文件（重新编辑申请）提交审核按钮
        this.ReeditSubmitAuditBtnFlag = this.typeFlag === '6' && this.periodType === '3';

        // 成果文件（重新编辑申请）通过按钮
        this.ReeditPassBtnFlag = (this.typeFlag === '2' || this.typeFlag === '3' || this.typeFlag === '4') && this.periodType === '3';

        // 成果文件（重新编辑申请）签发按钮
        this.ReeditIssueBtnFlag = this.typeFlag === '5' && this.periodType === '3';

        // 成果文件（重新编辑申请）驳回按钮
        this.ReeditRejectBtnFlag = (this.typeFlag === '2' || this.typeFlag === '3' || this.typeFlag === '4' || this.typeFlag === '5') && this.periodType === '3';

        // ==============================成果文件（重新编辑申请） 按钮 end=================================

        this.searchOutcomeDocumentMaterial(this.treeList.taskId); // 查询素材列表
        this.achievement(this.treeList.relationId); /* 成果文件任务阶段信息*/
        this.searchHTTaskHisTaskInst(this.treeList.relationId); //查询审核意见列表
        this.searchQuestionList(this.treeList.departmentId); // 根据部门id查询问题类型列表信息
        this.searchIsReviewTaskPeriodMaterialList(this.treeList.relationId); // 查询相关资料文件列表
        this.searchTaskArchiveFileInfo(this.treeList.relationId); // 查询任务归档文件列表信息
        this.searchTaskReeditHisTaskInst(this.treeList.taskId, this.treeList.relationId); // 查询重新编辑审核列表
        this.searchReviewFileRelationList(this.treeList.taskId); // 查询审核意见文件列表
      } else if (this.periodType === '5') {
        // 根据任务id查询归档任务信息
        this.searchArchiveTaskDetailInfo(this.treeList.taskId);
      }
      // 普通阶段    A3   人员调整
      if (this.periodType === "0" || this.periodType === "2" || this.periodType === "4" || this.periodType === "5" || this.periodType === "6" || this.periodType === "7" || this.periodType === "8" || this.periodType === "9") {

        this.showUploadBtnFlg = this.typeFlag === "1" || (this.typeFlag === "15" && currentState !== "6");// 是否显示上传按钮  true:显示  false:不显示
        this.delBtnFlag = !(this.showUploadBtnFlg);// 是否显示删除按钮  true:不显示  false:显示

      // 收集资料
      }else if (this.periodType === '1') {
        this.showUploadBtnFlg = ((currentState === '6' && submitReedit !== '1') || this.typeFlag === '1' || (this.typeFlag === '15' && submitReedit !== '1')) && this.typeFlag !== '0';
        this.delBtnFlag = !this.showUploadBtnFlg;
      } else {
        this.showUploadBtnFlg = false; // 是否显示上传按钮  true:显示  false:不显示
        this.delBtnFlag = true; // 是否显示删除按钮  true:不显示  false:显示
      }
      if (this.periodType === "1" && this.typeFlag === "6" && submitReedit !== "1") {// 如果是收集资料阶段，在归档时机，没提交重新编辑申请显示上传按钮
        this.showUploadBtnFlg = true;// 是否显示上传按钮  true:显示  false:不显示
        this.delBtnFlag = false;// 是否显示删除按钮  true:不显示  false:显示
      }

      if (this.typeFlag === "15" && submitReedit === "1") {// 如果是普通执行人，提了重新编辑申请
        this.showUploadBtnFlg = false;// 是否显示上传按钮  true:显示  false:不显示
        this.delBtnFlag = true;// 是否显示删除按钮  true:不显示  false:显示
      }
      if (this.treeList.treeNodeLevel === '6' && this.periodType !== '3') {
        this.init();
      }
    },
    init() {
      /* A3请求下载文件列表*/
      this.requestPost('/task/query/searchTaskPeriodMaterialList', {
        relationId: this.treeList.relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.files = res.data.dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /* A3初始化方法*/
    initA3() {
      let vm = this;
      let formData = {
        relationId: this.treeList.relationId
      };
      vm.requestPost('/task/query/searchTaskPeriodDetailInfoByRelationId', formData)
        .then(res => {
          if (res.data.status == 'success') {
            // 任务阶段信息
            this.taskPeriodInfo = res.data.dataVO;
            console.log('任务阶段信息', this.taskPeriodInfo);
            // 人员列表
            let userList = this.taskPeriodInfo.userList;
            // 如果人员列表不为空
            if (userList != undefined) {
              for (var i = userList.length - 1; i >= 0; i--) {
                userList[i].errorList = [];
              }
            }
            // 审核级别
            vm.object.title = vm.getStepName(this.taskPeriodInfo.delegateTaskKey);
            vm.nextTaskKey = this.taskPeriodInfo.submitNode;
            vm.delegateTaskKey = this.taskPeriodInfo.delegateTaskKey;
            vm.verifyState = this.taskPeriodInfo.verifyState;
            vm.isCanRecall = this.taskPeriodInfo.isCanRecall;
            // 修改时间
            vm.updateDate = this.taskPeriodInfo.updateDate;
            // 审批时间
            vm.approveTime = this.taskPeriodInfo.approveTime;
            // 当前流程人员
            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);
            // 下一流程人员
            vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));
            // 如果人员列表不为空
            if (userList != undefined) {
              // 循环人员列表，并转换格式
              for (var i = userList.length - 1; i >= 0; i--) {
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, 'id'));
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, 'name'));
              }
            }
            vm.object.children = userList;

            // 设置是否提交了重新编辑申请
            vm.isSubmitReedit = this.taskPeriodInfo.isSubmitReedit;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
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
    /* 上传组件相关 */
    uploadFile() {
      // 上传文件对话框显示
      /* 上传文件类型*/
      this.requestPost('/tenderTask/query/searchMaterialTypeList', {
        departmentId: this.treeList.departmentId,
        counselTypeId: this.treeList.counselTypeId
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.options = res.data.dataList;
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
      let vm = this;
      this.$nextTick(function() {
        vm.$refs['dialogUpload'].show = true;
      });
    },
    uploadSubmit(data) {
      /* 上传文件接口 */
      /* 上传文件数据*/
      var formdata = new FormData();
      if (this.periodType == '2') {
        formdata.append('file', data.form.get('file'));
        formdata.append('taskPeriodId', this.treeList.relationId);
        formdata.append('fileType', data.fileTypeId);
        formdata.append('periodType', this.periodType);
      } else {
        formdata.append('file', data.form.get('file'));
        formdata.append('taskPeriodId', this.treeList.relationId);
        formdata.append('fileType', data.fileTypeId);
      }

      this.requestPost('/task/add/insertTaskPeriodMaterialInfo', formdata)
        .then(res => {
          if (res.data.status == 'success') {
            console.log(res);
            this.init();
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteFileInfo(val) {
      /* 删除文件 */
      console.log(val);
      let vm = this;
      let formData = {
        relationId: val.relationId,
        materialPath: val.materialPath,
        taskId: vm.treeList.taskId
      };
      this.requestPost('/task/delete/deleteTaskPeriodMaterialInfo', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.init();
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    submitAudit(type) {
      let vm = this;
      /* 提交审核 */
      if (this.periodType == 2) {
        /* A3 提审 */
        if (this.files.length > 0) {
          // 下一流程节点
          let nextTaskKey = this.taskPeriodInfo.submitNode;
          if (this.above) {
            // 获取下一流程节点
            nextTaskKey = this.getNextStepNode(nextTaskKey);
          }
          this.requestPost('/task/modify/submitAuditA3Period', {
            // 任务阶段关联id
            relationId: this.treeList.relationId,
            // 下一流程节点
            submitNode: nextTaskKey,
            // 阶段名称
            periodName: this.treeList.label
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                // 刷新
                vm.$router.push('/app/workbench');
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(e => {
              console.log(err);
            });
        } else {
          this.Toast('请先上传文件');
        }
      } else if (this.periodType == 3) {
        console.log('files', this.files);
        /* 成果文件提审 */
        /* 先验证成果文件*/
        this.bus.$emit('MaterialList', type);
      }
    },
    /* 验证通过之后,展示可以提交审核的文件列表,如果没有文件，直接上传*/
    submitFile(val) {
      /* 提交审核 */
      this.echofileData = val;
      let vm = this;
      //查询以上传的所有文件(非审批文件)
      vm.requestPost('/task/query/searchAllMaterialTypeAndMaterialList', {
        relationId: this.treeList.relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            let dataList = res.data.dataList;
            let count = 0;
            //校验是否有上传文件
            if (null != dataList && dataList.length > 0) {
              for (var i = dataList.length - 1; i >= 0; i--) {
                let childDataList = dataList[i];
                count += childDataList.children.length;
              }
            }
            //无上传除投标文件其他相关文件时
            if (count == 0) {
              //直接提交
              this.$refs.submitAudit.submit();
            } else {
              this.$refs.submitAudit.list = dataList;
              this.$refs.submitAudit.show = true;
            }
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    confirmSubmit(val) {
      const vm = this;
      /* 确认提交审核 */
      // 下一流程节点
      let nextTaskKey = this.taskPeriodInfo.submitNode;
      if (this.above) {
        // 获取下一流程节点
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }
      // 提交审核请求
      let sendData = {
        // 任务阶段关联id
        relationId: this.treeList.relationId,
        // 文件id
        materialIds: val,
        // 下一流程节点
        submitNode: nextTaskKey
      };
      var templateForm = this.echofileData.submit;
      var type = this.echofileData.type;
      var formData = new FormData();
      formData.append('relationId', this.treeList.relationId); // 任务阶段关联id
      formData.append('materialIds', val); // 其他文件类型id
      formData.append('submitNode', nextTaskKey); // 下一流程节点
      formData.append('taskId', this.treeList.taskId); // 任务id
      formData.append('taskType', this.echofileData.resultsFile); // 合同类型
      formData.append('type', 'task');
      // 如果选择自定义合同，则上传附件
      if (this.echofileData.resultsFile == '0') {
        // 上传文件
        formData.append('signPathFile', templateForm.signPathFile);
        // 成果文件编号
        formData.append('reportNum', templateForm.reportNum);

        // 如果选择合同类型为模板，则添加模板
      } else if (this.echofileData.resultsFile == '1') {
        // 模板类型
        formData.append('formworkType', templateForm.formworkType);

        // 返回后台结果
        let formworkContent = {};
        // 报告日期
        let reportDate;
        // 报送时间
        let submissDate;
        // 收到日期
        let reveivedDate;
        // 规章颁布日期
        let ruleIssueDate;
        // 规章颁布日期2
        let ruleIssueDate2;
        // 规章颁布日期3
        let ruleIssueDate3;
        // 合同开竣工日期
        let contractProjectDate = [];
        // 实际开竣工日期
        let realProjectDate = [];
        // 定审日期
        let auditDate = [];
        // 附件
        let affix;
        // 抄送
        let carbonCopy;
        // 模板类型
        switch (templateForm.formworkType) {
          // 编制概算
          case '0':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 规章颁布日期（年）
            ruleIssueDate = new Date(formworkContent.input10);
            formworkContent.input10 = this.formatDate(ruleIssueDate, 1);
            // 规章颁布日期2（月）
            ruleIssueDate2 = new Date(formworkContent.input11);
            formworkContent.input11 = this.formatDate(ruleIssueDate2, 2);
            // 规章颁布日期3（月）
            ruleIssueDate3 = new Date(formworkContent.input13);
            formworkContent.input13 = this.formatDate(ruleIssueDate3, 2);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input19);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 编制估算
          case '1':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input14);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 编制结算
          case '2':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input19);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 编制决算
          case '3':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input18);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 编制清标报告
          case '4':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 收到本工程中标单位的投标文件时间
            reveivedDate = formworkContent.input9;
            formworkContent.input9 = this.formatDate(reveivedDate, 0);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input35);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 招标控制价模版
          case '5':
            formworkContent = templateForm.formworkContent;
            console.log('input10', formworkContent.input10);
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 规章颁布日期
            ruleIssueDate = formworkContent.input10;
            formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input19);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 招标清单模版（分包、承包）
          case '6':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 规章颁布日期（月份）
            ruleIssueDate = formworkContent.input10;
            formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input15);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 招标控制价模版（总包）
          case '7':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input15);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 预算编制模板
          case '8':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 规章颁布日期（月份）
            ruleIssueDate = formworkContent.input10;
            formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
            // 规章颁布日期2（月份）
            ruleIssueDate2 = formworkContent.input115;
            formworkContent.input115 = this.formatDate(ruleIssueDate2, 2);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input21);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 概算审核
          case '9':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 规章颁布日期（年）
            ruleIssueDate = formworkContent.input18;
            formworkContent.input18 = this.formatDate(ruleIssueDate, 1);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input34);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 估算审核
          case '10':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 报送时间
            submissDate = formworkContent.input16;
            formworkContent.input16 = this.formatDate(submissDate, 0);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input35);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 结算审核
          case '11':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 报送时间
            submissDate = formworkContent.input24;
            formworkContent.input24 = this.formatDate(submissDate, 0);
            // 定审日期
            auditDate = formworkContent.input43;
            formworkContent.input43 = this.formatDate(auditDate, '/');
            // 合同编号
            formData.append('contractNum', formworkContent.contractNum);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input48);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 预算审核
          case '12':
            formworkContent = templateForm.formworkContent;
            // 报告日期
            reportDate = formworkContent.input3;
            formworkContent.input3 = this.formatDate(reportDate, '/');
            // 报送时间
            submissDate = formworkContent.input16;
            formworkContent.input16 = this.formatDate(submissDate, 0);
            // 咨询报告编号
            formData.append('reportNum', formworkContent.input33);
            // 项目名称
            formData.append('projectName', templateForm.input1);
            break;
          // 进度款审核意见
          case '15':
            formworkContent = templateForm.formworkContent;
            // 报送时间
            submissDate = formworkContent.input6;
            formworkContent.input6 = this.formatDate(submissDate, 0);
            // 附件
            affix = formworkContent.input14;
            if (affix == '0') {
              formworkContent.input15 = '';
            }
            // 抄送
            carbonCopy = formworkContent.input16;
            if (carbonCopy == '0') {
              formworkContent.input17 = '';
              formworkContent.input18 = '';
            }
            break;
          // 变更审核意见
          case '16':
            formworkContent = templateForm.formworkContent;
            // 报送时间
            submissDate = formworkContent.input6;
            formworkContent.input6 = this.formatDate(submissDate, 0);
            // 附件
            affix = formworkContent.input14;
            if (affix == '0') {
              formworkContent.input15 = '';
            }
            // 抄送
            carbonCopy = formworkContent.input16;
            if (carbonCopy == '0') {
              formworkContent.input17 = '';
              formworkContent.input18 = '';
            }
            break;
          // 预算审核意见
          case '17':
            formworkContent = templateForm.formworkContent;
            // 报送时间
            submissDate = formworkContent.input6;
            formworkContent.input6 = this.formatDate(submissDate, 0);
            // 发布时间
            ruleIssueDate = formworkContent.input9;
            formworkContent.input9 = this.formatDate(ruleIssueDate, 2);
            // 附件
            affix = formworkContent.input14;
            if (affix == '0') {
              formworkContent.input15 = '';
            }
            // 抄送
            carbonCopy = formworkContent.input16;
            if (carbonCopy == '0') {
              formworkContent.input17 = '';
              formworkContent.input18 = '';
            }
            break;
          case '18': // 招标代理
          case '19': // 评标书面报告
            formworkContent = templateForm.formworkContent;
            break;
          default:
            formworkContent = templateForm.formworkContent;
            break;
        }
        formworkContent.input100 = this.formatDate(new Date(), 0);
        // 合同信息
        formData.append('formworkContent', JSON.stringify(formworkContent));
      }
      if (type == 'BrowseFile') {
        this.formData = formData;
        vm.file = {
          materialPath: true,
          isGeneratedFile: true,
          noSubmitPreview: '1'
        };
        vm.$refs.dialogBrowseFile.show = true;
      } else {
        // 提交审核请求
        this.requestPost('/task/modify/submitAuditOutcomeDocumentPeriod', formData)
          .then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == 'success') {
              this.Toast(res.data.message);
              // 刷新
              this.$router.go(0);
              this.$router.go(0);
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(e => {
            this.Toast(e);
          });
      }
    },
    /**
     * 替换日期格式
     * @param date 需要转换的日期
     * @param char 间隔符 "/"、"-"
     * @returns {*}
     */
    formatDate(date, char) {
      let time = '';
      if (!this.tool.isNull(date)) {
        if (typeof date == 'string') {
          var year = date.slice(0, 4);
          var month = date.slice(5, 7);
          var day = date.slice(8, 10);
        } else if (typeof date == 'object') {
          var year = date.getFullYear();
          var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        }
        // 如果char = 0，显示年月日，char = 1，只显示年份
        if ('0' == char) {
          time = year + '年' + month + '月' + day + '日';
        } else if ('1' == char) {
          time = year + '';
        } else if ('2' == char) {
          time = year + '年' + month + '月';
        } else {
          time = year + char + month + char + day + '';
        }
      }
      return time;
    },
    recallTask() {
      if (this.periodType == 2) {
        /* 撤回审核 */
        this.requestPost('/task/modify/recallTaskA3Period', {
          // 任务阶段关联id
          relationId: this.treeList.relationId,
          // 修改时间
          updateDate: this.taskPeriodInfo.updateDate
        })
          .then(res => {
            if (res.data.status == 'success') {
              // 刷新
              this.$router.go(0);
              this.initInfo();
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(err => {
            console.log(err);
          });
      } else if (this.periodType == 3) {
        /* 撤回审核 */
        this.requestPost('/task/modify/recallTaskOutcomeDocumentPeriod', {
          // 任务阶段关联id
          relationId: this.treeList.relationId
        })
          .then(res => {
            if (res.data.status == 'success') {
              // 刷新
              this.$router.go(0);
              this.initInfo();
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    /* 可写组件--相关 */
    selecError(data) {
      // 选择错误
      // 先赋值再显示对话框
      this.selectErrorObj = data;
      this.$refs['collapseReviewWrite_dialog'].show = true;
    },
    selectErrorSubmit(data) {
      this.selectErrorObj.errorList = data;
    },
    // 审核通过和驳回
    auditingHTTask(isPass) {
      let paramObj = this.object;
      paramObj.isPass = isPass;
      // 当前节点
      paramObj.currentState = this.typeFlag;
      // 下一流程节点
      let nextTaskKey = this.taskPeriodInfo.submitNode;
      if (this.above) {
        // 获取下一流程节点
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }
      paramObj.onlyOfficeEditKey = this.postil.onlyOfficeEditKey;
      paramObj.reviewFileName = this.postil.reviewFileName;
      paramObj.reviewFilePath = this.postil.reviewFilePath;
      if (this.periodType == 2) {
        paramObj.submitNode = nextTaskKey;
        paramObj.userQuestionRecordRelationList = new Array();
        paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, 'delegateTaskKey'));
        if (isPass == '0') {
          paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, 'rejectReason'));
          for (var i = paramObj.children.length - 1; i >= 0; i--) {
            if (paramObj.children[i].errorList) {
              for (var j = paramObj.children[i].errorList.length - 1; j >= 0; j--) {
                paramObj.children[i].errorList[j].userId = paramObj.children[i].id;
                paramObj.children[i].errorList[j].userName = paramObj.children[i].name;
                if (paramObj.children[i].errorList[j].questionNum != '0') {
                  paramObj.userQuestionRecordRelationList.push(paramObj.children[i].errorList[j]);
                }
              }
            }
          }
          delete paramObj.children;
        }
        // 任务阶段关联id
        paramObj.relationId = this.treeList.relationId;
        // 修改时间
        paramObj.updateDate = this.taskPeriodInfo.updateDate;
        let vm = this;
        this.requestPost('/task/add/auditingTaskA3Period', paramObj)
          .then(res => {
            if (res.data.status == 'success') {
              // 跳转到工作台页面
              vm.$router.push('/app/workbench');
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(err => {
            console.log(err);
          });
      } else if (this.periodType == 3) {
        paramObj.submitNode = nextTaskKey;
        paramObj.userQuestionRecordRelationList = new Array();
        paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, 'delegateTaskKey'));
        if (isPass == '0') {
          paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, 'rejectReason'));
          for (var i = paramObj.children.length - 1; i >= 0; i--) {
            if (paramObj.children[i].errorList) {
              for (var j = paramObj.children[i].errorList.length - 1; j >= 0; j--) {
                paramObj.children[i].errorList[j].userId = paramObj.children[i].id;
                paramObj.children[i].errorList[j].userName = paramObj.children[i].name;
                if (paramObj.children[i].errorList[j].questionNum != '0') {
                  paramObj.userQuestionRecordRelationList.push(paramObj.children[i].errorList[j]);
                }
              }
            }
          }
          delete paramObj.children;
        }
        // 任务阶段关联id
        paramObj.relationId = this.treeList.relationId;
        // 修改时间
        paramObj.updateDate = this.taskPeriodInfo.updateDate;
        let vm = this;
        this.requestPost('/task/add/auditingTaskOutcomeDocumentPeriod', paramObj)
          .then(res => {
            if (res.data.status == 'success') {
              this.$router.go(0);
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    //查询审核意见列表
    searchHTTaskHisTaskInst(relationId) {
      let vm = this;
      //查询审核意见列表
      vm.requestPost('/task/query/searchHTTaskHisTaskInst', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            /*------------------------------格式转换START---------------------------------*/

            let dataList = res.data.dataList;
            console.log('opinionList', dataList);

            //for循环遍历更改属性名
            for (var i = 0; i < dataList.length; i++) {
              dataList[i].title = vm.getStepName(dataList[i].taskKey);

              //将属性名htTaskPeriodVOList转换成属性名children
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htTaskPeriodVOList/g, 'children'));

              // 获取转换格式后的children
              let children = dataList[i].children;

              // 循环遍历历史列表
              for (var j = 0; j < children.length; j++) {
                //将属性名approveTime转换成属性名time
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, 'time'));
                //将属性名approveUserName转换成属性名name
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, 'name'));
                //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g, 'persions'));
                //将属性名rejectReason转换成属性名opinion
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, 'opinion'));

                let persions = vm.groupBy(children[j].persions, function(item) {
                  return [item.userId];
                });

                for (var k = 0; k < persions.length; k++) {
                  persions[k].name = persions[k].dataList[0].userName;
                  //将属性名dataList转换成属性名errorList
                  persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, 'errorList'));
                  let errorList = persions[k].errorList;
                  for (var l = errorList.length - 1; l >= 0; l--) {
                    //将属性名questionName转换成属性名type
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, 'type'));
                    //将属性名questionNum转换成属性名num
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, 'num'));
                  }
                  persions[k].errorList = errorList;
                }
                children[j].persions = persions;
              }
            }

            /*---------------------------------格式转换END-------------------------------------*/

            // 把转换后的dataList赋值给opinionList
            vm.opinionList = dataList;
            if (this.periodType == '2') {
              vm.$emit('A3data', dataList);
            }
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
    },
    /* 成果文件开始*/
    achievement(relationId) {
      /* 成果文件阶段信息 */
      let vm = this;
      vm.requestPost('/task/query/searchTaskDetailInfoByRelationId', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            console.log('成果文件阶段信息 ', res.data.dataVO);
            // 任务阶段信息
            this.taskPeriodInfo = res.data.dataVO;
            // 人员列表
            let userList = this.taskPeriodInfo.userList;

            // 如果人员列表不为空
            if (userList != undefined) {
              for (var i = userList.length - 1; i >= 0; i--) {
                userList[i].errorList = [];
              }
            }

            // 审核级别
            vm.object.title = vm.getStepName(this.taskPeriodInfo.delegateTaskKey);
            vm.nextTaskKey = this.taskPeriodInfo.submitNode;
            vm.delegateTaskKey = this.taskPeriodInfo.delegateTaskKey;
            vm.verifyState = this.taskPeriodInfo.verifyState;
            vm.isCanRecall = this.taskPeriodInfo.isCanRecall;
            // 修改时间
            vm.updateDate = this.taskPeriodInfo.updateDate;
            // 审批时间
            vm.approveTime = this.taskPeriodInfo.approveTime;

            // 当前流程人员
            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);

            // 下一流程人员
            vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));

            // 如果人员列表不为空
            if (userList != undefined) {
              // 循环人员列表，并转换格式
              for (var i = userList.length - 1; i >= 0; i--) {
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, 'id'));
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, 'name'));
              }
            }

            vm.object.children = userList;
            // 设置是否提交了重新编辑申请
            vm.isSubmitReedit = this.taskPeriodInfo.isSubmitReedit;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 查询素材列表
    searchOutcomeDocumentMaterial(taskId) {
      // 根据任务阶段ID查询阶段关联文件
      this.requestPost('/task/query/searchOutcomeDocumentMaterial', {
        taskId: taskId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            let tempTaskInfo = res.data.dataVO;

            // 任务文件路径
            let taskMaterialPath = tempTaskInfo.taskMaterialPath;

            // 任务文件名称
            let taskMaterialName = tempTaskInfo.taskMaterialName;

            // 任务文件类型
            let taskType = tempTaskInfo.taskType;

            // 是否调用任务文件模板生成接口 默认走正常的下载接口
            let isGeneratedFile = false;

            // 如果任务文件类型为模板
            if (taskType === '1') {
              // 调用任务模板生成接口 生成任务模板
              isGeneratedFile = true;
            }

            let tempList = [];

            if (tempTaskInfo.taskMaterialName != null && tempTaskInfo.taskMaterialName != '') {
              tempList[0] = {
                materialName: taskMaterialName,
                materialPath: taskMaterialPath,
                isGeneratedFile: isGeneratedFile
              };
            }
            this.files = tempList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    // 查询相关资料文件列表
    searchIsReviewTaskPeriodMaterialList(relationId) {
      this.requestPost('/task/query/searchIsReviewTaskPeriodMaterialList', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));
            }
            this.reviewFileList = dataList;
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // 提交归档
    submitArchive() {
      let vm = this;
      this.formData = new FormData();
      // 设置多图片传参数
      for (var i = 0; i < this.archiveList.length; i++) {
        this.formData.append('resourceFiles', this.archiveList[i]);
      }
      this.formData.append('relationId', this.treeList.relationId);
      // 任务id
      this.formData.append('taskId', this.treeList.taskId);
      // 提交归档请求
      this.requestPost('/task/modify/submitArchive', this.formData)
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.Toast(res.data.message);
           // 跳转到工作台页面
           vm.$router.push('/app/workbench');
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    // 确认归档
    confirmArchive() {
      let vm = this;
      // 确认归档请求
      if (this.fileNumber != '') {
        this.requestPost('/task/modify/confirmArchive', {
          // 任务阶段关联id
          relationId: this.treeList.relationId,
          // 档案编号
          fileNumber: this.fileNumber,
          // 修改时间
          updateDate: this.updateDate
        })
          .then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == 'success') {
              this.Toast(res.data.message);
              // 跳转到工作台页面
              vm.$router.push('/app/workbench');
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(e => {
            console.log(e);
          });
      } else {
        this.Toast('请输入档案编号');
      }
    },
    // 查询任务归档文件列表信息
    searchTaskArchiveFileInfo(relationId) {
      // 提交文件请求
      this.requestPost('/task/query/searchTaskSaveMaterialList', {
        relationId: relationId
      })
        .then(res => {
          if (res.data.status == 'success') {
            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));
            }
            // 设置转换之后的list
            this.Archivefiles = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    /* 归档文件上传*/
    uploadArchiveFile() {
      document.getElementById('Archivefile').click();
      return false;
    },
    ArchivefileChange(e) {
      // 存储每次选择的文件
      this.archiveList.push(e.target.files[0]);
      // 临时对象
      let tempObj = {
        // 文件名称
        materialName: e.target.files[0].name,
        // 下载标识
        downloadFlag: '1',
        // 删除标识
        deleteFlag: '1',
        // 唯一标识
        uuid: (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
      };
      // 把每次选择的对象放入多文件列表中
      this.Archivefiles.push(tempObj);
      console.log('Archivefiles', this.Archivefiles);
    },
    /* 归档文件删除*/
    deleteArchiveFileInfo(val) {
      // 如果是“1”，则是没添加到后台的数据，否则是数据库中的数据
      if (val.deleteFlag === '1') {
        for (var i = 0; i < this.Archivefiles.length; i++) {
          if (this.Archivefiles[i].uuid == val.uuid) {
            this.Archivefiles.splice(i, 1);
            this.archiveList.splice(i, 1);
          }
        }
      } else {
        this.requestPost('/task/delete/deleteTaskSaveMaterialInfo', {
          relationId: val.materialId,
          materialPath: val.materialPath,
          taskId: this.treeList.taskId
        })
          .then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == 'success') {
              this.Toast(res.data.message);
              // 查询归档文件列表
              this.searchTaskArchiveFileInfo(this.treeList.relationId);
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(e => {
            this.Toast(e);
          });
      }
    },
    // 重新编辑申请
    reeditApplication() {
      let vm = this;
      // 重新编辑申请请求
      this.requestPost('/task/modify/submitReedit', {
        // 任务阶段关联id
        relationId: this.treeList.relationId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            this.Toast(res.data.message);
            // 跳转到工作台页面
            vm.$router.push('/app/workbench');
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    // ==============================================================================成果文件 重新编辑的通过和驳回 start==============================================================================
    // 审核通过和驳回
    auditingOutcomeDocumentPeriodReeditApplication(isPass) {
      let paramObj = this.object;
      paramObj.isPass = isPass;

      // 下一流程节点
      let nextTaskKey = this.nextTaskKey;

      if (this.above) {
        // 获取下一流程节点
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }

      paramObj.submitNode = nextTaskKey;
      paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, 'delegateTaskKey'));
      if (isPass == '0') {
        paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, 'rejectReason'));
      }
      // 任务阶段关联id
      paramObj.relationId = this.treeList.relationId;
      // 修改时间
      paramObj.updateDate = this.taskPeriodInfo.updateDate;
      let vm = this;
      this.requestPost('/task/add/auditingOutcomeDocumentPeriodReeditApplication', paramObj)
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            this.Toast(res.data.message);
            // 刷新
            this.$router.go(0);
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // 重新编辑签发
    reeditApplicationIssue(isPass) {
      let paramObj = this.object;
      paramObj.isPass = isPass;

      // 下一流程节点
      let nextTaskKey = this.nextTaskKey;

      if (this.above) {
        // 获取下一流程节点
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }

      paramObj.submitNode = nextTaskKey;
      paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, 'delegateTaskKey'));
      if (isPass == '0') {
        paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, 'rejectReason'));
      }
      // 任务阶段关联id
      paramObj.relationId = this.treeList.relationId;
      // 修改时间
      paramObj.updateDate = this.taskPeriodInfo.updateDate;
      let vm = this;
      this.requestPost('/task/add/reeditApplicationIssue', paramObj)
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            this.Toast(res.data.message);
            // 刷新
            this.$router.go(0);
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ==============================================================================成果文件 重新编辑的通过和驳回 end======================================
    // 查询归档任务信息
    searchArchiveTaskDetailInfo(taskId) {
      this.requestPost('/task/query/searchArchiveTaskDetailInfoByTaskId', {
        taskId: taskId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // 委托任务id
            this.delegateTaskId = res.data.dataVO.delegateTaskId;
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ==============================================================================归档阶段 发起归档 项目监理部 start==============================================================================
    // 发起归档
    initiateArchive() {
      let vm = this;
      this.requestPost('/task/modify/initiateArchive', {
        // 任务id
        taskId: this.treeList.taskId
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 跳转到工作台页面
            vm.$router.push('/app/workbench');
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    // ==============================================================================归档阶段 发起归档 项目监理部 end==============================================================================
    // ==============================================================================归档阶段 确认归档 项目监理部 start==============================================================================
    // 确认归档
    confirmArchiveXM() {
      if (this.fileNumber != '') {
        this.requestPost('/task/modify/confirmArchiveXM', {
          // 任务id
          taskId: this.treeList.taskId,
          // 档案编号
          fileNumber: this.fileNumber,
          // 委托任务id
          delegateTaskId: this.delegateTaskId
        })
          .then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == 'success') {
              // 刷新页面
              this.$router.go(0);
            } else {
              this.Toast(res.data.message);
            }
          })
          .catch(e => {
            this.Toast(e);
          });
      } else {
        this.Toast('请输入档案');
      }
    },
    // ==============================================================================确认归档 查询文件列表 end==============================================
    // ==============================================================================成果文件阶段 查询重新编辑审核列表 start==============================================================================
    //查询重新编辑审核列表
    searchTaskReeditHisTaskInst(taskId, relationId) {
      //查询审核意见列表
      this.requestPost('/task/query/searchTaskReeditHisTaskInst', {
        taskId: taskId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // 获取重新编辑审核历史记录
            let dataList = res.data.dataList;

            // 是否提交了重新编辑申请
            let isSubmitReedit = res.data.isSubmitReedit;

            // 如果有数据
            if (dataList.length !== 0) {
              // 定义标识，用于判断dataList中是否是最后一个对象
              let o = 0;

              // 定义标识，用于判断是否在dataList中添加一个对象
              let next = false;

              // 循环遍历所有历史记录集合
              for (var k = 0; k < dataList.length; k++) {
                //for循环遍历更改属性名
                for (var i = 0; i < dataList[k].htTaskPeriodVOList.length; i++) {
                  // 设置三级审核标识（一级审核、二级审核...）
                  dataList[k].htTaskPeriodVOList[i].title = this.getStepName(dataList[k].htTaskPeriodVOList[i].taskKey);

                  //将属性名htTaskPeriodVOList转换成属性名children
                  dataList[k].htTaskPeriodVOList[i].children = dataList[k].htTaskPeriodVOList[i].htTaskPeriodVOList;

                  // 获取转换格式后的children
                  let children = dataList[k].htTaskPeriodVOList[i].children;

                  // 循环遍历历史列表
                  for (var j = 0; j < children.length; j++) {
                    //将属性名approveTime转换成属性名time
                    children[j].time = children[j].approveTime;

                    //将属性名approveUserName转换成属性名name
                    children[j].name = children[j].approveUserName;

                    //将属性名rejectReason转换成属性名opinion
                    children[j].opinion = children[j].rejectReason;
                  }
                }

                // 每循环一次dataList，o则加1
                o++;

                // 当o为dataList数据的长度的时候，说明到了循环到了最后一个对象，则判断是否显示可写审核意见组件
                if (o == dataList.length) {
                  // dataList[k].verifyState为0时，标识未通过
                  if (dataList[k].verifyState == 0) {
                    // 隐藏最后一个组件中的可写审核意见组件
                    dataList[k].isShowWrite = false;

                    // 在dataList添加一个对象
                    next = true;

                    // dataList[k].verifyState为2时，审核中
                  } else if (dataList[k].verifyState == 2) {
                    // 显示最后一个组件中的可写审核意见组件
                    dataList[k].isShowWrite = true;
                  }
                } else {
                  // 隐藏最后一个组件中的可写审核意见组件
                  dataList[k].isShowWrite = false;
                }
              }

              // 在dataList中添加一个对象
              if (next && (this.typeFlag === '2' || this.typeFlag === '3' || this.typeFlag === '4' || this.typeFlag === '5') && isSubmitReedit === '1') {
                dataList.push({
                  htTaskPeriodVOList: [],
                  isShowWrite: true
                });
              }

              // 如果没有数据，则需要判断是否提交了重新编辑申请，按钮显示类型是否是三级审核人或者签发人，如果满足条件，则在reeditApplicationList中创建一条数据，用于输入审核意见
            } else {
              // 如果提交了重新编辑申请，则添加一条数据
              if (isSubmitReedit === '1' && (this.typeFlag === '2' || this.typeFlag === '3' || this.typeFlag === '4' || this.typeFlag === '5')) {
                // 添加一条数据
                dataList.push({
                  htTaskPeriodVOList: [],
                  isShowWrite: true
                });
              }
            }

            // 把转换后的dataList赋值给reeditApplicationList
            this.reeditApplicationList = dataList;
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ==============================================================================成果文件阶段 查询重新编辑审核列表 end=================================
    /* 成果文件结束*/
    // ==============================================================================是否忽略任务阶段 start==============================================================================
    // 是否忽略
    isIgnoreTaskPeriod() {
      let isIgnoreFlag = '';

      if (this.isIgnore === '0') {
        isIgnoreFlag = '1';
      } else {
        isIgnoreFlag = '0';
      }

      this.requestPost('/task/modify/updateIsIgnore', {
        // 任务阶段id
        relationId: this.treeList.relationId,
        // 是否忽略
        isIgnore: isIgnoreFlag
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 刷新页面
            this.$router.go(0);
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    // ==============================================================================是否忽略任务阶段 end=====================================
    // ==============================================================================是否完成任务阶段 start==============================================================================
    /**
     * @method 完成
     */
    finish() {
      let isFinishFlag = '';

      if (this.isFinish === '0') {
        isFinishFlag = '1';
      } else {
        isFinishFlag = '0';
      }

      this.requestPost('/task/modify/updateIsFinish', {
        // 任务阶段id
        relationId: this.treeList.relationId,
        // 是否忽略
        isFinish: isFinishFlag
      })
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 刷新页面
            this.$router.go(0);
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
          this.Toast(e);
        });
    },
    // ==============================================================================是否完成任务阶段 end========================================
    skipGrade(val) {
      /* 跳级审核 */
      const vm = this;
      this.MessageBox.confirm('', {
        message: '审核本来要提交给' + vm.reviewer.current + ',现在将要跳级提交给' + vm.reviewer.next,
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          if (action == 'confirm') {
            vm.above = true;
          }
        })
        .catch(err => {
          if (err == 'cancel') {
            vm.above = false;
          }
        });
    }
  }
};
</script>
<style scoped lang="scss">
.fileNumber {
  width: 80%;
  height: 70px;
  border: 2px solid #e5e5e5;
  margin: 0 auto;
  display: block;
  border-radius: 10px;
  padding: 0 10px;
}
.reedit {
  width: 100%;
  margin: 0 auto;
  p {
    padding: 0 30px;
    color: #06a1ee;
  }
}
</style>
