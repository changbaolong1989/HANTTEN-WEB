<template>
	<!-- 工作台 市场部详情右侧折叠面板 -->
	<div class="right--work-contract-tenderTask">
    <div class="given-container">
		<el-collapse class="level1" v-model="activeNames">
			<fileList :title="rowInfoLabel" :showUploadBtn="uploadBtnFlag" :options="fileTypeList" @del="deleteFileInfo" :list="fileList"
				@submit="submitForm" v-if="rowInfoLevel==2"></fileList>
			<reviewFileList title="相关资料" :showUploadBtn="false" @del="deleteFileInfo" :list="reviewFileList" :delBtnFlag="false" v-if="(periodType==2||periodType==3)&&reviewFileListShowFlag"></reviewFileList>
			<template v-for="(item,i) in opinionList">
				<collapse-review-read :object="item" name="item.taskKey" :key="i" v-if="periodType==2||periodType==3"></collapse-review-read>
			</template>
			<!-- 面板-复核可写-->
			<collapse-review-write :object="object" name="white1" @submit="changeObject" :errorTyleList="errorTypeList" v-if='(userType==4||userType==3||userType==2)&&(periodType==2||periodType==3)' ></collapse-review-write>
			<fileList title="审核意见文件" :showUploadBtn="false" :delBtnFlag="false" :list="reviewFileRelationList" v-if="reviewFileRelationList.length>0&&(periodType==2||periodType==3)"></fileList>
		</el-collapse>
		<!-- 提交审核弹窗组件 -->
		<dialog-submit-audit ref="submit" @submit="submitConfirm" :above="above" @cancel="cancel" :reviewer="reviewer"></dialog-submit-audit>
	</div>

		<div class="btn-group">
			<p style="background-color: #B6C3CE;padding:0 20px;width: 100%;margin-bottom: 10px;">
				<el-checkbox v-model="above"  style="color: white;" v-if='jumpFlag&&(submitAuditBtn||userType==4||userType==3||userType==2)&&(periodType==2||periodType==3)'>
					跳级审核
				</el-checkbox>
			</p>
			<el-button type="primary" v-if='(userType==4||userType==3||userType==2)&&(periodType==2||periodType==3)' @click="auditingHTTenderTask('1')">通过</el-button>
			<el-button type="primary" v-if='(userType==4||userType==3||userType==2)&&(periodType==2||periodType==3)' @click="auditingHTTenderTask('0')">驳回</el-button>
			<el-button v-if="submitAuditBtn" type="primary" @submit="submitConfirm" @click="submit">提交审核</el-button>
			<el-button v-if="recallTaskBtn" type="primary" @click="recallTask">撤回</el-button>
		</div>

	</div>
</template>

