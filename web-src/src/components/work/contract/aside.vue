<template>
	<div class="aside--work-contract">
		<h2>
			<i class="icon icon-round"></i>
			项目阶段
		</h2>
    <el-row>
      <el-col :span="24" style="display: flex;">
        <el-button
          class="linear win"
          v-if="!hide&&(projectPhaseCalculate.length&&1!=projectPhaseCalculate[0].isInvalidFlag)"
          :disabled="verifyStateFlag"
          style="width: 100%;margin-top: 10px;padding: 0;color: white;"
          @click="centerDialogVisible = true"
          ref="state">{{projectSts==='1'?'已完成':'结束项目'}}
        </el-button>
        <!-- :disabled="verifyStateFlag" -->
        <el-button
          class="linear win"
          v-if="!!userPowerInfo.projectInvalid&&(projectPhaseCalculate.length&&1!=projectPhaseCalculate[0].isInvalidFlag)"
          style="width: 100%;margin:0;margin-top: 10px;padding: 0;color: white;"
          @click="centerDialogVisibleCancellation = true"
          ref="state">{{projectSts==='1'?'已完成':projectInfo.isInvalidFlag==='1'?'已作废':'作废项目'}}
        </el-button>
        <el-button
          class="linear"
          v-if="projectPhaseCalculate.length&&1==projectPhaseCalculate[0].isInvalidFlag"
          style="width: 100%;margin:0;margin-top: 10px;padding: 0;color: white;background: linear-gradient(to right, #909399 0, #C0C4CC 100%)!important;">已作废
        </el-button>


      </el-col>
    </el-row>
    <el-tree
    ref="tree"
    :data="projectPhaseCalculate"
    :show-checkbox="false"
    :class="{'full-height':hide}"
    accordion
    :render-content="renderContent"
    @node-click="clickNode"
    :indent="5"
    :default-expanded-keys="expandedKeys"
    node-key="id"
    @node-expand="nodeExpand">
    </el-tree>

		<!-- 结束项目弹窗 -->
		<el-dialog title="结束项目" :visible.sync="centerDialogVisible" width="400px" transition="fade-in-linear" center :modal-append-to-body="false" :close-on-click-modal="false">
      是否结束项目？
      <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取消</el-button>
          <el-button type="primary" class="linear" @click="submitForm">确定</el-button>
        </span>
		</el-dialog>

    <!-- 作废合同弹窗 -->
    <el-dialog title="作废合同" @closed="cleanFormData()" :visible.sync="centerDialogVisibleCancellation" width="400px" transition="fade-in-linear" center :modal-append-to-body="false" :close-on-click-modal="false">
       是否作废合同？
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="作废原因" prop="cause">
              <el-input type="textarea" v-model="form.cause"></el-input>
            </el-form-item>
        </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisibleCancellation = false">取消</el-button>
          <el-button type="primary" class="linear" @click="submitFormCancellation('form')">确定</el-button>
        </span>
    </el-dialog>


	</div>
</template>

