<template>
	<div class="aside--work-contract">
		<h2>
			<i class="icon icon-round"></i>
			项目阶段
		</h2>
		<el-button v-if="!hide" :disabled="verifyStateFlag" class="linear win" style="width: 100%;margin-top: 10px;padding: 0;color: white;" @click="centerDialogVisible = true" ref="state" >
			{{state==='1'?'未中标':state==='2'?'已中标':'中标状态'}}
		</el-button>

		<el-tree
    ref="tree"
    :data="treeData"
    :show-checkbox="false"
    :class="{'full-height':hide}"
    accordion
    :render-content="renderContent"
     @node-click="clickNode"
      :indent="5"
      :default-expanded-keys="expandedKeys"
      node-key="id"
     ></el-tree>

		<!-- 中标状态弹窗 -->
		<el-dialog title="中标状态" :visible.sync="centerDialogVisible" width="710px" transition="fade-in-linear" center :modal-append-to-body="false">
			<!-- 表单内容 -->
			<el-form :model="form" :rules="rules" ref="form" label-width="100px">
				<el-form-item label="中标状态" prop="major">
					<el-radio-group v-model="form.major">
						<el-radio :label="'2'">已中标</el-radio>
						<el-radio :label="'1'">未中标</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitForm('form')">确 定</el-button></span>
		</el-dialog>
	</div>
</template>

