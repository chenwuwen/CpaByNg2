/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : cpa

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 21/11/2019 17:15:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer_record
-- ----------------------------
DROP TABLE IF EXISTS `answer_record`;
CREATE TABLE `answer_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `petname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `item_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试题类型',
  `score` int(11) NULL DEFAULT NULL COMMENT '得分',
  `totalcount` int(11) NULL DEFAULT NULL COMMENT '总题数',
  `correctcount` int(11) NULL DEFAULT NULL COMMENT '答对数',
  `errorcount` int(11) NULL DEFAULT NULL COMMENT '答错数',
  `answer_date` datetime(0) NULL DEFAULT NULL COMMENT '答题日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cpa_option
-- ----------------------------
DROP TABLE IF EXISTS `cpa_option`;
CREATE TABLE `cpa_option`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `re_id` bigint(20) NULL DEFAULT NULL COMMENT '试题ID',
  `select_data` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选择内容即ABCD',
  `option_data` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK31o5kexwcgr9so8n5iikw91y5`(`re_id`) USING BTREE,
  CONSTRAINT `FK31o5kexwcgr9so8n5iikw91y5` FOREIGN KEY (`re_id`) REFERENCES `cpa_repertory` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_option
-- ----------------------------
INSERT INTO `cpa_option` VALUES (1, 1, 'A', '会计主体 ');
INSERT INTO `cpa_option` VALUES (2, 1, 'B', '持续经营');
INSERT INTO `cpa_option` VALUES (3, 1, 'C', '权责发生制原则');
INSERT INTO `cpa_option` VALUES (4, 1, 'D', '货币计量');
INSERT INTO `cpa_option` VALUES (5, 2, 'A', '成本计算与复式记账');
INSERT INTO `cpa_option` VALUES (6, 2, 'B', '评估预测');
INSERT INTO `cpa_option` VALUES (7, 2, 'C', '设置账户与填制、审核会计凭证');
INSERT INTO `cpa_option` VALUES (8, 2, 'D', '编制报表与登记账簿');
INSERT INTO `cpa_option` VALUES (9, 3, 'A', '80万元 ');
INSERT INTO `cpa_option` VALUES (10, 3, 'B', '90万元');
INSERT INTO `cpa_option` VALUES (11, 3, 'C', '100万元 ');
INSERT INTO `cpa_option` VALUES (12, 3, 'D', '70万元');
INSERT INTO `cpa_option` VALUES (13, 4, 'A', '31 000');
INSERT INTO `cpa_option` VALUES (14, 4, 'B', '39 000');
INSERT INTO `cpa_option` VALUES (15, 4, 'C', '50 000');
INSERT INTO `cpa_option` VALUES (16, 4, 'D', '45 000');
INSERT INTO `cpa_option` VALUES (17, 5, 'A', '资产＝负债＋所有者权益');
INSERT INTO `cpa_option` VALUES (18, 5, 'B', '资产＝权益');
INSERT INTO `cpa_option` VALUES (19, 5, 'C', '收入－费用＝利润');
INSERT INTO `cpa_option` VALUES (20, 5, 'D', '资产＋收入＝负债＋所有者权益＋费用');
INSERT INTO `cpa_option` VALUES (21, 6, 'A', '借方65万元 ');
INSERT INTO `cpa_option` VALUES (22, 6, 'B', '贷方65万元');
INSERT INTO `cpa_option` VALUES (23, 6, 'C', '借方15万元');
INSERT INTO `cpa_option` VALUES (24, 6, 'D', '贷方15 万元');
INSERT INTO `cpa_option` VALUES (25, 7, 'A', '费用的增加');
INSERT INTO `cpa_option` VALUES (26, 7, 'B', '收入的增加');
INSERT INTO `cpa_option` VALUES (27, 7, 'C', '费用的减少');
INSERT INTO `cpa_option` VALUES (28, 7, 'D', '所有者权益的增加');
INSERT INTO `cpa_option` VALUES (29, 8, 'A', '本月借方发生额为5000元');
INSERT INTO `cpa_option` VALUES (30, 8, 'B', '本月贷方发生额为5000元');
INSERT INTO `cpa_option` VALUES (31, 8, 'C', '月末借方余额为5000元');
INSERT INTO `cpa_option` VALUES (32, 8, 'D', '月末贷方余额为红字5000元');
INSERT INTO `cpa_option` VALUES (33, 9, 'A', '借方');
INSERT INTO `cpa_option` VALUES (34, 9, 'B', '贷方');
INSERT INTO `cpa_option` VALUES (35, 9, 'C', '借方或贷方');
INSERT INTO `cpa_option` VALUES (36, 9, 'D', '无余额');
INSERT INTO `cpa_option` VALUES (37, 10, 'A', '详细反映经济业务的发生情况');
INSERT INTO `cpa_option` VALUES (38, 10, 'B', '可以做到试算平衡');
INSERT INTO `cpa_option` VALUES (39, 10, 'C', '便于了解账户之间的对应关系');
INSERT INTO `cpa_option` VALUES (40, 10, 'D', '处理手续简便');
INSERT INTO `cpa_option` VALUES (41, 11, 'A', '现金收款凭证');
INSERT INTO `cpa_option` VALUES (42, 11, 'B', '银行存款收款凭证');
INSERT INTO `cpa_option` VALUES (43, 11, 'C', '现金付款凭证');
INSERT INTO `cpa_option` VALUES (44, 11, 'D', '银行存款付款凭证');
INSERT INTO `cpa_option` VALUES (45, 12, 'A', '将有余额的账户的余额直接记入新账余额栏内');
INSERT INTO `cpa_option` VALUES (46, 12, 'B', '编制记账凭证，将余额结转下年');
INSERT INTO `cpa_option` VALUES (47, 12, 'C', '将余额记入本年账户的借或贷方，将本年有余额的账户的余额为零');
INSERT INTO `cpa_option` VALUES (48, 12, 'D', '将其余额转入应收或应付明细分类账中，作为往来账挂账');
INSERT INTO `cpa_option` VALUES (49, 13, 'A', '经济利益的总流入');
INSERT INTO `cpa_option` VALUES (50, 13, 'B', '现金流入');
INSERT INTO `cpa_option` VALUES (51, 13, 'C', '其他业务收入');
INSERT INTO `cpa_option` VALUES (52, 13, 'D', '主营业务收入');
INSERT INTO `cpa_option` VALUES (53, 14, 'A', '收入、费用和利润');
INSERT INTO `cpa_option` VALUES (54, 14, 'B', '资产、负债和所有者权益');
INSERT INTO `cpa_option` VALUES (55, 14, 'C', '收入、资产和负债 ');
INSERT INTO `cpa_option` VALUES (56, 14, 'D', '资产、负债和利润');
INSERT INTO `cpa_option` VALUES (57, 15, 'A', '180 000  ');
INSERT INTO `cpa_option` VALUES (58, 15, 'B', '200 000 ');
INSERT INTO `cpa_option` VALUES (59, 15, 'C', '240 000 ');
INSERT INTO `cpa_option` VALUES (60, 15, 'D', '260 000  ');
INSERT INTO `cpa_option` VALUES (61, 16, 'A', '从银行提取现金');
INSERT INTO `cpa_option` VALUES (62, 16, 'B', '赊购原材料');
INSERT INTO `cpa_option` VALUES (63, 16, 'C', '用银行存款归还企业的银行短期借款   ');
INSERT INTO `cpa_option` VALUES (64, 16, 'D', '接受投资者投入的现金资产');
INSERT INTO `cpa_option` VALUES (65, 17, 'A', '一致  ');
INSERT INTO `cpa_option` VALUES (66, 17, 'B', '相反');
INSERT INTO `cpa_option` VALUES (67, 17, 'C', '基本相同 ');
INSERT INTO `cpa_option` VALUES (68, 17, 'D', '无关');
INSERT INTO `cpa_option` VALUES (69, 18, 'A', '记录资产和权益的增减变动情况不同  ');
INSERT INTO `cpa_option` VALUES (70, 18, 'B', '记录资产和负债的结果不同');
INSERT INTO `cpa_option` VALUES (71, 18, 'C', '反映交易或事项的内容不同 ');
INSERT INTO `cpa_option` VALUES (72, 18, 'D', '账户有结构而会计科目无结构');
INSERT INTO `cpa_option` VALUES (73, 19, 'A', '现金收款凭证  ');
INSERT INTO `cpa_option` VALUES (74, 19, 'B', '银行存款收款凭证');
INSERT INTO `cpa_option` VALUES (75, 19, 'C', '现金付款凭证 ');
INSERT INTO `cpa_option` VALUES (76, 19, 'D', '银行存款付款凭证');
INSERT INTO `cpa_option` VALUES (77, 20, 'A', '三栏式  ');
INSERT INTO `cpa_option` VALUES (78, 20, 'B', '数量金额式');
INSERT INTO `cpa_option` VALUES (79, 20, 'C', '多栏式  ');
INSERT INTO `cpa_option` VALUES (80, 20, 'D', '卡片式');
INSERT INTO `cpa_option` VALUES (81, 21, 'A', '记账凭证上会计科目或记账方向错误，导致账簿记录错误');
INSERT INTO `cpa_option` VALUES (82, 21, 'B', '记账凭证正确，在记账时发生错误，导致账簿记录错误');
INSERT INTO `cpa_option` VALUES (83, 21, 'C', '记账凭证上会计科目或记账方向正确，所记金额大于应记金额，导致账簿记录错误  ');
INSERT INTO `cpa_option` VALUES (84, 21, 'D', '记账凭证上会计科目或记账方向正确，所记金额小于应记金额，导致账簿记录错误');
INSERT INTO `cpa_option` VALUES (85, 22, 'A', '资产');
INSERT INTO `cpa_option` VALUES (86, 22, 'B', '负债');
INSERT INTO `cpa_option` VALUES (87, 22, 'C', '成本');
INSERT INTO `cpa_option` VALUES (88, 22, 'D', '损益');
INSERT INTO `cpa_option` VALUES (89, 23, 'A', '借、贷、余三栏式');
INSERT INTO `cpa_option` VALUES (90, 23, 'B', '数量金额式');
INSERT INTO `cpa_option` VALUES (91, 23, 'C', '多栏式 ');
INSERT INTO `cpa_option` VALUES (92, 23, 'D', '任意一种明细账形式');
INSERT INTO `cpa_option` VALUES (93, 24, 'A', '实地盘点');
INSERT INTO `cpa_option` VALUES (94, 24, 'B', '抽查检验');
INSERT INTO `cpa_option` VALUES (95, 24, 'C', '技术推算盘点  ');
INSERT INTO `cpa_option` VALUES (96, 24, 'D', '询证核对');
INSERT INTO `cpa_option` VALUES (97, 25, 'A', '非正常消耗的直接材料、直接人工和制造费用');
INSERT INTO `cpa_option` VALUES (98, 25, 'B', '仓储费用');
INSERT INTO `cpa_option` VALUES (99, 25, 'C', '购买存货的相关税费  ');
INSERT INTO `cpa_option` VALUES (100, 25, 'D', '不能归属于使存货达到目前场所和状态的其他支出');
INSERT INTO `cpa_option` VALUES (101, 26, 'A', '总账会计 ');
INSERT INTO `cpa_option` VALUES (102, 26, 'B', '业务经办单位或人员');
INSERT INTO `cpa_option` VALUES (103, 26, 'C', '会计主管    ');
INSERT INTO `cpa_option` VALUES (104, 26, 'D', '出纳人员');
INSERT INTO `cpa_option` VALUES (105, 27, 'A', '详细反映经济业务的发生情况 ');
INSERT INTO `cpa_option` VALUES (106, 27, 'B', '可以做到试算平衡');
INSERT INTO `cpa_option` VALUES (107, 27, 'C', '便于了解账户之间的对应关系 ');
INSERT INTO `cpa_option` VALUES (108, 27, 'D', '处理手续简便');
INSERT INTO `cpa_option` VALUES (109, 28, 'A', '交易性金融资产 ');
INSERT INTO `cpa_option` VALUES (110, 28, 'B', '投资收益');
INSERT INTO `cpa_option` VALUES (111, 28, 'C', '主营业务收入');
INSERT INTO `cpa_option` VALUES (112, 28, 'D', '营业外支出');
INSERT INTO `cpa_option` VALUES (113, 29, 'A', '财务会计部门  ');
INSERT INTO `cpa_option` VALUES (114, 29, 'B', '档案部门');
INSERT INTO `cpa_option` VALUES (115, 29, 'C', '人事部门');
INSERT INTO `cpa_option` VALUES (116, 29, 'D', '指定专人');
INSERT INTO `cpa_option` VALUES (117, 30, 'A', '费用的增加 ');
INSERT INTO `cpa_option` VALUES (118, 30, 'B', '收入的增加');
INSERT INTO `cpa_option` VALUES (119, 30, 'C', '费用的减少 ');
INSERT INTO `cpa_option` VALUES (120, 30, 'D', '所有者权益的增加');
INSERT INTO `cpa_option` VALUES (121, 31, 'A', '付款人名称 无忧考网：https://www.51test.net/show/8684757.html');
INSERT INTO `cpa_option` VALUES (122, 31, 'B', '收款人名称 无忧考网：https://www.51test.net/show/8684757.html');
INSERT INTO `cpa_option` VALUES (123, 31, 'C', '出票日期 无忧考网：https://www.51test.net/show/8684757.html');
INSERT INTO `cpa_option` VALUES (124, 31, 'D', '支票的金额 无忧考网：https://www.51test.net/show/8684757.html');
INSERT INTO `cpa_option` VALUES (125, 32, 'D', '提供修理劳务的汽车行 无忧考网：https://www.51test.net/show/8684761.html');
INSERT INTO `cpa_option` VALUES (126, 32, 'A', '进口货物的企业 无忧考网：https://www.51test.net/show/8684761.html');
INSERT INTO `cpa_option` VALUES (127, 32, 'B', '提供娱乐服务的游戏厅 无忧考网：https://www.51test.net/show/8684761.html');
INSERT INTO `cpa_option` VALUES (128, 32, 'C', '批发货物的超市 无忧考网：https://www.51test.net/show/8684761.html');

