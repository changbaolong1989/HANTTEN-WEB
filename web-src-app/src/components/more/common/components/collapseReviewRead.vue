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
            <div v-for="(li3, k) in li2.persions" :key="k">
              <ul>
                <li><span class="name">{{ li3.name }}</span><span class="badge error" style="margin-left: 5px;" v-if="li3.count">{{ li3.count }}</span></li>
                <li v-for="(li4, m) in li3.errorList" :key="m">
                  <span class="badge error">{{ li4.num }}</span>
                  {{ li4.type }}
                </li>
              </ul>
              <div class="decoration" v-if="li2.persions[k + 1]">
                <div class="line"></div>
              </div>
            </div>
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
          for (let j = 0, len2 = children.persions.length; j < len2; j++) {
            let p = children.persions[j];
            let count = 0;
            for (let k = 0, len3 = p.errorList.length; k < len3; k++) {
              count += Number.parseInt(p.errorList[k].num);
            }
            p.count = count;
          }
        }
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
