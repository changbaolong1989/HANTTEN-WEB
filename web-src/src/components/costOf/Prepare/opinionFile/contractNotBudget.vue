<template>
  <!-- 预算审核意见-->
  <el-form class="form" ref="form" :model="form" :rules="rule" :disabled="disabled">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="档案编号" prop="input1">
            <template slot="label">
              档案编号
              <i class="el-icon-info" title="HT1134BJ-ZJ-01第一个是合同编号，中间是部门编号 造价部ZJ招标代理部ZB 市场部SC ，最后是流水号"></i>
            </template>
            <el-input v-model="form.input1" placeholder="请输入档案编号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="公司名称" prop="input105">
            <el-input v-model="form.input105" placeholder="请输入公司名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目名称" prop="input2">
            <el-input v-model="form.input2" placeholder="请输入项目名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          审核意见
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="审核信息" prop="input3">
            <template slot="label">
              审核信息
              <i class="el-icon-info" title="我们接受贵公司委托，对×××工程进行全过程跟踪造价咨询工作。现对本工程施工合同进行审核。标红处为输入框"></i>
            </template>
            <el-input v-model="form.input3" placeholder="请输入审核信息"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          审核范围
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="建设单位" prop="input4">
            <el-input v-model="form.input4" placeholder="请输入建设单位"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="施工单位" prop="input5">
            <el-input v-model="form.input5" placeholder="请输入施工单位"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="报送日期" prop="input6">
            <el-date-picker v-model="form.input6" type="date" placeholder="请选择报送日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="送报文件名称" prop="input7">
            <el-input v-model="form.input7" placeholder="请输入送报文件名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="审核依据" prop="input8">
            <template slot="label">
              审核依据
              <i class="el-icon-info" title="XX项目施工图纸（备注版本号，图纸日期）；2018年XX月XX日图纸疑问回复"></i>
            </template>
            <el-input v-model="form.input8" placeholder="请输入审核依据"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="《北京工程造价信息》发布时间" prop="input9">
            <template slot="label">
              《北京工程造价信息》发布时间
              <i class="el-icon-info" title="北京市2019年3月《北京工程造价信息》及市场询价。yyyy年MM月"></i>
            </template>
            <el-date-picker v-model="form.input9" type="month" placeholder="请选择发布时间"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          审核意见
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="送审金额" prop="input10">
            <template slot="label">
              送审金额
              <i class="el-icon-info" title="金额千分符，小数点后两位"></i>
            </template>
            <el-input v-model="form.input10" placeholder="请输入送审金额">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审定金额" prop="input11">
            <template slot="label">
              审定金额
              <i class="el-icon-info" title="金额千分符，小数点后两位"></i>
            </template>
            <el-input v-model="form.input11" placeholder="请输入审定金额">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="核减金额" prop="input12">
            <template slot="label">
              核减金额
              <i class="el-icon-info" title="金额千分符，小数点后两位"></i>
            </template>
            <el-input v-model="form.input12" placeholder="请输入核减金额">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="意见如下" prop="input13">
            <el-input v-model="form.input13" type="textarea" placeholder="请输入意见"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          附件&其他
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          是否有附件
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-radio-group v-model="form.input14">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-col>
      </el-row>
      <el-row :gutter="gutter" v-if="form.input14==1">
        <el-col :span="12">
          <el-form-item label="附件名称" prop="input15">
            <el-input v-model="form.input15" placeholder="请输入附件名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          是否抄送
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-radio-group v-model="form.input16">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-col>
      </el-row>
      <el-row :gutter="gutter" v-if="form.input16==1">
        <el-col :span="12">
          <el-form-item label="抄送公司" prop="input17">
            <el-input v-model="form.input17" placeholder="请输入抄送公司"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="抄送人" prop="input18">
            <el-input v-model="form.input18" placeholder="请输入抄送人"></el-input>
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
        template : '17',
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
          input18: ""
        },
        rule: {
          input6: [{
            required: true,
            message: '请选择报送日期',
            trigger: 'blur'
          }],
          input9: [{
            required: true,
            message: '请输入发布时间',
            trigger: 'blur'
          }],
          input10: [{
            required: true,
            message: '请输入送审金额',
            trigger: 'blur'
          }],
          input11: [{
            required: true,
            message: '请输入审定金额',
            trigger: 'blur'
          }],
          input12: [{
            required: true,
            message: '请输入核减金额',
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
          this.form.input6 = this.tool.formatToDate(this.form.input6);
          this.form.input9 = this.tool.formatToDate(this.form.input9);
        }
      }
    }
  }
</script>

<style>
</style>
