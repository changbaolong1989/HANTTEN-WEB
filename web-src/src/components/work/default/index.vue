<template>
  <!-- 工作台默认内容 -->
  <div class="work-defalut">
    <el-badge :is-dot="isShowBadge" class="item" >
      <el-button class="btn-please-chapter" type="primary" @click="toPleaseChapter">
        <i class="iconfont">&#xe616;</i>请章
      </el-button>
    </el-badge>
    <el-tabs v-model="active" type="card" @tab-click="tabClick">
      <el-tab-pane :label="`待办(${backlogLabel})`" name="backlog">
      </el-tab-pane>
      <!--(${finishedLabel})-->
      <el-tab-pane :label="`已办`" name="finished">
      </el-tab-pane>
      <!--(${handleLabel})-->
      <el-tab-pane :label="`经手`" name="handle">
      </el-tab-pane>
    </el-tabs>
    <!-- 工时按钮相关 -->
    <btn-tool class="manHour"></btn-tool>
     <div class="item-tab-bor linear"></div>
     <component  :ref="active" v-bind:is="'table-'+active" @changeLabel="changeLabel"></component>
  </div>
</template>

<script>
  /* 待办表格 */
  import tableBacklog from './tableBacklog';
  /* 已办表格 */
  import tableFinished from './tableFinished';
  /* 经手表格 */
  import tableHandle from './tableHandle';
  /* 按钮工具 */
  import btnTool from './btnTool';
  export default {
    components: {
      'table-backlog': tableBacklog,
      'table-finished': tableFinished,
      'table-handle': tableHandle,
      'btn-tool': btnTool
    },
    data() {
      return {
        backlogLabel:0,// 待办label
        finishedLabel:0,// 已办label
        handleLabel:0,// 经手label
        active: 'backlog',
        newDate: "",
        isShowBadge: false,
        departmentId : ''
      };
    },
    created() {
      this.newDate = new Date().toLocaleDateString().replace(/\//g, '-');
      this.bus.$on('changeDepartmentId', val => {
        this.departmentId = val;
      });
      let vm = this;
      vm.$axios.post(
        '/workbench/sealApply/query/hasSealApplyTodo', {}, vm.config
      ).then(
        function (response) {
          vm.isShowBadge = response.data;
        }
      ).catch();
    },
    methods: {
      changeLabel(obj){// 更改
        let vm = this;
        vm[obj.name] = obj.count;
      },
      tabClick(target) { // 切换tab页
        let vm = this;
        vm.active = target.name;
      },
      toPleaseChapter() {
        // 跳转请章页面
        this.$router.push({
          path: '/page/work/pleaseChapter',
          query:{
            departmentId : this.departmentId
          }
        });
      },
      handClose(index) {
        this.$refs[index].resetFields();
      },
      submitForm(formName) {

      },
      hourslist() {
        this.$router.push('/page/work/hoursList');
      }
    },
  };
</script>
<style lang="scss">
  .work-defalut {

    /* 工时*/
    .manHour {
      position: absolute;
      top: 20px;
      right: 0;
    }

    /* 标签页 */
    .el-tabs--card {
      &>.el-tabs__header {
        border-bottom: none;
        margin: 0;

        .el-tabs__nav-scroll {
          padding: 20px 20px 0;
          // box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.4) inset;

          .el-tabs__nav {
            border: 1px;

            .el-tabs__item {
              border-radius: 10px 10px 0 0;
              border: none;
              box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
              background-color: white;

              &.is-active {
                background-color: #0aa3ef;
                color: white;
              }
            }
          }
        }
      }
    }
  }
</style>
<style lang="scss" scoped>
  .work-defalut {
    padding-bottom: 25px;
    flex-direction: column;
    display: flex;
    margin-left: 0px;
    position: relative;

    .item{
      width: 83px;
      height: 26px;
      z-index: 1;
      position: absolute;
      top: 77px;
      right: 36px;
      line-height: 26px;
      padding: 0;
      margin: 0;
    }
    /* 请章按钮样式 */
    .btn-please-chapter {
      color: #0099e8;
      border: 1px solid #0099e8;
      background-color: white;
      font-size: 12px;
      border-radius: 5px;
      i {
        font-size: 14px;
      }
    }
  }
</style>
