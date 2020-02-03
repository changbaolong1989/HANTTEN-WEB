<template>
  <!-- 工作台-已办-->
  <div class="work__table-finished" v-infinite-scroll="loadMore">
    <form-search :btns="btns" @click="search"></form-search>
    <ul v-for="(item, i) in tableData" class="contentlist" :key="i">
      <li class="contentlist-li" v-if="dataVOParamKeyMap.sortNum">
        <span>序号</span>
        <span>{{ item.sortNum }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.projectNum">
        <span>项目编号</span>
        <span>{{ item.projectNum }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.projectName">
        <span>项目名称</span>
        <span>{{ item.projectName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.contractNum">
        <span>合同编号</span>
        <span class="contentlist-li--clickable" @click="toContract(item)">{{ item.contractNum }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.contractName">
        <span>合同名称</span>
        <span>{{ item.contractName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.seniorManagerName">
        <span>所属高管</span>
        <span>{{ item.seniorManagerName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.tenderTaskName">
        <span>任务名称</span>
        <span class="contentlist-li--clickable" @click="toContract(item)">{{ item.tenderTaskName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.counselTypePeriod">
        <span>业务类型</span>
        <span> {{  item.counselTypePeriod == null|| item.counselTypePeriod ==""? '-' : selectGetName(item.counselTypePeriod,radios) }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.cooperateCompany">
        <span>合作公司</span>
        <span>
           {{ item.cooperateCompany == null|| item.cooperateCompany =="" ?"-":item.cooperateCompany }}
        </span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.compilationUserName">
        <span>办理人</span>
        <span>
          {{ !!item.compilationUserName?item.compilationUserName:"历史数据，暂无办理人 " }}
        </span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.handleDate">
        <span>办理时间</span>
        <span>{{ item.handleDate }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.startDate">
        <span>开标时间</span>
        <span>{{ item.startDate }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.userName">
        <span>投标任务创建人</span>
        <span>{{ item.userName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.departmentName">
        <span>所属部门</span>
        <span>{{ item.departmentName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.createUserName">
        <span>创建人</span>
        <span>{{ item.createUserName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.createDate">
        <span>创建时间</span>
        <span>{{ item.createDate }}</span>
      </li>
    </ul>
    <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
  import formSearch from './formSearch';
  import {
    Toast
  } from 'mint-ui';
  export default {
    components: {
      'form-search': formSearch
    },
    data() {
      const types=[{
          display: '造价咨询-编制',
          val: '0-0'
        }, {
          display: '造价咨询-审核',
          val: '0-1'
        }, {
          display: '造价咨询-全过程跟踪审计',
          val: '0-2'
        }, {
          display: '造价咨询-全过程工程造价咨询',
          val: '0-3'
        },{
          display: '招标代理-施工',
          val: '1-0'
        },{
          display: '招标代理-监理',
          val: '1-1'
        }, {
          display: '招标代理-设计',
          val: '1-2'
        },{
          display: '招标代理-勘察',
          val: '1-3'
        },{
          display: '招标代理-服务类',
          val: '1-4'
        },{
          display: '招标代理-采购',
          val: '1-5'
        },{
          display: '招标代理-其他',
          val: '1-6'
        }]
      return {
        noMore: false, // 显示更多提示
        btns: [], // 部门按钮
        dataVOParamKeyMap: { // 控制列表字段显示对象
          sortNum: true, // 序号
          tenderTaskName: true, // 任务名称
          startDate: true, // 开标时间
          userName: true, // 投标任务创建人
          projectNum: true, // 项目编号
          contractNum: true, // 合同编号
          departmentName: true, // 所属部门
          createUserName: true, // 创建人
          createDate: true // 创建时间
        },
        tableData: [], // 列表数据
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        departmentId: '', //当前活动的部门标识
        tenderTaskName: '', //投标任务名称
        projectNum: true, // 项目编号
        radios: types,
        userPowerInfo:this.SStorage.getItem("userPowerInfo")// 登录用户信息
      };
    },
    watch: {},
    mounted() {
      let vm = this;
      vm.btns = [];
       if (!!vm.userPowerInfo.projectList) {
       	let btn = {
       		id: 'contract',
       		name: "合同",
       		uniqueId:"合同"
       		}
       	vm.btns.push(btn);
       }
       if (!!vm.userPowerInfo.tenderTaskList) {
       	let btn = {
       		id: 'tenderTask',
       		name: "投标任务",
       		uniqueId:"投标任务"
       	}
       	vm.btns.push(btn);
       }
    },
    methods: {
      selectGetName(key,list){
        for (var i = 0; i < list.length; i++) {
          if (list[i].val==key) {
            return list[i].display
          }
        }
      },
      search(data) { // 表单查询
        let vm = this;
        //设置当前活动部门
        vm.departmentId = data.id;// 部门标识
        vm.tenderTaskName = data.tenderTaskName;// 投标任务名称
        vm.projectNum = data.projectNum;// 项目编号
        vm.contractNum = data.contractNum; // 合同编号
        vm.currentChangePage(this.currentPage = 1, this.pageSize);
      },
      currentChangePage(currentPage, pageSize) { //分页方法
        let vm = this;
        if(currentPage>1&& vm.tableData.length<(currentPage-1)*pageSize)return  vm.noMore = true;
        let formData = {
          searchType: vm.departmentId,
          pageNumber: currentPage,
          tenderTaskName: vm.tenderTaskName,
          pageSize: pageSize,
          projectNum: vm.projectNum,
          contractNum:vm.contractNum // 合同编号
        };
        vm.requestPost('/work/query/searchCompletedList', formData).then(res => {
          if (res.data.status == 'failure') {
            Toast({
              message: res.data.message,
              position: 'middle',
              duration: 5000
            });
          } else {
            vm.dataVOParamKeyMap = res.data.dataVOParamKeyMap; // 列表显示列
            vm.total = res.data.dataCount || 0; // 列表数据总数
            if (1 == currentPage) {
              vm.tableData = res.data.dataList; // 列表数据
            } else if (res.data.dataList) {
              vm.tableData = vm.tableData.concat(res.data.dataList); // 列表数据
            }
            //判断有没有更多数据
            if (res.data.dataList && res.data.dataList.length) {
              vm.noMore = false;
            } else {
              vm.noMore = true;
            }
            if (res.data.dataCount) {
              vm.$emit('changeLabel', {
                name: 'finishedLabel',
                count: vm.total
              });
            } else {
              vm.$emit('changeLabel', {
                name: 'finishedLabel',
                count: 0
              });
            }
          }
        }).catch(err => {
          Toast({
            message: err,
            position: 'middle',
            duration: 5000
          });
        });

      },
      toContract(rowInfo) {
        // 如果是市场部，则跳转到投标任务详情页
        if('593df92d-fb21-442a-b279-a8b821bbc0d4' == rowInfo.departmentId){
           this.SStorage.setItem('/app/bazaarItemIndex',rowInfo);
           this.$router.push('/app/bazaarItemIndex');
          // 否则，跳转到项目详情页面
        } else {
          this.SStorage.setItem('/app/foundItem', rowInfo);
          this.$router.push('/app/foundItem');
        }
      },
      loadMore() { // 加载更多
        let vm = this;
        vm.currentChangePage(vm.currentPage++, vm.pageSize);
      }
    }
  };
</script>
<style lang="scss">
</style>

<style lang="scss" scoped>
  .work__table-finished {
    height: calc(100vh - 260px);
    overflow: auto;
    background-color:#eff3f5;
  }
</style>
