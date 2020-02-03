package com.hd.common.util;

/**
 * 工具类
 *
 * @author JLF
 */
public final class Constant {


    /**
     * 隐藏公有构造方法
     */
    private Constant() {
    }

    /**
     * 默认密码
     */
    public static final String INIT_PASSWORD = "123456";
    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 共通常量 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */
    /**
     * 逻辑删除标识：启用
     */
    public static final String IS_DEL_FLG_ENABLE = "0";

    /**
     * 来源类型  0：合同
     */
    public static final String CONTRACT_SOURCE_TYPE = "0";

    /**
     * 是否可删除 :是
     */
    public static final String IS_CAN_DEL_FLG_ABLE = "1";

    /**
     * 是否可删除 :否
     */
    public static final String IS_CAN_DEL_FLG_ENABLE = "0";

    /**
     * 来源类型  1：普通任务
     */
    public static final String TASK_SOURCE_TYPE = "1";

    /**
     * 来源类型  2：投标任务
     */
    public static final String TENDER_TASK_SOURCE_TYPE = "2";

    /**
     * 来源类型  4：请款单
     */
    public static final String REQUEST_MENU_SOURCE_TYPE = "4";

    /**
     * 提审标识：默认(否)
     */
    public static final String IS_REVIEW_DEFAULT_VALUE = "0";

    /**
     * 逻辑删除标识：停用
     */
    public static final String IS_DEL_FLG_DISABLE = "1";

    /**
     * 逻辑是否提审:已提审
     */
    public static final String IS_REVIEW_FLAG_TRUE = "1";

    /**
     * 分页：默认页码
     */
    public static final String DEFAULT_PAGE_NUMBER = "1";

    /**
     * 分页：默认每页数据条数
     */
    public static final String DEFAULT_PAGE_SIZE = "12";

    /**
     * 分页查询得到的数据集合，含分页信息
     */
    public static final String PAGEED_DATA_KEY = "pagedData";

    /**
     * 分页查询得到的数据集合
     */
    public static final String DATA_LIST_KEY = "dataList";

    /**
     * 分页查询得到的数据条数
     */
    public static final String DATA_COUNT_KEY = "dataCount";


    /**
     * 查询得到的对象
     */
    public static final String BUTTON_TYPE_FLAG = "typeFlag";

    /**
     * 查询得到的对象
     */
    public static final String DATA_VO_KEY = "dataVO";

    /**
     * 查询得到的主键
     */
    public static final String DATA_PRIMARY_KEY = "primaryKey";

    /**
     * 查询得到的对象
     */
    public static final String DATA_VO_PARAM_KEY_LIST = "dataVOParamKeyList";

    /**
     * 查询得到的对象
     */
    public static final String DATA_VO_PARAM_KEY_MAP = "dataVOParamKeyMap";
    /**
     * 检索条件VO
     */
    public static final String CONDITION_VO_KEY = "conditionVO";

    /**
     * 逻辑异常堆栈打印条数
     */
    public static final int LOGIC_EXCEPTION_STACK_TRACE_COUNT = 5;

    /**
     * 登录认证Cookie key
     */
    public static final String AUTH_COOKIE_KEY = "authToken";

    /**
     * 请求中，登录用户信息的Key
     */
    public static final String REQ_LOGIN_INFO_KEY = "loginUserInfo";

    /**
     * 返回状态 key值
     */
    public static final String RETURN_STATUS_KEY = "status";

    /**
     * 返回消息 key值
     */
    public static final String RETURN_MESSAGE_KEY = "message";

    /**
     * 响应状态：失败
     */
    public static final String RESPONSE_STATUS_FAILURE = "failure";

    /**
     * 响应状态：成功
     */
    public static final String RESPONSE_STATUS_SUCCESS = "success";

    /**
     * 响应状态：登录认证失败
     */
    public static final String RESPONSE_STATUS_AUTH_ERROR = "authError";

    /**
     * 通用模块URL
     */
    public static final String COMMON_MODULE_URL = "/commonModule";

    /**
     * 根路径
     */
    public static final String ROOT_PATH = "/";

    /**
     * 文件分割符号
     */
    public static final String FILE_SEPARATE = "/";


    /**
     * 审批状态通用：通过
     */
    public static final String APPROVED = "1";

    /**
     * 审批状态通用：审批中
     */
    public static final String APPROVING = "2";

    /**
     * 审批状态通用：未通过
     */
    public static final String NOT_APPROVED = "0";

