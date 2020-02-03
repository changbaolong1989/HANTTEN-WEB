<template>
  <!-- 搜索功能-->
  <div class="fivesearch">
    <form>
      <div @click="openPicker" class="list">
        <label>{{ projectForm.date }}</label>
        <i class="iconfont">&#xe630;</i>
      </div>
    <mt-datetime-picker class="year" ref="picker" type="date" v-model="pickerValue" @confirm="handleConfirm('date','year')"></mt-datetime-picker>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      projectForm:{
        date:new Date().getFullYear(),
      },
      pickerValue: new Date()
    };
  },
  methods:{
    openPicker(text) {
      this.pickerValue=  new Date();
      this.$refs.picker.open();
    },
    handleConfirm(text, val) {
        this.projectForm[text] = this.formatting(this.pickerValue).slice(0, 4);
        this.$emit('condition',this.projectForm[text])
    },
  }
};
</script>
<style lang="scss">
  .fivesearch {
    .year {
      .picker-slot:nth-child(2) {
        display: none;
      }
      .picker-slot:nth-child(3) {
        display: none;
      }
    }
  }
</style>
<style scoped lang="scss">
.fivesearch {
  background-color: #f2f8f9;
  width: 100%;
  height: 70px;
  line-height: 70px;
  .list{
    margin: 0 20px;
    display: flex;
    justify-content: space-between;
    label,i{
      color: #333333;
      font-size: 28px;
    }
  }
}
</style>
