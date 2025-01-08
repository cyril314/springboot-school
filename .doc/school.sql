/*
 Navicat Premium Dump SQL

 Source Server         : 127.0.0.1l3306
 Source Server Type    : MySQL
 Source Server Version : 50709 (5.7.9)
 Source Host           : localhost:3306
 Source Schema         : school

 Target Server Type    : MySQL
 Target Server Version : 50709 (5.7.9)
 File Encoding         : 65001

 Date: 08/01/2025 15:26:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `id` bigint(5) UNSIGNED NOT NULL COMMENT '班级编号，302表示三年级二班',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名称，三年级二班',
  `head_teacher` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班主任姓名',
  `student_count` int(4) NOT NULL COMMENT '学生数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (302, '三年级二班', '赖导', 7);
INSERT INTO `classes` VALUES (301, '三年级一班', '曾近荣', 7);

-- ----------------------------
-- Table structure for classes_news
-- ----------------------------
DROP TABLE IF EXISTS `classes_news`;
CREATE TABLE `classes_news`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '公告编号',
  `class_id` bigint(10) UNSIGNED NOT NULL COMMENT '考试班级',
  `content` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `creat_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_class_news`(`class_id`, `creat_date`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes_news
-- ----------------------------
INSERT INTO `classes_news` VALUES (1, 302, '大家好，三年级二班将于本周六举行家长会，届时请各位学生家长准时到达开会，谢谢！', '2013-08-21 10:42:04');
INSERT INTO `classes_news` VALUES (2, 301, '各位学生家长注意了，最近有不少同学出现逃学的情况，还号称“逃学威龙”。', '2013-08-21 10:48:20');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '考试编号',
  `class_id` bigint(10) UNSIGNED NOT NULL COMMENT '考试班级',
  `course` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '科目',
  `exam_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '考试时间',
  `full_marks` decimal(4, 1) NOT NULL COMMENT '满分',
  `average` decimal(4, 1) NULL DEFAULT NULL COMMENT '平均分',
  `top_mark` decimal(4, 1) NULL DEFAULT NULL COMMENT '最高分',
  `lowest_mark` decimal(4, 1) NULL DEFAULT NULL COMMENT '最低分',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试说明',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_exams`(`id`) USING BTREE,
  INDEX `index_classes`(`class_id`, `course`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES (1, 301, '英语', '2013-08-21 10:56:07', 100.0, 84.1, 100.0, 59.0, '精诚中学三年级二班下学期英语单元测验');
INSERT INTO `exam` VALUES (2, 302, '数学', '2013-08-22 11:02:20', 100.0, 88.3, 100.0, 78.0, '数学单元测验');

-- ----------------------------
-- Table structure for exam_mark
-- ----------------------------
DROP TABLE IF EXISTS `exam_mark`;
CREATE TABLE `exam_mark`  (
  `student_id` bigint(10) UNSIGNED NOT NULL COMMENT '学生编号',
  `student_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生姓名',
  `exam_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '考试时间',
  `exam_id` bigint(10) UNSIGNED NOT NULL COMMENT '考试编号',
  `class_id` bigint(10) UNSIGNED NOT NULL COMMENT '考试班级',
  `mark` decimal(4, 1) NOT NULL COMMENT '分数',
  `rank` int(4) NULL DEFAULT NULL COMMENT '班级排名',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`student_id`) USING BTREE,
  INDEX `index_exam_mark`(`class_id`, `student_name`, `mark`, `exam_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_mark
-- ----------------------------
INSERT INTO `exam_mark` VALUES (3011, '周星星', '2013-08-21 10:56:07', 1, 301, 59.0, 7, '逃课就是这个下场');
INSERT INTO `exam_mark` VALUES (3012, '曹达华', '2013-08-21 10:56:07', 1, 301, 97.0, 3, '不错，有进步');
INSERT INTO `exam_mark` VALUES (3013, '大飞', '2013-08-21 10:56:07', 1, 301, 60.0, 6, '这次给你及格了');
INSERT INTO `exam_mark` VALUES (3014, '黄小龟', '2013-08-21 10:56:07', 1, 301, 84.0, 5, '好好学习天天向上');
INSERT INTO `exam_mark` VALUES (3015, '仙蒂', '2013-08-21 10:56:07', 1, 301, 100.0, 1, '满分不错，值得表扬');
INSERT INTO `exam_mark` VALUES (3016, '汤朱迪', '2013-08-21 10:56:07', 1, 301, 98.0, 2, '这次发挥有点小失常哟');
INSERT INTO `exam_mark` VALUES (3017, '林大岳', '2013-08-21 10:56:07', 1, 301, 91.0, 4, '继续努力');
INSERT INTO `exam_mark` VALUES (3021, '沈佳宜', '2013-08-22 11:02:20', 2, 302, 100.0, 1, '非常不错，好好保持');
INSERT INTO `exam_mark` VALUES (3022, '柯景腾', '2013-08-22 11:02:20', 2, 302, 80.0, 6, '上课认真听讲');
INSERT INTO `exam_mark` VALUES (3023, '曹国胜', '2013-08-22 11:02:20', 2, 302, 90.0, 3, '有进步');
INSERT INTO `exam_mark` VALUES (3024, '谢明和', '2013-08-22 11:02:20', 2, 302, 87.0, 5, '好好学习天天向上');
INSERT INTO `exam_mark` VALUES (3025, '廖英宏', '2013-08-22 11:02:20', 2, 302, 95.0, 2, '非常好');
INSERT INTO `exam_mark` VALUES (3026, '胡家玮', '2013-08-22 11:02:20', 2, 302, 88.0, 4, '继续努力');
INSERT INTO `exam_mark` VALUES (3027, '许博淳', '2013-08-22 11:02:20', 2, 302, 78.0, 7, '要加油了');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `to_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `from_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `msg_type` enum('TEXT','IMAGE','LOCATION','LINK','EVENT') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `creat_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `msg_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `location_x` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `location_y` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scale` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `event` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `event_key` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user`(`msg_type`, `to_user_name`, `from_user_name`, `creat_date`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `to_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `from_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `msg_type` enum('TEXT','MUSIC','NEWS') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `creat_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回复消息内容',
  `music_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hq_music_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `article_count` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user`(`msg_type`, `to_user_name`, `from_user_name`, `creat_date`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回复' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for reply_article
-- ----------------------------
DROP TABLE IF EXISTS `reply_article`;
CREATE TABLE `reply_article`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `reply_id` bigint(10) UNSIGNED NOT NULL,
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_replyid`(`reply_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回复文章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply_article
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(10) UNSIGNED NOT NULL COMMENT '学生学号，通常为班级编号加上序号，如3021',
  `class_id` bigint(10) UNSIGNED NOT NULL COMMENT '所属班级编号',
  `name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生姓名',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_classes`(`class_id`, `name`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (3021, 302, '沈佳宜', '佳宜（女主角）');
INSERT INTO `student` VALUES (3022, 302, '柯景腾', '柯腾（男主角）');
INSERT INTO `student` VALUES (3023, 302, '曹国胜', '老曹(柯腾的挚友)');
INSERT INTO `student` VALUES (3024, 302, '谢明和', '阿和(柯腾的挚友)');
INSERT INTO `student` VALUES (3025, 302, '廖英宏', '该边(柯腾的挚友)');
INSERT INTO `student` VALUES (3026, 302, '胡家玮', '弯弯(沈佳宜的好友)');
INSERT INTO `student` VALUES (3027, 302, '许博淳', '勃起(柯腾的挚友)');
INSERT INTO `student` VALUES (3011, 301, '周星星', '周星驰');
INSERT INTO `student` VALUES (3012, 301, '曹达华', '吴孟达');
INSERT INTO `student` VALUES (3013, 301, '大飞', '张耀扬');
INSERT INTO `student` VALUES (3014, 301, '黄小龟', '黄一山');
INSERT INTO `student` VALUES (3015, 301, '仙蒂', '朱茵');
INSERT INTO `student` VALUES (3016, 301, '汤朱迪', '梅艳芳');
INSERT INTO `student` VALUES (3017, 301, '林大岳', '黄秋生');

-- ----------------------------
-- Table structure for student_message
-- ----------------------------
DROP TABLE IF EXISTS `student_message`;
CREATE TABLE `student_message`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息编号',
  `student_id` bigint(10) UNSIGNED NOT NULL COMMENT '学生编号',
  `creat_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `content` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_student_message`(`student_id`, `creat_date`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_message
-- ----------------------------
INSERT INTO `student_message` VALUES (1, 3011, '2013-08-21 11:09:44', '不准逃课');
INSERT INTO `student_message` VALUES (2, 3021, '2013-08-22 10:56:15', '好好学习天天向上哈');

SET FOREIGN_KEY_CHECKS = 1;
