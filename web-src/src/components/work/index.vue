<template>
  <div class="m-work">
    <!-- 工作台 -->
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    data() {
      return {};
    }
  };
</script>
<style lang="scss">
  .m-work {
    .el-form-item__label{
      width: 100%;
      text-align: left;
    }
    .btn-group{
      p:first-child,.el-form:first-child{
        margin-top: 10px;
      }
      .el-button{
        min-height: 30px;
        &:last-child{
          margin-bottom: 10px;
        }
      }
    }
  }
</style>
<style lang="scss" scoped></style>
