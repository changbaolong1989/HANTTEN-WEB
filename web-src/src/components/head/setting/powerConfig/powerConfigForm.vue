<template>
  <!-- 权限弹窗 -->
  <div class="jurisdiction">
    <el-dialog :title="formTitle" :visible="true" width="700px" transition="fade-in-linear" center :before-close="handleClose">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="职位名称" prop="jobName">
          <span>{{ ruleForm.jobName }}</span>
        </el-form-item>
        <el-form-item label="权限" prop="tree" class="tree-error">
          <el-tree :data="powerTreeDate" show-checkbox default-expand-all node-key="powerId" ref="powerTree" highlight-current :props="props"></el-tree>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitRolefunc('ruleForm')">确 定</el-button></span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    var validateTree = (rule, value, callback) => {
      if (this.$refs.powerTree.getCheckedKeys().length == 0) {
        callback(new Error('请选择访问权限'));
      } else {
        callback();
      }
    };
    return {
      powerTreeDate: [],
      parentMenuIdList: [],
      formTitle: '修改职位权限',
      rules: {
        tree: [
          {
            validator: validateTree,
            trigger: 'change'
          }
        ]
      },
      ruleForm: {
        jobId: '',
        jobName: '',
        updateDate: ''
      },

      props: {
        label: 'powerName'
      }
    };
  },
  mounted() {
    this.excSearchAllPowerList();
  },
  methods: {
    updatePowerRelationFunc(jobId) {
      this.formTitle = '职位权限';
      this.ruleForm.jobId = jobId;
      const vm = this;
      let formData = {
        jobId: jobId
      };
      // 获取列表数据
      vm.apiAddress('/config/powerConfig/query/searchJobInfo', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            let dataVO = res.data.dataVO;
            // 循环数据放入表格数据对象中
            vm.ruleForm = {
              jobId: dataVO.jobId,
              jobName: dataVO.jobName
            };
            let checkedKeys = [];
            let htJobPowerRelationVOList = dataVO.htJobPowerRelationVOList;
            for (var i = 0; i < htJobPowerRelationVOList.length; i++) {
              checkedKeys.push(htJobPowerRelationVOList[i].powerId);
            }
            vm.$refs.powerTree.setCheckedKeys(checkedKeys);
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    excSearchAllPowerList() {
      const vm = this;
      let formData = {};
      // 获取列表数据
      vm.apiAddress('/config/powerConfig/query/searchAllPowerList', formData)
        .then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            let dataList = res.data.dataList;
            vm.powerTreeDate = dataList;
          }
        })
        .catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        });
    },
    submitRolefunc(formName) {
      this.$refs[formName].validate(valid => {
        let keys = this.$refs.powerTree.getCheckedKeys();
        let htJobPowerRelationVOList = [];
        for (var i = 0; i < keys.length; i++) {
          let obj = {
            powerId: keys[i]
          };
          htJobPowerRelationVOList.push(obj);
        }
        if (valid) {
          let formData = {
            jobId: this.ruleForm.jobId,
            htJobPowerRelationVOList: htJobPowerRelationVOList
          };
          const vm = this;
          vm.apiAddress('/config/powerConfig/modify/updatePowers', formData)
            .then(res => {
              if (res.data.status == 'failure') {
                vm.$message({
                  message: res.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                vm.$message({
                  message: res.data.message,
                  type: 'success',
                  center: true
                });
                this.$parent.reflashData();
                this.$parent.centerDialogVisible = false; /* 让弹窗隐藏 */
              }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleClose(done) {
      this.$refs.ruleForm.resetFields();
      done();
      console.log('------------', this.$parent.centerDialogVisible);
      this.$parent.centerDialogVisible = false;
    }
  }
};
</script>
<style lang="scss">
.jurisdiction {
  .el-tree-node {
    float: left;
    width: 40%;
    margin-left: 5%;
  }
  .el-tree {
    .is-checked {
      .el-tree-node__content {
        background-color: transparent !important;
        color: #606266 !important;
      }
    }
  }
}
</style>
