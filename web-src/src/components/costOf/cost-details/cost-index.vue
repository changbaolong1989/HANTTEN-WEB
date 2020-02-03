<template>
  <!-- 造价部 A1-A9 -->
  <div class="cost-index">
    <!-- 上边默认显示工作内容-->
    <div class="matterForm">
      <el-form :label-position="top" label-width="80px" :model="topForm">
        <el-form-item label="工作内容说明">
          <el-input type="textarea" :rows="5" placeholder="工作内容说明" v-model="topForm.textarea" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs v-model="active" type="card" class="tabs">
      <el-tab-pane label="A1" name="/page/report/details" :lazy="true">
        <costA1></costA1>
      </el-tab-pane>
      <el-tab-pane label="A9" name="/page/report/details/a9" :lazy="true">
        <costA9></costA9>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import costA1 from './cost-A1'
  import costA9 from './cost-A9'
  export default {
    data() {
      return {
        top: "top",
        topForm: {
          textarea: "我是工作内容说明我是工作内容说明我是工作内容说明",
        },
        active: this.$route.fullPath
      }
    },
    components: {
      "costA1": costA1,
      "costA9": costA9
    },
    methods: {}
  };
</script>
<style lang="scss">
  .cost-index {
    .matterForm {
      border-radius: 5px;
      padding: 10px;
      background-color: #edf1f4;
      margin-bottom: 10px;
    }

    .tabs {
      .el-tabs__header {
        margin-bottom: 0;
        .el-tabs__nav{
          border: none;
           .el-tabs__item{
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
            padding: 0 40px;
            border: none;
          }
          .is-active{
            background-color: #edf1f4;
          }
        }
      }
    }
  }
</style>
<style lang="scss" scoped>
  .cost-index {
    border-radius: 5px;
    /* #EBEEF5 */
  }
</style>
