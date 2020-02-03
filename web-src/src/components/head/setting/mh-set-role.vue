<template>
	<!-- 公司设置 > 角色管理 -->
	<div class="mh-set-role">
		<!-- 新增 -->
		<el-row type="flex" class="row-bg item-open" justify="end">
			<!-- 新增/修改-->
			<div class="item-tab-chuang" @click="addRole()">
				<span class="el-icon-plus linear"></span>
				新增
			</div>
			<!-- 新增弹窗 -->
			<el-dialog :title="formTitle" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center
			 :before-close="handleClose">
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="角色名称" prop="name">
						<el-input v-model="ruleForm.name" placeholder="请输入角色名称"></el-input>
					</el-form-item>
					<el-form-item label="访问权限" prop="tree" class="tree-error">
						<el-tree :data="menuTreeDate" show-checkbox default-expand-all node-key="menuId" ref="menuTree" highlight-current
						 :props="props">
						</el-tree>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitRolefunc('ruleForm')">确 定</el-button>
				</span>
			</el-dialog>

		</el-row>
		<!-- 表单 -->
		<div class="item-from">
			<div class="item-input">
				<label for="i1">角色名称</label>
				<el-input v-model="searchRoleName" placeholder="请输入角色名称" id="roleName"></el-input>
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
					<img src="../../../assets/image/no-item.png" alt="" />
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" prop="roleName" label="角色名称" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="createUserName" label="创建人" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="menuNames" label="角色权限" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
					<template slot-scope="scope">
						<el-button type="text" class="modify" @click="updateRole(scope.row.roleId)">修改</el-button>
						<el-button type="text" class="delete" @click="deleteRole(scope.row.roleId)">删除</el-button>
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
</template>

