<template>

  <!-- 任务right -->
  <div class="taskR">
    <div class="given-container">
    <!--折叠面板-->
    <el-collapse v-model="activeNames" accordion>

      <!-- 文件上传-->
      <fileList :title="currentTransmittedData.label" :showUploadBtn="showUploadBtnFlg" :options="fileTypeList"
                @del="deleteFileInfo" :list="fileList"
                @submit="submitForm"
                v-if='currentTransmittedData.treeNodeLevel === "6" && periodType !== "5" && periodType !== "3"'
                :delBtnFlag="delBtnFlag" :type="type" :id="taskId"
                :periodTypeBoolean='currentTransmittedData.periodType !== "1"' :periodType="currentTransmittedData.periodType"></fileList>

      <!-- 面板-复核只读-审核记录 -->
      <template v-for="(item,i) in opinionList"
                v-if='(periodType === "2" || periodType === "3") && typeFlag !== "20"'>
        <collapse-review-read :object="item" name="item.taskKey" :key="i"></collapse-review-read>
      </template>

      <!-- 面板-复核可写-A3和成果文件 -->
      <collapse-review-write :object="object" name="white1" @submit="changeObject" :noPenalty="false"
                             :errorTyleList="errorTyleList"
                             v-if='(typeFlag === "2" || typeFlag === "3" || typeFlag === "4" || typeFlag === "5") && (periodType === "2" || (periodType === "3" && isSubmitReedit !== "1"))'></collapse-review-write>

      <!-- 审核意见文件 -->
      <fileList title="审核意见文件" :showUploadBtn="false" :delBtnFlag="false" :list="auditFileList"
                v-if='isMainHeadFlag === "1" && auditFileList.length>0&&(periodType==="2"||periodType==="3")&&(typeFlag === "0" || typeFlag === "1" || typeFlag === "15")'></fileList>

    </el-collapse>
    </div>

    <!-- 提交审核弹窗组件 A3阶段 -->
    <dialog-submit-audit ref="submit" @submit="submitAuditA3Period" :above="above" @cancel="cancel" :reviewer="reviewer"></dialog-submit-audit>

    <!-- 文件浏览 -->
    <!--<dialog-browse-file :file="file" ref="dialogBrowseFile" :type="type" :id="taskId"></dialog-browse-file>-->

    <preview-file :file="file" ref="dialogBrowseFile" :type="type" :id="taskId"></preview-file>

    <div class="btn-group">

      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;"></p>

      <!-- 跳级审核 -->
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;">
        <el-checkbox v-model="above" style="color: white;"
                     v-if='((((approveTime == null && isSubmitReedit !== "1") || approveTime === "") && typeFlag === "1") || ((typeFlag === "2" || typeFlag === "3") && isSubmitReedit !== "1" )) && jurisdiction.verifyState === ""'>
          跳级审核
        </el-checkbox>
      </p>

      <!-- A3阶段 -->
      <el-button type="primary" v-if='A3SubmitAuditBtnFlag' @click="submitAuditA3Period">提交审核</el-button>
      <el-button type="primary" v-if='A3RecallBtnFlag' @click="recallTaskA3Period">撤回</el-button>
      <el-button type="primary" v-if='A3PassBtnFlag' @click="auditingTaskA3Period('1')">通过</el-button>
      <el-button type="primary" v-if='A3IssueBtnFlag' @click="auditingTaskA3Period('1')">签发</el-button>
      <el-button type="primary" v-if='A3RejectBtnFlag' @click="auditingTaskA3Period('0')">驳回</el-button>

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
  // import dialogBrowseFile from '../../../common/components/dialogBrowseFile.vue';
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
      // 重新编辑历史记录
      'collapseReviewWriteNoPoints': collapseReviewWriteNoPoints,
      // 浏览文件
      // 'dialog-browse-file':dialogBrowseFile,
      // 浏览文件
      'previewFile':previewFile
    },
    props: ['currentTransmitData', 'jurisdiction', 'typeFlag','auditFileList','errorTyleList','postil'],
    data() {
      const formData = new FormData();
      return {
        type: "task",// 任务标识
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
        // 上传文件列表
        fileList: [],
        // 阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
        periodType: '',
        // 文件类别列表
        fileTypeList: [],
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

        showUploadBtnFlg: false,// 上传按钮是否显示标识
        delBtnFlag: false,// 是否显示删除按钮
        updateDate:"",// 修改时间
        approveTime:null,// 审批时间
        isIgnore:"",// 是否忽略
        isFinish:"",// 是否完成
        file:'',// 浏览文件
        isMainHeadFlag:"",// 是否是主提审人
        A3SubmitAuditBtnFlag:false,// A3提交审核按钮
        A3RecallBtnFlag:false,// A3撤回按钮
        A3PassBtnFlag:false,// A3通过按钮
        A3IssueBtnFlag:false,// A3签发按钮
        A3RejectBtnFlag:false,// A3驳回按钮
      };
    },
    watch: {
      currentTransmitData: {
        handler(newval) {
          let vm = this;
          vm.currentTransmittedData = newval;
          vm.init();
        },
        immediate: true,
        deep: true
      },
      typeFlag: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          let vm = this;
          vm.typeFlag = newval;
          vm.init();
        },
        immeditate: true,
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
      auditFileList: {
        handler(newval) {
          let vm = this;
          vm.auditFileList = newval;
          console.log('auditFileList', vm.auditFileList);
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
      postil: {
        handler(newval) {
          let vm = this;
          vm.postil = newval;
        },
        immediate: true,
        deep: true
      },
    },
    methods: {
      // 未提交审核时的预览
      preview(data) {

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
          periodName: this.currentTransmittedData.label
        };

        // 触发提交审核-编制成果文件事件
        this.bus.$emit('preview', sendData);
      },
      /**
       * @method 浏览文件
       */
      browseFile(file){
        let vm = this;
        if(!file.materialPath){
          vm.stateDialog('error', "该文件暂时不能浏览");
          return;
        }

        vm.file = vm.tool.deepCopy(file, vm.tool.deepCopy);
        vm.$refs.dialogBrowseFile.show = true;
      },
      // ===================================================初始化信息 start===================================================
      // 初始化信息
      init() {

        let vm = this;

        // 清除上次的值
        vm.above = false;

        // 任务阶段关联id
        vm.relationId = vm.currentTransmittedData.relationId;
        // 任务id
        vm.taskId = vm.currentTransmittedData.taskId;
        // 阶段类型
        vm.periodType = vm.currentTransmittedData.periodType;
        // 部门id
        vm.departmentId = vm.currentTransmittedData.departmentId;
        // 咨询类别id
        vm.counselTypeId = vm.currentTransmittedData.counselTypeId;
        // 是否是主提审人
        vm.isMainHeadFlag = vm.currentTransmittedData.isMainHeadFlag;

        // A3上传文件
        vm.showUploadBtnFlg = (vm.typeFlag === "1" || (vm.typeFlag === "15" && currentState !== "6")) && vm.jurisdiction.verifyState === "";// 是否显示上传按钮  true:显示  false:不显示
        // A3删除文件
        vm.delBtnFlag = !(vm.showUploadBtnFlg);// 是否显示删除按钮  true:不显示  false:显示
        // A3提交审核按钮
        vm.A3SubmitAuditBtnFlag = vm.typeFlag === "1" && vm.jurisdiction.verifyState === "";
        // A3撤回按钮
        vm.A3RecallBtnFlag = vm.typeFlag === "20" && vm.jurisdiction.verifyState === "";
        // A3通过按钮
        vm.A3PassBtnFlag = (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4") && vm.jurisdiction.verifyState === "";
        // A3签发按钮
        vm.A3IssueBtnFlag = vm.typeFlag === "5" && vm.jurisdiction.verifyState === "";
        // A3驳回按钮
        vm.A3RejectBtnFlag = (vm.typeFlag === "2" || vm.typeFlag === "3" || vm.typeFlag === "4" || vm.typeFlag === "5") && vm.jurisdiction.verifyState === "";

        console.log("typeFlag为", vm.typeFlag)

        console.log("第", vm.currentTransmittedData.treeNodeLevel+" 级：");

        // 查询素材列表
        vm.searchMaterialList(vm.relationId,vm.typeFlag);
        // 查询上传素材类型下拉列表
        vm.searchMaterialTypeList();
        // 根据任务阶段关联id查询任务阶段信息
        vm.searchTaskPeriodDetailInfo(vm.relationId);
        // 根据任务阶段关联id查询审核意见列表
        vm.searchHTTaskHisTaskInst(vm.relationId);

      },
      // ===================================================初始化信息 end===================================================
      // 面板-复核可写改变时
      changeObject(obj) {
        this.object = obj
      },
      // 跳级取消
      cancel() {
        this.above = false;
      },
      // 撤回
      recallTaskA3Period() {
        this.$confirm('是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          this.apiAddress('/task/modify/recallTaskA3Period', {
            // 任务阶段关联id
            relationId: this.relationId,
            // 修改时间
            updateDate: this.updateDate
          }).then(res => {
            if (res.data.status == 'failure') {
              this.stateDialog('error', res.data.message);
            } else {
              this.stateDialog('success', res.data.message);
              // 刷新
              this.$router.go(0);
            }
          }).catch(err => {
            this.stateDialog('error', err);
          });
        }).catch(() => {
        });
      },
      // 在click事件之后，点击弹窗中的提交审核
      submitAuditA3Period(data) {
        let vm = this;
        vm.$confirm('是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          if (null == vm.fileList || vm.fileList.length == 0) {
            vm.$message({
              message: '请上传'+vm.currentTransmittedData.label+'文件',
              type: 'error',
              center: true
            });
            return;
          }

          let nextTaskKey = vm.nextTaskKey;// 下一流程节点
          if (vm.above) {
            nextTaskKey = vm.getNextStepNode(nextTaskKey)// 获取下一流程节点
          }

          // 提交审核请求
          vm.apiAddress('/task/modify/submitAuditA3Period', {
            relationId: vm.relationId,// 任务阶段关联id
            submitNode: nextTaskKey,// 下一流程节点
            periodName: vm.currentTransmittedData.label// 阶段名称
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);
              vm.$refs.submit.show = false;// 隐藏提交审核的弹出框
              // vm.$router.go(0);// 刷新
              vm.$router.push('/page/work/default');
            } else {
              vm.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            vm.stateDialog('error', e);
          });
        }).catch(() => {});
      },
      // 展示可以提交审核的文件列表,如果没有文件，则提示请上传投标文件
      submit(type) {
        let vm = this;
        vm.$confirm('是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
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
                  if (!type) {
                    // 文件列表
                    let dataList = res.data.dataList;
                    for (var i = 0; i < dataList.length; i++) {
                      if (dataList[i].children.length != 0) {
                        // 把返回的文件列表的数据放在提交审核的弹出框的list中
                        vm.$refs.submit.list = dataList;
                        // 显示提交审核的弹出框
                        vm.$refs.submit.show = true;
                        // 不直接提交审核
                        isSubmit = false;
                        break;
                      }
                    }
                  }
                  console.log("isSubmit",isSubmit)
                  // 如果没有任何文件，则直接提交审核
                  if (isSubmit) {

                    let param = {"checkbox": []};

                    // 成果文件提交审核
                    vm.submitAuditOutcomeDocumentPeriod(param,type);
                  }

                }
              }).catch(err => {
              vm.stateDialog('error', err)
            });
          });
        }).catch(() => {});
      },
      // 审核通过和驳回
      auditingTaskA3Period(isPass) {
        let vm = this;
        vm.$confirm('是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let paramObj = vm.object;
          paramObj.isPass = isPass;
          paramObj.updateDate = vm.updateDate;

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
          vm.apiAddress('/task/add/auditingTaskA3Period', paramObj).then(res => {
            if (res.data.status == 'failure') {
              vm.stateDialog('error', res.data.message);
            } else {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // this.$router.go(0)
              vm.$router.push('/page/work/default');
            }
          }).catch(err => {
            vm.stateDialog('error', err);
          })
        }).catch(() => {});
      },
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

                // 如果是收集资料阶段，在归档时机显示上传按钮
                if (vm.periodType === "1" && vm.typeFlag === "6" && vm.isSubmitReedit !== "1") {

                  dataList[i].isCanDel = '1';

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
      // 查询上传素材类型下拉列表
      searchMaterialTypeList() {
        let formData = {
          departmentId:this.departmentId,
          counselTypeId:this.counselTypeId
        }
        // 查询上传素材类型下拉列表
        this.apiAddress('/tenderTask/query/searchMaterialTypeList', formData).then(res => {
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
            this.fileTypeList = dataList
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        })
      },
      //查询审核意见列表
      searchHTTaskHisTaskInst(relationId) {

        let vm = this;

        //查询审核意见列表
        vm.apiAddress('/task/query/searchHTTaskHisTaskInst', {
          relationId: relationId,
        }).then(res => {
          if (res.data.status == 'failure') {
            this.stateDialog('error', res.data.message)
          } else {

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

            // 把转换后的dataList赋值给opinionList
            vm.opinionList = dataList
          }
        }).catch(err => {
          this.stateDialog('error', err)
        });
      },
      // 查询任务阶段信息
      searchTaskPeriodDetailInfo(relationId) {
        let vm = this;
        let formData = {
          relationId: relationId
        }
        vm.apiAddress('/task/query/searchTaskPeriodDetailInfoByRelationId', formData).then(res => {
          if (res.data.status == 'failure') {
            this.stateDialog('error', res.data.message)
          } else {
            // 任务阶段信息
            let taskPeriodInfo = res.data.dataVO;

            // 审核级别
            vm.object.title = vm.getStepName(taskPeriodInfo.delegateTaskKey);
            // 下一节点
            vm.nextTaskKey = taskPeriodInfo.submitNode;
            // 任务委托key
            vm.delegateTaskKey = taskPeriodInfo.delegateTaskKey;
            // 修改时间
            vm.updateDate = taskPeriodInfo.updateDate;
            // 是否能撤回
            vm.isCanRecall = taskPeriodInfo.isCanRecall;
            // 设置是否提交了重新编辑申请
            vm.isSubmitReedit = taskPeriodInfo.isSubmitReedit;
            // 审批时间
            vm.approveTime = taskPeriodInfo.approveTime;

            // 人员列表
            let userList = taskPeriodInfo.userList;
            // 如果人员列表不为空
            if (userList != undefined) {
              // 循环人员列表，并转换格式
              for (var i = userList.length - 1; i >= 0; i--) {
                userList[i].errorList = [];
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g, "id"));
                userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g, "name"));
              }
            }

            // 当前流程人员
            vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);
            // 下一流程人员
            vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));

            vm.object.children = userList;

          }
        }).catch(err => {
          this.stateDialog('error', err)
        })
      },
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
      // 下载文件信息
      downloadMatrial(item) {
        // 调用全局下载方法
        this.downloadFile(item.url, item.name);
      },
      // 提交上传文件
      submitForm(obj) {

        obj.append("taskPeriodId", this.relationId);// 任务阶段id
        obj.append("fileType", obj.get('select')); // 文件类型
        obj.delete('select')
        obj.append("periodType", this.currentTransmittedData.periodType);// A3阶段标识

        // 提交文件请求
        this.apiAddress('/task/add/insertTaskPeriodMaterialInfo', obj).then(res => {
          if (res.data.status == "success") {
            this.stateDialog('success', res.data.message);
            // 查询素材列表
            this.searchMaterialList(this.relationId,this.typeFlag);
          } else {
            this.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          this.stateDialog('error', err);
        })
      },
      checkFile(n) {
        this.bus.$emit('checkFile', n);
      },
      present() {
        this.bus.$emit("changeform")
      },
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
