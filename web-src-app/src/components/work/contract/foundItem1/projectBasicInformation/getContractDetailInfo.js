/* 获取合同表单信息 */
function getContractDetailInfo(res,vm) {
    // 如果返回成功，则绘制数据，否则提示失败信息
      // 取得合同详细信息
      let retDataVO = res.data.dataVO;
      // 合同Index页数据
      let indexContract = vm.currentTransmitData.indexContract;
      // 基本合同组件
      let tempBaseContract = vm.currentTransmitData.baseContract;
      // 项目类型列表
      let projectTypeList = [];
      // 项目类别及其属性赋值
      if (retDataVO.projectTypeList && retDataVO.projectTypeList.length) {
        // 项目类型不为空时
        for (let i = 0; i < retDataVO.projectTypeList.length; i++) {
          let projectType = parseInt(retDataVO.projectTypeList[i].projectType);
          projectTypeList.push(projectType);
          // 项目属性显示
          tempBaseContract.controlObj[projectType] = true;
          // 初始化临时项目属性
          var tempProjectFeatureObj = {};
          tempProjectFeatureObj = retDataVO.projectFeatureList[i];
          // 项目属性赋值
          switch (retDataVO.projectTypeList[i].projectType) {
            case "0":
              // 住宅-+
              tempBaseContract.form.residential = tempProjectFeatureObj;
              break;
            case "1":
              // 商业建筑
              tempBaseContract.form.commercialBuildings = tempProjectFeatureObj;
              break;
            case "2":
              // 酒店及休闲娱乐
              tempBaseContract.form.hotelAndLeisureEntertainment = tempProjectFeatureObj;
              break;
            case "3":
              // 工业建筑
              tempBaseContract.form.industrialArchitecture = tempProjectFeatureObj;
              break;

            case "4":
              // 教育
              tempBaseContract.form.education = tempProjectFeatureObj;
              break;

            case "5":
              // 文化及体育设施
              tempBaseContract.form.culturalAndSportsFacilities = tempProjectFeatureObj
              break;

            case "6":
              // 电力
              tempBaseContract.form.power = tempProjectFeatureObj;
              break;

            case "7":
              // 铁路、道路及桥梁建设
              tempBaseContract.form.railwayRoadBridgeConstruction = tempProjectFeatureObj;
              break;

            case "8":
              // 水务
              tempBaseContract.form.water = tempProjectFeatureObj;
              break;

            case "9":
              // 公用事业及城市基础设施
              tempBaseContract.form.publicUtilitiesUrbanInfrastructure = tempProjectFeatureObj;
              break;

            case "10":
              // 水运、交通
              tempBaseContract.form.waterTransportationAndTransportation = tempProjectFeatureObj;
              break;
              // 其他
            case "11":
              // 其他
              tempBaseContract.form.others = tempProjectFeatureObj;
              break;
          }
        }
      }

      // 预计收款时间赋值
      if (retDataVO.predictRequestDateList != null && retDataVO.predictRequestDateList.length > 0) {
        // 预计收款时间不为空时
        // 初始化预计收款时间
        tempBaseContract.form.characteristic = [];
        let predictRequestDateItem;
        for (let i = 0; i < retDataVO.predictRequestDateList.length; i++) {
          predictRequestDateItem = {};
          // 预计收款时间
          predictRequestDateItem.startDate = retDataVO.predictRequestDateList[i].startDate;
          // 预计收款金额
          predictRequestDateItem.receivedAmount = retDataVO.predictRequestDateList[i].receivedAmount;
          // 是否暂定
          predictRequestDateItem.isProvisional = !!parseInt(retDataVO.predictRequestDateList[i].isProvisional);
          tempBaseContract.form.characteristic.push(predictRequestDateItem);
        }
      }

      // index页数据
      indexContract.form = Object.assign(indexContract.form, retDataVO);
      // 基本合同数据
      tempBaseContract.form.formworkType = retDataVO.formworkType;
      tempBaseContract.form = Object.assign({}, tempBaseContract.form, retDataVO, {
        projectTypeList: projectTypeList
      });
      // 基本合同数据的暂定转换
      // 投资金额暂定
      tempBaseContract.form.isProvisional = !!parseInt(tempBaseContract.form.isProvisional);
      // 拟定合同金额暂定
      tempBaseContract.form.isValueProvisional = !!parseInt(tempBaseContract.form.isValueProvisional);
      tempBaseContract.controlObj = Object.assign({}, tempBaseContract.controlObj);
      // 合同类型以及模板类型赋值
      if (retDataVO.contractType == '0') {
        // 选择自定义合同时  TODO
        tempBaseContract.form.contractFile = {};
        tempBaseContract.form.contractFile.name = retDataVO.contractFileName;
      } else if (retDataVO.contractType == '1') {
        // 选择模板合同时
        // 模板内容json转换成对象
        let formworkContentObj = JSON.parse(retDataVO.formworkContent);
        // 判断合同模板类型
        switch (retDataVO.formworkType) {
          case "0":
            // 造价咨询合同
            let tempFormCostConsulting = {};
            // 省份
            tempFormCostConsulting.province = formworkContentObj.provinceName;
            // 地区
            tempFormCostConsulting.region = formworkContentObj.districtName;
            // 工程规模
            tempFormCostConsulting.generalize = formworkContentObj.projectScale;
            // 资金来源
            tempFormCostConsulting.fund = formworkContentObj.fundSource;
            // 建设工期或周期
            tempFormCostConsulting.period = formworkContentObj.buildPeriod;
            // 双方约定的服务范围及工作内容
            tempFormCostConsulting.scope = formworkContentObj.workContent;
            // 服务期限
            if (formworkContentObj.startYear != "") {
              // 拼成开始和结束日期
              let tempStartDate = new Date(parseInt(formworkContentObj.startYear), parseInt(formworkContentObj.startMonth) -
                1, parseInt(formworkContentObj.startDay));
              let tempEndDate = new Date(parseInt(formworkContentObj.endYear), parseInt(formworkContentObj.endMonth) -
                1, parseInt(formworkContentObj.endDay));
              tempFormCostConsulting.deadline = [];
              // 设置开始和结束日期
              tempFormCostConsulting.deadline.push(tempStartDate);
              tempFormCostConsulting.deadline.push(tempEndDate);
              tempFormCostConsulting.viabilityStart = tempStartDate;
              tempFormCostConsulting.viabilityEnd = tempEndDate;
            }

            // 总合同份数
            tempFormCostConsulting.copies = formworkContentObj.totalContractCount;
            // 委托人合同份数
            tempFormCostConsulting.entrust = formworkContentObj.trusterContractCount;
            // 咨询人合同份数
            tempFormCostConsulting.consult = formworkContentObj.consultantContractCount;
            // 委托人代表
            tempFormCostConsulting.representative = formworkContentObj.trusterRepresent;
            // 团队人员
            tempFormCostConsulting.personnel = formworkContentObj.personCount;

            // 支付方式
            if (formworkContentObj.paymentMethod != null && formworkContentObj.paymentMethod != "") {
              tempFormCostConsulting.pay = formworkContentObj.paymentMethod;
            }

            if (formworkContentObj.paymentMethod == "0") {
              // 一次性支付时
              // 咨询成果报告出具几日内支付
              tempFormCostConsulting.input = formworkContentObj.paymentInfo;
            } else if (formworkContentObj.paymentMethod == "1") {
              // 分期支付时
              // 分期信息
              tempFormCostConsulting.desc = formworkContentObj.paymentInfo;
            }
            // 争议解决
            tempFormCostConsulting.dispute = formworkContentObj.courtName;
            // 送达接收人
            tempFormCostConsulting.recipient = formworkContentObj.receiver;
            // 送达地点
            tempFormCostConsulting.site = formworkContentObj.deliveryPlace;
            // 酬金及计取方式为
            tempFormCostConsulting.reward = formworkContentObj.calculationMethod;

            // 服务范围及工作内容、酬金列表
            let tempServiceAndWorkContentList = formworkContentObj.serviceAndWorkContentList;
            // 预计收款时间赋值
            if (tempServiceAndWorkContentList != null && tempServiceAndWorkContentList.length > 0) {

              // 服务范围及工作内容、酬金列表不为空时  tempFormCostConsulting
              //                    let tempAform = this.$refs['contract'].$refs['formCostConsulting'].$refs['serviceAndWorkContent']._data.Aform ;
              let tempAform = tempFormCostConsulting.remuneration = [];
              // 初始化预计收款时间
              let dataItem;
              for (let i = 0; i < tempServiceAndWorkContentList.length; i++) {
                dataItem = {};
                // 服务阶段
                dataItem.serve = tempServiceAndWorkContentList[i].servicePhaseId + "," +
                  tempServiceAndWorkContentList[i].servicePhase + "," + tempServiceAndWorkContentList[i].serviceRange;
                // 工作内容
                dataItem.content = [];
                if (tempServiceAndWorkContentList[i].workContentList && tempServiceAndWorkContentList[i].workContentList
                  .length > 0) {
                  for (let j = 0; j < tempServiceAndWorkContentList[i].workContentList.length; j++) {
                    if (tempServiceAndWorkContentList[i].workContentList[j].isCheckedFlg == "1") {
                      // 选中状态
                      dataItem.content.push(parseInt(tempServiceAndWorkContentList[i].workContentList[j].workContentType) +
                        1);
                    }
                  }
                }


                // 收费基数
                dataItem.charge = {};
                dataItem.charge.money = tempServiceAndWorkContentList[i].chargeRadix;
                // 收费基数暂定
                dataItem.charge.state = tempServiceAndWorkContentList[i].isChargeRadixProvisional;
                // 收费标准
                dataItem.standard = {};
                dataItem.standard.money = tempServiceAndWorkContentList[i].chargeStandard;
                // 收费标准暂定
                dataItem.standard.state = tempServiceAndWorkContentList[i].isChargeStandardProvisional;
                dataItem.sum = {};
                // 酬金金额
                dataItem.sum.money = tempServiceAndWorkContentList[i].remuneration;
                // 酬金金额暂定
                dataItem.sum.state = tempServiceAndWorkContentList[i].isRemunerationProvisional;
                // 备注
                dataItem.remark = tempServiceAndWorkContentList[i].remark;
                let manyCheckBoxReg = /其他|工程实施阶段造价/g;
                let oneCheckBoxReg = /资金使用计划/g;
                // 工作内容列表
                let workContentList = [];
                // 工作内容元素
                let workContentItem = {};

                if (manyCheckBoxReg.test(tempServiceAndWorkContentList[i].serviceRange)) {
                  // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                  dataItem.input = true;
                  // 设置输入文本内容
                  dataItem.details = tempServiceAndWorkContentList[i].workContent;
                  // checkbox隐藏
                  dataItem.checkbox = false;
                  dataItem.checkbox1 = false;
                } else if (oneCheckBoxReg.test(tempServiceAndWorkContentList[i].serviceRange)) {
                  // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                  dataItem.input = false;
                  // 设置输入文本
                  dataItem.details = "";
                  // 多个checkbox隐藏
                  dataItem.checkbox = false;
                  // 1个checkbox隐藏
                  dataItem.checkbox1 = true;
                } else {
                  // 工作内容部分显示文字(0:显示checkBox,1:显示文字)
                  dataItem.input = false;
                  // 设置输入文本
                  dataItem.details = "";
                  // 多个checkbox隐藏
                  dataItem.checkbox = true;
                  // 1个checkbox隐藏
                  dataItem.checkbox1 = false;
                }
                tempAform.push(dataItem);
              }
            }

            // 咨询人提交成果列表
            let tempAchievementFileList = formworkContentObj.achievementFileList;
            // 预计收款时间赋值
            if (tempAchievementFileList != null && tempAchievementFileList.length > 0) {
              // 咨询人提交成果列表不为空时
              //                    let tempBform = this.$refs['contract'].$refs['formCostConsulting'].$refs['achievementFile']._data.Bform;
              let tempBform = tempFormCostConsulting.submitResults = [];
              // 初始化列表
              let dataItem;
              for (let i = 0; i < tempAchievementFileList.length; i++) {
                dataItem = {};
                // 服务范围
                dataItem.serve = tempAchievementFileList[i].servicePhaseId + "," + tempAchievementFileList[i].servicePhase;
                // 成果文件名称
                dataItem.name = tempAchievementFileList[i].achievementFileName;
                // 成果文件组成
                dataItem.constitute = tempAchievementFileList[i].achievementFileCompose;
                // 提交时间
                dataItem.date = tempAchievementFileList[i].submitDate;
                // 份数
                dataItem.copies = tempAchievementFileList[i].fileCount;
                // 质量标准
                dataItem.standard = tempAchievementFileList[i].qualityStandard;
                tempBform.push(dataItem);
              }
            }

            // 委托人提供资料列表
            let tempProvideDataList = formworkContentObj.provideDataList;
            if (tempProvideDataList != null && tempProvideDataList.length > 0) {
              // 委托人提供资料列表不为空时
              //                    let tempCform = this.$refs['contract'].$refs['formCostConsulting'].$refs['provideData']._data.Cform;
              let tempCform = tempFormCostConsulting.provided = [];
              // 初始化列表
              let dataItem;
              for (let i = 0; i < tempProvideDataList.length; i++) {
                dataItem = {};
                // 委托人名称
                dataItem.name = tempProvideDataList[i].trusterName;
                // 资料分数
                dataItem.copies = tempProvideDataList[i].dataCount;
                // 提供时间
                let provideYear = "";
                let provideMonth = "";
                let provideDay = "";
                let time = "";
                if (tempProvideDataList[i].provideDate) {
                  // 提供时间
                  let provideDate = tempProvideDataList[i].provideDate.replace("年", "-").replace("月", "-").replace(
                    "日", "").split("-");
                  provideYear = provideDate[0];
                  provideMonth = provideDate[1];
                  provideDay = provideDate[2];
                  time = new Date(provideYear, parseInt(provideMonth) - 1, provideDay);
                }
                dataItem.time = time;
                // 备注
                dataItem.remark = tempProvideDataList[i].remark;

                tempCform.push(dataItem);
              }
            }


            Object.assign(vm.currentTransmitData.formCostConsulting.form, tempFormCostConsulting);
            break;
          case "1":
            // 建设工程招标代理合同
            Object.assign(vm.currentTransmitData.formConstructionProjectBiddingAgency.form, formworkContentObj);
            break;
          case "2":
            // 工程管理咨询服务合同
            Object.assign(vm.currentTransmitData.formProjectManagementConsultingService.form, formworkContentObj);
            break;
          case "3":
            // 监理合同
            let tempFormSupervision = vm.currentTransmitData.formSupervision.form;
            Object.assign(tempFormSupervision, formworkContentObj);
            // 监理期限
            // 开始时间
            tempFormSupervision.superviseDateStart = new Date(parseInt(formworkContentObj.startYear), parseInt(
              formworkContentObj.startMonth) - 1, parseInt(formworkContentObj.startDay));
            // 结束时间
            tempFormSupervision.superviseDateEnd = new Date(parseInt(formworkContentObj.endYear), parseInt(
              formworkContentObj.endMonth) - 1, parseInt(formworkContentObj.endDay));
            // 订立时间
            if (formworkContentObj.signYear) {
              // 订立时间
              tempFormSupervision.signDate = new Date(parseInt(formworkContentObj.signYear), parseInt(
                formworkContentObj.signMonth) - 1, formworkContentObj.signDay);
            }
            break;
        }
      }
      vm.currentTransmitData = vm.tool.deepCopy(vm.currentTransmitData, vm.tool.deepCopy);
      return vm.currentTransmitData;

}
export default getContractDetailInfo;
