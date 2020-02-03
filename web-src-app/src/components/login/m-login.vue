<template>
  <div class="login">
    <div class="content">
      <img src="../../assets/img/login-bg2.png" class="contentImg" alt="" />
      <form @submit.prevent="submitForm">
        <mt-field placeholder="请输入您的账号" v-model="patientInfo.userAccount" v-validate="'required'" name="name" data-vv-as="账号">
          <span class="iconfont">&#xe619;</span>
        </mt-field>
        <span class="validaerr">{{ errors.first('name') }}</span>
        <mt-field placeholder="请输入您的密码" type="password" v-model="patientInfo.password" v-validate="'required'" name="pass" data-vv-as="密码">
          <span class="iconfont">&#xe61a;</span>
        </mt-field>
        <span class="validaerr">{{ errors.first('pass') }}</span>
        <mt-button class="linear">登录</mt-button>
      </form>
    </div>
    <div class="loginFooter">Copyright©2019 北京汉腾工程顾问有限公司版权所有</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patientInfo: {
        userAccount: '',
        password: ''
      }
    };
  },
  created(){
    this.SStorage.clear()
  },
  mounted() {

    var userAccount = this.getCookie("userAccount");

    console.log("userAccount:",userAccount);

    console.log("usernull:",userAccount != null);
    console.log("usernull:",userAccount != "");
    console.log("usernull:",typeof(userAccount));

    if(userAccount != "" && userAccount != null){
      this.noPasswordLogin();
    }

  },
  methods: {
    noPasswordLogin(){

      this.requestPost('/userLogin/getLoginInfo', {"openId":this.getCookie("openid")})
        .then(res => {
          if (res.data.status == 'success') {
            this.SStorage.setItem('userPowerInfo',res.data.loginPowerInfo);
            this.SStorage.setItem('menuList',res.data.loginUserInfo.menuList);
            var menuList = res.data.loginUserInfo.menuList;
            var arr = [];
            menuList.sort(function(a,b){
              return a.orderNum-b.orderNum;
            })
            menuList.forEach(function(i){
              arr.push(i.menuName)
            })
            for(var i=0;i<arr.length;i++){
              if(arr[i]=="工作台"){
                this.$router.push('/app/workbench');
                break;
              }else if(arr[i]=="项目"){
                this.$router.push('/app/projectList');
                break;
              }else if(arr[i]=="数据报表"){
                this.$router.push('/app/report');
                break;
              }else if(arr[i]=="合同信息"){
                this.$router.push('/app/contractInformation/default');
                break;
              }else if(arr[i]=="全过程造价"){
                this.$router.push('/app/overallProcess');
                break;
              }
            }
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });

    },
    getCookie(name) {
      var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
      if(arr=document.cookie.match(reg))
      return unescape(arr[2]);
      else
      return null;
    },
    submitForm() {
      this.$validator.validateAll().then(result => {
        if (result) {
          this.requestPost('/userLogin/checkLogin', this.patientInfo)
            .then(res => {
              if (res.data.status == 'success') {
                this.SStorage.setItem('userPowerInfo',res.data.loginPowerInfo);
                this.SStorage.setItem('menuList',res.data.loginUserInfo.menuList);
                var menuList = res.data.loginUserInfo.menuList;
                var arr = [];
                menuList.sort(function(a,b){
                  return a.orderNum-b.orderNum;
                })
                menuList.forEach(function(i){
                  arr.push(i.menuName)
                })
                for(var i=0;i<arr.length;i++){
                  if(arr[i]=="工作台"){
                    this.$router.push('/app/workbench');
                    break;
                  }else if(arr[i]=="项目"){
                    this.$router.push('/app/projectList');
                    break;
                  }else if(arr[i]=="数据报表"){
                    this.$router.push('/app/report');
                    break;
                  }else if(arr[i]=="合同信息"){
                    this.$router.push('/app/contractInformation/default');
                    break;
                  }else if(arr[i]=="全过程造价"){
                    this.$router.push('/app/overallProcess');
                    break;
                  }
                }
              } else {
                this.Toast(res.data.message);
              }
              return;
            })
            .catch(err => {
              console.log(err);
            });
        } else {
        }
      });
    }
  }
};
</script>
<style lang="scss">
.login {
  .mint-field-other {
    position: absolute;
    left: 0;
    top: 20px;
    width: 36px;
    height: 36px;
    text-align: center;
  }
  .mint-cell-wrapper {
    font-size: 28px !important;
  }
  .mintui {
    font-size: 28px !important;
  }
}
</style>
<style scoped lang="scss">
.login {
  width: 100%;
  height: 100vh;
  background: url(../../assets/img/login-bg1.png) no-repeat center;
  background-size: 100% 100vh;
  overflow: hidden;

  .content {
    width: 82%;
    height: 821px;
    background-color: #ffffff;
    box-shadow: 0px 15px 43px 0px rgba(0, 122, 182, 0.36);
    border-radius: 20px;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;

    .contentImg {
      width: 440px;
      height: 70px;
      display: block;
      margin: 98px auto 0;
    }

    form {
      width: 440px;
      margin: 100px auto;

      .mint-cell {
        border-bottom: 1px solid #b0b0b0;
        color: #333333;
        padding: 10px 0;
        margin: 20px 0 20px;
        padding-left: 36px;
        position: relative;
        .iconfont {
          font-size: 28px;
          color: #b0b0b0;
        }
      }

      .mint-button {
        color: #fff;
        width: 340px;
        height: 74px;
        border-radius: 10px;
        margin: 120px auto 0;
        display: block;
        font-size: 32px;
        color: #fafafb;
        box-shadow: 0px 15px 43px 0px rgba(0, 122, 182, 0.36);
      }
    }
  }

  .loginFooter {
    font-size: 24px;
    color: #9edfff;
    opacity: 0.5;
    position: fixed;
    bottom: 90px;
    text-align: center;
    width: 100%;
  }
}
</style>
