<template>
  <!-- 项目监理右边栏-->
  <div class="index">
    <div class="primary">
      <!-- 归档阶段 项目监理部 -->
      <button class="linear" v-if="typeFlag == '1'" @click="initiateArchive">发起归档</button>
      <input type="text" placeholder="请输入档案编号" class="fileNumber" v-model="fileNumber" v-if="typeFlag === '2'" />
      <button class="linear" v-if="typeFlag == '2'" @click="confirmArchiveXM">确认归档</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fileNumber: '', // 档案编号
      delegateTaskId: '' /* 委托任务id */
    };
  },
  props: ['currentTransmitData', 'typeFlag'],
  watch: {
    currentTransmitData: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.currentTransmitData = newval;
      },
      immeditate: true
    },
    typeFlag: {
      /* 被点击的左边栏的信息 */
      handler(newval) {
        const vm = this;
        vm.typeFlag = newval;
      },
      immeditate: true
    }
  },
  created() {
    this.searchArchiveTaskDetailInfo(this.currentTransmitData.taskId);
  },
  methods: {
    // 查询归档任务信息
    searchArchiveTaskDetailInfo(taskId) {
      this.requestPost('/task/query/searchArchiveTaskDetailInfoByTaskId', {
        taskId: taskId
      })
        .then(res => {
          if (res.data.status == 'failure') {
            this.Toast(res.data.message);
          } else {
            // 委托任务id
            this.delegateTaskId = res.data.dataVO.delegateTaskId;
          }
        })
        .catch(err => {
          this.Toast(err);
        });
    },
    // ==============================================================================归档阶段 发起归档 项目监理部 start=====
    // ==============================================================================归档阶段 发起归档 项目监理部 start==============================================================================
    // 发起归档
    initiateArchive() {
      this.MessageBox.confirm('', {
        message: '确定执行发起归档操作?',
        title: '提示',
        showCancelButton: true
      })
        .then(action => {
          this.requestPost('/task/modify/initiateArchive', {
            // 任务id
            taskId: this.currentTransmitData.taskId
          })
            .then(res => {
              // 如果返回成功，则提示成功信息，否则提示失败信息
              if (res.data.status == 'success') {
                // 跳转到工作台
                 this.$router.push('/app/workbench')
              } else {
                this.Toast(res.data.message);
              }
            })
            .catch(e => {
              this.Toast(e);
            });
        })
        .catch(err => {});
    },
    // ==============================================================================归档阶段 发起归档 项目监理部 end==============================================================================
    // ==============================================================================归档阶段 确认归档 项目监理部 start==============================================================================
    // 确认归档
    confirmArchiveXM() {
      if (this.fileNumber != '') {
        this.MessageBox.confirm('', {
          message: '确定执行确认归档操作?',
          title: '提示',
          showCancelButton: true
        })
          .then(action => {
            this.requestPost('/task/modify/confirmArchiveXM', {
              // 任务id
              taskId: this.currentTransmitData.taskId,
              // 档案编号
              fileNumber: this.fileNumber,
              // 委托任务id
              delegateTaskId: this.delegateTaskId
            })
              .then(res => {
                // 如果返回成功，则提示成功信息，否则提示失败信息
                if (res.data.status == 'success') {
                  // 刷新页面
                  this.$router.go(0);
                } else {
                  this.Toast(res.data.message);
                }
              })
              .catch(e => {
                this.Toast(e);
              });
          })
          .catch(err => {});
      } else {
        this.Toast('请输入档案');
      }
    }
    // ==============================================================================确认归档 查询文件列表 end=============
  }
};
</script>

<style scoped lang="scss">
.fileNumber {
  width: 80%;
  height: 70px;
  border: 2px solid #e5e5e5;
  margin: 0 auto;
  display: block;
  border-radius: 10px;
  padding: 0 10px;
}
</style>
