<template>
  <div class="project-basic-information--found-item">
    <!-- 项目基本信息表格 -->
    <basic-information
      ref="basicInformation"
      v-if="currentTransmittedData.data.isMainHeadFlag !== '1'"
      :nodeInfo="currentTransmitData.data"
      :_form="currentTransmitData.baseContract"
    ></basic-information>
    <!-- 项目基本信息表单v-else -->
    <base-contract ref="baseContract" :form="currentTransmitData" :theFormDisabled="disabled" :conceal="false" v-else></base-contract>
    <right :transmitData="currentTransmittedData.data"></right>
  </div>
</template>

<script>
// 合同表单的默认数据
import contractForm from '../contract/contractForm.js';
// 基本合同信息表
import basicInformation from '../../customContract/basicInformation.vue';
// 基本合同信息
import baseContract from '../../customContract/index.vue';
import setContractDetailInfo from './setContractDetailInfo.js'
import getContractDetailInfo from './getContractDetailInfo.js'
// 右侧操作按钮
import right from './right.vue';
export default {
  components: {
    'basic-information': basicInformation,
    'base-contract': baseContract,
    right: right
  },
  props: ['transmitData' /* 节点信息 */],
  data() {
    let sourceData = {
      indexContract: {
        // 合同index页
        form: this.tool.deepCopy(contractForm.indexContract, this.tool.deepCopy)
      },
      baseContract: {
        // 基础合同数据
        form: this.tool.deepCopy(contractForm.baseContract, this.tool.deepCopy),
        controlObj: {} // 控制对象
      },
      formCostConsulting: {
        // 造价合同
        form: this.tool.deepCopy(contractForm.formCostConsulting, this.tool.deepCopy)
      },
      formConstructionProjectBiddingAgency: {
        // 建设工程招标代理合同
        form: this.tool.deepCopy(contractForm.formConstructionProjectBiddingAgency, this.tool.deepCopy)
      },
      formProjectManagementConsultingService: {
        // 工程管理咨询服务合同
        form: this.tool.deepCopy(contractForm.formProjectManagementConsultingService, this.tool.deepCopy)
      },
      formSupervision: {
        // 工程管理咨询服务合同
        form: this.tool.deepCopy(contractForm.formSupervision, this.tool.deepCopy)
      }
    };
    return {
      disabled: false, // 禁用表单
      sourceData: sourceData,
      /* 节点信息 */
      currentTransmittedData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy)
    };
  },
  mounted() {
    let vm = this;
    vm.$nextTick(function() {
      // 走了两次mounted,这里为了保险,先取消一下事件监听
      vm.bus.$off('saveProjectBasicInformation1');
      // 保存基本合同信息表
      vm.bus.$on('saveProjectBasicInformation1', function() {
        // 保存基本合同信息表
        vm.save();
      });
    });
    // 查询基本合同信息表
    vm.getContractDetailInfo();
  },
  watch: {
    // 节点信息
    transmitData: {
      handler() {
        let vm = this;
        vm.currentTransmitData = vm.tool.deepCopy(Object.assign({},vm.transmitData,this.sourceData), vm.tool.deepCopy);
        // 当合同作废或者该项目下有合同被提审的时候，表单不可填写
        vm.disabled = vm.currentTransmitData.data.isInvalidFlag === '1' || vm.currentTransmitData.data.hasContractApply !== '0';
        // 获取合同详细信息
        vm.getContractDetailInfo();
      },
      immediate: true
    }
  },
  methods: {
    getContractDetailInfo() {
        let vm = this;
        // 回显
        vm.requestPost("/contract/query/excSearchBaseContractDetailInfo", {
          "projectId":vm.currentTransmittedData.data.projectId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 设置回显数据
              vm.currentTransmittedData = getContractDetailInfo(res,this)
          } else {
            vm.Toast(res.data.message)
          }
        }).catch(e => {
          vm.Toast(e)
        });
    },
    save() {
      let vm = this;
      // 获取表单数据
      vm.$nextTick(function() {
        // 确保组件注册过后调用其方法,获取数据
        vm.$refs['baseContract'].getData().then(v=>{
          //v :表单校验通过返回数据,否则返回Boolean值false
          if(v){
            var data = setContractDetailInfo(this.currentTransmitData,v);
            data.append('projectId', vm.currentTransmittedData.data.projectId);
            // 保存
            vm.requestPost('/contract/modify/updateBaseContractInfo', data)
              .then(res => {
                // 如果返回成功，则绘制数据，否则提示失败信息
                if (res.data.status == 'success') {
                  // 刷新页面
                  vm.$router.go(0);
                } else {
                  vm.Toast(res.data.message);
                }
              })
              .catch(e => {
                vm.Toast(e);
              });
          }
        });
      });
    }
  }
};
</script>
<style></style>
<style lang="scss" scoped></style>
