<template>
	<!-- 公司设置 > 工作步骤说明设置 -->
	<div class="explain-cost">
		<!-- 新增弹窗 -->
		<el-dialog title="修改" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center>
			<el-form :model="ruleForm" :rules="rules"  ref="ruleForm" label-width="100px" class="demo-ruleForm">
				<el-form-item label="工作阶段说明" prop="periodDesc">
					<el-input type="textarea" v-model="ruleForm.periodDesc" placeholder="请填写工作阶段说明"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 表格 -->
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab" :cell-style="rowClass">
				<template slot="empty" class="tabdata-empty">
					<img src="../../../../assets/image/no-item.png" alt="">
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" prop="periodName" width='200' label="工作阶段" :show-overflow-tooltip='true'></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="periodDesc" label="工作阶段说明" :show-overflow-tooltip='true'></el-table-column>
				<el-table-column align="center" class-name="table-column" label="状态" width='200' >
					<div @click="showUpdateTaskPeriodForm(scope.row.periodId,scope.row.periodDesc,scope.row.updateDate)" slot-scope="scope" class="modifyBtn">修改</div>
				</el-table-column>
			</el-table>
		</div>
		<!-- 分页 -->
		<div class="block item-fen" v-show="total>0">
			<el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
			 :current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
			 :total="total">
			</el-pagination>
			<el-button class="linear" type="primary" size="small">确定</el-button>
		</div>
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
				tabdata: [], //当前页数据
				centerDialogVisible: false, //弹窗显示
				dictDepartmentId:this.childSearchId,
				ruleForm: {
					periodDesc: '',
					periodId:'',
					updateDate:'',
				},
				rules: {
					periodDesc: [
					{required: true,message: '请填写工作阶段说明',trigger: 'blur'},
					{ max: 100, message: '最长100字', trigger: 'blur' }
					]
				}
			}
		},
		props:[
			"childSearchId"
		],
		mounted() {
			// 本地访问 
			this.currentChangePage(this.dictDepartmentId,this.currentPage, this.pageSize);
		},
		updated(){
		},
		methods: {
			submitForm(formName) {

				this.$refs[formName].validate((valid) => {
					if (valid) {
						const vm = this;
						let formData = {
							periodId:this.ruleForm.periodId,
							periodDesc:this.ruleForm.periodDesc,
							updateDate:this.ruleForm.updateDate
						};
						vm.$axios.post('/config/taskPeriod/modify/updateTaskPeriodInfo', formData, vm.config).then(function(response) {
							if (response.data.status=='failure') {
								vm.$message({
									message: response.data.message,
									type: 'error',
									center: true
								});
							}else{
								vm.$message({
									message: response.data.message,
									type: 'success',
									center: true
								});
							}
							vm.centerDialogVisible = false;
							vm.currentChangePage(vm.dictDepartmentId,vm.currentPage, vm.pageSize);
						});
					}
				});
			},
			handleSizeChange(val) { // 每页条数切换 
				this.pageSize = val;
				this.currentChangePage(this.dictDepartmentId,this.currentPage, this.pageSize);
			},
			handleCurrentChange(val) { //页码切换
				this.currentPage = val;
				this.currentChangePage(this.dictDepartmentId,this.currentPage, this.pageSize)
			},
			currentChangePage(dictDepartmentId,currentPage,pageSize) { //分页方法
				const vm = this;
				let formData = {
					dictDepartmentId:dictDepartmentId,
					pageNumber:currentPage,
					pageSize:pageSize
				};
				vm.$axios.post('/config/taskPeriod/query/searchTaskPeriodList', formData, vm.config).then(function(response) {
					
					vm.tabdata = response.data.pagedData.dataList;
					vm.total = response.data.pagedData.dataCount;
				})
				.catch(function(error) {
					vm.$message({
						message: error,
						type: 'error',
						center: true
					});
				});
			},
			refreshComponent(){
				this.currentPage = 1
				this.pageSize = 10
			},
			showUpdateTaskPeriodForm(periodId,periodDesc,updateDate){
				this.ruleForm.periodId = periodId;
				this.ruleForm.periodDesc = periodDesc;
				this.ruleForm.updateDate = updateDate;
				this.centerDialogVisible = true
			},
			rowClass({
				row,
				column,
				rowIndex,
				columnIndex
			}) { //状态检测
				if (columnIndex === 4) {
					return 'color:#06A1EE'
				}
			},
		}

	}
</script>


<style>
	.modifyBtn{
		color:#06A1EE;
		cursor:pointer;
	}
</style>
