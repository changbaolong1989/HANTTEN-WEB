<template>
  <div class="overallProcess">
    <!-- 全过程 -->
    <div class="search">
      <span class="iconfont">&#xe627;</span>
      <input type="search" id="itemNum" class="input" @search="init('search')" @change="init('search')" v-model="projectNum" placeholder="请输入搜索项目编号" />
    </div>
    <div v-infinite-scroll="loadMore" infinite-scroll-distance="10">
      <ul class="tabList" v-for="(item, index) in tabList" :key="index">
        <li>
          <span class="state" @click="routerLink(item)">{{ item.projectNum }}<span class="hiden">({{ item.data[0].projectName }})</span></span>
          <span>{{ item.contractCount }}</span>
        </li>
        <li v-for="(i, j) in item.data" :key="j">
          <p>{{ i.contractNum }}</p>
          <div>
            <span>{{ i.departmentName }}</span>
            <span>{{ i.contractAmount }}</span>
          </div>
        </li>
      </ul>
      <div class="no-more" v-if="noMore">没有更多了~</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tabList: [
        {
          projectNum: 'HT10003-bl',
          contractCount: 2,
          data: [
            {
              contractNum: 'HT10003',
              departmentName: '造价部',
              projectSts: 1000
            }
          ]
        }
      ],
      projectNum: '' /* 搜索条件 */,
      pageNumber: 0 /* 当前页码 */,
      pageSize: 10 /* 当前页码 */,
      list: [],
      noMore: false // 是否还有更多
    };
  },
  methods: {
    init(type) {
      if (type == 'search') {
        this.patientInfo = {
          pageNumber: 1,
          pageSize: this.pageSize,
          projectNum: this.projectNum,
          departmentId: "",
          projectSts: "",
          timeList: []
        };
        this.list = [];
      } else {
        this.patientInfo = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          timeList: []
        };
      }
      /* 页面初始化请求数据 */
      this.requestPost('/wholeProcess/project/query/searchContractList', this.patientInfo)
        .then(res => {
          if (res.data.status == 'success') {
            this.totle = res.data.dataCount;
            var arr = res.data.dataList;
            const vm = this;
            arr.forEach(function(i) {
              vm.list.push(i);
            });
            this.tabList = this.tabChange(this.list);
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
    routerLink(list) {
      this.SStorage.setItem('/app/projectDetails', list);
      this.$router.push({ path: '/app/projectDetails' });
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

<style scoped lang="scss">
.overallProcess {
  width: 100%;
  background-color: #eff3f5;
  .search {
    box-shadow: 0 0 50px rgba(0, 0, 0, 0.125);
  }
  .tabList {
    margin-top: 30px;
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
    .hiden {
      width: 320px;
      display: inline-block;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    li {
      color: #666666;
      border-bottom: 2px solid #e5e5e5;
      margin: 0 30px;
      padding: 30px 0;
      font-size: 28px;
      border-radius: 0;
      p {
        line-height: 40px;
      }
      .state {
        color: #06a1ee;
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
