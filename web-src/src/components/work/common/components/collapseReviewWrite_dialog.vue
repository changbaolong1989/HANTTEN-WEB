<template>
  <div class="collapse-review-write_dialog" @click.stop="(()=>{})">
    <!-- 对话框 -->
    <el-dialog :visible="show" width="60%" :title="'选择错误：' + title" :before-close="beforeClose" :modal-append-to-body="true"
      ref="dialog" center :class="{'zindex1':zindex1,'zindex2':zindex2}" :close-on-click-modal="false"
      @open="open">
      <el-form ref="form" :model="form" label-width="80px">
        <el-table :data="countErrorTyleList" :border="true" :span-method="spanMethod">
          <el-table-column align="center" prop="examineTypeName" label="考核分类">
          </el-table-column>
          <el-table-column align="center" prop="questionName" label="问题类型">
          </el-table-column>
          <el-table-column width="300" align="center" prop="questionNum" label="次数">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.questionNum" :min="0" :style="{marginRight:scope.row.questionName!='其他'?'62px':0}"></el-input-number>
              <button type="button" class="btn-function" v-if="scope.row.questionName=='其他'" @click="rejectOpinion">驳回意见</button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit" style="padding: 0 45px;">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  let mergeArr =  [] ;// 合并对象数组(源数据)
  let mergeArrBackup = [];// 合并对象数组备份(会被修改的数据)
  export default {
    /**
     * option 审核意见
     */
    props: ['errorTyleList' /* 表格数据 */ ,'title','option'],
    mounted() {},
    data() {
      return {
        countErrorTyleList:this.sortTableData(this.tool.deepCopy(this.errorTyleList, this.tool.deepCopy)),
        show: false, // 显示对话框
        zindex1: true, // ie兼容调整
        zindex2: false, // ie兼容调整
        form: { // 表单
        }
      };
    },
    watch:{
      errorTyleList:{
        handler(){
          let vm = this;
          vm.countErrorTyleList = vm.sortTableData(vm.tool.deepCopy(vm.errorTyleList, vm.tool.deepCopy));
        },
        immediate: true
      }
    },
    methods: {
      /**
       * @method 驳回意见
       */
      rejectOpinion(){
        let vm = this;
          vm.$prompt('请输入驳回意见', '提示', {
                    confirmButtonText: '确定',
                    inputType:'textarea',
                    inputValue:vm.option,
                    cancelButtonText: '取消',
                  }).then(({ value }) => {
                    vm.$emit('changeOption',value);
                  }).catch(() => {
                  });
      },
      /**
       * @method 合并单元格
       */
      spanMethod({
        row,
        column,
        rowIndex,
        columnIndex
      }) { // 数据表格合并行和列

         let vm = this;
         let returnArr = [1,1];
        if(columnIndex==0){
            // 不知什么原因,导致这个合并会执行两次
            // 所以合并数据开始遍历前,重新赋值
          if(0==rowIndex)mergeArrBackup = vm.tool.deepCopy(mergeArr, vm.tool.deepCopy);
          let arr = vm.tool.deepCopy(mergeArrBackup, vm.tool.deepCopy);
          // 遍历要合并的对象数组
          for (let i = 0,len = arr.length; i < len; i++) {
            let obj = arr[i];
            if(null===obj)returnArr = [0,1];
            // 如果和当前行的examineTypeId相等,则设置当前行的合并行
            if(obj&&obj.id==row.examineTypeId){
               mergeArrBackup.splice(i,1,null);
                return [obj.rowSpan,1];
            }
          }
        }
         return returnArr;
      },
      /**
       * @method 排序表格数据
       * @param {Array} data 表格数据
       */
      sortTableData(data){
        if(!data||!data.length)return [];
        // 合并对象数组每次查询的时候清空
        mergeArr = [];
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
            if (current.rowSpan > 1) mergeArr.push(current);
            // 不相等的时候,重置当前合并对象
            current = {
              rowSpan: 1,
              id: obj.examineTypeId
            };
          }
          // 如果是最后一个,则执行添加一次
          if ((i + 1) == len) mergeArr.push(current);
        }
        return data.sort((a,b)=>parseInt(a.examineTypeId) > parseInt(b.examineTypeId)?1:-1);
      },
      open() { // 打开的回调
        let vm = this;
        /* 这个页面 IE浏览器下,对话框弹窗会再显示消失,动态更改el-dialog的z-index,可以显示对话框*/
        /* IE */
        let count = 0;
        let interval = setInterval(function() {
          vm.zindex1 = !vm.zindex1;
          vm.zindex2 = !vm.zindex2;
          count++;
          if (5 == count) {
            clearInterval(interval);
          }
        }, 1000);
        /* IE */
      },
      submit() { // 关闭的回调
        let vm = this;
        let fire = [];
         for (let i = 0,len = vm.countErrorTyleList.length; i < len; i++) {
           if(vm.countErrorTyleList[i].questionNum){
            fire.push(vm.tool.deepCopy(vm.countErrorTyleList[i], vm.tool.deepCopy));
           }
        }
        this.$emit('submit', fire);
         vm.show = false;
      },
      beforeClose() {
        let vm = this;
        vm.show = false;
      }
    }
  };
</script>
<style lang="scss"></style>
<style lang="scss" scoped>
  .collapse-review-write_dialog{
    /* 功能按钮 */
    .btn-function{
      background: #66b1ff;
      border-color: #66b1ff;
      color: #FFF;
    }
  }
</style>
