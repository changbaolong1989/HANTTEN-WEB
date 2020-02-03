<template>
	<!-- 请章记录 -->
	<div class="tablePleaseRecordInChapter--work-pleaseChapter">
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab" :cell-style="rowClass">
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
				<el-table-column align="center" class-name="table-column" prop="verifyState" label="审批状态">
          <template slot-scope="scope">
              {{scope.row.verifyState | formatState}}
              <!--<span style="display: inline-block;height: 28px;line-height: 28px;"> {{scope.row.verifyState}} &nbsp; &nbsp;</span>-->
            <span  type="text" size="small" @click="approval(scope.row,'2')"  style="display: inline-block;height: 28px;line-height: 28px;color: red;cursor: pointer" v-if="'0'==scope.row.verifyState">拒绝原因</span>
          </template>
				</el-table-column>
			</el-table>
		</div>


    <!-- 填写拒绝原因 -->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="50%" :before-close="handleClose">
      <el-form class="form" ref="form" :model="form">
        <el-form-item label="拒绝人" v-if="!isRead" >
          <el-input type="text"  v-model="form.approveUserName" :disabled="true" ></el-input>
        </el-form-item>
        <el-form-item label="拒绝原因">
          <el-input type="textarea" v-model="form.refusalReason" placeholder="请输入拒绝原因" :disabled="!isRead"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
				<el-button @click="chanel">取 消</el-button>
				<el-button type="primary" @click="noApproval" v-if="isRead">确 定</el-button>
			</span>
    </el-dialog>

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
	</div>
</template>

<script>
export default {
	data() {
		return {
			tabdata: [],
			total: 100, //分页总数据
			currentPage: 1, //当前页码
			pageSize: 10, //每页显示多少条数据
			pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
			config: {
				headers: {
					'Content-Type': 'application/json',
					Accept: 'application/json'
				}
			},
      dialogVisible:false,// 显示拒绝原因填写弹窗
      isRead:false,// 是否输出状态还是只读状态
      form:{// 表单
        refusalReason:'',     //拒绝原因
        approveUserName:'',   //拒绝人
        delegateTaskId: ''
      },
			biaodata: [] //表格数据
		};
	},
  filters:{
    formatState: function (input) {
      var result = "";
      if (input == "0") {
        result = "未通过";
      }
      if (input == "1") {
        result = "已通过";
      }
      if (input == "2") {
        result = "审批中";
      }
      return result;
    },
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
	created() {
		// 本地访问
		this.querySealApplyRecord();
	},

  mounted() {
    //绑定全局事件globalEvent
    this.bus.$on('reLoadData', val => {
      this.querySealApplyRecord();
    });
  },

	methods: {
		toDetails(row) {
			// 跳转到详情页面
			this.$router.push(row.path);
		},

    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          const vm = this;
          vm.form.refusalReason = '';
          done();
        })
        .catch(_ => {});
    },
    chanel(){
      const vm = this;
      vm.dialogVisible = false;
      vm.form.refusalReason = '';
    },

    approval(row,state){
      const vm = this;
      // 填写拒绝原因
      if('2'===state){
        vm.dialogVisible = true;
        vm.isRead = false;
        vm.form.refusalReason = row.rejectReason;
        vm.form.approveUserName = row.approveUserName;
      }
    },

		querySealApplyRecord() {
			const vm = this;
			vm.$axios
				.post(
					'/workbench/sealApply/query/querySealApplyRecord',
					JSON.stringify({
						pageNumber: vm.currentPage,
						/* 页码   1*/
						pageSize: vm.pageSize
						/* 每页总条数 */
					}),
					{
						headers: {
							'Content-Type': 'application/json',
							Accept: 'application/json'
						}
					}
				)
				.then(function(response) {
					if (response.data.status == 'success') {
						vm.biaodata = response.data.pagedData.dataList;
						vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
						vm.total = response.data.pagedData.dataCount; /* 总条数 */
					} else {
						vm.$message({
							message: response.data.message,
							type: 'error',
							center: true
						});
					}
				})
				.catch(function(error) {
					vm.$message({
						message: error,
						type: 'error',
						center: true
					});
				});
		},
		handleSizeChange(val) {
			// 每页条数切换
			this.pageSize = val;
			this.querySealApplyRecord();
		},
		handleCurrentChange(val) {
			//页码切换
			this.currentPage = val;
			this.querySealApplyRecord();
		},
		currentChangePage(list, i, k) {
			//分页方法
			this.tabdata = list;
		},

		rowClass({ row, column, rowIndex, columnIndex }) {
			//状态检测
			if (columnIndex === 10) {
				return 'color:#06A1EE';
			}
		}
	}
};
</script>
<style lang="scss">
</style>
<style lang="scss" scoped>
.tablePleaseRecordInChapter--work-pleaseChapter{
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
