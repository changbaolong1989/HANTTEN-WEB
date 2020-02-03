<template>
  <!-- 造价咨询合同 -->
  <el-form class="formCostConsulting--work-contract-customContract form" ref="form" :model="form" :rules="rules" :disabled="disabled">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="省份">
            <el-input v-model="form.province" placeholder="请输入省份"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="地区">
            <el-input v-model="form.region" placeholder="请输入地区"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="工程规模">
            <el-input v-model="form.generalize" placeholder="请输入工程规模 "></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="资金来源">
            <el-input v-model="form.fund" placeholder="请输入资金来源 "></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="建设工期或周期">
            <el-input v-model="form.period" placeholder="请输入建设工期或周期"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="双方约定的服务范围及工作内容">
            <el-input v-model="form.scope" placeholder="请输入双方约定的服务范围及工作内容"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="服务期限">
            <el-date-picker v-model="form.deadline" type="daterange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总合同份数">
            <el-input v-model="form.copies" placeholder="请输入总合同份数"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="委托人合同份数">
            <el-input v-model="form.entrust" placeholder="请输入委托人合同份数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="咨询人合同份数">
            <el-input v-model="form.consult" placeholder="请输入咨询人合同份数"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="委托人代表">
            <!--<el-date-picker v-model="form.representative" type="date" placeholder="选择委托人代表"></el-date-picker>-->
            <el-input v-model="form.representative" placeholder="请输入选择委托人代表"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="团队人员">
            <el-input v-model="form.personnel" placeholder="请输入团队人员"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="支付方式">
            <el-radio-group v-model="form.pay">
              <el-radio :label="r.id" name="radio" :value="r.id" v-for="(r, i) in paylist" :key="i">{{r.name}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter" v-if="form.pay==0">
        <el-col :span="12">
          <el-form-item label="咨询成果报告出具几日内支付">
            <el-input v-model="form.input" placeholder="请输入">
              <template slot="append">
                天
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter" v-if="form.pay==1">
        <el-col :span="12">
          <el-form-item label="分期信息">
            <el-input type="textarea" v-model="form.desc" placeholder="请输入分期信息"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="争议解决">
            <el-input v-model="form.dispute" placeholder="请输入争议解决"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="送达接收人">
            <el-input v-model="form.recipient" placeholder="请输入送达接收人"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="送达地点">
            <el-input v-model="form.site" placeholder="请输入送达地点"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="酬金及计取方式为">
            <el-input v-model="form.reward" placeholder="请输入酬金及计取方式"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <Aform ref="serviceAndWorkContent" :theFormData="form.serviceAndWorkContent" :disabled="disabled"></Aform>
    <Bform ref="achievementFile" :theFormData="form.achievementFile" :disabled="disabled"></Bform>
    <Cform ref="provideData" :theFormData="form.provideData" :disabled="disabled"></Cform>
  </el-form>
</template>

<script>
  import Aform from './Aform'
  import Bform from './Bform'
  import Cform from './Cform'
  export default {
    props: ['theFormData',"disabled"],
    data() {
      return {
        gutter: 40, // 表单相间距
        paylist: [{
            name: "一次性支付",
            id: 0
          },
          {
            name: "分期支付",
            id: 1
          }
        ],
        form: this.theFormData.form,
        rules: {},
        form:{
          pay:0
        }
      };
    },
    watch: {
      'theFormData.form'() {
        this.form = this.theFormData.form;
      }
    },
    methods: {},
    components: {
      Aform: Aform,
      Bform: Bform,
      Cform: Cform
    },
  };
</script>
<style lang="scss">
  .formCostConsulting--work-contract-customContract {
    .form-block {

      .el-form-item__label,
      .el-radio__label {
        font-size: 12px;
      }
    }

    .el-form-item__label {
      text-align: left;
      line-height: 30px;
      width: auto !important;
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
