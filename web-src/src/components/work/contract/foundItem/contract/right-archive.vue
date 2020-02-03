<template>
  <!-- 归档 -->
  <div class="right-archive">
    <div class="given-container">
    <el-collapse v-model="activeNames" accordion>
      <fileList res = "contractTemplate" title="合同" :showUploadBtn="false" :list="contractList" :delBtnFlag="false" v-if="rowInfoLevel==3&&loadChild&&contractList.length>0"></fileList>

      <!--<fileList-->
        <!--res="contractTemplate"-->
        <!--title="上传归档附件"-->
        <!--:showUploadBtn="true"-->
        <!--:list="annexList"-->
        <!--:userType='userType'-->
        <!--@submit="submitForm"-->
        <!--@del="deleteFileInfo"-->
        <!--v-if="rowInfoLevel==3&&loadChild"-->
        <!--:isDirectlyUpload="true"-->
        <!--&gt;</fileList>-->

      <el-collapse-item title="法务意见" name="99" v-if="!!legalOpinion">
         <div class="content">
          <el-input type="textarea" v-model="legalOpinion" :disabled="true" placeholder="请输入法务意见"></el-input>
         </div>
      </el-collapse-item>
      <!--审核意见-->
      <div v-for="(item,i) in opinionList" :key="i">
        <collapse-review-read :object="item" name="item.taskKey"></collapse-review-read>
      </div>

         <!-- 面板-上传下载 -->
        <el-collapse-item class="invoice" title="上传归档附件">
          <template slot="title">
            上传归档附件&nbsp;
            <i class="el-icon-plus linear" style="color: white;padding: 2px 6px;border-radius: 5px;" @click.stop="uploadFile"></i>
          </template>
          <div class="content" hide-upload-container>
            <el-upload style="display: none;"
              ref="uploadDemo"
              accept="text"
              class="upload-demo"
              action="#"
              :file-list="list"
              :show-file-list="false"
              :http-request="httpRequest"
              multiple>
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            <div class="file-list">
              <ul>

                <li tabindex="0" class="el-upload-list__item is-finished" v-for="(f, index) in list" v-bind:key="index">
                  <a class="el-upload-list__item-name" style="width: 100%; margin-right: 0;" :title="f.materialName">
                    <i class="el-icon-document"></i>{{ f.materialName }}
                  </a>
                  <a class="el-upload-list__item-name" style="width: 60px;">
                    {{ f.userName}}
                  </a>
                  <label class="el-upload-list__item-status-label"><i class="el-icon-upload-success el-icon-circle-check"></i></label>
                  <i class="el-icon-download" @click.stop="download(f.materialPath,f.materialName,f.isGeneratedFile)"></i>
                  <i class="el-icon-close" @click.stop="deleteFileInfo(f)" v-if="(userType == 1 && f.isCanDel == 1 )|| userType == 2"></i>
                </li>

              </ul>
            </div>
          </div>
        </el-collapse-item>





    </el-collapse>
    </div>
    <div class="btn-group">
      <el-input type="text" v-model="form.desc"></el-input>
      <el-button type="primary" style="margin-top: 10px;" @click="submitArchiveButton">确认归档</el-button>
    </div>

  </div>
</template>

