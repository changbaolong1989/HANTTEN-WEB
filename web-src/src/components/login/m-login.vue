<template>
  <div class="login">
    <div class="left"></div>
    <div class="box">
      <!-- 登录表单 -->
      <div class="login-from">
        <div class="login_box">
          <h1>登录</h1>
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm" @keyup.native="handleLogin">
            <el-form-item prop="name">
              <label for="user">账号</label>
              <el-input v-model="ruleForm.userAccount" id="user" @focus="inputFocus($event)"></el-input>
              <span></span>
            </el-form-item>
            <el-form-item prop="pass">
              <label for="password">密码</label>
              <el-input type="password" id="password" v-model="ruleForm.password" @focus="inputFocus($event)"></el-input>
              <span></span>
            </el-form-item>
            <el-form-item><el-button type="primary" class="linear" @click="submitForm('ruleForm')">提交</el-button></el-form-item>
          </el-form>
        </div>
      </div>
      <!-- 登录表单结束 -->
    </div>
    <div class="right"><img src="../../assets/image/bg_toptight.png" alt="" /></div>
  </div>
</template>

<script>
export default {
  data() {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('账号错误,请输入正确帐号'));
      } else {
        callback();
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('密码错误,请输入正确密码'));
      } else {
        callback();
      }
    };
    return {
      usertext: '账号',
      passtext: '密码',
      ruleForm: {
        userAccount: '',
        password: ''
      },
      config: {
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json'
        }
      },
      rules: {
        userAccount: [
          {
            validator: validateName,
            trigger: 'blur'
          }
        ],
        password: [
          {
            validator: validatePass,
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    handleLogin(e) {
      if (e.keyCode === 13) {
        this.submitForm('ruleForm');
      }
    },
    submitForm(formName) {
      /* 点击提交 */
      this.$refs[formName].validate(valid => {
        if (valid) {
          let formData = {
            userAccount: this.ruleForm.userAccount,
            password: this.ruleForm.password
          };
          const vm = this;

          vm.$axios
            .post('/userLogin/checkLogin', vm.ruleForm, vm.config)
            .then(function(response) {
              if (response.data.status == 'failure') {
                vm.$message({
                  message: response.data.message,
                  type: 'error',
                  center: true
                });
              } else {
                let menuList = response.data.loginUserInfo.menuList;
                let loginPowerInfo = response.data.loginPowerInfo;
                let loginUserShowName = response.data.loginUserShowName;
                let loginUserInfo = response.data.loginUserInfo;
                sessionStorage.setItem('menuList', JSON.stringify(menuList));
                sessionStorage.setItem('userPowerInfo', JSON.stringify(loginPowerInfo));
                sessionStorage.setItem('loginUserShowName', JSON.stringify(loginUserShowName));
                var arr = menuList.filter(function(i) {
                  return i.menuPosition == '3';
                });
                if (arr.length > 0) {
                  function compare(property) {
                    return function(a, b) {
                      var value1 = a[property];
                      var value2 = b[property];
                      return value1 - value2;
                    };
                  }
                  arr = arr.sort(compare('orderNum'));
                  switch (arr[0].menuName) {
                    case '工作台':
                      vm.$router.push('/page/work/default');
                      break;
                    case '项目':
                      vm.$router.push('/page/item');
                      break;
                    case '数据报表':
                      vm.$router.push('/page/report');
                      break;
                    case '合同信息':
                      vm.$router.push('/page/contractInformation');
                      break;
                    case '工作流配置':
                      vm.$router.push('/page/processConfig');
                      break;
                    case '全过程造价':
                      vm.$router.push('/page/wholeProcessCost/projectList');
                      break;
                  }
                } else {
                  // 这个时候左侧边的权限一个都没有
                  vm.$router.push('/page');
                }

                vm.$message({
                  message: response.data.message,
                  type: 'success',
                  center: true
                });
              }
            })
            .catch(function(error) {
              vm.$message({
                message: error,
                type: 'error',
                center: true
              });
            });
          /* 通过SessionStorage 方式的改变值*/
          /*this.$store.commit('setRemark',[this.ruleForm.userAccount,this.ruleForm.password])*/
        } else {
          return false;
        }
      });
    },
    inputFocus(e) {
      /* 获得焦点 */
      let el = e.target;
      el.parentNode.previousElementSibling.className = 'focus-label';
      el.className = 'active';
      el.parentNode.nextElementSibling.className = 'active1';
    }
  }
};
</script>

<style lang="scss">
@mixin linear_bottom {
  background: linear-gradient(to bottom, #06a1ee 0, #4cc2fc 100%) !important;
  background: -moz-linear-gradient(to bottom, #06a1ee 0, #4cc2fc 100%) !important;
  background: -ms-linear-gradient(to bottom, #06a1ee 0, #4cc2fc 100%) !important;
  background: -webkit-linear-gradient(to bottom, #06a1ee 0, #4cc2fc 100%) !important;
}

.login {
  height: 100vh;
  position: relative;
  .left {
    position: absolute;
    width: 50vw;
    height: 100vh;
    left: 0;
    top: 0;
    background: #f1f5f8;
    z-index: -1;
  }

  .box {
    width: 1120px;
    height: 100vh;
    margin: 0 auto;
    background: url('../../assets/image/bg4.jpg') no-repeat center;
    background-size: cover;
    .login-from {
      width:380px;
      height: 400px;
      border-radius: 10px;
      background-color: #fff;
      box-shadow: 0px 0px 15px rgba(176, 192, 202, 0.5);
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      margin: auto;
      .login_box {
        padding: 30px 42px;
        h1 {
          font-size: 24px;
          color: #333333;
        }

        .el-form {
          .el-form-item {
            margin-bottom: 15px;
            position: relative;
            margin-top: 40px;

            label {
              display: block;
              font-size: 14px;
              color: #b2b2b2;
              position: absolute;
              top: 0px;
              z-index: 10;
              transition: all 0.4s linear;
            }

            .focus-label {
              top: -25px;
              font-size: 12px;
            }

            i {
              color: #ff3d4d;
              font-size: 12px;
              text-align: right;
              display: block;
              margin-top: 3px;
            }

            .el-input__inner {
              display: block;
              width: 100%;
              border: none;
              border-bottom: 1px solid #d7d8db;
              padding: 10px 0;
              font-size: 14px;
              color: #333333;
              box-shadow: none;
              border-radius: 0 !important;
              background-color: #fff !important;
            }

            .active {
              border: none;
              background-color: #fff !important;
            }

            span {
              height: 2px;
              width: 0px;
              display: block;
              margin-top: -3px;
            }

            .active1 {
              height: 2px;
              width: 100%;
              @include linear_bottom;
              transition: all 0.5s linear;
            }

            .el-form-item__error {
              border-top: 2px solid #ff3d4d;
              width: 100%;
              margin-top: -2px;
              padding: 5px 0;
              background: none;
              color: #f56c6c !important;
              border-radius: 0;
              text-align: right;
              &::before {
                content: '';
                display: none;
              }
            }

            button {
              color: #fff;
              font-size: 12px;
              display: block;
              width: 165px;
              height: 40px;
              margin: 40px auto 30px;
              border: none;
              border-radius: 5px;
              text-align: center;
              padding: 0;

              span {
                display: inline;
              }
            }
          }
        }
      }
    }
  }

  .right {
    position: absolute;
    width: 50vw;
    height: 100vh;
    right: 0;
    top: 0;
    @include linear_bottom();
    z-index: -1;

    img {
      position: absolute;
      right: 0;
      top: 0;
      z-index: 10;
    }
  }
}

// 	.login {
// 		height: 100vh;
// 		overflow: hidden;
// 		display: flex;
// 		justify-content:center;
// 		align-items:center;
// 		background: #f1f5f8;
// 		.left{
// 			background:#f1f5f8;
// 			left: 0;
// 			width: 40%;
// 			position: absolute;
// 			z-index: 10;
// 		}
//
// 		.box {
// 			width: 1120px;
// 			height: 100vh;
// 			background:url("../../assets/image/bg2.png") no-repeat center center;
// 			background-size: cover;
// 			margin: 0 auto;
// 			position: relative;
// 			z-index: 10;
// 			display: flex;
// 			justify-content:center;
// 			align-items:center;
// 			.login-from {
// 				width: 425px;
// 				border-radius: 10px;
// 				background-color: #fff;
// 				box-shadow: 0px 0px 15px rgba(176, 192, 202, 0.5);
// 				position: absolute;
// 				z-index: 9999;
//
// 				.login_box {
// 					padding: 30px 42px;
//
// 					h1 {
// 						font-size: 24px;
// 						color: #333333;
// 					}
//
// 					.el-form {
// 						.el-form-item {
// 							margin-bottom: 15px;
// 							position: relative;
// 							margin-top: 40px;
// 							label {
// 								display: block;
// 								font-size: 14px;
// 								color: #B2B2B2;
// 								position: absolute;
// 								top: 0px;
// 								z-index: 10;
// 								transition: all 0.4s linear;
// 							}
// 							.focus-label{
// 								top: -25px;
// 								font-size: 12px;
// 							}
// 							i {
// 								color: #FF3D4D;
// 								font-size: 12px;
// 								text-align: right;
// 								display: block;
// 								margin-top: 3px;
// 							}
//
// 							.el-input__inner {
// 								display: block;
// 								width: 100%;
// 								border: none;
// 								border-bottom: 1px solid #d7d8db;
// 								padding: 10px 0;
// 								font-size: 14px;
// 								color: #333333;
// 								box-shadow: none;
// 								border-radius: 0!important;
// 							}
//
// 							.active {
// 								border: none;
// 							}
// 							span {
// 								height: 2px;
// 								width: 0px;
// 								display: block;
// 							}
// 							.active1 {
// 								height: 2px;
// 								width: 100%;
// 								@include linear_bottom;
// 								transition: all 0.5s linear;
// 							}
// 							.el-form-item__error{
// 								border-top: 2px solid #FF3D4D;
// 								width: 100%;
// 								margin-top: -2px;
// 							}
// 							button {
// 								color: #fff;
// 								font-size: 12px;
// 								display: block;
// 								width: 165px;
// 								height: 40px;
// 								margin: 40px auto 30px;
// 								border: none;
// 								border-radius: 5px;
// 								text-align: center;
// 								padding: 0;
//
// 								span{
// 									display: inline;
// 								}
// 							}
// 						}
//
// 					}
//
//
// 				}
// 			}
//
// 		}
//
// 		.right {
// 			height: 100vh;
// 			width: 50%;
// 			@include linear_bottom;
// 			position: absolute;
// 			right: 0;
//
// 			img {
// 				position: absolute;
// 				right: 0;
// 				top: 0;
// 			}
// 		}
// 	}
//
//
//
</style>
