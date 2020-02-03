<template>
  <!-- 造价部A6-->
  <div class="modify">
    <p class="Atitle">咨询工作记录</p>
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ data.projectName }}</span>
      </li>
      <li>
        <span>咨询合同编号</span>
        <span>{{ data.contractNum}}</span>
      </li>
    </ul>
    <ul v-for="(i, j) in data.unitProjectList" class="contentlist" :key="'list'+j">
      <li>
        <span>序号</span>
        <span>{{ i.sortNum }}</span>
      </li>
      <li>
        <span>咨询意见</span>
        <span>{{ i.counselAdvise }}</span>
      </li>
      <li>
        <span>承包人回复意见</span>
        <span>{{ i.responseAdvise }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data:{},
      currentTransmittedData:this.tool.deepCopy(this.transmittedData,this.tool.deepCopy)
    };
  },
  props:['transmittedData'],
  watch:{
      transmittedData:{
        handler(){
          let vm = this;
          vm.currentTransmittedData = vm.tool.deepCopy(vm.transmittedData,vm.tool.deepCopy);
        },
        immediate: true
      }
  },
  mounted() {
    this.searchList();
  },
  methods: {
    searchList() {
      this.requestPost('/taskExecute/query/searchA6Info', {
        relationId: this.transmittedData.relationId
      })
        .then(res => {
          // 如果返回成功，则绘制数据，否则提示失败信息
          if (res.data.status == 'success') {
            let vm = this;
            vm.data = res.data.dataVO;
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
.modify {
  .contentlist {
    background-color: #fff !important;
  }
}
</style>
