<template>
  <!--工时列表备注内容-->
  <div class="workingRemark">
    <form>
      <label for="area">工时备注</label>
      <textarea name="" id="area" placeholder="请输入工时备注"></textarea>
    </form>
    <div class="commonFooter">
      <mt-button>取消</mt-button>
      <mt-button class="linear">确定</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  }
};
</script>

<style scoped lang="scss">
.workingRemark {
  width: 100%;
  padding-bottom: 110px;
  form {
    label {
      display: block;
      font-size: 28px;
      color: #333333;
      height: 80px;
      line-height: 80px;
      padding-left: 20px;
      box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    }
    textarea {
      width: 90%;
      margin: 20px auto;
      display: block;
      border: 2px solid #ccc;
      padding: 10px;
      min-height: 300px;
      border-radius: 10px;
      background-color: #f8f9fb;
      font-size: 28px;
      color: #666;
    }
  }
}
</style>
