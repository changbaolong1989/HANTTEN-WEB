<template>
  <!-- 编制清标报告 -->
  <div class="prepareTenderClearanceReport">
    <el-form class="form" :model="form" ref="form" :rules="rules" :disabled="disabled">
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="项目名称" prop="input1"><el-input v-model="form.input1" placeholder="请输入项目名称"></el-input></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同号" >{{contractNum}}</el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
        <el-col :span="12">
          <el-form-item label="成果文件编号" prop="input35"><el-input v-model="form.input35" placeholder="请输入成果文件编号"></el-input></el-form-item>
        </el-col>
          <el-col :span="12">
            <el-form-item label="报告编号" prop="input2">
              <el-input v-model="form.input2" placeholder="请输入报告编号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="报告日期" prop="input3">
              <el-date-picker v-model="form.input3" type="date" placeholder="请选择报告日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            工程概况
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="工程名称" prop="input5">
              <el-input v-model="form.input5" placeholder="请输入工程名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工程地点" prop="input6">
              <el-input v-model="form.input6" placeholder="请输入工程地点"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="建筑规模/范围" prop="input7">
              <el-input v-model="form.input7" placeholder="请输入建筑规模/范围"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="招标范围" prop="input8">
              <el-input v-model="form.input8" type="textarea" placeholder="请输入招标范围"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            投标文件总体对比分析
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            工程名称:{{projectName}}
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="收到本工程中标单位的投标文件时间" prop="input9">
              <el-date-picker v-model="form.input9" type="date" placeholder="请选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row class="add addtwo" :gutter="gutter">
          <el-col :span="12">
            <span>中标单位投标函</span>
            <i class="el-icon-plus btn-plus" @click="addAform"></i>
          </el-col>
        </el-row>
        <el-row :gutter="gutter" class="addform" v-for="(item,index) in form.tenderList" :key="index">
          <el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm(index)"></el-button>
          <el-col :span="12">
            <el-form-item label="投标单位" prop="input10">
              <el-input v-model="item.input10" placeholder="请输入投标单位"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中标价（RMB）" prop="input11">
              <el-input v-model="item.input11 " placeholder="请输入中标价（RMB）"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其中暂列金额及专业工程分包" prop="input12">
              <el-input v-model="item.input12" placeholder="请输入其中暂列金额及专业工程分包"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="控制价" prop="input13">
              <el-input placeholder="请输入控制价" v-model="form.input13">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中标价低于控制价" prop="input14">
              <el-input placeholder="请输入中标价低于控制价" v-model="form.input14">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="约占控制价" prop="input15">
              <el-input placeholder="请输入约占控制价" v-model="form.input15">
                <template slot="append">%</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            投标文件完整性分析
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="1.投标函及投标函附录" prop="input16">
              <el-input v-model="form.input16" type="textarea" placeholder="请输入投标函及投标函附录"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="2.投标报价汇总表" prop="input17">
              <el-input v-model="form.input17" type="textarea" placeholder="请输入投标报价汇总表"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="3.工程量清单编制" prop="input19">
              <el-input v-model="form.input19" type="textarea" placeholder="请输入工程量清单编制"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            投标价对比
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="1.算术复核结果" prop="input20">
              <el-input v-model="form.input20" type="textarea" placeholder="请输入算术复核结果"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="投标单位" prop="input36">
              <el-input v-model="form.input36" placeholder="请输入投标单位"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            投标文件商务标详细分析
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="总报价分析如下" prop="input21">
              <el-input v-model="form.input21" type="textarea" placeholder="请输入总报价分析如下"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            分部分项综合单价分析
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="综合单价中人工单价" prop="input22">
              <el-input v-model="form.input22" type="textarea" placeholder="请输入综合单价中人工单价"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="取费费率" prop="input23">
              <el-input v-model="form.input23" type="textarea" placeholder="请输入取费费率"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="人工费单价" prop="input24">
              <el-input v-model="form.input24"  placeholder="请输入人工费单价"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业管理费" prop="input25">
              <el-input v-model="form.input25"  placeholder="请输入企业管理费"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="12">
            <el-form-item label="利润" prop="input26">
              <el-input v-model="form.input26"  placeholder="请输入利润"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="风险费用" prop="input27">
              <el-input v-model="form.input27"  placeholder="请输入风险费用"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="综合单价偏离项" prop="input28">
              <el-input v-model="form.input28" type="textarea"  placeholder="请输入综合单价偏离项"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="12">
            措施项目
          </el-col>
        </el-row>
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="1.总价措施" prop="input29">
              <el-input v-model="form.input29" type="textarea" placeholder="请输入总价措施"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="2.单价措施" prop="input30">
              <el-input v-model="form.input30" type="textarea" placeholder="请输入单价措施"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="其他项目" prop="input31">
              <el-input v-model="form.input31" type="textarea" placeholder="请输入总价措施"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="规费" prop="input32">
              <el-input v-model="form.input32" type="textarea" placeholder="请输入规费"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="税金" prop="input33">
              <el-input v-model="form.input33" type="textarea" placeholder="请输入税金"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div class="form-block">
        <el-row :gutter="gutter">
          <el-col :span="24">
            <el-form-item label="结论" prop="input34">
              <el-input v-model="form.input34" type="textarea" placeholder="请输入结论"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
  export default {
    props: ['transmittedData', 'disabled'],
    data() {
      return {
        currentTransmittedData: this.transmittedData,
        contractNum: this.transmittedData.contractNum,
        projectName: this.transmittedData.projectName,
        template : '4',
        gutter: 40,
        form: {
          input1: "",
          input2: "",
          input3: "",
          input4: "",
          input5: "",
          input6: "",
          input7: "",
          input8: "",
          input9: "",
          tenderList: [],
          input13:"",
          input14:"",
          input15:"",
          input16:"",
          input17:"",
          input18:"",
          input19:"",
          input20:"",
          input21:"",
          input22:"",
          input23:"",
          input24:"",
          input25:"",
          input26:"",
          input27:"",
          input28:"",
          input29:"",
          input30:"",
          input31:"",
          input32:"",
          input33:"",
          input34:"",
          input35:""
        },
        rules: {
          input3: [{
            required: true,
            message: '请选择报告日期',
            trigger: 'blur'
          }],
        },
      }
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
    mounted() {
      let vm = this;
      // 查询数据
      vm.getDetailInfo();
    },
    methods: {
      addAform() {
        this.form.tenderList.push({
          input10: "",
          input11: "",
          input12: ""
        })
      },
      colseForm(index) {
        this.form.tenderList.splice(index, 1)
      },
      getDetailInfo() {
        if (!this.tool.isNull(this.currentTransmittedData.formworkContent) && this.template == this.currentTransmittedData.formworkType) {
          this.form = JSON.parse(this.currentTransmittedData.formworkContent);
          this.form.input9 = this.tool.formatToDate(this.form.input9);
        }
      }
    }
  }
</script>

<style lang="scss">
  @import url('../../common/css/form.css');

  .prepareTenderClearanceReport {
    .addform {
      position: relative;
      background: #F1F5F8;
      border-radius: 10px;
      padding: 20px 15px;
      margin: 10px 0;
    }

    .colseform {
      position: absolute;
      top: 0;
      right: 10px;
      z-index: 10;
    }
  }
</style>
<style lang="scss" scoped>
  @import url('../../common/css/form.css');
</style>
