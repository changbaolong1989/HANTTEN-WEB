<template>
  <!-- 面板-复核可写 -->
  <el-collapse-item class="collapseReviewWrite" :name="name">
    <template slot="title">
      <span>审核</span>
      <!-- 对话框 -->
      <collapse-review-write-dialog :title="title" @submit="submit" @changeOption="changeOption" :option="countObject.opinion" ref="collapseReviewWrite_dialog" :errorTyleList="countErrorTyleList"></collapse-review-write-dialog>
    </template>
    <div class="width-94">
      <el-input type="textarea" :rows="2" placeholder="驳回请输入驳回意见，通过不填默认为同意" v-model="countObject.opinion" @change="submit">
      </el-input>
    </div>
    <div v-if="!noPenalty">
      <div class="width-94" style="margin-top: 15px;">扣分</div>
      <el-collapse accordion class="level2" v-model="activeNames">
        <el-collapse-item :name="name + '-' + j" v-for="(item, j) in countObject.children" :key="j">
          <template slot="title">
            <span class="name" style="width: auto;">{{ item.name}}
            </span>
            <span class="badge error" style="margin-left: 5px;" v-if="item.count">{{ item.count }}</span>
            <span class="time" style="position: absolute;top: 0;right: 35px;"><a href="#" @click.stop="chooseError(item)">请选择错误</a></span>
          </template>
          <div class="content">
            <ul>
              <li v-for="(li3, m) in item.errorList" :key="m">
                <div class="error-container" v-if="li3.questionNum"><span class="badge error">{{ li3.questionNum }}</span> {{ li3.questionName }}</div>
                <span class="is-deduction" :class="{'is-deduction__yes':!!li3.deduction}" @click="li3.deduction=!!!li3.deduction">{{!!li3.deduction?'扣分':'不扣分'}}</span>
              </li>
            </ul>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </el-collapse-item>
</template>

<script>
  import Vue from 'vue'
  /* 面板--复核可写 选择错误弹窗 */
  import collapseReviewWrite_dialog from '../../common/components/collapseReviewWrite_dialog.vue';
  export default {
    components: {
      'collapse-review-write-dialog': collapseReviewWrite_dialog,
    },
    props: ['object', 'name', 'errorTyleList', 'noPenalty' /* 不查看扣分 */ ],
    mounted() {},
    data() {
      return {
        activeNames:this.name+'-0',// 默认展开
        count: 0,
        countErrorTyleList: this.tool.deepCopy(this.errorTyleList, this.tool.deepCopy), // 当前错误列表(表格数据)
        countObject: this.tool.deepCopy(this.object, this.tool.deepCopy), // 当前审核对象
        countId: '', // 当前被修改被审核人标识
        zindex1: true, // ie兼容调整
        zindex2: false, // ie兼容调整
        title: '', //对话框标题
      };
    },
    watch: {
      object: {
        handler() {
          let vm = this;
          vm.countObject = vm.tool.deepCopy(vm.object, vm.tool.deepCopy);
          for (let i = 0, len = vm.countObject.children.length; i < len; i++) {
            let children = vm.countObject.children[i];
            let count = 0;
            for (let j = 0, len2 = children.errorList.length; j < len2; j++) {
              count += children.errorList[j].questionNum;
            }
            children.count = count;
          }
        },
        deep:true,
        immediate: true
      },
      errorTyleList:{
        handler() {
          let vm = this;
          vm.countErrorTyleList = vm.tool.deepCopy(vm.errorTyleList, vm.tool.deepCopy);
        },
        immediate: true

      }
    },
    methods: {
      // 更改
      changeOption(val){
        let vm = this;
        vm.countObject.opinion = val;
      },
      submit(data) { // 弹窗表单提交事件
        let vm = this;
        if (data instanceof Array) {
          // 改变countObject指定子集的errorList
          let objs = vm.countObject.children // 被审核人数组
          for (let i = 0, len = objs.length; i < len; i++) {
            if (objs[i].id == vm.countId) {

              objs[i].errorList = [];

              for (let j = 0, len2 = data.length; j < len2; j++) {
                objs[i].errorList.push(data[j]);
              }
            }
          }
        }
        vm.$emit('submit', vm.tool.deepCopy(vm.countObject, vm.tool.deepCopy));
      },
      /* 选择错误*/
      chooseError(item) {
        let vm = this;
        vm.resetTable(item);
        vm.title = item.name;
        vm.countId = item.id;
        // 显示选择错误弹窗
        vm.$refs['collapseReviewWrite_dialog'].show = true;
      },
      resetTable(item) { // 重置表格数据




        let vm = this;

        if (item.errorList && item.errorList.length) {



          // 赋值
          for (let i = 0, len = vm.countErrorTyleList.length; i < len; i++) {

            let change = false; // 是否改变

            for (let j = 0, len2 = item.errorList.length; j < len2; j++) {

              if (vm.countErrorTyleList[i].questionId == item.errorList[j].questionId) {

                vm.countErrorTyleList[i].questionNum = item.errorList[j].questionNum;
                change = true; // 改变

              }
            }

            if (!change) vm.countErrorTyleList[i].questionNum = 0;

          }




        } else {
          for (let i = 0, len = vm.countErrorTyleList.length; i < len; i++) {
            vm.countErrorTyleList[i].questionNum = 0;
          }
        }



      }
    }
  };
</script>
<style lang="scss"></style>
<style lang="scss" scoped>
  .collapseReviewWrite{

    .content{

      li{
        position: relative;
        padding:1px 0px;

        .error-container{
          padding-right: 37px;
        }
      }
    }

    /* 是否扣分 */
    .is-deduction{
      position:absolute;
      top:3px;
      right:-14px;
      background-color:#7fd680;
      height: 15px;
      line-height: 13px;
      width: 48px;
      border-radius: 8px;
      color: white;
      text-align: center;
      padding: 2px 5px;
      display: inline-block;
      cursor: pointer;

      &.is-deduction__yes{
        background-color:#ff688b;
      }
    }
  }
</style>
