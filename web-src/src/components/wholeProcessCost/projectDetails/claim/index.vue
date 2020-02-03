<template>
  <!-- 项目 > 索赔台账 -->
  <div class="mh-set-role" style="width: 1550px; overflow: auto;">
    <!-- 新增 -->
    <el-row type="flex" class="row-bg" justify="end">
      <el-col :span="24" class="text-align-right"><!-- 新增/修改-->
        <div class="item-tab-chuang" @click="addClaimBtn()" style="float: right;">
          <span class="el-icon-plus linear"></span>
          创建索赔台账
        </div>
        <div class="item-tab-chuang" style="float: right;" @click="exportBtn()">
          <span class="el-icon-upload2 linear"></span>
          导出
        </div>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="item-tab-bottom">
      <el-table :data="tabdata" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="../../../../assets/image/no-item.png" alt="" />
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="contractNum" label="合同编号"></el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="treatyType" label="合同分类" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.treatyType ? '-' : selectGetName(scope.row.treatyType,treatyTypeList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="undertakeType" label="承包方式" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{scope.row.treatyType=='1'?selectGetName(scope.row.undertakeType,undertakeTypeList):'-'}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="contractName" label="合同名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="contractClass" label="合同类型" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.contractClass ? '-' : selectGetName(scope.row.contractClass,contractClassList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="withTaxMoney" label="合同签订金额(元)" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.withTaxMoney ? '-' : new Number(scope.row.withTaxMoney)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="declareAmount" label="申报金额(元)" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.declareAmount ? '-' : new Number(scope.row.declareAmount)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="hanttenAmount" label="汉腾审核金额（元）" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.hanttenAmount ? '-' : new Number(scope.row.hanttenAmount)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="exciseMoney" label="汉腾审核金额（%）" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{(scope.row.hanttenAmount/(!scope.row.withTaxMoney?1:scope.row.withTaxMoney==0?1:scope.row.withTaxMoney))*100}}%</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="confirmAmount" label="已确认(元)" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.confirmAmount ? '-' : new Number(scope.row.confirmAmount)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="estimatedAmount" label="预估金额(元)" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.estimatedAmount ? '-' : new Number(scope.row.estimatedAmount)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  prop="remark" label="备注" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ !scope.row.remark ? '-' : scope.row.remark }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column"  width="150"  label="操作" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" class="modify" @click="updateClaimBtn(scope.row)">修改</el-button>
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
    <claim-form v-if = "centerDialogVisible" ref="childForm"></claim-form>
  </div>
</template>

<script>
  import ClaimForm from './claimForm' /* 索赔台账表单  */
  import DefaultProp from './default' /* 索赔台账默认元素  */
  export default {
    data() {
      const formdata = new FormData();
      return {
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        tabdata: [], //当前页数据
        projectId:this.$parent.projectId,//项目ID(从父级页面取值)
        //初始化上传参数载体(用以附带文件上传)
        formData:formdata,
        //dialog(弹出层)展示头
        formTitle:'',
        //弹窗显示Flag
        centerDialogVisible: false,
        //表单提交路径
        url:'',
        treatyTypeList:DefaultProp.treatyTypeList,
        undertakeTypeList:DefaultProp.undertakeTypeList,
        contractClassList:DefaultProp.contractClassList,


      }
    },
     components: {
       ClaimForm,//索赔台账表单
     },
    //页面初始化加载钩子函数
    mounted(){
      this.currentPage = 1;
      this.pageSize = 10;
      this.reflashData();
    },
    methods: {
      selectGetName(key,list) {
        return this.$parent.selectGetName(key,list);
      },
      reflashData(){
        this.searchList(this.currentPage, this.pageSize);
      },
      //文件上传组件提交前钩子方法
      beforeAvatarUpload(file, fileList) { /*  */
        this.formData.append("file", file);
        return false;
      },
      //新增索赔台账信息先行方法
      addClaimBtn() {
        const vm = this
        vm.centerDialogVisible = true;
        vm.$nextTick(() => {
          vm.$refs.childForm.addClaimFunc();
        })
      },
      // 每页条数切换
      handleSizeChange(val) {
        this.pageSize = val;
        this.reflashData();
      },
      //页码切换
      handleCurrentChange(val) {
        this.currentPage = val;
        this.reflashData();
      },
      //导出列表数据
      exportBtn() {
        const vm = this;
        // 导出列表数据
        const elemIF = document.createElement('iframe');
        elemIF.src = '/wholeProcess/claim/export/exportClaimData?realFileName=' + "索赔台账.xls" + "&projectId=" +  vm.projectId;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
      },

      //查询索赔台账信息(修改索赔台账信息先行方法)
      updateClaimBtn(row){
        const vm = this
        vm.centerDialogVisible = true;
        vm.$nextTick(() => {
          vm.$refs.childForm.updateClaimFunc(row);
        })
      },
      // 查询列表数据
      searchList(currentPage,pageSize) {
        const vm = this;
        let formData={
          // 页码
          "pageNumber": currentPage,
          // 每页总条数
          "pageSize": pageSize,
          // 项目编号
          "projectId": vm.projectId,
        }
        // 获取列表数据
        vm.apiAddress('/wholeProcess/claim/query/searchProjectClaimPeriodList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            let dataList = res.data.dataList;
            // 循环数据放入表格数据对象中
            vm.tabdata = dataList;
            // 设置数据总条数
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
    }
  }
</script>


<style lang="scss">
  .mh-set-role .el-table .table-column:last-child .cell {
    cursor: pointer;
  }

  /* 弹窗 */
  .mh-set-role .el-dialog--center {
    .el-dialog__body {
      padding: 0;

      .el-form {
        padding: 18px 30px 0;
        border-bottom: 1px solid #dcdfe6;

        .el-form-item {
          border-bottom: 1px solid #dcdfe6;
          margin: 0;
          padding: 10px 0;

          &:last-child {
            border-bottom: none;
          }

          label {
            width: 110px !important;
            color: #424242;
            font-size: 12px;
          }

          .el-form-item__content {
            margin-left: 110px !important;

            .el-input__inner {
              height: 27px;
              line-height: 27px;
              color: #424242;
              font-size: 12px;
              background-color: #f8f9fb;
            }

          }

          .el-date-editor{
            width:100%;
          }
        }

      }
    }

    .dialog-footer {
      .el-button {
        width: 120px;
        height: 30px;
        line-height: 30px;
        padding: 0;
        font-size: 12px;
      }
    }
  }

  /* 表单 */
  .mh-set-role .item-from {
    padding: 14px 20px;
    border-radius: 10px;
    background: #fff;
    box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
    margin-bottom: 20px;
  }

  .mh-set-role .item-from>div {
    display: inline-block;
  }

  .mh-set-role .el-input__inner {
    background-color: #f8f9fb;
  }

  .mh-set-role .item-serial .el-form-item {
    display: block !important;
  }

  .mh-set-role .item-from .el-input {
    width: 125px;
    height: 32px;
    border: 1px solid #f8f9fb;
    border-radius: 5px;
    margin-right: 20px;
    font-size: 12px;
  }

  .mh-set-role .item-input label {
    font-size: 12px;
    color: #333;
    margin-right: 10px;
  }

  .mh-set-role .item-date {
    margin-right: 30px;
  }

  .mh-set-role .item-date span {
    font-size: 12px;
    color: #333;
    margin-right: 10px;
  }

  .mh-set-role .itemfrom-right {
    float: right;
  }

  .mh-set-role .itemfrom-right button {
    margin: 0 10px;
    font-size: 12px;
    width: 90px;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
    padding: 0;
  }

  .mh-set-role .item-from .el-input .el-input__inner {
    color: #333;
    width: 135px;
    height: 30px;
    line-height: 30px;
  }

  .mh-set-role .item-from .el-range-editor.el-input__inner {
    border-radius: 5px;
    width: 233px;
    height: 30px;
  }

  .mh-set-role .el-date-editor .el-range-input {
    font-size: 12px;
    color: #333333;
    background-color: #f8f9fb;
    height: 28px !important;
  }

  .mh-set-role .item-serial {
    margin: 25px 0;
  }

  .mh-set-role .el-date-editor .el-range__icon,
  .el-date-editor .el-range-separator {
    line-height: 20px;
    color: #B1B1B1 !important;
  }

  .delete {
    color: #fc4053;
  }

  .modify {
    color: #06A1EE;
  }
</style>
