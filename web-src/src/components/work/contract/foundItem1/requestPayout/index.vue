<template>
  <!-- 请款单详情 -->
  <div class="requestPayout">
    <el-form class="form" ref="form" :model="form" :rules="rules">
      <div class="form-block" v-if="!(correctExamineFlag && showOnlyOfficeCompFlag)">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <p>项目编号:{{ form.projectNum }}</p>
          </el-col>
          <el-col :span="12">
            <p>合同编号:{{ form.projectNum + '-' + form.contractNum }}</p>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <p>请款单创建时间:{{ formatCreateDate(form.createDate) }}</p>
          </el-col>
          <el-col :span="12"></el-col>
        </el-row>
      </div>
      <div class="form-block" v-if="!(correctExamineFlag && showOnlyOfficeCompFlag)">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="档案编号" prop="requestMenuNum">
              <el-input v-model="form.requestMenuNum" placeholder="请输入档案编号" :disabled="disabledFlag"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item class="senior" label="所属高管" prop="seniorManagerId">
              <el-select v-model="form.seniorManagerId" placeholder="请选择高管" :disabled="disabledFlag">
                <el-option v-for="item in options" :key="item.userId" :label="item.userName" :value="item.userId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="工作截止日期" prop="endDate">
              <el-date-picker v-model="form.endDate" type="date" @change="changeDateType()" placeholder="选择工作截止时间" :disabled="disabledFlag"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请款金额(元)" prop="requestAmount">
              <el-input v-model="form.requestAmount" placeholder="请输入请款金额" :disabled="disabledFlag"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" v-if="showPredictTotalFlag">
          <el-col :span="12">
            <el-form-item label="预计合同总金额(元)" prop="predictTotal">
              <el-input v-model="form.predictTotal" placeholder="请输入预计合同总金额" :disabled="disabledFlag"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-form-item label="" required>
            <el-col :span="24">
              <el-button style="color:#409EFF;float: left; border: none; margin-right: 10px;height: 28px!important;line-height: 28px;margin-top: 1px;" @click="downDemoBtn()">
                <span class="iconfont iconxiazai-" style="font-size:12px;"></span>
                下载发票申请单模板
              </el-button>
              <el-button
                style="color:#409EFF;float: left; border: none; margin-right: 10px;height: 28px!important;line-height: 28px;margin-top: 1px;"
                :disabled="invoiceApplyDisabledFlag"
                @click="openApplication()"
              >
                <span class="iconfont iconxiazai-" style="font-size:12px;"></span>
                上传发票申请单附件
              </el-button>
            </el-col>
            <el-col :span="10" v-for="(item, index) in form.requestNoteFileName" :key="index">
              <el-input placeholder="请选择文件" v-model="item.name" :disabled="true" style="margin-top: 10px;">
                <el-button slot="append" icon="el-icon-download" style="color: rgb(64, 158, 255);" @click="downRequestNoteFile(index)"></el-button>
                <el-button slot="append" icon="el-icon-delete" style="color: #F56C6C;" :disabled="invoiceApplyDisabledFlag" @click="deleteNoteFile(index)"></el-button>
              </el-input>
            </el-col>
          </el-form-item>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="备注"><el-input type="textarea" readonly="readonly" v-model="form.invoiceApplyRemark"></el-input></el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="img-failure" v-if="showImg && !(correctExamineFlag && showOnlyOfficeCompFlag)">
        <img v-bind:src="imgSrc" :alt="imgAlt" />
        <p style="text-align: center;">{{ imgAlt }}</p>
      </div>
      <div class="form-block" v-if="!correctExamineFlag">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="请款单类型" prop="requestType">
              <el-radio-group v-model="form.requestType" @change="changeRequestType" :disabled="disabledFlag">
                <el-radio label="0">自定义请款单</el-radio>
                <el-radio label="1">模板请款单</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="!correctExamineFlag" class="form-block">
        <div class="form-block" v-if="'0' == form.requestType">
          <el-row :gutter="gutter">
            <el-col :span="24">
              <el-form-item label="" required prop="requestFileName">
                <el-upload
                  class="avatar-uploader upload"
                  ref="upload"
                  action="#"
                  :show-file-list="false"
                  :on-change="handleRequestSuccess"
                  :auto-upload="false"
                  :before-upload="beforeRequestUpload"
                  :data="form"
                  :disabled="disabledFlag"
                >
                  <el-button style="color:#409EFF" :disabled="disabledFlag">
                    <span class="iconfont" style="font-size:12px;">&#xe614;</span>
                    上传请款单附件
                  </el-button>
                </el-upload>
                <el-input placeholder="请输入文件名称" v-model="form.requestFileName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div class="form-block" v-if="'1' == form.requestType">
          <payroll-requisition ref="payrollRequisition" :form="formPayroll" :disabledFlag="disabledFlag"></payroll-requisition>
        </div>
      </div>
      <div v-if="correctExamineFlag && showOnlyOfficeCompFlag" style="height: 700px;" class="form-block"><office :file="reviewFile" v-if="hideOffice"></office></div>
      <!-- 文件浏览 -->
      <!--<dialog-browse-file :file="file" ref="dialogBrowseFile" type="request" :formData="watchData"></dialog-browse-file>-->
      <preview-file :file="file" ref="previewFile" type="request" :formData="watchData"></preview-file>
    </el-form>
    <el-dialog title="上传发票申请单附件" :visible.sync="application" width="50%" center @close="handleClose()">
      <el-form :model="ruleForm" :rules="ruleFormRules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="上传文件">
          <el-upload
            class="avatar-uploader upload"
            ref="demoUpload"
            action="#"
            :multiple="true"
            :show-file-list="true"
            :on-change="handleRequestNoteSuccess"
            :before-remove="beforeRemove"
            :auto-upload="false"
            :before-upload="beforeRequestNoteUpload"
            :data="form"
            :disabled="invoiceApplyDisabledFlag"
          >
            <el-button style="color:#409EFF" :disabled="invoiceApplyDisabledFlag">
              <span class="iconfont" style="font-size:12px;">&#xe614;</span>
              上传发票申请单附件
            </el-button>
          </el-upload>
          <el-row :gutter="gutter">
            <el-col :span="16"><p v-if="fileshow" class="fileshow">请上传发票申请单附件</p></el-col>
          </el-row>
        </el-form-item>

        <el-row :gutter="gutter">
          <el-col :span="18">
            <el-form-item label="备注" prop="invoiceApplyRemark"><el-input type="textarea" v-model="ruleForm.invoiceApplyRemark"></el-input></el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="application = false">取 消</el-button>
        <el-button type="primary" @click="applicationConfirm('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import payrollRequisition from './payrollRequisition';