<script>
export default {
	data() {
		return {
      form:{
        id:'',
        cause:''// 作废原因
      },
      rules:{
        cause: [{ required: true, message: '请输入作废原因', trigger: 'blur' }],
      },
      projectInfo:{},
			hide: false,
			projectPhase: [],// 树形菜单数据
      projectPhaseCalculate:[],// 树形菜单数据(计算后)
      expandedKeys:[],// 展开的菜单标识数组
      checkedKeys:[this.SStorage.getItem('/page/work/contract/FoundItem').businessId],// 选中的菜单标识数组
      verifyStateFlag:true,
      projectSts:"",// 项目状态
			centerDialogVisible: false, // 结束合同弹窗显示
      centerDialogVisibleCancellation:false,// 作废项目弹窗显示
      userPowerInfo:this.SStorage.getItem("userPowerInfo"),
			// 请求头
			config: {
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				}
			},
			storageData : this.SStorage.getItem('/page/work/contract/FoundItem'),
      isProjectTermination:false// 项目终止
		};
	},
  watch:{
    projectPhase(){// 计算菜单数据,添加parent
      let vm = this;
      // 赋值计算后的树形菜单数据
      if(!vm.projectPhase.length)return vm.projectPhaseCalculate = [];
      // 拷贝的菜单数据(返回的对象)
      let returnArr = vm.tool.deepCopy(vm.projectPhase,vm.tool.deepCopy);
      let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem')
      // 维护项目标识\状态
      sessionData.projectId = returnArr[0].id;
      sessionData.projectSts = returnArr[0].projectSts||'';
      vm.SStorage.setItem('/page/work/contract/FoundItem',sessionData);
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
      vm.projectPhaseCalculate = vm.tool.deepCopy(vm.projectPhase,vm.tool.deepCopy);
      // vm.expandedKeys = expandedKeys;
      // vm.checkedKeys = [checkedKey];

      vm.$nextTick(function () {
        vm.$refs['tree'].setCurrentKey(vm.checkedKeys[0]);
         let interValIndex = setInterval(function() {
           let nodes = document.getElementsByClassName('is-current');//isFinish
           let node = nodes[0];
           if(node){
             node.click();
            clearInterval(interValIndex);
           }
         }, 120);
      });

    },
    immediate: true
  },
	mounted() {
		let vm = this;

		vm.getContractTreeData();
		// 监听刷新树形菜单事件
		vm.bus.$on('asideRefresh', val => {
		  // 左侧树刷新
      this.$router.go(0);
      // 设置树形菜单默认选中
      // TODO 刷新页面暂时使用 【this.$router.go(0);】
      // if(val&&val.checkedKey)vm.checkedKeys = [val.checkedKey];
			// vm.getContractTreeData();
		});
    vm.bus.$on('isProjectTermination', val => {// 监听终止项目
      let vm = this;
      vm.isProjectTermination = val;
    });
    vm.searchInfo();
	},
	methods: {
    /**
     * @mevent 点击树形菜单节点
     * @param {Object} data 数据
     * @param {Object} node 节点数据
     * @param {Object} tree 整个树形菜单节点数据
     */
    clickNode(data,node,tree){
      let vm = this;
      vm.trigger("click",data,node);
      // 相当于触发了树形菜单的展开事件
      vm.nodeExpand(data,node,tree);
    },
    /**
     * @event 展开树形菜单节点
     * @param {Object} data 数据
     * @param {Object} node 节点数据
     * @param {Object} tree 整个树形菜单节点数据
     */
    nodeExpand(data,node,tree){
      let vm = this;
      let num = 0;
      clearInterval(interValIndex2);
      // 设置节点的
      let interValIndex2 = setInterval(function() {
        num++;
        let disableNodes = document.getElementsByClassName('node-dis');
        let finishNodes = document.getElementsByClassName('node-fin');

        if(num==3){
          clearInterval(interValIndex2);
          for (let i = 0, len = disableNodes.length; i < len; i++) {
           disableNodes[i].parentNode.classList.add('node-disabled');
          }
          for (let i = 0, len = finishNodes.length; i < len; i++) {
           finishNodes[i].parentNode.classList.add('node-finish');
          }

        }
      }, 120);
    },
		trigger(name,data,node) {
			let vm = this;
      let sessionData = vm.SStorage.getItem('/page/work/contract/FoundItem');
      // 维护项目标识\状态
      data.projectId = sessionData.projectId;
      data.projectSts = sessionData.projectSts||'';
      // 维护默认选中对象
      data.businessId = data.id;
      vm.SStorage.setItem('/page/work/contract/FoundItem',data);
			// 触发事件
			vm.bus.$emit(name, data ,node);
		},

		// 查询树形数据
		getContractTreeData() {
			const vm = this;
			// 获取列表数据
			vm.$axios.post('/project/query/searchProjectTree', JSON.stringify({
					// 项目id
					"projectId": vm.storageData.projectId
				}), vm.config)
			.then(function(response) {
				// 如果返回成功，则绘制数据，否则提示失败信息
				if(response.data.status == "success"){
					// 设置表格数据
					vm.projectPhase = [response.data.dataVO];

                    vm.projectSts = vm.storageData.projectSts;

                    if (vm.projectSts != '1'&&!!vm.userPowerInfo.projectClose){

                      vm.verifyStateFlag = false;
                    }

				}else{
					vm.$message({
						type: 'error',
						message: response.data.message,
						center:true
					});
				}
			})
			.catch(function(error) {
				console.log(error);
			});
		},

    /**
     * @method 表单清空
     */
    cleanFormData(){
      let vm = this;
      vm.form.id = "";
      vm.form.cause = "";
    },
    /**
     * @method 作废项目
     */
    submitFormCancellation(formName){
      this.$confirm('此操作将作废当前合同, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        center: true
      }).then(() => {
        let vm = this;
        console.log("vm.storageData.projectId",vm.storageData.projectId)
        vm.$refs[formName].validate(valid => {
          if(valid){
            let formData = {
              "contractId": vm.form.id,
              "projectId": vm.storageData.projectId,
              "invalidReason":vm.form.cause
            };
            // 查询咨询类别下拉列表
            vm.apiAddress('/contract/update/invalidContract', formData).then(res => {
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
                vm.cleanFormData()
                // 左侧树刷新
                this.$router.go(0);
              }
            }).catch(err => {
              vm.$message({
                message: err,
                type: 'error',
                center: true
              });
            })
          }
        });
      }).catch(() => {
      });
    },
    /**
     * @method 结束项目
     */
		submitForm() {
			/* 弹窗表单提交事件 */
            this.centerDialogVisible = false;

            let formData = {
                "projectId": this.SStorage.getItem('/page/work/contract/FoundItem').projectId
		        };
            let vm = this;

            this.apiAddress('/project/close/closeProject', formData).then(res => {
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
                    let tempStorageData = vm.storageData;
                    tempStorageData.projectSts = "1";

                    this.SStorage.setItem('/page/work/contract/FoundItem',tempStorageData);

                    this.$router.go(0)
                }
            }).catch(err => {
                vm.$message({
                    message: err,
                    type: 'error',
                    center: true
                });
            })

		},
		/* 渲染树形菜单工具弹窗 */
		renderContent(h, { node, data, store }) {
      let vm = this;
      let powerInfo = Object.assign({},vm.userPowerInfo,data.powerInfo);
      // 菜单
			let menu = '';
      let menu1ContentProjectModify = '';// 修改项目
      let menu1ContentProjectDelete = '';// 删除项目
      let menu1ContentContractAdd = '';// 添加合同
      if (powerInfo.projectModify&&1!=data.hideMenuFlg&&1!=data.isInvalidFlag) {
        menu1ContentProjectModify=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('editProject',data)}>修改项目</button></el-dropdown-item>
      }
      if (powerInfo.projectDelete&&1!=data.hideMenuFlg&&1!=data.isInvalidFlag) {
        menu1ContentProjectDelete=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('delProject',data)}> 删除项目</button></el-dropdown-item>
      }
      if (powerInfo.contractAdd&&1!=data.isInvalidFlag) {
        menu1ContentContractAdd=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('addContract',data)}>添加合同</button></el-dropdown-item>
      }
      // 项目菜单
			let menu1 = menu1ContentContractAdd||menu1ContentProjectDelete||menu1ContentContractAdd?
                  <span>
                    <el-dropdown placement="right-start">
                      <span class="tool"><i class="iconfont">&#xe61e;</i></span>
                      <el-dropdown-menu slot="dropdown">
                      {menu1ContentProjectModify}
                      {menu1ContentProjectDelete}
                      {menu1ContentContractAdd}
                      </el-dropdown-menu>
                    </el-dropdown>
                  </span>:'';

      let menu2ContentTaskAdd = '';// 创建任务
      let menu2ContentRequestMenuAdd = '';// 创建请款单
      let menu2ContentContractDelete = '';// 删除合同
      let menu2ContentContractNumModify = '';// 修改合同
      let menu2ContentContractUserModify = '';// 分配合同编撰人
      let menu2ContentContractInvalid = '';// 合同作废
      if (powerInfo.taskAdd&&'1'!=data.isInvalidFlag) {
        menu2ContentTaskAdd=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('createTask',data,'0')}>创建任务</button></el-dropdown-item>
      }
      if (powerInfo.requestMenuAdd&&'1'!=data.isInvalidFlag) {
        menu2ContentRequestMenuAdd=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('showAddRequestMenuForm',data)}>创建请款单</button></el-dropdown-item>
       }
      if (powerInfo.contractDelete&&1!=data.hideMenuFlg&&'1'!=data.isInvalidFlag) {
        menu2ContentContractDelete=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('delContract',data)}>删除合同</button></el-dropdown-item>
      }
      if (powerInfo.contractNumModify&&1!=data.hideMenuFlg&&'1'!=data.isInvalidFlag) {
        menu2ContentContractNumModify=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('editContract',data)}>修改合同</button></el-dropdown-item>
      }
      if (powerInfo.contractUserModify&&'1'!=data.isInvalidFlag) {
        menu2ContentContractUserModify=<el-dropdown-item class="tool-item" ><button class="edit-tool" on-click={() => this.trigger('allocation',data)}>分配合同编撰人</button></el-dropdown-item>
      }
      if (powerInfo.contractInvalid&&'1'!=data.isInvalidFlag) {
        menu2ContentContractInvalid=<el-dropdown-item class="tool-item" ><button class="edit-tool" on-click={() => this.contractInvalid(data)}>合同作废</button></el-dropdown-item>
      }
      // 合同菜单
			let menu2 = menu2ContentTaskAdd||menu2ContentRequestMenuAdd||menu2ContentContractDelete||menu2ContentContractNumModify||menu2ContentContractUserModify||menu2ContentContractInvalid?
                  <span>
                  <el-dropdown placement="right-start">
                    <span class="tool"><i class="iconfont">&#xe61e;</i></span>
                    <el-dropdown-menu slot="dropdown">
                      {menu2ContentTaskAdd}
                      {menu2ContentRequestMenuAdd}
                      {menu2ContentContractDelete}
                      {menu2ContentContractNumModify}
                      {menu2ContentContractUserModify}
                      {menu2ContentContractInvalid}
                      </el-dropdown-menu>
                  </el-dropdown>
                  </span>:'';


      // 下载文件
      let downloadFile = '';
      // 删除任务
      let menu3ContentTaskDelete = '';
      // 修改任务
      let menu3ContentTaskModify = '';

      // 如果是项目监理部，则没有下载
      if (this.superviseDeptId !== data.departmentId) {
        downloadFile = <el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('aside-task-download',data)}>下载流程控制表</button></el-dropdown-item>;
      }

      // 判断修改按钮显示
      if (!node.data.approveTime && node.data.projectSts !== "1" && '1' != data.isInvalidFlag && powerInfo.taskModify) {
          menu3ContentTaskModify=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('createTask',data,'1')}>修改任务</button></el-dropdown-item>
      }

      // 判断删除按钮显示
      if (!node.data.verifyState && node.data.projectSts !== "1" && '1' != data.isInvalidFlag && powerInfo.taskDelete) {
          menu3ContentTaskDelete=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('delTask',data)}>删除任务</button></el-dropdown-item>
      }

      // 任务菜单
			let menu3 = <span>
            <el-dropdown placement="right-start">
							<span class="tool"><i class="iconfont">&#xe61e;</i></span>
              <el-dropdown-menu slot="dropdown">
                {menu3ContentTaskDelete}
                {menu3ContentTaskModify}
                {downloadFile}
              </el-dropdown-menu>
						</el-dropdown>
						</span>;

      // 如果任务没有下载任务、删除任务、修改任务
      if(!downloadFile && !menu3ContentTaskDelete && !menu3ContentTaskModify){
        menu3 = '';
      }

      let menuRequestMenuContentRequestMenuDelete = '';// 删除请款单

      if (powerInfo.requestMenuDelete&&'1'!=data.isInvalidFlag&&!!powerInfo.requestMenuDelete) {
        menuRequestMenuContentRequestMenuDelete=<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('delRequestMenu',data)}>删除请款单</button></el-dropdown-item>
      }

      // 请款单菜单
			let menuRequestMenu = menuRequestMenuContentRequestMenuDelete?
          <span>
            <el-dropdown placement="right-start">
							<span class="tool"><i class="iconfont">&#xe61e;</i></span>
							<el-dropdown-menu slot="dropdown">
              {menuRequestMenuContentRequestMenuDelete}
							</el-dropdown-menu>
						</el-dropdown>
						</span>:'';

      if(node.data.projectSts === "1"){
        menu1 = '';// 项目菜单
        menu2 = '';// 合同菜单
        menuRequestMenu = '';// 请款单菜单
      }
   		switch (data.showTool) {
            case "1":
              menu = menu1;
              break;
            case "2":
              menu = menu2;
              break;
            case "3":
              menu = menu3;
              break;
            case "requestMent":
              menu = menuRequestMenu;
              break;
      }
      let spanClass = '';
      if(1==data.isIgnore)spanClass += 'node-dis ';// 忽略样式
      if(1==data.isFinish)spanClass += 'node-fin ';// 完成样式
			return (
				<span class={spanClass} style="flex: 1;display: flex;align-items: center;justify-content: space-between;font-size: 14px;padding-right: 8px;">
					<span title={node.label}>{node.label}</span>
					{vm.isProjectTermination?"":menu}
				</span>
			);
		},
    searchInfo() {
      const vm = this;
      let formData = {
        projectId: vm.projectId,
      };
      vm.apiAddress('/project/query/searchProjectInfo', formData)
      .then(res => {
        if (res.data.status == 'failure') {
          vm.$message({
            message: res.data.message,
            type: 'error',
            center: true
          });
        } else {
          let dataVO = res.data.dataVO;
          vm.projectInfo = dataVO
        }
      })
      .catch(err => {
        vm.$message({
          message: err,
          type: 'error',
          center: true
        });
      });
    },
    contractInvalid(data){
      let vm = this
      vm.centerDialogVisibleCancellation = true
      vm.form.id = data.id
    }
	}
};
</script>

<style lang="scss">

/* 树形控件 */
.el-dropdown-menu{
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

  /* 置灰样式 */
  .node-disabled,.node-disabled:hover,.el-tree-node.is-current .node-disabled{
    // background-color: #DCDFE6!important;
    color: #ccc;
  }

  /* 完成样式 */
  .node-finish,.node-finish:hover,.el-tree-node.is-current .node-finish{
    color: #67C23A;
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
