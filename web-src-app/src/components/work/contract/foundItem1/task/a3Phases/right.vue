<template>
  <!-- A3阶段右边栏-->
  <div class="right more-sublevel">
    <el-collapse style="border-radius:5px;">
      <!--文件上传下载 -->
      <collapseUpAndDownload
        :title="UpAndDownloadTitle"
        :showUploadBtn="showUploadBtnFlg"
        :delBtnFlag="delBtnFlag"
        @uploadFile="uploadFile"
        @del="deleteFileInfo"
        :multiple="true"
        :files="files"
        :type="type"
        :id="currentTransmitData.taskId"
      ></collapseUpAndDownload>
      <!-- 上传文件 -->
      <dialogUpload
        ref="dialogUpload"
        :options="options"
        unique="fileTypeId"
        display="fileTypeName"
        :uploadOptions="uploadOptions"
        :files="files"
        @submit="uploadSubmit"
        :periodTypeBoolean="currentTransmitData.periodType != '1'"
        :periodType="currentTransmitData.periodType"
      ></dialogUpload>
      <!-- 审核意见文件-->
      <collapseUpAndDownload
        title="审核意见文件"
        :multiple="true"
        :files="auditFileList"
        v-if="isMainHeadFlag == '1' && auditFileList.length > 0 && (typeFlag == 1 || typeFlag == 15 || typeFlag == 0)"
      ></collapseUpAndDownload>
      <div class="primary" v-if="typeFlag != '20'">
        <!-- 只读-->
        <template v-for="(item, i) in opinionList">
          <collapseReviewRead :object="item"></collapseReviewRead>
        </template>
      </div>
      <div class="primary" v-if="typeFlag === '2' || typeFlag == '3' || typeFlag == '4' || typeFlag === '5'">
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
      <div class="primary" v-if="verifyState == ''">
        <mt-switch v-model="above" @change="skipGrade" v-if="(typeFlag === '1' && approveTime === '') || (typeFlag === '2' || typeFlag === '3')">是否跳级审核</mt-switch>
        <button class="linear" @click.prevent="submitAudit" v-if="typeFlag == 1">提交审核</button>
        <button class="linear" v-if="typeFlag == 20" @click="recallTask">撤回</button>
        <div v-if="typeFlag == '2' || typeFlag == '3' || typeFlag == '4' || typeFlag == '5'">
          <button class="linear" @click="auditingHTTask('1')">{{ typeFlag == '5' ? '签发' : '通过' }}</button>
          <button class="linear" @click="auditingHTTask('0')">驳回</button>
        </div>
      </div>
    </el-collapse>
  </div>
</template>

