<template>
  <div class="cost-A4 matterForm">


    <el-row>
      <el-col :span="18">
        <div>
          <slot>咨询工作计划</slot>
        </div>
      </el-col>
      <el-col :span="6" class="item-tab-chuang" style="text-align: right;" v-if='transmittedData.currentState === "1" && transmittedData.isInvalidFlag!="1" && transmittedData.isMainHeadFlag === "1" && transmittedData.projectSts !== "1"'>
        <div @click="redact" style="display: inline-block;">
          <span class="linear el-icon-edit"></span>
          编辑
        </div>
      </el-col>
    </el-row>

    <table class="align-center">
      <tbody>
        <tr>
          <td rowspan="2">
            <div class="cell">项目名称</div>
          </td>
          <td rowspan="2" colspan="3">
            <div class="cell">{{tableData.projectName}}</div>
          </td>
          <td>
            <div class="cell">编制人</div>
          </td>
          <td>
            <div class="cell">{{tableData.operatorName}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">编制时间</div>
          </td>
          <td>
            <div class="cell">{{tableData.applyTimeBZ}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">咨询业务简述</div>
          </td>
          <td colspan="5">
            <div class="cell">{{tableData.counselDesc}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">建设规模</div>
          </td>
          <td colspan="3">
            <div class="cell">{{tableData.constructScale}}</div>
          </td>
          <td>
            <div class="cell">投资额（万元）</div>
          </td>
          <td>
            <div class="cell">{{tableData.investedAmount}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">咨询内容与重点</div>
          </td>
          <td colspan="5">
            <div class="cell">{{tableData.counselContent}}</div>
          </td>
        </tr>
        <tr>
          <td rowspan="2">
            <div class="cell">咨询业务小组</div>
          </td>
          <td>
            <div class="cell">项目负责人（组长）</div>
          </td>
          <td>
            <div class="cell">{{tableData.counselGroup}}</div>
          </td>
          <td>
            <div class="cell">专业负责人</div>
          </td>
          <td colspan="2">
            <div class="cell">{{tableData.counselDuty}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">成员</div>
          </td>
          <td colspan="4">
            <div class="cell">{{tableData.memberList}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">咨询业务小组分工</div>
          </td>
          <td colspan="5">
            <div class="cell">{{tableData.divideWork}}</div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">部门经理审核</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="cell">公司负责人审定</div>
          </td>
          <td colspan="5">
            <div class="cell"></div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 修改弹窗 -->
    <el-dialog :close-on-click-modal="false" title="修改项目" :visible.sync="centerDialogVisible" width="500px" transition="fade-in-linear"
      center @close="handClose('ruleForm')">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="咨询业务简述" prop="counselDesc">
          <el-input type="textarea" :rows="2" placeholder="请输入咨询业务简述" v-model="ruleForm.counselDesc">
          </el-input>
        </el-form-item>
        <el-form-item label="建设规模" prop="constructScale">
          <el-input type="text" placeholder="请输入建筑面积或道路管道长宽" v-model="ruleForm.constructScale">
          </el-input>
        </el-form-item>
        <el-form-item label="咨询内容与重点" prop="counselContent">
          <el-input type="text" placeholder="请输入咨询内容与重点" v-model="ruleForm.counselContent">
          </el-input>
        </el-form-item>
        <el-form-item label="专业负责人" prop="counselDuty">
          <el-input type="text" placeholder="请输入专业负责人" v-model="ruleForm.counselDuty">
          </el-input>
        </el-form-item>
        <el-form-item label="咨询业务小组分工" prop="divideWork">
          <el-input type="text" placeholder="请输入咨询业务小组分工" v-model="ruleForm.divideWork">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

  export default {
    props: ['projectName', 'transmittedData'],
    data() {
      return {
        tableData: {
          counselDesc: '',//咨询业务简述
          constructScale: '',//建设规模
          counselContent: '',//咨询内容与重点
          counselGroup: '',//组长
          memberList:'',//成员
          counselDuty: '',//专业负责人
          divideWork: '',//咨询业务小组分工
          operatorName: '',//编制人
          applyTimeBZ: '',//编制时间
          investedAmount: '',//投资额
        },
        centerDialogVisible: false,
        ruleForm: {
          counselDesc: "", //咨询业务简述
          constructScale: "", // 建设规模
          counselContent: "", // 咨询内容与重点
          counselGroup: "", // 组长
          counselDuty: "", // 专业负责人
          divideWork: "", // 咨询业务小组分工
          relationId:"",
          taskId:""
        },
        rules: {
          counselDesc: [{
            required: true,
            message: '请输入咨询业务简述',
            trigger: 'blur'
          }],
          constructScale: [{
            required: true,
            message: '请输入建设规模',
            trigger: 'blur'
          }],
          counselContent: [{
            required: true,
            message: '请输入咨询内容与重点',
            trigger: 'blur'
          }],
          counselGroup: [{
            required: true,
            message: '请输入组长',
            trigger: 'blur'
          }],
          counselDuty: [{
            required: true,
            message: '请输入专业负责人',
            trigger: 'blur'
          }],
          divideWork: [{
            required: true,
            message: '请输入咨询业务小组分工',
            trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      redact() {
        this.ruleForm = Object.assign({}, this.tableData);
        this.centerDialogVisible = true;
      },

      // 新增数据
			submitForm(formName) {
				const vm = this;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$confirm('是否提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							center: true
						}).then(() => {
              // 关系id
              vm.ruleForm.relationId = vm.transmittedData.relationId;
							/* 新增数据 */
							vm.$axios.post('/taskExecute/add/insertA5Info', vm.ruleForm)
							.then(function(response) {

								// 如果返回成功，则提示成功信息，否则提示失败信息
								if(response.data.status == "success"){

									// 隐藏编辑弹窗
									vm.centerDialogVisible = false;
									// 清空上次添加的表单内容
									vm.$refs[formName].resetFields();
                  // 查询拟定工作计划信息
                  vm.getTaskWorkPlan();
									vm.$message({
										type: 'success',
										message: response.data.message,
										center: true
									});
								}else{
									vm.$message({
										type: 'error',
										message: response.data.message,
										center: true
									});
								}

							}).catch(function(error) {
								vm.$message({
									type: 'error',
									message: error,
									center: true
								});
							});

						}).catch(() => {});

					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
      handClose(index) {
        this.$refs[index].resetFields();
      },
      //查询拟定工作计划信息
      getTaskWorkPlan(){
        const vm = this;
        this.apiAddress('/taskExecute/query/searchA5Info',{
          "relationId" : this.transmittedData.relationId
        }).then(res => {
          // 如果返回成功，则提示成功信息，否则提示失败信息
          if (res.data.status == "success") {
            let vm = this;
            // 拷贝返回结果到页面
            vm.tableData = Object.assign({},res.data.dataVO);
            // 拷贝返回结果到弹出层
            vm.ruleForm = Object.assign({},res.data.dataVO);

          } else {
            this.stateDialog('error', res.data.message)
          }
        }).catch(e => {
          this.stateDialog('error', e)
        })
      }
    },
    mounted() {
      let vm = this;
      // 查询拟定工作计划信息
      this.getTaskWorkPlan();
    }
  }
</script>

<style lang="scss">
  @import url('../common/css/form.css');
</style>
<style lang="scss" scoped>
  @import url('../common/css/tableScoped.css');

  .item-tab-chuang {
    margin: 0;
  }
</style>
