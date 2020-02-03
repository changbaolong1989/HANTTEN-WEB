<template>
	<!-- 创建任务弹窗 -->
	<div class="create-task">
		<el-dialog :title="title" :visible.sync="centerDialogVisible" width="710px" transition="fade-in-linear" center @close="handleClose">
			<el-row>
				<el-col :span="12">项目编号：{{projectNum}}</el-col>
				<el-col :span="12">合同编号：{{this.$parent.changeContract.contractNum}}</el-col>
			</el-row>
			<hr color="#dcdfe6" size="1" width="100%">
			<!-- 大表单 -->
			<div class="taskform" v-for="(item,index) in taskform" :key="index">
				<el-form :model="item" ref="taskform">
					<el-row>
						<el-col :span="24">
							<el-form-item label="任务名称" prop="taskName">
								<el-input v-model="item.taskName" placeholder="请输入成果文件名称"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="12">
							<el-form-item label="咨询类别" prop="contractTypeName">
								<el-select v-model="item.contractTypeName" :disabled="title=='修改任务'" placeholder="请输入合同类型" @change="getContractType(item.contractTypeName,index)">
									<el-option v-for="i in contractList" :label="i.contractTypeName" :key="i.contractTypeId" :value="i.contractTypeName"></el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="" prop="counselTypeName">
								<el-select v-model="item.counselTypeName" :disabled="title=='修改任务'" placeholder="请输入咨询类别" @change="getCounselType(item.counselTypeName,index)">
									<el-option v-for="i in item.consultList" :label="i.counselTypeName" :key="i.counselTypeId" :value="i.counselTypeName"></el-option>
								</el-select>
							</el-form-item>
						</el-col>
					</el-row>
					<hr color="#dcdfe6" size="1" width="100%">
					<p>指定人员</p>
					<el-row>
						<el-col :span="12">
							<el-form-item label="编制成果文件人" prop="userName">
								<el-select v-model="item.userName" placeholder="请选择编制成果文件人" @change="getUserId(item.userName,index)">
									<el-option v-for="i in personList" :label="i.userName" :key="i.userId" :value="i.userName"></el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="3">
							<el-button type="text" @click="showTab(item,index)" class="personnel">人员信息<i :class="{'el-icon--right':true,'el-icon-arrow-down':item.iconDown,'el-icon-arrow-up':item.iconUp}"></i></el-button>
						</el-col>
					</el-row>
					<el-row class="showTable" v-show="item.showTable">
						<el-table :data="item.tableData" border style="width: 100%">
							<el-table-column align="center" prop="taskName" label="执行任务" :show-overflow-tooltip='true'></el-table-column>
							<el-table-column align="center" prop="endDate" label="完成时间" :show-overflow-tooltip='true'></el-table-column>
							<el-table-column align="center" prop="needHour" label="所用工时" :show-overflow-tooltip='true'></el-table-column>
						</el-table>
					</el-row>

					<el-row>
						<el-col :span="12">
							<el-form-item label="完成时间" prop="endDate">
								<el-date-picker v-model="item.endDate" value-format="yyyy-MM-dd" type="date" placeholder="请输入完成时间"></el-date-picker>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="所需工时" prop="needHour">
								<el-input placeholder="请输入所需工时" v-model="item.needHour">
									<template slot="append">时</template>
								</el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="24">
							<el-form-item label="任务概况" prop="taskContent">
								<el-input type="textarea" v-model="item.taskContent" placeholder="请输入任务概况"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row class="else">
						<el-col :span="12">
							其他组员：
							<el-button class="linear add" icon="el-icon-plus" @click="addCrew(index)"></el-button>
						</el-col>
					</el-row>
					<hr color="#dcdfe6" size="1" width="100%">
					<!-- 小表单 -->
					<div class="smallForm" v-for="(value,xu) in item.smallForm" :key="xu">
						<el-button type="text" icon="el-icon-close" class="colseform" @click="colseForm1(value,xu,item,index)"></el-button>
						<el-row>
							<el-col :span="12">
								<el-form-item label="执行人" :prop="'smallForm['+xu+'].userName'" :rules="[{ required: true, message: '请选择执行人', trigger: 'blur' }]">
									<el-select v-model="value.userName" placeholder="请选择执行人" @change="getExecutor(value.userName,xu,index)">
										<el-option v-for="i in personList" :label="i.userName" :key="i.userId" :value="i.userName"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="3">
								<el-button type="text" @click="showTab1(index,xu)" class="personnel">人员信息<i :class="{'el-icon--right':true,'el-icon-arrow-down':value.iconDown1,'el-icon-arrow-up':value.iconUp1}"></i></el-button>
							</el-col>
						</el-row>
						<el-row class="showTable" v-show="value.showTable1">
							<el-table :data="value.tableData" border style="width: 100%">
								<el-table-column align="center" prop="taskName" label="执行任务" :show-overflow-tooltip='true'></el-table-column>
								<el-table-column align="center" prop="endDate" label="完成时间" :show-overflow-tooltip='true'></el-table-column>
								<el-table-column align="center" prop="needHour" label="所用工时" :show-overflow-tooltip='true'></el-table-column>
							</el-table>
						</el-row>

						<el-row>
							<el-col :span="12">
								<el-form-item label="完成时间" :prop="'smallForm['+xu+'].endDate'">
									<el-date-picker v-model="value.endDate" value-format="yyyy-MM-dd" type="date" placeholder="请输入完成时间"></el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="所需工时" :prop="'smallForm['+xu+'].needHour'">
									<el-input placeholder="请输入所需工时" v-model="value.needHour">
										<template slot="append">时</template>
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="任务概况" :prop="'smallForm['+xu+'].taskContent'">
									<el-input type="textarea" v-model="value.taskContent" placeholder="请输入任务概况"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
					</div>

				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" class="linear" @click="submitForm('taskform')">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				storageData: this.SStorage.getItem(this.$route.path),
				// 项目编号
				projectNum: "",
				// 默认不显示
				centerDialogVisible: false,
				// 大表单
				taskform: [{
					// 任务id
					taskId:"",
					// 任务名称
					taskName: "",
					// 合同类型id
					contractTypeId:"",
					// 咨询类型id
					counselTypeId:"",
					// 指定人名称
					userName: "",
					// 成果文件人id
					userId: "",
					// 完成时间
					endDate: "",
					// 所需工时
					needHour: "",
					// 任务概况
					taskContent: "",
					// 表格是否显示
					showTable: false,
					// 下箭头
					iconDown: true,
					// 上箭头
					iconUp: false,
					// 其他组员表单
					smallForm: [],
					// 其他组员表单(传到后台参数)
					userList: [],
					// 咨询类型数据
          			consultList:[],
          			// 指定人员信息列表数据
					tableData: []
				}],
				personList:[]
			}
		},
		props:['contractList',"title"],
    	mounted(){
    		// 查询项目编号
	      	this.getProjectNum();

    	},
		methods: {
			// 查询项目编号
		    getProjectNum(){
		        // 查询项目详情
		        this.apiAddress("/project/query/searchProjectInfo", {
		          // 项目id
		          "projectId": this.storageData.projectId
		        }).then(res => {
		          // 如果返回成功，则绘制数据，否则提示失败信息
		          if (res.data.status == "success") {
		            // 设置项目编号
		            this.projectNum = res.data.dataVO.projectNum;
		          } else {
		            this.stateDialog('error', res.data.message)
		          }
		        }).catch(e => {
		          this.stateDialog('error', e)
		        });
		    },
			// 设置合同类型id
			getContractType(val,index){
				let obj = {};
		        // 获取下拉框的所有值 contractList:下拉框
		        obj = this.contractList.find((item) => {
		          return item.contractTypeName === val;
		        });

		        // 合同类型id
		        this.taskform[index].contractTypeId = obj.contractTypeId;

				// 查询合同类别
		        this.apiAddress("/task/query/searchDictCounselTypeListByContractTypeId", {
		          // 合同类别id
		          "contractTypeId": obj.contractTypeId
		        }).then(res => {
		          // 如果返回成功，则绘制数据，否则提示失败信息
		          if (res.data.status == "success") {
		            // 设置咨询类别列表
		            this.taskform[index].consultList = res.data.dataList;
		          } else {
		            this.stateDialog('error', res.data.message)
		          }
		        }).catch(e => {
		          this.stateDialog('error', e)
		        });
			},
			// 动态查询咨询类型
			getCounselType(val,index){
				let obj = {};
		        // 获取下拉框的所有值 consultList:下拉框
		        obj = this.taskform[index].consultList.find((item) => {
		          return item.counselTypeName === val;
		        });
		        // 咨询类型id
		        this.taskform[index].counselTypeId = obj.counselTypeId;
			},
			// 获取执行人信息
			getExecutor(val,xu,index){
				let obj = {};
		        // 获取下拉框的所有值 personList:下拉框
		        obj = this.personList.find((item) => {
		          return item.userName === val;
		        });
		        // 执行人id
		        this.taskform[index].smallForm[xu].userId = obj.userId;

		        // 参数
		        let params = {};
		        if(this.$parent.title == "创建任务"){ // 如果是创建任务
		        	// 参数设置
		        	params = {
			          // 用户id
			          "userId": obj.userId
			        };
		        } else if (this.$parent.title == "修改任务"){ // 如果是修改任务
		        	// 参数设置
		        	params = {
			          // 用户id
			          "userId": obj.userId,
			          // 任务id
			          "taskId": this.taskform[0].taskId
			        };
		        }

		         // 查询人员信息
		        this.apiAddress("/task/query/searchUserInfo", params).then(res => {
		          // 如果返回成功，则绘制数据，否则提示失败信息
		          if (res.data.status == "success") {
		            // 设置人员信息列表
		            this.taskform[index].smallForm[xu].tableData = res.data.dataList;
		          } else {
		            this.stateDialog('error', res.data.message)
		          }
		        }).catch(e => {
		          this.stateDialog('error', e)
		        });
			},
			// 获取指定编纂人姓名
			getUserId(val,index){
				let obj = {};
		        // 获取下拉框的所有值 personList:下拉框
		        obj = this.personList.find((item) => {
		          return item.userName === val;
		        });
		        // 编纂人id
		        this.taskform[index].userId = obj.userId;

		        // 查询人员信息
		        this.apiAddress("/task/query/searchUserInfo", {
		          // 用户id
		          "userId": obj.userId
		        }).then(res => {
		          // 如果返回成功，则绘制数据，否则提示失败信息
		          if (res.data.status == "success") {
		            // 设置人员信息列表
		            this.taskform[index].tableData = res.data.dataList;
		          } else {
		            this.stateDialog('error', res.data.message)
		          }
		        }).catch(e => {
		          this.stateDialog('error', e)
		        });
			},
			// 动态创建表单里面的小表单
			addCrew(index) {
				this.taskform[index].smallForm.push({
					// 执行人id
					userId: "",
					// 成果文件人名称
					userName:"",
					// 是否是主负责人
					isMainDuty:"0",
					// 完成时间
					endDate: "",
					// 所需工时
					needHour: "",
					// 任务概况
					taskContent: "",
					// 表格是否显示
					showTable1: false,
					// 下箭头
					iconDown1: true,
					// 上箭头
					iconUp1: false,
					// 人员信息列表数据
					tableData: []
				});
			},
			// 确认关闭之后清空表单内容
			handleClose() {
				this.$refs.taskform[0].resetFields();
				this.taskform[0].smallForm = [];
				this.taskform[0].userList = [];
				// 清空人员信息列表
		        this.taskform[0].tableData = [];
		        // 清空咨询类型列表
		        this.taskform[0].consultList = [];
			},
			// 关闭小表单
			colseForm1(value, xu, item, index) {
				this.taskform[index].smallForm.splice(xu, 1)
			},
			showTab(item, index) { /* 控制表单中的表格显示 */
				if (!this.taskform[index].showTable) {
					this.taskform[index].showTable = true;
					this.taskform[index].iconDown = false;
					this.taskform[index].iconUp = true;
				} else {
					this.taskform[index].showTable = false;
					this.taskform[index].iconDown = true;
					this.taskform[index].iconUp = false;
				}
			},
			showTab1(index, xu) { /* 控制表单中的表格显示 */
				if (!this.taskform[index].smallForm[xu].showTable1) {
					this.taskform[index].smallForm[xu].showTable1 = true;
					this.taskform[index].smallForm[xu].iconDown1 = false;
					this.taskform[index].smallForm[xu].iconUp1 = true;
				} else {
					this.taskform[index].smallForm[xu].showTable1 = false;
					this.taskform[index].smallForm[xu].iconDown1 = true;
					this.taskform[index].smallForm[xu].iconUp1 = false;
				}
			},
			// 提交添加请求
			submitForm(index) {
				// 二次确认
				this.$confirm('您确认要提交吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'info ',
					center:true
				}).then(() => {
					// 循环遍历任务中的指定人员，并放入userList中
					// userList原本不包括指定负责人，所以就需要在提交数据的时候，手动添加到userList中
					for(let i = 0; i < this.taskform.length; i++){
						// 定义对象
						let userObj = {
							// 指定负责人id
							"userId":this.taskform[i].userId,
							// 是指定负责人
							"isMainDuty":"1",
							// 完成时间
							"endDate":this.taskform[i].endDate,
							// 所需工时
							"needHour":this.taskform[i].needHour,
							// 任务概述
							"taskContent":this.taskform[i].taskContent
						};
						// 把指定负责人添加到userList的头部
						this.taskform[i].userList = this.taskform[i].userList.concat(this.taskform[i].smallForm);
						// 把指定负责人添加到userList的头部
						this.taskform[i].userList.unshift(userObj);
					}

					// 访问后台的url
					let url = "";
					// 添加到后台的参数
					let params = {};
					// 如果是创建任务，则设置创建任务的参数和url
					if(this.title == "创建任务"){
						// 创建任务地址
						url = "/task/add/insertTaskInfo";
						// 创建任务参数
						params = {
				        	// 设置合同id
				        	"contractId":this.$parent.changeContract.contractId,
				        	// 表单数据
				        	"taskList":this.taskform
				        };
				        // 如果是修改任务，则设置修改任务的参数和url
					} else if(this.title == "修改任务"){
						// 修改任务地址
						url = "/task/modify/updateTaskInfo";
						// 修改任务参数
						params = {
				        	// 表单数据
				        	"taskList":this.taskform
				        };
					}

					// 提交请求
			        this.apiAddress(url, params).then(res => {
			          // 如果返回成功，则绘制数据，否则提示失败信息
			          if (res.data.status == "success") {
			            this.stateDialog('success', res.data.message);
			            // 关闭弹窗
			            this.centerDialogVisible = false;
						// 触发事件,重新刷新项目树
            			this.bus.$emit('asideRefresh');
			          } else {
			            this.stateDialog('error', res.data.message)
			          }
			        }).catch(e => {
			          this.stateDialog('error', e)
			        });

				}).catch(() => {

				});
			}
		}
	}