<script>
import {apiAddress,stateDialog} from '@/request/api';
export default {
	data() {
		return {
			hide:false,
			verifyStateFlag:true,
			treeData: [],// 属性菜单数据
      projectPhaseCalculate:[],// 树形菜单数据(计算后)
      expandedKeys:[],// 展开的菜单标识数组
      checkedKeys:[this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.businessId],// 选中的菜单标识数组
			isMainHeadFlag:'',
			tenderTaskId: this.SStorage.getItem('/page/work/contract/tenderTask').rowInfo.tenderTaskId,
			storageData : this.SStorage.getItem('/page/work/contract/tenderTask'),
      		userPowerInfo:this.SStorage.getItem("userPowerInfo"),
			state: '',
			verifyState:null,
			userType:'',
			centerDialogVisible: false, //弹窗显示
			form: {
				major: ''
			},
			rules: {
				major: [{ required: true, message: '请确认是否中标', trigger: 'change' }]
			},
			// 请求头
			config: {
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				}
			}
		};
	},
  watch:{
      treeData(){// 计算菜单数据,添加parent
        let vm = this;
        // 赋值计算后的树形菜单数据
        if(!vm.treeData.length)return vm.projectPhaseCalculate = [];
        // 拷贝的菜单数据(返回的对象)
        let returnArr = vm.tool.deepCopy(vm.treeData,vm.tool.deepCopy);
        let expandedKeys = [];// 默认展开的标识数组
        let checkedKey = '';// 默认选中的标识
        let checkedObj = null// 被选中对象
        // 是否已经完成点击节点的录入 1:下一个节点是默认点击的叶子节点,2:默认点击的叶子结点已经录入完成
        let isComplete;
         /**
          * @param {Object} arr 需要遍历的节点数组
          * @param {Object} parent 父节点
          */
        let addParent = function(arr,parent){// 添加节点关系
          for (let i = 0,len = arr.length; i < len; i++) {
            if(vm.checkedKeys&&vm.checkedKeys.length){
              // 如果指定了选中的值,找到值所对应的对象
              if(vm.checkedKeys[0]==arr[i].id){
                checkedObj = arr[i];
              }
            }else{
              // 没有指定的值,默认选中第一个
              if((0==i&&2!=isComplete)||1==isComplete){
                 // 如果默认选中的标识有值,现将选中的值添加到展开的标识的数组中
                 if(checkedKey)expandedKeys.push(checkedKey);
                 // checkedObj = arr[i];
                 checkedKey = arr[i].id;
                 if(1==isComplete)isComplete = 2;
               }
            }
            if(parent)arr[i].parent = parent;
            if(arr[i].children&&arr[i].children.length){
              //第一个叶子节点(被选中的节点,之后的节点就不添加到默认展开节点的数组中了)
              if(!arr[i].children[0].children&&!isComplete)isComplete = 1;
                addParent(arr[i].children,arr[i],isComplete)
            };
          }
        }
        addParent(returnArr,null);
        // 如果有指定选中的值
        if(checkedObj){
          let parentIds = [];
          let findParent = function(obj){
            if(obj.parent&&obj.parent.id){
              parentIds.push(obj.parent.id);
              findParent(obj.parent);
            }
          }
          findParent(checkedObj);
          vm.expandedKeys = parentIds;
          vm.checkedKeys = [checkedObj.id];
        }else{
          vm.expandedKeys = expandedKeys;
          vm.checkedKeys = [checkedKey];
        }
        // 触发选中树形菜单点击事件
        vm.projectPhaseCalculate = vm.tool.deepCopy(vm.treeData,vm.tool.deepCopy);
        // vm.expandedKeys = expandedKeys;
        // vm.checkedKeys = [checkedKey];

        vm.$nextTick(function () {
          vm.$refs['tree'].setCurrentKey(vm.checkedKeys[0]);
           let interValIndex = setInterval(function() {
             let nodes = document.getElementsByClassName('is-current');
             let node = nodes[0];
             if(node){
               node.click();
              clearInterval(interValIndex);
              // 设置节点的
              let disableNodes = document.getElementsByClassName('node-dis');
              for (let i = 0, len = disableNodes.length; i < len; i++) {
               disableNodes[i].parentNode.classList.add('node-disabled');
              }
             }
           }, 120);
        });

      },
      immediate: true

  },
	mounted() {
		let vm = this;
		vm.getContractTreeData();
			vm.showUserType (vm.tenderTaskId)
		// 监听刷新树形菜单事件
		vm.bus.$on('asideRefresh', val => {
			vm.getContractTreeData();
			vm.showUserType (vm.tenderTaskId)
		});
	},
	updated(){
		let vm = this;
		console.log('vm.verifyState',vm.verifyState)
		console.log('vm.state',vm.state)
		console.log('vm.userType',vm.userType)
		console.log('vm.isMainHeadFlag=="1"',vm.isMainHeadFlag=='1')
        if (vm.verifyState=='1'&&vm.state != '1'&&vm.state != '2'&&vm.isMainHeadFlag=='1') {
			vm.verifyStateFlag =false;
        }else{
        	vm.verifyStateFlag = true;
        }
	},
	methods: {
		clickNode(data){
      let vm = this;
      let session = this.SStorage.getItem('/page/work/contract/tenderTask');
      let rowInfo = {};
      rowInfo.businessId = data.id;
      rowInfo.tenderTaskId = session.rowInfo.tenderTaskId;
      // 项目表标识
      rowInfo.projectSts = session.rowInfo.projectSts||'';
      vm.SStorage.setItem('/page/work/contract/tenderTask',{rowInfo:rowInfo});
			this.trigger("click",data);
		},
		trigger(name,data) {
			let vm = this;
			// 触发事件
			vm.bus.$emit(name, data);
		},
		// 查询树形数据
		getContractTreeData() {
			const vm = this;
			let formData = {
				tenderTaskId:this.storageData.rowInfo.tenderTaskId
			};
			let url = "/tenderTask/query/searchTaskPeriodByTenderTaskId"
			apiAddress(url,formData).then(res=>{
				if (res.data.status == 'failure') {
				  vm.$message({
					message: res.data.message,
					type: 'error',
					center: true
				  });
				} else {
				  	const table = res.data.dataList;
				  	vm.treeData = table;
				  	vm.verifyState = table[0].verifyState;
				  	vm.isMainHeadFlag = table[0].isMainHeadFlag;
					vm.trigger("click",vm.treeData[0]);
          if(table[0].tenderTaskSts){
            vm.form.major = table[0].tenderTaskSts==0?'':table[0].tenderTaskSts;
          }
					vm.state = table[0].tenderTaskSts;
					vm.bus.$emit('changeState',table[0].state);
					vm.bus.$emit('changeBtnFlag',table[0].isCanRecall);
				}
			}).catch(err=>{
				vm.$message({
				  message: err,
				  type: 'error',
				  center: true
				});
			})
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
	            vm.correctTenderStageMaterialFlag=(vm.userType==2||vm.userType==3||vm.userType==4)
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
		submitForm(formName) {
			/* 弹窗表单提交事件 */
			this.$refs[formName].validate(valid => {
				if (valid) {
					let obj = {
						tenderTaskId:this.tenderTaskId,
						tenderTaskSts:this.form.major
					}
					let vm = this;
					this.apiAddress('/tenderTask/modify/updateTenderTaskSts', obj).then(res => {
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
							vm.getContractTreeData() ;
							vm.centerDialogVisible = false
						}
					}).catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					})
				} else {
					console.log('error submit!!');
					return false;
				}
			});
		},
		/* 渲染树形菜单工具弹窗 */
		renderContent(h, { node, data, store }) {
      		let powerInfo = Object.assign(this.userPowerInfo,data.powerInfo)
			let menu = '';
			if(data.showTool=='1'){
				let tenderTaskModify = '';
				let tenderTaskDelete = '';
				if (!this.verifyState&&!!powerInfo) {
					if (!!powerInfo.tenderTaskModify) {
						tenderTaskModify = <el-dropdown-item class="tool-item" ><button class="edit-tool" on-click={() => this.trigger('update',data)}>修改</button></el-dropdown-item>
					}
					if (!!powerInfo.tenderTaskDelete) {
						tenderTaskDelete = <el-dropdown-item class="tool-item" ><button class="edit-tool" on-click={() => this.trigger('delete',data)}> 删除</button></el-dropdown-item>
					}
				}
            	menu = <span>
        			<el-dropdown placement="right-start">
						<span class="tool"><i class="iconfont">&#xe61e;</i></span>
						<el-dropdown-menu slot="dropdown">
							{tenderTaskModify}
							{tenderTaskDelete}
          					<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('aside-tender-task-download',data)}>下载流程控制表</button></el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</span>;
            }


			return (
				<span style="flex: 1;display: flex;align-items: center;justify-content: space-between;font-size: 14px;padding-right: 8px;">
          <span title={node.label}>{node.label}</span>
					{menu}
				</span>
			);
		}
	}
};
</script>

