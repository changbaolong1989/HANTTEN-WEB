<template>
  <!-- 请款单表单 -->
  <div class="payrollRequisition">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>档案编号</label>
          <input type="text" placeholder="请输入档案编号" v-model="templateform.input1" v-validate="'required'"
          data-vv-as="档案编号"/>
        </div>
        <div class="form-item">
          <label>公司名称</label>
          <input type="text" placeholder="请输入公司名称" v-model="templateform.input2" v-validate="'required'"
          data-vv-as="公司名称"/>
        </div>
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称 " v-model="templateform.input3" v-validate="'required'"
          data-vv-as="项目名称"/>
        </div>
        <div class="form-item">
          <label>费用名称</label>
          <input type="text" placeholder="请输入费用名称" v-model="templateform.input4" v-validate="'required'"
          data-vv-as="费用名称"/>
        </div>
        <div class="form-item" @click="openPicker('picker')">
          <mt-cell title="工程节点请款时间">
            <span>{{ templateform.input5||'请输入' }}</span>
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input5')"></mt-datetime-picker>
          <input type="hidden"  v-model="templateform.input5" v-validate="'required'"
          data-vv-as="工程节点请款时间"/>

        <div class="form-item">
          <label>编制工作内容</label>
          <input type="text" placeholder="请输入编制工作内容" v-model="templateform.input6" v-validate="'required'"
          data-vv-as="编制工作内容"/>
        </div>
        <div class="form-item">
          <label>双方签订的合同</label>
          <input type="text" placeholder="请输入双方签订的合同" v-model="templateform.input7" v-validate="'required'"
          data-vv-as="双方签订的合同"/>
        </div>
        <div class="form-item">
          <label>合同类型</label>
          <input type="text" placeholder="请输入合同类型" v-model="templateform.input8" v-validate="'required'"
          data-vv-as="合同类型"/>
        </div>
        <div class="form-item">
          <label>收费说明</label>
          <textarea v-model="templateform.input9" placeholder="请输入收费说明" v-validate="'required'"
          data-vv-as="收费说明"></textarea>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
  import {
    Toast
  } from 'mint-ui';
export default {
  props:['form'],
  data() {
    return {
      templateform: {
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        input5: '',
        input6: '',
        input7: '',
        input8: '',
        input9: '',
      },
      pickerVisible: this.formatting(new Date()),
    };
  },
  watch:{
    form:{
      handler(){
        let vm = this;
        vm.templateform = vm.tool.deepCopy(vm.form,vm.tool.deepCopy);
        let date = new Date();// 默认值
        if(vm.templateform.input5)date = new Date(vm.templateform.input5);
        if(undefined===vm.templateform.input5){
          vm.$set(vm.templateform,'input5',this.formatting(date));
        }else{
          vm.templateform.input5 = this.formatting(date);
         }

      },
       immediate: true
    }
  },
  methods: {
    openPicker(text) {
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      let vm = this;
      if(undefined===this.templateform[text]){
        vm.$set( this.templateform,text ,this.formatting(this.pickerVisible))
      }else{
        this.templateform[text] = this.formatting(this.pickerVisible);
      }
    },
    /**
     * @method 获取表单数据
     */
    getFormData(){
      let vm = this;
      let promise = new Promise(function(resolve, reject) {
        vm.$validator.validateAll().then(result => {
          if (result) {
              resolve(vm.tool.deepCopy(vm.templateform,vm.tool.deepCopy));
          } else {
            vm.Toast({
              message: vm.errors.all()[0],
              position: 'middle',
              className: 'toast',
              duration: 2000
            });
           reject(vm.errors.all());
          }
        });
      });
    return promise;
    }
  }
};
</script>

<style lang="scss" scoped>
.block{
  border-radius: 10px 10px 0 0!important;
}
</style>
