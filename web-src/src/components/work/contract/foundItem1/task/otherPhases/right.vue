<template>

  <!-- 任务right -->
  <div class="taskR">
    <div class="given-container">
    <!--折叠面板-->
    <el-collapse v-model="activeNames" accordion>

      <!-- 文件上传-->
      <fileList :title="currentTransmitData.label" :showUploadBtn="showUploadBtnFlg" :options="fileTypeList"
                @del="deleteFileInfo" :list="fileList"
                @submit="submitForm"
                :delBtnFlag="delBtnFlag" :type="type" :id="taskId"
                :periodTypeBoolean='currentTransmitData.periodType !== "1"' :periodType="currentTransmitData.periodType"></fileList>

    </el-collapse>
    </div>

    <!-- 文件浏览 -->
    <!--<dialog-browse-file :file="file" ref="dialogBrowseFile" :type="type" :id="taskId"></dialog-browse-file>-->

    <preview-file :file="file" ref="dialogBrowseFile" :type="type" :id="taskId"></preview-file>

    <div class="btn-group">
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 20px;"></p>

      <!-- 是否忽略 -->
      <el-button type="primary" v-if='IgnoreBtnFlag' @click="isIgnoreTaskPeriod">{{isIgnore==="1"?"展示":"忽略"}}</el-button>
      <!-- 完成 -->
      <el-button type="primary" v-if='FinishBtnFlag' @click="finish">{{isFinish==="1"?"未完成":"完成"}}</el-button>

    </div>

  </div>

</template>

