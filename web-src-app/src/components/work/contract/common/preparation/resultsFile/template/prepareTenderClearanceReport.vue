<template>
  <!-- 编制清标报告-->
  <div class="prepareTenderClearanceReport">
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
          <input type="text" placeholder="请输入成果文件编号" v-model="templateform.input35"  v-validate="'required'" name="input35" data-vv-as="成果文件编号"/>
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
      </div>
      <div class="block">
        <p>工程概况</p>
        <div class="form-item">
          <label>工程名称</label>
          <input type="text" placeholder="请输入工程名称" v-model="templateform.input5"   v-validate="'required'" name="input5" data-vv-as="工程名称"/>
        </div>
        <div class="form-item">
          <label>工程地点</label>
          <input type="text" placeholder="请输入工程地点" v-model="templateform.input6"  v-validate="'required'" name="input6" data-vv-as="工程地点"/>
        </div>
        <div class="form-item">
          <label>建筑规模/范围</label>
          <input type="text" placeholder="请输入建筑规模/范围" v-model="templateform.input7"  v-validate="'required'" name="input7" data-vv-as="建筑规模/范围" />
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>招标范围</label>
          <textarea name="" id="area" placeholder="请输入招标范围" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="招标范围"></textarea>
        </div>
      </div>
      <div class="block">
        <p>投标文件总体对比分析</p>
        <p>工程名称:石景山区2018年环卫设施改造提升工程</p>
        <div class="form-item">
          <mt-cell title="收到本工程中标单位的投标文件时间">
            <input type="text" @click="openPicker('tender')" placeholder="请选择" readonly="readonly" v-model="templateform.input9" v-validate="'required'" name="input9" data-vv-as="收到本工程中标单位的投标文件时间" />
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
        </div>
        <mt-datetime-picker ref="tender" v-model="pickerVisible" type="date" @confirm="handleConfirm('input9')"></mt-datetime-picker>
        <div class="form-item">
          <mt-cell title="中标单位投标函 ">
            <span @click="addlist">添加</span>
            <i class="iconfont">&#xe630;</i>
          </mt-cell>
          <div class="DynamicallyAdd" v-for="(item, index) in templateform.list" :key="index">
            <i class="iconfont iconshanchu" @click="removeSwipe(index)"></i>
            <div class="form-item">
              <label for="">投标单位</label>
              <input type="text" placeholder="请输入投标单位" v-model="item.input10" v-validate="'required'" name="input10" data-vv-as="投标单位"/>
            </div>
            <div class="form-item">
              <label for="">中标价（RMB）</label>
              <input type="text" placeholder="请输入中标价" v-model="item.input11" v-validate="'required'" name="input11" data-vv-as="中标价（RMB）"/>
            </div>
            <div class="form-item">
              <label for="">其中暂列金额及专业工程分包</label>
              <input type="text" placeholder="请输入其中暂列金额及专业工程分包" v-model="item.input12" v-validate="'required'" name="input12" data-vv-as="其中暂列金额及专业工程分包"/>
            </div>
          </div>
        </div>
      </div>
      <div class="block">
        <div class="form-item zanding">
          <label for="">控制价</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入控制价" v-model="templateform.input13" v-validate="'required'" name="input13" data-vv-as="控制价"/>
            <span>元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">中标价低于控制价</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入中标价低于控制价" v-model="templateform.input14" v-validate="'required'" name="input14" data-vv-as="中标价低于控制价" />
            <span>元</span>
          </div>
        </div>
        <div class="form-item zanding">
          <label for="">约占控制价</label>
          <div class="zandiv">
            <input type="text" placeholder="请输入约占控制价" v-model="templateform.input15" v-validate="'required'" name="input15" data-vv-as="约占控制价"/>
            <span>元</span>
          </div>
        </div>
      </div>
      <div class="block">
        <p>投标文件完整性分析</p>
        <div class="form-item">
          <label>1.投标函及投标函附录</label>
          <textarea name="" id="area" placeholder="请输入1.投标函及投标函附录" v-model="templateform.input16" v-validate="'required'" name="input16" data-vv-as="投标函及投标函附录"></textarea>
        </div>
        <div class="form-item">
          <label>2.投标报价汇总表</label>
          <textarea name="" id="area" placeholder="请输入2.投标报价汇总表" v-model="templateform.input17" v-validate="'required'" name="input17" data-vv-as="投标报价汇总表"></textarea>
        </div>
        <div class="form-item">
          <label>3.工程量清单编制</label>
          <textarea name="" id="area" placeholder="请输入3.工程量清单编制" v-model="templateform.input19" v-validate="'required'" name="input19" data-vv-as="工程量清单编制"></textarea>
        </div>
      </div>
      <div class="block">
        <p>投标价对比</p>
        <div class="form-item">
          <label>1.算术复核结果</label>
          <textarea name="" id="area" placeholder="请输入1.算术复核结果" v-model="templateform.input20" v-validate="'required'" name="input20" data-vv-as="算术复核结果"></textarea>
        </div>
      </div>
      <div class="block">
        <p>投标文件商务标详细分析</p>
        <div class="form-item">
          <label>总报价分析如下</label>
          <textarea name="" id="area" placeholder="请输入总报价分析如下" v-model="templateform.input21" v-validate="'required'" name="input21" data-vv-as="总报价分析如下"></textarea>
        </div>
      </div>
      <div class="block">
        <p>分部分项综合单价分析</p>
        <div class="form-item">
          <label>综合单价中人工单价</label>
          <textarea name="" id="area" placeholder="请输入综合单价中人工单价" v-model="templateform.input22" v-validate="'required'" name="input22" data-vv-as="综合单价中人工单价"></textarea>
        </div>
        <div class="form-item">
          <label>取费费率</label>
          <textarea name="" id="area" placeholder="请输入取费费率" v-model="templateform.input23" v-validate="'required'" name="input23" data-vv-as="取费费率"></textarea>
        </div>
        <div class="form-item">
          <label>人工费单价</label>
          <input type="text" placeholder="请输入人工费单价" v-model="templateform.input24"  v-validate="'required'" name="input24" data-vv-as="人工费单价"/>
        </div>
        <div class="form-item">
          <label>企业管理费</label>
          <input type="text" placeholder="请输入企业管理费" v-model="templateform.input25"  v-validate="'required'" name="input25" data-vv-as="企业管理费"/>
        </div>
        <div class="form-item">
          <label>利润</label>
          <input type="text" placeholder="请输入利润" v-model="templateform.input26"  v-validate="'required'" name="input26" data-vv-as="利润"/>
        </div>
        <div class="form-item">
          <label>风险费用</label>
          <input type="text" placeholder="请输入风险费用" v-model="templateform.input27"  v-validate="'required'" name="input27" data-vv-as="风险费用"/>
        </div>
        <div class="form-item">
          <label>综合单价偏离项</label>
          <textarea name="" id="area" placeholder="请输入综合单价偏离项" v-model="templateform.input28" v-validate="'required'" name="input28" data-vv-as="综合单价偏离项"></textarea>
        </div>
      </div>
      <div class="block">
        <p>措施项目</p>
        <div class="form-item">
          <label>1.总价措施</label>
          <textarea name="" id="area" placeholder="请输入1.总价措施" v-model="templateform.input29" v-validate="'required'" name="input29" data-vv-as="1.总价措施"></textarea>
        </div>
        <div class="form-item">
          <label>2.单价措施</label>
          <textarea name="" id="area" placeholder="请输入2.单价措施" v-model="templateform.input30" v-validate="'required'" name="input30" data-vv-as="2.单价措施"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>其他项目</label>
          <textarea name="" id="area" placeholder="请输入其他项目" v-model="templateform.input31" v-validate="'required'" name="input31" data-vv-as="其他项目"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>规费</label>
          <textarea name="" id="area" placeholder="请输入规费" v-model="templateform.input32" v-validate="'required'" name="input32" data-vv-as="规费"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>税金</label>
          <textarea name="" id="area" placeholder="请输入税金" v-model="templateform.input33" v-validate="'required'" name="input33" data-vv-as="税金"></textarea>
        </div>
      </div>
      <div class="block">
        <div class="form-item">
          <label>结论</label>
          <textarea name="" id="area" placeholder="请输入结论" v-model="templateform.input34" v-validate="'required'" name="input34" data-vv-as="结论"></textarea>
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
        input15: '',
        input16: '',
        input17: '',
        input18: '',
        input19: '',
        input20: '',
        input21: '',
        input22: '',
        input23: '',
        input24: '',
        input25: '',
        input26: '',
        input27: '',
        input28: '',
        input29: '',
        input30: '',
        input31: '',
        input32: '',
        input33: '',
        input34: '',
        input35: '',
        list: []
      },
      pickerVisible: new Date()
    };
  },
  props: ['contractNum'],
  methods: {
    openPicker(text) {
      this.pickerVisible = new Date();
      this.$refs[text].open();
    },
    handleConfirm(text, val) {
      this.templateform[text] = this.formatting(this.pickerVisible);
    },
    addlist(text) {
      /* 动态添加 */
      this.templateform.list.push({
        input12: '',
        input13: '',
        input14: ''
      });
    },
    removeSwipe(index) {
      /* 动态删除 */
      this.templateform.list.splice(index, 1);
    }
  }
};
</script>

<style scoped lang="scss"></style>
