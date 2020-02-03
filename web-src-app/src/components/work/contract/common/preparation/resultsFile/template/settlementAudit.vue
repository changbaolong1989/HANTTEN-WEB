<template>
  <!-- 结算审核模板-->
  <div class="settlementAudit">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input1"  v-validate="'required'" name="input1" data-vv-as="项目名称" />
        </div>
        <div class="form-item">
          <mt-cell title="合同号">
            <span>{{ contractNum}}</span>
          </mt-cell>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>成果文件编号</label>
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input48"  v-validate="'required'" name="input48" data-vv-as="成果文件编号" />
        </div>
        <div class="form-item">
          <label>报告编号</label>
          <input type="text" placeholder="请输入报告编号" v-model="templateform.input2"  v-validate="'required'" name="input2" data-vv-as="报告编号" />
        </div>
        <div class="form-item">
          <mt-cell title="报告日期">
            <input type="text" @click="openPicker('picker')" placeholder="请选择" readonly="readonly" v-model="templateform.input3" v-validate="'required'" name="input3" data-vv-as="报告日期" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input3')" :start-date='start_Date' :end-date="end_Date"></mt-datetime-picker>
        <div class="form-item">
          <label>目录</label>
          <input type="text" placeholder="请输入目录" v-model="templateform.input4"   v-validate="'required'" name="input4" data-vv-as="目录"/>
        </div>
      </div>
      <div class="block">
        <p>工程审核报告</p>
        <div class="form-item">
          <label>委托人</label>
          <input type="text" placeholder="请输入合同基本信息委托人" v-model="templateform.input5"  v-validate="'required'" name="input5" data-vv-as="委托人" />
        </div>
        <div class="form-item">
          <label>委托人称呼<i class="iconfont iconhelp" @click.stop="help('贵公司、贵单位')"></i> </label>
          <input type="text" placeholder="请输入委托人称呼" v-model="templateform.input6"  v-validate="'required'" name="input6" data-vv-as="委托人称呼" />
        </div>
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input7"  v-validate="'required'" name="input7" data-vv-as="项目名称" />
        </div>
        <div class="form-item">
          <label>报送单位</label>
          <input type="text" placeholder="请输入报送单位" v-model="templateform.input8"  v-validate="'required'" name="input8" data-vv-as="报送单位" />
        </div>
        <div class="form-item">
          <label>编制单位</label>
          <input type="text" placeholder="请输入编制单位" v-model="templateform.input9"  v-validate="'required'" name="input9" data-vv-as="编制单位" />
        </div>
        <div class="form-item zanding">
          <label for="">送审价格</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入送审价格" v-model="templateform.input10"  v-validate="'required'" name="input10" data-vv-as="送审价格" />
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审定价格</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审定价格" v-model="templateform.input11"  v-validate="'required'" name="input11" data-vv-as="审定价格" />
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审减价格</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审减价格" v-model="templateform.input12"  v-validate="'required'" name="input12" data-vv-as="审减价格" />
            <span>万元</span>
          </div>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>工程概算审核说明</label>
          <textarea name="" id="area" placeholder="请输入工程概算审核说明" v-model="templateform.input50" v-validate="'required'" name="input50" data-vv-as="工程概算审核说明"></textarea>
        </div>
      </div>
      <!--单选弹窗-->
      <mt-popup v-model="popupVisibleGenre" popup-transition="popup-fade" position="bottom" @touchmove.stop.prevent>
        <div class="radiolist">
          <p>{{ title }}</p>
          <div class="item" v-for="(item, index) in Genrelist">
            <label :for="'genre' + index">
              {{ item.label }}
              <input type="radio" :id="'genre' + index" v-model="genre" :value="item.value" name="genre" @change="GenreChange(title)" />
              <span></span>
            </label>
          </div>
        </div>
        <div class="ComponentsFooter"><mt-button class="linear" @click.prevent="popupVisibleGenre = false">确定</mt-button></div>
      </mt-popup>
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
        input50: "",
      },
      start_Date:new Date("1901-01-01"),
      end_Date:new Date(new Date().getFullYear()+100+'-01-01'),
      pickerVisible: new Date(),
      popupVisibleGenre: false,
      title: '',
      Genrelist: [],
      genre: '',
    };
  },
  props: ['contractNum'],

  methods: {
    openPicker(text) {
      this.pickerVisible=new Date();
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      this.templateform[text] = this.formattingStr(this.pickerVisible);
    },
    handleConfirm1(text, val) {
      this[text] = this.formattingStr(this.pickerVisible);
    },
    choose(title) {
      this.title = title;
      this.popupVisibleGenre = true;
      this.Genrelist = [
        {
          label: '固定单价结算',
          value: '0'
        },
        {
          label: '固定总价结算',
          value: '1'
        },
        {
          label: '据实结算',
          value: '2'
        }
      ];
    },
    GenreChange() {
      this.templateform.input19 = this.genre;
      const vm = this;
      this.Genrelist.forEach(function(i) {
        if (i.value === vm.templateform.input19) {
          vm.templateform.input19 = i.label;
        }
      });
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
.settlementAudit {
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
