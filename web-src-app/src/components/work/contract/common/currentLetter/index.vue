<template>
  <div class="currentLetter">
    <p class="Atitle">成果文件提交签收单</p>
    <ul v-for="(item, index) in contentList" class="contentlist" :key="index">
      <li>
        <span>接收单位</span>
        <span>{{ item.contractNum }}</span>
      </li>
      <li>
        <span>成果文件名称</span>
        <span>{{ item.name }}</span>
      </li>
      <li>
        <span>份数</span>
        <span>{{ item.category }}</span>
      </li>
      <li>
        <span>项目相关资料</span>
        <span>{{ item.client }}</span>
      </li>
      <li>
        <span>送件人</span>
        <span>{{ item.transaction }}</span>
      </li>
      <li>
        <span>收件人</span>
        <span>{{ item.sign }}</span>
      </li>
      <li>
        <span>签收日期</span>
        <span>{{ item.transaction1 }}</span>
      </li>
      <li>
        <span>备注</span>
        <span>{{ item.remark }}</span>
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
          contractNum: '',
          name: '',
          category: '',
          client: '',
          transaction: '',
          sign: '',
          transaction1: '',
          remark:""
        }
      ]
    };
  }
};
</script>
