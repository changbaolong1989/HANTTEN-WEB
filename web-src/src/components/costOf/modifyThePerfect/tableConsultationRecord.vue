<template>
  <!-- 表格-咨询工作记录 -->
    <table class="align-center matterForm">
      <thead>
        <tr style="border-bottom: 10px solid #edf1f4;">
          <td>
            <div class="cell">项目名称</div>
          </td>
           <td>
            <div class="cell">{{data.projectName}}</div>
          </td>
           <td>
            <div class="cell">咨询合同编号</div>
          </td>
          <td>
            <div class="cell">{{data.contractNum}}</div>
          </td>
        </tr>
        <tr>
          <th>
            <div class="cell">序号</div>
          </th>
          <th>
            <div class="cell">咨询意见</div>
          </th>
          <th colspan="2">
            <div class="cell">承包人回复意见</div>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,i) in data.unitProjectList" :key="i">
          <td>
            <div class="cell">{{item.sortNum}}</div>
          </td>
          <td>
            <div class="cell">{{item.counselAdvise}}</div>
          </td>
          <td colspan="2">
            <div class="cell">{{item.responseAdvise}}</div>
          </td>
        </tr>
      </tbody>
    </table>
</template>

<script>
  export default {
    props:['data'],
    data() {
      return {
      }
    },
    methods: {
    }
  }
</script>

<style lang="scss"></style>
<style lang="scss" scoped>
 @import url('../common/css/tableScoped.css');
</style>