    /**
     * 数据状态:草稿
     */
    public static final String IS_DRAFT = "1";
    /**
     * 数据状态:正常
     */
    public static final String IS_NOT_DRAFT = "0";

    /**
     * 项目数据状态:已作废
     */
    public static final String IS_INVALID = "1";

    /**
     * 项目数据状态:未作废
     */
    public static final String IS_NOT_INVALID = "0";

    /**
     * 公众号的appId
     */
    public static final String APP_ID = "wx13b779704d9d9710";

    /**
     * 重定向路径
     */
    public static final String REDIRECT_URI = "http://manage.hantten.com/userLogin/toLogin";

    /**
     * secret
     */
    public static final String SECRET = "398ecd11e488be423edb3ae07afd3c7e";

    /**
     * app登录url
     */
    public static final String APP_LOGIN_URL = "http://manage.hantten.com/app/login";

    /**
     * 消息模板id
     */
    public static final String TEMPLATE_ID = "ORPFZVHvCiE6QxnHpwC3guQx18v0_7sU17-rBU2YW8U";


    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 项目参与人员 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 人员身份：负责人
     */
    public static final String LEADER = "负责人";

    /**
     * 人员身份：执行人
     */
    public static final String EXECUTOR = "执行人";
    /**
     * 人员身份：执行人标识
     */
    public static final String EXECUTOR_FLAG = "0";

    /**
     * 工作类型：合同拟定
     */
    public static final String CONTRACT_TYPE = "合同拟定";
    /**
     * 工作类型：合同拟定标识
     */
    public static final String CONTRACT_TYPE_FLAG = "0";

    /**
     * 工作类型：执行任务
     */
    public static final String TASK_TYPE = "执行任务";

    /**
     * 成果文件类型：自定义
     */
    public static final String TASK_TYPE_ZERO = "0";

    /**
     * 成果文件类型：模板
     */
    public static final String TASK_TYPE_ONE = "1";

    /**
     * 工作类型：执行任务标识
     */
    public static final String TASK_TYPE_FLAG = "1";

    /**
     * 工作类型：请款单
     */
    public static final String REQUEST_MENU_TYPE = "请款单";

    /**
     * 请款单默认模板类型(自定义模板)
     */
    public static final String REQUEST_MENU_FILE_TYPE = "0";

    /**
     * 工作类型：请款单标识
     */
    public static final String REQUEST_MENU_TYPE_FLAG = "4";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 项目参与人员 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */


    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 二级权限URL常量 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 二级权限通用URL:列表
     */
    public static final String COMMON_LEVEL_TWO_MODEL_LIST_URL = "/list";

    /**
     * 二级权限通用URL:新增
     */
    public static final String COMMON_LEVEL_TWO_MODEL_ADD_URL = "/add";

    /**
     * 二级权限通用URL:修改
     */
    public static final String COMMON_LEVEL_TWO_MODEL_MODIFY_URL = "/modify";

    /**
     * 二级权限通用URL:详情
     */
    public static final String COMMON_LEVEL_TWO_MODEL_DETAIL_URL = "/detail";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 二级权限URL常量 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 三级通用URL常量 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 三级权限通用URL:跳转列表页
     */
    public static final String COMMON_LEVEL_THREE_MODEL_TO_LIST_URL = "/toList";

    /**
     * 三级权限通用URL:查询列表数据
     */
    public static final String COMMON_LEVEL_THREE_MODEL_SEARCH_LIST_URL = "/searchList";

    /**
     * 三级权限通用URL:跳转新增页
     */
    public static final String COMMON_LEVEL_THREE_MODEL_TO_ADD_URL = "/toAdd";

    /**
     * 三级权限通用URL:新增数据
     */
    public static final String COMMON_LEVEL_THREE_MODEL_DO_ADD_URL = "/doAdd";

    /**
     * 三级权限通用URL:跳转修改页
     */
    public static final String COMMON_LEVEL_THREE_MODEL_TO_MODIFY_URL = "/toModify";

    /**
     * 三级权限通用URL:修改数据
     */
    public static final String COMMON_LEVEL_THREE_MODEL_DO_MODIFY_URL = "/doModify";

    /**
     * 三级权限通用URL:跳转详情页
     */
    public static final String COMMON_LEVEL_THREE_MODEL_TO_DETAIL_URL = "/toDetail";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 三级通用URL常量 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 共通常量 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */


    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 模块常量 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 登录 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 一级模块路径：登录
     */
    public static final String LEVEL_ONE_MODEL_LOGIN_URL = "/login";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 登录 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 用户管理 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 一级模块路径：用户
     */
    public static final String LEVEL_ONE_MODEL_USER_URL = "/user";

