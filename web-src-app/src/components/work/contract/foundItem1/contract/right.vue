<template>
  <!-- 合同 -->
  <component v-bind:is="view" :transmitData="transmitData" :typeFlag="typeFlag"></component>
</template>

<script>
  import axios from 'axios';
  // 默认
  import rightDefault from './right-default';
  import {
    Toast
  } from 'mint-ui';
  export default {
    components: {
      'right-default': rightDefault
    },
    props: ['transmitData'],
    data() {
      return {
        view: 'right-default',
        typeFlag: "",
        currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy) //传输数据
      };
    },
    watch: {
      transmitData: {
        handler() {
          let vm = this;
          vm.currentTransmitData = vm.tool.deepCopy(vm.currentTransmitData, vm.tool.deepCopy);
          this.searchContractStsInfo(vm.currentTransmitData.data);
        },
        immediate: true
      }
    },
    methods: {
      searchContractStsInfo(val) {
        let vm = this;
        vm.rowInfoLevel = val.treeNodeLevel;
        // 合同层级时 查询合同状态 判断展示哪个按钮
        if (val.treeNodeLevel == 3) {
          vm.getContractStsInfoFunc(val.id);
        }
      },
      getContractStsInfoFunc(contractId) {
        let vm = this;
        let formData = {
          contractId: contractId
        };
        vm.requestPost('/contract/query/searchContractArchiveSts', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.Toast({
              message: res.data.message,
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
          } else {
            // 合同状态标识 判断 哪些按钮展示不展示
            let tempTypeFlag = res.data.typeFlag;
            vm.typeFlag = tempTypeFlag;

            // 如果状态不为提交审核 则页面所有数据不能修改
            if (tempTypeFlag != '1') {
              vm.bus.$emit('theFormDisabled', true);
            } else {
              vm.bus.$emit('theFormDisabled', false);
            }
            if(tempTypeFlag == '2' || tempTypeFlag == '3'|| tempTypeFlag == '4'|| tempTypeFlag == '5'){
                vm.bus.$emit('onlyOfficeFlg',true);
            }else{
                vm.bus.$emit('onlyOfficeFlg',false);
            }
          }
        }).catch(err => {
          vm.Toast({
            message: err,
            position: 'middle',
            className: 'toast',
            duration: 2000
          });
        })
      }
    }
  };
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
</style>
