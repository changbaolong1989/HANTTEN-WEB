<template>
  <div class="item">
    <el-tabs v-model="active" type="card">
      <el-tab-pane label="合同项目" name="/page/item" :lazy="true" v-if="!!userPowerInfo.projectList">
        <div class="item-tab-bor linear"></div>
        <item-table ref="itemTable"></item-table>
      </el-tab-pane>
      <el-tab-pane label="市场项目" name="/page/item/bazaarItemList" :lazy="true" v-if="!!userPowerInfo.tenderTaskList">
        <div class="item-tab-bor linear"></div>
        <bazaar-item params="593df92d-fb21-442a-b279-a8b821bbc0d4"></bazaar-item>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import itemTable from './m-item-table.vue'
  import bazaarItemList from '../bazaar/item/m-bazaar-itemList'
  export default {
    data() {
      return {
        active: this.SStorage.getItem("userPowerInfo").projectList?'/page/item': '/page/item/bazaarItemList',
        userPowerInfo:this.SStorage.getItem("userPowerInfo"),
      }
    },
    components: {
      "item-table": itemTable,
      "bazaar-item":bazaarItemList
    },
    methods: {
    },
  }
</script>

<style lang="scss">
  .item {

    /* 标签页 */
    .el-tabs--card {
      &>.el-tabs__header {
        border-bottom: none;
        margin: 0;

        .el-tabs__nav-scroll {
          padding: 20px 20px 0;
          // box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.4) inset;

          .el-tabs__nav {
            border: 1px;

            .el-tabs__item {
              border-radius: 10px 10px 0 0;
              border: none;
              box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
              background-color: white;

              &.is-active {
                background-color: #0aa3ef;
                color: white;
              }
            }
          }
        }
      }
    }
  }
</style>
<style lang="scss" scoped>
  .item {
    padding-bottom: 25px;
    flex-direction: column;
    display: flex;
    margin-left: 0px;
    position: relative;

    /* 请章按钮样式 */
    .btn-please-chapter {
      width: 83px;
      height: 26px;
      line-height: 26px;
      padding: 0;
      margin: 0;
      position: absolute;
      top: 77px;
      right: 36px;
      color: #0099e8;
      border: 1px solid #0099e8;
      background-color: white;
      font-size: 12px;
      border-radius: 5px;
      z-index: 1;

      i {
        font-size: 14px;
      }
    }
  }
</style>
