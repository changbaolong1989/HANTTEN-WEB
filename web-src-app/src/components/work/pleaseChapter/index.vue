<template>
  <!-- 请章记录-->
  <div class="work__please-chapter">
    <more-redact :listarr="listarr"></more-redact>
    <mt-navbar v-model="selected">
      <mt-tab-item id="table-please-record-chapter">请章记录</mt-tab-item>
      <mt-tab-item id="table-recorded-chapter">用章记录</mt-tab-item>
      <mt-tab-item id="table-chapter-review">用章审核</mt-tab-item>
    </mt-navbar>
    <component class="component-item" v-bind:is="selected"></component>
  </div>
</template>
<script>
  import tableChapterReview from './tableChapterReview'; /* 用章审核 */
  import tablePleaseRecordChapter from './tablePleaseRecordChapter'; /* 请章记录 */
  import tableRecordedChapter from './tableRecordedChapter'; /* 用章记录 */
  import moreRedact from '../../more/moreRedact.vue'; /* 更多操作 */
  export default {
    data() {
      return {
        selected: 'table-please-record-chapter',
        /* 部门标识 */
        listarr: [{
          name: '请章申请',
          link: '/app/pleaseApplyChapter'
        }]
      };
    },
    components: {
      'table-please-record-chapter': tablePleaseRecordChapter,
      'table-recorded-chapter': tableRecordedChapter,
      'table-chapter-review': tableChapterReview,
      'more-redact': moreRedact
    }
  };
</script>
<style lang="scss">
  @import url(../common/css/work-common.css);
  .work__please-chapter {
    .component-item {
      height: calc(100vh - 85px);
      overflow: auto;
      margin-top: 85px;
    }
  }
</style>
<style scoped lang="scss">
  .work__please-chapter {
    width: 100%;
  }
</style>
