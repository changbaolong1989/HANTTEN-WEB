<template>
  <!-- 编制估算 -->
  <div class="PrepareTheEstimate">
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
            <el-form-item label="成果文件编号" prop="input14"><el-input v-model="form.input14" placeholder="请输入成果文件编号"></el-input></el-form-item>
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
          <el-col :span="12">
            工程概况
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="工程名称" prop="input5">
              <el-input v-model="form.input5" placeholder="请输入工程名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工程地点" prop="input6">
              <el-input v-model="form.input6" placeholder="请输入工程地点"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="建筑规模/范围" prop="input7">
              <el-input v-model="form.input7" placeholder="请输入建筑规模/范围"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="建筑功能及分类" prop="input8">
              <el-input v-model="form.input8" placeholder="请输入建筑功能及分类"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="编制范围" prop="input9">
              <el-input v-model="form.input9" type="textarea" placeholder="请输入编制范围"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="编制依据" prop="input10">
              <template slot="label">
                编制依据
                <i class="el-icon-info" title="提示：与建设项目相关的工程地质资料、设计文件、图纸或有关设计专业提供的主要工程量和主要设备清单等"></i>
              </template>
              <el-input v-model="form.input10" type="textarea" placeholder="请输入编制依据"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="估算说明" prop="input11">
               <template slot="label">
                估算说明
                <i class="el-icon-info" title="说明：如果工程费用采用单方造价指标，现有的资料又不够充分显示项目信息，例如地基处理方式，结构类型，装修标准，机电系统配置，可在此进行系统拟定，拟定时应描述全部单体及室外工程的全部工程内容。"></i>
              </template>
              <el-input v-model="form.input11" type="textarea" placeholder="请输入估算说明"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="估算外费用" prop="input12">
              <el-input v-model="form.input12" type="textarea" placeholder="请输入估算外费用"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="其他说明" prop="input13">
              <el-input v-model="form.input13" placeholder="请输入其他说明"></el-input>
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
        template : '1',
        gutter: 40,
        form: {
          input1: "",
          input2: "",
          input3: "",
          input4: "",
          input5: "",
          input6: "",
          input7: "",
          input8: "",
          input9: "",
          input10: "",
          input11: "",
          input12: "",
          input13: "",
          input14: ""
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
