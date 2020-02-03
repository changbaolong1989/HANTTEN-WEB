<template>
  <!-- 项目 > 清标管理 -->
  <div class="mh-set-role">
    <el-row type="flex" class="row-bg" justify="end">
      <el-col :span="24" class="text-align-right"><!-- 新增/修改-->
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
        <el-table-column align="center" class-name="table-column" prop="tenderName" label="招标名称" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="contractType" label="合同类型" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{!scope.row.contractType ? '-' :selectGetName(scope.row.contractType,contractTypeList)}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="acceptFileTime" label="接收资料时间" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{scope.row.acceptFileTime?scope.row.acceptFileTime:'-'}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="sendReportTime" label="发出报告时间" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{scope.row.sendReportTime?scope.row.sendReportTime:'-'}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" class="delete" @click="downLoadFile(scope.row)">下载</el-button>
            <el-button type="text" class="modify" @click="updateCleanTenderBtn(scope.row)">修改</el-button>
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
    <clean-tender-form v-if = "centerDialogVisible" ref="childForm"></clean-tender-form>
  </div>
</template>

<script>
  import DefaultProp from './default' /* 清标管理默认元素  */
  import CleanTenderForm from '@/components/wholeProcessCost/projectDetails/cleanTender/cleanTenderForm' /* 招标管理表单  */
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
        contractTypeList:DefaultProp.contractTypeList
      }
    },
     components: {
       CleanTenderForm,//招标管理表单
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
      //文件上传组件提交成功后钩子方法
      handleAvatarSuccess(file, fileList) { /* 招标管理展示 */
        this.ruleForm.file = URL.createObjectURL(file.raw)
        this.ruleForm.materialName = file.name;
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

      //查询清标管理信息(修改清标管理信息先行方法)
      updateCleanTenderBtn(row){
        const vm = this
        console.log("~~~~~~~~~~~~~~")
        vm.centerDialogVisible = true;
        vm.$nextTick(() => {
          console.log("this.$refs.childForm",vm.$refs.childForm)
          vm.$refs.childForm.updateCleanTenderFunc(row);
        })
      },
      //导出列表数据
      exportBtn() {
        const vm = this;
        // 导出列表数据
        const elemIF = document.createElement('iframe');
        elemIF.src = '/wholeProcess/tender/export/exportCleanTenderData?realFileName=' + "清标台账.xls" + "&projectId=" +  vm.projectId;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
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
        vm.apiAddress('/wholeProcess/tender/query/searchProjectCleanTenderPeriodList', formData).then(res => {
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
      //下载文件
      downLoadFile(row){
        this.downloadFile(row.reportPath,row.reportName)
      },
      /**
       * @method 文件上传设置
       * @param {Object} files
       * @param {Object} fileList
       */
      handleExceed(files, fileList){
        let vm = this;
        this.$message.warning(`当前限制选择 ${vm.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
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
