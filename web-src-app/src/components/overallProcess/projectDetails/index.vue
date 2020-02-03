<template>
  <!-- 全过程详情主页面 -->
  <div class="projectDetails">
    <!-- 左侧栏-->
    <transition name="slide-fade">
      <div class="contractAside" v-if="state">
        <div class="treeShow">
          <tree-overall :dataVO="treeData"></tree-overall>
          <i class="iconfont iconshouqi-" @click="state = false"></i>
        </div>
      </div>
    </transition>
    <div class="foundItemIndex">
      <ul class="contractList">
        <li @click="state = true">
          <span>全部项目-{{ details.projectNum }}</span>
          <i class="iconfont">&#xe630;</i>
        </li>
      </ul>
      <div class="exhibition"><div :is="current"></div></div>
    </div>
  </div>
</template>

<script>
import treeOverall from './tree-overall'; /* 左侧边栏 */
import estimate from './estimate/index'; /*估算*/
import budget from './budget/index'; /*概算*/
import dynamicCost from './dynamicCost/index';// 动态成本管理
import contractPlanning from './contractPlanning/index'; /*合约规划*/
import biddingManagement from './biddingManagement/index'; /*招标管理*/
import ClearParameter from './ClearParameter/index'; /*清标台账*/
import contractParameter from './contractParameter/index'; /*合同台账*/
import changepManagementStanding from './changepManagement/standing/index'; /*变更台账*/
import changepManagementDetail from './changepManagement/detail/index'; /*变更明细*/
import claimParameter from './claimParameter/index'; /*索赔台账*/
import paymentParameter from './paymentParameter/index'; /*付款台账*/
import settlementParameter from './settlementParameter/index'; /*结算台账*/
import Issue from './Issue/index'; /*发出信函登记表*/
import minutes from './minutes/index'; /*会议纪要*/
export default {
  data() {
    return {
      treeData: [],
      state: false /*, 左侧菜单显示 */,
      current: '',
    };
  },
  components: {
    'tree-overall': treeOverall,
    estimate: estimate,
    budget: budget,
    dynamicCost:dynamicCost,
    contractPlanning: contractPlanning,
    biddingManagement: biddingManagement,
    ClearParameter: ClearParameter,
    contractParameter: contractParameter,
    claimParameter: claimParameter,
    paymentParameter: paymentParameter,
    changepManagementStanding: changepManagementStanding,
    changepManagementDetail: changepManagementDetail,
    settlementParameter: settlementParameter,
    Issue: Issue,
    minutes: minutes,
  },
  created() {
    this.details = this.SStorage.getItem(this.$route.path);
    this.projectId = this.details.projectId;
  },
  mounted() {
    const vm = this;
    this.init();
    this.bus.$on('changeList', function(val) {
      vm.revealList(val);
    });
  },
  methods: {
    init() {
      const vm = this;
      let formData = {
        projectNum: vm.details.projectNum
      };
      let url = '/wholeProcess/project/query/searchProjectPeriodList';
      vm.requestPost(url, formData)
        .then(res => {
          if (res.data.status == 'failure') {
           this.Toast(res.data.message);
          } else {
            const table = res.data.dataList;
            vm.treeData = table;
          }
        })
        .catch(err => {
          this.Toast(res.data.message);
        });
    },
    revealList(val) {
      if (val.label == '估算') {
        this.current = 'estimate';
      } else if (val.label == '概算') {
        this.current = 'budget';
      } else if (val.label == '动态成本管理') {
        this.current = 'dynamicCost';
      } else if (val.label == '合约规划') {
        this.current = 'contractPlanning';
      } else if (val.label == '招标管理') {
        this.current = 'biddingManagement';
      }else if (val.label == '清标台账') {
        this.current = 'ClearParameter';
      }else if (val.label == '合同台账') {
        this.current = 'contractParameter';
      }else if (val.label == '变更台账') {
        this.current = 'changepManagementStanding';
      }else if (val.label == '变更明细') {
        this.current = 'changepManagementDetail';
      }else if (val.label == '索赔台账') {
        this.current = 'claimParameter';
      }else if (val.label == '付款台账') {
        this.current = 'paymentParameter';
      }else if (val.label == '结算台账') {
        this.current = 'settlementParameter';
      }else if (val.label == '发出信函登记表') {
        this.current = 'Issue';
      }else if (val.label == '会议纪要') {
        this.current = 'minutes';
      }
      this.state = false;
    },
    selectGetName(key,list){
      console.log("list",list)
      for (var i = 0; i < list.length; i++) {
        if (list[i].value==key) {
          return list[i].label
        }
      }
    },
  }
};
</script>
<style lang="scss">
.slide-fade-enter-active {
  /* 进入 */
  transition: all 0.8s ease;
}
.slide-fade-leave-active {
  /* 离开 */
  transition: all 0.8s linear;
}
.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateY(-900px);
}
</style>
<style lang="scss" scoped>
.projectDetails {
  width: 100%;
  background-color: #eff3f5;
  .contractAside {
    width: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    height: 100vh;
    position: fixed;
    z-index: 200;
    .treeShow {
      width: 100%;
      background-color: #fff;
      overflow: hidden;
      height: 900px;
      overflow: auto;
      .iconshouqi- {
        position: absolute;
        bottom: 10px;
        color: #fff;
        font-size: 35px;
        left: 50%;
        right: 50%;
      }
    }
  }
  .foundItemIndex {
    overflow: hidden;

    .contractList {
      box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
      background-color: #fff;
      li {
        height: 80px;
        line-height: 80px;
        display: flex;
        padding: 0px 30px;
        justify-content: space-between;
        font-size: 28px;
        color: #333333;
        i {
          font-size: 28px;
        }
      }
    }
    .exhibition {
      width: 94%;
      margin: 20px auto;
      border-radius: 10px;
      ul {
        width: 92%;
        margin: 0 auto;
        overflow: hidden;
        li {
          margin: 30px 0;
          display: flex;
          justify-content: space-between;
          font-size: 28px;
        }
      }
    }
    .surface {
      width: 100%;
      overflow: hidden;
    }
  }
}
</style>
