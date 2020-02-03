<template>
	<!-- 公司设置 > 合作公司 -->
	<div class="mh-set-collaborate">
		<!-- 新增 -->
		<el-row type="flex" class="row-bg item-open" justify="end">
			<!-- 新增-->
			<div class="item-tab-chuang" @click="centerDialogVisible = true">
				<span class="el-icon-plus linear"></span>新增
			</div>
			<!-- 新增弹窗 -->
			<el-dialog title="新增" :visible.sync="centerDialogVisible" width="600px" transition="fade-in-linear" center>
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
					<el-form-item label="合作公司名称" prop="companyName">
						<el-input v-model="ruleForm.companyName" placeholder="请输入合作公司名称"></el-input>
					</el-form-item>
					<el-form-item label="序号" prop="serialNum">
						<el-input v-model="ruleForm.serialNum" placeholder="请输入序号"></el-input>
					</el-form-item>
					<el-form-item label="地址" prop="companyAddress">
						<el-input v-model="ruleForm.companyAddress" placeholder="请输入地址"></el-input>
					</el-form-item>
					<el-form-item label="负责人" prop="chargePeople">
						<el-input v-model="ruleForm.chargePeople" placeholder="请输入负责人"></el-input>
					</el-form-item>
					<el-form-item label="电话" prop="phoneNum">
						<el-input v-model="ruleForm.phoneNum" placeholder="请输入电话"></el-input>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
				</span>
			</el-dialog>
			<!-- 修改弹窗 -->
			<el-dialog title="修改" :visible.sync="centerDialogVisible1" width="600px" transition="fade-in-linear" center>
				<el-form :model="ruleForm1" :rules="rules" ref="ruleForm1" label-width="120px" class="demo-ruleForm">
					<el-form-item label="合作公司名称" prop="companyName">
						<el-input v-model="ruleForm1.companyName" placeholder="请输入合作公司名称"></el-input>
					</el-form-item>
					<el-form-item label="序号" prop="serialNum">
						<el-input v-model="ruleForm1.serialNum" placeholder="请输入序号"></el-input>
					</el-form-item>
					<el-form-item label="地址" prop="companyAddress">
						<el-input v-model="ruleForm1.companyAddress" placeholder="请输入地址"></el-input>
					</el-form-item>
					<el-form-item label="负责人" prop="chargePeople">
						<el-input v-model="ruleForm1.chargePeople" placeholder="请输入负责人"></el-input>
					</el-form-item>
					<el-form-item label="电话" prop="phoneNum">
						<el-input v-model="ruleForm1.phoneNum" placeholder="请输入电话"></el-input>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm1('ruleForm1')">确 定</el-button>
				</span>
			</el-dialog>

		</el-row>

		<!-- 表单 -->
		<div class="item-from">
			<div class="item-input">
				<label for="i1">合作公司名称</label>
				<el-input v-model="searchCompanyName" placeholder="请输入公司名称" id="i1"></el-input>
			</div>
			<div class="itemfrom-right">
				<el-button type="primary" icon="el-icon-search" class="linear" @click="searchDataList">查询</el-button>
			</div>
		</div>

		<!-- 表格 -->
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab">
				<template slot="empty" class="tabdata-empty">
					<img src="../../../assets/image/no-item.png" alt="">
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" prop="companyName" label="合作公司名称" :show-overflow-tooltip="true" :resizable="false">
					<template slot-scope="scope">
						<p class="text-overflow-ellipsis">{{ scope.row.companyName }}</p>
						<p style="display: none;">{{ scope.row.companyId }}</p>
					</template>
				</el-table-column>
				<el-table-column align="center" class-name="table-column" prop="serialNum" label="序号" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="companyAddress" label="地址" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="chargePeople" label="负责人" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="phoneNum" label="电话" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip='true' :resizable="false">
					<template slot-scope="scope">
						<el-button type="text" class="download" @click="changeTable(scope.$index, scope.row)">修改</el-button>
					</template>
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
				biaodata: "", //表格数据
				tabdata: [], //当前页数据
				centerDialogVisible: false, //弹窗显示
				centerDialogVisible1: false, //弹窗显示
				searchCompanyName:'',
				companyName:"",
				//请求头
				config: {
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				},
				ruleForm: {
					companyName: "",
					serialNum: "",
					companyAddress: "",
					chargePeople: "",
					phoneNum: ""
				},
				ruleForm1: {
					companyName: "",
					serialNum: "",
					companyAddress: "",
					chargePeople: "",
					phoneNum: "",
					updateDate: ''
				},
				rules: {
					companyName: [{
						required: true,
						message: '请输入合作公司名称',
						trigger: 'blur'
					},{
						max: 30,
						message: '最长30字',
						trigger: 'blur'
					}],
					serialNum: [{
						required: true,
						message: '请输入序号',
						trigger: 'blur'
					},{
						max: 3,
						message: '最长3位',
						trigger: 'blur'
					},{
						validator: this.integer,
						trigger: 'blur'
					}],
					companyAddress: [{
						required: true,
						message: '请输入地址',
						trigger: 'blur'
					},{
						max: 30,
						message: '最长30字',
						trigger: 'blur'
					}],
					chargePeople: [{
						required: true,
						message: '请输入负责人',
						trigger: 'blur'
					},{
						max: 10,
						message: '最长10字',
						trigger: 'blur'
					}],
					phoneNum: [{
						required: true,
						message: '请输入电话',
						trigger: 'blur'
					},{
						max: 11,
						message: '最长11位',
						trigger: 'blur'
					}]
				}
			}
		},
		mounted() {
			// 本地访问
			this.refer();
		},
		methods: {
			// 每页条数切换
			handleSizeChange(val) {
				this.pageSize = val;
				this.refers()
			},

			//页码切换
			handleCurrentChange(val) {
				this.currentPage = val;
				this.refers()
			},

			//分页方法
			currentChangePage(list, i, k) {
				this.tabdata = list;
			},
			//点击查询修改查询条件后查询列表
			searchDataList(){
				this.companyName = this.searchCompanyName;
				this.currentPage = 1;
				this.pageSize = 10;
				this.refer();
			},
			// 页面初始化时、点击查询时查询列表数据
			refer() {
				const vm = this;
				// 发送查询列表数据请求
				vm.$axios.post('/config/cooperationCompany/query/searchCooperationCompanyList', JSON.stringify({
						// 页码
						"pageNumber": 1,
						// 每页总条数
						"pageSize": vm.pageSize,
						// 公司名称
						"companyName": vm.companyName
					}), vm.config)
				.then(function(response) {

					// 如果返回成功，则绘制数据，否则提示失败信息
					if(response.data.status == "success"){
						// 设置表格数据
						vm.biaodata = response.data.pagedData.dataList;
						// 循环数据放入表格数据对象中
						vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
						// 设置数据总条数
						vm.total = response.data.pagedData.dataCount;
					}else{
						vm.$message({
							type: 'error',
							message: response.data.message
						});
					}

				}).catch(function(error) {
					console.log(error);
				});
			},

			// 点击分页
			refers() {
				const vm = this;
				vm.$axios.post('/config/cooperationCompany/query/searchCooperationCompanyList', JSON.stringify({
						// 页码
						"pageNumber": vm.currentPage,
						// 每页总条数
						"pageSize": vm.pageSize,
						// 公司名称
						"companyName": vm.companyName
					}), vm.config).
				then(function(response) {

					// 如果返回成功，则绘制数据，否则提示失败信息
					if(response.data.status == "success"){
						// 设置表格数据
						vm.biaodata = response.data.pagedData.dataList;
						// 循环数据放入表格数据对象中
						vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
						// 设置数据总条数
						vm.total = response.data.pagedData.dataCount;
					}else{
						vm.$message({
							type: 'error',
							message: response.data.message
						});
					}

				}).catch(function(error) {
					console.log(error);
				});
			},

			// 新增数据
			submitForm(formName) {
				const vm = this;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							/* 新增数据 */
							vm.$axios.post('/config/cooperationCompany/add/saveCooperationCompanyInfo', JSON.stringify({
								// 合作公司名称
								"companyName":vm.ruleForm.companyName,
								// 序号
								"serialNum":vm.ruleForm.serialNum,
								// 地址
								"companyAddress":vm.ruleForm.companyAddress,
								// 负责人
								"chargePeople":vm.ruleForm.chargePeople,
								// 电话
								"phoneNum":vm.ruleForm.phoneNum
							}), vm.config)
							.then(function(response) {

								// 如果返回成功，则提示成功信息，否则提示失败信息
								if(response.data.status == "success"){

									// 隐藏新增弹窗
									vm.centerDialogVisible = false;
									// 清空上次添加的表单内容
									vm.$refs[formName].resetFields();
									// 查询列表数据
									vm.refer();
									vm.$message({
										type: 'success',
										message: response.data.message,
										center: true
									});
								}else{
									vm.$message({
										type: 'error',
										message: response.data.message,
										center: true
									});
								}

							}).catch(function(error) {
								vm.$message({
									type: 'error',
									message: error,
									center: true
								});
							});

						}).catch(() => {});

					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},

			// 修改数据
			submitForm1(formName) {
				const vm = this;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							/* 新增数据 */
							vm.$axios.post('/config/cooperationCompany/modify/updateCooperationCompanyInfo', JSON.stringify({
								// 合作公司id
								"companyId":vm.companyId,
								// 合作公司名称
								"companyName":vm.ruleForm1.companyName,
								// 序号
								"serialNum":vm.ruleForm1.serialNum,
								// 地址
								"companyAddress":vm.ruleForm1.companyAddress,
								// 负责人
								"chargePeople":vm.ruleForm1.chargePeople,
								// 电话
								"phoneNum":vm.ruleForm1.phoneNum,
								// 修改时间
								"updateDate":vm.ruleForm1.updateDate
							}), vm.config)
							.then(function(response) {

								// 如果返回成功，则提示成功信息，否则提示失败信息
								if(response.data.status == "success"){
									// 隐藏新增弹窗
									vm.centerDialogVisible1 = false;
									// 清空上次添加的表单内容
									vm.$refs[formName].resetFields();
									// 查询列表数据
									vm.refer();
									vm.$message({
										type: 'success',
										message: response.data.message,
										center: true
									});
								}else{
									vm.$message({
										type: 'error',
										message: response.data.message,
										center: true
									});
								}


							}).catch(function(error) {
								vm.$message({
									type: 'error',
									message: error,
									center: true
								});
							});

						}).catch(() => {});

					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},

			// 点击修改请求数据
			changeTable(a, b) {
				const vm = this;

				// 显示修改弹窗
				this.centerDialogVisible1 = true;
				// 存储合作公司id,供修改时传入参数使用
				vm.companyId = b.companyId;
				// 发送查询详情请求
				vm.$axios.post('/config/cooperationCompany/query/searchCooperationCompanyInfo', JSON.stringify({
						"companyId": b.companyId
					}), vm.config).
				then(function(response) {
					// 合作公司名称
					vm.ruleForm1.companyName = response.data.dataVO.companyName;
					// 序号
					vm.ruleForm1.serialNum = response.data.dataVO.serialNum;
					// 地址
					vm.ruleForm1.companyAddress = response.data.dataVO.companyAddress;
					// 负责人
					vm.ruleForm1.chargePeople = response.data.dataVO.chargePeople;
					// 电话
					vm.ruleForm1.phoneNum = response.data.dataVO.phoneNum;
					// 修改时间
					vm.ruleForm1.updateDate = response.data.dataVO.updateDate;
				}).catch(function(error) {
					console.log(error);
				});
			}
		}
	}