<script>
	import axios from 'axios';
	import dialogSubmitAudit from '../../common/components/dialogSubmitAudit.vue'; /* 提审弹窗 */
	/* 面板--上传下载 */
	import collapseUpAndDownload from '../../common/components/collapseUpAndDownload.vue';
	// <!-- 面板-复核只读 -->
	import collapseReviewRead from '../../common/components/collapseReviewRead.vue';
	// <!-- 面板-复核可写 -->
	import collapseReviewWrite from '../../common/components/collapseReviewWrite.vue';

	export default {
		components: {
			'dialog-submit-audit': dialogSubmitAudit,
			'fileList': collapseUpAndDownload,
			'reviewFileList': collapseUpAndDownload,
			'collapse-review-read': collapseReviewRead,
			'collapse-review-write': collapseReviewWrite
		},
		data() {
			return {
        activeNames:'white1',
				object: { // 一级面板的对象
					title: '', // 一级面板的标题
					opinion: '', // 一级级面板的意见
					children: [{
						id: '', // 二级面板的被审批人标识
						name: '', // 二级面板的被审批人名字
						errorList: [ // 二级面板的被审批人的错误数组
						]
					}]
				},
				userType:'',
				reviewer: { // 审核人
					current: '',// 当前
					next: ''// 下一个
				},
				uploadBtnFlag:true,
				delegateTaskId:'',
				submitAuditBtn:this.userType==1&&(this.periodType==2||this.periodType==3),
				recallTaskBtn:this.userType==20&&(this.periodType==2||this.periodType==3),
				above: false, // 越级flag
				rowInfoLevel: '1',//左侧树选中项级别(条件判断)
				rowInfoLabel: '',//左侧树选中标签
				fileList: [],//二级节点对应的文件列表
				tenderTaskPeriodId:'',//投标任务和阶段的关联表
				periodType:'',//阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
				reviewFileList: [],//以提审文件列表
				reviewFileListShowFlag:false,
				fileTypeList: [],//文件类别列表
				delegateTaskKey:null,
				isCanRecall:null,
				verifyState:null,
				rowInfo: {},//当前投标任务信息(取自sessionStorage)
				tenderTaskId: '',//投标任务唯一标识
				departmentId: '',//部门唯一标识
				nextTaskKey:'',//流程下一节点
				taskNum:'',//流程节点序号
				jumpFlag:false,
				approveTime:null,
				opinionList: [],//查询审核意见列表
				errorTypeList: [], // 错误类型列表
				reviewFileRelationList:[],
			};
		},
	    beforeDestroy(){
	      let vm = this;
	      vm.bus.$off('click');
	    },
	    watch:{
			reviewFileList(){
				let vm = this;
				vm.reviewFileListShowFlag = true
				if (!vm.reviewFileList||vm.reviewFileList.length==0) {
					vm.reviewFileListShowFlag = false
				}
			}
	    },
	    created(){
			let vm = this;
			vm.tenderTaskId = this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId;//投标任务ID
			//查询当前人类别(用以展示按钮)
			vm.showUserType(vm.tenderTaskId);
			console.log("this.userType",this.userType)
	    },
		mounted() {
			let vm = this;
			vm.tenderTaskId = this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId;//投标任务ID
			vm.departmentId = this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.departmentId;//部门ID
			//绑定全局事件globalEvent
			vm.bus.$on('click', val => {
				vm.checkRowInfo(val);
				vm.showUserType(vm.tenderTaskId);
				vm.searchMaterialTypeList();
				//查询投标任务以提审文件
				vm.searchIsReviewTenderTaskPeriodMaterialList();
				//查询审核意见列表
				vm.searchHTTenderTaskHisTaskInst(vm.tenderTaskId);
				//查询问题类型列表信息
				vm.searchQuestionList(vm.departmentId);
				//查询投标任务信息
				vm.searchTenderTaskInfo(vm.tenderTaskId);
				//查询投标任务投标阶段文件信息
				vm.searchReviewFileRelationList(vm.tenderTaskId);
			});

			//查询投标任务以提审文件
			vm.searchIsReviewTenderTaskPeriodMaterialList();
			//查询审核意见列表
			vm.searchHTTenderTaskHisTaskInst(vm.tenderTaskId);
			//查询问题类型列表信息
			vm.searchQuestionList(vm.departmentId);
			//查询投标任务信息
			vm.searchTenderTaskInfo(vm.tenderTaskId);
			//查询投标任务投标阶段文件信息
			vm.searchReviewFileRelationList(vm.tenderTaskId);
		},
		updated(){
			//提交审核按钮状态
			this.submitAuditBtn=this.userType==1&&(this.periodType==2||this.periodType==3)
			//撤回按钮状态
			this.recallTaskBtn=this.userType==20&&(this.periodType==2||this.periodType==3)
			//跳级显示flag
			this.jumpFlag = this.delegateTaskKey == 'ManagerVerify'||(this.delegateTaskKey == 'SubmitVerify'  && this.approveTime==null)
			//文件上传按钮flag
			this.uploadBtnFlag = this.userType == 1 || (this.userType ==15 && this.periodType!=2&&this.periodType!=3)
		},
		methods: {
			//事件车
			trigger(name,data) {
				let vm = this;
				// 触发事件
				vm.bus.$emit(name, data);
			},
			checkRowInfo(val) {
				this.rowInfo = val
				//所选节点级别
				this.rowInfoLevel = val.treeNodeLevel;
				//投标任务和阶段的ID
				this.tenderTaskPeriodId = val.id;
				//阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）
				this.periodType = val.periodType;
				console.log("this.periodType",this.periodType)
				//左侧树选中标签
				this.rowInfoLabel = val.label;
				//当所选节点为投标任务阶段时(2级节点)
				if (val.treeNodeLevel == '2') {
					let vm = this;
					let formData = {
						relationId: val.id
					}
					vm.apiAddress('/tenderTask/query/searchTenderTaskPeriodMaterialList', formData).then(
						res => {
							if (res.data.status == 'failure') {
								vm.$message({
									message: res.data.message,
									type: 'error',
									center: true
								});
							} else {
								let dataList = res.data.dataList;
								//遍历调整结构(变更属性名)
								for (var i = dataList.length - 1; i >= 0; i--) {
									//更改属性名
									dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g, "materialId"));
									if (vm.userType=='1') {
										dataList[i].isCanDel = '1'
									}else if (vm.userType=='15') {
										continue;
									}else{
										dataList[i].isCanDel = '0'
									}
								}
								vm.fileList = dataList;
							}
						}).catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					});
				}

			},
			//查询相关资料
			searchAllMaterialTypeAndMaterialList(tenderTaskId) {
				let vm = this;
				let formData = {
					tenderTaskPeriodId:vm.tenderTaskPeriodId,
					tenderTaskId: tenderTaskId,
					periodType:vm.periodType
				}
				//查询以上传的所有文件(非审批文件)
				vm.apiAddress('/tenderTask/query/searchAllMaterialTypeAndMaterialList', formData).then(
					res => {
						if (res.data.status == 'failure') {
								vm.$message({
									message: res.data.message,
									type: 'error',
									center: true
								});
						} else {
							let dataList = res.data.dataList;
							let count = 0;
							//校验是否有上传文件
							if (null != dataList&&dataList.length>0) {
								for (var i = dataList.length - 1; i >= 0; i--) {
									let childDataList = dataList[i]
										count+=childDataList.children.length
								}
							}
							//无上传除投标文件其他相关文件时
							if (count==0) {
								//直接提交
								this.$refs.submit.submit()
							}else{
								this.$refs.submit.list = dataList;
								this.$refs.submit.show = true;
							}
						}

			}).catch(err => {
				vm.$message({
					message: err,
					type: 'error',
					center: true
				});

			});
		},
		//查询审核意见列表
		searchHTTenderTaskHisTaskInst(tenderTaskId){
			let vm = this;
			let formData = {
				tenderTaskId: tenderTaskId,
			}
			//查询审核意见列表
			vm.apiAddress('/tenderTask/query/searchHTTenderTaskHisTaskInst', formData).then(
				res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						/*------------------------------格式转换START---------------------------------*/
						let dataList = res.data.dataList;
						//for循环遍历更改属性名
						for (var i = 0 ; i < dataList.length ; i++) {
							dataList[i].title = vm.getStepName(dataList[i].taskKey)
							//将属性名httenderTaskHisTaskInstVOList转换成属性名children
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/httenderTaskHisTaskInstVOList/g,"children"));
							let children = dataList[i].children
							for (var j = 0 ; j < children.length ; j++) {
								//将属性名approveTime转换成属性名time
								children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveTime/g,"time"));
								//将属性名approveUserName转换成属性名name
								children[j] = JSON.parse(JSON.stringify(children[j]).replace(/approveUserName/g,"name"));
								//将属性名htUserQuestionRecordRelationVOList转换成属性名persions
								children[j] = JSON.parse(JSON.stringify(children[j]).replace(/htUserQuestionRecordRelationVOList/g,"persions"));
								//将属性名rejectReason转换成属性名opinion
								children[j] = JSON.parse(JSON.stringify(children[j]).replace(/rejectReason/g,"opinion"));
								let persions = vm.groupBy(children[j].persions,function(item){
									return[item.userId]
								})
								for (var k = 0 ; k < persions.length ; k++) {
									persions[k].name = persions[k].dataList[0].userName
									//将属性名dataList转换成属性名errorList
									persions[k] = JSON.parse(JSON.stringify(persions[k]).replace(/dataList/g,"errorList"));
									let errorList = persions[k].errorList;
									for (var l = errorList.length - 1; l >= 0; l--) {
									//将属性名questionName转换成属性名type
										errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionName/g,"type"));
									//将属性名questionNum转换成属性名num
										errorList[l] = JSON.parse(JSON.stringify(errorList[l]).replace(/questionNum/g,"num"));
									}
									persions[k].errorList = errorList;
								}
								children[j].persions = persions;
							}
						}
						/*---------------------------------格式转换END-------------------------------------*/
						vm.opinionList = dataList
					}
				}).catch(err => {
				vm.$message({
					message: err,
					type: 'error',
					center: true
				});
			});
		},
			/*查询投标任务以提审文件*/
			searchIsReviewTenderTaskPeriodMaterialList() {
				let vm = this;
				let formData = {
					tenderTaskId : vm.tenderTaskId,
					isReview : "1"
				}
				vm.apiAddress('/tenderTask/query/searchIsReviewTenderTaskPeriodMaterialList', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						let dataList = res.data.dataList;
						for (var i = dataList.length - 1; i >= 0; i--) {
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/relationId/g,"materialId"));
						}
						vm.reviewFileList = dataList
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				});
			},
			/*添加阶段文件信息*/
			submitForm(obj) {
				obj.append("tenderTaskPeriodId", this.rowInfo.id);
				obj.append("periodType", this.periodType);
				obj.append("fileTypeId", obj.get('select'));
				obj.delete('select')
				let vm = this;
				this.apiAddress('/tenderTask/insert/saveTenderTaskPeriodMaterialInfo', obj).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						vm.$message({
							message: res.data.message,
							type: 'success',
							center: true
						});
						vm.checkRowInfo(vm.rowInfo);
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				})
			},
			/*删除文件*/
			deleteFileInfo(val) {
				this.$confirm('此操作将永久删除文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center: true
				}).then(() => {
					let vm = this;
					let formData = {
						relationId: val.materialId,
						materialPath: val.materialPath
					}
					this.apiAddress('/tenderTask/delete/deleteTenderTaskPeriodMaterialInfo', formData).then(res => {
						if (res.data.status == 'failure') {
							vm.$message({
								message: res.data.message,
								type: 'error',
								center: true
							});
						} else {
							vm.$message({
								message: res.data.message,
								type: 'success',
								center: true
							});
							vm.searchIsReviewTenderTaskPeriodMaterialList();
							vm.checkRowInfo(vm.rowInfo);
						}
					}).catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					})
				}).catch(() => {});
			},
			/*查询文件类别列表*/
			searchMaterialTypeList() {
				let vm = this;
				let formData = {
					departmentId:'593df92d-fb21-442a-b279-a8b821bbc0d4'
				}
				vm.apiAddress('/tenderTask/query/searchMaterialTypeList', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						let dataList = res.data.dataList;
						for (var i = dataList.length - 1; i >= 0; i--) {
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeId/g, "id"));
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileTypeName/g, "label"));
						}
						vm.fileTypeList = dataList
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				});
			},
			/*文件下载*/
			downloadMatrial(item) {
				this.downloadFile(item.url, item.name);
			},

			checkFile(n) {
				let vm = this;
				vm.bus.$emit('checkFile', n);
			},

			submitConfirm(data) { 
				this.$confirm('此操作将提交审核, 是否继续?', '提示', {
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
		          center: true
		        }).then(() => {
		        	// 确认提审
					let vm = this;
					let nextTaskKey = this.nextTaskKey
					if(this.above){
						nextTaskKey = this.getNextStepNode(nextTaskKey)
					}
					let formData = {
						tenderTaskId: this.tenderTaskId,
						departmentId: this.departmentId,
						materialIds : data.checkbox,
						submitNode: nextTaskKey
					}
					vm.apiAddress('/tenderTask/modify/updateTenderTaskPeriodMaterialInfoIsReviewType', formData).then(res => {
						if (res.data.status == 'failure') {
							vm.$message({
								message: res.data.message,
								type: 'error',
								center: true
							});
						} else {
							vm.$message({
								message: res.data.message,
								type: 'success',
								center: true
							});
							// vm.bus.$emit('asideRefresh');
							// vm.showUserType(vm.tenderTaskId);
							// vm.$refs.submit.show = false;
							// vm.searchIsReviewTenderTaskPeriodMaterialList();
							// vm.searchTenderTaskInfo(vm.tenderTaskId);
							// vm.searchReviewFileRelationList(vm.tenderTaskId);
							//让index组件重新请求数据
							// this.trigger("reSearchTenderStateFile");
				              vm.$router.push({
				                path: '/page/work/default'
				              });
						}
					}).catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					});
		        }).catch(() => {
		        });	
				
			},
			/*展示可以提交审核的文件列表*/
			submit(){
				if (null== this.fileList || this.fileList.length == 0) {
					this.$message({
						message: '请上传投标文件',
						type: 'error',
						center: true
					});
					return
				}
				this.searchAllMaterialTypeAndMaterialList(this.tenderTaskId)
			},
			/*撤回*/
			recallTask(){
				this.$confirm('此操作将撤回投标任务, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center: true
				}).then(() => {
					let vm = this;
					let formData = {
						tenderTaskId : this.tenderTaskId,
						delegateTaskId: this.delegateTaskId,
					}
					vm.apiAddress('/tenderTask/modify/recallTask', formData).then(res => {
						if (res.data.status == 'failure') {
							vm.$message({
								message: res.data.message,
								type: 'error',
								center: true
							});
						} else {
							vm.$message({
								message: res.data.message,
								type: 'success',
								center: true
							});
							vm.searchTenderTaskInfo(vm.tenderTaskId)
							vm.showUserType(vm.tenderTaskId);
							vm.bus.$emit('asideRefresh');
						}
					}).catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					});
				}).catch(() => {});
			},
			/*查询投标任务信息*/
			searchTenderTaskInfo(tenderTaskId) {
				let vm = this;
				let formData = {
					tenderTaskId: tenderTaskId
				}
				vm.apiAddress('/tenderTask/query/searchTenderTaskInfoById', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						let tenderTaskInfo = res.data.dataVO;
						let userList = tenderTaskInfo.userList;
						for (var i = userList.length - 1; i >= 0; i--) {
							userList[i].errorList = [];
						}
						vm.object.title = vm.getStepName(tenderTaskInfo.delegateTaskKey);
						vm.nextTaskKey = tenderTaskInfo.submitNode;
						vm.delegateTaskKey = tenderTaskInfo.delegateTaskKey;
						vm.verifyState = tenderTaskInfo.verifyState;
						vm.delegateTaskId = tenderTaskInfo.delegateTaskId;
						vm.isCanRecall = tenderTaskInfo.isCanRecall;
						vm.approveTime = tenderTaskInfo.approveTime;
						vm.tasknum = vm.getStepNum(tenderTaskInfo.delegateTaskKey);
						vm.reviewer.current = vm.getStepWorker(vm.nextTaskKey);
						vm.reviewer.next = vm.getStepWorker(vm.getNextStepNode(vm.nextTaskKey));
						for (var i = userList.length - 1; i >= 0; i--) {
							userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userId/g,"id"));
							userList[i] = JSON.parse(JSON.stringify(userList[i]).replace(/userName/g,"name"));
						}
						vm.object.children = userList
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				})
				this.centerDialogVisible = true
			},
			changeObject(obj){
				this.object = obj
			},
			//查询问题类型列表信息
			searchQuestionList(departmentId){
				let vm = this;
				let formData = {
					departmentId: departmentId
				}
				vm.apiAddress('/tenderTask/query/searchQuestionList', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						let questionList = res.data.dataList;
							for (var i = questionList.length - 1; i >= 0; i--) {
								questionList[i].questionNum = 0;
							}
							vm.errorTypeList = questionList;
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				})
			},
			//审核
			auditingHTTenderTask(isPass){
		        let message = " 是否驳回?"
		        if (isPass=='1') {
		          message = " 是否通过?"
		        }
				this.$confirm(message, '提示', {
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
		          center: true
		        }).then(() => {
		        	let paramObj = this.object
					paramObj.isPass = isPass;
					let nextTaskKey = this.nextTaskKey
					if (this.above) {
						nextTaskKey = this.getNextStepNode(nextTaskKey)
					}
			        paramObj.businessId = this.tenderTaskId;
					paramObj.onlyOfficeEditKey = this.$parent.onlyOfficeEditKey
					paramObj.delegateTaskId = this.delegateTaskId;
					paramObj.reviewFileName = this.$parent.reviewFileName
					paramObj.reviewFilePath = this.$parent.reviewFilePath
					paramObj.submitNode = nextTaskKey;
					paramObj.userQuestionRecordRelationList = new Array();
					paramObj = JSON.parse(JSON.stringify(paramObj).replace(/title/g,"delegateTaskKey"));

					if (isPass=='0') {
						paramObj = JSON.parse(JSON.stringify(paramObj).replace(/opinion/g,"rejectReason"));
						for (var i = paramObj.children.length - 1; i >= 0; i--) {
							if (paramObj.children[i].errorList) {

								console.log("paramObj.children[i].errorList",paramObj.children[i].errorList)

								for (var j = paramObj.children[i].errorList.length - 1; j >= 0; j--) {
									paramObj.children[i].errorList[j].userId = paramObj.children[i].id
									paramObj.children[i].errorList[j].userName = paramObj.children[i].name
									if (paramObj.children[i].errorList[j].questionNum!='0') {
										paramObj.userQuestionRecordRelationList.push(paramObj.children[i].errorList[j])
									}
								}
							}
						}
						delete paramObj.children;
					}
					paramObj.tenderTaskId = this.tenderTaskId;
					let vm = this;
					this.apiAddress('/tenderTask/insert/auditingHTTenderTask', paramObj).then(res => {
						if (res.data.status == 'failure') {
							vm.$message({
								message: res.data.message,
								type: 'error',
								center: true
							});
						} else {
							vm.$message({
								message: res.data.message,
								type: 'success',
								center: true
							});
							// vm.searchIsReviewTenderTaskPeriodMaterialList()
							// vm.searchTenderTaskInfo(vm.tenderTaskId)
							// this.$router.go(0)
			                vm.$router.push({
			                    path: '/page/work/default'
			                });
						}
					}).catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					})
		        }).catch(() => {

		        });
				
			},
			cancel(){// 跳级取消
					let vm = this;
					vm.above = false;
			},
			/*查询当前人类别(用以展示按钮)*/
			showUserType (tenderTaskId){
				let vm = this;
				let formData = {
					tenderTaskId: tenderTaskId
				}
				vm.apiAddress('/tenderTask/query/showBtnType', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						vm.userType = res.data.typeFlag;
						console.log("vm.userType",vm.userType)
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				})
			},

			/*查询审核意见文件列表*/
			searchReviewFileRelationList(tenderTaskId) {
				let vm = this;
				let formData = {
					businessId:tenderTaskId
				}
				vm.apiAddress('/commonModule/query/searchReviewFileRelationList', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						let dataList = res.data.dataList;
						for (var i = dataList.length - 1; i >= 0; i--) {
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/fileId/g, "materialId"));
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFileName/g, "materialName"));
							dataList[i] = JSON.parse(JSON.stringify(dataList[i]).replace(/reviewFilePath/g, "materialPath"));
						}
						console.log("dataList----------------------------",dataList)
						vm.reviewFileRelationList = dataList
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				});
			},
		},
	};

</script>
<style lang="scss">
	@import url('../../common/css/titleDownLoadTool.css');
	/* 标题下载工具*/
	@import url('../../common/css/foldedPanel.css');
	/* 折叠面板*/
	@import url('../../common/css/fileList.css');
	/* 文件列表*/
</style>
<style lang="scss" scoped>
	.right--work-contract-tenderTask {
		height: 100%;
    display:flex;
    flex-direction:column;

    .given-container{
      flex-grow: 100;
      overflow: auto;
    }

		.btn-group {
		  //按钮组
		  width: 80%;
		  bottom: 20px;
		  margin-left: 10%;
		  display: flex;
		  flex-direction: column;
		  flex-grow: 1;

		  .el-button {
		    width: 100%;

		    &+.el-button {
		      margin-left: 0;
		      margin-top: 10px;
		    }
		  }
		}
	}
</style>
