<template>
  <!-- 法务意见 -->
  <div class="foundItem__dialog-request-payout">
    <form ref="form" class="projectForm">
      <div class="form-item">
        <label>法务意见</label>
        <textarea  placeholder="请输入法务意见" v-model="form.input" v-validate="'required'" data-vv-name="法务意见"  :readonly="!!currentData.legalOpinion"></textarea>
      </div>
    </form>
    <div class="commonFooter">
      <mt-button @click="reset">取消</mt-button>
      <mt-button class="linear" @click="submit" v-if="!currentData.legalOpinion">确定</mt-button>
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
          input: this.tool.deepCopy(this.data,this.tool.deepCopy).legalOpinion||'',
        },
        currentData: this.tool.deepCopy(this.data,this.tool.deepCopy)
      }
    },
    watch: {
      data:{
        handler() {
          let vm = this;
          vm.currentData = vm.tool.deepCopy(vm.data,vm.tool.deepCopy);
          vm.form.input = vm.currentData.legalOpinion||'';
        },
        immediate: true
      }
    },
    methods: {
      reset() {
        let vm = this;
        vm.form = {
          input: ''
        };
        vm.$emit('cancel');
      },
      submit() {
        let vm = this;
        vm.$validator
          .validateAll()
          .then(result => {
            if (result) {
              let formData = {
                legalOpinion:vm.form.input,//-法务意见
                contractId:vm.currentData.contractId,
              };
             vm.requestPost('/legalAffair/modify/insertLegalOpinion', formData).then(res => {
                vm.Toast(res.data.message);
                if (res.data.status != 'failure')vm.reset();
             }).catch(err => {
               vm.Toast(err);
             })
            } else {
               vm.Toast(vm.errors.all()[0]);
            }
          })
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
