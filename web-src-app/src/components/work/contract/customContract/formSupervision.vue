<template>
  <!-- 监理合同 -->
  <div class="custom-contract__form-manufacturing-cost">
    <ul class="classes">
      <p>监理合同</p>
      <li class="form-item">
        <label for="">监理人</label>
        <div><input type="text" placeholder="请输入监理人" v-model="currentForm.supervisor" v-validate="'required'" data-vv-name="监理人"/></div>
      </li>
      <li class="form-item">
        <label for="">工程规模</label>
        <div><input type="text" placeholder="请输入工程规模" v-model="currentForm.projectScale" v-validate="'required'" data-vv-name="工程规模"/></div>
      </li>
      <li class="form-item zanding">
        <label for="">工程概算投资额或建筑安装工程费</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入工程概算投资额或建筑安装工程费" v-model="currentForm.instalCost" v-validate="'required'" data-vv-name="工程概算投资额或建筑安装工程费"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isInstalCostProvisional">暂定</mt-switch>
        </div>
      </li>
      <p>总监理工程师</p>
      <li class="form-item">
        <label for="">总监理工程师姓名</label>
        <div><input type="text" placeholder="请输入总监理工程师姓名" v-model="currentForm.engineerName" v-validate="'required'" data-vv-name="总监理工程师姓名"/></div>
      </li>
      <li class="form-item">
        <label for="">身份证号码</label>
        <div><input type="text" placeholder="请输入身份证号码" v-model="currentForm.IDNumber" v-validate="'required'" data-vv-name="身份证号码"/></div>
      </li>
      <li class="form-item">
        <label for="">注册号</label>
        <div><input type="text" placeholder="请输入注册号" v-model="currentForm.registrationNumber" v-validate="'required'" data-vv-name="注册号"/></div>
      </li>
      <li class="form-item zanding">
        <label for="">签约酬金与补偿费用</label>
        <div class="zandiv">
          <input type="text" placeholder="请输入签约酬金与补偿费用" v-model="currentForm.signAmount" v-validate="'required'" data-vv-name="签约酬金与补偿费用"/>
          <span>元</span>
          <mt-switch v-model="currentForm.isSignAmountProvisional">暂定</mt-switch>
        </div>
      </li>

      <li class="form-item">
        <label>监理期限</label>
        <div class="temporal">
          <span @click="openPicker('completed')">{{ tool.dateFormat(currentForm.superviseDateStart,'--') }}</span>
          <input type="text" v-model="currentForm.superviseDateStart" v-validate="'required'" data-vv-name="监理期限--开始日期" hidden/>
          <b>———</b>
          <span @click="openPicker('completed1')">{{ tool.dateFormat(currentForm.superviseDateEnd,'--') }}</span>
          <input type="text" v-model="currentForm.superviseDateEnd" v-validate="'required'" data-vv-name="监理期限--结束日期" hidden/>
        </div>
      </li>

      <mt-datetime-picker ref="completed" v-model="pickerValue" type="date" @confirm="handleConfirm('superviseDateStart')"></mt-datetime-picker>
      <mt-datetime-picker ref="completed1" v-model="pickerValue" type="date" @confirm="handleConfirm('superviseDateEnd')"></mt-datetime-picker>
      <p>合同订立</p>

      <li class="form-item" @click="openPicker('conclude')">
        <mt-cell title="订立时间">
          <span>{{ tool.dateFormat(currentForm.signDate,'--') }}</span>
          <input type="text" v-model="currentForm.signDate" v-validate="'required'" data-vv-name="订立时间" hidden/>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </li>

      <mt-datetime-picker ref="conclude" type="date" v-model="pickerValue" @confirm="handleConfirm('signDate')"></mt-datetime-picker>
      <li class="form-item">
        <label for="">订立地点</label>
        <div><input type="text" placeholder="请输入订立地点" v-model="currentForm.signAddress" v-validate="'required'" data-vv-name="订立地点"/></div>
      </li>
      <p>委托人合同相关信息</p>
      <li class="form-item">
        <label for="">营业执照号</label>
        <div><input type="text" placeholder="请输入营业执照号" v-model="currentForm.truBusinessLicenseNumber" v-validate="'required'" data-vv-name="营业执照号"/></div>
      </li>
      <li class="form-item">
        <label for="">住所</label>
        <div><input type="text" placeholder="请输入住所" v-model="currentForm.truAddress" v-validate="'required'" data-vv-name="住所"/></div>
      </li>
      <li class="form-item">
        <label for="">邮政编码</label>
        <div><input type="text" placeholder="请输入邮政编码" v-model="currentForm .truPostalCode" v-validate="'required'" data-vv-name="邮政编码"/></div>
      </li>
      <p>法定代表人或其委托</p>
      <li class="form-item">
        <label for="">开户银行</label>
        <div><input type="text" placeholder="请输入开户银行" v-model="currentForm.truOpenBank" v-validate="'required'" data-vv-name="开户银行"/></div>
      </li>
      <li class="form-item">
        <label for="">账号</label>
        <div><input type="text" placeholder="请输入账号" v-model="currentForm.truAcountNumber" v-validate="'required'" data-vv-name="账号"/></div>
      </li>
      <li class="form-item">
        <label for="">电话</label>
        <div><input type="text" placeholder="请输入电话" v-model="currentForm.truTelNumber" v-validate="'required'" data-vv-name="电话"/></div>
      </li>
      <li class="form-item">
        <label for="">传真</label>
        <div><input type="text" placeholder="请输入传真" v-model="currentForm.truFaxNumber" v-validate="'required'" data-vv-name="传真"/></div>
      </li>
      <li class="form-item">
        <label for="">电子邮箱</label>
        <div><input type="text" placeholder="请输入电子邮箱" v-model="currentForm.truEMail" v-validate="'required'" data-vv-name="电子邮箱"/></div>
      </li>
      <p>监理人合同相关信息</p>
      <li class="form-item">
        <label for="">营业执照号</label>
        <div><input type="text" placeholder="请输入营业执照号" v-model="currentForm.supBusinessLicenseNumber" v-validate="'required'" data-vv-name="营业执照号"/></div>
      </li>
      <li class="form-item">
        <label for="">资质证书编号</label>
        <div><input type="text" placeholder="请输入资质证书编号" v-model="currentForm.supCertificateNumber" v-validate="'required'" data-vv-name="资质证书编号"/></div>
      </li>
      <li class="form-item">
        <label for="">住所</label>
        <div><input type="text" placeholder="请输入住所" v-model="currentForm.supAddress" v-validate="'required'" data-vv-name="住所"/></div>
      </li>
      <li class="form-item">
        <label for="">邮政编码</label>
        <div><input type="text" placeholder="请输入邮政编码" v-model="currentForm.supPostalCode" v-validate="'required'" data-vv-name="邮政编码"/></div>
      </li>
      <p>法定代表人或其委托</p>
      <li class="form-item">
        <label for="">开户银行</label>
        <div><input type="text" placeholder="请输入开户银行" v-model="currentForm.supOpenBank" v-validate="'required'" data-vv-name="开户银行"/></div>
      </li>
      <li class="form-item">
        <label for="">账号</label>
        <div><input type="text" placeholder="请输入账号" v-model="currentForm.supAcountNumber" v-validate="'required'" data-vv-name="账号"/></div>
      </li>
      <li class="form-item">
        <label for="">电话</label>
        <div><input type="text" placeholder="请输入电话" v-model="currentForm.supTelNumber" v-validate="'required'" data-vv-name="电话"/></div>
      </li>
      <li class="form-item">
        <label for="">传真</label>
        <div><input type="text" placeholder="请输入传真" v-model="currentForm.supFaxNumber" v-validate="'required'" data-vv-name="传真"/></div>
      </li>
      <li class="form-item">
        <label for="">电子邮箱</label>
        <div><input type="text" placeholder="请输入电子邮箱" v-model="currentForm.supEMail" v-validate="'required'" data-vv-name="电子邮箱"/></div>
      </li>
      <li class="form-item">
        <label for="">专用条款</label>
        <div><input type="text" placeholder="请输入专用条款" v-model="currentForm.specialClauses" v-validate="'required'" data-vv-name="专用条款"/></div>
      </li>
      <li class="form-item">
        <label for="">委托人代表为</label>
        <div><input type="text" placeholder="请输入委托人代表为" v-model="currentForm.trusterAgent" v-validate="'required'" data-vv-name="委托人代表为"/></div>
      </li>
      <li class="form-item">
        <label for="">监理酬金具体支付方式</label>
        <div><input type="text" placeholder="请输入监理酬金具体支付方式" v-model="currentForm.paymentMethod" v-validate="'required'" data-vv-name="监理酬金具体支付方式"/></div>
      </li>
      <li class="form-item">
        <label for="">争议解决</label>
        <div><input type="text" placeholder="请输入争议解决" v-model="currentForm.disputeResolution" v-validate="'required'" data-vv-name="争议解决"/></div>
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
        pickerValue: new Date()// 日期控件时间
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
       *  @method 打开日期选择插件
       * @param {Object} text 表单字段名称
       * @param {Object} index  如果是动态添加,则为索引值
       */
      openPicker(text, index) {
        if(this.theFormDisabled)return;// 禁用表单
        this.$refs[text].open();
      },
      /**
       * @method 确认日期选择
       * @param {Object} text 表单字段名称
       * @param {Object} index  如果是动态添加,则为索引值
       */
      handleConfirm(text, index) {
        this.currentForm[text] = this.formatting(this.pickerValue);
      },
      /**
       * @method 获取日期
       */
      getDate(val){
        return val?new Date(val):new Date();
      },
      /**
       * 获取表单数据
       */
      getData(){
         let vm = this;
          const promise = new Promise(function(resolve, reject) {
            vm.$validator.validate().then((v)=>{
              if(v){
                let form = vm.tool.deepCopy(vm.currentForm, vm.tool.deepCopy);
                 form.superviseDateStart = vm.getDate(form.superviseDateStart);
                 form.superviseDateEnd = vm.getDate(form.superviseDateEnd);
                 form.signDate = vm.getDate(form.signDate);
               resolve(vm.tool.deepCopy(form, vm.tool.deepCopy));
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