</script>

<style lang="scss">

	/* 表单 */
	.mh-set-collaborate .item-from {
		padding: 14px 20px;
		border-radius: 10px;
		background: #fff;
		box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
		margin-bottom: 20px;
	}

	.mh-set-collaborate .item-from>div {
		display: inline-block;
	}

	.mh-set-collaborate .el-input__inner {
		background-color: #f8f9fb;
	}

	.mh-set-collaborate .item-serial .el-form-item {
		display: block !important;
	}

	.mh-set-collaborate .item-from .el-input {
		width: 125px;
		height: 32px;
		border: 1px solid #f8f9fb;
		border-radius: 5px;
		margin-right: 20px;
		font-size: 12px;
	}

	.mh-set-collaborate .item-input label {
		font-size: 12px;
		color: #333;
		margin-right: 10px;
	}

	.mh-set-collaborate .item-date {
		margin-right: 30px;
	}

	.mh-set-collaborate .item-date span {
		font-size: 12px;
		color: #333;
		margin-right: 10px;
	}

	.mh-set-collaborate .itemfrom-right {
		float: right;
	}

	.mh-set-collaborate .itemfrom-right button {
		margin: 0 10px;
		font-size: 12px;
		width: 90px;
		height: 30px;
		line-height: 30px;
		border-radius: 5px;
		padding: 0;
	}

	.mh-set-collaborate .item-from .el-input .el-input__inner {
		color: #333;
		width: 135px;
		height: 30px;
		line-height: 30px;
	}

	.mh-set-collaborate .item-from .el-range-editor.el-input__inner {
		border-radius: 5px;
		width: 233px;
		height: 30px;
	}

	.mh-set-collaborate .el-date-editor .el-range-input {
		font-size: 12px;
		color: #333333;
		background-color: #f8f9fb;
		height: 28px !important;
	}

	.mh-set-collaborate .item-serial {
		margin: 25px 0;
	}

	.mh-set-collaborate .el-date-editor .el-range__icon,
	.el-date-editor .el-range-separator {
		line-height: 20px;
		color: #B1B1B1 !important;
	}






	.mh-set-collaborate .el-table  .table-column:last-child .cell{
		cursor: pointer;
	}

	/* 弹窗 */
	.mh-set-collaborate .el-dialog--center{
		.el-dialog__body{
			padding: 0;
			.el-form{
				padding: 18px 30px 0;
				border-bottom: 1px solid #dcdfe6;

				.el-form-item{
					border-bottom: 1px solid #dcdfe6;
					margin: 0;
					padding: 10px 0;
					&:last-child{
						border-bottom: none;
					}
					label{
						color: #424242;
						font-size: 12px;
					}
					.el-form-item__content{
						.el-input__inner{
							height: 27px;
							line-height: 27px;
							color: #424242;
							font-size: 12px;
							background-color: #f8f9fb;
						}
					}
				}
			}

		}
		.dialog-footer{
			.el-button{
				width: 120px;
				height: 30px;
				line-height: 30px;
				padding: 0;
				font-size: 12px;
			}
		}

	}


</style>
