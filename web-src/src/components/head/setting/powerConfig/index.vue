<template>
	<!-- 公司设置 > 权限管理 -->
	<div class="mh-set-role">
		<!-- 表单 -->
		<div class="item-from">
			<div class="item-input">
				<label for="i1">职位名称</label>
				<el-input v-model="searchJobName" placeholder="请输入职位名称" id="jobName"></el-input>
			</div>
      <div class="item-input">
        <label for="i1">部门</label>
        <el-select v-model="searchDepartmentId" placeholder="请选择部门" clearable>
          <el-option v-for="(item,index) in departmentList" :key="index" :label="item.departmentName" :value="item.departmentId">{{ item.departmentName }}</el-option>
        </el-select>
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
					<img src="../../../../assets/image/no-item.png" alt="" />
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" prop="departmentName" label="部门" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="jobName" label="职位名称" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" prop="powerNames" label="职位权限" :show-overflow-tooltip="true"></el-table-column>
				<el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
					<template slot-scope="scope">
						<el-button type="text" class="modify" @click="updatePowerRelation(scope.row.jobId)">修改</el-button>
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
    	<power-config-form  v-if="centerDialogVisible" ref="childForm"></power-config-form>
	</div>
</template>

<script>
  	import PowerConfigForm from './powerConfigForm' /* 付款台账表单  */
	export default {
		data() {
			return {
				total: 0, //分页总数据
				currentPage: 1, //当前页码
				pageSize: 10, //每页显示多少条数据
				pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
				tabdata: [], //当前页数据
				departmentList:[],
				updateRoleId: '',
				jobName: '',
        		departmentId:'',
				searchJobName: '',
				searchDepartmentId: '',
				centerDialogVisible:false,
			};
		},
	    components: {
	        PowerConfigForm,//付款台账表单
	    },
		mounted() {
			this.currentPage = 1;
			this.jobName = null;
			this.searchList(this.currentPage, this.pageSize, this.jobName,this.departmentId);
			this.searchAllDepartments();
		},
		methods: {
			updatePowerRelation(jobId){
		        const vm = this
		        vm.centerDialogVisible = true;
		        vm.$nextTick(() => {
		          vm.$refs.childForm.updatePowerRelationFunc(jobId);
		        })
			},

			reflashData(){
                this.centerDialogVisible = false; /* 让弹窗隐藏 */
				this.$nextTick(() => {
					this.searchList(this.currentPage, this.pageSize, this.jobName,this.departmentId)
				})
			},
			handleClose(done) {
				this.$refs.ruleForm.resetFields();
				this.$refs.menuTree.setCheckedKeys([]);
				done()
			},
			refer() {
				this.jobName = this.searchJobName;
				this.departmentId = this.searchDepartmentId;
				this.currentPage = 1;
				this.pageSize = 10;
				this.searchList(this.currentPage, this.pageSize, this.jobName,this.departmentId)
			},
			searchAllDepartments() {
				const vm = this;
				let formData = {};
		        // 获取列表数据
		        vm.apiAddress('/config/powerConfig/query/searchAllDepartments', formData).then(res => {
		          if (res.data.status == 'failure') {
		            vm.$message({
		              message: res.data.message,
		              type: 'error',
		              center: true
		            });
		          } else {
		            // 设置表格数据
		            let dataList = res.data.dataList;
		            // 循环数据放入表格数据对象中
		            vm.departmentList = dataList;
		            console.log("vm.departmentList",vm.departmentList)
		          }
		        }).catch(err => {
		          vm.$message({
		            message: err,
		            type: 'error',
		            center: true
		          });
		        })
			},
			searchList(pageNumber, pageSize, jobName,departmentId) {
				const vm = this;
				let formData = {
					jobName: jobName,
          			departmentId:departmentId,
					pageNumber: pageNumber,
					pageSize: pageSize,
				};
		        // 获取列表数据
		        vm.apiAddress('/config/powerConfig/query/searchJobList', formData).then(res => {
		          if (res.data.status == 'failure') {
		            vm.$message({
		              message: res.data.message,
		              type: 'error',
		              center: true
		            });
		          } else {
		            // 设置表格数据
		            let dataList = res.data.dataList;
		            // 循环数据放入表格数据对象中
		            vm.tabdata = dataList;
		            // 设置数据总条数
		            vm.total = res.data.dataCount;
		          }
		        }).catch(err => {
		          vm.$message({
		            message: err,
		            type: 'error',
		            center: true
		          });
		        })
			},
			handleSizeChange(val) {
				// 每页条数切换
				this.pageSize = val;
				this.searchList(this.currentPage, this.pageSize, this.jobName,this.departmentId)
			},
			handleCurrentChange(val) {
				//页码切换
				this.currentPage = val;
				this.searchList(this.currentPage, this.pageSize, this.jobName,this.departmentId)
			},
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
