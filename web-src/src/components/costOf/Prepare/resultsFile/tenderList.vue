<template>
  <!-- 招标清单 -->
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
              <i class="el-icon-info" title="《北京工程造价信息》及市场询价"></i>
            </template>
            <el-date-picker v-model="form.input10" type="year" placeholder="请选择法规颁布年份"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="清单项目及工程量计量说明" prop="input11">
            <el-input type="textarea" v-model="form.input11" placeholder="请输入清单项目及工程量计量说明">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="24">
          清单计价说明
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="暂列金额（含税）" prop="input12">
            <el-input type="text" v-model="form.input12" placeholder="请输入暂列金额（含税）">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="暂估价专业分包金额（含税）" prop="input13">
            <template slot="label">
              暂估价专业分包金额（含税）
              <i class="el-icon-info" title="暂估价专业分包金额（含税）XX元。其中：XX分包工程暂估金额含税XX元"></i>
            </template>
            <el-input type="textarea" v-model="form.input13" placeholder="请输入暂估价专业分包金额（含税）">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="其他有关问题的说明" prop="input14">
             <template slot="label">
                          其他有关问题的说明
                          <i class="el-icon-info" title="达到什么标准"></i>
                        </template>
            <el-input type="textarea" v-model="form.input14" placeholder="请输入其他有关问题的说明">
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
        template : '6',
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
          /* 清单项目及工程量计量说明 */
          input11: '',
          /* 暂列金额（含税） */
          input12: '',
          /* 暂估价专业分包金额（含税） */
          input13: '',
          /* 其他有关问题的说明 */
          input14: '',
          input15: ''
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
          input12: [{
            required: true,
            message: '请输入暂列金额（含税）',
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
          this.form.input10 = this.tool.formatToDate(this.form.input10);
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
