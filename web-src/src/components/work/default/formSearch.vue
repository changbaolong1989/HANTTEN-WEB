<template>
  <!-- 搜索表单 -->
  <el-form class="formSearch--work-default" el-form ref="form" :model="form" label-width="80px">
    <div class="screening">
      <el-badge ref ='departmentBtns' :is-dot="!!item.departmentBtnFlag" class="item" v-for="(item, i) in currentBtns" :key="i">
        <el-button  size="mini" :class="{ active: item.id == form.department.id }" @click="btnActive(item)">{{ item.name }}</el-button>
      </el-badge>
    </div>
    <div class="screeningInput" :class="{'margin-plus':userPowerInfo&&userPowerInfo.doneListExport=='1'&&searchFlagObj.showExport}">
      <div class="form-goup" v-if="theMarketingDepartment == uniqueId || '投标任务' == uniqueId">
        <div class="item-input">
          <label>投标任务名</label>
          <el-input v-model="form.tenderTaskName" placeholder="请输入投标任务名"></el-input>
        </div>
      </div>

      <div class="form-goup" v-if="'合同' == uniqueId || costOf == uniqueId || biddingAgencyDepartment == uniqueId || '监理业务' == uniqueId || projectSupervisionDepartment==uniqueId">
        <div class="item-input">
          <label>项目编号</label>
          <el-input v-model="form.projectNum" placeholder="请输入项目编号"></el-input>
        </div>
        <div class="item-input" v-if="'合同' == uniqueId">
          <label>合同编号</label>
          <el-input v-model="form.contractNum" placeholder="请输入合同编号"></el-input>
        </div>
        <div class="item-input" v-if="searchFlagObj&&searchFlagObj.projectNameFlag||projectSupervisionDepartment==uniqueId">
          <label>项目名称</label>
          <el-input v-model="form.projectName" placeholder="请输入项目名称"></el-input>
        </div>
      </div>

      <div class="form-goup" v-if="qualityInspectionDepartment == uniqueId || administrationDepartment == uniqueId || financial == uniqueId">
        <div class="item-input">
          <label>提审人</label>
          <el-input v-model="form.userName" placeholder="请选择提审人"></el-input>
        </div>
        <div class="item-input">
          <label>项目编号</label>
          <el-input v-model="form.projectNum" placeholder="请输入项目编号"></el-input>
        </div>
        <div class="item-input" v-if="searchFlagObj&&searchFlagObj.projectNameFlag">
          <label>项目名称</label>
          <el-input v-model="form.projectName" placeholder="请输入项目名称"></el-input>
        </div>
      </div>

      <div class="item-input search-btn-container item-date">
        <!-- <el-button type="primary" icon="el-icon-search" class="linear">批量待办</el-button>
        <el-button type="primary" icon="el-icon-search" class="linear">批量签发</el-button> -->
        <el-button type="primary" class="linear" v-if="userPowerInfo&&userPowerInfo.doneListExport=='1'&&searchFlagObj.showExport" @click="weekDialogVisible = true">导出周报</el-button>
        <el-dialog title="选择日期" :visible.sync="weekDialogVisible" width="35%" transition="fade-in-linear" center  class="picker">
          <el-date-picker
            v-model="inquireDateArr"
            type="daterange"
            value-format="yyyy-MM-dd"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-value="nowDate"
          ></el-date-picker>

          <span slot="footer" class="dialog-footer">
            <el-button @click="weekDialogVisible = false">取 消</el-button>
            <el-button type="primary"class="linear" @click="exportWeekBtn">导 出</el-button>
          </span>

        </el-dialog>
        <el-button type="primary" icon="el-icon-search" class="linear" @click="search">查询</el-button>

      </div>
    </div>
  </el-form>
</template>

