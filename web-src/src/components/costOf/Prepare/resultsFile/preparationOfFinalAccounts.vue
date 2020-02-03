<template>
  <!-- 编制决算 -->
  <div class="preparationOfFinalAccounts">
    <el-form class="form" :model="form" ref="form" :rules="rules" :disabled="disabled">
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目名称" prop="input1"><el-input v-model="form.input1" placeholder="请输入项目名称"></el-input></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同号" >{{contractNum}}</el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="成果文件编号" prop="input18"><el-input v-model="form.input18" placeholder="请输入成果文件编号"></el-input></el-form-item>
        </el-col>
          <el-col :span="12">
            <el-form-item label="报告编号" prop="input2">
              <el-input v-model="form.input2" placeholder="请输入报告编号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="报告日期" prop="input3">
              <el-date-picker v-model="form.input3" type="date" placeholder="请选择报告日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="目录" prop="input4">
              <el-input type="textarea" :rows="3" v-model="form.input4" placeholder="请输入目录"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="项目概况" prop="input5">
              <el-input v-model="form.input5" type="textarea" placeholder="请输入项目概况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="会计账务处理、财产物资清理及债权债务的清偿情况" prop="input6">
              <el-input v-model="form.input6" type="textarea" placeholder="请输入会计账务处理、财产物资清理及债权债务的清偿情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="项目支出预算、投资计划和资金到位情况" prop="input7">
              <el-input v-model="form.input7" type="textarea" placeholder="请输入项目支出预算、投资计划和资金到位情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="项目建设资金使用、结余情况" prop="input8">
              <el-input v-model="form.input8" type="textarea" placeholder="请输入项目建设资金使用、结余情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="概（预）算执行情况及分析" prop="input9">
              <el-input v-model="form.input9" type="textarea" placeholder="请输入概（预）算执行情况及分析"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="尾工工程情况" prop="input10">
              <el-input v-model="form.input10" type="textarea" placeholder="请输入尾工工程情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="历次审计、检查、稽查及整改情况" prop="input11">
              <el-input v-model="form.input11" type="textarea" placeholder="请输入历次审计、检查、稽查及整改情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="主要技术经济指标的分析、计算情况" prop="input12">
              <el-input v-model="form.input12" type="textarea" placeholder="请输入主要技术经济指标的分析、计算情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="项目管理经验、问题和建议" prop="input13">
              <el-input v-model="form.input13" type="textarea" placeholder="请输入项目管理经验、问题和建议"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="预备费动用情况" prop="input14">
              <el-input v-model="form.input14" type="textarea" placeholder="请输入预备费动用情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="招投标情况、工程政府采购情况、合同履行情况" prop="input15">
              <el-input v-model="form.input15" type="textarea" placeholder="请输入招投标情况、工程政府采购情况、合同履行情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="征地拆迁补偿情况、移民安置情况" prop="input16">
              <el-input v-model="form.input16" type="textarea" placeholder="请输入征地拆迁补偿情况、移民安置情况"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="需说明的其他事项" prop="input17">
              <el-input v-model="form.input17" type="textarea" placeholder="请输入需说明的其他事项"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

    </el-form>

  </div>
</template>

<script>
  export default {
    props: ['transmittedData', 'disabled'],
    data() {
      return {
        currentTransmittedData: this.transmittedData,
        contractNum: this.transmittedData.contractNum,
        projectName: this.transmittedData.projectName,
        template : '3',
        gutter: 40,
        form: {
          input1: "",
          input2: "",
          input3: "",
          input4: "",
          input5:"",
          input6:"",
          input7:"",
          input8:"",
          input9:"",
          input10:"",
          input11:"",
          input12:"",
          input13:"",
          input14:"",
          input15:"",
          input16:"",
          input17:"",
          input18:""
        },
        rules: {
          input3: [{
            required: true,
            message: '请选择报告日期',
            trigger: 'blur'
          }],
        },
      }
    },
    watch: {
      transmittedData: {
        handler() {
          let vm = this;
          vm.currentTransmittedData = Object.assign({}, this.transmittedData);
        },
        immediate: true
      }
    },
    mounted() {
      let vm = this;
      // 查询数据
      vm.getDetailInfo();
    },
    methods: {
      getDetailInfo() {
        if (!this.tool.isNull(this.currentTransmittedData.formworkContent) && this.template == this.currentTransmittedData.formworkType) {
          this.form = JSON.parse(this.currentTransmittedData.formworkContent);
        }
      }
    }
  }
</script>

<style lang="scss">
  @import url('../../common/css/form.css');
</style>
<style lang="scss" scoped>
  @import url('../../common/css/form.css');
</style>
