<template>
  <!-- 创建请款单 -->
  <div class="foundItem__dialog-request-payout">
    <form ref="form" class="projectForm">
      <div class="form-item">
        <label>请款单名</label>
        <input placeholder="请输入请款单名" v-model="form.name" v-validate="'required'" data-vv-name="请款单名">
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
          name: ''
        },
        currentData: this.tool.deepCopy(this.data,this.tool.deepCopy)
      }
    },
    watch: {
      handler() {
        let vm = this;
        vm.currentData = vm.tool.deepCopy(vm.data,vm.tool.deepCopy);
      },
      immediate: true
    },
    methods: {
      reset() {
        let vm = this;
        vm.form = {
          name: ''
        };
        vm.$emit('cancel');
      },
      submit() {
        let vm = this;
        vm.MessageBox.confirm('确定执行此操作?').then(action => {
          vm.$validator
            .validateAll()
            .then(result => {
              if (result) {
                let formData = {
                  contractId: vm.currentData.id,
                  contractName: vm.currentData.label,
                  requestMenuName: vm.form.name
                }
                //新增请款单
                vm.requestPost('/requestMenu/add/insertRequestMenuInfo', formData).then(
                  res => {
                    vm.Toast({
                      message: res.data.message,
                      position: 'middle',
                      className: 'toast',
                      duration: 2000
                    });
                    if (res.data.status != 'failure') {
                        let sessionData = vm.SStorage.getItem('/app/foundItem');
                        sessionData.businessId = res.data.primaryKey;
                        vm.SStorage.setItem('/app/foundItem', sessionData);
                        history.go(0);
                    }
                  }
                ).catch(err => {
                  vm.Toast({
                    message: err,
                    position: 'middle',
                    className: 'toast',
                    duration: 2000
                  });
                });
              } else {
                vm.Toast({
                  message: vm.errors.all()[0],
                  position: 'middle',
                  className: 'toast',
                  duration: 2000
                });
              }
            })
        });
      }
    }
  }
</script>


<style lang="scss">
  .foundItem__dialog-request-payout {
    position: absolute;
    height: 100vh;
    width: 100vw;
    top: 0;
    left: 0;
    z-index: 99999;
    background-color: white;
  }
</style>
