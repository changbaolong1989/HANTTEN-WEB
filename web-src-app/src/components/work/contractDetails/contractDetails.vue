<template>
  <!-- 项目详情页面 -->
  <div class="contractDetails">
    <ul class="contractList">
      <li @click="routerLink">
        <span>全部项目-{{ message }}</span>
        <i class="iconfont">&#xe630;</i>
      </li>
    </ul>
    <div class="module">
      <p>工作内容</p>
      <div class="modulePart"><p>任务概况说明文字概况说明文字概况说明文字概况 说明文字概况说明文字概况说明文字概况说明文字 概况说明文字概况说明文字概况说明</p></div>
      <p>成果文件表单展示</p>
      <div class="modulePart template"></div>
    </div>
    <more :listarr="listarr"></more>
  </div>
</template>

<script>
import moreRedact from '../../more/moreRedact.vue';
export default {
  data() {
    return {
      message: 'HT 1000 BJ',
      listarr: [
        {
          name: '下载合同',
          link: ''
        },
        {
          name: '投标文件',
          link: ''
        },
        {
          name: '附属资料',
          link: ''
        },
        {
          name: '审核意见',
          link: '/app/auditOpinion'
        },
        {
          name: '意见文件',
          link: ''
        }
      ]
    };
  },
  components: {
    more: moreRedact
  },
  methods: {
    routerLink() {
      this.$router.push({ path: '/app/contractAside' });
    }
  }
};
</script>

<style lang="scss" scoped>
.contractDetails {
  background-color: #f1f5f8;
  .contractList {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    background-color: #fff;
    li {
      height: 76px;
      line-height: 76px;
      display: flex;
      padding: 0px 30px;
      justify-content: space-between;
      font-size: 28px;
      color: #333333;
    }
  }
  .module {
    margin: 0 30px;
    padding: 30px 0;
    & > p {
      font-size: 28px;
      line-height: 36px;
      color: #333333;
    }
    .modulePart {
      min-height: 226px;
      margin: 40px 0;
      padding: 30px;
      background-color: #fff;
      border-radius: 10px;
      font-size: 28px;
      line-height: 36px;
      color: #333333;
    }
    .template {
      min-height: 730px;
    }
  }
}
</style>
