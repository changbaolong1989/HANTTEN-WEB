<template>
  <!-- 造价部 A2-A3-A7-->
  <div class="cost-index">
    <!-- 上边默认显示工作内容-->
    <el-tabs v-model="active" type="card" class="tabs">
      <el-tab-pane label="成果文件" name="/page/report/prepare" :lazy="true">
        <results ref="results" :transmittedData="currentTransmittedData" :disabled="disabled" v-if="prepareFlg"></results>
      </el-tab-pane>
      <el-tab-pane label="A2" v-if="currentTransmittedData.pageContent.indexOf('A2') > -1" name="/page/report/prepare/a2" :lazy="true">
        <costA2 :transmittedData="currentTransmittedData"></costA2>
      </el-tab-pane>
      <el-tab-pane label="A3" v-if="currentTransmittedData.pageContent.indexOf('A3') > -1" name="/page/report/prepare/a3" :lazy="true">
        <costA3 :transmittedData="currentTransmittedData"></costA3>
      </el-tab-pane>
      <el-tab-pane label="A7" v-if="currentTransmittedData.pageContent.indexOf('A7') > -1" name="/page/report/prepare/a7" :lazy="true">
        <costA7 :transmittedData="currentTransmittedData"></costA7>
      </el-tab-pane>
    </el-tabs>
    <!-- 文件浏览 -->
    <!--<dialog-browse-file :file="file" ref="dialogBrowseFile" type="task" :formData="formData"></dialog-browse-file>-->
    <preview-file :file="file" ref="previewFile" type="request" :formData="formData"></preview-file>
  </div>
