<template>
  <!-- 编制结算-->
  <div class="prepareSettlement">
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
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input3')"></mt-datetime-picker>
        <div class="form-item">
          <label>目录</label>
          <input type="text" placeholder="请输入目录" v-model="templateform.input4" v-validate="'required'" name="input4" data-vv-as="目录" />
        </div>
      </div>
      <div class="block">
        <p>工程概况</p>
        <div class="form-item">
          <label>工程名称</label>
          <input type="text" placeholder="请输入工程名称" v-model="templateform.input5" v-validate="'required'" name="input5" data-vv-as="工程名称" />
        </div>
        <div class="form-item">
          <label>工程地点</label>
          <input type="text" placeholder="请输入工程地点" v-model="templateform.input6" v-validate="'required'" name="input6" data-vv-as="工程地点" />
        </div>
        <div class="form-item">
          <label>建筑规模/范围</label>
          <input type="text" placeholder="请输入建筑规模/范围" v-model="templateform.input7" v-validate="'required'" name="input7" data-vv-as="建筑规模/范围" />
        </div>
        <div class="form-item">
          <label>合同开竣工日期</label>
          <div class="temporal">
            <input
              type="text"
              v-model="startDate"
              placeholder="开始时间"
              readonly="readonly"
              @click="openPicker('completed')"
              v-validate="'required'"
              name="startDate"
              data-vv-as="合同开竣工日期开始时间"
            />
            <input type="text" readonly="readonly" value="至" style="text-align: center;" />
            <input
              type="text"
              v-model="startDate1"
              placeholder="结束时间"
              readonly="readonly"
              @click="openPicker('completed1')"
              v-validate="'required'"
              name="startDate1"
              data-vv-as="合同开竣工日期结束时间"
            />
          </div>
        </div>
        <mt-datetime-picker ref="completed" v-model="pickerVisible" type="date" @confirm="handleConfirm1('startDate')"></mt-datetime-picker>
        <mt-datetime-picker ref="completed1" v-model="pickerVisible" type="date" @confirm="handleConfirm1('startDate1')"></mt-datetime-picker>
        <div class="form-item">
          <label>实际开竣工日期</label>
          <div class="temporal">
            <input
              type="text"
              v-model="endDate"
              placeholder="开始时间"
              readonly="readonly"
              @click="openPicker('practical')"
              v-validate="'required'"
              name="endDate"
              data-vv-as="实际开竣工日期开始时间"
            />
            <input type="text" readonly="readonly" value="至" style="text-align: center;" />
            <input
              type="text"
              v-model="endDate1"
              placeholder="结束时间"
              readonly="readonly"
              @click="openPicker('practical1')"
              v-validate="'required'"
              name="endDate1"
              data-vv-as="实际开竣工日期结束时间"
            />
          </div>
        </div>
        <mt-datetime-picker ref="practical" v-model="pickerVisible" type="date" @confirm="handleConfirm1('endDate')"></mt-datetime-picker>
        <mt-datetime-picker ref="practical1" v-model="pickerVisible" type="date" @confirm="handleConfirm1('endDate1')"></mt-datetime-picker>
        <div class="form-item zanding">
          <label for="">合同金额</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入合同金额" v-model="templateform.input10" v-validate="'required'" name="input10" data-vv-as="合同金额" />
            <span>元</span>
            <mt-switch v-model="templateform.moneyTentative">暂定</mt-switch>
          </div>
        </div>
        <div class="form-item">
          <mt-cell title="结算方式">
            <input type="text" placeholder="请选择" @click="choose('结算方式')" readonly="readonly" v-model="templateform.input11" v-validate="'required'" name="input11" data-vv-as="结算方式" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <div class="form-item">
          <label>发包人</label>
          <input type="text" placeholder="请输入发包人" v-model="templateform.input12" v-validate="'required'" name="input12" data-vv-as="发包人" />
        </div>
        <div class="form-item">
          <label>承包人</label>
          <input type="text" placeholder="请输入承包人" v-model="templateform.input13"  v-validate="'required'" name="input13" data-vv-as="承包人"/>
        </div>
        <div class="form-item">
          <label>设计单位</label>
          <input type="text" placeholder="请输入设计单位" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="设计单位" />
        </div>
        <div class="form-item">
          <label>监理单位</label>
          <input type="text" placeholder="请输入监理单位" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="监理单位" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>编制范围</label>
          <textarea name="" id="area" placeholder="请输入编制范围" v-model="templateform.input16" v-validate="'required'" name="input16" data-vv-as="编制范围"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>编制依据</label>
          <textarea name="" id="area" placeholder="请输入编制依据" v-model="templateform.input17" v-validate="'required'" name="input17" data-vv-as="编制依据"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>有关计量计价说明</label>
          <textarea name="" id="area" placeholder="请输入有关计量计价说明" v-model="templateform.input18" v-validate="'required'" name="input18" data-vv-as="有关计量计价说明"></textarea>
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
        input20: '',
        input21: ''
      },
      startDate: '',
      startDate1: '',
      endDate: '',
      endDate1: '',
      popupVisibleGenre: false,
      pickerVisible: new Date(),
      Genrelist: [],
      title: '',
      genre: ''
    };
  },
  props: ['contractNum'],
  watch: {
    startDate(newval, oldval) {
      this.templateform.input8 = newval + '至' + this.startDate1;
    },
    startDate1(newval, oldval) {
      this.templateform.input8 =  this.startDate+ '至' + newval;
    },
    endDate(newval, oldval) {
      this.templateform.input9 = newval + '至' + this.endDate1;
    },
    endDate1(newval, oldval) {
      this.templateform.input9 = this.endDate + '至' + newval;
    }
  },
  methods: {
    openPicker(text) {
      this.pickerVisible = new Date();
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
          label: '单位结算',
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
      this.templateform.input11 = this.genre;
      const vm = this;
      this.Genrelist.forEach(function(i) {
        if (i.value === vm.templateform.input11) {
          vm.templateform.input11 = i.label;
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.prepareSettlement {
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
