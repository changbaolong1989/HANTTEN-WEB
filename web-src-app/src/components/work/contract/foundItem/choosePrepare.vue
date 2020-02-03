<template>
  <!-- 选择编制成果人文件 -->
  <div class="choosePrepare">
    <template v-for="(item, index) in radiolist">
      <label>
        <input type="radio" v-model="radiolistuserId" :value="item.userId" @change="checked($event)" />
        <span></span>
        {{ item.userName }}
      </label>
      <ul v-if="radiolistuserId==item.userId">
        <li v-for="(i, v) in contentList" :key="v">
          <p>
            <span>任务名称</span>
            <span>{{ i.taskName }}</span>
          </p>
          <p>
            <span>完成时间</span>
            <span>{{ i.endDate }}</span>
          </p>
          <p>
            <span>任务工时</span>
            <span>{{ i.needHour }}</span>
          </p>
        </li>
      </ul>
    </template>
    <div class="commonFooter">
      <mt-button>取消</mt-button>
      <mt-button class="linear">确定</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      radiolistuserId: '',
      contentList: [],
    };
  },
  props: {
    radiolist: Array,
    finishDate:String,
    index:Number,
    userId:String,
    personList:Array
  },
  watch: {
    userId:function(newval,oldval){
      if(newval){
        this.radiolistuserId = newval;
      }
    },
    personList:function(newval,oldval){
      if(newval){
        this.contentList = newval;
      }
    },
    radiolist: function(newval,oldval) {
        this.radiolist = newval;
    },
    finishDate:function(newval) {
     this.finishDate = newval;
     this.searchUserInfo()
    },
    index:function(newval) {
     this.index = newval;
    },
  },
  created(){
    if(this.userId){
      this.radiolistuserId = this.userId;
    }
    if(this.personList){
      this.contentList = this.personList;
    }
  },
  methods: {
    checked(e) {
      /* 编制成果人文件改变*/
      this.contentList = [];
      this.searchUserInfo()
    },
    searchUserInfo(){
      const vm = this;
      vm.requestPost('/task/query/searchUserInfo', {
        userId: this.radiolistuserId,
        endDate:this.finishDate
      })
        .then(res => {
          if (res.data.status == 'success') {
            this.contentList = res.data.dataList;
          }
        })
        .catch(err => {
          console.log(err);
        });
        var index = this.radiolist.filter(function(item){
          return item.userId==vm.radiolistuserId;
        })
        this.$emit('returned',{item:index,index:this.index})
    }
  }
};
</script>

<style lang="scss">
.choosePrepare {
  width: 94%;
  margin: 0 auto;
  label {
    border-bottom: 2px solid #e5e5e5;
    width: 100%;
    display: flex;
    height: 90px;
    line-height: 90px;
    font-size: 28px;
    color: #333333;
    span {
      display: inline-block;
      border: 2px solid #d6d6d6;
      background-color: #f8f9fb;
      width: 60px;
      height: 60px;
      border-radius: 50%;
      transform: scale(0.5);
      transform-origin: 0% 0%;
      margin-top: 30px;
    }
    input {
      &:checked + span {
        border: 2px solid #06a1ee;
        background-color: #06a1ee;
        position: relative;
        &::after {
          content: '';
          width: 60px;
          height: 60px;
          background-color: #fff;
          position: absolute;
          top: 15px;
          left: 15px;
          border-radius: 50%;
          z-index: 10;
          transform: scale(0.5);
          transform-origin: 0% 0%;
        }
      }
    }
  }
}
</style>
<style scoped="scoped" lang="scss">
.choosePrepare {
  ul {
    border-bottom: 2px solid #e5e5e5;
    li {
      background-color: #f1f5f8;
      border-radius: 10px;
      margin: 20px auto;
      padding: 0 30px;
      overflow: hidden;
      p {
        display: flex;
        justify-content: space-between;
        font-size: 28px;
        color: #666666;
        margin: 30px 0;
      }
    }
  }
}
</style>
