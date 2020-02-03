<template>
  <!-- 工作台 合同默认父视图 -->
  <div class="costOf">
    <!-- 左侧菜单 -->
     <router-view name="aside" class="aside--costOf"></router-view>
    <!-- 项目详情 -->
    <div class="content--costOf">
      <!-- 标题 -->
      <h2><i class="icon icon-round"></i>造价部详情</h2>
      <!-- 内容 -->
      <div class="main--costOf">
        <!-- 中间视图 -->
        <div class="center--costOf">
          <router-view name="center"></router-view>
        </div>
        <!-- 右侧视图 -->
        <div width="200px" class="right--costOf">
          <router-view name="right"></router-view>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
      };
    }
  };

</script>
<style lang="scss">
  .costOf {
    /* 中间视图 */
    .center--costOf {
      p,
      h4,
      .el-col,
      .el-form-item__label,
      .el-input-group__append,
      .el-checkbox__label,
      .el-upload-list__item-name{
        font-size: 12px;
      }
    }


  }
</style>
<style lang="scss" scoped>
  .costOf {
    margin-left: 0px;
    min-height: calc(100vh - 114px);
    padding-bottom: 20px;
    display: flex;
    align-items: stretch;


    /* 项目阶段侧边栏菜单 */
    .aside--costOf {
      min-width: 200px;
      width: 200px;
      overflow: visible;
      position: relative;
      background-color: white;
      border-radius: 15px 0 0 15px;
      padding: 0 10px 10px 10px;
      box-shadow: 6px 0 8px rgba(176, 192, 202, 0.3);
      z-index: 99;
    }

    /* 项目详情 */
    .content--costOf {
      flex-grow: 1;
      display: flex;
      flex-direction: column;

      h2 {
        background-color: white;
        border-radius: 0 15px 0 0;
        height: 48px !important;
        font-size: 14px;
        padding: 10px 0 10px 20px;
        box-shadow: 0 0 8px rgba(176, 192, 202, 0.3);
        z-index: 98;

        .icon-round {
          position: relative;
          width: 20px;
          height: 20px;
          background-origin: content-box;
          background-position: -326px -61px;
          vertical-align: middle;
        }
      }

      /* 主内容区 */
      .main--costOf {
        background-color: #f7fafc;
        border-radius: 0 0 15px 0;
        display: flex;
        align-items: stretch;
        flex-grow: 1;

        /* 中间视图 */
        .center--costOf {
          height: calc(100vh - 222px);
          display: flex;
          flex-grow: 1;
          flex-direction: column;
          margin: 20px 0 20px 20px;
          border-radius: 5px;
          box-shadow: 0 0 8px rgba(176, 192, 202, 0.3);
          overflow-x: hidden;
          overflow-y: auto;
        }

        /* 右侧视图 */
        .right--costOf {
          min-width: 260px;
          height: calc(100vh - 222px);
          position: relative;
          width: 260px !important;
          border-radius: 5px;
          margin: 20px;
          font-size: 12px;
          background-color: white;
          box-shadow: 0 0 8px rgba(176, 192, 202, 0.3);
          overflow: auto;
        }

      }
    }
  }
</style>
