<template>
  <!-- 估算审核模板-->
  <div class="estimateAudit">
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
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input35" v-validate="'required'" name="input35" data-vv-as="成果文件编号"  />
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
        <mt-datetime-picker ref="picker" v-model="pickerVisible" type="date" @confirm="handleConfirm('input3')" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <div class="form-item">
          <label>目录</label>
          <input type="text" placeholder="请输入目录" v-model="templateform.input4" v-validate="'required'" name="input4" data-vv-as="目录" />
        </div>
      </div>
      <div class="block">
        <p>工程审核报告</p>
        <div class="form-item">
          <label>委托人</label>
          <input type="text" placeholder="请输入合同基本信息委托人" v-model="templateform.input5"  v-validate="'required'" name="input5" data-vv-as="委托人" />
        </div>
        <div class="form-item">
          <label>委托人称呼<i class="iconfont iconhelp" @click.stop="help('贵公司、贵单位')"></i></label>
          <input type="text" placeholder="请输入委托人称呼" v-model="templateform.input6"  v-validate="'required'" name="input6" data-vv-as="委托人称呼" />
        </div>
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input7"  v-validate="'required'" name="input7" data-vv-as="项目名称"/>
        </div>
        <div class="form-item">
          <label>报送单位</label>
          <input type="text" placeholder="请输入报送单位" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="报送单位"/>
        </div>
        <div class="form-item">
          <label>编制单位</label>
          <input type="text" placeholder="请输入编制单位" v-model="templateform.input9" v-validate="'required'" name="input9" data-vv-as="编制单位"/>
        </div>
        <div class="form-item zanding">
          <label for="">送审价格</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入送审价格" v-model="templateform.input10" v-validate="'required'" name="input10" data-vv-as="送审价格"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审定价格</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审定价格" v-model="templateform.input11" v-validate="'required'" name="input11" data-vv-as="审定价格"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审减价格</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审减价格" v-model="templateform.input12" v-validate="'required'" name="input12" data-vv-as="审减价格"/>
            <span>万元</span>
          </div>
        </div>
      </div>
      <div class="block">
        <p>工程概算审核说明</p>
        <p>一、工程概况</p>
        <div class="form-item">
          <label>工程地点</label>
          <input type="text" placeholder="请输入工程地点" v-model="templateform.input13" v-validate="'required'" name="input13" data-vv-as="工程地点"/>
        </div>
        <div class="form-item">
          <label>建筑规模/范围</label>
          <input type="text" placeholder="请输入建筑规模/范围" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="建筑规模/范围"/>
        </div>
        <div class="form-item">
          <label>建筑功能及分类<i class="iconfont iconhelp" @click.stop="help('公用事业及城市基础设施/商业、酒店及休闲娱乐建筑/电力、工业建筑/文化、教育及体育设施/铁路、道路及桥梁建设/水务/水运交通/住宅物业')"></i> </label>
          <textarea name="" id="area" placeholder="请输入建筑功能及分类" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="建筑功能及分类"></textarea>
        </div>
        <p>二、审核范围</p>
        <div class="form-item" @click="openPicker('report')">
          <mt-cell title="送报时间">
            <input type="text" placeholder="请选择" readonly="readonly" v-model="templateform.input16" v-validate="'required'" name="input16" data-vv-as="送报时间" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="report" v-model="pickerVisible" type="date" @confirm="handleConfirm('input16')" :start-date='startDate' :end-date="endDate"></mt-datetime-picker>
        <div class="form-item">
          <label>三、审核方法<i class="iconfont iconhelp" @click.stop="help('采用全面审核法对本工程进行概算审核')"></i></label>
          <textarea name="" id="area" placeholder="请输入审核方法" v-model="templateform.input17" v-validate="'required'" name="input17" data-vv-as="审核方法"></textarea>
        </div>
        <p>四、审核依据</p>
        <div class="form-item">
          <label>资料<i class="iconfont iconhelp" @click.stop="help('与建设项目相关的工程地质资料、设计文件、图纸或有关设计专业提供的主要工程量和主要设备清单等此内容替换第4项，第五项委托人名称从上面的委托人获取')"></i></label>
          <input type="text" placeholder="请输入资料" v-model="templateform.input18" v-validate="'required'" name="input18" data-vv-as="资料"/>
        </div>
        <div class="form-item">
          <label>方案资料<i class="iconfont iconhelp" @click.stop="help('建设单位提供的工程实施方案及工程概算')"></i></label>
          <input type="text" placeholder="请输入方案资料" v-model="templateform.input19" v-validate="'required'" name="input19" data-vv-as="方案资料"/>
        </div>
        <p>五、审核情况说明</p>
        <p>1.1、工程费用</p>
        <div class="form-item zanding">
          <label for="">送审</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入送审" v-model="templateform.input21" v-validate="'required'" name="input21" data-vv-as="送审"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审定</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审定" v-model="templateform.input22" v-validate="'required'" name="input22" data-vv-as="审定"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审减</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审减" v-model="templateform.input23" v-validate="'required'" name="input23" data-vv-as="审减"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item">
          <label>工程费用金额来源</label>
          <textarea name="" id="area" placeholder="请输入工程费用金额来源" v-model="templateform.input24" v-validate="'required'" name="input24" data-vv-as="工程费用金额来源"></textarea>
        </div>
        <p>1.2、其他费用</p>
        <div class="form-item zanding">
          <label for="">送审</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入送审" v-model="templateform.input25" v-validate="'required'" name="input25" data-vv-as="送审"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审定</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审定" v-model="templateform.input26" v-validate="'required'" name="input26" data-vv-as="审定"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审减</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审减" v-model="templateform.input27" v-validate="'required'" name="input27" data-vv-as="审减"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item">
          <label>工程费用金额来源</label>
          <textarea name="" id="area" placeholder="请输入工程费用金额来源" v-model="templateform.input28" v-validate="'required'" name="input28" data-vv-as="工程费用金额来源"></textarea>
        </div>
        <p>1.3、工程预备费</p>
        <div class="form-item zanding">
          <label for="">送审</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入送审" v-model="templateform.input29" v-validate="'required'" name="input29" data-vv-as="送审"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审定</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审定" v-model="templateform.input30" v-validate="'required'" name="input30" data-vv-as="审定"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">审减</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入审减" v-model="templateform.input31" v-validate="'required'" name="input31" data-vv-as="审减"/>
            <span>万元</span>
          </div>
        </div>
        <div class="form-item">
          <label>工程费用金额来源</label>
          <textarea name="" id="area" placeholder="请输入工程费用金额来源" v-model="templateform.input32" v-validate="'required'" name="input32" data-vv-as="工程费用金额来源"></textarea>
        </div>
        <div class="form-item">
          <label>2、重要事项说明</label>
          <textarea name="" id="area" placeholder="请输入重要事项说明" v-model="templateform.input33" v-validate="'required'" name="input33" data-vv-as="重要事项说明"></textarea>
        </div>
        <div class="form-item">
          <label>3、存在的问题及建议</label>
          <textarea name="" id="area" placeholder="请输入存在的问题及建议" v-model="templateform.input34" v-validate="'required'" name="input34" data-vv-as="存在的问题及建议"></textarea>
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
        input13: "",
        input14:"",
        input15: "",
        input16: "",
        input17:"",
        input18: "",
        input19: '',
        input20: "",
        input21: '',
        input22: '',
        input23: '',
        input24:"",
        input25: '',
        input26: "",
        input27: '',
        input28: "",
        input29: '',
        input30: "",
        input31: '',
        input32: "",
        input33: "",
        input34: '',
        input35: "",
      },
      pickerVisible: new Date(),
      startDate:new Date("1901-01-01"),
      endDate:new Date(new Date().getFullYear()+100+'-01-01'),
    };
  },
  props: ['contractNum'],
  methods: {
    openPicker(text) {
      this.pickerVisible=new Date();
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      this.templateform[text] = this.formatting(this.pickerVisible);
    },
  }
};
</script>

<style scoped lang="scss">
.estimateAudit {
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