<script>
import collapseUpAndDownload from '../../../../../more/common/components/collapseUpAndDownload.vue'; /* 文件上传下载 */
import dialogUpload from '../../../../../more/common/components/dialogUpload.vue'; /* 文件选择弹窗 */
import collapseReviewWrite from '../../../../../more/common/components/collapseReviewWrite.vue'; /* 审核意见可写*/
import collapseReviewRead from '../../../../../more/common/components/collapseReviewRead.vue'; /* 审核意见只读 */
import collapseReviewWrite_dialog from '../../../../../more/common/components/collapseReviewWrite_dialog.vue'; /* 可写组件 - 对话框*/
export default {
  data() {
    return {
      UpAndDownloadTitle: this.currentTransmitData.label /* 上传文件的title */,
      // 上传按钮是否显示标识
      showUploadBtnFlg: false,
      delBtnFlag: false, // 是否显示删除按钮
      files: [], // 上传文件列表
      type: 'task', // 任务标识
      options: [] /* 文件上传单选数据 */,
      uploadOptions: {
        isInvoice: false, // 是否是发票
        limit: 1,
        radioFlag: this.periodType == '1' ? false : true
      },
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
      isMainHeadFlag: false,
      verifyState:null
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
      /* 显示按钮的相关信息 */
      handler(newval) {
        let vm = this;
        vm.jurisdiction = newval;
        this.init();
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
    dialogUpload,
    collapseReviewWrite,
    collapseReviewRead,
    'collapse-review-write-dialog': collapseReviewWrite_dialog
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // 是否是主提审人
      this.isMainHeadFlag = this.currentTransmitData.isMainHeadFlag;

      // 重新编辑申请
      this.submitReedit = this.jurisdiction.isSubmitReedit;
      // 当前状态
      this.currentState = this.jurisdiction.currentState;
      // 部门id
      this.departmentId = this.currentTransmitData.departmentId;
      this.verifyState  = this.jurisdiction.verifyState;
      // ==============================各个阶段通用 start=================================

      this.showUploadBtnFlg = (this.typeFlag === "1" || (this.typeFlag === "15" && this.currentState !== "6"))&&this.verifyState=='';// 是否显示上传按钮  true:显示  false:不显示
      this.delBtnFlag = !(this.showUploadBtnFlg);// 是否显示删除按钮  true:不显示  false:显示

      // ==============================各个阶段通用 end=================================
      this.initA3(); /* A3初始化方法*/
      this.A3File(this.currentTransmitData.relationId,this.typeFlag); /* A3请求下载文件列表*/
      this.searchHTTaskHisTaskInst(this.currentTransmitData.relationId); //查询审核意见列表
    },
    /* A3初始化方法*/
    initA3() {
      let vm = this;
      let formData = {
        relationId: this.currentTransmitData.relationId
      };
      vm.requestPost('/task/query/searchTaskPeriodDetailInfoByRelationId', formData)
        .then(res => {
          if (res.data.status == 'success') {
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
            // vm.verifyState = this.taskPeriodInfo.verifyState;
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
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    A3File(relationId,typeFlag) {
      let vm = this;
      /* A3请求下载文件列表*/
      this.requestPost('/task/query/searchTaskPeriodMaterialList', {
        relationId: this.currentTransmitData.relationId,
      })
        .then(res => {
          if (res.data.status == 'success') {
            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));

              // 如果是项目监理部
              if (vm.departmentId === vm.superviseDeptId) {
                // 如果是主提审人或者是普通执行人并且已归档，则不显示删除按钮
                if ((typeFlag === '1' || typeFlag === '15') && dataList[i].isReview === '1') {
                  dataList[i].isCanDel = '0';
                }

                // 如果不是项目监理部
              } else {
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

                // 如果是收集资料阶段，在归档时机显示上传按钮
                if (vm.periodType === '1' && vm.typeFlag === '6' && vm.isSubmitReedit !== '1') {
                  dataList[i].isCanDel = '1';
                }
              }
            }
            // 设置转换之后的list
            vm.files = dataList;
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(e => {
         this.Toast(e);
        });
    },
    // 查询上传素材类型下拉列表
    searchMaterialTypeList() {
      let formData = {
        departmentId: this.departmentId,
        counselTypeId: this.counselTypeId
      };
      // 查询上传素材类型下拉列表
      this.apiAddress('/tenderTask/query/searchMaterialTypeList', formData)
        .then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == 'success') {
            // 获取文件类型list
            let dataList = res.data.dataList;
            // 转换格式
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeId/g, 'id'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeName/g, 'label'));
            }
            // 设置转换之后的list
            this.fileTypeList = dataList;
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
        departmentId: this.currentTransmitData.departmentId,
        counselTypeId: this.currentTransmitData.counselTypeId
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
    deleteFileInfo(val) {
      /* 删除文件 */
      let vm = this;
      let formData = {
        relationId: val.relationId,
        materialPath: val.materialPath,
        taskId: vm.currentTransmitData.taskId
      };
      this.requestPost('/task/delete/deleteTaskPeriodMaterialInfo', formData)
        .then(res => {
          if (res.data.status == 'success') {
            this.A3File();
          } else {
            this.Toast(res.data.message);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    uploadSubmit(data) {
      /* 上传文件接口 */
      /* 上传文件数据*/
      var formdata = new FormData();
      formdata.append('file', data.form.get('file'));
      formdata.append('taskPeriodId', this.currentTransmitData.relationId);
      formdata.append('fileType', data.fileTypeId);
      formdata.append('periodType', this.currentTransmitData.periodType);

      this.requestPost('/task/add/insertTaskPeriodMaterialInfo', formdata)
        .then(res => {
          if (res.data.status == 'success') {
            this.A3File();
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    submitAudit(type) {
      /* 提交审核 */
      this.MessageBox.confirm('', {
        message: '确定执行提交审核操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          if (this.files.length > 0) {
            // 下一流程节点
            let nextTaskKey = this.taskPeriodInfo.submitNode;
            if (this.above) {
              // 获取下一流程节点
              nextTaskKey = this.getNextStepNode(nextTaskKey);
            }
            this.requestPost('/task/modify/submitAuditA3Period', {
              // 任务阶段关联id
              relationId: this.currentTransmitData.relationId,
              // 下一流程节点
              submitNode: nextTaskKey,
              // 阶段名称
              periodName: this.currentTransmitData.label
            })
              .then(res => {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (res.data.status == 'success') {
                  // 跳到工作台
                  this.$router.push('/app/workbench');
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
        })
        .catch(err => {});
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
    recallTask() {
      /* 撤回审核 */
      this.MessageBox.confirm('', {
        message: '确定执行撤回操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/modify/recallTaskA3Period', {
            // 任务阶段关联id
            relationId: this.currentTransmitData.relationId,
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
      this.MessageBox.confirm('', {
        message: '确定执行当前操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/add/auditingTaskA3Period', paramObj)
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
    }
  }
};
</script>

<style></style>
