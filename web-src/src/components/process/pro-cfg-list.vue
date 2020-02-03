<template>
  <div>
    <div class="item-tab-bottom">
      <el-table :data="processConfigData" border style="width: 100%" class="item-tab">
        <template slot="empty" class="tabdata-empty">
          <img src="../../assets/image/no-item.png" alt="">
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" v-for="(dataHeader,index) in dataHeaders"
                         :label="dataHeader.name" :prop="dataHeader.name" :key="index++">
          <template slot-scope="scope">
            {{processConfigData[scope.$index][dataHeader.id]}}
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作">
          <template slot-scope="scope">
            <el-button type="text" class="modify" @click="update(scope.row)">修改</el-button>
            <el-button type="text" class="delete" @click="delFun(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <common-paging :total="this.total" :queryUrl="queryUrl" @pagingQueryData="pagingQueryData"
                   :queryCondition="queryCondition"></common-paging>
    <pro-cfg-list-edit :isShowEditDialog.sync="isShowEditDialog" @showDialog="showDialog"
                       :formData="rowData"></pro-cfg-list-edit>
  </div>
</template>

<script>

  import CommonPaging from '../common/common-paging'
  import ProCfgListAdd from './pro-cfg-list-add'
  import ProCfgListEdit from './pro-cfg-list-edit'
  import CommonSelect from '../common/common-select'
  import proCfgCommon from './pro-cfg-common'

  export default {
    name: "pro-cfg-list",
    components: {CommonPaging, ProCfgListAdd, ProCfgListEdit, CommonSelect},
    data() {
      return {
        //total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        dataHeaders: proCfgCommon.listHeaders,
        processName: '',
        processSelectData: [],
        departmentSelectData: [],
        queryUrl: '/process/config/searchProcessConfigInfo',
        isShowAddDialog: false, //弹窗显示
        isShowEditDialog: false,
        rowData: '1',
        //processConfigData: []
      }
    },
    created() {
      this.initData();
    },
    computed: {
      processConfigData: {
        get: function () {
          return this.$store.state.processConfigObject.data;
        },
        set: function (newVal) {
          this.$store.state.processConfigObject.data = newVal;
        }
      },
      total: {
        get: function () {
          return this.$store.state.processConfigObject.total;
        },
        set: function (newVal) {
          this.$store.state.processConfigObject.total = newVal;
        }
      },
      queryCondition: {
        get: function () {
          return this.$store.state.processConfigObject.queryCondition;
        }
      }
    },
    methods: {
      initData() {
        const vm = this;
        vm.$axios.post(vm.queryUrl, JSON.stringify({
          "pageNumber": vm.currentPage,
          "pageSize": vm.pageSize
        }), vm.config).then(function (response) {
          if (response.data.status === "success") {
            vm.processConfigData = response.data.pagedData.dataList;
            //vm.$store.commit('changeProcessConfigData', vm.processConfigData);
            // 循环数据放入表格数据对象中
            vm.currentChangePage(vm.processConfigData, vm.currentPage, vm.pageSize);
            // 设置数据总条数
            vm.total = response.data.pagedData.dataCount;
          } else {
            vm.$message({
              type: 'error',
              message: response.data.message
            });
          }
        });
        proCfgCommon.loadSelect(vm, '/process/config/searchProcessConfigSelect', {}, 'processSelectData');
        proCfgCommon.loadSelect(vm, '/process/config/searchDepartmentSelect', {}, 'departmentSelectData');
      },
      update(data) {
        this.isShowEditDialog = true;
        this.rowData = data;
      },
      delFun(data) {
        this.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          const vm = this;
          vm.$axios.post('/process/config/delProcessNodeRelation', JSON.stringify({"uuid": data.uuid}), vm.config).then(function (response) {
            // 如果返回成功，则提示成功信息，否则提示失败信息
            if (response.data.status === "success") {
              // 查询列表数据
              vm.bus.$emit('refersPagingData');
              vm.$message({
                type: 'success',
                message: response.data.message,
                center: true
              });
            } else {
              vm.$message({
                type: 'error',
                message: response.data.message,
                center: true
              });
            }
          }).catch(function (error) {
            vm.$message({
              type: 'error',
              message: error,
              center: true
            });
          });
        }).catch(() => {
        });
      },
      showDialog() {
        this.isShowEditDialog = false;
        this.isShowAddDialog = false
      },
      pagingQueryData(res) {
        const vm = this;
        // 设置表格数据
        vm.processConfigData = res.data.pagedData.dataList;
        // 循环数据放入表格数据对象中
        vm.currentChangePage(vm.processConfigData, vm.currentPage, vm.pageSize);
        // 设置数据总条数
        vm.total = res.data.pagedData.dataCount;
      },
      //分页方法
      currentChangePage(list) {
        //this.$store.commit('changeProcessConfigData', list);
        this.processConfigData = list;
      }
    }
  }
</script>

<style scoped>

</style>
