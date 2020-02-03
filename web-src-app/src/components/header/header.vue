<template>
  <div class="header">
    <mt-tabbar v-model="selected">
      <mt-tab-item id="/app/workbench" v-if="workbench">
        <router-link to="/app/workbench" tag="div">
          <span class="iconfont">&#xe61b;</span>
          工作台
        </router-link>
      </mt-tab-item>
      <mt-tab-item id="/app/projectList" v-if="projectList">
        <router-link to="/app/projectList" tag="div">
          <span class="iconfont">&#xe61d;</span>
          项目
        </router-link>
      </mt-tab-item>

      <mt-tab-item id="/app/report" v-if="report">
        <router-link to="/app/report" tag="div">
          <span class="iconfont">&#xe61e;</span>
          数据列表
        </router-link>
      </mt-tab-item>

      <mt-tab-item id="/app/contractInformation/default" v-if="contractInformation">
        <router-link to="/app/contractInformation/default" tag="div">
          <span class="iconfont">&#xe61e;</span>
          合同信息
        </router-link>
      </mt-tab-item>

      <mt-tab-item id="/app/overallProcess" v-if="overallProcess">
        <router-link to="/app/overallProcess" tag="div">
          <span class="iconfont">&#xe63e;</span>
          全过程造价
        </router-link>
      </mt-tab-item>

 <!--     <mt-tab-item>
        <div @click="quit">
          <span class="iconfont">&#xe644;</span>
          退出
        </div>
      </mt-tab-item> -->
    </mt-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selected: this.$route.path,
      menuList: this.SStorage.getItem('menuList'),
      workbench:false,
      projectList:false,
      report:false,
      contractInformation:false,
      overallProcess:false,
    };
  },
  created(){
    var menuList = this.SStorage.getItem('menuList');
    for(var i=0;i<menuList.length;i++){
      if(menuList[i].menuName=="工作台"){
        this.workbench = true;
      }else if(menuList[i].menuName=="项目"){
        this.projectList = true;
      }else if(menuList[i].menuName=="数据报表"){
        this.report = true;
      }else if(menuList[i].menuName=="合同信息"){
        this.contractInformation = true;
      }else if(menuList[i].menuName=="全过程造价"){
        this.overallProcess = true;
      }
    }
  },
  watch: {
    $route: function(to, go) {
      this.selected = to.path;
    }
  },
  methods: {
    /* 退出*/
    quit() {
    	const vm = this;
    	vm.MessageBox.confirm('', {
    	  message: '您确定退出吗?',
    	  title: '提示',
    	  showCancelButton: true
    	}).then(action => {
    		vm.requestPost('/userLogout/logout', {})
    		.then(res => {
    			if (res.data.status == 'failure') {
    				this.Toast(res.data.message);
    			} else {
    				vm.$router.push('/app/login')
    			}
    		})
    		.catch(err => {
    			this.Toast(res.data.message);
    		})
    	})
    	.catch(function(error) {
    		this.Toast(error);
    	});

    },
  }
};
</script>
<style lang="scss">
.header {
  overflow: hidden;
  .mint-tab-item {
    border: none;
  }
  .mint-tab-item-label {
    font-size: 28px;
  }
}
</style>
<style scoped lang="scss">
.header {
  overflow: auto;
  min-width: 100%;
  max-width: 150%;
  .mint-tabbar {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
    position: fixed;
    z-index: 10000;
    min-width: 100%;
    max-width: 150%;
    overflow: auto;
  }
  .mint-tab-item {
    min-width: 20%;
    padding: 13px 0;
    line-height: 27px;
    color: #b2b2b2;
    border: none;
    .iconfont {
      font-size: 50px;
      display: block;
      margin-bottom: 7px;
    }
  }
  .mint-tab-item.is-selected {
    background: #fafafa;
    color: #06a1ee;
  }
}
</style>