<script>
  import axios from 'axios';
  // 面板--上传下载
  import collapseUpAndDownload from '../../../common/components/collapseUpAndDownload.vue';
  // 面板-复核只读
  import collapseReviewRead from '../../../common/components/collapseReviewRead.vue';

  export default {
    components: {
      'fileList': collapseUpAndDownload,
        'collapse-review-read': collapseReviewRead,
    },
    props:['transmittedData','typeFlag'],
    data() {
      return {
        limit:1,// 文件上传个数
        list:[],// 文件列表
        currentTransmittedData:this.transmittedData,
        form: {
          desc: ''
        },
        legalOpinion:'',
        contractList: [],
        annexList:[],
        activeNames: '',
        contract: false,
        rowInfoLevel: '1',
        rowInfoLabel: 'sdsd',
        userType: '2',
        userContractTemplateType: '0',
        pageContent: "",
        opinionList: [],
        auditOpinionList: [],
        loadChild: true,
        updateDate:""
      };
    },
    watch:{
        transmittedData(){
            this.currentTransmittedData = this.transmittedData;
        },
        list(){
          let vm = this;
        }
    },
    mounted() {
        const vm = this;
        this.getContractTemplateInfo(this.transmittedData);
        // 合同基本信息显示
        vm.bus.$on('click', val => {
            vm.getContractTemplateInfo(val);
        });
    },
    methods: {
      httpRequest(request) { // 自定义上传请求
        let vm = this;
        let form = new FormData();
        form.append('file', request.file);
        form.append('contractId',vm.currentTransmittedData.id);
        vm.submitForm(form);

      },
      uploadFile(){// 上传文件点击事件
        let vm = this;
        vm.$refs['uploadDemo'].$children[0].$el.click();
      },
        getContractTemplateInfo(val){
            let vm = this;
            vm.rowInfoLevel = val.treeNodeLevel;
            vm.contractId = val.contractId;
            if (val.treeNodeLevel == 3){
                /*查询合同信息*/
                vm.searchContractInfo(val.id);
                /*查询合同归档文件信息*/
                vm.searchContractArchiveFileInfo(val.id);
                /*查询审核意见*/
                vm.searchAuditOpinionListFunc(val.id);
            }
        },
        sealButton(){
            let vm = this;
            let formData = {
                contractId: vm.currentTransmittedData.id
            };
            // 走盖章接口
            this.apiAddress('/contract/modify/confirmSealHTContract', formData).then(res => {
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
                    this.bus.$emit('asideRefresh',{checkedKey:vm.currentTransmittedData.id});
                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            })
        },
        /*查询合同信息*/
        searchContractInfo(contractId) {
            let vm = this;
            let formData = {
                contractId: contractId
            };
            vm.apiAddress('/contract/query/searchContractInfoById', formData).then(res => {
                if (res.data.status == 'failure') {
                    vm.$message({
                        message: res.data.message,
                        type: 'error',
                        center: true
                    });
                } else {
                    let tempContractInfo = res.data.dataVO;

                    vm.legalOpinion = tempContractInfo.legalOpinion;
                    //修改时间
                    vm.updateDate = tempContractInfo.updateDate;

                    // 合同路径
                    let contractFilePath = tempContractInfo.contractFilePath;

                    // 合同类型
                    let contractType = tempContractInfo.contractType;

                    // 是否调用合同模板生成接口 默认走正常的下载接口
                    let isGeneratedFile = false;

                    // 如果合同类型为模板合同
                    if (contractType == '1'){

                        // 合同路径 后面拼接合同id + 项目编号
                        contractFilePath = contractFilePath + '?contractId=' + tempContractInfo.contractId + '&projectNum=' + tempContractInfo.projectNum;

                        // 调用同模板生成接口 生成合同模板
                        isGeneratedFile = true;
                    }

                    if (tempContractInfo.contractFileName != null && tempContractInfo.contractFileName != ""){

                        vm.contractList[0] = {
                            "materialName": tempContractInfo.contractFileName,
                            "materialPath": contractFilePath,
                            "isGeneratedFile": isGeneratedFile
                        };
                    }
                    vm.toggleTab();
                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            });
            this.centerDialogVisible = true
        },
        searchContractArchiveFileInfo(contractId){
            let vm = this;
            let formData = {
                contractId: contractId

            };
            vm.apiAddress('/contract/query/searchContractArchiveFileList', formData).then(res => {
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
                        dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));
                    }
                    // 设置转换之后的list
                    this.list = dataList;
                    vm.toggleTab();
                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            });
            this.centerDialogVisible = true
        },
        // 提交上传文件
        submitForm(obj) {
            let vm  = this;

            // 提交文件请求
            this.apiAddress('/contract/add/insertContractArchiveFileInfo', obj).then(res => {
                if (res.data.status == "success") {
                    this.stateDialog('success', res.data.message);
                    // 查询归档文件列表
                    this.searchContractArchiveFileInfo(vm.currentTransmittedData.id);
                } else {
                    this.stateDialog('error', res.data.message)
                }
            }).catch(err => {
                this.stateDialog('success', res.data.message);
            })
        },
        // 删除文件信息
        deleteFileInfo(val) {
            let vm  = this;
            this.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                center: true
            }).then(() => {
                this.apiAddress('/contract/delete/deleteContractArchiveFileInfo', {
                    relationId: val.materialId,
                    materialPath: val.materialPath
                }).then(res => {
                    // 如果返回成功，则提示成功信息，否则提示失败信息
                    if (res.data.status == "success") {
                        // 查询归档文件列表
                        this.searchContractArchiveFileInfo(vm.currentTransmittedData.id);
                    } else {
                        this.stateDialog('error', res.data.message)
                    }
                }).catch(e => {
                    this.stateDialog('error', e)
                })
            }).catch(() => {});
        },
        submitArchiveButton(){
          let vm = this;
          vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
          }).then(() => {
            if (null== vm.list || vm.list.length == 0) {
                vm.$message({
                    message: '请上传归档附件',
                    type: 'error',
                    center: true
                });
                return
            }
            let formData = {
                contractId: vm.currentTransmittedData.id,
                fileNumber: vm.form.desc,
                updateDate: vm.updateDate
            };
            // 走提交归档接口
            vm.apiAddress('/contract/modify/submitArchiveHTContract', formData).then(res => {
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
                    vm.$router.go(0);
                    // vm.$router.push('/page/work/default');
                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            })
          }).catch(() => {});
        },

        searchAuditOpinionListFunc(contractId) {

            let vm = this;
            let formData = {
                contractTaskId: contractId,
            };
            //查询审核意见列表
            vm.apiAddress('/contract/query/searchHTContractTaskHisTaskInst', formData).then(
                res => {
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
                              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htcontractTaskHisTaskInstVOList/g,
                                  "children"));
                              let children = dataList[i].children;
                              for (var j = 0; j < children.length; j++) {
                                  //将属性名approveTime转换成属性名time
                                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g, "time"));
                                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g, "name"));
                                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,
                                      "persions"));
                                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g, "opinion"));
                                  let persions = vm.groupBy(children[j].persions, function(item) {
                                      return [item.userId]
                                  })
                                  for (var k = 0; k < persions.length; k++) {
                                      persions[k].name = persions[k].dataList[0].userName;
                                      persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g, "errorList"));
                                      let errorList = persions[k].errorList;
                                      for (var l = errorList.length - 1; l >= 0; l--) {
                                          errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g, "type"));
                                          errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g, "num"));
                                      }
                                      persions[k].errorList = errorList;
                                  }
                                  children[j].persions = persions;
                               }
                          }
            /*---------------------------------格式转换END-------------------------------------*/
                 vm.opinionList = dataList
                }
              }).catch(err => {
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
            })
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
</style>
<style lang="scss" scoped>
  .right-archive{
    height: calc(100% - 20px;);
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