import office from '../../../common/components/office';
// 浏览文件
import dialogBrowseFile from '../../../common/components/dialogBrowseFile.vue';
import previewFile from '../../../common/components/previewFile';
export default {
  props: ['transmittedData'],
  data() {
    return {
      fileshow: false /* 显示验证信息 */,
      application: false /* 上传发票申请单附件 */,
      ruleForm: {
        invoiceApplyRemark: ''
      },
      formPayroll: {},
      taskTitle: '',
      showImg: false,
      taskContent: '',
      contractList: [], // 合同下拉列表
      personList: [], // 人员列表
      // 表单项间距
      gutter: 40,
      taskDesc: '', // 任务描述
      InfoShowFlag: false, // 工作步骤说明
      delegateTaskId: '', //委托任务ID
      updateDate: '', //修改时间
      form: {
        contractId: '',
        requestMenuNum: '',
        seniorManagerId: '',
        predictTotal: 0,
        endDate: '',
        requestAmount: 0,
        requestFileName: '',
        requestNoteFileName: [],
        requestNoteFilePath: [],
        requestType: '',
        requestFilePath: '',
        invoiceApplyRemark:'',
      },
      invoiceApplyDisabledFlag: true,
      userType: 0,
      commitForm: null,
      options: [],
      filedata: null,
      requestNoteFileData: null,
      fileNameFlag: true,
      showOnlyOfficeCompFlag: false,
      correctExamineFlag: false,
      showPredictTotalFlag: false,
      reviewFile: null,
      requestMenuInfo: '',
      disabledFlag: false,
      hideOffice: true,
      ruleFormRules: {
        invoiceApplyRemark: [{ required: true, message: '请输入备注', trigger: 'blur' }, { max: 500, message: '最长500字', trigger: 'blur' }]
      },
      rules: {
        requestMenuNum: [{ required: true, message: '请输入档案编号', trigger: 'blur' }, { max: 30, message: '最长30字', trigger: 'blur' }],
        seniorManagerId: [{ required: true, message: '请选择所属高管', trigger: 'change' }],
        endDate: [{ required: true, message: '请选择工作截止日期', trigger: 'blur' }],
        requestAmount: [{ required: true, message: '请输入请款金额', trigger: 'blur' }, { validator: this.moneyCheck, trigger: 'blur' }],
        predictTotal: [{ required: true, message: '请输入预计合同总金额', trigger: 'blur' }, { validator: this.moneyCheck, trigger: 'blur' }],
        requestType: [{ required: true, message: '请选择请款单类型', trigger: 'blur' }],
        requestFileName: [{ required: true, message: '请上传请款单附件', trigger: 'change' }]
      },
      file: '', // 待浏览文件
      displayList:[],
      invoiceApplyFormData: new FormData(),
      watchData: new FormData()
    };
  },
  components: {
    'payroll-requisition': payrollRequisition,
    office: office,
    // 浏览文件
    'dialog-browse-file': dialogBrowseFile,
    previewFile: previewFile
  },
  // 实例销毁之前调用
  beforeDestroy() {
    let vm = this;
    vm.bus.$off('click');
  },
  created() {
    let vm = this;
    vm.requestMenuId = vm.transmittedData.id;
    vm.$nextTick(() => {
      vm.refreshThisComp();
    });
    vm.bus.$on('click', val => {
      vm.$nextTick(() => {
        vm.requestMenuId = vm.transmittedData.id;
        if (val.treeNodeLevel == '5' && val.objType == 'requestMent') {
          vm.refreshThisComp();
        }
      });
    });

    vm.bus.$on('refreshThisComp', val => {
      vm.$nextTick(() => {
        vm.refreshThisComp();
      });
    });
    //绑定全局事件globalEvent
    vm.bus.$on('updateQuestionMenuInfo', val => {
      vm.updateQuestionMenuInfo(val);
    });
    vm.bus.$off('requestPreview');
    //绑定全局事件globalEvent
    vm.bus.$on('requestPreview', val => {
      vm.requestPreview(val);
    });
  },
  watch: {
    correctExamineFlag() {
      if (this.correctExamineFlag) {
        this.searchRequestMenuInfo();
      } else {
        this.showOnlyOfficeCompFlag = this.correctExamineFlag;
      }
    }
  },
  methods: {
    openApplication() {
      /* 打开上传发票申请单附件*/
      this.application = true;
    },
    handleClose() {
      /* 关闭之前上传发票申请单附件*/
      this.application = false;
      this.$refs['ruleForm'].resetFields();
      this.$refs['demoUpload'].clearFiles();
    },
    applicationConfirm(formName) {
      /* 确定上传发票申请单附件*/
      const vm = this;
      if (this.displayList.length>0) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.displayList.forEach(function(item,index){
              vm.invoiceApplyFormData.append('file',item); /* 文件赋值 */
            })
            this.invoiceApplyFormData.append('invoiceApplyRemark', this.ruleForm.invoiceApplyRemark); /* 备注赋值 */
            this.invoiceApplyFormData.append('requestMenuId', this.requestMenuId); //请款单ID
            this.apiAddress('/requestMenu/modify/uploadInvoiceApplyFile', this.invoiceApplyFormData)
              .then(res => {
                if (res.data.status == 'success') {
                  let requestMenuInfo = res.data.dataVO;
                  /* 转化上传发票附件数据格式*/
                  if (requestMenuInfo.requestNoteFileName) {
                    let requestNoteFileName = requestMenuInfo.requestNoteFileName.split('|');
                    let requestNoteFilePath = requestMenuInfo.requestNoteFilePath.split('|');
                    requestNoteFileName.forEach(function(item, index) {
                      if (item != '') {
                        vm.form.requestNoteFileName.push({ name: item });
                        vm.form.requestNoteFilePath.push(requestNoteFilePath[index]);
                      }
                    });
                  }
                  vm.form.invoiceApplyRemark = requestMenuInfo.invoiceApplyRemark;
                } else {
                  vm.$message({
                    message: res.data.message,
                    type: 'error',
                    center: true
                  });
                }
              })
              .catch(e => {});
            this.application = false;
            this.invoiceApplyFormData = new FormData();
            this.displayList = [];
          } else {
            return false;
          }
        });
      } else {
        this.fileshow = true;
      }
    },
    /**
     * 请款单预览
     * @param {Object} jumpFlag
     */
    requestPreview(jumpFlag) {
      let vm = this;
      let submitNode = vm.form.submitNode;
      if (jumpFlag) {
        submitNode = vm.getNextStepNode(submitNode);
      }

      // 返回后台结果
      let formworkContent = {};
      // 如果是自定义类型，则提示->"自定义类型不能预览"
      if (vm.form.requestType != '1') {
        vm.stateDialog('error', '自定义类型不能预览');
        return;
        // 如果是模板
      } else {
        formworkContent = Object.assign({}, vm.$refs['payrollRequisition'].currentForm);
        // 请款时间
        let tempDate = vm.$refs['payrollRequisition'].currentForm.input5;
        formworkContent.input5 = vm.formatDate(tempDate, 0);
        formworkContent.input1 = vm.form.requestMenuNum;
      }

      vm.commitForm = new FormData();
      vm.commitForm.append('requestMenuId', vm.requestMenuId);
      vm.commitForm.append('delegateTaskId', vm.delegateTaskId);
      vm.commitForm.append('contractId', vm.form.contractId);
      vm.commitForm.append('requestMenuNum', vm.form.requestMenuNum);
      vm.commitForm.append('seniorManagerId', vm.form.seniorManagerId);
      if (vm.form.predictTotal) {
        vm.commitForm.append('predictTotal', vm.form.predictTotal);
      }
      vm.commitForm.append('endDate', vm.form.endDate);
      vm.commitForm.append('requestAmount', vm.form.requestAmount);
      vm.commitForm.append('requestType', vm.form.requestType);
      vm.commitForm.append('submitNode', submitNode);

      // 模板信息
      vm.commitForm.append('frameworkContent', JSON.stringify(formworkContent));
      vm.commitForm.append('type', 'request');

      vm.watchData = vm.commitForm;

      vm.file = {
        materialPath: true,
        isGeneratedFile: true,
        noSubmitPreview: '1'
      };
      vm.$refs.previewFile.show = true;
    },
    changeRequestType() {
      /* 单选按钮改变 */
      if (this.form.requestType == 0) {
        this.$set(this.form, 'requestFileName', '');
      }
    },
    trigger(name, data) {
      let vm = this;
      // 触发事件
      vm.bus.$emit(name, data);
    },
    //刷新当前组件
    refreshThisComp() {
      let vm = this;
      vm.searchRequestMenuInfo();
      vm.findSeniorManageruserList();
      vm.showUserType(vm.requestMenuId);
      vm.setInvoiceApplyDisabledFlag(vm.requestMenuId);
    },
    //上传发票申请单附件可用标识
    setInvoiceApplyDisabledFlag(requestMenuId) {
      let formData = {
        requestMenuId: requestMenuId
      };
      this.apiAddress('/requestMenu/query/getIsApplyUser', formData)
        .then(res => {
          if (res.data) {
            this.invoiceApplyDisabledFlag = false;
          }
        })
        .catch(e => {});
    },
    // 新建时存储发票请款单模板文件
    beforeRequestNoteUpload(file, fileList) {
      return false;
    },
    // 新建发票请款单模板文件名称展示
    handleRequestNoteSuccess(file, fileList) {
      this.fileshow = false;/* 文件验证信息显隐 */
      this.requestNoteFileData = file.raw;
      this.form.requestNotesSignPathFile = URL.createObjectURL(file.raw);
      this.displayList.push(file.raw)
    },
    beforeRemove(file, fileList) {
      /* 删除上传文件列表 */
      let index = this.displayList.indexOf(file.raw);
      if(index!=-1){
        this.displayList.splice(index,1);
      }
    },
    // 新建时存储请款单文件
    beforeRequestUpload(file, fileList) {
      return false;
    },
    // 新建请款单文件名称展示
    handleRequestSuccess(file, fileList) {
      this.filedata = file.raw;
      this.form.signPathFile = URL.createObjectURL(file.raw);
      this.$set(this.form, 'requestFileName', file.name);
    },
    formatCreateDate(prop) {
      const date = new Date(prop);
      var year = date.getFullYear();
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
      return year + '-' + month + '-' + day;
    },
    /*提交审核*/
    updateQuestionMenuInfo(jumpFlag) {
      const vm = this;
      let flag = true;
      this.$refs['form'].validate(valid => {
        if (vm.form.requestType == '1') {
          this.$refs['payrollRequisition'].$refs['form'].validate(valid => {
            flag = valid;
          });
        }
        if (valid && flag) {
          let submitNode = vm.form.submitNode;
          if (jumpFlag) {
            submitNode = vm.getNextStepNode(submitNode);
          }
          vm.commitForm = new FormData();
          vm.commitForm.append('requestMenuId', vm.requestMenuId);
          vm.commitForm.append('delegateTaskId', vm.delegateTaskId);
          vm.commitForm.append('contractId', vm.form.contractId);
          vm.commitForm.append('requestMenuNum', vm.form.requestMenuNum);
          vm.commitForm.append('seniorManagerId', vm.form.seniorManagerId);
          if (vm.form.predictTotal) {
            vm.commitForm.append('predictTotal', vm.form.predictTotal);
          }
          vm.commitForm.append('endDate', vm.form.endDate);
          vm.commitForm.append('requestAmount', vm.form.requestAmount);
          vm.commitForm.append('requestType', vm.form.requestType);
          vm.commitForm.append('submitNode', submitNode);

          // 返回后台结果
          let formworkContent = {};
          if (vm.form.requestType == '0') {
            vm.commitForm.append('file', vm.filedata);
            vm.commitForm.append('demoFile', vm.requestNoteFileData);
          } else {
            vm.commitForm.append('demoFile', vm.requestNoteFileData);
            formworkContent = vm.$refs['payrollRequisition'].currentForm;

            // 请款时间
            let tempDate = formworkContent.input5;
            formworkContent.input5 = vm.formatDate(tempDate, 0);
            formworkContent.input1 = vm.form.requestMenuNum;
          }
          // 模板信息
          vm.commitForm.append('frameworkContent', JSON.stringify(formworkContent));

          vm.$axios
            .post('/requestMenu/modify/updateRequestMenuInfo', vm.commitForm)
            .then(function(response) {
              if (response.data.status == 'failure') {
                vm.$message({
                  message: response.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.$message({
                  message: response.data.message,
                  type: 'success',
                  center: true
                });
                vm.$router.push({
                  path: '/page/work/default'
                });
              }
            })
            .catch(function(error) {
              vm.$message({
                message: error,
                type: 'error',
                center: true
              });
            });
        } else {
          return false;
        }
      });
    },
    changeDateType() {
      this.form.endDate = this.formatting(this.form.endDate);
    },
    downRequestNoteFile(index) {
      /* 下载上传发票申请单附件 */
      this.downloadFile(this.form.requestNoteFilePath[index], this.form.requestNoteFileName[index].name, false);
    },
    deleteNoteFile(index){/* 删除上传发票申请单附件*/
      let formData = {
        requestMenuId: this.requestMenuId,
        requestNoteFilePath: this.form.requestNoteFilePath[index]
      };
      this.apiAddress('/requestMenu/modify/deleteInvoiceApplyFile', formData)
        .then(res => {
          this.searchRequestMenuInfo()
        })
        .catch(e => {
        });
    },
    /*查询请款单信息*/
    searchRequestMenuInfo() {
      let vm = this;
      let formData = {
        requestMenuId: this.requestMenuId
      };
      vm.apiAddress('/requestMenu/query/searchRequestMenuInfo', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let requestMenuInfo = res.data.dataVO;
            vm.requestMenuInfo = requestMenuInfo;
            /* 转化上传发票附件数据格式*/
            if (requestMenuInfo.requestNoteFileName) {
              let requestNoteFileName = requestMenuInfo.requestNoteFileName.split('|');
              let requestNoteFilePath = requestMenuInfo.requestNoteFilePath.split('|');
              requestMenuInfo.requestNoteFilePath = requestNoteFilePath;
              requestNoteFileName.forEach(function(item, index) {
                requestNoteFileName[index] = { name: item };
              });
              requestMenuInfo.requestNoteFileName = requestNoteFileName;
            }else{
              requestMenuInfo.requestNoteFileName= [];
              requestMenuInfo.requestNoteFilePath= [];
            }
            vm.form = Object.assign({}, vm.form, requestMenuInfo);
            vm.delegateTaskId = requestMenuInfo.delegateTaskId;
            vm.reviewFile = {
              fileName: requestMenuInfo.reviewFileName, //文件名称
              key: requestMenuInfo.onlyOfficeEditKey, //标识ID
              downloadUrl: requestMenuInfo.onlyOfficeAbsDownLoadPath,
              saveUrl: requestMenuInfo.reviewFilePath //
            };
            vm.$parent.onlyOfficeEditKey = requestMenuInfo.onlyOfficeEditKey;
            vm.$parent.reviewFilePath = requestMenuInfo.reviewFilePath;
            vm.$parent.reviewFileName = requestMenuInfo.reviewFileName;
            vm.hideOffice = false;
            vm.$nextTick(function() {
              vm.hideOffice = true;
            });
            vm.trigger('changeReviewer', requestMenuInfo.submitNode);
            vm.showOnlyOfficeCompFlag = true;
            if (requestMenuInfo.requestType == '1') {
              let tempRequestMenuTemplate = JSON.parse(requestMenuInfo.frameworkContent);

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
              this.formPayroll = tempRequestMenuTemplate;
            }
          }
          vm.showBtnFunc();
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    //查询高管部门下角色
    findSeniorManageruserList() {
      const vm = this;
      let params = vm.seniorManagerDeptId;
      let formData = {
        departmentId: params
      };
      vm.apiAddress('/tenderTask/query/searchUsersByDepartmentId', formData)
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            let userList = res.data.dataList;
            vm.options = userList;
          } else {
            vm.stateDialog('error', res.data.message);
          }
        })
        .catch(e => {
          vm.stateDialog('error', e);
        });
    },

    /*查询当前人类别(用以展示按钮)*/
    showUserType(requestMenuId) {
      let vm = this;
      let formData = {
        requestMenuId: requestMenuId
      };
      vm.apiAddress('/requestMenu/query/showUserType', formData)
        .then(res => {
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
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    showBtnFunc() {
      let vm = this;
      vm.showPredictTotalFlag = false;
      vm.correctExamineFlag = false;
      vm.disabledFlag = false;
      vm.$nextTick(() => {
        vm.showPredictTotalFlag = true && vm.requestMenuInfo.verifyState == null;
        vm.disabledFlag = vm.userType != 1;
      });
      if (vm.userType == 2 || vm.userType == 3 || vm.userType == 4) {
        vm.correctExamineFlag = true;
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
      if (!this.isNull(date)) {
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
    // 判断参数是否为空
    isNull(paramVar) {
      let paramVarIsNullBool = false;
      if (paramVar == undefined || paramVar == null || paramVar === '' || paramVar.length == 0) {
        paramVarIsNullBool = true;
      }
      return paramVarIsNullBool;
    },
    uploadBtn() {},
    downDemoBtn() {
      const vm = this;
      // 导出列表数据
      const elemIF = document.createElement('iframe');
      elemIF.src = '/requestMenu/download/downloadDemoFile?realFileName=' + '开票信息附件.xlsx';
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
    }
  }
};
</script>
<style>
@import url('../../../common/css/other.css');
/* 文件列表*/
</style>
<style lang="scss">
.requestPayout {
  .senior {
    label {
      float: none;
    }
    .el-select {
      width: 100%;
    }

  }
}
.fileshow {
  color: rgb(245, 108, 108);
}
</style>