<script>
export default {
  /**
   * searchFlagObj:{Object}搜索条件显示控制对象
   * searchFlagObj.projectNameFlag{Boolean}显示项目名称
   * searchFlagObj.isBacklog{Boolean}是否是待办
   * searchFlagObj.currentDeptId{String}待办默认选中的部门标识
   * searchFlagObj.showExport{Boolean}显示导出按钮
   */
  props: ['btns','searchFlagObj'],
  data() {
    return {
      inquireDateArr: '',
      weekDialogVisible:false,
      nowDate:new Date(),
      uniqueId: '', // 唯一标识id
      form: {
        department: {
          // 部门
          id: '', // 部门标识
          display: '' // 部门名称
        },
        tenderTaskName: '', // 投标任务名
        projectNum: '', // 项目编号
        contractNum: '', // 合同编号
        userName: '' ,// 提审人
        projectName:''// 项目名称
      },
      financial: '092fcf86-7eed-4ec3-8b37-5c7c0c156072', // 财务部
      theMarketingDepartment: '593df92d-fb21-442a-b279-a8b821bbc0d4', // 市场部
      administrationDepartment: 'd60c4000-9fbd-11e9-a036-0e411fd5cf91', // 行政部
      costOf: 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', // 造价部
      biddingAgencyDepartment: 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', // 招标代理部
      executives: 'e18edd98-9fbd-11e9-a036-0e411fd5cf91', // 高管
      projectSupervisionDepartment: 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', // 项目监理部
      qualityInspectionDepartment: 'eb9bfd9a-7519-473e-8f08-502ae74cdb40', // 质检部
      currentBtns: this.btns || [],
      userPowerInfo:this.SStorage.getItem("userPowerInfo")
    };
  },
  watch: {
    // 监听器
    btns: {
      // 设置选中默认按钮
      handler() {
        let vm = this;
        if (!vm.btns || !vm.btns[0]) return;
        if(!vm.searchFlagObj||(vm.searchFlagObj&&!vm.searchFlagObj.isBacklog)){
          vm.btnActive(vm.btns[0]);
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
                vm.bus.$emit('changeDepartmentId', vm.form.department.id);//初始化加载赋值部门
              }
          }
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    btnActive(data) {
      // 按钮激活
      let vm = this;
      // 清空数据
      vm.form.department.id = '';
      vm.form.department.display = '';
      vm.form.tenderTaskName = '';
      // 赋值数据
      vm.uniqueId = data.uniqueId;
      vm.form.department.id = data.id;
      vm.form.department.display = data.name; // 被激活部门的名字
      vm.search();
    },
    search() {
      let vm = this;
      vm.$emit('click', {
        id: vm.form.department.id, // 部门标识
        tenderTaskName: vm.form.tenderTaskName, // 投标任务名
        projectNum: vm.form.projectNum, // 项目编号
        contractNum: vm.form.contractNum, // 合同编号
        userName: vm.form.userName ,// 提审人
        projectName: vm.form.projectName // 项目名称
      });
      vm.bus.$emit('changeDepartmentId', vm.form.department.id);
    },
    exportWeekBtn(){
      const vm = this;

      let startTime ="";
      let endTime = "";
      // 如果时间不为空，则遍历时间，放在集合中
      if (vm.inquireDateArr != null) {
        startTime = vm.inquireDateArr[0];
        endTime = vm.inquireDateArr[1];
      }
      // 导出列表数据
      const elemIF = document.createElement('iframe');
      elemIF.src = '/work/export/exportWeekInfo?realFileName=' + "周报.xls&startTime="+startTime+"&endTime="+endTime;
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
    }
  }
};
</script>
<style lang="scss">
.formSearch--work-default {
  .picker .el-dialog__body{
    text-align: center!important;
  }

  /* 表单项 */
  .el-input {
    width: 125px;
    height: 32px;
    border: 1px solid #f8f9fb;
    border-radius: 5px;
    margin-right: 20px;
    font-size: 12px;

    .el-input__inner {
      background-color: #f8f9fb;
      width: 122px;
      height: 30px;
      line-height: 30px;

      &.el-range-editor {
        border-radius: 5px;
        width: 233px;
        height: 30px;
      }
    }
  }

  .item-input {
    .el-input {
      width: auto !important;
    }
  }
}
</style>
<style lang="scss" scoped>
.formSearch--work-default {
  background-color: white;
  border-radius: 10px 10px 0 0;
  .picker .el-dialog__body{
    text-align: center!important;
  }

  /* 筛选条件 */
  .screening {
    height: 55px;
    padding: 15px 20px;

    .item+.item{
      margin-left: 10px;
    }
  }

  /* 筛选输入框 */
  .screeningInput {
    padding: 15px 20px;
    position: relative;
    box-shadow: 0 0 15px #e7e8e9 inset;
    height: 62px;

    &.margin-plus{
       .form-goup{
          margin-right: 220px;
       }
    }

    .form-goup {
      display: flex;
      flex-wrap: wrap;
      overflow: auto;
      height: 100%;
      margin-right: 130px;

      .item-input {
        min-width: 220px;

        label {
          font-size: 12px;
          color: #333;
          margin-right: 10px;
        }
      }
    }



    /* 搜索按钮容器 */
    .search-btn-container {
      position: absolute;
      right: 36px;
      top: 16px;
    }
  }
}
</style>
