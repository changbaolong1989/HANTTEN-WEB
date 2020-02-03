<template>
  <!-- 造价咨询合同 -->
  <div class="custom-contract__form-manufacturing-cost">
    <p>造价咨询合同</p>
    <ul class="classes">
      <li class="form-item">
        <label for="">省份</label>
        <div><input type="text" placeholder="请输入省份" v-model="currentForm.province" v-validate="'required'" data-vv-name="省份"/></div>
      </li>
      <li class="form-item">
        <label for="">地区</label>
        <div><input type="text" placeholder="请输入地区" v-model="currentForm.region" v-validate="'required'" data-vv-name="地区"/></div>
      </li>
      <li class="form-item">
        <label for="">工程规模</label>
        <div><input type="text" placeholder="请输入工程规模" v-model="currentForm.generalize" v-validate="'required'" data-vv-name="工程规模"/></div>
      </li>
      <li class="form-item">
        <label for="">资金来源</label>
        <div><input type="text" placeholder="请输入资金来源" v-model="currentForm.fund" v-validate="'required'" data-vv-name="资金来源"/></div>
      </li>
      <li class="form-item">
        <label for="">建设工期或周期</label>
        <div><input type="text" placeholder="请输入建设工期或周期" v-model="currentForm.period" v-validate="'required'" data-vv-name="建设工期或周期"/></div>
      </li>
      <li class="form-item">
        <label for="">双方约定的服务范围及工作内容</label>
        <div><input type="text" placeholder="请输入双方约定的服务范围及工作内容" v-model="currentForm.scope" v-validate="'required'" data-vv-name="双方约定的服务范围及工作内容"/></div>
      </li>

      <li class="form-item" @click="openPicker('viabilityStart')">
        <mt-cell title="服务期限--开始日期">
          <span>{{ tool.dateFormat(currentForm.viabilityStart,'--') }}</span>
          <input type="text" v-model="currentForm.viabilityStart" v-validate="'required'" data-vv-name="服务期限--开始日期" hidden/>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </li>
      <mt-datetime-picker ref="viabilityStart" type="date" v-model="pickerValue" @confirm="handleConfirm('viabilityStart')"></mt-datetime-picker>

      <li class="form-item" @click="openPicker('viabilityEnd')">
        <mt-cell title="服务期限--结束日期">
          <span>{{ tool.dateFormat(currentForm.viabilityEnd,'--') }}</span>
          <input type="text" v-model="currentForm.viabilityEnd" v-validate="'required'" data-vv-name="服务期限--结束日期" hidden/>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
      </li>
      <mt-datetime-picker ref="viabilityEnd" type="date" v-model="pickerValue" @confirm="handleConfirm('viabilityEnd')"></mt-datetime-picker>

      <li class="form-item">
        <label for="">总合同份数</label>
        <div><input type="text" placeholder="请输入总合同份数" v-model="currentForm.copies" v-validate="'required'" data-vv-name="总合同份数"/></div>
      </li>
      <li class="form-item">
        <label for="">委托人合同份数</label>
        <div><input type="text" placeholder="请输入委托人合同份数" v-model="currentForm.entrust" v-validate="'required'" data-vv-name="委托人合同份数"/></div>
      </li>
      <li class="form-item">
        <label for="">咨询人合同份数</label>
        <div><input type="text" placeholder="请输入咨询人合同份数" v-model="currentForm.consult" v-validate="'required'" data-vv-name="咨询人合同份数"/></div>
      </li>
      <li class="form-item">
        <label for="">委托人代表</label>
        <div><input type="text" placeholder="请输入委托人代表" v-model="currentForm.representative" v-validate="'required'" data-vv-name="委托人代表"/></div>
      </li>
      <li class="form-item">
        <label for="">团队人员</label>
        <div><input type="text" placeholder="请输入团队人员" v-model="currentForm.personnel" v-validate="'required'" data-vv-name="团队人员"/></div>
      </li>
      <li class="form-item">
        <m-select v-model="currentForm.pay" :data="{title: '支付方式',options: dataList.payment}"
          unique="value" display="label" v-validate="'required'" data-vv-name="支付方式">
        </m-select>
      </li>
      <li class="form-item" v-if="'0'==currentForm.pay">
        <label for="">咨询成果报告出具几日内支付</label>
        <div class="sky">
          <input type="text" placeholder="请输入" v-model="currentForm.input" v-validate="'required'" data-vv-name="咨询成果报告出具几日内支付"/>
          <span>天</span>
        </div>
      </li>

      <li class="form-item" v-if="'1'==currentForm.pay">
        <label for="">分期信息</label>
        <textarea placeholder="请输入分期信息" v-model="currentForm.desc" v-validate="'required'" data-vv-name="分期信息"></textarea>
      </li>

      <li class="form-item">
        <label for="">争议解决</label>
        <div><input type="text" placeholder="请输入争议解决" v-model="currentForm.dispute" v-validate="'required'" data-vv-name="争议解决"/></div>
      </li>
      <li class="form-item">
        <label for="">送达接收人</label>
        <div><input type="text" placeholder="请输入送达接收人" v-model="currentForm.recipient" v-validate="'required'" data-vv-name="送达接收人"/></div>
      </li>
      <li class="form-item">
        <label for="">送达地点</label>
        <div><input type="text" placeholder="请输入送达地点" v-model="currentForm.site" v-validate="'required'" data-vv-name="送达地点"/></div>
      </li>
      <li class="form-item">
        <label for="">酬金及计取方式为</label>
        <div><input type="text" placeholder="请输入酬金及计取方式" v-model="currentForm.reward" v-validate="'required'" data-vv-name="酬金及计取方式为"/></div>
      </li>


      <li class="form-item">
        <mt-cell title="服务范围及工作内容、酬金">
          <span @click="addlist('服务范围及工作内容、酬金')">添加</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
        <p class="small">(注：未选择的服务内容将在文档中以“/”体现)</p>
        <div class="DynamicallyAdd dark" v-for="(item, index) in currentForm.remuneration" :key="index">
          <i class="iconfont iconshanchu" @click="removeSwipe(index, 'remuneration')"></i>
          <div class="form-item">
            <m-select v-model="item.serve" :data="{title: '服务阶段',options: dataList.services}"
              unique="value" display="label" v-validate="'required'" data-vv-name="服务阶段" @change="servicesChange">
            </m-select>
          </div>
          <div class="form-item" v-show="1==showContent">
            <label for="">工作内容</label>
            <div><input type="text" placeholder="请输入工作内容" v-model="item.details" v-validate="'required'" data-vv-name="工作内容"/></div>
          </div>
          <div class="form-item" v-show="2==showContent">
            <m-select v-model="item.content" :data="{title: '工作内容',options: dataList.jobContent2}"
              unique="value" display="label" v-validate="'required'" data-vv-name="工作内容" :multiple="true">
            </m-select>
          </div>
          <div class="form-item" v-show="3==showContent">
             <m-select v-model="item.content" :data="{title: '工作内容',options: dataList.jobContent1}"
               unique="value" display="label" v-validate="'required'" data-vv-name="工作内容" :multiple="true">
             </m-select>
           </div>
          <div class="form-item zanding">
            <label for="">收费基数</label>
            <div class="zandiv">
              <input type="text" placeholder="请输入收费基数" v-model="item.charge.money" v-validate="'required'" data-vv-name="收费基数"/>
              <span>元</span>
              <mt-switch v-model="item.charge.state">暂定</mt-switch>
            </div>
          </div>
          <div class="form-item zanding">
            <label for="">收费标准</label>
            <div class="zandiv">
              <input type="text" placeholder="请输入收费标准" v-model="item.standard.money" v-validate="'required'" data-vv-name="收费标准"/>
              <span>元</span>
              <mt-switch v-model="item.standard.state">暂定</mt-switch>
            </div>
          </div>
          <div class="form-item zanding">
            <label for="">酬金金额</label>
            <div class="zandiv">
              <input type="text" placeholder="请输入酬金金额" v-model="item.sum.money" v-validate="'required'" data-vv-name="酬金金额"/>
              <span>元</span>
              <mt-switch v-model="item.sum.state">暂定</mt-switch>
            </div>
          </div>
          <div class="form-item">
            <label>备注</label>
            <textarea placeholder="请输入备注" v-model="item.remark" v-validate="'required'" data-vv-name="备注"></textarea>
          </div>
        </div>
      </li>

      <li class="form-item">
        <mt-cell title="咨询人提交成果">
          <span @click="addlist('咨询人提交成果')">添加</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
        <p class="small">(注：未选择的服务内容将在文档中以“/”体现)</p>
        <div class="DynamicallyAdd dark" v-for="(item, index) in currentForm.submitResults" :key="index">
          <i class="iconfont iconshanchu" @click="removeSwipe(index, 'submitResults')"></i>
          <div class="form-item">
            <m-select v-model="item.serve" :data="{title: '服务范围',options: dataList.servicesrange}"
              unique="value" display="label" v-validate="'required'" data-vv-name="服务范围">
            </m-select>
          </div>
          <div class="form-item">
            <label for="">成果文件名称</label>
            <input type="text" placeholder="请输入成果文件名称" v-model="item.name" v-validate="'required'" data-vv-name="成果文件名称"/>
          </div>
          <div class="form-item">
            <label for="">成果文件组成</label>
            <input type="text" placeholder="请输入成果文件组成" v-model="item.constitute" v-validate="'required'" data-vv-name="成果文件组成"/>
          </div>
          <div class="form-item">
            <label for="">提交时间</label>
            <input type="text" placeholder="请输入提交时间" v-model="item.date" v-validate="'required'" data-vv-name="提交时间"/>
          </div>
          <div class="form-item">
            <label for="">份数</label>
            <input type="text" placeholder="请输入份数" v-model="item.copies" v-validate="'required'" data-vv-name="份数"/>
          </div>
          <div class="form-item">
            <label for="">质量标准</label>
            <input type="text" placeholder="请输入质量标准" v-model="item.standard" v-validate="'required'" data-vv-name="质量标准"/>
          </div>
        </div>
      </li>

      <li class="form-item">
        <mt-cell title="委托人提供资料">
          <span @click="addlist('委托人提供资料')">添加</span>
          <i class="iconfont">&#xe630;</i>
        </mt-cell>
        <p class="small">(注：未选择的服务内容将在文档中以“/”体现)</p>
        <div class="DynamicallyAdd dark" v-for="(item, index) in currentForm.provided" :key="index">
          <i class="iconfont iconshanchu" @click="removeSwipe(index, 'provided')"></i>
          <div class="form-item">
            <label for="">委托人名称</label>
            <input type="text" placeholder="请输入委托人名称" v-model="item.name" v-validate="'required'" data-vv-name="委托人名称"/>
          </div>
          <div class="form-item">
            <label for="">资料份数</label>
            <input type="text" placeholder="请输入资料份数" v-model="item.copies" v-validate="'required'" data-vv-name="资料份数"/>
          </div>
          <div class="form-item" @click="openPicker('offer')">
            <mt-cell title="提供时间">
              <span>{{ tool.dateFormat(item.time,'--') }}</span>
              <input type="text" v-model="currentForm.viabilityStart" v-validate="'required'" data-vv-name="提供时间" hidden/>
              <i class="iconfont">&#xe630;</i>
            </mt-cell>
          </div>
          <mt-datetime-picker ref="offer" type="date" v-model="pickerValue" @confirm="handleConfirm('time', index)"></mt-datetime-picker>
          <div class="form-item">
            <label>备注</label>
            <textarea placeholder="请输入备注" v-model="item.remark" v-validate="'required'" data-vv-name="备注"></textarea>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
  import dataList from './dataList';// 选项以及表单数据
  export default{
    props:['form','theFormDisabled'],
    data(){
      return{
        dataList:this.tool.deepCopy(dataList, this.tool.deepCopy),// 选项以及表单数据
        pickerValue: new Date(),// 日期控件时间
        showContent:'',
        currentForm:this.tool.deepCopy(this.form, this.tool.deepCopy),// 表单数据
      };
    },
    watch:{
      form:{
        handler(){
          let vm = this;
          vm.currentForm = this.tool.deepCopy(this.form, this.tool.deepCopy);// 表单数据
        },
        immediate: true
      }
    },
    methods:{
      /**
       * @method 获取日期
       */
      getDate(val){
        return val?new Date(val):new Date();
      },
      /**
       * 获取表单数据
       */
      getData(){
        let vm = this;
         const promise = new Promise(function(resolve, reject) {
           vm.$validator.validate().then((v)=>{
             if(v){
               let form = vm.tool.deepCopy(vm.currentForm, vm.tool.deepCopy)
               form.viabilityStart = getDate(form.viabilityStart);
               form.viabilityEnd = getDate(form.viabilityEnd);
               for (let i = 0, len = form.provided.length; i < len; i++) {
                 form.provided[i].time = getDate(form.provided[i].time);
               }
              resolve(vm.tool.deepCopy(form, vm.tool.deepCopy));
             }else{
                resolve(false);
               vm.Toast(vm.errors.all()[0]);
             }
           });
         });
         return promise;
      },
      /**
       * @method 动态删除
       * @param {Object} index 删除条目的索引值
       * @param {Object} type 对应的表单字段
       */
      removeSwipe(index, type) {
        /* 动态删除 */
        this.currentForm[type].splice(index, 1);
      },
      /**
       * @mehtod 服务阶段变更事件(用于清空服务阶段的工作内容)
       */
      servicesChange(val){
        let vm = this;
        if(/其他|工程实施阶段造价/g.test(val)){
          vm.showContent = '1';
        }else if(/资金使用计划/g.test(val)){
          vm.showContent = '2';
        }else{
          vm.showContent = '3';
        }
        vm.currentForm.jobContent = [];
      },
      /**
       * @method 打开日期选择插件
       * @param {Object} text 表单字段名称
       * @param {Object} index  如果是动态添加,则为索引值
       */
      openPicker(text, index) {
        if(this.theFormDisabled)return;
        if (text == 'offer') {
          this.$refs[text][0].open();
        } else {
          this.$refs[text].open();
        }
      },
      /**
       * @method 确认日期选择
       * @param {Object} text 表单字段名称
       * @param {Object} index  如果是动态添加,则为索引值
       */
      handleConfirm(text, index) {
        if (text == 'time') {
          this.currentForm.provided[index][text] = this.formatting(this.pickerValue);
        } else {
          this.currentForm[text] = this.formatting(this.pickerValue);
        }
      },
      /**
       * @method 动态添加
       * @param {Object} text
       */
      addlist(text) {
        if (text == '服务范围及工作内容、酬金') {
          this.currentForm.remuneration.push({
            serve: '请选择' /* 服务阶段 */,
            content: [] /* 工作内容 */,
            details: '' /* 工作内容 */,
            charge:{// 收费基数
              money:'',// 收费基数(金额)
              state:false// 收费基数(暂定)
            },
            standard:{// 收费标准
              money:'',// 收费标准(金额)
              state:false// 收费标准(暂定)
            },
            sum:{// 酬金金额
              money:'',// 酬金金额(金额)
              state:false// 酬金金额(暂定)
            },
            additional: '' /* 附加条款 */
          });
        } else if (text == '咨询人提交成果') {
          this.currentForm.submitResults.push({
            serve: '' /* 服务范围*/,
            name: '' /* 成果文件名称 */,
            constitute: '' /* 成果文件组成 */,
            date: '' /* 提交时间 */,
            copies: '' /* 份数 */,
            standard: '' /*, 质量标准 */
          });
        } else if (text == '委托人提供资料') {
          this.currentForm.provided.push({
            name: '' /* 委托人名称 */,
            copies: '' /*, 资料份数 */,
            time: '' /* 提供时间 */,
            remark: '' /* 备注 */
          });
        }
      }
    }
  }
</script>

<style>
</style>
