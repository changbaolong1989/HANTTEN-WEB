<template>
  <!-- 对话框 -->
  <div class="collapseReviewWrite_dialog redact" v-if="show" @click.self="show = false">
    <div class="content">
      <p class="title">选择错误：{{title}}</p>
      <table>
        <tr>
          <th>考核分类</th>
          <th>问题类型</th>
          <th>次数</th>
        </tr>
        <tr v-for="(item,i) in countErrorTyleList" :key="i">
          <td :rowspan="item.rowSpan" v-if="parseInt(item.rowSpan)">{{item.examineTypeName}}</td>
          <td>{{item.questionName}}</td>
          <td>
            <input-number :max="100" :min="0" v-model="item.questionNum"></input-number>
            <br>
            <button v-if="'其他'==item.questionName" class="btn-opinion" @click="rejectOpinion">驳回意见</button>
          </td>
        </tr>
      </table>
      <div class="footer">
        <button class="linear btn" @click="submit">确定</button>
      </div>
    </div>
    <div class="option-container">
      <textarea name="" id="" cols="30" rows="10"></textarea>
    </div>
  </div>
</template>
<script>
  import inputNumber from './inputNumber.vue'
  export default {
    components: {
      'input-number': inputNumber
    },
    props: [
      'errorTyleList' /* 错误类型数组 */ ,
      'title', /* 选择错误的标题(谁被选择错误) */
      'obj'
    ],
    mounted() {},
    data() {
      return {
        countErrorTyleList:this.sortTableData(this.tool.deepCopy(this.errorTyleList, this.tool.deepCopy)),
        show: false, // 显示对话框
        mergeArr : {} ,// 合并对象数组(源数据)
      };
    },
    watch: {
      errorTyleList: {
        handler() {
          let vm = this;
          // 重置数据
         vm.countErrorTyleList = vm.sortTableData(vm.tool.deepCopy(vm.errorTyleList, vm.tool.deepCopy));
        },
        immediate: true
      },
      show: {
        handler() {
          let vm = this;
          // 重置数据
          vm.countErrorTyleList = vm.sortTableData(vm.tool.deepCopy(vm.errorTyleList, vm.tool.deepCopy));
          if (vm.show) {
            // 赋值数据
            vm.assignment(vm.obj);
          }
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 变更驳回意见
       */
      rejectOpinion(){
        let vm = this;
        vm.MessageBox.prompt('请输入驳回意见',{
          inputType:'textarea'
        }).then(({ value, action }) => {
          vm.bus.$emit('changeWriteOption',value);
        });
      },
      /**
       * @method 合并单元格
       */
      spanMethod(data){
        let vm = this;
        for (let i = 0, len = data.length; i < len; i++) {
          let span = vm.mergeArr[data[i].examineTypeId];
          switch (span){
            case 1:
              item[i].rowSpan = 1;
              break;
            default:
              if(i){
                // 相同的值
                if(data[i-1].examineTypeId==data[i].examineTypeId){
                  data[i].rowSpan = 0;
                }else{
                  data[i].rowSpan = span;
                }
              }else{
                data[i].rowSpan = span;
              }
              break;
          }
        }
        return data;
      },
      /**
       * @method 排序表格数据
       * @param {Array} data 表格数据
       */
      sortTableData(data){
        let vm = this;
        if(!data.length)return [];
        // 合并对象数组每次查询的时候清空
        vm.mergeArr = {};
        // 当前合并对象
        let current = {
          rowSpan: 1,
          /* 合并列数量 */
          id: '' // 合并相同的值
        };
        // 添加合并对象信息
        for (let i = 0, len = data.length; i < len; i++) {
          let obj = data[i];
          if (current.id == obj.examineTypeId) {
            // 相等的时候,添加合并列个数
            current.rowSpan++;
          } else {
            if (current.rowSpan > 1) vm.mergeArr[current.id] = current.rowSpan;
            // 不相等的时候,重置当前合并对象
            current = {
              rowSpan: 1,
              id: obj.examineTypeId
            };
          }
          // 如果是最后一个,则执行添加一次
          if ((i + 1) == len) vm.mergeArr[current.id] = current.rowSpan;
        }
        return vm.spanMethod(data.sort((a,b)=>parseInt(a.examineTypeId) > parseInt(b.examineTypeId)?1:-1));
      },
      submit() { // 关闭的回调
        let vm = this;
        let fire = [];
        for (let i = 0, len = vm.countErrorTyleList.length; i < len; i++) {
          if (vm.countErrorTyleList[i].questionNum) {
            fire.push(vm.tool.deepCopy(vm.countErrorTyleList[i], vm.tool.deepCopy));
          }
        }
        this.$emit('submit', fire);
        vm.show = false;
      },
      assignment(item) { // 赋值
        let vm = this;
        if (item.errorList && item.errorList.length) {
          for (let i = 0, len = vm.countErrorTyleList.length; i < len; i++) {
            for (let j = 0, len2 = item.errorList.length; j < len2; j++) {
              if (vm.countErrorTyleList[i].questionId == item.errorList[j].questionId)
                vm.countErrorTyleList[i].questionNum = item.errorList[j].questionNum;
            }
          }
        }
      }
    }
  };
</script>
<style lang="scss">
  .collapseReviewWrite_dialog {
    * {
      box-shadow: border-box;
    }
  }
</style>
<style lang="scss" scoped>
  .redact {
    position: fixed;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 100000;
    width: 100%;
    height: 100vh;
    top: 0;
    left: 0;
    box-sizing: border-box;

    .content {
      max-height: 1000px;
      width: 100%;
      background-color: #fff;
      position: absolute;
      bottom: 0;
      z-index: 100;
      overflow:auto;

      .title {
        font-size: 28px;
        line-height: 36px;
        color: #b3b3b3;
        padding: 25px 30px;
        box-shadow: 0 0 50px 0px rgba(0, 0, 0, 0.125);
      }

      table{
        width: 100%;
        tr{

          th,td{
            text-align: center;
            padding:20px;
            border: 1px solid #e5e5e5;
          }

          td{
            &:first-child{
              min-width: 100px;
            }
            &:last-child{
              width: 160px;
            }
          }
        }
      }
    }

    .footer {
      padding: 13px;
      box-shadow: 0 -5px 50px rgba(0, 0, 0, 0.125);

      .btn {
        width: 100%;
        height: 70px;
        line-height: 70px;
        color: white;
        border-radius: 10px;
      }
    }

    .btn-opinion{
      padding:4px 8px;
      background-color: #13A7F1;
      color: white;
      border-radius: 10%;
      font-size: 12px;
    }


  }
</style>
