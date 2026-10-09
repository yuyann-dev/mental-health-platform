/*
 Navicat Premium Dump SQL

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : localhost:3306
 Source Schema         : mental_health

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 12/06/2025 18:11:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'http://localhost:9090/files/download/1734432198743-1721114905635-柴犬.jpeg', 'ADMIN', '18899990011', 'admin2@qq.com');

-- ----------------------------
-- Table structure for advice
-- ----------------------------
DROP TABLE IF EXISTS `advice`;
CREATE TABLE `advice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `doctor_id` int(11) NULL DEFAULT NULL COMMENT '医生ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `advice_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '诊疗建议内容',
  `advice_time` datetime NULL DEFAULT NULL COMMENT '诊疗建议时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '诊疗建议表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of advice
-- ----------------------------
INSERT INTO `advice` VALUES (1, 1, 1, '多喝热水', '2024-12-27 19:27:18');
INSERT INTO `advice` VALUES (2, 1, 1, 'dfgdfg', '2025-05-06 19:31:04');
INSERT INTO `advice` VALUES (3, 1, 7, '多吃水果蔬菜', '2025-05-06 20:20:36');
INSERT INTO `advice` VALUES (4, 1, 7, '多运动', '2025-05-06 20:21:06');
INSERT INTO `advice` VALUES (5, 1, 7, '多喝热水', '2025-05-06 20:21:24');
INSERT INTO `advice` VALUES (6, 1, 7, 'dfgdfgdf', '2025-05-06 20:39:42');
INSERT INTO `advice` VALUES (7, 1, 7, '好好吃饭', '2025-05-06 20:39:53');
INSERT INTO `advice` VALUES (8, 1, 8, '好好吃饭', '2025-05-06 20:40:50');

-- ----------------------------
-- Table structure for answer_record
-- ----------------------------
DROP TABLE IF EXISTS `answer_record`;
CREATE TABLE `answer_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test_record_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of answer_record
-- ----------------------------
INSERT INTO `answer_record` VALUES (1, 1, 1, 'd');
INSERT INTO `answer_record` VALUES (2, 1, 2, 'a');
INSERT INTO `answer_record` VALUES (3, 1, 3, 'a');
INSERT INTO `answer_record` VALUES (4, 1, 4, 'c');
INSERT INTO `answer_record` VALUES (5, 1, 5, 'd');
INSERT INTO `answer_record` VALUES (6, 1, 6, 'd');
INSERT INTO `answer_record` VALUES (7, 1, 7, 'a');
INSERT INTO `answer_record` VALUES (8, 1, 8, 'd');
INSERT INTO `answer_record` VALUES (9, 1, 9, 'a');
INSERT INTO `answer_record` VALUES (10, 1, 10, 'd');
INSERT INTO `answer_record` VALUES (11, 1, 11, 'c');
INSERT INTO `answer_record` VALUES (12, 1, 12, 'd');
INSERT INTO `answer_record` VALUES (13, 1, 13, 'd');
INSERT INTO `answer_record` VALUES (14, 1, 14, 'b');
INSERT INTO `answer_record` VALUES (15, 1, 15, 'a');
INSERT INTO `answer_record` VALUES (16, 1, 16, 'd');
INSERT INTO `answer_record` VALUES (17, 1, 17, 'd');
INSERT INTO `answer_record` VALUES (18, 1, 18, 'a');
INSERT INTO `answer_record` VALUES (19, 1, 19, 'd');
INSERT INTO `answer_record` VALUES (20, 1, 20, 'd');
INSERT INTO `answer_record` VALUES (21, 2, 21, 'a');
INSERT INTO `answer_record` VALUES (22, 2, 22, 'b');
INSERT INTO `answer_record` VALUES (23, 2, 23, 'c');
INSERT INTO `answer_record` VALUES (24, 2, 24, 'd');
INSERT INTO `answer_record` VALUES (25, 2, 25, 'a');
INSERT INTO `answer_record` VALUES (26, 2, 26, 'a');
INSERT INTO `answer_record` VALUES (27, 2, 27, 'c');
INSERT INTO `answer_record` VALUES (28, 2, 28, 'b');
INSERT INTO `answer_record` VALUES (29, 2, 29, 'd');
INSERT INTO `answer_record` VALUES (30, 2, 30, 'b');
INSERT INTO `answer_record` VALUES (31, 2, 31, 'b');
INSERT INTO `answer_record` VALUES (32, 2, 32, 'a');
INSERT INTO `answer_record` VALUES (33, 2, 33, 'c');
INSERT INTO `answer_record` VALUES (34, 2, 34, 'd');
INSERT INTO `answer_record` VALUES (35, 2, 35, 'c');
INSERT INTO `answer_record` VALUES (36, 2, 36, 'c');
INSERT INTO `answer_record` VALUES (37, 2, 37, 'd');
INSERT INTO `answer_record` VALUES (38, 2, 38, 'b');
INSERT INTO `answer_record` VALUES (39, 2, 39, 'c');
INSERT INTO `answer_record` VALUES (40, 2, 40, 'b');
INSERT INTO `answer_record` VALUES (41, 3, 21, 'b');
INSERT INTO `answer_record` VALUES (42, 3, 22, 'c');
INSERT INTO `answer_record` VALUES (43, 3, 23, 'b');
INSERT INTO `answer_record` VALUES (44, 3, 24, 'd');
INSERT INTO `answer_record` VALUES (45, 3, 25, 'c');
INSERT INTO `answer_record` VALUES (46, 3, 26, 'c');
INSERT INTO `answer_record` VALUES (47, 3, 27, 'd');
INSERT INTO `answer_record` VALUES (48, 3, 28, 'c');
INSERT INTO `answer_record` VALUES (49, 3, 29, 'a');
INSERT INTO `answer_record` VALUES (50, 3, 30, 'b');
INSERT INTO `answer_record` VALUES (51, 3, 31, 'b');
INSERT INTO `answer_record` VALUES (52, 3, 32, 'd');
INSERT INTO `answer_record` VALUES (53, 3, 33, 'b');
INSERT INTO `answer_record` VALUES (54, 3, 34, 'c');
INSERT INTO `answer_record` VALUES (55, 3, 35, 'a');
INSERT INTO `answer_record` VALUES (56, 3, 36, 'a');
INSERT INTO `answer_record` VALUES (57, 3, 37, 'd');
INSERT INTO `answer_record` VALUES (58, 3, 38, 'c');
INSERT INTO `answer_record` VALUES (59, 3, 39, 'b');
INSERT INTO `answer_record` VALUES (60, 3, 40, 'a');
INSERT INTO `answer_record` VALUES (61, 4, 1, 'd');
INSERT INTO `answer_record` VALUES (62, 4, 2, 'a');
INSERT INTO `answer_record` VALUES (63, 4, 3, 'a');
INSERT INTO `answer_record` VALUES (64, 4, 4, 'c');
INSERT INTO `answer_record` VALUES (65, 4, 5, 'd');
INSERT INTO `answer_record` VALUES (66, 4, 6, 'a');
INSERT INTO `answer_record` VALUES (67, 4, 7, 'd');
INSERT INTO `answer_record` VALUES (68, 4, 8, 'd');
INSERT INTO `answer_record` VALUES (69, 4, 9, 'a');
INSERT INTO `answer_record` VALUES (70, 4, 10, 'd');
INSERT INTO `answer_record` VALUES (71, 4, 11, 'c');
INSERT INTO `answer_record` VALUES (72, 4, 12, 'a');
INSERT INTO `answer_record` VALUES (73, 4, 13, 'd');
INSERT INTO `answer_record` VALUES (74, 4, 14, 'b');
INSERT INTO `answer_record` VALUES (75, 4, 15, 'd');
INSERT INTO `answer_record` VALUES (76, 4, 16, 'a');
INSERT INTO `answer_record` VALUES (77, 4, 17, 'a');
INSERT INTO `answer_record` VALUES (78, 4, 18, 'a');
INSERT INTO `answer_record` VALUES (79, 4, 19, 'd');
INSERT INTO `answer_record` VALUES (80, 4, 20, 'a');
INSERT INTO `answer_record` VALUES (81, 5, 50, 'B');
INSERT INTO `answer_record` VALUES (82, 5, 51, 'C');
INSERT INTO `answer_record` VALUES (83, 5, 52, 'B');
INSERT INTO `answer_record` VALUES (84, 5, 53, 'A');
INSERT INTO `answer_record` VALUES (85, 5, 54, 'C');
INSERT INTO `answer_record` VALUES (86, 5, 55, 'B');
INSERT INTO `answer_record` VALUES (87, 5, 56, 'C');
INSERT INTO `answer_record` VALUES (88, 5, 57, 'B');
INSERT INTO `answer_record` VALUES (89, 5, 58, 'A');
INSERT INTO `answer_record` VALUES (90, 5, 59, 'C');
INSERT INTO `answer_record` VALUES (91, 6, 60, 'C');
INSERT INTO `answer_record` VALUES (92, 6, 61, 'B');
INSERT INTO `answer_record` VALUES (93, 6, 62, 'C');
INSERT INTO `answer_record` VALUES (94, 6, 63, 'D');
INSERT INTO `answer_record` VALUES (95, 6, 64, 'C');
INSERT INTO `answer_record` VALUES (96, 7, 65, 'B');
INSERT INTO `answer_record` VALUES (97, 7, 66, 'C');
INSERT INTO `answer_record` VALUES (98, 7, 67, 'B');
INSERT INTO `answer_record` VALUES (99, 7, 68, 'A');
INSERT INTO `answer_record` VALUES (100, 7, 69, 'C');
INSERT INTO `answer_record` VALUES (101, 8, 70, 'B');
INSERT INTO `answer_record` VALUES (102, 8, 71, 'C');
INSERT INTO `answer_record` VALUES (103, 8, 72, 'B');
INSERT INTO `answer_record` VALUES (104, 8, 73, 'C');
INSERT INTO `answer_record` VALUES (105, 8, 74, 'B');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `seniority` int(11) NULL DEFAULT NULL COMMENT '工龄',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '简介',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `certificate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资格证',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批状态',
  `mbwt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密保问题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '心理医生表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (1, 'xiaoming', 'e10adc3949ba59abbe56e057f20f883e', '小明', 'http://localhost:9090/files/download/1734432198743-1721114905635-柴犬.jpeg', 'DOCTOR', 2, '24岁，事大学生（心虚）', '13712345678', 'xiaoming@qq.com', '341223198509079011', 'http://localhost:9090/files/download/零秩矩阵.png', '审批通过', '电棍笑传');

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `doctor_id` int(11) NULL DEFAULT NULL COMMENT '医生ID',
  `start` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '问题描述',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请时间',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批状态',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拒绝理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (1, 1, 1, '2024-11-13 18:41:31', '2024-11-13 20:41:31', '我最近有点抑郁，想找您聊聊', '2024-11-12 20:41:53', '审核通过', NULL);
INSERT INTO `reservation` VALUES (2, 1, 1, '2024-11-14 20:53:39', '2024-11-14 21:53:39', '我想和您聊聊', '2024-11-12 20:53:56', '已结束', NULL);

-- ----------------------------
-- Table structure for test_paper
-- ----------------------------
DROP TABLE IF EXISTS `test_paper`;
CREATE TABLE `test_paper`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷名称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷封面',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '试卷介绍',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '分类ID',
  `doctor_id` int(11) NULL DEFAULT NULL COMMENT '医生ID',
  `num` int(11) NULL DEFAULT NULL COMMENT '题目数量',
  `score` int(11) NULL DEFAULT NULL COMMENT '试卷总分',
  `ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所有题目ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷状态',
  `test_num` int(11) NULL DEFAULT 0 COMMENT '测试人数',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `a_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '底部区间',
  `b_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中部区间',
  `c_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '顶部区间',
  `a_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '底部解答',
  `b_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '中部解答',
  `c_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '顶部解答',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_paper
-- ----------------------------
INSERT INTO `test_paper` VALUES (1, '抑郁自评量表SDS', 'http://localhost:9090/files/download/555.png', '抑郁自评量表SDS', 1, 1, 20, 100, '[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]', '审核通过', 9, '2024-11-14 23:27:31', '0~50', '50~70', '70~100', '正常', '轻至中度抑郁', '重度抑郁');
INSERT INTO `test_paper` VALUES (2, 'CES-D抑郁自评量表', 'http://localhost:9090/files/download/666.png', 'CES-D抑郁自评量表。', 2, 1, 20, 60, '[21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40]', '审核通过', 3, '2024-11-17 14:55:25', '0~15', '15~20', '20~60', '无抑郁症状', '可能有抑郁症状', '肯定是有抑郁症状，建议看心理医生');
INSERT INTO `test_paper` VALUES (3, 'PHQ-9抑郁症筛查量表', 'http://localhost:9090/files/download/777.png', 'PHQ-9抑郁症筛查量表。', 3, 1, 9, 27, '[41,42,43,44,45,46,47,48,49]', '审核通过', 1, '2024-11-17 14:55:25', '0~9', '10~19', '20~27', '无抑郁症状或轻微抑郁症', '中度或中重度抑郁症', '可能有重度抑郁症');
INSERT INTO `test_paper` VALUES (4, '焦虑自评量表(SAS)', 'http://localhost:9090/files/download/sas.png', '焦虑自评量表(SAS)用于评估个人焦虑程度，包含10个评估项目。', 4, 1, 10, 40, '[50,51,52,53,54,55,56,57,58,59]', '审核通过', 0, '2025-05-06 22:26:44', '0~20', '21~30', '31~40', '焦虑程度轻微，建议保持良好的生活习惯和心态。', '存在中度焦虑，建议适当进行心理咨询。', '存在严重焦虑，建议及时就医并进行专业心理治疗。');
INSERT INTO `test_paper` VALUES (5, '社交焦虑量表(LSAS)', 'http://localhost:9090/files/download/lsas.png', '社交焦虑量表(LSAS)用于评估社交焦虑障碍，包含5个评估项目。', 5, 1, 5, 15, '[60,61,62,63,64]', '审核通过', 0, '2025-05-06 22:26:44', '0~5', '6~10', '11~15', '社交焦虑程度轻微，社交能力正常。', '存在中度社交焦虑，建议进行社交技能训练。', '存在严重社交焦虑，建议寻求专业心理治疗。');
INSERT INTO `test_paper` VALUES (6, '强迫症状自评量表(MOCI)', 'http://localhost:9090/files/download/moci.png', '强迫症状自评量表(MOCI)用于评估强迫症状，包含5个评估项目。', 6, 1, 5, 15, '[65,66,67,68,69]', '审核通过', 0, '2025-05-06 22:26:44', '0~5', '6~10', '11~15', '强迫症状轻微，心理状态良好。', '存在中度强迫症状，建议进行心理咨询。', '存在严重强迫症状，建议进行专业心理治疗。');
INSERT INTO `test_paper` VALUES (7, '创伤后应激障碍量表(PCL-5)', 'http://localhost:9090/files/download/pcl.png', '创伤后应激障碍量表(PCL-5)用于评估创伤后应激障碍，包含5个评估项目。', 7, 1, 5, 15, '[70,71,72,73,74]', '审核通过', 0, '2025-05-06 22:26:44', '0~5', '6~10', '11~15', 'PTSD症状轻微，建议保持积极心态。', '存在中度PTSD症状，建议寻求心理咨询。', '存在严重PTSD症状，建议及时进行专业心理治疗。');

-- ----------------------------
-- Table structure for test_record
-- ----------------------------
DROP TABLE IF EXISTS `test_record`;
CREATE TABLE `test_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `test_paper_id` int(11) NULL DEFAULT NULL COMMENT '测试卷ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `doctor_id` int(11) NULL DEFAULT NULL COMMENT '医生ID',
  `score` int(11) NULL DEFAULT NULL COMMENT '分数',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '测试结果',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '测试时间',
  `ai_result` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_record
-- ----------------------------
INSERT INTO `test_record` VALUES (1, 1, 1, 1, 60, '轻至中度抑郁', '2024-12-10 21:37:58', '<p>根据这份问卷的回答，填写者的心理状态可能表现出以下特点：<br><br>1.<strong>情绪低落</strong>：填写者在\"我觉得闷闷不乐，情绪消沉\"和\"我一阵阵哭出来或觉得想哭\"等题目中选择了较高分值的选项，表明可能存在情绪低落或抑郁的倾向。<br><br>2.<strong>睡眠问题</strong>：在\"我晚上睡眠不好\"一题中，填写者选择了\"有时\"，表明可能存在一定的睡眠困扰，但程度不严重。<br><br>3.<strong>食欲和体重变化</strong>：填写者在\"我吃得跟平常一样多\"和\"我觉察我的体重在下降\"中选择了不同的选项，表明可能存在食欲和体重波动的情况。<br><br>4.<strong>焦虑和不安</strong>：在\"我觉得不安而平静不下来\"一题中，填写者选择了较低分值的选项，表明焦虑感不明显。但在\"我心跳比平时快\"和\"我比平常容易生气冲动\"中选择了较高分值的选项，表明可能存在一定的焦虑或情绪波动。<br><br>5.<strong>自我价值感较低</strong>：在\"我觉得自己是个有用的人，有人需要我\"和\"我的生活过得很有意思\"等题目中，填写者选择了较低分值的选项，表明可能对自我价值和生活意义感存在一定的怀疑或消极看法。<br><br>6.<strong>对未来抱有希望</strong>：在\"我对将来抱有希望\"一题中，填写者选择了\"大局部时间\"，表明虽然存在情绪低落，但仍对未来抱有希望。<br><br>7.<strong>兴趣减退</strong>：在\"平常感兴趣的事，我仍然照样感兴趣\"一题中，填写者选择了较低分值的选项，表明可能对日常活动的兴趣有所减退。<br><br>###总结：<br>填写者可能处于一种情绪低落、焦虑和自我价值感较低的状态，但尚未完全失去对未来的希望。可能存在轻度的抑郁倾向，建议进一步关注心理健康，必要时寻求专业帮助。</p>\n');
INSERT INTO `test_record` VALUES (2, 2, 1, 1, 44, '肯定有抑郁症状，建议看心理医生', '2024-12-11 21:39:10', '<p>根据这份问卷的回答，填写者的心理状态显示出明显的抑郁倾向。以下是对整体情况的分析：<br><br>1.<strong>情绪低落与消极思维</strong>：填写者在多个问题中表现出情绪低落（如\"我感到情绪低落\"、\"我感到不高兴\"、\"我感到忧愁\"），并且对未来感到无望（如\"我感到前途没有希望\"、\"我觉得生活没有意思\"）。这些回答表明填写者可能处于一种持续的消极情绪状态。<br><br>2.<strong>自我评价低</strong>：填写者对自己的评价较低（如\"我觉得不如多数人好\"、\"我觉得我的生活是失败的\"），这可能反映出自我价值感的缺失或自卑感。<br><br>3.<strong>社交孤立感</strong>：填写者感到孤单，并认为人们对他们不友好（如\"我感到孤单\"、\"我觉得人们对我不太友好\"、\"我感到人们不喜欢我\"），这可能表明他们在社交关系中感到孤立或被排斥。<br><br>4.<strong>精力不足与注意力不集中</strong>：填写者表示在做事情时无法集中注意力，并且感到做任何事都很费力（如\"我在做事时无法集中注意力\"、\"我感到做任何事都很费力\"），这可能反映出精力不足或动力缺乏。<br><br>5.<strong>睡眠与食欲问题</strong>：虽然填写者在睡眠和食欲方面的回答相对较轻（选择了\"时常或一半时间\"或\"有时\"），但这些问题的存在仍然可能表明他们的生理状态受到了心理状态的影响。<br><br>6.<strong>无助感</strong>：填写者表示即使家人和朋友帮助，仍然无法摆脱心中的苦闷（如\"即使家人和朋友帮助我，我仍然无法摆脱心中苦闷\"），这可能表明他们感到无助或无法通过外部支持来改善自己的情绪状态。<br><br>###总结：<br>填写者的心理状态显示出明显的抑郁症状，包括情绪低落、自我评价低、社交孤立感、精力不足、注意力不集中以及无助感。建议填写者寻求专业的心理帮助，如心理咨询或治疗，以进一步评估和改善其心理状态。</p>\n');
INSERT INTO `test_record` VALUES (3, 2, 1, 1, 66, '肯定有抑郁症状，建议看心理医生', '2024-12-11 21:58:55', '<p>根据这份问卷调查的结果，填写者的心理状态显示出一定程度的情绪低落和焦虑。以下是对填写者心理状态的综合分析：<br><br>1.<strong>情绪低落</strong>：填写者在多个问题中表现出情绪低落的迹象。例如，他们感到\"情绪低落\"（C）、\"感到忧愁\"（C）、\"觉得生活没有意思\"（A）和\"觉得我的生活是失败的\"（A）。这些回答表明填写者可能正在经历一定程度的抑郁情绪。<br><br>2.<strong>焦虑和不安</strong>：填写者在一些问题中表现出焦虑和不安。例如，他们\"感到害怕\"（B）、\"感到孤单\"（C）和\"觉得人们对我不太友好\"（A）。这些回答表明填写者可能对社交关系和外界环境感到不安。<br><br>3.<strong>自我评价较低</strong>：填写者在\"我觉得不如多数人好\"（D）和\"我觉得我无法继续我的生活\"（A）等问题中表现出较低的自我评价。这表明填写者可能对自己的能力和生活前景缺乏信心。<br><br>4.<strong>睡眠和食欲问题</strong>：填写者在\"我的睡眠不好\"（B）和\"我不想吃东西，我胃口不好\"（C）等问题中表现出睡眠和食欲方面的问题。这些问题通常与情绪低落和焦虑有关。<br><br>5.<strong>社交退缩</strong>：填写者在\"我比平时说话要少\"（B）和\"我感到人们不喜欢我\"（B）等问题中表现出社交退缩的迹象。这表明填写者可能在社交互动中感到不适或缺乏自信。<br><br>总体来看，填写者的心理状态显示出一定程度的抑郁、焦虑和自我怀疑。他们可能在情绪调节、自我评价和社交互动方面遇到困难。建议填写者寻求专业的心理帮助，以进一步评估和改善他们的心理状态。</p>\n');
INSERT INTO `test_record` VALUES (4, 1, 1, 1, 30, '正常', '2024-12-12 22:35:14', '<p>根据这份问卷的回答，填写者的心理状态总体较为积极，但存在一些情绪波动和轻微的身体不适。以下是对填写者心理状态的简要分析：<br><br>1.<strong>情绪状态</strong>：填写者大多数时候情绪较为稳定，很少感到闷闷不乐或情绪消沉（选择D），并且对生活充满希望（选择B）。然而，填写者有时会感到不安或情绪波动（选择D），偶尔会有哭泣的冲动（选择A），这表明可能存在一定的情绪压力或焦虑。<br><br>2.<strong>睡眠和饮食</strong>：填写者的睡眠质量一般，有时会感到睡眠不好（选择C），但饮食基本正常（选择D），没有明显的体重下降（选择D）。这表明填写者的生理状态相对稳定，但睡眠问题可能对情绪产生一定影响。<br><br>3.<strong>身体状态</strong>：填写者有时会感到心跳加快（选择A），但很少感到无缘无故的疲乏（选择D）。这表明填写者可能偶尔会有身体上的不适，但整体身体状况尚可。<br><br>4.<strong>自我认知和生活态度</strong>：填写者对自己的能力、价值和生活的意义持积极态度（选择A），认为自己是有用的、生活有意思，并且对平常感兴趣的事仍然保持兴趣。这表明填写者有较强的自我认同感和生活满足感。<br><br>5.<strong>情绪控制</strong>：填写者很少感到不安或容易生气冲动（选择D），并且认为做出决定是容易的（选择A）。这表明填写者在情绪管理和决策能力方面表现较好。<br><br><strong>总结</strong>：填写者的心理状态总体较为积极，情绪稳定，生活态度乐观，自我认同感强。然而，填写者偶尔会有情绪波动、睡眠问题和轻微的身体不适，可能需要注意情绪管理和身体健康。建议填写者继续保持积极的生活态度，同时关注睡眠质量和情绪调节，以维持良好的心理和生理健康。</p>\n');
INSERT INTO `test_record` VALUES (6, 4, 1, 1, 25, '存在中度焦虑，建议适当进行心理咨询。', '2025-05-06 22:30:00', '根据测试结果显示，您目前存在中度焦虑症状，建议进行心理咨询和适当的放松训练。');
INSERT INTO `test_record` VALUES (7, 5, 1, 1, 12, '存在严重社交焦虑，建议寻求专业心理治疗。', '2025-05-06 22:35:00', '测试结果表明您存在明显的社交焦虑特征，建议寻求专业心理医生的帮助。');
INSERT INTO `test_record` VALUES (8, 6, 1, 1, 8, '存在中度强迫症状，建议进行心理咨询。', '2025-05-06 22:40:00', '您的测试结果显示存在一定程度的强迫症状，建议及时进行心理咨询。');
INSERT INTO `test_record` VALUES (9, 7, 1, 1, 10, '存在中度PTSD症状，建议寻求心理咨询。', '2025-05-06 22:45:00', '根据量表评估，您可能存在创伤后应激障碍的症状，建议进行专业的心理治疗。');
INSERT INTO `test_record` VALUES (10, 1, 9, NULL, 51, '轻至中度抑郁', '2025-06-12 17:08:17', NULL);
INSERT INTO `test_record` VALUES (11, 1, 9, NULL, 51, '轻至中度抑郁', '2025-06-12 17:08:21', NULL);
INSERT INTO `test_record` VALUES (12, 1, 9, NULL, 51, '轻至中度抑郁', '2025-06-12 17:08:26', NULL);
INSERT INTO `test_record` VALUES (13, 1, 9, NULL, 51, '轻至中度抑郁', '2025-06-12 17:09:05', NULL);
INSERT INTO `test_record` VALUES (14, 1, 9, NULL, 51, '轻至中度抑郁', '2025-06-12 17:12:51', '<p>根据本次测评结果分析，您的心理状态处于中度水平：<br><br>1.<strong>情绪症状</strong>：您的情绪状态相对稳定，较少出现情绪低落的情况。<br><br>2.<strong>躯体症状</strong>：您的睡眠质量较好，身体状况良好。<br><br>建议：<br>1. 保持规律的作息时间<br>2. 适当进行运动<br>3. 与家人朋友多交流<br>4. 必要时寻求专业心理咨询</p>');
INSERT INTO `test_record` VALUES (15, 1, 9, NULL, 51, '轻至中度抑郁', '2025-06-12 17:14:15', '<p>根据本次测评结果分析，您的心理状态处于中度水平：<br><br>1.<strong>情绪症状</strong>：您可能经常感到情绪低落，建议寻求专业帮助。<br><br>2.<strong>躯体症状</strong>：您的睡眠质量较好，身体状况良好。<br><br>建议：<br>1. 保持规律的作息时间<br>2. 适当进行运动<br>3. 与家人朋友多交流<br>4. 必要时寻求专业心理咨询</p>');
INSERT INTO `test_record` VALUES (16, 2, 9, NULL, 49, '肯定是有抑郁症状，建议看心理医生', '2025-06-12 17:16:33', '<p>根据本次测评结果分析，您的心理状态处于重度水平：<br><br></p>');
INSERT INTO `test_record` VALUES (17, 3, 9, NULL, 9, '无抑郁症状或轻微抑郁症', '2025-06-12 17:17:26', '<p>根据本次测评结果分析，您的心理状态处于轻度水平：<br><br></p>');
INSERT INTO `test_record` VALUES (18, 1, 9, NULL, 53, '轻至中度抑郁', '2025-06-12 17:34:38', '<p>根据本次测评结果分析，您的心理状态处于中度水平：<br><br>1.<strong>情绪症状</strong>：您可能经常感到情绪低落，建议寻求专业帮助。<br><br>2.<strong>躯体症状</strong>：您的睡眠质量较好，身体状况良好。<br><br>建议：<br>1. 保持规律的作息时间<br>2. 适当进行运动<br>3. 与家人朋友多交流<br>4. 必要时寻求专业心理咨询</p>');

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题目名称',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '分类ID',
  `a_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项A',
  `a_score` int(11) NULL DEFAULT NULL COMMENT '选项A分数',
  `b_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项B',
  `b_score` int(11) NULL DEFAULT NULL COMMENT '选项B分数',
  `c_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项C',
  `c_score` int(11) NULL DEFAULT NULL COMMENT '选项C分数',
  `d_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项D',
  `d_score` int(11) NULL DEFAULT NULL COMMENT '选项D分数',
  `score` int(11) NULL DEFAULT NULL COMMENT '最高分数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES (1, '我觉得闷闷不乐，情绪消沉。', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (2, '我觉得一天之中早晨最好。', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (3, '我一阵阵哭出来或觉得想哭。', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (4, '我晚上睡眠不好。', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (5, '我吃得跟平常一样多', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (6, '我与异性密切接触时和一样一样感到愉快', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (7, '我觉察我的体重在下降', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (8, '我有便秘的苦恼', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (9, '我心跳比平时快', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (10, '我无缘无故的感到疲乏', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (11, '我的头脑跟平常一样清楚', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (12, '我觉得经常做的事情并没有困难', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (13, '我觉得不安而平静不下来', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (14, '我对将来抱有希望', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (15, '我比平常容易生气冲动', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (16, '我觉得做出决定是容易的', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (17, '我觉得自己是个有用对人，有人需要我 ', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (18, '我的生活过得很有意思', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (19, '我认为我死了别人会生活得好些', 1, '绝大多数时间', 4, '大局部时间', 3, '有时', 2, '很少', 1, 4);
INSERT INTO `topic` VALUES (20, '平常感兴趣的事，我仍然照样感兴趣', 1, '绝大多数时间', 1, '大局部时间', 2, '有时', 3, '很少', 4, 4);
INSERT INTO `topic` VALUES (21, '我因一些小事而烦恼；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (22, '我不想吃东西，我胃口不好；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (23, '即使家人和朋友帮助我，我仍然无法摆脱心中苦闷；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (24, '我觉得不如多数人好；', 2, '时常或一半时间', 4, '有时', 3, '偶尔或无', 2, '无', 1, 4);
INSERT INTO `topic` VALUES (25, '我在做事时无法集中注意力；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (26, '我感到情绪低落；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (27, '我感到做任何事都很费力；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (28, '我感到前途没有希望；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (29, '我觉得我的生活是失败的；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (30, '我感到害怕；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (31, '我的睡眠不好；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (32, '我感到不高兴；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (33, '我比平时说话要少；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (34, '我感到孤单；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (35, '我觉得人们对我不太友好；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (36, '我觉得生活没有意思；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (37, '我曾哭泣； ', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (38, '我感到忧愁；', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (39, '我感到人们不喜欢我；', 2, '多数时间或持续', 4, '时常或一半时间', 3, '有时', 2, '偶尔或无', 1, 4);
INSERT INTO `topic` VALUES (40, '我觉得我无法继续我的生活。', 2, '多数时间或持续', 1, '时常或一半时间', 2, '有时', 3, '偶尔或无', 4, 4);
INSERT INTO `topic` VALUES (41, '做事时提不起劲或没有兴趣。', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (42, '感到心情低落、沮丧或绝望。', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (43, '入睡困难、睡不安稳或睡眠过多。', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (44, '感觉疲倦或没有活力。', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (45, '食欲不振或吃太多', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (46, '觉得自己很糟，或觉得自己很失败，或让自己或家人失望', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (47, '对事物专注有困难，例如阅读报纸或看电视时不能集中注意力', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (48, '动作或说话速度缓慢到别人已经觉察？或正好相反，烦躁或坐立不安、动来动去的情况更胜于平常', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (49, '有不如死掉或用某种方式伤害自己的念头', 3, '几乎每天', 3, '一半以上时间', 2, '有几天', 1, '没有', 0, 3);
INSERT INTO `topic` VALUES (50, '我感到比平常更加紧张和焦虑', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (51, '我无缘无故地感到害怕', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (52, '我容易心烦意乱或感到恐慌', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (53, '我觉得我快要发疯了', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (54, '我感到一切都很好，也不会发生什么不幸', 4, '很少', 4, '有时', 3, '经常', 2, '总是如此', 1, 4);
INSERT INTO `topic` VALUES (55, '我手脚发抖打颤', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (56, '我因为头痛、颈痛和背痛而苦恼', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (57, '我感到容易衰弱和疲乏', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (58, '我心平气和，并且容易安静坐着', 4, '很少', 4, '有时', 3, '经常', 2, '总是如此', 1, 4);
INSERT INTO `topic` VALUES (59, '我感到心跳得很快', 4, '很少', 1, '有时', 2, '经常', 3, '总是如此', 4, 4);
INSERT INTO `topic` VALUES (60, '在公共场合讲话时感到紧张', 5, '从不', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (61, '与陌生人交谈时感到不适', 5, '从不', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (62, '参加社交活动时感到焦虑', 5, '从不', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (63, '在他人注视下工作或活动时紧张', 5, '从不', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (64, '成为关注的中心时感到不自在', 5, '从不', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (65, '反复检查门窗是否锁好', 6, '从不', 1, '偶尔', 2, '经常', 3, '总是', 4, 4);
INSERT INTO `topic` VALUES (66, '过分担心清洁卫生', 6, '从不', 1, '偶尔', 2, '经常', 3, '总是', 4, 4);
INSERT INTO `topic` VALUES (67, '反复确认事情是否做对', 6, '从不', 1, '偶尔', 2, '经常', 3, '总是', 4, 4);
INSERT INTO `topic` VALUES (68, '有强迫性的想法困扰', 6, '从不', 1, '偶尔', 2, '经常', 3, '总是', 4, 4);
INSERT INTO `topic` VALUES (69, '需要按特定顺序做事', 6, '从不', 1, '偶尔', 2, '经常', 3, '总是', 4, 4);
INSERT INTO `topic` VALUES (70, '反复出现令人痛苦的记忆', 7, '完全没有', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (71, '对某些提醒性事物产生强烈的身心反应', 7, '完全没有', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (72, '回避与创伤有关的想法或感受', 7, '完全没有', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (73, '对人和世界的看法变得消极', 7, '完全没有', 1, '轻微', 2, '中等', 3, '严重', 4, 4);
INSERT INTO `topic` VALUES (74, '容易受到惊吓或警觉性增高', 7, '完全没有', 1, '轻微', 2, '中等', 3, '严重', 4, 4);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '心理分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '抑郁自评量表SDS');
INSERT INTO `type` VALUES (2, 'CES-D抑郁自评量表');
INSERT INTO `type` VALUES (3, 'PHQ-9抑郁症筛查量表');
INSERT INTO `type` VALUES (4, '焦虑自评量表(SAS)');
INSERT INTO `type` VALUES (5, '社交焦虑量表(LSAS)');
INSERT INTO `type` VALUES (6, '强迫症状自评量表(MOCI)');
INSERT INTO `type` VALUES (7, '创伤后应激障碍量表(PCL-5)');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'xiaogang', 'e10adc3949ba59abbe56e057f20f883e', '小刚', 'USER', '1145141919810', 'xiaogang@qq.com', 'http://localhost:9090/files/download/233.jpeg');
INSERT INTO `user` VALUES (6, '123', '202cb962ac59075b964b07152d234b70', '123', 'USER', NULL, NULL, NULL);
INSERT INTO `user` VALUES (7, '1234', '81dc9bdb52d04dc20036dbd8313ed055', '1234', 'USER', '1234', '1234', 'http://192.168.0.105:9090/files/download/1746533484730-爆炒空心菜.jpg');
INSERT INTO `user` VALUES (8, '12345', '827ccb0eea8a706c4c34a16891f84e7b', '12345', 'USER', '1151253718', '12789569@qq.com', 'http://localhost:9090/files/download/1746535226696-爆炒空心菜.jpg');
INSERT INTO `user` VALUES (9, '111111', '96e79218965eb72c92a549dd5a330112', '111111', 'USER', NULL, NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
