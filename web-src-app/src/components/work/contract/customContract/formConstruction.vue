<template>
  <!-- 建设工程招标代理合同 -->
  <div class="custom-contract__form-construction">
    <ul class="classes">
      <p>建设工程招标代理合同</p>
      <li class="form-item">
        <label for="">省份</label>
        <div><input type="text" placeholder="请输入省份" v-model="currentForm.provinceName" v-validate="'required'" data-vv-name="省份"/></div>
      </li>
      <li class="form-item">
        <label for="">地区</label>
        <div><input type="text" placeholder="请输入地区" v-model="currentForm.districtName" v-validate="'required'" data-vv-name="地区"/></div>
      </li>
      <li class="form-item">
        <label for="">规模</label>
        <div><input type="text" placeholder="请输入规模" v-model="currentForm.scale" v-validate="'required'" data-vv-name="规模"/></div>
      </li>
      <li class="form-item">
        <label for="">招标规模</label>
        <div><input type="text" placeholder="请输入招标规模" v-model="currentForm.biddingScale" v-validate="'required'" data-vv-name="招标规模"/></div>
      </li>
      <li class="form-item">
        <label for="">代理工作内容</label>
        <div><input type="text" placeholder="请输入代理工作内容" v-model="currentForm.agentWorkContent" v-validate="'required'" data-vv-name="代理工作内容"/></div>
      </li>
      <li class="form-item">
        <label for="">合同订立地点</label>
        <div><input type="text" placeholder="请输入合同订立地点" v-model="currentForm.signAddress" v-validate="'required'" data-vv-name="合同订立地点"/></div>
      </li>
      <li class="form-item">
        <label for="">代理范围</label>
        <div><input type="text" placeholder="请输入代理范围" v-model="currentForm.agentRange" v-validate="'required'" data-vv-name="代理范围"/></div>
      </li>
      <li class="form-item">
        <m-select v-model="currentForm.agentContent" :data="{title: '代理内容',options: dataList.agentContent}"
          unique="value" display="label" v-validate="'required'" data-vv-name="代理内容">
        </m-select>
      </li>
      <li class="form-item">
        <m-select v-model="currentForm.paymentMethod" :data="{title: '代理报酬的支付方式',options: dataList.agencyRemuneration}"
          unique="value" display="label" v-validate="'required'" data-vv-name="代理报酬的支付方式">
        </m-select>
      </li>
    </ul>
  </div>
</template>

<script>
  import dataList from './dataList';// 选项以及表单数据
  export default{
    props:['form','theFormDisabled'],
    data(){
      return{
        dataList:this.tool.deepCopy(dataList, this.tool.deepCopy),// 选项以及表单数据
        currentForm:this.tool.deepCopy(this.form, this.tool.deepCopy),// 表单数据
      };
    },
    watch:{
      form:{
        handler(){
          let vm = this;
          vm.currentForm = this.tool.deepCopy(this.form, this.tool.deepCopy);// 表单数据
        },
        immediate: true
      }
    },
    methods:{
      /**
       * 获取表单数据
       */
      getData(){
        let vm = this;
         const promise = new Promise(function(resolve, reject) {
           vm.$validator.validate().then((v)=>{
             if(v){
              resolve(vm.tool.deepCopy(vm.currentForm, vm.tool.deepCopy));
             }else{
               resolve(false);
               vm.Toast(vm.errors.all()[0]);
             }
           });
         });
         return promise;
      }
    }
  }
</script>

<style>
</style>
