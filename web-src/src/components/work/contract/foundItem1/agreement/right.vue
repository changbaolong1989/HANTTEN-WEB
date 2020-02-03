<template>
  <!-- 补充协议右侧-->
  <div class="right-default">
    <div class="given-container">
      <el-collapse v-model="activeNames" accordion style="margin: 0;">
        <fileList
          res="contractTemplate"
          title="补充协议"
          :showUploadBtn="false"
          :list="contractList"
          :userType="userType"
          v-if="rowInfoLevel == 3 && loadChild && contractList.length > 0"
          type="contract"
          :id="transmittedData.id"
        ></fileList>
        <fileList
          res="auditOpinion"
          title="审核意见文件"
          :showUploadBtn="false"
          :list="auditOpinionList"
          :userType="userType"
          v-if="rowInfoLevel == 3 && loadChild && typeFlag == 1 && auditOpinionList.length > 0"
        ></fileList>
        <!--审核意见-->
        <template v-for="(item, i) in opinionList">
          <collapse-review-read :object="item" name="item.taskKey" :key="i"></collapse-review-read>
        </template>
        <!--审核意见文件-->
        <fileList title="审核意见文件" :showUploadBtn="false" :delBtnFlag="false" :list="reviewFileRelationList" v-if="reviewFileRelationList.length > 0"></fileList>
        <!-- 面板-复核可写-->
        <collapse-review-write
          name="write1"
          :object="object"
          @submit="changeObject"
          v-if="typeFlag == 2 || typeFlag == 3 || typeFlag == 4 || typeFlag == 5"
          :errorTyleList="errorTyleList"
        ></collapse-review-write>
      </el-collapse>
      <!-- 提交审核弹窗组件 -->
      <dialog-submit-audit :above="above" @cancel="cancel" :reviewer="reviewer"></dialog-submit-audit>
    </div>

    <!-- 文件浏览 -->
    <!--<dialog-browse-file :file="file" ref="dialogBrowseFile"></dialog-browse-file>-->

    <preview-file :file="file" ref="previewFile"></preview-file>

    <div class="btn-group">
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;">
        <el-checkbox
          v-model="above"
          style="color: white;"
          v-if="
            (this.jumpFlag &&
              (typeFlag == 1 || typeFlag == 2 || typeFlag == 3 || typeFlag == 4) &&
              (this.currentTransmittedData.departmentId == 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91' ||
                this.currentTransmittedData.departmentId == 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91')) ||
              (this.jumpFlag && (typeFlag == 1 || typeFlag == 2) && this.currentTransmittedData.departmentId == 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3')
          "
        >
          跳级审核
        </el-checkbox>
      </p>
      <el-button type="primary" v-if="typeFlag == 2 || typeFlag == 3 || typeFlag == 4" @click="auditingHTContractTask('1')">通过</el-button>
      <el-button type="primary" v-if="typeFlag == 5" @click="auditingHTContractTask('1')">签发</el-button>
      <el-button type="primary" v-if="typeFlag == 20" @click="revokeHTContract">撤回</el-button>
      <el-button type="primary" v-if="typeFlag == 2 || typeFlag == 3 || typeFlag == 4 || typeFlag == 5" @click="auditingHTContractTask('0')">驳回</el-button>
      <el-button type="primary" v-if="typeFlag == 1" @click="present">提交审核</el-button>
      <el-button type="primary" v-if="typeFlag == 1" @click="preview">预览</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
// 面板--上传下载
import collapseUpAndDownload from '../../../common/components/collapseUpAndDownload.vue';
// 面板-复核只读
import collapseReviewRead from '../../../common/components/collapseReviewRead.vue';
// <!-- 面板-复核可写 -->
import collapseReviewWrite from '../../../common/components/collapseReviewWrite.vue';
// 提交审核弹窗
import dialogSubmitAudit from '../../../common/components/dialogSubmitAudit.vue';
// 浏览文件
import dialogBrowseFile from '../../../common/components/dialogBrowseFile.vue';

import previewFile from '../../../common/components/previewFile.vue';

export default {
  components: {
    fileList: collapseUpAndDownload,
    'collapse-review-read': collapseReviewRead,
    'dialog-submit-audit': dialogSubmitAudit,
    'collapse-review-write': collapseReviewWrite,
    // 浏览文件
    'dialog-browse-file': dialogBrowseFile,
    previewFile: previewFile
  },
  props: ['transmittedData', 'typeFlag'],
  data() {
    return {
      currentTransmittedData: this.transmittedData,
      object: {
        // 一级面板的对象
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
      form: {
        fileNumber: ''
      },
      reviewer: {
        // 审核人
        current: '', // 当前
        next: '' // 下一个
      },
      reviewFileRelationList: [],
      nextTaskKey: '', //流程下一节点
      contractList: [],
      delegateTaskKey: null,
      isCanRecall: null,
      verifyState: null,
      approveTime: null,
      taskNum: '', //流程节点序号
      user: {
        userId: '123',
        userName: '小红'
      },
      // 越级flag
      above: false,
      activeNames: 'write1',
      contract: false,
      rowInfoLevel: '1',
      rowInfoLabel: 'sdsdsd',
      userType: '',
      pageContent: '',
      opinionList: [],
      auditOpinionList: [],
      loadChild: true,
      submitAuditBtn: true,
      jumpFlag: true,
      contractId: '',
      legalOpinion: '',
      list: [], // 文件列表
      errorTyleList: [], // 错误类型列表
      // 浏览文件
      file: '',
      // 修改时间
      updateDate: ''
    };
  },
  watch: {
    transmittedData() {
      this.currentTransmittedData = this.transmittedData;
    }
  },

  mounted() {
    const vm = this;
    this.searchAuditOpinionInfo(this.transmittedData);
    //查询投标任务投标阶段文件信息
    vm.searchReviewFileRelationList(this.transmittedData.id);

    // 合同基本信息显示
    vm.bus.$on('click', val => {
      vm.searchAuditOpinionInfo(val);
      //查询投标任务投标阶段文件信息
      vm.searchReviewFileRelationList(this.transmittedData.id);
    });
  },
  updated() {
    //跳级显示flag
    this.jumpFlag = this.delegateTaskKey == 'QualityVerify' || this.delegateTaskKey == 'ManagerVerify' || (this.delegateTaskKey == 'SubmitVerify' && this.approveTime == null);
  },
  methods: {
    /**
     * 预览模板文件
     */
    preview() {
      this.bus.$emit('contractPreview', this._data);
    },
    /**
     * @method 浏览文件
     */
    browseFile(file) {
      let vm = this;
      if (!file.materialPath) {
        vm.stateDialog('error', '该文件暂时不能浏览');
        return;
      }
      vm.file = vm.tool.deepCopy(file, vm.tool.deepCopy);
      vm.$refs.previewFile.show = true;
    },
    httpRequest(request) {
      // 自定义上传请求
      let vm = this;
      let form = new FormData();
      form.append('file', request.file);
      form.append('contractId', vm.currentTransmittedData.id);
      vm.submitForm(form);
    },
    uploadFile() {
      // 上传文件点击事件
      let vm = this;
      vm.$refs['uploadDemo'].$children[0].$el.click();
    },
    searchAuditOpinionInfo(val) {
      let vm = this;
      vm.rowInfoLevel = val.treeNodeLevel;
      // 合同层级时 查询审核意见
      if (val.treeNodeLevel == 3) {
        vm.searchAuditOpinionListFunc(val.id);
        //查询问题类型列表信息
        vm.searchQuestionList(val.departmentId);
        /*查询合同信息*/
        vm.searchContractInfo(val.id);
        /*查询合同归档文件信息*/
        vm.searchContractArchiveFileInfo(val.id);
      }
    },
    // 提交上传文件
    submitForm(obj) {
      let vm = this;

      // 提交文件请求
      this.apiAddress('/contract/add/insertContractArchiveFileInfo', obj)
        .then(res => {
          if (res.data.status == 'success') {
            this.stateDialog('success', res.data.message);
            // 查询归档文件列表
            this.searchContractArchiveFileInfo(vm.currentTransmittedData.id);
          } else {
            this.stateDialog('error', res.data.message);
          }
        })
        .catch(err => {
          this.stateDialog('success', res.data.message);
        });
    },
    // 删除文件信息
    deleteFileInfo(val) {
      let vm = this;
      this.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      })
        .then(() => {
          this.apiAddress('/contract/delete/deleteContractArchiveFileInfo', {
            relationId: val.materialId,
            materialPath: val.materialPath
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                // 查询归档文件列表
                this.searchContractArchiveFileInfo(vm.currentTransmittedData.id);
              } else {
                this.stateDialog('error', res.data.message);
              }
            })
            .catch(e => {
              this.stateDialog('error', e);
            });
        })
        .catch(() => {});
    },
    submitArchiveButton() {
      let vm = this;
      vm.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      })
        .then(() => {
          if (null == vm.list || vm.list.length == 0) {
            vm.$message({
              message: '请上传归档附件',
              type: 'error',
              center: true
            });
            return;
          }
          let formData = {
            contractId: vm.currentTransmittedData.id,
            fileNumber: vm.form.fileNumber,
            updateDate: vm.updateDate
          };
          // 走提交归档接口
          vm.apiAddress('/contract/modify/submitArchiveHTContract', formData)
            .then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
                vm.$router.push('/page/work/default');
                // vm.bus.$emit('asideRefresh',{checkedKey:vm.currentTransmittedData.id});
              }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
        })
        .catch(() => {});
    },
    searchContractArchiveFileInfo(contractId) {
      let vm = this;
      let formData = {
        contractId: contractId
      };
      vm.apiAddress('/contract/query/searchContractArchiveFileList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 获取文件list
            let dataList = res.data.dataList;
            // 转换格式，替换relationId为materialId
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, 'materialId'));
            }
            // 设置转换之后的list
            this.list = dataList;
            vm.toggleTab();
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      this.centerDialogVisible = true;
    },
    /*查询合同信息*/
    searchContractInfo(contractId) {
      let vm = this;
      let formData = {
        contractId: contractId
      };
      vm.apiAddress('/contract/query/searchContractInfoById', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let tempContractInfo = res.data.dataVO;
            let userList = tempContractInfo.userList;
            for (let i = userList.length - 1; i >= 0; i--) {
              userList[i].errorList = [];
            }
            vm.object.title = vm.getStepName(tempContractInfo.delegateTaskKey);
            vm.nextTaskKey = tempContractInfo.submitNode;
            vm.delegateTaskKey = tempContractInfo.delegateTaskKey;
            vm.legalOpinion = tempContractInfo.legalOpinion; //法务意见
            vm.verifyState = tempContractInfo.verifyState;
            vm.approveTime = tempContractInfo.approveTime;
            vm.isCanRecall = tempContractInfo.isCanRecall;
            // 修改时间
            vm.updateDate = tempContractInfo.updateDate;
            vm.tasknum = vm.getStepNum(tempContractInfo.delegateTaskKey);
            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);

            //当合同业务线为项目监理 并且 当前审核节点为"部门经理"审核，则跳级到的下一个节点为“签发人”
            if (this.currentTransmittedData.departmentId == 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3' && tempContractInfo.delegateTaskKey == 'ManagerVerify') {
              //TODO
              vm.reviewer.next = '签发人';
            } else {
              vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));
            }

            for (let i = userList.length - 1; i >= 0; i--) {
              userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, 'id'));
              userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, 'name'));
            }
            vm.contractId = tempContractInfo.contractId;
            vm.object.children = userList;

            // 合同路径
            let contractFilePath = tempContractInfo.contractFilePath;

            // 合同类型
            let contractType = tempContractInfo.contractType;

            // 是否调用合同模板生成接口 默认走正常的下载接口
            let isGeneratedFile = false;

            // 如果合同类型为模板合同
            if (contractType == '1') {
              // 合同路径 后面拼接合同id和项目编号
              contractFilePath = contractFilePath + '?contractId=' + tempContractInfo.contractId + '&projectNum=' + tempContractInfo.projectNum;

              // 调用同模板生成接口 生成合同模板
              isGeneratedFile = true;
            }

            if (tempContractInfo.contractFileName != null && tempContractInfo.contractFileName != '') {
              vm.contractList[0] = {
                materialName: tempContractInfo.contractFileName,
                materialPath: contractFilePath,
                isGeneratedFile: isGeneratedFile
              };
            }

            if (tempContractInfo.reviewFileName != null && tempContractInfo.reviewFileName != '') {
              vm.auditOpinionList[0] = {
                // "materialName": tempContractInfo.reviewFileName,
                // "materialPath": tempContractInfo.reviewFilePath
              };
            }

            vm.toggleTab();
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      this.centerDialogVisible = true;
    },
    changeObject(obj) {
      this.object = obj;
    },
    searchAuditOpinionListFunc(contractId) {
      let vm = this;
      let formData = {
        contractTaskId: contractId
      };
      //查询审核意见列表
      vm.apiAddress('/contract/query/searchHTContractTaskHisTaskInst', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            /*------------------------------格式转换START---------------------------------*/
            let dataList = res.data.dataList;
            //for循环遍历更改属性名
            for (var i = 0; i < dataList.length; i++) {
              dataList[i].title = vm.getStepName(dataList[i].taskKey);
              //将属性名httenderTaskHisTaskInstVOList转换成属性名children
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htcontractTaskHisTaskInstVOList/g, 'children'));
              let children = dataList[i].children;
              for (var j = 0; j < children.length; j++) {
                //将属性名approveTime转换成属性名time
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, 'time'));
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, 'name'));
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g, 'persions'));
                children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, 'opinion'));
                let persions = vm.groupBy(children[j].persions, function(item) {
                  return [item.userId];
                });
                for (var k = 0; k < persions.length; k++) {
                  persions[k].name = persions[k].dataList[0].userName;
                  persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, 'errorList'));
                  let errorList = persions[k].errorList;
                  for (var l = errorList.length - 1; l >= 0; l--) {
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, 'type'));
                    errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, 'num'));
                  }
                  persions[k].errorList = errorList;
                }
                children[j].persions = persions;
              }
            }
            /*---------------------------------格式转换END-------------------------------------*/
            vm.opinionList = dataList;
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    toggleTab() {
      this.loadChild = false;
      this.$nextTick(() => {
        this.loadChild = true;
      });
    },
    //审核
    auditingHTContractTask(isPass) {
      let vm = this;
      vm.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      })
        .then(() => {
          let paramObj = vm.object;
          paramObj.isPass = isPass;
          paramObj.updateDate = vm.updateDate;
          let nextTaskKey = vm.nextTaskKey;

          if (vm.above) {
            if (vm.currentTransmittedData.departmentId == 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3' && vm.delegateTaskKey == 'ManagerVerify') {
              //TODO
              nextTaskKey = 'Signer';
            } else {
              //TODO
              nextTaskKey = vm.getNextStepNode(nextTaskKey);
            }
          }

          paramObj.onlyOfficeEditKey = vm.transmittedData.taskMaterialInfo.key;
          paramObj.reviewFileName = vm.transmittedData.taskMaterialInfo.fileName;
          paramObj.reviewFilePath = vm.transmittedData.taskMaterialInfo.saveUrl;

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
          paramObj.contractId = vm.contractId;
          vm.apiAddress('/contract/audit/auditingHTContract', paramObj)
            .then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
                vm.$router.push('/page/work/default');
                // vm.bus.$emit('asideRefresh',{checkedKey:vm.currentTransmittedData.id});
              }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
        })
        .catch(() => {});
    },
    cancel() {
      // 跳级取消
      let vm = this;
      vm.above = false;
    },
    //查询问题类型列表信息
    searchQuestionList(departmentId) {
      let vm = this;
      let formData = {
        departmentId: departmentId
      };
      vm.apiAddress('/tenderTask/query/searchQuestionList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let questionList = res.data.dataList;
            for (var i = questionList.length - 1; i >= 0; i--) {
              questionList[i].questionNum = 0;
            }
            vm.errorTyleList = questionList;
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    baseContract(val) {
      if (val.treeNodeLevel == 3) {
        this.contract = true;
      } else {
        this.contract = false;
      }
    },

    checkFile(n) {
      let vm = this;
      vm.bus.$emit('checkFile', n);
    },
    present() {
      let vm = this;
      vm.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      })
        .then(() => {
          vm.bus.$emit('changeform', vm._data);
        })
        .catch(() => {});
    },
    revokeHTContract() {
      let vm = this;
      let formData = {
        contractId: vm.contractId
      };

      vm.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      })
        .then(() => {
          vm.apiAddress('/contract/revoke/revokeHTContract', formData)
            .then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
                vm.bus.$emit('asideRefresh', { checkedKey: vm.currentTransmittedData.id });
              }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
        })
        .catch(() => {});
    },
    download(fileUrl, fileName, isGeneratedFile) {
      // 下载文件
      this.downloadFile(fileUrl, fileName, isGeneratedFile);
    },

    /*查询审核意见文件列表*/
    searchReviewFileRelationList(contractId) {
      let vm = this;
      let formData = {
        businessId: contractId
      };
      vm.apiAddress('/commonModule/query/searchReviewFileRelationList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileId/g, 'materialId'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFileName/g, 'materialName'));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFilePath/g, 'materialPath'));
            }
            vm.reviewFileRelationList = dataList;
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    }
  }
};
</script>
<style lang="scss">
@import url('../../../common/css/titleDownLoadTool.css');
/* 标题下载工具*/
@import url('../../../common/css/foldedPanel.css');
/* 折叠面板*/
@import url('../../../common/css/fileList.css');
/* 文件列表*/
.right-default {
}
</style>
<style lang="scss" scoped>
.right-default {
  height: calc(100% - 20px);
  display: flex;
  flex-direction: column;

  .given-container {
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

      & + .el-button {
        margin-left: 0;
        margin-top: 10px;
      }
    }
  }
}
</style>
