<template>
  <div class="contract" >
    <div class="form-block" v-if="!transmittedData.onlyOfficeFlg">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p>项目编号:{{transmittedData.projectNumber}}</p>
        </el-col>
        <el-col :span="12">
          <p>合同编号:{{transmittedData.contractNumber}}</p>
        </el-col>
      </el-row>
    </div>


    <div class="img-failure" v-if="showImg">
      <img v-bind:src="imgSrc" :alt="imgAlt" />
      <p style="text-align: center;">{{ imgAlt }}</p>
    </div>
   <base-contract ref="baseContract"
   :_form="theFormData.baseContract"
   :disabled="theFormDisabled"
   v-if="!transmittedData.onlyOfficeFlg"
   @changeContractType="changeContractType"></base-contract>

    <el-form class="form" ref="form" :model="form" :disabled="theFormDisabled" v-if="!transmittedData.onlyOfficeFlg" :rules="rules">

      <div class="form-block" v-if="'0'==form.contractType">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="" prop="materialName">
              <el-upload class="avatar-uploader upload" ref="upload" action="#" :show-file-list="false" :on-change="handleAvatarSuccess"
                :auto-upload="false" :before-upload="beforeAvatarUpload" :data="form">
                <el-button style="color:#409EFF"><span class="iconfont" style="font-size:12px;">&#xe614;</span>上传合同附件</el-button>
              </el-upload>
                <el-input placeholder="请输入文件名称" v-model="form.materialName" :disabled="true" style="margin-top:5px;"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block" v-if="'1'==form.contractType">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-radio v-model="form.formworkType" label="0">造价咨询合同</el-radio>
            <el-radio v-model="form.formworkType" label="1">建设工程招标代理合同</el-radio>
            <el-radio v-model="form.formworkType" label="2">工程管理咨询服务合同</el-radio>
            <el-radio v-model="form.formworkType" label="3">监理合同</el-radio>
          </el-col>
        </el-row>

        <form-cost-consulting
          ref="formCostConsulting"
          v-if="form&&'0' == form.formworkType"
          :theFormData="theFormData.formCostConsulting" :disabled="theFormDisabled"></form-cost-consulting>

        <form-construction-project-bidding-agency
          ref="formConstructionProjectBiddingAgency"
          v-if="form&&'1' == form.formworkType"
          :theFormData="theFormData.formConstructionProjectBiddingAgency" :disabled="theFormDisabled"></form-construction-project-bidding-agency>

        <form-project-management-consulting-service
          ref="formProjectManagementConsultingService"
          v-if="form&&'2' == form.formworkType"
          :theFormData="theFormData.formProjectManagementConsultingService" :disabled="theFormDisabled"></form-project-management-consulting-service>

        <form-supervision
          ref="formSupervision"
          v-if="form&&'3' == form.formworkType"
          :theFormData="theFormData.formSupervision" :disabled="theFormDisabled"></form-supervision>
      </div>

    </el-form>

    <!-- onlyOffice -->
    <div class="form-block form-block__row--auto-width" v-if="transmittedData.onlyOfficeFlg" style="height: 700px;">
      <office :file="taskMaterialInfo"></office>
    </div>
    <!-- 文件浏览 -->
    <!--<dialog-browse-file :file="file" ref="dialogBrowseFile" type="contract" :formData="formData"></dialog-browse-file>-->
    <preview-file :file="file" ref="previewFile" type="request" :formData="formData"></preview-file>
  </div>
</template>