-- ----------------------------
-- Table structure for cpa_permission
-- ----------------------------
DROP TABLE IF EXISTS `cpa_permission`;
CREATE TABLE `cpa_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_permission
-- ----------------------------
INSERT INTO `cpa_permission` VALUES (1, 'select', NULL);
INSERT INTO `cpa_permission` VALUES (2, 'delete', NULL);
INSERT INTO `cpa_permission` VALUES (3, 'update', NULL);
INSERT INTO `cpa_permission` VALUES (4, 'insert', NULL);

-- ----------------------------
-- Table structure for cpa_repertory
-- ----------------------------
DROP TABLE IF EXISTS `cpa_repertory`;
CREATE TABLE `cpa_repertory`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TEST_STEM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题干',
  `TEST_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试题类型',
  `QUESTION_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题的类型(单选多选)',
  `INSERT_DATE` datetime(0) NULL DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_repertory
-- ----------------------------
INSERT INTO `cpa_repertory` VALUES (1, '会计分期是建立的在（）基础上的。', 'BASIC_ACCOUNT', 'SINGLE', '2018-01-01 14:13:46');
INSERT INTO `cpa_repertory` VALUES (2, '下列不属于会计核算专门方法的是（）', 'BASIC_ACCOUNT', 'SINGLE', '2017-12-01 14:14:01');
INSERT INTO `cpa_repertory` VALUES (3, '某企业资产总额为100万元，现发生一笔以银行存款10万元偿还银行借款的经济业务，此时，该企业的权益总额为（）。', 'BASIC_ACCOUNT', 'SINGLE', '2018-02-22 11:14:07');
INSERT INTO `cpa_repertory` VALUES (4, '某企业9月份的资产总额为50 000元，负债总额为20 000元。9月份发生如下业务：9月份取得收入共计24 000元，发生费用共计15 000元，则9月份该企业的所有者权益总额为（）元。', 'BASIC_ACCOUNT', 'SINGLE', '2018-01-16 14:14:14');
INSERT INTO `cpa_repertory` VALUES (5, '下列等式不正确的是（）。', 'BASIC_ACCOUNT', 'SINGLE', '2018-02-22 14:14:20');
INSERT INTO `cpa_repertory` VALUES (6, '某企业月初有短期借款40万元，本月向银行借入短期借款45万元，以银行存款偿还短期借款20万元，则月末“短期借款”账户的余额为(     )。', 'BASIC_ACCOUNT', 'SINGLE', '2018-02-19 14:14:27');
INSERT INTO `cpa_repertory` VALUES (7, '根据借贷记账法的账户结构，在账户借方登记的是(     )', 'BASIC_ACCOUNT', 'SINGLE', '2018-02-12 12:14:32');
INSERT INTO `cpa_repertory` VALUES (8, '某企业月末编制的试算平衡表中，全部账户的本月借方发生额合计为900000元，除“应付账款”以外其他账户的本月贷方发生额合计为895000元，则应付账款账户(     )', 'BASIC_ACCOUNT', 'SINGLE', '2018-02-05 14:14:39');
INSERT INTO `cpa_repertory` VALUES (9, '“本年利润”账户的期末余额一般在(     )', 'BASIC_ACCOUNT', 'SINGLE', '2017-10-24 14:14:49');
INSERT INTO `cpa_repertory` VALUES (10, '汇总记账凭证账务处理程序的优点是(     )', 'BASIC_ACCOUNT', 'SINGLE', '2017-07-20 14:14:58');
INSERT INTO `cpa_repertory` VALUES (11, '将现金存入银行，按规定应编制(     )。', 'CPU_ACCOUNT', 'SINGLE', '2018-01-18 14:15:07');
INSERT INTO `cpa_repertory` VALUES (12, '年度终了结账时，有余额的账户，要将其余额结转下年，结转的方法是(     )', 'CPU_ACCOUNT', 'SINGLE', '2018-05-30 14:15:12');
INSERT INTO `cpa_repertory` VALUES (13, '收入是指企业在销售商品、提供劳务及让渡资产所有权等日常活动中所形成的（    ）', 'CPU_ACCOUNT', 'SINGLE', '2017-09-26 14:15:20');
INSERT INTO `cpa_repertory` VALUES (14, '反映企业经营成果的会计要素是（   ）', 'CPU_ACCOUNT', 'SINGLE', '2017-02-21 14:15:26');
INSERT INTO `cpa_repertory` VALUES (15, '某公司某会计期间期初资产总额为200 000元，当期期末负债总额比期初减少20 000元，期末所有者权益比期初增加60 000元。则该企业期末资产总额为(    )元', 'CPU_ACCOUNT', 'SINGLE', '2017-04-25 14:15:35');
INSERT INTO `cpa_repertory` VALUES (16, '下列经济业务中，引起资产类项目和负债类项目同时减少的是(   )', 'CPU_ACCOUNT', 'SINGLE', '2018-02-03 14:15:44');
INSERT INTO `cpa_repertory` VALUES (17, '资产类账户的结构与权益类账户的结构(     )', 'CPU_ACCOUNT', 'SINGLE', '2018-02-01 14:15:51');
INSERT INTO `cpa_repertory` VALUES (18, '会计科目和账户之间的显著区别是(     )。', 'CPU_ACCOUNT', 'SINGLE', '2017-10-10 14:15:54');
INSERT INTO `cpa_repertory` VALUES (19, '将现金存入银行，按规定应编制(     )。', 'CPU_ACCOUNT', 'SINGLE', '2018-02-17 14:15:54');
INSERT INTO `cpa_repertory` VALUES (20, '固定资产明细账的外表形式一般采用(     )', 'CPU_ACCOUNT', 'SINGLE', '2017-11-22 14:15:54');
INSERT INTO `cpa_repertory` VALUES (21, '更正错账时，划线更正法的适用范围是(     )', 'STATUTE_ETHICS', 'SINGLE', '2017-12-12 14:15:54');
INSERT INTO `cpa_repertory` VALUES (22, '“长期待摊费用”账户按照经济内容分类，应属于(     )类账户。', 'STATUTE_ETHICS', 'SINGLE', '2018-02-07 14:15:54');
INSERT INTO `cpa_repertory` VALUES (23, '应收账款明细账一般采用的格式是(     )。', 'STATUTE_ETHICS', 'SINGLE', '2016-11-23 14:15:54');
INSERT INTO `cpa_repertory` VALUES (24, '对于大堆、笨重的材料物资盘存及确定，一般采用(     )法', 'STATUTE_ETHICS', 'SINGLE', '2018-02-20 14:15:54');
INSERT INTO `cpa_repertory` VALUES (25, '企业的下列费用中应计入存货成本的是（   ）。', 'STATUTE_ETHICS', 'SINGLE', '2018-02-04 14:15:54');
INSERT INTO `cpa_repertory` VALUES (26, '原始凭证是由（）取得或填制的。', 'STATUTE_ETHICS', 'SINGLE', '2018-02-20 14:15:54');
INSERT INTO `cpa_repertory` VALUES (27, '汇总记账凭证账务处理程序的优点是(     )', 'STATUTE_ETHICS', 'SINGLE', '2017-10-17 14:15:54');
INSERT INTO `cpa_repertory` VALUES (28, '企业购买或出售交易性金融资产过程中发生的交易费用应直接记入的会计科目是（）', 'STATUTE_ETHICS', 'SINGLE', '2017-04-27 14:15:54');
INSERT INTO `cpa_repertory` VALUES (29, '各单位每年形成的会计档案，都应由本单位(     ) 负责整理立卷，装订成册，编制会计档案保管清册。', 'STATUTE_ETHICS', 'SINGLE', '2017-08-30 14:15:54');
INSERT INTO `cpa_repertory` VALUES (30, '根据借贷记账法的账户结构，在账户借方登记的是(     )。', 'STATUTE_ETHICS', 'SINGLE', '2016-12-20 14:15:54');
INSERT INTO `cpa_repertory` VALUES (31, '　　根据《票据法》的规定，支票的记载事项可以授权补记的有()。 无忧考网：https://www.51test.net/show/8684757.html', 'BASIC_ACCOUNT', 'MULTIPLE', '2018-03-03 11:11:04');
INSERT INTO `cpa_repertory` VALUES (32, '下列各项中，属于增值税纳税人的有()。 无忧考网：https://www.51test.net/show/8684761.html', 'BASIC_ACCOUNT', 'MULTIPLE', '2018-03-14 21:44:44');

-- ----------------------------
-- Table structure for cpa_role
-- ----------------------------
DROP TABLE IF EXISTS `cpa_role`;
CREATE TABLE `cpa_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_role
-- ----------------------------
INSERT INTO `cpa_role` VALUES (1, 'admin', NULL);
INSERT INTO `cpa_role` VALUES (2, 'manager', NULL);
INSERT INTO `cpa_role` VALUES (3, 'normal', NULL);

