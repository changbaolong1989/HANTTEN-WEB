<template>
  <div class="foundItem__requestPayout-right">
    <el-collapse class="given-container">
      <fileList title="请款单文件" name='请款单文件' :showUploadBtn="false" :delBtnFlag="false" :files="requestFileList" v-if="requestFileList.length"></fileList>
      <template v-for="(item,i) in opinionList">
        <!-- 只组件 -->
        <collapse-review-read :name="'read'+i" :object="item" :key="i"></collapse-review-read>
      </template>
      <!-- 面板-复核可写-->
      <collapse-review-write v-if="questionCompFlag" :object="object" :errorTyleList="errorTyleList" @selecError="selecError"></collapse-review-write>
      <!-- 发票 -->
      <fileList
      title="发票"
      name='发票'
      :showUploadBtn="invoiceUploadBtnFlag"
      :delBtnFlag="invoiceDelBtnFlag"
      :files="invoiceFileList"
      v-if="showInvoiceCompFlag||invoiceFileList.length>0"
      @uploadFile="uploadFile('invoice')"></fileList>
      <!-- 打款凭证 -->
      <fileList
      title="打款凭证"
      name='打款凭证'
      :showUploadBtn="paymentVoucherUploadBtnFlag"
      :delBtnFlag="paymentVoucherDelBtnFlag"
      :files="paymentVoucherFileList"
      v-if="showPaymentVoucherCompFlag||paymentVoucherFileList.length>0"
      @uploadFile="uploadFile('paymentVoucher')"
      @del="delFileMakeMoney"></fileList>
    </el-collapse>
    <!-- 可写组件 - 对话框 -->
    <collapse-review-write-dialog ref="collapseReviewWrite_dialog" :errorTyleList="errorTyleList" :obj="selectErrorObj" @submit="selectErrorSubmit"></collapse-review-write-dialog>
    <!-- 上传下载组件 - 对话框 -->
    <dialog-upload
    ref="invoice"
    :uploadOptions="uploadOptions"
    :files="invoiceFileList"
    @submit="submitInvoiceForm"></dialog-upload>
    <!-- 上传下载组件 - 对话框 -打款凭证 -->
    <dialog-upload
    ref="paymentVoucher"
    :uploadOptions="paymentVoucherUploadOptions"
    :files="paymentVoucherFileList"
    @submit="submitPaymentVoucherForm"></dialog-upload>
    <div class="btn-group primary">
      <p v-if="showJumpBtnFlag">
        <mt-switch v-model="above" @change="changeAbove">
          是否跳级审核
        </mt-switch>
      </p>
      <button class="linear" @click="commitBillStep" v-if="showInvoiceCompBtnFlag">提交发票</button>
      <button class="linear" @click="confirmVoucherStep" v-if="showPaymentVoucherCompBtnFlag">确认到款</button>
      <button class="linear" @click="auditingHTQuestionMenu('1')" v-if="showPassBtnFlag">通过</button>
      <button class="linear" @click="auditingHTQuestionMenu('0')" v-if="showRejectBtnFlag">驳回</button>
      <button class="linear" @click="submit" v-if="showSubmitBtnFlag">提交审核</button>
      <button class="linear" @click="recallQuestionMenu" v-if="showRecallBtnFlag">撤回</button>
      <button class="linear" @click="preview" v-if="showSubmitBtnFlag">预览</button>
    </div>
  </div>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
  // <!-- 面板-复核只读 -->
  import collapseReviewRead from '../../../../more/common/components/collapseReviewRead.vue';
  // <!-- 面板-复核可写 -->
  import collapseReviewWrite from '../../../../more/common/components/collapseReviewWrite.vue';
  // <!-- 面板-复核可写__对话框 -->
  import collapseReviewWrite_dialog from '../../../../more/common/components/collapseReviewWrite_dialog.vue';
  /* 面板-上传下载 */
  import collapseUpAndDownload from '../../../../more/common/components/collapseUpAndDownload.vue';
  /* 上传下载组件 - 对话框 */
  import dialogUpload from '../../../../more/common/components/dialogUpload.vue';

  export default {
    components: {
      'collapse-review-read': collapseReviewRead,// 面板-复核只读
      'collapse-review-write': collapseReviewWrite,// 面板-复核可写
      'collapse-review-write-dialog':collapseReviewWrite_dialog,// 面板-复核可写__对话框
      'fileList': collapseUpAndDownload,// 面板-上传下载
      'dialog-upload':dialogUpload// 面板-上传下载__对话框
    },
    props: ['transmitData'],
    data() {
      return {
        above: false, // 是否跳级
        currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy), //传输数据
        requestMenuId: '', // 请求菜单标识
        requestMenuInfo: {}, // 请求菜单信息
        errorTyleList: [], // 错误类型数据
        invoiceFileList: [], // 发票文件列表
        paymentVoucherFileList: [], // 打款文件列表
        requestFileList: [], // 请求文件列表
        reviewFileRelationList: [], // 查看文件关系列表
        opinionList: [], // 意见清单
        selectErrorObj:null,// 选择错误的对象
        object: { // 一级面板的对象
          title: '', // 一级面板的标题
          opinion: '', // 一级级面板的意见
          children: [{
            id: '', // 二级面板的被审批人标识
            name: '', // 二级面板的被审批人名字
            errorList: [ // 二级面板的被审批人的错误数组
            ]
          }]
        }, // 一级面板的对象
        userType: '', // 用户类型
        reviewer:{// 审核人
          current: '',// 当前
          next: ''// 下一个
        },
        paymentVoucherUploadBtnFlag: false, //打款凭证上传
        paymentVoucherDelBtnFlag: false, //打款凭证删除
        invoiceUploadBtnFlag: false, //发票上传
        invoiceDelBtnFlag: false, //发票删除
        showSubmitBtnFlag: false, //提交审核
        showPassBtnFlag: false, //通过
        showRejectBtnFlag: false, //驳回
        showRecallBtnFlag: false, //撤回
        showJumpBtnFlag: false, //跳级
        showInvoiceCompFlag: false, //发票
        showInvoiceCompBtnFlag: false, // 发表按钮
        showPaymentVoucherCompFlag: false, //打款凭证
        showPaymentVoucherCompBtnFlag: false, //打款凭证
        questionCompFlag: false, //驳回意见信息
        uploadOptions:{// 上传下载对话框组件的参数(发票)
          isInvoice:true,// 是否是发票
          limit:1// 文件数量
        },
        paymentVoucherUploadOptions:{// 上传下载对话框组件的参数(打款凭证)
          isPayMoney:true,// 是否是打款凭证
           limit:1// 文件数量
        },
        updateDate: "",// 更新时间(用于后端判断数据是否修改)
        delegateTaskId:""// 流程标识
      }
    },
    watch: {
      transmitData: {
        handler() {
          let vm = this;
          if (!vm.transmitData) return;
          vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
          vm.requestMenuId = vm.currentTransmitData.data.id
        },
        immediate: true
      }
    },
    mounted() {
      let vm = this;
      vm.$nextTick(() => {
        vm.searchRequestMenuInfo();
        vm.searchReviewFileRelationList();
        vm.showUserType();
        vm.searchHTRequestMenuHisTaskInst();
      })
      vm.bus.$on('changeReviewer', val => {
        console.log("val", val)
        vm.submitNode = val
        vm.reviewer = { // 审核人
          current: this.getStepWorker(val), // 当前
          next: this.getStepWorker(this.getNextStepNode(val)) // 下一个
        }
      });
      vm.bus.$on('updateDateRequestPayout',function(val){
        vm.updateDate = val.updateDate;
        vm.delegateTaskId = val.delegateTaskId;
      })
    },
    methods: {
      // 未提交审核时的预览
      preview() {
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          vm.bus.$emit('requestPreview', vm.above);
        }).catch(err => {});;
      },
      /**
       * @method 删除打款凭证文件
       */
      delFileMakeMoney(){
        console.log('删除打款凭证文件');
      },
      /**
       * @method 跳级审核提示
       */
      changeAbove(){
        let vm = this;
        if(vm.above){
          vm.MessageBox.confirm('', {
            message: '审核本来要提交给' + vm.reviewer.current + ',现在将要跳级提交给' + vm.reviewer.next,
            title: '提示',
            showCancelButton: true
          }).then(action => {
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
      },
      /**
       * @methos 通过/驳回
       * @param {Object} isPass
       */
      auditingHTQuestionMenu(isPass){
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          let paramObj = vm.object
          paramObj.updateDate = vm.updateDate;
          paramObj.delegateTaskId = vm.delegateTaskId;
          paramObj.requestMenuId = vm.requestMenuId;
          paramObj.isPass = isPass;
          let submitNode = vm.submitNode
          if (vm.above) {
            submitNode = vm.getNextStepNode(submitNode)
          }
          paramObj.submitNode = submitNode;
          paramObj.businessId = vm.requestMenuId;
          paramObj.delegateTaskId = vm.requestMenuInfo.delegateTaskId;
          paramObj.onlyOfficeEditKey = vm.requestMenuInfo.onlyOfficeEditKey;
          paramObj.reviewFileName = vm.requestMenuInfo.reviewFileName;
          paramObj.reviewFilePath = vm.requestMenuInfo.reviewFilePath;
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
          vm.requestPost('/requestMenu/modify/auditingQuestionMenu', paramObj).then(res => {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
            // if (res.data.status != 'failure')vm.$router.go(0);
            if (res.data.status != 'failure')vm.$router.push('/app/workbench');
          }).catch(err => {
            Toast({
              message: err,
              position: 'middle',
              duration: 5000
            });
          })
        }).catch(err => {});;
      },
      /**
       * @method 查询请款单信息
       */
      searchRequestMenuInfo() {
        let vm = this;
        let formData = {
          requestMenuId: this.requestMenuId
        }
        vm.requestPost('/requestMenu/query/searchRequestMenuInfo', formData).then(res => {
          if (res.data.status == 'failure') {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
          } else {
            let requestMenuInfo = vm.requestMenuInfo = res.data.dataVO;
            vm.updateDate = requestMenuInfo.updateDate;
            vm.delegateTaskId = requestMenuInfo.delegateTaskId;
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
            // 发票文件
            if (null != requestMenuInfo.invoiceFileName && null != requestMenuInfo.invoiceFilePath) {
              let invoiceFileObj = {
                materialName: requestMenuInfo.invoiceFileName,
                materialPath: requestMenuInfo.invoiceFilePath,
                userName: '金额(元)' + requestMenuInfo.invoiceAmount
              }
              vm.$set(vm.invoiceFileList, 0, invoiceFileObj)
            }
            // 打款文件
            if (null != requestMenuInfo.paymentFileName && null != requestMenuInfo.paymentFilePath) {
              let paymentVoucherFileObj = {
                materialName: requestMenuInfo.paymentFileName,
                materialPath: requestMenuInfo.paymentFilePath,
                userName: '金额(元)' + requestMenuInfo.paymentAmount
              }
              vm.$set(vm.paymentVoucherFileList, 0, paymentVoucherFileObj);
            }
            // 请求文件
            if (null != requestMenuInfo.requestFileName && null != requestMenuInfo.requestFilePath) {
              let requestFileObj = {
                materialName: requestMenuInfo.requestFileName,
                materialPath: requestMenuInfo.requestFilePath,
                isGeneratedFile: requestMenuInfo.requestType == 1
              }
              vm.$set(vm.requestFileList, 0, requestFileObj)
            }
            vm.showBtnFunc();
          }
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        })
      },
      /**
       * @method 查询问题类型列表信息
       */
      searchQuestionList() {
        let vm = this;
        let formData = {
          departmentId: vm.requestMenuInfo.departmentId
        }
        vm.requestPost('/tenderTask/query/searchQuestionList', formData).then(res => {
          if (res.data.status == 'failure') {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
          } else {
            let questionList = res.data.dataList;
            for (var i = questionList.length - 1; i >= 0; i--) {
              questionList[i].questionNum = 0;
            }
            vm.errorTyleList = questionList;
          }
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        })
      },
      /**
       * @method 查询当前人类别(用以展示按钮)
       */
      showUserType() {
        let vm = this;
        let formData = {
          requestMenuId: vm.requestMenuId
        }
        vm.requestPost('/requestMenu/query/showUserType', formData).then(res => {
          if (res.data.status == 'failure') {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
          } else {
            vm.userType = res.data.typeFlag;
            vm.showBtnFunc();
          }
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        })
      },
      /**
       * @method 通过条件判断显示相应的按钮
       */
      showBtnFunc() {
        let vm = this;
        vm.paymentVoucherUploadBtnFlag = false; //打款凭证上传
        vm.paymentVoucherDelBtnFlag = false; //打款凭证删除
        vm.invoiceUploadBtnFlag = false; //发票上传
        vm.invoiceDelBtnFlag = false; //发票删除
        vm.showSubmitBtnFlag = false; //提交审核
        vm.showPassBtnFlag = false; //通过
        vm.showRejectBtnFlag = false; //驳回
        vm.showRecallBtnFlag = false; //撤回
        vm.showJumpBtnFlag = false; //跳级
        vm.showInvoiceCompFlag = false; //发票
        vm.showInvoiceCompBtnFlag = false; //发票
        vm.showPaymentVoucherCompFlag = false; //打款凭证
        vm.showPaymentVoucherCompBtnFlag = false; //打款凭证
        vm.questionCompFlag = false; //驳回意见信息
        let userType = vm.userType;
        let requestMenuInfo = vm.requestMenuInfo;
        switch (userType) {
          case "1":
            vm.showSubmitBtnFlag = true; //提交审核
            vm.showJumpBtnFlag = true && requestMenuInfo.approveTime == null; //跳级
            break;
          case "20":
            vm.showRecallBtnFlag = true; //撤回
            break;
          case "2":
          case "3":
          case "4":
            vm.showPassBtnFlag = true; //通过
            vm.showRejectBtnFlag = true; //驳回
            vm.showJumpBtnFlag = true && userType == "2"; //跳级
            vm.questionCompFlag = true; //驳回意见信息
            break;
          case "5":
            vm.showInvoiceCompFlag = true;
            vm.showInvoiceCompBtnFlag = true;
            vm.invoiceUploadBtnFlag = true; //发票上传
            vm.invoiceDelBtnFlag = false; //发票删除
            break;
          case "6":
            vm.showInvoiceCompFlag = true;
            vm.showPaymentVoucherCompFlag = true;
            vm.showPaymentVoucherCompBtnFlag = true;
            vm.paymentVoucherUploadBtnFlag = true; //打款凭证上传
            vm.paymentVoucherDelBtnFlag = true; //打款凭证删除
            break;
          default:
            break;
        }
        vm.$forceUpdate()// 迫使 Vue 实例重新渲染
      },
      /**
       * @method 查询审核意见文件列表
       */
      searchReviewFileRelationList() {
        let vm = this;
        let formData = {
          businessId: vm.requestMenuId
        }
        vm.requestPost('/commonModule/query/searchReviewFileRelationList', formData).then(res => {
          if (res.data.status == 'failure') {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
          } else {
            let dataList = res.data.dataList;
            // 替换对象属性
            for (var i = dataList.length - 1; i >= 0; i--) {
              dataList[i].materialId = dataList[i].fileId;
              dataList[i].materialName = dataList[i].reviewFileName;
              dataList[i].materialPath = dataList[i].reviewFilePath;
              // 删除重复的属性
              delete dataList[i].fileId
              delete dataList[i].reviewFileName
              delete dataList[i].reviewFilePath
            }
            vm.reviewFileRelationList = dataList;
          }
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        });
      },
      /**
       * @method 查询审核意见信息
       */
      searchHTRequestMenuHisTaskInst() {
        let vm = this;
        let formData = {
          requestMenuId: vm.requestMenuId,
        }
        //查询审核意见列表
        vm.requestPost('/requestMenu/query/searchHTRequestMenuHisTaskInst', formData).then(
          res => {
            if (res.data.status == 'failure') {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
            } else {
              /*------------------------------格式转换START---------------------------------*/
              let dataList = res.data.dataList;
              //for循环遍历更改属性名
              for (let i = 0; i < dataList.length; i++) {
                dataList[i].title = vm.getStepName(dataList[i].taskKey);
                //将属性名httenderTaskHisTaskInstVOList转换成属性名children
                dataList[i].children = dataList[i].htRequestMenuHisTaskInstList;
                delete dataList[i].htRequestMenuHisTaskInstList;
                let children = dataList[i].children;
                for (let j = 0; j < children.length; j++) {
                  //将属性名approveTime转换成属性名time
                  children[j].time = children[j].approveTime;
                  delete children[j].approveTime;
                  //将属性名approveUserName转换成属性名name
                  children[j].name = children[j].approveUserName;
                  delete children[j].approveUserName;
                  //将属性名htUserQuestionRecordRelationVOList转换成属性名persions
                  children[j].persions = children[j].htUserQuestionRecordRelationVOList;
                  delete children[j].htUserQuestionRecordRelationVOList;
                  //将属性名rejectReason转换成属性名opinion
                  children[j].opinion = children[j].rejectReason;
                  delete children[j].rejectReason;
                  let persions = vm.groupBy(children[j].persions, function(item) {
                    return [item.userId]
                  })
                  for (let k = 0; k < persions.length; k++) {
                    persions[k].name = persions[k].dataList[0].userName
                    //将属性名dataList转换成属性名errorList
                    persions[k].errorList = persions[k].dataList;
                    delete persions[k].dataList;
                    let errorList = persions[k].errorList;
                    for (let l = errorList.length - 1; l >= 0; l--) {
                      //将属性名questionName转换成属性名type
                      errorList[l].type = errorList[l].questionName;
                      delete errorList[l].questionName;
                      //将属性名questionNum转换成属性名num
                      errorList[l].num = errorList[l].questionNum;
                      delete errorList[l].questionNum;
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
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        });
      },
      /**
       * @method 提交发票
       */
      commitBillStep() {
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          let formData = {
            requestMenuId: vm.requestMenuId,
            updateDate:vm.updateDate,
            delegateTaskId:vm.delegateTaskId
          }
          vm.requestPost('/requestMenu/modify/commitBillStep', formData).then(res => {
            if (res.data.status == 'failure') {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
            } else {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
              // vm.$router.go(0)
              vm.$router.push('/app/workbench');
            }
          }).catch(err => {
            Toast({
              message: err,
              position: 'middle',
              duration: 5000
            });
          })
        }).catch(err => {});;
      },
      /**
       * @method 提交审核
       */
      submit() {
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          vm.bus.$emit("updateQuestionMenuInfo", vm.above);
        }).catch(err => {});;
      },
      /**
       * @method 撤回
       */
      recallQuestionMenu(){
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          let formData = {
            requestMenuId:vm.requestMenuId,
            updateDate:vm.updateDate,
            delegateTaskId:vm.delegateTaskId
          }
          vm.requestPost('/requestMenu/modify/recallQuestionMenu', formData).then(res => {
            if (res.data.status == 'failure') {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
            } else {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
              vm.$router.go(0)
            }
          }).catch(err => {
            Toast({
              message: err,
              position: 'middle',
              duration: 5000
            });
          })
        }).catch(err => {});;
      },
      /* 可写组件--相关 */
      selecError(data){// 选择错误
        let vm = this;
        // 先赋值再显示对话框
         vm.selectErrorObj = data;
        vm.$refs['collapseReviewWrite_dialog'].show = true;
      },
      /**
       * method 选择错误回调
       * @param {Object} data
       */
      selectErrorSubmit(data){
        let vm = this;
         vm.selectErrorObj.errorList = data;
        // 选择错误的表单提交
        console.log('selectErrorSubmit',data);
      },
      /**
       * @method 上传发票提交
       * @param {Object} data
       */
      submitInvoiceForm(data){
        let vm = this;
        let obj = data.form;
        obj.append("updateDate", vm.updateDate);
        obj.append("delegateTaskId", vm.delegateTaskId);
        obj.append("requestMenuId", this.requestMenuId);
        obj.append("invoiceAmount", obj.get('money'));
        obj.delete('money');
        obj.delete('payMoney');
        this.requestPost('/requestMenu/modify/uploadBill', obj).then(res => {
          Toast({
            message: res.data.message,
            position: 'middle',
            duration: 5000
          });
          if (res.data.status != 'failure')vm.searchRequestMenuInfo();
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        });

      },
      /* 上传组件相关 */
      uploadFile(name){// 上传文件对话框显示
         let vm = this;
        this.$nextTick(function () {
          vm.$refs[name].show = true;
        })
      },
      /**
       * @method 确认到款
       */
      confirmVoucherStep(){
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          // 打款凭证 非必须传
          // if(!(vm.paymentVoucherFileList&&vm.paymentVoucherFileList.length>0)){
          //     Toast({
          //       message: '请上传打款凭证',
          //       position: 'middle',
          //       duration: 5000
          //     });
          //   return
          // }
          let formData = {
            requestMenuId:vm.requestMenuId,
            updateDate:vm.updateDate,
            delegateTaskId:vm.delegateTaskId
          }
          vm.requestPost('/requestMenu/modify/confirmVoucherStep', formData).then(res => {
            if (res.data.status == 'failure') {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
            } else {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
              // vm.$router.go(0)
              vm.$router.push('/app/workbench');
            }
          }).catch(err => {
            Toast({
              message: err,
              position: 'middle',
              duration: 5000
            });
          })
        }).catch(err => {});;
      },
      /**
       * @method 打款凭证
       * @param {Object} data
       */
      submitPaymentVoucherForm(data){
        let vm = this;
        let obj = data.form;
        obj.append("updateDate", vm.updateDate);
        obj.append("delegateTaskId", vm.delegateTaskId);
        obj.append("requestMenuId", vm.requestMenuId);
        obj.append("paymentAmount", obj.get('payMoney'));
        obj.delete('money');
        obj.delete('payMoney');
        this.requestPost('/requestMenu/modify/uploadPaymentVoucher', obj).then(res => {
          if (res.data.status == 'failure') {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
          } else {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
            vm.searchRequestMenuInfo()
          }
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        })
      },
    }
  }
</script>
<style lang="scss">
.foundItem__requestPayout-right {
  .given-container{
    border: none;
  }
}
</style>
<style lang="scss" scoped>
  * {
    box-sizing: border-box;
  }

  .foundItem__requestPayout-right {
    width: 100%;
    margin-bottom: 20px;
    border-radius: 0 0 10px 10px;
    padding: 20px;
    background-color: white;
  }
</style>
