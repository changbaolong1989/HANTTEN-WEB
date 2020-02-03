<template>
  <div>
    <common-list-btn-add :addComponentKey="addComponentKey" :addComponentText="addComponentText"
                         style="float: right"></common-list-btn-add>
    <contract-list></contract-list>
  </div>
</template>

<script>

  import ContractList from './contract-list'
  import CommonListBtnAdd from '@/components/common/common-list-btn-add'

  export default {
    components: {CommonListBtnAdd, ContractList},
    data() {
      return {
        addComponentKey: 'WholeProcessContractListAdd',
        addComponentText: '创建合同台账',
        projectId: this.$parent.projectId
      }
    },
    computed: {
      tenderInfo: {
        get: function () {
          return this.$store.state.wholeProcess.contractObject.tenderInfo;
        },
        set: function (newVal) {
          this.$store.state.wholeProcess.contractObject.tenderInfo = newVal;
        }
      }
    },
    mounted() {
      const vm = this;
      vm.$axios.post('/wholeProcess/contract/query/searchContractName', JSON.stringify({"projectId": vm.projectId}), vm.config).then(function (response) {
        if (response.data.status === "success") {
          vm.tenderInfo = response.data.dataList;
        } else {
          vm.$message({
            type: 'error',
            message: response.data.message
          });
        }
      });
    },
    destroyed() {
      this.tenderInfo = [];
    }
  }
</script>

<style lang="scss">

</style>
