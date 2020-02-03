<template>
  <!-- 编制决算-->
  <div class="prepareAccounts">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input1" v-validate="'required'" name="input1" data-vv-as="项目名称" />
        </div>
        <div class="form-item">
          <mt-cell title="合同号">
            <span>{{ contractNum }}</span>
          </mt-cell>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>成果文件编号</label>
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input18"  v-validate="'required'" name="input18" data-vv-as="成果文件编号" />
        </div>
        <div class="form-item">
          <label>报告编号</label>
          <input type="text" placeholder="请输入报告编号" v-model="templateform.input2"  v-validate="'required'" name="input2" data-vv-as="报告编号"/>
        </div>
        <div class="form-item">
          <mt-cell title="报告日期">
            <input type="text" @click="openPicker('picker')" placeholder="请选择" readonly="readonly" v-model="templateform.input3" v-validate="'required'" name="input3" data-vv-as="报告日期" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input3')"></mt-datetime-picker>
        <div class="form-item">
          <label>目录</label>
          <input type="text" placeholder="请输入目录" v-model="templateform.input4"  v-validate="'required'" name="input4" data-vv-as="目录"/>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>项目概况</label>
          <input type="text" placeholder="请输入项目概况" v-model="templateform.input5" v-validate="'required'" name="input5" data-vv-as="项目概况" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>会计账务处理、财产物资清理及债权债务的清偿情况</label>
          <textarea name="" id="area" placeholder="请输入会计账务处理、财产物资清理及债权债务的清偿情况" v-model="templateform.input6" v-validate="'required'" name="input6" data-vv-as="会计账务处理、财产物资清理及债权债务的清偿情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>项目支出预算、投资计划和资金到位情况 </label>
          <textarea name="" id="area" placeholder="请输入项目支出预算、投资计划和资金到位情况" v-model="templateform.input7" v-validate="'required'" name="input7" data-vv-as="项目支出预算、投资计划和资金到位情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>项目建设资金使用、结余情况</label>
          <textarea name="" id="area" placeholder="请输入项目建设资金使用、结余情况" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="项目建设资金使用、结余情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>概（预）算执行情况及分析  </label>
          <textarea name="" id="area" placeholder="请输入概（预）算执行情况及分析" v-model="templateform.input9" v-validate="'required'" name="input9" data-vv-as="概（预）算执行情况及分析"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>尾工工程情况  </label>
          <textarea name="" id="area" placeholder="请输入尾工工程情况" v-model="templateform.input10" v-validate="'required'" name="input10" data-vv-as="尾工工程情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>历次审计、检查、稽查及整改情况  </label>
          <textarea name="" id="area" placeholder="请输入历次审计、检查、稽查及整改情况" v-model="templateform.input11" v-validate="'required'" name="input11" data-vv-as="历次审计、检查、稽查及整改情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>主要技术经济指标的分析、计算情况</label>
          <textarea name="" id="area" placeholder="请输入主要技术经济指标的分析、计算情况" v-model="templateform.input12" v-validate="'required'" name="input12" data-vv-as="主要技术经济指标的分析、计算情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>项目管理经验、问题和建议</label>
          <textarea name="" id="area" placeholder="请输入项目管理经验、问题和建议" v-model="templateform.input13" v-validate="'required'" name="input13" data-vv-as="项目管理经验、问题和建议"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>预备费动用情况</label>
          <textarea name="" id="area" placeholder="请输入预备费动用情况" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="预备费动用情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>招投标情况、工程政府采购情况、合同履行情况</label>
          <textarea name="" id="area" placeholder="请输入招投标情况、工程政府采购情况、合同履行情况" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="招投标情况、工程政府采购情况、合同履行情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>征地拆迁补偿情况、移民安置情况</label>
          <textarea name="" id="area" placeholder="请输入征地拆迁补偿情况、移民安置情况" v-model="templateform.input16"  v-validate="'required'" name="input16" data-vv-as="征地拆迁补偿情况、移民安置情况"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>需说明的其他事项</label>
          <textarea name="" id="area" placeholder="请输入需说明的其他事项" v-model="templateform.input17"  v-validate="'required'" name="input17" data-vv-as="需说明的其他事项"></textarea>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      templateform: {
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        input5: '',
        input6: '',
        input7: '',
        input8: '',
        input9: '',
        input10: '',
        input11: '',
        input12: '',
        input13: '',
        input14: '',
        input15: '',
        input16: '',
        input17: '',
        input18: '',
      },
      pickerVisible: new Date(),
    };
  },
  props: ['contractNum'],
  methods: {
    openPicker(text) {
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      this.templateform[text] = this.formatting(this.pickerVisible);
    },
  }
};
</script>

<style scoped lang="scss">
.prepareAccounts {
  .projectForm {
    .block {
      background-color: #fff;
      border-radius: 10px;
      padding: 20px 20px 0;
      margin-top: 20px;
      p {
        font-size: 28px;
      }
    }
  }
}
</style>
