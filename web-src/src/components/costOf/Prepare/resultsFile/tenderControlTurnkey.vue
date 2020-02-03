<template>
  <!-- 招标控制价模版(总包) -->
  <div class="prepareTenderClearanceReport">
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
            <el-form-item label="成果文件编号" prop="input15"><el-input v-model="form.input15" placeholder="请输入成果文件编号"></el-input></el-form-item>
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
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="编制范围" prop="input8">
              <el-input v-model="form.input8" type="textarea" placeholder="请输入编制范围"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            编制依据
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="图纸&资料" prop="input9">
              <el-input v-model="form.input9" type="textarea" placeholder="请输入图纸、设计文件，标出版本时间信息"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="清单项目及工程量计量说明" prop="input10 ">
              <el-input v-model="form.input10 " type="textarea" placeholder="请输入清单项目及工程量计量说明"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            清单计价说明
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="暂列金额（含税）" prop="input11">
              <el-input v-model="form.input11" placeholder="请输入暂列金额（含税）">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="暂估价专业分包金额（含税）XX元" prop="input12">
              <template slot="label">
                暂估价专业分包金额（含税）XX元
                <i class="el-icon-info" title="暂估价专业分包金额（含税）XX元。其中：XX分包工程暂估金额含税XX元"></i>
              </template>
              <el-input v-model="form.input12" type="textarea" placeholder="请输入暂估价专业分包金额（含税）XX元"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="其他内容" prop="input13">
              <template slot="label">
                其他内容
                <i class="el-icon-info" title="附加计价说明，编号从6开始"></i>
              </template>
              <el-input v-model="form.input13" type="textarea" placeholder="请输入其他内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            其他有关问题的说明
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="安全文明施工措施清单项目" prop="input14">
              <template slot="label">
                安全文明施工措施清单项目
                <i class="el-icon-info" title="达到什么标准"></i>
              </template>
              <el-input v-model="form.input14" type="textarea" placeholder="请输入安全文明施工措施清单项目"></el-input>
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
        template : '7',
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
          input10 :"",
          input11: "",
          input12: "",
          input13:"",
          input14: "",
          input15: ""
        },
        rules: {
          input3: [{
            required: true,
            message: '请选择报告日期',
            trigger: 'blur'
          }],
          input11: [{
            required: true,
            message: '请输入暂列金额（含税）',
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
