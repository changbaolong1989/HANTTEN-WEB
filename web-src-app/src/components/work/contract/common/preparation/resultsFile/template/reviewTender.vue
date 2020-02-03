<template>
  <!-- 合同审核意见（未招标）模板-->
  <div class="reviewTender">
    <form class="projectForm">
      <div class="block">
        <div class="form-item">
          <label>档案编号 <i class="iconfont iconhelp" @click.stop="help('HT1134BJ-ZJ-01第一个是合同编号，中间是部门编号 造价部ZJ招标代理部ZB 市场部SC ，最后是流水号')"></i> </label>
          <input type="text" placeholder="请输入档案编号" v-model="templateform.input1" v-validate="'required'" name="input1" data-vv-as="档案编号"/>
        </div>
        <div class="form-item">
          <label>公司名称</label>
          <input type="text" placeholder="请输入公司名称" v-model="templateform.input105" v-validate="'required'" name="input105" data-vv-as="公司名称"/>
        </div>
        <div class="form-item">
          <label>项目名称</label>
          <input type="text" placeholder="请输入项目名称" v-model="templateform.input2" v-validate="'required'" name="input2" data-vv-as="项目名称"/>
        </div>
      </div>
      <div class="block">
        <p>审核意见</p>
        <div class="form-item">
          <label>审核信息 <i class="iconfont iconhelp" @click.stop="help('我们接受贵公司委托，对×××工程进行全过程跟踪造价咨询工作。现对本工程施工合同进行审核。标红处为输入框')"></i>  </label>
          <input type="text" placeholder="请输入审核信息 " v-model="templateform.input3" v-validate="'required'" name="input3" data-vv-as="审核信息"/>
        </div>
        <p>审核范围</p>
        <div class="form-item">
          <label>建设单位</label>
          <input type="text" placeholder="请输入建设单位" v-model="templateform.input4" v-validate="'required'" name="input4" data-vv-as="建设单位"/>
        </div>
        <div class="form-item">
          <label>施工单位</label>
          <input type="text" placeholder="请输入施工单位" v-model="templateform.input5" v-validate="'required'" name="input5" data-vv-as="施工单位"/>
        </div>
        <div class="form-item">
          <label>合同</label>
          <input type="text" placeholder="请输入合同" v-model="templateform.input6" v-validate="'required'" name="input6" data-vv-as="合同" />
        </div>
        <p>审核意见</p>
        <div class="form-item">
          <label>资质条件</label>
          <textarea name="" id="area" placeholder="请输入资质条件" v-model="templateform.input7" v-validate="'required'" name="input7" data-vv-as="资质条件"></textarea>
        </div>
        <div class="form-item zanding">
          <label for="">合同金额 <i class="iconfont iconhelp" @click.stop="help('金额单位为元，金额千分符，小数点后两位')"></i> </label>
          <div class="zandiv">
            <input type="text" placeholder="请输入合同金额" v-model="templateform.input8" v-validate="'required'" name="input8" data-vv-as="合同金额" />
            <span>元</span>
          </div>
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
        input105: '',
      },
    };
  },
  methods:{
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
