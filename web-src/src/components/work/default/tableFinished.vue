<template>
	<!-- 已办表格 -->
	<el-container class="tableFinished--work-default">
		<form-search @click="search" :btns="btns" :searchFlagObj="{projectNameFlag:dataVOParamKeyMap.projectName,showExport:true}"></form-search>
		<div class="item-tab-bottom">
			<el-table :data="biaodata" border style="width: 100%" class="item-tab" :cell-style="rowClass" v-if="tableShow">
				<template slot="empty" class="tabdata-empty">
					<img src="../../../assets/image/no-item.png" alt="" />
					<p>暂无数据</p>
				</template>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="sortNum" label="序号" v-if="dataVOParamKeyMap.sortNum"></el-table-column>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="projectNum" label="项目编号" v-if="dataVOParamKeyMap.projectNum"></el-table-column>
		        <el-table-column :show-overflow-tooltip="true" align="center" min-width="200" class-name="table-column" prop="projectName" label="项目名称" v-if="dataVOParamKeyMap.projectName">
					<template slot-scope="scope">
		            	{{scope.row.projectName == ''?'-':scope.row.projectName}}
		          	</template>
		        </el-table-column>
		        <el-table-column :show-overflow-tooltip="true" min-width="100" align="center" class-name="table-column" prop="contractNum" label="合同编号" v-if="dataVOParamKeyMap.contractNum">
		          <template slot-scope="scope">
		            <el-button @click="toContract(scope.row)" type="text" size="small">{{scope.row.contractNum == ''?'-':scope.row.contractNum}}</el-button>
		          </template>
		        </el-table-column>
		        <el-table-column :show-overflow-tooltip="true" align="center" min-width="200" class-name="table-column" prop="contractName" label="合同名称" v-if="dataVOParamKeyMap.contractName">
					<template slot-scope="scope">
		            	{{scope.row.contractName == ''?'-':scope.row.contractName}}
		          	</template>
	          	</el-table-column>
					<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="tenderTaskName" label="任务名称" v-if="dataVOParamKeyMap.tenderTaskName">
						<template slot-scope="scope">
							{{scope.row.tenderTaskName == ''?'-':scope.row.tenderTaskName}}
						</template>
					</el-table-column>
		        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="counselTypePeriod" label="业务类型" v-if="dataVOParamKeyMap.counselTypePeriod">
		          <template slot-scope="scope">
		            {{  scope.row.counselTypePeriod == null|| scope.row.counselTypePeriod ==""? '-' : selectGetName(scope.row.counselTypePeriod,radios) }}
		          </template>
		        </el-table-column>
		        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="cooperateCompany" label="合作公司" v-if="dataVOParamKeyMap.cooperateCompany">
		          <template slot-scope="scope">
		            {{ scope.row.cooperateCompany == null|| scope.row.cooperateCompany =="" ?"-":scope.row.cooperateCompany }}
		          </template>
		        </el-table-column>
		        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="compilationUserName" label="办理人" v-if="dataVOParamKeyMap.compilationUserName">
		          <template slot-scope="scope">
		            <span>{{ !!scope.row.compilationUserName?scope.row.compilationUserName:"历史数据，暂无办理人 " }}</span>
		          </template>
		        </el-table-column>
		        <el-table-column :show-overflow-tooltip="true"  align="center" class-name="table-column" prop="handleDate" label="办理时间" v-if="dataVOParamKeyMap.handleDate"></el-table-column>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="startDate" label="开标时间" v-if="dataVOParamKeyMap.startDate"></el-table-column>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="userName" label="投标任务创建人" v-if="dataVOParamKeyMap.userName"></el-table-column>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="departmentName" label="所属部门" v-if="dataVOParamKeyMap.departmentName"></el-table-column>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="createUserName" label="创建人" v-if="dataVOParamKeyMap.createUserName"></el-table-column>
				<el-table-column :show-overflow-tooltip="true" align="center" class-name="table-column" prop="createDate" label="创建时间" v-if="dataVOParamKeyMap.createDate"></el-table-column>
			</el-table>
		</div>
		<div style="margin-top: 30px;">
			<!-- 分页 -->
			<div class="block item-fen" v-show="total > 0">
				<el-pagination background class="fen-left" @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="currentPage" :page-sizes="pageSizes" :page-size="pageSize" layout="total, prev, pager,next,sizes,jumper"
					:total="total"></el-pagination>
				<el-button class="linear" type="primary" size="small">确定</el-button>
			</div>
		</div>
	</el-container>
</template>

