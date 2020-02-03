<template>
  <!-- 预算审核 -->
  <el-form class="form" ref="form" :model="form" :rules="rule" :disabled="disabled">
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
          <el-form-item label="成果文件编号" prop="input33"><el-input v-model="form.input33" placeholder="请输入成果文件编号"></el-input></el-form-item>
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
          工程审核报告
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="委托人" prop="input5">
            <el-input v-model="form.input5" placeholder="请输入合同基本信息委托人"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="委托人称呼" prop="input6">
            <template slot="label">
              委托人称呼
              <i class="el-icon-info" title="贵公司、贵单位"></i>
            </template>
            <el-input v-model="form.input6" placeholder="请输入委托人称呼"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="项目名称" prop="input7">
            <el-input v-model="form.input7" placeholder="请输入合同基本信息合同名称报送单位"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="报送单位" prop="input8">
            <el-input v-model="form.input8" placeholder="请输入合同基本信息委托人"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="编制单位" prop="input9">
            <el-input v-model="form.input9" placeholder="请输入编制单位"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="送审价格" prop="input10">
            <el-input type="text" v-model="form.input10" placeholder="请输入送审价格">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="审定价格" prop="input11">
            <el-input type="text" v-model="form.input11" placeholder="请输入审定价格">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审减价格" prop="input12">
            <el-input type="text" v-model="form.input12" placeholder="请输入审减价格">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          工程概算审核说明
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          一、工程概况
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="工程地点" prop="input13">
            <el-input v-model="form.input13" placeholder="请输入工程地点"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="建筑规模/范围" prop="input14">
            <el-input type="text" v-model="form.input14" placeholder="请输入建筑规模/范围"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="建筑功能及分类" prop="input15">
            <template slot="label">
              建筑功能及分类
              <i class="el-icon-info" title="公用事业及城市基础设施/商业、酒店及休闲娱乐建筑/电力、工业建筑/文化、教育及体育设施/铁路、道路及桥梁建设/水务/水运交通/住宅物业"></i>
            </template>
            <el-input v-model="form.input15" type="textarea" placeholder="请输入建筑功能及分类"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          二、审核范围
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="送报时间" prop="input16">
            <el-date-picker v-model="form.input16" type="date" placeholder="请选择送报时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="三、审核方法" prop="input17">
            <template slot="label">
              三、审核方法
              <i class="el-icon-info" title="采用全面审核法对本工程进行概算审核"></i>
            </template>
            <el-input v-model="form.input17" type="textarea" placeholder="请输入审核方法"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          四、审核依据
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="图纸" prop="input18">
            <template slot="label">
              图纸
              <i class="el-icon-info" title="2018年XX月XX日XX项目施工图纸；2018年XX月XX日图纸疑问回复"></i>
            </template>
            <el-input v-model="form.input18" placeholder="请输入图纸"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          五、审核情况说明
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          1.1、工程费用
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="送审" prop="input19">
            <el-input v-model="form.input19" placeholder="请输入送审">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审定" prop="input20">
            <el-input v-model="form.input20" placeholder="请输入审定">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="审减" prop="input21">
            <el-input v-model="form.input21" placeholder="请输入审减">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="工程费用金额来源" prop="input22">
            <el-input v-model="form.input22" type="textarea" placeholder="其中内容为解释上面3个金额来源等">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          1.2、其他费用
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="送审" prop="input23">
            <el-input v-model="form.input23" placeholder="请输入送审">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审定" prop="input24">
            <el-input v-model="form.input24" placeholder="请输入审定">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="审减" prop="input25">
            <el-input v-model="form.input25" placeholder="请输入审减">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="其他费用金额来源" prop="input26">
            <el-input v-model="form.input26" type="textarea" placeholder="其中内容为解释上面3个金额来源等">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          1.3、工程预备费
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="送审" prop="input27">
            <el-input v-model="form.input27" placeholder="请输入送审">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审定" prop="input28">
            <el-input v-model="form.input28" placeholder="请输入审定">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="审减" prop="input29">
            <el-input v-model="form.input29" placeholder="请输入审减">
              <template slot="append">万元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="其他费用金额来源" prop="input30">
            <el-input v-model="form.input30" type="textarea" placeholder="其中内容为解释上面3个金额来源等">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="2、重要事项说明" prop="input31">
            <el-input v-model="form.input31" type="textarea" placeholder="重要事项说明"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="3、存在的问题及建议" prop="input32">
            <el-input v-model="form.input32" type="textarea" placeholder="存在的问题及建议"></el-input>
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
        template : '12',
        gutter: 40,
        form: { // 表单
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
          input19: "",
          input20: "",
          input21: "",
          input22: "",
          input23: "",
          input24: "",
          input25: "",
          input26: "",
          input27: "",
          input28: "",
          input29: "",
          input30: "",
          input31: "",
          input32: "",
          input33: ""
        },
        rule: {
          input3: [{
            required: true,
            message: '请选择报告日期',
            trigger: 'blur'
          }],
          input10: [{
            required: true,
            message: '请输入送审价格',
            trigger: 'blur'
          }],
          input11: [{
            required: true,
            message: '请输入审定价格',
            trigger: 'blur'
          }],
          input12: [{
            required: true,
            message: '请输入审减价格',
            trigger: 'blur'
          }],
          input16: [{
            required: true,
            message: '请选择送报时间',
            trigger: 'blur'
          }],
          input19: [{
            required: true,
            message: '请输入送审',
            trigger: 'blur'
          }],
          input20: [{
            required: true,
            message: '请输入审定',
            trigger: 'blur'
          }],
          input21: [{
            required: true,
            message: '请输入审减',
            trigger: 'blur'
          }],
          input23: [{
            required: true,
            message: '请输入送审',
            trigger: 'blur'
          }],
          input24: [{
            required: true,
            message: '请输入审定',
            trigger: 'blur'
          }],
          input25: [{
            required: true,
            message: '请输入审减',
            trigger: 'blur'
          }],
          input27: [{
            required: true,
            message: '请输入送审',
            trigger: 'blur'
          }],
          input28: [{
            required: true,
            message: '请输入审定',
            trigger: 'blur'
          }],
          input29: [{
            required: true,
            message: '请输入审减',
            trigger: 'blur'
          }],
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
          this.form.input16 = this.tool.stringFormatToDate(this.form.input16);
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
