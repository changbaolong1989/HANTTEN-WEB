<template>
  <!-- 面板-复核可写 -->
  <el-collapse-item class="collapseReviewWrite" :name="name">
    <template slot="title">
      <span>{{ countObject.title }}</span>
    </template>
    <div class="width-94">
      <!-- <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="countObject.opinion" @change="submit">
      </el-input> -->
    </div>
    <div class="aa">
      <el-collapse accordion>
        <collapse-review-read :object="countObject.ManagerVerify" v-if="countObject.ManagerVerify"></collapse-review-read>
        <collapse-review-read :object="countObject.QualityVerify" v-if="countObject.QualityVerify"></collapse-review-read>
        <collapse-review-read :object="countObject.GenerManagerVerify" v-if="countObject.GenerManagerVerify"></collapse-review-read>
      </el-collapse>
    </div>
  </el-collapse-item>
</template>

<script>
  import Vue from 'vue'
  /* 面板--复核可写 选择错误弹窗 */
  import collapseReviewRead from '../../common/components/collapseReviewRead.vue';

  export default {
    components: {
      'collapse-review-read': collapseReviewRead,
    },
    props: ['object', 'name', 'errorTyleList'],
    mounted() {

    },
    data() {
      return {
        count: 0,
        countObject: Object.assign({}, this.object), // 当前审核对象
        countId: '', // 当前被修改被审核人标识
        title: '', //对话框标题
      };
    },
    watch: {
      object: {
        handler() {
          let vm = this;
          vm.countObject = Object.assign({}, vm.object);
        },
        deep: true,
         immediate: true
      }

    },
    methods: {
      submit(data) { // 弹窗表单提交事件
        let vm = this;
        vm.$emit('submit', Object.assign({}, vm.countObject));
      }
    }
  };
</script>
<style lang="scss"></style>
<style lang="scss" scoped>
  .aa {
    width: 94%;
    margin: auto;
    margin-top: 10px;
    border: 1px solid #DCDFE6;
    border-radius: 5px !important;

    .el-collapse-item__header {
      border: 1px;
    }
  }
</style>
