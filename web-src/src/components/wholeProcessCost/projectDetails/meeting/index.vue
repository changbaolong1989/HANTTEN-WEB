<template>
  <!-- 项目 > 会议纪要 -->
  <div class="mh-set-role">
    <!-- 新增 -->
    <el-row type="flex" class="row-bg" justify="end">
      <el-col :span="24" class="text-align-right"><!-- 新增/修改-->
        <div class="item-tab-chuang" @click="addMeetingBtn()" style="float: right;">
          <span class="el-icon-plus linear"></span>
          创建会议纪要
        </div>
        <div class="item-tab-chuang"style="float: right;" @click="exportBtn()">
          <span class="el-icon-upload2 linear"></span>
          导出
        </div>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <div class="item-tab-bottom">
      <el-table :data="tabdata" border style="width: 100%" >
        <template slot="empty" class="tabdata-empty">
          <img src="../../../../assets/image/no-item.png" alt=""/>
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" type="index" label="序号"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="editDate" label="日期"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="address" label="地点"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="meetingTheme" label="会议主题"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="contentSummary" label="内容摘要"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="participatoryUnit" label="参与单位"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="remark" label="备注"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" class="delete" @click="downLoadFile(scope.row)">下载</el-button>
            <el-button type="text" class="modify" @click="updateMeetingBtn(scope.row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="block item-fen" v-show="total > 0">
      <el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize"
                     layout="total, prev, pager,next,sizes,jumper"
                     :total="total"></el-pagination>
      <el-button class="linear" type="primary" size="small">确定</el-button>
    </div>

    <!--会议纪要表单-->
    <meeting-form v-if="centerDialogVisible" ref="childForm"></meeting-form>

  </div>
</template>

<script>
  import MeetingForm from '@/components/wholeProcessCost/projectDetails/meeting/meetingForm'
  /* 会议纪要表单  */
  export default {
    data() {
      const formdata = new FormData();
      return {
        total: 0, //分页总数据
        currentPage: 1, //当前页码
        pageSize: 10, //每页显示多少条数据
        pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        tabdata: [], //当前页数据
        projectId: this.$parent.projectId,//项目ID(从父级页面取值)
        //初始化上传参数载体(用以附带文件上传)
        formData: formdata,
        //dialog(弹出层)展示头
        formTitle: '',
        //弹窗显示Flag
        centerDialogVisible: false,
        //表单提交路径
        url: '',
      }
    },
    components: {
      MeetingForm,//会议纪要表单
    },
    //页面初始化加载钩子函数
    mounted() {
      this.currentPage = 1;
      this.pageSize = 10;
      this.reflashData();
    },
    methods: {
      reflashData(){
        this.searchList(this.currentPage, this.pageSize);
      },
      //文件上传组件提交前钩子方法
      beforeAvatarUpload(file, fileList) {
        this.formData.append("file", file);
        return false;
      },
      //文件上传组件提交成功后钩子方法
      handleAvatarSuccess(file, fileList) { /* 信函展示 */
        this.ruleForm.file = URL.createObjectURL(file.raw);
        this.ruleForm.meetingFileName = file.name;
      },
      //新增信息先行方法
      addMeetingBtn() {
        const vm = this;
        //弹出层展示Flag
        this.centerDialogVisible = true;
        vm.$nextTick(() => {
          vm.$refs.childForm.addMeetingFunc();
        })
      },
      // 导出列表数据
      exportBtn() {
        const vm = this;
        // 导出列表数据
        const elemIF = document.createElement('iframe');
        elemIF.src = '/wholeProcess/meeting/export/exportMeetingData?realFileName=' + "会议纪要.xls" + "&projectId=" +  vm.projectId;
        elemIF.style.display = 'none';
        document.body.appendChild(elemIF);
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

      //查询信息(修改信息先行方法)
      updateMeetingBtn(row) {
        const vm = this;
        vm.centerDialogVisible = true;
        vm.$nextTick(() => {
          vm.$refs.childForm.updateMeetingFunc(row);
        })
      },
      // 查询列表数据
      searchList(currentPage, pageSize) {
        const vm = this;
        let formData = {
          // 页码
          "pageNumber": currentPage,
          // 每页总条数
          "pageSize": pageSize,
          // 项目编号
          "projectId": vm.projectId,
        }
        // 获取列表数据
        vm.apiAddress('/wholeProcess/meeting/query/searchMeetingPeriodList', formData).then(res => {
          if (res.data.status == 'failure') {
            vm.$message({
              message: res.data.message,
              type: 'error',
              center: true
            });
          } else {
            // 设置表格数据
            let pagedData = res.data.pagedData;
            // 循环数据放入表格数据对象中
            vm.tabdata = pagedData.dataList;
            // 设置数据总条数
            vm.total = pagedData.dataCount;
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
      downLoadFile(row) {
        this.downloadFile(row.meetingFilePath, row.meetingFileName)
      },
    }
  }
</script>


<style scoped>

</style>

