<template>
  <!-- 基础合同 -->
  <div class="baseContract--work-common-components">
    <el-form class="form" ref="form" :model="form" :disabled="disabled" :rules="rules">
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目名称" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入项目名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="咨询业务类型" prop="counselBusinessType">
              <el-select v-model="form.counselBusinessType" placeholder="请选择咨询业务类型" @change="change">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>

                <el-checkbox-group v-if="radios.length" style="margin-top: 10px;">
                  <el-radio v-model="form.counselTypePeriod" :label="r.val" v-for="(r, i) in radios"
                    :key="i">{{r.display}}</el-radio>
                </el-checkbox-group>

            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="合同盖章类型" prop="contractSealType">
              <el-radio v-model="form.contractSealType" label="1">委托代理人章</el-radio>
              <el-radio v-model="form.contractSealType" label="2">法定代表人章</el-radio>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="项目地点" prop="projectAddress">
              <el-input type="textarea" v-model="form.projectAddress" placeholder="请输入项目地点"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="项目类别" prop="projectTypeList">
              <el-checkbox-group v-model="form.projectTypeList" @change="handleCheckboxC">
                <el-checkbox :label="0">住宅</el-checkbox>
                <el-checkbox :label="1">商业建筑</el-checkbox>
                <el-checkbox :label="2">酒店及休闲娱乐</el-checkbox>
                <el-checkbox :label="3">工业建筑</el-checkbox>
                <el-checkbox :label="4">教育</el-checkbox>
                <el-checkbox :label="5">文化及体育设施</el-checkbox>
                <el-checkbox :label="6">电力</el-checkbox>
                <el-checkbox :label="7">铁路、道路及桥梁建设</el-checkbox>
                <el-checkbox :label="8">水务</el-checkbox>
                <el-checkbox :label="9">公用事业及城市基础设施</el-checkbox>
                <el-checkbox :label="10">水运、交通</el-checkbox>
                <el-checkbox :label="11">其他</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>

        <h3 class="form-title" v-if="form.projectTypeList.length">项目特征/指标</h3>
        <!-- 住宅 -->
        <!-- 占地面积，建筑面积，檐高，建筑控制高度，容积率，住宅类别（例如：商品房/公租房/共有产权等） -->
        <div class="control-block" v-if="controlObj[0]">
          <h4 class="form-title">住宅</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="residential.landArea">
                <el-input v-model="form.residential.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="residential.floorArea">
                <el-input v-model="form.residential.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="檐高" prop="residential.eavesHeight">
                <el-input v-model="form.residential.eavesHeight" placeholder="请输入檐高">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑控制高度" prop="residential.buildingControlHeight">
                <el-input v-model="form.residential.buildingControlHeight" placeholder="请输入建筑控制高度">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="容积率" prop="residential.volumeRatio">
                <el-input v-model="form.residential.volumeRatio" placeholder="请输入容积率">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="住宅类别" prop="residential.homeType">
                <el-select v-model="form.residential.homeType" placeholder="请选择住宅类别">
                  <el-option label="商品房" value="0"></el-option>
                  <el-option label="公租房" value="1"></el-option>
                  <el-option label="共有产权" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 商业建筑 -->
        <!-- 占地面积，建筑面积，檐高，建筑控制高度。 -->
        <div class="control-block" v-if="controlObj[1]">
          <h4 class="form-title">商业建筑</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="commercialBuildings.landArea">
                <el-input v-model="form.commercialBuildings.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="commercialBuildings.floorArea">
                <el-input v-model="form.commercialBuildings.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="檐高" prop="commercialBuildings.eavesHeight">
                <el-input v-model="form.commercialBuildings.eavesHeight" placeholder="请输入檐高">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑控制高度" prop="commercialBuildings.buildingControlHeight">
                <el-input v-model="form.commercialBuildings.buildingControlHeight" placeholder="请输入建筑控制高度">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 酒店及休闲娱乐 -->
        <!-- 占地面积，建筑面积，檐高，建筑控制高度，房间数量。 -->
        <div class="control-block" v-if="controlObj[2]">
          <h4 class="form-title">酒店及休闲娱乐</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="hotelAndLeisureEntertainment.landArea">
                <el-input v-model="form.hotelAndLeisureEntertainment.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积"  prop="hotelAndLeisureEntertainment.floorArea">
                <el-input v-model="form.hotelAndLeisureEntertainment.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="檐高" prop="hotelAndLeisureEntertainment.eavesHeight">
                <el-input v-model="form.hotelAndLeisureEntertainment.eavesHeight" placeholder="请输入檐高">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑控制高度" prop="hotelAndLeisureEntertainment.buildingControlHeight">
                <el-input v-model="form.hotelAndLeisureEntertainment.buildingControlHeight" placeholder="请输入建筑控制高度">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="房间数量" prop="hotelAndLeisureEntertainment.roomNum">
                <el-input v-model="form.hotelAndLeisureEntertainment.roomNum" placeholder="请输入房间数量">
                  <template slot="append">
                    个
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 工业建筑-->
        <!-- 占地面积，建筑面积，层数，檐高，跨度，生产状况。 -->
        <div class="control-block" v-if="controlObj[3]">
          <h4 class="form-title">工业建筑</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="industrialArchitecture.landArea">
                <el-input v-model="form.industrialArchitecture.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="industrialArchitecture.floorArea">
                <el-input v-model="form.industrialArchitecture.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="层数" prop="industrialArchitecture.level">
                <el-input v-model="form.industrialArchitecture.level" placeholder="请输入层数">
                  <template slot="append">
                    层
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="檐高" prop="industrialArchitecture.eavesHeight">
                <el-input v-model="form.industrialArchitecture.eavesHeight" placeholder="请输入檐高">
                  <template slot="append">
                    米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="跨度" prop="industrialArchitecture.span">
                <el-input v-model="form.industrialArchitecture.span" placeholder="请输入跨度"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="生产状况" prop="industrialArchitecture.produceCondition">
                <el-input v-model="form.industrialArchitecture.produceCondition" placeholder="请输入生产状况"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 教育 -->
        <!-- 占地面积，建筑面积、绿化率，绿色等级。 -->
        <div class="control-block" v-if="controlObj[4]">
          <h4 class="form-title">教育</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="education.landArea">
                <el-input v-model="form.education.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="education.floorArea">
                <el-input v-model="form.education.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="绿化率" prop="education.makeGreenRatio">
                <el-input v-model="form.education.makeGreenRatio" placeholder="请输入绿化率">
                  <template slot="append">
                    %
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="绿色等级" prop="education.greenLevel">
                <el-input v-model="form.education.greenLevel" placeholder="请输入绿色等级">
                  <template slot="append">
                    级
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 文化及体育设施 -->
        <!-- 占地面积，建筑面积、绿化率，绿色等级。 -->
        <div class="control-block" v-if="controlObj[5]">
          <h4 class="form-title">文化及体育设施</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="culturalAndSportsFacilities.landArea">
                <el-input v-model="form.culturalAndSportsFacilities.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="culturalAndSportsFacilities.floorArea">
                <el-input v-model="form.culturalAndSportsFacilities.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="人容量" prop="culturalAndSportsFacilities.persionCapacity">
                <el-input v-model="form.culturalAndSportsFacilities.persionCapacity" placeholder="请输入人容量">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 电力 -->
        <!-- 装机容量，建筑面积，占地面积，发电机变压器参数数量，线缆敷设长度，线缆敷设方式，输电量。 -->
        <div class="control-block" v-if="controlObj[6]">
          <h4 class="form-title">电力</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="装机容量" prop="power.installationCapacity">
                <el-input v-model="form.power.installationCapacity" placeholder="请输入装机容量"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="power.floorArea">
                <el-input v-model="form.power.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="power.landArea">
                <el-input v-model="form.power.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="发电机变压器参数数量" prop="power.transformersNum">
                <el-input v-model="form.power.transformersNum" placeholder="请输入发电机变压器参数数量"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="线缆敷设长度" prop="power.cableLayingLength">
                <el-input v-model="form.power.cableLayingLength" placeholder="请输入线缆敷设长度">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="线缆敷设方式" prop="power.cableLayingMethod">
                <el-input v-model="form.power.cableLayingMethod" placeholder="请输入线缆敷设方式"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="输电量" prop="power.transmissionCapacity">
                <el-input v-model="form.power.transmissionCapacity" placeholder="请输入输电量"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 铁路、道路及桥梁建设 -->
        <!-- 等级，全线（桥）长度，设计速度，轨道类型，桥梁类型。 -->
        <div class="control-block" v-if="controlObj[7]">
          <h4 class="form-title">铁路、道路及桥梁建设</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="等级" prop="railwayRoadBridgeConstruction.grade">
                <el-input v-model="form.railwayRoadBridgeConstruction.grade" placeholder="请输入等级">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="全线（桥）长度" prop="railwayRoadBridgeConstruction.bridgeLength">
                <el-input v-model="form.railwayRoadBridgeConstruction.bridgeLength" placeholder="请输入全线（桥）长度"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="设计速度" prop="railwayRoadBridgeConstruction.designSpeed">
                <el-input v-model="form.railwayRoadBridgeConstruction.designSpeed" placeholder="请输入设计速度"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="轨道类型" prop="railwayRoadBridgeConstruction.orbitalType">
                <el-input v-model="form.railwayRoadBridgeConstruction.orbitalType" placeholder="请输入轨道类型"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="桥梁类型" prop="railwayRoadBridgeConstruction.bridgeType">
                <el-input v-model="form.railwayRoadBridgeConstruction.bridgeType" placeholder="请输入桥梁类型"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 水务 -->
        <!-- 正常运用水位，总库容，最大坝高，电站装机容量。 -->
        <div class="control-block" v-if="controlObj[8]">
          <h4 class="form-title">水务</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="正常运用水位" prop="water.normalOperatingWaterLevel">
                <el-input v-model="form.water.normalOperatingWaterLevel" placeholder="请输入正常运用水位"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="总库容" prop="water.totalStorageCapacity">
                <el-input v-model="form.water.totalStorageCapacity" placeholder="请输入总库容">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="最大坝高" prop="water.maximumDamHeight">
                <el-input v-model="form.water.maximumDamHeight" placeholder="请输入最大坝高"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="电站装机容量" prop="water.installedCapacityOfPowerStation">
                <el-input v-model="form.water.installedCapacityOfPowerStation" placeholder="请输入电站装机容量"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 公用事业及城市基础设施 -->
        <!-- 占地面积，建筑面积，其他（长度/规模/范围等）。 -->
        <div class="control-block" v-if="controlObj[9]">
          <h4 class="form-title">公用事业及城市基础设施</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="占地面积" prop="publicUtilitiesUrbanInfrastructure.landArea">
                <el-input v-model="form.publicUtilitiesUrbanInfrastructure.landArea" placeholder="请输入占地面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建筑面积" prop="publicUtilitiesUrbanInfrastructure.floorArea">
                <el-input v-model="form.publicUtilitiesUrbanInfrastructure.floorArea" placeholder="请输入建筑面积">
                  <template slot="append">
                    平方米
                  </template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="其他（长度/规模/范围等）" prop="publicUtilitiesUrbanInfrastructure.others">
                <el-input v-model="form.publicUtilitiesUrbanInfrastructure.others" placeholder="请输入其他（长度/规模/范围等）"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 水运、交通 -->
        <!-- 港口（码头）吞吐量，航道（闸机）通过量，主要货物的流量流向，分航线运量，船舶流量，集疏方式。 -->
        <div class="control-block" v-if="controlObj[10]">
          <h4 class="form-title">水运、交通</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="港口（码头）吞吐量" prop="waterTransportationAndTransportation.portThroughput">
                <el-input v-model="form.waterTransportationAndTransportation.portThroughput" placeholder="请输入港口（码头）吞吐量"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="航道（闸机）通过量" prop="waterTransportationAndTransportation.channelThroughput">
                <el-input v-model="form.waterTransportationAndTransportation.channelThroughput" placeholder="请输入航道（闸机）通过量"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="主要货物的流量流向" prop="waterTransportationAndTransportation.flowDirection">
                <el-input v-model="form.waterTransportationAndTransportation.flowDirection" placeholder="请输入主要货物的流量流向"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="分航线运量" prop="waterTransportationAndTransportation.branchLineVolume">
                <el-input v-model="form.waterTransportationAndTransportation.branchLineVolume" placeholder="请输入分航线运量"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="船舶流量" prop="waterTransportationAndTransportation.shipFlow">
                <el-input v-model="form.waterTransportationAndTransportation.shipFlow" placeholder="请输入船舶流量"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="集疏方式" prop="waterTransportationAndTransportation.gatheringAndDistributingWays">
                <el-input v-model="form.waterTransportationAndTransportation.gatheringAndDistributingWays" placeholder="请输入集疏方式"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <!-- 其他 -->
        <!-- 占地面积，建筑面积，其他（长度/规模/范围等）。 -->
        <div class="control-block" v-if="controlObj[11]">
          <h4 class="form-title">其他</h4>
          <el-row :gutter="gutter">
            <el-col :span="12">
              <el-form-item label="其他" prop="others.others">
                <el-input v-model="form.others.others" placeholder="请输入其他">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="投资金额" prop="investedAmount">
              <template slot="label">
                <div style="position: relative;">
                  投资金额
                     <el-checkbox :label="1" v-model="form.isProvisional" true-label="1" false-label="0" style="position: absolute;right: -12px;">暂定</el-checkbox>
                </div>
              </template>
              <el-input v-model="form.investedAmount" placeholder="请输入投资金额">
                <template slot="append">
                  万元
                </template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目性质" prop="projectNatureList">
              <el-checkbox-group v-model="form.projectNatureList">
                <el-checkbox label="0">新建</el-checkbox>
                <el-checkbox label="1">扩建</el-checkbox>
                <el-checkbox label="2">改建</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="委托人" prop="trusterName">
              <el-input v-model="form.trusterName" placeholder="请输入委托人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="委托人性质" prop="trusterTypeList">
              <el-checkbox-group v-model="form.trusterTypeList">
                <el-checkbox label="0" name="checkbox">政府部门</el-checkbox>
                <el-checkbox label="1" name="checkbox">国企</el-checkbox>
                <el-checkbox label="2" name="checkbox">上市公司</el-checkbox>
                <el-checkbox label="3" name="checkbox">私企</el-checkbox>
                <el-checkbox label="4" name="checkbox">其他</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="委托方负责人" prop="trusterDutyUser">
              <el-input v-model="form.trusterDutyUser" placeholder="请输入委托方负责人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="委托方负责人电话" prop="trusterDutyPhone">
              <el-input v-model="form.trusterDutyPhone" placeholder="请输入委托方负责人电话"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="咨询人" prop="consultUser">
              <el-input v-model="form.consultUser" placeholder="请输入咨询人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="第三方（合作单位）" prop="thirdParty">
              <el-input v-model="form.thirdParty" placeholder="请输入第三方（合作单位）"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="扉页咨询人" prop="titlePageConsultant">
              <el-input v-model="form.titlePageConsultant" placeholder="请输入扉页咨询人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="拟定合同负责人" prop="dutyPeople">
              <el-input v-model="form.dutyPeople" placeholder="请输入拟定合同负责人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拟定人" prop="planner">
              <el-input v-model="form.planner" placeholder="请输入拟定人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="合同性质" prop="contractNatureList">
              <el-checkbox-group v-model="form.contractNatureList" style="display: inline-block;margin-right: 30px;">
                <el-checkbox label="0">双方合同</el-checkbox>
                <el-checkbox label="1">三方合同</el-checkbox>
                <el-checkbox label="2">补充合同</el-checkbox>
              </el-checkbox-group>
              <span class="checkbox-parentheses-left">(</span>
              <el-checkbox-group v-model="form.contractNatureList" style="display: inline-block;padding: 0 5px;">
                <el-checkbox label="3">单独核算</el-checkbox>
                <el-checkbox label="4">非单独核算</el-checkbox>
              </el-checkbox-group>
              <span class="checkbox-parentheses-right">)</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="确定方式" prop="determinateMethodList">
              <el-checkbox-group v-model="form.determinateMethodList">
                <el-checkbox label="0" name="checkbox">招标</el-checkbox>
                <el-checkbox label="1" name="checkbox">财政委托</el-checkbox>
                <el-checkbox label="2" name="checkbox">审计委托</el-checkbox>
                <el-checkbox label="3" name="checkbox">商务洽谈</el-checkbox>
                <el-checkbox label="4" name="checkbox">战略合作</el-checkbox>
                <el-checkbox label="5" name="checkbox">法院委托</el-checkbox>
                <el-checkbox label="6" name="checkbox">其他</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="拟定合同金额" prop="contractAmount">
              <template slot="label">
                <div style="position: relative;">
                  拟定合同金额
                  <el-checkbox :label="1" v-model="form.isValueProvisional" true-label="1" false-label="0" style="position: absolute;right: -12px;">暂定</el-checkbox>
                </div>
              </template>
              <el-input v-model="form.contractAmount" placeholder="请输入拟定合同金额">
                <template slot="append">
                  元
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="咨询合同签订日期" prop="contractDate">
              <el-date-picker
              v-model="form.contractDate"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div class="form-block">
        <h4 class="form-title">
          预计收款时间
          <el-button style="width: 26px;
								height: 16px !important;
								line-height: 16px !important;
								border-radius: 5px;
								padding: 0 !important;
								border: none;"
            type="primary" icon="el-icon-plus" class="linear" size="mini" @click="addCharacteristic"></el-button>
        </h4>


        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item
              :label="'第1次收款'" :prop="'characteristic[0].startDate'"
              :rules="[{type: 'date',required: true,message: '请选择日期',trigger: 'blur'}]">
              <el-date-picker
              v-model="form.characteristic[0].startDate"
              type="date"
              placeholder="选择日期"
             ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label=""
            :prop="'characteristic[0].receivedAmount'"
            :rules="[{required: true,message: '请输入预计收款金额',trigger: 'blur'}]"
            >
              <template slot="label">
                <div style="position: relative;">
                  请输入预计收款金额
                  <span style="opacity:0;">1</span>
                  <el-checkbox
                  :label="1"
                  v-model="form.characteristic[0].isProvisional"
                  true-label="1"
                  false-label="0"
                  style="position: absolute;right:calc(20% - 9px);">暂定</el-checkbox>
                </div>
              </template>
              <el-input
                v-model="form.characteristic[0].receivedAmount"
                placeholder="请输入预计收款金额"
                style="width: 80%;margin-right: 10px;">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="gutter" v-for="(item, i) in form.characteristic" :key="i">
          <template v-if="i>=1">
          <el-col :span="12">
            <el-form-item
              :label="'第' + (i + 1) + '次收款'" :prop="'characteristic['+i+'].startDate'"
              :rules="[{type: 'date',required: true,message: '请选择日期',trigger: 'blur'}]">
              <el-date-picker
              v-model="item.startDate"
              type="date"
              placeholder="选择日期"
             ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label=""
            :prop="'characteristic['+i+'].receivedAmount'"
            :rules="[{required: true,message: '请输入预计收款金额',trigger: 'blur'}]">
              <template slot="label">
                <div style="position: relative;">
                  请输入预计收款金额
                  <span style="opacity:0;">1</span>
                  <el-checkbox
                  :label="1"
                  v-model="item.isProvisional"
                  true-label="1"
                  false-label="0"
                  style="position: absolute;right:calc(20% - 9px);">暂定</el-checkbox>
                </div>
              </template>
              <el-input
                v-model="item.receivedAmount"
                placeholder="请输入预计收款金额"
                style="width: 80%;margin-right: 10px;">
                <template slot="append">元</template>
              </el-input>
              <i class="el-icon-close" @click="delCollection(i)"></i>
            </el-form-item>
          </el-col>
