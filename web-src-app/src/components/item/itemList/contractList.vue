<template>
  <!--合同项目列表 -->
  <div class="contractList">
    <div class="search">
      <div>
        <span class="iconfont">&#xe627;</span>
        <input type="search" id="itemNum" class="input" @search="init('search')" @change="init('search')" v-model="projectNum" placeholder="请输入搜索项目编号" />
      </div>
      <p>
        <i>汇总记录：</i>
        <i>合同已收款：{{ collect.incomeReceivedSum }}元</i>
        <i>合同金额:{{ collect.contractAmountSum }}元</i>
      </p>
    </div>
    <more :listarr="listarr" v-if="!!userPowerInfo.projectAdd"></more>
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="1">
      <ul class="tabList" v-for="(item, index) in tabList" :key="index">
        <li>
          <span>
            {{ item.projectNum }}
            <span class="hiden">({{ item.data[0].projectName }})</span>
          </span>
          <span>{{ item.contractCount }}</span>
           <span>{{item.projectIsInvalidFlag=='1'?'作废':item.projectSts == 0 ? '未完成' : '已完成' }}</span>
        </li>
        <li v-for="(i, j) in item.data" :key="j">
          <p @click="routerLink(item,i.contractId)" class="state">{{ i.contractNum }}</p>
          <div>
            <span>{{ i.departmentName}}</span>
            <span>{{ i.isInvalidFlag=='1'?'作废':'--'}}</span>
          </div>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
  </div>
</template>

<script>
import moreRedact from '../../more/moreRedact';
export default {
  data() {
    return {
      tabList: [] /* 列表数据 */,
      projectNum: '' /* 搜索条件 */,
      pageNumber: 0 /* 当前页码 */,
      pageSize: 10 /* 当前页码 */,
      userPowerInfo: this.SStorage.getItem('userPowerInfo'),
      collect: '' /* 汇总记录 */,
      listarr: [
        {
          name: '新增',
          link: '/app/createContract'
        }
      ],
      list: [],
      noMore: false // 是否还有更多
    };
  },
  components: {
    more: moreRedact
  },
  created() {
    /* 请求汇总记录*/
    this.requestPost('/project/query/searchIncomeReceivedSumAndContractAmountSum', {})
      .then(res => {
        if (res.data.status == 'success') {
          this.collect = res.data.dataVO;
        } else {
          this.Toast(res.data.message);
        }
        return;
      })
      .catch(err => {
        this.Toast(err);
      });
  },
  methods: {
    compare(property) {
      return function(a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value2 - value1;
      };
    },
    init(type) {
      if (type == 'search') {
        this.patientInfo = {
          pageNumber: 1,
          pageSize: this.pageSize,
          projectNum: this.projectNum
        };
        this.list = [];
      } else {
        this.patientInfo = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          projectNum: this.projectNum
        };
      }
      /* 页面初始化请求数据 */
      this.requestPost('/project/query/searchProjectList', this.patientInfo)
        .then(res => {
          if (res.data.status == 'success') {
            this.totle = res.data.pagedData.dataCount;
            var arr = res.data.pagedData.dataList;
            const vm = this;
            arr.forEach(function(i) {
              vm.list.push(i);
            });
            this.tabList = this.tabChange(this.list);
          } else {
            this.Toast(res.data.message);
          }
          return;
        })
        .catch(err => {
          console.log(err);
        });
    },
    tabChange(list) {
      /* 数据格式化 */
      var map = {};
      var dest = [];
      for (var i = 0; i < list.length; i++) {
        var ai = list[i];
        if (!map[list[i].projectNum]) {
          dest.push({
            projectNum: ai.projectNum,
            contractCount: ai.contractCount,
            projectId: ai.projectId,
            projectIsInvalidFlag:ai.projectIsInvalidFlag,
            projectSts:ai.projectSts,
            data: [ai]
          });
          map[ai.projectNum] = ai;
        } else {
          for (var j = 0; j < dest.length; j++) {
            var dj = dest[j];
            if (dj.projectNum == ai.projectNum) {
              dj.data.push(ai);
              break;
            }
          }
        }
      }
      return dest;
    },
    routerLink(list,businessId) {
      // 赋值选中节点标识
      list.businessId = businessId;
      this.SStorage.setItem('/app/foundItem', list);
      this.$router.push({ path: '/app/foundItem' });
    },
    loadMore() {
      this.pageNumber += 1;
      if (this.list.length === this.totle) {
        this.noMore = true;
      } else {
        this.init();
        this.noMore = false;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.contractList {
  width: 100%;
  .search {
    p {
      width: 88%;
      margin: 20px auto 0;
      color: #666666;
      i {
        font-style: normal;
        margin-right: 20px;
        font-size: 22px;
      }
    }
  }
  .tabList {
    margin-top: 30px;
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
    li {
      color: #666666;
      border-bottom: 2px solid #e5e5e5;
      margin: 0 30px;
      padding: 30px 0;
      font-size: 28px;
      border-radius: 0;
      .state{
        color: #06a1ee;
      }
      .hiden {
        width: 320px;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      p {
        line-height: 40px;
      }
      div {
        display: flex;
        justify-content: space-between;
        padding: 30px 0 0;
      }
    }
    li:first-child {
      display: flex;
      justify-content: space-between;
      color: #333;
    }
    li:last-child {
      border-bottom: none !important;
    }
  }
}
</style>
