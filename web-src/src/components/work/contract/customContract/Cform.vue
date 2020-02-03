<template>
	<!-- 附录C -->
	<div class="Cform">
		<div class="form-block">
			<el-row :gutter="gutter" class="add">
				<el-col :span="12">
					<span>委托人提供资料</span>
					<el-button type="primary" class="linear" icon="el-icon-plus" @click="addCform"></el-button>
				</el-col>
				<el-col :span="12">
					<p>注：未选择的服务内容将在文档中以“/”体现</p>
				</el-col>
			</el-row>
			<el-form class="form" ref="Cform" :model="Cform" :disabled="disabled">
				<div class="addform" v-for="(item,index) in Cform.data" :key="index">
					<el-button type="text" icon="el-icon-close" class="colse-icon" @click="colseForm(index)"></el-button>
					<el-row :gutter="gutter">
						<el-col :span="8">
							<el-form-item label="委托人名称" :prop="'data.'+index+'.name'" :rules="[{ required: true, message: '请输入委托人名称', trigger: 'blur' }]">
								<el-input type="text" v-model="item.name"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="资料份数" :prop="'data.'+index+'.copies'" :rules="[{ required: true, message: '请输入资料份数', trigger: 'blur' }]">
								<el-input type="text" v-model="item.copies"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="10">
							<el-form-item label="提供时间" :prop="'data.'+index+'.time'" :rules="[{ required: true, message: '请提供提供时间', trigger: 'blur' }]">
								<el-date-picker v-model="item.time" type="date" placeholder="选择日期"></el-date-picker>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row :gutter="gutter">
						<el-col :span="24">
							<el-form-item label="备注" :prop="'data.'+index+'.remark'" :rules="[{ required: true, message: '请输入备注', trigger: 'blur' }]">
								<el-input type="textarea" v-model="item.remark"></el-input>
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
				Cform: this.theFormData||{data:[]},
			};
		},
    watch:{
      theFormData(){
        this.Cform = this.theFormData;
      }
    },
		methods: {
       resetForm(){
        let vm = this;
          vm.$refs['Cform'].resetFields();
      },
			addCform() {
				this.Cform.data.push({
					name: "",
					copies:"",
					time:"",
					remark: ""
				})
			},
			colseForm(index) {
				this.Cform.data.splice(index, 1)
			},

		}
	};
</script>
<style lang="scss">
  .Cform{
    .form-block {
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
