<template>
  <!-- 公司设置 > 员工管理-->
  <div class="set-member">
    <!-- 新增 -->
    <el-row type="flex" class="row-bg item-open" justify="end">
      <!-- 新增 -->
      <div class="item-tab-chuang" @click="requestForm">
        <span class="el-icon-plus linear"></span>
        新增
      </div>
      <!-- 新增弹窗 -->
      <el-dialog title="新增" :visible.sync="centerDialogVisible" width="710px" transition="fade-in-linear" center @close="closeDialog">
        <!-- 表单内容 -->
        <el-form :model="ruleForm" ref="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
          <el-form-item label="姓名" prop="name"><el-input v-model="ruleForm.name" placeholder="请输入姓名"></el-input></el-form-item>
          <el-form-item label="文化程度" prop="culture">
            <el-select v-model="ruleForm.culture" placeholder="请选择文化程度">
              <el-option v-for="item in culturelist" :key="item.educationId" :label="item.educationName" :value="item.educationId">{{ item.educationName }}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="登录帐号" prop="account"><el-input v-model="ruleForm.account" placeholder="请输入登录帐号"></el-input></el-form-item>
          <el-form-item label="所属公司" prop="company">
            <el-select v-model="ruleForm.company" placeholder="请选择所属公司">
              <el-option v-for="item in companylist" :key="item.companyId" :label="item.companyName" :value="item.companyId">{{ item.companyName }}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="登录密码" prop="password"><el-input v-model="ruleForm.password" placeholder="请输入登录密码" class="loginpass" readonly></el-input></el-form-item>
          <el-form-item label="角色权限" prop="role">
            <el-select v-model="ruleForm.role" placeholder="请选择角色">
              <el-option v-for="item in rolelist" :key="item.roleId" :label="item.roleName" :value="item.roleId">{{ item.roleName }}</el-option>
            </el-select>
          </el-form-item>
          <div class="module is-required is-error" style="overflow: hidden;">
            <div class="module1">
              <el-form-item label="所属部门">
                <el-select v-model="ruleForm.section" placeholder="请选择所属部门" @change="selectchange">
                  <el-option v-for="item in options" :key="item.deptId" :label="item.deptName" :value="item.deptId">{{ item.deptName }}</el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="职务" prop="post">
                <el-select v-model="ruleForm.post" placeholder="请选择职务" @change="selectchange2">
                  <el-option v-for="item in postlist" :key="item.jobId" :label="item.jobName" :value="item.jobId">{{ item.jobName }}</el-option>
                </el-select>
              </el-form-item>
            </div>
            <el-button type="primary" class="linear" @click="addCard(0)" :disabled="disabled" icon="el-icon-plus"></el-button>
            <el-alert :title="item.departmentJobName" show-icon v-for="(item, index) in content" :key="index" @close="closeTip(index)"></el-alert>
            <div class="el-form-item__error" v-show="contentShow">最少选择一个</div>
          </div>
          <el-form-item label="所属专业" prop="major"><el-input v-model="ruleForm.major" placeholder="请输入专业"></el-input></el-form-item>
          <el-form-item label="邮箱" class="du" prop="email"><el-input v-model="ruleForm.email" placeholder="请输入邮箱地址"></el-input></el-form-item>
          <el-form-item label="入职日期" prop="date" class="du"><el-date-picker v-model="ruleForm.date" type="date" placeholder="选择入职日期"></el-date-picker></el-form-item>
          <el-form-item label="拥有资质" class="upload" prop="dialogImageUrl">
            <el-upload
              action="#"
              class="aptitude"
              list-type="picture-card"
              ref="upload1"
              :on-change="handlePictureCardPreview"
              :auto-upload="false"
              :before-upload="beforeAvatarUpload1"
              :file-list="fileList"
              :on-remove="handleRemove"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog><img width="100%" :src="ruleForm.dialogImageUrl" alt="" /></el-dialog>
          </el-form-item>
          <el-form-item label="个人签名" class="upload upload1" prop="signPathFile">
            <el-upload
              class="avatar-uploader"
              ref="upload"
              action="#"
              :show-file-list="false"
              :on-change="handleAvatarSuccess"
              :auto-upload="false"
              :before-upload="beforeAvatarUpload"
              :data="ruleForm"
            >
              <el-button>
                <span class="iconfont">&#xe614;</span>
                上传签名
              </el-button>
              <br />
              <span class="sample">示例图</span>
              <img v-if="ruleForm.signPathFile" :src="ruleForm.signPathFile" class="avatar" />
              <p class="sampleP">271px × 120px</p>
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitForm('ruleForm')">确 定</el-button></span>
      </el-dialog>

      <!-- 修改弹窗 -->
      <el-dialog title="修改" :visible.sync="centerDialogVisible1" width="710px" transition="fade-in-linear" center @close="closeDialog">
        <!-- 表单内容 -->
        <el-form :model="ruleForm1" ref="ruleForm1" :rules="rules" label-width="100px" class="demo-ruleForm">
          <el-form-item label="姓名" prop="name"><el-input v-model="ruleForm1.name" placeholder="请输入姓名"></el-input></el-form-item>
          <el-form-item label="文化程度" prop="culture">
            <el-select v-model="ruleForm1.culture" placeholder="请选择文化程度">
              <el-option v-for="item in culturelist" :key="item.educationId" :label="item.educationName" :value="item.educationId">{{ item.educationName }}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="登录帐号" prop="account"><el-input v-model="ruleForm1.account" placeholder="请输入登录帐号"></el-input></el-form-item>
          <el-form-item label="所属公司" prop="company">
            <el-select v-model="ruleForm1.company" placeholder="请选择所属公司">
              <el-option v-for="item in companylist" :key="item.companyId" :label="item.companyName" :value="item.companyId">{{ item.companyName }}</el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="重置密码" prop="password1">
            <el-radio-group v-model="ruleForm1.password1">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="角色权限" prop="role">
            <el-select v-model="ruleForm1.role" placeholder="请选择角色">
              <el-option v-for="item in rolelist" :key="item.roleId" :label="item.roleName" :value="item.roleId">{{ item.roleName }}</el-option>
            </el-select>
          </el-form-item>
          <div class="module is-required is-error">
            <div class="module1">
              <el-form-item label="所属部门">
                <el-select v-model="ruleForm1.section" placeholder="请选择所属部门" @change="selectchange">
                  <el-option v-for="item in options" :key="item.deptId" :label="item.deptName" :value="item.deptId">{{ item.deptName }}</el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="职务">
                <el-select v-model="ruleForm1.post" placeholder="请选择职务" @change="selectchange2">
                  <el-option v-for="item in postlist" :key="item.jobId" :label="item.jobName" :value="item.jobId">{{ item.jobName }}</el-option>
                </el-select>
              </el-form-item>
            </div>
            <el-button type="primary" class="linear" @click="addCard(1)" :disabled="disabled1" icon="el-icon-plus"></el-button>
            <el-alert :title="item.departmentJobName" show-icon v-for="(item, index) in content" :key="index" @close="closeTip(index)"></el-alert>
            <div class="el-form-item__error" v-show="contentShow">最少选择一个</div>
          </div>
          <el-form-item label="所属专业" class="du" prop="major"><el-input v-model="ruleForm1.major" placeholder="请输入专业"></el-input></el-form-item>
          <el-form-item label="邮箱" class="du" prop="email"><el-input v-model="ruleForm1.email" placeholder="请输入邮箱地址"></el-input></el-form-item>

          <el-form-item label="入职日期" class="du" prop="date"><el-date-picker v-model="ruleForm1.date" type="date" placeholder="选择入职日期"></el-date-picker></el-form-item>

          <el-form-item label="拥有资质" class="upload">
            <el-upload
              action="#"
              class="aptitude"
              list-type="picture-card"
              ref="modifyUpload1"
              :on-change="handlePictureCardPreview"
              :auto-upload="false"
              :before-upload="modifyBeforeAvatarUpload1"
              :file-list="modifyfileList"
              :on-remove="handleRemove"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog><img width="100%" :src="ruleForm1.dialogImageUrl" alt="" /></el-dialog>
          </el-form-item>
          <el-form-item label="个人签名" class="upload upload1" prop="signPathFile">
            <el-upload
              class="avatar-uploader"
              ref="modifyUpload"
              action="#"
              :show-file-list="false"
              :on-change="modifyFormHandleAvatarSuccess"
              :auto-upload="false"
              :before-upload="modifyBeforeAvatarUpload"
              :data="ruleForm"
            >
              <el-button>
                <span class="iconfont">&#xe614;</span>
                上传签名
              </el-button>
              <br />
              <span class="sample">示例图</span>
              <img v-if="ruleForm1.signPathFile" :src="ruleForm1.signPathFile" class="avatar" />
              <p class="sampleP">271px × 120px</p>
            </el-upload>
          </el-form-item>

          <el-form-item label="是否在职" prop="isIncumbent" class="quan">
            <el-radio-group v-model="ruleForm1.isIncumbent">
              <el-radio label="1">在职</el-radio>
              <el-radio label="0">离职</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer"><el-button type="primary" class="linear" @click="submitForm1('ruleForm1')">确 定</el-button></span>
      </el-dialog>
    </el-row>
    <!-- 表单 -->
    <div class="item-from">
      <div class="item-input">
        <label for="i1">姓名</label>
        <el-input v-model="searchUserName" placeholder="请输入姓名" id="i1"></el-input>
      </div>
      <div class="item-input">
        <label for="i2">部门</label>
        <el-select v-model="searchDepartmentId" placeholder="请选择部门"  clearable>
          <el-option v-for="item in option" :key="item.deptId" :label="item.deptName" :value="item.deptId">{{ item.deptName }}</el-option>
        </el-select>
      </div>
      <div class="item-input">
        <label for="i3">是否在职</label>
        <el-select v-model="searchIsIncumbent" placeholder="请选择在职状态" clearable>
          <el-option v-for="item in option1" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </div>
      <div class="itemfrom-right"><el-button type="primary" icon="el-icon-search" class="linear" @click="searchDataList">查询</el-button></div>
    </div>
    <!-- 表格 -->
    <div class="item-tab-bottom">
      <div class="item-tab-bor linear"></div>
      <el-table :data="tabdata" border style="width: 100%" class="item-tab">
        <template slot="empty" class="tabdata-empty">
          <img src="../../../assets/image/no-item.png" alt="" />
          <p>暂无数据</p>
        </template>
        <el-table-column align="center" class-name="table-column" label="姓名" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <p>{{ scope.row.userName }}</p>
            <p style="display: none;">{{ scope.row.userId }}</p>
          </template>
        </el-table-column>
        <el-table-column align="center" class-name="table-column" prop="userAccount" label="账号" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="educationName" label="文化程度" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="deptName" label="所属部门" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="entryDate" label="入职日期" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="isIncumbent" label="是否在职" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="isCooperateCompany" label="是否是合作公司" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column align="center" class-name="table-column" prop="signPath" label="个人签名" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-popover placement="right" title="" trigger="hover" key = "imageKey">
                <img :src="scope.row.signPath" style="height: 500px;width: 500px">
                <img slot="reference" :src="scope.row.signPath" alt="" style="max-height: 50px;max-width: 130px">
              </el-popover>
            </template>
      </el-table-column>
        <el-table-column align="center" class-name="table-column" label="操作" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" class="download" @click="changeTable(scope.$index, scope.row)">修改</el-button>
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
export default {
  data() {
    const formdata = new FormData();
    const formdata1 = new FormData();
    return {
      total: 100, //分页总数据
      currentPage: 1, //当前页码
      pageSize: 10, //每页显示多少条数据
      pageSizes: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], //条目列表
      biaodata: [], //表格数据
      tabdata: [], //当前页数据
      centerDialogVisible: false, //弹窗显示新增
      centerDialogVisible1: false, //弹窗显示修改
      fileList: [],
      modifyfileList: [],
      /* 修改表单图片存储 */
      dialogVisible: false,
      formData: formdata,
      modifyformData: formdata1,
      config: {
        headers: {
          'Content-Type': 'application/json',
          Accept: 'application/json'
        }
      },
      searchUserName: '',
      searchDepartmentId: '',
      searchIsIncumbent: '',
      option: '',
      options: [],
      arr: [],
      option1: [
        {
          label: '是',
          value: 1
        },
        {
          label: '否',
          value: 0
        }
      ],
      culturelist: [],
      companylist: [],
      rolelist: [],
      deleteResourceList: [],
      majorlist: [],
      postlist: [],
      ruleForm: {
        name: '',
        culture: '',
        account: '',
        section: '',
        password: '123456',
        company: '',
        post: '',
        role: '',
        major: '',
        email: '',
        date: '',
        dialogImageUrl: '',
        signPathFile: ''
      },
      ruleForm1: {
        name: '',
        culture: '',
        account: '',
        section: '',
        password1: 0,
        company: '',
        post: '',
        role: '',
        major: '',
        email: '',
        date: '',
        dialogImageUrl: '',
        signPathFile: '',
        isIncumbent: ''
      },

      rules: {
        name: [
          {
            required: true,
            message: '请输入成员名称',
            trigger: 'blur'
          }
        ],
        culture: [
          {
            required: true,
            message: '请选择文化程度',
            trigger: 'change'
          }
        ],
        account: [
          {
            required: true,
            message: '请输入登录帐号',
            trigger: 'blur'
          }
        ],
        company: [
          {
            required: true,
            message: '请选择所属公司',
            trigger: 'change'
          }
        ],
        role: [
          {
            required: true,
            message: '请选择角色',
            trigger: 'change'
          }
        ],
        major: [
          {
            required: true,
            message: '请输入所属专业',
            trigger: 'change'
          }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '必须输入邮箱格式', trigger: 'blur' },
          { max: 50, message: '最长50位', trigger: 'blur' }
        ],
        date: [
          {
            required: true,
            message: '请选择入职日期',
            trigger: 'blur'
          }
        ],
        signPathFile: [
          {
            required: true,
            message: '请选择个人签名',
            trigger: 'blur'
          }
        ]
      },
      resourcePathFileList_update: [], // 修改时的资源文件回显
      content: [],
      contentShow: false,
      jobIds: [],
      disabled: false,
      disabled1: false,
      condUserName:'',
      condIsIncumbent:''

    };
  },
  created() {
    // 本地访问
    this.refer();
    this.getAllData(this);
  },
  methods: {
    addCard(j) {
      /* 添加卡片 */
      this.str1 = '';
      this.str2 = '';
      for (var i = 0; i < this.options.length; i++) {
        if (j == 0) {
          if (this.options[i].deptId === this.ruleForm.section) {
            /* 新增 */
            this.str1 = this.options[i].deptName;
          }
        } else if (j == 1) {
          if (this.options[i].deptId === this.ruleForm1.section) {
            /* 修改 */
            this.str1 = this.options[i].deptName;
          }
        }
      }
      let jobId = '';
      for (var i = 0; i < this.postlist.length; i++) {
        if (j == 0) {
          if (this.postlist[i].jobId === this.ruleForm.post) {
            this.str2 = this.postlist[i].jobName;
            jobId = this.postlist[i].jobId;
          }
        } else if (j == 1) {
          if (this.postlist[i].jobId === this.ruleForm1.post) {
            this.str2 = this.postlist[i].jobName;
            jobId = this.postlist[i].jobId;
          }
        }
      }
      let obj = {
        departmentJobName: this.str1 + '-' + this.str2,
        jobId: jobId
      };
      this.content.push(obj);
      if (this.content.length > 0) {
        this.contentShow = false;
      }
    },
    changeDisabled() {
      /* 是否禁用 */
      const vm = this;
      if (vm.ruleForm.post !== '' && vm.ruleForm.section !== '') {
        vm.disabled = false;
      } else {
        vm.disabled = true;
      }
      if (vm.ruleForm1.post !== '' && vm.ruleForm1.section !== '') {
        vm.disabled1 = false;
      } else {
        vm.disabled1 = true;
      }
    },
    selectchange(id) {
      /* 表单--部门 */
      const vm = this;
      vm.condDeptId = id;
      vm.ruleForm.post = '';
      vm.ruleForm1.post = '';
      vm.$axios
        .post(
          '/config/user/query/searchJobListByDeptId',
          JSON.stringify({
            /* 职务数据 */
            deptId: vm.condDeptId
          }),
          vm.config
        )
        .then(function(response) {
          vm.postlist = response.data.dataList;
        })
        .catch(function(error) {
          console.log(error);
        });
      vm.changeDisabled();
    },
    searchDataList() {
      let vm = this;
      vm.condDeptId1 = vm.searchDepartmentId;
      vm.condUserName = vm.searchUserName;
      vm.condIsIncumbent = vm.searchIsIncumbent;
      vm.refer();
    },
    selectchange2() {
      this.changeDisabled();
    },
    refer() {
      /* 点击查询 */
      const vm = this;
      vm.$axios
        .post(
          '/config/user/query/searchUserList',
          JSON.stringify({
            pageNumber: 1,
            /* 页码   1*/
            pageSize: vm.pageSize,
            /* 每页总条数 */
            condUserName: vm.condUserName,
            /* 用户名 */
            condDeptId: vm.condDeptId1,
            /* 部门id */
            condIsIncumbent: vm.condIsIncumbent /* 是否在职 */
          }),
          vm.config
        )
        .then(function(response) {
          if (response.data.status == 'success') {
            vm.biaodata = response.data.pagedData.dataList;
            vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
            vm.total = response.data.pagedData.dataCount; /* 总条数 */
          } else {
            vm.$message({
              message: response.data.message,
              type: 'error',
              center: true
            });
          }
        })
        .catch(function(error) {
          vm.$message({
            message: error,
            type: 'error',
            center: true
          });
        });
    },
    refers() {
      /* 点击分页 */
      const vm = this;

      vm.$axios
        .post(
          '/config/user/query/searchUserList',
          JSON.stringify({
            pageNumber: vm.currentPage,
            /* 页码 */
            pageSize: vm.pageSize,
            /* 每页总条数 */
            condUserName: vm.condUserName,
            /* 用户名 */
            condDeptId: vm.condDeptId1,
            /* 部门id */
            condIsIncumbent: vm.condIsIncumbent /* 是否在职 */
          }),
          vm.config
        )
        .then(function(response) {
          vm.biaodata = response.data.pagedData.dataList;
          vm.currentChangePage(vm.biaodata, vm.currentPage, vm.pageSize);
          vm.total = response.data.pagedData.dataCount; /* 总条数 */
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    getAllData(vm) {
      /* 请求部门下拉列表*/
      vm.$axios
        .post('/config/user/query/searchDeptTree', {}, vm.config)
        .then(function(response) {
          vm.option = response.data.dataList;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    requestcommit() {
      /* 请求下拉列表数据 */
      const vm = this; /* 文化程度 */
      vm.$axios
        .post('/config/user/query/searchEducationList', {}, vm.config)
        .then(function(response) {
          vm.culturelist = response.data.dataList;
        })
        .catch(function(error) {
          console.log(error);
        }); /* 所属公司 */
      vm.$axios
        .post('/config/user/query/searchCompanyList', {}, vm.config)
        .then(function(response) {
          vm.companylist = response.data.dataList;
        })
        .catch(function(error) {
          console.log(error);
        }); /* 角色权限 */
      vm.$axios
        .post('/config/user/query/searchRoleList', {}, vm.config)
        .then(function(response) {
          vm.rolelist = response.data.dataList;
        })
        .catch(function(error) {
          console.log(error);
        }); /* 所属专业 */
      // vm.$axios
      //   .post('/config/user/query/searchProfessionalList', {}, vm.config)
      //   .then(function(response) {
      //     vm.majorlist = response.data.dataList;
      //   })
      //   .catch(function(error) {
      //     console.log(error);
      //   });
      vm.$axios
        .post('/config/user/query/searchDeptTree', {}, vm.config)
        .then(function(response) {
          vm.options = response.data.dataList;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    requestForm() {
      /* 点击新增请求数据 */
      this.fileList = [];
      this.centerDialogVisible = true;
      this.requestcommit();
      this.changeDisabled();
    },
    changeTable(a, b) {
      /* 点击修改请求数据 */
      this.centerDialogVisible1 = true;
      const vm = this;
      this.content = [];
      this.requestcommit();
      vm.userId = b.userId;
      vm.$axios
        .post(
          '/config/user/query/searchUserInfo',
          JSON.stringify({
            /* 修改查询 */
            userId: b.userId
          }),
          vm.config
        )
        .then(function(response) {
          vm.fileList = [];
          vm.ruleForm1.name = response.data.dataVO.userName;
          vm.ruleForm1.culture = response.data.dataVO.educationId;
          vm.ruleForm1.account = response.data.dataVO.userAccount;
          vm.ruleForm1.email = response.data.dataVO.email;
          vm.ruleForm1.section = response.data.dataVO.deptId; /* 所属部门 */
          vm.ruleForm1.company = response.data.dataVO.companyId;
          vm.ruleForm1.role = response.data.dataVO.roleId;
          vm.ruleForm1.major = response.data.dataVO.professionalName;
          vm.ruleForm1.date = response.data.dataVO.entryDate;

          console.info('isIncumbent:' + response.data.dataVO.isIncumbent);
          vm.ruleForm1.isIncumbent = response.data.dataVO.isIncumbent;

          console.info('isIncumbent11:' + vm.ruleForm1.isIncumbent);

          if (!response.data.dataVO.jobList) {
            vm.content = [];
          } else {
            vm.content = response.data.dataVO.jobList;
          }
          let fileList = response.data.dataVO.flieList;
          if (null != fileList && fileList.length > 0) {
            for (var i = 0; i < fileList.length; i++) {
              let obj = {
                name: fileList[i].fileRelationId,
                url: fileList[i].fileUrl
              };
              vm.fileList.push(obj);
            }
          }

          let path = response.data.dataVO.signPath;
          // TODO
          vm.ruleForm1.signPathFile = path;

          //response.data.dataVO.professionalIds;

          const l = response.data.dataVO.jobId;
          vm.updateDate = response.data.dataVO.updateDate; /* 更新时间 */
          vm.$axios
            .post(
              '/config/user/query/searchJobListByDeptId',
              JSON.stringify({
                /* 职务数据 */
                deptId: vm.ruleForm1.section
              }),
              vm.config
            )
            .then(function(response) {
              vm.postlist = response.data.dataList;
              vm.ruleForm1.post = l;
              vm.changeDisabled();
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    handlePictureCardPreview(file, fileList) {
      /* 多图片状态改变时 */
      this.ruleForm1.dialogImageUrl = file.url;
    },
    beforeAvatarUpload1(file) {
      /* 多图片 */
      this.fileList.push(file);
      return false;
    },
    modifyBeforeAvatarUpload1(file) {
      /*修改多图片 */
      this.modifyfileList.push(file);
      return false;
    },
    modifyFormHandleAvatarSuccess(file, fileList) {
      /* 修改个人签名图片展示 */
      const isPng = file.raw.type === 'image/png';
      if (!isPng) {
        this.$message.error('上传个人签名图片只能是 PNG 格式!');
      } else {
        this.ruleForm1.signPathFile = URL.createObjectURL(file.raw);
      }
    },
    handleAvatarSuccess(file, fileList) {
      /* 个人签名图片展示 */
      const isPng = file.raw.type === 'image/png';
      if (!isPng) {
        this.$message.error('上传个人签名图片只能是 PNG 格式!');
      } else {
        this.ruleForm.signPathFile = URL.createObjectURL(file.raw);
      }
    },
    beforeAvatarUpload(file, fileList) {
      /* 个人签名 */
      this.formData.set('signPathFile', file);
      return false;
    },
    modifyBeforeAvatarUpload(file, fileList) {
      /* 修改个人签名 */
      this.modifyformData.set('signPathFile', file);
      return false;
    },
    closeTip(index, item) {
      this.content.splice(index, 1);
    },
    handleRemove(file, haveFileList) {
      let tempList = this.fileList.slice(0);
      if (haveFileList.length > 0) {
        for (var i = 0; i < haveFileList.length; i++) {
          for (var j = 0; j < tempList.length; j++) {
            if (haveFileList[i].name == tempList[j].name) {
              tempList.splice(j, 1);
              j--;
            }
          }
        }
      }
      this.deleteResourceList = tempList;
    },
    /*弹窗关闭回调*/
    closeDialog() {
      this.content = [];
      this.$refs.ruleForm.resetFields();
    },
    submitForm(formName) {
      /* 新增表单提交事件 */
      this.$refs[formName].validate(valid => {
        if (valid && this.content.length > 0) {
          const vm = this;
          vm.jobIds = [];
          vm.formData.set('userName ', vm.ruleForm.name);
          vm.formData.set('userAccount', vm.ruleForm.account);
          vm.formData.set('password', vm.ruleForm.password);
          vm.formData.set('companyId', vm.ruleForm.company);
          vm.formData.set('roleId', vm.ruleForm.role);
          vm.formData.set('professionalName', vm.ruleForm.major); /* 专业 */
          vm.formData.set('email', vm.ruleForm.email); /* 邮箱 */
          vm.formData.set('educationId', vm.ruleForm.culture);
          vm.formData.set('deptId', vm.ruleForm.section);
          for (var i = 0; i < vm.content.length; i++) {
            vm.jobIds.push(vm.content[i].jobId);
          }
          vm.formData.set('jobIds', vm.jobIds);
          const date = new Date(vm.ruleForm.date);
          var year = date.getFullYear();
          var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
          const time = year + '-' + month + '-' + day;
          vm.formData.set('entryDate', time); /* 日期 */
          vm.$confirm('是否提交?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            center: true
          }).then(() => {
              vm.$refs.upload.submit();
              vm.$refs.upload1.submit();
              // 设置多图片传参数
              vm.formData.set('resourceFiles', null);
              for (var i = 0; i < vm.fileList.length; i++) {
                vm.formData.append('resourceFiles', vm.fileList[i]);
              }
              vm.$axios
                .post('/config/user/add/saveUserInfo', vm.formData, {
                  headers: {
                    'Content-Type': 'application/x-www-form-urlencode;charset=utf-8'
                  }
                })
                .then(function(response) {
                  vm.centerDialogVisible = false; /* 让弹窗隐藏 */
                  vm.formData = new FormData(); /* 清空formData数据*/
                  // this.$refs[formName].resetFields(); /* 清空表单数据 */
                  for (var i = 0; i < vm.$refs.upload1.$el.children[0].children.length; i++) {
                    /* 清空上传图片列表显示 */
                    vm.$refs.upload1.$el.children[0].children[i].style.display = 'none';
                  }
                  vm.fileList = []; /* 清空上传图片列表 */
                  vm.content = [];
                  if (response.data.status == 'success') {
                    vm.refer();
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
          if (this.content.length == 0) {
            this.contentShow = true;
          }
          return false;
        }
      });
    },
    submitForm1(formName) {
      /*修改表单提交事件 */
      const vm = this;
      vm.$refs[formName].validate(valid => {
        if (valid && vm.content.length > 0) {
          vm.jobIds = [];
          vm.modifyformData.set('userId', vm.userId);
          vm.modifyformData.set('userName', vm.ruleForm1.name);
          vm.modifyformData.set('userAccount', vm.ruleForm1.account);
          vm.modifyformData.set('companyId', vm.ruleForm1.company);
          vm.modifyformData.set('roleId', vm.ruleForm1.role);
          vm.modifyformData.set('professionalName', vm.ruleForm1.major); /* 专业 */
          vm.modifyformData.set('email', vm.ruleForm1.email); /* 邮箱 */
          vm.modifyformData.set('educationId', vm.ruleForm1.culture);
          vm.modifyformData.set('isIncumbent', vm.ruleForm1.isIncumbent);
          for (var i = 0; i < vm.content.length; i++) {
            vm.jobIds.push(vm.content[i].jobId);
          }
          vm.modifyformData.set('jobIds', vm.jobIds);
          let deleteResourceList = [];
          for (var i = 0; i < vm.deleteResourceList.length; i++) {
            deleteResourceList.push(vm.deleteResourceList[i].name);
          }
          vm.modifyformData.set('deleteResourceList', deleteResourceList);

          const date = new Date(vm.ruleForm1.date);
          var year = date.getFullYear();
          var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
          var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
          const time = year + '-' + month + '-' + day;
          vm.modifyformData.set('entryDate', time); /* 日期 */
          vm.modifyformData.set('updateDate', vm.updateDate);
          vm.modifyformData.set('isResetPwd', vm.ruleForm1.password1);

          vm.$confirm('是否提交?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            center: true
          })
            .then(() => {
              vm.$refs.modifyUpload.submit();
              vm.$refs.modifyUpload1.submit();
              // 设置多图片传参数
              vm.modifyformData.set('resourceFiles', null);
              for (var i = 0; i < vm.modifyfileList.length; i++) {
                vm.modifyformData.append('resourceFiles', vm.modifyfileList[i]);
              }
              vm.$axios
                .post('/config/user/modify/updateUserInfo', vm.modifyformData, {
                  /* 修改数据 */
                  headers: {
                    'Content-Type': 'application/x-www-form-urlencode;charset=utf-8'
                  }
                })
                .then(function(response) {
                  vm.modifyformData = new FormData(); /* 清空formData数据*/
                  vm.modifyfileList = []; /* 清空上传图片列表 */
                  vm.centerDialogVisible1 = false;
                  if (response.data.status == 'success') {
                    vm.refers();
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
                  vm.modifyformData = new FormData(); /* 清空formData数据*/
                  vm.modifyfileList = []; /* 清空上传图片列表 */
                  vm.$message({
                    type: 'error',
                    message: response.data.status,
                    center: true
                  });
                });
            })
            .catch(() => {});
        } else {
          if (vm.content.length == 0) {
            vm.contentShow = true;
          }
          return false;
        }
      });
    },
    handleSizeChange(val) {
      // 每页条数切换
      this.pageSize = val;
      this.refers();
    },
    handleCurrentChange(val) {
      //页码切换
      this.currentPage = val;
      this.refers();
    },
    currentChangePage(list, i, k) {
      //分页方法
      this.tabdata = list;
    }
  }
};
</script>
<style lang="scss">
/* 表单 */
.set-member .item-from {
  padding: 14px 20px;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0 0 10px 5px rgba(176, 192, 202, 0.1);
  margin-bottom: 20px;
}

.set-member .item-from > div {
  display: inline-block;
}

.set-member .el-input__inner {
  background-color: #f8f9fb;
}

.set-member .item-serial .el-form-item {
  display: block !important;
}

.set-member .item-from .el-input {
  width: 125px;
  height: 32px;
  border: 1px solid #f8f9fb;
  border-radius: 5px;
  margin-right: 20px;
  font-size: 12px;
}

.set-member .loginpass .el-input__inner {
  border: none !important;
  background: none !important;
}

.set-member .item-input label {
  font-size: 12px;
  color: #333;
  margin-right: 10px;
}

.set-member .item-date {
  margin-right: 30px;
}

.set-member .item-date span {
  font-size: 12px;
  color: #333;
  margin-right: 10px;
}

.set-member .itemfrom-right {
  float: right;
}

.set-member .itemfrom-right button {
  margin: 0 10px;
  font-size: 12px;
  width: 90px;
  height: 30px;
  line-height: 30px;
  border-radius: 5px;
  padding: 0;
}

.set-member .item-from .el-input .el-input__inner {
  color: #333;
  width: 135px;
  height: 30px;
  line-height: 30px;
}

.set-member .item-from .el-range-editor.el-input__inner {
  border-radius: 5px;
  width: 233px;
  height: 30px;
}
.set-member .el-date-editor {
  float: left;
  margin: 5px 0;
}
.set-member .el-date-editor .el-range-input {
  font-size: 12px;
  color: #333333;
  background-color: #f8f9fb;
  height: 28px !important;
}

.set-member .item-serial {
  margin: 25px 0;
}

.set-member .el-date-editor .el-range__icon,
.el-date-editor .el-range-separator {
  line-height: 20px;
  color: #b1b1b1 !important;
}

.set-member {
  .download {
    color: #06a1ee;
  }

  .delete {
    color: #fc4053;
  }

  .el-dialog--center {
    .el-dialog__body {
      padding: 0;

      .el-form {
        padding: 18px 15px 0;
        border-bottom: 1px solid #dcdfe6;
        overflow: hidden;
        max-height: 540px;
        overflow: auto;

        .quan {
          width: 100% !important;
        }

        .el-form-item {
          border-bottom: 1px solid #dcdfe6;
          margin: 0;
          padding: 10px 0;
          width: 50%;
          float: left;

          &:last-child {
            border-bottom: none;
          }

          label {
            width: 70px !important;
            color: #424242;
            font-size: 12px;
            text-align: right;
          }

          .el-form-item__content {
            margin-left: 70px !important;

            .el-input__inner {
              width: 250px;
            }

            .el-checkbox-group {
              float: left;
              height: 40px;
            }

            .el-date-editor.el-input {
              width: 250px;
            }

            .el-radio-group {
              .el-radio__label {
                font-size: 12px;
                color: #808080;
              }

              .is-checked {
                .el-radio__label {
                  color: #06a1ee;
                }
              }
            }
          }
        }

        .du {
          width: 100%;
        }

        .module {
          border-bottom: 1px solid #dcdfe6;
          min-height: 80px;
          clear: both;
          position: relative;

          .el-form-item__error {
            top: 70%;
          }

          .module1 {
            background-color: #f1f5f8;
            width: 585px;
            height: 50px;
            display: inline-block;
            border-radius: 5px;
            margin: 10px 0;
          }

          &::after {
            clear: both;
          }

          .el-alert--info.is-light {
            width: auto;
            display: inline-block !important;
            margin: 5px;
            float: left;

            .el-icon-info {
              display: none;
            }

            .el-alert__content {
              padding-right: 15px;
            }
          }

          .el-form-item {
            border-bottom: none;
            width: 50%;
            padding: 5px 0;

            &:last-child {
              border-bottom: none;
            }

            label {
              width: 70px !important;
              color: #424242;
              font-size: 12px;
              text-align: right;
            }

            .el-form-item__content {
              margin-left: 70px !important;

              .el-input__inner {
                width: 200px;
              }
            }
          }

          .el-button {
            padding: 0 10px;
            float: right;
            margin: 20px;
          }
        }

        .upload {
          width: 100%;

          .aptitude {
            position: relative;

            .el-upload--picture-card {
              width: 26px;
              height: 16px;
              background: linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
              background: -moz-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
              background: -ms-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
              background: -webkit-linear-gradient(to right, #06a1ee 0, #4cc2fc 100%) !important;
              border: none;
              line-height: 16px;
              position: absolute;
              top: 10px;
              left: 0;
              display: inline-block !important;

              i {
                font-size: 12px;
                color: #fff;
              }
            }

            .el-upload-list--picture-card {
              padding-top: 30px;
              display: block;

              li {
                margin-top: 10px;
                float: left;
              }
            }
          }

          .el-upload {
            .el-button {
              width: 26px !important;
              height: 16px !important;
              border: none !important;
              padding: 0;
              line-height: 16px !important;

              i {
                color: #fff;
                font-size: 8px;
              }
            }
          }
        }

        .upload1 {
          .el-form-item__label {
            float: left;
          }

          .el-form-item__content {
            margin-left: 0 !important;
            clear: both;
            text-align: left;

            .el-upload {
              float: left;
            }

            .el-button {
              width: 90px !important;
              height: 25px !important;
              color: #0099e8;
              float: left;
              position: relative;
              border: 4px solid transparent;
              border-radius: 5px;
              background: linear-gradient(to right, #06a1ee, #4cc2fc);

              & > span {
                left: 0;
                top: 0;
                right: 0;
                bottom: 0;
                margin: auto;
                font-size: 12px;
                margin-right: 3px;
                position: absolute;
                line-height: 25px;
                z-index: 10;

                span {
                  font-size: 10px;
                  margin-right: 2px;
                }
              }

              &:hover {
                background: linear-gradient(to right, #06a1ee, #4cc2fc) !important;
              }

              &::before {
                position: absolute;
                top: 1px;
                bottom: 1px;
                left: 1px;
                right: 1px;
                background: linear-gradient(#fff, #fff);
                content: '';
                z-index: 0;
                border-radius: 4px;
              }
            }

            img {
              width: 160px;
              height: 90px;
              border: 2px solid #e6e6e6;
              border-radius: 5px;
            }
          }
        }

        div {
          p {
            color: #424242;
            font-size: 12px;
            margin: 15px 0;
            text-align: left;
          }

          .upload-demo {
            .el-upload {
              float: left;

              .el-button {
                width: 90px;
                height: 25px;
                color: #0099e8;
                border: 1px solid #0099e8;
                font-size: 12px;
                padding: 0;
                line-height: 12px;

                .icon-commit {
                  width: 12px;
                  height: 12px;
                  background-position: -106px -148px;
                  margin-right: 3px;
                }
              }
            }

            ul {
              clear: both;
              overflow: hidden;
              margin-left: -70px;

              li {
                width: 160px;
                height: 90px;
                background-color: #f1f5f8;
                text-align: left;
                padding: 0px;
                border: 2px solid #e6e6e6;
                float: left;
                margin: 0 20px 15px 0;
                border-radius: 5px;

                img {
                  margin: 0;
                  float: left;
                  width: 160px;
                  height: 90px;
                }

                .el-icon-close {
                  display: block;
                  width: 16px;
                  height: 16px;
                  opacity: 0.5;
                  background: #000;
                  color: #fff;
                  border-radius: 50%;
                  text-align: center;
                  line-height: 16px;
                  font-size: 14px;
                  z-index: 1000;
                }

                .el-upload-list__item-name,
                .el-upload-list__item-status-label {
                  display: none;
                }

                a {
                  color: #06a1ee;
                  padding: 12px 10px;
                }

                .el-progress {
                  top: 30px;
                }
              }
            }
          }
        }
      }
    }

    .dialog-footer {
      .el-button {
        width: 120px;
        height: 30px;
        line-height: 30px;
        padding: 0;
        font-size: 12px;
      }
    }
  }
}
.sample {
  display: inline-block;
  width: 160px;
  height: 90px;
  line-height: 80px;
  text-align: center;
  border: 1px solid #666;
  border-radius: 5px;
  font-size: 30px;
  color: #666;
  margin-right: 10px;
}
.sampleP {
  font-size: 14px!important;
  color: #666!important;
  width: 160px;
  text-align: center!important;
  margin: 0!important;
}
</style>
