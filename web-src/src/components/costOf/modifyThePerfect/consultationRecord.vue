<template>
  <!-- 表格-咨询工作记录-->
    <table class="align-center matterForm">
      <thead>
        <tr style="border-bottom: 10px solid #edf1f4;">
          <td colspan="2">
            <div class="cell">合同编号</div>
          </td>
           <td colspan="2">
            <div class="cell"></div>
          </td>
           <td colspan="2">
            <div class="cell"></div>
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
        <tr v-for="(item,i) in data" :key="i">
          <td>
            <div class="cell">{{i}}</div>
          </td>
          <td>
            <div class="cell">{{item.name+i}}</div>
          </td>
          <td>
            <div class="cell">{{item.num+i}}份</div>
          </td>
          <td>
            <div class="cell">{{item.note+i}}</div>
          </td>
           <td>
            <div class="cell">{{item.note+i}}</div>
          </td>
           <td>
            <div class="cell">{{item.note+i}}</div>
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
