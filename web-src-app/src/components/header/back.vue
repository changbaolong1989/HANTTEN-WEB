<template>
  <div class="back" @click="routerLink"><i class="iconfont">&#xe633;</i></div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    routerLink() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
.back {
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
  position: fixed;
  top: 250px;
  left: 30px;
  color: #fff;
  font-size: 28px;
  padding: 50px;
  border-radius: 50%;
  transform: scale(0.5);
  transform-origin: 0% 0%;
  .iconfont{
    font-size: 50px;
    color: #fff;
  }
}
</style>
