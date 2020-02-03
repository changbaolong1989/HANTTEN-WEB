<template>
  <div class="basic-information--work-common-components">
    <div class="form-block">
      <table width="96%" style="margin: 10px auto;">
        <tr>
          <td>项目编号：*</td>
          <td>{{curNodeInfo.projectNum}}</td>
          <td>合同编号：*</td>
          <td>由行政部提供生成</td>
        </tr>
        <tr>
          <td>项目名称：*</td>
          <td>{{form.projectName}}</td>
          <td>咨询业务类型：*</td>
          <td>
          {{(form.counselBusinessType==0&&form.counselBusinessType!=='')?'造价咨询':''}}
          {{form.counselBusinessType==1?'招标代理':''}}
          {{form.counselBusinessType==2?'项目管理（监理）':''}}
          </td>
        </tr>
        <tr>
          <td>委托人：*</td>
          <td>{{form.trusterName}}</td>
          <td>委托人性质：*</td>
          <td v-if="form.trusterTypeList.length">
            {{-1!=form.trusterTypeList.indexOf('0')?'政府部门':''}}
            {{-1!=form.trusterTypeList.indexOf('1')?'国企':''}}
            {{-1!=form.trusterTypeList.indexOf('2')?'上市公司':''}}
            {{-1!=form.trusterTypeList.indexOf('3')?'私企':''}}
            {{-1!=form.trusterTypeList.indexOf('4')?'其他':''}}
          </td>
          <td v-else></td>
        </tr>
        <tr>
          <td>委托方负责人：*</td>
          <td>{{form.trusterDutyUser}}</td>
          <td>委托方负责人电话：*</td>
          <td>{{form.trusterDutyPhone}}</td>
        </tr>
        <tr>
          <td>咨询人：*</td>
          <td colspan="3">{{form.consultUser}}</td>
        </tr>
        <tr>
          <td>第三方（合作单位）：</td>
          <td colspan="3">{{form.thirdParty}}</td>
        </tr>
        <tr>
          <td>项目类别：*</td>
          <td colspan="3" v-if="form.projectTypeList.length">
          {{-1!=form.projectTypeList.indexOf(0)?'住宅':''}}
          {{-1!=form.projectTypeList.indexOf(1)?'商业建筑':''}}
          {{-1!=form.projectTypeList.indexOf(2)?'酒店及休闲娱乐':''}}
          {{-1!=form.projectTypeList.indexOf(3)?'工业建筑':''}}
          {{-1!=form.projectTypeList.indexOf(4)?'教育':''}}
          {{-1!=form.projectTypeList.indexOf(5)?'文化及体育设施':''}}
          {{-1!=form.projectTypeList.indexOf(6)?'电力':''}}
          {{-1!=form.projectTypeList.indexOf(7)?'铁路、道路及桥梁建设':''}}
          {{-1!=form.projectTypeList.indexOf(8)?'水务':''}}
          {{-1!=form.projectTypeList.indexOf(9)?'公用事业及城市基础设施':''}}
          {{-1!=form.projectTypeList.indexOf(10)?'水运、交通':''}}
          {{-1!=form.projectTypeList.indexOf(11)?'其他':''}}
          </td>
          <td colspan="3" v-else></td>
        </tr>
        <tr>
          <td>项目性质：*</td>
          <td v-if="form.projectNatureList.length">
            {{form.projectNatureList.indexOf('0')?'新建':''}}
            {{form.projectNatureList.indexOf('1')?'扩建':''}}
            {{form.projectNatureList.indexOf('2')?'改建':''}}
          </td>
          <td v-else></td>
          <td>投资金额（万元）：*</td>
          <td>{{form.isProvisional==="1"?"暂定":""}}{{form.investedAmount?form.investedAmount+'万元':''}}</td>
        </tr>
        <tr>
          <td>项目特征/指标：*</td>
          <td colspan="3">
            <p v-if="controlObj[0]">
              住宅：<br>
              占地面积：{{form.residential.landArea}}平方米<br>
              建筑面积：{{form.residential.floorArea}}平方米<br>
              檐高：{{form.residential.eavesHeight}}米<br>
              建筑控制高度：{{form.residential.buildingControlHeight}}米<br>
              容积率：{{form.residential.volumeRatio}}米<br>
              住宅类别：
                        {{(form.residential.homeType==0&&form.residential.homeType!=='')?'商品房':''}}
                        {{form.residential.homeType==1?'公租房':''}}
                        {{form.residential.homeType==2?'共有产权':''}}
            </p>
            <p v-if="controlObj[1]">
              商业建筑：<br>
              占地面积：{{form.commercialBuildings.landArea}}平方米<br>
              建筑面积：{{form.commercialBuildings.floorArea}}平方米<br>
              檐高：{{form.commercialBuildings.eavesHeight}}米<br>
              建筑控制高度：{{form.commercialBuildings.buildingControlHeight}}米<br>
            </p>
            <p v-if="controlObj[2]">
              酒店及休闲娱乐：<br>
              占地面积：{{form.hotelAndLeisureEntertainment.landArea}}平方米<br>
              建筑面积：{{form.hotelAndLeisureEntertainment.floorArea}}平方米<br>
              檐高：{{form.hotelAndLeisureEntertainment.eavesHeight}}米<br>
              建筑控制高度：{{form.hotelAndLeisureEntertainment.buildingControlHeight}}米<br>
              房间数量：{{form.hotelAndLeisureEntertainment.roomNum}}个<br>
            </p>
            <p v-if="controlObj[3]">
              工业建筑：<br>
              占地面积：{{form.industrialArchitecture.landArea}}平方米<br>
              建筑面积：{{form.industrialArchitecture.floorArea}}平方米<br>
              层数：{{form.industrialArchitecture.level}}层<br>
              檐高：{{form.industrialArchitecture.eavesHeight}}米<br>
              跨度：{{form.industrialArchitecture.span}}<br>
              生产状况：{{form.industrialArchitecture.produceCondition}}<br>
            </p>
            <p v-if="controlObj[4]">
              教育：<br>
              占地面积：{{form.education.landArea}}平方米<br>
              建筑面积：{{form.education.floorArea}}平方米<br>
              绿化率：{{form.education.makeGreenRatio}}%<br>
              绿色等级：{{form.education.greenLevel}}级<br>
            </p>
            <p v-if="controlObj[5]">
              文化及体育设施：<br>
              占地面积：{{form.culturalAndSportsFacilities.landArea}}平方米<br>
              建筑面积：{{form.culturalAndSportsFacilities.floorArea}}平方米<br>
              人容量：{{form.culturalAndSportsFacilities.persionCapacity}}<br>
            </p>
            <p v-if="controlObj[6]">
              电力：<br>
              装机容量：{{form.power.installationCapacity}}<br>
              建筑面积：{{form.power.floorArea}}平方米<br>
              占地面积：{{form.power.landArea}}平方米<br>
              发电机变压器参数数量：{{form.power.transformersNum}}<br>
              线缆敷设长度：{{form.power.cableLayingLength}}平方米<br>
              线缆敷设方式：{{form.power.cableLayingMethod}}<br>
              输电量：{{form.power.transmissionCapacity}}<br>
            </p>
            <p v-if="controlObj[7]">
              铁路、道路及桥梁建设：<br>
              等级：{{form.railwayRoadBridgeConstruction.grade}}<br>
              全线（桥）长度：{{form.railwayRoadBridgeConstruction.bridgeLength}}<br>
              设计速度：{{form.railwayRoadBridgeConstruction.designSpeed}}<br>
              轨道类型：{{form.railwayRoadBridgeConstruction.orbitalType}}<br>
              桥梁类型：{{form.railwayRoadBridgeConstruction.bridgeType}}<br>
            </p>
            <p v-if="controlObj[8]">
              水务：<br>
              正常运用水位：{{form.water.normalOperatingWaterLevel}}<br>
              总库容：{{form.water.totalStorageCapacity}}平方米<br>
              最大坝高：{{form.water.maximumDamHeight}}<br>
              电站装机容量：{{form.water.installedCapacityOfPowerStation}}<br>
            </p>
            <p v-if="controlObj[9]">
              公用事业及城市基础设施：<br>
              占地面积：{{form.publicUtilitiesUrbanInfrastructure.landArea}}平方米<br>
              建筑面积：{{form.publicUtilitiesUrbanInfrastructure.floorArea}}平方米<br>
              其他（长度/规模/范围等）：{{form.publicUtilitiesUrbanInfrastructure.others}}<br>
            </p>
            <p v-if="controlObj[10]">
              水运、交通：<br>
              港口（码头）吞吐量：{{form.waterTransportationAndTransportation.portThroughput}}<br>
              航道（闸机）通过量：{{form.waterTransportationAndTransportation.channelThroughput}}<br>
              主要货物的流量流向：{{form.waterTransportationAndTransportation.flowDirection}}<br>
              分航线运量：{{form.waterTransportationAndTransportation.branchLineVolume}}<br>
              船舶流量：{{form.waterTransportationAndTransportation.shipFlow}}<br>
              集疏方式：{{form.waterTransportationAndTransportation.gatheringAndDistributingWays}}<br>
            </p>
            <p v-if="controlObj[11]">
              其他：<br>
              其他：{{form.others.others}}<br>
            </p>
          </td>
        </tr>
        <tr>
          <td>项目地点：*</td>
          <td colspan="3">{{form.projectAddress}}</td>
        </tr>
        <tr>
          <td>合同性质：*</td>
          <td colspan="3" v-if="form.contractNatureList.length">
            {{form.contractNatureList.indexOf('0')>=0?'双方合同':''}}
            {{form.contractNatureList.indexOf('1')>=0?'三方合同':''}}
            {{form.contractNatureList.indexOf('2')>=0?'补充合同':''}}
            {{form.contractNatureList.indexOf('3')>=0?'单独核算':''}}
            {{form.contractNatureList.indexOf('4')>=0?'非单独核算':''}}
            </td>
            <td colspan="3" v-else></td>
        </tr>
        <tr>
          <td>合同确定方式：*</td>
          <td colspan="3" v-if="form.determinateMethodList.length">
            {{form.determinateMethodList.indexOf('0')>=0?'招标':''}}
            {{form.determinateMethodList.indexOf('1')>=0?'财政委托':''}}
            {{form.determinateMethodList.indexOf('2')>=0?'审计委托':''}}
            {{form.determinateMethodList.indexOf('3')>=0?'商务洽谈':''}}
            {{form.determinateMethodList.indexOf('4')>=0?'战略合作':''}}
            {{form.determinateMethodList.indexOf('5')>=0?'法院委托':''}}
            {{form.determinateMethodList.indexOf('6')>=0?'其他':''}}
            </td>
            <td colspan="3" v-else></td>
        </tr>
        <tr>
          <td>拟定合同金额（元）：* </td>
          <td>{{form.isValueProvisional==="1"?"暂定":""}}{{form.contractAmount?form.contractAmount+'元':''}}</td>
          <td>预计回款情况：*</td>
          <td>
          <template v-if="form.characteristic&&form.characteristic.length&&form.characteristic[0].startDate">
            <p v-for="(item, i) in form.characteristic" :key="i">
              {{tool.formatDate(item.startDate,'yyyy年MM月dd日')}},{{'第' + (i + 1) + '次收款'}},预计收款金额{{item.isProvisional==="1"?"暂定":""}}{{item.receivedAmount}}元
            </p>
          </template>
          </td>
        </tr>
        <tr>
          <td>拟定合同负责人：* </td>
          <td>{{form.dutyPeople}}</td>
          <td>咨询合同签订日期：*</td>
          <td>{{form.contractDate?tool.formatDate(form.contractDate,'yyyy年MM月dd日'):''}}</td>
        </tr>
        <tr>
          <td>其他情况说明：</td>
          <td colspan="3">{{form.otherThings}}</td>
        </tr>
        <tr>
          <td>拟定人：*</td>
          <td>{{form.planner}}</td>
          <td>合同模板：*</td>
          <td>
            {{form.planner&&form.contractType==0?'自定义合同':''}}
            {{form.planner&&form.contractType==1?'模板合同':''}}
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
  import formData from '../foundItem1/contract/contractForm.js';
  export default{
    props:['_form','nodeInfo'],
    data(){
      return {
        sourceData:formData,// 表单源数据
        form:this.tool.deepCopy(this._form.form, this.tool.deepCopy),// 表单(表格)数据
        controlObj:this.tool.deepCopy(this._form.controlObj, this.tool.deepCopy), // 控制对象
        curNodeInfo:this.tool.deepCopy(this.nodeInfo, this.tool.deepCopy)// 节点信息
      };
    },
    watch:{
      _form:{
        handler(){
          let vm = this;
          // 赋值表单(数据)
          vm.form =  this.tool.deepCopy(vm._form.form, this.tool.deepCopy);
          // 赋值项目阶段显示控制(数据)
          vm.controlObj =  this.tool.deepCopy(vm._form.controlObj, this.tool.deepCopy);
        },
        immediate:true
      },
      nodeInfo:{
        handler(){
          let vm = this;
          vm.curNodeInfo = vm.tool.deepCopy(vm.nodeInfo, vm.tool.deepCopy) // 节点信息
        },
        immediate:true,
      }
    },
    methods:{
      /**
       * @method 设置实例数据
       * @param {Object} data 实例数据
       */
      setData(data){
        let vm = this;
        // 设置实例表单数据
        vm._setFormData(data);
      },
      /**
       * @method 负责转换并给出符合要求的表单
       * @param {Object} form 合同信息数据
       */
      _setFormData(form){
          let vm = this;
          let retDataVO = form;
          let _form = vm.tool.deepCopy(vm.sourceData.baseContract, vm.tool.deepCopy);;
          let projectTypeList = [];
          let controlObj = {};
          // 项目类别及其属性赋值
          if (retDataVO.projectTypeList && retDataVO.projectTypeList.length) {
            let tempProjectFeatureObj = {};
            // 项目类型不为空时
            for (let i = 0; i < retDataVO.projectTypeList.length; i++) {
              let projectType = parseInt(retDataVO.projectTypeList[i].projectType);
              projectTypeList.push(projectType);
              // 项目属性显示
              controlObj[projectType] = true;
              // 初始化临时项目属性
              tempProjectFeatureObj = {};
              tempProjectFeatureObj = retDataVO.projectFeatureList[i];
              // 项目属性赋值
              switch (retDataVO.projectTypeList[i].projectType) {
                case '0':
                  // 住宅-+
                  _form.residential = tempProjectFeatureObj;
                  break;
                case '1':
                  // 商业建筑
                  _form.commercialBuildings = tempProjectFeatureObj;
                  break;
                case '2':
                  // 酒店及休闲娱乐
                  _form.hotelAndLeisureEntertainment = tempProjectFeatureObj;
                  break;
                case '3':
                  // 工业建筑
                  _form.industrialArchitecture = tempProjectFeatureObj;
                  break;

                case '4':
                  // 教育
                  _form.education = tempProjectFeatureObj;
                  break;

                case '5':
                  // 文化及体育设施
                  _form.culturalAndSportsFacilities = tempProjectFeatureObj;
                  break;

                case '6':
                  // 电力
                  _form.power = tempProjectFeatureObj;
                  break;

                case '7':
                  // 铁路、道路及桥梁建设
                  _form.railwayRoadBridgeConstruction = tempProjectFeatureObj;
                  break;

                case '8':
                  // 水务
                  _form.water = tempProjectFeatureObj;
                  break;

                case '9':
                  // 公用事业及城市基础设施
                  _form.publicUtilitiesUrbanInfrastructure = tempProjectFeatureObj;
                  break;

                case '10':
                  // 水运、交通
                  _form.waterTransportationAndTransportation = tempProjectFeatureObj;
                  break;
                // 其他
                case '11':
                  // 其他
                  _form.others = tempProjectFeatureObj;
                  break;
              }
            }
          }

          // 预计收款时间赋值
          if (retDataVO.predictRequestDateList != null && retDataVO.predictRequestDateList.length > 0) {
            // 预计收款时间不为空时
            // 初始化预计收款时间
            _form.characteristic = [];
            let predictRequestDateItem;
            for (let i = 0; i < retDataVO.predictRequestDateList.length; i++) {
              predictRequestDateItem = {};
              // 预计收款时间
              predictRequestDateItem.startDate = new Date(retDataVO.predictRequestDateList[i].startDate);
              // 预计收款金额
              predictRequestDateItem.receivedAmount = retDataVO.predictRequestDateList[i].receivedAmount;
              // 是否暂定
              predictRequestDateItem.isProvisional = retDataVO.predictRequestDateList[i].isProvisional;
              _form.characteristic.push(predictRequestDateItem);
            }
          }
          // 基本合同数据
          _form = Object.assign({}, _form, retDataVO, { projectTypeList: projectTypeList });
          vm.controlObj = vm.tool.deepCopy(controlObj, vm.tool.deepCopy);
          vm.form = vm.tool.deepCopy(_form, vm.tool.deepCopy);
      }
    }
  }
</script>

<style lang="scss" scoped>
table{
  tr{
    background-color:#FFF;

    td{
      border:1px solid #EBEEF5;
      padding: 8px 10px;
      min-width:100px;

      p{
        font-size: 14px!important;
      }
    }
  }
}
</style>
