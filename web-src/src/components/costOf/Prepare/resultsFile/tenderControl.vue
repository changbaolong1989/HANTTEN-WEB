<template>
  <!-- 招标控制价模版 -->
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
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="规章颁布日期" prop="input10">
              <template slot="label">
                规章颁布日期
                <i class="el-icon-info" title="什么法规"></i>
              </template>
              <el-date-picker v-model="form.input10" type="month" placeholder="请选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            有关材料、设备、参数和费用的说明
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="人工费按造价 信息执行方式" prop="input11">
              <template slot="label">
                人工费按造价 信息执行方式
                <i class="el-icon-info" title="低限"></i>
              </template>
              <el-input v-model="form.input11" placeholder="请输入人工费按造价 信息执行方式"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="建筑工程人工费" prop="input12">
              <el-input placeholder="请输入建筑工程人工费" v-model="form.input12">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="装饰工程人工费" prop="input13">
              <el-input placeholder="请输入装饰工程人工费" v-model="form.input13">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装工程人工费" prop="input14">
              <el-input placeholder="请输入安装工程人工费" v-model="form.input14">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="费率均执行指导费率编制" prop="input15">
              <template slot="label">
                费率均执行指导费率编制
                <i class="el-icon-info" title="执行指导费率编制"></i>
              </template>
              <el-input v-model="form.input15" placeholder="请输入费率均执行指导费率编制"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="暂列金额（含税）" prop="input16">
              <el-input v-model="form.input16" placeholder="请输入暂列金额（含税）">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="暂估价专业分包金额（含税）XX元" prop="input17">
              <template slot="label">
                暂估价专业分包金额（含税）XX元
                <i class="el-icon-info" title="暂估价专业分包金额（含税）XX元。其中：XX分包工程暂估金额含税XX元"></i>
              </template>
              <el-input v-model="form.input17" type="textarea" placeholder="请输入暂估价专业分包金额（含税）XX元"></el-input>
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
            <el-form-item label="安全文明施工措施清单项目" prop="input18">
              <template slot="label">
                安全文明施工措施清单项目
                <i class="el-icon-info" title="达到什么标准"></i>
              </template>
              <el-input v-model="form.input18" type="textarea" placeholder="请输入安全文明施工措施清单项目"></el-input>
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
        template : '5',
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
          input10: [{
            required: true,
            message: '请选择规章颁布日期',
            trigger: 'blur'
          }],
          input12: [{
            required: true,
            message: '请输入建筑工程人工费',
            trigger: 'blur'
          }],
          input13: [{
            required: true,
            message: '请输入装饰工程人工费',
            trigger: 'blur'
          }],
          input14: [{
            required: true,
            message: '请输入安装工程人工费',
            trigger: 'blur'
          }],
          input16: [{
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
      addAform() {
        this.form.tender.push({
          tenderer: "",
          biddingPrice: "",
          subcontract: ""
        })
      },
      colseForm(index) {
        this.form.tender.splice(index, 1)
      },
      getDetailInfo() {
        if (!this.tool.isNull(this.currentTransmittedData.formworkContent) && this.template == this.currentTransmittedData.formworkType) {
          this.form = JSON.parse(this.currentTransmittedData.formworkContent);
          this.form.input10 = this.tool.formatToDate(this.form.input10);
        }
      }
    }
  }
</script>

<style lang="scss">
  @import url('../../common/css/form.css');

  .prepareTenderClearanceReport {
    .addform {
      position: relative;
      background: #F1F5F8;
      border-radius: 10px;
      padding: 20px 15px;
      margin: 10px 0;
    }

    .colseform {
      position: absolute;
      top: 0;
      right: 10px;
      z-index: 10;
    }
  }
</style>
<style lang="scss" scoped>
  @import url('../../common/css/form.css');
</style>
