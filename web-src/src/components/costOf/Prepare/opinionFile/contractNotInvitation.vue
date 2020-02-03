<template>
  <!-- 合同审核意见（招标）-->
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
          <el-form-item label="合同" prop="input6">
            <el-input v-model="form.input6" placeholder="请输入合同项目名称"></el-input>
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
          <el-form-item label="合同金额" prop="input7">
            <template slot="label">
              合同金额
              <i class="el-icon-info" title="金额单位为元，金额千分符，小数点后两位"></i>
            </template>
            <el-input v-model="form.input7" placeholder="请输入合同金额">
              <template slot="append">元</template>
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
        template : '14',
        gutter: 40,
        form: {
          input1: "",
          input2: "",
          input3: "",
          input4: "",
          input5: "",
          input6: "",
          input7: "",
          input105: ""
        },
        rule: {
          input7: [{
            required: true,
            message: '请输入合同金额',
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
        }
      }
    }
  }
</script>

<style>
</style>
