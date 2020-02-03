<template>
  <form class="search" :class="{'search--active':searchActive}">
    <span class="iconfont icon--search">&#xe627;</span>
    <span class="iconfont icon--search-text" @click="search">搜索</span>
    <input type="text" class="input search-input" v-model="form.input" :placeholder="placeholder" @focus="searchActive = true"
      @blur="blur" @change="change"/>
  </form>
</template>

<script>
  export default {
    props:['placeholder'],
    data() {
      return {
        searchActive: false, // 输入框的激活状态
        form: {
          input: ''
        }
      };
    },
    methods: {
      search() {
        let vm = this;
        vm.$emit('submit', vm.tool.deepCopy(vm.form, vm.tool.deepCopy));
      },
      blur() {
        let vm = this;
        // 如果输入框里没有值,延迟5秒将其关闭
        if (!vm.input) {
          setTimeout(function() {
            vm.searchActive = false;
          }, 5000);
        }
      },
      change(){
        let vm = this;
        vm.$emit('change', vm.tool.deepCopy(vm.form, vm.tool.deepCopy));
      }
    }
  };
</script>

<style lang="scss" scoped>
  * {
    box-sizing: border-box;
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
</style>
