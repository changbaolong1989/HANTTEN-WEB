<template>
  <!-- 盖章 -->
  <div class="right-seal">
    <div class="given-container">
    <el-collapse v-model="activeNames" accordion>

      <fileList res = "contractTemplate" title="合同" :showUploadBtn="false" :list="contractList" :userType='userType' v-if="rowInfoLevel==3&&loadChild&&contractList.length>0"></fileList>

      <el-collapse-item title="法务意见" name="99" v-if="!!legalOpinion">
         <div class="content">
          <el-input type="textarea" v-model="legalOpinion" :disabled="true" placeholder="请输入法务意见"></el-input>
         </div>
      </el-collapse-item>


      <!--审核意见-->
      <div v-for="(item,i) in opinionList" :key="i">
        <collapse-review-read :object="item" name="item.taskKey"></collapse-review-read>
      </div>

    </el-collapse>
</div>
    <div class="btn-group">
      <el-button type="primary" style="padding: 0 20px;width: 50%;position: absolute;bottom: 20px;left: 25%;" @click="sealButton">确认盖章</el-button>
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
        currentTransmittedData:this.transmittedData,
        contractList: [],
        user: {
          userId: '123',
          userName: '小红'
        },
        legalOpinion:'',
        activeNames: '',
        contract:false,
        rowInfoLevel: '1',
        rowInfoLabel: 'sdsd',
        userType:'0',
        pageContent:"",
        opinionList: [],
        auditOpinionList:[],
        loadChild:true,
        contractId:"",
        updateDate:""
      };
    },
    watch:{
        transmittedData(){
            this.currentTransmittedData = this.transmittedData;
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
        getContractTemplateInfo(val){
            let vm = this;
            vm.rowInfoLevel = val.treeNodeLevel;
            vm.contractId = val.contractId;
            if (val.treeNodeLevel == 3){
              /*查询合同信息*/
                vm.searchContractInfo(val.id);
              /*查询审核意见*/
                vm.searchAuditOpinionListFunc(val.id);
            }
        },
        sealButton(){
          let vm = this;
          vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
          }).then(() => {
            let formData = {
                contractId: vm.currentTransmittedData.id,
                updateDate: vm.updateDate
            };
            // 走盖章接口
            vm.apiAddress('/contract/modify/confirmSealHTContract', formData).then(res => {
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
                    // vm.bus.$emit('asideRefresh',{checkedKey:vm.currentTransmittedData.id});
                    vm.$router.push('/page/work/default');
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

                    //法务信息
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
                    vm.toggleTab()
                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            })
            this.centerDialogVisible = true
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
  .right-seal{
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
