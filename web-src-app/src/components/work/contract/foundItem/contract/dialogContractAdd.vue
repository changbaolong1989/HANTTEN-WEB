<template>
  <!-- 添加合同 -->
  <div class="foundItem__dialog-contract-add">
    <form ref="form" class="projectForm">

      <div class="form-item">
        <label>指定人员</label>
        <m-select
          v-model="form.designatedPersonnel"
          :data="{title: '指定人员',options: this.designatedPersonnel}"
          unique="jobId"
          display="userName"
          v-validate="'required'"
          data-vv-name="指定人员"
          @change="change">
        </m-select>
      </div>

      <div class="form-item">
        <label>合同编号</label>
        <input placeholder="请输入合同编号" v-model="form.contractNumber" v-validate="'required'" data-vv-name="合同编号">
      </div>

    </form>
    <div class="commonFooter">
      <mt-button @click="reset">取消</mt-button>
      <mt-button class="linear" @click="submit">确定</mt-button>
    </div>
  </div>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
  export default {
    props: ['data'],
    data() {
      return {
        form: {
          designatedPersonnel: '',// 指定人员
          contractNumber:'',// 合同编号
          departmentId:''// 部门标识
        },
        designatedPersonnel:[],// 指定人员数组
        currentData: this.tool.deepCopy(this.data.data,this.tool.deepCopy)
      }
    },
    watch: {
      data:{
        handler() {
          let vm = this;
          vm.currentData = vm.tool.deepCopy(vm.data.data,vm.tool.deepCopy);
          if('修改合同'==vm.data.name){
           vm.form.contractNumber =  vm.currentData.label.substring(vm.currentData.label.indexOf("-") + 1);
          }
          vm.getPersonList();// 获取指定人员下拉列表
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 获取合同信息
       */
      getContractInfo() {
        const vm = this;
        // 查询合同信息
        this.requestPost('/project/query/excSearchContractInfo', {
          // 合同id
          "contractId": vm.currentData.id
        }).then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == "success") {
            // 职位id
            vm.form.designatedPersonnel = res.data.dataVO.jobId;
          } else {
            vm.Toast(res.data.message);
          }
        }).catch(e => {
          vm.Toast(e);
        })

      },
      /**
       * @event 改变指定人员事件
       */
      change(val,option){
        let vm = this;
        vm.form.departmentId = option.departmentId;
      },
      /**
       *@method 获取指定人员下拉列表
       */
      getPersonList() {
        const vm = this;
        // 查询人员
        vm.requestPost('/project/query/excSearchPersonList', {}).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            // 设置指定人员下拉信息
            vm.designatedPersonnel = res.data.dataList;
            if('修改合同'==vm.data.name){
              // 获取合同信息
               vm.getContractInfo();
            }
          } else {
            vm.Toast(res.data.message);
          }
        }).catch(e => {
          vm.Toast(e);
        })

      },
      /**
       * @method 重置
       */
      reset() {
        let vm = this;
        vm.form = {
          designatedPersonnel: '',// 指定人员
          contractNumber:'',// 合同编号
          departmentId:''// 部门标识
        };
        vm.$emit('cancel');
      },
      /**
       * @event 提交表单
       */
      submit() {
        let vm = this;
        vm.$validator
          .validateAll()
          .then(result => {
            if (result) {
              let vm = this;
              let formData = {
                // 项目id
                "projectId": vm.SStorage.getItem('/app/foundItem').projectId,
                // 合同编号
                "contractNum": vm.form.contractNumber,
                // 部门id
                "departmentId": vm.form.departmentId,
                // 职位id
                "jobId": vm.form.designatedPersonnel
              };
              let url = '/project/add/saveContractInfo';
              if('修改合同'==vm.data.name){
                // 合同标识
                formData.contractId = vm.currentData.id;
                url = '/project/modify/updateContractInfo';
              }
              vm.requestPost(url, formData).then(res => {
                vm.Toast(res.data.message);
                if (res.data.status != 'failure')vm.reset();
              }).catch(err => {
                vm.Toast(err);
              });
            } else {
              vm.Toast(vm.errors.all()[0]);
            }
          })
      }
    }
  }
</script>


<style lang="scss">
  .foundItem__dialog-contract-add {
    position: absolute;
    height: 100vh;
    width: 100vw;
    top: 0;
    left: 0;
    z-index: 99999;
    background-color: white;
  }
</style>
