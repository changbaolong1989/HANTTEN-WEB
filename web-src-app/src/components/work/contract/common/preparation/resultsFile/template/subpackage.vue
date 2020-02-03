<template>
  <!-- 招标控制价模板(分包，承包)-->
  <div class="subpackage">
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
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="成果文件编号" />
        </div>
        <div class="form-item">
          <label>报告编号</label>
          <input type="text" placeholder="请输入报告编号" v-model="templateform.input2" v-validate="'required'" name="input2" data-vv-as="报告编号" />
        </div>
        <div class="form-item">
          <mt-cell title="报告日期">
            <input type="text" @click="openPicker('picker')" placeholder="请选择" readonly="readonly" v-model="templateform.input3" v-validate="'required'" name="input3" data-vv-as="报告日期" :start-date='startDate' :end-date="endDate" />
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
          <textarea name="" id="area" placeholder="请输入编制范围" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="编制范围"></textarea>
        </div>
      </div>
      <div class="block">
        <p>编制依据</p>
        <div class="form-item">
          <label>图纸&资料<i class="iconfont iconhelp" @click.stop="help('图纸、设计文件，标出版本时间信息')"></i></label>
          <textarea name="" id="area" placeholder="请输入图纸、设计文件，标出版本时间信息" v-model="templateform.input9" v-validate="'required'" name="input9" data-vv-as="图纸、设计文件，标出版本时间信息"></textarea>
        </div>
       <div class="form-item form-help" @click="openPicker('particular')">
         <i class="iconfont iconhelp" @click.stop="help('《北京工程造价信息》及市场询价')"></i>
         <mt-cell title="法规颁布年份">
           <input type="text" placeholder="请选择" readonly="readonly" v-model="templateform.input10" v-validate="'required'" name="input10" data-vv-as="法规颁布年份" />
           <i class="iconfont">&#xe630;</i>
         </mt-cell>
       </div>
       <mt-datetime-picker ref="particular" v-model="pickerVisible" type="date" @confirm="handleConfirm('input10', 'year')" class="year" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
      </div>
      <div class="block">
        <div class="form-item">
          <label>清单项目及工程量计量说明 </label>
          <textarea name="" id="area" placeholder="请输入清单项目及工程量计量说明" v-model="templateform.input11" v-validate="'required'" name="input11" data-vv-as="清单项目及工程量计量说明"></textarea>
        </div>
      </div>
      <div class="block">
        <p>清单计价说明</p>
        <div class="form-item zanding">
          <label for="">暂列金额（含税）</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入暂列金额（含税）" v-model="templateform.input12" v-validate="'required'" name="input12" data-vv-as="暂列金额（含税）"  />
            <span>元</span>
          </div>
        </div>
        <div class="form-item">
          <label>暂估价专业分包金额（含税）<i class="iconfont iconhelp" @click.stop="help('暂估价专业分包金额（含税）XX元。其中：XX分包工程暂估金额含税XX元')"></i></label>
          <textarea name="" id="area" placeholder="请输入暂估价专业分包金额（含税）" v-model="templateform.input13" v-validate="'required'" name="input13" data-vv-as="暂估价专业分包金额（含税）" ></textarea>
        </div>
        <div class="form-item">
          <label>其他有关问题的说明 <i class="iconfont iconhelp" @click.stop="help('达到什么标准')"></i></label>
          <textarea name="" id="area" placeholder="请输入其他有关问题的说明" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="其他有关问题的说明" ></textarea>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      popupVisibleyear1: false,
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
      },
      pickerVisible: new Date(),
      startDate:new Date("1901-01-01"),
      endDate:new Date(new Date().getFullYear()+100+'-01-01')
    };
  },
  props: ['contractNum'],
  methods: {
    openPicker(text) {
      this.pickerVisible=new Date();
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      if (val == 'year') {
        this.$set(this.templateform,text,this.formatting(this.pickerVisible).slice(0, 4))
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
.subpackage {
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
.subpackage {
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
