<!--通用分页组件 @editor cbl-->
<!--
使用方法：
在父组件中import当前组件，父组件props:total和queryUrl，父组件接收pagingQueryData方法
-->
<template>
  <div>
    <div class="block item-fen" v-show="total>0">
      <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize"
                     layout="total, prev, pager,next,sizes,jumper"
                     :total="total">
      </el-pagination>
      <el-button class="linear" type="primary" size="small">确定</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "common-paging",
    data() {
      return {
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100] //条目列表
      }
    },
    mounted() {
      this.bus.$on('refersPagingData', () => {
        this.refers();
      });
    },
    props: ['total', 'queryUrl', 'queryCondition'],
    methods: {
      // 每页条数切换
      handleSizeChange(val) {
        this.pageSize = val;
        this.refers()
      },
      //页码切换
      handleCurrentChange(val) {
        this.currentPage = val;
        this.refers()
      },
      // 分页查询
      refers() {
        const vm = this;
        let pageParam = {"pageNumber": vm.currentPage, "pageSize": vm.pageSize};
        let param = Object.assign({}, this.queryCondition, pageParam);
        // 发送查询列表数据请求
        vm.$axios.post(vm.queryUrl, JSON.stringify(param), vm.config).then(function (response) {
          if (response.data.status === "success") {
            vm.$emit('pagingQueryData', response);
          } else {
            vm.$message({
              type: 'error',
              message: response.data.message
            });
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
