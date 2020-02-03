<template>
  <!-- 工作台-待办-->
  <div class="work__table-backlog" v-infinite-scroll="loadMore">
    <form-search :btns="btns" @click="search" :searchFlagObj="{isBacklog:true,currentDeptId:departmentId}"></form-search>
    <form ref="form" >
    <ul v-for="(item, i) in tableData" class="contentlist" :key="i">

      <li class="contentlist-li">
        <span><m-checkbox :ref="item.businessId" name="table-checkbox" v-model="item.checkbox" :disabled="item.isPass=='0'"></m-checkbox></span>
        <span></span>
      </li>
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
        <span :title="item.contractNum">{{ item.contractNum }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.contractName">
        <span>合同名称</span>
        <span :title="item.contractName">{{ item.contractName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.counselTypePeriod">
        <span>咨询类型</span>
        <span :title="item.counselTypePeriod">
           {{  item.counselTypePeriod == null|| item.counselTypePeriod ==""? '-' : selectGetName(item.counselTypePeriod,radios) }}
        </span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.cooperateCompany">
        <span>合作公司</span>
        <span :title="item.cooperateCompany">
          {{ item.cooperateCompany == null|| item.cooperateCompany =="" ?"-":item.cooperateCompany }}
        </span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.cooperateCompany">
        <span>合同创建人</span>
        <span :title="item.cooperateCompany">{{ item.cooperateCompany }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.seniorManagerName">
        <span>所属高管</span>
        <span :title="item.seniorManagerName">{{ item.seniorManagerName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.tenderTaskName">
        <span>任务名称</span>
        <span>{{ item.tenderTaskName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.departmentName">
        <span>所属部门</span>
        <span>{{ item.departmentName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.startDate">
        <span>开标时间</span>
        <span>{{ item.startDate }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.userName">
        <span>提审人</span>
        <span>{{ item.userName }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.createUserName">
        <span>流转人</span>
        <span>{{ item.compilationUserName?item.compilationUserName:"历史数据，暂无流转人" }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.createDate">
        <span>业务数据创建时间</span>
        <span>{{ item.createDate }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.handleDate">
        <span>流转时间</span>
        <span>{{ item.handleDate }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.pending">
        <span>任务</span>
        <span class="contentlist-li--clickable" @click="toContract(item)">{{ item.delegateTaskKey }}</span>
      </li>
      <li class="contentlist-li" v-if="dataVOParamKeyMap.currentStateName">
        <span>状态</span>
        <span class="contentlist-li--clickable" @click="toContract(item)">{{ item.currentStateName }}</span>
      </li>
      <!-- <li class="contentlist-li" v-if="dataVOParamKeyMap.createDate">
        <span>操作</span>
        <span class="contentlist-li-
        -clickable" @click="approval(item)" v-if="1==item.isPass">通过</span>
      </li> -->
    </ul>
    </form>
    <div class="check-all-container">
      <div class="check-all-item"><mt-checklist v-model="checklistValue" :options="['全选']"></mt-checklist></div>
      <div class="check-all-item btn-all-pass" :class="{hasLength:selected}" @click="batchThrough">批量通过({{selected}})</div>
    </div>
    <div class="no-more" v-if="noMore">没有更多了~</div>
  </div>
</template>

<script>
  import Vue from 'Vue';
  import formSearch from './formSearch';
  import mCheckbox from '../../common/vue/m-checkbox.vue';
  import { Checklist } from 'mint-ui';
  Vue.component(Checklist.name, Checklist);
  export default {
    components: {
      'form-search': formSearch,
      'm-checkbox':mCheckbox
    },
    props: {},
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
        form:{
          checkbox:[]
        },
        selected:0,//已选择的数组
        checklistValue:[],// 全选
        noMore: false, // 显示更多提示
        btns: [], // 部门按钮
        dataVOParamKeyMap: { // 控制列表字段显示对象
          sortNum: true, // 序号
          projectNum: true, // 项目编号
          contractNum: true, // 合同编号
          tenderTaskName: true, // 任务名称
          departmentName: true, // 所属部门
          startDate: true, // 开标时间
          userName: true, // 提审人
          createUserName: true, // 流转人
          pending: true, // 任务
          createDate: true // 业务数据创建时间
        },
        tableData: [], // 列表数据
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        departmentId: '', //当前活动的部门标识
        tenderTaskName: '', //投标任务名称
        projectNum: '', // 项目编号
        userName: '', // 用户名
        radios: types,
      };
    },
    watch: {
      checklistValue:{
        handler(){
          let vm = this;
          vm.selectAllOrCancelAll(!!vm.checklistValue.length);
        },
        immediate:true
      },
      tableData:{
        handler(){
          let vm = this;
          let num = 0;
          for (let i = 0, len = vm.tableData.length; i < len; i++) {
            if(vm.tableData[i].checkbox) num++;
          }
          vm.selected = num;
        },
        immediate:true,
        deep:true
      }
    },
    mounted() {
      let vm = this;
      vm.$nextTick(function() {
        // 默认查询一次
        vm.currentChangePage(vm.currentPage, vm.pageSize);
        // 本地访问
        vm.searchDepartmentList();
      })
    },
    methods: {
      selectGetName(key,list){
        for (var i = 0; i < list.length; i++) {
          if (list[i].val==key) {
            return list[i].display
          }
        }
      },
      /**
       * @method 批量通过
       */
      batchThrough(){
        let vm = this;
        if(!vm.selected)return;
        let copyData = this.tool.deepCopy(vm.tableData, this.tool.deepCopy);
        let postData = [];
        // 取出选中的数据
        for (let i = 0, len = copyData.length; i < len; i++) {
          if(copyData[i].checkbox)postData.push(copyData[i]);
        }
        vm.MessageBox.confirm('是否提交?').then(action => {
          vm.requestPost('/work/update/batchPassCheckBusinessList', postData).then(res => {
            vm.Toast(res.data.message);
              if (res.data.status != 'failure') {
                // 刷新页面
                vm.currentChangePage(1, vm.pageSize);
                // 重置全选按钮
                vm.checklistValue = [];
                // 重新查询部门按钮
                vm.searchDepartmentList();
              }
            }).catch(err => {
              vm.Toast(err);
            });
        }).catch(cancel => {
        });
      },
      /**
       * @method 全选或者取消全选
       * @param {Boolean} isSelectAll 是否是全选
       */
      selectAllOrCancelAll(isSelectAll){
        let vm = this;
        if(isSelectAll){
          for (let i = 0, len = vm.tableData.length; i < len; i++) {
            if(vm.tableData[i].isPass!="0")
                if(!vm.tableData[i].checkbox){
                  vm.$nextTick(function () {
                    vm.$refs[vm.tableData[i].businessId][0].$el.click();
                  })
                }
          }
        }else{
          for (let i = 0, len = vm.tableData.length; i < len; i++) {
            if(vm.tableData[i].isPass!="0")
                if(vm.tableData[i].checkbox){
                  vm.$nextTick(function () {
                    vm.$refs[vm.tableData[i].businessId][0].$el.click();
                  })
                }
          }
        }
      },
      /**
       * @method 审核通过
       * @param {Object} data
       */
      approval(data){
        let vm = this;
        let paramObj = {
          isPass:'1',
          delegateTaskId:data.delegateTaskId,
          submitNode :data.submitNode
        };

        var url = '';
        // 合同
        if('0' == data.sourceType){// 合同

          paramObj.contractId = data.contractId;
          paramObj.updateDate = data.updateDate;

          url = '/contract/audit/auditingHTContract';

        }else if('1-1' == data.sourceType){// 成果文件

          paramObj.relationId = data.businessId;
          paramObj.updateDate = data.updateDate;

          url = '/task/add/auditingTaskOutcomeDocumentPeriod';

        }else if('1-2' == data.sourceType){// A3阶段

          paramObj.relationId = data.businessId;
          paramObj.updateDate = data.updateDate;

          url = '/task/add/auditingTaskA3Period';

        }else if('1-3' == data.sourceType){// 重新编辑

          paramObj.relationId = data.businessId;
          paramObj.updateDate = data.updateDate;

          // 当重新编辑的流程的节点不是‘签发’时，执行改接口
          if('任务重新编辑-待签发' != data.delegateTaskKey){

            url = '/task/add/auditingOutcomeDocumentPeriodReeditApplication';

          }else{

            url = '/task/add/reeditApplicationIssue';
          }

        }else if('2' == data.sourceType){// 投标任务

          paramObj.tenderTaskId = data.tenderTaskId;

          url = '/tenderTask/insert/auditingHTTenderTask';

        }else if('3' == data.sourceType){// 请款单

          paramObj.requestMenuId = data.businessId;

          url = '/requestMenu/modify/auditingQuestionMenu';

        }
        vm.MessageBox.confirm('是否提交?').then(action => {
          vm.requestPost(url, paramObj).then(res => {
            vm.Toast(res.data.message);
              if (res.data.status != 'failure') {
                // 刷新页面
                vm.currentChangePage(1, vm.pageSize);
              }
            }).catch(err => {
              vm.Toast(err);
            });
        }).catch(cancel => {
        });
    },
    search(data) { // 表单查询
      let vm = this;
      //设置当前活动部门
      vm.departmentId = data.id;
      vm.tenderTaskName = data.tenderTaskName;
      vm.projectNum = data.projectNum;
      vm.userName = data.userName;
      vm.currentChangePage(1, vm.pageSize)
    },
    currentChangePage(currentPage, pageSize) { //分页方法
      let vm = this;
      if(currentPage>1&& vm.tableData.length<(currentPage-1)*pageSize){
        vm.currentPage--
        return  vm.noMore = true;
      }
      let formData = {
        pageNumber: currentPage,
        pageSize: pageSize,
        tenderTaskName: vm.tenderTaskName,
        departmentId: vm.departmentId,
        projectNum: vm.projectNum,
        userName: vm.userName
      };
      vm.requestPost('/work/query/searchPendingList', formData).then(res => {
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
              name: 'backlogLabel',
              count: vm.total
            });
          } else {
            vm.$emit('changeLabel', {
              name: 'backlogLabel',
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
		console.log("rowInfo",rowInfo)
      }
    },
    searchDepartmentList() { // 查询部门按钮列表
      let vm = this;
      vm.requestPost('/work/query/searchAllDepartmentList', {}).then(res => {
        if (res.data.status == 'failure') {
          Toast({
            message: res.data.message,
            position: 'middle',
            duration: 5000
          });
        } else {
          if (!vm.departmentId) {
            vm.departmentId = res.data.dataList[0].departmentId; // 默认显示部门标识
          }
          let dataList = res.data.dataList;
          for (let i = dataList.length - 1; i >= 0; i--) {
            // 设置部门id
            dataList[i].uniqueId = dataList[i].departmentId;
            dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/departmentId/g, "id"));
            dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/departmentName/g, "name"));
            if (dataList.length==1) {
              dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/departmentBtnFlag/g, 'flag'));
            }
          }
          vm.btns = dataList; // 显示部门按钮
        }
      }).catch(err => {
        Toast({
          message: err,
          position: 'middle',
          duration: 5000
        });
      });
    },
    loadMore() { // 加载更多
      let vm = this;
      vm.currentChangePage(vm.currentPage++, vm.pageSize);
    }
  }
};
</script>
<style lang="scss">
.work__table-backlog{
  background-color:#eff3f5;

  /* 全选容器 */
  .check-all-container{
    display: flex;
    justify-content:space-around;
    position: fixed;
    bottom: 110px;
    background-color: #fafafa;
    height: 90px;
    width: 100%;

    .check-all-item{
      flex-grow: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items:center;

      .mint-checkbox-core{
        width: 30px;
        height: 30px;

        &::after{
          top: 8px;
          left:12px;
        }
      }

      .mint-checklist-title{
        margin: 0;
      }

      /* 批量通过按钮 */
      &.btn-all-pass{
        background-color: #d9d9d9;
        color:white;

        /* 有批量通过的值时的样式 */
        &.hasLength{
          background-color: #06A1EE;
        }
      }
    }

    .mint-cell{
      background-color: #fafafa;
    }
  }
}
</style>

<style lang="scss" scoped>
  .work__table-backlog {
    height: calc(100vh - 340px);
    overflow: auto;
    margin-bottom: 90px;
  }
</style>
