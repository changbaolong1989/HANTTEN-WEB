<template>
  <!-- 工程管理咨询服务合同 -->
  <el-form class="formProjectManagementConsultingService--work-contract-customContract form" ref="form" :model="form" :disabled="disabled">
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
          <el-form-item label="服务内容">
            <el-input v-model="form.serviceContent" placeholder="请输入服务内容"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="咨询工程师地址">
            <el-input v-model="form.consultAddress" placeholder="请输入咨询工程师地址"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="电话号码">
            <el-input v-model="form.telNumber" placeholder="请输入电话号码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="传真号码">
            <el-input v-model="form.faxNumber" placeholder="请输入传真号码"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="项目建筑面积">
            <el-input v-model="form.floorage" placeholder="请输入项目建筑面积"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目使用性质">
            <el-input v-model="form.useNature" placeholder="请输入项目使用性质"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="设计阶段服务报酬">
            <template slot="label">
              <div style="position: relative;">
                设计阶段服务报酬
                <el-checkbox class="tentative" label="1" v-model="form.isDesignPayProvisional" true-label="1" false-label="0">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.designPay" placeholder="请输入设计阶段服务报酬">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="施工阶段服务报酬">
            <template slot="label">
              <div style="position: relative;">
                施工阶段服务报酬
                <el-checkbox class="tentative" label="1" v-model="form.isConstructionPayProvisional" true-label="1" false-label="0">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.constructionPay" placeholder="请输入施工阶段服务报酬">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="结算阶段服务报酬">
            <template slot="label">
              <div style="position: relative;">
                结算阶段服务报酬
                <el-checkbox class="tentative" label="1" v-model="form.isSettlementPayProvisional" true-label="1" false-label="0">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.settlementPay" placeholder="请输入结算阶段服务报酬">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :span="12">
        	<el-form-item label="合计">
        		<template slot="label">
        			<div style="position: relative;">
        				合计
        				<el-checkbox :label="1" v-model="form.checkbox" style="position: absolute;right: -12px;">暂定</el-checkbox>
        			</div>
        		</template>
        		<el-input v-model="form.input" placeholder="请输入合计"></el-input>
        	</el-form-item>
        </el-col>-->
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="预付款">
            <template slot="label">
              <div style="position: relative;">
                预付款
                <el-checkbox class="tentative" label="1" v-model="form.isAdvancePaymentProvisional" true-label="1" false-label="0">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.advancePayment" placeholder="请输入预付款">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="进度款">
            <template slot="label">
              <div style="position: relative;">
                进度款
                <el-checkbox class="tentative" label="1" v-model="form.isProgressPaymentProvisional" true-label="1" false-label="0">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.progressPayment" placeholder="请输入进度款">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="余款">
            <template slot="label">
              <div style="position: relative;">
                余款
                <el-checkbox class="tentative" label="1" v-model="form.isBalanceProvisional" true-label="1" false-label="0">暂定</el-checkbox>
              </div>
            </template>
            <el-input v-model="form.balance" placeholder="请输入余款">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="附加条款">
            <el-input type="textarea" v-model="form.addClauses" placeholder="请输入附加条款"></el-input>
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
  .formProjectManagementConsultingService--work-contract-customContract{
    .form-block {

      .el-form-item__label,
      .el-radio__label {
        font-size: 12px;
      }
    }

    .el-form-item__label {
      text-align: left;
      line-height: 30px;
      width: 100%!important;

      .tentative{
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
<style lang="scss" scoped>



</style>
