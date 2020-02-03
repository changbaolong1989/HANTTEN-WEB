<template>
  <div class="m-page" v-loading="loading">
    <el-aside class="aside">
      <img src="../../assets/image/aside-logo.png" class="aside-logo" alt="" />
      <el-menu :default-active="activeIndex" class="el-menu-demo aside-menu" mode="horizontal" @select="getNavDisplay">
        <el-menu-item index="work" v-show="menu01">
          <i class="iconfont">&#xe60c;</i>
          <p slot="title">工作台</p>
        </el-menu-item>
        <el-menu-item index="item" v-show="menu02">
          <i class="iconfont">&#xe60d;</i>
          <p slot="title">项目</p>
        </el-menu-item>
        <el-menu-item index="report" v-show="menu03">
          <i class="iconfont">&#xe60b;</i>
          <p slot="title">数据报表</p>
        </el-menu-item>
        <el-menu-item index="processConfig" v-show="menu20">
          <i class="iconfont">&#xe60b;</i>
          <p slot="title">工作流配置</p>
        </el-menu-item>
        <el-menu-item index="contractInformation" v-show="menu21">
          <i class="iconfont">&#xe60b;</i>
          <p slot="title">合同信息</p>
        </el-menu-item>
        <el-menu-item index="projectList" v-show="menu22">
          <i class="iconfont">&#xe60b;</i>
          <p slot="title">全过程造价</p>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container class="el-container-main" style="flex-direction: column;">
      <m-head ref="child"></m-head>
      <div class="content-body">
        <el-breadcrumb separator-class="el-icon-arrow-right" class="item-bread">
          <i class="icon icon-breadcrumb"></i>

          <template v-for="(item, index) in breadcrumb">
            <el-breadcrumb-item :key="index">{{ item }}</el-breadcrumb-item>
          </template>
        </el-breadcrumb>
        <router-view class="view"></router-view>
      </div>
    </el-container>
  </div>
</template>

<script>
import mHead from '../head/m-head.vue';

export default {
  components: {
    'm-head': mHead
  },
  mounted: function() {
    this.menuList = JSON.parse(sessionStorage.getItem('menuList'));
    var arr = this.menuList.filter(function(i) {
      return i.menuPosition == '3';
    });
    if (arr.length == 0) {
      this.nodata = true;
    } else {
      this.nodata = false;
    }
    for (var i = 0; i < this.menuList.length; i++) {
      if (this.menuList[i].menuId === 'menu_01') {
        this.menu01 = true;
      }
      if (this.menuList[i].menuId === 'menu_02') {
        this.menu02 = true;
      }
      if (this.menuList[i].menuId === 'menu_03') {
        this.menu03 = true;
      }
      if (this.menuList[i].menuId === 'menu_20') {
        this.menu20 = true;
      }
      if (this.menuList[i].menuId === 'menu_21') {
        this.menu21 = true;
      }
      if (this.menuList[i].menuId === 'menu_22') {
        this.menu22 = true;
      }
    }
    this.selectRouter();

    let vm = this;
    vm.bus.$off('searchMessageDetailInfoFunc');
    // 查询消息集合中的最大创建时间的数据信息
    vm.bus.$on('searchMessageDetailInfoFunc', function() {
      vm.$nextTick(function() {
        vm.searchMessageDetailInfo();
      });
    });
  },
  updated: function() {
    this.selectRouter();
  },
  data() {
    return {
      activeIndex: '',
      breadcrumb: [],
      notify: '',
      loading: false,
      menuList: [],
      menu01: false,
      menu02: false,
      menu03: false,
      menu20: false,
      menu21: false,
      menu22: false,
      // 创建时间
      createDate: '',
      // 定时器对象
      interval: null,
      nodata: false
    };
  },
  created() {
    // 查询消息集合中的最大创建时间的数据信息
    this.searchMessageDetailInfo();
  },
  // 实例销毁之前调用
  beforeDestroy() {
    // 如果有弹窗，则清除
    if (this.notify) {
      this.notify.close();
    }
    // 清除定时器
    this.clearIntervalFunc();
  },
  watch: {
    $route(to, from) {
      if (this.$route.path == '/page/inform' && this.notify) {
        this.notify.close();
      }
    }
  },
  methods: {
    getNavDisplay(index) {
      /*控制路由导航显示*/
      this.$refs.child.hideselect();
      let vm = this,
        path = '';
      if (/work/g.test(index)) path = '/page/work/default'; /* 工作台 */
      if (/item/g.test(index)) path = '/page/item'; /* 项目 */
      if (/report/g.test(index)) path = '/page/report'; /* 数据报表 */
      if (/contractInformation/g.test(index)) path = '/page/contractInformation'; /* 合同信息 */
      if (/processConfig/g.test(index)) path = '/page/processConfig'; /* 工作流配置 */
      if (/projectList/g.test(index)) path = '/page/wholeProcessCost/projectList'; /* 全过程造价 */
      vm.$router.push(path);
    },
    selectRouter() {
      /* 控制路由的激活状态 */
      let fullPath = this.$router.current.fullPath,
        path = '';
      if (/work/g.test(fullPath)) path = 'work';
      if (/item/g.test(fullPath)) path = 'item';
      if (/report/g.test(fullPath)) path = 'report';
      if (/contractInformation/g.test(fullPath)) path = 'contractInformation';
      if (/processConfig/g.test(fullPath)) path = 'processConfig';
      if (/projectList/g.test(fullPath)) path = 'projectList';
      this.activeIndex = path;
      let matched = this.$router.current.matched;
      this.breadcrumb = matched[matched.length - 1].meta.navDisplay || [];
    },
    // 消息提醒
    open() {
      const vm = this;
      // 如果有弹窗，则清除
      if (this.notify) {
        this.notify.close();
        // 清除定时器
        this.clearIntervalFunc();
      }
      this.notify = this.$notify({
        dangerouslyUseHTMLString: true,
        message: '<p><i class="icon icon-pink"></i>您有新消息，快来查看吧!</p>',
        duration: 0,
        position: 'bottom-right',
        onClick: function() {
          vm.$router.push('/page/inform');
          this.close();
        },
        // 关闭回调函数
        onClose: function() {
          // 设置定时器
          vm.setIntervalFunc();
        }
      });
    },
    // 查询消息集合中的最大创建时间的数据信息
    searchMessageDetailInfo() {
      let vm = this;


      // 发送请求
      this.apiAddress('/message/query/searchMessageDetailInfo', {})
        .then(res => {
          if (res.data.status === 'failure') {
            this.stateDialog('error', res.data.message);
          } else {

            // 消息对象
            let dataVO = res.data.dataVO;

            // 如果查询出的时间比页面中存储的时间大，并且页面中的时间不能为空
            if (
              ((new Date(dataVO.createDate) >= new Date(vm.createDate) && vm.createDate !== '') ||
                // 初次进入页面，当页面中的时间为空，并且数据库中查询出的时间不为空
                (vm.createDate === '' && dataVO.createDate !== '')) &&
              // 如果有数据
              Object.keys(dataVO).length !== 0
            ) {

              // 显示通知小红点
              vm.$refs.child.hidden = false;

              // 弹出通知提示
              vm.open();

              // 清除定时器
              vm.clearIntervalFunc();

              // 用数据库查询出来的值，替换掉页面中存储的值
              vm.createDate = dataVO.createDate;
            } else {

              // 隐藏通知小红点
              vm.$refs.child.hidden = true;

              // 清除定时器
              vm.clearIntervalFunc();

              // 设置定时器
              vm.setIntervalFunc();
            }
          }
        })
        .catch(err => {
          this.stateDialog('error', err);
        });
    },
    // 定时器
    setIntervalFunc() {
      let vm = this;
      // 设置定时器
      vm.interval = setInterval(function() {
        // 查询消息集合中的最大创建时间的数据信息
        vm.searchMessageDetailInfo();
      }, 1000 * 60);
    },
    // 清除定时器
    clearIntervalFunc() {
      let vm = this;
      // 如果有定时器
      if (vm.interval) {
        // 清除定时器
        clearInterval(vm.interval);
      }
    }
  }
};
</script>

