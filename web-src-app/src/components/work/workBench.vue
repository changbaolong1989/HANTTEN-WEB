<template>
  <!-- 工作台主视图-->
  <div class="work__work-bench">
    <more-redact :listarr="listarr"></more-redact>
    <mt-navbar v-model="selected">
      <mt-tab-item id="table-backlog">待办<span v-if="backlogLabel">({{backlogLabel}})</span></mt-tab-item>
      <mt-tab-item id="table-finished">已办<span v-if="backlogLabel">({{finishedLabel}})</span></mt-tab-item>
      <mt-tab-item id="table-handle">经手<span v-if="backlogLabel">({{handleLabel}})</span></mt-tab-item>
    </mt-navbar>
     <component v-bind:is="selected"></component>
  </div>
</template>

<script>
import tableBacklog from './tableBacklog.vue';/* 待办 */
import tableFinished from './tableFinished.vue';/* 已办 */
import tableHandle from './tableHandle.vue';/* 经手 */
import moreRedact from '../more/moreRedact.vue';
export default {
  components: {
    'table-backlog': tableBacklog,/* 待办 */
    'table-finished': tableFinished,/* 已办 */
    'table-handle': tableHandle,/* 经手 */
    'more-redact': moreRedact
  },
  data() {
    return {
      selected: 'table-backlog',
      listarr: [
        {
          name: '请章审批',
          link: '/app/pleaseChapter'
        },
        {
          name: '我的工时',
          link: '/app/manHour'
        },
        {
          name: '工时列表',
          link: '/app/workingHours'
        }
      ],
      backlogLabel:0,// 待办label
      finishedLabel:0,// 已办label
      handleLabel:0,// 经手label
    };
  },
  methods:{
    changeLabel(obj){// 更改
      let vm = this;
      vm[obj.name] = obj.count;
    }
  }
};
</script>
<style>
  @import url(./common/css/work-common.css);
</style>
<style scoped lang="scss">
.work__work-bench {
  width:100%;
  padding-top:150px;
  padding-bottom: 110px;

  .mint-tab-container {
    margin-top: 150px;
    width:100%;
  }
}
</style>
