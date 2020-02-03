<template>
	<div class="item-table item-table--item m-item-table--item">
	<!-- 表单 -->
	<div class="item-from" style="padding:0px 20px">
		<div class="item-from search-form"
		style="display: inline-block;
		width: calc(100% - 150px);
		overflow: auto;
		padding: 14px 10px;
		box-shadow: none;
		white-space: nowrap;">
			<div class="item-input">
			<label for="i3">项目名称</label>
			<el-input v-model="inquireProjectName" placeholder="请输入项目名称" id="i4"></el-input>
			</div>
			<div class="item-input">
			<label for="i3">所属部门</label>
			<el-select v-model="inquireDepartmentId" placeholder="请选择所属部门" clearable>
				<el-option v-for="item in departmentSelect" :key="item.deptId" :label="item.deptName" :value="item.deptId">{{ item.deptName }}</el-option>
			</el-select>
			</div>
			<div class="item-input">
			<label for="i3">项目编号</label>
			<el-input v-model="inquireProjectNum" placeholder="请输入项目编号" id="i3"></el-input>
			</div>
			<div class="item-input">
			<label for="i3">完成状态</label>
			<el-select v-model="inquireProjectSts" placeholder="请选择完成状态" clearable>
				<el-option v-for="item in projectStsSelect" :key="item.projectSts" :label="item.projectStsLabel" :value="item.projectSts">{{ item.projectStsLabel }}</el-option>
			</el-select>
			</div>
			<div class="item-date">
			<span class="demonstration">项目创建时间</span>
			<el-date-picker
				v-model="inquireDateArr"
				type="daterange"
				value-format="yyyy-MM-dd"
				start-placeholder="开始日期"
				end-placeholder="结束日期"
				:default-value="nowDate"
			></el-date-picker>
			</div>
		</div>
		<div class="itemfrom-right" style="margin-top: 14px;"><el-button type="primary" icon="el-icon-search" class="linear" @click="searchData">查询</el-button></div>
		<!-- 新增 -->
		<el-row type="flex" class="row-bg item-open" justify="end">
		<!-- 创建 -->
		<div class="item-tab-chuang"  @click="requestForm" v-if="!!userPowerInfo.projectAdd">
			<span class="el-icon-plus linear"></span>
			新增
		</div>
		<!-- 创建弹窗 -->
		<el-dialog title="创建项目" :visible.sync="centerDialogVisible" width="750px" center>
			<el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" :inline-message="true">
			<el-row>
				<el-col :span="8">
				<p style="padding: 10px 9px;">合成项目编号：{{ projectNumPrefix }}{{condense}}</p>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="8">
				<div class="item-serial">
					<el-form-item label="项目编号" class="projectNum" prop="projectNumSelect" :rules="{ required: true, message: '请选择项目编号', trigger: 'change' }">
					<el-select v-model="ruleForm.projectNumSelect" placeholder="请选择项目编号" @change="getCondense">
						<el-option v-for="item in condenseSelect" :key="item.companyId" :label="item.condense" :value="item.companyId">{{ item.condense }}</el-option>
					</el-select>
					</el-form-item>
				</div>
				</el-col>
				<el-col :span="8">
				<div class="item-serial">
					<el-form-item label="项目名称" class="projectNum projectName" prop="projectName" :rules="{ required: true, message: '请输入项目名称', trigger: 'blur' }">
						<el-input v-model="ruleForm.projectName" placeholder="请输入项目名称"></el-input>
					</el-form-item>
				</div>
				</el-col>
				<el-col :span="8">
				<div class="item-serial">
					<el-form-item label="咨询类别" class="projectNum" prop="counselTypePeriod" :rules="{ required: true, message: '请选择咨询类别', trigger: 'change' }">
					<el-select v-model="ruleForm.counselTypePeriod" placeholder="请选择咨询类别">
						<el-option v-for="(r, i) in radios" :key="i" :label="r.counselTypeName" :value="r.counselTypeId"></el-option>
					</el-select>
					</el-form-item>
				</div>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="8">
				<p style="padding: 10px 9px;">指定业务部门副总</p>
				</el-col>
			</el-row>
			<div class="card-body">
				<el-card class="box-card">
					<div slot="header" class="clearfix">
					<span>合同</span>
					<span class="item-serial-num">{{ projectNumPrefix }}{{ condense }}-{{ ruleForm.contractNum }}</span>
					</div>

					<div class="text item dynamically-add">
					<el-row>
						<el-col :span="8">
						<el-form-item
							label="指定人员"
							prop="contractList[0].departmentId"
							:class="{ top: false, right: false, bottom: true, left: false }"
							:rules="{ required: true, message: '请选择指定人员', trigger: 'change' }">
							<el-select v-model="ruleForm.contractList[0].departmentId" placeholder="请选择指定人员" @change="getAbbr(ruleForm.contractList[0])">
							<el-option v-for="(i, k) in personSelect" :key="i.jobId" :label="i.userName" :value="i.departmentId"></el-option>
							</el-select>
						</el-form-item>
						</el-col>
						<el-col :span="8">
						<el-form-item
							label="合同编号"
							prop="contractNum"
							:class="{ top: false, right: false, bottom: true, left: false }"
							:rules="{ required: true, message: '请输入合同编号', trigger: 'change' }">
							<el-input v-model="ruleForm.contractNum" placeholder="请输入合同编号"></el-input>
						</el-form-item>
						</el-col>
						<el-col :span="8">
						<el-form-item label="跨部门合作" class="cooperateContract">
							<el-checkbox-group v-model="ruleForm.cooperateContract" @change="cooperateContractChange">
								<el-checkbox :true-label="1" :false-label="0" name="cooperateContract"></el-checkbox>
							</el-checkbox-group>
							</el-form-item>
						</el-col>
					</el-row>
					</div>
					<div style="border-bottom: 1px solid #e3e3e3;padding: 10px 0;" v-if="ruleForm.cooperateContract">
					<span>添加部门</span>
					<el-button style="width: 26px;
								height: 16px !important;
								line-height: 16px !important;
								border-radius: 5px;
								padding: 0 !important;
								border: none;margin-left: 5px;"
						type="primary" icon="el-icon-plus" class="linear" size="mini" @click="addPersonnel"></el-button>
					</div>

					<template v-if="ruleForm.cooperateContract">
					<div class="text item dynamically-add" v-for="(item,index) in ruleForm.contractList" :key="index">
						<template v-if="index">
						<el-form-item
							label="指定人员"
							:prop="'contractList.'+index+'.departmentId'"
							:class="{ top: false, right: false, bottom: true, left: false }"
							:rules="{ required: true, message: '请选择指定人员', trigger: 'change' }">
							<el-select v-model="item.departmentId" placeholder="请选择指定人员" @change="getAbbr(item)">
							<el-option v-for="(i, k) in personSelect" :key="i.jobId" :label="i.userName" :value="i.departmentId"></el-option>
							</el-select>
						</el-form-item>
						<i data-v-3831ef16="" class="el-icon-close" @click="delPersonnel(index)"></i>
						</template>
					</div>
					</template>

				</el-card>
			</div>
			</el-form>
			<span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button></span>
		</el-dialog>
		</el-row>
	</div>
	<div class="collect">
		<span>汇总记录 ： </span>
		<span>合同已收款 ： {{collect.incomeReceivedSum}} 元</span>
		<span>合同金额 ： {{collect.contractAmountSum}} 元</span>
	</div>
	<!-- 表格 -->
	<div class="item-tab-bottom">
		<el-table :data="tabdata" border style="width: 100%" class="item-tab" :span-method="objectSpanMethod" :cell-style="rowClass">
		<template slot="empty" class="tabdata-empty">
			<img src="../../assets/image/no-item.png" alt="" />
			<p>暂无数据</p>
		</template>
		<el-table-column align="center"  class-name="table-column" label="项目编号" :show-overflow-tooltip="true" :resizable="false">
			<template slot-scope="scope">
			<el-button type="text" @click="routerList(scope.$index, scope.row)" class="text-overflow-ellipsis" style="width:100%">{{ scope.row.projectNum }}</el-button>
			<!-- {{ scope.row.projectNum }} -->
			</template>
		</el-table-column>
		<el-table-column align="center" width="70" class-name="table-column" label="合同数量" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.contractCount }}
			</template>
		</el-table-column>
		<el-table-column align="center" min-width="200" class-name="table-column" label="项目名称" :resizable="false">
			<template slot-scope="scope">
			{{  scope.row.projectName == null ? '-' : scope.row.projectName }}
			</template>
		</el-table-column>
		<el-table-column align="center" min-width="130" class-name="table-column" label="合同编号" :show-overflow-tooltip="true" :resizable="false">
			<template slot-scope="scope">
			<el-button type="text" @click="goToInfoPage(scope.$index, scope.row)" class="text-overflow-ellipsis" style="width:100%">{{ scope.row.contractNum == null ? '-' : scope.row.contractNum }}</el-button>
			<!--{{ scope.row.contractNum == null ? '-' : scope.row.contractNum }}-->
			</template>
		</el-table-column>
		<el-table-column align="center" min-width="200" class-name="table-column" label="合同名称" :show-overflow-tooltip="true" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.contractName == null ? '-' : scope.row.contractName }}
			</template>
		</el-table-column>
		<el-table-column align="center" width="180" class-name="table-column" label="咨询类别" :resizable="false">
			<template slot-scope="scope">
			{{  scope.row.counselTypePeriod == null ? '-' : selectGetName(scope.row.counselTypePeriod,radios) }}
			</template>
		</el-table-column>
		<el-table-column align="center" min-width="150" class-name="table-column" label="合作公司" :show-overflow-tooltip="true" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.cooperateCompany == null ? '-' : scope.row.cooperateCompany }}
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" label="合同所属部门" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.departmentName == null ? '-' : scope.row.departmentName }}
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" label="合同负责人" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.dutyPeople == null ? '-' : scope.row.dutyPeople }}
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" label="合同签订时间" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.contractDate == null ? '-' : scope.row.contractDate }}
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" label="合同已收金额" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.incomeReceived == null ? '-' : scope.row.incomeReceived }}
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" label="合同金额" :resizable="false">
			<template slot-scope="scope">
			{{ scope.row.contractAmount == null ? '-' : scope.row.contractAmount }}
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" prop="projectSts" label="项目状态" :resizable="false">
			<template slot-scope="scope">
                <span v-if="scope.row.projectIsInvalidFlag=='1'" style="color: #FF0000;cursor: pointer;">作废</span>
                <span v-else-if="scope.row.projectSts=='0'" style="cursor: pointer;">未完成</span>
                <span v-else style="cursor: pointer;">已完成</span>
			</template>
		</el-table-column>
		<el-table-column align="center" class-name="table-column" prop="isInvalidFlag" label="合同状态" :resizable="false">
			<template slot-scope="scope">
	        <el-tooltip v-if="scope.row.isInvalidFlag=='1'" class="item" effect="dark" :content="'作废原因：'+scope.row.invalidReason" placement="top">
                <span style="color: #FF0000;cursor: pointer;">作废</span>
	        </el-tooltip>
	        <span v-else style="cursor: pointer;">-</span>
			</template>
		</el-table-column>
		</el-table>
	</div>
	<!-- 分页 -->
	<div class="block item-fen" v-show="total > 0">
		<el-pagination
		background
		class="fen-left"
		@size-change="handleSizeChange"
		@current-change="handleCurrentChange"
		:current-page="currentPage"
		:page-sizes="pageSizes"
		:page-size="pageSize"
		layout="total, prev, pager,next,sizes,jumper"
		:total="total"
		></el-pagination>
		<el-button class="linear" type="primary" size="small">确定</el-button>
	</div>
	</div>