<script>
	/* 搜索表单 */
	import formSearch from './formSearch';
	export default {
		components: {
			'form-search': formSearch
		},
		data() {
      const types=[{
          display: '造价咨询-编制',
          val: '0-0'
        }, {
          display: '造价咨询-审核',
          val: '0-1'
        }, {
          display: '造价咨询-全过程跟踪审计',
          val: '0-2'
        }, {
          display: '造价咨询-全过程工程造价咨询',
          val: '0-3'
        },{
          display: '招标代理-施工',
          val: '1-0'
        },{
          display: '招标代理-监理',
          val: '1-1'
        }, {
          display: '招标代理-设计',
          val: '1-2'
        },{
          display: '招标代理-勘察',
          val: '1-3'
        },{
          display: '招标代理-服务类',
          val: '1-4'
        },{
          display: '招标代理-采购',
          val: '1-5'
        },{
          display: '招标代理-其他',
          val: '1-6'
        }]
			return {
				tableShow:true,// 显示表格数据
				total: 0, //分页总数据
				currentPage: 1, //当前页码
				pageSize: 10, //每页显示多少条数据
				pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
        userPowerInfo:this.SStorage.getItem("userPowerInfo"),
				biaodata: [], //表格数据
				screening: !!this.$route.query.screening,
				btns: [], // 筛选按钮数组
				searchType:'',
      			projectName: '', // 项目名称
				dataVOParamKeyMap: {} ,// 表格显示列
        radios: types,
			}
		},
		created() {
			let vm = this;
			vm.btns = [];
			if (!!vm.userPowerInfo.projectList) {
				let btn = {
					id: 'contract',
					name: "合同",
					uniqueId:"合同"
					}
				vm.btns.push(btn);
			}
			if (!!vm.userPowerInfo.tenderTaskList) {
				let btn = {
					id: 'tenderTask',
					name: "投标任务",
					uniqueId:"投标任务"
				}
				vm.btns.push(btn);
			}
		},
    updated(){
      let vm = this;
    },
		methods: {
			search(data) { // 表单查询
				let vm = this;
				//设置当前活动部门
				vm.searchType = data.id;
				vm.tenderTaskName = data.tenderTaskName;
				vm.projectNum = data.projectNum; // 项目编号
				vm.projectName = data.projectName; // 项目名称
				vm.contractNum = data.contractNum; // 合同编号

                // 重置页码
				this.currentPage = 1;

                // 查询列表
				vm.currentChangePage(this.currentPage, vm.pageSize)
			},
			toContract(row) { // 跳转到详情页面
        		// 如果是市场部，则跳转到投标任务详情页
		        if(this.searchType == "tenderTask"){
		           this.SStorage.setItem('/page/work/contract/tenderTask', {
		            rowInfo: row,
		            stateFlag: "tenderTask",
		            titleName: row.tenderTaskName
		          })
		          this.$router.push('/page/work/contract/tenderTask');
		          // 否则，跳转到项目详情页面
		        } else {
		          this.SStorage.setItem('/page/work/contract/FoundItem', row)
		          this.$router.push('/page/work/contract/FoundItem');
		        }
			},
			handleSizeChange(val) { // 每页条数切换
				this.pageSize = val;
				this.currentChangePage(this.currentPage, this.pageSize);
			},
			handleCurrentChange(val) { //页码切换
				this.currentPage = val;
				this.currentChangePage( this.currentPage, this.pageSize);
			},
			currentChangePage(currentPage, pageSize) { //分页方法
				let vm = this;
				let formData = {
					searchType:vm.searchType,
					tenderTaskName: vm.tenderTaskName,
					pageNumber: currentPage,
					pageSize: pageSize,
					projectNum:vm.projectNum,  // 项目编号
					projectName:vm.projectName,  // 项目名称
					contractNum:vm.contractNum // 合同编号
				};
				vm.tableShow = false;
				vm.apiAddress('/work/query/searchCompletedList', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						vm.dataVOParamKeyMap = res.data.dataVOParamKeyMap; // 表格显示列
						vm.biaodata = res.data.dataList;
						vm.total = res.data.dataCount;
						vm.tableShow = true;
	            		if (res.data.dataCount) {
	            			 vm.$emit('changeLabel',{
			                  name:'finishedLabel',
			                  count:vm.total
			                });
	            		}else{
	            			vm.$emit('changeLabel',{
			                  name:'finishedLabel',
			                  count:0
			                });
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
			rowClass({
				row,
				column,
				rowIndex,
				columnIndex
			}) { //状态检测
				if (columnIndex === 10) {
					return 'color:#06A1EE';
				}
			},
      selectGetName(key,list){
        for (var i = 0; i < list.length; i++) {
          if (list[i].val==key) {
            return list[i].display
          }
        }
      }
		}
	};
</script>
<style lang="scss">
	.tableFinished--work-default {

		/* 总容器 */
		&.el-container {
			padding-bottom: 25px;
			display: flex;
			flex-direction: column;
		}
	}
</style>
<style lang="scss" scoped>
	.tableFinished--work-default {
		margin-left: 0px;

		.item-tab-bottom {
			box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
			position: relative;
		}

		/* 筛选内容 */
		.screening {
			box-shadow: 0 0 10px rgba(176, 192, 202, 0.5) inset;
			background-color: white;
			border-radius: 10px 10px 0 0;
			padding: 15px 40px;
			height: 62px;

			button {
				background-color: white;
				border: 1px solid #dcdfe6;
				border-radius: 5px;
				color: #989898;
				padding: 8px;

				&+button {
					margin-left: 20px;
				}

				&.active {
					border: 1px solid #0099e8;
					color: #0099e8;
				}
			}
		}

		.el-table {
			margin: 0;
		}
	}
</style>
