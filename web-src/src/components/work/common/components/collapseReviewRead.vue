<template>

  <!-- 面板-复核只读 -->
  <el-collapse-item class="collapseReviewRead">
    <template slot="title">
      <span>{{ object.title }}</span>
    </template>
    <el-collapse accordion class="level2">
      <el-collapse-item :name="name+'-' + j" v-for="(li2, j) in object.children||[]" :key="j">
        <template slot="title">
          <span class="name" :title="li2.name">{{ li2.name }}
          </span>
          <span class="time">{{ li2.time }}</span>
          <span class="time" :title="1==li2.isPass?'通过':'未通过'">{{1==li2.isPass?'通过':'未通过'}}</span>
        </template>
        <div class="content">
          <div class="opinion">{{ li2.opinion }}</div>
          <div v-for="(li3, k) in li2.persions" :key="k">
            <ul>
              <li>{{ li3.name }}<span class="badge error" style="margin-left: 5px;" v-if="li3.count">{{ li3.count }}</span></li>
              <li v-for="(li4, m) in li3.errorList" :key="m">
                <span class="badge error" v-if="li4.num">{{ li4.num }}</span>
                {{ li4.type }}
              </li>
            </ul>
            <div class="decoration" v-if="li2.persions&&li2.persions[k + 1]">
              <div class="line"></div>
            </div>
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
  </el-collapse-item>


</template>

<script>
  // object: { // 一级面板的对象
  //         title: '', // 一级面板的标题
  //         children: [{
  //           name: '', // 二级面板的审批人
  //           time: '', // 二级面板的时间
  //           isPass:0,// 通过1,未通过0
  //           opinion: '', // 二级面板的意见
  //           persions: [ // 二级面板的被审批人数组
  //             {
  //               name: '', // 二级面板的被审批人名字
  //               errorList: [ // 二级面板的被审批人的错误数组
  //                 {
  //                   num: '', // 错误数量
  //                   type: '' // 错误类型
  //                 }
  //               ]
  //             }
  //           ]
  //         }]
  //       }
  export default {
    props: ['object','name'],
    mounted() {
    },
    data() {
      return {
        countObject: this.getCount(this.object)// 当前审核对象
      };
    },
     watch: {
       object() {
         let vm = this;
        vm.getCount(vm.object);
      }
    },
    methods: {
      getCount(object){
        let vm = this;
        vm.countObject = object;
        if(!vm.countObject.children)return;
        for (let i = 0,len = vm.countObject.children.length; i < len; i++) {
          let children = vm.countObject.children[i];
          if(!children.persions)return;
          for (let j = 0,len2 = children.persions.length; j < len2; j++) {
            let p = children.persions[j];
            let count = 0;
             for (let k = 0,len3 = p.errorList.length; k < len3; k++) {
               count += Number.parseInt(p.errorList[k].num);
              }
              p.count = count;
          }

        }
      }
    }
  };
</script>
<style lang="scss"></style>
<style lang="scss" scoped></style>
