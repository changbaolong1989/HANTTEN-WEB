<template>
  <!-- 项目基本信息表-->
  <div class="m-work-contract-foundItem">
    <!-- <div class="given-container"></div> -->
    <div class="primary" v-if="transmitData.hasContractApply === '0' && transmitData.isMainHeadFlag === '1'">
      <button class="linear" style="margin-top: 10px;" @click="save">保存</button>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {};
  },
  mounted() {},
  props: ['transmitData'],
  watch: {
    // 节点信息
    transmitData: {
      handler(newval) {
        let vm = this;
        vm.transmitData =newval;
        console.log("节点信息",vm.transmitData)
      },
      immediate: true
    }
  },
  methods: {
    /**
     * @method 保存
     */
    save() {
      let vm = this;
      this.MessageBox.confirm('', {
        message: '确定执行保存操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(() => {
          vm.bus.$emit('saveProjectBasicInformation1');
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss"></style>
<style lang="scss" scoped>
.m-work-contract-foundItem {
  height: calc(100% - 20px);
  display: flex;
  flex-direction: column;

  .given-container {
    flex-grow: 100;
    overflow: auto;
  }

  .btn-group {
    //按钮组
    width: 80%;
    bottom: 20px;
    margin-left: 10%;
    display: flex;
    flex-direction: column;
    flex-grow: 1;

    .el-button {
      width: 100%;

      & + .el-button {
        margin-left: 0;
        margin-top: 10px;
      }
    }
  }
}
</style>