<style lang="scss">

/* 树形控件 */
.el-dropdown-menu{
	&.tool-item{

	}
	padding:0!important;
	button.edit-tool{
		width:100%;
		background-color:white;

		&:hover{
			background-color: #ecf5ff;
			color: #66b1ff;
		}
	}
}

/* 左侧菜单 */
.aside--work-contract {
	/* 中标状态 */
	.el-dialog {
		.el-dialog__body {
			padding-bottom: 0;
			border-bottom: 1px solid #dcdfe6;
		}
	}

	/* 编辑工具 */
	.tool {
		i {
			font-size: 12px;
		}
	}
}
</style>
<style lang="scss" scoped>
/* 左侧菜单 */
.aside--work-contract {
	h2 {
		padding: 10px 0;
		border-bottom: 2px solid #32b4f5;
		font-size: 14px;
	}
	.icon-round {
		position: relative;
		width: 20px;
		height: 20px;
		background-origin: content-box;
		background-position: -326px -62px;
		vertical-align: middle;
	}

	/* 树形控件 */
	.el-tree {
		background-color: #f0f7fe;
		border-radius: 5px;
		margin-top: 10px;
		height: calc(100vh - 244px);
		overflow: auto;

		&.full-height{
		  height: calc(100vh - 204px);
		}
		.el-dropdown-item{

			button{
				width:100%;
			}
		}
	}

}
</style>
