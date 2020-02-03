<template>
  <div class="m-contract-information__default">
    <!-- 合同信息__默认内容 -->
     <div class="item-from">
       <div class="item-input">
         <label for="i3">合同编号</label>
         <el-input v-model="form.contractNum" placeholder="请输入合同编号" id="i3"></el-input>
       </div>
       <div class="item-input">
         <label for="i2">所属部门</label>
         <el-select v-model="form.departmentId" placeholder="请选择所属部门" clearable>
           <el-option v-for="item in departmentList" :key="item.departmentId" :label="item.departmentName" :value="item.departmentId">
           </el-option>
         </el-select>
       </div>
       <div class="itemfrom-right">
         <el-button type="primary" icon="el-icon-search" class="linear" @click="clickSearchList()">查询</el-button>
       </div>
     </div>
    <div class="form-block" style="padding-bottom:56px;">
      <div>
          <el-table :data="tableData" border style="width: 100%;">
            <el-table-column align="center" prop="contractNum" label="合同编号"></el-table-column>
            <el-table-column align="center" prop="departmentName" label="合同任务线"></el-table-column>
            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="text" class="modify" @click="download(scope.row)">合同下载</el-button>
                <el-button type="text" class="delete" v-if="!scope.row.legalOpinion" @click="showLegalOptionAddForm(scope.row)">添加法务意见</el-button>
                <el-button type="text" class="delete" v-if="scope.row.legalOpinion" @click="showLegalOptionDetailForm(scope.row)">查看法务意见</el-button>
              </template>
            </el-table-column>
          </el-table>
      </div>
      <!-- 分页 -->
      <div class="block item-fen" v-show="total > 0">
        <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
         :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
         :total="total"></el-pagination>
        <el-button class="linear" type="primary" size="small">确定</el-button>
      </div>
    </div>
    <el-dialog title="法务意见" :visible="show"  width="50%" center :before-close="beforeClose" :close-on-click-modal="true">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="text-align: left;">
        <el-input type="textarea" :disabled='disabledFlag' v-model="form.legalOpinion" placeholder="请输入法务意见"></el-input>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit('form')" v-if="!disabledFlag" style="padding: 0 45px;">确定提交</el-button>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel('form')" v-if="disabledFlag" style="padding: 0 45px;">取消 </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        tableData: [], //当前页数据
        show: false, // 对话框-法务意见
        disabledFlag:true,
        form: { // +表单
          legalOpinion:'',//-法务意见
          departmentId:'',//部门ID
          contractNum:'',
          contractId:'',
        },
        rules: {
          name: [{
            required: true,
            message: '请输入法务意见',
            trigger: 'blur'
          }],
        },
        departmentList:[
          {
            departmentId:'db5e76f9-9fbd-11e9-a036-0e411fd5cf91',
            departmentName:'造价部门',
          },{
            departmentId:'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91',
            departmentName:'招标代理部门',
          },{
            departmentId:'eb1f8c91-960d-4717-84ee-cc3b8137ccd3',
            departmentName:'项目监理部门',
          }],
      };
    },
    mounted(){
        this.clickSearchList()
    },
    methods: {
      showLegalOptionAddForm(row){
        console.log("row",row)
        let vm = this
        vm.form.legalOpinion = '';
        vm.form.contractId = row.contractId;
        vm.show = true;
        vm.disabledFlag = false;
      },
      showLegalOptionDetailForm(row){
        console.log("row",row)
        let vm = this
        vm.form.legalOpinion = row.legalOpinion
        vm.form.contractId = row.contractId;
        vm.show = true;
        vm.disabledFlag = true;
      },
      beforeClose() { // 对话框关闭-法务意见
        let vm = this;
        vm.show = false;
      },
      submit(formName) { // 表单提交-法务意见
        let vm = this;
        let formData = {
          legalOpinion:vm.form.legalOpinion,//-法务意见
          contractId:vm.form.contractId,
        };
        vm.apiAddress('/legalAffair/modify/insertLegalOpinion', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            vm.$message({
              message: res.data.message,
              type: 'success',
              center: true
            });
            vm.$refs[formName].resetFields();
            vm.searchList(vm.currentPage, vm.pageSize)
            vm.show = false;
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      cancel(formName) { // 表单取消-法务意见
      let vm = this;
        vm.$refs[formName].resetFields();
        vm.show = false;
      },
      clickSearchList(){
        this.currentPage = 1;
        this.pageSize = 10;
        this.searchList(this.currentPage, this.pageSize)
      },
      handleSizeChange(val) {
        // 每页条数切换
        this.pageSize = val;
        this.searchList(this.currentPage, this.pageSize)
      },
      handleCurrentChange(val) {
        //页码切换
        this.currentPage = val;
        this.searchList(this.currentPage, this.pageSize)
      },
      searchList(pageNumber, pageSize) {
        const vm = this;
        let formData = {
          contractNum: vm.form.contractNum,
          departmentId: vm.form.departmentId,
          pageNumber: pageNumber,
          pageSize: pageSize
        };
        vm.apiAddress('/legalAffair/query/searchContractList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
              let table = res.data.dataList;
              vm.tableData = table;
              vm.total = res.data.dataCount;
          }
        }).catch(err => {
          vm.$message({
            message: err,
            type: 'error',
            center: true
          });
        })
      },
      download(row){
        this.downloadFile(row.contractFilePath, row.contractFileName, row.contractType=='1');
      }
    }
  };
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
  /* 表单 */
  .m-contract-information__default .item-from {
    padding: 14px 20px;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    background: #fff;
    box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
    position: relative;
  }
  .m-contract-information__default .item-from>div {
    display: inline-block;
  }

  .m-contract-information__default .el-input__inner {
    background-color: #f8f9fb;
  }

  .m-contract-information__default .item-serial .el-form-item {
    display: block !important;
  }

  .m-contract-information__default .item-from .el-input {
    width: 125px;
    height: 32px;
    border: 1px solid #f8f9fb;
    border-radius: 5px;
    margin-right: 20px;
    font-size: 12px;
  }

  .m-contract-information__default .item-input label {
    font-size: 12px;
    color: #333;
    margin-right: 10px;
  }

  .m-contract-information__default .itemfrom-right {
    float: right;
  }

  .m-contract-information__default .itemfrom-right button {
    margin: 0 10px;
    font-size: 12px;
    width: 90px;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
    padding: 0;
  }

  .m-contract-information__default .item-from .el-input .el-input__inner {
    color: #333;
    width: 122px;
    height: 30px;
    line-height: 30px;
  }

  .m-contract-information__default .item-from .el-range-editor.el-input__inner {
    border-radius: 5px;
    width: 233px;
    height: 30px;
  }

  .m-contract-information__default .el-date-editor .el-range-input {
    font-size: 12px;
    color: #333333;
    background-color: #f8f9fb;
    height: 28px !important;
  }

  .m-contract-information__default .item-serial {
    margin: 25px 0;
  }

  .m-contract-information__default .el-date-editor .el-range__icon,
  .el-date-editor .el-range-separator {
    line-height: 20px;
    color: #B1B1B1 !important;
  }
</style>
