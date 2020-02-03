<template>
  <!-- 预算审核意见 -->
  <div class="budgetReviewComments">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>档案编号<i class="iconfont iconhelp" @click.stop="help('HT1134BJ-ZJ-01第一个是合同编号，中间是部门编号 造价部ZJ招标代理部ZB 市场部SC ，最后是流水号')"></i></label>
          <input type="text" placeholder="请输入档案编号" v-model="templateform.input1" v-validate="'required'" name="input1" data-vv-as="档案编号"  />
        </div>
        <div class="form-item">
          <label>公司名称</label>
          <input type="text" placeholder="请输入公司名称" v-model="templateform.input105" v-validate="'required'" name="input105" data-vv-as="公司名称"  />
        </div>
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input2" v-validate="'required'" name="input2" data-vv-as="项目名称"  />
        </div>
      </div>
      <div class="block">
        <p>审核意见</p>
        <div class="form-item">
          <label>审核信息<i class="iconfont iconhelp" @click.stop="help('我们接受贵公司委托，对×××工程进行全过程跟踪造价咨询工作。现对本工程施工合同进行审核。标红处为输入框')"></i></label>
          <input type="text" placeholder="请输入审核信息 " v-model="templateform.input3" v-validate="'required'" name="input3" data-vv-as="审核信息"  />
        </div>
        <p>审核范围</p>
        <div class="form-item">
          <label>建设单位</label>
          <input type="text" placeholder="请输入建设单位" v-model="templateform.input4" v-validate="'required'" name="input4" data-vv-as="建设单位"  />
        </div>
        <div class="form-item">
          <label>施工单位</label>
          <input type="text" placeholder="请输入施工单位" v-model="templateform.input5"  v-validate="'required'" name="input5" data-vv-as="施工单位" />
        </div>
        <div class="form-item">
          <mt-cell title="报送日期">
            <input type="text" @click="openPicker('picker')" placeholder="请选择" readonly="readonly" v-model="templateform.input6" v-validate="'required'" name="input6" data-vv-as="报送日期" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input6')" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <div class="form-item">
          <label>送报文件名称</label>
          <input type="text" placeholder="请输入送报文件名称" v-model="templateform.input7" v-validate="'required'" name="input7" data-vv-as="送报文件名称"  />
        </div>
        <div class="form-item">
          <label>审核依据<i class="iconfont iconhelp" @click.stop="help('XX项目施工图纸（备注版本号，图纸日期）；2018年XX月XX日图纸疑问回复')"></i></label>
          <input type="text" placeholder="请输入审核依据" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="审核依据"  />
        </div>
        <div class="form-item form-help form-te">
          <i class="iconfont iconhelp" @click.stop="help('北京市2019年3月《北京工程造价信息》及市场询价。yyyy年MM月')"></i>
          <mt-cell title="《北京工程造价信息》发布时间">
            <input type="text" @click="openPicker('date')" placeholder="请选择" readonly="readonly" v-model="templateform.input9" v-validate="'required'" name="input9" data-vv-as="《北京工程造价信息》发布时间" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="date" v-model="pickerVisible" type="date" @confirm="handleConfirm('input9')" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <p>审核意见</p>
        <div class="form-item zanding">
          <label for="">送审金额<i class="iconfont iconhelp" @click.stop="help('金额千分符，小数点后两位')"></i></label>
          <div class="zandiv">
            <input type="text" placeholder="请输入送审金额" v-model="templateform.input10" v-validate="'required'" name="input10" data-vv-as="送审金额"  />
            <span>元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审定金额<i class="iconfont iconhelp" @click.stop="help('金额千分符，小数点后两位')"></i></label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审定金额" v-model="templateform.input11" v-validate="'required'" name="input11" data-vv-as="审定金额"  />
            <span>元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">核减金额<i class="iconfont iconhelp" @click.stop="help('金额千分符，小数点后两位')"></i></label>
          <div class="zandiv">
            <input type="text" placeholder="请输入核减金额" v-model="templateform.input12" v-validate="'required'" name="input12" data-vv-as="核减金额"  />
            <span>元</span>
          </div>
        </div>
        <div class="form-item">
          <label>意见如下</label>
          <textarea placeholder="请输入意见" v-model="templateform.input13" v-validate="'required'" name="input13" data-vv-as="意见如下" ></textarea>
        </div>
      </div>
      <div class="block">
        <p>附件&其他</p>
        <div class="form-item" @click="choose('是否有附件')">
          <mt-cell title="是否有附件">
            <input type="text" placeholder="请选择" readonly="readonly" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="是否有附件" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <div class="form-item" v-if="templateform.input14 == '是'">
          <label>附件名称</label>
          <input type="text" placeholder="请输入附件名称" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="附件名称"  />
        </div>
        <div class="form-item" @click="choose('是否抄送')">
          <mt-cell title="是否抄送">
            <input type="text" placeholder="请选择" readonly="readonly" v-model="templateform.input16" v-validate="'required'" name="input16" data-vv-as="是否抄送" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <div class="form-item" v-if="templateform.input16 == '是'">
          <label>抄送公司</label>
          <input type="text" placeholder="请输入抄送公司" v-model="templateform.input17" v-validate="'required'" name="input17" data-vv-as="抄送公司"  />
        </div>
        <div class="form-item" v-if="templateform.input16 == '是'">
          <label>抄送人</label>
          <input type="text" placeholder="请输入抄送人" v-model="templateform.input18" v-validate="'required'" name="input18" data-vv-as="抄送人"  />
        </div>
      </div>
      <!--单选弹窗-->
      <mt-popup v-model="popupVisibleGenre" popup-transition="popup-fade" position="bottom" @touchmove.stop.prevent>
        <div class="radiolist">
          <p>{{ title }}</p>
          <div class="item" v-for="(item, index) in Genrelist">
            <label :for="'budget' + index">
              {{ item.label }}
              <input type="radio" :id="'budget' + index" v-model="genre" :value="item.value" name="budget" @change="GenreChange(title)" />
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
        input105: '',
      },
      pickerVisible: new Date(),
      popupVisibleGenre: false,
      startDate:new Date("1901-01-01"),
      endDate:new Date(new Date().getFullYear()+100+'-01-01'),
      genre: '',
      title: '',
      Genrelist: [
        {
          label: '是',
          value: '1'
        },
        {
          label: '否',
          value: '0'
        }
      ]
    };
  },
  methods: {
    openPicker(text) {
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      this.templateform[text] = this.formatting(this.pickerVisible);
    },
    choose(title) {
      this.title = title;
      this.popupVisibleGenre = true;
      this.genre = '';
    },
    GenreChange(title) {
      if (title == '是否有附件') {
        this.templateform.input14 = this.genre;
        const vm = this;
        this.Genrelist.forEach(function(i) {
          if (i.value === vm.templateform.input14) {
            vm.templateform.input14 = i.label;
          }
        });
      } else if (title == '是否抄送') {
        this.templateform.input16 = this.genre;
        const vm = this;
        this.Genrelist.forEach(function(i) {
          if (i.value === vm.templateform.input16) {
            vm.templateform.input16 = i.label;
          }
        });
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

<style></style>
