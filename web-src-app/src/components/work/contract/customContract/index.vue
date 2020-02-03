<template>
  <!--合同模板-->
  <fieldset class="index" :disabled="theFormDisabled">
    <!-- <div class="exhibition">
      <ul>
        <li>
          <span>项目编号</span>
          <span>HT100220HTGS12</span>
        </li>
        <li>
          <span>合同编号</span>
          <span>QS01assdf</span>
        </li>
      </ul>
    </div> -->
    <form class="projectForm" ref="form">
      <div class="form-item">
        <label for="">项目名称</label>
        <input type="text" placeholder="请输入项目名称" v-model="projectForm.projectName" v-validate="'required'" data-vv-name="项目名称"/>
      </div>
      <div class="form-item">
        <m-select v-model="projectForm.counselBusinessType" :data="{title: '咨询业务类型',options: dataList.consultancy}"
          unique="value" display="label" @change="changeConsultancy" v-validate="'required'" data-vv-name="咨询业务类型">
        </m-select>
      </div>
      <div class="form-item" v-show="radios.length">
        <m-select v-model="projectForm.counselTypePeriod" :data="{title: '咨询类型阶段',options: radios}"
          unique="value" display="label" v-validate="'required'" data-vv-name="咨询类型阶段">
        </m-select>
      </div>

      <div class="form-item">
        <m-select v-model="projectForm.contractSealType"
          :data="{title: '合同盖章类型',options: [{label:'委托代理人章',value:'1'},{label:'法定代表人章',value:'2'}]}"
          unique="value" display="label" v-validate="'required'" data-vv-name="合同盖章类型">
        </m-select>
      </div>

      <div class="form-item">
        <label>项目地点</label>
        <textarea v-model="projectForm.projectAddress" placeholder="请输入项目地点" v-validate="'required'" data-vv-name="项目地点"></textarea>
      </div>

      <div class="form-item">
        <m-select v-model="projectForm.projectTypeList" :data="{title: '项目类别',options: dataList.classification}"
          unique="value" display="label" :multiple="true" v-validate="'required'" data-vv-name="项目类别">
        </m-select>
      </div>

      <div class="form-item" v-show="projectForm.projectTypeList&&projectForm.projectTypeList.length">
        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('0')">
          <p>住宅</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.residential.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.residential.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">檐高</label>
            <div>
              <input type="text" placeholder="请输入檐高" v-model="projectForm.residential.eavesHeight" v-validate="'required'" data-vv-name="檐高"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑控制高度</label>
            <div>
              <input type="text" placeholder="请输入建筑控制高度" v-model="projectForm.residential.buildingControlHeight" v-validate="'required'" data-vv-name="建筑控制高度"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">容积率</label>
            <div>
              <input type="text" placeholder="请输入容积率" v-model="projectForm.residential.volumeRatio" v-validate="'required'" data-vv-name="容积率"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
           <m-select v-model="projectForm.residential.homeType" :data="{title: '住宅类别',options: dataList.residential}"
              unique="value" display="label" v-validate="'required'" data-vv-name="住宅类别">
            </m-select>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('1')">
          <p>商业建筑</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.commercialBuildings.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.commercialBuildings.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">檐高</label>
            <div>
              <input type="text" placeholder="请输入檐高" v-model="projectForm.commercialBuildings.eavesHeight" v-validate="'required'" data-vv-name="檐高"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑控制高度</label>
            <div>
              <input type="text" placeholder="请输入建筑控制高度" v-model="projectForm.commercialBuildings.buildingControlHeight" v-validate="'required'" data-vv-name="建筑控制高度"/>
              <span>米</span>
            </div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('2')">
          <p>酒店及休闲娱乐</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.hotelAndLeisureEntertainment.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.hotelAndLeisureEntertainment.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">檐高</label>
            <div>
              <input type="text" placeholder="请输入檐高" v-model="projectForm.hotelAndLeisureEntertainment.eavesHeight" v-validate="'required'" data-vv-name="檐高"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑控制高度</label>
            <div>
              <input type="text" placeholder="请输入建筑控制高度" v-model="projectForm.hotelAndLeisureEntertainment.buildingControlHeight" v-validate="'required'" data-vv-name="建筑控制高度"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">房间数量</label>
            <div>
              <input type="text" placeholder="请输入房间数量" v-model="projectForm.hotelAndLeisureEntertainment.roomNum" v-validate="'required'" data-vv-name="房间数量"/>
              <span>个</span>
            </div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('3')">
          <p>工业建筑</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.industrialArchitecture.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.industrialArchitecture.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">檐高</label>
            <div>
              <input type="text" placeholder="请输入檐高" v-model="projectForm.industrialArchitecture.eavesHeight" v-validate="'required'" data-vv-name="檐高"/>
              <span>米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">层数</label>
            <div>
              <input type="text" placeholder="请输入层数" v-model="projectForm.industrialArchitecture.level" v-validate="'required'" data-vv-name="层数"/>
              <span>层</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">跨度</label>
            <div><input type="text" placeholder="请输入跨度" v-model="projectForm.industrialArchitecture.span" v-validate="'required'" data-vv-name="跨度"/></div>
          </li>
          <li class="form-item">
            <label for="">生产状况</label>
            <div><input type="text" placeholder="请输入生产状况" v-model="projectForm.industrialArchitecture.produceCondition" v-validate="'required'" data-vv-name="生产状况"/></div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('4')">
          <p>教育</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.education.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.education.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">绿化率</label>
            <div>
              <input type="text" placeholder="请输入绿化率" v-model="projectForm.education.makeGreenRatio" v-validate="'required'" data-vv-name="绿化率"/>
              <span>%</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">绿色等级</label>
            <div>
              <input type="text" placeholder="请输入绿色等级" v-model="projectForm.education.greenLevel" v-validate="'required'" data-vv-name="绿色等级"/>
              <span>级</span>
            </div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('5')">
          <p>文化及体育设施</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.culturalAndSportsFacilities.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.culturalAndSportsFacilities.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">人容量</label>
            <div>
              <input type="text" placeholder="请输入人容量" v-model="projectForm.culturalAndSportsFacilities.persionCapacity" v-validate="'required'" data-vv-name="人容量"/>
              <span>%</span>
            </div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('6')">
          <p>电力</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.power.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.power.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">装机容量</label>
            <div><input type="text" placeholder="请输入装机容量" v-model="projectForm.power.installationCapacity" v-validate="'required'" data-vv-name="装机容量"/></div>
          </li>
          <li class="form-item">
            <label for="">发电机变压器参数数量</label>
            <div><input type="text" placeholder="请输入发电机变压器参数数量" v-model="projectForm.power.transformersNum" v-validate="'required'" data-vv-name="发电机变压器参数数量"/></div>
          </li>
          <li class="form-item">
            <label for="">线缆敷设长度</label>
            <div>
              <input type="text" placeholder="请输入线缆敷设长度" v-model="projectForm.power.cableLayingLength" v-validate="'required'" data-vv-name="线缆敷设长度"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">线缆敷设方式</label>
            <div><input type="text" placeholder="请输入线缆敷设方式" v-model="projectForm.power.cableLayingMethod" v-validate="'required'" data-vv-name="线缆敷设方式"/></div>
          </li>
          <li class="form-item">
            <label for="">输电量</label>
            <div><input type="text" placeholder="请输入输电量" v-model="projectForm.power.transmissionCapacity" v-validate="'required'" data-vv-name="输电量"/></div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('7')">
          <p>铁路、道路及桥梁建设</p>
          <li class="form-item">
            <label for="">等级</label>
            <div><input type="text" placeholder="请输入等级" v-model="projectForm.railwayRoadBridgeConstruction.grade" v-validate="'required'" data-vv-name="等级"/></div>
          </li>
          <li class="form-item">
            <label for="">全线（桥）长度</label>
            <div><input type="text" placeholder="请输入全线（桥）长度" v-model="projectForm.railwayRoadBridgeConstruction.bridgeLength" v-validate="'required'" data-vv-name="全线（桥）长度<"/></div>
          </li>
          <li class="form-item">
            <label for="">设计速度</label>
            <div><input type="text" placeholder="请输入设计速度" v-model="projectForm.railwayRoadBridgeConstruction.designSpeed" v-validate="'required'" data-vv-name="设计速度"/></div>
          </li>
          <li class="form-item">
            <label for="">轨道类型</label>
            <div><input type="text" placeholder="请输入轨道类型" v-model="projectForm.railwayRoadBridgeConstruction.orbitalType" v-validate="'required'" data-vv-name="轨道类型"/></div>
          </li>
          <li class="form-item">
            <label for="">桥梁类型</label>
            <div><input type="text" placeholder="请输入桥梁类型" v-model="projectForm.railwayRoadBridgeConstruction.bridgeType" v-validate="'required'" data-vv-name="桥梁类型"/></div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('8')">
          <p>水务</p>
          <li class="form-item">
            <label for="">正常运用水位</label>
            <div><input type="text" placeholder="请输入正常运用水位" v-model="projectForm.water.normalOperatingWaterLevel" v-validate="'required'" data-vv-name="正常运用水位"/></div>
          </li>
          <li class="form-item">
            <label for="">总库容</label>
            <div>
              <input type="text" placeholder="请输入总库容" v-model="projectForm.water.totalStorageCapacity" v-validate="'required'" data-vv-name="总库容"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">最大坝高</label>
            <div><input type="text" placeholder="请输入最大坝高" v-model="projectForm.water.maximumDamHeight" v-validate="'required'" data-vv-name="最大坝高"/></div>
          </li>
          <li class="form-item">
            <label for="">电站装机容量</label>
            <div><input type="text" placeholder="请输入电站装机容量" v-model="projectForm.water.installedCapacityOfPowerStation" v-validate="'required'" data-vv-name="电站装机容量"/></div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('9')">
          <p>公用事业及城市基础设施</p>
          <li class="form-item">
            <label for="">占地面积</label>
            <div>
              <input type="text" placeholder="请输入占地面积" v-model="projectForm.publicUtilitiesUrbanInfrastructure.landArea" v-validate="'required'" data-vv-name="占地面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">建筑面积</label>
            <div>
              <input type="text" placeholder="请输入建筑面积" v-model="projectForm.publicUtilitiesUrbanInfrastructure.floorArea" v-validate="'required'" data-vv-name="建筑面积"/>
              <span>平方米</span>
            </div>
          </li>
          <li class="form-item">
            <label for="">其他（长度/规模/范围等）</label>
            <div><input type="text" placeholder="请输入其他（长度/规模/范围等）" v-model="projectForm.publicUtilitiesUrbanInfrastructure.others" v-validate="'required'" data-vv-name="其他（长度/规模/范围等）"/></div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('10')">
          <p>水运、交通</p>
          <li class="form-item">
            <label for="">港口（码头）吞吐量</label>
            <div><input type="text" placeholder="请输入港口（码头）吞吐量" v-model="projectForm.waterTransportationAndTransportation.portThroughput" v-validate="'required'" data-vv-name="港口（码头）吞吐量"/></div>
          </li>
          <li class="form-item">
            <label for="">航道（闸机）通过量</label>
            <div><input type="text" placeholder="请输入航道（闸机）通过量" v-model="projectForm.waterTransportationAndTransportation.channelThroughput" v-validate="'required'" data-vv-name="航道（闸机）通过量"/></div>
          </li>
          <li class="form-item">
            <label for="">主要货物的流量流向</label>
            <div><input type="text" placeholder="请输入主要货物的流量流向" v-model="projectForm.waterTransportationAndTransportation.flowDirection" v-validate="'required'" data-vv-name="主要货物的流量流向"/></div>
          </li>
          <li class="form-item">
            <label for="">分航线运量</label>
            <div><input type="text" placeholder="请输入分航线运量" v-model="projectForm.waterTransportationAndTransportation.branchLineVolume" v-validate="'required'" data-vv-name="分航线运量"/></div>
          </li>
          <li class="form-item">
            <label for="">船舶流量</label>
            <div><input type="text" placeholder="请输入船舶流量" v-model="projectForm.waterTransportationAndTransportation.shipFlow" v-validate="'required'" data-vv-name="船舶流量"/></div>
          </li>
          <li class="form-item">
            <label for="">集疏方式</label>
            <div><input type="text" placeholder="请输入集疏方式" v-model="projectForm.waterTransportationAndTransportation.gatheringAndDistributingWays" v-validate="'required'" data-vv-name="集疏方式"/></div>
          </li>
        </ul>

        <ul class="classes" v-show="-1!=projectForm.projectTypeList.indexOf('11')">
          <p>其他</p>
          <li class="form-item">
            <label for="">其他</label>
            <div><input type="text" placeholder="请输入其他" v-model="projectForm.others.others" v-validate="'required'" data-vv-name="其他"/></div>
          </li>
        </ul>
      </div>

      <div class="form-item zanding">
        <label for="">投资金额</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入投资金额" v-model="projectForm.investedAmount" v-validate="'required'" data-vv-name="投资金额"/>
          <span>元</span>
          <mt-switch v-model="projectForm.isProvisional">暂定</mt-switch>
        </div>
      </div>

      <div class="form-item">
        <m-select v-model="projectForm.projectNatureList" :data="{title: '项目性质',options: dataList.nature}"
          unique="value" display="label" :multiple="true" v-validate="'required'" data-vv-name="项目性质">
        </m-select>
      </div>

      <div class="form-item">
        <label>委托人</label>
        <input type="text" placeholder="请输入委托人" v-model="projectForm.trusterName" v-validate="'required'" data-vv-name="委托人"/>
      </div>

      <div class="form-item">
        <m-select v-model="projectForm.trusterTypeList" :data="{title: '委托人性质',options: dataList.clientNature}"
          unique="value" display="label" :multiple="true" v-validate="'required'" data-vv-name="委托人性质">
        </m-select>
      </div>

      <div class="form-item">
        <label>委托方负责人</label>
        <input type="text" placeholder="请输入委托方负责人" v-model="projectForm.trusterDutyUser" v-validate="'required'" data-vv-name="委托方负责人"/>
      </div>
      <div class="form-item">
        <label>委托方负责人电话</label>
        <input type="text" placeholder="请输入委托方负责人电话" v-model="projectForm.trusterDutyPhone" v-validate="'required'" data-vv-name="委托方负责人电话"/>
      </div>
      <div class="form-item">
        <label>咨询人</label>
        <input type="text" placeholder="请输入咨询人" v-model="projectForm.consultUser" v-validate="'required'" data-vv-name="咨询人"/>
      </div>
      <div class="form-item">
        <label>第三方（合作单位）</label>
        <input type="text" placeholder="请输入第三方（合作单位）" v-model="projectForm.thirdParty" v-validate="'required'" data-vv-name="第三方（合作单位）"/>
      </div>
      <div class="form-item">
        <label>扉页咨询人</label>
        <input type="text" placeholder="请输入扉页咨询人" v-model="projectForm.titlePageConsultant" v-validate="'required'" data-vv-name="扉页咨询人"/>
      </div>
      <div class="form-item">
        <label>拟定合同负责人</label>
        <input type="text" placeholder="请输入拟定合同负责人" v-model="projectForm.dutyPeople" v-validate="'required'" data-vv-name="拟定合同负责人"/>
      </div>
      <div class="form-item">
        <label>拟定人</label>
        <input type="text" placeholder="请输入拟定人" v-model="projectForm.planner" v-validate="'required'" data-vv-name="拟定人"/>
      </div>

      <div class="form-item">
        <m-select v-model="projectForm.contractNatureList" :data="{title: '合同性质',options: dataList.character}"
          unique="value" display="label" :multiple="true" v-validate="'required'" data-vv-name="合同性质">
        </m-select>
      </div>

      <div class="form-item">
        <m-select v-model="projectForm.determinateMethodList" :data="{title: '确定方式',options: dataList.confirmation}"
          unique="value" display="label" :multiple="true" v-validate="'required'" data-vv-name="确定方式">
        </m-select>
      </div>

      <div class="form-item zanding">
        <label for="">拟定合同金额</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入拟定合同金额" v-model="projectForm.contractAmount" v-validate="'required'" data-vv-name="拟定合同金额"/>
          <span>元</span>
          <mt-switch v-model="projectForm.isValueProvisional">暂定</mt-switch>
        </div>
      </div>

      <div class="form-item" @click="openPicker('picker')">
        <mt-cell title="咨询合同签订日期">
          <span>{{ projectForm.contractDate }}</span>
          <input type="text" hidden="hidden" v-model="projectForm.contractDate" v-validate="'required'" data-vv-name="咨询合同签订日期">
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </div>
      <mt-datetime-picker ref="picker" type="date" v-model="pickerValue" @confirm="handleConfirm('contractDate')"></mt-datetime-picker>
      <div class="form-item">
        <mt-cell title="预计收款时间">
          <span @click="addlist('预计收款时间')">添加</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
        <div class="DynamicallyAdd" v-for="(item, index) in projectForm.characteristic" :key="index">

          <i class="iconfont iconshanchu" @click="removeSwipe(--index, 'characteristic')"></i>
          <div class="form-item" @click="openPicker('tender', --index, 'characteristic')">
            <mt-cell class="sideslip_cell" :title="'第' + ++index + '次收款时间'">
              <span>{{ item.startDate }}</span>
               <input type="text" hidden="hidden" v-model="item.startDate" v-validate="'required'" :data-vv-name="'第' + index + '次收款时间'">
              <i class="iconfont">&#xe630;</i>
            </mt-cell>
          </div>
          <mt-datetime-picker
          ref="tender"
          type="date"
          v-model="pickerValue" @confirm="handleConfirm('startDate', signboard)"></mt-datetime-picker>
          <div class="form-item zanding">
            <label for="">{{ '第' + index + '次收款金额' }}</label>
            <div class="zandiv">
              <input type="text" placeholder="请输入收款金额" v-model="item.receivedAmount" v-validate="'required'" data-vv-name="收款金额"/>
              <span>元</span>
              <mt-switch v-model="item.isProvisional">暂定</mt-switch>
            </div>
          </div>

        </div>
      </div>

      <div class="form-item">
        <label>其他情况</label>
        <textarea placeholder="请输入其他情况" v-model="projectForm.otherThings" v-validate="'required'" data-vv-name="其他情况"></textarea>
      </div>

      <div class="form-item">
        <label>合作公司</label>
        <input placeholder="请输入合作公司" v-model="projectForm.cooperateCompany" v-validate="'required'" data-vv-name="合作公司"></input>
      </div>
    <div v-show="conceal">
      <div class="form-item">
        <m-select v-model="projectForm.contractType" :data="{title: '合同类型',options: dataList.contractType}"
          unique="value" display="label" @change="contractChange" v-validate="'required'" data-vv-name="合同类型">
        </m-select>
      </div>

      <div class="form-item" v-show="projectForm.contractType == '0'">
        <div class="DynamicallyAdd">
          <div class="form-item file upload">
            <mt-cell title="上传合同附件">
              <label for="file">{{projectForm.contractFile&&projectForm.contractFile.name?projectForm.contractFile.name:'上传'}}</label>
              <input type="file" value="" id="file" @change="onUpload($event)" v-validate="'required'" data-vv-name="合同附件"/>
              <i class="iconfont">&#xe630;</i>
            </mt-cell>
          </div>
        </div>
      </div>

      <div class="form-item" v-show="projectForm.contractType == '1'">
        <div class="template">
          <mt-radio v-model="projectForm.formworkType" :options="templatelist" align="right" @change="initTemplate">
          </mt-radio>
         </div>
      </div>
      <component v-show="projectForm.contractType == '1'" :ref="component" v-bind:is="component" :form="templateForm" :theFormDisabled="theFormDisabled"></component>
   </div>
    </form>
  </fieldset>
