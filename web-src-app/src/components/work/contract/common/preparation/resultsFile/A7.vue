<template>
  <!-- A7-->
  <div class="A7">
    <p class="Atitle">工程造价指标分析表</p>
    <ul class="contentlist">
      <li>
        <span>咨询合同编号</span>
        <span>{{ form.contractNum }}</span>
      </li>
      <li>
        <span>项目名称</span>
        <span>{{ form.projectName }}</span>
      </li>
      <li>
        <span>工程类别</span>
        <span>{{ form.engineeingTypeName }}</span>
      </li>
      <li>
        <span>合同价格</span>
        <span>{{ form.contractAmount }}</span>
      </li>
      <li>
        <span>结算价格</span>
        <span>{{ form.settlePrice }}</span>
      </li>
      <li>
        <span>结算价超出合同价的原因（若有）</span>
        <span>{{ form.settleReason }}</span>
      </li>
    </ul>
    <p class="Atitle">单位工程造价指标</p>
    <ul v-for="(item, index) in data" class="contentlist" :key="'list' + index">
      <li>
        <span>序号</span>
        <span>{{ item.sortNum }}</span>
      </li>
      <li>
        <span>单位工程名称</span>
        <span>{{ item.unitName }}</span>
      </li>
      <li>
        <span>建设规模(m²/m)</span>
        <span>{{ item.constructScale }}</span>
      </li>
      <li>
        <span>造价(元)</span>
        <span>{{ item.constructCost }}</span>
      </li>
      <li>
        <span>单位造价(元/m²)</span>
        <span>{{ item.unitCost }}</span>
      </li>
      <li>
        <span>造价比例（%）</span>
        <span>{{ item.costRatio }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: {
        relationId: '',
        engineeingTypeArr: [],
        contractAmount: '',
        settlePrice: '',
        settleReason: '',
        unitProjectList: []
      },
      form: {
        contractNum:"",
        projectName:"",
        engineeingTypeName:"",
        contractAmount:"",
        settlePrice:"",
        settleReason:"",
        contractAmount:"",
      }
    };
  },
  props: ['transmittedData'],
  mounted() {
    this.searchList();
  },
  methods: {
    searchList() {
      let vm = this;
      vm.requestPost('/taskExecute/query/searchA7ZJInfo', {
        relationId: vm.transmittedData.relationId,
        taskId: vm.transmittedData.taskId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            if(res.data.dataVO){
              vm.form = {
                contractNum:res.data.dataVO.contractNum,
                projectName:res.data.dataVO.projectName,
                engineeingTypeName:res.data.dataVO.engineeingTypeName,
                contractAmount:res.data.dataVO.contractAmount,
                settlePrice:res.data.dataVO.settlePrice,
                settleReason:res.data.dataVO.settleReason,
                contractAmount:res.data.dataVO.contractAmount,
              }
              vm.data = res.data.dataVO.unitProjectList;
            }

          }
        })
        .catch(e => {
          this.Toast(e);
        });
    }
  }
};
</script>

<style lang="scss">
.A7 {
  width: 100%;
  .contentlist {
    background-color: #fff !important;
  }
}
</style>
