<template>
  <div>
    <div class="item-tab-chuang" @click="isShowAddDialog = true">
      <span class="el-icon-plus linear"></span>新增
    </div>
    <pro-cfg-list-add :isShowAddDialog.sync="isShowAddDialog" @showDialog="showDialog"></pro-cfg-list-add>
  </div>
</template>

<script>

  import ProCfgListAdd from './pro-cfg-list-add'

  export default {
    name: "pro-cfg-list-btn-add",
    components: {ProCfgListAdd},
    data() {
      return {
        isShowAddDialog: false, //弹窗显示
      }
    },
    methods: {
      showDialog() {
        this.isShowAddDialog = false
      }
    }
  }
</script>

<style scoped>
  .item-tab-chuang {
    text-align: right;
  }
</style>
