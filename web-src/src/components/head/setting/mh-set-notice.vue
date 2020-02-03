<template>
	<!--公司设置 > 公告中心 -->
	<div class="set-notice">
		<!-- 新增 -->
		<el-row type="flex" class="row-bg item-open" justify="end">
			<!-- 新增-->
			<div class="item-tab-chuang" @click="centerDialogVisible = true">
				<span class="el-icon-plus linear"></span>新增
			</div>
			<!-- 新增弹窗 -->
			<el-dialog title="新增" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear" center>
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="公告名称" prop="noticeName">
						<el-input placeholder="请输入公告名称" v-model="ruleForm.noticeName"></el-input>
					</el-form-item>
					<el-form-item label="公告文件" class="upload upload1" prop="signPathFile">
						<el-input v-model="ruleForm.signPathFile" v-if="false" ></el-input>
						<el-upload class="avatar-uploader" ref="upload" action="#"
						 :show-file-list="false" :on-change="handleAvatarSuccess" :auto-upload="false" :before-upload="beforeAvatarUpload"
						 :data="ruleForm" :on-success="fileSuccess">
							<el-button><span class="iconfont">&#xe614;</span>上传附件</el-button>
							<br>
							<el-input placeholder="请输入公告文件名称" v-model="ruleForm.materialName" :disabled="true"></el-input>
						</el-upload>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
				</span>
			</el-dialog>

			<!-- 修改弹窗 -->
			<el-dialog title="修改" :visible.sync="centerDialogVisible1" width="500px" transition="fade-in-linear" center>
				<el-form :model="ruleForm1" :rules="rules" ref="ruleForm1" label-width="100px" class="demo-ruleForm">
					<el-form-item label="公告名称" prop="noticeName">
						<el-input placeholder="请输入公告名称" v-model="ruleForm1.noticeName"></el-input>
					</el-form-item>
					<el-form-item label="公告文件" class="upload upload1">
						<el-input v-model="ruleForm1.signPathFile" v-if="false" prop="signPathFile"></el-input>
						<el-upload class="avatar-uploader" ref="upload" action="#"
						 :show-file-list="false" :on-change="handleAvatarSuccess1" :auto-upload="false" :before-upload="beforeAvatarUpload1"
						 :data="ruleForm" :on-success="fileSuccess">
							<el-button><span class="iconfont">&#xe614;</span>上传附件</el-button>
							<br>
							<el-input placeholder="请输入公告文件名称" v-model="ruleForm1.materialName" :disabled="true"></el-input>
						</el-upload>
					</el-form-item>
				</el-form>
				<span slot="footer" class="dialog-footer">
					<el-button type="primary" class="linear" @click="submitForm1('ruleForm1')">确 定</el-button>
				</span>
			</el-dialog>

		</el-row>
		<!-- 表格 -->
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab">
				<template slot="empty" class="tabdata-empty">
					<img src="../../../assets/image/no-item.png" alt="">
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" label="公告名称" :show-overflow-tooltip="true" :resizable="false">
					<template slot-scope="scope">
						<p class="text-overflow-ellipsis">{{ scope.row.noticeName }}</p>
						<p style="display: none;">{{ scope.row.noticeId }}</p>
						<p style="display: none;">{{ scope.row.materialPath }}</p>
						<p style="display: none;">{{ scope.row.materialName }}</p>
					</template>
				</el-table-column>
				<el-table-column width="200px" align="center" class-name="table-column" prop="releaseDate" label="发布时间" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column width="200px" align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true" :resizable="false">
					<template slot-scope="scope">
						<el-button type="text" class="download" @click="download(scope.$index, scope.row)">下载</el-button>
						<el-button type="text" class="modify" @click="changeTable(scope.$index, scope.row)">修改</el-button>
						<el-button type="text" class="delete" @click="deleteTable(scope.$index, scope.row)">删除</el-button>
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
			const formdata = new FormData();
			const formdata1 = new FormData();
			return {
				total: 0, //分页总数据
				currentPage: 1, //当前页码
				pageSize: 10, //每页显示多少条数据
				pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
				biaodata: "", //表格数据
				tabdata: [], //当前页数据
				iconform: false,
				iconform1: true,
				centerDialogVisible: false, //弹窗显示
				centerDialogVisible1: false, //弹窗显示
				fileList: [],
				formData: formdata,
				formData1: formdata1,
				ruleForm: {
					noticeName: "",
					releaseDate: "",
					userName: "",
					signPathFile:"",
					materialName:""
				},
				ruleForm1: {
					noticeName: "",
					releaseDate: "",
					userName: "",
					signPathFile:"",
					materialName:"",
					updateDate:""
				},
				//请求头
				config: {
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				},
				rules: {
					noticeName: [{
						required: true,
						message: '请输入公告名称',
						trigger: 'blur'
					},
					{
						max: 120,
						message: '最长120字',
						trigger: 'blur'
					}],
					signPathFile: [{
						required: true,
						message: '请选择公告文件',
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

			// 页面初始化时、点击查询时查询列表数据
			refer() {
				const vm = this;
				// 发送查询列表数据请求
				vm.$axios.post('/config/publicNotice/query/searchPublicNoticeList', JSON.stringify({
						// 页码
						"pageNumber": 1,
						// 每页总条数
						"pageSize": vm.pageSize
					}), vm.config)
					.then(function(response) {
						// 如果返回成功，则绘制数据，否则提示失败信息
						if (response.data.status == "success") {
							// 设置表格数据
							vm.biaodata = response.data.pagedData.dataList;
							// 循环数据放入表格数据对象中
							vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
							// 设置数据总条数
							vm.total = response.data.pagedData.dataCount;
						} else {
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
				vm.$axios.post('/config/publicNotice/query/searchPublicNoticeList', JSON.stringify({
					// 页码
					"pageNumber": vm.currentPage,
					// 每页总条数
					"pageSize": vm.pageSize
				}), vm.config).
				then(function(response) {

					// 如果返回成功，则绘制数据，否则提示失败信息
					if (response.data.status == "success") {
						// 设置表格数据
						vm.biaodata = response.data.pagedData.dataList;
						// 循环数据放入表格数据对象中
						vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
						// 设置数据总条数
						vm.total = response.data.pagedData.dataCount;
					} else {
						vm.$message({
							type: 'error',
							message: response.data.message
						});
					}

				}).catch(function(error) {
					console.log(error);
				});
			},
			// 新建公告文件名称展示
			handleAvatarSuccess(file, fileList) {
				// 校验文件类型
		        if(!this.checkFileType(file,'(doc|docx|xls|xlsx|pdf)','ig'))
	        	return this.$message.error('文件格式必须为doc、docx、xls、xlsx、pdf');
				this.ruleForm.signPathFile = URL.createObjectURL(file.raw)
				this.ruleForm.materialName = file.name;
			},

			// 修改公告文件名称展示
			handleAvatarSuccess1(file, fileList) {
				// 校验文件类型
		        if(!this.checkFileType(file,'(doc|docx|xls|xlsx|pdf)','ig'))
		        return this.$message.error('文件格式必须为doc、docx、xls、xlsx、pdf');
				this.ruleForm1.signPathFile = URL.createObjectURL(file.raw)
				this.ruleForm1.materialName = file.name;
			},
			// 新建时存储公告文件
			beforeAvatarUpload(file, fileList) {
				this.filedata = file;
				return false;
			},
			// 修改时时存储公告文件
			beforeAvatarUpload1(file, fileList) {
				this.filedata1 = file;
				return false;
			},
			// 上传文件成功回调
			fileSuccess(response, file, fileList) {
			},
			/**
			* @method 文件类型校验
			* @param {Object} file 被校验的文件
			* @param {String} pattern 校验模式
			* @param {String} modifiers 修饰符
			*/
			checkFileType(file,pattern,modifiers){
				if(!file)return console.error('文件不能为空');
				if(!file.name)return console.error('文件名不能为空');
				// 正则表达式
				let patt = new RegExp(pattern,modifiers);
				// 分割的文件数组
				let strArr = file.name.split('.');
				// 文件后缀
				let suffix = strArr[strArr.length - 1];
				return patt.test(suffix);
			},
			// 新增表单提交事件
			submitForm(formName) {
				const vm = this;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						// 设置公告名称
						vm.formData.append("noticeName ", vm.ruleForm.noticeName);
						// 二次确认
						vm.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							// 提交文件
							vm.$refs.upload.submit()
							vm.formData.append("signPathFile", vm.filedata)
							// 清空文件名称
							vm.ruleForm.materialName = "";
							// 添加公告请求
							vm.$axios.post('/config/publicNotice/add/savePublicNoticeInfo', vm.formData, {
								headers: {
									'Content-Type': 'multipart/form-data',
								}
							}).then(function(response) {
								// 清空formData
								vm.formData = new FormData();

								// 如果返回成功，则提示成功信息，否则提示失败信息
								if (response.data.status == "success") {
									// 隐藏新增弹窗
									vm.centerDialogVisible = false;
									// 清空上次修改的表单内容
									vm.$refs[formName].resetFields();
									// 查询列表数据
									vm.refer();
									vm.$message({
										type: 'success',
										message: response.data.message,
										center: true
									});
								} else {
									vm.$message({
										type: 'error',
										message: response.data.message,
										center: true
									});
								}

							})
							.catch(function(error) {
								// 清空formData
								vm.formData = new FormData();
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

			// 修改表单提交事件
			submitForm1(formName) {
				const vm = this;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						// 设置公告id
						vm.formData1.append("noticeId ", vm.noticeId);
						// 设置公告名称
						vm.formData1.append("noticeName ", vm.ruleForm1.noticeName);
						// 二次确认
						this.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
							// 提交文件
							vm.$refs.upload.submit();
							vm.formData1.append("signPathFile", vm.filedata1)
							// 清空文件名称
							vm.ruleForm1.materialName = "";
							// 修改时间
							vm.formData1.append("updateDate", vm.ruleForm1.updateDate);
							// 发送修改请求
							vm.$axios.post('/config/publicNotice/modify/updatePublicNoticeInfo', vm.formData1, {
								headers: {
									'Content-Type': 'application/x-www-form-urlencode;charset=utf-8',
								}
							}).then(function(response) {
								// 清空formData1
								vm.formData1 = new FormData();
								// 如果返回成功，则提示成功信息，否则提示失败信息
								if (response.data.status == "success") {
									// 隐藏新增弹窗
									vm.centerDialogVisible1 = false;
									// 清空上次修改的表单内容
									vm.$refs[formName].resetFields();
									// 查询列表数据
									vm.refer();
									vm.$message({
										type: 'success',
										message: response.data.message,
										center: true
									});
								} else {
									vm.$message({
										type: 'error',
										message: response.data.message,
										center: true
									});
								}

							}).catch(function(error) {
								// 清空formData1
								vm.formData1 = new FormData();
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
				// 存储公告id,供修改时传入参数使用
				vm.noticeId = b.noticeId;
				// 发送查询详情请求
				vm.$axios.post('/config/publicNotice/query/searchPublicNoticeInfo', JSON.stringify({
						"noticeId": b.noticeId
					}), vm.config).
				then(function(response) {
					// 公告名称
					vm.ruleForm1.noticeName = response.data.dataVO.noticeName;
					// 公告文件名称
					vm.ruleForm1.materialName = response.data.dataVO.materialName;
					// 修改时间
					vm.ruleForm1.updateDate = response.data.dataVO.updateDate;
				}).catch(function(error) {
					console.log(error);
				});
			},

			// 删除
			deleteTable(a, b) {
				const vm = this;
				// 二次确认
				vm.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center: true
				}).then(() => {
					// 发送删除请求
					vm.$axios.post('/config/publicNotice/delete/deletePublicNoticeInfo',  JSON.stringify({
						"noticeId": b.noticeId
					}), {
						headers: {
							'Content-Type': 'application/json',
							'Accept': 'application/json'
						}
					}).then(function(response) {
						// 如果返回成功，则提示成功信息，否则提示失败信息
						if (response.data.status == "success") {
							// 跳到第一页
							vm.currentPage = 1;
							// 查询列表数据
							vm.refer();
							vm.$message({
								type: 'success',
								message: response.data.message,
								center: true
							});
						} else {
							vm.$message({
								type: 'error',
								message: response.data.message,
								center: true
							});
						}

					})
					.catch(function(error) {
						vm.$message({
							type: 'error',
							message: error,
							center: true
						});
					});

				}).catch(() => {});
			},

			// 下载
			download(a, b) {
				var elemIF = document.createElement('iframe');
				elemIF.src = '/commonModule/downloadFile?filePathName=' + b.materialPath + '&realFileName='+b.materialName;
				elemIF.style.display = 'none';
				document.body.appendChild(elemIF);
			}
		}
	}
</script>
<style lang="scss">
	.set-notice {
		.download {
			color: #06A1EE;
		}

		.delete {
			color: #FC4053;
		}

		.el-dialog--center {
			.el-dialog__body {
				padding: 0;

				.el-form {
					padding: 18px 30px 25px;
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
							text-align: left;
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

					.upload {
						width: 100%;

						.el-upload--picture-card {
							width: 26px;
							height: 16px;
							background: linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
							background: -moz-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
							background: -ms-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
							background: -webkit-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
							border: none;
							line-height: 16px;
							i {
								font-size: 12px;
								color: #8c939d;
							}
						}

						.el-upload {
							.el-button {
								width: 26px !important;
								height: 16px !important;
								border: none !important;
								padding: 0;
								line-height: 16px !important;

								i {
									color: #fff;
									font-size: 8px;
								}
							}
						}

					}

					.upload1 {
						.el-form-item__label {
							float: left
						}

						.el-form-item__content {
							margin-left: 0 !important;
							clear: both;

							.el-upload {
								float: left;
							}

							.el-button {
								width: 90px !important;
								height: 25px !important;
								color: #0099E8;
								float: left;
								position: relative;
								border: 4px solid transparent;
								border-radius: 5px;
								background: linear-gradient(to right, #06a1ee, #4cc2fc);

								&>span {
									left: 0;
									top: 0;
									right: 0;
									bottom: 0;
									margin: auto;
									font-size: 12px;
									margin-right: 3px;
									position: absolute;
									line-height: 25px;
									z-index: 10;

									span {
										font-size: 10px;
										margin-right: 2px;
									}
								}

								&:hover {
									background: linear-gradient(to right, #06a1ee, #4cc2fc) !important;
								}

								&::before {
									position: absolute;
									top: 1px;
									bottom: 1px;
									left: 1px;
									right: 1px;
									background: linear-gradient(#fff, #fff);
									content: '';
									z-index: 0;
									border-radius: 4px;
								}
							}

							img {
								width: 160px;
								height: 90px;
								border: 2px solid #e6e6e6;
								border-radius: 5px;

							}
						}
					}

					div {
						p {
							color: #424242;
							font-size: 12px;
							margin: 15px 0;
							text-align: left;
						}

						.upload-demo {
							.el-upload {
								float: left;
								margin-bottom: 10px;

								.el-button {
									width: 90px;
									height: 25px;
									color: #0099E8;
									border: 1px solid #0099E8;
									font-size: 12px;
									padding: 0;
									line-height: 12px;

									i {
										font-size: 10px;
										color: #0099E8;
										margin-right: 2px;
									}
								}

							}

							ul {
								clear: both;

								li {
									background-color: #f1f5f8;
									text-align: left;

									i {
										display: none;
									}

									a {
										color: #06A1EE;
										padding: 12px 10px;
									}

									.el-progress {
										top: 30px;
									}
								}
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
	}
</style>