    /**
     * 一级模块路径：用户
     */
    public static final String USER_PHOTO_BASE_URL = "/customImages/userPhoto/";

    /**
     * 默认用户类型选项：请选择用户类型
     */
    public static final String DEFAULT_USER_TYPE_OPTION_VALUE = "请选择用户类型";

    /**
     * 重置密码(0:不重置 1:重置)
     */
    public static final String IS_RESET_PWD_ENABLE = "1";

    /**
     * 是否在职(0:否)
     */
    public static final String IS_INCUMBENT_DISABLE = "0";

    /**
     * 是否在职(1:是)
     */
    public static final String IS_INCUMBENT_ENABLE = "1";


    /**
     * 是否可以撤回（0：否 ）
     */
    public static final String IS_CAN_RECALL_DISABLE = "0";

    /**
     * 是否可以撤回（1：是）
     */
    public static final String IS_CAN_RECALL_ENABLE = "1";



    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 用户管理 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 项目 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 职位名称
     */
    public static final String JOB_NAME = "副总";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 项目 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 部门管理 START ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */
    /**
     * 部门ID：行政部
     */
    public static final String DEPT_ID_XZ = "d60c4000-9fbd-11e9-a036-0e411fd5cf91";

    /**
     * 部门ID：市场部
     */
    public static final String DEPT_ID_SC = "593df92d-fb21-442a-b279-a8b821bbc0d4";

    /**
     * 部门ID：造价部
     */
    public static final String DEPT_ID_ZJ = "db5e76f9-9fbd-11e9-a036-0e411fd5cf91";

    /**
     * 部门ID：招标代理部
     */
    public static final String DEPT_ID_ZBDL = "dea3cd5d-9fbd-11e9-a036-0e411fd5cf91";

    /**
     * 部门ID：项目监理部
     */
    public static final String DEPT_ID_XMJL = "eb1f8c91-960d-4717-84ee-cc3b8137ccd3";

    /**
     * 部门ID：质检部
     */
    public static final String DEPT_ID_QUALITY = "eb9bfd9a-7519-473e-8f08-502ae74cdb40";

    /**
     * 部门ID：高管部
     */
    public static final String DEPT_ID_GG = "e18edd98-9fbd-11e9-a036-0e411fd5cf91";

    /**
     * 部门ID：财务部
     */
    public static final String DEPT_ID_CW = "092fcf86-7eed-4ec3-8b37-5c7c0c156072";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 部门管理 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ element-tree常量 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */
    /**
     * 展示一类菜单
     */
    public static final String SHOW_FIRST_TYPE_MENU_FLAG = "1";
    /**
     * 展示二类菜单
     */
    public static final String SHOW_SECOND_TYPE_MENU_FLAG = "2";
    /**
     * 展示三类菜单
     */
    public static final String SHOW_THIRD_TYPE_MENU_FLAG = "3";

    /**
     * 展示请款单(类型/菜单)
     */
    public static final String SHOW_THIRD_TYPE_REQUEST_MENU_FLAG = "requestMent";

    /**
     * 展示合同类型
     */
    public static final String SHOW_SECOND_TYPE_CONTRACT_FLAG = "contract";

    /**
     * 展示项目基本情况表信息
     */
    public static final String SHOW_FOUR_TYPE_BASE_INFO_FLAG = "baseInfo";

    /**
     * 隐藏菜单
     */
    public static final String HIDDEN_MENU_FLAG = "0";

    /**
     * 二级菜单名称("合同")
     */
    public static final String TWO_LEVEL_MENU_NAME_ONE = "合同";

    /**
     * 四级菜单项目基本情况表名称
     */
    public static final String TWO_LEVEL_MENU_NAME_BASE_INFO = "项目基本情况表";

    /**
     * 四级菜单任务名称
     */
    public static final String FOUR_LEVEL_MENU_NAME_TASK = "任务";

    /**
     * 四级菜单请款单名称
     */
    public static final String FOUR_LEVEL_MENU_NAME_REQUEST_MENU = "请款单";

    /**
     * 一级菜单标识
     */
    public static final String FIRST_LEVEL_MENU_FLAG = "1";

    /**
     * 二级菜单标识
     */
    public static final String SECOND_LEVEL_MENU_FLAG = "2";

    /**
     * 三级菜单标识
     */
    public static final String THIRD_LEVEL_MENU_FLAG = "3";

