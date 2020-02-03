<template>
  <!-- 项目详情 -->
  <div class="foundItem__project">
    <p style="padding: 10px;">项目编号:{{ currentTransmitData.data ? currentTransmitData.data.label : '' }}</p>
    <div class="nav">
      <mt-button @click.native.prevent="selected = 'task'" :class="{active:selected == 'task',linear:selected == 'task'}">任务台账</mt-button>
      <mt-button @click.native.prevent="selected = 'resultsFile'" :class="{active:selected == 'resultsFile',linear:selected == 'resultsFile'}">成果文件台账</mt-button>
    </div>
    <mt-tab-container v-model="selected">
      <mt-tab-container-item id="task"><task :transmitData="currentTransmitData"></task></mt-tab-container-item>
      <mt-tab-container-item id="resultsFile"><resultsFile :transmitData="currentTransmitData"></resultsFile></mt-tab-container-item>
    </mt-tab-container>
  </div>
</template>

<script>
import resultsFile from './resultsFile.vue';
import task from './task.vue';
export default {
  props: ['transmitData'],
  data() {
    return {
      selected: 'task',
      currentTransmitData: this.tool.deepCopy(this.transmitData, this.tool.deepCopy) // 传输数据(项目信息)
    };
  },
  watch: {
    transmitData: {
      handler() {
        let vm = this;
        vm.currentTransmitData = vm.tool.deepCopy(vm.transmitData, vm.tool.deepCopy);
      },
      immediate: true
    }
  },
  components: {
    resultsFile,
    task
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
.foundItem__project {
  overflow: auto;
  p {
    font-size: 28px;
  }
  .contentlist {
    background-color: white;
  }
  .nav{
    width: 94%;
    margin: 0 auto;
    button{
      font-size: 28px;
    }
  }
  .active{
    color: #fff;
    }
}
</style>
