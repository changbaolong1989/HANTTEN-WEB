<template>
	<el-header class="m-head">
		<a href="http://www.hantten.com/" target="_blank"><img src="../../assets/image/head-company-name.png"></img></a>
		<ul :default-active="activeIndex">
			<li class="personDetails">
				<i class="iconfont">&#xe62b;</i>
          <span>{{loginUserShowName}}</span>
			</li>
			<li title="技术标准" @click="hideselect" v-show="menu04">
				<router-link to="/page/skill">
					<span><i class="iconfont">&#xe619;</i></span>
				</router-link>
			</li>
			<li title="公告" @click="hideselect" v-show="menu05">
				<router-link to="/page/notice">
					<span>
						<i class="iconfont">&#xe61c;</i>
					</span>
				</router-link>
			</li>
			<li title="通知" @click="hideselect" v-show="menu06">
				<router-link to="/page/inform">
					<span>
						<el-badge is-dot class="item" :hidden="hidden">
							<i class="iconfont">&#xe61b;</i>
						</el-badge>
					</span>
				</router-link>
			</li>
			<li title="邮箱" @click="getQQEmailToken" v-show="menu07">
				<router-link to="/noJump">
					<span><i class="iconfont">&#xe618;</i></span>
				</router-link>
			</li>
			<li title="设置" v-show="menu08">
				<el-dropdown @command="changeItem" :class="{selectChange:selectChange}">
					<span>
						<i class="iconfont">&#xe61d;</i>
					</span>
					<el-dropdown-menu slot="dropdown">
						<router-link to="/page/set-inform" v-show="menu09">
							<el-dropdown-item command="a">公司信息</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-collaborate" v-show="menu10">
							<el-dropdown-item command="a">合作公司</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-role" v-show="menu11">
							<el-dropdown-item command="a">角色管理</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-PowerConfig" v-show="menu23">
							<el-dropdown-item command="a">权限管理</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-member" v-show="menu12">
							<el-dropdown-item command="a">员工管理</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-notice" v-show="menu13">
							<el-dropdown-item command="a">公告中心</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-skill" v-show="menu14">
							<el-dropdown-item command="a">技术标准</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-organization" v-show="menu15">
							<el-dropdown-item command="a">组织构架</el-dropdown-item>
						</router-link>
						<router-link to="/page/set-explain" v-show="menu16">
							<el-dropdown-item command="a">工作步骤说明设置</el-dropdown-item>
						</router-link>
					</el-dropdown-menu>
				</el-dropdown>
			</li>
			<li title="个人中心" @click="hideselect">
				<router-link to="/page/person" tag="div">
					<span><i class="icon icon-user"></i></span>
				</router-link>
			</li>
			<li title="退出" @click="quit">
				<a>
					<span><i class="iconfont iconexit"></i></span>
				</a>
			</li>
		</ul>
	</el-header>
</template>

<script>
	export default {
		data() {
			return {
				activeIndex: "",
				selectChange: false,
				hidden: false,
				menu04:false,
				menu05:false,
				menu06:false,
				menu07:false,
				menu08:false,
				menu09:false,
				menu10:false,
				menu11:false,
				menu12:false,
				menu13:false,
				menu14:false,
				menu15:false,
				menu16:false,
				menu23:false,
				loginUserShowName:JSON.parse(sessionStorage.getItem('loginUserShowName')),
				config:{
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					}
				},
			}
		},
		mounted(){
			this.menuList = JSON.parse(sessionStorage.getItem('menuList'));
			for(var i =0;i<this.menuList.length;i++){
				if(this.menuList[i].menuId==="menu_04"){
					this.menu04 = true;
				}
				if(this.menuList[i].menuId==="menu_05"){
					this.menu05 = true;
				}
				if(this.menuList[i].menuId==="menu_06"){
					this.menu06 = true;
				}
				if(this.menuList[i].menuId==="menu_07"){
					this.menu07 = true;
				}
				if(this.menuList[i].menuId==="menu_08"){
					this.menu08 = true;
				}
				if(this.menuList[i].menuId==="menu_09"){
					this.menu09 = true;
				}
				if(this.menuList[i].menuId==="menu_10"){
					this.menu10 = true;
				}
				if(this.menuList[i].menuId==="menu_11"){
					this.menu11 = true;
				}
				if(this.menuList[i].menuId==="menu_12"){
					this.menu12 = true;
				}
				if(this.menuList[i].menuId==="menu_13"){
					this.menu13 = true;
				}
				if(this.menuList[i].menuId==="menu_14"){
					this.menu14 = true;
				}
				if(this.menuList[i].menuId==="menu_15"){
					this.menu15 = true;
				}
				if(this.menuList[i].menuId==="menu_16"){
					this.menu16 = true;
				}
				if(this.menuList[i].menuId==="menu_23"){
					this.menu23 = true;
				}

			}
		},
		methods: {
			getNavDisplay() {
				this.activeIndex = this.$router.current.fullPath;
			},
			changeItem(a, b) {
				this.selectChange = true;
			},
			hideselect() {
				this.selectChange = false;
			},
			/* 退出*/
			quit() {
				const vm = this;
				vm.$confirm('是否退出?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					center: true
				}).then(() => {
					vm.apiAddress('/userLogout/logout', {})
					.then(res => {
						if (res.data.status == 'failure') {
							vm.$message({
								message: res.data.message,
								type: 'error',
								center: true
							});
						} else {
							vm.$router.push('/login')
						}
					})
					.catch(err => {
						vm.$message({
							message: err,
							type: 'error',
							center: true
						});
					})
				})
				.catch(function(error) {
					console.log(error);
				});

			},
			getQQEmailToken(){
				const vm = this;
				let formData = {};
				// 获取列表数据
				vm.apiAddress('/qqEmail/query/getQQEmailToken', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						// 设置表格数据
						let dataVO = res.data.dataVO;
						vm.goToEmailPage(dataVO.access_token)
					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				})
			},
			goToEmailPage(token){
				const vm = this;
				let formData = {
					token:token
				};
				// 获取列表数据
				vm.apiAddress('/qqEmail/query/goToEmailPage', formData).then(res => {
					if (res.data.status == 'failure') {
						vm.$message({
							message: res.data.message,
							type: 'error',
							center: true
						});
					} else {
						// 设置表格数据
						let dataVO = res.data.dataVO;
						window.open(dataVO.login_url);

					}
				}).catch(err => {
					vm.$message({
						message: err,
						type: 'error',
						center: true
					});
				})
			},
		}
	}
