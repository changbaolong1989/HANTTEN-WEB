<template>

  <!-- 任务right -->
  <div class="taskR">

    <!-- 占位作用 -->
    <div class="given-container"></div>

    <div class="btn-group">
        <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
          <el-form-item
              prop="fileNumber"
              label="档案编号"
              :rules="[{ required: true, message: '请输入档案编号', trigger: 'blur'}]">
          <!-- 档案编号 -->
          <el-input type="text" v-model="dynamicValidateForm.fileNumber" v-if='typeFlag === "2"' placeholder="请输入档案编号"></el-input>
            </el-form-item>
        </el-form>
      <p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 20px;"></p>

      <el-button type="primary" v-if='InitiateArchive' @click="initiateArchive">发起归档</el-button>
      <el-button type="primary" v-if='ConfirmArchiveXM' @click="confirmArchiveXM('dynamicValidateForm')">确认归档</el-button>

    </div>

  </div>

</template>

<script>
  export default {
    data() {
      return {
        type: "task",// 任务标识
        dynamicValidateForm:{// 表单
          fileNumber:''// 档案编号
        },
        gutter: 40,// 表单项间距
        delegateTaskId:"",// 委托任务id
        InitiateArchive:false,// 项目监理部 发起归档
        ConfirmArchiveXM:false,// 项目监理部 确认归档
        taskId:"",// 任务id
      };
    },
    props: ['currentTransmitData','typeFlag'],
    watch: {
      currentTransmitData: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          let vm = this;
          vm.currentTransmitData = newval;
        },
        immeditate: true
      },
      typeFlag: {
        /* 被点击的左边栏的信息 */
        handler(newval) {
          let vm = this;
          vm.typeFlag = newval;
          vm.init();
        },
        immeditate: true
      },
    },
    created() {
      this.init();
    },
    methods: {
      // =================================================初始化信息 start=================================================
      // 初始化信息
      init() {

        this.taskId = this.currentTransmitData.taskId;// 任务id

        this.InitiateArchive = this.typeFlag === "1"; // 项目监理部 发起归档
        this.ConfirmArchiveXM = this.typeFlag === "2"; // 项目监理部 确认归档

        // 如果是归档时机，则查询委托任务id
        if(this.typeFlag === "2"){
          this.searchArchiveTaskDetailInfo(this.taskId);// 根据任务id查询归档任务信息
        }

      },
      // =================================================初始化信息 end=================================================
      // =================================================查询归档任务信息 end=================================================
      // 查询归档任务信息
      searchArchiveTaskDetailInfo(taskId) {
        let vm = this;
        vm.apiAddress('/task/query/searchArchiveTaskDetailInfoByTaskId', {
          taskId: taskId
        }).then(res => {
          if (res.data.status == 'failure') {
            vm.stateDialog('error', res.data.message)
          } else {
            vm.delegateTaskId = res.data.dataVO.delegateTaskId;// 委托任务id
          }
        }).catch(err => {
          vm.stateDialog('error', err)
        })
      },
      // =================================================查询归档任务信息 end=================================================
      // =================================================发起归档 项目监理部 start=================================================
      // 发起归档
      initiateArchive() {
        let vm = this;
        vm.$confirm('是否提交?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: true
        }).then(() => {
          vm.apiAddress('/task/modify/initiateArchive', {
            // 任务id
            "taskId": vm.taskId
          }).then(res => {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (res.data.status == "success") {
              // 刷新页面
              // vm.$router.go(0);
              vm.$router.push('/page/work/default');
            } else {
              vm.stateDialog('error', res.data.message)
            }
          }).catch(e => {
            vm.stateDialog('error', e);
          })
        }).catch(() => {});
      },
      // =================================================发起归档 项目监理部 end=================================================
      // =================================================确认归档 项目监理部 start=================================================
      formValidation(formName){
        let vm = this;
       return new Promise(function(resolve, reject) {
         vm.$refs[formName].validate((valid) => {
             if (valid)resolve();
         });
        });
      },
      // 确认归档
      confirmArchiveXM(formName) {
        let vm = this;
         vm.formValidation(formName).then(function(){
           vm.$confirm('是否提交?', '提示', {
                 confirmButtonText: '确定',
                 cancelButtonText: '取消',
                 center: true
           }).then(() => {
             vm.apiAddress('/task/modify/confirmArchiveXM', {
               // 任务id
               "taskId": vm.taskId,
               // 档案编号
               "fileNumber": vm.dynamicValidateForm.fileNumber,
               // 修改时间
               "delegateTaskId": vm.delegateTaskId
             }).then(res => {
               // 如果返回成功，则提示成功信息，否则提示失败信息
               if (res.data.status == "success") {
                 // 刷新页面
                 // vm.$router.go(0);
                 vm.$router.push('/page/work/default');
               } else {
                 vm.stateDialog('error', res.data.message)
               }
             }).catch(e => {
               vm.stateDialog('error', e)
             });
           }).catch(() => {});
         });
      },
      // =================================================确认归档 项目监理部 end=================================================
    }
  };
</script>

<style lang="scss">
  .taskR {
    .el-upload-list__item-name {
      margin: 0;
      display: inline-block;
      vertical-align: middle;
    }
    .btn-group{
      .el-form-item{
        margin-bottom:10px;

        .el-form-item__label{
          display: none;
        }
        .el-form-item__content{
          margin-left: 0px!important;
        }
      }
    }
  }
</style>

<style lang="scss" scoped>
  .taskR {
    height: calc(100% - 20px);
    display:flex;
    flex-direction:column;

    .given-container{
      flex-grow: 100;
      overflow: auto;
    }

    .btn-group {
      //按钮组
      width: 80%;
      bottom: 20px;
      margin-left: 10%;
      display: flex;
      flex-direction: column;
      flex-grow: 1;

      .el-button {
        width: 100%;

        &+.el-button {
          margin-left: 0;
          margin-top: 10px;
        }
      }
    }
  }
</style>
