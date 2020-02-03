<template>
  <!-- 编制概算-->
  <div class="prepareBudget">
    <form class="projectForm" id="resultsFile">
      <div class="block">
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input1" v-validate="'required'" name="input1" data-vv-as="项目名称" />
        </div>
        <div class="form-item">
          <mt-cell title="合同号:">
            <span>{{ contractNum }}</span>
          </mt-cell>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>成果文件编号</label>
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input19" v-validate="'required'" name="input19" data-vv-as="成果文件编号" />
         </div>
        <div class="form-item">
          <label>报告编号</label>
          <input type="text" placeholder="请输入报告编号" v-model="templateform.input2" v-validate="'required'" name="input2" data-vv-as="报告编号" />
        </div>
        <div class="form-item">
          <mt-cell title="报告日期">
            <input type="text" @click="openPicker('picker')" placeholder="请选择" readonly="readonly" v-model="templateform.input3" v-validate="'required'" name="input3" data-vv-as="报告日期" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input3')" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <div class="form-item">
          <label>目录</label>
          <input type="text" placeholder="请输入目录" v-model="templateform.input4" v-validate="'required'" name="input4" data-vv-as="目录"/>
        </div>
      </div>
      <div class="block">
        <p>工程概况</p>
        <div class="form-item">
          <label>工程名称</label>
          <input type="text" placeholder="请输入工程名称" v-model="templateform.input5"  v-validate="'required'" name="input5" data-vv-as="工程名称"/>
        </div>
        <div class="form-item">
          <label>工程地点</label>
          <input type="text" placeholder="请输入工程地点" v-model="templateform.input6"  v-validate="'required'" name="input6" data-vv-as="工程地点"/>
        </div>
        <div class="form-item">
          <label>建筑规模/范围</label>
          <input type="text" placeholder="请输入建筑规模/范围" v-model="templateform.input7"  v-validate="'required'" name="input7" data-vv-as="建筑规模/范围"/>
        </div>
        <div class="form-item">
          <label>编制范围</label>
          <textarea name="" id="area" placeholder="请输入编制范围"  v-validate="'required'" name="input8" data-vv-as="编制范围" v-model="templateform.input8"></textarea>
        </div>
      </div>
      <div class="block">
        <p>编制依据</p>
        <div class="form-item">
          <label>
            图纸、文件
            <i class="iconfont iconhelp" @click="help('图纸、设计文件，标出版本时间信息')"></i>
          </label>
          <textarea name="" id="area" placeholder="请输入图纸、文件" v-validate="'required'" name="input9" data-vv-as="图纸、文件" v-model="templateform.input9"></textarea>
        </div>
        <div class="form-item form-help">
          <i class="iconfont iconhelp" @click.stop="help('《北京市建设工程计价依据——概算定额》及相应费用文件、北京市建设工程造价管理部门颁布的相关文件及规定')"></i>
          <mt-cell title="法规颁布年份">
            <input type="text" @click="openPicker('particular')" placeholder="请选择" readonly="readonly" v-model="templateform.input10" v-validate="'required'" name="input10" data-vv-as="法规颁布年份" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="particular" v-model="pickerVisible" type="date" @confirm="handleConfirm('input10', 'year')" class="year" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <div class="form-item form-help">
          <i class="iconfont iconhelp"  @click.stop="help('北京市《北京工程造价信息》及市场询价')"></i>
          <mt-cell title="法规颁布年月">
            <input type="text" @click="openPicker('month')" placeholder="请选择" readonly="readonly" v-model="templateform.input11" v-validate="'required'" name="input11" data-vv-as="法规颁布年月" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="month" v-model="pickerVisible" type="date" @confirm="handleConfirm('input11', 'month')" class="month" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <div class="form-item">
          <label>计量说明</label>
          <textarea name="" id="area" placeholder="请输入计量说明" v-validate="'required'" name="input12" data-vv-as="计量说明" v-model="templateform.input12"></textarea>
        </div>
      </div>
      <div class="block">
        <p>计价说明</p>
        <div class="form-item form-help">
          <i class="iconfont iconhelp" @click.stop="help('《北京工程造价信息》及市场询价编制')"></i>
          <mt-cell title="法规颁布年月">
            <input type="text" @click="openPicker('month1')" placeholder="请选择" readonly="readonly" v-model="templateform.input13" v-validate="'required'" name="input13" data-vv-as="法规颁布年月" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="month1" v-model="pickerVisible" type="date" @confirm="handleConfirm('input13', 'month')" class="month"></mt-datetime-picker>
        <div class="form-item zanding">
          <label for="">建筑工程人工费/工日</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入建筑工程人工费/工日" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="建筑工程人工费/工日" />
            <span>元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">装饰工程人工费/工日</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入装饰工程人工费/工日" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="装饰工程人工费/工日"/>
            <span>元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">安装工程人工费/工日</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入安装工程人工费/工日" v-model="templateform.input16" v-validate="'required'" name="input16" data-vv-as="安装工程人工费/工日"/>
            <span>元</span>
          </div>
        </div>
        <div class="form-item">
          <label @click="help('执行指导费率编制')">费率均执行指导费率编制<i class="iconfont iconhelp"></i></label>
          <input type="text" placeholder="请输入费率均执行指导费率编制" v-model="templateform.input17" v-validate="'required'" name="input17" data-vv-as="费率均执行指导费率编制"/>
        </div>
        <div class="form-item">
          <label @click="help('弃土/渣土消纳运距按15km计价')">弃土/渣土消纳运距按15km计价 <i class="iconfont iconhelp"></i></label>
          <input type="text" placeholder="请输入弃土/渣土消纳运距按15km计价" v-model="templateform.input18" v-validate="'required'" name="input18" data-vv-as="弃土/渣土消纳运距按15km计价"/>
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
        input19: '',
      },
      pickerVisible: new Date(),
      startDate:new Date("1901-01-01"),
      endDate:new Date(new Date().getFullYear()+100+'-01-01')
    };
  },
  props: ['contractNum'],
  methods: {
    openPicker(text) {
      this.pickerVisible = new Date();
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      if (val == 'year') {
        this.$set(this.templateform,text,this.formatting(this.pickerVisible).slice(0, 4))
      } else if (val == 'month') {
        this.$set(this.templateform,text,this.formatting(this.pickerVisible).slice(0, 7))
      } else {
        this.$set(this.templateform,text,this.formatting(this.pickerVisible))
      }
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
<style lang="scss">
.prepareBudget {
  .year {
    .picker-slot:nth-child(2) {
      display: none;
    }
    .picker-slot:nth-child(3) {
      display: none;
    }
  }
  .month {
    .picker-slot:nth-child(3) {
      display: none;
    }
  }
}
</style>
<style scoped lang="scss">
.prepareBudget {
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
