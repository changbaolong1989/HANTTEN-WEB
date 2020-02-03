<template>
  <!-- 所有页面的主视图-->
  <div class="home">
    <m-header v-if="$route.meta.showFooter"></m-header>
    <m-back v-show="!$route.meta.showFooter&&$route.path!='/app/login'"></m-back>
     <mt-tab-container>
       <router-view></router-view>
    </mt-tab-container>
  </div>
</template>

<script>
import Mheader from '../header/header.vue';
import Mback from '../header/back.vue';
export default {
  data() {
    return {};
  },
  components: {
    'm-header': Mheader,
    'm-back': Mback,
  },
  methods: {
  }
};
</script>
<style lang="scss">
</style>
<style scoped lang="scss">
.home {
}
</style>
