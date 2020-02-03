<template>
  <!-- 更多操作-->
  <div class="moreRedact" @touchmove="touch($event)" v-if="list.length > 0">
    <div class="radius linear" @click="shade = true"><span class="iconfont">&#xe61f;</span></div>
    <div class="redact" v-if="shade" @click.self="shade = false">
      <div class="content">
        <p class="title">更多操作</p>
        <ul>
          <li v-for="(item, index) in list" :key="index" @click="routerLink(item)">
            <p>{{ item.name }}</p>
            <i class="iconfont">&#xe630;</i>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      shade: false,
      list: []
    };
  },
  props: {
    listarr: Array,
    searchProp: String,
    projectId:String
  },
  created() {
    this.list = this.listarr;
  },
  watch: {
    listarr(newval, oldval) {
      this.list = newval;
    }
  },
  methods: {
    routerLink(item) {
      const vm = this;
      if (item.link == '') {
        this.$emit('rightShow', item);
      } else if (item.link == '数据报表') {
        // 导出列表数据
        const elemIF = document.createElement('iframe');
        elemIF.src = '/reportForm/exprot/exportPerformanceReport?realFileName=' + '绩效报表.xls' + '&month=' + this.searchProp;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      } else if (item.link == '任务台账') {
        // 导出任务台账数据
        const elemIF = document.createElement('iframe');
        elemIF.src = '/project/export/excExportParticipantsInfoList?projectId=' + vm.projectId
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      } else if (item.link == '成果文件台账') {
       // 导出成果文件台账数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/project/export/excExportResultFileLedgerInfoList?projectId=' + vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '估算') {
        // 导出估算数据
        const elemIF = document.createElement('iframe');
        elemIF.src = '/wholeProcess/assess/exprot/exportAssessData?realFileName=' + '估算台账.xls' + '&projectId=' + vm.projectId;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      } else if (item.link == '概算') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/estimate/export/exportEstimateData?realFileName=' + "概算.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '动态成本') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/cost/export/exportDynamicCostData?realFileName=' + "动态成本.xlsx" + "&projectId=" + vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '合约规划') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/treaty/export/exportTreatyData?realFileName=' + "合约规划.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '招标管理') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/tender/export/exportTenderData?realFileName=' + "招标管理.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '清标台账') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/tender/export/exportCleanTenderData?realFileName=' + "清标台账.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '合同台账') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/contract/export/exportContractData?realFileName=' + "合同台账.xlsx" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '变更明细') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/changeDetail/export/exportChangeDetailData?realFileName=' + "变更洽商签证台帐.xlsx" + "&projectId="  +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '变更台账') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/changeStandingBook/export/exportChangeStandingBookData?realFileName=' + "变更台账.xlsx" + "&projectId="  +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '索赔台账') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/claim/export/exportClaimData?realFileName=' + "索赔台账.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '付款台账') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/pay/export/exportPayData?realFileName=' + "付款台账.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '结算台账') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/statement/export/exportStatementData?realFileName=' + "结算台账.xlsx" + "&projectId="  +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '发出信函登记表') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/letter/export/exportLetterData?realFileName=' + "发出信函登记表.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else if (item.link == '会议纪要') {
       // 导出列表数据
       const elemIF = document.createElement('iframe');
       elemIF.src = '/wholeProcess/meeting/export/exportMeetingData?realFileName=' + "会议纪要.xls" + "&projectId=" +  vm.projectId;
       elemIF.style.display = 'none';
       document.body.appendChild(elemIF);
      } else {
        this.$router.push({ path: item.link });
      }
    },
    touch(e) {
      e.preventDefault();
    }
  }
};
</script>
<style lang="scss">
.moreRedact {
  .radius {
    .iconfont {
      font-size: 24px;
    }
  }
}
</style>
<style scoped lang="scss">
.moreRedact {
  position: relative;
  z-index: 10000;
  .iconfont {
    &:before {
      background-image: -webkit-linear-gradient(90deg, #06a1ee 0%, #4cc2fc 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      font-size: 50px !important;
    }
  }

  .radius {
    position: fixed;
    right: 20px;
    bottom: 170px;
    width: 76px;
    height: 76px;
    color: #fff;
    text-align: center;
    border-radius: 50%;
    line-height: 76px;
  }
  .redact {
    position: fixed;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 100000;
    width: 100%;
    height: 100vh;
    top: 0;
    .content {
      max-height: 1000px;
      width: 100%;
      background-color: #fff;
      position: absolute;
      bottom: 0;
      z-index: 100;
      .title {
        font-size: 28px;
        line-height: 36px;
        color: #b3b3b3;
        padding: 25px 30px;
        box-shadow: 0 0 50px 0px rgba(0, 0, 0, 0.125);
      }
      ul {
        li {
          width: 92%;
          margin-top: 30px;
          display: flex;
          justify-content: space-between;
          border-bottom: 2px solid #e5e5e5;
          margin: 0 auto;
          padding: 30px 0;
          &:last-child {
            border: none;
          }
          p {
            font-size: 28px;
            line-height: 36px;
            color: #333333;
          }
          i {
            font-size: 28px;
            color: #979797;
          }
        }
      }
    }
  }
}
</style>
