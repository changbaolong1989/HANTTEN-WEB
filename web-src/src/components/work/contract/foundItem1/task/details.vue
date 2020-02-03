<template>

  <div class="task">
    <div class="form-block">
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">项目编号:{{currentTransmitData.projectNumber}}</p>
        </el-col>
        <el-col :span="12" style="padding-left: 15px;">
          <p style="font-size: 12px;">合同编号:{{dataVO.contractNum}}</p>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">任务名称:{{dataVO.taskName}}</p>
        </el-col>
        <el-col :span="12" style="padding-left: 15px;">
          <p style="font-size: 12px;">创建时间:{{dataVO.createDate}}</p>
        </el-col>
      </el-row>
      <el-row :gutter="gutter">
        <el-col :span="12">
          <p style="font-size: 12px;padding-left: 10px;">咨询类别:{{dataVO.counselTypeName}}</p>
        </el-col>
        <!--<el-col :span="12" style="padding-left: 15px;">-->
          <!--<p style="font-size: 12px;">咨询类别:{{dataVO.counselTypeName}}</p>-->
        <!--</el-col>-->
      </el-row>
    </div>

    <div class="title form-block" v-if="dataVO.taskContent != ''">
      <el-row :gutter="gutter">
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">任务说明:</p>
        </el-col>
        <el-col :span="24">
          <p style="font-size: 12px;padding-left: 10px;">{{dataVO.taskContent}}</p>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
  export default {
    props: ['currentTransmitData'],
    data() {
      return {
        gutter: 40,// 表单项间距
        dataVO:{
          contractNum: "", // 合同编号
          createDate: "", // 创建时间
          taskName: "", // 任务名称
          taskContent:"", // 任务内容
          counselTypeName:"", // 咨询类别名称
          contractTypeName:"", // 合同类型名称
        }
      }
    },
    watch: {
      currentTransmitData: {
        handler(newval) {
          let vm = this;
          vm.currentTransmitData = newval;
          vm.init(vm.currentTransmitData.id);// 根据任务id查询合同内容
        },
        immediate: true,
        deep: true
      },
    },
    created() {
      let vm = this;
      vm.init(vm.currentTransmitData.id);// 根据任务id查询合同内容
    },
    methods: {
      // 根据任务id查询合同内容
      init(taskId) {
        let vm = this;
        vm.apiAddress('/task/query/searchTaskContent', {
          taskId: taskId// 任务id
        }).then(res => {
          if (res.data.status == 'success') {
            vm.dataVO = this.tool.deepCopy(Object.assign({}, res.data.dataVO), this.tool.deepCopy);// 任务内容
          } else {
            vm.stateDialog('error', res.data.message);
          }
        }).catch(err => {
          vm.stateDialog('error', err);
        })
      },
    }
  }
</script>
<style>
  @import url('../../../common/css/other.css');
</style>
