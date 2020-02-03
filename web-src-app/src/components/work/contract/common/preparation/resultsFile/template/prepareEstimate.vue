<template>
  <!-- 编制估算-->
  <div class="prepareEstimate">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input1" v-validate="'required'" name="input1" data-vv-as="项目名称"  />
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
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input14" />
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
          <input type="text" placeholder="请输入目录" v-model="templateform.input4" v-validate="'required'" name="input4" data-vv-as="目录"  />
        </div>
      </div>
      <div class="block">
        <p>工程概况</p>
        <div class="form-item">
          <label>工程名称</label>
          <input type="text" placeholder="请输入工程名称" v-model="templateform.input5" v-validate="'required'" name="input5" data-vv-as="工程名称"  />
        </div>
        <div class="form-item">
          <label>工程地点</label>
          <input type="text" placeholder="请输入工程地点" v-model="templateform.input6" v-validate="'required'" name="input6" data-vv-as="工程地点"  />
        </div>
        <div class="form-item">
          <label>建筑规模/范围</label>
          <input type="text" placeholder="请输入建筑规模/范围" v-model="templateform.input7" v-validate="'required'" name="input7" data-vv-as="建筑规模/范围"  />
        </div>
        <div class="form-item">
          <label>建筑功能及分类</label>
          <input type="text" placeholder="请输入建筑功能及分类" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="建筑功能及分类"  />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>编制范围</label>
          <textarea name="" id="area" placeholder="请输入编制范围" v-validate="'required'" name="input9" data-vv-as="编制范围" v-model="templateform.input9"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>编制依据 <i class="iconfont iconhelp" @click.stop="help('与建设项目相关的工程地质资料、设计文件、图纸或有关设计专业提供的主要工程量和主要设备清单等')"></i> </label>
          <textarea name="" id="area" placeholder="请输入编制依据" v-validate="'required'" name="input10" data-vv-as="编制依据" v-model="templateform.input10"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>估算说明  <i class="iconfont iconhelp" @click.stop="help('如果工程费用采用单方造价指标，现有的资料又不够充分显示项目信息，例如地基处理方式，结构类型，装修标准，机电系统配置，可在此进行系统拟定，拟定时应描述全部单体及室外工程的全部工程内容。')"></i> </label>
          <textarea name="" id="area" placeholder="请输入估算说明" v-validate="'required'" name="input11" data-vv-as="估算说明" v-model="templateform.input11"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>估算外费用  </label>
          <textarea name="" id="area" placeholder="请输入估算外费用" v-validate="'required'" name="input12" data-vv-as="估算外费用" v-model="templateform.input12"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>其他说明  </label>
          <textarea name="" id="area" placeholder="请输入其他说明" v-validate="'required'" name="input13" data-vv-as="其他说明" v-model="templateform.input13"></textarea>
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
    help(text) {
      this.MessageBox({
        title: '提示',
        message: text,
        showCancelButton: true,
        showCancelButton:false,
      });
    }
  }
};
</script>

<style scoped lang="scss">
.prepareEstimate {
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
