<template>
	<!-- 公司设置 > 公司信息 -->
	<div class="set-inform">
		<!-- 新增 -->
		<el-row type="flex" class="row-bg item-open" justify="end">
			<!-- 新增-->
			<div class="item-tab-chuang" @click="centerDialogVisible = true">
				<span class="el-icon-plus linear"></span>新增
			</div>
			<!-- 新增弹窗 -->
			<el-dialog title="新增" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center>
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="公司名称" prop="companyName">
						<el-input v-model="ruleForm.companyName" placeholder="请输入公司名称"></el-input>
					</el-form-item>
					<el-form-item label="公司缩写" prop="condense">
						<el-input v-model="ruleForm.condense" placeholder="请输入公司缩写"></el-input>
					</el-form-item>
					<el-form-item label="开户行" prop="bankName">
						<el-input v-model="ruleForm.bankName" placeholder="请输入开户行"></el-input>
					</el-form-item>
					<el-form-item label="帐号" prop="bankAccount">
						<el-input v-model="ruleForm.bankAccount" placeholder="请输入帐号"></el-input>
					</el-form-item>
					<el-form-item label="邮编" prop="postCode">
						<el-input v-model="ruleForm.postCode" placeholder="请输入邮编"></el-input>
					</el-form-item>
					<el-form-item label="电话(开票用)" prop="phoneNum">
						<el-input v-model="ruleForm.phoneNum" placeholder="请输入电话(开票用)"></el-input>
					</el-form-item>
					<el-form-item label="电话(对外联系用)" prop="phoneForeign">
						<el-input v-model="ruleForm.phoneForeign" placeholder="请输入(对外联系用)"></el-input>
					</el-form-item>
					<el-form-item label="传真" prop="faxNum">
						<el-input v-model="ruleForm.faxNum" placeholder="请输入传真"></el-input>
					</el-form-item>
					<el-form-item label="邮箱" prop="email">
						<el-input v-model="ruleForm.email" placeholder="请输入邮箱"></el-input>
					</el-form-item>
					<el-form-item label="公司地址" prop="companyAddress">
						<el-input v-model="ruleForm.companyAddress" placeholder="请输入公司地址"></el-input>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
				</span>
			</el-dialog>

			<!-- 修改弹窗 -->
			<el-dialog title="修改" :visible.sync="centerDialogVisible1" width="500px" transition="fade-in-linear" center>
				<el-form :model="ruleForm1" :rules="rules" ref="ruleForm1" label-width="100px" class="demo-ruleForm">
					<el-form-item label="公司名称" prop="companyName">
						<el-input v-model="ruleForm1.companyName" placeholder="请输入公司名称"></el-input>
					</el-form-item>
					<el-form-item label="公司缩写" prop="condense">
						<el-input v-model="ruleForm1.condense" placeholder="请输入公司缩写"></el-input>
					</el-form-item>
					<el-form-item label="开户行" prop="bankName">
						<el-input v-model="ruleForm1.bankName" placeholder="请输入开户行"></el-input>
					</el-form-item>
					<el-form-item label="帐号" prop="bankAccount">
						<el-input v-model="ruleForm1.bankAccount" placeholder="请输入帐号"></el-input>
					</el-form-item>
					<el-form-item label="邮编" prop="postCode">
						<el-input v-model="ruleForm1.postCode" placeholder="请输入邮编"></el-input>
					</el-form-item>
					<el-form-item label="电话(开票用)" prop="phoneNum">
						<el-input v-model="ruleForm1.phoneNum" placeholder="请输入电话(开票用)"></el-input>
					</el-form-item>
					<el-form-item label="电话(对外联系用)" prop="phoneForeign">
						<el-input v-model="ruleForm1.phoneForeign" placeholder="请输入(对外联系用)"></el-input>
					</el-form-item>
					<el-form-item label="传真" prop="faxNum">
						<el-input v-model="ruleForm1.faxNum" placeholder="请输入传真"></el-input>
					</el-form-item>
					<el-form-item label="邮箱" prop="email">
						<el-input v-model="ruleForm1.email" placeholder="请输入邮箱"></el-input>
					</el-form-item>
					<el-form-item label="公司地址" prop="companyAddress">
						<el-input v-model="ruleForm1.companyAddress" placeholder="请输入公司地址"></el-input>
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
				<label for="i1">公司名称</label>
				<el-input v-model="searchCompanyName" placeholder="请输入公司名称" id="i1"></el-input>
			</div>
			<div class="item-input">
				<label for="i2">公司缩写</label>
				<el-input v-model="searchCondense" placeholder="请输入公司缩写" id="i2"></el-input>
			</div>
			<div class="itemfrom-right">
				<el-button type="primary" icon="el-icon-search" class="linear" @click="refer">查询</el-button>
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
				<el-table-column align="center" class-name="table-column" prop="companyName" label="公司名称" :show-overflow-tooltip="true" :resizable="false">
					<template slot-scope="scope">
						<p class="text-overflow-ellipsis">{{ scope.row.companyName }}</p>
						<p style="display: none;">{{ scope.row.companyId }}</p>
					</template>
				</el-table-column>
				<el-table-column align="center" class-name="table-column" prop="condense" label="公司缩写" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="bankName" label="开户行" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="bankAccount" label="账号" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="postCode" label="邮编" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
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
				tabdata:[], //当前页数据
				centerDialogVisible: false, //弹窗显示
				centerDialogVisible1: false, //弹窗显示
				searchCompanyName:"",//公司名称查询值
				searchCondense:"",//公司缩写查询值
				companyName:"",//公司名称查询条件
				condense:"",//公司缩写查询条件
				//请求头
				config: {
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				},
				ruleForm: {
					companyName: '',
					condense: '',
					bankName: '',
					bankAccount: "",
					postCode: "",
					phoneNum: '',
		  phoneForeign: "",
					faxNum:'',
					email: '',
		  companyAddress:''
				},
				ruleForm1: {
					companyName: '',
					condense: '',
					bankName: '',
					bankAccount: "",
					postCode: "",
					phoneNum: '',
		  phoneForeign:"",
					faxNum:'',
					email: '',
		  companyAddress:'',
					updateDate: ''
				},
				rules: {
					companyName: [
						{ required: true,message: '请输入公司名称', trigger: 'blur'},
						{ max: 30, message: '最长30字', trigger: 'blur' }
					],
					condense: [
						{ required: true, message: '请选择公司缩写', trigger: 'blur'},
						{ max: 5, message: '最长5位', trigger: 'blur' },
						{ validator: this.capital, trigger: 'blur' }
					],
					bankName: [
						{ required: true, message: '请输入开户行', trigger: 'blur' },
						{ max: 50, message: '最长50字', trigger: 'blur' }
					],
					bankAccount: [
						{ required: true, message: '请输入帐号', trigger: 'blur' },
						{ max: 20, message: '最长20位', trigger: 'blur' }
					],
					postCode: [
						{ required: true, message: '请输入邮编', trigger: 'blur' },
						{ max: 6, message: '最长6位', trigger: 'blur' }
					],
					phoneNum: [
						{ required: true, message: '请输入电话(开票用)', trigger: 'blur' },
						{ max: 13, message: '最长13位', trigger: 'blur' }
			// ,{ validator: this.fixation, trigger: 'blur' }
					],
		  phoneForeign: [
						{ required: true, message: '请输入电话(对外联系用)', trigger: 'blur' },
						{ max: 13, message: '最长13位', trigger: 'blur' }
					],
					faxNum: [
						{ required: true, message: '请输入传真', trigger: 'blur' },
						{ max: 20, message: '最长20位', trigger: 'blur' }
					],
					email: [
						{ required: true, message: '请输入邮箱', trigger: 'blur' },
						{ type: "email", message: '必须输入邮箱格式', trigger: 'blur' },
						{ max: 50, message: '最长50位', trigger: 'blur' }
					],
		  companyAddress: [
			{ required: true, message: '请输入公司地址', trigger: 'blur' },
			{ max: 50, message: '最长40位', trigger: 'blur' }
		  ]
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

			// 页面初始化时、点击查询时查询列表数据
			refer() {
				const vm = this;
				vm.companyName = vm.searchCompanyName;//公司名称查询条件
				vm.condense = vm.searchCondense;//公司缩写查询条件
				vm.currentPage = 1;
				vm.pageSize = 10;
				vm.refers();
			},

			// 点击分页
			refers() {
				const vm = this;
				vm.$axios.post('/config/company/query/searchCompanyList', JSON.stringify({
						// 页码
						"pageNumber": vm.currentPage,
						// 每页总条数
						"pageSize": vm.pageSize,
						// 公司名称
						"companyName": vm.companyName,
						// 公司缩写
						"condense": vm.condense
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
				vm.$refs[formName].validate((valid) => {
					if (valid) {
						vm.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							/* 新增数据 */
							vm.$axios.post('/config/company/add/saveCompanyInfo', JSON.stringify({
								// 公司名称
								"companyName":vm.ruleForm.companyName,
								// 公司缩写
								"condense":vm.ruleForm.condense,
								// 开户行
								"bankName":vm.ruleForm.bankName,
								// 账号
								"bankAccount":vm.ruleForm.bankAccount,
								// 邮编
								"postCode":vm.ruleForm.postCode,
								// 电话（开票用）
								"phoneNum":vm.ruleForm.phoneNum,
				// 电话（对外联系用）
				"phoneForeign":vm.ruleForm.phoneForeign,
								// 传真
								"faxNum":vm.ruleForm.faxNum,
								// 邮箱
								"email":vm.ruleForm.email,
				// 公司地址
				"companyAddress":vm.ruleForm.companyAddress
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
									vm.stateDialog('success', response.data.message);
								}else{
									vm.stateDialog('error', response.data.message);
								}

							}).catch(function(error) {
								vm.stateDialog('error', error);
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
				vm.$refs[formName].validate((valid) => {
					if (valid) {
						vm.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							// 修改数据
							vm.$axios.post('/config/company/modify/updateCompanyInfo', JSON.stringify({
								// 公司id
								"companyId":vm.companyId,
								// 公司名称
								"companyName":vm.ruleForm1.companyName,
								// 公司缩写
								"condense":vm.ruleForm1.condense,
								// 开户行
								"bankName":vm.ruleForm1.bankName,
								// 账号
								"bankAccount":vm.ruleForm1.bankAccount,
								// 邮编
								"postCode":vm.ruleForm1.postCode,
								// 电话（开票用）
								"phoneNum":vm.ruleForm1.phoneNum,
				// 电话（对外联系用）
				"phoneForeign":vm.ruleForm1.phoneForeign,
								// 传真
								"faxNum":vm.ruleForm1.faxNum,
								// 邮箱
								"email":vm.ruleForm1.email,
				// 公司地址
				"companyAddress":vm.ruleForm1.companyAddress,
								// 修改时间
								"updateDate":vm.ruleForm1.updateDate
							}), vm.config)
							.then(function(response) {

								// 如果返回成功，则提示成功信息，否则提示失败信息
								if(response.data.status == "success"){
									// 隐藏新增弹窗
									vm.centerDialogVisible1 = false;
									// 清空上次修改的表单内容
									vm.$refs[formName].resetFields();
									// 查询列表数据
									vm.refer();
									vm.stateDialog('error', response.data.message);
								}else{
				  vm.stateDialog('error', response.data.message);
								}

							}).catch(function(error) {
								vm.stateDialog('error', error);
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
				vm.centerDialogVisible1 = true;
				// 存储公司id,供修改时传入参数使用
				vm.companyId = b.companyId;
				// 发送查询详情请求
				vm.$axios.post('/config/company/query/searchCompanyInfo', JSON.stringify({
						"companyId": b.companyId
					}), vm.config).
				then(function(response) {
					// 公司名称
					vm.ruleForm1.companyName = response.data.dataVO.companyName;
					// 公司缩写
					vm.ruleForm1.condense = response.data.dataVO.condense;
					// 开户行
					vm.ruleForm1.bankName = response.data.dataVO.bankName;
					// 账号
					vm.ruleForm1.bankAccount = response.data.dataVO.bankAccount;
					// 邮编
					vm.ruleForm1.postCode = response.data.dataVO.postCode;
					// 电话(开票用)
					vm.ruleForm1.phoneNum = response.data.dataVO.phoneNum;
		  // 电话(对外联系用)
		  vm.ruleForm1.phoneForeign = response.data.dataVO.phoneForeign;
					// 传真
					vm.ruleForm1.faxNum = response.data.dataVO.faxNum;
					// 邮箱
					vm.ruleForm1.email = response.data.dataVO.email;
		  // 公司地址
		  vm.ruleForm1.companyAddress = response.data.dataVO.companyAddress;
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

	/* 表单 start */
	.set-inform .item-from {
		padding: 14px 20px;
		border-radius: 10px;
		background: #fff;
		box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
		margin-bottom: 20px;
	}

	.set-inform .item-from>div {
		display: inline-block;
	}

	.set-inform .el-input__inner {
		background-color: #f8f9fb;
	}

	.set-inform .item-serial .el-form-item {
		display: block !important;
	}

	.set-inform .item-from .el-input {
		width: 125px;
		height: 32px;
		border: 1px solid #f8f9fb;
		border-radius: 5px;
		margin-right: 20px;
		font-size: 12px;
	}

	.set-inform .item-input label {
		font-size: 12px;
		color: #333;
		margin-right: 10px;
	}

	.set-inform .item-date {
		margin-right: 30px;
	}

	.set-inform .item-date span {
		font-size: 12px;
		color: #333;
		margin-right: 10px;
	}

	.set-inform .itemfrom-right {
		float: right;
	}

	.set-inform .itemfrom-right button {
		margin: 0 10px;
		font-size: 12px;
		width: 90px;
		height: 30px;
		line-height: 30px;
		border-radius: 5px;
		padding: 0;
	}

	.set-inform .item-from .el-input .el-input__inner {
		color: #333;
		width: 135px;
		height: 30px;
		line-height: 30px;
	}

	.set-inform .item-from .el-range-editor.el-input__inner {
		border-radius: 5px;
		width: 233px;
		height: 30px;
	}

	.set-inform .el-date-editor .el-range-input {
		font-size: 12px;
		color: #333333;
		background-color: #f8f9fb;
		height: 28px !important;
	}

	.set-inform .item-serial {
		margin: 25px 0;
	}

	.set-inform .el-date-editor .el-range__icon,
	.el-date-editor .el-range-separator {
		line-height: 20px;
		color: #B1B1B1 !important;
	}

	/* 表单 end */




	.set-inform .el-table  .table-column:last-child .cell{
		cursor: pointer;
	}

	/* 弹窗 */
	.set-inform .el-dialog--center{
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
						width: 113px!important;
						color: #424242;
						font-size: 12px;
					}
					.el-form-item__content{
						margin-left: 113px!important;
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