<script>
	export default {
		data() {
			var validateTree = (rule, value, callback) => {
				if (this.$refs.menuTree.getCheckedKeys().length == 0) {
					callback(new Error('请选择访问权限'));
				} else {
					callback();
				}
			};
			return {
				total: 0, //分页总数据
				currentPage: 1, //当前页码
				pageSize: 10, //每页显示多少条数据
				pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
				tabdata: [], //当前页数据
				menuTreeDate: [],
				updateRoleId: '',
				parentMenuIdList: [],
				centerDialogVisible: false, //弹窗显示
				searchRoleName: '',
				roleName: '',
				formTitle: '新增',
				config: {
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				},
				ruleForm: {
					name: '',
					updateDate: '',

				},
				rules: {
					name: [
						{required: true,message: '请输入角色名称',trigger: 'blur'},
						{ max: 20, message: '最长20字', trigger: 'blur' }
					],
					tree: [{
						validator: validateTree,
						trigger: 'change'
					}]
				},
				props: {
					label: 'menuName',
					children: 'childrenList'
				},
				count: 1
			};
		},
		mounted() {
			this.searchList(1, this.pageSize, null);
			this.searchMenuList();
		},
		methods: {
			handleClose(done) {
				this.$refs.ruleForm.resetFields();
				this.$refs.menuTree.setCheckedKeys([]);
				done()
			},
			refer() {
				this.roleName = this.searchRoleName
				this.searchList(this.currentPage, this.pageSize, this.roleName)
			},
			deleteRole(roleId) {
				this.$confirm('是否删除该角色', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center: true
				}).then(() => {
					const vm = this;
					vm.$axios.post('/config/role/delete/deleteRoleInfo', {
							roleId: roleId
						}, vm.config).then(function(response) {
							if (response.data.status == 'failure') {
								vm.$message({
									message: response.data.message,
									type: 'error',
									center: true
								});
							} else {
								vm.$message({
									message: response.data.message,
									type: 'success',
									center: true
								});
							}
							vm.refer()
						})
						.catch(function(error) {
							vm.$message({
								message: error,
								type: 'error',
								center: true
							});
						});
				}).catch(() => {
				});
			},
			searchList(pageNumber, pageSize, searchRoleName) {
				const vm = this;
				let formData = {
					roleName: searchRoleName,
					pageNumber: pageNumber,
					pageSize: pageSize
				};
				vm.$axios.post('/config/role/query/findRoleList', formData, vm.config).then(function(response) {
						if (response.data.status == 'failure') {
							vm.$message({
								message: response.data.message,
								type: 'error',
								center: true
							});
						} else {
							let table = response.data.pagedData.dataList;
							vm.tabdata = table;
							vm.total = response.data.pagedData.dataCount;
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
			searchMenuList() {
				const vm = this;
				vm.$axios.post('/config/role/query/findAllMenuList', {}, vm.config).then(function(response) {
						if (response.data.status == 'failure') {
							vm.$message({
								message: response.data.message,
								type: 'error',
								center: true
							});
						} else {
							let menuTreeDate = response.data.pagedData.dataList;
							for (var i = 0; i < menuTreeDate.length; i++) {
								if (menuTreeDate[i].childrenList.length != 0) {
									vm.parentMenuIdList.push(menuTreeDate[i].menuId);
								}
							}
							vm.menuTreeDate = menuTreeDate;
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
			addRole() {
				this.centerDialogVisible = true;
				this.formTitle = '新增'
			},
			updateRole(roleId) {
				this.centerDialogVisible = true;
				this.formTitle = '修改'
				this.updateRoleId = roleId;
				const vm = this;

				vm.$axios.post('/config/role/query/findRoleInfo', {
						roleId: roleId
					}, vm.config).then(function(response) {
						if (response.data.status == 'failure') {
							vm.$message({
								message: response.data.message,
								type: 'error',
								center: true
							});
						} else {
							vm.ruleForm.name = response.data.dataVO.roleName;
							vm.ruleForm.updateDate = response.data.dataVO.updateDate;
							let checkKeys = response.data.dataVO.menuVOList;
							if (vm.parentMenuIdList.length > 0) {
								for (var i = 0; i < vm.parentMenuIdList.length; i++) {
									for (var j = 0; j < checkKeys.length; j++) {
										if (checkKeys[j] == vm.parentMenuIdList[i]) {
											checkKeys.splice(j, 1);
											j--;
										}
									}
								}
							}
							vm.$refs.menuTree.setCheckedKeys(checkKeys);
						}

						vm.refer()
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
				this.searchList(this.currentPage, this.pageSize, this.roleName)
			},
			handleCurrentChange(val) {
				//页码切换
				this.currentPage = val;
				this.searchList(this.currentPage, this.pageSize, this.roleName)
			},
			submitRolefunc(formName) {
				this.$refs[formName].validate(valid => {
					let keys = this.$refs.menuTree.getCheckedKeys();
					let halfKeys = this.$refs.menuTree.getHalfCheckedKeys();
					keys = keys.concat(halfKeys)
					if (valid) {
						let formData = {
							roleName: this.ruleForm.name,
							menuVOList: keys
						}
						let url = '/config/role/add/insertRoleInfo'
						if (this.formTitle == '修改') {
							formData = {
								roleId: this.updateRoleId,
								roleName: this.ruleForm.name,
								updateDate: this.ruleForm.updateDate,
								menuVOList: keys
							}
							url = '/config/role/modify/updateRoleInfo'
						}
						const vm = this;
						vm.$axios.post(url, formData, vm.config).then(function(response) {
								if (response.data.status == 'failure') {
									vm.$message({
										message: response.data.message,
										type: 'error',
										center: true
									});
								} else {
									vm.refer();
									vm.$message({
										message: response.data.message,
										type: 'success',
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
						this.centerDialogVisible = false;
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			}
		}
	};
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

				.tree-error {
					.el-form-item__error {
						top: 5px;
						left: 60%;

						&::before {
							border: 5px solid transparent;
							border-bottom: 5px solid transparent;
							border-right: 5px solid #F85B48;
							left: -10px;
							top: 5px;
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
