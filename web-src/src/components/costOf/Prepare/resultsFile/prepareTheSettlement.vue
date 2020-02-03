<template>
  <!-- 编制结算 -->
  <div class="PrepareTheSettlement">
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
            <el-form-item label="成果文件编号" prop="input19"><el-input v-model="form.input19" placeholder="请输入成果文件编号"></el-input></el-form-item>
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
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="合同开竣工日期" prop="input8">
              <el-date-picker v-model="form.input8" type="daterange" range-separator="至" start-placeholder="开始日期"
                end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际开竣工日期" prop="input9">
              <el-date-picker v-model="form.input9" type="daterange" range-separator="至" start-placeholder="开始日期"
                end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="合同金额" prop="input10">
              <el-input placeholder="请输入合同金额" v-model="form.input10">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结算方式" prop="input11">
              <el-select v-model="form.input11" placeholder="请选择结算方式">
                <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="发包人" prop="input12">
              <el-input placeholder="请输入发包人" v-model="form.input12"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="承包人" prop="input13">
              <el-input placeholder="请输入承包人" v-model="form.input13"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="设计单位" prop="input14">
              <el-input placeholder="请输入设计单位" v-model="form.input14"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="监理单位" prop="input15">
              <el-input placeholder="请输入监理单位" v-model="form.input15"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="编制范围" prop="input16">
              <el-input v-model="form.input16" type="textarea" placeholder="请输入编制范围"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
           <el-col :span="24">
            <el-form-item label="编制依据" prop="input17">
              <el-input v-model="form.input17" type="textarea" placeholder="请输入编制依据"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="有关计量计价说明" prop="input18">
              <el-input v-model="form.input18" type="textarea" placeholder="请输入有关计量计价说明"></el-input>
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
        template : '2',
        gutter: 40,
        options: [{
            name: "单价结算",
            id: "1"
          },
          {
            name: "固定总价结算",
            id: "2"
          },
          {
            name: "据实结算",
            id: "3"
          }
        ],
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
          input14: "",
          input15: "",
          input16: "",
          input17: "",
          input18: "",
          input19: ""
        },
        rules: {
          input3: [{
            required: true,
            message: '请选择报告日期',
            trigger: 'blur'
          }],
          input8: [{
            required: true,
            message: '请选择合同开竣工日期',
            trigger: 'blur'
          }],
          input9: [{
            required: true,
            message: '请选择实际开竣工日期',
            trigger: 'blur'
          }],
          input10: [{
            required: true,
            message: '请输入合同金额',
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
          this.form.input8 = this.tool.formatTosplit(this.form.input8);
          this.form.input9 = this.tool.formatTosplit(this.form.input9);
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
