<template>
  <!-- 面板-复核可写 -->
  <el-collapse-item class="collapseReviewWrite" :name="name">
    <template slot="title">
      <span>{{ countObject.title }}</span>
    </template>
    <mt-field type="textarea" rows="3" placeholder="请输入内容" v-model="countObject.opinion"></mt-field>
  </el-collapse-item>
</template>

<script>
  import Vue from 'vue'
  export default {
    props: ['object','name'],
    mounted() {

    },
    data() {
      return {
        count: 0,
        countObject:this.object,
        countId: '', // 当前被修改被审核人标识
        zindex1: true, // ie兼容调整
        zindex2: false, // ie兼容调整
        title: '', //对话框标题
      }
    },
    watch: {
      object: {
        handler(newval) {
          let vm = this;
          vm.countObject = vm.object;
          for (let i = 0, len = vm.countObject.children.length; i < len; i++) {
            let children = vm.countObject.children[i];
          }
        },
        deep: true,
        immediate: true
      }
    },
  };
</script>
<style lang="scss">
  .collapseReviewWrite {
    * {
      box-sizing: border-box;
    }
  }
</style>
<style lang="scss" scoped>
</style>
