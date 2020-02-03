<template>
	<!-- 公司设置 > 工作步骤说明设置-->
	<div class="set-explain">
		<el-menu class="el-menu-demo"  mode="horizontal" :default-active="activeIndex">
			<el-menu-item index="1" @click="toggleTab('db5e76f9-9fbd-11e9-a036-0e411fd5cf91')">造价部</el-menu-item>
			<el-menu-item index="2" @click="toggleTab('dea3cd5d-9fbd-11e9-a036-0e411fd5cf91')">招标代理部</el-menu-item>
			<el-menu-item index="3" @click="toggleTab('593df92d-fb21-442a-b279-a8b821bbc0d4')">市场部</el-menu-item>
			<el-menu-item index="4" @click="toggleTab('eb1f8c91-960d-4717-84ee-cc3b8137ccd3')">项目部</el-menu-item>
		</el-menu>
		<component  ref="myChild" :is="current" :childSearchId="childSearchId" v-if="loadChild"></component>
	</div>
</template>

<script>
	/* 工作步骤说明设置路由开始 */
	import MHadSetExplainConst from '@/components/head/setting/set-explain/set-explain-cost' /* 公司设置 > 工作步骤说明设置 > */
	/* 工作步骤说明设置路由结束 */
	export default {
		data() {
			return {
				loadChild:true,
				current: MHadSetExplainConst,
				activeIndex: "1",
				childSearchId:"db5e76f9-9fbd-11e9-a036-0e411fd5cf91"
			}
		},
		components: {
			MHadSetExplainConst,
		},
		methods: {
			toggleTab(childSearchId) {
				this.childSearchId = childSearchId;
				this.loadChild = false;
				this.$nextTick(() => {
					this.loadChild = true;
		      	})
				this.$refs.myChild.refreshComponent();
			}
		}
	}
</script>


<style lang="scss">
	.set-explain {
		.el-menu-demo {
			margin-left: 10px;
			overflow: hidden;

			.el-menu-item {
				height: 40px;
				padding: 0 50px;
				float: left;
				margin-bottom: 0px;
				border-radius: 10px 10px 0 0;
				box-shadow: 0px 0px 10px rgba(176, 192, 202, 0.3);
				background: #fff;
				line-height: 40px;
				cursor: pointer;
				color: #B1B1B1;
				border: none;

				&:hover {
					background: #0099E8;
					color: #fff;
				}
			}

			.is-active {
				background: #0099E8 !important;
				color: #fff;
			}
		}
		.addcol{
			cursor: pointer;
		}
				.el-dialog__body {
			padding: 0;
		
			.el-form {
				padding: 18px 30px 0;
				border-bottom: 1px solid #dcdfe6;
				.el-form-item{
					&>label{
						width: 100%!important;
						text-align: left;
					}
					.el-form-item__content{
						margin-left: 0px!important;
						textarea{
							min-height:100px!important;
						}
						label{
							width: 50%;
							margin-right: 0;
						}
					}
				}
			}
		}
		.dialog-footer{
			.el-button{
				padding-top: 0;
				padding-bottom: 0;
			}
		}
		
	}
</style>
