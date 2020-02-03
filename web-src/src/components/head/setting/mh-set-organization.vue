<template>
	<!-- 公司设置 > 组织架构 -->
	<div class="mh-set-collaborate">
		<!-- 新增 -->
		<el-row type="flex" class="row-bg item-open" justify="end">
			<!-- 新增弹窗 -->
			<el-dialog title="新增" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center>
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="部门名称" prop="name">
						<el-input v-model="ruleForm.name" placeholder="请输入部门名称"></el-input>
					</el-form-item>
					<el-form-item label="部门职位" prop="address">
						<el-select v-model="ruleForm.region" placeholder="请选择所属部门" style="width: 100%;">
							<el-option label="区域一" value="shanghai"></el-option>
							<el-option label="区域二" value="beijing"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="备注" prop="open">
						<el-input style="60px" type="textarea" placeholder="请输入备注" v-model="ruleForm.region"></el-input>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
				</span>
			</el-dialog>

		</el-row>

		<!-- 表格 -->
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab" :cell-style="rowClass">
				<template slot="empty" class="tabdata-empty">
					<img src="../../../assets/image/no-item.png" alt="">
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" prop="number" label="部门名称"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="count" label="上级部门"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="contract" label="部门流"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="tel" label="部门职位"></el-table-column>
				<el-table-column align="center" class-name="table-column" label="操作">
					<template slot-scope="scope">
						<el-button @click="centerDialogVisible= true" type="text" size="small">修改</el-button>
						<el-button @click="centerDialogVisible= true" type="text" size="small">新增</el-button>
						<el-button type="text" size="small" class="delete" @click="open2">删除</el-button>
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
			const table = [{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
				{
					"number": "北京汉腾工程顾问有限公司",
					"count": "BJHTGCGW",
					"contract": "中国邮政储蓄银行分行营业部",
					"tel": "010-66789123",
					"stage": "修改",
				},
			]
			return {
				total: 100, //分页总数据
				currentPage: 1, //当前页码
				pageSize: 10, //每页显示多少条数据
				pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
				biaodata: table, //表格数据
				tabdata: "", //当前页数据
				centerDialogVisible: false, //弹窗显示
				ruleForm: {
					name: '',
					address: '',
					open: '',
					account: '',
					postcode: "",
					tel: "",
					fax: '',
					email: ''
				},
				rules: {
					name: [{
						required: true,
						message: '请输入公司名称',
						trigger: 'blur'
					}],
					address: [{
						required: true,
						message: '请输入公司地址',
						trigger: 'blur'
					}],
					open: [{
						required: true,
						message: '请输入开户行',
						trigger: 'blur'
					}],
					account: [{
						required: true,
						message: '请输入帐号',
						trigger: 'blur'
					}],
					postcode: [{
						required: true,
						message: '请输入邮编',
						trigger: 'blur'
					}],
					tel: [{
						required: true,
						message: '请输入电话',
						trigger: 'blur'
					}],
					fax: [{
						required: true,
						message: '请输入传真',
						trigger: 'blur'
					}],
					email: [{
						required: true,
						message: '请输入邮箱',
						trigger: 'blur'
					}]
				}
			}
		},
		created() {
			// 本地访问 
			this.currentChangePage(this.biaodata, this.currentPage, this.pageSize);
		},
		methods: {
			handleSizeChange(val) { // 每页条数切换 
				this.pageSize = val;
				this.currentChangePage(this.biaodata, this.currentPage, this.pageSize);
			},
			handleCurrentChange(val) { //页码切换
				this.currentPage = val;
				this.currentChangePage(this.biaodata, this.currentPage, this.pageSize)
			},
			currentChangePage(list, i, k) { //分页方法
				this.tabdata = list.slice((i - 1) * k, i * k);
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
			submitForm(formName) {/* 表单提交验证 */
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$message({
						  message: '恭喜你，提交成功',
						  type: 'success',
						  center:true
						});
						this.centerDialogVisible = false;
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			open2() {/* 删除二次确认框 */
				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center: true
				}).then(() => {
					this.$message({
						type: 'success',
						message: '删除成功!'
					});
				}).catch(() => {
				});
			}
		}
	}
</script>

<style lang="scss">
	.mh-set-collaborate .el-table .table-column:last-child .cell {
		cursor: pointer;

		.delete {
			color: #FC4053;
		}
	}

	/* 弹窗 */
	.mh-set-collaborate .el-dialog--center {
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
						width: 70px !important;
						color: #424242;
						font-size: 12px;
					}

					.el-form-item__content {
						margin-left: 70px !important;

						.el-input__inner {
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
</style>