</script>
<style lang="scss">
	.create-task {
		.el-dialog__body {
			font-size: 12px;
			color: #424242;
			max-height: 700px;
			overflow: auto;
		}

		/* 大表单 */
		.taskform {
			background-color: #f1f5f8;
			border-radius: 10px;
			padding: 20px 15px;

			.showTable {
				box-shadow: 0px 0px 20px rgba(176, 192, 202, 0.5) inset;

				.el-table__empty-text{
					margin:0px auto!important;
				}
				.el-table--border {
					background-color: #f1f5f8;
					border: none;
					margin: 10px 0;
				}

				.el-table {

					tr,
					th,
					td {
						background-color: #f1f5f8 !important;
					}
				}

			}

			.colseform {
				position: absolute;
				right: 10px;
				top: 5px;
				color: #B0B0B0;

				i {
					font-size: 18px;
				}

			}

			.el-form {
				.personnel {
					line-height: 40px !important;
					color: #06A1EE;
				}

				.el-form-item {
					margin-bottom: 0px;
					overflow: hidden;

					.el-form-item__label {
						font-size: 12px !important;
						color: #424242;
						display: inline;
					}

					.el-form-item__content {
						display: inline;

						.el-select {
							.el-input {
								width: 100%;
							}
						}

						.el-date-editor--date {
							width: 70% !important;
						}

						.el-input-group--append {
							width: 70% !important;
							margin-top: 5px;

							input {
								border-top-right-radius: 0px !important;
								border-bottom-right-radius: 0px !important;
							}
						}

						.el-textarea {
							width: 90%;
							margin-top: 10px;

							.el-textarea__inner {
								min-height: 57px;
							}
						}

						.el-input {
							width: 90%;
						}
					}
				}

				.smallForm {
					position: relative;
					padding: 25px 0;

					.colseform {
						top: -5px;
					}
				}

				.else {
					margin-top: 15px;
				}
			}
		}
	}
</style>

<style lang="scss" scoped="scoped">
	.create-task {
		.add {
			color: #fff;
			width: 26px;
			height: 16px !important;
			padding: 0;
			font-size: 10px;
			line-height: 16px !important;
			border: none;
		}

		hr {
			margin: 15px 0;
		}

		.cities {
			span {
				margin-right: 20px;
			}

			.el-checkbox-group {
				display: inline;

				.el-checkbox__label {
					color: #808080;
				}
			}
		}

		/* 大表单 */
		.taskform {
			background-color: #f1f5f8;
			border-radius: 10px;
			padding: 35px 15px 20px;
			margin: 10px 0 0;
			position: relative;

			.el-table {
				tr {
					background-color: transparent !important;
				}
			}
		}
	}
</style>
