/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.124_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.1.124:3306
 Source Schema         : hantten_test

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 12/07/2019 11:50:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_apply_seal_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_apply_seal_record`;
CREATE TABLE `tb_apply_seal_record`  (
  `record_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务Key',
  `process_inst_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例ID',
  `delegate_task_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '委托任务Key',
  `delegate_task_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '委托任务ID',
  `apply_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人ID',
  `apply_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请原因',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `apply_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人',
  `approve_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批人',
  `approve_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批人ID',
  `approve_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `complete_time` datetime(0) NULL DEFAULT NULL COMMENT '流程完成时间',
  `verify_state` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态\r\n0：未通过\r\n1：通过\r\n2：审批中',
  `reject_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `seal__type_id` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章类型',
  `is_paging_seal` int(1) NULL DEFAULT NULL COMMENT '是否骑缝章',
  `attachment_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `attachment_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件地址',
  `is_signatured` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否已盖章',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_biz_process
-- ----------------------------
DROP TABLE IF EXISTS `tb_biz_process`;
CREATE TABLE `tb_biz_process`  (
  `biz_process_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `process_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ACTIVITI流程ID',
  `process_xml` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程XML',
  `process_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `deploy_time` datetime(0) NULL DEFAULT NULL COMMENT '部署时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `process_namespace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程命名空间',
  `deploy_num` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部署编号',
  `bpmn_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程文件名称',
  `png_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片文件名',
  `png_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `is_hang_up` int(1) NULL DEFAULT NULL COMMENT '是否挂起',
  `process_file_blob` blob NULL COMMENT '流程文件blob',
  `process_eng_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程缩写',
  PRIMARY KEY (`biz_process_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_company
-- ----------------------------
DROP TABLE IF EXISTS `tb_company`;
CREATE TABLE `tb_company`  (
  `company_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司ID',
  `company_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `condense` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称缩写',
  `company_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司地址',
  `bank_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户行',
  `bank_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `fax_num` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真',
  `postcode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `charge_people` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `serial_num` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序号',
  `is_cooperate_company` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是合作公司（否：0，是：1）',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公司信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_contract
-- ----------------------------
DROP TABLE IF EXISTS `tb_contract`;
CREATE TABLE `tb_contract`  (
  `contract_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合同ID',
  `contract_num` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同编号',
  `verify_state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同状态(0.未通过 1.通过 2.审核中 3,已分配)',
  `income_received` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已收入',
  `contract_amount` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同金额',
  `duty_people` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `project_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目ID',
  `delivery_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分配人ID',
  `department_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID(根据部门ID可以查询签发人)',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `predict_end_date` datetime(0) NULL DEFAULT NULL COMMENT '预计结束时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`contract_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_dict_contract_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_contract_type`;
CREATE TABLE `tb_dict_contract_type`  (
  `contract_type_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contract_type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同类型字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_dict_counsel_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_counsel_type`;
CREATE TABLE `tb_dict_counsel_type`  (
  `counsel_type_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '咨询类别ID',
  `counsel_type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询类别名称',
  `contract_type_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同类型ID',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '咨询类别状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_dict_department
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_department`;
CREATE TABLE `tb_dict_department`  (
  `department_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门ID',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `abbr` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简写',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dict_department
-- ----------------------------
INSERT INTO `tb_dict_department` VALUES ('e1f39859-df17-48ff-b63a-25f400daa779', '造价部', 'QS');

-- ----------------------------
-- Table structure for tb_dict_process
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_process`;
CREATE TABLE `tb_dict_process`  (
  `process_dict_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程字典ID',
  `process_dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  PRIMARY KEY (`process_dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dict_process
-- ----------------------------
INSERT INTO `tb_dict_process` VALUES ('1', '请章');

-- ----------------------------
-- Table structure for tb_dict_seal
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict_seal`;
CREATE TABLE `tb_dict_seal`  (
  `seal__type_id` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '章类型key',
  `seal_type_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章类型名称',
  PRIMARY KEY (`seal__type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_dict_seal
-- ----------------------------
INSERT INTO `tb_dict_seal` VALUES ('1', '公章');
INSERT INTO `tb_dict_seal` VALUES ('2', '合同章');
INSERT INTO `tb_dict_seal` VALUES ('3', '财务章');
INSERT INTO `tb_dict_seal` VALUES ('4', '法人人名章');

-- ----------------------------
-- Table structure for tb_education
-- ----------------------------
DROP TABLE IF EXISTS `tb_education`;
CREATE TABLE `tb_education`  (
  `education_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `education_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`education_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文化程度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_education
-- ----------------------------
INSERT INTO `tb_education` VALUES ('1', '初中', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_education` VALUES ('2', '高中', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_education` VALUES ('3', '大专', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_education` VALUES ('4', '本科', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_education` VALUES ('5', '研究生', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_education` VALUES ('6', '博士', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_education` VALUES ('7', '博士后', NULL, NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for tb_job
-- ----------------------------
DROP TABLE IF EXISTS `tb_job`;
CREATE TABLE `tb_job`  (
  `job_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位ID',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  `involved_department` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门ID',
  `partent_job_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级职位ID',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  `job_level` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位级',
  `sort` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_job
-- ----------------------------
INSERT INTO `tb_job` VALUES ('4dd21c02-fe3f-470b-bea3-0dfdae4cadbd', '签发人', 'e1f39859-df17-48ff-b63a-25f400daa779', NULL, NULL, NULL, NULL, NULL, '0', NULL, '1');
INSERT INTO `tb_job` VALUES ('4f963610-5412-4c20-a443-5a834cee7fe6', '副总', 'e1f39859-df17-48ff-b63a-25f400daa779', '4dd21c02-fe3f-470b-bea3-0dfdae4cadbd', NULL, NULL, NULL, NULL, '0', NULL, '2');
INSERT INTO `tb_job` VALUES ('5ec1cae0-5370-4217-a17a-3c59238da58e', '经理', 'e1f39859-df17-48ff-b63a-25f400daa779', '4f963610-5412-4c20-a443-5a834cee7fe6', NULL, NULL, NULL, NULL, '0', NULL, '3');
INSERT INTO `tb_job` VALUES ('690a5563-8050-466a-86c6-4bad027008d0', '副经理', 'e1f39859-df17-48ff-b63a-25f400daa779', '5ec1cae0-5370-4217-a17a-3c59238da58e', NULL, NULL, NULL, NULL, '0', NULL, '4');
INSERT INTO `tb_job` VALUES ('ac70a4ae-94f5-43c7-ac0d-e6522e9d9686', '组员', 'e1f39859-df17-48ff-b63a-25f400daa779', 'b0613470-5bc6-4fb4-ae25-8dab95336a27', NULL, NULL, NULL, NULL, '0', NULL, '5');
INSERT INTO `tb_job` VALUES ('b0613470-5bc6-4fb4-ae25-8dab95336a27', '组长', 'e1f39859-df17-48ff-b63a-25f400daa779', '690a5563-8050-466a-86c6-4bad027008d0', NULL, NULL, NULL, NULL, '0', NULL, '6');

-- ----------------------------
-- Table structure for tb_job_processnode_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_job_processnode_relation`;
CREATE TABLE `tb_job_processnode_relation`  (
  `job_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `node_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点Key',
  `process_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `process_dict_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程字典ID',
  PRIMARY KEY (`node_id`, `department_id`, `job_id`, `process_dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职位和流程节点关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_job_processnode_relation
-- ----------------------------
INSERT INTO `tb_job_processnode_relation` VALUES ('4dd21c02-fe3f-470b-bea3-0dfdae4cadbd', 'e1f39859-df17-48ff-b63a-25f400daa779', 'AssignHandleVerify', '请章流程(指定办理人)', '总经理审批', '1');
INSERT INTO `tb_job_processnode_relation` VALUES ('5ec1cae0-5370-4217-a17a-3c59238da58e', 'e1f39859-df17-48ff-b63a-25f400daa779', 'DepartmentManagerVerify', '请章流程', '部门经理审批', '1');
INSERT INTO `tb_job_processnode_relation` VALUES ('690a5563-8050-466a-86c6-4bad027008d0', 'e1f39859-df17-48ff-b63a-25f400daa779', 'DepartmentManagerVerify', '请章流程', '部门经理审批', '1');
INSERT INTO `tb_job_processnode_relation` VALUES ('4dd21c02-fe3f-470b-bea3-0dfdae4cadbd', 'e1f39859-df17-48ff-b63a-25f400daa779', 'GeneralManagerVerify', '请章流程', '总经理审批', '1');

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `menu_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能清单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能清单名称',
  `menu_type` int(1) NULL DEFAULT NULL COMMENT '菜单类型：1业务请求菜单；2中间节点菜单',
  `menu_position` int(1) NULL DEFAULT NULL COMMENT '菜单位置：1上；2下；3左；4右',
  `menu_icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标URL',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URL',
  `order_num` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单顺序',
  `parent_menu_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级菜单ID',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '功能菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES ('menu_01', '工作台', 1, 1, '1', '1', '1', '', '2019-07-03 13:42:21', '1', '2019-07-03 13:42:14', '1', '0');
INSERT INTO `tb_menu` VALUES ('menu_02', '项目', 2, 2, '2', '2', '2', '', '2019-07-03 13:42:21', '2', '2019-07-03 13:42:14', '2', '0');
INSERT INTO `tb_menu` VALUES ('menu_03', '数据报表', 3, 3, '3', '3', '3', '', '2019-07-03 21:33:48', '3', '2019-07-03 21:33:52', '3', '0');
INSERT INTO `tb_menu` VALUES ('menu_04', '技术标准', 16, 16, '16', '16', '4', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_menu` VALUES ('menu_05', '公告', 4, 4, '4', '4', '5', '', '2019-07-03 21:34:11', '4', '2019-07-03 21:34:16', '4', '0');
INSERT INTO `tb_menu` VALUES ('menu_06', '消息中心', 5, 5, '5', '5', '6', '', '2019-07-03 21:34:39', '5', '2019-07-03 21:34:46', '5', '0');
INSERT INTO `tb_menu` VALUES ('menu_07', '邮箱', 6, 6, '6', '6', '7', NULL, '2019-07-03 21:34:39', '6', '2019-07-03 21:34:39', '6', '0');
INSERT INTO `tb_menu` VALUES ('menu_08', '设置', 7, 7, '7', '7', '8', '', '2019-07-03 21:34:39', '7', '2019-07-03 21:34:39', '7', '0');
INSERT INTO `tb_menu` VALUES ('menu_09', '公司管理', 8, 8, '8', '8', '9', 'menu_08', '2019-07-03 21:34:39', '8', '2019-07-03 21:34:39', '8', '0');
INSERT INTO `tb_menu` VALUES ('menu_10', '合作公司管理', 9, 9, '9', '9', '10', 'menu_08', '2019-07-03 21:34:39', '9', '2019-07-03 21:34:39', '9', '0');
INSERT INTO `tb_menu` VALUES ('menu_11', '成员管理', 10, 10, '10', '10', '11', 'menu_08', '2019-07-03 21:34:39', '10', '2019-07-03 21:34:39', '10', '0');
INSERT INTO `tb_menu` VALUES ('menu_12', '部门管理', 11, 11, '11', '11', '12', 'menu_08', '2019-07-03 21:34:39', '11', '2019-07-03 21:34:39', '11', '0');
INSERT INTO `tb_menu` VALUES ('menu_13', '角色管理', 12, 12, '12', '12', '13', 'menu_08', '2019-07-03 21:34:39', '12', '2019-07-03 21:34:39', '12', '0');
INSERT INTO `tb_menu` VALUES ('menu_14', '公告管理', 13, 13, '13', '13', '14', 'menu_08', '2019-07-03 21:34:39', '13', '2019-07-03 21:34:39', '13', '0');
INSERT INTO `tb_menu` VALUES ('menu_15', '技术标准管理', 14, 14, '14', '14', '15', 'menu_08', '2019-07-03 21:34:39', '14', '2019-07-03 21:34:39', '14', '1');
INSERT INTO `tb_menu` VALUES ('menu_16', '工作内容说明', 15, 15, '15', '15', '16', 'menu_08', '2019-07-03 21:34:39', '15', '2019-07-03 21:34:39', '15', '0');

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message`  (
  `message_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '信息ID',
  `message_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `accept_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收消息人',
  `is_read` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否阅读(0:否,1:是)',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_professional
-- ----------------------------
DROP TABLE IF EXISTS `tb_professional`;
CREATE TABLE `tb_professional`  (
  `professional_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业ID',
  `professional_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业名称',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`professional_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '专业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_professional
-- ----------------------------
INSERT INTO `tb_professional` VALUES ('1', '工程管理', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_professional` VALUES ('2', '工程造价', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_professional` VALUES ('3', '土木工程', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_professional` VALUES ('4', '测绘工程', NULL, NULL, NULL, NULL, '0');
INSERT INTO `tb_professional` VALUES ('5', '勘测技术', NULL, NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for tb_project
-- ----------------------------
DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE `tb_project`  (
  `project_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `project_num` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL,
  `close_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关闭备注',
  `project_sts` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目状态(0.未完成 1.已完成(项目关闭也视为已完成))',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_public_notice
-- ----------------------------
DROP TABLE IF EXISTS `tb_public_notice`;
CREATE TABLE `tb_public_notice`  (
  `notice_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告ID',
  `notice_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告名称',
  `release_people` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布人',
  `release_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `material_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `material_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件路径',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'super', '2019-07-11 21:06:51', NULL, '2019-07-11 21:06:51', NULL, '0');

-- ----------------------------
-- Table structure for tb_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu_relation`;
CREATE TABLE `tb_role_menu_relation`  (
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单ID',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和功能菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu_relation
-- ----------------------------
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_01', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_02', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_03', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_04', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_05', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_06', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_07', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_08', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_09', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_10', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_11', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_12', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_13', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_14', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_15', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_role_menu_relation` VALUES ('c9d6bcc8-a3dc-11e9-907d-7085c2837efb', 'menu_16', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');

-- ----------------------------
-- Table structure for tb_task_period
-- ----------------------------
DROP TABLE IF EXISTS `tb_task_period`;
CREATE TABLE `tb_task_period`  (
  `period_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '阶段ID',
  `parent_period_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级阶段ID',
  `period_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段名称',
  `contract_type_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同类型',
  `counsel_type_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询类型',
  `dict_department_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门类型ID',
  `period_level` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '级别',
  `period_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作内容说明',
  `period_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段类型（0：普通阶段 1：收集资料 2：A3审核阶段  3：成果阶段）',
  `sort` int(2) NULL DEFAULT NULL COMMENT '阶段排序',
  `page_content` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面展示内容(A1,A2,A3...A12)',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`period_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务阶段表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of tb_task_period
-- ----------------------------
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf01', '', '收集资料', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '1', '1', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf02', '', '拟定工作计划', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '2', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf03', '', '踏勘现场', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '3', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf04', '', '往来函件', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '4', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf05', '', '计量', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '5', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf06', '', '计价', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '6', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf07', '', '核对', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '7', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf08', '', '完善工作底稿', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '8', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf09', '', '协调会', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '9', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf10', '', '修改完善', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '10', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf11', '', '定案', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '11', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf12', '', '编制成果文件', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '3', '12', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf13', '', '存档', '', '', 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '13', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf14', '', '收集资料', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '1', '14', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf15', '', '拟定工作计划', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '15', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf16', '', '拟定招标规划', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '16', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf17', '', '招标工作进度计划', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '17', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf18', '', '往来函件', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '18', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf19', '', '招标公告/ 投标邀请书', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '19', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf20', '', '变更公告/撤销公告', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '0', '20', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf21', '', '资格预审文件', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '21', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf22', '', '资格预审文件补充文件(澄清、修改)', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '22', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf23', '', '评审专家抽取', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '23', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf24', '', '资格预审结果确认', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '24', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf25', '', '招标文件', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '25', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf26', '', '招标文件补充文件(澄清、修改)', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '26', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf27', '', '投标保证金', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '27', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf28', '', '踏勘现场', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '28', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf29', '', '评标专家抽取', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '29', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf30', '', '预约标室', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '30', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf31', '', '开标', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '31', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf32', '', '评标', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '32', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf33', '', '中标候选人公示', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '33', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf34', '', '中标结果公示', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '34', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf35', '', '中标通知书发放', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '35', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf36', '', '废标公告', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '36', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf37', '', '招标代理工作报告', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '37', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf38', '', '招标代理服务费申请', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '38', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf39', '', '其他', '', '', 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91', '1', '', '2', '39', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf40', '', '监理开工令', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '40', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf41', '', '监理规划', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '41', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf42', '', '监理实施细则', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '42', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf43', '', '监理会议纪要', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '43', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf44', '', '监理通知', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '44', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf45', '', '工作联系单', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '45', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf46', '', '监理月报', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '46', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf47', '', '质量评估报告', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '47', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf48', '', '监理总结', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '48', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf49', '', '监理日记', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '49', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf50', '', '监理暂停工令', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '50', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf51', '', '监理复工令', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '51', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf52', '', '开工报审', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '52', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf53', '', '施工组织设计', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '53', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf54', '', '施工方案', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '54', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf55', '', '施工日志', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '55', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf56', '', '工程开工报审', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '56', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf57', '', '工程复工报审', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '57', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf58', '', '工程款支付报审', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '58', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf59', '', '监理通知回复', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '59', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf60', '', '图纸会审记录', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '60', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf61', '', '设计变更通知单', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '61', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf62', '', '工程变更洽商记录', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '62', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf63', '', '分项工程质量验收记录', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '63', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf64', '', '分部工程质量验收记录', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '64', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf65', '', '单位工程质量竣工验收记录', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '65', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf66', '', '施工日记', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '66', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf67', '', '施工照片', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '67', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf68', '', '其他', '', '', 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3', '1', '', '0', '68', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf69', '', '招标公告', '', '', '593df92d-fb21-442a-b279-a8b821bbc0d4', '1', '', '0', '69', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf70', '', '招标文件', '', '', '593df92d-fb21-442a-b279-a8b821bbc0d4', '1', '', '0', '70', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf71', '', '编制投标文件', '', '', '593df92d-fb21-442a-b279-a8b821bbc0d4', '1', '', '2', '71', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf72', '', '投标', '', '', '593df92d-fb21-442a-b279-a8b821bbc0d4', '1', '', '0', '72', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf73', '', '开标', '', '', '593df92d-fb21-442a-b279-a8b821bbc0d4', '1', '', '0', '73', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');
INSERT INTO `tb_task_period` VALUES ('033aec1d-9e6e-11e9-a036-0e411fd5cf74', '', '定标', '', '', '593df92d-fb21-442a-b279-a8b821bbc0d4', '1', '', '0', '74', '', '2019-07-11 21:06:51', '', '2019-07-11 21:06:51', '', '0');

-- ----------------------------
-- Table structure for tb_technical_standard
-- ----------------------------
DROP TABLE IF EXISTS `tb_technical_standard`;
CREATE TABLE `tb_technical_standard`  (
  `standard_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '技术标准ID',
  `standard_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '技术标准名称',
  `release_people` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布人',
  `release_date` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `material_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `material_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件路径',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`standard_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '技术标准表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_tender_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_tender_task`;
CREATE TABLE `tb_tender_task`  (
  `tender_task_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '投标任务ID',
  `sort_num` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序号(系统生成，永远是当前列表中最大的序号+1)',
  `tender_task_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投标任务名称',
  `tender_task_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投标任务概况',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开标时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`tender_task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '投标任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_tender_task__period_material
-- ----------------------------
DROP TABLE IF EXISTS `tb_tender_task__period_material`;
CREATE TABLE `tb_tender_task__period_material`  (
  `relation_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tender_task_period_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投标任务和阶段的关联表',
  `material_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `material_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件实际名称',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '投标任务阶段和资料的关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_tender_task_period
-- ----------------------------
DROP TABLE IF EXISTS `tb_tender_task_period`;
CREATE TABLE `tb_tender_task_period`  (
  `relation_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tender_task_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `period_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '投标任务和阶段关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_tender_task_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_tender_task_user`;
CREATE TABLE `tb_tender_task_user`  (
  `relation_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `tender_task_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投标任务ID',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人ID',
  `is_main_duty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是主负责人ID(0：否  1：是)',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '投标任务和执行人关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `education` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `company_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属公司ID',
  `entry_date` datetime(0) NULL DEFAULT NULL COMMENT '入职日期',
  `is_incumbent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否在职   1:在职/0:离职',
  `sign_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名路径',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('b48f5d02-1f6b-466a-ac8a-96dff2ea7582', 'admin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for tb_user_job_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_job_relation`;
CREATE TABLE `tb_user_job_relation`  (
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`, `job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员职位关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_professional
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_professional`;
CREATE TABLE `tb_user_professional`  (
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `professional_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`user_id`, `professional_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人和专业关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_resource_relation`;
CREATE TABLE `tb_user_resource_relation`  (
  `relation_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_file_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资质文件路径',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和资质文件路径关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `is_del_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('b48f5d02-1f6b-466a-ac8a-96dff2ea7582', 'c9d6bcc8-a3dc-11e9-907d-7085c2837efb', NULL, NULL, NULL, NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
