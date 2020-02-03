<template>
  <!-- 成果文件右边栏-->
  <div class="right more-sublevel">
    <el-collapse style="border-radius:5px;">
      <!-- 普通阶段文件上传下载删除-->
      <collapseUpAndDownload
        :title="UpAndDownloadTitle"
        v-if="currentTransmitData.treeNodeLevel == '6' && files.length > 0"
        :showUploadBtn="false"
        :delBtnFlag="typeFlag === '1'"
        @del="deleteFileInfo"
        :multiple="true"
        :files="files"
        :type="type"
        :id="currentTransmitData.taskId"
      ></collapseUpAndDownload>
      <!--归档文件-->
      <collapseUpAndDownload
        title="归档文件"
        v-if="
          ((typeFlag === '0' || ((typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit === '1')) && Archivefiles.length > 0) ||
            typeFlag === '6' ||
            ((typeFlag === '7' || typeFlag === '8') && Archivefiles.length > 0)
        "
        :showUploadBtn="typeFlag === '6'"
        :delBtnFlag="typeFlag === '6'"
        @uploadFile="uploadArchiveFile"
        @del="deleteArchiveFileInfo"
        :files="Archivefiles"
      ></collapseUpAndDownload>
      <input type="file" id="Archivefile" @change="ArchivefileChange($event)" style="display: none;" />
      <collapseUpAndDownload title="相关资料" v-if="reviewFileList.length > 0" :multiple="true" :files="reviewFileList"></collapseUpAndDownload>
      <collapseUpAndDownload
        title="审核意见文件"
        v-if="currentTransmitData.isMainHeadFlag == '1' && auditFileList.length > 0 && (typeFlag == 1 || typeFlag == 15 || typeFlag == 0)"
        :multiple="true"
        :files="auditFileList"
      ></collapseUpAndDownload>
      <!-- 审核记录 -->
      <div class="primary" v-if="typeFlag != '20'">
        <!-- 只读-->
        <template v-for="(item, i) in opinionList">
          <collapseReviewRead :object="item"></collapseReviewRead>
        </template>
      </div>
      <div class="primary" v-if="(typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit !== '1'">
        <!-- 可写-->
        <collapseReviewWrite ref="write" :object="object" @selecError="selecError"></collapseReviewWrite>
      </div>
      <!-- 可写组件 - 对话框 -->
      <collapse-review-write-dialog
        ref="collapseReviewWrite_dialog"
        :errorTyleList="errorTyleList"
        :obj="selectErrorObj"
        @submit="selectErrorSubmit"
      ></collapse-review-write-dialog>

      <!-- 重新编辑审核历史记录 -->
      <div
        class="primary reedit"
        v-for="(obj, j) in reeditApplicationList"
        v-if="
          reeditApplicationList.length &&
            (((typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit === '1') ||
              (typeFlag === '6' && isSubmitReedit !== '1') ||
              (typeFlag === '0' || typeFlag === '15' || typeFlag === '1' || typeFlag === '7' || typeFlag === '8'))
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
          v-if="(typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit === '1' && obj.isShowWrite"
          @selecError="selecError"
        ></collapseReviewWriteAudit>
      </div>

      <div class="primary">
        <mt-switch v-model="above" @change="skipGrade" v-if="(typeFlag === '1' && approveTime == null) || ((typeFlag === '2' || typeFlag === '3') && isSubmitReedit !== '1')">
          是否跳级审核
        </mt-switch>
        <button class="linear" @click.prevent="submitAudit(false)" v-if="typeFlag == 1">提交审核</button>
        <!-- <button class="linear" @click.prevent="submitAudit(true)" v-if="(typeFlag === '1' && approveTime == null) || ((typeFlag === '2' || typeFlag === '3') && isSubmitReedit !== '1')">保存</button> -->
        <button class="linear" @click.prevent="submitAudit('BrowseFile')" v-if="typeFlag == 1">预览</button>
        <button class="linear" v-if="typeFlag == 20" @click="recallTask">撤回</button>
        <div v-if="(typeFlag == '2' || typeFlag == '3' || typeFlag == '4' || typeFlag == '5' || typeFlag == '7') && isSubmitReedit !== '1'">
          <button class="linear" @click="auditingHTTask('1')">{{ typeFlag == '5' ? '签发' : '通过' }}</button>
          <button class="linear" @click="auditingHTTask('0')">驳回</button>
        </div>
        <!-- 成果文件归档阶段 -->
        <button class="linear" v-if="typeFlag == 6" @click="submitArchive">提交归档</button>
        <div v-if="typeFlag == 8">
          <input type="text" class="fileNumber" placeholder="请输入档案编号" v-model="fileNumber" />
          <button class="linear" @click="confirmArchive">确认归档</button>
        </div>
        <!-- 重新编辑申请 -->
        <button class="linear" v-if="typeFlag == 6" @click="reeditApplication">重新编辑申请</button>
        <button
          class="linear"
          v-if="(typeFlag === '2' || typeFlag === '3' || typeFlag === '4') && isSubmitReedit === '1'"
          @click="auditingOutcomeDocumentPeriodReeditApplication('1')"
        >
          通过
        </button>
        <button class="linear" v-if="typeFlag === '5' && isSubmitReedit === '1'" @click="reeditApplicationIssue('1')">签发</button>
        <button
          class="linear"
          v-if="(typeFlag === '2' || typeFlag === '3' || typeFlag === '4' || typeFlag === '5') && isSubmitReedit === '1'"
          @click="auditingOutcomeDocumentPeriodReeditApplication('0')"
        >
          驳回
        </button>
      </div>
    </el-collapse>
    <!-- 提交审核-对话框 -->
    <submit-audit ref="submitAudit" @confirmSubmit="confirmSubmit"></submit-audit>
    <previewFile ref="dialogBrowseFile" :file="file" :type="type" :formData="formData"></previewFile>
    <!-- 预览 -->
  </div>
</template>

<script>
import collapseUpAndDownload from '../../../../../more/common/components/collapseUpAndDownload.vue'; /* 文件上传下载 */
import collapseReviewWrite from '../../../../../more/common/components/collapseReviewWrite.vue'; /* 审核意见可写*/
import collapseReviewRead from '../../../../../more/common/components/collapseReviewRead.vue'; /* 审核意见只读 */
import collapseReviewWriteAudit from '../../../../../more/common/components/collapseReviewWriteAudit.vue'; /* 重新编辑可写*/
import collapseReviewReadAudit from '../../../../../more/common/components/collapseReviewReadAudit.vue'; /* 重新编辑只读 */
import collapseReviewWrite_dialog from '../../../../../more/common/components/collapseReviewWrite_dialog.vue'; /* 可写组件 - 对话框*/
import submitAudit from '../../../../../more/common/components/submitAudit.vue'; /* 提交审核-对话框 */
import previewFile from '../../../../../more/common/components/previewFile.vue'; /* 预览 */
export default {
  data() {
    const formData = new FormData();
    return {
      formData: formData,
      UpAndDownloadTitle: '编制成果文件' /* 上传文件的title */,
      // 上传按钮是否显示标识
      showUploadBtnFlg: false,
      // 是否显示删除按钮
      delBtnFlag: false,
      files: [], // 上传文件列表
      type: 'task', // 任务标识
      /* 是否跳级*/
      above: false,
      object: {
        /* 审核意见模板 */
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
      opinionList: [] /* 文件列表数据 */,
      selectErrorObj: {}, // 选择错误的对象,
      approveTime: null, // 审批时间
      // 审核人
      reviewer: {
        // 当前
        current: '',
        // 下一个
        next: ''
      },
      Archivefiles: [], // 归档文件列表
      // 相关资料文件列表
      reviewFileList: [],
      // 重新申请列表
      reeditApplicationList: [],
      fileNumber: '', // 档案编号
      file: '', // 待浏览文件
      isSubmitReedit: '', // 是否提交重新编辑申请
      archiveList: [] // 上传归档文件数组
    };
  },
  props: ['currentTransmitData', 'postil', 'jurisdiction', 'typeFlag', 'errorTyleList', 'auditFileList'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.currentTransmitData = newval;
        this.init();
      },
      immeditate: true
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
  components: {
    collapseUpAndDownload,
    collapseReviewWrite,
    collapseReviewRead,
    'collapse-review-write-dialog': collapseReviewWrite_dialog,
    'submit-audit': submitAudit,
    previewFile,
    collapseReviewWriteAudit,
    collapseReviewReadAudit
  },
  created() {
    this.init();
  },
  mounted() {
    const vm = this;
    vm.bus.$on('echofile', function(val) {
      vm.submitFile(val);
    });
  },
  methods: {
    init() {
      this.achievement(this.currentTransmitData.relationId); /* 成果文件信息*/
      this.searchOutcomeDocumentMaterial(this.currentTransmitData.taskId); /* 查询素材列表 */
      this.searchTaskArchiveFileInfo(this.currentTransmitData.relationId); /* 查询任务归档文件列表信息 */
      this.searchIsReviewTaskPeriodMaterialList(this.currentTransmitData.relationId, this.currentTransmitData.taskId); /* 查询相关资料文件列表 */
      this.searchHTTaskHisTaskInst(this.currentTransmitData.relationId); /* 查询审核意见列表 */
      this.searchTaskReeditHisTaskInst(this.currentTransmitData.taskId, this.currentTransmitData.relationId); // 查询重新编辑审核列表
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
          } else {
            this.Toast(res.data.message);
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
            // 人员名称
            let userName = tempTaskInfo.userName;

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
                isGeneratedFile: isGeneratedFile,
                userName: userName
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
    },
    // 查询相关资料文件列表
    searchIsReviewTaskPeriodMaterialList(relationId, taskId) {
      this.requestPost('/task/query/searchIsReviewTaskPeriodMaterialList', {
        relationId: relationId,
        taskId: taskId
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
      this.formData = new FormData();
      // 设置多图片传参数
      for (var i = 0; i < this.archiveList.length; i++) {
        this.formData.append('resourceFiles', this.archiveList[i]);
      }
      this.formData.append('relationId', this.currentTransmitData.relationId);
      // 任务id
      this.formData.append('taskId', this.currentTransmitData.taskId);
      // 提交归档请求
      this.MessageBox.confirm('', {
        message: '确定执行提交归档操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/modify/submitArchive', this.formData)
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                this.Toast(res.data.message);
                // 刷新
                this.$router.push('/app/workbench');
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(e => {
              console.log(e);
            });
        })
        .catch(err => {});
    },
    // 确认归档
    confirmArchive() {
      // 确认归档请求
      if (this.fileNumber != '') {
        this.MessageBox.confirm('', {
          message: '确定执行确认归档操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.requestPost('/task/modify/confirmArchive', {
              // 任务阶段关联id
              relationId: this.currentTransmitData.relationId,
              // 档案编号
              fileNumber: this.fileNumber,
              // 修改时间
              updateDate: this.updateDate
            })
              .then(res => {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (res.data.status == 'success') {
                  this.Toast(res.data.message);
                  // 刷新
                  this.$router.push('/app/workbench');
                } else {
                  this.Toast(res.data.message);
                }
              })
              .catch(e => {
                console.log(e);
              });
          })
          .catch(err => {});
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
      let elFile = document.getElementById('Archivefile');
      elFile.value = '';
      elFile.click();
      return false;
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
          taskId: this.currentTransmitData.taskId
        })
          .then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == 'success') {
              this.Toast(res.data.message);
              // 查询归档文件列表
              this.searchTaskArchiveFileInfo(this.currentTransmitData.relationId);
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
      this.MessageBox.confirm('', {
        message: '确定执行重新编辑申请操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          // 重新编辑申请请求
          this.requestPost('/task/modify/submitReedit', {
            // 任务阶段关联id
            relationId: this.currentTransmitData.relationId
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                this.Toast(res.data.message);
                // 刷新
                this.$router.push('/app/workbench');
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(e => {
              this.Toast(e);
            });
        })
        .catch(err => {});
    },
    // ==============================================================================成果文件 重新编辑的通过和驳回 start==========
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
      paramObj.relationId = this.currentTransmitData.relationId;
      // 修改时间
      paramObj.updateDate = this.taskPeriodInfo.updateDate;
      let vm = this;
      this.MessageBox.confirm('', {
        message: '确定执行当前操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/add/auditingOutcomeDocumentPeriodReeditApplication', paramObj)
            .then(res => {
              if (res.data.status == 'failure') {
                this.Toast(res.data.message);
              } else {
                this.Toast(res.data.message);
                // 跳转到工作台
                this.$router.push('/app/workbench');
              }
            })
            .catch(err => {
              this.Toast(err);
            });
        })
        .catch(err => {});
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
      paramObj.relationId = this.currentTransmitData.relationId;
      // 修改时间
      paramObj.updateDate = this.taskPeriodInfo.updateDate;
      let vm = this;
      this.MessageBox.confirm('', {
        message: '确定执行签发操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/add/reeditApplicationIssue', paramObj)
            .then(res => {
              if (res.data.status == 'failure') {
                this.Toast(res.data.message);
              } else {
                this.Toast(res.data.message);
                // 跳转到工作台
                this.$router.push('/app/workbench');
              }
            })
            .catch(err => {
              this.Toast(err);
            });
        })
        .catch(err => {});
    },
    // ==============================================================================成果文件 重新编辑的通过和驳回 end=========
    submitAudit(type) {
      /* 提交审核 */
      if (type != 'BrowseFile') {
        this.MessageBox.confirm('', {
          message: '确定执行提交审核操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.bus.$emit('MaterialList', type);
          })
          .catch(err => {});
      } else {
        this.bus.$emit('MaterialList', type);
      }
      /* 先验证成果文件*/
    },
    /* 验证通过之后,展示可以提交审核的文件列表,如果没有文件，直接上传*/
    submitFile(val) {
      /* 提交审核 */
      this.echofileData = val;
      let vm = this;
      if (val.type == 'BrowseFile') {
        this.$refs.submitAudit.submit();
      } else {
        //查询以上传的所有文件(非审批文件)
        vm.requestPost('/task/query/searchAllMaterialTypeAndMaterialList', {
          relationId: this.currentTransmitData.relationId
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
      }
    },
    confirmSubmit(val) {
      const vm = this;
      /* 确认提交审核 */
      // 下一流程节点
      let nextTaskKey = this.taskPeriodInfo.submitNode;
      var type = this.echofileData.type;
      if (this.above) {
        // 获取下一流程节点
        nextTaskKey = this.getNextStepNode(nextTaskKey);
      }
      if(type!="BrowseFile"){
        // 提交审核请求
        let sendData = { 
          // 任务阶段关联id
          relationId: this.currentTransmitData.relationId,
          // 文件id
          materialIds: val,
          // 下一流程节点
          submitNode: nextTaskKey
        };
      }
      var templateForm = this.echofileData.submit;
      var formData = new FormData();
      formData.append('relationId', this.currentTransmitData.relationId); // 任务阶段关联id
      formData.append('materialIds', val); // 其他文件类型id
      formData.append('submitNode', nextTaskKey); // 下一流程节点
      formData.append('taskId', this.currentTransmitData.taskId); // 任务id
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
            // 合同编号
            formData.append('contractNum', formworkContent.contractNum);
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
              // 跳转到工作台
              this.$router.push('/app/workbench');
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
    },
    /* 可写组件--相关 */
    selecError(data) {
      // 选择错误
      // 先赋值再显示对话框
      this.selectErrorObj = data;
      this.$refs['collapseReviewWrite_dialog'].show = true;
    },
    selectErrorSubmit(data) {
      /* 编辑审核意见组件确定 */
      this.selectErrorObj.errorList = data;
    },
    recallTask() {
      /* 撤回审核 */
      this.MessageBox.confirm('', {
        message: '确定执行撤回操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/modify/recallTaskOutcomeDocumentPeriod', {
            // 任务阶段关联id
            relationId: this.currentTransmitData.relationId
          })
            .then(res => {
              if (res.data.status == 'success') {
                // 刷新
                this.$router.go(0);
                this.init();
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(err => {
              console.log(err);
            });
        })
        .catch(err => {});
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
      paramObj.relationId = this.currentTransmitData.relationId;
      // 修改时间
      paramObj.updateDate = this.taskPeriodInfo.updateDate;
      let vm = this;
      this.MessageBox.confirm('', {
        message: '确定执行当前操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/add/auditingTaskOutcomeDocumentPeriod', paramObj)
            .then(res => {
              if (res.data.status == 'success') {
                // 跳转到工作台
                this.$router.push('/app/workbench');
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(err => {
              console.log(err);
            });
        })
        .catch(err => {});
    },
    deleteFileInfo(val) {
      /* 删除文件 */
      let vm = this;
      let formData = {
        relationId: val.relationId,
        materialPath: val.materialPath,
        taskId: vm.currentTransmitData.taskId
      };
      this.requestPost('/task/delete/excDeleteResultFileMaterialInfo', formData)
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
    },
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
    }
    // ==============================================================================成果文件阶段 查询重新编辑审核列表 end==========
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
