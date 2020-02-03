<template>
  <!-- 请款单 -->
  <div class="found-item__request-payout">
    <form class="projectForm" v-if="!(correctExamineFlag && showOnlyOfficeCompFlag)">
      <fieldset :disabled="disabledFlag">
      <div class="form-item">
        <label>档案编号</label>
        <input placeholder="请输入档案编号" v-model="currentForm.requestMenuNum" v-validate="'required'" data-vv-name="档案编号">
      </div>
      <div class="form-item">
        <m-select v-model="currentForm.seniorManagerId" :data="{title: '所属高管',options: this.seniorManagerId}" unique="userId"
          display="userName" v-validate="'required'" data-vv-name="所属高管">
        </m-select>
      </div>
      <div class="form-item" @click="openPicker('picker')">
        <mt-cell title="工程节点请款时间">
          <span>{{ currentForm.endDate ||'请输入' }}</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>

      <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('endDate')"></mt-datetime-picker>

      <input type="hidden" v-model="currentForm.endDate" v-validate="'required'" data-vv-name="工程节点请款时间" />

      <div class="form-item">
        <label>请款金额(元)</label>
        <input placeholder="请输入请款金额(元)" v-model="currentForm.requestAmount" v-validate="'required'" data-vv-name="请款金额(元)">
      </div>
      <div class="form-item" v-if="showPredictTotalFlag">
        <label>预计合同总金额(元)</label>
        <input placeholder="请输入预计合同总金额(元)" v-model="currentForm.predictTotal" v-validate="'required'" data-vv-name="预计合同总金额(元)">
      </div>
       </fieldset>

      <div class="form-item" style="position: relative;">
        <label>上传发票申请单附件</label>
        <input type="file" ref="requestNoteFile" placeholder="请选择上传发票申请单附件" hidden="hidden" @change="requestNoteFileChange">
        <input placeholder="请选择上传发票申请单附件"
        v-model="currentForm.requestNoteFileName"
        v-validate="'required'" data-vv-name="上传发票申请单附件"
        readonly="readonly"
        :disabled="disabledFlag"
        @click="requestNoteFileClick">
        <span style="position: absolute;right: 20px;bottom: 14px;background-color: #f1f5f8;">
          <span style="color: #06a1ee;" @click="requestNoteFileDownload">下载</span>
          &nbsp;/&nbsp;
          <span style="color: #06a1ee;" @click="requestNoteFileDownloadTemplate">下载模板</span>
        </span>
      </div>

       <fieldset :disabled="disabledFlag">
      <div class="form-item">
        <m-select v-model="currentForm.requestType" :data="{title: '合同类型',options: this.requestType}" v-validate="'required'"
          data-vv-name="合同类型">
        </m-select>
      </div>
      <div class="form-item file" v-if="'0'==currentForm.requestType">
        <mt-cell title="上传请款单附件">
          <label for="file">{{ currentForm.resourceFiles[0]?currentForm.resourceFiles[0].name:'上传' }}</label>
          <input type="file" id="file" @change="onUpload" />
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
    </fieldset>
    </form>
    <!-- 请款单提交审核 -->
    <payroll-requisition v-if="!(correctExamineFlag && showOnlyOfficeCompFlag)&&'1'==currentForm.requestType" ref="payrollRequisition" :form="formPayroll"></payroll-requisition>
    <div v-if="correctExamineFlag && showOnlyOfficeCompFlag" :style="{height: officeHeight}">
      <office :file="reviewFile" v-if="hideOffice" @supportOffice="supportOffice"></office>
    </div>
    <!-- 文件预览 -->
    <dialog-browse-file :file="file" ref="dialogBrowseFile" type="request" :formData="watchData"></dialog-browse-file>
	<!-- 请款单提交审核右侧组件 -->
	<right :transmitData="transmitData"></right>
  </div>
</template>


