<template>
	<!-- 工作台 编制投标文件 -->
	<div class="work-contract-compile" style="height: 100%;">

		<div v-if="showA3" style="position: relative;height: 100%;">
			<div class="form-block">
				<el-row :gutter="20">
					<el-col :span="12">项目名称:{{projectName}}
					</el-col>
					<el-col :span="12" class="text-align-right" style="padding-right: 20px;">咨询合同编号：{{contractNum}}
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="12" :offset="12" class="text-align-right" style="padding-right: 20px;">成果文件编号：{{outcomeDocNo}}
					</el-col>
				</el-row>
			</div>
			<div class="form-block" style="position: absolute;width: 100%; margin-top: 30px;">
				<el-table :data="resultList" :border="true" style="width: 100%;">
					<el-table-column align="center" prop="title" label="复核">
					</el-table-column>
					<el-table-column align="center"  label="复核意见" class-name="classMame">
						<template slot-scope="scope">
							<div class="table-block">
								<div class="contant" v-html='showOpinions(scope.row.children)'>
								</div>
								<div class="floor">初审日期：{{showMaxDate(scope.row.children)}}</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column align="center"  label="执行结果" class-name="classMame">
						<template slot-scope="scope">
							<div class="table-block">
								<div class="contant" >
									<p>{{showResult(scope.row.children)}}</p>
								</div>
								<div class="floor">执行日期：{{showPassDate(scope.row.children)}}</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column align="center"  label="复核人" class-name="classMame">
						<template slot-scope="scope">
							<div class="table-block">
								<div class="contant">
									<img :src="showSignPath(scope.row.children)"/>
								</div>
								<div class="floor">通过日期：{{showPassDate(scope.row.children)}}</div>
							</div>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<div class="img-failure" v-if="showImg">
				<img v-bind:src="imgSrc" :alt="imgAlt" />
				<p style="text-align: center;">{{ imgAlt }}</p>
			</div>
		</div>




	</div>
</template>

<script>
	export default {
    	props: ['projectName', 'contractNum', 'outcomeDocNo','tableListData'],
		data() {
			return {
				showA3: true, // 是否显示A3
				showImg: false,
				imgSrc: '',
				imgAlt: '',
				resultList : [
					{ // 一级面板的对象
						title: '一级审核', // 一级面板的标题
						taskKey : 'ManagerVerify',
						children: [{
							name: '', // 二级面板的审批人
							time: '', // 二级面板的时间
							isPass:null,// 通过1,未通过0
							signPath:'',//签名路径
							opinion: '', // 二级面板的意见
						}]
					},
					{ // 一级面板的对象
						title: '二级审核', // 一级面板的标题
						taskKey: 'QualityVerify',
						children: [{
							name: 'XXX', // 二级面板的审批人
							time: '', // 二级面板的时间
							isPass:null,// 通过1,未通过0
							signPath:'',//签名路径
							opinion: '', // 二级面板的意见
						}]
					},
					{ // 一级面板的对象
						title: '三级审核', // 一级面板的标题
						taskKey: 'GenerManagerVerify',
						children: [{
							name: '', // 二级面板的审批人
							time: '', // 二级面板的时间
							isPass:null,// 通过1,未通过0
							signPath:'',//签名路径
							opinion: '', // 二级面板的意见
						}]
					},
				]
			};
		},
    watch:{
      tableListData:{
        handler(){
          let vm = this;
          if(vm.tableListData.length){
            let copyArr = vm.tool.deepCopy(vm.tableListData, vm.tool.deepCopy);
            for (let i = 0, len = copyArr.length; i < len; i++) {
              for (let j = 0, len = vm.resultList.length; j < len; j++) {
                if(copyArr[i].taskKey==vm.resultList[j].taskKey){
                  vm.resultList[j] = vm.tool.deepCopy(copyArr[i], vm.tool.deepCopy);
                }
              }
            }
          };
          vm.resultList = vm.tool.deepCopy(vm.resultList, vm.tool.deepCopy);
        },
        immediate:true
      }
    },
		mounted() {
			let vm = this;
			//绑定全局事件globalEvent
			vm.bus.$on('checkFile', val => {
				vm.checkFile(val);
			});
		},
		methods: {
			showOpinions(children){
				let resultOpinion = '';
				for (var i = 0; i < children.length; i++) {
					if (children[i].isPass == null) {
						return ''
					}
					if (children[i].isPass == '0') {
						resultOpinion+='<p>'+(i+1)+'. '+children[i].opinion
						resultOpinion+=';</p></br>'
					}
					if (children[i].isPass == '1') {
						resultOpinion+='<p>'+(i+1)+'、通过;</p></br>'
					}
				}
				return resultOpinion
			},
			showMaxDate(children){
				let maxDate = children[0].time;
				for (var i = children.length - 1; i >= 0; i--) {
					if (new Date(maxDate)>new Date(children[i].time)) {
						maxDate = children[i].time
					}
				}
				return maxDate
			},
			showResult(children){
				let result = '-';
				let returnFlag = false;
				for (var i = children.length - 1; i >= 0; i--) {
					if (children[i].isPass == '0') {
						result = '已修改';
					}
					if(children[i].isPass == '1'){
						returnFlag = true
					}
				}
				if (returnFlag) {
					return result
				}
				return ''
			},
			showPassDate(children){
				let passDate = ''
				for (var i = children.length - 1; i >= 0; i--) {

					if (children[i].isPass=='1') {
						passDate = children[i].time
					}
				}
				return passDate
			},
			showSignPath(children){
				for (var i = children.length - 1; i >= 0; i--) {
					if (children[i].isPass=='1') {
						return children[i].signPath;
					}
				}
				return ''
			},
			checkFile(n) {
				let vm = this;
				vm.showImg = true;
				if (1 == n) {
					// vm.imgSrc = require('../../../../assets/image/noFile.png');
					vm.imgAlt = '暂无文件';
				} else if (2 == n) {
					// vm.imgSrc = require('../../../../assets/image/unable2preview.png');
					vm.imgAlt = '文件暂时无法预览，请下载查看！';
				}
			}
		}
	};
</script>
<style lang="scss">
	/* 其他公共样式 */
	// @import url('../../common/css/other.css');

	.work-contract-compile {
    height:100%;

		.classMame {
			padding: 0 !important;
		}
	}
</style>
<style lang="scss" scoped>
	.work-contract-compile {
		width: 100%;
		border-radius: 5px;

		/* 失败时显示图片的容器 */
		.img-failure {
			text-align: center;
			width: 300px;
			margin: auto;

			img {
				width: 150px;
				margin: auto;
				display: inline-block
			}
		}

		/* 表哥快 */
		.table-block {
			height: 150px;
			display: flex;
			flex-direction: column;

			/* 内容 */
			.contant {
				flex-grow: 1;
				overflow: auto;

				p {
					line-height: 30px;
				}
			}

			/* 底部内容 */
			.floor {
				height: 30px;
				line-height: 30px;
				border-top: 1px solid #EBEEF5;
			}
		}
	}
</style>
