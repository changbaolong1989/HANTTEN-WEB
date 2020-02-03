<template>
  <!-- 工程管理咨询服务合同 -->
  <div class="custom-contract__form-manufacturing-cost">
    <ul class="classes">
      <p>工程管理咨询服务合同</p>
      <li class="form-item">
        <label for="">省份</label>
        <div><input type="text" placeholder="请输入省份" v-model="currentForm.provinceName" v-validate="'required'" data-vv-name="省份"/></div>
      </li>
      <li class="form-item">
        <label for="">地区</label>
        <div><input type="text" placeholder="请输入地区" v-model="currentForm.districtName" v-validate="'required'" data-vv-name="地区"/></div>
      </li>
      <li class="form-item">
        <label for="">服务内容</label>
        <div><input type="text" placeholder="请输入服务内容" v-model="currentForm.serviceContent" v-validate="'required'" data-vv-name="服务内容"/></div>
      </li>
      <li class="form-item">
        <label for="">咨询工程师地址</label>
        <div><input type="text" placeholder="请输入咨询工程师地址" v-model="currentForm.consultAddress" v-validate="'required'" data-vv-name="咨询工程师地址"/></div>
      </li>
      <li class="form-item">
        <label for="">电话号码</label>
        <div><input type="text" placeholder="请输入电话号码" v-model="currentForm.telNumber" v-validate="'required'" data-vv-name="电话号码"/></div>
      </li>
      <li class="form-item">
        <label for="">传真号码</label>
        <div><input type="text" placeholder="请输入传真号码" v-model="currentForm.faxNumber" v-validate="'required'" data-vv-name="传真号码"/></div>
      </li>
      <li class="form-item">
        <label for="">项目建筑面积</label>
        <div><input type="text" placeholder="请输入项目建筑面积" v-model="currentForm.floorage" v-validate="'required'" data-vv-name="项目建筑面积"/></div>
      </li>
      <li class="form-item">
        <label for="">项目使用性质</label>
        <div><input type="text" placeholder="请输入项目使用性质" v-model="currentForm.useNature" v-validate="'required'" data-vv-name="项目使用性质"/></div>
      </li>
      <li class="form-item zanding">
        <label for="">设计阶段服务报酬</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入设计阶段服务报酬" v-model="currentForm.designPay" v-validate="'required'" data-vv-name="设计阶段服务报酬"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isDesignPayProvisional">暂定</mt-switch>
        </div>
      </li>
      <li class="form-item zanding">
        <label for="">施工阶段服务报酬</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入施工阶段服务报酬" v-model="currentForm.constructionPay" v-validate="'required'" data-vv-name="施工阶段服务报酬"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isConstructionPayProvisional">暂定</mt-switch>
        </div>
      </li>
      <li class="form-item zanding">
        <label for="">结算阶段服务报酬</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入结算阶段服务报酬" v-model="currentForm.settlementPay" v-validate="'required'" data-vv-name="结算阶段服务报酬"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isSettlementPayProvisional">暂定</mt-switch>
        </div>
      </li>
      <li class="form-item zanding">
        <label for="">预付款</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入预付款" v-model="currentForm.advancePayment" v-validate="'required'" data-vv-name="预付款"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isAdvancePaymentProvisional">暂定</mt-switch>
        </div>
      </li>
      <li class="form-item zanding">
        <label for="">进度款</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入进度款" v-model="currentForm.progressPayment" v-validate="'required'" data-vv-name="进度款"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isProgressPaymentProvisional">暂定</mt-switch>
        </div>
      </li>
      <li class="form-item zanding">
        <label for="">余款</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入余款" v-model="currentForm.balance" v-validate="'required'" data-vv-name="余款"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isBalanceProvisional">暂定</mt-switch>
        </div>
      </li>
      <li class="form-item">
        <label>附加条款</label>
        <textarea placeholder="请输入附加条款" v-model="currentForm.addClauses" v-validate="'required'" data-vv-name="附加条款"></textarea>
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
