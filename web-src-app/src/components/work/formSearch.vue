<template>
  <div class="work__form-search">
    <ul class="nav-list">
      <li class="nav-list__li" :class="{'nav-list_li--active':item.id==form.department.id}" v-for="(item, index) in currentBtns"
        :key="index" @click="btnActive(item)">{{item.name}}
        <sup v-if="item.departmentBtnFlag"></sup>
        </li>
    </ul>
    <form class="search" :class="{'search--active':searchActive}">
      <span class="iconfont icon--search">&#xe627;</span>
      <span class="iconfont icon--search-text" @click="search">搜索</span>
      <!-- 市场部593df92d-fb21-442a-b279-a8b821bbc0d4
            投标任务-已办tenderTask -->
      <input
        v-if="'593df92d-fb21-442a-b279-a8b821bbc0d4'==form.department.id
            ||'tenderTask'==form.department.id"
        @focus="searchActive = true"
        @blur="blur"
        @change="search"
        type="text"
        class="input search-input"
        v-model="form.tenderTaskName"
        placeholder="请输入投标任务名"/>

      <input
        v-else-if="'d60c4000-9fbd-11e9-a036-0e411fd5cf91'==form.department.id
            ||'092fcf86-7eed-4ec3-8b37-5c7c0c156072'==form.department.id
            ||'eb9bfd9a-7519-473e-8f08-502ae74cdb40'==form.department.id"
        @focus="searchActive = true"
        @blur="blur"
        @change="search"
        type="text"
        class="input search-input"
        v-model="form.userName"
        placeholder="请输入提审人"/>

     <input
        v-else-if="'contract'==form.department.id"
        @focus="searchActive = true"
        @blur="blur"
        @change="search"
        type="text"
        class="input search-input"
        v-model="form.contractNum"
        placeholder="请输入合同编号"/>

      <input
        v-else
        @focus="searchActive = true"
        @blur="blur"
        @change="search"
        type="text"
        class="input search-input"
        v-model="form.projectNum"
        placeholder="请输入项目编号"/>
    </form>
  </div>
</template>

<script>
  /**
   * searchFlagObj:{Object}搜索条件显示控制对象
   * searchFlagObj.isBacklog{Boolean}是否是待办
   * searchFlagObj.currentDeptId{String}待办默认选中的部门标识
   */
  export default {
    props: ['btns','searchFlagObj'],
    data() {
      return {
        placeholder: '请输入项目编号',
        currentBtns: this.btns || [],
        searchActive: false, // 输入框的激活状态
        uniqueId: '', // 唯一标识id
        input: '', // 输入框的内容
        form: {
          department: { // 部门
            id: '', // 部门标识
            display: '' // 部门名称
          },
          tenderTaskName: '', // 投标任务名
          projectNum: '', // 项目编号
          contractNum: '', // 合同编号
          userName: '' // 提审人
        }
      };
    },
    watch: {
      btns:{ // 设置选中默认按钮
        handler() {
            let vm = this;
            if (!vm.btns || !vm.btns[0]) return;
            if(!vm.searchFlagObj||(vm.searchFlagObj&&!vm.searchFlagObj.isBacklog)){
              vm.btnActive(vm.btns[0]);
              // 默认执行一次查询方法
              vm.search();
            }
            vm.currentBtns = vm.tool.deepCopy(vm.btns, vm.tool.deepCopy);
          },
          deep: true,
          immediate: true
      },
      searchFlagObj:{
        handler(){
          let vm = this;
          // 待办默认的选中但不触发查询
          if(vm.searchFlagObj&&vm.searchFlagObj.currentDeptId){
            for (let i = 0, len = vm.currentBtns.length; i < len; i++) {
                if(vm.searchFlagObj.currentDeptId==vm.currentBtns[i].id){
                  // 清空数据
                  vm.form.department.id = '';
                  vm.form.department.display = '';
                  vm.form.tenderTaskName = '';
                  // 赋值数据
                  vm.uniqueId = vm.currentBtns[i].uniqueId;
                  vm.form.department.id = vm.currentBtns[i].id;
                  vm.form.department.display = vm.currentBtns[i].name; // 被激活部门的名字
                }
            }
          }
        },
        deep: true,
        immediate: true
      }
    },
    mounted() { // el 被新创建的 vm.$el 替换，并挂载到实例上去之后调用该钩子
    },
    methods: {
      btnActive(data) { // 按钮激活
        let vm = this;
        vm.resetData();
        // 赋值数据
        vm.uniqueId = data.uniqueId;
        vm.form.department.id = data.id;
        vm.form.department.display = data.name; // 被激活部门的名字
        vm.search();
      },
      resetData() { // 清空数据
        let vm = this;
        // 清空数据
        vm.input = '';
        vm.form = {
          department: { // 部门
            id: '', // 部门标识
            display: '' // 部门名称
          },
          tenderTaskName: '', // 投标任务名
          projectNum: '', // 项目编号
          contractNum: '', // 合同编号
          userName: '' // 提审人
        };
      },
      search() {
        let vm = this;
        vm.$emit('click', {
          id: vm.form.department.id, // 部门标识
          tenderTaskName: vm.form.tenderTaskName, // 投标任务名
          projectNum: vm.form.projectNum, // 项目编号
          contractNum: vm.form.contractNum, // 合同编号
          userName: vm.form.userName // 提审人
        });
        // 将部门标识(vm.form.department.id),存放到sessionStorage中,方便请章查询调用
        sessionStorage.setItem("work__departmentId--default",vm.form.department.id);
      },
      blur() {
        let vm = this;
        // 如果输入框里没有值,延迟5秒将其关闭
        if (!vm.input) {
          setTimeout(function() {
            vm.searchActive = false;
          }, 5000);
        }

      }
    }
  };
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
  .work__form-search {
    position: relative;
    overflow: hidden;
    background-color: #eff3f5;

    * {
      box-sizing: border-box;
    }

    .nav-list {
      width: 100%;
      background-color: #f2f8f9;
      display: flex;
      overflow: auto;
      box-shadow: 0 20px 50px rgba(0, 0, 0, 0.125);
      position: fixed;
      top: 82px;
      z-index: 200;

      .nav-list__li {
        padding: 22px 25px;
        font-size: 28px;
        line-height: 27px;
        color: #999999;
        white-space: nowrap;
        position:relative;

        sup{
          display: inline-block;
          position: absolute;
          top: 22px;
          right: 10px;
          width: 12px;
          height: 12px;
          border-radius: 50%;
          background-color: #F56C6C;
          border: 1px solid #FFF;
        }
      }

      .nav-list_li--active {
        color: #06a1ee;
      }
    }

    /* 搜索栏 */
    .search {
      padding: 0 !important;
      width: 94%;
      margin: 15px auto;

      &.search--active {
        .icon--search-text {
          display: inline-block;
        }
      }

      .search-input {
        width: 100%;
        padding-left: 52px;
        box-sizing: border-box;
      }

      /* 搜索图标 */
      .icon--search {
        position: absolute;
        top: 18px;
        left: 16px;
        font-size: 18px;
        color: #b1b1b1;
      }

      /* 搜索文字 */
      .icon--search-text {
        position: absolute;
        right: 16px;
        left: unset !important;
        top: 18px;
        font-size: 18px;
        color: #06a1ee;
        display: none;
      }
    }

  }
</style>
