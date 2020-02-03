<template>
  <!-- 合同 -->
  <component v-bind:is="view" :transmittedData="transmittedData" :typeFlag="typeFlag"></component>
</template>

<script>
  import axios from 'axios';
  // 默认
  import rightDefault from './right-default';
  // 归档
  import rightArchive from './right-archive';
  // 盖章
  import rightSeal from './right-seal';

  export default {
    components: {
      'right-default': rightDefault,
      'right-archive': rightArchive,
      'right-seal': rightSeal
    },
    props: ['transmittedData'],
    data() {
      return {
        view: 'right-default',
        typeFlag:""
      };
    },
    mounted() {
        const vm = this;
        this.searchContractStsInfo(this.transmittedData);

        // 合同基本信息显示
        vm.bus.$on('click', val => {
            vm.searchContractStsInfo(val);
        });

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
        getContractStsInfoFunc(contractId){
            let vm = this;
            let formData = {
                contractId: contractId
            };
            vm.apiAddress('/contract/query/searchContractArchiveSts', formData).then(res => {
                if (res.data.status == 'failure') {
                    vm.$message({
                        message: res.data.message,
                        type: 'error',
                        center: true
                    });
                } else {
                    // 合同状态标识 判断 哪些按钮展示不展示
                    let tempTypeFlag = res.data.typeFlag;

                    vm.typeFlag = tempTypeFlag;

                    // 如果状态不为提交审核 则页面所有数据不能修改
                    if (tempTypeFlag != '1'){
                        vm.bus.$emit('theFormDisabled',true);
                    }else {
                        vm.bus.$emit('theFormDisabled',false);
                    }

                    if(tempTypeFlag == '2' || tempTypeFlag == '3'|| tempTypeFlag == '4'|| tempTypeFlag == '5'){
                        vm.bus.$emit('onlyOfficeFlg',true);
                    }else{
                        vm.bus.$emit('onlyOfficeFlg',false);

                    }

                    // 如果状态为6 则展示盖章右侧信息
                    if (tempTypeFlag == '6'){
                        vm.view = 'right-seal';
                    }else {
                        vm.view = 'right-default';
                    }

                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            })
            this.centerDialogVisible = true
        }
    }
  };
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
</style>