<script>
  import Vue from 'vue';
  /* 基本合同信息*/
  import BaseContract from '../../../common/components/baseContract.vue';
  /* 造价咨询合同 */
  import formCostConsulting from '../../customContract/formCostConsulting';
  /* 建设工程招标代理合同 */
  import formConstructionProjectBiddingAgency from '../../customContract/formConstructionProjectBiddingAgency';
  /* 工程管理咨询服务合同 */
  import formProjectManagementConsultingService from '../../customContract/formProjectManagementConsultingService';
  /* 监理合同 */
  import formSupervision from '../../customContract/formSupervision';
  /* onlyOffice组件 */
  import office from '../../../common/components/office';
  // 浏览文件
  import dialogBrowseFile from '../../../common/components/dialogBrowseFile.vue';
  import previewFile from '../../../common/components/previewFile';

  export default {
    props: ['transmittedData', 'theFormData','theFormDisabled'],
    components: {
      'base-contract': BaseContract,
      'form-cost-consulting': formCostConsulting,
      'form-construction-project-bidding-agency': formConstructionProjectBiddingAgency,
      'form-project-management-consulting-service': formProjectManagementConsultingService,
      'form-supervision': formSupervision,
      'office':office,
      // 浏览文件
      "dialog-browse-file": dialogBrowseFile,
      "previewFile": previewFile
    },
    updated() {
      let vm = this;
      vm.$nextTick(function () {
        vm.bus.$off('changeform');
        // 提交触发
        vm.bus.$on('changeform', val => {
          let baseContract = vm.$refs['baseContract'];
          let baseContractForm = null;
          if(baseContract)baseContractForm = baseContract.$refs['form'];
          if(baseContractForm){
            // 中央合同
            vm.$refs['form'].validate((valid) => {if(valid)vm.checkForm(val)});
            // 基本合同
            baseContractForm.validate(function(valid){if(valid)vm.checkForm(val)});
          }
        });
        vm.bus.$off('contractPreview');
        // 提交触发
        vm.bus.$on('contractPreview', val => {
          let baseContract = vm.$refs['baseContract'];
          let baseContractForm = null;
          if(baseContract)baseContractForm = baseContract.$refs['form'];
          if(baseContractForm){
             vm.submit(val,"preview");
          }
        });
      });
    },
    methods: {
      /**
       * @method 校验表单
       */
      checkForm(val){
        this.checkIndex++;
        if (this.checkIndex>=2)this.submit(val,"");
      },
      // 新建时存储公告文件
      beforeAvatarUpload(file, fileList) {
        this.filedata = file;
        return false;
      },
      // 新建公告文件名称展示
      handleAvatarSuccess(file, fileList) {
        this.form.signPathFile = URL.createObjectURL(file.raw);
        this.form.materialName = file.name;
      },
      //计算时间区间
      dateDiff(targetDate) {
        return parseInt(Math.abs(targetDate[1].getTime() - targetDate[0].getTime()) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
      },
      submit(val,preview) {
        // 获取基础合同数据
        const contractData = this.$refs['baseContract'].form;

        // 如果是预览
        if (preview == "preview") {
          // 如果选择自定义合同并且是预览，则提示->"自定义类型不能预览"
          if(this.form.contractType != "1"){
            this.stateDialog('error', "自定义类型不能预览")
            return;
          }
          // 模板合同标识
          let type = this.form.formworkType;
          // 如果为模板，但是没有选择模板的时候，则提示->"请选择模板类型"
          if (type != "0" && type != "1" && type != "2" && type != "3") {
            this.stateDialog('error', "请选择模板类型")
            return;
          }
        }

        const formdata = new FormData();

        // 下一个节点
        let nextTaskKey = val.nextTaskKey;

        //  如果选择跳级审核
        if (val.above) {
            // 得到下一个节点
            nextTaskKey = this.getNextStepNode(nextTaskKey)
        }
        // 修改时间
        formdata.append('updateDate',val.updateDate);
        // 项目编号
        formdata.append('projectNum',this.transmittedData.projectNumber);
        // 下一节点
        formdata.append('submitNode',nextTaskKey);
        // 合同id
        formdata.append("contractId", this.transmittedData.id);
        // 项目名称
        formdata.append("projectName", contractData.projectName);
        // 咨询业务类型
        formdata.append("counselBusinessType", contractData.counselBusinessType);
        // 咨询业务阶段
        formdata.append("counselTypePeriod", contractData.counselTypePeriod);
        // 合同盖章类型
        formdata.append("contractSealType", contractData.contractSealType);
        // 项目地点
        formdata.append("projectAddress", contractData.projectAddress);

        // 投资金额
        formdata.append("investedAmount", contractData.investedAmount);

        // 是否暂定（0：否，1：是）
        formdata.append("isProvisional", contractData.isProvisional);
        // 项目性质(升序排列)
        formdata.append("projectNatureList", contractData.projectNatureList.sort());
        // 委托人
        formdata.append("trusterName", contractData.trusterName);
        // 委托人性质(升序排列)
        formdata.append("trusterTypeList", contractData.trusterTypeList.sort());
        // 委托方负责人
        formdata.append("trusterDutyUser", contractData.trusterDutyUser);
        // 委托方负责人电话
        formdata.append("trusterDutyPhone", contractData.trusterDutyPhone);
        // 咨询人
        formdata.append("consultUser", contractData.consultUser);
        // 第三方（合作单位）
        formdata.append("thirdParty", contractData.thirdParty);
        // 扉页咨询人
        formdata.append("titlePageConsultant", contractData.titlePageConsultant);
        // 拟定合同负责人
        formdata.append("dutyPeople", contractData.dutyPeople);
        // 拟定人
        formdata.append("planner", contractData.planner);
        // 合同性质(升序排列)
        formdata.append("contractNatureList", contractData.contractNatureList.sort());
        // 确认方式(升序排列)
        formdata.append("determinateMethodList", contractData.determinateMethodList.sort());
        // 拟定合同金额
        formdata.append("contractAmount", contractData.contractAmount);
        // 拟定合同金额是否暂定（0：否，1：是）
        formdata.append("isValueProvisional", contractData.isValueProvisional);
        // 咨询合同签订日期
        formdata.append("contractDate", contractData.contractDate);

        // 其他情况
        formdata.append("otherThings", contractData.otherThings);

        // 合作公司
        formdata.append("cooperateCompany", contractData.cooperateCompany);

        // 合同类型
        formdata.append("contractType", this.form.contractType);

        // 如果选择自定义合同，则上传附件
        if (this.form.contractType == '0') {
          this.$refs.upload.submit();
          // 上传文件
          formdata.append("signPathFile", this.filedata);

          // 如果选择模板合同，则添加合同标识
        } else if (this.form.contractType == '1') {
          // 模板合同标识
          let formworkType = this.form.formworkType;

          //合同类型
          formdata.append("formworkType", formworkType);

          // 获取合同信息数据
          let contractInfoData = {};
          //返回后台结果
          let formworkContent = {};

          switch (formworkType) {
            case "0":
              // 造价咨询合同
              let formCostConsultingData = this.$refs["formCostConsulting"]._data.form;
              //                let formCostConsultingData = this.theFormData.formCostConsulting;

              // 省份
              formworkContent.provinceName = formCostConsultingData.province;
              // 地区
              formworkContent.districtName = formCostConsultingData.region;
              // 工程概况
              formworkContent.projectScale = formCostConsultingData.generalize;

              // 资金来源
              formworkContent.fundSource = formCostConsultingData.fund;
              // 建设工期或周期
              formworkContent.buildPeriod = formCostConsultingData.period;
              // 双方约定的服务范围及工作内容
              formworkContent.workContent = formCostConsultingData.scope;
              // 服务期限
              let startYear = "";
              let startMonth = "";
              let startDay = "";
              let endYear = "";
              let endMonth = "";
              let endDay = "";
              let deadline = formCostConsultingData.deadline;
              if (deadline) {
                // 服务期限输入时，取得开始和结束日期
                // 取得开始日期
                let startDate = deadline[0];
                startYear = startDate.getFullYear().toString();
                startMonth = (startDate.getMonth() + 1).toString();
                if (startMonth.length == 1) {
                  startMonth = "0" + startMonth;
                }
                startDay = startDate.getDate().toString();
                if (startDay.length == 1) {
                  startDay = "0" + startDay;
                }
                // 取得结束日期
                let endDate = deadline[1];
                endYear = endDate.getFullYear().toString();
                endMonth = (endDate.getMonth() + 1).toString();
                if (endMonth.length == 1) {
                  endMonth = "0" + endMonth;
                }
                endDay = endDate.getDate().toString();
                if (endDay.length == 1) {
                  endDay = "0" + endDay;
                }
              }
              // 设置开始日期
              formworkContent.startYear = startYear;
              formworkContent.startMonth = startMonth;
              formworkContent.startDay = startDay;
              // 设置结束日期
              formworkContent.endYear = endYear;
              formworkContent.endMonth = endMonth;
              formworkContent.endDay = endDay;
              // 总合同份数
              formworkContent.totalContractCount = formCostConsultingData.copies;
              // 委托人合同份数
              formworkContent.trusterContractCount = formCostConsultingData.entrust;
              // 咨询人合同份数
              formworkContent.consultantContractCount = formCostConsultingData.consult;
              // 委托人代表
              formworkContent.trusterRepresent = formCostConsultingData.representative;
              // 团队人员
              formworkContent.personCount = formCostConsultingData.personnel;
              // 支付方式
              let paymentMethod = "";
              let paymentInfo = "";
              if (formCostConsultingData.pay == 0) {
                // 一次性支付
                paymentInfo = formCostConsultingData.input;
                paymentMethod = formCostConsultingData.pay.toString();
              } else if (formCostConsultingData.pay == 1) {
                // 分期支付
                paymentInfo = formCostConsultingData.desc;
                paymentMethod = formCostConsultingData.pay.toString();
              }
              // 支付方式
              formworkContent.paymentMethod = paymentMethod;
              // 分期信息/咨询成果报告出具几日内支付
              formworkContent.paymentInfo = paymentInfo;
              // 争议解决
              formworkContent.courtName = formCostConsultingData.dispute;
              // 送达接收人
              formworkContent.receiver = formCostConsultingData.recipient;
              // 送达地点
              formworkContent.deliveryPlace = formCostConsultingData.site;
              // 酬金及计取方式为
              formworkContent.calculationMethod = formCostConsultingData.reward;

              // 服务范围及工作内容、酬金列表
              let retServiceAndWorkContentListData = this.$refs["formCostConsulting"].$refs["serviceAndWorkContent"]._data
                .Aform.data;
              //                  let retServiceAndWorkContentListData = this.theFormData.formCostConsulting
              let serviceAndWorkContentItemData = {};
              if (retServiceAndWorkContentListData && retServiceAndWorkContentListData.length > 0) {
                // 定义返回结果服务范围及工作内容、酬金列表
                formworkContent.serviceAndWorkContentList = [];
                let serviceAndWorkContentItem = {};
                // 存在服务范围及工作内容、酬金列表时
                for (let i = 0; i < retServiceAndWorkContentListData.length; i++) {
                  serviceAndWorkContentItem = {};
                  // 页面临时变量
                  serviceAndWorkContentItemData = retServiceAndWorkContentListData[i];
                  // 服务阶段组合临时变量
                  let servicePhaseTempList = serviceAndWorkContentItemData.serve.split(",");
                  // 服务阶段id临时变量
                  let servicePhaseIdTemp = servicePhaseTempList[0];
                  // 服务阶段名称临时变量
                  let servicePhaseTemp = servicePhaseTempList[1];
                  // 服务范围名称临时变量
                  let serviceRangeTemp = servicePhaseTempList[2];

                  // 服务阶段id
                  serviceAndWorkContentItem.servicePhaseId = servicePhaseIdTemp;
                  // 服务阶段名称
                  serviceAndWorkContentItem.servicePhase = servicePhaseTemp;
                  // 服务范围名称
                  serviceAndWorkContentItem.serviceRange = serviceRangeTemp;

                  // 工作内容
                  let manyCheckBoxReg = /其他|工程实施阶段造价/g;
                  let oneCheckBoxReg = /资金使用计划/g;
                  // 工作内容列表
                  let workContentList = [];
                  // 工作内容元素
                  let workContentItem = {};

                  if (manyCheckBoxReg.test(serviceRangeTemp)) {
                    // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                    serviceAndWorkContentItem.workContentFlg = "1";
                    // 服务范围名称为【其他】或【工程实施阶段造价】时，显示输入文本
                    serviceAndWorkContentItem.workContent = serviceAndWorkContentItemData.details;
                  } else if (oneCheckBoxReg.test(serviceRangeTemp)) {
                    // 工作内容部分显示checkBox(0:显示checkBox,1:显示文字)
                    serviceAndWorkContentItem.workContentFlg = "0";
                    // 工作内容类型固定为0：编制（0:编制,1:审核,2:调整）
                    workContentItem.workContentType = "0";
                    // 选中状态默认 0：不选中（0:未选中，1：选中）
                    workContentItem.isCheckedFlg = "0";

                    // 判断是否选中（0:未选中，1：选中）
                    if (serviceAndWorkContentItemData.content.length > 0) {
                      // 列表中包含编辑时 （0:未选中，1：选中）
                      workContentItem.isCheckedFlg = "1";
                    }
                    workContentList.push(workContentItem);
                    serviceAndWorkContentItem.workContentList = workContentList;
                  } else {
                    // 工作内容部分显示checkBox(0:显示checkBox,1:显示文字)
                    serviceAndWorkContentItem.workContentFlg = "0";

                    // 设置工作内容类型默认值
                    for (let j = 0; j < 3; j++) {
                      workContentItem = {};
                      // 工作内容类型固定为0：编制（0:编制,1:审核,2:调整）
                      workContentItem.workContentType = j.toString();
                      // 选中状态默认 0：不选中（0:未选中，1：选中）
                      workContentItem.isCheckedFlg = "0";
                      workContentList.push(workContentItem);
                    }

                    // 工作内容类型
                    if (serviceAndWorkContentItemData.content.length > 0) {
                      for (let k = 0; k < serviceAndWorkContentItemData.content.length; k++) {
                        if (serviceAndWorkContentItemData.content[k] == 1) {
                          // 1: 编辑（1:编制,2:审核,3:调整）,为选中状态
                          workContentList[0].isCheckedFlg = "1";
                        } else if (serviceAndWorkContentItemData.content[k] == 2) {
                          // 2: 审核（1:编制,2:审核,3:调整）,为选中状态
                          workContentList[1].isCheckedFlg = "1";
                        } else if (serviceAndWorkContentItemData.content[k] == 3) {
                          // 2: 调整（1:编制,2:审核,3:调整）,为选中状态
                          workContentList[2].isCheckedFlg = "1";
                        }
                      }
                    }

                    serviceAndWorkContentItem.workContentList = workContentList;
                  }
                  // 收费基数
                  serviceAndWorkContentItem.chargeRadix = serviceAndWorkContentItemData.charge.money;
                  // 收费基数是否暂定(是否暂定 0：未选中 1：选中)
                  serviceAndWorkContentItem.isChargeRadixProvisional = serviceAndWorkContentItemData.charge.state;

                  // 收费标准
                  serviceAndWorkContentItem.chargeStandard = serviceAndWorkContentItemData.standard.money;
                  // 收费标准是否暂定(是否暂定 0：未选中 1：选中)
                  serviceAndWorkContentItem.isChargeStandardProvisional = serviceAndWorkContentItemData.standard.state;

                  // 酬金金额
                  serviceAndWorkContentItem.remuneration = serviceAndWorkContentItemData.sum.money;
                  //  酬金金额是否暂定(是否暂定 0：未选中 1：选中)
                  serviceAndWorkContentItem.isRemunerationProvisional = serviceAndWorkContentItemData.sum.state;


                  // 备注
                  serviceAndWorkContentItem.remark = serviceAndWorkContentItemData.remark;

                  formworkContent.serviceAndWorkContentList.push(serviceAndWorkContentItem);
                }
              }

              // 咨询人提交成果
              let retAchievementFileListData = this.$refs["formCostConsulting"].$refs["achievementFile"]._data.Bform.data;
              let achievementFileItemData = {};
              if (retAchievementFileListData && retAchievementFileListData.length > 0) {
                // 定义返回结果咨询人提交成果列表
                formworkContent.achievementFileList = [];
                let achievementFileItem = {};
                // 存在咨询人提交成果时
                for (let i = 0; i < retAchievementFileListData.length; i++) {
                  achievementFileItem = {};
                  achievementFileItemData = {};
                  // 页面临时变量
                  achievementFileItemData = retAchievementFileListData[i];

                  // 服务阶段组合临时变量
                  let servicePhaseTempList = achievementFileItemData.serve.split(",");
                  // 服务阶段id临时变量
                  let servicePhaseIdTemp = servicePhaseTempList[0];
                  // 服务阶段名称临时变量
                  let servicePhaseTemp = servicePhaseTempList[1];

                  // 服务阶段id
                  achievementFileItem.servicePhaseId = servicePhaseIdTemp;
                  // 服务阶段名称
                  achievementFileItem.servicePhase = servicePhaseTemp;
                  // 成果文件名称
                  achievementFileItem.achievementFileName = achievementFileItemData.name;
                  // 成果文件组成
                  achievementFileItem.achievementFileCompose = achievementFileItemData.constitute;
                  // 提交时间
                  achievementFileItem.submitDate = achievementFileItemData.date;
                  // 份数
                  achievementFileItem.fileCount = achievementFileItemData.copies;
                  // 质量标准
                  achievementFileItem.qualityStandard = achievementFileItemData.standard;

                  formworkContent.achievementFileList.push(achievementFileItem);

                }
              }

              // 委托人提供资料
              let retProvideListData = this.$refs["formCostConsulting"].$refs["provideData"]._data.Cform.data;
              let provideItemData = {};
              if (retProvideListData && retProvideListData.length > 0) {
                // 定义返回结果咨询人提交成果列表
                formworkContent.provideDataList = [];
                let provideItem = {};
                // 存在委托人提供资料时
                for (let i = 0; i < retProvideListData.length; i++) {
                  provideItem = {};
                  provideItemData = {};
                  // 页面临时变量
                  provideItemData = retProvideListData[i];

                  // 委托人名称
                  provideItem.trusterName = provideItemData.name;
                  // 资料份数
                  provideItem.dataCount = provideItemData.copies;
                  // 提供时间
                  let provideYear = "";
                  let provideMonth = "";
                  let provideDay = "";
                  let provideDate = "";
                  if (provideItemData.time) {
                    // 提供时间
                    let provideTime = provideItemData.time;
                    provideYear = provideTime.getFullYear().toString();
                    provideMonth = (provideTime.getMonth() + 1).toString();
                    if (provideMonth.length == 1) {
                      provideMonth = "0" + provideMonth;
                    }
                    provideDay = provideTime.getDate().toString();
                    if (provideDay.length == 1) {
                      provideDay = "0" + provideDay;
                    }
                    provideDate = provideYear + "年" + provideMonth + "月" + provideDay + "日";
                  }
                  provideItem.provideDate = provideDate;
                  // 备注
                  provideItem.remark = provideItemData.remark;
                  formworkContent.provideDataList.push(provideItem);
                }
              }
              break;
            case "1":
              contractInfoData = this.$refs["formConstructionProjectBiddingAgency"].$data.form;
              formworkContent.provinceName = contractInfoData.provinceName; // 省份
              formworkContent.districtName = contractInfoData.districtName; //地区
              formworkContent.scale = contractInfoData.scale; // 规模
              formworkContent.biddingScale = contractInfoData.biddingScale; // 招标规模
              formworkContent.agentWorkContent = contractInfoData.agentWorkContent; // 代理工作内容
              formworkContent.signAddress = contractInfoData.signAddress; // 合同订立地点
              formworkContent.agentRange = contractInfoData.agentRange; // 代理范围
              formworkContent.agentContent = contractInfoData.agentContent.toString(); // 代理内容
              formworkContent.paymentMethod = contractInfoData.paymentMethod; // 代理报酬的支付方式
              break;
            case "2":
              contractInfoData = this.$refs["formProjectManagementConsultingService"].$data.form;
              formworkContent.provinceName = contractInfoData.provinceName; // 省份
              formworkContent.districtName = contractInfoData.districtName; //地区
              formworkContent.serviceContent = contractInfoData.serviceContent; // 服务内容
              formworkContent.consultAddress = contractInfoData.consultAddress; // 咨询工程师的地址
              formworkContent.telNumber = contractInfoData.telNumber; // 电话号码
              formworkContent.faxNumber = contractInfoData.faxNumber; // 传真号码
              formworkContent.floorage = contractInfoData.floorage; // 项目建筑面积
              formworkContent.useNature = contractInfoData.useNature; // 项目使用性质
              formworkContent.designPay = contractInfoData.designPay; // 设计阶段服务报酬
              formworkContent.constructionPay = contractInfoData.constructionPay; // 施工阶段服务报酬
              formworkContent.settlementPay = contractInfoData.settlementPay; // 结算阶段服务报酬
              formworkContent.advancePayment = contractInfoData.advancePayment; // 预付款
              formworkContent.progressPayment = contractInfoData.progressPayment; //进度款
              formworkContent.balance = contractInfoData.balance; // 余款
              formworkContent.isDesignPayProvisional = contractInfoData.isDesignPayProvisional; // 设计阶段服务报酬暂定
              formworkContent.isConstructionPayProvisional = contractInfoData.isConstructionPayProvisional; // 施工阶段服务报酬暂定
              formworkContent.isSettlementPayProvisional = contractInfoData.isSettlementPayProvisional; // 结算阶段服务报酬暂定
              formworkContent.isAdvancePaymentProvisional = contractInfoData.isAdvancePaymentProvisional; // 预付款暂定
              formworkContent.isProgressPaymentProvisional = contractInfoData.isProgressPaymentProvisional; // 进度款暂定
              formworkContent.isBalanceProvisional = contractInfoData.isBalanceProvisional; // 余款暂定
              formworkContent.addClauses = contractInfoData.addClauses; // 附加条款
              break;
            case "3":
              contractInfoData = this.$refs["formSupervision"].$data.form;
              formworkContent.supervisor = contractInfoData.supervisor; //监理人
              formworkContent.projectScale = contractInfoData.projectScale; //工程规模
              formworkContent.instalCost = contractInfoData.instalCost; //工程概算投资额或建筑安装工程费
              formworkContent.isInstalCostProvisional = contractInfoData.isInstalCostProvisional; //工程概算投资额或建筑安装工程费暂定
              formworkContent.engineerName = contractInfoData.engineerName; //姓名：3（汉字）
              formworkContent.IDNumber = contractInfoData.IDNumber; //身份证号：18（数字）
              formworkContent.registrationNumber = contractInfoData.registrationNumber; //注册号
              formworkContent.signAmount = contractInfoData.signAmount; //请输入签约酬金与补偿费用
              formworkContent.isSignAmountProvisional = contractInfoData.isSignAmountProvisional; //请输入签约酬金与补偿费用 暂定

              let startYearSupervision = "";
              let startMonthSupervision = "";
              let startDaySupervision = "";
              let endYearSupervision = "";
              let endMonthSupervision = "";
              let endDaySupervision = "";
              let dayCount = "";
              let superviseDate = contractInfoData.superviseDate;
              if (superviseDate) {
                // 服务期限输入时，取得开始和结束日期
                // 取得开始日期
                let startDate = superviseDate[0];
                startYearSupervision = startDate.getFullYear().toString();
                startMonthSupervision =(startDate.getMonth() + 1).toString();
                if(startMonthSupervision.length == 1){
                  startMonthSupervision = "0" + startMonthSupervision;
                }
                startDaySupervision = startDate.getDate().toString();
                if(startDaySupervision.length == 1){
                  startDaySupervision = "0" + startDaySupervision;
                }
                // 取得结束日期
                let endDate = superviseDate[1];
                endYearSupervision = endDate.getFullYear().toString();
                endMonthSupervision =(endDate.getMonth() + 1).toString();
                if(endMonthSupervision.length == 1){
                  endMonthSupervision = "0" + endMonthSupervision;
                }
                endDaySupervision = endDate.getDate().toString();
                if(endDaySupervision.length == 1){
                  endDaySupervision = "0" + endDaySupervision;
                }

                dayCount = (this.dateDiff(superviseDate) + 1).toString(); //（共计）
              }

              formworkContent.startYear = startYearSupervision;
              formworkContent.startMonth = startMonthSupervision;
              formworkContent.startDay = startDaySupervision;
              formworkContent.endYear = endYearSupervision;
              formworkContent.endMonth = endMonthSupervision;
              formworkContent.endDay = endDaySupervision;
              formworkContent.dayCount = dayCount;

              let signYear = "";
              let signMonth = "";
              let signDay = "";
              if(contractInfoData.signDate){
                signYear = contractInfoData.signDate.getFullYear().toString();
                signMonth = (contractInfoData.signDate.getMonth() + 1).toString();
                if(signMonth.length == 1){
                  signMonth = "0" + signMonth;
                }
                signDay = contractInfoData.signDate.getDate().toString();
                if(signDay.length == 1){
                  signDay = "0" + signDay;
                }
              }

              formworkContent.signYear = signYear;
              formworkContent.signMonth = signMonth;
              formworkContent.signDay = signDay;

              formworkContent.signAddress = contractInfoData.signAddress; //订立地点
              formworkContent.truBusinessLicenseNumber = contractInfoData.truBusinessLicenseNumber; //营业执照号
              formworkContent.truAddress = contractInfoData.truAddress; //住所
              formworkContent.truPostalCode = contractInfoData.truPostalCode; //邮政编码
              formworkContent.truOpenBank = contractInfoData.truOpenBank; //开户银行
              formworkContent.truAcountNumber = contractInfoData.truAcountNumber; //账号
              formworkContent.truTelNumber = contractInfoData.truTelNumber; //电话
              formworkContent.truFaxNumber = contractInfoData.truFaxNumber; //传真
              formworkContent.truEMail = contractInfoData.truEMail; //电子邮箱
              formworkContent.supBusinessLicenseNumber = contractInfoData.supBusinessLicenseNumber; //营业执照号
              formworkContent.supCertificateNumber = contractInfoData.supCertificateNumber; //资质证书编号
              formworkContent.supAddress = contractInfoData.supAddress; //住所
              formworkContent.supPostalCode = contractInfoData.supPostalCode; //邮政编码
              formworkContent.supOpenBank = contractInfoData.supOpenBank; //开户银行
              formworkContent.supAcountNumber = contractInfoData.supAcountNumber; //账号
              formworkContent.supTelNumber = contractInfoData.supTelNumber; //电话
              formworkContent.supFaxNumber = contractInfoData.supFaxNumber; //传真
              formworkContent.supEMail = contractInfoData.supEMail; //电子邮箱
              formworkContent.specialClauses = contractInfoData.specialClauses; //专用条款
              formworkContent.trusterAgent = contractInfoData.trusterAgent; //委托人代表为
              formworkContent.paymentMethod = contractInfoData.paymentMethod; //监理酬金具体支付方式
              formworkContent.disputeResolution = contractInfoData.disputeResolution; //争议解决
              break;
            default:
              break;
          }
          //合同信息
          formdata.append("formworkContent", JSON.stringify(formworkContent));
        }

        // 收款时间
        for (var i = 0; i < contractData.characteristic.length; i++) {
          formdata.append('predictRequestDateList[' + i + '].startDate', this.tool.formatDate(contractData.characteristic[i].startDate,'yyyy-MM-dd'));
          formdata.append('predictRequestDateList[' + i + '].receivedAmount', contractData.characteristic[i].receivedAmount);
          formdata.append('predictRequestDateList[' + i + '].isProvisional', contractData.characteristic[i].isProvisional);
        }

        // 循环项目类别，根据项目类别分别赋值
        for (var i = 0; i < contractData.projectTypeList.length; i++) {

          // 项目类别
          formdata.append('projectTypeList[' + i + '].projectType', contractData.projectTypeList[i]);
          switch (contractData.projectTypeList[i]) {

            // 住宅
            case 0:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.residential
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.residential
                .floorArea);
              // 檐高
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].eavesHeight', contractData.residential
                .eavesHeight);
              // 建筑控制高度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].buildingControlHeight',
                contractData.residential.buildingControlHeight);
              // 容积率
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].volumeRatio', contractData.residential
                .volumeRatio);
              // 住宅类别
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].homeType', contractData.residential
                .homeType);

              break;
              // 商业建筑
            case 1:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.commercialBuildings
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.commercialBuildings
                .floorArea);
              // 檐高
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].eavesHeight', contractData.commercialBuildings
                .eavesHeight);
              // 建筑控制高度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].buildingControlHeight',
                contractData.commercialBuildings.buildingControlHeight);

              break;
              // 酒店及休闲娱乐
            case 2:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.hotelAndLeisureEntertainment
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.hotelAndLeisureEntertainment
                .floorArea);
              // 檐高
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].eavesHeight', contractData.hotelAndLeisureEntertainment
                .eavesHeight);
              // 建筑控制高度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].buildingControlHeight',
                contractData.hotelAndLeisureEntertainment.buildingControlHeight);
              // 建筑控制高度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].roomNum', contractData.hotelAndLeisureEntertainment
                .roomNum);

              break;
              // 工业建筑
            case 3:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.industrialArchitecture
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.industrialArchitecture
                .floorArea);
              // 檐高
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].eavesHeight', contractData.industrialArchitecture
                .eavesHeight);
              // 层数
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].level', contractData.industrialArchitecture
                .level);
              // 跨度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].span', contractData.industrialArchitecture
                .span);
              // 生产状况
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].produceCondition', contractData
                .industrialArchitecture.produceCondition);

              break;
              // 教育
            case 4:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.education
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.education
                .floorArea);
              // 绿化率
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].makeGreenRatio', contractData.education
                .makeGreenRatio);
              // 绿色等级
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].greenLevel', contractData.education
                .greenLevel);

              break;
              // 文化及体育设施
            case 5:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.culturalAndSportsFacilities
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.culturalAndSportsFacilities
                .floorArea);
              // 绿化率
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].persionCapacity', contractData.culturalAndSportsFacilities
                .persionCapacity);

              break;
              // 电力
            case 6:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.power.landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.power.floorArea);
              // 装机容量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].installationCapacity',
                contractData.power.installationCapacity);
              // 发电机变压器参数数量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].transformersNum', contractData.power
                .transformersNum);
              // 线缆敷设长度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].cableLayingLength',
                contractData.power.cableLayingLength);
              // 线缆敷设方式
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].cableLayingMethod',
                contractData.power.cableLayingMethod);
              // 输电量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].transmissionCapacity',
                contractData.power.transmissionCapacity);

              break;
              // 铁路、道路及桥梁建设
            case 7:

              // 等级
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].grade', contractData.railwayRoadBridgeConstruction
                .grade);
              // 全线（桥）长度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].bridgeLength', contractData.railwayRoadBridgeConstruction
                .bridgeLength);
              // 设计速度
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].designSpeed', contractData.railwayRoadBridgeConstruction
                .designSpeed);
              // 轨道类型
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].orbitalType', contractData.railwayRoadBridgeConstruction
                .orbitalType);
              // 桥梁类型
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].bridgeType', contractData.railwayRoadBridgeConstruction
                .bridgeType);

              break;
              // 水务
            case 8:

              // 正常运用水位
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].normalOperatingWaterLevel',
                contractData.water.normalOperatingWaterLevel);
              // 总库容
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].totalStorageCapacity',
                contractData.water.totalStorageCapacity);
              // 最大坝高
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].maximumDamHeight', contractData
                .water.maximumDamHeight);
              // 电站装机容量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i +
                '].installedCapacityOfPowerStation', contractData.water.installedCapacityOfPowerStation);

              break;
              // 公用事业及城市基础设施
            case 9:

              // 占地面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].landArea', contractData.publicUtilitiesUrbanInfrastructure
                .landArea);
              // 建筑面积
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].floorArea', contractData.publicUtilitiesUrbanInfrastructure
                .floorArea);
              // 其他（长度/规模/范围等）
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].others', contractData.publicUtilitiesUrbanInfrastructure
                .others);

              break;
              // 水运、交通
            case 10:

              // 港口（码头）吞吐量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].portThroughput', contractData.waterTransportationAndTransportation
                .portThroughput);
              // 航道（闸机）通过量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].channelThroughput',
                contractData.waterTransportationAndTransportation.channelThroughput);
              // 主要货物的流量流向
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].flowDirection', contractData.waterTransportationAndTransportation
                .flowDirection);
              // 分航线运量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].branchLineVolume', contractData
                .waterTransportationAndTransportation.branchLineVolume);
              // 船舶流量
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].shipFlow', contractData.waterTransportationAndTransportation
                .shipFlow);
              // 集疏方式
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].gatheringAndDistributingWays',
                contractData.waterTransportationAndTransportation.gatheringAndDistributingWays);

              break;
              // 其他
            case 11:

              // 其他
              formdata.append('projectTypeList[' + i + '].projectFeatureList[' + i + '].others', contractData.others.others);

              break;

            default:
              break;

          }
        }

        // 预览
        if(preview == "preview"){
          // 表示该预览为合同
          formdata.append('type', "contract");
          this.formData = formdata;
          this.file = {
            "materialPath":true,
            "isGeneratedFile":true,
            "noSubmitPreview":"1"
          }
          this.$refs.previewFile.show = true;

        // 提交审核
        } else {
          // 提交审核
          this.apiAddress("/contract/modify/updateContractInfo", formdata).then(res => {
            // 如果返回成功，则绘制数据，否则提示失败信息
            if (res.data.status == "success") {
              this.stateDialog('success', res.data.message);
              // 触发事件,重新刷新项目树
              // this.bus.$emit('asideRefresh',{checkedKey:val.contractId});
              this.$router.push('/page/work/default');
              // 隐藏基本合同信息
              this.contract = false;
              // 清除合同编号
              this.contractNumber = "";
              // 销毁当前实例
              this.$destroy;
            } else {
              this.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            this.stateDialog('error', e)
          });
        }

      },
      /**
       * @method 改变合同类型
       */
      changeContractType(val){
        let vm = this;
        vm.form.contractType = val;
      }
    },
    data() {
      return {
        checkIndex:0,// 校验表单数量
        form: this.theFormData.indexContract.form,
        componentTemplate:'',// 动态模板
        gutter: 40,
        projectNumber: '',
        showImg: false,
        // office
        taskMaterialInfo:this.tool.deepCopy(this.transmittedData.taskMaterialInfo, this.tool.deepCopy),
        rules: {
        	materialName: [{
        		required: true,
        		message: '请选择公告文件',
        		trigger: 'blur'
        	}]
        },
        file:'',// 待浏览文件
        formData: new FormData(),
      }
    },
    watch:{
      'theFormData.indexContract':{
        handler(){// index合同数据
          this.form = this.theFormData.indexContract.form
        },
        deep: true,
        immediate: true
      },
        'transmittedData':{
            handler(){// index合同数据
              let vm = this;
                vm.taskMaterialInfo = vm.tool.deepCopy(vm.transmittedData.taskMaterialInfo, vm.tool.deepCopy);
            },
             deep: true,
            immediate: true
        }
    }
  };
</script>
<style lang="scss">
    @import url('../../../common/css/other.css');
    /* 文件列表*/
</style>
<style lang="scss" scoped>

</style>
