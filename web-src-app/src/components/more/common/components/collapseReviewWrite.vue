<template>
  <!-- 面板-复核可写 -->
  <el-collapse-item class="collapseReviewWrite" :name="name">
    <template slot="title">
      <span>审核</span>
    </template>
    <mt-field type="textarea" rows="3" placeholder="驳回请输入驳回意见，通过不填默认为同意" v-model="countObject.opinion"></mt-field>
    扣分
    <el-collapse accordion v-model="activeNames" class="level2">
      <el-collapse-item :name="name + '-' + j" v-for="(item, j) in countObject.children" :key="j">
        <template slot="title">
          <span class="name">{{ item.name}}</span>
          <span class="badge error" v-if="item.count">{{ item.count }}</span>
          <span class="time" style="position: absolute;top: 0;right: 35px;"><a @click.stop="chooseError(item)">请选择错误</a></span>
        </template>
        <div class="content">
          <ul>
            <li v-for="(li3,m) in item.errorList" :key="m">
              <div v-if="li3.questionNum"><span class="badge error">{{ li3.questionNum}}</span> {{ li3.questionName }}</div>
            </li>
          </ul>
        </div>
      </el-collapse-item>
    </el-collapse>
  </el-collapse-item>
</template>

<script>
  import Vue from 'vue'
  export default {
    components: {

    },
    props: ['object', 'name'],
    mounted() {
      let vm = this;
      vm.bus.$off('changeWriteOption');
      vm.bus.$on('changeWriteOption',function(val){
        vm.countObject.opinion = val;
      });
    },
    data() {
      return {
        activeNames:this.name+'-0',
        count: 0,
        countObject:this.object,
        countId: '', // 当前被修改被审核人标识
        zindex1: true, // ie兼容调整
        zindex2: false, // ie兼容调整
        title: '', //对话框标题
      };
    },
    watch: {
      object: {
        handler(newval) {
          let vm = this;
          vm.countObject = vm.object;
          for (let i = 0, len = vm.countObject.children.length; i < len; i++) {
            let children = vm.countObject.children[i];
            let count = 0;
            for (let j = 0, len2 = children.errorList.length; j < len2; j++) {
              count += children.errorList[j].questionNum;
            }
            children.count = count;
          }
        },
        deep: true,
        immediate: true
      }
    },
    methods: {
      /* 选择错误*/
      chooseError(item) {
        let vm = this;
        // 触发选择错误事件
        vm.$emit('selecError', item);
      }
    }
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
