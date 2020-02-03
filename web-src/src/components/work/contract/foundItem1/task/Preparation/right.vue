<template>

  <!-- 任务right -->
  <div class="taskR">
    <div class="given-container">
    <!--折叠面板-->
    <el-collapse v-model="activeNames" accordion>

      <!-- 成果文件上传-->
      <fileList :title="currentTransmitData.label" :showUploadBtn="false" :options="fileTypeList"
                @del="deleteResultFileInfo" :list="fileList"
                @submit="submitForm"
                v-if='fileList.length > 0'
                :delBtnFlag="delBtnFlag" :type="type" :id="taskId"></fileList>

      <!-- 相关资料 -->
      <reviewFileList title="相关资料" :showUploadBtn="false" @del="deleteFileInfo" :list="reviewFileList"
                      :delBtnFlag="false" v-if='reviewFileList.length>0'></reviewFileList>

      <!-- 归档文件 -->
      <el-collapse-item class="invoice" title="归档文件"
                        v-if='((typeFlag === "0" || ((typeFlag === "2" || typeFlag === "3" || typeFlag === "4" || typeFlag === "5") && isSubmitReedit === "1")) && list.length > 0) || typeFlag === "6" || ((typeFlag === "7" || typeFlag === "8") && list.length > 0)'>
        <template slot="title">
          归档文件&nbsp;
          <i class="el-icon-plus linear" style="color: white;padding: 2px 6px;border-radius: 5px;"
             @click.stop="uploadFile" v-if='typeFlag === "6"'></i>
        </template>
        <div class="content" hide-upload-container>
          <el-upload style="display: none;"
                     ref="uploadDemo"
                     accept="text"
                     class="upload-demo"
                     action="#"
                     :file-list="list"
                     :show-file-list="false"
                     :before-upload="beforeAvatarUpload"
                     multiple>
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
          <div class="file-list">
            <ul>
              <li tabindex="0" class="el-upload-list__item is-finished" v-for="(f, index) in list" v-bind:key="index">

                <a class="el-upload-list__item-name" style="width: 120px;" @click="browseFile(f)">
                  <i class="el-icon-document"></i><span :title="f.materialName">{{ f.materialName }}</span>
                </a>

                <a class="el-upload-list__item-name" style="width: 60px;">
                  {{ f.userName}}
                </a>
                <label class="el-upload-list__item-status-label"><i
                  class="el-icon-upload-success el-icon-circle-check"></i></label>
                <i class="el-icon-download" @click.stop="download(f.materialPath,f.materialName,f.isGeneratedFile)" v-if='f.downloadFlag !== "1"'></i>
                <i class="el-icon-close" @click.stop="deleteTaskSaveMaterialInfo(f)" v-if='typeFlag === "6"'></i>
              </li>

            </ul>
          </div>
        </div>
      </el-collapse-item>

      <!-- 面板-复核只读-审核记录 -->
      <template v-for="(item,i) in opinionList" v-if='typeFlag !== "20"'>
        <collapse-review-read :object="item" name="item.taskKey" :key="i"></collapse-review-read>
      </template>

      <!-- 重新编辑审核历史记录 -->
      <template v-for="(obj,j) in reeditApplicationList"
                v-if='reeditApplicationList.length && (((typeFlag === "2" || typeFlag === "3" || typeFlag === "4" || typeFlag === "5") && isSubmitReedit === "1")
                || (typeFlag === "6" && isSubmitReedit !== "1") || (typeFlag === "0" || typeFlag === "15" || typeFlag === "1" || typeFlag === "7" || typeFlag === "8"))'>
        <el-collapse-item title="重新编辑审核历史记录" :name="reeditApplicationList.length - 1  == j?'white1':'reeditApplicationList'+j">
          <div class="nested-panel">
            <el-collapse v-model="activeNames2" accordion>

              <!-- 面板-复核只读-重新编辑申请记录 -->
              <template v-for="(item,i) in obj.htTaskPeriodVOList">
                <collapse-review-read :object="item" :noPenalty="true" :key="i"></collapse-review-read>
              </template>

              <!-- 面板-复核可写-重新编辑申请 -->
              <collapse-review-write :object="object" name="activeNames2" @submit="changeObject" :noPenalty="true"
                                     v-if='(typeFlag === "2" || typeFlag === "3" || typeFlag === "4" || typeFlag === "5") && isSubmitReedit === "1" && obj.isShowWrite'></collapse-review-write>

            </el-collapse>
          </div>
        </el-collapse-item>
      </template>

      <!-- 面板-复核可写-A3和成果文件 -->
      <collapse-review-write :object="object" name="white1" @submit="changeObject" :noPenalty="false"
                             :errorTyleList="errorTyleList"
                             v-if='(typeFlag === "2" || typeFlag === "3" || typeFlag === "4" || typeFlag === "5") && isSubmitReedit !== "1"'></collapse-review-write>

      <!-- 审核意见文件 -->
      <fileList title="审核意见文件" :showUploadBtn="false" :delBtnFlag="false" :list="auditFileList"
                v-if='isMainHeadFlag === "1" && auditFileList.length > 0 && (typeFlag === "0" || typeFlag === "1" || typeFlag === "15")'></fileList>

    </el-collapse>
    </div>

    <!-- 提交审核弹窗组件 成果文件阶段 -->
    <dialog-submit-audit ref="submit" @submit="submitAuditOutcomeDocumentPeriod" :above="above" @cancel="cancel" :reviewer="reviewer"></dialog-submit-audit>

    <preview-file :file="file" ref="dialogBrowseFile" :type="type" :id="taskId"></preview-file>

    <div class="btn-group">
        <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
          <el-form-item
              prop="fileNumber"
              label="档案编号"
              :rules="[{ required: true, message: '请输入档案编号', trigger: 'blur'}]">
          <!-- 档案编号 -->
          <el-input type="text" v-model="dynamicValidateForm.fileNumber" v-if='typeFlag === "8"' placeholder="请输入档案编号"></el-input>
            </el-form-item>
        </el-form>
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;"></p>

      <!-- 跳级审核 -->
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;">
        <el-checkbox v-model="above" style="color: white;"
                     v-if='(((approveTime == null && isSubmitReedit !== "1") || approveTime === "") && typeFlag === "1") || ((typeFlag === "2" || typeFlag === "3") && isSubmitReedit !== "1" )'>
          跳级审核
        </el-checkbox>
      </p>

      <!-- 成果文件归档阶段 -->
      <el-button type="primary" v-if='ArchiveSubmitAuditBtnFlag' @click="submit(false)">提交审核</el-button>
      <el-button type="primary" v-if='ArchiveSave' @click="submit(true)">保存</el-button>
      <el-button type="primary" v-if='ArchiveRecallBtnFlag' @click="recallTaskOutcomeDocumentPeriod">撤回</el-button>
      <el-button type="primary" v-if='ArchivePassBtnFlag' @click="auditingTaskOutcomeDocumentPeriod('1')">通过</el-button>
      <el-button type="primary" v-if='ArchiveIssueBtnFlag' @click="auditingTaskOutcomeDocumentPeriod('1')">签发</el-button>
      <el-button type="primary" v-if='ArchiveRejectBtnFlag' @click="auditingTaskOutcomeDocumentPeriod('0')">驳回</el-button>
      <el-button type="primary" v-if='ArchiveSubmitArchive' @click="submitArchive">提交归档</el-button>
      <el-button type="primary" v-if='ArchiveConfirmArchive' @click="confirmArchive('dynamicValidateForm')">确认归档</el-button>
      <el-button type="primary" v-if='ArchiveSubmitAuditBtnFlag' @click="preview">预览</el-button>

      <!-- 重新编辑申请 -->
      <el-button type="primary" v-if='ReeditSubmitAuditBtnFlag' @click="reeditApplication">重新编辑申请</el-button>
      <el-button type="primary" v-if='ReeditPassBtnFlag' @click="auditingOutcomeDocumentPeriodReeditApplication('1')">通过</el-button>
      <el-button type="primary" v-if='ReeditIssueBtnFlag' @click="reeditApplicationIssue('1')">签发</el-button>
      <el-button type="primary" v-if='ReeditRejectBtnFlag' @click="auditingOutcomeDocumentPeriodReeditApplication('0')">驳回</el-button>

    </div>

  </div>