-- ----------------------------
-- Table structure for cpa_solution
-- ----------------------------
DROP TABLE IF EXISTS `cpa_solution`;
CREATE TABLE `cpa_solution`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `re_id` bigint(20) NULL DEFAULT NULL COMMENT '题库ID',
  `result` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正确答案选项',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKh4999mnolnr2xxldwcna8o727`(`re_id`) USING BTREE,
  CONSTRAINT `FKh4999mnolnr2xxldwcna8o727` FOREIGN KEY (`re_id`) REFERENCES `cpa_repertory` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_solution
-- ----------------------------
INSERT INTO `cpa_solution` VALUES (1, 1, 'B');
INSERT INTO `cpa_solution` VALUES (2, 2, 'B');
INSERT INTO `cpa_solution` VALUES (3, 3, 'B');
INSERT INTO `cpa_solution` VALUES (4, 4, 'B');
INSERT INTO `cpa_solution` VALUES (5, 5, 'D');
INSERT INTO `cpa_solution` VALUES (6, 6, 'B');
INSERT INTO `cpa_solution` VALUES (7, 7, 'A');
INSERT INTO `cpa_solution` VALUES (8, 8, 'B');
INSERT INTO `cpa_solution` VALUES (9, 9, 'C');
INSERT INTO `cpa_solution` VALUES (10, 10, 'C');
INSERT INTO `cpa_solution` VALUES (11, 11, 'C');
INSERT INTO `cpa_solution` VALUES (12, 12, 'A');
INSERT INTO `cpa_solution` VALUES (13, 13, 'A');
INSERT INTO `cpa_solution` VALUES (14, 14, 'A');
INSERT INTO `cpa_solution` VALUES (15, 15, 'C');
INSERT INTO `cpa_solution` VALUES (16, 16, 'C');
INSERT INTO `cpa_solution` VALUES (17, 17, 'B');
INSERT INTO `cpa_solution` VALUES (18, 18, 'D');
INSERT INTO `cpa_solution` VALUES (19, 19, 'C');
INSERT INTO `cpa_solution` VALUES (20, 20, 'D');
INSERT INTO `cpa_solution` VALUES (21, 21, 'B');
INSERT INTO `cpa_solution` VALUES (22, 22, 'A');
INSERT INTO `cpa_solution` VALUES (23, 23, 'A');
INSERT INTO `cpa_solution` VALUES (24, 24, 'C');
INSERT INTO `cpa_solution` VALUES (25, 25, 'C');
INSERT INTO `cpa_solution` VALUES (26, 26, 'B');
INSERT INTO `cpa_solution` VALUES (27, 27, 'C');
INSERT INTO `cpa_solution` VALUES (28, 28, 'B');
INSERT INTO `cpa_solution` VALUES (29, 29, 'A');
INSERT INTO `cpa_solution` VALUES (30, 30, 'A');
INSERT INTO `cpa_solution` VALUES (31, 31, 'D,B');
INSERT INTO `cpa_solution` VALUES (32, 32, 'A,B,C,D');