</template>

<script>
import dataList from './dataList';// 选项以及表单数据
import formManufacturingCost from './formManufacturingCost';// 造价咨询合同
import formConstruction from './formConstruction';// 建设工程招标代理合同
import formEngineering from './formEngineering';// 工程管理咨询服务合同
import formSupervision from './formSupervision';// 监理合同
export default {
  components:{
    formManufacturingCost,// 造价咨询合同
    formConstruction,// 建设工程招标代理合同
    formEngineering,// 工程管理咨询服务合同
    formSupervision// 监理合同
  },
  // props:['form','theFormDisabled'],
  props:{
     form: Object,
     theFormDisabled:Boolean,
     conceal:{
       type:Boolean,
       default:true,
     }
  },
  data() {
    return {
      component:'',
      dataList:this.tool.deepCopy(dataList, this.tool.deepCopy),// 选项以及表单数据
      radios:[],// 咨询类型阶段数组
      projectForm: this.tool.deepCopy(Object.assign({},this.form.baseContract.form), this.tool.deepCopy),// 表单
      signboard: '' /* 动态日期标识 */,
      pickerValue: new Date(),// 日期控件时间
      templatelist: this.tool.deepCopy(dataList.templatelist, this.tool.deepCopy),
      templateForm:{},// 模板表单
      hidden:'block'
    };
  },
  watch:{
    form:{
      handler(){
        let vm = this;
        if(!vm.form)return;
        vm.$nextTick(() => {
          vm.projectForm = this.tool.deepCopy(Object.assign({},this.form.baseContract.form), this.tool.deepCopy);
          if(vm.projectForm.counselBusinessType){
            vm.changeConsultancy(vm.projectForm.counselBusinessType)
          }
          vm.initTemplate();// 初始化模板数据
        })
      },
      immediate: true
    },
    conceal:{
      handler(newval){
        let vm = this;
        vm.conceal = newval;
      },
      immediate: true
    }
  },
  methods: {
    /**
     * @method 合同类型改变
     */
    contractChange(val){
      let vm = this;
      if(1==val){
        vm.component = 'formManufacturingCost';
        vm.projectForm.formworkType = '0';
        vm.initTemplate();// 初始化模板数据
      }
    },
    /**
     * @method 初始化模板数据
     */
    initTemplate(){
      let vm = this;
      switch (vm.projectForm.formworkType){
        case '0':// 造价咨询合同
          vm.component = 'formManufacturingCost';
          vm.templateForm = this.tool.deepCopy(Object.assign({},this.form.formCostConsulting.form), this.tool.deepCopy);
          break;
        case '1':// 建设工程招标代理合同
          vm.component = 'formConstruction';
          vm.templateForm = this.tool.deepCopy(Object.assign({},this.form.formConstructionProjectBiddingAgency.form), this.tool.deepCopy);
          break;
        case '2':// 工程管理咨询服务合同
          vm.component = 'formEngineering';
          vm.templateForm = this.tool.deepCopy(Object.assign({},this.form.formProjectManagementConsultingService.form), this.tool.deepCopy);
          break;
        case '3':// 监理合同
        vm.component = 'formSupervision';
          vm.templateForm = this.tool.deepCopy(Object.assign({},this.form.formSupervision.form), this.tool.deepCopy);
          break;
      }
    },
    /**
     * @method 上传文件
     */
    onUpload(e){
      let vm = this;
      vm.projectForm.contractFile = e.currentTarget.files[0];
    },
    openPicker(text, index) {
      if(this.theFormDisabled)return;
      /* 打开日期选择插件 */
      if (text == 'tender') {
        this.signboard = index;
        this.$refs[text][0].open();
      } else if (text == 'offer') {
        this.$refs[text][0].open();
      } else {
        this.$refs[text].open();
      }
    },
    handleConfirm(text, index) {
      let vm = this;
      if (text == 'startDate') {
        this.projectForm.characteristic[index][text] = this.formatting(this.pickerValue);
      } else if (text == 'offertime') {
        this.projectForm.provided[index][text] = this.formatting(this.pickerValue);
      } else {
         vm.projectForm[text] = vm.formatting(vm.pickerValue);
      }
    },
    addlist(text) {
      /* 动态添加 */
      this.projectForm.characteristic.push({
        startDate: '',
        receivedAmount: '',
        isProvisional:false
      });
    },
    removeSwipe(index, type) {
      /* 动态删除 */
      this.projectForm[type].splice(index, 1);
    },
    // 提审数据转化
    getData() {
      const vm = this;
      let formData = {};
      const promise = new Promise(function(resolve, reject) {
        vm.$validator.validate().then((v)=>{
         if(v){
           if(1==vm.projectForm.contractType&&vm.component){
             //提取模板数据
             vm.$refs[vm.component].getData().then(templateForm =>{
               if(templateForm){
                switch (vm.component){
                    case 'formManufacturingCost':
                      formData.formCostConsulting = templateForm;// 造价合同
                      break;
                    case 'formConstruction':
                      formData.formConstructionProjectBiddingAgency = templateForm;// 建设工程招标代理合同
                      break;
                    case 'formEngineering':
                      formData.formProjectManagementConsultingService = templateForm;// 工程管理咨询服务合同
                      break;
                    case 'formSupervision':
                      formData.formSupervision = templateForm;// 监理合同
                      break;
                  }
                // 混合表单数据
                let mixForm = Object.assign({},vm.projectForm,formData);
                resolve(vm.tool.deepCopy(mixForm,vm.tool.deepCopy));
               }else{
                resolve(false);
               }
             });
           }else{
             // 文件字段
             let file = vm.projectForm.contractFile;
             console.log("file",file)
             delete vm.projectForm.contractFile;
             let newForm = vm.tool.deepCopy(vm.projectForm,vm.tool.deepCopy);
             vm.projectForm.contractFile = newForm.contractFile = file;
             resolve(newForm);
           }
         }else{
            resolve(false);
           vm.Toast(vm.errors.all()[0]);
         }
        });
      });
      return promise;
    },
    /**
     * @method 设置表单数据
     */
    setData(){

    },
    /**
     * @event 咨询业务类型改变
     */
    changeConsultancy(val){
      let vm = this;
      vm.radios = vm.dataList.consultancyStage[val];
    }
  }
};
</script>
<style lang="scss">
.mint-radio-core {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  transform: scale(0.5);
  transform-origin: 0% 0%;
  border: 2px solid #d6d6d6;
}
.mint-radio-core::after {
  top: 14px;
  left: 14px;
  width: 30px;
  height: 30px;
}
.mint-radiolist-label {
  padding: 10px 0;
}
.DynamicallyAdd {
  .mint-cell-wrapper {
    line-height: 80px;
    .mint-cell-value {
      display: block;
      .sideslip_cell {
        .mint-cell-value {
          display: flex;
          width: auto;
        }
      }
    }
  }
  .mint-cell-right {
    line-height: 0;
    .mint-cell-swipe-buttongroup {
      background-color: #f15a24;
      padding: 10px;
      font-size: 28px;
      a {
        color: #fff;
        line-height: 350px;
      }
    }
  }
}
</style>
<style scoped lang="scss">
.index {
  width: 100%;
  padding-bottom: 110px;
  .projectForm {
    .classes {
      overflow: hidden;
      margin-bottom: 30px;
      p {
        font-size: 28px;
        color: #666;
        margin: 30px;
        overflow: hidden;
      }
      & > li {
        padding: 0;
        margin: 20px auto;
        width: 94%;
        display: block;
        border-bottom: 2px solid #e5e5e5;
        label {
          padding: 0 10px;
        }
        .sky {
          margin: 0;
          padding: 0 10px;
          display: flex;
          justify-content: space-between;
          input {
            width: 80%;
          }
          span {
            line-height: 90px;
          }
        }
      }
      li:last-child {
        border: none;
      }
    }
  }
  .exhibition {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    ul {
      width: 92%;
      margin: 0 auto;
      overflow: hidden;
      li {
        margin: 30px 0;
        display: flex;
        justify-content: space-between;
        font-size: 28px;
      }
    }
  }
  .DynamicallyAdd {
    margin: 0;
    padding: 0;
    height: auto;
    background-color: #f1f5f8 !important;
    overflow: hidden;
    border-radius: 10px;
    .mint-cell-wrapper {
      .mint-cell-value {
        display: block !important;
        .form-item {
          padding: 0;
          margin: 30px auto 0;
          width: 92%;
          display: block;
          &:last-child {
            border-bottom: none;
          }
          .mint-cell {
            width: 100%;
            background-color: transparent;
            .iconfont {
              visibility: visible;
            }
          }
        }
      }
    }
    .upload {
      margin-top: 0;
    }
  }
  .dark {
    background-color: #e5ebef !important;
  }
  .template {
    background-color: #f1f5f8;
    border-radius: 10px;
    margin-bottom: 20px;
  }
}
</style>
