<template>
  <!-- 合同列表/市场列表-->
  <div class="projectList">
    <mt-navbar v-model="selected" v-if="!noData">
      <mt-tab-item id="1" v-if="!!userPowerInfo.projectList">合同项目</mt-tab-item>
      <mt-tab-item id="2" v-if="!!userPowerInfo.tenderTaskList">市场项目</mt-tab-item>
    </mt-navbar>
    <mt-tab-container v-if="!noData">
      <mt-tab-container-item v-if="selected==1"><contract-list></contract-list></mt-tab-container-item>
      <mt-tab-container-item v-if="selected==2"><market-list ></market-list></mt-tab-container-item>
    </mt-tab-container>
    <div v-if="noData" class="NoData">
      <div class="noDataCont">
        <img src="../../assets/img/noData.png" width="100%" alt="" />
        <p>暂无数据</p>
      </div>
    </div>
  </div>
</template>

<script>
import marketList from './itemList/marketList';/* 市场项目 */
import contractList from './itemList/contractList';/* 合同项目 */
export default {
  data() {
    return {
      selected: this.SStorage.getItem("userPowerInfo").projectList?'1':'2',
      userPowerInfo:this.SStorage.getItem("userPowerInfo"),
      noData:false
    };
  },
  components: {
    'market-list': marketList,
    'contract-list': contractList,
  },
  created(){
    if(this.SStorage.getItem('/app/projectList')){
      this.selected = this.SStorage.getItem('/app/projectList').selected;
    }
    if(!this.SStorage.getItem("userPowerInfo").projectList){
      if(!this.SStorage.getItem("userPowerInfo").tenderTaskList){
        this.noData = true;
      }else{
      this.noData = false;
    }
    }else{
      this.noData = false;
    }
  },
  watch:{
    selected(newval,oldval){
      if(newval=="1"){
         this.SStorage.removeItem('/app/projectList')
      }
    }
  },
  methods:{
  }
};
</script>

<style scoped lang="scss">
  .projectList{
    width: 100%;
    .mint-tab-container {
      margin-top: 90px;
      padding-bottom: 110px;
      background-color: #eff3f5;
    }
    .NoData {
      width: 100%;
      height: 91.8vh;
      background-color: #eff3f5;
      overflow: hidden;
      .noDataCont {
        width: 35%;
        margin:auto;
        position: relative;
        top: 50%;
        transform: translateY(-50%);
        img {
          margin: 0 auto;
          display: block;
        }
        p{
          text-align: center;
          margin-top: 15px;
          color: #9d9b9b;
          font-size: 28px;
        }
      }
    }
  }

</style>
