<template>
  <div class="workplan">
    <p class="Atitle">咨询工作计划</p>
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ tableData.projectName }}</span>
      </li>
      <li>
        <span>编制人</span>
        <span>{{ tableData.operatorName }}</span>
      </li>
      <li>
        <span>编制时间</span>
        <span>{{ tableData.applyTimeBZ }}</span>
      </li>
      <li>
        <span>咨询业务简述</span>
        <span>{{ tableData.counselDesc}}</span>
      </li>
      <li>
        <span>建设规模</span>
        <span>{{ tableData.constructScale }}</span>
      </li>
      <li>
        <span>投资额（万元）</span>
        <span>{{ tableData.investedAmount }}</span>
      </li>
      <li>
        <span>咨询内容与重点</span>
        <span>{{ tableData.counselContent }}</span>
      </li>
      <li>
        <span>咨询业务小组组长</span>
        <span>{{ tableData.counselGroup }}</span>
      </li>
      <li>
        <span>咨询业务小组成员</span>
        <span>{{ tableData.memberList }}</span>
      </li>
      <li>
        <span>专业负责人</span>
        <span>{{ tableData.counselDuty }}</span>
      </li>
      <li>
        <span>咨询业务小组分工</span>
        <span>{{ tableData.divideWork }}</span>
      </li>
      <li>
        <span>项目负责人审核</span>
        <span></span>
      </li>
      <li>
        <span>公司负责人审定</span>
        <span></span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
        tableData: {
          counselDesc: '',//咨询业务简述
          constructScale: '',//建设规模
          counselContent: '',//咨询内容与重点
          counselGroup: '',//组长
          memberList:'',//成员
          counselDuty: '',//专业负责人
          divideWork: '',//咨询业务小组分工
          operatorName: '',//编制人
          applyTimeBZ: '',//编制时间
          investedAmount: '',//投资额
        },
    };
  },
  props: ['projectName', 'transmittedData'],
  mounted() {
    let vm = this;
    // 查询拟定工作计划信息
    this.getTaskWorkPlan();
  },
  methods:{
    //查询拟定工作计划信息
    getTaskWorkPlan(){
      const vm = this;
      this.requestPost('/taskExecute/query/searchA5Info',{
        "relationId" : this.transmittedData.relationId
      }).then(res => {
        // 如果返回成功，则提示成功信息，否则提示失败信息
        if (res.data.status == "success") {
          let vm = this;
          // 拷贝返回结果到页面
          vm.tableData = Object.assign({},res.data.dataVO);
        } else {
          this.Toast(res.data.message);
        }
      }).catch(e => {
        this.Toast(e);
      })
    }
  }
};
</script>