</template>

<script>
import axios from 'axios';
export default {
	data() {
	// 部门下拉列表
	const departmentSelect = [
		{
		deptId: 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91',
		deptName: '造价部'
		},
		{
		deptId: 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3',
		deptName: '项目部'
		},
		{
		deptId: 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91',
		deptName: '招标代理部'
		}
	];
	// 项目状态下拉列表
	const projectStsSelect = [
		{
		projectSts: '0',
		projectStsLabel: '未完成'
		},
		{
		projectSts: '1',
		projectStsLabel: '已完成'
		}
	];
	// 返回数据
	return {
		inquireDateArr: '',
		/* 展示查询时间 */
		inquireDepartmentId: '',
		/* 展示查询部门 */
		inquireProjectSts: '',
		/* 展示查询状态 */
		inquireProjectNum: '',
		//查询项目名称
		inquireProjectName: '',
		/* 展示查询编号 */
		nowDate: new Date(),
		dateArr: '',
		total: 0, //分页总数据
		currentPage: 1, //当前页码
		pageSize: 10, //每页显示多少条数据
		pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
		bianhao1: '',
		bianhao: '',
		// 新建弹窗显示
		centerDialogVisible: false,
		serialNumber: '',
		// 表格数据
		biaodata: [],
		//项目创建时间查询值
		timeList:[],
		//权限集合
		userPowerInfo:this.SStorage.getItem("userPowerInfo"),
		// 当前页数据
		tabdata: [],
		collect:{},/* 汇总记录数据 */
		departmentSelect: departmentSelect,
		projectStsSelect: projectStsSelect,
		router: this.$route,
		ruleForm: {
		//验证数据
		// 项目编号
		projectNumSelect: '',
		// 人员显示名称（部门+姓名）
		userName: '',
		projectName:'',
		//咨询类别
		counselTypePeriod:'',
		contractNum:'',// 合同编号
		// 指定人员数组
		contractList:[
			{
			userName: '', // 人员显示名称（部门+姓名）
			departmentId:'',// 部门id
			jobId:''// 职位id
			}
		],
		// 跨部门合作
		cooperateContract:0
		},
		obj: {},
		// 请求头
		config: {
		headers: {
			'Content-Type': 'application/json',
			Accept: 'application/json'
		}
		},
		// 检索条件：部门id
		departmentId: '',
		// 检索条件：项目编号
		projectNum: '',
		// 检索条件：项目名称
		projectName: '',
		// 检索条件：项目状态
		projectSts: '',
		// 公司缩写下拉框
		condenseSelect: [],
		// 人员下拉列表
		personSelect: [],
		// 项目编号前缀
		projectNumPrefix: '',
		// 公司缩写
		condense: '',
		spanArr: [], // 一个空的数组，用于存放每一行记录的合并数
		pos: 0, // spanArr 的索引
		radios: [],
	};
	},
	watch:{
	centerDialogVisible:{
		handler(){
		let vm = this;
		if(!vm.centerDialogVisible){
			vm.resetRuleForm();// 重置表单信息
			vm.$nextTick(function () {
			// 清空表单校验
			if(vm.$refs['ruleForm'])vm.$refs['ruleForm'].clearValidate();
			})
		}
		},
		immediate:true
	}
	},
	created() {
		const vm = this;
		// 获取数据
		this.getAllData();
		//查询咨询类别下拉列表
		this.searchCounselTypePeriodList();
	},
	methods: {
	selectGetName(key,list){
		for (var i = 0; i < list.length; i++) {
			if (list[i].counselTypeId==key) {
				return list[i].counselTypeName
			}
		}
	},
	// 获取公司缩写,并且设置数据
	getCondense(val) {
		let obj = {};
		// 获取下拉框的所有值 condenseSelect:下拉框
		obj = this.condenseSelect.find(item => {
		return item.companyId === val;
		});
		// 设置公司缩写
		this.condense = obj.condense;
	},

	// 获取部门缩写,并且设置数据
	getAbbr(item_) {
		let vm = this;
		let obj = {};
		let repetition = 0;// 重复次数
		// 获取下拉框的所有值 personSelect:下拉框
		obj = this.personSelect.find(item => {
		return item.departmentId === item_.departmentId;
		});
		for (let i = 0, len = vm.ruleForm.contractList.length; i < len; i++) {
		let li = vm.ruleForm.contractList[i];
		if(li.departmentId==item_.departmentId&&++repetition>1){
			item_.userName = '';
			item_.departmentId = '';
			item_.jobId = '';
			item_.abbr = '';
			// 计算合同编号
			vm.countContractNum();
			return vm.stateDialog('error','不可以选择重复的指定人员!');
		};
		}
		// 设置用户id
		item_.userName = obj.userName;
		item_.departmentId = obj.departmentId;
		item_.jobId = obj.jobId;
		item_.abbr = obj.abbr;
		// 计算合同编号
		vm.countContractNum();
	},
	/**
	 * @method 计算部门缩写
	 */
	countContractNum(){
		let vm = this;
		let a = '',b = '',c = '';// 三个指定人员字符串拼接片段
		for (let i = 0, len = vm.ruleForm.contractList.length; i < len; i++) {
		let li = vm.ruleForm.contractList[i];
		switch (i){
			case 0:
			a = li.abbr?li.abbr:'';
			break;
			case 1:
			b = li.abbr?'/'+li.abbr:'';
			break;
			case 2:
			c = li.abbr?'/'+li.abbr:'';
			break;
		}
		}
		// 设置部门缩写,如果abc为空字符串则设置为空
		vm.ruleForm.contractNum = a+b+c?a+b+c+'01':'';
	},
	// 每页条数切换
	handleSizeChange(val) {
		this.pageSize = val;
		this.refers();
	},

	//页码切换
	handleCurrentChange(val) {
		this.currentPage = val;
		this.refers();
	},
	//分页方法
	currentChangePage(list, i, k) {
		this.tabdata = list;
		// 设定一个数组spanArr/contentSpanArr来存放要合并的格数，同时还要设定一个变量pos/position来记录
		this.spanArr = [];
		this.contentSpanArr = [];
		for (var i = 0; i < this.tabdata.length; i++) {
		if (i === 0) {
			this.spanArr.push(1);
			this.pos = 0;
			this.contentSpanArr.push(1);
			this.position = 0;
		} else {
			// 判断当前元素与上一个元素是否相同(第1和第2列)
			if (this.tabdata[i].projectNum === this.tabdata[i - 1].projectNum) {
			this.spanArr[this.pos] += 1;
			this.spanArr.push(0);
			} else {
			this.spanArr.push(1);
			this.pos = i;
			}
		}
		}
	},
	// 查询列表数据
	searchData() {
		const vm = this;
		/* 查询时把查询条件赋值*/
		vm.departmentId = vm.inquireDepartmentId;
		vm.projectSts = vm.inquireProjectSts;
		vm.projectNum = vm.inquireProjectNum;
		vm.projectName = vm.inquireProjectName;
		// 页码重置
		vm.currentPage = 1;
		let searchTimeList = [];
		// 如果时间不为空，则遍历时间，放在集合中
		if (vm.inquireDateArr != null) {
		// 遍历时间
		for (var i = 0; i < vm.inquireDateArr.length; i++) {
			// 把时间放在集合中
			searchTimeList.push(vm.inquireDateArr[i]);
		}
		}
		vm.timeList = searchTimeList
		vm.getAllData()
	},
	// 查询列表数据
	getAllData() {
		const vm = this;

		// 获取列表数据
		vm.$axios
		.post(
			'/project/query/searchProjectList',
			JSON.stringify({
			// 页码
			pageNumber: vm.currentPage,
			// 每页总条数
			pageSize: vm.pageSize,
			// 项目编号
			projectNum: vm.projectNum,
			// 项目编号
			projectName: vm.projectName,
			// 项目状态
			projectSts: vm.projectSts,
			// 部门id
			departmentId: vm.departmentId,
			// 时间
			timeList: vm.timeList
			}),
			vm.config
		)
		.then(function(response) {
			// 如果返回成功，则绘制数据，否则提示失败信息
			if (response.data.status == 'success') {
			// 设置表格数据
			vm.biaodata = response.data.pagedData.dataList;
			// 循环数据放入表格数据对象中
			vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
			// 设置数据总条数
			vm.total = response.data.pagedData.dataCount;
			} else {
			vm.$message({
				type: 'error',
				message: response.data.message,
				center: true
			});
			}
		})
		.catch(function(error) {
			console.log(error);
		});

		/* 获取汇总记录数据*/

		this.apiAddress('/project/query/searchIncomeReceivedSumAndContractAmountSum', {})
		.then(res => {
			// 如果返回成功，则提示成功信息，否则提示失败信息
			if (res.data.status == 'success') {
			// 设置指定人员下拉信息
			vm.collect = res.data.dataVO;
			} else {
			this.stateDialog('error', res.data.message);
			}
		})
		.catch(e => {
			this.stateDialog('error', e);
		});
	},

	// 点击分页
	refers() {
		const vm = this;
		// 获取列表数据
		vm.$axios
		.post(
			'/project/query/searchProjectList',
			JSON.stringify({
			// 页码
			pageNumber: vm.currentPage,
			// 每页总条数
			pageSize: vm.pageSize,
			// 项目编号
			projectNum: vm.projectNum,
			// 项目状态
			projectSts: vm.projectSts,
			// 部门id
			departmentId: vm.departmentId,
			// 时间
			timeList: vm.timeList
			}),
			vm.config
		)
		.then(function(response) {
			// 如果返回成功，则绘制数据，否则提示失败信息
			if (response.data.status == 'success') {
			// 设置表格数据
			vm.biaodata = response.data.pagedData.dataList;
			// 循环数据放入表格数据对象中
			vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
			// 设置数据总条数
			vm.total = response.data.pagedData.dataCount;
			} else {
			vm.$message({
				type: 'error',
				message: response.data.message,
				center: true
			});
			}
		})
		.catch(function(error) {
			console.log(error);
		});
	},

	// 动态修改项目状态的颜色
	rowClass({ row, column, rowIndex, columnIndex }) {
		if (columnIndex === 8 && row.projectSts == '1') {
		return 'color:#FC4053';
		} else if (columnIndex === 8 && row.projectSts == '0') {
		return 'color:#7FD680';
		}
	},
	objectSpanMethod({ row, column, rowIndex, columnIndex }) {
		if (columnIndex === 0 || columnIndex === 1||columnIndex === 2) {
		const _row = this.spanArr[rowIndex];
		const _col = _row > 0 ? 1 : 0;
		return {
			rowspan: _row,
			colspan: _col
		};
		} else if (columnIndex === 8) {
		const _row = this.spanArr[rowIndex];
		const _col = _row > 0 ? 1 : 0;
		return {
			rowspan: _row,
			colspan: _col
		};
		} else if (columnIndex === 12) {
		const _row = this.spanArr[rowIndex];
		const _col = _row > 0 ? 1 : 0;
		return {
			rowspan: _row,
			colspan: _col
		};
		}
	},
	// 点击新增请求数据
	requestForm() {
		const vm = this;
		// 显示添加弹窗
		vm.centerDialogVisible = true;
		vm.addCard();
		// 查询项目编号
		vm.$axios
		.post('/project/query/excSearchMaxProjectNum', {}, vm.config)
		.then(function(response) {
			// 如果返回成功，则绘制数据，否则提示失败信息
			if (response.data.status == 'success') {
			vm.projectNumPrefix = response.data.dataVO.projectNumPrefix;
			} else {
			vm.$message({
				type: 'error',
				message: response.data.message,
				center: true
			});
			}
		})
		.catch(function(error) {
			console.log(error);
		});

		// 查询公司缩写
		vm.$axios
		.post('/project/query/searchCondenseList', {}, vm.config)
		.then(function(response) {
			// 如果返回成功，则绘制数据，否则提示失败信息
			if (response.data.status == 'success') {
			vm.condenseSelect = response.data.dataList;
			} else {
			vm.$message({
				type: 'error',
				message: response.data.message,
				center: true
			});
			}
		})
		.catch(function(error) {
			console.log(error);
		});
	},

	routerList(a, list) {
		this.SStorage.setItem('/page/work/contract/FoundItem', list);
		// this.$router.push({
		//   path: '/page/work/contract/FoundItem'
		// });
		this.$router.push({
		path: '/page/item/contract/FoundItem'
		});
	},
	goToInfoPage(a, obj) {
		obj.businessId = obj.contractId
		this.SStorage.setItem('/page/work/contract/FoundItem', obj);
		this.$router.push({
		path: '/page/item/contract/FoundItem'
		});


	},

	// 弹出层添加卡片
	addCard() {
		const vm = this;
		// 查询人员
		vm.$axios
		.post('/project/query/excSearchPersonList', {}, vm.config)
		.then(function(response) {
			// 如果返回成功，则绘制数据，否则提示失败信息
			if (response.data.status == 'success') {
			// 设置指定人员下拉信息
			vm.personSelect = response.data.dataList;
			} else {
			vm.$message({
				type: 'error',
				message: response.data.message,
				center: true
			});
			}
		})
		.catch(function(error) {
			console.log(error);
		});
	},
	// 添加项目
	submitForm(formName) {
		const vm = this;
		vm.$refs[formName].validate(valid => {
		if (valid) {
			this.$confirm('是否提交?', '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			center: true
			})
			.then(() => {
				// 新增数据
				vm.$axios
				.post(
					'/project/add/saveProjectInfo',
					JSON.stringify({
					// 项目编号前缀
					projectNum: vm.projectNumPrefix+vm.condense,
					// 项目名称
					projectName: vm.ruleForm.projectName,
					// 咨询类别
					counselTypePeriod: vm.ruleForm.counselTypePeriod,
					// 公司id
					companyId: vm.ruleForm.projectNumSelect,
					// 合同编号
					contractNum:vm.ruleForm.contractNum,
					// 跨部门合作
					cooperateContract:vm.ruleForm.cooperateContract,
					// 指定人员
					contractList:vm.ruleForm.contractList
					}),
					vm.config
				)
				.then(function(response) {
					// 如果返回成功，则提示成功信息，否则提示失败信息
					if (response.data.status == 'success') {
					// 隐藏新增弹窗
					vm.centerDialogVisible = false;
					// 清空公司缩写
					vm.condense = '';
					// 查询列表数据
					vm.getAllData();
					vm.$message({
						type: 'success',
						message: response.data.message,
						center: true
					});
					} else {
					vm.$message({
						type: 'error',
						message: response.data.message,
						center: true
					});
					}
				})
				.catch(function(error) {
					vm.$message({
					type: 'error',
					message: error,
					center: true
					});
				});
			})
			.catch(() => {});
		} else {
			console.log('error submit!!');
			return false;
		}
		});
	},
	resetRuleForm(){
		let vm = this;
		vm.ruleForm = {
		//验证数据
		// 项目编号
		projectNumSelect: '',
		// 人员显示名称（部门+姓名）
		userName: '',
		projectName:'',
		//咨询类别
		counselTypePeriod:'',
		contractNum:'',// 合同编号
		// 指定人员数组
		contractList:[
			{
			userName: '', // 人员显示名称（部门+姓名）
			departmentId:'',// 部门id
			jobId:''// 职位id
			}
		],
		// 跨部门合作
		cooperateContract:0
		};
	},
	/**
	 * @method 添加指定人员
	 */
	addPersonnel(){
		let vm = this;
		if(vm.ruleForm.contractList.length>2)return;
		vm.ruleForm.contractList.push({
			userName: '', // 人员显示名称（部门+姓名）
			departmentId:'',// 部门id
			jobId:''// 职位id
			});
	},
	/**
	 * @method 删除指定人员
	 */
	delPersonnel(i){
		let vm = this;
		if(vm.ruleForm.contractList.length>1)vm.ruleForm.contractList.splice(i, 1);
		// 计算合同编号
		vm.countContractNum();
	},
	/**
	 * @method 跨部门合作改变
	 */
	cooperateContractChange(val){
		let vm = this;
		if(!val)vm.ruleForm.contractList
		while(vm.ruleForm.contractList.length>1){
		vm.ruleForm.contractList.pop();
		// 计算合同编号
		vm.countContractNum();
		}
	},
	// 查询列表数据
	searchCounselTypePeriodList() {
		const vm = this;
		let formData={
		}
		// 查询咨询类别下拉列表
		vm.apiAddress('/project/query/searchCounselTypePeriodList', formData).then(res => {
		if (res.data.status == 'failure') {
			vm.$message({
			message: res.data.message,
			type: 'error',
			center: true
			});
		} else {
			// 设置表格数据
			let dataList = res.data.dataList;
			this.radios = dataList
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
};
</script>

<style lang="scss">
.el-tabs__content {
	overflow: visible!important;
}
.collect {
	display: flex;
	background-color: #fff;
	padding: 20px;
	font-size: 12px;
	color: #333;
	span{
	margin-right: 30px;
	}
}
/* 表单 */
.item-table .item-from {
	padding: 14px 20px;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
	background: #fff;
	box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
	position: relative;

	.item-tab-chuang {
	position: absolute;
	top: -50px;
	right: 0;
	width: 75px;
	}
}

.item-table .item-from > div {
	display: inline-block;
}

.item-table .el-input__inner {
	background-color: #f8f9fb;
}

.item-table .item-serial .el-form-item {
	display: block !important;
}

.item-table .item-from .el-input {
	width: 125px;
	height: 32px;
	border: 1px solid #f8f9fb;
	border-radius: 5px;
	margin-right: 20px;
	font-size: 12px;
}

.item-table .item-input label {
	font-size: 12px;
	color: #333;
	margin-right: 10px;
}

.item-table .item-date {
	margin-top:10px;
	margin-right: 30px;
}

.item-table .item-date span {
	font-size: 12px;
	color: #333;
	margin-right: 10px;
}

.item-table .itemfrom-right {
	float: right;
}

.item-table .itemfrom-right button {
	margin: 0 10px;
	font-size: 12px;
	width: 90px;
	height: 30px;
	line-height: 30px;
	border-radius: 5px;
	padding: 0;
}

.item-table .item-from .el-input .el-input__inner {
	color: #333;
	width: 122px;
	height: 30px;
	line-height: 30px;
}

.item-table .item-from .el-range-editor.el-input__inner {
	border-radius: 5px;
	width: 233px;
	height: 30px;
}

.item-table .el-date-editor .el-range-input {
	font-size: 12px;
	color: #333333;
	background-color: #f8f9fb;
	height: 28px !important;
}

.item-table .el-date-editor .el-range__icon,
.el-date-editor .el-range-separator {
	line-height: 20px;
	color: #b1b1b1 !important;
}

/* 表格 */
.item-table .item-tab-bottom {
	.el-table--border {
	border-top-left-radius: 0 !important;
	border-top-right-radius: 0 !important;
	border-top: none;
	box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
	}
}

.item-table .item-tab-bor {
	margin-top: 20px;
}

/* 分页 */
.item-table .item-fen {
	float: right;
	color: #424242;
	font-size: 12px;
}

.item-table .el-table .cell {
	line-height: 20px;
}

.item-table .item-fen button {
	margin: 0 10px;
}

.item-table .fen-left {
	float: left;
}

.item-table .el-pagination__total {
	color: #424242;
}

.item-table .el-pagination.is-background .el-pager li:not(.disabled).active {
	background: linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
	background: -moz-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
	background: -ms-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
	background: -webkit-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
}

.item-table .el-pagination.is-background .el-pager li:not(.disabled).active:hover {
	color: #fff;
}

.item-table .el-pagination.is-background .el-pager li:not(.disabled):hover {
	border: 1px solid #06a1ee;
	color: #424242;
}

.item-table .el-pager li {
	font-size: 12px;
}

.item-table .item-fen .el-input__inner {
	height: 25px;
}

/* 弹窗 */

.item-table .el-dialog__body {
	padding: 25px 20px 30px;
	font-size: 12px;
	color: #424242;
}

.item-table .item-serial .el-input {
	margin-left: 10px;
	width: 120px;
	height: 24px;
}

.item-table .addcard {
	width: 24px;
	height: 14px !important;
	line-height: 14px !important;
	padding: 0px 2px;
}

.add-card {
	width: 100%;
	text-align: left;
}

.box-card {
	padding: 15px 20px;
	background-color: #f1f5f8;
	border: none;
	width: 100%;
	display: inline-block;
	vertical-align: middle;
	box-shadow: none !important;
	overflow: visible;
}

.box-card .el-card__header {
	border-bottom: 1px solid #e3e3e3;
	padding: 10px 0;
}

.box-card .el-card__header .clearfix span:first-child {
	float: left;
}

.box-card .el-card__body {
	padding: 10px 0;
}

.box-card .el-card__body .el-form-item,
.item-serial .el-form-item {
	display: inline-block;
	margin-bottom: 0px;
}

.box-card .el-card__body .el-form-item label,
.item-serial .el-form-item label {
	width: 58px !important;
	font-size: 12px;
	color: #424242;
	padding: 0;
}

.box-card .el-card__body .el-form-item .el-form-item__content,
.item-serial .el-form-item .el-form-item__content {
	margin-left: 5px !important;
	float: left;
}

.box-card .el-card__body .el-form-item .el-form-item__content input,
.item-serial .el-form-item .el-form-item__content input {
	width: 120px;
	height: 24px;
	line-height: 24px;
}

.item-serial-num {
	float: right;
	color: #b3b3b3;
}

.card-body {
	max-height: 290px;
}

.card-right {
	float: right;
}

.card-right .el-select {
	width: 120px;
	height: 24px;
}

.colsecard {
	margin-left: 3%;
	cursor: pointer;
}

.colsecard i {
	font-size: 18px;
	color: #999999;
}

.box-card .el-card__body .el-card {
	color: #424242 !important;
}

.item-table .el-dialog__footer .el-button {
	width: 121px;
	height: 31px;
	padding: 0;
	font-size: 12px;
}

.top,
.right,
.bottom.left {
	position: relative;
}

.top .el-form-item__error,
.right .el-form-item__error,
.bottom .el-form-item__error,
.left .el-form-item__error {
	padding: 6px 10px;
	background-color: #f85b48;
	border-radius: 5px;
	top: -60%;
	font-size: 12px;
	left: 0px !important;
	white-space: nowrap;
	position: absolute;
	color: #fff;
	z-index: 9999;
}

.top .el-form-item__error:before,
.right .el-form-item__error:before,
.bottom .el-form-item__error:before,
.left .el-form-item__error:before {
	content: '';
	width: 0px;
	height: 0px;
	border-top: 5px solid #f85b48;
	border-left: 5px solid transparent;
	border-right: 5px solid transparent;
	border-bottom: 5px solid transparent;
	position: absolute;
	bottom: -10px;
}

.right .el-form-item__error {
	top: 10px;
	left: inherit;
}

.right .el-form-item__error:before {
	border-top: 5px solid transparent;
	border-right: 5px solid #f56c6c;
	left: -10px;
	top: 5px;
}

.bottom .el-form-item__error {
	top: 40px!important;
}

.bottom .el-form-item__error:before {
	top: -10px;
	border-top: 5px solid transparent;
	border-bottom: 5px solid #f56c6c;
}

.left .el-form-item__error {
	top: 10px;
	left: -100%;
}

.left .el-form-item__error:before {
	border-top: 5px solid transparent;
	border-left: 5px solid #f56c6c;
	right: -10px;
	top: 5px;
}

.item-serial {
	/* 项目编号 */
	.projectNum {
	.el-form-item__label,
	.el-form-item__content {
		display: inline-block;
		width: auto !important;
	}

	.el-input--suffix {
		height: 30px;
		line-height: 30px;
	}

	.el-form-item__error {
		&::before {
		top: 6px;
		left: -10px;
		border-right: 5px solid #f85b48;
		border-bottom: 5px solid transparent;
		}
	}
	}
}

/* 项目表格和添加 */
.m-item-table--item{
	/* 表单校验 */
	.el-form-item__error--inline{
	position: absolute;
	top: 100%!important;
	left: 0!important;
	}

	/* 动态添加指定人员 */
	.dynamically-add{
	position:relative;

	/* 删除图标 */
	.el-icon-close{
		position: absolute;
		top: 15px;
		left: 195px;
	}
	}

	.box-card{
	.el-card__body{
		.el-form-item{
		/* 跨部门合作 */
		&.cooperateContract{

			label{
			width: auto!important;
			}
		}
		}
	}
	}
}


</style>