<script>
  import {
    Toast
  } from 'mint-ui';
  import payrollRequisition from '../../common/preparation/resultsFile/template/payrollRequisition.vue'; /* 请款单提交审核表单*/
  import office from '../../../common/vue/office.vue';// 合同表单的默认数据
  import dialogBrowseFile from '../../../../more/common/components/dialogBrowseFile.vue';// 预览文件插件
  import right from './right.vue';// 预览文件插件
  export default {
    components: {
      'payroll-requisition': payrollRequisition, // 请款单提交审核
      office,// onlyOffice组件
      'dialog-browse-file':dialogBrowseFile,
      right// 右侧操作组件
    },
    props: ['transmitData'],
    data() {
      return {
        disabledFlag:false,// 禁用表单(提交审核的人不可以编辑请款单的表单)
        officeHeight:'700px',// onlyOffice文件的高度
        showOnlyOfficeCompFlag:false,// 显示onlyOffice容器?
        reviewFile:null,// onlyOffice文件信息
        hideOffice: true,// 隐藏onlyOffice
        userType: 0,// 用户类型
        currentForm: { // 当前表单
          requestMenuNum: '', // 档案编号
          seniorManagerId: '', // 所属高管,
          endDate: '', // 工程节点请款时间
          requestAmount: '', // 请款金额(元)
          predictTotal: '', // 预计合同总金额(元)
          requestType: '', // 合同类型
          resourceFiles: [] ,// 源文件数据
          requestNoteFileName:'',// 发票申请单附件名称
          requestNoteFilePath:''// 发票申请单附件下载路径
        },
        requestNoteFileData:null,// 发票申请单附件
        formPayroll: {}, //请款单模板表单数据
        correctExamineFlag: false, //正确检查标志
        showPredictTotalFlag: true, //是否显示预计合同总金额(元)
        requestMenuId: '', // 请求菜单标识
        form: {}, // 表单
        seniorManagerId: [], // 所属高管的下拉选项数组
        requestType: [{ //合同类型对应的下拉数据
          id: '0',
          display: '自定义请款单'
        }, {
          id: '1',
          display: '模板请款单'
        }],
        pickerVisible: this.formatting(new Date()),
        updateDate: "",// 更新时间(用于后端判断数据是否修改)
        delegateTaskId:"",// 流程标识
        file:'',// 待浏览文件
        watchData: new FormData(),
      };
    },
    watch: {
      correctExamineFlag: {
        handler(){
          if (this.correctExamineFlag){
            this.searchRequestMenuInfo(); // 查询请款单信息
          }else{
            this.showOnlyOfficeCompFlag = this.correctExamineFlag;
          }
        },
        immediate: true
      },
      transmitData:{
        handler(){
          let vm = this;
          vm.$nextTick(() => {
            vm.requestMenuId = vm.transmitData.data.id;
            vm.refreshThisComp();
          });
        },
        immediate: true
      }
    },
    mounted() {
      let vm = this;
      // 请款单
      //绑定全局事件globalEvent
      vm.bus.$on('updateQuestionMenuInfo', val => {
        vm.$nextTick(function() {
          vm.updateQuestionMenuInfo(val);
        });
      });
      vm.bus.$off('requestPreview');
      //绑定全局事件globalEvent
      vm.bus.$on('requestPreview', val => {
        vm.nextSubmit(val,true)
      });
    },
    methods: {
      /**
       * 请款单预览
       * @param {Object} commitForm
       */
      requestPreview(commitForm) {
        let vm = this;
        vm.watchData = vm.commitForm;
        vm.file = {
          "materialPath":true,
          "isGeneratedFile":true,
          "noSubmitPreview":"1"
        }
        vm.$refs.dialogBrowseFile.show = true;
      },
      /**
       * @event 上传发票申请单附件改变
       */
      requestNoteFileChange(el){
        let vm = this;
        vm.requestNoteFileData = el.target.files[0];
        if(!vm.currentForm.requestNoteFileName){
          vm.$set(vm.currentForm,'requestNoteFileName',vm.requestNoteFileData.name)
        }else{
          vm.currentForm.requestNoteFileName = vm.requestNoteFileData.name;
        }
      },
      /**
       * @event 上传发票申请单附件
       */
      requestNoteFileClick(){
        let vm = this;
        vm.$nextTick(() => {
          vm.$refs['requestNoteFile'].click();
        });
      },
      /**
       * @event 下载发票申请单附件
       */
      requestNoteFileDownload(){
        let vm = this;
        vm.downloadFile(vm.currentForm.requestNoteFilePath, vm.currentForm.requestNoteFileName, false);
      },
      /**
       * @event 下载发票申请单附件模板
       */
      requestNoteFileDownloadTemplate(){
        const vm = this;
        // 导出列表数据
        const elemIF = document.createElement('iframe');
        elemIF.src = "/requestMenu/download/downloadDemoFile?realFileName=开票信息附件.xlsx";
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      },
      /**
       * @method 查看是否支持onlyOffice信息显示
       */
      supportOffice(val){
        let vm = this;
        vm.officeHeight = val?'700px':'30px';
      },
      /**
       * @method 刷新当前组件
       */
      refreshThisComp() {
        let vm = this;
        // 查询请款单信息
        vm.searchRequestMenuInfo();
        // 查询高管部门下角色
        vm.findSeniorManageruserList();
        // 查询当前人类别(用以展示按钮)
        vm.showUserType(vm.requestMenuId);
      },
      /**
       * @method 显示按钮
       */
      showBtnFunc() {
        let vm = this;
        vm.showPredictTotalFlag = false;
        vm.correctExamineFlag = false;
        vm.disabledFlag = false;
        vm.$nextTick(() => {
          vm.showPredictTotalFlag = true && vm.currentForm.verifyState == null;
          vm.disabledFlag = vm.userType != 1;
        });
        if (vm.userType == 2 || vm.userType == 3 || vm.userType == 4) {
          vm.correctExamineFlag = true;
        }
      },
      /**
       * @method 查询当前人类别(用以展示按钮)
       */
      showUserType() {
        let vm = this;
        let formData = {
          requestMenuId: vm.requestMenuId
        };
        vm.requestPost('/requestMenu/query/showUserType', formData)
          .then(res => {
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
          })
          .catch(err => {
            Toast({
              message: err,
              position: 'middle',
              duration: 5000
            });
          });
      },
      /**
       * @method 查询高管部门下角色
       */
      findSeniorManageruserList() {
        const vm = this;
        let params = vm.seniorManagerDeptId;
        let formData = {
          departmentId: params
        };
        vm.requestPost('/tenderTask/query/searchUsersByDepartmentId', formData, vm.config)
          .then(function(response) {
            if (response.data.status == 'failure') {
              Toast({
                message: response.data.message,
                position: 'middle',
                duration: 5000
              });
            } else {
              let userList = response.data.dataList;
              vm.seniorManagerId = userList;
            }
          })
          .catch(function(error) {
            Toast({
              message: error,
              position: 'middle',
              duration: 5000
            });
          });
      },
      /**
       * @method 查询请款单信息
       */
      searchRequestMenuInfo() {
        let vm = this;
        let formData = {
          requestMenuId: this.requestMenuId
        };
        vm.requestPost('/requestMenu/query/searchRequestMenuInfo', formData)
          .then(res => {
            // 重置表单数据
             vm.resetData();
            if (res.data.status == 'failure') {
              Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
            } else {
              let requestMenuInfo = res.data.dataVO;
              // 数据更新时间
              vm.updateDate =  requestMenuInfo.updateDate;
              vm.delegateTaskId =  requestMenuInfo.delegateTaskId;
              vm.bus.$emit('updateDateRequestPayout',{updateDate:requestMenuInfo.updateDate,delegateTaskId:requestMenuInfo.delegateTaskId});
              console.log('vm.currentForm',vm.currentForm);
              vm.currentForm = Object.assign({}, vm.currentForm, res.data.dataVO);
              vm.currentForm.endDate = vm.currentForm.endDate ? this.formatting(new Date(vm.currentForm.endDate)):'';
              if (res.data.dataVO.requestFileName) {
                // 源文件数据
                vm.currentForm.resourceFiles = [{
                  name: res.data.dataVO.requestFileName,
                  url: res.data.dataVO.reviewFilePath
                }];
              }

              // 更新onlyOffice文件信息
              vm.reviewFile = {
                fileName: requestMenuInfo.reviewFileName, //文件名称
                key: requestMenuInfo.onlyOfficeEditKey, //标识ID
                downloadUrl: requestMenuInfo.onlyOfficeAbsDownLoadPath,
                saveUrl: requestMenuInfo.reviewFilePath //
              };
              // 刷新onlyOffice组件
              vm.hideOffice = false;
              vm.$nextTick(function() {
                vm.hideOffice = true;
              });
              // 改变评论者
              vm.bus.$emit('changeReviewer', vm.currentForm.submitNode);
              // 显示onlyOffice组件
              vm.showOnlyOfficeCompFlag = true;
              if (vm.currentForm.requestType == '1') {
                let tempRequestMenuTemplate = JSON.parse(vm.currentForm.frameworkContent);
                // 工程节点请款时间
                let requestDate = tempRequestMenuTemplate.input5
                  .replace('年', '-')
                  .replace('月', '-')
                  .replace('日', '')
                  .split('-');
                let year = requestDate[0];
                let month = requestDate[1];
                let day = requestDate[2];
                tempRequestMenuTemplate.input5 = new Date(year, parseInt(month) - 1, day);
                vm.formPayroll = tempRequestMenuTemplate;
              }
            }
            vm.showBtnFunc();
          })
          .catch(err => {
            Toast({
              message: err,
              position: 'middle',
              duration: 5000
            });
          });
      },
      /**
       * @method 提交审核(基本信息校验)
       * @param {Boolean} jumpFlag 是否跳级
       */
      updateQuestionMenuInfo(jumpFlag) {
        let vm = this;
        vm.$nextTick(function() {
          vm.$validator
            .validateAll()
            .then(result => {
              let errorIofo = '';
              if (result && !vm.errors.all().length && ('0' != vm.currentForm.requestType || vm.currentForm.resourceFiles
                  .length)) {
                if (vm.showPredictTotalFlag) {
                  if (vm.currentForm.predictTotal) {
                    // 校验模板合同信息
                    vm.nextSubmit(jumpFlag);
                  } else {
                    // v-if 和校验有点冲突,这里单独写一个校验提示
                    vm.Toast({
                      message: '请输入预计合同总金额(元)',
                      position: 'middle',
                      className: 'toast',
                      duration: 2000
                    });
                  }
                } else {
                  // 校验模板合同信息
                  vm.nextSubmit(jumpFlag);
                }
              } else {
                vm.Toast({
                  message: vm.errors.all()[0] || '请选择请款单附件',
                  position: 'middle',
                  className: 'toast',
                  duration: 2000
                });
              }
            });
        });
      },
      /**
       * 提交审核校验(自定义模板/模板合同)
       * @param {Boolean} jumpFlag 是否跳级
       * @param {Boolean} jumpFlag 是否是预览
       */
      nextSubmit(jumpFlag,isPreview) {
        let vm = this;
        // 模板请款单
        let submitNode = vm.currentForm.submitNode;
        if (jumpFlag) {
          submitNode = vm.getNextStepNode(submitNode);
        }
        vm.commitForm = new FormData();
        vm.commitForm.append('updateDate', vm.updateDate);

        vm.commitForm.append('requestMenuId', vm.requestMenuId);
        vm.commitForm.append('delegateTaskId', vm.delegateTaskId);
        vm.commitForm.append('contractId', vm.currentForm.contractId);
        vm.commitForm.append('requestMenuNum', vm.currentForm.requestMenuNum);
        vm.commitForm.append('seniorManagerId', vm.currentForm.seniorManagerId);
        if (vm.currentForm.predictTotal) {
          vm.commitForm.append('predictTotal', vm.currentForm.predictTotal);
        }
        vm.commitForm.append('endDate', vm.currentForm.endDate);
        vm.commitForm.append('requestAmount', vm.currentForm.requestAmount);
        vm.commitForm.append('requestType', vm.currentForm.requestType);
        vm.commitForm.append('submitNode', submitNode);

        vm.commitForm.append('demoFile', vm.requestNoteFileData);
        vm.commitForm.append('type', "request");
        // 返回后台结果
        let formworkContent = {};
        if (vm.currentForm.requestType == '0') {
          if(isPreview){
            vm.Toast('上传的自定义文件格式不支持，系统只支持扩展名为.doc、.docx的文件');
          }else{
            // 模板信息
            vm.commitForm.append('frameworkContent', JSON.stringify(formworkContent));
            vm.commitForm.append('file', vm.currentForm.resourceFiles[0]);
            vm.fire(vm.commitForm);
          }
        } else {
          vm.$nextTick(function() {
            let payrollRequisition = vm.$refs['payrollRequisition'];
            // 获取模板信息的异步函数对象
            let promise = payrollRequisition.getFormData();
            promise.then(function(result) {
              if (result) {
                formworkContent = result;
                formworkContent.input5 = vm.formatDate(formworkContent.input5, 0);
                formworkContent.input1 = vm.currentForm.requestMenuNum;
                // 模板信息
                vm.commitForm.append('frameworkContent', JSON.stringify(formworkContent));
                if(isPreview){
                  vm.requestPreview(vm.commitForm);
                }else{
                  vm.fire(vm.commitForm);
                }
              }
            });
          });
        }


      },
      /**
       * @method 最终提交表单
       */
      fire(commitForm) {
        let vm = this;
        vm.requestPost('/requestMenu/modify/updateRequestMenuInfo', commitForm)
          .then(function(response) {
            Toast({
              message: response.data.message,
              position: 'middle',
              duration: 5000
            });
            // if (response.data.status != 'failure') vm.$router.go(0);
            if (response.data.status != 'failure')  vm.$router.push('/app/workbench');
          })
          .catch(function(error) {
            Toast({
              message: error,
              position: 'middle',
              duration: 5000
            });
          });
      },
      /**
       * 替换日期格式
       * @param date 需要转换的日期
       * @param char 间隔符 "/"、"-"
       * @returns {*}
       */
      formatDate(date, char) {
        let time = '';
        if (!this.isNull(date)) {
          date = new Date(date);
          let year = date.getFullYear();
          let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          let day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
          // 如果char = 0，显示年月日，char = 1，只显示年份
          if (0 === char) {
            time = year + '年' + month + '月' + day + '日';
          } else {
            time = year + char + month + char + day;
          }
        }
        return time;
      },
      /**
       * @method 判断参数是否为空
       * @param {Object} paramVar
       */
      isNull(paramVar) {
        let paramVarIsNullBool = false;
        if (paramVar == undefined || paramVar == null || paramVar === '' || paramVar.length == 0) {
          paramVarIsNullBool = true;
        }
        return paramVarIsNullBool;
      },
      /**
       * @method 打开日期控件
       * @param {Object} text
       */
      openPicker(text) {
        this.$refs[text].open();
      },
      /**
       * @method 日期控件确认
       * @param {Object} text
       * @param {Object} val
       */
      handleConfirm(text, val) {
        this.currentForm[text] = this.formatting(new Date(this.pickerVisible));
      },
      /**
       * @method 上传
       * @param {Object} e 当前节点对象
       */
      onUpload(e) {
        this.currentForm.resourceFiles = e.currentTarget.files;
      },
      /**
       * @method 重置表单
       */
      resetData(){
        let vm = this;
        // 重置onlyOffice文件信息
        vm.reviewFile = null;
        // 重置表单数据
        vm.currentForm =  { // 当前表单
          requestMenuNum: '', // 档案编号
          seniorManagerId: '', // 所属高管,
          endDate: '', // 工程节点请款时间
          requestAmount: '', // 请款金额(元)
          predictTotal: '', // 预计合同总金额(元)
          requestType: '', // 合同类型
          resourceFiles: [] ,// 源文件数据
          requestNoteFileName:'',// 发票申请单附件名称
          requestNoteFilePath:''// 发票申请单附件下载路径
        };
      }
    }
  }
</script>

<style>
</style>
