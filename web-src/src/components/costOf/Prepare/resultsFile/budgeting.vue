<template>
  <!-- 预算编制模板 -->
  <el-form class="form" ref="form" :model="form" :rules="rules" :disabled="disabled">
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
          <el-form-item label="成果文件编号" prop="input21"><el-input v-model="form.input21" placeholder="请输入成果文件编号"></el-input></el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="报告编号" prop="input2"><el-input v-model="form.input2" placeholder="请输入报告编号"></el-input></el-form-item>
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
      <el-row :gutter="gutter"><el-col :span="24">工程概况</el-col></el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="工程名称" prop="input5"><el-input v-model="form.input5" placeholder="请输入工程名称"></el-input></el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工程地点" prop="input6"><el-input v-model="form.input6" placeholder="请输入工程地点"></el-input></el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="建筑规模/范围" prop="input7"><el-input v-model="form.input7" placeholder="请输入建筑规模/范围"></el-input></el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="编制范围" prop="input8"><el-input type="textarea" :rows="3" v-model="form.input8" placeholder="请输入编制范围"></el-input></el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter"><el-col :span="24">编制依据</el-col></el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="图纸、文件" prop="input9">
            <template slot="label">
              图纸、文件
              <i class="el-icon-info" title="图纸、设计文件，标出版本时间信息"></i>
            </template>
            <el-input type="textarea" :rows="3" v-model="form.input9" placeholder="请输入图纸、文件"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="规章颁布日期" prop="input10">
            <template slot="label">
              规章颁布日期
              <i
                class="el-icon-info"
                title="《北京工程造价信息》及市场询价"
              ></i>
            </template>
            <el-date-picker v-model="form.input10" type="month" placeholder="请选择规章颁布日期"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="清单项目计量说明" prop="input11">
            <el-input type="textarea" :rows="3" v-model="form.input11" placeholder="请输入清单项目计量说明"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter"><el-col :span="12">清单项目计价说明</el-col></el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="规章颁布日期" prop="input115">
            <template slot="label">
              规章颁布日期
              <i class="el-icon-info" title="人材机价格依据北京市2018年10月《北京工程造价信息》及市场询价编制"></i>
            </template>
            <el-date-picker v-model="form.input115" type="month" placeholder="请选择规章颁布日期"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="人工费按造价信息执行方式" prop="input12">
            <template slot="label">
              人工费按造价信息执行方式
              <i class="el-icon-info" title="低限"></i>
            </template>
            <el-input type="text" v-model="form.input12" placeholder="请输入人工费按造价信息执行方式"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="建筑工程人工费/工日" prop="input13">
            <el-input type="text" v-model="form.input13" placeholder="请输入建筑工程人工费/工日">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="装饰工程人工费/工日" prop="input14">
            <el-input type="text" v-model="form.input14" placeholder="请输入装饰工程人工费/工日">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="安装工程人工费/工日" prop="input15">
            <el-input type="text" v-model="form.input15" placeholder="请输入安装工程人工费/工日">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="费率均执行指导费率编制" prop="input16">
            <template slot="label">
              费率均执行指导费率编制
              <i class="el-icon-info" title="执行指导费率编制"></i>
            </template>
            <el-input type="text" v-model="form.input16" placeholder="请输入费率均执行指导费率编制"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="弃土/渣土消纳运距按15km计价" prop="input17">
            <template slot="label">
              弃土/渣土消纳运距按15km计价
              <i class="el-icon-info" title="弃土/渣土消纳运距按15km计价"></i>
            </template>
            <el-input type="text" v-model="form.input17" placeholder="请输入弃土/渣土消纳运距按15km计价"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="暂列金额（含税）" prop="input18">
            <el-input v-model="form.input18" placeholder="请输入暂列金额（含税）">
              <template slot="append">
                元
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="暂估价专业分包金额（含税）XX元" prop="input19">
            <template slot="label">
              暂估价专业分包金额（含税）XX元
              <i class="el-icon-info" title="暂估价专业分包金额（含税）XX元。其中：XX分包工程暂估金额含税XX元"></i>
            </template>
            <el-input v-model="form.input19" type="textarea" placeholder="请输入暂估价专业分包金额（含税）XX元"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter"><el-col :span="12">其他有关问题的说明</el-col></el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="安全文明施工措施清单项目" prop="input20">
            <template slot="label">
              安全文明施工措施清单项目
              <i class="el-icon-info" title="达到什么标准"></i>
            </template>
            <el-input v-model="form.input20" type="textarea" placeholder="请输入安全文明施工措施清单项目"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
  </el-form>
</template>

<script>
export default {
  props: ['transmittedData', 'disabled'],
  data() {
    return {
      currentTransmittedData: this.transmittedData,
      contractNum: this.transmittedData.contractNum,
      projectName: this.transmittedData.projectName,
      template : '8',
      gutter: 40,
      form: {
        // 表单
        /* 项目名称 */
        input1: '',
        /* 报告编号 */
        input2: '',
        /* 报告日期 */
        input3: '',
        /* 目录 */
        input4: '',
        /* 工程名称 */
        input5: '',
        /* 工程地点 */
        input6: '',
        /* 建筑规模/范围 */
        input7: '',
        /* 编制范围 */
        input8: '',
        /* 图纸、文件 */
        input9: '',
        /* 请输入规章颁布日期 */
        input10: '',
        input115: '',
        /* 请输入规章颁布日期 */
        /* 清单项目计量说明*/
        input11: '',
        /* 法规颁布年份C */
        input12: '',
        /* 建筑工程人工费 */
        input13: '',
        /* 装饰工程人工费 */
        input14: '',
        /* 安装工程人工费 */
        input15: '',
        /* 费率均执行指导费率编制 */
        input16: '',
        /* 弃土/渣土消纳运距按15km计价 */
        input17: '',
        input18: '',
        input19: '',
        input20: '',
        input21:""
      },
      rules: {
        input3: [
          {
            required: true,
            message: '请输入报告日期',
            trigger: 'blur'
          }
        ],
        input10: [
          {
            required: true,
            message: '请输入规章颁布日期',
            trigger: 'blur'
          }
        ],
        input115: [
          {
            required: true,
            message: '请输入规章颁布日期',
            trigger: 'blur'
          }
        ],
        input13: [
          {
            required: true,
            message: '请输入建筑工程人工费',
            trigger: 'blur'
          }
        ],
        input14: [
          {
            required: true,
            message: '请输入装饰工程人工费',
            trigger: 'blur'
          }
        ],
        input15: [
          {
            required: true,
            message: '请输入安装工程人工费',
            trigger: 'blur'
          }
        ],
        input18: [
          {
            required: true,
            message: '请输入暂列金额（含税）',
            trigger: 'blur'
          }
        ],
        input21: [
          {
            required: true,
            message: '请输入成果文件编号',
            trigger: 'blur'
          }
        ]
      }
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
        this.form.input10 = this.tool.formatToDate(this.form.input10);
        this.form.input115 = this.tool.formatToDate(this.form.input115);
      }
    }
  }
};
</script>
<style lang="scss">
@import url('../../common/css/form.css');
</style>
<style lang="scss" scoped></style>
