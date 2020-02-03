<template>
	<!-- 用章记录 -->
	<el-container class="tableRecordedInChapter--work-pleaseChapter">
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata"  border style="width: 100%" class="item-tab" :cell-style="rowClass">
				<template slot="empty" class="tabdata-empty">
					<img src="../../../assets/image/no-item.png" alt="" />
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" prop="applyTime" label="创建时间"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="applyReason" label="用章原因"  :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="sealTypeText" label="用章类型" ></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="isPagingSeal" label="是否骑缝">
          <template slot-scope="scope">
            {{ scope.row.isPagingSeal | formatIsPagingSeal }}
          </template>
        </el-table-column>
				<el-table-column align="center" class-name="table-column" prop="applyUserName" label="申请人"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="approveTime" label="审批时间"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="approveUserName" label="审批人"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="t8" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="browseFile(scope.$index, scope.row)">预览</el-button>
					<el-button type="text" size="small" @click="download(scope.$index, scope.row)">下载</el-button>
					<el-button type="text" size="small"  @click="singSeal(scope.row)" v-show="scope.row.isSignatured!=='1'">标记盖章</el-button>
          <el-button type="text" size="small"  :disabled="true" v-show="scope.row.isSignatured==='1'">已盖章</el-button>
				</template>
				</el-table-column>
			</el-table>
		</div>
    <!--<dialog-browse-file :file="file" ref="dialogBrowseFile"></dialog-browse-file>-->
    <preview-file :file="file" ref="previewFile"></preview-file>
		<div style="margin-top: 30px;">
			<!-- 分页 -->
			<div class="block item-fen" v-show="total > 0">
				<el-pagination
					background
					class="fen-left"
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="currentPage"
					:page-sizes="pageSizes"
					:page-size="pageSize"
					layout="total, prev, pager,next,sizes,jumper"
					:total="total"
				></el-pagination>
				<el-button class="linear" type="primary" size="small">确定</el-button>
			</div>
		</div>
	</el-container>
</template>

<script>
/* 浏览文件 */
  import dialogBrowseFile from '../common/components/dialogBrowseFile.vue';
import previewFile from '../common/components/previewFile';
export default {
  components:{
    'dialog-browse-file':dialogBrowseFile,
    'previewFile':previewFile
  },
	data() {
		return {
      tabdata: [],
			total: 100, //分页总数据
			currentPage: 1, //当前页码
			pageSize: 10, //每页显示多少条数据
			pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
			biaodata: [
      ], //表格数据
      config: {
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json'
        }
      },
      attachmentPath:'',
      attachmentName:'',
      isSignatured: '',
			screening: !!this.$route.query.screening,
      file:''// 浏览文件
		}
	},
	created() {
    //绑定全局事件globalEvent
    this.bus.$on('c', val => {
      this.queryApprovedRecord();
    });
    this.bus.$on('reLoadSignSealData', val => {
      this.queryApprovedRecord();
    });
    this.queryApprovedRecord();
	},
  filters:{
    formatIsPagingSeal (input) {
      let result = "-";
      if(input=="0"){
        result = "否";
      }
      if(input=="1"){
        result = "是";
      }
      return result;
    }
  },
	methods: {
/*		toDetails(row) {// 跳转到详情页面
			this.$router.push(row.path);
		},*/
    /**
     * @method 浏览文件
     */
    browseFile(index,row){
      let vm = this;
      let file =  vm.tool.deepCopy(row, vm.tool.deepCopy);
      file.materialPath = row.attachmentPath;
      vm.file = file;
       vm.$refs.previewFile.show = true;
    },
    singSeal(row){
      const vm = this;
      vm.$confirm('是否提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(() => {
        vm.$axios.post('/workbench/sealApply/modify/signSeal', JSON.stringify({
          pageNumber: vm.currentPage,
          /* 页码   1*/
          pageSize: vm.pageSize,
          recordId: row.recordId,
          // 提审时间
          applyTime: row.applyTime,
          // 提审人
          applyUserId : row.applyUserId
          /* 每页总条数 */
        }),vm.config).then(function(response) {
          if (response.data.status === 'success') {
            vm.bus.$emit('reLoadSignSealData');
            /*vm.biaodata = response.data.pagedData.dataList;
            vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
            vm.total = response.data.pagedData.dataCount; /!* 总条数 *!/*/
          } else {
            vm.$message({
              message: response.data.message,
              type: 'error',
              center: true
            });
          }
        }).catch(function(error) {
          vm.$message({
            message: error,
            type: 'error',
            center: true
          });
        });
      });
    },
    queryApprovedRecord(){
      const vm = this;
      vm.$axios.post('/workbench/sealApply/query/queryApprovedRecord', JSON.stringify({
          pageNumber: vm.currentPage,
          /* 页码   1*/
          pageSize: vm.pageSize
          /* 每页总条数 */
        }),vm.config).then(function(response) {
          if (response.data.status === 'success') {
            vm.biaodata = response.data.pagedData.dataList;
            vm.currentChangePage1(vm.biaodata, vm.currentPage, vm.pageSize);
            vm.total = response.data.pagedData.dataCount; /* 总条数 */
          } else {
            vm.$message({
              message: response.data.message,
              type: 'error',
              center: true
            });
          }
        }).catch(function(error) {
          vm.$message({
            message: error,
            type: 'error',
            center: true
          });
        });
    },
    // 下载
    download(a, b) {
      const elemIF = document.createElement('iframe');
      elemIF.src = '/commonModule/downloadFile?filePathName=' + b.attachmentPath + '&realFileName='+b.attachmentName;
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
    },
		handleSizeChange(val) {// 每页条数切换
			this.pageSize = val;
			this.currentChangePage(this.biaodata, this.currentPage, this.pageSize);
		},
		handleCurrentChange(val) {//页码切换
			this.currentPage = val;
			this.currentChangePage(this.biaodata, this.currentPage, this.pageSize);
		},
		currentChangePage(list, i, k) {//分页方法
      this.queryApprovedRecord();
		},
    currentChangePage1(list, i, k){
      this.tabdata = list;
    },
		rowClass({ row, column, rowIndex, columnIndex }) {//状态检测
			if (columnIndex === 10) {
				return 'color:#06A1EE';
			}
		}
	}
};
</script>
<style lang="scss">
.tableRecordedInChapter--work-pleaseChapter {
    .hui{
      color: #b1b1b1;
      cursor: text;
    }
    /* 总容器 */
    &.el-container {
      padding-bottom: 25px;
      display: flex;
      flex-direction: column;
    }
  }
</style>
<style lang="scss" scoped>
.tableRecordedInChapter--work-pleaseChapter {
	margin-left: 0px;
	padding-bottom: 25px;
	display: flex;
	flex-direction: column;

	.item-tab-bottom {
		box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
		position: relative;
	}

	.el-table {
		margin: 0;
	}
}
</style>