<script>
  // 面板-上传下载
  import collapseUpAndDownload from '../../../../common/components/collapseUpAndDownload.vue';
  // 浏览文件
  import dialogBrowseFile from '../../../../common/components/dialogBrowseFile.vue';
  // 浏览文件
  import previewFile from '../../../../common/components/previewFile.vue';

  export default {
    components: {
      // 引入上传组件
      'fileList': collapseUpAndDownload,
      // 浏览文件
      'dialog-browse-file':dialogBrowseFile,
      // 浏览文件
      'previewFile':previewFile
    },
    props: ['currentTransmitData', 'typeFlag'],
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          const vm = this;
          vm.currentTransmitData = newval;
          vm.init();
        },
        immeditate: true
      },
      typeFlag: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          const vm = this;
          vm.typeFlag = newval;
          // 初始化信息
          vm.init();
        },
        immeditate: true
      }
    },
    created() {
      let vm = this;
      // 初始化信息
      vm.init();
    },
    data() {
      return {
        // 任务标识
        type: "task",
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
        // 任务阶段id
        taskPeriodId: "",
        // 部门唯一标识
        departmentId: '',
        // 咨询类别ID
        counselTypeId:'',
        // 任务阶段关联id
        relationId: '',
        delBtnFlag: false,// 是否显示删除按钮
        isIgnore:"",// 是否忽略字段
        isFinish:"",// 是否完成字段
        isMainHeadFlag:"",// 是否是主提审人
        IgnoreBtnFlag:false,// 是否忽略按钮
        FinishBtnFlag:false,// 是否完成按钮
        file:'',// 浏览文件
        projectSts:"",// 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
        showUploadBtnFlg:"",// 上传按钮是否显示
        isInvalidFlag:"",// 是否作废
        isSubmitReedit:""// 是否提了重新编辑申请
      };
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
          periodName: this.currentTransmitData.label
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

        // if(file.deleteFlag ==="1"){

        //   for (var i = 0; i < vm.list.length; i++) {
        //     if (vm.list[i].uuid == file.uuid) {
        //       Object.assign(file, {file:vm.archiveList[i]});
        //       break;
        //     }
        //   }

        // }

        vm.file = vm.tool.deepCopy(file, vm.tool.deepCopy);
        vm.$refs.dialogBrowseFile.show = true;
      },
      // =================================================初始化信息start=================================================
      // 初始化信息
      init() {

        // 任务阶段关联id
        this.relationId = this.currentTransmitData.relationId;
        // 任务id
        this.taskId = this.currentTransmitData.taskId;
        // 阶段类型
        this.periodType = this.currentTransmitData.periodType;
        // 部门id
        this.departmentId = this.currentTransmitData.departmentId;
        // 咨询类别id
        this.counselTypeId = this.currentTransmitData.counselTypeId;
        // 是否忽略
        this.isIgnore = this.currentTransmitData.isIgnore;
        // 是否完成
        this.isFinish = this.currentTransmitData.isFinish;
        // 是否是主提审人
        this.isMainHeadFlag = this.currentTransmitData.isMainHeadFlag;
        // 项目状态(0.未完成 1.已完成(项目关闭也视为已完成))
        this.projectSts = this.currentTransmitData.projectSts;
        // 是否作废
        this.isInvalidFlag = this.currentTransmitData.isInvalidFlag;


        // 查询当前人类别(用以展示按钮)
        this.showTypeFlag(this.relationId, this.periodType, this.taskId);

      },
      // =================================================初始化信息end=================================================
      // =================================================查询当前人类别 start=================================================
      //查询当前人类别(用以展示按钮)
      showTypeFlag(relationId, periodType, taskId) {
        let vm = this;
        this.apiAddress('/task/query/showBtnType', {
          // 任务阶段id
          "relationId": relationId,
          // 阶段类型
          "periodType": periodType,
          // 任务id
          "taskId": taskId
        }).then(res => {
          if (res.data.status == 'success') {

            // 重新编辑申请
            vm.isSubmitReedit = res.data.dataVO.isSubmitReedit;
            // 当前状态
            let currentState = res.data.dataVO.currentState;

            // ==============================各个阶段通用 start=================================

            // 其他阶段
            if (periodType === "0" || periodType === "4" || periodType === "5" || periodType === "6" || periodType === "7" || periodType === "8" || periodType === "9") {

              vm.showUploadBtnFlg = vm.typeFlag === "1" || (vm.typeFlag === "15" && currentState !== "6");// 是否显示上传按钮  true:显示  false:不显示
              vm.delBtnFlag = !(vm.showUploadBtnFlg);// 是否显示删除按钮  true:不显示  false:显示

            // 收集资料
            } else if (periodType === "1"){

              // (vm.currentState === "6" && vm.isSubmitReedit !== "1") 主提审人和普通执行人待归档时机
              // vm.typeFlag === "1" 主提审人待归档之前时机
              // (vm.typeFlag === "15" && vm.isSubmitReedit !== "1") 普通执行人待归档及待归档之前时机
              vm.showUploadBtnFlg = ((currentState === "6" && vm.isSubmitReedit !== "1")
                                    || vm.typeFlag === "1"
                                    || (vm.typeFlag === "15" && vm.isSubmitReedit !== "1")) && vm.typeFlag !== "0" ;

              vm.delBtnFlag = !(vm.showUploadBtnFlg);

            }

            if (periodType === "1" && vm.typeFlag === "6" && vm.isSubmitReedit !== "1") {// 如果是收集资料阶段，在归档时机，没提交重新编辑申请显示上传按钮
              vm.showUploadBtnFlg = true;// 是否显示上传按钮  true:显示  false:不显示
              vm.delBtnFlag = false;// 是否显示删除按钮  true:不显示  false:显示
            }

            if (vm.typeFlag === "15" && vm.isSubmitReedit === "1") {// 如果是普通执行人，提了重新编辑申请
              vm.showUploadBtnFlg = false;// 是否显示上传按钮  true:显示  false:不显示
              vm.delBtnFlag = true;// 是否显示删除按钮  true:不显示  false:显示
            }

            // ==============================各个阶段通用 end=================================

            // ==============================忽略和完成 按钮 start=================================


            this.IgnoreBtnFlag = this.isInvalidFlag !=="1" && this.projectSts !=="1" && this.isMainHeadFlag === "1";// 是否忽略按钮
            this.FinishBtnFlag = this.IgnoreBtnFlag;// 是否完成按钮

            // ==============================忽略和完成 归档 按钮 end=================================

            // 查询素材列表
            vm.searchMaterialList(vm.relationId,vm.typeFlag);
            // 查询上传素材类型下拉列表
            vm.searchMaterialTypeList();


          } else {
            this.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          this.stateDialog('error', err);
        })
      },
      // =================================================查询当前人类别 end=================================================
      // =================================================是否完成任务阶段 start=================================================
      // 是否完成
      finish() {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {
          let isFinishFlag = "";

          if(vm.isFinish === "0"){
            isFinishFlag = "1";
          } else {
            isFinishFlag = "0";
          }

          vm.apiAddress('/task/modify/updateIsFinish', {
            // 任务阶段id
            "relationId": vm.relationId,
            // 是否忽略
            "isFinish": isFinishFlag
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
          })
        }).catch(() => {});
      },
      // =================================================是否完成任务阶段 end=================================================
      // =================================================是否忽略任务阶段 start=================================================
      // 是否忽略
      isIgnoreTaskPeriod() {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {
          let isIgnoreFlag = "";

          if(vm.isIgnore === "0"){
            isIgnoreFlag = "1";
          } else {
            isIgnoreFlag = "0";
          }

          vm.apiAddress('/task/modify/updateIsIgnore', {
            // 任务阶段id
            "relationId": vm.relationId,
            // 是否忽略
            "isIgnore": isIgnoreFlag
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
          })
        }).catch(() => {});
      },
      // =================================================是否忽略任务阶段 end=================================================
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
      // =================================================查询素材列表 end=================================================
      // =================================================查询上传素材类型下拉列表 start=================================================
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
      // =================================================查询上传素材类型下拉列表 end=================================================
      // =================================================提交上传文件 end=================================================
      // 提交上传文件
      submitForm(obj) {
        // 任务阶段id
        obj.append("taskPeriodId", this.relationId);
        // 文件类型
        obj.append("fileType", obj.get('select'));
        obj.delete('select')

        // 提交文件请求
        this.apiAddress('/task/add/insertTaskPeriodMaterialInfo', obj).then(res => {
          if (res.data.status == "success") {
            this.stateDialog('success', res.data.message);
            // 查询素材列表
            this.searchMaterialList(this.relationId,this.typeFlag);
            // 如果是收集资料结算，刷新主页面
            if (this.currentTransmitData.periodType === "1" && this.currentTransmitData.pageContent == 'A4') {
              this.searchList(this.currentTransmitData);
            }
          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(err => {
          this.stateDialog('error', err);
        })
      },
      // =================================================提交上传文件 end=================================================
      // =================================================删除阶段文件 start=================================================
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
              // 如果是收集资料结算，刷新主页面
              if (vm.currentTransmitData.periodType == 1 && vm.currentTransmitData.pageContent == 'A4') {
                vm.searchList(vm.currentTransmitData);
              }
            } else {
              vm.stateDialog('error', res.data.message);
            }
          }).catch(e => {
            vm.stateDialog('error', e)
          })
        }).catch(() => {
        });
      },
      // =================================================删除阶段文件 end=================================================
      // 下载文件信息
      downloadMatrial(item) {
        // 调用全局下载方法
        this.downloadFile(item.url, item.name);
      },
      // 查询A4信息
      searchList(data) {
        this.bus.$emit("searchList", data);
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