-- ----------------------------
-- Table structure for cpa_user
-- ----------------------------
DROP TABLE IF EXISTS `cpa_user`;
CREATE TABLE `cpa_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆密码',
  `gender` tinyint(2) NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email',
  `pet_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `reg_date` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime(0) NULL DEFAULT NULL COMMENT '上次登陆时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '用户状态',
  `salt` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `img_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_user
-- ----------------------------
INSERT INTO `cpa_user` VALUES (1, 'admin', '0997cdf3576e93e1562a3d8b8467cc47', NULL, '2504849@qq.com', NULL, '2018-02-22 14:40:11', NULL, 1, '567841dde604e0bde6bd2837ac992feb', NULL);
INSERT INTO `cpa_user` VALUES (2, 'kanyun', 'ea0bd5db8ae426453038a77be3951627', NULL, '2504849@qq.com', NULL, '2018-02-22 14:44:29', NULL, 1, '85d20b4f7c329ae648a9631c630a00d5', NULL);
INSERT INTO `cpa_user` VALUES (3, 'chenwuwen', '7052506b83e0d190b99422c486b4aa79', NULL, '2504849@qq.com', NULL, '2018-02-22 14:45:31', NULL, 1, 'd6c6b261c31f8e41d3593088ceb42b4c', NULL);

-- ----------------------------
-- Table structure for cpa_user_extend
-- ----------------------------
DROP TABLE IF EXISTS `cpa_user_extend`;
CREATE TABLE `cpa_user_extend`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `invite_user` bigint(20) NULL DEFAULT NULL COMMENT '邀请人ID',
  `share_chain` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享链接',
  `share_qr_url` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享二维码图片地址',
  `reap_signIn_day` int(11) NULL DEFAULT NULL COMMENT '连续打卡天数',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK9sy9t4lajbaspcuryir7vll5y`(`user_id`) USING BTREE,
  CONSTRAINT `FK9sy9t4lajbaspcuryir7vll5y` FOREIGN KEY (`user_id`) REFERENCES `cpa_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cpa_user_extend
-- ----------------------------
INSERT INTO `cpa_user_extend` VALUES (1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cpa_user_extend` VALUES (2, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `cpa_user_extend` VALUES (3, 3, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKpf6l392icfk87b08q5wyquy1w`(`role_id`) USING BTREE,
  INDEX `FKc8p811lptx37fje7mw7b8rag5`(`permission_id`) USING BTREE,
  CONSTRAINT `FKc8p811lptx37fje7mw7b8rag5` FOREIGN KEY (`permission_id`) REFERENCES `cpa_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpf6l392icfk87b08q5wyquy1w` FOREIGN KEY (`role_id`) REFERENCES `cpa_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 2, 1);
INSERT INTO `role_permission` VALUES (6, 2, 2);
INSERT INTO `role_permission` VALUES (7, 2, 3);

-- ----------------------------
-- Table structure for user_collect
-- ----------------------------
DROP TABLE IF EXISTS `user_collect`;
CREATE TABLE `user_collect`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `petname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `re_id` bigint(20) NULL DEFAULT NULL COMMENT '试题ID',
  `collect_date` datetime(0) NULL DEFAULT NULL COMMENT '收藏时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '收藏状态 1：已收藏，0：收藏又取消收藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collect
-- ----------------------------
INSERT INTO `user_collect` VALUES (1, 2, 'kanyun', NULL, 31, '2018-03-10 21:35:27', 1);
INSERT INTO `user_collect` VALUES (2, 2, 'kanyun', NULL, 17, '2018-03-10 21:40:55', 0);
INSERT INTO `user_collect` VALUES (3, 2, 'kanyun', NULL, 16, '2018-03-10 21:42:31', 1);

-- ----------------------------
-- Table structure for user_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `petname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `re_id` bigint(20) NULL DEFAULT NULL COMMENT '试题ID',
  `comment_date` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收藏内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_comment
-- ----------------------------
INSERT INTO `user_comment` VALUES (1, 1, '1', '1', 1, '2018-03-27 11:15:03', NULL);
INSERT INTO `user_comment` VALUES (2, 3, '3', '3', 3, NULL, NULL);
INSERT INTO `user_comment` VALUES (3, 2, 'kanyun', NULL, 31, '2018-03-10 21:36:30', '这不是打广告么');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK5rlinoeaq40qamiqsegr84akq`(`user_id`) USING BTREE,
  INDEX `FK1paraflpnxfte8gnxukt9dm6a`(`role_id`) USING BTREE,
  CONSTRAINT `FK1paraflpnxfte8gnxukt9dm6a` FOREIGN KEY (`role_id`) REFERENCES `cpa_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK5rlinoeaq40qamiqsegr84akq` FOREIGN KEY (`user_id`) REFERENCES `cpa_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
