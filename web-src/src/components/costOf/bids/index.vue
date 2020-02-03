<template>
  <!-- 招标部 A8-->
  <div class="cost-index">
    <!-- 上边默认显示工作内容-->
    <div class="matterForm">
      <el-form :label-position="top" label-width="80px" :model="topForm">
        <el-form-item label="工作内容说明">
          <el-input type="textarea" :rows="5" placeholder="工作内容说明" v-model="topForm.textarea" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <costa8>
      <p>咨询工作记录</p>
    </costa8>
  </div>
</template>
<script>
  import costA8 from './cost-A8'
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
      "costa8": costA8
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
