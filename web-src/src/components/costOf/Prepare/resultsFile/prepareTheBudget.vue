<template>
  <!-- 编制概算 -->
  <el-form class="form" ref="form" :model="form" :disabled="disabled">
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
        <el-col :span="24">
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
        <el-col :span="24">
          <el-form-item label="编制范围" prop="input8">
            <el-input type="textarea" :rows="3" v-model="form.input8" placeholder="请输入编制范围"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          编制依据
        </el-col>
      </el-row>
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
          <el-form-item label="法规颁布年份" prop="input10">
            <template slot="label">
              法规颁布年份
              <i class="el-icon-info" title="《北京市建设工程计价依据——概算定额》及相应费用文件、北京市建设工程造价管理部门颁布的相关文件及规定"></i>
            </template>
            <el-date-picker v-model="form.input10" type="year" placeholder="请选择法规颁布年份"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="法规颁布年份" prop="input11">
            <template slot="label">
              法规颁布年份<i class="el-icon-info" title="北京市《北京工程造价信息》及市场询价"></i>
            </template>
            <el-date-picker v-model="form.input11" type="month" placeholder="请选择法规颁布年份"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="计量说明" prop="input12">
            <el-input type="textarea" :rows="3" v-model="form.input12" placeholder="请输入计量说明"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          计价说明
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="法规颁布年份" prop="input13">
            <template slot="label">
              法规颁布年份<i class="el-icon-info" title="《北京工程造价信息》及市场询价编制"></i>
            </template>
            <el-date-picker v-model="form.input13" type="month" placeholder="请输入法规颁布年份"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="建筑工程人工费/工日" prop="input14">
            <el-input type="text" v-model="form.input14" placeholder="请输入建筑工程人工费/工日">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="装饰工程人工费/工日" prop="input15">
            <el-input type="text" v-model="form.input15" placeholder="请输入装饰工程人工费/工日">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="安装工程人工费/工日" prop="input16">
            <el-input type="text" v-model="form.input16" placeholder="请输入安装工程人工费/工日">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="费率均执行指导费率编制" prop="input17">
            <template slot="label">
              费率均执行指导费率编制<i class="el-icon-info" title="执行指导费率编制"></i>
            </template>
            <el-input type="text" v-model="form.input17" placeholder="请输入费率均执行指导费率编制">
            </el-input>
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="弃土/渣土消纳运距按15km计价" prop="input18">
             <template slot="label">
              弃土/渣土消纳运距按15km计价<i class="el-icon-info" title="弃土/渣土消纳运距按15km计价"></i>
            </template>
            <el-input type="text" v-model="form.input18" placeholder="请输入弃土/渣土消纳运距按15km计价">
            </el-input>
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
        template : '0',
        gutter: 40,
        form: { // 表单
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
          /* 法规颁布年份A */
          input10: '',
          /* 法规颁布年份B */
          input11: '',
          /* 计量说明 */
          input12: '',
          /* 法规颁布年份C */
          input13: '',
          /* 建筑工程人工费 */
          input14: '',
           /* 装饰工程人工费 */
          input15: '',
           /* 安装工程人工费 */
          input16: '',
           /* 费率均执行指导费率编制 */
          input17: '',
          /* 弃土/渣土消纳运距按15km计价 */
          input18:'',
          input19:''
        },
        rule: {
          input3: [{
            required: true,
            message: '请选择报告日期',
            trigger: 'change'
          }],
          input10: [{
            required: true,
            message: '请选择法规颁布年份',
            trigger: 'change'
          }],
          input11: [{
            required: true,
            message: '请选择法规颁布年份',
            trigger: 'change'
          }],
          input13: [{
            required: true,
            message: '请选择法规颁布年份',
            trigger: 'change'
          }],
          input14: [{
            required: true,
            message: '请输入建筑工程人工费',
            trigger: 'blur'
          }],
           input15: [{
            required: true,
            message: '请输入装饰工程人工费',
            trigger: 'blur'
          }],
            input16: [{
            required: true,
            message: '请输入安装工程人工费',
            trigger: 'blur'
          }]
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
          this.form.input11 = this.tool.formatToDate(this.form.input11);
          this.form.input13 = this.tool.formatToDate(this.form.input13);
        }
      }
    }
  }
</script>
<style lang="scss">
  @import url('../../common/css/form.css');
</style>
<style lang="scss" scoped>
</style>
