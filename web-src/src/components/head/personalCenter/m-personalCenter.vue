<template>
  <!-- 个人中心 -->
  <el-tabs v-model="active" type="card" @tab-click="handleClick" class="m-personal-center">
    <div class="item-tab-bor linear"></div>
    <el-tab-pane label="修改密码" name="first">
      <el-form :status-icon="true" ref="ruleForm" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="登录账号">{{ userAccount }}</el-form-item>
        <el-form-item label="原密码" prop="oldPwd"><el-input type="password" v-model="form.oldPwd" placeholder="请输入原密码"></el-input></el-form-item>
        <el-form-item label="新密码" prop="newPwd"><el-input type="password" v-model="form.newPwd" placeholder="请输入新密码"></el-input></el-form-item>
        <el-form-item label="再次输入新密码" prop="resetNewPwd"><el-input type="password" v-model="form.resetNewPwd" placeholder="请输入再次输入新密码"></el-input></el-form-item>
        <el-form-item size="large"><el-button class="linear" type="primary" @click="onSubmit('ruleForm')">确定</el-button></el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="个人信息" name="second">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="姓名">{{ userName }}</el-form-item>
        <el-form-item label="文化程度">{{ educationName }}</el-form-item>
        <el-form-item label="所属公司">{{ companyName }}</el-form-item>
        <el-form-item label="入职日期">{{ entryDate }}</el-form-item>
        <el-form-item label="职务">
          <el-alert :title="item.departmentJobName" show-icon v-for="(item, index) in jobList" :key="index" :closable="closeFlag"></el-alert>
        </el-form-item>
        <el-form-item label="拥有资质">
          <el-upload action="#" class="aptitude" list-type="picture-card" :auto-upload="false" disabled :file-list="fileList"><i class="el-icon-plus"></i></el-upload>
        </el-form-item>
        <el-form-item label="个人签名"><img preview="0" :src="signPathFile" alt="" /></el-form-item>
      </el-form>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  data() {
    return {
      closeFlag: false,
      // 姓名
      userName: '',
      // 文化程度
      educationName: '',
      // 所属部门
      deptName: '',
      //所属公司
      companyName: '',
      // 入职日期
      entryDate: '',
      // 职务
      jobName: '',
      // 登陆账号
      userAccount: '',
      // 个人签名
      signPathFile: '',
      // 资质文件list
      fileList: [],
      active: 'first',
      jobList: [],
      form: {
        oldPwd: '',
        newPwd: '',
        resetNewPwd: ''
      },
      rules: {
        oldPwd: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newPwd: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        resetNewPwd: [{ required: true, message: '请输入再次输入新密码', trigger: 'blur' },{validator:this.checkResetNewPwd,trigger: 'change'}]
      }
    };
  },
  mounted() {
    // 初始化
    this.getUserInfo();
  },
  methods: {
    handleClick(){},
    onSubmit(formName) {
      const vm = this;
      // 提交
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm('是否提交?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            center: true
          })
            .then(() => {
              /* 新增数据 */
              vm.$axios
                .post(
                  '/user/modify/updateUserPwd',
                  {
                    oldPwd: vm.form.oldPwd,
                    // 修改密码
                    password: vm.form.newPwd
                  },
                  vm.config
                )
                .then(function(response) {
                  // 如果返回成功，则提示成功信息，否则提示失败信息
                  if (response.data.status == 'success') {
                    vm.$message({
                      type: 'success',
                      message: response.data.message
                    });
                  } else {
                    vm.$message({
                      type: 'error',
                      message: response.data.message
                    });
                  }
                })
                .catch(function(error) {
                  vm.$message({
                    type: 'error',
                    message: error
                  });
                });

              // 隐藏新增弹窗
              this.centerDialogVisible1 = false;
              // 清空上次修改的表单内容
              this.$refs[formName].resetFields();
            })
            .catch(() => {});
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    /*查询人员信息*/
    getUserInfo() {
      const vm = this;
      vm.$axios
        .post('/user/query/searchUserInfo', {}, vm.config)
        .then(function(response) {
          vm.userName = response.data.dataVO.userName;
          vm.educationName = response.data.dataVO.educationName;
          vm.entryDate = response.data.dataVO.entryDate;
          vm.userAccount = response.data.dataVO.userAccount;
          vm.companyName = response.data.dataVO.companyName;
          let fileList = response.data.dataVO.flieList;
          if (null != fileList && fileList.length > 0) {
            for (var i = 0; i < fileList.length; i++) {
              let obj = {
                name: fileList[i].fileRelationId,
                url: fileList[i].fileUrl
              };
              vm.fileList.push(obj);
            }
          }
          vm.signPathFile = response.data.dataVO.signPath;
          vm.jobList = response.data.dataVO.jobList;
          // vm.form.culture = response.data.dataVO.educationId;
          // vm.form.account = response.data.dataVO.userAccount;
          // vm.form.section = response.data.dataVO.deptId; /* 所属部门 */
          // vm.form.company = response.data.dataVO.companyId;
          // vm.form.role = response.data.dataVO.roleId;
          // vm.form.major = response.data.dataVO.professionalIds;
          // vm.form.date = response.data.dataVO.entryDate;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    /*校验新密码和再次输入的密码的校验*/
    checkResetNewPwd(rule, value, callback){
      if(this.form.newPwd != this.form.resetNewPwd){
        callback(new Error('两次输入密码不相等，请重新输入'));
      }else{
        callback();
      }
    }
  }
};
</script>
<style lang="scss">
/* 个人中心 */
.el-upload--picture-card {
  display: none;
}
.m-personal-center {
  /* 标签页头部 */
  .el-tabs__header {
    margin: 0;
    border: none;
    /* 标签项 */
    .el-tabs__item {
      border-radius: 10px 10px 0 0;
      box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
      border: none;
      background-color: white;
      color: #b7b7b8;
      /* 标签的激活状态 */
      &.is-active {
        background-color: #0aa3ef;
        color: white;
      }
    }

    .el-tabs__nav-scroll {
      padding: 0 10px;

      .el-tabs__nav {
        border: none;
      }
    }
  }
  .el-alert--info.is-light {
    width: auto;
    display: inline-block !important;
    margin: 5px;

    .el-icon-info {
      display: none;
    }

    .el-alert__content {
      padding-right: 15px;
    }
  }

  /* 标签页内容部分 */
  .el-tabs__content {
    overflow: visible;
    border-radius: 10px 10px 0 0;

    /* 各自的内容面板 */
    .el-tab-pane {
      background-color: white;
      border-radius: 10px;
      padding: 20px 20px 0;

      /* 表单 */
      .el-form {
        .el-form-item {
          text-align: left;
          margin: 0;
          padding: 10px 0;
          border-bottom: 1px solid #f1f1f1;

          &:last-child {
            border-bottom: none;
          }

          .el-form-item__label {
            width: 140px !important;
            text-align: left;
            color: #999999;
          }

          .el-form-item__content {
            color: #333333;
            margin-left: 140px !important;

            .el-input {
              max-width: 200px;
              border: 1px solid #f8f9fb;
              border-radius: 5px;
              margin-right: 20px;
              font-size: 12px;

              .el-input__inner {
                background-color: #f8f9fb;
                color: #333333;
                height: 30px;
              }
            }

            img {
              width: 160px;
              padding: 10px;
            }
          }

          .el-button {
            width: 120px;
            height: 30px;
            padding: 0;
            line-height: 30px;
          }
        }
      }
    }
  }
}
</style>
<style scoped lang="scss">
/*退出按钮样式 */
.btn-please-chapter {
  width: 83px;
  height: 26px;
  line-height: 26px;
  padding: 0;
  margin: 0;
  position: absolute;
  top: -40px;
  right: 0;
  color: #0099e8;
  border: 1px solid #0099e8;
  background-color: white;
  font-size: 12px;
  border-radius: 5px;
  z-index: 1;

  i {
    font-size: 14px;
  }
}
</style>
