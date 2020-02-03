<template>
  <div class="cost-A4 matterForm">
    <el-row>
      <el-col :span="18">
        <div>
          <slot></slot>
        </div>
      </el-col>
    </el-row>

    <table class="align-center">
      <tbody>
        <tr>
          <td>
            <div class="cell">接收单位：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">成果文件名称：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
           <td>
            <div class="cell">份数：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">项目相关资料：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">送件人：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">收件人：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">签收日期：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">备注：</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
      }
    }
  }
</script>

<style lang="scss">
</style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');

  .item-tab-chuang {
    margin: 0;
  }
</style>
