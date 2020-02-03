<template>
  <!-- 结算审核 -->
  <el-form class="form" ref="form" :model="form" :rules="rule" :disabled="disabled">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="项目名称" prop="input1"><el-input v-model="form.input1" placeholder="请输入项目名称"></el-input></el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="合同号">{{ contractNum }}</el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="成果文件编号" prop="input48"><el-input v-model="form.input48" placeholder="请输入成果文件编号"></el-input></el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="报告编号" prop="input2"><el-input v-model="form.input2" placeholder="请输入报告编号"></el-input></el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="报告日期" prop="input3"><el-date-picker v-model="form.input3" type="date" placeholder="请选择报告日期"></el-date-picker></el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工程名称" prop="input49">
            <el-input v-model="form.input49" placeholder="请输入工程名称,例如&quot;球类综合馆总承包工程&quot;"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="24">
          <el-form-item label="目录" prop="input4"><el-input type="textarea" :rows="3" v-model="form.input4" placeholder="请输入目录" autosize></el-input></el-form-item>
        </el-col>
      </el-row>
    </div>

    <div class="form-block">
      <el-row :gutter="gutter"><el-col :span="12">工程结算审核报告</el-col></el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="致:" prop="input5"><el-input v-model="form.input5"></el-input></el-form-item>
        </el-col>
      </el-row>
      <form-text-area
                :obj="{ model: form.input6, field: 'input6' }"
                @childByValue="childBy"
                :text='textContent.input6'
              ></form-text-area>
    </div>
    <div class="form-block">
      <el-row :gutter="gutter"><el-col :span="12">工程概算审核说明</el-col></el-row>
      <el-row :gutter="gutter"><el-col :span="12">一． 工程概况</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input7, field: 'input7' }"
        @childByValue="childBy"
        :text="textContent.input7"
      ></form-text-area>
      <el-row :gutter="gutter"><el-col :span="12">二． 审核范围</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input8, field: 'input8' }"
        @childByValue="childBy"
        :text='textContent.input8'
      ></form-text-area>
      <el-row :gutter="gutter"><el-col :span="12">三．审核原则</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input9, field: 'input9' }"
        @childByValue="childBy"
        :text='textContent.input9'
      ></form-text-area>
      <el-row :gutter="gutter"><el-col :span="12">四．审核方法</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input10, field: 'input10' }"
        @childByValue="childBy"
        :text='textContent.input10'
      ></form-text-area>
      <el-row :gutter="gutter"><el-col :span="12">五．审核依据</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input11, field: 'input11' }"
        @childByValue="childBy"
        :text='textContent.input11'
      ></form-text-area>
      <el-row :gutter="gutter"><el-col :span="12">六．审核程序</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input12, field: 'input12' }"
        @childByValue="childBy"
        :text='textContent.input12'
      ></form-text-area>
      <el-row :gutter="gutter"><el-col :span="12">七．审核情况说明</el-col></el-row>
      <form-text-area
        :obj="{ model: form.input13, field: 'input13' }"
        @childByValue="childBy"
        :text='textContent.input13'
      ></form-text-area>
    </div>
  </el-form>
</template>

<script>
import formTextArea from '../../../common/form-textarea.vue';
import dataJs from './data.js';
export default {
  props: ['transmittedData', 'disabled'],
  data() {
    return {
      currentTransmittedData: this.transmittedData,
      contractNum: this.transmittedData.contractNum,
      projectName: this.transmittedData.projectName,
      template: '11',
      gutter: 40,
      form: {
        // 表单
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        input48: '',
        input49: '',
        contractNum: '',
        input5: '北京市海淀区体育局' /* 致公司 */,
        input6: dataJs.input6/*, 工程结算审核报告具体内容 */,
        input7:dataJs.input7 /* 工程概况 */,
        input8: dataJs.input8 /* 审核范围 */,
        input9: dataJs.input9/* 审核原则 */,
        input10: dataJs.input10 /* 审核方法*/,
        input11: dataJs.input11/*审核依据 */,
        input12:dataJs.input12 /* 审核程序 */,
        input13:dataJs.input13/* 审核情况说明 */
      },
      textContent:{
        input6:dataJs.input6,/*, 工程结算审核报告具体内容 */
        input7:dataJs.input7,
        input8:dataJs.input8,
        input9:dataJs.input9,
        input10:dataJs.input10,
        input11:dataJs.input11,
        input12:dataJs.input12,
        input13:dataJs.input13,
      },
      rule: {
        input3: [
          {
            required: true,
            message: '请选择报告日期',
            trigger: 'blur'
          }
        ],
        input49: [
          {
            required: true,
            message: '请输入工程名称',
            trigger: 'blur'
          }
        ],
        input50: [
          {
            required: true,
            message: '请输入工程概算审核说明',
            trigger: 'blur'
          }
        ]
      }
    };
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
  components: {
    'form-text-area': formTextArea
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
    },
    childBy(obj){
      this.$set(this.form,obj.label,obj.value)
    },
  }
};
</script>
<style lang="scss">
@import url('../../common/css/form.css');
</style>
<style lang="scss" scoped></style>
