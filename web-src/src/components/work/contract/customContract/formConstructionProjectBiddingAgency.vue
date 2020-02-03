<template>
  <!-- 建设工程招标代理合同 -->
  <el-form class="formConstructionProjectBiddingAgency--work-contract-customContract form" ref="form" :model="form" :disabled="disabled">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="省份">
            <el-input v-model="form.provinceName" placeholder="请输入省份"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="地区">
            <el-input v-model="form.districtName" placeholder="请输入地区"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="规模">
            <el-input v-model="form.scale" placeholder="请输入规模"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="招标规模">
            <el-input v-model="form.biddingScale" placeholder="请输入招标规模"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="代理工作内容">
            <el-input v-model="form.agentWorkContent" placeholder="请输入代理工作内容"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="合同订立地点">
            <el-input v-model="form.signAddress" placeholder="请输入合同订立地点"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="代理范围">
            <el-input v-model="form.agentRange" placeholder="请输入代理范围"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="代理内容">
            <el-radio-group v-model="form.agentContent">
              <el-radio label="1">是</el-radio>
              <el-radio label="0" value="2">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="代理报酬的支付方式">
            <el-select v-model="form.paymentMethod" placeholder="请选择代理报酬的支付方式">
              <el-option label="委托人" value="委托人"></el-option>
              <el-option label="中标人" value="中标人"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

    </div>
  </el-form>
</template>

<script>
  export default {
    props: ['theFormData','disabled'],
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
  .formConstructionProjectBiddingAgency--work-contract-customContract {
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
    }

    .el-form-item__content {
      margin-left: 0 !important;
      line-height: 30px;
      position: relative;
      font-size: 14px;
      width: 100%;
    }
  }
</style>
<style lang="scss" scoped></style>
