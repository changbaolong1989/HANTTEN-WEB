<template>
  <div class="project-basic-information--found-item">
    <basic-information ref="basicInformation" v-if="currentTransmittedData.isMainHeadFlag !== '1'" :nodeInfo="currentTransmittedData" :_form="currentTheFormData.baseContract"></basic-information>
    <base-contract ref="baseContract" v-else :_form="currentTheFormData.baseContract" :disabled="disabled"></base-contract>
  </div>
</template>

<script>
  // 基本合同信息
  import baseContract from '../../../common/components/baseContract.vue';
  // 基本合同信息表
  import basicInformation from '../../../common/components/basicInformation.vue';

  export default {
    components: {
      'base-contract': baseContract,
      'basic-information': basicInformation
    },
    props: ['transmittedData' /* 节点信息 */ , 'theFormData' /* 表单信息 */ ],
    data() {
      return {
        disabled:false,// 禁用表单
        /* 节点信息 */
        currentTransmittedData: this.tool.deepCopy(this.transmittedData, this.tool.deepCopy),
        /* 表单信息 */
        currentTheFormData: this.tool.deepCopy(this.theFormData, this.tool.deepCopy)
      }
    },
    watch:{
      // 节点信息
      transmittedData:{
        handler(){
          let vm = this;
          vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData, vm.tool.deepCopy);
          // 当合同作废或者该项目下有合同被提审的时候，表单不可填写
          vm.disabled = vm.currentTransmittedData.isInvalidFlag === "1";
        },
        immediate: true
      },
      // 表单信息
      theFormData:{
        handler(){
          let vm = this;
          vm.currentTheFormData = vm.tool.deepCopy(vm.theFormData, vm.tool.deepCopy);
        },
        immediate: true
      }
    },
    mounted() {
      let vm = this;
      vm.$nextTick(function() {
        // 走了两次mounted,这里为了保险,先取消一下事件监听
        vm.bus.$off('saveProjectBasicInformation');
        // 保存基本合同信息表
        vm.bus.$on('saveProjectBasicInformation', function() {
          // 保存基本合同信息表
          vm.save();
        });
      });
      // 查询基本合同信息表
      vm.excSearchBaseContractDetailInfo();
    },
    methods: {
      /**
       * @method 查询基本合同信息表
       */
      excSearchBaseContractDetailInfo(){
        let vm = this;
        // 回显
        vm.apiAddress("/contract/query/excSearchBaseContractDetailInfo", {
          // 合同id
          "contractId":vm.currentTransmittedData.contractId
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            vm.$nextTick(function() {
              // 设置回显数据
              if(vm.$refs['baseContract'])vm.$refs['baseContract'].setData(res.data.dataVO);
              if(vm.$refs['basicInformation'])vm.$refs['basicInformation'].setData(res.data.dataVO);
            });
          } else {
            vm.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          vm.stateDialog('error', e)
        });
      },
      /**
       * @method 保存基本合同信息表
       */
      save() {
        let vm = this;
        // 获取表单数据
        vm.$nextTick(function() {
          // 确保组件注册过后调用其方法,获取数据
          vm.$refs['baseContract'].getData().then(function(data){
            // 合同id
            data.append("contractId",vm.currentTransmittedData.contractId);
            // 保存
            vm.apiAddress("/contract/modify/updateBaseContractInfo", data).then(res => {
              // 如果返回成功，则绘制数据，否则提示失败信息
              if (res.data.status == "success") {
                // 刷新页面
                // vm.$router.go(0);
                // 触发事件,重新刷新项目树
                vm.bus.$emit('asideRefresh',{checkedKey:res.data.baseId});
              } else {
                vm.stateDialog('error', res.data.message)
              }
            }).catch(e => {
              vm.stateDialog('error', e)
            });
          });
         });
      }
    }
  }
</script>
<style>
  @import url('../../../common/css/other.css');

  /* 文件列表*/
  .task {}
</style>
<style lang="scss" scoped>
  .loaded{
    position:absolute;
  }
</style>