</template>
<script>
  import Results from './results'
  import costA2 from './cost-A2'
  import costA3 from './cost-A3'
  import costA7 from './cost-A7'
  // 浏览文件
  import dialogBrowseFile from '../../work/common/components/dialogBrowseFile.vue';
  import previewFile from '../../work/common/components/previewFile';
  export default {
    props: ['transmittedData', 'disabled'],
    data() {
      return {
        // 默认不显示成果文件
        prepareFlg: false,
        top: "top",
        active: '/page/report/prepare',
        currentTransmittedData:this.tool.deepCopy(this.transmittedData,this.tool.deepCopy),
        file:'',// 待浏览文件
        formData: new FormData(),
      }
    },
    watch:{
      transmittedData:{
        handler(){
          let vm = this;
          vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData,vm.tool.deepCopy);
          // 获取成果文件信息
          vm.getPreparationOfOutcomeDocumentsDetailInfo();
        },
        immediate: true
      }
    },
    components: {
      "costA2": costA2,
      "costA3": costA3,
      "costA7": costA7,
      "results": Results,
      // 浏览文件
      "dialog-browse-file":dialogBrowseFile,
      "previewFile":previewFile
    },
    mounted() {
      let vm = this;
      vm.bus.$off('submitPreparationOfOutcomeDocuments');
      // 触发提交审核-编制成果文件事件
      vm.bus.$on('submitPreparationOfOutcomeDocuments', function(data){
        vm.$nextTick(function () {
          vm.submit(data,data.submitFlag);
        });
      });

      // 校验表单
      vm.bus.$off('checkOutcomeDocuments');
      // 触发提交审核-编制成果文件事件
      vm.bus.$on('checkOutcomeDocuments',function(todo){
        vm.$nextTick(function () {
          let results = vm.$refs['results'];
          let resultsForm = results.form;
          if(resultsForm.model == '0'){
            // 自定义合同
            results.$refs['form'].validate((valid) => {
              if(valid)todo();
            });
          }else{
            // 模板
            results.$refs['template'].$refs['form'].validate((valid) => {
              if(valid)todo();
            });
          }
        });
      });
      vm.bus.$off('preview');
      vm.bus.$on('preview', function(data){
        vm.$nextTick(function () {
          vm.preview(data);
        });
      });

    },
    methods: {
      preview(data) {
        const vm = this;
        let results = vm.$refs['results'];
        let resultsForm = results.form;
        let templateForm;
        // 如果是模板 则加载模板的form
        if (resultsForm.model == '1'){
          templateForm = Object.assign({}, results.$refs['template'].form);
        }
        const formData = new FormData();

        // right页传值
        formData.append('relationId', data.relationId);
        formData.append("materialIds", data.materialIds);
        formData.append("submitNode", data.submitNode);
        formData.append("periodName", data.periodName);
        formData.append('taskId', this.currentTransmittedData.taskId);
        // 合同类型
        formData.append("taskType", resultsForm.model);

        formData.append('type', "task");

        // 如果选择自定义合同，则上传附件
        if (resultsForm.model == '0') {
           results.$refs.upload.submit();
           // 上传文件
           formData.append("signPathFile", results.filedata);
           // 成果文件编号
           formData.append("reportNum", resultsForm.reportNum);

           vm.formData = formData;
           vm.file = {
             "materialPath":true,
             "isGeneratedFile":true,
             "noSubmitPreview":"1"
           }
           vm.$refs.previewFile.show = true;

          // 如果选择合同类型为模板，则添加模板
        } else if (resultsForm.model == '1') {
          //模板类型
          let formworkType = resultsForm.template.replace("t", "");
          // 模板类型
          formData.append("formworkType", formworkType);


          // 返回后台结果
          let formworkContent = {};
          // 报告日期
          let reportDate;
          // 报送时间
          let submissDate;
          // 收到日期
          let reveivedDate;
          // 规章颁布日期
          let ruleIssueDate;
          // 规章颁布日期2
          let ruleIssueDate2;
          // 规章颁布日期3
          let ruleIssueDate3;
          // 合同开竣工日期
          let contractProjectDate = [];
          // 实际开竣工日期
          let realProjectDate = [];
          // 定审日期
          let auditDate = [];
          // 附件
          let affix;
          // 抄送
          let carbonCopy;
          // 日期
          let date;
          // 模板类型
          switch (formworkType) {
            // 编制概算
            case "0":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");

              // 规章颁布日期（年）
              if(formworkContent.input10 !== ""){
                ruleIssueDate = new Date(formworkContent.input10);
                formworkContent.input10 = this.formatDate(ruleIssueDate, 1);
              }

              // 规章颁布日期2（月）
              if(formworkContent.input11 !== ""){
                ruleIssueDate2 = new Date(formworkContent.input11);
                formworkContent.input11 = this.formatDate(ruleIssueDate2, 2);
              }

              // 规章颁布日期3（日）
              if(formworkContent.input13 !== ""){
                ruleIssueDate3 = new Date(formworkContent.input13);
                formworkContent.input13 = this.formatDate(ruleIssueDate3, 2);
              }

              // 咨询报告编号
              formData.append("reportNum", formworkContent.input19);
              break;
            // 编制估算
            case "1":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input14);
              break;
            // 编制结算
            case "2":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 合同开竣工日期（范围）
              contractProjectDate = formworkContent.input8;
              if (!this.tool.isNull(contractProjectDate)) {
                formworkContent.input8 = this.formatDate(contractProjectDate[0], 0) + '至' + this.formatDate(contractProjectDate[1], 0);
              }
              // 实际开竣工日期（范围）
              realProjectDate = formworkContent.input9;
              if (!this.tool.isNull(realProjectDate)) {
                formworkContent.input9 = this.formatDate(realProjectDate[0], 0) + '至' + this.formatDate(realProjectDate[1], 0);
              }
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input19);
              break;
            // 编制决算
            case "3":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input18);
              break;
            // 编制清标报告
            case "4":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 收到本工程中标单位的投标文件时间
              reveivedDate = formworkContent.input9;
              formworkContent.input9 = this.formatDate(reveivedDate, 0);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input35);
              break;
            // 招标控制价模版
            case "5":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期
              ruleIssueDate = formworkContent.input10;
              formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input19);
              break;
            // 招标清单模版（分包、承包）
            case "6":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（月份）
              ruleIssueDate = formworkContent.input10;
              formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input15);
              break;
            // 招标控制价模版（总包）
            case "7":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input15);
              break;
            // 预算编制模板
            case "8":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（月份）
              ruleIssueDate = formworkContent.input10;
              formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
              // 规章颁布日期2（月份）
              ruleIssueDate2 = formworkContent.input115;
              formworkContent.input115 = this.formatDate(ruleIssueDate2, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input21);
              break;
            // 概算审核
            case "9":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（年）
              ruleIssueDate = formworkContent.input18;
              formworkContent.input18 = this.formatDate(new Date(ruleIssueDate), 1);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input34);
              break;
            // 估算审核
            case "10":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 报送时间
              submissDate = formworkContent.input16;
              formworkContent.input16 = this.formatDate(submissDate, 0);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input35);
              break;
            // 结算审核
            case "11":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input48);
              break;
            // 预算审核
            case "12":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 报送时间
              submissDate = formworkContent.input16;
              formworkContent.input16 = this.formatDate(new Date(submissDate), 0);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input33);
              break;
            // 进度款审核意见
            case "15":
              formworkContent = templateForm;
              // 报送时间
              submissDate = formworkContent.input6;
              formworkContent.input6 = this.formatDate(submissDate, 0);
              // 附件
              affix = formworkContent.input14;
              if (affix == "0") {
                formworkContent.input15 = "";
              }
              // 抄送
              carbonCopy = formworkContent.input16;
              if (carbonCopy == "0") {
                formworkContent.input17 = "";
                formworkContent.input18 = "";
              }
              break;
            // 变更审核意见
            case "16":
              formworkContent = templateForm;
              // 报送时间
              submissDate = formworkContent.input6;
              formworkContent.input6 = this.formatDate(submissDate, 0);
              // 附件
              affix = formworkContent.input14;
              if (affix == "0") {
                formworkContent.input15 = "";
              }
              // 抄送
              carbonCopy = formworkContent.input16;
              if (carbonCopy == "0") {
                formworkContent.input17 = "";
                formworkContent.input18 = "";
              }
              break;
            // 预算审核意见
            case "17":
              formworkContent = templateForm;
              // 报送时间
              submissDate = formworkContent.input6;
              formworkContent.input6 = this.formatDate(submissDate, 0);
              // 发布时间
              ruleIssueDate = formworkContent.input9;
              formworkContent.input9 = this.formatDate(ruleIssueDate, 2);
              // 附件
              affix = formworkContent.input14;
              if (affix == "0") {
                formworkContent.input15 = "";
              }
              // 抄送
              carbonCopy = formworkContent.input16;
              if (carbonCopy == "0") {
                formworkContent.input17 = "";
                formworkContent.input18 = "";
              }
              break;
            // 招标代理
            case "18":
            // 评标书面报告
            case "19":
              results.$refs['template'].getFormData('form').then(function(data){
                formworkContent = data;
                // 日期转换为字符串
                formworkContent.date = vm.tool.formatDate(data.date,"yyyy-mm-dd hh:MM:ss")
                // 当前日期
                formworkContent.input100 = vm.formatDate(new Date(), 0);
                // 合同信息
                formData.append("formworkContent", JSON.stringify(formworkContent));

                vm.formData = formData;
                 vm.file = {
                   "materialPath":true,
                   "isGeneratedFile":true,
                   "noSubmitPreview":"1"
                 }
                 vm.$refs.previewFile.show = true;
              });
              return;
            default:
              formworkContent = templateForm;
              break;
          }
          formworkContent.input100 = this.formatDate(new Date(), 0);
          // 合同信息
          formData.append("formworkContent", JSON.stringify(formworkContent));

          // 表单
          vm.$refs['results'].$refs['template'].$refs['form'].validate((valid) => {
            if(valid){
              vm.formData = formData;
              vm.file = {
                "materialPath":true,
                "isGeneratedFile":true,
                "noSubmitPreview":"1"
              }
              vm.$refs.previewFile.show = true;
            }
          });

        }

      },
      // 提交审核
      submit(data,type) {
        const vm = this;
        let results = vm.$refs['results'];
        let resultsForm = results.form;
        let templateForm;


        // 如果是模板 则加载模板的form
        if (resultsForm.model == '1'){
            templateForm = results.$refs['template'].form;
        }
        const formData = new FormData();
        // 咨询报告份数
        formData.append("reportCopies", resultsForm.reportCopies);
        // right页传值
        formData.append('relationId', data.relationId);
        formData.append("materialIds", data.materialIds);
        formData.append("submitNode", data.submitNode);
        formData.append("periodName", data.periodName);
        formData.append('taskId', this.currentTransmittedData.taskId);
        formData.append("taskType", resultsForm.model);// 合同类型
        for(let i = 0;i<results.form.fileList.length;i++){
          formData.append("fileList", results.form.fileList[i]); // 上传文件
        }
        // 如果选择自定义合同，则上传附件
        if (resultsForm.model == '0') {
           results.$refs.upload.submit();
           // 上传文件
           formData.append("signPathFile", results.filedata);
           // 成果文件编号
           formData.append("reportNum", resultsForm.reportNum);
           console.log("type",type)
            if (type) {
              vm.$confirm('是否保存?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                center: true
              }).then(() => {
                // 保存审核请求
                vm.submitDraftReview(formData);
              });
            }else{
              // 当没选文件并且没有成果文件名称的时候，提示错误信息。
              if(!results.filedata && !resultsForm.materialName){
                vm.stateDialog('error', "成果文件不能为空！");
                return;
              }
              // 表单
              vm.$refs['results'].$refs['form'].validate((valid) => {
                if(valid){
                  // 提交审核请求
                  return vm.submitReview(formData);
                }
              });
            }

          // 如果选择合同类型为模板，则添加模板
        } else if (resultsForm.model == '1') {
          console.log("resultsForm.template",resultsForm.template)
          //模板类型
          let formworkType = resultsForm.template.replace("t", "");
          // 模板类型
          formData.append("formworkType", formworkType);
          // 返回后台结果
          let formworkContent = {};
          // 报告日期
          let reportDate;
          // 报送时间
          let submissDate;
          // 收到日期
          let reveivedDate;
          // 规章颁布日期
          let ruleIssueDate;
          // 规章颁布日期2
          let ruleIssueDate2;
          // 规章颁布日期3
          let ruleIssueDate3;
          // 合同开竣工日期
          let contractProjectDate = [];
          // 实际开竣工日期
          let realProjectDate = [];
          // 定审日期
          let auditDate = [];
          // 附件
          let affix;
          // 抄送
          let carbonCopy;
          // 日期
          let date;
          // 模板类型
          console.log("formworkType",formworkType)
          switch (formworkType) {
            // 编制概算
            case "0":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（年）
              ruleIssueDate = new Date(formworkContent.input10);
              formworkContent.input10 = this.formatDate(ruleIssueDate, 1);
              // 规章颁布日期2（月）
              ruleIssueDate2 = new Date(formworkContent.input11);
              formworkContent.input11 = this.formatDate(ruleIssueDate2, 2);
              // 规章颁布日期3（月）
              ruleIssueDate3 = new Date(formworkContent.input13);
              formworkContent.input13 = this.formatDate(ruleIssueDate3, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input19);
              break;
            // 编制估算
            case "1":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input14);
              break;
            // 编制结算
            case "2":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 合同开竣工日期（范围）
              contractProjectDate = formworkContent.input8;
              if (!this.tool.isNull(contractProjectDate)) {
                formworkContent.input8 = this.formatDate(contractProjectDate[0], 0) + '至' + this.formatDate(contractProjectDate[1], 0);
              }
              // 实际开竣工日期（范围）
              realProjectDate = formworkContent.input9;
              if (!this.tool.isNull(realProjectDate)) {
                formworkContent.input9 = this.formatDate(realProjectDate[0], 0) + '至' + this.formatDate(realProjectDate[1], 0);
              }
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input19);
              break;
            // 编制决算
            case "3":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input18);
              break;
            // 编制清标报告
            case "4":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 收到本工程中标单位的投标文件时间
              reveivedDate = formworkContent.input9;
              formworkContent.input9 = this.formatDate(reveivedDate, 0);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input35);
              break;
            // 招标控制价模版
            case "5":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期
              ruleIssueDate = formworkContent.input10;
              formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input19);
              break;
            // 招标清单模版（分包、承包）
            case "6":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（月份）
              ruleIssueDate = formworkContent.input10;
              formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input15);
              break;
            // 招标控制价模版（总包）
            case "7":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input15);
              break;
            // 预算编制模板
            case "8":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（月份）
              ruleIssueDate = formworkContent.input10;
              formworkContent.input10 = this.formatDate(ruleIssueDate, 2);
              // 规章颁布日期2（月份）
              ruleIssueDate2 = formworkContent.input115;
              formworkContent.input115 = this.formatDate(ruleIssueDate2, 2);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input21);
              break;
            // 概算审核
            case "9":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 规章颁布日期（年）
              ruleIssueDate = formworkContent.input18;
              formworkContent.input18 = this.formatDate(new Date(ruleIssueDate), 1);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input34);
              break;
            // 估算审核
            case "10":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 报送时间
              submissDate = formworkContent.input16;
              formworkContent.input16 = this.formatDate(submissDate, 0);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input35);
              break;
            // 结算审核
            case "11":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input48);
              break;
            // 预算审核
            case "12":
              formworkContent = templateForm;
              // 报告日期
              reportDate = formworkContent.input3;
              formworkContent.input3 = this.formatDate(reportDate, "/");
              // 报送时间
              submissDate = formworkContent.input16;
              formworkContent.input16 = this.formatDate(new Date(submissDate), 0);
              // 咨询报告编号
              formData.append("reportNum", formworkContent.input33);
              break;
            // 进度款审核意见
            case "15":
              formworkContent = templateForm;
              // 报送时间
              submissDate = formworkContent.input6;
              formworkContent.input6 = this.formatDate(submissDate, 0);
              // 附件
              affix = formworkContent.input14;
              if (affix == "0") {
                formworkContent.input15 = "";
              }
              // 抄送
              carbonCopy = formworkContent.input16;
              if (carbonCopy == "0") {
                formworkContent.input17 = "";
                formworkContent.input18 = "";
              }
              break;
            // 变更审核意见
            case "16":
              formworkContent = templateForm;
              // 报送时间
              submissDate = formworkContent.input6;
              formworkContent.input6 = this.formatDate(submissDate, 0);
              // 附件
              affix = formworkContent.input14;
              if (affix == "0") {
                formworkContent.input15 = "";
              }
              // 抄送
              carbonCopy = formworkContent.input16;
              if (carbonCopy == "0") {
                formworkContent.input17 = "";
                formworkContent.input18 = "";
              }
              break;
            // 预算审核意见
            case "17":
              formworkContent = templateForm;
              // 报送时间
              submissDate = formworkContent.input6;
              formworkContent.input6 = this.formatDate(submissDate, 0);
              // 发布时间
              ruleIssueDate = formworkContent.input9;
              formworkContent.input9 = this.formatDate(ruleIssueDate, 2);
              // 附件
              affix = formworkContent.input14;
              if (affix == "0") {
                formworkContent.input15 = "";
              }
              // 抄送
              carbonCopy = formworkContent.input16;
              if (carbonCopy == "0") {
                formworkContent.input17 = "";
                formworkContent.input18 = "";
              }
              break;
            // 招标代理
            case "18":
            // 评标书面报告
            case "19":
            let vm = this;
               if (type) {
                  results.$refs['template'].getFormData('form',true).then(function(data){
                    formworkContent = data;
                    formworkContent.input100 = vm.formatDate(new Date(), 0);
                    // 合同信息
                    formData.append("formworkContent", JSON.stringify(formworkContent));
                    vm.$confirm('是否保存?', '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      center: true
                    }).then(() => {
                      // 保存审核请求
                      vm.submitDraftReview(formData);
                    });
                  });
                }else{
                  results.$refs['template'].getFormData('form').then(function(data){
                    formworkContent = data;
                    formworkContent.input100 = vm.formatDate(new Date(), 0);
                    // 合同信息
                    formData.append("formworkContent", JSON.stringify(formworkContent));
                      // 提交审核请求
                      vm.submitReview(formData);
                  });
                }
              return;
            default:
              formworkContent = templateForm;
              break;
          }


          formworkContent.input100 = this.formatDate(new Date(), 0);
          // 合同信息
          formData.append("formworkContent", JSON.stringify(formworkContent));

          if (type) {
            vm.$confirm('是否保存?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
            }).then(() => {
              // 保存审核请求
              vm.submitDraftReview(formData);
            });
          }else{
            // 表单
            vm.$refs['results'].$refs['template'].$refs['form'].validate((valid) => {
              if(valid){
                // 提交审核请求
                vm.submitReview(formData);
              }
            });
          }
        }
      },
      /**
       * @method 提交审核请求
       * @param {FormData} formData 表单数据
       */
      submitReview(formData){
        let vm = this;
        vm.$confirm('是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          vm.apiAddress('/task/modify/submitAuditOutcomeDocumentPeriod', formData).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              vm.stateDialog('success', res.data.message);
              // 刷新
              // vm.$router.go(0);
              vm.$router.push('/page/work/default');
            } else {
              vm.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            vm.stateDialog('error', e);
          });
        }).catch(() => {});
      },
      /**
       * @method 提交审核请求
       * @param {FormData} formData 表单数据
       */
      submitDraftReview(formData){
        let vm = this;
        vm.apiAddress('/task/modify/saveDraftAuditOutcomeDocumentPeriod', formData).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            vm.$message({
              message: res.data.message,
              type: 'success',
              duration:750,
              center: true,
              onClose:function(){
                vm.$router.go(0);
              }
            });
          } else {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          }
        }).catch(e => {
          vm.stateDialog('error', e);
        });
      },
      getPreparationOfOutcomeDocumentsDetailInfo() {
        let vm = this;
        this.apiAddress("/task/query/searchTaskInfo", {
          "taskId": this.currentTransmittedData.taskId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            let data = res.data.dataVO;
              vm.currentTransmittedData = Object.assign({},this.currentTransmittedData,
              {
                taskType: data.taskType,//模板类型
                formworkType: data.formworkType,/* 成果文件类型 */
                formworkContent: data.formworkContent,/* 表单内容 */
                contractNum: data.contractNum,/* 合同编号 */
                projectName: data.projectName/* 项目名称 */
              });
            if (this.currentTransmittedData.periodType == '3') {
              this.prepareFlg = true;
            }
          }
        }).catch(e => {
          this.stateDialog('error', e)
        });
      },
      /**
       * 替换日期格式
       * @param date 需要转换的日期
       * @param char 间隔符 "/"、"-"
       * @returns {*}
       */
      formatDate(date, char) {
        if (typeof(date) == 'string' && date.indexOf('/') > -1) {
          return date;
        }
        let time = '';
        if (!this.tool.isNull(date)) {
          let year = date.getFullYear();
          let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          let day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
          // 如果char = 0，显示年月日，char = 1，只显示年份
          if ('0' == char) {
            time = year + '年' + month + '月' + day + '日';
          } else if ('1' == char) {
            time = year + '';
          } else if ('2' == char) {
            time = year + '年' + month + '月';
          } else {
            time = year + char + month + char + day + '';
          }
        }
        return time;
      }
    }
  };
</script>
<style lang="scss">

  .prepare {
    background-color: #ffffff;
  }

  .cost-index {
    .matterForm {
      border-radius: 5px;
      padding: 10px;
      background-color: #edf1f4;
      margin-bottom: 10px;
    }

    .tabs {
      .el-tabs__header {
        margin-bottom: 0;

        .el-tabs__nav {
          border: none;

          .el-tabs__item {
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
            padding: 0 40px;
            border: none;
          }

          .is-active {
            background-color: #edf1f4;
          }
        }
      }
    }
  }
</style>
<style lang="scss" scoped>
  .cost-index {
    border-radius: 5px;
    /* #EBEEF5 */
  }
</style>
