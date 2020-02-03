<template>
  <div class="contractInformation">
    <!-- 合同信息 -->
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    data() {
      return {};
    }
  }
</script>

<style lang="scss" scoped>
  .contractInformation {
    height: calc(100vh - 110px);
    width: 100%;
  }
</style>