    /**
     * 四级菜单标识
     */
    public static final String FOURTH_LEVEL_MENU_FLAG = "4";

    /**
     * 五级菜单标识
     */
    public static final String FIFTH_LEVEL_MENU_FLAG = "5";

    /**
     * 六级菜单标识
     */
    public static final String SIXTH_LEVEL_MENU_FLAG = "6";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ element-tree常量 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 合同常量 start ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 住宅
     */
    public static final String PROJECT_TYPE_ZERO = "0";
    /**
     * 商业建筑
     */
    public static final String PROJECT_TYPE_ONE = "1";
    /**
     * 酒店及休闲娱乐
     */
    public static final String PROJECT_TYPE_TWO = "2";
    /**
     * 工业建筑
     */
    public static final String PROJECT_TYPE_THREE = "3";
    /**
     * 教育
     */
    public static final String PROJECT_TYPE_FOUR = "4";
    /**
     * 文化及体育设施
     */
    public static final String PROJECT_TYPE_FIVE = "5";
    /**
     * 电力
     */
    public static final String PROJECT_TYPE_SIX = "6";
    /**
     * 铁路、道路及桥梁建设
     */
    public static final String PROJECT_TYPE_SEVEN = "7";
    /**
     * 水务
     */
    public static final String PROJECT_TYPE_EIGHT = "8";
    /**
     * 公用事业及城市基础设施
     */
    public static final String PROJECT_TYPE_NINE = "9";
    /**
     * 水运、交通
     */
    public static final String PROJECT_TYPE_TEN = "10";
    /**
     * 其他
     */
    public static final String PROJECT_TYPE_ELEVEN = "11";

    /**
     *  合同模板中文字 暂定
     */
    public static final String CONTRACT_TEMPLATE_PROVISIONAL_STR = "暂定";

    /**
     *  合同模板中 人民币 ￥
     */
    public static final String CONTRACT_TEMPLATE_RMB = "￥";

    /**
     *  造价咨询合同
     */
    public static final String CONTRACT_COST_CONSULTATION_VAL = "0";

    /**
     *  建设工程招标代理合同
     */
    public static final String CONTRACT_BIDDING_AGENT_VAL = "1";

    /**
     *  工程管理咨询服务合同
     */
    public static final String CONTRACT_CONSULTING_SERVICE_VAL = "2";

    /**
     *  监理合同
     */
    public static final String CONTRACT_SUPERVISOR_VAL = "3";

    /**
     *  合同类型   "0" :自定义合同
     */
    public static final String CONTRACT_TYPE_CUSTOMER = "0";

    /**
     *  合同类型   "1" :模板合同
     */
    public static final String CONTRACT_TYPE_FORMWORK = "1";



    /**
     * 查询类型:投标任务
     */
    public static final String SEARCH_TYPE_TENDER_TASK = "tenderTask";

    /**
     * 查询类型:合同
     */
    public static final String SEARCH_TYPE_CONTRACT = "contract";



    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 合同常量 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 角色权限 start ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */


    /**
     * 权限状态：拥有
     */
    public static final String POWER_ABLE = "1";

    /**
     * 权限所属：项目
     */
    public static final String POWER_MODULE_BELONG_PROJECT = "project";

    /**
     * 权限状态：合同
     */
    public static final String POWER_MODULE_BELONG_CONTRACT = "contract";

    /**
     * 权限所属：任务
     */
    public static final String POWER_MODULE_BELONG_TASK = "task";

    /**
     * 权限状态：投标任务
     */
    public static final String POWER_MODULE_BELONG_TENDER_TASK = "tenderTask";

    /**
     * 权限所属：请款单
     */
    public static final String POWER_MODULE_BELONG_REQUEST_MENU = "requestMenu";

    /**
     * 权限规则：编撰人及上级
     */
    public static final String POWER_ROLE_KEY_APPLY_USER_OR_BOSS = "applyUserOrBoss";

    /**
     * 权限规则：创建人及上级
     */
    public static final String POWER_ROLE_KEY_CREATE_USER_OR_BOSS = "creatUserOrBoss";

    /**
     * 权限规则：组长及上级
     */
    public static final String POWER_ROLE_KEY_GROUP_OR_BOSS = "groupOrBoss";

    /**
     * 权限规则：部门副总
     */
    public static final String POWER_ROLE_KEY_NMG = "NMG";

    /**
     * 权限参数：部门副总
     */
    public static final String POWER_ROLE_PARAM_NMG = "10";

