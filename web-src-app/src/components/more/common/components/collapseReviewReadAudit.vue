<template>
    <!-- 面板-复核只读 -->
    <el-collapse-item class="collapseReviewRead" :name="name">
      <template slot="title">
        <span>{{ countObject.title}}</span>
      </template>
      <el-collapse accordion class="level2">
        <el-collapse-item :name="name+'-' + j" v-for="(li2, j) in countObject.children" :key="j">
          <template slot="title">
            <span class="name" :title="li2.name">{{ li2.name }}
            </span>
            <span class="time">{{ li2.time }}</span>
            <span class="pass">{{1==li2.isPass?'通过':'未通过'}}</span>
          </template>
          <div class="content">
            <div class="opinion">{{ li2.opinion }}</div>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-collapse-item>
</template>

<script>
  export default {
    props: ['object', 'name'],
    data() {
      return {
        countObject: this.getCount(this.object) // 当前审核对象
      };
    },
    watch: {
      object: {
        handler() {
          let vm = this;
          vm.getCount(this.object)
        },
        deep: true,
        immediate: true
      }
    },
    methods: {
      getCount(object) {
        let vm = this;
        vm.countObject = object;
        for (let i = 0, len = vm.countObject.children.length; i < len; i++) {
          let children = vm.countObject.children[i];
        }
        console.log("countObject",vm.countObject)
      }
    }
  };
</script>
<style lang="scss">
 .collapseReviewRead{
    *{
      box-sizing: border-box;
    }
  }
</style>
<style lang="scss" scoped>
</style>
