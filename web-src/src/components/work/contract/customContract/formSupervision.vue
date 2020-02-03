<template>
  <!-- 监理合同 -->
  <el-form class="formSupervision--work-contract-customContract form" ref="form" :model="form" :disabled="disabled">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="监理人">
            <el-input v-model="form.supervisor" placeholder="请输入监理人"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工程规模">
            <el-input v-model="form.projectScale" placeholder="请输入工程规模"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="工程概算投资额或建筑安装工程费">
            <template slot="label">
              <div style="position: relative;">
                工程概算投资额或建筑安装工程费
                <el-checkbox label="1" v-model="form.isInstalCostProvisional" true-label="1" false-label="0" style="position: absolute;right: -12px;">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.instalCost" placeholder="请输入工程概算投资额或建筑安装工程费"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">总监理工程师
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="总监理工程师姓名">
            <el-input v-model="form.engineerName" placeholder="请输入总监理工程师姓名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="身份证号码">
            <el-input v-model="form.IDNumber" placeholder="请输入身份证号码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="注册号">
            <el-input v-model="form.registrationNumber" placeholder="请输入注册号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="签约酬金与补偿费用">
            <template slot="label">
              <div style="position: relative;">
                签约酬金与补偿费用
                <el-checkbox label="1" v-model="form.isSignAmountProvisional" true-label="1" false-label="0" style="position: absolute;right: -12px;">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.signAmount" placeholder="请输入签约酬金与补偿费用"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="监理期限">
            <el-date-picker v-model="form.superviseDate" type="daterange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">合同订立
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="订立时间">
            <el-date-picker v-model="form.signDate" placeholder="请输入订立时间"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="订立地点">
            <el-input v-model="form.signAddress" placeholder="请输入订立地点"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">委托人合同相关信息
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="营业执照号">
            <el-input v-model="form.truBusinessLicenseNumber" placeholder="请输入营业执照号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="住所">
            <el-input v-model="form.truAddress" placeholder="请输入住所"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="邮政编码">
            <el-input v-model="form.truPostalCode" placeholder="请输入邮政编码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">法定代表人或其委托
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="开户银行">
            <el-input v-model="form.truOpenBank" placeholder="请输入开户银行"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="账号">
            <el-input v-model="form.truAcountNumber" placeholder="请输入账号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="电话">
            <el-input v-model="form.truTelNumber" placeholder="请输入电话"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="传真">
            <el-input v-model="form.truFaxNumber" placeholder="请输入传真"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="电子邮箱">
            <el-input v-model="form.truEMail" placeholder="请输入电子邮箱"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">监理人合同相关信息
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="营业执照号">
            <el-input v-model="form.supBusinessLicenseNumber" placeholder="请输入营业执照号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="资质证书编号">
            <el-input v-model="form.supCertificateNumber" placeholder="请输入资质证书编号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="住所">
            <el-input v-model="form.supAddress" placeholder="请输入住所"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮政编码">
            <el-input v-model="form.supPostalCode" placeholder="请输入邮政编码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">法定代表人或其委托
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="开户银行">
            <el-input v-model="form.supOpenBank" placeholder="请输入开户银行"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="账号">
            <el-input v-model="form.supAcountNumber" placeholder="请输入账号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="电话">
            <el-input v-model="form.supTelNumber" placeholder="请输入电话"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="传真">
            <el-input v-model="form.supFaxNumber" placeholder="请输入传真"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="电子邮箱">
            <el-input v-model="form.supEMail" placeholder="请输入电子邮箱"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="专用条款">
            <el-input v-model="form.specialClauses" placeholder="请输入专用条款"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="委托人代表为">
            <el-input v-model="form.trusterAgent" placeholder="请输入委托人代表为"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="监理酬金具体支付方式">
            <el-input v-model="form.paymentMethod" placeholder="请输入监理酬金具体支付方式"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="争议解决">
            <el-input v-model="form.disputeResolution" placeholder="请输入争议解决"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
  </el-form>
</template>

<script>
  export default {
    props: ['theFormData', "disabled"],
    data() {
      return {
        gutter: 40, // 表单相间距
        form: this.theFormData.form
      };
    },
    watch: {
      'theFormData.form'() {
        this.form = this.theFormData.form;
      }
    },
    methods: {}
  };
</script>
<style lang="scss">
  .formSupervision--work-contract-customContract {
    .form-block {

      .el-form-item__label,
      .el-radio__label {
        font-size: 12px;
      }
    }

    .el-form-item__label {
      text-align: left;
      line-height: 30px;
      width: 100% !important;

      .tentative {
        position: absolute;
        right: 0;
      }
    }

    .el-form-item__content {
      margin-left: 0 !important;
      line-height: 30px;
      position: relative;
      font-size: 14px;
      width: 100%;

      .el-select {
        width: 100%;
      }
    }
  }
</style>
<style lang="scss" scoped></style>
