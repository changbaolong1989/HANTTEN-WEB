<template>
	<div class="cost-aside">
		<h2>
			<i class="icon icon-round"></i>
			造价部
		</h2>
		<el-tree :data="treeData" :class="{'full-height':hide}" accordion :render-content="renderContent" @node-click="clickNode"></el-tree>
  </div>
</template>
<script>
export default {
	data() {
		return {
			hide: false,
			treeData:[],
		};
	},
	mounted() {
		this.getContractTreeData();
	},
	methods: {
		clickNode(node){
      if(node.label=="A1--A9"){
          this.$router.push('/page/report/details')
      }else if(node.label=="编制成果文件"){
          this.$router.push('/page/report/prepare')
      }else if(node.label=="收集资料"){
          this.$router.push('/page/report/collectingData')
      }else if(node.label=="拟定工作计划"){
          this.$router.push('/page/report/drawUpAWorkPlan')
      }else if(node.label=="踏勘现场"){
          this.$router.push('/page/report/costOfSiteVisit')
      }else if(node.label=="修改完善"){
          this.$router.push('/page/report/costOfModifyThePerfect')
      }else if(node.label=="存档"){
          this.$router.push('/page/report/costOfArchives')
      }else if(node.label=="招标部A7"){
        this.$router.push('/page/report/costOfTender')
      }else if(node.label=="招标部A8"){
        this.$router.push('/page/report/costOfBids')
      }
		},
		trigger(name,data) {
			let vm = this;
			// 触发事件
			vm.bus.$emit(name, data);
		},
		// 查询树形数据
		getContractTreeData() {
        this.treeData = [{
          label: '123456789',
          children: [{
            label: '请款',
            children: [{
              label: 'A1--A9'
            },{
              label: '编制成果文件'
            },{
              label: '收集资料'
            },{
              label: '拟定工作计划'
            },{
              label: '修改完善'
            },{
              label: '踏勘现场'
            },{
              label: '存档'
            },{
              label: '往来函件'
            },
            {
              label: '招标部A7'
            },{
              label: '招标部A8'
            }]
         }]
      }];
      this.trigger("click",this.treeData[0]);
    },
    /* 渲染树形菜单工具弹窗 */
		renderContent(h, { node, data, store }) {
			let menu = '';
			if(data.showTool=='1'){
            	menu = <span><el-dropdown placement="right-start">
							<span class="tool"><i class="iconfont">&#xe61e;</i></span>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('update',data)}>修改</button></el-dropdown-item>
								<el-dropdown-item class="tool-item"><button class="edit-tool" on-click={() => this.trigger('delete',data)}> 删除</button></el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
						</span>;
            }


			return (
				<span style="flex: 1;display: flex;align-items: center;justify-content: space-between;font-size: 14px;padding-right: 8px;">
					<span>{node.label}</span>
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
.cost-aside {

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
.cost-aside {
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