    /**
     * 权限参数：组长
     */
    public static final String POWER_ROLE_PARAM_GROUP = "25";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 角色权限 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 工作台代办模块 start ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 待执行
     */
    public static final String WAIT_EXECUTE = "待执行";
    /**
     * 待审核
     */
    public static final String WAIT_AUDIT = "待审核";
    /**
     * 提交审核
     */
    public static final String SUBMITVERIFY = "SubmitVerify";


    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 工作台代办模块 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 投标文件类型 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */
    /**
     * 投标文件类型:普通阶段
     */
    public static final String TASK_PERIOD_TYPE_LEVEL_ZERO = "0";

    /**
     * 投标文件类型:收集资料
     */
    public static final String TASK_PERIOD_TYPE_LEVEL_ONE = "1";

    /**
     * 投标文件类型:A3审核阶段
     */
    public static final String TASK_PERIOD_TYPE_LEVEL_TWO = "2";

    /**
     * 投标文件类型:成果阶段
     */
    public static final String TASK_PERIOD_TYPE_LEVEL_THREE = "3";


    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 投标文件类型 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 投标任务常量 start ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 投标任务当前登录人:主提审人
     */
    public static final String TENDER_TASK_LOGIN_USER_TYPE = "1";
    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 投标任务常量 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 请款单常量 start ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 请款单当前登录人:主提审人
     */
    public static final String REQUEST_MENU_LOGIN_USER_TYPE = "1";
    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 投标任务常量 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 任务常量 start ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 普通阶段类型
     */
    public static final String GENERAL_PERIOD_TYPE = "0";

    /**
     * 收集资料阶段类型
     */
    public static final String GATHER_DATA_PERIOD_TYPE = "1";

    /**
     * A3阶段类型
     */
    public static final String A3_PERIOD_TYPE = "2";

    /**
     * 成果文件阶段类型
     */
    public static final String RESULT_FILE_PERIOD_TYPE = "3";

    /**
     * 现场人员调整阶段
     */
    public static final String PERSONNEL_ADJUSTMEN_PERIOD_TYPE = "4";

    /**
     * 归档阶段类型
     */
    public static final String ARCHIVE_PERIOD_TYPE = "5";

    /**
     * 专家论证意见
     */
    public static final String EXPERT_OPINION_PERIOD_TYPE = "6";

    /**
     * 招标文件确认意见
     */
    public static final String FILE_CONFIRM_OPINION_PERIOD_TYPE = "7";

    /**
     * 落标通知书
     */
    public static final String FAIL_TENDER_NOTICE_PERIOD_TYPE = "8";

    /**
     * 开标、评标
     */
    public static final String OPEN_SEALED_TENDERS_PERIOD_TYPE = "9";

    /**
     * 是否提交了重新编辑申请 0:否 1:是
     */
    public static final String IS_SUBMIT_REEDIT = "1";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 任务常量 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 全过程 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 全过程和合同列表约束咨询业务类型:造价咨询
     */
    public static final String WHOLE_PROCESS_PROPERTY_BUSINESS_TYPE = "0";
    /**
     * 全过程和合同列表约束咨询类型阶段:全过程
     */
    public static final String WHOLE_PROCESS_PROPERTY_TYPE_PERIOD = "3";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 全过程 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */



    /** ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ 全过程 START ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼ */

    /**
     * 待办列表批量通过提交数据业务类型:合同
     */
    public static final String BATCH_PASS_BUSINESS_TYPE_WRITE_CONTRACT = "0";
    /**
     * 待办列表批量通过提交数据业务类型:归档(包含成果文件)
     */
    public static final String BATCH_PASS_BUSINESS_TYPE_ARCHIVE = "1-1";
    /**
     * 待办列表批量通过提交数据业务类型:A3阶段
     */
    public static final String BATCH_PASS_BUSINESS_TYPE_TASK_A3 = "1-2";
    /**
     * 待办列表批量通过提交数据业务类型:重新编辑(包含重新编辑待签发)
     */
    public static final String BATCH_PASS_BUSINESS_TASK_REEDIT = "1-3";
    /**
     * 待办列表批量通过提交数据业务类型:投标任务
     */
    public static final String BATCH_PASS_BUSINESS_TYPE_TENDER = "2";
    /**
     * 待办列表批量通过提交数据业务类型:请款单
     */
    public static final String BATCH_PASS_BUSINESS_TYPE_CASH_APPLY = "3";

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 全过程 END ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */

    /** ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ 模块常量 End ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲ */


}