<style lang="scss">
.m-page .aside {
  width: 111px !important;
  height: 100vh;
  background: url(../../assets/image/aside-banner.jpg) no-repeat;
  background-size: cover;
  box-shadow: 0 0 20px rgba(12, 43, 58, 0.7);
  position: fixed;
  left: 0;
}

.m-page .aside-logo {
  display: block;
  margin: 22px auto;
  margin-bottom: 0;
}

.m-page .m-head {
  position: fixed;
  height: 60px;
  width: calc(100vw - 111px);
  top: 0;
  right: 0;
}

.m-page .aside-menu {
  font-size: 12px;
  color: #acb3bc;
  margin-top: 52px;
}

.m-page .el-aside {
  z-index: 1999;
}

.m-page .el-menu {
  background-color: transparent;
}

.m-page .el-menu.el-menu--horizontal {
  border-bottom: none;
}

.m-page .el-menu--horizontal > .el-menu-item {
  border-bottom: none !important;
  background-color: rgba(255, 255, 255, 0);
  border-radius: 10px;
  float: none;
  margin: auto;
  margin-bottom: 15px;
  padding: 20px 0;

  i {
    font-size: 24px;
    color: #acb3bc;
  }

  p {
    margin: 15px 0;
    color: #acb3bc;
  }
}

.m-page .aside-menu li {
  line-height: 12px;
  height: 80px;
  width: 80px;
  text-align: center;
  border-radius: 10px;
}

.m-page .aside-menu li:hover,
.m-page .aside-menu li.is-active,
.el-menu--horizontal > .el-menu-item:not(.is-disabled):focus,
.el-menu--horizontal > .el-menu-item:not(.is-disabled):hover {
  background-color: rgba(255, 255, 255, 0.17);
  color: #fff;
  i {
    color: #fff;
  }
  p {
    color: #fff;
  }
}

.m-page .aside-menu li:hover i,
.m-page .aside-menu li.is-active p {
  color: #fff;
}

.m-page .aside-menu li .icon {
  display: block;
  margin: auto;
  margin-bottom: 10px;
}

.m-page .view {
  padding: 0px 25px;
}

.m-page {
  .item-bread {
    padding: 20px 25px;

    .el-breadcrumb__item {
      & .el-breadcrumb__inner.is-link,
      .el-breadcrumb__inner {
        color: #999999;
      }

      &:last-child {
        & .el-breadcrumb__inner {
          color: #333333;
        }
      }
    }
  }
}

.m-page .icon-breadcrumb {
  width: 16px;
  height: 14px;
  display: inline-block;
  float: left;
  background-position: -62px -62px;
  margin: 0 13px;
}

.m-page .el-breadcrumb-item {
  font-size: 14px;
  font-weight: 400;
  color: rgba(51, 51, 51, 1);
}

/*el-container  */
.m-page .el-container-main {
  margin-left: 111px;
  padding-top: 60px;
  height: 100vh;

  .content-body {
    height: calc(100vh - 60px);
    overflow: auto;
    position: relative;
  }
}

/* 消息通知 */
.icon-pink {
  width: 10px;
  height: 10px;
  background-position: -558px -105px !important;
  margin-right: 12px;
}

.el-notification.right {
  position: fixed;
  cursor: pointer;
}
</style>
