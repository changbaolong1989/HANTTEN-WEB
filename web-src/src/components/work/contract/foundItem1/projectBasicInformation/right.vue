<template>
  <!-- 项目基本信息表-->
  <div class="m-work-contract-foundItem">
    <!-- <div class="given-container"></div> -->
    <div class="btn-group" v-if='transmittedData.isMainHeadFlag === "1" && transmittedData.isInvalidFlag !== "1"'>
     <el-button type="primary" style="margin-top: 10px;" @click="save">保存</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    components: {
    },
    data() {
      return {};
    },
    mounted() {
    },
    props: ['transmittedData'],
    methods: {
      /**
       * @method 保存
       */
      save(){
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {

          vm.bus.$emit('saveProjectBasicInformation');

        }).catch(() => {});
      }
    }
  };
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
  .m-work-contract-foundItem{
    height: calc(100% - 20px);
    display:flex;
    flex-direction:column;

    .given-container{
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

        &+.el-button {
          margin-left: 0;
          margin-top: 10px;
        }
      }
    }
  }
</style>
