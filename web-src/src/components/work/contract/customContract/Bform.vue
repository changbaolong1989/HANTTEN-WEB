<template>
	<!-- 附录A -->
	<div class="Bform">
		<div class="form-block">
			<el-row :gutter="gutter" class="add">
				<el-col :span="12">
					<span>咨询人提交成果</span>
					<el-button type="primary" class="linear" icon="el-icon-plus" @click="addBform"></el-button>
				</el-col>
				<el-col :span="12">
					<p>注：未选择的服务内容将在文档中以“/”体现</p>
				</el-col>
			</el-row>
			<el-form class="form" ref="Bform" :model="Bform" :disabled="disabled">
				<div class="addform" v-for="(item,index) in Bform.data" :key="index">
					<el-button type="text" icon="el-icon-close" class="colse-icon" @click="colseForm(index)"></el-button>
					<el-row :gutter="gutter">
						<el-col :span="12">
							<el-form-item label="服务范围" :prop="'data.'+index+'.serve'" :rules="[{ required: true, message: '请选择服务范围', trigger: 'change' }]">
								<el-select v-model="item.serve" placeholder="请选择服务范围">
									<el-option v-for="k in servelist" :key="k.id" :label="k.name" :value="k.id + ',' + k.name">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="成果文件名称" :prop="'data.'+index+'.name'" :rules="[{ required: true, message: '请输入成果文件名称', trigger: 'blur' }]">
								<el-input type="text" v-model="item.name" placeholder="请输入成果文件名称"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="gutter">
						<el-col :span="12">
							<el-form-item label="成果文件组成" :prop="'data.'+index+'.constitute'" :rules="[{ required: true, message: '请输入成果文件组成', trigger: 'blur' }]">
								<el-input type="text" v-model="item.constitute" placeholder="请输入成果文件组成"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="提交时间" :prop="'data.'+index+'.date'" :rules="[{ required: true, message: '请选择提交时间', trigger: 'blur' }]">
								<!--<el-date-picker v-model="item.date" type="date" placeholder="请选择提交时间"></el-date-picker>-->
                <el-input type="text" v-model="item.date" placeholder="请输入提交时间"></el-input>
              </el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="gutter">
						<el-col :span="12">
							<el-form-item label="份数" :prop="'data.'+index+'.copies'" :rules="[{ required: true, message: '请输入份数', trigger: 'blur' }]">
								<el-input type="text" v-model="item.copies" placeholder="请输入份数"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="质量标准" :prop="'data.'+index+'.standard'" :rules="[{ required: true, message: '请输入质量标准', trigger: 'blur' }]">
								<el-input type="text" v-model="item.standard" placeholder="请输入质量标准"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
				</div>
			</el-form>
		</div>
	</div>

</template>

<script>
	export default {
    props:['theFormData',"disabled"],
		data() {
			return {
				gutter: 40, // 表单相间距
				servelist: [
					{
						name:"决策阶段",
						id:0
					},
					{
						name:"设计阶段",
						id:1
					},
					{
						name:"发承包阶段",
						id:2
					},
					{
						name:"实施阶段",
						id:3
					},
					{
						name:"竣工阶段",
						id:4
					},
					{
						name:"其他服务",
						id:5
					},
				],
				Bform: this.theFormData||{data:[]},
			};
		},
    watch:{
      theFormData(){
        this.Bform = this.theFormData;
      }
    },
		methods: {
       resetForm(){
        let vm = this;
          vm.$refs['Bform'].resetFields();
      },
			addBform() {
				this.Bform.data.push({
					serve:""
				})
			},
			colseForm(index) {
				this.Bform.data.splice(index, 1)
			}
		}
	};
</script>
<style lang="scss">
	.Bform{
		.el-checkbox__label {
			font-size: 12px;
		}

		.tentative {
			position: relative;

			.el-checkbox-group {
				position: absolute;
				top: -30px;
				right: 0;
			}
		}

		.addform {
			.colse-icon {
				i {
					font-size: 18px;
				}
			}
		}
    .el-form-item__label{
      float: none;
      text-align: left;
      line-height: 30px;
      width: auto !important;
    }
    .el-form-item__content{
      margin-left: 0 !important;
      line-height: 30px;
      position: relative;
      font-size: 14px;
      width: 100%;
       .el-select{
          width: 100%;
      }
    }
	}
</style>
<style lang="scss" scoped>
	.add {
		font-size: 12px;

		span {
			color: #333333;
			margin-right: 5px;
		}

		p {
			color: #B0B0B0;
			text-align: right;
		}

		.el-button {
			width: 26px;
			height: 16px !important;
			line-height: 16px !important;
			border-radius: 5px;
			padding: 0px !important;
			border: none;

			i {
				font-size: 12px;
			}
		}
	}

	.addform {
		border-top: 1px solid #e3e3e3;
		padding-top: 15px;
		position: relative;
		margin-top: 15px;

		.colse-icon {
			color: #808080;
			position: absolute;
			top: 0;
			right: 0;

			i {
				font-size: 18px;
			}
		}
	}
</style>
