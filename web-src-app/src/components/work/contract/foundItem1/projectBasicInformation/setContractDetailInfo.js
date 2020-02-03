/* 设置合同表单信息 */
/**
 * @提交表单
 */
/*
currentTransmitData 节点信息
contractData 获取基础合同数据
*/
/**
 * @method 计算时间区间
 * @param {Object} targetDate
 */
import tool from '../../../../../tool.js'
function dateDiff(targetDate) {
  return parseInt(Math.abs(targetDate[1].getTime() - targetDate[0].getTime()) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
}

function setContractDetailInfo(currentTransmitData, contractData) {
  // 获取基础合同数据
  // let contractData = vm.$refs['baseContract'].getData();
  // 如果是预览
  // if (boo) {
  //   // 如果选择自定义合同并且是预览，则提示->"上传的自定义文件格式不支持，系统只支持扩展名为.doc、.docx的文件"
  //   if (contractData.contractType != "1") return vm.Toast("上传的自定义文件格式不支持，系统只支持扩展名为.doc、.docx的文件");
  //   // 模板合同标识
  //   let type = contractData.formworkType;
  //   // 如果为模板，但是没有选择模板的时候，则提示->"请选择模板类型"
  //   if (type != "0" && type != "1" && type != "2" && type != "3") return vm.Toast("请选择模板类型");
  // }
  const formdata = new FormData();
  // 项目编号 HT10192刘岩测试专用
  formdata.append('projectNum', currentTransmitData.data.label.slice(0, currentTransmitData.data.label.indexOf(
    '-')));
  // 下一节点
  // formdata.append('submitNode', nextTaskKey);
  // 合同id *
  formdata.append("contractId", currentTransmitData.data.id);
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
  // 项目类别

  // 投资金额
  formdata.append("investedAmount", contractData.investedAmount);
  // 是否暂定（0：否，1：是）
  formdata.append("isProvisional", contractData.isProvisional?'1':'0');
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
  formdata.append("isValueProvisional", contractData.isValueProvisional?'1':'0');
  // 咨询合同签订日期
  formdata.append("contractDate", contractData.contractDate);
  // 其他情况
  formdata.append("otherThings", contractData.otherThings);
  // 合作公司
  formdata.append("cooperateCompany", contractData.cooperateCompany);
  // 合同类型
  formdata.append("contractType", contractData.contractType);

  if (contractData.contractType == '0') {
    // 如果选择自定义合同，则上传附件
    // // 上传文件
    formdata.append("signPathFile", contractData.contractFile);
  } else if (contractData.contractType == '1') {
    // 如果选择模板合同，则添加合同标识
    //合同类型
    formdata.append("formworkType", contractData.formworkType);

    // 获取合同信息数据
    let contractInfoData = {};
    //返回后台结果
    let formworkContent = {};

    switch (contractData.formworkType) {
      case "0":
        // 造价咨询合同
        let formCostConsultingData = contractData.formCostConsulting;
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
        // 服务期限输入时，取得开始和结束日期
        // 取得开始日期
        let startDate = new Date(formCostConsultingData.viabilityStart);
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
        let endDate = new Date(formCostConsultingData.viabilityEnd);
        endYear = endDate.getFullYear().toString();
        endMonth = (endDate.getMonth() + 1).toString();
        if (endMonth.length == 1) {
          endMonth = "0" + endMonth;
        }
        endDay = endDate.getDate().toString();
        if (endDay.length == 1) {
          endDay = "0" + endDay;
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
        let paymentInfo = "";
        if (formCostConsultingData.pay == 0) {
          // 一次性支付
          paymentInfo = formCostConsultingData.input;
        } else {
          // 分期支付
          paymentInfo = formCostConsultingData.desc;
        }
        // 支付方式
        formworkContent.paymentMethod = formCostConsultingData.pay.toString();
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
        let retServiceAndWorkContentListData = formCostConsultingData.remuneration;

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
                // 工作内容类型固定为0:编制,1:审核,2:调整）
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
        let retAchievementFileListData = contractData.formCostConsulting.submitResults;
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
        let retProvideListData = contractData.formCostConsulting.provided;
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
              let provideTime = new Date(provideItemData.time);
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
        contractInfoData = contractData.formConstructionProjectBiddingAgency;
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
        contractInfoData = contractData.formProjectManagementConsultingService;
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
        contractInfoData = contractData.formSupervision;
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
        // 服务期限输入时，取得开始和结束日期
        startYearSupervision = contractInfoData.superviseDateStart.getFullYear().toString();
        startMonthSupervision = (contractInfoData.superviseDateStart.getMonth() + 1).toString();
        if (startMonthSupervision.length == 1) {
          startMonthSupervision = "0" + startMonthSupervision;
        }
        startDaySupervision = contractInfoData.superviseDateStart.getDate().toString();
        if (startDaySupervision.length == 1) {
          startDaySupervision = "0" + startDaySupervision;
        }
        // 取得结束日期
        endYearSupervision = contractInfoData.superviseDateEnd.getFullYear().toString();
        endMonthSupervision = (contractInfoData.superviseDateEnd.getMonth() + 1).toString();
        if (endMonthSupervision.length == 1) {
          endMonthSupervision = "0" + endMonthSupervision;
        }
        endDaySupervision = contractInfoData.superviseDateEnd.getDate().toString();
        if (endDaySupervision.length == 1) {
          endDaySupervision = "0" + endDaySupervision;
        }
        dayCount = (dateDiff([contractInfoData.superviseDateStart, contractInfoData.superviseDateEnd]) + 1).toString(); //（共计）
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
        if (contractInfoData.signDate) {
          signYear = contractInfoData.signDate.getFullYear().toString();
          signMonth = (contractInfoData.signDate.getMonth() + 1).toString();
          if (signMonth.length == 1) {
            signMonth = "0" + signMonth;
          }
          signDay = contractInfoData.signDate.getDate().toString();
          if (signDay.length == 1) {
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
  // --------------------------------模板内容结束----------------

  // 收款时间
  for (let i = 0; i < contractData.characteristic.length; i++) {
    formdata.append('predictRequestDateList[' + i + '].startDate', tool.formatDate(contractData.characteristic[
      i].startDate, 'yyyy-MM-dd'));
    formdata.append('predictRequestDateList[' + i + '].receivedAmount', contractData.characteristic[i].receivedAmount);
    formdata.append('predictRequestDateList[' + i + '].isProvisional', contractData.characteristic[i].isProvisional?'1':'0');
  }
  // 循环项目类别，根据项目类别分别赋值
  for (let i = 0, len = contractData.projectTypeList.length; i < len; i++) {
    // 项目类别
    formdata.append('projectTypeList[' + i + '].projectType', contractData.projectTypeList[i]);
    switch (parseInt(contractData.projectTypeList[i])) {
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
        // 人容量
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
  return formdata;
}
export default setContractDetailInfo;
