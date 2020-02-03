<template>
  <!-- 表格-招标规划 -->
    <table class="align-center matterForm">
      <thead>
        <tr style="border-bottom: 10px solid #edf1f4;">
          <td colspan="2">
            <div class="cell">项目名称</div>
          </td>
           <td colspan="2">
            <div class="cell">{{data.projectName}}</div>
          </td>
           <td colspan="2">
            <div class="cell">{{data.applyTimeBZ}}</div>
          </td>
        </tr>
        <tr>
          <th>
            <div class="cell">序号</div>
          </th>
          <th>
            <div class="cell">工程名称</div>
          </th>
          <th>
            <div class="cell">招标方式</div>
          </th>
          <th>
            <div class="cell">招标形式</div>
          </th>
          <th>
            <div class="cell">招标平台</div>
          </th>
          <th>
            <div class="cell">投标人资格要求</div>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,i) in data.unitProjectList" :key="i">
          <td>
            <div class="cell">{{item.sortNum}}</div>
          </td>
          <td>
            <div class="cell">{{item.engineeringName}}</div>
          </td>
          <td>
            <div class="cell">{{item.tenderMethod}}</div>
          </td>
          <td>
            <div class="cell">{{item.tenderType}}</div>
          </td>
           <td>
            <div class="cell">{{item.tenderPlatform}}</div>
          </td>
           <td>
            <div class="cell">{{item.investorRequire}}</div>
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
