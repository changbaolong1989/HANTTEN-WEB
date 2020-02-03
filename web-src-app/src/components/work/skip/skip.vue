<template>
  <!-- 跳级审核-->
  <div class="skip">
    <form><mt-switch v-model="value">是否跳级审核</mt-switch></form>
    <p>当质控部人员请假时，可选择跳级审核，跳过质控部，直接进入张总审核</p>
    <div class="commonFooter"><mt-button class="linear" style="width: 90%;">确定</mt-button></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: false
    };
  }
};
</script>
<style lang="scss">
.skip {
  .mint-switch-core::before {
    background-color: #e5e5e5;
  }
  .mint-switch-label{
    color: #333;
  }
}
</style>
<style scoped lang="scss">
.skip {
  width: 100%;
  height: 100vh;
  background-color: #f1f5f8;
  overflow: hidden;
  form {
    width: 92%;
    background-color: #fff;
    margin: 30px auto;
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    border-radius: 10px;
    label {
      padding: 30px;
      flex-direction: row-reverse;
      justify-content: space-between;
      font-size: 28px;
    }
  }
  p {
    width: 90%;
    margin: 30px auto;
    font-size: 24px;
    line-height: 48px;
    color: #999999;
  }
}
</style>