</script>

<style lang="scss">
	@mixin linear_right {
		background: linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
		background: -moz-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
		background: -ms-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
		background: -webkit-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
	}


	/* header */
	.m-page .el-header {
		line-height: 60px;
		box-shadow: 0 0px 10px rgba(176, 192, 202, .3);
		background-color: white;
	}

	.m-page .el-header ul,
	.m-page .el-header ul li {
		display: inline-block;
	}

	.m-page .el-header ul {
		position: absolute;
		right: 0px;
		line-height: 25px;
	}

	.m-page .el-header ul li a {
		display: block;
		width: 46px;
		height: 46px;
		text-align: center;
		margin: 6px 25px;
	}
  .personDetails{
    margin: 0;
    height: 60px;
    line-height: 60px;
    color: #a3afbc;
    float: left;
    font-size: 14px;
    margin-right: 20px;
    cursor: text!important;
  }
  .personDetails i{
    font-size: 24px;
    margin-right: 10px;
  }
  .personDetails span{
    line-height: 60px;
    vertical-align: top;
  }
	.m-page .el-header ul li {
		position: relative;
		cursor: pointer;
	}

	.m-page .el-header ul li a span {
		position: absolute;
		width: 100%;
		height: 25px;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		margin: auto;
		border-right: 1px solid #eeeef4;
		i{
			color: #a3afbc;
			font-size: 44px;
		}
	}
  .m-page .el-header ul li:nth-child(2) a span {
    border-left: 1px solid #eeeef4;
  }
		.m-page .el-header ul li:last-child{
			margin-left: 25px;
		}
	.m-page .el-header ul li:last-child a span {
		border-right: none;
		border-left: 1px solid #eeeef4;
	}
	.m-page .el-header ul li:last-child a span i{
		font-size: 22px;
	}
	.m-page .el-header ul li .el-dropdown {
		display: block;
		width: 46px;
		height: 46px;
		text-align: center;
		margin: 6px 25px;
		float: left;
		position: static;
	}

	.m-page .el-header ul li .el-dropdown:hover,
	.selectChange {
		background: #f2f2f2;
		border-radius: 5px;

		.icon-set {
			background-position: -57px -97px !important;
		}
	}

	.m-page .el-header ul li .el-dropdown span {
		position: absolute;
		width: 100%;
		height: 25px;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		margin: auto;
		border-right: 1px solid #eeeef4;
		i{
			font-size: 44px;
			color: #a3afbc;
		}
	}

	.m-page .el-header ul li a .el-badge-mail sup {
		background-color: #0000ED;
	}

	.m-page .el-breadcrumb__item {
		& .is-link {
			color: #B1B1B1;
		}

		&:last-child {

			& .el-breadcrumb__inner {
				color: #333333;
			}
		}
	}
	.m-page .icon-user {
		width: 25px;
		height: 25px;
		position: relative;
		top: -10px;
	}

	.m-page .icon-user {
		width: 30px;
		height: 30px;
		background-position: -16px -58px;
		border-radius: 15px;
		margin: 0 0 0 25px;
		box-shadow: 0 5px 8px rgba(176, 192, 202, .5);
		padding: 0 !important;
		top: -15px;
	}

	.m-page .el-header ul li a[class*="router-link-active"],
	.m-page .el-header ul li a:hover {
		background: #f2f2f2;
		border-radius: 5px;
		i{
			color: #333333;
		}
	}

	.m-page .router-link-active .icon-user,
	.m-page .el-header ul li:hover .icon-user {
		background: url(../../assets/image/com-icon.png) no-repeat -16px -95px, linear-gradient(to right, #06a1ee 0, #4cc2fc 100%);
		box-shadow: 0 5px 8px rgba(69, 186, 244, .5);
	}

	.el-dropdown-menu {
		border-radius: 10px;
		padding: 0;
	}

	.el-dropdown-menu li {
		width: 140px;
		height: 30px;
		line-height: 30px;
		color: #424242;
		font-size: 12px;
	}

	.el-dropdown-menu a li:hover {
		color: #fff !important;
		@include linear_right();
	}
	.m-page .el-header ul li .el-dropdown:hover span i{
		color: #333333;
	}
	.el-dropdown-menu a:first-child:hover~.popper__arrow::after {
		border-bottom-color: #4cc2fc !important;
	}

	.el-dropdown-menu .router-link-active li {
		color: #fff !important;
		@include linear_right();
	}

	.el-dropdown-menu .router-link-active:first-child~.popper__arrow::after {
		border-bottom-color: #4cc2fc !important;
	}
</style>
