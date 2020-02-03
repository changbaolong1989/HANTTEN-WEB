<template>
  <!-- 请款 -->
  <div class="foundItem__requestPayout">
    <div class="given-container">
      <el-collapse class="level1" v-model="activeNames">
        <fileList type="request" :id = "transmittedData.id" title="请款单文件" :showUploadBtn="false" :delBtnFlag="false" :list="requestFileList" v-if="requestFileList.length>0"></fileList>
        <template v-for="(item,i) in opinionList">
          <collapse-review-read :object="item" :key="i"></collapse-review-read>
        </template>
        <!-- 面板-复核可写-->
        <collapse-review-write name="white1" v-if="questionCompFlag" :object="object" @submit="changeObject" :errorTyleList="errorTyleList" ></collapse-review-write>
        <fileList  title="发票" name='发票' v-if="showInvoiceCompFlag||invoiceFileList.length>0" :showUploadBtn="invoiceUploadBtnFlag" :delBtnFlag="invoiceDelBtnFlag" :uploadOptions="uploadOptions"  @del="deleteFileInfo" :list="invoiceFileList" :userType = '2'
          @submit="submitInvoiceForm"></fileList>
        <fileList  title="打款凭证" name='打款凭证' :uploadOptions="paymentVoucherUploadOptions" v-if="showPaymentVoucherCompFlag||paymentVoucherFileList.length>0" :showUploadBtn="paymentVoucherUploadBtnFlag" :delBtnFlag="paymentVoucherDelBtnFlag"  @del="deleteFileInfo" :list="paymentVoucherFileList" :userType = '2' :isPayMoney = 'true'
            @submit="submitPaymentVoucherForm"></fileList>
        <fileList  title="审核意见文件" :showUploadBtn="false" :delBtnFlag="false" :list="reviewFileRelationList" v-if="reviewFileRelationList.length>0"></fileList>
      </el-collapse>
    </div>


    <!-- 提交审核弹窗组件 -->
    <dialog-submit-audit ref="submit" @submit="submitConfirm" :above="above" @cancel="cancel" :reviewer="reviewer"></dialog-submit-audit>
    <div class="btn-group">
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;">
        <el-checkbox v-model="above"  style="color: white;" v-if="showJumpBtnFlag" >
          跳级审核
        </el-checkbox>
      </p>

      <el-button type="primary"  @click="commitBillStep" v-if="showInvoiceCompBtnFlag">提交发票</el-button>
      <el-button type="primary"  @click="confirmVoucherStep" v-if="showPaymentVoucherCompBtnFlag">确认到款</el-button>
      <el-button type="primary"  @click="auditingHTQuestionMenu('1')" v-if="showPassBtnFlag">通过</el-button>
      <el-button type="primary"  @click="auditingHTQuestionMenu('0')" v-if="showRejectBtnFlag">驳回</el-button>
      <el-button type="primary"  @click="submit" v-if="showSubmitBtnFlag">提交审核</el-button>
      <el-button type="primary"  @click="recallQuestionMenu" v-if="showRecallBtnFlag">撤回</el-button>
      <el-button type="primary"  @click="preview" v-if='showSubmitBtnFlag'>预览</el-button>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import dialogSubmitAudit from '../../../common/components/dialogSubmitAudit.vue'; /* 提审弹窗 */
  // <!-- 面板-复核只读 -->
  import collapseReviewRead from '../../../common/components/collapseReviewRead.vue';
  // <!-- 面板-复核可写 -->
  import collapseReviewWrite from '../../../common/components/collapseReviewWrite.vue';
  /* 面板--上传下载 */
  import collapseUpAndDownload from '../../../common/components/collapseUpAndDownload.vue';
  export default {
    props: ['transmittedData'],
    components: {
      'dialog-submit-audit': dialogSubmitAudit,
      'collapse-review-read': collapseReviewRead,
      'collapse-review-write': collapseReviewWrite,
      'fileList': collapseUpAndDownload,
    },
    data() {
      return {
        activeNames:'white1',// 默认展开的折叠面板
        above:false,
        errorTyleList:[],
        uploadOptions:{
          isInvoice:true
        },
        paymentVoucherUploadOptions:{
          isPayMoney:true
        },
        userType:null,
        object: { // 一级面板的对象
          title: '', // 一级面板的标题
          opinion: '', // 一级级面板的意见
          children: [{
            id: '', // 二级面板的被审批人标识
            name: '', // 二级面板的被审批人名字
            errorList: [ // 二级面板的被审批人的错误数组
            ]
          }]
        },
        delegateTaskId:'',
        updateDate:'',
        opinionList:[],
        invoiceFileList:[],
        paymentVoucherFileList:[],
        fileTypeList:[],
        requestFileList:[],
        reviewFileRelationList:[],
        submitNode:'',//提交节点
        reviewer: { // 审核人
          current: '',// 当前
          next: ''// 下一个
        },
        requestMenuId:'',

        paymentVoucherUploadBtnFlag:false,//打款凭证上传
        paymentVoucherDelBtnFlag:false,//打款凭证删除
        invoiceUploadBtnFlag:false,//发票上传
        invoiceDelBtnFlag:false,//发票删除
        showSubmitBtnFlag:false,//提交审核
        showPassBtnFlag:false,//通过
        showRejectBtnFlag:false,//驳回
        showRecallBtnFlag:false,//撤回
        showJumpBtnFlag:false,//跳级
        showInvoiceCompFlag:false,//发票
        showInvoiceCompBtnFlag:false,
        showPaymentVoucherCompFlag:false,//打款凭证
        showPaymentVoucherCompBtnFlag:false,//打款凭证
        questionCompFlag:false,//驳回意见信息
        requestMenuInfo:'',
        isMainHeadFlag:false,
      };
    },
    created(){
    },
    mounted() {
      let vm = this;
      vm.requestMenuId = vm.transmittedData.id
      vm.isMainHeadFlag = vm.transmittedData.isMainHeadFlag;
      vm.$nextTick(() => {
        vm.searchRequestMenuInfo();
        //查询请款单自定义请款单文件信息
        vm.searchReviewFileRelationList(vm.requestMenuId);
        vm.showUserType(vm.requestMenuId);
        vm.searchHTRequestMenuHisTaskInst(vm.requestMenuId);
      })
      vm.bus.$on('click', val => {
        console.log("right-val",val)
          vm.$nextTick(() => {
            if (val.treeNodeLevel=='5'&&val.objType == 'requestMent') {
              vm.isMainHeadFlag = val.isMainHeadFlag;
              vm.requestMenuId = vm.transmittedData.id
              vm.searchRequestMenuInfo();
              //查询请款单自定义请款单文件信息
              vm.searchReviewFileRelationList(vm.requestMenuId);
              vm.showUserType(vm.requestMenuId);
              vm.searchHTRequestMenuHisTaskInst(vm.requestMenuId);
            }
          })
      });
      vm.bus.$on('refreshThisComp', val => {
          vm.$nextTick(() => {
              vm.searchRequestMenuInfo();
              //查询请款单自定义请款单文件信息
              vm.searchReviewFileRelationList(vm.requestMenuId);
              vm.showUserType(vm.requestMenuId);
              vm.searchHTRequestMenuHisTaskInst(vm.requestMenuId);
          })
      });
      vm.bus.$on('changeReviewer', val => {
          vm.submitNode = val
          vm.reviewer = { // 审核人
            current: this.getStepWorker(val),// 当前
            next: this.getStepWorker(this.getNextStepNode(val))// 下一个
          }
      });
    },
    methods: {
      // 未提交审核时的预览
      preview() {
        this.trigger("requestPreview",this.above);
      },
      trigger(name,data) {
        let vm = this;
        // 触发事件
        vm.bus.$emit(name, data);
      },
      changeObject(obj){
        this.object = obj
      },
      //审核/驳回
      auditingHTQuestionMenu(isPass){
        let message = " 是否驳回?"
        if (isPass=='1') {
          message = " 是否通过?"
        }
        this.$confirm(message, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let paramObj = this.object
          paramObj.requestMenuId = this.requestMenuId;
          paramObj.isPass = isPass;
          let submitNode = this.submitNode
          if (this.above) {
            submitNode = this.getNextStepNode(submitNode)
          }
          paramObj.submitNode = submitNode;
          paramObj.businessId = this.requestMenuId;
          paramObj.delegateTaskId = this.delegateTaskId;
          paramObj.onlyOfficeEditKey = this.$parent.onlyOfficeEditKey;
          paramObj.reviewFileName = this.$parent.reviewFileName;
          paramObj.reviewFilePath = this.$parent.reviewFilePath;
          paramObj.userQuestionRecordRelationList = new Array();
          paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g,"delegateTaskKey"));
          if (isPass=='0') {
            paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g,"rejectReason"));

            for (var i = paramObj.children.length - 1; i >= 0; i--) {
              if (paramObj.children[i].errorList) {

                for (var j = paramObj.children[i].errorList.length - 1; j >= 0; j--) {
                  if (paramObj.children[i].errorList[j].questionNum!='0') {
                    paramObj.children[i].errorList[j].userId = paramObj.children[i].id
                    paramObj.children[i].errorList[j].userName = paramObj.children[i].name
                    paramObj.userQuestionRecordRelationList.push(paramObj.children[i].errorList[j])
                  }
                }
              }
            }
            delete paramObj.children;
          }
          let vm = this;
          this.apiAddress('/requestMenu/modify/auditingQuestionMenu', paramObj).then(res => {
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
              // vm.trigger("refreshThisComp");
                vm.$router.push({
                  path: '/page/work/default'
                });
            }
          }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          })
        }).catch(() => {
        });
      },
      submitConfirm(obj){

      },
      submit(){
        this.$confirm('此操作将提交审核, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
            this.trigger("updateQuestionMenuInfo",this.above);
        }).catch(() => {
        });
      },
      commitBillStep(){
        this.$confirm('此操作将提交发票, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let vm = this;
          if(!(vm.invoiceFileList&&vm.invoiceFileList.length>0)){
            vm.$message({
                message: "请上传发票文件",
                type: 'error',
                center: true
              });
            return
          }
          let formData = {
            requestMenuId:vm.requestMenuId,
            updateDate:vm.updateDate,
            delegateTaskId:vm.delegateTaskId,
          }
          this.apiAddress('/requestMenu/modify/commitBillStep', formData).then(res => {
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
              // vm.trigger("refreshThisComp");
              vm.$router.push({
                path: '/page/work/default'
              });
            }
          }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          })
        }).catch(() => {
        });
      },
      confirmVoucherStep(){
        this.$confirm('此操作将确认到款, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let vm = this;
          // 打款凭证 非必须传
          // if(!(vm.paymentVoucherFileList&&vm.paymentVoucherFileList.length>0)){
          //   vm.$message({
          //       message: "请上传打款凭证",
          //       type: 'error',
          //       center: true
          //     });
          //   return
          // }
          let formData = {
            requestMenuId:vm.requestMenuId,
            updateDate:vm.updateDate,
            delegateTaskId:vm.delegateTaskId,
          }
          this.apiAddress('/requestMenu/modify/confirmVoucherStep', formData).then(res => {
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
              // vm.trigger("refreshThisComp");
              vm.$router.push({
                path: '/page/work/default'
              });
            }
          }).catch(err => {
            vm.$message({
              message: err,
              type: 'error',
              center: true
            });
          })
        }).catch(() => {
        });
      },
      recallQuestionMenu(){
        let vm = this;
        let formData = {
          requestMenuId:vm.requestMenuId,
          delegateTaskId:vm.delegateTaskId
        }

        this.$confirm('是否撤回?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
            this.apiAddress('/requestMenu/modify/recallQuestionMenu', formData).then(res => {
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
                // vm.trigger("refreshThisComp");
                vm.$router.go(0)
              }
            }).catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            })

        }).catch(() => {
        });
      },
      //查询问题类型列表信息
      searchQuestionList(){
        let vm = this;
        let formData = {
          departmentId: vm.requestMenuInfo.departmentId
        }
        vm.apiAddress('/tenderTask/query/searchQuestionList', formData).then(res => {
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
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      deleteFileInfo(){

      },
      submitInvoiceForm(obj){
        obj.append("requestMenuId", this.requestMenuId);
        obj.append("updateDate", this.updateDate);
        obj.append("invoiceAmount", obj.get('money'));
        obj.delete('money')
        let vm = this;
        this.apiAddress('/requestMenu/modify/uploadBill', obj).then(res => {
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
            vm.searchRequestMenuInfo()
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      /*查询请款单信息*/
      searchRequestMenuInfo() {
        let vm = this;
        let formData = {
          requestMenuId: this.requestMenuId
        }
        vm.apiAddress('/requestMenu/query/searchRequestMenuInfo', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let requestMenuInfo = res.data.dataVO;
            vm.requestMenuInfo = res.data.dataVO;
            vm.delegateTaskId = requestMenuInfo.delegateTaskId;
            vm.updateDate = requestMenuInfo.updateDate;
            vm.searchQuestionList();
            vm.object = { // 一级面板的对象
              title: vm.getStepName(requestMenuInfo.delegateTaskKey), // 一级面板的标题
              opinion: '', // 一级级面板的意见
              children: [{
                id: requestMenuInfo.createUserId, // 二级面板的被审批人标识
                name: requestMenuInfo.createUserName, // 二级面板的被审批人名字
                errorList: [ // 二级面板的被审批人的错误数组
                ]
              }]
            }

            if (null!=requestMenuInfo.invoiceFileName && null!=requestMenuInfo.invoiceFilePath) {
              let invoiceFileObj = {
                materialName:requestMenuInfo.invoiceFileName,
                materialPath:requestMenuInfo.invoiceFilePath,
                userName:'金额(元)'+requestMenuInfo.invoiceAmount
              }
              vm.$set(vm.invoiceFileList,0,invoiceFileObj)
            }
            if (null!=requestMenuInfo.paymentFileName && null!=requestMenuInfo.paymentFilePath) {
              let paymentVoucherFileObj = {
                materialName:requestMenuInfo.paymentFileName,
                materialPath:requestMenuInfo.paymentFilePath,
                userName:'金额(元)'+requestMenuInfo.paymentAmount
              }
              vm.$set(vm.paymentVoucherFileList,0,paymentVoucherFileObj)
            }
            if (null!=requestMenuInfo.requestFileName && null!=requestMenuInfo.requestFilePath) {
              let requestFileObj = {
                materialName:requestMenuInfo.requestFileName,
                materialPath:requestMenuInfo.requestFilePath,
                isGeneratedFile:requestMenuInfo.requestType==1
              }
              // vm.requestFileList[0]=requestFileObj;
              vm.$set(vm.requestFileList,0,requestFileObj)
            }
            vm.showBtnFunc();
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      submitPaymentVoucherForm(obj){
        obj.append("requestMenuId", this.requestMenuId);
        obj.append("updateDate", this.updateDate);
        obj.append("paymentAmount", obj.get('payMoney'));
        obj.delete('payMoney')
        let vm = this;
        this.apiAddress('/requestMenu/modify/uploadPaymentVoucher', obj).then(res => {
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
            vm.searchRequestMenuInfo()
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },

      /*查询当前人类别(用以展示按钮)*/
      showUserType (requestMenuId){
        let vm = this;
        let formData = {
          requestMenuId: requestMenuId
        }
        vm.apiAddress('/requestMenu/query/showUserType', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.userType = res.data.typeFlag;
            vm.showBtnFunc();
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      showBtnFunc(){
        let vm = this;
        vm.paymentVoucherUploadBtnFlag=false;//打款凭证上传
        vm.paymentVoucherDelBtnFlag=false;//打款凭证删除
        vm.invoiceUploadBtnFlag=false;//发票上传
        vm.invoiceDelBtnFlag=false;//发票删除
        vm.showSubmitBtnFlag=false;//提交审核
        vm.showPassBtnFlag=false;//通过
        vm.showRejectBtnFlag=false;//驳回
        vm.showRecallBtnFlag=false;//撤回
        vm.showJumpBtnFlag=false;//跳级
        vm.showInvoiceCompFlag=false;//发票
        vm.showInvoiceCompBtnFlag=false;//发票
        vm.showPaymentVoucherCompFlag=false;//打款凭证
        vm.showPaymentVoucherCompBtnFlag=false;//打款凭证
        vm.questionCompFlag=false;//驳回意见信息
        let userType = vm.userType;
        let requestMenuInfo = vm.requestMenuInfo;
        switch (userType) {
          case "1":
            vm.showSubmitBtnFlag=true;//提交审核
            vm.showJumpBtnFlag=true && requestMenuInfo.approveTime==null;//跳级
            break;
          case "20":
            vm.showRecallBtnFlag=true;//撤回
            break;
          case "2":
            vm.showJumpBtnFlag=true //跳级
          case "3":
          case "4":
            vm.showPassBtnFlag=true;//通过
            vm.showRejectBtnFlag=true;//驳回
            vm.questionCompFlag=true;//驳回意见信息
            break;
          case "5":
            vm.showInvoiceCompFlag=true;
            vm.showInvoiceCompBtnFlag=true;
            vm.invoiceUploadBtnFlag=true;//发票上传
            vm.invoiceDelBtnFlag=true;//发票删除
            break;
          case "6":
            vm.showInvoiceCompFlag=true;
            vm.showPaymentVoucherCompFlag=true;
            vm.showPaymentVoucherCompBtnFlag=true;
            vm.paymentVoucherUploadBtnFlag=true;//打款凭证上传
            vm.paymentVoucherDelBtnFlag=true;//打款凭证删除
            break;
          default:
            break;
        }
        vm.$forceUpdate()
      },

      cancel(){// 跳级取消
          let vm = this;
          vm.above = false;
      },
      /*查询审核意见文件列表*/
      searchReviewFileRelationList(requestMenuId) {
        let vm = this;
        let formData = {
          businessId:requestMenuId
        }
        vm.apiAddress('/commonModule/query/searchReviewFileRelationList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let dataList = res.data.dataList;
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileId/g, "materialId"));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFileName/g, "materialName"));
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFilePath/g, "materialPath"));
            }
            vm.reviewFileRelationList = dataList
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
      },
      /*查询审核意见信息*/
      //查询审核意见列表
      searchHTRequestMenuHisTaskInst(requestMenuId){
        let vm = this;
        let formData = {
          requestMenuId: requestMenuId,
        }
        //查询审核意见列表
        vm.apiAddress('/requestMenu/query/searchHTRequestMenuHisTaskInst', formData).then(
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
              for (var i = 0 ; i < dataList.length ; i++) {
                dataList[i].title = vm.getStepName(dataList[i].taskKey)
                //将属性名httenderTaskHisTaskInstVOList转换成属性名children
                dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/htRequestMenuHisTaskInstList/g,"children"));
                let children = dataList[i].children
                for (var j = 0 ; j < children.length ; j++) {
                  //将属性名approveTime转换成属性名time
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g,"time"));
                  //将属性名approveUserName转换成属性名name
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g,"name"));
                  //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,"persions"));
                  //将属性名rejectReason转换成属性名opinion
                  children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g,"opinion"));
                  let persions = vm.groupBy(children[j].persions,function(item){
                    return[item.userId]
                  })
                  for (var k = 0 ; k < persions.length ; k++) {
                    persions[k].name = persions[k].dataList[0].userName
                    //将属性名dataList转换成属性名errorList
                    persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g,"errorList"));
                    let errorList = persions[k].errorList;
                    for (var l = errorList.length - 1; l >= 0; l--) {
                    //将属性名questionName转换成属性名type
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g,"type"));
                    //将属性名questionNum转换成属性名num
                      errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g,"num"));
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
  .foundItem__requestPayout {
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
