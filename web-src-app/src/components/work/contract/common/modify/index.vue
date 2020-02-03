<template>
  <div class="modify">
    <p class="Atitle">招标规划</p>
    <ul v-for="(item, index) in contentList" class="contentlist" :key="index">
      <li>
        <span>项目名称</span>
        <span>{{ item.name }}</span>
      </li>
      <li>
        <span>咨询合同编号</span>
        <span>{{ item.contractNum  }}</span>
      </li>
    </ul>
    <ul v-for="(i, j) in contentList1" class="contentlist" :key="'list'+j">
      <li>
        <span>序号</span>
        <span>{{ i.number }}</span>
      </li>
      <li>
        <span>咨询意见</span>
        <span>{{ i.advisory }}</span>
      </li>
      <li>
        <span>承包人回复意见</span>
        <span>{{ i.idea }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      contentList: [
        {
          name: '',
          contractNum: '',
        }
      ],
      contentList1: [
        {
          number: '1',
          advisory: '文件1',
          idea: '1份',
        },
        {
          number: '1',
          advisory: '文件1',
          idea: '1份',
        },
      ]
    };
  }
};
</script>
