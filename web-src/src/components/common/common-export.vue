<!--通用导出组件 @editor cbl-->
<!--
使用方法：
在父组件中import当前组件，父组件props:exportUrl
-->
<template>
  <div class="item-tab-chuang" style="float: right;" @click="exportBtn()" :exportUrl="exportUrl">
    <span class="el-icon-upload2 linear"></span>
    导出
  </div>
</template>

<script>
  export default {
    name: "common-export.vue",
    props: ['exportUrl'],
    methods: {
      exportBtn() {
        // 导出列表数据
        const elemIF = document.createElement('iframe');
        elemIF.src = this.exportUrl;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      }
    }
  }
</script>

<style scoped>

</style>
