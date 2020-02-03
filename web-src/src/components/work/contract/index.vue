<template>
  <!-- 工作台 合同默认父视图 -->
  <div class="work-contract container__main">
    <!-- 左侧菜单 -->
     <router-view name="aside" class="aside--work-contract resize-bar container__left"></router-view>
    <!-- 项目详情 -->
    <div class="content--work-contract container__right">
      <!-- 标题 -->
      <h2><i class="icon icon-round"></i>{{titleName?titleName:"合同"}}</h2>

      <!-- 龙哥 -->
      <div class="main--work-contract" v-if="isFoundItem">
          <router-view name="center"></router-view>
      </div>
      <!-- 海哥 -->
      <div class="main--work-contract" v-else>
        <!-- 中间视图 -->
        <div class="center--work-contract">
          <router-view name="center"></router-view>
        </div>
        <!-- 右侧视图 -->
        <div width="200px" class="right--work-contract">
          <router-view name="right"></router-view>
        </div>
      </div>
    </div>
    <!-- 拖拉把手 -->
    <div class="handle"></div>
  </div>
</template>

<script>
  export default {
    data() {
      let itemName = '/page/work/contract/FoundItem';
      if('/page/item/contract/tenderTask'==this.$route.path||'/page/work/contract/tenderTask'==this.$route.path){
        itemName = '/page/work/contract/tenderTask';
      }
      return {
        titleName:this.SStorage.getItem(itemName).titleName,
        isFoundItem: /FoundItem/.test(itemName),
        onlyOfficeEditKey:'',
        reviewFilePath:'',
        reviewFileName:''
      };
    },
    mounted(){
      let vm = this;
       vm.bus.$on('click', val => {
         vm.titleName = val.label;
       })
      vm.$nextTick(function () {
        var mousePos = null;
        // 左边拖拉容器
        var container__left = document.getElementsByClassName('container__left')[0];
        // 右边拖拉容器
        var container__right = document.getElementsByClassName('container__right')[0];
        // 拖拉把手
        var handle = document.getElementsByClassName('handle')[0];
        handle.style.left = parseInt(container__left.offsetWidth)+'px';

        function mouseMove(ev){
         ev = ev || window.event;
         mousePos = mousePosition(ev);
        }
        function mousePosition(ev){
          let p = null;
          if(ev.pageX || ev.pageY){
            p = {x:ev.pageX, y:ev.pageY};
          }
          p = {
            x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
            y:ev.clientY + document.body.scrollTop - document.body.clientTop
          };
          if(mousePos){
            let distance = p.x - mousePos.x;
            let width_left = parseInt(container__left.offsetWidth)+distance+'px';
            let width_right = parseInt(container__right.offsetWidth)+distance+'px';
            container__left.style.width = width_left;
            container__right.style.width = width_right;
            handle.style.borderStyle = 'dashed';
            handle.style.left = width_left;
          }
          return p;
        }
        document.getElementsByClassName('handle')[0].onmousedown = function(ev){
          document.onmousemove = mouseMove;
		   // -moz-user-select:none;
		   //      -webkit-user-select:none;
		   //      user-select:none;
       document.getElementsByTagName('body')[0].style.userSelect = 'none';

        }
        document.onmouseup = function(ev){
          document.onmousemove = null;
          mousePos = null
          handle.style.borderStyle = 'solid';
          document.getElementsByTagName('body')[0].style.userSelect = 'auto';
        }
      })
    }
  };

</script>
<style lang="scss">
  .work-contract {

    /* 拖拉样式开始 */
    &.container__main{
    	display: flex;
    	height: 100%;
    	position: relative;
      .container__left,.container__main .container__right{
      	display: inline-block;
      	overflow: auto;
      	white-space: nowrap;
      	height: 100%;
      }
      .container__left{
      	/* padding-right: 10px; */
      }
      .container__right{
      	/* padding-left: 10px; */
      }
      /* 把手 */
      .handle{
      	position: absolute;
      	top:0;
      	right: 0;
      	cursor: col-resize;
      	width: 0;
      	height: calc(100% - 20px);
      	border-width: 2px;
      	border-color: rgba(187,188,189,0);
      	border-style:solid;
      	box-sizing: border-box;
      }
      .handle:hover{
      	border-style:dashed;
      }
    }
    /* 拖拉样式结束 */

    /* 去掉了日期选择框的清除图标,图标清空不会触发校验,项目比较着急,所以暂时去掉 */
  .el-date-editor{
    .el-icon-circle-close{
      display: none!important;
    }
  }
    /* 中间视图 */
    .center--work-contract {
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


    /* scoped */
    /* 项目阶段侧边栏菜单 */
    .aside--work-contract {
      min-width: 200px;
      width: 200px;
      overflow: auto;
      position: relative;
      background-color: white;
      border-radius: 15px 0 0 15px;
      padding: 0 10px 10px 10px;
      box-shadow: 6px 0 8px rgba(176, 192, 202, 0.3);
      z-index: 99;


    }

    /* 项目详情 */
    .content--work-contract {
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
      .main--work-contract {
        background-color: #f7fafc;
        border-radius: 0 0 15px 0;
        display: flex;
        align-items: stretch;
        flex-grow: 1;

        /* 中间视图 */
        .center--work-contract {
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
        .right--work-contract {
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

  .resize-bar::-webkit-scrollbar {
      width: 5px; height: 5px;
  }
</style>
<style lang="scss" scoped>
  .work-contract {
    margin-left: 0px;
    min-height: calc(100vh - 114px);
    padding-bottom: 20px;
    display: flex;
    align-items: stretch;




  }
</style>