</template>

<script>

  // 面板-上传下载
  import collapseUpAndDownload from '../../../../common/components/collapseUpAndDownload.vue';
  // 提交审核弹窗
  import dialogSubmitAudit from '../../../../common/components/dialogSubmitAudit.vue';
  // 面板-复核只读
  import collapseReviewRead from '../../../../common/components/collapseReviewRead.vue';
  // 面板-复核可写
  import collapseReviewWrite from '../../../../common/components/collapseReviewWrite.vue';
  // 面板-复核可写-重新编辑历史记录
  import collapseReviewWriteNoPoints from '../../../../common/components/collapseReviewWriteNoPoints.vue';
  // 浏览文件
  import previewFile from '../../../../common/components/previewFile.vue';

  export default {
    components: {
      // 引入上传组件
      'fileList': collapseUpAndDownload,
      // 提交审核弹窗
      'dialog-submit-audit': dialogSubmitAudit,
      // 面板-复核只读
      'collapse-review-read': collapseReviewRead,
      // 面板-复核可写
      'collapse-review-write': collapseReviewWrite,
      // 相关资料
      'reviewFileList': collapseUpAndDownload,
      // 重新编辑历史记录
      'collapseReviewWriteNoPoints': collapseReviewWriteNoPoints,
       // 浏览文件
      'previewFile':previewFile
    },
    props: ['currentTransmitData', 'postil', 'jurisdiction', 'typeFlag', 'errorTyleList', 'auditFileList'],
    data() {
      const formData = new FormData();
      return {
        // 任务标识
        type: "task",
        dynamicValidateForm:{// 表单
          fileNumber:''// 档案编号
        },
        formData: formData,
        obj: {
          title: '重新编辑审核'
        },
        // 表单项间距
        gutter: 40,
        // 折叠面板内容
        activeNames: 'white1',
        // 重新编辑默认选中
        activeNames2:'activeNames2',
        // 上传文件列表
        fileList: [],
        // 阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
        periodType: '',
        // 文件类别列表
        fileTypeList: [],
        // 以提审文件列表
        reviewFileList: [],
        // 是否跳级审核标识
        jumpFlag: false,
        //是否越级flag
        above: false,
        // 审核人
        reviewer: {
          // 当前
          current: '',
          // 下一个
          next: ''
        },
        // 查询审核意见列表
        opinionList: [],
        // 重新申请列表
        reeditApplicationList: [],
        // 一级面板的对象
        object: {
          title: '', // 一级面板的标题
          opinion: '', // 一级级面板的意见
          children: [{
            id: '', // 二级面板的被审批人标识
            name: '', // 二级面板的被审批人名字
            errorList: [ // 二级面板的被审批人的错误数组
              {
                num: '', // 错误数量
                type: '' // 错误类型
              }
            ]
          }]
        },
        // 流程下一节点
        nextTaskKey: '',
        // 委托任务Key
        delegateTaskKey: 'ManagerVerify',
        // 委托任务id
        delegateTaskId:"",
        // 是否可以撤回
        isCanRecall: null,
        // 任务阶段id
        taskPeriodId: "",
        // 部门唯一标识
        departmentId: '',
        // 咨询类别ID
        counselTypeId:'',
        // 任务阶段关联id
        relationId: '',
        // 归档文件列表
        list: [],
        loadChild: true,
        // 是否提交重新编辑申请
        isSubmitReedit: "",
        // 是否显示删除按钮
        delBtnFlag: false,
        // 上传归档文件数组
        archiveList:[],
        // 修改时间
        updateDate:"",
        // 审批时间
        approveTime:null,
        // 是否是主提审人
        isMainHeadFlag:"",
        // ============================================成果文件 归档
        // 成果文件（归档）提交审核按钮
        ArchiveSubmitAuditBtnFlag:false,
        // 保存
        ArchiveSave:false,
        // 成果文件（归档）撤回按钮
        ArchiveRecallBtnFlag:false,
        // 成果文件（归档）通过按钮
        ArchivePassBtnFlag:false,
        // 成果文件（归档）签发按钮
        ArchiveIssueBtnFlag:false,
        // 成果文件（归档）驳回按钮
        ArchiveRejectBtnFlag:false,
        // 成果文件（归档）提交归档
        ArchiveSubmitArchive:false,
        // 成果文件（归档）确认归档
        ArchiveConfirmArchive:false,
        // ============================================成果文件 重新编辑申请
        // 成果文件（重新编辑申请）提交审核按钮
        ReeditSubmitAuditBtnFlag:false,
        // 成果文件（重新编辑申请）通过按钮
        ReeditPassBtnFlag:false,
        // 成果文件（重新编辑申请）签发按钮
        ReeditIssueBtnFlag:false,
        // 成果文件（重新编辑申请）驳回按钮
        ReeditRejectBtnFlag:false,
        file:'',// 浏览文件
        tempArchiveList:[],// 存放上传的归档文件（未提交到后台的数据）
        projectSts:"",// 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
        taskId:""
      };
    },
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          let vm = this;
          vm.currentTransmitData = newval;
        },
        immeditate: true,
        deep: true
      },
      postil: {
        handler(newval) {
          let vm = this;
          vm.postil = newval;
        },
        immediate: true,
        deep: true
      },
      jurisdiction: {
        handler(newval) {
          let vm = this;
          vm.jurisdiction = newval;
        },
        immediate: true,
        deep: true
      },
      typeFlag: {
        handler(newval) {
          let vm = this;
          vm.typeFlag = newval;
        },
        immediate: true,
        deep: true
      },
      errorTyleList: {
        handler(newval) {
          let vm = this;
          vm.errorTyleList = newval;
        },
        immediate: true,
        deep: true
      },
      auditFileList: {
        handler(newval) {
          let vm = this;
          vm.auditFileList = newval;
        },
        immediate: true,
        deep: true
      }
    },
    created() {
      let vm = this;
      vm.init()
    },
    methods: {
      // =================================================初始化信息 start=================================================
      // 初始化信息
      init() {
        // 清除上次的值
        this.above = false;

        this.relationId = this.currentTransmitData.relationId;// 任务阶段关联id
        this.taskId = this.currentTransmitData.taskId;// 任务id
        this.periodType = this.currentTransmitData.periodType;// 阶段类型
        this.departmentId = this.currentTransmitData.departmentId;// 部门id
        this.counselTypeId = this.currentTransmitData.counselTypeId;// 咨询类别id
        this.isMainHeadFlag = this.currentTransmitData.isMainHeadFlag;// 是否是主提审人
        this.projectSts = this.currentTransmitData.projectSts;// 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))

        console.log("第", this.currentTransmitData.treeNodeLevel+" 级：");
        console.log("typeFlag",this.typeFlag);

        // 根据任务阶段关联id查询任务信息
        this.searchTaskDetailInfo(this.relationId);
        // 查询素材列表
        this.searchOutcomeDocumentMaterial(this.taskId, this.relationId);
        // 查询重新编辑审核列表
        this.searchTaskReeditHisTaskInst(this.taskId, this.relationId);
        // 查询任务归档文件列表信息
        this.searchTaskArchiveFileInfo(this.relationId);
        // 查询相关资料文件列表
        this.searchIsReviewTaskPeriodMaterialList(this.relationId);
        // 根据任务阶段关联id查询审核意见列表
        this.searchHTTaskHisTaskInst(this.relationId);

      },
      // =================================================初始化信息 end=================================================
      // =================================================未提交审核时的预览 start=================================================
      // 未提交审核时的预览
      preview(data) {

        let nextTaskKey = this.nextTaskKey;// 下一流程节点
        if (this.above) {
          nextTaskKey = this.getNextStepNode(nextTaskKey);// 获取下一流程节点
        }

        let sendData = {
          relationId: this.relationId,// 任务阶段关联id
          materialIds: data.checkbox,// 文件id
          submitNode: nextTaskKey,// 下一流程节点
          periodName: this.currentTransmitData.label// 阶段名称
        };

        this.bus.$emit('preview', sendData);// 触发提交审核-编制成果文件事件
      },
      // =================================================未提交审核时的预览 end=================================================
      // =================================================正常预览 start=================================================
      // 浏览文件
      browseFile(file){
        let vm = this;
        if(!file.materialPath){
          vm.stateDialog('error', "该文件暂时不能浏览");
          return;
        }
        vm.file = vm.tool.deepCopy(file, vm.tool.deepCopy);
        vm.$refs.dialogBrowseFile.show = true;
      },
      // =================================================正常预览 end=================================================
      // =================================================任务详情 start=================================================
      // 查询任务信息
      searchTaskDetailInfo(relationId) {
        let vm = this;
        vm.apiAddress('/task/query/searchTaskDetailInfoByRelationId', {
          relationId: relationId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message)
          } else {

            let taskInfo = res.data.dataVO;// 任务阶段信息
            vm.object.title = vm.getStepName(taskInfo.delegateTaskKey);// 审核级别名称
            vm.nextTaskKey = taskInfo.submitNode;// 下一节点
            vm.delegateTaskKey = taskInfo.delegateTaskKey;// 任务委托key
            vm.isCanRecall = taskInfo.isCanRecall;// 能否撤回
            vm.isSubmitReedit = taskInfo.isSubmitReedit;// 设置是否提交了重新编辑申请
            console.log("isSubmitReedit",vm.isSubmitReedit)
            vm.updateDate = taskInfo.updateDate;// 修改时间
            vm.approveTime = taskInfo.approveTime;// 审批时间

            let userList = taskInfo.userList;// 人员列表
            // 如果人员列表不为空
            if (userList != undefined) {
              // 循环人员列表，并转换格式
              for (var i = userList.length - 1; i >= 0; i--) {
                userList[i].errorList = [];
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, "id"));
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, "name"));
              }
            }

            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);// 当前流程人员
            vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));// 下一流程人员
            vm.object.children = userList;

            // ==============================成果文件（归档） 按钮 start=================================
            // 是否显示删除按钮  true:不显示  false:显示
            vm.delBtnFlag = !(vm.typeFlag === "1");
            // 成果文件（归档）提交审核按钮
            vm.ArchiveSubmitAuditBtnFlag = vm.typeFlag === "1" && vm.isSubmitReedit !== "1";
            // 成果文件（归档）保存按钮
            vm.ArchiveSave = vm.ArchiveSubmitAuditBtnFlag && ((vm.approveTime == null && vm.isSubmitReedit !== "1") || vm.approveTime === "");
            // 成果文件（归档）撤回按钮
            vm.ArchiveRecallBtnFlag = vm.typeFlag === "20" && vm.isSubmitReedit !== "1";
            // 成果文件（归档）通过按钮
            vm.ArchivePassBtnFlag = (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4" || vm.typeFlag === "7") && vm.isSubmitReedit !== "1";
            // 成果文件（归档）签发按钮
            vm.ArchiveIssueBtnFlag = vm.typeFlag === "5" && vm.isSubmitReedit !== "1";
            // 成果文件（归档）驳回按钮
            vm.ArchiveRejectBtnFlag = (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4" || vm.typeFlag === "5" || vm.typeFlag === "7") && vm.isSubmitReedit !== "1";
            // 成果文件（归档）提交归档
            vm.ArchiveSubmitArchive = vm.typeFlag === "6" && vm.isSubmitReedit !== "1";
            // 成果文件（归档）确认归档
            vm.ArchiveConfirmArchive = vm.typeFlag === "8" && vm.isSubmitReedit !== "1";

            // ==============================成果文件（重新编辑申请） 按钮 start=================================
            // 成果文件（重新编辑申请）提交审核按钮
            vm.ReeditSubmitAuditBtnFlag = vm.typeFlag === "6" && vm.isSubmitReedit !== "1";
            // 成果文件（重新编辑申请）通过按钮
            vm.ReeditPassBtnFlag = (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4") && vm.isSubmitReedit === "1";
            // 成果文件（重新编辑申请）签发按钮
            vm.ReeditIssueBtnFlag = vm.typeFlag === "5" && vm.isSubmitReedit === "1";
            // 成果文件（重新编辑申请）驳回按钮
            vm.ReeditRejectBtnFlag = (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4" || vm.typeFlag === "5") && vm.isSubmitReedit === "1";

          }
        }).catch(err => {
          vm.stateDialog('error', err);
        })
      },
      // =================================================任务详情 start=================================================
      // =================================================成果文件阶段 查询文件列表 start=================================================
      // 查询素材列表
      searchOutcomeDocumentMaterial(taskId, relationId) {
        let vm = this;
        // 根据任务ID查询阶段关联文件
        vm.apiAddress('/task/query/searchOutcomeDocumentMaterial', {
          "taskId": taskId, // 任务id
          "relationId": relationId // 关联id
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            let tempTaskInfo = res.data.dataVO;
            let taskMaterialPath = tempTaskInfo.taskMaterialPath; // 任务文件路径
            let taskMaterialName = tempTaskInfo.taskMaterialName;// 任务文件名称
            let userName = tempTaskInfo.userName;// 提审人名称
            let taskType = tempTaskInfo.taskType;// 任务文件类型

            // 是否调用任务文件模板生成接口 默认走正常的下载接口
            // 如果任务文件类型为模板，则返回true
            let isGeneratedFile = taskType == '1'?true:false;
            // 如果是主提审人，则显示删除按钮,否则不显示删除按钮
            let isCanDel = vm.typeFlag == '1'?"1":"0";
            // 多文件列表
            let tempFileList = res.data.fileList;
            // 如果多文件列表存在，则循环遍历放入文件列表对象中
            if(tempFileList.length > 0){
              for (let i = 0;i<tempFileList.length;i++) {
                let tempObj = {
                  "materialName": tempFileList[i].materialName,
                  "materialPath": tempFileList[i].materialPath,
                  "userName": tempFileList[i].userName,
                  "isGeneratedFile": isGeneratedFile,
                  "isCanDel": isCanDel,
                  "relationId": tempFileList[i].relationId
                };
                vm.fileList.push(tempObj);
              }
            }

            // 如果成果文件存在，则循环遍历放入文件列表对象中
            if (tempTaskInfo.taskMaterialName != null && tempTaskInfo.taskMaterialName != "") {
              let tempObj = {
                "materialName": taskMaterialName,
                "materialPath": taskMaterialPath,
                "userName": userName,
                "isGeneratedFile": isGeneratedFile,
                "isCanDel": isCanDel
              };
              vm.fileList.push(tempObj);
            }

          } else {
            vm.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        })
      },
      // =================================================成果文件阶段 查询文件列表 end=================================================
      // =================================================成果文件阶段 查询重新编辑审核列表 start=================================================
      //查询重新编辑审核列表
      searchTaskReeditHisTaskInst(taskId, relationId) {
        let vm = this;
        //查询审核意见列表
        vm.apiAddress('/task/query/searchTaskReeditHisTaskInst', {
          "taskId": taskId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message)
          } else {

            let dataList = res.data.dataList;// 获取重新编辑审核历史记录

            let isSubmitReedit = res.data.isSubmitReedit;// 是否提交了重新编辑申请

            // 如果有数据
            if (dataList.length !== 0) {

              let o = 0;// 定义标识，用于判断dataList中是否是最后一个对象

              let next = false;// 定义标识，用于判断是否在dataList中添加一个对象

              // 循环遍历所有历史记录集合
              for (var k = 0; k < dataList.length; k++) {

                //for循环遍历更改属性名
                for (var i = 0; i < dataList[k].htTaskPeriodVOList.length; i++) {

                  // 设置三级审核标识（一级审核、二级审核...）
                  dataList[k].htTaskPeriodVOList[i].title = vm.getStepName(dataList[k].htTaskPeriodVOList[i].taskKey);

                  //将属性名htTaskPeriodVOList转换成属性名children
                  dataList[k].htTaskPeriodVOList[i].children = dataList[k].htTaskPeriodVOList[i].htTaskPeriodVOList;

                  let children = dataList[k].htTaskPeriodVOList[i].children;// 获取转换格式后的children

                  // 循环遍历历史列表
                  for (var j = 0; j < children.length; j++) {

                    children[j].time = children[j].approveTime;//将属性名approveTime转换成属性名time

                    children[j].name = children[j].approveUserName;//将属性名approveUserName转换成属性名name

                    children[j].opinion = children[j].rejectReason;//将属性名rejectReason转换成属性名opinion

                  }
                }

                o++;// 每循环一次dataList，o则加1

                // 当o为dataList数据的长度的时候，说明到了循环到了最后一个对象，则判断是否显示可写审核意见组件
                if (o == dataList.length) {

                  // dataList[k].verifyState为0时，表示未通过
                  if (dataList[k].verifyState == 0) {

                    dataList[k].isShowWrite = false;// 隐藏最后一个组件中的可写审核意见组件

                    next = true;// 在dataList添加一个对象

                    // dataList[k].verifyState为2时，审核中
                  } else if (dataList[k].verifyState == 2) {

                    dataList[k].isShowWrite = true;// 显示最后一个组件中的可写审核意见组件

                  }

                } else {
                  dataList[k].isShowWrite = false;// 隐藏最后一个组件中的可写审核意见组件
                }

              }

              // 在dataList中添加一个对象
              if (next && (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4" || vm.typeFlag === "5") && isSubmitReedit === "1") {
                // 添加一条数据
                dataList.push({
                  "htTaskPeriodVOList": [],
                  "isShowWrite": true
                });
              }

              // 如果没有数据，则需要判断是否提交了重新编辑申请，按钮显示类型是否是三级审核人或者签发人，如果满足条件，则在reeditApplicationList中创建一条数据，用于输入审核意见
            } else {
              // 如果提交了重新编辑申请，则添加一条数据
              if (isSubmitReedit === "1" && (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4" || vm.typeFlag === "5")) {
                // 添加一条数据
                dataList.push({
                  "htTaskPeriodVOList": [],
                  "isShowWrite": true
                });
              }

            }

            // 把转换后的dataList赋值给reeditApplicationList
            vm.reeditApplicationList = dataList;

          }
        }).catch(err => {
          vm.stateDialog('error', err)
        });
      },
      // =================================================成果文件阶段 查询重新编辑审核列表 end=================================================
      // =================================================归档文件 start=================================================
      // 下载归档文件
      download(fileUrl, fileName, isGeneratedFile) {
        vm.downloadFile(fileUrl, fileName, isGeneratedFile);
      },
      // 删除归档文件信息
      deleteTaskSaveMaterialInfo(val) {
        let vm = this;
        vm.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {

          // 如果是“1”，则是没添加到后台的数据，否则是数据库中的数据
          if(val.deleteFlag ==="1"){

            for (var i = 0; i < vm.list.length; i++) {
              if (vm.list[i].uuid == val.uuid) {
                vm.list.splice(i, 1);
                vm.archiveList.splice(i, 1);
              }
            }

          } else {

            vm.apiAddress('/task/delete/deleteTaskSaveMaterialInfo', {
              taskId:vm.taskId,
              relationId: val.materialId,
              materialPath: val.materialPath
            }).then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == "success") {
                vm.stateDialog('success', res.data.message);
                // 查询归档文件列表
                vm.searchTaskArchiveFileInfo(vm.relationId);
              } else {
                vm.stateDialog('error', res.data.message)
              }
            }).catch(e => {
              vm.stateDialog('error', e)
            })

          }
        }).catch(() => {});

      },
      // 多文件存储
      beforeAvatarUpload(file) {
        // 存储每次选择的文件
        this.archiveList.push(file);
        // 临时对象
        let tempObj = {
          // 文件名称
          "materialName":file.name,
          // 下载标识
          "downloadFlag":"1",
          // 删除标识
          "deleteFlag":"1",
          // 唯一标识
          "uuid":(((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
        };

        // 临时文件
        this.tempArchiveList.push(tempObj);

        // 把每次选择的对象放入多文件列表中
        this.list.push(tempObj);
        return false;
      },
      // 查询任务归档文件列表信息
      searchTaskArchiveFileInfo(relationId) {
        let vm = this;
        // 提交文件请求
        vm.apiAddress('/task/query/searchTaskSaveMaterialList', {
          "relationId": relationId
        }).then(res => {
          if (res.data.status == "success") {

            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));
            }

            // 设置转换之后的list
            vm.list = dataList;

          } else {
            vm.stateDialog('error', res.data.message)
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        })
      },
      // 上传文件点击事件
      uploadFile() {
        this.$refs['uploadDemo'].$children[0].$el.click();
      },
      // =================================================归档文件 end=================================================
      // =================================================相关资料 start=================================================
      // 查询相关资料文件列表
      searchIsReviewTaskPeriodMaterialList(relationId) {
        let vm = this;
        vm.apiAddress('/task/query/searchIsReviewTaskPeriodMaterialList', {
          "relationId": relationId,
          "taskId": vm.taskId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message);
          } else {
            let dataList = res.data.dataList;

            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));
            }
            vm.reviewFileList = dataList
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        });
      },
      // =================================================相关资料 start=================================================
      // =================================================查询审核意见文件列表 start=================================================
      //查询审核意见列表
      searchHTTaskHisTaskInst(relationId) {
        let vm = this;
        //查询审核意见列表
        vm.apiAddress('/task/query/searchHTTaskHisTaskInst', {
          relationId: relationId,
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message)
          } else {

            /*------------------------------格式转换START---------------------------------*/

            let dataList = res.data.dataList;

            //for循环遍历更改属性名
            for (var i = 0; i < dataList.length; i++) {

              dataList[i].title = vm.getStepName(dataList[i].taskKey)

              //将属性名htTaskPeriodVOList转换成属性名children
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htTaskPeriodVOList/g, "children"));

              // 获取转换格式后的children
              let children = dataList[i].children;

              // 循环遍历历史列表
              for (var j = 0; j < children.length; j++) {
                //将属性名approveTime转换成属性名time
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, "time"));
                //将属性名approveUserName转换成属性名name
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, "name"));
                //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g, "persions"));
                //将属性名rejectReason转换成属性名opinion
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, "opinion"));

                let persions = vm.groupBy(children[j].persions, function (item) {
                  return [item.userId]
                })

                for (var k = 0; k < persions.length; k++) {
                  persions[k].name = persions[k].dataList[0].userName
                  //将属性名dataList转换成属性名errorList
                  persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, "errorList"));
                  let errorList = persions[k].errorList;
                  for (var l = errorList.length - 1; l >= 0; l--) {
                    //将属性名questionName转换成属性名type
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, "type"));
                    //将属性名questionNum转换成属性名num
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, "num"));
                  }
                  persions[k].errorList = errorList;
                }
                children[j].persions = persions;
              }
            }

            /*---------------------------------格式转换END-------------------------------------*/

            // 把转换后的dataList赋值给opinionList
            vm.opinionList = dataList
          }
        }).catch(err => {
          vm.stateDialog('error', err)
        });
      },
      // =================================================查询审核意见文件列表 end=================================================
      // =================================================归档阶段 发起归档 项目监理部 start=================================================
      // 发起归档
      initiateArchive() {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {
          vm.apiAddress('/task/modify/initiateArchive', {
            // 任务id
            "taskId": vm.taskId
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {

              // 刷新页面
              vm.$router.go(0);

            } else {
              vm.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            vm.stateDialog('error', e);
          })
        }).catch(() => {});
      },
      // =================================================归档阶段 发起归档 项目监理部 end=================================================
      // =================================================归档阶段 确认归档 项目监理部 start=================================================
      // 确认归档
      confirmArchiveXM(formName) {
        let vm = this;
         vm.formValidation(formName).then(function(){
           vm.$confirm('是否提交?', '提示', {
                 confirmButtonText: '确定',
                 cancelButtonText: '取消',
                 center: true
           }).then(() => {
             vm.apiAddress('/task/modify/confirmArchiveXM', {
               // 任务id
               "taskId": vm.taskId,
               // 档案编号
               "fileNumber": vm.dynamicValidateForm.fileNumber,
               // 修改时间
               "delegateTaskId": vm.delegateTaskId
             }).then(res => {
               // 如果返回成功，则提示成功信息，否则提示失败信息
               if (res.data.status == "success") {
                 // 刷新页面
                 vm.$router.go(0);
               } else {
                 vm.stateDialog('error', res.data.message)
               }
             }).catch(e => {
               vm.stateDialog('error', e)
             });
           }).catch(() => {});
         });
      },
      // =================================================确认归档 查询文件列表 end=================================================
      // =================================================成果文件 重新编辑签发 start=================================================
      // 重新编辑签发
      reeditApplicationIssue(isPass) {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {
          let paramObj = vm.object;
          paramObj.isPass = isPass;// 是否通过
          paramObj.updateDate = vm.updateDate;// 修改时间
          let nextTaskKey = vm.nextTaskKey;// 下一流程节点

          if (vm.above) {
            // 获取下一流程节点
            nextTaskKey = vm.getNextStepNode(nextTaskKey)
          }
          // 流程下一节点
          paramObj.submitNode = nextTaskKey;
          paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, "delegateTaskKey"));
          if (isPass == '0') {
            paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, "rejectReason"));
          }
          // 任务阶段关联id
          paramObj.relationId = vm.relationId;
          vm.apiAddress('/task/add/reeditApplicationIssue', paramObj).then(res => {
            if (res.data.status == 'failure') {
              vm.stateDialog('error', res.data.message);
            } else {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // vm.$router.go(0)
              vm.$router.push('/page/work/default');
            }
          }).catch(err => {
            vm.stateDialog('error', err);
          })
        }).catch(() => {});
      },
      // =================================================成果文件 重新编辑签发 end=================================================
      // =================================================成果文件 重新编辑的通过和驳回 start=================================================
      // 审核通过和驳回
      auditingOutcomeDocumentPeriodReeditApplication(isPass) {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {
          let paramObj = vm.object;
          // 是否通过
          paramObj.isPass = isPass;
          // 修改时间
          paramObj.updateDate = vm.updateDate;

          // 下一流程节点
          let nextTaskKey = vm.nextTaskKey;

          if (vm.above) {
            // 获取下一流程节点
            nextTaskKey = vm.getNextStepNode(nextTaskKey)
          }

          paramObj.submitNode = nextTaskKey;
          paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, "delegateTaskKey"));
          if (isPass == '0') {
            paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, "rejectReason"));
          }
          // 任务阶段关联id
          paramObj.relationId = vm.relationId;
          vm.apiAddress('/task/add/auditingOutcomeDocumentPeriodReeditApplication', paramObj).then(res => {
            if (res.data.status == 'failure') {
              vm.stateDialog('error', res.data.message);
            } else {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // vm.$router.go(0)
              vm.$router.push('/page/work/default');
            }
          }).catch(err => {
            vm.stateDialog('error', err);
          })
        }).catch(() => {});
      },
      // =================================================成果文件 重新编辑的通过和驳回 end=================================================
      // =================================================成果文件 重新编辑申请 start=================================================
      // 重新编辑申请
      reeditApplication() {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {

          // 重新编辑申请请求
          vm.apiAddress('/task/modify/submitReedit', {
            // 任务阶段关联id
            "relationId": vm.relationId
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // vm.$router.go(0);
              vm.$router.push('/page/work/default');
            } else {
              vm.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            vm.stateDialog('error', e);
          })

        }).catch(() => {});
      },
      // =================================================成果文件 重新编辑申请 start=================================================
      // =================================================成果文件 确认归档 start=================================================
      formValidation(formName){
        let vm = this;
        return new Promise(function(resolve, reject) {
          vm.$refs[formName].validate((valid) => {
              if (valid)resolve();
          });
         });
      },
      // 确认归档
      confirmArchive(formName) {
        let vm = this;
        vm.formValidation(formName).then(function(){
          vm.$confirm('是否提交?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            center: true
          }).then(() => {
            // 确认归档请求
            vm.apiAddress('/task/modify/confirmArchive', {
              // 任务阶段关联id
              relationId: vm.relationId,
              // 档案编号
              fileNumber: vm.dynamicValidateForm.fileNumber,
              // 修改时间
              updateDate: vm.updateDate
            }).then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == "success") {
                vm.stateDialog('success', res.data.message);
                // 刷新
                // vm.$router.go(0);
                vm.$router.push('/page/work/default');
              } else {
                vm.stateDialog('error', res.data.message);
              }
            }).catch(e => {
              vm.stateDialog('error', e);
            });
          }).catch(() => {});
        });
      },
      // =================================================成果文件 确认归档 end=================================================
      // =================================================成果文件 提交归档 start=================================================
      // 提交归档
      submitArchive(formName) {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          // 删除上次提交之后存储的文件数据
          vm.formData.delete("resourceFiles");
          // 删除上次提交之后存储的关联id
          vm.formData.delete("relationId");
          // 删除上次提交之后存储的任务id
          vm.formData.delete("taskId");

          // 设置多图片传参数
          for (var i = 0; i < vm.archiveList.length; i++) {
            vm.formData.append("resourceFiles", vm.archiveList[i])
          }

          // 任务id
          vm.formData.append("taskId",vm.taskId);
          // 任务阶段关联id
          vm.formData.append("relationId",vm.relationId);

          // 提交归档请求
          vm.apiAddress('/task/modify/submitArchive', vm.formData).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // vm.$router.go(0);
              vm.$router.push('/page/work/default');
            } else {
              vm.stateDialog('error', res.data.message);
            }
          }).catch(e => {
            vm.stateDialog('error', e);
          });
        }).catch(() => {});
      },
      // =================================================成果文件 提交归档 end=================================================
      // =================================================成果文件 归档文件的通过和驳回 start=================================================
      // 审核通过和驳回
      auditingTaskOutcomeDocumentPeriod(isPass) {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let paramObj = vm.object;
          // 是否通过
          paramObj.isPass = isPass;
          // 修改时间
          paramObj.updateDate = vm.updateDate;
          // 当前节点
          paramObj.currentState = vm.typeFlag;

          // 下一流程节点
          let nextTaskKey = vm.nextTaskKey;

          if (vm.above) {
            // 获取下一流程节点
            nextTaskKey = vm.getNextStepNode(nextTaskKey)
          }

          paramObj.onlyOfficeEditKey = vm.postil.onlyOfficeEditKey;
          paramObj.reviewFileName = vm.postil.reviewFileName;
          paramObj.reviewFilePath = vm.postil.reviewFilePath;
          paramObj.submitNode = nextTaskKey;
          paramObj.userQuestionRecordRelationList = new Array();
          paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g, "delegateTaskKey"));
          if (isPass == '0') {
            paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g, "rejectReason"));
            for (var i = paramObj.children.length - 1; i >= 0; i--) {
              if (paramObj.children[i].errorList) {
                for (var j = paramObj.children[i].errorList.length - 1; j >= 0; j--) {
                  paramObj.children[i].errorList[j].userId = paramObj.children[i].id
                  paramObj.children[i].errorList[j].userName = paramObj.children[i].name
                  if (paramObj.children[i].errorList[j].questionNum != '0') {
                    paramObj.userQuestionRecordRelationList.push(paramObj.children[i].errorList[j])
                  }
                }
              }
            }
            delete paramObj.children;
          }
          // 任务阶段关联id
          paramObj.relationId = vm.relationId;
          vm.apiAddress('/task/add/auditingTaskOutcomeDocumentPeriod', paramObj).then(res => {
            if (res.data.status == 'failure') {
              vm.stateDialog('error', res.data.message);
            } else {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // vm.$router.go(0);
              vm.$router.push('/page/work/default');
            }
          }).catch(err => {
            vm.stateDialog('error', err);
          });
        }).catch(() => {});
      },
      // =================================================成果文件 归档文件的通过和驳回 end=================================================
      // =================================================成果文件 撤回 start=================================================
      // 撤回
      recallTaskOutcomeDocumentPeriod() {
        let vm = this;
        vm.$confirm('是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          vm.apiAddress('/task/modify/recallTaskOutcomeDocumentPeriod', {
            // 任务阶段关联id
            relationId: vm.relationId
          }).then(res => {
            if (res.data.status == 'failure') {
              vm.stateDialog('error', res.data.message);
            } else {
              vm.stateDialog('success', res.data.message);
              // 刷新
              vm.$router.go(0);
            }
          }).catch(err => {
            vm.stateDialog('error', err);
          });
        }).catch(() => {
        });
      },
      // =================================================成果文件 撤回 end=================================================
      // =================================================成果文件 提交审核 start=================================================
      // 面板-复核可写改变时
      changeObject(obj) {
        this.object = obj
      },
      // 跳级取消
      cancel() {
        this.above = false;
      },
      // 展示可以提交审核的文件列表,如果没有文件，则提示请上传文件
      submit(type) {
        let vm = this;
        vm.$nextTick(function () {
          // 查询任务阶段相关所有文件类型以级相关文件类型的文件
          vm.apiAddress('/task/query/searchAllMaterialTypeAndMaterialList', {
            // 任务阶段关联id
            relationId: vm.relationId
          }).then(
            res => {
              if (res.data.status == 'failure') {
                // 失败提示
                vm.stateDialog('error', res.data.message)
              } else {
                // 默认直接提交审核
                let isSubmit = true;
                // todo
                // 如果是提交审核
                if (!type) {
                  // 文件列表
                  let dataList = res.data.dataList;
                  for (var i = 0; i < dataList.length; i++) {
                    if (dataList[i].children.length != 0) {
                      // 不直接提交审核
                      isSubmit = false;
                     // 校验成果文件必填字段
                      vm.bus.$emit('checkOutcomeDocuments',function(){
                        console.log('校验成功!');
                        // 触发提交审核-编制成果文件事件
                        // 把返回的文件列表的数据放在提交审核的弹出框的list中
                        vm.$refs.submit.list = dataList;
                        // 显示提交审核的弹出框
                        vm.$refs.submit.show = true;
                      });
                      break;
                    }
                  }
                }
                // 如果没有任何文件，则直接提交审核
                if (isSubmit) {
                  let param = {"checkbox": []};
                    vm.bus.$emit('checkOutcomeDocuments',function(){
                      // 成果文件提交审核
                      vm.submitAuditOutcomeDocumentPeriod(param,type);
                    });
                }
              }
            }).catch(err => {
            vm.stateDialog('error', err)
          });
        });
      },
      // 提交审核
      submitAuditOutcomeDocumentPeriod(data,type) {
        let vm = this;
        // 下一流程节点
        let nextTaskKey = this.nextTaskKey;

        if (this.above) {
          // 获取下一流程节点
          nextTaskKey = this.getNextStepNode(nextTaskKey)
        }

        let sendData = {
          // 任务阶段关联id
          relationId: this.relationId,
          // 文件id
          materialIds: data.checkbox,
          // 下一流程节点
          submitNode: nextTaskKey,
          // 阶段名称
          periodName: this.currentTransmitData.label,
          //提交标识(用以区分提交审核还是保存)
          submitFlag: type
        };
        vm.bus.$emit('submitPreparationOfOutcomeDocuments', sendData);
      },
      // =================================================成果文件 提交审核 end=================================================
      // =================================================查询素材列表 start=================================================
      // 查询素材列表
      searchMaterialList(relationId,typeFlag) {
        let vm = this;
        // 根据任务阶段ID查询阶段关联文件
        vm.apiAddress('/task/query/searchTaskPeriodMaterialList', {
          relationId: relationId
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));

              // 如果是项目监理部
              if (vm.departmentId === vm.superviseDeptId){

                // 如果是主提审人或者是普通执行人并且已归档，则不显示删除按钮
                if ((typeFlag === "1" || typeFlag === "15") && dataList[i].isReview === "1") {

                   dataList[i].isCanDel = '0';

                }

                // 如果不是项目监理部
              }else{

                // 如果是主提审人，则显示删除按钮
                if (typeFlag == '1') {

                  dataList[i].isCanDel = '1';

                  // 如果是普通执行人，则随后台显示
                } else if (typeFlag == '15') {

                  continue;

                  // 否则，不显示删除按钮
                } else {

                  dataList[i].isCanDel = '0';

                }

              }

            }
            // 设置转换之后的list
            vm.fileList = dataList;

          } else {
            vm.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        })
      },
      // =================================================查询素材列表 end=================================================
      // =================================================查询上传素材类型下拉列表 start=================================================
      // 查询上传素材类型下拉列表
      searchMaterialTypeList() {
        let vm = this;
        let formData = {
          departmentId:vm.departmentId,
          counselTypeId:vm.counselTypeId
        }
        // 查询上传素材类型下拉列表
        vm.apiAddress('/tenderTask/query/searchMaterialTypeList', formData).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            // 获取文件类型list
            let dataList = res.data.dataList;
            // 转换格式
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeId/g, "id"));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeName/g, "label"));
            }
            // 设置转换之后的list
            vm.fileTypeList = dataList
          } else {
            vm.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        })
      },
      // =================================================查询上传素材类型下拉列表 end=================================================
      // =================================================查询归档任务信息 start=================================================
      // 查询归档任务信息
      searchArchiveTaskDetailInfo(taskId) {
        let vm = this;
        vm.apiAddress('/task/query/searchArchiveTaskDetailInfoByTaskId', {
          taskId: taskId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message)
          } else {
            // 委托任务id
            vm.delegateTaskId = res.data.dataVO.delegateTaskId;
          }
        }).catch(err => {
          vm.stateDialog('error', err)
        })
      },
      // =================================================查询归档任务信息 end=================================================
      // 删除文件信息
      deleteFileInfo(val) {
        let vm = this;
        vm.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          vm.apiAddress('/task/delete/deleteTaskPeriodMaterialInfo', {
            relationId: val.materialId,
            materialPath: val.materialPath,
            taskId:vm.taskId
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);

              // 查询素材列表
              vm.searchMaterialList(vm.relationId,vm.typeFlag);

            } else {
              vm.stateDialog('error', res.data.message);
            }
          }).catch(e => {
            vm.stateDialog('error', e)
          })
        }).catch(() => {
        });
      },
      // 删除成果文件
      deleteResultFileInfo(val){
        let vm = this;
        vm.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          vm.apiAddress('/task/delete/excDeleteResultFileMaterialInfo', {
            relationId: val.relationId,
            materialPath: val.materialPath,
            taskId:vm.taskId
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);
              // 查询素材列表
              // vm.searchOutcomeDocumentMaterial(vm.taskId);
              // 根据任务阶段关联id查询任务信息
              // vm.searchTaskDetailInfo(vm.relationId);
              // 刷新页面
              vm.$router.go(0);
            } else {
              vm.stateDialog('error', res.data.message);
            }
          }).catch(e => {
            vm.stateDialog('error', e)
          })
        }).catch(() => {
        });
      },
      // 下载文件信息
      downloadMatrial(item) {
        // 调用全局下载方法
        this.downloadFile(item.url, item.name);
      },
      // 提交上传文件
      submitForm(obj) {
        let vm = this;
        // 任务阶段id
        obj.append("taskPeriodId", vm.relationId);
        // 文件类型
        obj.append("fileType", obj.get('select'));
        obj.delete('select')

        // 提交文件请求
        vm.apiAddress('/task/add/insertTaskPeriodMaterialInfo', obj).then(res => {
          if (res.data.status == "success") {
            vm.stateDialog('success', res.data.message);
            // 查询素材列表
            vm.searchMaterialList(vm.relationId,vm.typeFlag);
          } else {
            vm.stateDialog('error', res.data.message)
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        })
      },
      checkFile(n) {
        this.bus.$emit('checkFile', n);
      },
      present() {
        this.bus.$emit("changeform")
      },
      searchList(data) {
        this.trigger("searchList", data);
      },
      trigger(name, data) {
        // 触发事件
        this.bus.$emit(name, data);
      }
    }
  };
</script>

<style lang="scss">
  @import url('../../../../common/css/titleDownLoadTool.css');
  /* 标题下载工具*/
  @import url('../../../../common/css/foldedPanel.css');
  /* 折叠面板*/
  @import url('../../../../common/css/fileList.css');
  /* 文件列表*/

  .taskR {
    .el-upload-list__item-name {
      margin: 0;
      display: inline-block;
      vertical-align: middle;
    }
    .btn-group{
      .el-form-item{
        margin-bottom:10px;

        .el-form-item__label{
          display: none;
        }
        .el-form-item__content{
          margin-left: 0px!important;
        }
      }
    }
  }
</style>

<style lang="scss" scoped>
  .taskR {
    height: calc(100% - 20px);
    display:flex;
    flex-direction:column;

    .given-container{
      flex-grow: 100;
      overflow: auto;
    }

    .btn-group {
      //按钮组
      width: 80%;
      bottom: 20px;
      margin-left: 10%;
      display: flex;
      flex-direction: column;
      flex-grow: 1;

      .el-button {
        width: 100%;

        &+.el-button {
          margin-left: 0;
          margin-top: 10px;
        }
      }
    }
  }
</style>
