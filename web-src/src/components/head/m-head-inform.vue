<template>
	<!-- 通知中心 -->
	<div class="inform">
		<!-- 表格 -->
		<div class="item-tab-bottom">
			<div class="item-tab-bor linear"></div>
			<el-table :data="tabdata" border style="width: 100%" class="item-tab" :cell-style="rowClass" @selection-change="selectionChange">
				<template slot="empty" class="tabdata-empty">
					<img src="../../assets/image/no-item.png" alt="">
					<p>暂无数据</p>
				</template>
         <el-table-column
              type="selection"
              width="55">
            </el-table-column>
				<el-table-column prop="state" width="80px" :show-overflow-tooltip='true' :resizable="false">
					<template slot-scope="scope">
						<div @click="isReadFunc(scope.row.messageId,scope.row.acceptUserId)">
							<span class="iconfont">&#xe617;</span>
							{{scope.row.isRead==='0'?'未读':'已读'}}
						</div>
					</template>
				</el-table-column>
				<el-table-column align="center" class-name="table-column"  prop="createDate" label="时间" width="300" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
				<el-table-column align="center" class-name="table-column " prop="messageContent" label="消息内容" :show-overflow-tooltip='true' :resizable="false"></el-table-column>
				<!--<el-table-column align="center" class-name="table-column" width="200px" label="操作" >-->
					<!--<div slot-scope="scope"  @click="open2(scope.row.messageId,scope.row.acceptUserId)" class="delete">删除</div>-->
				<!--</el-table-column>-->
			</el-table>
		</div>
		<!-- 分页 -->
    <el-button v-if="true" class="linear" type="primary" @click="batchDelete()">
      批量删除
    </el-button>
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
				iconform: false,
				iconform1: true,
				config:{
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				},
        checkList:[]//多选框选择的值（为批量删除）
			}
		},
		mounted() {
			this.searchList(1,10)
			// 本地访问
		},
		methods: {
			searchList(pageNumber,pageSize){
				const vm = this;
				let formData = {
					pageNumber:pageNumber,
					pageSize:pageSize
				};
				vm.$axios.post('/message/query/searchMessageList',formData, vm.config).then(function(response) {
					if (response.data.status=='failure') {
						vm.$message({
							message: response.data.message,
							type: 'error',
							center: true
						});
					}else{
						vm.tabdata = response.data.pagedData.dataList
						vm.total = response.data.pagedData.dataCount
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
			handleSizeChange(val) { // 每页条数切换
				this.pageSize = val;
				this.searchList( this.currentPage, this.pageSize);
			},
			handleCurrentChange(val) { //页码切换
				this.currentPage = val;
				this.searchList( this.currentPage, this.pageSize)
			},
			rowClass({
				row,
				column,
				rowIndex,
				columnIndex
			}) { //状态检测
				if (columnIndex === 7) {
					return 'color:#FC4053'
				}
				if (row.isRead == "0") {
					if (columnIndex === 0) {
						return 'color:#06A1EE'
					} else {
						return 'color:#424242'
					}
				} else if (row.isRead == "1") {
					return 'color:#999999'
				}
			},
			open2(messageId,acceptUserId) {
				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center:true
				}).then(() => {

					const vm = this;
					let formData = {
						messageId:JSON.stringify(messageId)//拼接选择删除的消息ID
					};
					vm.$axios.post('/message/delete/deleteMessageInfo',formData, vm.config).then(function(response) {
						if (response.data.status=='failure') {
							vm.$message({
								message: response.data.message,
								type: 'error',
								center: true
							});
						}else{
							vm.$message({
								type: 'success',
								message: '删除成功!'
							});

              // 查询消息集合中的最大创建时间的数据信息
              vm.bus.$emit('searchMessageDetailInfoFunc');

							vm.searchList(vm.currentPage,vm.pageSize);
						}
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
			isReadFunc(messageId,acceptUserId) {

				const vm = this;
				let formData = {
					messageId:messageId
				};
				vm.$axios.post('/message/modify/updateMessageIsReadType',formData, vm.config).then(function(response) {
					if (response.data.status=='failure') {
						vm.$message({
							message: response.data.message,
							type: 'error',
							center: true
						});
					}else{
						vm.$message({
							type: 'success',
							message: '操作成功!'
						});
            // 查询消息集合中的最大创建时间的数据信息
            vm.bus.$emit('searchMessageDetailInfoFunc');
						vm.searchList(vm.currentPage,vm.pageSize)
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
      // 多选框选择的值放入list中
      selectionChange(val){
			  // 每次选择的多选框时，清空checkList
        this.checkList = [];
        // 循环拼接信息ID
        for (let i = 0; i < val.length; i++) {
          this.checkList.push("'"+val[i].messageId+"'");
        }
      },
      // 批量删除
      batchDelete(){
        let paramVOList=this.checkList;
        const vm = this;
        if (null==paramVOList||paramVOList.length==0) {
          vm.$message({
            message: "请至少选取一条数据",
            type: 'error',
            center: true
          });
          return
        }
        vm.$confirm('是否提交?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true
        }).then(() => {
          let formData = {
            messageId:paramVOList.join(",")//拼接选择删除的消息ID
          };

          vm.$axios.post('/message/delete/deleteMessageInfo',formData, vm.config).then(function(response) {

            if (response.data.status=='failure') {
              vm.$message({
                message: response.data.message,
                type: 'error',
                center: true
              });
            }else{
              vm.$message({
                type: 'success',
                message: '删除成功!'
              });

              // 清空选择的批量删除的消息ID的数组
              vm.checkList = [];
              // 查询消息集合中的最大创建时间的数据信息
              vm.bus.$emit('searchMessageDetailInfoFunc');
              // 重新刷新页面
              vm.currentPage = 1;
              vm.searchList(vm.currentPage,vm.pageSize);
            }
            })
            .catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            });
        });
      }

		}
	}
</script>

<style lang="scss">
	.inform .el-table--enable-row-hover .el-table__body tr:hover>td {
		background-color: #e4f4fc !important;
	}

	.inform .el-table .cell {
		text-align: center;
		.delete{
			cursor: pointer;
		}
		.iconfont{
			font-size: 12px;
		}
	}
	.icon-form {
		width: 11px;
		height: 11px;
		background-position: -475px -105px;
	}

	.icon-form1 {
		width: 11px;
		height: 11px;
		background-position: -516px -105px;
	}
</style>
