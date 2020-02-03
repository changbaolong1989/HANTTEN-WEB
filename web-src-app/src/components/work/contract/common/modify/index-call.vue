<template>
  <!-- 招标部A6-->
  <div class="modify">
    <p class="Atitle">招标规划</p>
    <ul class="contentlist">
      <li>
        <span>项目名称</span>
        <span>{{ data.projectName }}</span>
      </li>
      <li>
        <span>提审时间</span>
        <span>{{ data.applyTimeBZ }}</span>
      </li>
    </ul>
    <ul v-for="(i, j) in data.unitProjectList" class="contentlist" :key="'list' + j">
      <li>
        <span>序号</span>
        <span>{{ i.sortNum }}</span>
      </li>
      <li>
        <span>工程名称</span>
        <span>{{ i.engineeringName }}</span>
      </li>
      <li></li>
      <li>
        <span>招标方式</span>
        <span>{{ i.tenderMethod }}</span>
      </li>
      <li></li>
      <li>
        <span>招标形式</span>
        <span>{{ i.tenderType }}</span>
      </li>
      <li>
        <span>招标平台</span>
        <span>{{ i.tenderPlatform }}</span>
      </li>
      <li>
        <span>投标人资格要求</span>
        <span>{{ i.investorRequire }}</span>
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