</template>
        </el-row>



      </div>

      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="其他情况" prop="otherThings">
              <el-input type="textarea" v-model="form.otherThings" placeholder="其他情况说明"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="合作公司" prop="cooperateCompany">
              <el-input  v-model="form.cooperateCompany" placeholder="请输入合作公司"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="合同类型" prop="contractType">
              <el-radio-group v-model="form.contractType" @change="changeContractType">
                <el-radio label="0">自定义合同</el-radio>
                <el-radio label="1">模板合同</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

    </el-form>

  </div>
</template>

<script>
  import formData from './formData.js';
  export default {
    props:['_form','disabled'],
    data() {
      return {
        sourceData:formData,// 表单源数据
        gutter: 40, // 表单项间距
        controlObj: this._form.controlObj, // 控制对象
        // 表单数据
        form: this._form.form,
        // 咨询业务类型
        options: [{
            value: '0',
            label: '造价咨询'
          },
          {
            value: '1',
            label: '招标代理'
          },
          {
            value: '2',
            label: '项目管理（监理）'
          }
        ],
        radios: [],
        fileList: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],
        rules: {
          projectName: [{
            required: true,
            message: '请输入项目名称',
            trigger: 'blur'
          }],
          counselBusinessType: [{
            required: true,
            message: '请选择咨询业务类型',
            trigger: 'change'
          }],
          contractSealType: [{
            required: true,
            message: '请选择合同盖章类型',
            trigger: 'change'
          }],
          projectAddress: [{
            required: true,
            message: '请输入项目地点',
            trigger: 'blur'
          }],
          projectTypeList: [{
            required: true,
            message: '请选择项目类别',
            trigger: 'change'
          }],
          trusterName: [{
            required: true,
            message: '请输入委托人',
            trigger: 'blur'
          }],
          trusterTypeList: [{
            required: true,
            message: '请选择委托人性质',
            trigger: 'change'
          }],
          trusterDutyUser: [{
            required: true,
            message: '请输入委托方负责人',
            trigger: 'blur'
          }],
          trusterDutyPhone: [{
            required: true,
            message: '请输入委托方负责人电话',
            trigger: 'blur'
          }],
          consultUser: [{
            required: true,
            message: '请输入咨询人',
            trigger: 'blur'
          }],
          thirdParty: [{
            required: true,
            message: '请输入第三方（合作单位）',
            trigger: 'blur'
          }],
          titlePageConsultant:[{
              required: true,
              message: '请输入扉页咨询人',
              trigger: 'blur'
          }],
          projectNatureList: [{
            required: true,
            message: '请选择项目性质',
            trigger: 'change'
          }],
          investedAmount: [{
            required: true,
            message: '请输入投资金额',
            trigger: 'blur'
          }],
          contractNatureList: [{
            required: true,
            message: '请选择合同性质',
            trigger: 'change'
          }],
          determinateMethodList: [{
            required: true,
            message: '请选择合同确认方式',
            trigger: 'change'
          }],
          contractAmount: [{
            required: true,
            message: '请输入拟定合同金额',
            trigger: 'blur'
          }],
          dutyPeople: [{
            required: true,
            message: '请输入拟定合同负责人',
            trigger: 'blur'
          }],
          contractDate: [{
            required: true,
            message: '请选择咨询合同签订日期',
            trigger: 'blur'
          }],
          otherThings: [{
            required: true,
            message: '请输入其他情况说明',
            trigger: 'blur'
          }],
          planner: [{
            required: true,
            message: '请输入拟定人',
            trigger: 'blur'
          }],
          'residential.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'residential.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'residential.eavesHeight': [{
            required: true,
            message: '请输入檐高',
            trigger: 'blur'
          }],
          'residential.buildingControlHeight': [{
            required: true,
            message: '请输入建筑控制高度',
            trigger: 'blur'
          }],
          'residential.volumeRatio': [{
            required: true,
            message: '请输入容积率',
            trigger: 'blur'
          }],
          'residential.homeType': [{
            required: true,
            message: '请选择住宅类别',
            trigger: 'change'
          }],
          'commercialBuildings.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'commercialBuildings.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'commercialBuildings.eavesHeight': [{
            required: true,
            message: '请输入檐高',
            trigger: 'blur'
          }],
          'commercialBuildings.buildingControlHeight': [{
            required: true,
            message: '请输入建筑控制高度',
            trigger: 'blur'
          }],
          'hotelAndLeisureEntertainment.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'hotelAndLeisureEntertainment.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'hotelAndLeisureEntertainment.eavesHeight': [{
            required: true,
            message: '请输入檐高',
            trigger: 'blur'
          }],
          'hotelAndLeisureEntertainment.buildingControlHeight': [{
            required: true,
            message: '请输入建筑控制高度',
            trigger: 'blur'
          }],
          'hotelAndLeisureEntertainment.roomNum': [{
            required: true,
            message: '请输入房间数量',
            trigger: 'blur'
          }],
          'industrialArchitecture.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'industrialArchitecture.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'industrialArchitecture.eavesHeight': [{
            required: true,
            message: '请输入檐高',
            trigger: 'blur'
          }],
          'industrialArchitecture.level': [{
            required: true,
            message: '请输入层数',
            trigger: 'blur'
          }],
          'industrialArchitecture.span': [{
            required: true,
            message: '请输入跨度',
            trigger: 'blur'
          }],
          'industrialArchitecture.produceCondition': [{
            required: true,
            message: '请输入生产状况',
            trigger: 'blur'
          }],
          'education.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'education.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'education.makeGreenRatio': [{
            required: true,
            message: '请输入绿化率',
            trigger: 'blur'
          }],
          'education.greenLevel': [{
            required: true,
            message: '请输入绿色等级',
            trigger: 'blur'
          }],
          'culturalAndSportsFacilities.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'culturalAndSportsFacilities.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'culturalAndSportsFacilities.persionCapacity': [{
            required: true,
            message: '请输入人容量',
            trigger: 'blur'
          }],
          'power.installationCapacity': [{
            required: true,
            message: '请输入装机容量',
            trigger: 'blur'
          }],
          'power.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'power.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'power.transformersNum': [{
            required: true,
            message: '请输入发电机变压器参数数量',
            trigger: 'blur'
          }],
          'power.cableLayingLength': [{
            required: true,
            message: '请输入线缆敷设长度',
            trigger: 'blur'
          }],
          'power.cableLayingMethod': [{
            required: true,
            message: '请输入线缆敷设方式',
            trigger: 'blur'
          }],
          'power.transmissionCapacity': [{
            required: true,
            message: '请输入输电量',
            trigger: 'blur'
          }],
          'railwayRoadBridgeConstruction.grade': [{
            required: true,
            message: '请输入等级',
            trigger: 'blur'
          }],
          'railwayRoadBridgeConstruction.bridgeLength': [{
            required: true,
            message: '请输入全线（桥）长度',
            trigger: 'blur'
          }],
          'railwayRoadBridgeConstruction.designSpeed': [{
            required: true,
            message: '请输入设计速度',
            trigger: 'blur'
          }],
          'railwayRoadBridgeConstruction.orbitalType': [{
            required: true,
            message: '请输入轨道类型',
            trigger: 'blur'
          }],
          'railwayRoadBridgeConstruction.bridgeType': [{
            required: true,
            message: '请输入桥梁类型',
            trigger: 'blur'
          }],
          'water.normalOperatingWaterLevel': [{
            required: true,
            message: '请输入正常运用水位',
            trigger: 'blur'
          }],
          'water.totalStorageCapacity': [{
            required: true,
            message: '请输入总库容',
            trigger: 'blur'
          }],
          'water.maximumDamHeight': [{
            required: true,
            message: '请输入最大坝高',
            trigger: 'blur'
          }],
          'water.installedCapacityOfPowerStation': [{
            required: true,
            message: '请输入电站装机容量',
            trigger: 'blur'
          }],
          'publicUtilitiesUrbanInfrastructure.landArea': [{
            required: true,
            message: '请输入占地面积',
            trigger: 'blur'
          }],
          'publicUtilitiesUrbanInfrastructure.floorArea': [{
            required: true,
            message: '请输入建筑面积',
            trigger: 'blur'
          }],
          'publicUtilitiesUrbanInfrastructure.others': [{
            required: true,
            message: '请输入其他（长度/规模/范围等）',
            trigger: 'blur'
          }],
          'waterTransportationAndTransportation.portThroughput': [{
            required: true,
            message: '请输入港口（码头）吞吐量',
            trigger: 'blur'
          }],
          'waterTransportationAndTransportation.channelThroughput': [{
            required: true,
            message: '请输入航道（闸机）通过量',
            trigger: 'blur'
          }],
          'waterTransportationAndTransportation.flowDirection': [{
            required: true,
            message: '请输入主要货物的流量流向',
            trigger: 'blur'
          }],
          'waterTransportationAndTransportation.branchLineVolume': [{
            required: true,
            message: '请输入分航线运量',
            trigger: 'blur'
          }],
          'waterTransportationAndTransportation.shipFlow': [{
            required: true,
            message: '请输入船舶流量',
            trigger: 'blur'
          }],
          'waterTransportationAndTransportation.gatheringAndDistributingWays': [{
            required: true,
            message: '请输入集疏方式',
            trigger: 'blur'
          }],
          'others.others': [{
            required: true,
            message: '请输入其他',
            trigger: 'blur'
          }],
          'cooperateCompany':[
          {
            required: true,
            message: '请输入合作公司',
            trigger: 'blur'
          },{
            max: 50,
            message: '最长50字',
            trigger: 'blur'
          },
          ],
          contractType: [{
            required: true,
            message: '请输选择合同类型',
            trigger: 'blur'
          }],
        }
      };
    },
    watch:{
      _form:{
         handler(){
           let vm = this;
           vm.form = vm.tool.deepCopy(vm._form.form, vm.tool.deepCopy);
           vm.controlObj = vm.tool.deepCopy(vm._form.controlObj, vm.tool.deepCopy);
         },
         immediate: true
      },
      '_form.form':{
        handler(){
          let vm = this;
          vm.form = vm.tool.deepCopy(vm._form.form, vm.tool.deepCopy);
          vm.$nextTick(function () {
             if(vm.$refs['form'])vm.$refs['form'].clearValidate();
          });
        },
        immediate: true
      },
        'form.counselBusinessType':{
          handler(){
             let vm = this;
                // 重置上表单的校验状态
                  vm.radios = [];
                  if('0'==vm.form.counselBusinessType){
                      vm.radios =  [{
                          display: '编制',
                          val: '0'
                        }, {
                          display: '审核',
                          val: '1'
                        }, {
                          display: '全过程跟踪审计',
                          val: '2'
                        }, {
                          display: '全过程工程造价咨询',
                          val: '3'
                        }];
                  }
                  else if ('1'== vm.form.counselBusinessType){
                      vm.radios = [{
                          display: '施工',
                          val: '0'
                          },
                          {
                              display: '监理',
                              val: '1'
                          },
                          {
                              display: '设计',
                              val: '2'
                          },
                          {
                              display: '勘察',
                              val: '3'
                          },
                          {
                              display: '服务类',
                              val: '4'
                          },
                          {
                              display: '采购',
                              val: '5'
                          },
                          {
                              display: '其他',
                              val: '6'
                          }
                      ];
                  }
                   vm.$nextTick(function () {
                   if(vm.$refs['form'])vm.$refs['form'].clearValidate();
                })
          },
          immediate: true
        },
        '_form.controlObj':{
          handler(){
            let vm = this;
            vm.controlObj = vm.tool.deepCopy(vm._form.controlObj, vm.tool.deepCopy);
          },
          immediate: true
        }
    },
    updated(){
      let vm = this;
      // vm.$refs['form'].validate();
    },
    methods: {
      delCollection(i) {
        // 删除收款项
        let vm = this;
        vm.form.characteristic.splice(i, 1);
      },
      addCharacteristic() {
        // 添加预计收款时间
        let vm = this;
        vm.form.characteristic.push({
          startDate: '',
          receivedAmount: '',
          isProvisional:''
        });
        console.log('vm.form.characteristic',vm.form.characteristic);
      },
      handleCheckboxC(val) {
        // 项目类别 多选框改变时事件
        let vm = this;
        vm.controlObj = {};
        for (var i = 0, len = val.length; i < len; i++) {
          vm.controlObj[val[i]] = true;
        }
      },
      change(val) {
        let vm = this;
        // 下拉框选中值发生变化时触发
        // 造价咨询\标代理\项目管理（监理）的数据对象
        let obj = {
          0:[{
              display: '编制',
              val: '0'
            }, {
              display: '审核',
              val: '1'
            }, {
              display: '全过程跟踪审计',
              val: '2'
            }, {
              display: '全过程工程造价咨询',
              val: '3'
            }],
          1:[{
              display: '施工',
              val: '0'
            },
            {
              display: '监理',
              val: '1'
            },
            {
              display: '设计',
              val: '2'
            },
            {
              display: '勘察',
              val: '3'
            },
            {
              display: '服务类',
              val: '4'
            },
            {
              display: '采购',
              val: '5'
            },
            {
              display: '其他',
              val: '6'
            }
          ],
          2:[]
        };
        vm.radios = obj[val];
        // 当下拉框发生改变的时候,查看是否有默认值,若没有默认值,则需要添加默认值
        if(!vm.form.counselTypePeriod){
           if(vm.radios.length){
             vm.form.counselTypePeriod = vm.radios[0].val;
           }
        }
        // 如果选择项目管理,数组为0,此时要清空counselTypePeriod的数据
        if(!vm.radios.length){
          vm.form.counselTypePeriod = '';
        }
      },
      /**
       * @method 获取数据
       */
      getData(){
        let vm = this;
        let promise = new Promise(function(resolve, reject){
          vm.$refs['form'].validate(valid => {
            if(valid){
              resolve(vm._getFormData(vm.form));
            }
          });
        });
        return promise;
      },
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
       * @param {Object} form 表单信息
       */
      _getFormData(form){
          let vm = this;
          // 获取基础合同数据
          const contractData = form;
          const formdata = new FormData();
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
          formdata.append("contractType", contractData.contractType);
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
        return formdata;
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
        vm.$nextTick(function () {
           if(vm.$refs['form'])vm.$refs['form'].clearValidate();
        });
      },
      /**
       * @method 改变合同类型
       */
      changeContractType(val){
        let vm = this;
        vm.$emit('changeContractType',val);
      }
    }
  };
