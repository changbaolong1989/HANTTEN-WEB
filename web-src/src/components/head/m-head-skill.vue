<template>
	<!-- 技术标准 -->
	<div class="skill">
		<!-- 表格 -->
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab">
				<template slot="empty" class="tabdata-empty">
					<img src="../../assets/image/no-item.png" alt="">
					<p>暂无数据</p>
				</template>
				<el-table-column align="center" class-name="table-column" label="技术标准名称" :show-overflow-tooltip="true" :resizable="false">
					<template slot-scope="scope">
						<p class="text-overflow-ellipsis">{{ scope.row.standardName }}</p>
						<p style="display: none;">{{ scope.row.standardId }}</p>
						<p style="display: none;">{{ scope.row.materialPath }}</p>
						<p style="display: none;">{{ scope.row.materialName }}</p>
					</template>
				</el-table-column>
				<el-table-column width="200px" align="center" class-name="table-column" prop="releaseDate" label="发布时间" :show-overflow-tooltip="true" :resizable="false"></el-table-column>
				<el-table-column width="200px" align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true" :resizable="false">
					<template slot-scope="scope">
						<el-button type="text" class="download" @click="download(scope.$index, scope.row)">下载</el-button>
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
				biaodata: [], //表格数据
				tabdata: [], //当前页数据
				ruleForm: {
					standardName: '',
					releaseDate: '',
					userName: ''
				},
				//请求头
				config: {
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				}
			}
		},
		created() {
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
				vm.$axios.post('/technicalStandardCenter/query/searchTechnicalStandardCenterList', JSON.stringify({
						// 页码
						"pageNumber": 1,
						// 每页总条数
						"pageSize": vm.pageSize
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
				vm.$axios.post('/technicalStandardCenter/query/searchTechnicalStandardCenterList', JSON.stringify({
						// 页码
						"pageNumber": vm.currentPage,
						// 每页总条数
						"pageSize": vm.pageSize
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
.skill{
	.el-table__body{
		tr{
			&>td:last-child{
				cursor: pointer;
			}
		}
	}
}
</style>