</script>
<style lang="scss">
  .baseContract--work-common-components {
    .form {
      .el-form-item {
        .el-form-item__label {
          display: flex;
          text-align: left;
          line-height: 30px;
          width: auto !important;

          &>div{
            flex-grow: 1;
          }
        }

        .el-form-item__content {
          margin-left: 0 !important;
          line-height: 30px;

          .el-input-group {
            vertical-align: middle;
          }
        }

        .el-input__inner {
          padding: 0 5px;
        }

        .el-date-editor {
          width: 100%;

          .el-input__inner {
            padding: 0 30px;
          }
        }

        /* 标准的表单布局 */
        &.normal {
          .el-form-item {
            display: block;
            margin-bottom: 10px;

            .el-form-item__label {
              text-align: right;
              width: 130px !important;
            }

            .el-form-item__content {
              margin-left: 130px !important;
            }
          }
        }
      }
    }

    /* 可控制显示块 */
    .control-block {
      margin-bottom: 20px;

      .el-row {
        margin-bottom: 5px;
      }
    }

    /* 项目详情标题 */
    .title {
      background-color: #edf1f4;
      padding: 20px;
      border-radius: 5px;
      line-height: initial;
      box-shadow: none;

      p {
        line-height: 19px;
      }
    }

    /* 表单样式 */
    .form {
      flex-grow: 1;

      .form-block {
        margin-top: 10px;
        background-color: #edf1f4;
        border-radius: 5px;
        padding: 20px 20px 20px 10px;

        .el-row {
          max-width: 860px;
          margin-right: 0 !important;
          margin-left: 0 !important;
          margin-bottom: 5px;

          .el-col {
            padding-right: 0 !important;
          }
        }
      }

      .el-form-item {
        display: flex;
        flex-direction: column;
        max-width: 860px;
        margin-bottom: 5px;
      }

      .el-select {
        width: 100%;
      }
    }

    h3 {
      &.form-title {
        padding-left: 20px;
      }
    }

    h4 {
      &.form-title {
        padding-left: 20px;
        margin-top: 10px;
        color: #999999;
        font-weight: normal;
      }
    }
  }
</style>
<style lang="scss" scoped></style>
